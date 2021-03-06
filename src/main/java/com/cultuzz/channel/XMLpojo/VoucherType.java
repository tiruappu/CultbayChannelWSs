//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.12.11 at 04:31:02 PM IST 
//


package com.cultuzz.channel.XMLpojo;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VoucherType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VoucherType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VoucherPdfTemplateId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="VoucherPictureURL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VoucherLogoPictureURL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VoucherType", propOrder = {
    "voucherPdfTemplateId",
    "voucherPictureURL",
    "voucherLogoPictureURL"
})
public class VoucherType
    implements Serializable
{

    private final static long serialVersionUID = 20070801L;
    @XmlElement(name = "VoucherPdfTemplateId")
    protected int voucherPdfTemplateId;
    @XmlElement(name = "VoucherPictureURL", required = true)
    protected String voucherPictureURL;
    @XmlElement(name = "VoucherLogoPictureURL", required = true)
    protected String voucherLogoPictureURL;

    /**
     * Gets the value of the voucherPdfTemplateId property.
     * 
     */
    public int getVoucherPdfTemplateId() {
        return voucherPdfTemplateId;
    }

    /**
     * Sets the value of the voucherPdfTemplateId property.
     * 
     */
    public void setVoucherPdfTemplateId(int value) {
        this.voucherPdfTemplateId = value;
    }

    public boolean isSetVoucherPdfTemplateId() {
        return true;
    }

    /**
     * Gets the value of the voucherPictureURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVoucherPictureURL() {
        return voucherPictureURL;
    }

    /**
     * Sets the value of the voucherPictureURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVoucherPictureURL(String value) {
        this.voucherPictureURL = value;
    }

    public boolean isSetVoucherPictureURL() {
        return (this.voucherPictureURL!= null);
    }

    /**
     * Gets the value of the voucherLogoPictureURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVoucherLogoPictureURL() {
        return voucherLogoPictureURL;
    }

    /**
     * Sets the value of the voucherLogoPictureURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVoucherLogoPictureURL(String value) {
        this.voucherLogoPictureURL = value;
    }

    public boolean isSetVoucherLogoPictureURL() {
        return (this.voucherLogoPictureURL!= null);
    }

}
