//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.04.09 at 11:35:44 AM IST 
//


package com.cultuzz.XMLPojos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for buyerDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="buyerDataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userId" type="{}CDATAString"/>
 *         &lt;element name="address" type="{}AddressType"/>
 *         &lt;element name="bookingLink" type="{}CDATAString" minOccurs="0"/>
 *         &lt;element name="bookingDates" type="{}BookingDatesType" minOccurs="0"/>
 *         &lt;element name="buyerDetailsQRCode" type="{}QRCodeType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "buyerDataType", propOrder = {
    "userId",
    "address",
    "bookingLink",
    "bookingDates",
    "buyerDetailsQRCode"
})
public class BuyerDataType {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(JaxbCDATAAdapter.class)
    protected String userId;
    @XmlElement(required = true)
    protected AddressType address;
    @XmlJavaTypeAdapter(JaxbCDATAAdapter.class)
    protected String bookingLink;
    protected BookingDatesType bookingDates;
    @XmlElement(required = true)
    protected QRCodeType buyerDetailsQRCode;

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setAddress(AddressType value) {
        this.address = value;
    }

    /**
     * Gets the value of the bookingLink property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookingLink() {
        return bookingLink;
    }

    /**
     * Sets the value of the bookingLink property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookingLink(String value) {
        this.bookingLink = value;
    }

    /**
     * Gets the value of the bookingDates property.
     * 
     * @return
     *     possible object is
     *     {@link BookingDatesType }
     *     
     */
    public BookingDatesType getBookingDates() {
        return bookingDates;
    }

    /**
     * Sets the value of the bookingDates property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookingDatesType }
     *     
     */
    public void setBookingDates(BookingDatesType value) {
        this.bookingDates = value;
    }

    /**
     * Gets the value of the buyerDetailsQRCode property.
     * 
     * @return
     *     possible object is
     *     {@link QRCodeType }
     *     
     */
    public QRCodeType getBuyerDetailsQRCode() {
        return buyerDetailsQRCode;
    }

    /**
     * Sets the value of the buyerDetailsQRCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link QRCodeType }
     *     
     */
    public void setBuyerDetailsQRCode(QRCodeType value) {
        this.buyerDetailsQRCode = value;
    }

}
