//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.04.09 at 11:35:44 AM IST 
//


package com.cultuzz.XMLPojos;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


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
 *         &lt;element name="language" type="{}LanguageType" minOccurs="0"/>
 *         &lt;element name="ebayitemid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="czId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="voucherLogo" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="voucherOfferText" type="{}CDATAString"/>
 *         &lt;element name="voucherPicture" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="voucherValiditySpecification" type="{}CDATAString" minOccurs="0"/>
 *         &lt;element name="cancellationPolicyText" type="{}CDATAString" minOccurs="0"/>
 *         &lt;element name="paidDate" type="{}DateType" minOccurs="0"/>
 *         &lt;element name="hotelData" type="{}hotelDataType"/>
 *         &lt;element name="buyerData" type="{}buyerDataType" minOccurs="0"/>
 *         &lt;element name="travellerAddress" type="{}AddressType"/>
 *         &lt;element name="bookingLinkQRCode" type="{}QRCodeType" minOccurs="0"/>
 *         &lt;element name="uniqueCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Amenities" type="{}AmenitiesType" minOccurs="0"/>
 *         &lt;element name="Price" type="{}PriceType" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="durationOfValidity" type="{}DateType"/>
 *           &lt;element name="validityPeriods" type="{}ValidityPeriodsType"/>
 *         &lt;/choice>
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
    "language",
    "ebayitemid",
    "czId",
    "voucherLogo",
    "voucherOfferText",
    "voucherPicture",
    "voucherValiditySpecification",
    "cancellationPolicyText",
    "paidDate",
    "hotelData",
    "buyerData",
    "travellerAddress",
    "bookingLinkQRCode",
    "uniqueCode",
    "amenities",
    "price",
    "durationOfValidity",
    "validityPeriods"
})
@XmlRootElement(name = "voucherElements")
public class VoucherElements {

    protected LanguageType language;
    protected String ebayitemid;
    protected String czId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String voucherLogo;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(JaxbCDATAAdapter.class)
    protected String voucherOfferText;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String voucherPicture;
    @XmlJavaTypeAdapter(JaxbCDATAAdapter.class)
    protected String voucherValiditySpecification;
    @XmlJavaTypeAdapter(JaxbCDATAAdapter.class)
    protected String cancellationPolicyText;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(DateTypeAdapter.class)
    protected Date paidDate;
    @XmlElement(required = true)
    protected HotelDataType hotelData;
    protected BuyerDataType buyerData;
    @XmlElement(required = true)
    protected AddressType travellerAddress;
    protected QRCodeType bookingLinkQRCode;
    @XmlElement(required = true)
    protected String uniqueCode;
    @XmlElement(name = "Amenities")
    protected AmenitiesType amenities;
    @XmlElement(name = "Price")
    protected PriceType price;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(DateTypeAdapter.class)
    protected Date durationOfValidity;
    protected ValidityPeriodsType validityPeriods;

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link LanguageType }
     *     
     */
    public LanguageType getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link LanguageType }
     *     
     */
    public void setLanguage(LanguageType value) {
        this.language = value;
    }

    /**
     * Gets the value of the ebayitemid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEbayitemid() {
        return ebayitemid;
    }

    /**
     * Sets the value of the ebayitemid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEbayitemid(String value) {
        this.ebayitemid = value;
    }

    /**
     * Gets the value of the czId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCzId() {
        return czId;
    }

    /**
     * Sets the value of the czId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCzId(String value) {
        this.czId = value;
    }

    /**
     * Gets the value of the voucherLogo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVoucherLogo() {
        return voucherLogo;
    }

    /**
     * Sets the value of the voucherLogo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVoucherLogo(String value) {
        this.voucherLogo = value;
    }

    /**
     * Gets the value of the voucherOfferText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVoucherOfferText() {
        return voucherOfferText;
    }

    /**
     * Sets the value of the voucherOfferText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVoucherOfferText(String value) {
        this.voucherOfferText = value;
    }

    /**
     * Gets the value of the voucherPicture property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVoucherPicture() {
        return voucherPicture;
    }

    /**
     * Sets the value of the voucherPicture property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVoucherPicture(String value) {
        this.voucherPicture = value;
    }

    /**
     * Gets the value of the voucherValiditySpecification property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVoucherValiditySpecification() {
        return voucherValiditySpecification;
    }

    /**
     * Sets the value of the voucherValiditySpecification property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVoucherValiditySpecification(String value) {
        this.voucherValiditySpecification = value;
    }

    /**
     * Gets the value of the cancellationPolicyText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCancellationPolicyText() {
        return cancellationPolicyText;
    }

    /**
     * Sets the value of the cancellationPolicyText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCancellationPolicyText(String value) {
        this.cancellationPolicyText = value;
    }

    /**
     * Gets the value of the paidDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getPaidDate() {
        return paidDate;
    }

    /**
     * Sets the value of the paidDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaidDate(Date value) {
        this.paidDate = value;
    }

    /**
     * Gets the value of the hotelData property.
     * 
     * @return
     *     possible object is
     *     {@link HotelDataType }
     *     
     */
    public HotelDataType getHotelData() {
        return hotelData;
    }

    /**
     * Sets the value of the hotelData property.
     * 
     * @param value
     *     allowed object is
     *     {@link HotelDataType }
     *     
     */
    public void setHotelData(HotelDataType value) {
        this.hotelData = value;
    }

    /**
     * Gets the value of the buyerData property.
     * 
     * @return
     *     possible object is
     *     {@link BuyerDataType }
     *     
     */
    public BuyerDataType getBuyerData() {
        return buyerData;
    }

    /**
     * Sets the value of the buyerData property.
     * 
     * @param value
     *     allowed object is
     *     {@link BuyerDataType }
     *     
     */
    public void setBuyerData(BuyerDataType value) {
        this.buyerData = value;
    }

    /**
     * Gets the value of the travellerAddress property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getTravellerAddress() {
        return travellerAddress;
    }

    /**
     * Sets the value of the travellerAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setTravellerAddress(AddressType value) {
        this.travellerAddress = value;
    }

    /**
     * Gets the value of the bookingLinkQRCode property.
     * 
     * @return
     *     possible object is
     *     {@link QRCodeType }
     *     
     */
    public QRCodeType getBookingLinkQRCode() {
        return bookingLinkQRCode;
    }

    /**
     * Sets the value of the bookingLinkQRCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link QRCodeType }
     *     
     */
    public void setBookingLinkQRCode(QRCodeType value) {
        this.bookingLinkQRCode = value;
    }

    /**
     * Gets the value of the uniqueCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqueCode() {
        return uniqueCode;
    }

    /**
     * Sets the value of the uniqueCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqueCode(String value) {
        this.uniqueCode = value;
    }

    /**
     * Gets the value of the amenities property.
     * 
     * @return
     *     possible object is
     *     {@link AmenitiesType }
     *     
     */
    public AmenitiesType getAmenities() {
        return amenities;
    }

    /**
     * Sets the value of the amenities property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmenitiesType }
     *     
     */
    public void setAmenities(AmenitiesType value) {
        this.amenities = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link PriceType }
     *     
     */
    public PriceType getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceType }
     *     
     */
    public void setPrice(PriceType value) {
        this.price = value;
    }

    /**
     * Gets the value of the durationOfValidity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDurationOfValidity() {
        return durationOfValidity;
    }

    /**
     * Sets the value of the durationOfValidity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDurationOfValidity(Date value) {
        this.durationOfValidity = value;
    }

    /**
     * Gets the value of the validityPeriods property.
     * 
     * @return
     *     possible object is
     *     {@link ValidityPeriodsType }
     *     
     */
    public ValidityPeriodsType getValidityPeriods() {
        return validityPeriods;
    }

    /**
     * Sets the value of the validityPeriods property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidityPeriodsType }
     *     
     */
    public void setValidityPeriods(ValidityPeriodsType value) {
        this.validityPeriods = value;
    }

}