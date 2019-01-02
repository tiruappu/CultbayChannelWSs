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
 *         &lt;element name="ItemId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OrderId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VoucherId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TravellerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Period">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="From" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="To" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ErrorLang" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "itemId",
    "orderId",
    "voucherId",
    "action",
    "travellerName",
    "period",
    "errorLang"
})
@XmlRootElement(name = "VoucherRedemptionRQ")
public class VoucherRedemptionRQ
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
    @XmlElement(name = "ItemId", required = true)
    protected String itemId;
    @XmlElement(name = "OrderId", required = true)
    protected String orderId;
    @XmlElement(name = "VoucherId", required = true)
    protected String voucherId;
    @XmlElement(name = "Action", required = true)
    protected String action;
    @XmlElement(name = "TravellerName", required = true)
    protected String travellerName;
    @XmlElement(name = "Period", required = true)
    protected VoucherRedemptionRQ.Period period;
    @XmlElement(name = "ErrorLang", required = true)
    protected String errorLang;
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
     * Gets the value of the itemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * Sets the value of the itemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemId(String value) {
        this.itemId = value;
    }

    public boolean isSetItemId() {
        return (this.itemId!= null);
    }

    /**
     * Gets the value of the orderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets the value of the orderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderId(String value) {
        this.orderId = value;
    }

    public boolean isSetOrderId() {
        return (this.orderId!= null);
    }

    /**
     * Gets the value of the voucherId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVoucherId() {
        return voucherId;
    }

    /**
     * Sets the value of the voucherId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVoucherId(String value) {
        this.voucherId = value;
    }

    public boolean isSetVoucherId() {
        return (this.voucherId!= null);
    }

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAction(String value) {
        this.action = value;
    }

    public boolean isSetAction() {
        return (this.action!= null);
    }

    /**
     * Gets the value of the travellerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTravellerName() {
        return travellerName;
    }

    /**
     * Sets the value of the travellerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTravellerName(String value) {
        this.travellerName = value;
    }

    public boolean isSetTravellerName() {
        return (this.travellerName!= null);
    }

    /**
     * Gets the value of the period property.
     * 
     * @return
     *     possible object is
     *     {@link VoucherRedemptionRQ.Period }
     *     
     */
    public VoucherRedemptionRQ.Period getPeriod() {
        return period;
    }

    /**
     * Sets the value of the period property.
     * 
     * @param value
     *     allowed object is
     *     {@link VoucherRedemptionRQ.Period }
     *     
     */
    public void setPeriod(VoucherRedemptionRQ.Period value) {
        this.period = value;
    }

    public boolean isSetPeriod() {
        return (this.period!= null);
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


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="From" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="To" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Period
        implements Serializable
    {

        private final static long serialVersionUID = 20070801L;
        @XmlAttribute(name = "From")
        protected String from;
        @XmlAttribute(name = "To")
        protected String to;

        /**
         * Gets the value of the from property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFrom() {
            return from;
        }

        /**
         * Sets the value of the from property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFrom(String value) {
            this.from = value;
        }

        public boolean isSetFrom() {
            return (this.from!= null);
        }

        /**
         * Gets the value of the to property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTo() {
            return to;
        }

        /**
         * Sets the value of the to property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTo(String value) {
            this.to = value;
        }

        public boolean isSetTo() {
            return (this.to!= null);
        }

    }

}