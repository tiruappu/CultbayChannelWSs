//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.20 at 11:11:49 AM IST 
//


package com.cultuzz.channel.XMLpojo;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PropertiesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PropertiesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Nights" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Persons" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CateringType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TypeOfRoom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PropertiesType", propOrder = {
    "nights",
    "persons",
    "cateringType",
    "typeOfRoom"
})
public class PropertiesType
    implements Serializable
{

    private final static long serialVersionUID = 20070801L;
    @XmlElement(name = "Nights")
    protected String nights;
    @XmlElement(name = "Persons")
    protected String persons;
    @XmlElement(name = "CateringType")
    protected String cateringType;
    @XmlElement(name = "TypeOfRoom")
    protected String typeOfRoom;

    /**
     * Gets the value of the nights property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNights() {
        return nights;
    }

    /**
     * Sets the value of the nights property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNights(String value) {
        this.nights = value;
    }

    public boolean isSetNights() {
        return (this.nights!= null);
    }

    /**
     * Gets the value of the persons property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersons() {
        return persons;
    }

    /**
     * Sets the value of the persons property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersons(String value) {
        this.persons = value;
    }

    public boolean isSetPersons() {
        return (this.persons!= null);
    }

    /**
     * Gets the value of the cateringType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCateringType() {
        return cateringType;
    }

    /**
     * Sets the value of the cateringType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCateringType(String value) {
        this.cateringType = value;
    }

    public boolean isSetCateringType() {
        return (this.cateringType!= null);
    }

    /**
     * Gets the value of the typeOfRoom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeOfRoom() {
        return typeOfRoom;
    }

    /**
     * Sets the value of the typeOfRoom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeOfRoom(String value) {
        this.typeOfRoom = value;
    }

    public boolean isSetTypeOfRoom() {
        return (this.typeOfRoom!= null);
    }

}
