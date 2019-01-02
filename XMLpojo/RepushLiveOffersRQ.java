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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="AuthenticationCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SourceId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ChannelId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ObjectId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ErrorLang" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TemplateId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SubTitle" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="GalleryPicture" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ItemPictures" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CategoriesAndAttributes" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="RepushTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="TimeStamp" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "authenticationCode",
    "sourceId",
    "channelId",
    "objectId",
    "errorLang",
    "templateId",
    "title",
    "subTitle",
    "galleryPicture",
    "itemPictures",
    "categoriesAndAttributes",
    "description",
    "repushTime"
})
@XmlRootElement(name = "RepushLiveOffersRQ")
public class RepushLiveOffersRQ
    implements Serializable
{

    private final static long serialVersionUID = 20070801L;
    @XmlElement(name = "AuthenticationCode", required = true)
    protected String authenticationCode;
    @XmlElement(name = "SourceId")
    protected int sourceId;
    @XmlElement(name = "ChannelId")
    protected int channelId;
    @XmlElement(name = "ObjectId")
    protected int objectId;
    @XmlElement(name = "ErrorLang", required = true)
    protected String errorLang;
    @XmlElement(name = "TemplateId")
    protected int templateId;
    @XmlElement(name = "Title")
    protected boolean title;
    @XmlElement(name = "SubTitle")
    protected boolean subTitle;
    @XmlElement(name = "GalleryPicture")
    protected boolean galleryPicture;
    @XmlElement(name = "ItemPictures")
    protected boolean itemPictures;
    @XmlElement(name = "CategoriesAndAttributes")
    protected boolean categoriesAndAttributes;
    @XmlElement(name = "Description")
    protected boolean description;
    @XmlElement(name = "RepushTime", required = true)
    protected String repushTime;
    @XmlAttribute(name = "TimeStamp")
    protected String timeStamp;

    /**
     * Gets the value of the authenticationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthenticationCode() {
        return authenticationCode;
    }

    /**
     * Sets the value of the authenticationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthenticationCode(String value) {
        this.authenticationCode = value;
    }

    public boolean isSetAuthenticationCode() {
        return (this.authenticationCode!= null);
    }

    /**
     * Gets the value of the sourceId property.
     * 
     */
    public int getSourceId() {
        return sourceId;
    }

    /**
     * Sets the value of the sourceId property.
     * 
     */
    public void setSourceId(int value) {
        this.sourceId = value;
    }

    public boolean isSetSourceId() {
        return true;
    }

    /**
     * Gets the value of the channelId property.
     * 
     */
    public int getChannelId() {
        return channelId;
    }

    /**
     * Sets the value of the channelId property.
     * 
     */
    public void setChannelId(int value) {
        this.channelId = value;
    }

    public boolean isSetChannelId() {
        return true;
    }

    /**
     * Gets the value of the objectId property.
     * 
     */
    public int getObjectId() {
        return objectId;
    }

    /**
     * Sets the value of the objectId property.
     * 
     */
    public void setObjectId(int value) {
        this.objectId = value;
    }

    public boolean isSetObjectId() {
        return true;
    }

    /**
     * Gets the value of the errorLang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorLang() {
        return errorLang;
    }

    /**
     * Sets the value of the errorLang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorLang(String value) {
        this.errorLang = value;
    }

    public boolean isSetErrorLang() {
        return (this.errorLang!= null);
    }

    /**
     * Gets the value of the templateId property.
     * 
     */
    public int getTemplateId() {
        return templateId;
    }

    /**
     * Sets the value of the templateId property.
     * 
     */
    public void setTemplateId(int value) {
        this.templateId = value;
    }

    public boolean isSetTemplateId() {
        return true;
    }

    /**
     * Gets the value of the title property.
     * 
     */
    public boolean isTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     */
    public void setTitle(boolean value) {
        this.title = value;
    }

    public boolean isSetTitle() {
        return true;
    }

    /**
     * Gets the value of the subTitle property.
     * 
     */
    public boolean isSubTitle() {
        return subTitle;
    }

    /**
     * Sets the value of the subTitle property.
     * 
     */
    public void setSubTitle(boolean value) {
        this.subTitle = value;
    }

    public boolean isSetSubTitle() {
        return true;
    }

    /**
     * Gets the value of the galleryPicture property.
     * 
     */
    public boolean isGalleryPicture() {
        return galleryPicture;
    }

    /**
     * Sets the value of the galleryPicture property.
     * 
     */
    public void setGalleryPicture(boolean value) {
        this.galleryPicture = value;
    }

    public boolean isSetGalleryPicture() {
        return true;
    }

    /**
     * Gets the value of the itemPictures property.
     * 
     */
    public boolean isItemPictures() {
        return itemPictures;
    }

    /**
     * Sets the value of the itemPictures property.
     * 
     */
    public void setItemPictures(boolean value) {
        this.itemPictures = value;
    }

    public boolean isSetItemPictures() {
        return true;
    }

    /**
     * Gets the value of the categoriesAndAttributes property.
     * 
     */
    public boolean isCategoriesAndAttributes() {
        return categoriesAndAttributes;
    }

    /**
     * Sets the value of the categoriesAndAttributes property.
     * 
     */
    public void setCategoriesAndAttributes(boolean value) {
        this.categoriesAndAttributes = value;
    }

    public boolean isSetCategoriesAndAttributes() {
        return true;
    }

    /**
     * Gets the value of the description property.
     * 
     */
    public boolean isDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     */
    public void setDescription(boolean value) {
        this.description = value;
    }

    public boolean isSetDescription() {
        return true;
    }

    /**
     * Gets the value of the repushTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepushTime() {
        return repushTime;
    }

    /**
     * Sets the value of the repushTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepushTime(String value) {
        this.repushTime = value;
    }

    public boolean isSetRepushTime() {
        return (this.repushTime!= null);
    }

    /**
     * Gets the value of the timeStamp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets the value of the timeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeStamp(String value) {
        this.timeStamp = value;
    }

    public boolean isSetTimeStamp() {
        return (this.timeStamp!= null);
    }

}