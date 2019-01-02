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
 *         &lt;element name="ErrorLang" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MemberMessages">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="MemberMessage">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ItemId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="BuyerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="SellerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="Subject" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="CreationDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="OfferTitle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Question" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Answer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="TimeStamp" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    "memberMessages"
})
@XmlRootElement(name = "AnswerBuyerMessageRQ")
public class AnswerBuyerMessageRQ
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
    @XmlElement(name = "MemberMessages", required = true)
    protected AnswerBuyerMessageRQ.MemberMessages memberMessages;
    @XmlAttribute(name = "TimeStamp", required = true)
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
     * Gets the value of the memberMessages property.
     * 
     * @return
     *     possible object is
     *     {@link AnswerBuyerMessageRQ.MemberMessages }
     *     
     */
    public AnswerBuyerMessageRQ.MemberMessages getMemberMessages() {
        return memberMessages;
    }

    /**
     * Sets the value of the memberMessages property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnswerBuyerMessageRQ.MemberMessages }
     *     
     */
    public void setMemberMessages(AnswerBuyerMessageRQ.MemberMessages value) {
        this.memberMessages = value;
    }

    public boolean isSetMemberMessages() {
        return (this.memberMessages!= null);
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
     *         &lt;element name="MemberMessage">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ItemId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="BuyerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="SellerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="Subject" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="CreationDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="OfferTitle" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Question" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Answer" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
        @XmlElement(name = "MemberMessage", required = true)
        protected AnswerBuyerMessageRQ.MemberMessages.MemberMessage memberMessage;

        /**
         * Gets the value of the memberMessage property.
         * 
         * @return
         *     possible object is
         *     {@link AnswerBuyerMessageRQ.MemberMessages.MemberMessage }
         *     
         */
        public AnswerBuyerMessageRQ.MemberMessages.MemberMessage getMemberMessage() {
            return memberMessage;
        }

        /**
         * Sets the value of the memberMessage property.
         * 
         * @param value
         *     allowed object is
         *     {@link AnswerBuyerMessageRQ.MemberMessages.MemberMessage }
         *     
         */
        public void setMemberMessage(AnswerBuyerMessageRQ.MemberMessages.MemberMessage value) {
            this.memberMessage = value;
        }

        public boolean isSetMemberMessage() {
            return (this.memberMessage!= null);
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
         *         &lt;element name="BuyerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="SellerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="Subject" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="CreationDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="OfferTitle" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="Question" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="Answer" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
            "sellerName",
            "subject",
            "status",
            "creationDate",
            "offerTitle",
            "question",
            "answer"
        })
        public static class MemberMessage
            implements Serializable
        {

            private final static long serialVersionUID = 20070801L;
            @XmlElement(name = "ItemId", required = true)
            protected String itemId;
            @XmlElement(name = "BuyerName")
            protected String buyerName;
            @XmlElement(name = "SellerName")
            protected String sellerName;
            @XmlElement(name = "Subject")
            protected String subject;
            @XmlElement(name = "Status")
            protected String status;
            @XmlElement(name = "CreationDate")
            protected String creationDate;
            @XmlElement(name = "OfferTitle", required = true)
            protected String offerTitle;
            @XmlElement(name = "Question", required = true)
            protected String question;
            @XmlElement(name = "Answer", required = true)
            protected String answer;
            @XmlAttribute(name = "id", required = true)
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
             * Gets the value of the sellerName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSellerName() {
                return sellerName;
            }

            /**
             * Sets the value of the sellerName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSellerName(String value) {
                this.sellerName = value;
            }

            public boolean isSetSellerName() {
                return (this.sellerName!= null);
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
             * Gets the value of the offerTitle property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getOfferTitle() {
                return offerTitle;
            }

            /**
             * Sets the value of the offerTitle property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setOfferTitle(String value) {
                this.offerTitle = value;
            }

            public boolean isSetOfferTitle() {
                return (this.offerTitle!= null);
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
             * Gets the value of the answer property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAnswer() {
                return answer;
            }

            /**
             * Sets the value of the answer property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAnswer(String value) {
                this.answer = value;
            }

            public boolean isSetAnswer() {
                return (this.answer!= null);
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