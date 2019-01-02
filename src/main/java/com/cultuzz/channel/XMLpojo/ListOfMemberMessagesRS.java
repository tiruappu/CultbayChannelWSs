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
 *         &lt;element name="Ack" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MemberMessages">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="MemberMessage" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ItemId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="BuyerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Subject" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="CreationDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Question" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="TotalMessagesCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Errors" type="{}ErrorsType" minOccurs="0"/>
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
    "ack",
    "memberMessages",
    "totalMessagesCount",
    "errors"
})
@XmlRootElement(name = "ListOfMemberMessagesRS")
public class ListOfMemberMessagesRS
    implements Serializable
{

    private final static long serialVersionUID = 20070801L;
    @XmlElement(name = "Ack", required = true)
    protected String ack;
    @XmlElement(name = "MemberMessages", required = true)
    protected ListOfMemberMessagesRS.MemberMessages memberMessages;
    @XmlElement(name = "TotalMessagesCount", required = true)
    protected String totalMessagesCount;
    @XmlElement(name = "Errors")
    protected ErrorsType errors;
    @XmlAttribute(name = "TimeStamp")
    protected String timeStamp;

    /**
     * Gets the value of the ack property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAck() {
        return ack;
    }

    /**
     * Sets the value of the ack property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAck(String value) {
        this.ack = value;
    }

    public boolean isSetAck() {
        return (this.ack!= null);
    }

    /**
     * Gets the value of the memberMessages property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfMemberMessagesRS.MemberMessages }
     *     
     */
    public ListOfMemberMessagesRS.MemberMessages getMemberMessages() {
        return memberMessages;
    }

    /**
     * Sets the value of the memberMessages property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfMemberMessagesRS.MemberMessages }
     *     
     */
    public void setMemberMessages(ListOfMemberMessagesRS.MemberMessages value) {
        this.memberMessages = value;
    }

    public boolean isSetMemberMessages() {
        return (this.memberMessages!= null);
    }

    /**
     * Gets the value of the totalMessagesCount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalMessagesCount() {
        return totalMessagesCount;
    }

    /**
     * Sets the value of the totalMessagesCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalMessagesCount(String value) {
        this.totalMessagesCount = value;
    }

    public boolean isSetTotalMessagesCount() {
        return (this.totalMessagesCount!= null);
    }

    /**
     * Gets the value of the errors property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorsType }
     *     
     */
    public ErrorsType getErrors() {
        return errors;
    }

    /**
     * Sets the value of the errors property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorsType }
     *     
     */
    public void setErrors(ErrorsType value) {
        this.errors = value;
    }

    public boolean isSetErrors() {
        return (this.errors!= null);
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
     *         &lt;element name="MemberMessage" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ItemId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="BuyerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Subject" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="CreationDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Question" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
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
        "memberMessage"
    })
    public static class MemberMessages
        implements Serializable
    {

        private final static long serialVersionUID = 20070801L;
        @XmlElement(name = "MemberMessage")
        protected List<ListOfMemberMessagesRS.MemberMessages.MemberMessage> memberMessage;

        /**
         * Gets the value of the memberMessage property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the memberMessage property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMemberMessage().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ListOfMemberMessagesRS.MemberMessages.MemberMessage }
         * 
         * 
         */
        public List<ListOfMemberMessagesRS.MemberMessages.MemberMessage> getMemberMessage() {
            if (memberMessage == null) {
                memberMessage = new ArrayList<ListOfMemberMessagesRS.MemberMessages.MemberMessage>();
            }
            return this.memberMessage;
        }

        public boolean isSetMemberMessage() {
            return ((this.memberMessage!= null)&&(!this.memberMessage.isEmpty()));
        }

        public void unsetMemberMessage() {
            this.memberMessage = null;
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
         *         &lt;element name="ItemId" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="BuyerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="Subject" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="CreationDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="Question" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "itemId",
            "buyerName",
            "subject",
            "creationDate",
            "status",
            "question"
        })
        public static class MemberMessage
            implements Serializable
        {

            private final static long serialVersionUID = 20070801L;
            @XmlElement(name = "ItemId", required = true)
            protected String itemId;
            @XmlElement(name = "BuyerName", required = true)
            protected String buyerName;
            @XmlElement(name = "Subject", required = true)
            protected String subject;
            @XmlElement(name = "CreationDate", required = true)
            protected String creationDate;
            @XmlElement(name = "Status", required = true)
            protected String status;
            @XmlElement(name = "Question", required = true)
            protected String question;
            @XmlAttribute(name = "id")
            protected String id;

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
             * Gets the value of the buyerName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBuyerName() {
                return buyerName;
            }

            /**
             * Sets the value of the buyerName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBuyerName(String value) {
                this.buyerName = value;
            }

            public boolean isSetBuyerName() {
                return (this.buyerName!= null);
            }

            /**
             * Gets the value of the subject property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSubject() {
                return subject;
            }

            /**
             * Sets the value of the subject property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSubject(String value) {
                this.subject = value;
            }

            public boolean isSetSubject() {
                return (this.subject!= null);
            }

            /**
             * Gets the value of the creationDate property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCreationDate() {
                return creationDate;
            }

            /**
             * Sets the value of the creationDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCreationDate(String value) {
                this.creationDate = value;
            }

            public boolean isSetCreationDate() {
                return (this.creationDate!= null);
            }

            /**
             * Gets the value of the status property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStatus() {
                return status;
            }

            /**
             * Sets the value of the status property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStatus(String value) {
                this.status = value;
            }

            public boolean isSetStatus() {
                return (this.status!= null);
            }

            /**
             * Gets the value of the question property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getQuestion() {
                return question;
            }

            /**
             * Sets the value of the question property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setQuestion(String value) {
                this.question = value;
            }

            public boolean isSetQuestion() {
                return (this.question!= null);
            }

            /**
             * Gets the value of the id property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getId() {
                return id;
            }

            /**
             * Sets the value of the id property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setId(String value) {
                this.id = value;
            }

            public boolean isSetId() {
                return (this.id!= null);
            }

        }

    }

}
