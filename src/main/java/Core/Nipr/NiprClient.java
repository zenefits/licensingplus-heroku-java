package Core.Nipr;

/**
 * Created by vthiruvengadam on 8/5/16.
 */
import nipr.*;
import nipr.wsdl.*;
import Core.Utils.*;

import java.util.GregorianCalendar;
import java.util.*;
import javax.activation.DataHandler;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import java.lang.*;
import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.xml.bind.*;
import javax.xml.transform.Source;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.SoapFaultDetailElement;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class NiprClient extends WebServiceGatewaySupport {

    public void getNiprReports(
            Map<String, GregorianCalendar> aInDates,
            Map<String, LicenseInternal> aInOutLatestLicenses,
            Map<String, GregorianCalendar> aOutSuccessDates,
            StringBuilder aInOutErrors)
    {

        Map<String, LicenseInternal> lCurrentDayInfo = new  HashMap<String, LicenseInternal>();

        for(GregorianCalendar lCal : aInDates.values()) {

            String lFormattedDate = CalenderUtils.getFormattedDate(lCal);
            System.out.println("NiprClient: Get data for " + lFormattedDate);

            AtomicBoolean lSpecificFailure = new AtomicBoolean(false);
            lCurrentDayInfo = getSpecificReport(lCal, lSpecificFailure, aInOutErrors);

            if(lSpecificFailure.get()) {
                System.out.println("Nipr Sync for date " + lFormattedDate + " failed");
                continue;
            }

            System.out.println("Nipr Sync for date " + lFormattedDate + " SUCCESS");

            // Previous Day is higher
            mergeReports(lCurrentDayInfo, aInOutLatestLicenses);
            GregorianCalendar lCalCopy = (GregorianCalendar)lCal.clone();
            aOutSuccessDates.put(CalenderUtils.getFormattedDate(lCalCopy), lCalCopy);
        }
    }


    public void mergeReports(Map<String, LicenseInternal> aInDateInfo1, Map<String, LicenseInternal> aInDateInfo2) {

        for (LicenseInternal lLicense : aInDateInfo1.values()) {
            if(!aInDateInfo2.containsKey(lLicense.GetKey())) {
                aInDateInfo2.put(lLicense.GetKey(), lLicense);
            }
            else {
                String lDate1Str = lLicense.niprUpdateDate;
                GregorianCalendar lDate1 = CalenderUtils.getCalenderTimeFromString(lDate1Str);
                String lDate2Str = aInDateInfo2.get(lLicense.GetKey()).niprUpdateDate;
                GregorianCalendar lDate2 = CalenderUtils.getCalenderTimeFromString(lDate2Str);

                if(lDate2.compareTo(lDate1) < 0) {
                    aInDateInfo2.put(lLicense.GetKey(), lLicense);
                }
            }
        }
    }

    public Map<String, LicenseInternal> getSpecificReport(GregorianCalendar aInDate, AtomicBoolean aOutFailure, StringBuilder aInOutErrors) {

        Map<String, LicenseInternal> lAllLicenses = new HashMap<String, LicenseInternal>();
        aOutFailure.set(false);
        ReceiveSpecificReport lRequest = new ReceiveSpecificReport();
        /*
        Date lDate = new Date();
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(lDate);
        */
        String formatted = CalenderUtils.getFormattedDate(aInDate);
        System.out.println("Getting Record for ====================== " + formatted);

        try {
            XMLGregorianCalendar lXmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(aInDate);
            lRequest.setReportDate(lXmlDate);

            Object lSoapResponse = getWebServiceTemplate()
                    .marshalSendAndReceive(
                            lRequest,
                            new SoapActionCallback(getDefaultUri() + "/receiveSpecificReport"));

            System.out.println("NIPR soap api SUCCESS ++++++++++++ " + formatted);

            ReceiveSpecificReportResponse lResponse =  (ReceiveSpecificReportResponse)lSoapResponse;
            AlertsReportType lType = lResponse.getAlertsReport();
            DataHandler lHandler = lType.getAlertsReport();
            //String theString = IOUtils.toString(lHandler.getInputStream(), "utf-8");
            //System.out.println("Response " + theString);
            lAllLicenses = parseReport(lType.getAlertsReport().getInputStream(), lXmlDate);
            System.out.println("Total NIPR Liceses for " + formatted + " are " + lAllLicenses.size());
        }
        catch (SoapFaultClientException e)
        {
            aOutFailure.set(true);
            try {
                PdbAlertsFaultType lFaultType = getDetailedFault(e);
                if(lFaultType != null) {
                    System.out.println("NiprSoapApi error from server " + lFaultType.getMessage());
                    if(lFaultType.getErrorCode() == 5) {
                        aOutFailure.set(false);
                        System.out.println("Treating no Nipr alerts for the day " + formatted + " as SUCCESS ++++++++++");
                    }
                }
            }
            catch (Exception ex) {
                aOutFailure.set(true);
                String lMsg = "NiprSoapApi SoapFaultClientException error in parsing the exception " + ex.getMessage();
                WebUtils.appendline(lMsg, aInOutErrors);
                System.out.println(lMsg);
            }
        }
        catch (Exception e)
        {
            aOutFailure.set(true);
            e.printStackTrace();
            String lMsg = "NiprSoapApi generic exception  " + e.getMessage();
            WebUtils.appendline(lMsg, aInOutErrors);
            System.out.println(lMsg);
        }

        return lAllLicenses;
    }

    private PdbAlertsFaultType getDetailedFault(SoapFaultClientException aInException) throws IOException {
        SoapFaultDetail soapFaultDetail = aInException.getSoapFault().getFaultDetail();
        // if there is no fault detail ...
        if (soapFaultDetail == null) {
            throw aInException;
        }
        // cannot simply use SoapFaultDetail.getSource() since it returns <detail> as root XML element
        SoapFaultDetailElement detailElementChild = (SoapFaultDetailElement) soapFaultDetail.getDetailEntries().next();
        Source detailSource = detailElementChild.getSource();
        Object lFaultResponse = getWebServiceTemplate().getUnmarshaller().unmarshal(detailSource);
        PdbAlertsFaultType lFaultType = (PdbAlertsFaultType)lFaultResponse;
        return lFaultType;
    }


    /*
    NIPR RESPONSE SCHEMA -
		LicensingReportProcessResult
			LicensingReport
				Person
					ExternalIdentifier
						TypeCode - NAICProducerCode
						Id - 8306087
				JurisdictionReport
					PersonReferences @personReference=PersonPRI722253100
					JurisdictionReportItem
						StateOrProvinceCode
						Licensee
							InsuranceLicense
								ResidentLicenseIndicator - false/true
								TypeCode - Producer
								License
									LicenseNumberId - 14927135
									LicensePeriod
										StartDate - 2014-06-30 (not always there)
										EndDate - 2014-07-31
									IssueDate - 2009-12-01
									LicenseClassDescription - Producer
									StatusCode - Active/Revoked/inactive/cancelled
								LineOfAuthority
									IssueDate - 2009-12-01
									LineOfAuthorityDescription - Life
									StatusCode - Active/Inactive

							InsuranceLicense
								ResidentLicenseIndicator - false/true
								QualifyingInsuranceLicenseIndicator - true
								TypeCode - Adjuster
    */
    private Map<String, LicenseInternal> parseReport(InputStream aInData, XMLGregorianCalendar aInCalDate) {

        Map<String, LicenseInternal> lAllLicenses = new HashMap<String, LicenseInternal>();

        Map<String, Integer> lPersonNumberByKey = new HashMap<String, Integer>();
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(LicensingReportProcessResult.class);
            Unmarshaller jaxbMarshaller = jaxbContext.createUnmarshaller();
            LicensingReportProcessResult lResult = (LicensingReportProcessResult)jaxbMarshaller.unmarshal(aInData);
            //System.out.println("Deserialization was a success");

            if(lResult != null) {
                LicensingReportProcessResult.LicensingReport lReport = lResult.getLicensingReport();
                if(lReport != null) {

                    lPersonNumberByKey = getPersonNumbersByKey(lReport);

                    List<LicensingReportProcessResult.LicensingReport.JurisdictionReport> lElements = lReport.getJurisdictionReport();
                    for (LicensingReportProcessResult.LicensingReport.JurisdictionReport lJdReport : lElements) {
                        LicensingReportProcessResult.LicensingReport.JurisdictionReport.PersonReferences lPReferences = lJdReport.getPersonReferences();
                        if(lPReferences == null) {
                            continue;
                        }
                        String lPersonRef = lPReferences.getPersonReference();
                        // Process as Many as we can
                        try {
                            processJuriductionReport(lPersonNumberByKey, lJdReport, aInCalDate, lAllLicenses);
                        }
                        catch(Exception e) {
                            System.out.println("Exception Processing JDReport Person: " + lPersonRef + " Exception: " + e.getMessage());
                        }
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("Nipr Report parsing threw an Exception " + ex.getMessage());
        }

        return lAllLicenses;
    }

    private void processJuriductionReport(Map<String, Integer> aInPersonNumberByKey, LicensingReportProcessResult.LicensingReport.JurisdictionReport lJdReport, XMLGregorianCalendar aInCalDate, Map<String, LicenseInternal> aInOutAllLicenses) {

        LicensingReportProcessResult.LicensingReport.JurisdictionReport.PersonReferences lPReferences = lJdReport.getPersonReferences();
        String lPersonRef = lPReferences.getPersonReference();

        if(!aInPersonNumberByKey.containsKey(lPersonRef)) {
            System.out.println("Person: " + lPersonRef + " Not found in map");
            return;
        }

        Integer lNpnNumber = aInPersonNumberByKey.get(lPersonRef);
        List<LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem> lJdReportItems = lJdReport.getJurisdictionReportItem();
        for(LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem lJdReportItem : lJdReportItems) {
            String lState = lJdReportItem.getStateOrProvinceCode();
            LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee lLicensee = lJdReportItem.getLicensee();

            if(lLicensee == null) {
                System.out.println("Person: " + lPersonRef + " JurisdictionReportItem.Licensee is null");
                continue;
            }

            List<LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense> lInsuranceLicenses = lLicensee.getInsuranceLicense();
            for(LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense lInsuranceLicense : lInsuranceLicenses) {
                String lTypeCode = lInsuranceLicense.getTypeCode();

                if(!Objects.equals(lTypeCode, new String("Producer"))) {
                    continue;
                }
                LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.License lLicense = lInsuranceLicense.getLicense();
                if(lLicense == null) {
                    continue;
                }

                //System.out.println("Person " + lPersonRef + " Issued License on " + lLicense.getIssueDate());
                LicenseInternal lLicenseInt = new LicenseInternal();
                lLicenseInt.licenseNumber = Integer.toString(lLicense.getLicenseNumberId());
                XMLGregorianCalendar lCal = lLicense.getIssueDate();
                lLicenseInt.effectiveDate = CalenderUtils.toSFDCDateFormat(lCal);
                LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.License.LicensePeriod lPeriod = lLicense.getLicensePeriod();
                if(lPeriod != null) {
                    List<Serializable> lContents = lPeriod.getContent();
                    for(Serializable lContent : lContents) {
                        if(lContent instanceof JAXBElement) {
                            JAXBElement lElem = (JAXBElement)lContent;
                            if(lElem.getValue() instanceof XMLGregorianCalendar) {
                                lCal = (XMLGregorianCalendar)lElem.getValue();
                                lLicenseInt.expirationDate = CalenderUtils.toSFDCDateFormat(lCal);
                            }

                        }
                    }
                }
                lLicenseInt.className = lLicense.getLicenseClassDescription();
                lLicenseInt.npnNumber = Integer.toString(lNpnNumber);
                if(Objects.equals(lInsuranceLicense.getResidentLicenseIndicator(), new String("true"))) {
                    lLicenseInt.isResidentLicense = true;
                }
                lLicenseInt.state = lJdReportItem.getStateOrProvinceCode();
                List<LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.LineOfAuthority> lLoas = lInsuranceLicense.getLineOfAuthority();
                boolean oneActiveLoa = false;
                for(LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.LineOfAuthority l : lLoas) {
                    LineOfAuthorityInternal lLoaInternal = new LineOfAuthorityInternal();
                    lLoaInternal.name = l.getLineOfAuthorityDescription();
                    if(Objects.equals(l.getStatusCode().toLowerCase(), new String("active"))) {
                        lLoaInternal.isActive = true;
                        oneActiveLoa = true;
                    }
                    lLicenseInt.linesOfAuthority.add(lLoaInternal);
                }

                if(!CalenderUtils.isNullOrWhiteSpace(lLicense.getStatusCode())) {
                    if(Objects.equals(lLicense.getStatusCode().toLowerCase(), new String("active"))) {
                        lLicenseInt.isActive = true;
                    }
                }
                else {
                    lLicenseInt.isActive = oneActiveLoa;
                }
                lLicenseInt.niprUpdateDate = CalenderUtils.toSFDCDateFormat(aInCalDate);
                if(!aInOutAllLicenses.containsKey(lLicenseInt.GetKey())) {
                    aInOutAllLicenses.put(lLicenseInt.GetKey(), lLicenseInt);
                }
            }
        }
    }

    private Map<String, Integer> getPersonNumbersByKey(LicensingReportProcessResult.LicensingReport aInReport) {
        Map<String, Integer> lPersonNumberByKey = new HashMap<String, Integer>();
        List<LicensingReportProcessResult.LicensingReport.Person> lElements = aInReport.getPerson();
        for (LicensingReportProcessResult.LicensingReport.Person lPerson : lElements) {
            LicensingReportProcessResult.LicensingReport.Person.ExternalIdentifier lId = lPerson.getExternalIdentifier();
            if(lId != null) {
                //System.out.println("Person " + lPerson.getKey() + " Id: " + lId.getId() + " TypeCode " + lId.getTypeCode());
                if(!lPersonNumberByKey.containsKey(lPerson.getKey()))
                {
                    lPersonNumberByKey.put(lPerson.getKey(), lId.getId());
                }
            }
        }
        return lPersonNumberByKey;
    }

}
