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

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class NiprClient extends WebServiceGatewaySupport {

    private GregorianCalendar LastSuccessfulSyncDate;

    private HashSet<String> SyncedDates;

    public NiprClient() {
        SyncedDates = new HashSet<String>();
    }

    public boolean GetNiprReports(
            HashMap<String, GregorianCalendar> aInDates,
            HashMap<String, LicenseInternal> aInOutLatestLicenses)
    {

        boolean lFailure = false;
        HashMap<String, LicenseInternal> lCurrentDayInfo = new  HashMap<String, LicenseInternal>();

        for(GregorianCalendar lCal : aInDates.values()) {

            String lFormattedDate = CalenderUtils.GetFormattedDate(lCal);
            System.out.println("NiprClient: Get data for " + lFormattedDate);

            AtomicBoolean lSpecificFailure = new AtomicBoolean(false);
            lCurrentDayInfo = GetSpecificReport(lCal, lSpecificFailure);

            if(lSpecificFailure.get()) {
                System.out.println("Nipr Sync for date " + lFormattedDate + " failed");
                lFailure = true;
                continue;
            }

            System.out.println("Nipr Sync for date " + lFormattedDate + " SUCCESS");

            // Previous Day is higher
            MergeReports(lCurrentDayInfo, aInOutLatestLicenses);
        }

        return lFailure;
    }


    public void MergeReports(HashMap<String, LicenseInternal> aInDateInfo1, HashMap<String, LicenseInternal> aInDateInfo2) {

        for (LicenseInternal lLicense : aInDateInfo1.values()) {
            if(!aInDateInfo2.containsKey(lLicense.GetKey())) {
                aInDateInfo2.put(lLicense.GetKey(), lLicense);
            }
            else {
                String lDate1Str = lLicense.niprUpdateDate;
                GregorianCalendar lDate1 = CalenderUtils.GetCalenderTimeFromString(lDate1Str);
                String lDate2Str = aInDateInfo2.get(lLicense.GetKey()).niprUpdateDate;
                GregorianCalendar lDate2 = CalenderUtils.GetCalenderTimeFromString(lDate2Str);

                if(lDate2.compareTo(lDate1) < 0) {
                    aInDateInfo2.put(lLicense.GetKey(), lLicense);
                }
            }
        }
    }

    public HashMap<String, LicenseInternal> GetSpecificReport(GregorianCalendar aInDate, AtomicBoolean aOutFailure) {

        HashMap<String, LicenseInternal> lAllLicenses = new HashMap<String, LicenseInternal>();
        aOutFailure.set(false);
        ReceiveSpecificReport lRequest = new ReceiveSpecificReport();
        /*
        Date lDate = new Date();
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(lDate);
        */
        String formatted = CalenderUtils.GetFormattedDate(aInDate);
        System.out.println("Getting Record for ====================== " + formatted);

        try {
            XMLGregorianCalendar lXmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(aInDate);
            lRequest.setReportDate(lXmlDate);

            Object lSoapResponse = getWebServiceTemplate()
                    .marshalSendAndReceive(
                            lRequest,
                            new SoapActionCallback("https://pdb-services.nipr.com/pdb-alerts-industry-services/industry-ws/receiveSpecificReport"));

            System.out.println("NIPR soap api SUCCESS ++++++++++++ " + formatted);

            ReceiveSpecificReportResponse lResponse =  (ReceiveSpecificReportResponse)lSoapResponse;
            AlertsReportType lType = lResponse.getAlertsReport();
            DataHandler lHandler = lType.getAlertsReport();
            //String theString = IOUtils.toString(lHandler.getInputStream(), "utf-8");
            //System.out.println("Response " + theString);
            lAllLicenses = ParseReport(lType.getAlertsReport().getInputStream(), lXmlDate);
            System.out.println("Total NIPR Liceses for " + formatted + " are " + lAllLicenses.size());
        }
        catch(Exception e)
        {
            aOutFailure.set(true);
            System.out.println("NIPR soap api threw an exception " + e.getMessage());
        }

        return lAllLicenses;
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
    private HashMap<String, LicenseInternal> ParseReport(InputStream aInData, XMLGregorianCalendar aInCalDate) {

        HashMap<String, LicenseInternal> lAllLicenses = new HashMap<String, LicenseInternal>();

        HashMap<String, Integer> lPersonNumberByKey = new HashMap<String, Integer>();
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(LicensingReportProcessResult.class);
            Unmarshaller jaxbMarshaller = jaxbContext.createUnmarshaller();
            LicensingReportProcessResult lResult = (LicensingReportProcessResult)jaxbMarshaller.unmarshal(aInData);
            //System.out.println("Deserialization was a success");

            if(lResult != null) {
                LicensingReportProcessResult.LicensingReport lReport = lResult.getLicensingReport();
                if(lReport != null) {

                    lPersonNumberByKey = GetPersonNumbersByKey(lReport);

                    List<LicensingReportProcessResult.LicensingReport.JurisdictionReport> lElements = lReport.getJurisdictionReport();
                    for (LicensingReportProcessResult.LicensingReport.JurisdictionReport lJdReport : lElements) {
                        LicensingReportProcessResult.LicensingReport.JurisdictionReport.PersonReferences lPReferences = lJdReport.getPersonReferences();
                        if(lPReferences == null) {
                            continue;
                        }
                        String lPersonRef = lPReferences.getPersonReference();
                        // Process as Many as we can
                        try {
                            ProcessJuriductionReport(lPersonNumberByKey, lJdReport, aInCalDate, lAllLicenses);
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

    private void ProcessJuriductionReport(HashMap<String, Integer> aInPersonNumberByKey, LicensingReportProcessResult.LicensingReport.JurisdictionReport lJdReport, XMLGregorianCalendar aInCalDate, HashMap<String, LicenseInternal> aInOutAllLicenses) {

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
                lLicenseInt.effectiveDate = CalenderUtils.ToSFDCDateFormat(lCal);
                LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.License.LicensePeriod lPeriod = lLicense.getLicensePeriod();
                if(lPeriod != null) {
                    List<Serializable> lContents = lPeriod.getContent();
                    for(Serializable lContent : lContents) {
                        if(lContent instanceof JAXBElement) {
                            JAXBElement lElem = (JAXBElement)lContent;
                            if(lElem.getValue() instanceof XMLGregorianCalendar) {
                                lCal = (XMLGregorianCalendar)lElem.getValue();
                                lLicenseInt.expirationDate = CalenderUtils.ToSFDCDateFormat(lCal);
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
                lLicenseInt.niprUpdateDate = CalenderUtils.ToSFDCDateFormat(aInCalDate);
                if(!aInOutAllLicenses.containsKey(lLicenseInt.GetKey())) {
                    aInOutAllLicenses.put(lLicenseInt.GetKey(), lLicenseInt);
                }
            }
        }
    }

    private HashMap<String, Integer> GetPersonNumbersByKey(LicensingReportProcessResult.LicensingReport aInReport) {
        HashMap<String, Integer> lPersonNumberByKey = new HashMap<String, Integer>();
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
