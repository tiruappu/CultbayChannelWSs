//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.12.11 at 04:31:02 PM IST 
//


package com.cultuzz.channel.XMLpojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentOptionsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentOptionsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PaymentOption" type="{}PaymentOptionType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="paymentToHotelier" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentOptionsType", propOrder = {
    "paymentOption"
})
public class PaymentOptionsType
    implements Serializable
{

    private final static long serialVersionUID = 20070801L;
    @XmlElement(name = "PaymentOption")
    protected List<PaymentOptionType> paymentOption;
    @XmlAttribute(name = "paymentToHotelier")
    protected String paymentToHotelier;

    /**
     * Gets the value of the paymentOption property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentOption property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentOption().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentOptionType }
     * 
     * 
     */
    public List<PaymentOptionType> getPaymentOption() {
        if (paymentOption == null) {
            paymentOption = new ArrayList<PaymentOptionType>();
        }
        return this.paymentOption;
    }

    public boolean isSetPaymentOption() {
        return ((this.paymentOption!= null)&&(!this.paymentOption.isEmpty()));
    }

    public void unsetPaymentOption() {
        this.paymentOption = null;
    }

    /**
     * Gets the value of the paymentToHotelier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentToHotelier() {
        return paymentToHotelier;
    }

    /**
     * Sets the value of the paymentToHotelier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentToHotelier(String value) {
        this.paymentToHotelier = value;
    }

    public boolean isSetPaymentToHotelier() {
        return (this.paymentToHotelier!= null);
    }

}
