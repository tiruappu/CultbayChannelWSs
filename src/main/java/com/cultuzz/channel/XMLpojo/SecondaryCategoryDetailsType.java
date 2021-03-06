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
 * <p>Java class for SecondaryCategoryDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SecondaryCategoryDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SecondaryCategoryId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SecondaryCategoryName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SecondaryItemSpecifics" type="{}SecondaryItemSpecificsType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SecondaryCategoryDetailsType", propOrder = {
    "secondaryCategoryId",
    "secondaryCategoryName",
    "secondaryItemSpecifics"
})
public class SecondaryCategoryDetailsType
    implements Serializable
{

    private final static long serialVersionUID = 20070801L;
    @XmlElement(name = "SecondaryCategoryId")
    protected String secondaryCategoryId;
    @XmlElement(name = "SecondaryCategoryName")
    protected String secondaryCategoryName;
    @XmlElement(name = "SecondaryItemSpecifics")
    protected SecondaryItemSpecificsType secondaryItemSpecifics;

    /**
     * Gets the value of the secondaryCategoryId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondaryCategoryId() {
        return secondaryCategoryId;
    }

    /**
     * Sets the value of the secondaryCategoryId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondaryCategoryId(String value) {
        this.secondaryCategoryId = value;
    }

    public boolean isSetSecondaryCategoryId() {
        return (this.secondaryCategoryId!= null);
    }

    /**
     * Gets the value of the secondaryCategoryName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondaryCategoryName() {
        return secondaryCategoryName;
    }

    /**
     * Sets the value of the secondaryCategoryName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondaryCategoryName(String value) {
        this.secondaryCategoryName = value;
    }

    public boolean isSetSecondaryCategoryName() {
        return (this.secondaryCategoryName!= null);
    }

    /**
     * Gets the value of the secondaryItemSpecifics property.
     * 
     * @return
     *     possible object is
     *     {@link SecondaryItemSpecificsType }
     *     
     */
    public SecondaryItemSpecificsType getSecondaryItemSpecifics() {
        return secondaryItemSpecifics;
    }

    /**
     * Sets the value of the secondaryItemSpecifics property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecondaryItemSpecificsType }
     *     
     */
    public void setSecondaryItemSpecifics(SecondaryItemSpecificsType value) {
        this.secondaryItemSpecifics = value;
    }

    public boolean isSetSecondaryItemSpecifics() {
        return (this.secondaryItemSpecifics!= null);
    }

}
