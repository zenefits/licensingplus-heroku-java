
package nipr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MessageHeader">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="MessageId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="CorrelationId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="MessageDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                   &lt;element name="MessageDocumentCount" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="MessageResult">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="MessageDocumentResult">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="MessageDocumentReferences">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="DocumentId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="SuccessCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="LicensingReport">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Address" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="LineOne" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="CityName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="StateOrProvinceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="PostalCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="CountryCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Person" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ExternalIdentifier">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="TypeCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="JurisdictionReport" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="PersonReferences">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="personReference" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="JurisdictionReportItem" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="StateOrProvinceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="Licensee">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="InsuranceLicense" maxOccurs="unbounded" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="ResidentLicenseIndicator" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                           &lt;element name="TypeCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                           &lt;element name="License">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;sequence>
 *                                                                     &lt;element name="LicenseNumberId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                                                                     &lt;element name="LicensePeriod">
 *                                                                       &lt;complexType>
 *                                                                         &lt;complexContent>
 *                                                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                             &lt;sequence>
 *                                                                               &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                                                                             &lt;/sequence>
 *                                                                           &lt;/restriction>
 *                                                                         &lt;/complexContent>
 *                                                                       &lt;/complexType>
 *                                                                     &lt;/element>
 *                                                                     &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                     &lt;element name="IssueDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                                                                     &lt;element name="LicenseClassCode" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                                                                     &lt;element name="LicenseClassDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                     &lt;element name="StateOrProvinceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                     &lt;element name="StateOrProvinceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                   &lt;/sequence>
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                           &lt;element name="LineOfAuthority" maxOccurs="unbounded" minOccurs="0">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;sequence>
 *                                                                     &lt;element name="IssueDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                                                                     &lt;element name="StateOrProvinceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                     &lt;element name="StateOrProvinceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                     &lt;element name="LineOfAuthorityCode" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *                                                                     &lt;element name="LineOfAuthorityDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                     &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                     &lt;element name="StatusDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                     &lt;element name="StatusReasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                                     &lt;element name="StatusPeriod">
 *                                                                       &lt;complexType>
 *                                                                         &lt;complexContent>
 *                                                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                             &lt;sequence>
 *                                                                               &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                                                                             &lt;/sequence>
 *                                                                           &lt;/restriction>
 *                                                                         &lt;/complexContent>
 *                                                                       &lt;/complexType>
 *                                                                     &lt;/element>
 *                                                                   &lt;/sequence>
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "messageHeader",
    "messageResult",
    "licensingReport"
})
@XmlRootElement(name = "LicensingReportProcessResult", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1")
public class LicensingReportProcessResult {

    @XmlElement(name = "MessageHeader", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
    protected LicensingReportProcessResult.MessageHeader messageHeader;
    @XmlElement(name = "MessageResult", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
    protected LicensingReportProcessResult.MessageResult messageResult;
    @XmlElement(name = "LicensingReport", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
    protected LicensingReportProcessResult.LicensingReport licensingReport;

    /**
     * Gets the value of the messageHeader property.
     * 
     * @return
     *     possible object is
     *     {@link LicensingReportProcessResult.MessageHeader }
     *     
     */
    public LicensingReportProcessResult.MessageHeader getMessageHeader() {
        return messageHeader;
    }

    /**
     * Sets the value of the messageHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link LicensingReportProcessResult.MessageHeader }
     *     
     */
    public void setMessageHeader(LicensingReportProcessResult.MessageHeader value) {
        this.messageHeader = value;
    }

    /**
     * Gets the value of the messageResult property.
     * 
     * @return
     *     possible object is
     *     {@link LicensingReportProcessResult.MessageResult }
     *     
     */
    public LicensingReportProcessResult.MessageResult getMessageResult() {
        return messageResult;
    }

    /**
     * Sets the value of the messageResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link LicensingReportProcessResult.MessageResult }
     *     
     */
    public void setMessageResult(LicensingReportProcessResult.MessageResult value) {
        this.messageResult = value;
    }

    /**
     * Gets the value of the licensingReport property.
     * 
     * @return
     *     possible object is
     *     {@link LicensingReportProcessResult.LicensingReport }
     *     
     */
    public LicensingReportProcessResult.LicensingReport getLicensingReport() {
        return licensingReport;
    }

    /**
     * Sets the value of the licensingReport property.
     * 
     * @param value
     *     allowed object is
     *     {@link LicensingReportProcessResult.LicensingReport }
     *     
     */
    public void setLicensingReport(LicensingReportProcessResult.LicensingReport value) {
        this.licensingReport = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Address" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="LineOne" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="CityName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="StateOrProvinceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="PostalCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="CountryCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Person" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ExternalIdentifier">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="TypeCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="JurisdictionReport" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="PersonReferences">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="personReference" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="JurisdictionReportItem" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="StateOrProvinceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="Licensee">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="InsuranceLicense" maxOccurs="unbounded" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="ResidentLicenseIndicator" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                 &lt;element name="TypeCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                 &lt;element name="License">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;sequence>
     *                                                           &lt;element name="LicenseNumberId" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                                                           &lt;element name="LicensePeriod">
     *                                                             &lt;complexType>
     *                                                               &lt;complexContent>
     *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                                   &lt;sequence>
     *                                                                     &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *                                                                   &lt;/sequence>
     *                                                                 &lt;/restriction>
     *                                                               &lt;/complexContent>
     *                                                             &lt;/complexType>
     *                                                           &lt;/element>
     *                                                           &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                           &lt;element name="IssueDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                                                           &lt;element name="LicenseClassCode" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *                                                           &lt;element name="LicenseClassDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                           &lt;element name="StateOrProvinceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                           &lt;element name="StateOrProvinceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                         &lt;/sequence>
     *                                                       &lt;/restriction>
     *                                                     &lt;/complexContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                                 &lt;element name="LineOfAuthority" maxOccurs="unbounded" minOccurs="0">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;sequence>
     *                                                           &lt;element name="IssueDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                                                           &lt;element name="StateOrProvinceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                           &lt;element name="StateOrProvinceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                           &lt;element name="LineOfAuthorityCode" type="{http://www.w3.org/2001/XMLSchema}short"/>
     *                                                           &lt;element name="LineOfAuthorityDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                           &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                           &lt;element name="StatusDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                           &lt;element name="StatusReasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                           &lt;element name="StatusPeriod">
     *                                                             &lt;complexType>
     *                                                               &lt;complexContent>
     *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                                   &lt;sequence>
     *                                                                     &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                                                                   &lt;/sequence>
     *                                                                 &lt;/restriction>
     *                                                               &lt;/complexContent>
     *                                                             &lt;/complexType>
     *                                                           &lt;/element>
     *                                                         &lt;/sequence>
     *                                                       &lt;/restriction>
     *                                                     &lt;/complexContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "address",
        "person",
        "jurisdictionReport"
    })
    public static class LicensingReport {

        @XmlElement(name = "Address", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1")
        protected List<LicensingReportProcessResult.LicensingReport.Address> address;
        @XmlElement(name = "Person", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1")
        protected List<LicensingReportProcessResult.LicensingReport.Person> person;
        @XmlElement(name = "JurisdictionReport", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1")
        protected List<LicensingReportProcessResult.LicensingReport.JurisdictionReport> jurisdictionReport;

        /**
         * Gets the value of the address property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the address property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAddress().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link LicensingReportProcessResult.LicensingReport.Address }
         * 
         * 
         */
        public List<LicensingReportProcessResult.LicensingReport.Address> getAddress() {
            if (address == null) {
                address = new ArrayList<LicensingReportProcessResult.LicensingReport.Address>();
            }
            return this.address;
        }

        /**
         * Gets the value of the person property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the person property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPerson().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link LicensingReportProcessResult.LicensingReport.Person }
         * 
         * 
         */
        public List<LicensingReportProcessResult.LicensingReport.Person> getPerson() {
            if (person == null) {
                person = new ArrayList<LicensingReportProcessResult.LicensingReport.Person>();
            }
            return this.person;
        }

        /**
         * Gets the value of the jurisdictionReport property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the jurisdictionReport property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getJurisdictionReport().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link LicensingReportProcessResult.LicensingReport.JurisdictionReport }
         * 
         * 
         */
        public List<LicensingReportProcessResult.LicensingReport.JurisdictionReport> getJurisdictionReport() {
            if (jurisdictionReport == null) {
                jurisdictionReport = new ArrayList<LicensingReportProcessResult.LicensingReport.JurisdictionReport>();
            }
            return this.jurisdictionReport;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="LineOne" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="CityName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="StateOrProvinceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="PostalCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="CountryCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *       &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "lineOne",
            "cityName",
            "stateOrProvinceCode",
            "postalCode",
            "countryCode"
        })
        public static class Address {

            @XmlElement(name = "LineOne", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
            protected String lineOne;
            @XmlElement(name = "CityName", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
            protected String cityName;
            @XmlElement(name = "StateOrProvinceCode", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
            protected String stateOrProvinceCode;
            @XmlElement(name = "PostalCode", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
            protected String postalCode;
            @XmlElement(name = "CountryCode", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
            protected String countryCode;
            @XmlAttribute(name = "key")
            protected String key;

            /**
             * Gets the value of the lineOne property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLineOne() {
                return lineOne;
            }

            /**
             * Sets the value of the lineOne property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLineOne(String value) {
                this.lineOne = value;
            }

            /**
             * Gets the value of the cityName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCityName() {
                return cityName;
            }

            /**
             * Sets the value of the cityName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCityName(String value) {
                this.cityName = value;
            }

            /**
             * Gets the value of the stateOrProvinceCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStateOrProvinceCode() {
                return stateOrProvinceCode;
            }

            /**
             * Sets the value of the stateOrProvinceCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStateOrProvinceCode(String value) {
                this.stateOrProvinceCode = value;
            }

            /**
             * Gets the value of the postalCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPostalCode() {
                return postalCode;
            }

            /**
             * Sets the value of the postalCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPostalCode(String value) {
                this.postalCode = value;
            }

            /**
             * Gets the value of the countryCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCountryCode() {
                return countryCode;
            }

            /**
             * Sets the value of the countryCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCountryCode(String value) {
                this.countryCode = value;
            }

            /**
             * Gets the value of the key property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKey() {
                return key;
            }

            /**
             * Sets the value of the key property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKey(String value) {
                this.key = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="PersonReferences">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="personReference" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="JurisdictionReportItem" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="StateOrProvinceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="Licensee">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="InsuranceLicense" maxOccurs="unbounded" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="ResidentLicenseIndicator" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                       &lt;element name="TypeCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                       &lt;element name="License">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;sequence>
         *                                                 &lt;element name="LicenseNumberId" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *                                                 &lt;element name="LicensePeriod">
         *                                                   &lt;complexType>
         *                                                     &lt;complexContent>
         *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                                         &lt;sequence>
         *                                                           &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
         *                                                         &lt;/sequence>
         *                                                       &lt;/restriction>
         *                                                     &lt;/complexContent>
         *                                                   &lt;/complexType>
         *                                                 &lt;/element>
         *                                                 &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                 &lt;element name="IssueDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *                                                 &lt;element name="LicenseClassCode" type="{http://www.w3.org/2001/XMLSchema}byte"/>
         *                                                 &lt;element name="LicenseClassDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                 &lt;element name="StateOrProvinceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                 &lt;element name="StateOrProvinceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                               &lt;/sequence>
         *                                             &lt;/restriction>
         *                                           &lt;/complexContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                       &lt;element name="LineOfAuthority" maxOccurs="unbounded" minOccurs="0">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;sequence>
         *                                                 &lt;element name="IssueDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *                                                 &lt;element name="StateOrProvinceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                 &lt;element name="StateOrProvinceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                 &lt;element name="LineOfAuthorityCode" type="{http://www.w3.org/2001/XMLSchema}short"/>
         *                                                 &lt;element name="LineOfAuthorityDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                 &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                 &lt;element name="StatusDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                 &lt;element name="StatusReasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                                 &lt;element name="StatusPeriod">
         *                                                   &lt;complexType>
         *                                                     &lt;complexContent>
         *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                                         &lt;sequence>
         *                                                           &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *                                                         &lt;/sequence>
         *                                                       &lt;/restriction>
         *                                                     &lt;/complexContent>
         *                                                   &lt;/complexType>
         *                                                 &lt;/element>
         *                                               &lt;/sequence>
         *                                             &lt;/restriction>
         *                                           &lt;/complexContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "personReferences",
            "jurisdictionReportItem"
        })
        public static class JurisdictionReport {

            @XmlElement(name = "PersonReferences", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
            protected LicensingReportProcessResult.LicensingReport.JurisdictionReport.PersonReferences personReferences;
            @XmlElement(name = "JurisdictionReportItem", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1")
            protected List<LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem> jurisdictionReportItem;

            /**
             * Gets the value of the personReferences property.
             * 
             * @return
             *     possible object is
             *     {@link LicensingReportProcessResult.LicensingReport.JurisdictionReport.PersonReferences }
             *     
             */
            public LicensingReportProcessResult.LicensingReport.JurisdictionReport.PersonReferences getPersonReferences() {
                return personReferences;
            }

            /**
             * Sets the value of the personReferences property.
             * 
             * @param value
             *     allowed object is
             *     {@link LicensingReportProcessResult.LicensingReport.JurisdictionReport.PersonReferences }
             *     
             */
            public void setPersonReferences(LicensingReportProcessResult.LicensingReport.JurisdictionReport.PersonReferences value) {
                this.personReferences = value;
            }

            /**
             * Gets the value of the jurisdictionReportItem property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the jurisdictionReportItem property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getJurisdictionReportItem().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem }
             * 
             * 
             */
            public List<LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem> getJurisdictionReportItem() {
                if (jurisdictionReportItem == null) {
                    jurisdictionReportItem = new ArrayList<LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem>();
                }
                return this.jurisdictionReportItem;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="StateOrProvinceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="Licensee">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="InsuranceLicense" maxOccurs="unbounded" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="ResidentLicenseIndicator" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                             &lt;element name="TypeCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                             &lt;element name="License">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;sequence>
             *                                       &lt;element name="LicenseNumberId" type="{http://www.w3.org/2001/XMLSchema}int"/>
             *                                       &lt;element name="LicensePeriod">
             *                                         &lt;complexType>
             *                                           &lt;complexContent>
             *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                               &lt;sequence>
             *                                                 &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
             *                                               &lt;/sequence>
             *                                             &lt;/restriction>
             *                                           &lt;/complexContent>
             *                                         &lt;/complexType>
             *                                       &lt;/element>
             *                                       &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                       &lt;element name="IssueDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
             *                                       &lt;element name="LicenseClassCode" type="{http://www.w3.org/2001/XMLSchema}byte"/>
             *                                       &lt;element name="LicenseClassDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                       &lt;element name="StateOrProvinceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                       &lt;element name="StateOrProvinceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                     &lt;/sequence>
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                             &lt;element name="LineOfAuthority" maxOccurs="unbounded" minOccurs="0">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;sequence>
             *                                       &lt;element name="IssueDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
             *                                       &lt;element name="StateOrProvinceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                       &lt;element name="StateOrProvinceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                       &lt;element name="LineOfAuthorityCode" type="{http://www.w3.org/2001/XMLSchema}short"/>
             *                                       &lt;element name="LineOfAuthorityDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                       &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                       &lt;element name="StatusDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                       &lt;element name="StatusReasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                                       &lt;element name="StatusPeriod">
             *                                         &lt;complexType>
             *                                           &lt;complexContent>
             *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                               &lt;sequence>
             *                                                 &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
             *                                               &lt;/sequence>
             *                                             &lt;/restriction>
             *                                           &lt;/complexContent>
             *                                         &lt;/complexType>
             *                                       &lt;/element>
             *                                     &lt;/sequence>
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "stateOrProvinceCode",
                "licensee"
            })
            public static class JurisdictionReportItem {

                @XmlElement(name = "StateOrProvinceCode", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
                protected String stateOrProvinceCode;
                @XmlElement(name = "Licensee", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
                protected LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee licensee;

                /**
                 * Gets the value of the stateOrProvinceCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getStateOrProvinceCode() {
                    return stateOrProvinceCode;
                }

                /**
                 * Sets the value of the stateOrProvinceCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setStateOrProvinceCode(String value) {
                    this.stateOrProvinceCode = value;
                }

                /**
                 * Gets the value of the licensee property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee }
                 *     
                 */
                public LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee getLicensee() {
                    return licensee;
                }

                /**
                 * Sets the value of the licensee property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee }
                 *     
                 */
                public void setLicensee(LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee value) {
                    this.licensee = value;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="InsuranceLicense" maxOccurs="unbounded" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="ResidentLicenseIndicator" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                   &lt;element name="TypeCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                   &lt;element name="License">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;sequence>
                 *                             &lt;element name="LicenseNumberId" type="{http://www.w3.org/2001/XMLSchema}int"/>
                 *                             &lt;element name="LicensePeriod">
                 *                               &lt;complexType>
                 *                                 &lt;complexContent>
                 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                                     &lt;sequence>
                 *                                       &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
                 *                                     &lt;/sequence>
                 *                                   &lt;/restriction>
                 *                                 &lt;/complexContent>
                 *                               &lt;/complexType>
                 *                             &lt;/element>
                 *                             &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                             &lt;element name="IssueDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
                 *                             &lt;element name="LicenseClassCode" type="{http://www.w3.org/2001/XMLSchema}byte"/>
                 *                             &lt;element name="LicenseClassDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                             &lt;element name="StateOrProvinceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                             &lt;element name="StateOrProvinceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                           &lt;/sequence>
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                   &lt;element name="LineOfAuthority" maxOccurs="unbounded" minOccurs="0">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;sequence>
                 *                             &lt;element name="IssueDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
                 *                             &lt;element name="StateOrProvinceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                             &lt;element name="StateOrProvinceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                             &lt;element name="LineOfAuthorityCode" type="{http://www.w3.org/2001/XMLSchema}short"/>
                 *                             &lt;element name="LineOfAuthorityDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                             &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                             &lt;element name="StatusDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                             &lt;element name="StatusReasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                             &lt;element name="StatusPeriod">
                 *                               &lt;complexType>
                 *                                 &lt;complexContent>
                 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                                     &lt;sequence>
                 *                                       &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
                 *                                     &lt;/sequence>
                 *                                   &lt;/restriction>
                 *                                 &lt;/complexContent>
                 *                               &lt;/complexType>
                 *                             &lt;/element>
                 *                           &lt;/sequence>
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "insuranceLicense"
                })
                public static class Licensee {

                    @XmlElement(name = "InsuranceLicense", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1")
                    protected List<LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense> insuranceLicense;

                    /**
                     * Gets the value of the insuranceLicense property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the insuranceLicense property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getInsuranceLicense().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense }
                     * 
                     * 
                     */
                    public List<LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense> getInsuranceLicense() {
                        if (insuranceLicense == null) {
                            insuranceLicense = new ArrayList<LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense>();
                        }
                        return this.insuranceLicense;
                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;sequence>
                     *         &lt;element name="ResidentLicenseIndicator" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *         &lt;element name="TypeCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *         &lt;element name="License">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;sequence>
                     *                   &lt;element name="LicenseNumberId" type="{http://www.w3.org/2001/XMLSchema}int"/>
                     *                   &lt;element name="LicensePeriod">
                     *                     &lt;complexType>
                     *                       &lt;complexContent>
                     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                           &lt;sequence>
                     *                             &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
                     *                           &lt;/sequence>
                     *                         &lt;/restriction>
                     *                       &lt;/complexContent>
                     *                     &lt;/complexType>
                     *                   &lt;/element>
                     *                   &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="IssueDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
                     *                   &lt;element name="LicenseClassCode" type="{http://www.w3.org/2001/XMLSchema}byte"/>
                     *                   &lt;element name="LicenseClassDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="StateOrProvinceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="StateOrProvinceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                 &lt;/sequence>
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *         &lt;element name="LineOfAuthority" maxOccurs="unbounded" minOccurs="0">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;sequence>
                     *                   &lt;element name="IssueDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
                     *                   &lt;element name="StateOrProvinceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="StateOrProvinceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="LineOfAuthorityCode" type="{http://www.w3.org/2001/XMLSchema}short"/>
                     *                   &lt;element name="LineOfAuthorityDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="StatusDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="StatusReasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *                   &lt;element name="StatusPeriod">
                     *                     &lt;complexType>
                     *                       &lt;complexContent>
                     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                           &lt;sequence>
                     *                             &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
                     *                           &lt;/sequence>
                     *                         &lt;/restriction>
                     *                       &lt;/complexContent>
                     *                     &lt;/complexType>
                     *                   &lt;/element>
                     *                 &lt;/sequence>
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *       &lt;/sequence>
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "residentLicenseIndicator",
                        "typeCode",
                        "license",
                        "lineOfAuthority"
                    })
                    public static class InsuranceLicense {

                        @XmlElement(name = "ResidentLicenseIndicator", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
                        protected String residentLicenseIndicator;
                        @XmlElement(name = "TypeCode", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
                        protected String typeCode;
                        @XmlElement(name = "License", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
                        protected LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.License license;
                        @XmlElement(name = "LineOfAuthority", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1")
                        protected List<LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.LineOfAuthority> lineOfAuthority;

                        /**
                         * Gets the value of the residentLicenseIndicator property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getResidentLicenseIndicator() {
                            return residentLicenseIndicator;
                        }

                        /**
                         * Sets the value of the residentLicenseIndicator property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setResidentLicenseIndicator(String value) {
                            this.residentLicenseIndicator = value;
                        }

                        /**
                         * Gets the value of the typeCode property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getTypeCode() {
                            return typeCode;
                        }

                        /**
                         * Sets the value of the typeCode property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setTypeCode(String value) {
                            this.typeCode = value;
                        }

                        /**
                         * Gets the value of the license property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.License }
                         *     
                         */
                        public LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.License getLicense() {
                            return license;
                        }

                        /**
                         * Sets the value of the license property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.License }
                         *     
                         */
                        public void setLicense(LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.License value) {
                            this.license = value;
                        }

                        /**
                         * Gets the value of the lineOfAuthority property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the lineOfAuthority property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getLineOfAuthority().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.LineOfAuthority }
                         * 
                         * 
                         */
                        public List<LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.LineOfAuthority> getLineOfAuthority() {
                            if (lineOfAuthority == null) {
                                lineOfAuthority = new ArrayList<LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.LineOfAuthority>();
                            }
                            return this.lineOfAuthority;
                        }


                        /**
                         * <p>Java class for anonymous complex type.
                         * 
                         * <p>The following schema fragment specifies the expected content contained within this class.
                         * 
                         * <pre>
                         * &lt;complexType>
                         *   &lt;complexContent>
                         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *       &lt;sequence>
                         *         &lt;element name="LicenseNumberId" type="{http://www.w3.org/2001/XMLSchema}int"/>
                         *         &lt;element name="LicensePeriod">
                         *           &lt;complexType>
                         *             &lt;complexContent>
                         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *                 &lt;sequence>
                         *                   &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
                         *                 &lt;/sequence>
                         *               &lt;/restriction>
                         *             &lt;/complexContent>
                         *           &lt;/complexType>
                         *         &lt;/element>
                         *         &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="IssueDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
                         *         &lt;element name="LicenseClassCode" type="{http://www.w3.org/2001/XMLSchema}byte"/>
                         *         &lt;element name="LicenseClassDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="StateOrProvinceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="StateOrProvinceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *       &lt;/sequence>
                         *     &lt;/restriction>
                         *   &lt;/complexContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                            "licenseNumberId",
                            "licensePeriod",
                            "statusCode",
                            "issueDate",
                            "licenseClassCode",
                            "licenseClassDescription",
                            "stateOrProvinceCode",
                            "stateOrProvinceName"
                        })
                        public static class License {

                            @XmlElement(name = "LicenseNumberId", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1")
                            protected int licenseNumberId;
                            @XmlElement(name = "LicensePeriod", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
                            protected LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.License.LicensePeriod licensePeriod;
                            @XmlElement(name = "StatusCode", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
                            protected String statusCode;
                            @XmlElement(name = "IssueDate", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
                            @XmlSchemaType(name = "date")
                            protected XMLGregorianCalendar issueDate;
                            @XmlElement(name = "LicenseClassCode", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1")
                            protected byte licenseClassCode;
                            @XmlElement(name = "LicenseClassDescription", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
                            protected String licenseClassDescription;
                            @XmlElement(name = "StateOrProvinceCode", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
                            protected String stateOrProvinceCode;
                            @XmlElement(name = "StateOrProvinceName", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
                            protected String stateOrProvinceName;

                            /**
                             * Gets the value of the licenseNumberId property.
                             * 
                             */
                            public int getLicenseNumberId() {
                                return licenseNumberId;
                            }

                            /**
                             * Sets the value of the licenseNumberId property.
                             * 
                             */
                            public void setLicenseNumberId(int value) {
                                this.licenseNumberId = value;
                            }

                            /**
                             * Gets the value of the licensePeriod property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.License.LicensePeriod }
                             *     
                             */
                            public LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.License.LicensePeriod getLicensePeriod() {
                                return licensePeriod;
                            }

                            /**
                             * Sets the value of the licensePeriod property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.License.LicensePeriod }
                             *     
                             */
                            public void setLicensePeriod(LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.License.LicensePeriod value) {
                                this.licensePeriod = value;
                            }

                            /**
                             * Gets the value of the statusCode property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getStatusCode() {
                                return statusCode;
                            }

                            /**
                             * Sets the value of the statusCode property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setStatusCode(String value) {
                                this.statusCode = value;
                            }

                            /**
                             * Gets the value of the issueDate property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link XMLGregorianCalendar }
                             *     
                             */
                            public XMLGregorianCalendar getIssueDate() {
                                return issueDate;
                            }

                            /**
                             * Sets the value of the issueDate property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link XMLGregorianCalendar }
                             *     
                             */
                            public void setIssueDate(XMLGregorianCalendar value) {
                                this.issueDate = value;
                            }

                            /**
                             * Gets the value of the licenseClassCode property.
                             * 
                             */
                            public byte getLicenseClassCode() {
                                return licenseClassCode;
                            }

                            /**
                             * Sets the value of the licenseClassCode property.
                             * 
                             */
                            public void setLicenseClassCode(byte value) {
                                this.licenseClassCode = value;
                            }

                            /**
                             * Gets the value of the licenseClassDescription property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getLicenseClassDescription() {
                                return licenseClassDescription;
                            }

                            /**
                             * Sets the value of the licenseClassDescription property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setLicenseClassDescription(String value) {
                                this.licenseClassDescription = value;
                            }

                            /**
                             * Gets the value of the stateOrProvinceCode property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getStateOrProvinceCode() {
                                return stateOrProvinceCode;
                            }

                            /**
                             * Sets the value of the stateOrProvinceCode property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setStateOrProvinceCode(String value) {
                                this.stateOrProvinceCode = value;
                            }

                            /**
                             * Gets the value of the stateOrProvinceName property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getStateOrProvinceName() {
                                return stateOrProvinceName;
                            }

                            /**
                             * Sets the value of the stateOrProvinceName property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setStateOrProvinceName(String value) {
                                this.stateOrProvinceName = value;
                            }


                            /**
                             * <p>Java class for anonymous complex type.
                             * 
                             * <p>The following schema fragment specifies the expected content contained within this class.
                             * 
                             * <pre>
                             * &lt;complexType>
                             *   &lt;complexContent>
                             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                             *       &lt;sequence>
                             *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
                             *       &lt;/sequence>
                             *     &lt;/restriction>
                             *   &lt;/complexContent>
                             * &lt;/complexType>
                             * </pre>
                             * 
                             * 
                             */
                            @XmlAccessorType(XmlAccessType.FIELD)
                            @XmlType(name = "", propOrder = {
                                "content"
                            })
                            public static class LicensePeriod {

                                @XmlElementRef(name = "EndDate", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", type = JAXBElement.class, required = false)
                                @XmlMixed
                                protected List<Serializable> content;

                                /**
                                 * Gets the value of the content property.
                                 * 
                                 * <p>
                                 * This accessor method returns a reference to the live list,
                                 * not a snapshot. Therefore any modification you make to the
                                 * returned list will be present inside the JAXB object.
                                 * This is why there is not a <CODE>set</CODE> method for the content property.
                                 * 
                                 * <p>
                                 * For example, to add a new item, do as follows:
                                 * <pre>
                                 *    getContent().add(newItem);
                                 * </pre>
                                 * 
                                 * 
                                 * <p>
                                 * Objects of the following type(s) are allowed in the list
                                 * {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
                                 * {@link String }
                                 * 
                                 * 
                                 */
                                public List<Serializable> getContent() {
                                    if (content == null) {
                                        content = new ArrayList<Serializable>();
                                    }
                                    return this.content;
                                }

                            }

                        }


                        /**
                         * <p>Java class for anonymous complex type.
                         * 
                         * <p>The following schema fragment specifies the expected content contained within this class.
                         * 
                         * <pre>
                         * &lt;complexType>
                         *   &lt;complexContent>
                         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *       &lt;sequence>
                         *         &lt;element name="IssueDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
                         *         &lt;element name="StateOrProvinceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="StateOrProvinceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="LineOfAuthorityCode" type="{http://www.w3.org/2001/XMLSchema}short"/>
                         *         &lt;element name="LineOfAuthorityDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="StatusDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="StatusReasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                         *         &lt;element name="StatusPeriod">
                         *           &lt;complexType>
                         *             &lt;complexContent>
                         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *                 &lt;sequence>
                         *                   &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
                         *                 &lt;/sequence>
                         *               &lt;/restriction>
                         *             &lt;/complexContent>
                         *           &lt;/complexType>
                         *         &lt;/element>
                         *       &lt;/sequence>
                         *     &lt;/restriction>
                         *   &lt;/complexContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                            "issueDate",
                            "stateOrProvinceCode",
                            "stateOrProvinceName",
                            "lineOfAuthorityCode",
                            "lineOfAuthorityDescription",
                            "statusCode",
                            "statusDescription",
                            "statusReasonCode",
                            "statusPeriod"
                        })
                        public static class LineOfAuthority {

                            @XmlElement(name = "IssueDate", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
                            @XmlSchemaType(name = "date")
                            protected XMLGregorianCalendar issueDate;
                            @XmlElement(name = "StateOrProvinceCode", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
                            protected String stateOrProvinceCode;
                            @XmlElement(name = "StateOrProvinceName", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
                            protected String stateOrProvinceName;
                            @XmlElement(name = "LineOfAuthorityCode", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1")
                            protected short lineOfAuthorityCode;
                            @XmlElement(name = "LineOfAuthorityDescription", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
                            protected String lineOfAuthorityDescription;
                            @XmlElement(name = "StatusCode", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
                            protected String statusCode;
                            @XmlElement(name = "StatusDescription", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
                            protected String statusDescription;
                            @XmlElement(name = "StatusReasonCode", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1")
                            protected String statusReasonCode;
                            @XmlElement(name = "StatusPeriod", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
                            protected LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.LineOfAuthority.StatusPeriod statusPeriod;

                            /**
                             * Gets the value of the issueDate property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link XMLGregorianCalendar }
                             *     
                             */
                            public XMLGregorianCalendar getIssueDate() {
                                return issueDate;
                            }

                            /**
                             * Sets the value of the issueDate property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link XMLGregorianCalendar }
                             *     
                             */
                            public void setIssueDate(XMLGregorianCalendar value) {
                                this.issueDate = value;
                            }

                            /**
                             * Gets the value of the stateOrProvinceCode property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getStateOrProvinceCode() {
                                return stateOrProvinceCode;
                            }

                            /**
                             * Sets the value of the stateOrProvinceCode property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setStateOrProvinceCode(String value) {
                                this.stateOrProvinceCode = value;
                            }

                            /**
                             * Gets the value of the stateOrProvinceName property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getStateOrProvinceName() {
                                return stateOrProvinceName;
                            }

                            /**
                             * Sets the value of the stateOrProvinceName property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setStateOrProvinceName(String value) {
                                this.stateOrProvinceName = value;
                            }

                            /**
                             * Gets the value of the lineOfAuthorityCode property.
                             * 
                             */
                            public short getLineOfAuthorityCode() {
                                return lineOfAuthorityCode;
                            }

                            /**
                             * Sets the value of the lineOfAuthorityCode property.
                             * 
                             */
                            public void setLineOfAuthorityCode(short value) {
                                this.lineOfAuthorityCode = value;
                            }

                            /**
                             * Gets the value of the lineOfAuthorityDescription property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getLineOfAuthorityDescription() {
                                return lineOfAuthorityDescription;
                            }

                            /**
                             * Sets the value of the lineOfAuthorityDescription property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setLineOfAuthorityDescription(String value) {
                                this.lineOfAuthorityDescription = value;
                            }

                            /**
                             * Gets the value of the statusCode property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getStatusCode() {
                                return statusCode;
                            }

                            /**
                             * Sets the value of the statusCode property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setStatusCode(String value) {
                                this.statusCode = value;
                            }

                            /**
                             * Gets the value of the statusDescription property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getStatusDescription() {
                                return statusDescription;
                            }

                            /**
                             * Sets the value of the statusDescription property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setStatusDescription(String value) {
                                this.statusDescription = value;
                            }

                            /**
                             * Gets the value of the statusReasonCode property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getStatusReasonCode() {
                                return statusReasonCode;
                            }

                            /**
                             * Sets the value of the statusReasonCode property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setStatusReasonCode(String value) {
                                this.statusReasonCode = value;
                            }

                            /**
                             * Gets the value of the statusPeriod property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.LineOfAuthority.StatusPeriod }
                             *     
                             */
                            public LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.LineOfAuthority.StatusPeriod getStatusPeriod() {
                                return statusPeriod;
                            }

                            /**
                             * Sets the value of the statusPeriod property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.LineOfAuthority.StatusPeriod }
                             *     
                             */
                            public void setStatusPeriod(LicensingReportProcessResult.LicensingReport.JurisdictionReport.JurisdictionReportItem.Licensee.InsuranceLicense.LineOfAuthority.StatusPeriod value) {
                                this.statusPeriod = value;
                            }


                            /**
                             * <p>Java class for anonymous complex type.
                             * 
                             * <p>The following schema fragment specifies the expected content contained within this class.
                             * 
                             * <pre>
                             * &lt;complexType>
                             *   &lt;complexContent>
                             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                             *       &lt;sequence>
                             *         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
                             *       &lt;/sequence>
                             *     &lt;/restriction>
                             *   &lt;/complexContent>
                             * &lt;/complexType>
                             * </pre>
                             * 
                             * 
                             */
                            @XmlAccessorType(XmlAccessType.FIELD)
                            @XmlType(name = "", propOrder = {
                                "startDate"
                            })
                            public static class StatusPeriod {

                                @XmlElement(name = "StartDate", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
                                @XmlSchemaType(name = "date")
                                protected XMLGregorianCalendar startDate;

                                /**
                                 * Gets the value of the startDate property.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link XMLGregorianCalendar }
                                 *     
                                 */
                                public XMLGregorianCalendar getStartDate() {
                                    return startDate;
                                }

                                /**
                                 * Sets the value of the startDate property.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link XMLGregorianCalendar }
                                 *     
                                 */
                                public void setStartDate(XMLGregorianCalendar value) {
                                    this.startDate = value;
                                }

                            }

                        }

                    }

                }

            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="personReference" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class PersonReferences {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "personReference")
                protected String personReference;

                /**
                 * Gets the value of the value property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setValue(String value) {
                    this.value = value;
                }

                /**
                 * Gets the value of the personReference property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPersonReference() {
                    return personReference;
                }

                /**
                 * Sets the value of the personReference property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPersonReference(String value) {
                    this.personReference = value;
                }

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="ExternalIdentifier">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="TypeCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "externalIdentifier"
        })
        public static class Person {

            @XmlElement(name = "ExternalIdentifier", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
            protected LicensingReportProcessResult.LicensingReport.Person.ExternalIdentifier externalIdentifier;
            @XmlAttribute(name = "key")
            protected String key;

            /**
             * Gets the value of the externalIdentifier property.
             * 
             * @return
             *     possible object is
             *     {@link LicensingReportProcessResult.LicensingReport.Person.ExternalIdentifier }
             *     
             */
            public LicensingReportProcessResult.LicensingReport.Person.ExternalIdentifier getExternalIdentifier() {
                return externalIdentifier;
            }

            /**
             * Sets the value of the externalIdentifier property.
             * 
             * @param value
             *     allowed object is
             *     {@link LicensingReportProcessResult.LicensingReport.Person.ExternalIdentifier }
             *     
             */
            public void setExternalIdentifier(LicensingReportProcessResult.LicensingReport.Person.ExternalIdentifier value) {
                this.externalIdentifier = value;
            }

            /**
             * Gets the value of the key property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKey() {
                return key;
            }

            /**
             * Sets the value of the key property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKey(String value) {
                this.key = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="TypeCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}int"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "typeCode",
                "id"
            })
            public static class ExternalIdentifier {

                @XmlElement(name = "TypeCode", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
                protected String typeCode;
                @XmlElement(name = "Id", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1")
                protected int id;

                /**
                 * Gets the value of the typeCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTypeCode() {
                    return typeCode;
                }

                /**
                 * Sets the value of the typeCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTypeCode(String value) {
                    this.typeCode = value;
                }

                /**
                 * Gets the value of the id property.
                 * 
                 */
                public int getId() {
                    return id;
                }

                /**
                 * Sets the value of the id property.
                 * 
                 */
                public void setId(int value) {
                    this.id = value;
                }

            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="MessageId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="CorrelationId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="MessageDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="MessageDocumentCount" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "messageId",
        "correlationId",
        "messageDateTime",
        "messageDocumentCount"
    })
    public static class MessageHeader {

        @XmlElement(name = "MessageId", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
        protected String messageId;
        @XmlElement(name = "CorrelationId", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
        protected String correlationId;
        @XmlElement(name = "MessageDateTime", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar messageDateTime;
        @XmlElement(name = "MessageDocumentCount", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1")
        protected byte messageDocumentCount;

        /**
         * Gets the value of the messageId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMessageId() {
            return messageId;
        }

        /**
         * Sets the value of the messageId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMessageId(String value) {
            this.messageId = value;
        }

        /**
         * Gets the value of the correlationId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCorrelationId() {
            return correlationId;
        }

        /**
         * Sets the value of the correlationId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCorrelationId(String value) {
            this.correlationId = value;
        }

        /**
         * Gets the value of the messageDateTime property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getMessageDateTime() {
            return messageDateTime;
        }

        /**
         * Sets the value of the messageDateTime property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setMessageDateTime(XMLGregorianCalendar value) {
            this.messageDateTime = value;
        }

        /**
         * Gets the value of the messageDocumentCount property.
         * 
         */
        public byte getMessageDocumentCount() {
            return messageDocumentCount;
        }

        /**
         * Sets the value of the messageDocumentCount property.
         * 
         */
        public void setMessageDocumentCount(byte value) {
            this.messageDocumentCount = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="MessageDocumentResult">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="MessageDocumentReferences">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="DocumentId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="SuccessCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "statusCode",
        "messageDocumentResult"
    })
    public static class MessageResult {

        @XmlElement(name = "StatusCode", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
        protected String statusCode;
        @XmlElement(name = "MessageDocumentResult", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
        protected LicensingReportProcessResult.MessageResult.MessageDocumentResult messageDocumentResult;

        /**
         * Gets the value of the statusCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStatusCode() {
            return statusCode;
        }

        /**
         * Sets the value of the statusCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStatusCode(String value) {
            this.statusCode = value;
        }

        /**
         * Gets the value of the messageDocumentResult property.
         * 
         * @return
         *     possible object is
         *     {@link LicensingReportProcessResult.MessageResult.MessageDocumentResult }
         *     
         */
        public LicensingReportProcessResult.MessageResult.MessageDocumentResult getMessageDocumentResult() {
            return messageDocumentResult;
        }

        /**
         * Sets the value of the messageDocumentResult property.
         * 
         * @param value
         *     allowed object is
         *     {@link LicensingReportProcessResult.MessageResult.MessageDocumentResult }
         *     
         */
        public void setMessageDocumentResult(LicensingReportProcessResult.MessageResult.MessageDocumentResult value) {
            this.messageDocumentResult = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="MessageDocumentReferences">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="DocumentId" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="SuccessCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "messageDocumentReferences",
            "statusCode",
            "successCode"
        })
        public static class MessageDocumentResult {

            @XmlElement(name = "MessageDocumentReferences", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
            protected LicensingReportProcessResult.MessageResult.MessageDocumentResult.MessageDocumentReferences messageDocumentReferences;
            @XmlElement(name = "StatusCode", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
            protected String statusCode;
            @XmlElement(name = "SuccessCode", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
            protected String successCode;

            /**
             * Gets the value of the messageDocumentReferences property.
             * 
             * @return
             *     possible object is
             *     {@link LicensingReportProcessResult.MessageResult.MessageDocumentResult.MessageDocumentReferences }
             *     
             */
            public LicensingReportProcessResult.MessageResult.MessageDocumentResult.MessageDocumentReferences getMessageDocumentReferences() {
                return messageDocumentReferences;
            }

            /**
             * Sets the value of the messageDocumentReferences property.
             * 
             * @param value
             *     allowed object is
             *     {@link LicensingReportProcessResult.MessageResult.MessageDocumentResult.MessageDocumentReferences }
             *     
             */
            public void setMessageDocumentReferences(LicensingReportProcessResult.MessageResult.MessageDocumentResult.MessageDocumentReferences value) {
                this.messageDocumentReferences = value;
            }

            /**
             * Gets the value of the statusCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStatusCode() {
                return statusCode;
            }

            /**
             * Sets the value of the statusCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStatusCode(String value) {
                this.statusCode = value;
            }

            /**
             * Gets the value of the successCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuccessCode() {
                return successCode;
            }

            /**
             * Sets the value of the successCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuccessCode(String value) {
                this.successCode = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="DocumentId" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "documentId"
            })
            public static class MessageDocumentReferences {

                @XmlElement(name = "DocumentId", namespace = "http://www.acord.org/schema/data/draft/ReusableDataComponents/1", required = true)
                protected String documentId;

                /**
                 * Gets the value of the documentId property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDocumentId() {
                    return documentId;
                }

                /**
                 * Sets the value of the documentId property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDocumentId(String value) {
                    this.documentId = value;
                }

            }

        }

    }

}
