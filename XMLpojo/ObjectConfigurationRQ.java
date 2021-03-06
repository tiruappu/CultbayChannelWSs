//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.20 at 11:11:49 AM IST 
//


package com.cultuzz.channel.XMLpojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="Voucher">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="VoucherPdfTemplateId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="VoucherPictureURL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="VoucherLogoPictureURL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Settings">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Setting" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Key" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "voucher",
    "settings",
    "errorLang"
})
@XmlRootElement(name = "ObjectConfigurationRQ")
public class ObjectConfigurationRQ
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
    @XmlElement(name = "Voucher", required = true)
    protected ObjectConfigurationRQ.Voucher voucher;
    @XmlElement(name = "Settings", required = true)
    protected ObjectConfigurationRQ.Settings settings;
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
     * Gets the value of the voucher property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectConfigurationRQ.Voucher }
     *     
     */
    public ObjectConfigurationRQ.Voucher getVoucher() {
        return voucher;
    }

    /**
     * Sets the value of the voucher property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectConfigurationRQ.Voucher }
     *     
     */
    public void setVoucher(ObjectConfigurationRQ.Voucher value) {
        this.voucher = value;
    }

    public boolean isSetVoucher() {
        return (this.voucher!= null);
    }

    /**
     * Gets the value of the settings property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectConfigurationRQ.Settings }
     *     
     */
    public ObjectConfigurationRQ.Settings getSettings() {
        return settings;
    }

    /**
     * Sets the value of the settings property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectConfigurationRQ.Settings }
     *     
     */
    public void setSettings(ObjectConfigurationRQ.Settings value) {
        this.settings = value;
    }

    public boolean isSetSettings() {
        return (this.settings!= null);
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
     *       &lt;sequence>
     *         &lt;element name="Setting" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Key" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "setting"
    })
    public static class Settings
        implements Serializable
    {

        private final static long serialVersionUID = 20070801L;
        @XmlElement(name = "Setting")
        protected List<ObjectConfigurationRQ.Settings.Setting> setting;

        /**
         * Gets the value of the setting property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the setting property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSetting().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ObjectConfigurationRQ.Settings.Setting }
         * 
         * 
         */
        public List<ObjectConfigurationRQ.Settings.Setting> getSetting() {
            if (setting == null) {
                setting = new ArrayList<ObjectConfigurationRQ.Settings.Setting>();
            }
            return this.setting;
        }

        public boolean isSetSetting() {
            return ((this.setting!= null)&&(!this.setting.isEmpty()));
        }

        public void unsetSetting() {
            this.setting = null;
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
         *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="Key" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
            "name",
            "key"
        })
        public static class Setting
            implements Serializable
        {

            private final static long serialVersionUID = 20070801L;
            @XmlElement(name = "Name", required = true)
            protected String name;
            @XmlElement(name = "Key", required = true)
            protected String key;

            /**
             * Gets the value of the name property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getName() {
                return name;
            }

            /**
             * Sets the value of the name property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setName(String value) {
                this.name = value;
            }

            public boolean isSetName() {
                return (this.name!= null);
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

            public boolean isSetKey() {
                return (this.key!= null);
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
    @XmlType(name = "", propOrder = {
        "voucherPdfTemplateId",
        "voucherPictureURL",
        "voucherLogoPictureURL"
    })
    public static class Voucher
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

}
