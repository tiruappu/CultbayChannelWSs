package com.cultuzz.channel.handler;

import java.io.IOException;
import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cultuzz.channel.XMLpojo.AddPictureRQ;
import com.cultuzz.channel.XMLpojo.AddPictureRS;
import com.cultuzz.channel.XMLpojo.AnswerBuyerMessageRQ;
import com.cultuzz.channel.XMLpojo.AnswerBuyerMessageRS;
import com.cultuzz.channel.XMLpojo.DeletePictureRQ;
import com.cultuzz.channel.XMLpojo.DeletePictureRS;
import com.cultuzz.channel.XMLpojo.GetHotelDataRQ;
import com.cultuzz.channel.XMLpojo.GetHotelDataRS;
import com.cultuzz.channel.XMLpojo.ListOfDesignTemplatesRQ;
import com.cultuzz.channel.XMLpojo.ListOfDesignTemplatesRS;
import com.cultuzz.channel.XMLpojo.ListOfMemberMessagesRQ;
import com.cultuzz.channel.XMLpojo.ListOfMemberMessagesRS;
import com.cultuzz.channel.XMLpojo.ListOfOffersRQ;
import com.cultuzz.channel.XMLpojo.ListOfOffersRS;
import com.cultuzz.channel.XMLpojo.ListOfPicturesRQ;
import com.cultuzz.channel.XMLpojo.ListOfPicturesRS;
import com.cultuzz.channel.XMLpojo.ListOfTemplatesRQ;
import com.cultuzz.channel.XMLpojo.ListOfTemplatesRS;
import com.cultuzz.channel.XMLpojo.ListOfVouchersRQ;
import com.cultuzz.channel.XMLpojo.ListOfVouchersRS;
import com.cultuzz.channel.XMLpojo.MemberMessageDetailsRQ;
import com.cultuzz.channel.XMLpojo.MemberMessageDetailsRS;
import com.cultuzz.channel.XMLpojo.ObjectConfigurationDetailsRQ;
import com.cultuzz.channel.XMLpojo.ObjectConfigurationDetailsRS;
import com.cultuzz.channel.XMLpojo.ObjectConfigurationRQ;
import com.cultuzz.channel.XMLpojo.ObjectConfigurationRS;
import com.cultuzz.channel.XMLpojo.ObjectMetaDataRQ;
import com.cultuzz.channel.XMLpojo.ObjectMetaDataRS;
import com.cultuzz.channel.XMLpojo.OfferCreationRQ;
import com.cultuzz.channel.XMLpojo.OfferCreationRS;
import com.cultuzz.channel.XMLpojo.OfferDetailsRQ;
import com.cultuzz.channel.XMLpojo.OfferDetailsRS;
import com.cultuzz.channel.XMLpojo.OfferEndItemRQ;
import com.cultuzz.channel.XMLpojo.OfferEndItemRS;
import com.cultuzz.channel.XMLpojo.OfferReviseRQ;
import com.cultuzz.channel.XMLpojo.OfferReviseRS;
import com.cultuzz.channel.XMLpojo.RepushLiveOffersRQ;
import com.cultuzz.channel.XMLpojo.RepushLiveOffersRS;
import com.cultuzz.channel.XMLpojo.SetHotelDataRQ;
import com.cultuzz.channel.XMLpojo.SetHotelDataRS;
import com.cultuzz.channel.XMLpojo.TemplateCreationRQ;
import com.cultuzz.channel.XMLpojo.TemplateCreationRS;
import com.cultuzz.channel.XMLpojo.TemplateDeletionRQ;
import com.cultuzz.channel.XMLpojo.TemplateDeletionRS;
import com.cultuzz.channel.XMLpojo.TemplateDetailsRQ;
import com.cultuzz.channel.XMLpojo.TemplateDetailsRS;
import com.cultuzz.channel.XMLpojo.TemplateEditRQ;
import com.cultuzz.channel.XMLpojo.TemplateEditRS;
import com.cultuzz.channel.XMLpojo.TemplatePreviewRQ;
import com.cultuzz.channel.XMLpojo.TemplatePreviewRS;
import com.cultuzz.channel.XMLpojo.VoucherDetailsRQ;
import com.cultuzz.channel.XMLpojo.VoucherDetailsRS;
import com.cultuzz.channel.XMLpojo.VoucherRedemptionRQ;
import com.cultuzz.channel.XMLpojo.VoucherRedemptionRS;
import com.cultuzz.channel.XMLpojo.VoucherServiceRQ;
import com.cultuzz.channel.XMLpojo.VoucherServiceRS;
import com.cultuzz.channel.helper.DesignTemplateHelper;
import com.cultuzz.channel.helper.HotelDataHelper;
import com.cultuzz.channel.helper.ListOfMemeberMessagesHelper;
import com.cultuzz.channel.helper.ListOfTemplatesHelper;
import com.cultuzz.channel.helper.ListOfTemplatesValidator;
import com.cultuzz.channel.helper.MemberDetailsHelper;
import com.cultuzz.channel.helper.ObjectConfigurationDetailsHelper;
import com.cultuzz.channel.helper.ObjectConfigurationHelper;
import com.cultuzz.channel.helper.ObjectMetaDataHelper;
import com.cultuzz.channel.helper.OfferEndHelper;
import com.cultuzz.channel.helper.OfferHelper;
import com.cultuzz.channel.helper.OfferReviseHelper;
import com.cultuzz.channel.helper.OfferValidator;
import com.cultuzz.channel.helper.PictureAdministrationHelper;
import com.cultuzz.channel.helper.TemplateCreationHelper;
import com.cultuzz.channel.helper.TemplateDeleteHelper;
import com.cultuzz.channel.helper.TemplateDetailsHelper;
import com.cultuzz.channel.helper.TemplateEditHelper;
import com.cultuzz.channel.helper.TemplatePreviewHelper;
import com.cultuzz.channel.helper.VoucherHelper;
import com.cultuzz.channel.helper.VoucherValidator;
import com.cultuzz.channel.helper.impl.AnswerToBuyerMessageHelperImpl;
import com.cultuzz.channel.helper.impl.OfferReviseHelperImpl;
import com.cultuzz.channel.helper.impl.RepushLiveOffersHelperImpl;
import com.cultuzz.channel.marshalling.MarshallingJAXB;
import com.cultuzz.channel.util.CommonValidations;

@RestController
public class CultbayChannelController {

	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CultbayChannelController.class);

	@Autowired
	TemplateCreationHelper templateCreationHelper;

	@Autowired
	TemplatePreviewHelper templatePreviewHelper;

	@Autowired
	TemplateDeleteHelper templateDeleteHelper;

	@Autowired
	TemplateEditHelper templateEditHelper;

	@Autowired
	OfferReviseHelperImpl offerReviseHelperImpl;

	@Autowired
	OfferReviseHelper offerReviseHelper;

	@Autowired
	@Qualifier("offerCreationHelper")
	OfferHelper offerCreationHelper;

	@Autowired
	@Qualifier("offerDetailsHelper")
	OfferHelper offerDetailsHelper;

	@Autowired
	@Qualifier("listOfOffersHelper")
	OfferHelper listOfOffersHelper;


	@Autowired
	@Qualifier("offerDetailsValidator")
	OfferValidator offerDetailsValidator;

	@Autowired
	@Qualifier("listOfOffersValidator")
	OfferValidator listOfOffersValidator;


	@Autowired
	@Qualifier("offerCreationValidator")
	OfferValidator offerCreationValidator;

	@Autowired
	OfferEndHelper offerEndHelper;

	@Autowired
	TemplateDetailsHelper templateDetailsHelper;

	@Autowired
	ListOfTemplatesValidator listOfTemplatesValidator;

	@Autowired
	ListOfTemplatesHelper listOfTemplatesHelper;

	@Autowired
	MarshallingJAXB marshallingJAXB;

	@Autowired
	CommonValidations commonValidations;

	@Autowired
	@Qualifier("voucherDetailsValidator")
	VoucherValidator voucherDetailsValidator;

	@Autowired
	@Qualifier("voucherDetailsHelper")
	VoucherHelper voucherDetailsHelper;

	@Autowired
	@Qualifier("listOfVouchersValidator")
	VoucherValidator listOfVouchersValidator;

	@Autowired
	@Qualifier("listOfVouchersHelper")
	VoucherHelper listOfVouchersHelper;

	@Autowired
	@Qualifier("voucherReddemValidator")
	VoucherValidator voucherReddemValidator;

	@Autowired
	@Qualifier("voucherReddemHelper")
	VoucherHelper voucherReddemHelper;

	@Autowired
	@Qualifier("voucherServiceValidator")
	VoucherValidator voucherServiceValidator;

	@Autowired
	@Qualifier("voucherServiceHelper")
	VoucherHelper voucherServiceHelper;

	@Autowired
	ListOfMemeberMessagesHelper listOfMemeberMessagesHelper;

	@Autowired
	AnswerToBuyerMessageHelperImpl answerToBuyerMessageHelperImpl;

	@Autowired
	MemberDetailsHelper memberDetailsHelper;

	@Autowired
	ObjectConfigurationHelper objectConfigurationHelper;
	
	@Autowired
	ObjectConfigurationDetailsHelper objectConfigurationDetailsHelper;
	
	@Autowired
	RepushLiveOffersHelperImpl repushLiveOffersHelperImpl;

	@Autowired
	DesignTemplateHelper designTemplateHelperImpl;
	// @Autowired
	// Processor processor;
	@Autowired
	ObjectMetaDataHelper objectMetaDataHelperImpl;
	
	@Autowired
	PictureAdministrationHelper pictureAdminHelper;


	@Autowired
	HotelDataHelper hotelDataHelperImpl;


	@RequestMapping(value = "/createTemplate", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
	public TemplateCreationRS processTemplateCreationRQ(
			@RequestBody TemplateCreationRQ templateCreationRQ) {
		long startTime = System.currentTimeMillis();
		
		// TemplateCreationRQ templateCreationRQ=(TemplateCreationRQ)
		// marshallingJAXB.xmlToObj(templateCreationReq);

		TemplateCreationRS templateCreationRS = null;

		templateCreationRS = templateCreationHelper
				.validateTemplateCreationRQ(templateCreationRQ);

		if (templateCreationRS.getAck().equals("Success")) {
			templateCreationRS = templateCreationHelper
					.processTemplateCreationRQ(templateCreationRQ);
		}

		LOGGER.debug("this is Create template handler");

		long endtime = System.currentTimeMillis();
		String request = marshallingJAXB.objToXml(templateCreationRQ);
		String response = marshallingJAXB.objToXml(templateCreationRS);
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		int status = 0;
		if (templateCreationRS.getAck().equals("Success"))
			status = 1;
		else
			status = 0;
		try {
			if (templateCreationRQ.isSetObjectId() && request != null
					&& response != null)
				commonValidations.saveLogData(templateCreationRQ.getObjectId(),
						0, 0, null, request, response, 1, status, processtime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return templateCreationRS;
	}

	@RequestMapping(value = "/deleteTemplate", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
	public TemplateDeletionRS processTemplateDeletionRQ(
			@RequestBody TemplateDeletionRQ templateDeletionRQ) {
		LOGGER.debug("this is Delete template handler");
		long startTime = System.currentTimeMillis();
		TemplateDeletionRS templateDeletionRS = null;

		templateDeletionRS = templateDeleteHelper
				.validateTemplateDeleteRQ(templateDeletionRQ);

		if (templateDeletionRS.getAck().equals("Success"))
			templateDeletionRS = templateDeleteHelper
					.processTemplateDeleteRQ(templateDeletionRQ);

		long endtime = System.currentTimeMillis();
		String request = marshallingJAXB.objToXml(templateDeletionRQ);
		String response = marshallingJAXB.objToXml(templateDeletionRS);
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		int status = 0;
		if (templateDeletionRS.getAck().equals("Success"))
			status = 1;
		else
			status = 0;
		
		try{
		if (templateDeletionRQ.isSetTemplateId()
				&& templateDeletionRQ.isSetObjectId() && request != null
				&& response != null)
			commonValidations.saveLogData(templateDeletionRQ.getObjectId(),
					templateDeletionRQ.getTemplateId(), 0, null, request,
					response, 2, status, processtime);
		}catch(Exception e){
			e.printStackTrace();
		}
		return templateDeletionRS;
	}

	/*
	 * @RequestMapping(value = "/createDesignTemplate", method = {
	 * RequestMethod.POST }, consumes={MediaType.APPLICATION_XML_VALUE} ,
	 * produces = { MediaType.APPLICATION_XML_VALUE}) public
	 * DesignTemplateCreationRS processDesignTemplateCreation(@RequestBody
	 * DesignTemplateCreationRQ designTemplateCreateRQ ) {
	 * 
	 * DesignTemplateCreationRS designTemplateCreationRS=null;
	 * 
	 * 
	 * return designTemplateCreationRS; } }
	 */

	@RequestMapping(value = "/previewTemplate", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
	public TemplatePreviewRS processTemplatePreviewRQ(
			@RequestBody TemplatePreviewRQ templatePreviewRQ) {
		long startTime = System.currentTimeMillis();
		TemplatePreviewRS templatePreviewRS = null;

		LOGGER.debug("this is template preview handler");

		templatePreviewRS = templatePreviewHelper
				.validatePreviewRQ(templatePreviewRQ);

		if (templatePreviewRS.getAck().equals("Success"))
			templatePreviewRS = templatePreviewHelper
					.processPreviewRQ(templatePreviewRQ);

		long endtime = System.currentTimeMillis();

		String request = marshallingJAXB.objToXml(templatePreviewRQ);
		String response = marshallingJAXB.objToXml(templatePreviewRS);
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		int status = 0;
		if (templatePreviewRS.getAck().equals("Success"))
			status = 1;
		else
			status = 0;

		if (status == 1)
			response = "Success";

		try{
		if (templatePreviewRQ.isSetTemplateId()
				&& templatePreviewRQ.isSetObjectId() && request != null
				&& response != null)
			commonValidations.saveLogData(templatePreviewRQ.getObjectId(),
					templatePreviewRQ.getTemplateId(), 0, null, request,
					response, 3, status, processtime);
		}catch(Exception e){
			e.printStackTrace();
		}
		return templatePreviewRS;
	}

	@RequestMapping(value = "/OfferReviseItem", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
	public OfferReviseRS processReviseItem(
			@RequestBody OfferReviseRQ offerReviseRQ) {
		long startTime = System.currentTimeMillis();

		LOGGER.debug("================================Beggining Of Request Processing===================================");

		LOGGER.debug("This is OfferRevise for an Item beggining");

		OfferReviseRS offerReviseRS = null;

		LOGGER.debug("Before Calling validateOfferReviseRequest method... ");
		offerReviseRS = offerReviseHelper
				.validateOfferReviseRequest(offerReviseRQ);
		LOGGER.debug("Ack value from helper is:::" + offerReviseRS.getAck());

		if (offerReviseRS.getAck() != null) {
			LOGGER.debug("Inside NOT NULL for response from helper");
			if (offerReviseRS.getAck().equals("Success")) {

				offerReviseRS = offerReviseHelper
						.processOfferReviseRequest(offerReviseRQ);
			}
			LOGGER.debug("Response From OfferRevise process is:::"
					+ offerReviseRS.getAck());
			LOGGER.debug("This is OfferRevise for an Item ");

			LOGGER.debug("OfferRevise Response value is:::::: " + offerReviseRS);

			LOGGER.debug("================================End Of Request Processing===================================");

			// return offerReviseRS;
		} else {
			offerReviseRS.setAck("Failure");
			// return offerReviseRS;
		}

		long endtime = System.currentTimeMillis();
		String request = marshallingJAXB.objToXml(offerReviseRQ);
		// System.out.println("=========="+request);
		String response = marshallingJAXB.objToXml(offerReviseRS);
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		int status = 0;
		if (offerReviseRS.getAck().equals("Success"))
			status = 1;
		else
			status = 0;

		String ebayitemid = null;

		if (offerReviseRQ.isSetEbayItemId() && !offerReviseRQ.getEbayItemId().isEmpty()) {
			try {
				ebayitemid = offerReviseRQ.getEbayItemId();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (offerReviseRS.getAck().equals("Success")) {
			response = "Success";
		} else
			response = "Failure";

		try{
		if (offerReviseRQ.isSetEbayItemId() && offerReviseRQ.isSetObjectId()
				&& request != null && response != null)
			commonValidations.saveLogData(offerReviseRQ.getObjectId(), 0, 0,
					ebayitemid, request, response, 10, status, processtime);
		}catch(Exception e){
			e.printStackTrace();
		}
		return offerReviseRS;

	}

	@RequestMapping(value = "/OfferDetails", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
	public OfferDetailsRS processOfferDetails(
			@RequestBody OfferDetailsRQ offerDetailsRQ) {

		LOGGER.debug("This is for offerDetails");

		long startTime = System.currentTimeMillis();
		OfferDetailsRS offerDetailsRS = null;

		offerDetailsRS = offerDetailsValidator
				.validateOfferDetails(offerDetailsRQ);

		if (offerDetailsRS.getAck().compareToIgnoreCase("success") == 0) {

			offerDetailsRS = offerDetailsHelper
					.processOfferDetailsHelper(offerDetailsRQ);

		}

		LOGGER.debug("this is forlogging the tables");

		long endtime = System.currentTimeMillis();
		String request = marshallingJAXB.objToXml(offerDetailsRQ);
		String response = marshallingJAXB.objToXml(offerDetailsRS);
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		int status = 0;
		if (offerDetailsRS.getAck().equals("Success"))
			status = 1;
		else
			status = 0;

		try{
		if (offerDetailsRQ.isSetObjectId() && offerDetailsRQ.isSetOfferId()
				&& request != null && response != null)
			commonValidations.saveLogData(offerDetailsRQ.getObjectId(), 0,
					offerDetailsRQ.getOfferId(), null, request, response, 8,
					status, processtime);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return offerDetailsRS;
	}

	/**
	 * This method is required to process End of Offer Notification
	 * 
	 * @param endItemRQ
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	@RequestMapping(value = "/offerEnd", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
	public OfferEndItemRS processEndItem(@RequestBody OfferEndItemRQ endItemRQ) {
		OfferEndItemRS endItemRS = null;
		long startTime = System.currentTimeMillis();
		String request = marshallingJAXB.objToXml(endItemRQ);
		String response = null;
		int status = 0;
		try {
			endItemRS = offerEndHelper.validateOferEndRequest(endItemRQ,
					endItemRS);
			if (null == endItemRS.getErrors() ) {
				response = marshallingJAXB.objToXml(endItemRS);
				status = 1;
			} else {
				// endItemRS =new OfferEndItemRS();
				response = marshallingJAXB.objToXml(endItemRS);
				endItemRS.setAck("Failure");
				status = 0;
			}
			long endtime = System.currentTimeMillis();
			long processtime = commonValidations.calculateProcessTime(
					startTime, endtime);
			int vorlageId = 0;
			int offerId = 0;
			String itemId = null;
			if (request != null && response != null) {
				if (endItemRQ.getTemplateId() != null && !endItemRQ.getTemplateId().equalsIgnoreCase("null")) {
					vorlageId = Integer.parseInt(endItemRQ.getTemplateId());
				}
				if (endItemRQ.getOfferId() != 0) {
					offerId = endItemRQ.getOfferId();
				}
				if (endItemRQ.getItemId() != null && !endItemRQ.getItemId().equalsIgnoreCase("null")) {
					itemId = endItemRQ.getItemId();
				}

				if (endItemRQ.isSetObjectId())
					commonValidations.saveLogData(endItemRQ.getObjectId(),
							vorlageId, offerId, itemId, request, response, 11,
							status, processtime);
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return endItemRS;

	}

	@RequestMapping(value = "/ListOfOffers", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
	public ListOfOffersRS processListOfOffers(
			@RequestBody ListOfOffersRQ listOfOffersRQ) {

		LOGGER.debug("This is for listOfOffers");

		ListOfOffersRS listOfOffersRS = null;
		long startTime = System.currentTimeMillis();

		listOfOffersRS = listOfOffersValidator
				.validateListOfOffers(listOfOffersRQ);

		if (listOfOffersRS.getAck().compareToIgnoreCase("success") == 0) {

			listOfOffersRS = listOfOffersHelper
					.processListOfOffersHelper(listOfOffersRQ);

		}

		LOGGER.debug("this is forlogging the tables");

		long endtime = System.currentTimeMillis();
		String request = marshallingJAXB.objToXml(listOfOffersRQ);
		String response = marshallingJAXB.objToXml(listOfOffersRS);
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		int status = 0;
		if (listOfOffersRS.getAck().equals("Success"))
			status = 1;
		else
			status = 0;

		if (status == 1) {
			response = "Success";
		}

		try{
		if (listOfOffersRQ.isSetObjectId() && request != null && response != null)
			commonValidations.saveLogData(listOfOffersRQ.getObjectId(),
					0, 0, null, request, response,
					9, status, processtime);
		}catch(Exception e){
			e.printStackTrace();
		}
		return listOfOffersRS;

	}

	@RequestMapping(value = "/OfferCreation", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
	public OfferCreationRS processOfferCreation(
			@RequestBody OfferCreationRQ offerCreationRQ) {

		LOGGER.debug("This is for offerCreation");

		OfferCreationRS offerCreationRS = null;
		long startTime = System.currentTimeMillis();

		offerCreationRS = offerCreationValidator
				.validateOfferCreation(offerCreationRQ);

		if (offerCreationRS.getAck().compareToIgnoreCase("success") == 0) {

			LOGGER.debug("completed the offer creation validation");

			offerCreationRS = offerCreationHelper
					.processOfferCreationHelper(offerCreationRQ);

		}
		LOGGER.debug("this is forlogging the tables");

		long endtime = System.currentTimeMillis();
		String request = marshallingJAXB.objToXml(offerCreationRQ);
		String response = marshallingJAXB.objToXml(offerCreationRS);
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		int status = 0;
		if (offerCreationRS.getAck().equals("Success"))
			status = 1;
		else
			status = 0;

		try{
		if (offerCreationRQ.isSetObjectId()
				&& offerCreationRQ.isSetTemplateId() && request != null
				&& response != null)
			commonValidations.saveLogData(offerCreationRQ.getObjectId(),
					offerCreationRQ.getTemplateId(), 0, null, request,
					response, 7, status, processtime);
		}catch(Exception e){
			e.printStackTrace();
		}
		return offerCreationRS;
	}

	// This service is for getting Template details
	@RequestMapping(value = "/detailsTemplate", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
	public TemplateDetailsRS processTemplateDetailsRQ(
			@RequestBody TemplateDetailsRQ templateDetailsRQ) {
		long startTime = System.currentTimeMillis();
		TemplateDetailsRS templateDetailsRS = null;

		LOGGER.debug("this is template details handler");

		templateDetailsRS = templateDetailsHelper
				.validateTemplateDetailsRQ(templateDetailsRQ);

		if (templateDetailsRS.getAck().equals("Success")) {
			templateDetailsRS = templateDetailsHelper
					.processTemplateDetailsRQ(templateDetailsRQ);
		}

		long endtime = System.currentTimeMillis();
		String request = marshallingJAXB.objToXml(templateDetailsRQ);
		String response = marshallingJAXB.objToXml(templateDetailsRS);
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		int status = 0;
		if (templateDetailsRS.getAck().equals("Success"))

			status = 1;
		else
			status = 0;

		if (status == 1) {

			response = "success";
		}

		try{
		if (request != null && response != null
				&& templateDetailsRQ.isSetObjectId())
			commonValidations.saveLogData(templateDetailsRQ.getObjectId(), 0,
					0, null, request, response, 4, status, processtime);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return templateDetailsRS;
	}

	@RequestMapping(value = "/ListOfTemplates", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
	public ListOfTemplatesRS processListOfTemplates(
			@RequestBody ListOfTemplatesRQ listOfTemplatesRQ) {

		LOGGER.debug("This is for listOfTemplates");

		ListOfTemplatesRS listOfTemplatesRS = null;
		long startTime = System.currentTimeMillis();

		listOfTemplatesRS = listOfTemplatesValidator
				.validateListOfTemplates(listOfTemplatesRQ);

		if (listOfTemplatesRS.getAck().compareToIgnoreCase("success") == 0) {

			listOfTemplatesRS = listOfTemplatesHelper
					.processListOfTemplates(listOfTemplatesRQ);

		}

		LOGGER.debug("this is forlogging the tables");

		long endtime = System.currentTimeMillis();
		String request = marshallingJAXB.objToXml(listOfTemplatesRQ);
		String response = marshallingJAXB.objToXml(listOfTemplatesRS);
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		int status = 0;
		if (listOfTemplatesRS.getAck().equals("Success"))
			status = 1;
		else
			status = 0;

		if (status == 1) {

			response = "success";
		}

		try{
		if (listOfTemplatesRQ.isSetObjectId() && request != null
				&& response != null)
			commonValidations.saveLogData(listOfTemplatesRQ.getObjectId(), 0,
					0, null, request, response, 6, status, processtime);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return listOfTemplatesRS;
	}

	@RequestMapping(value = "/editTemplate", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
	public TemplateEditRS processEditTemplate(
			@RequestBody TemplateEditRQ templateEditRQ) {
		long startTime = System.currentTimeMillis();
		TemplateEditRS templateEditRS = null;
		// System.out.println(editrqString);
		// MarshallingUnMarshalling mum=new MarshallingUnMarshalling();
		// TemplateEditRQ
		// templateEditRQ=(TemplateEditRQ)mum.getUnMarshallerObj(editrqString);
		templateEditRS = templateEditHelper
				.validateTemplateEditRQ(templateEditRQ);

		if (templateEditRS.getAck().equals("Success")) {
			templateEditRS = templateEditHelper
					.processTemplateEditRQ(templateEditRQ);
		}
		long endtime = System.currentTimeMillis();

		String request = marshallingJAXB.objToXml(templateEditRQ);
		String response = marshallingJAXB.objToXml(templateEditRS);
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		int status = 0;
		if (templateEditRS.getAck().equals("Success"))
			status = 1;
		else
			status = 0;

		try{
		if (templateEditRQ.isSetTemplateId() && templateEditRQ.isSetObjectId()
				&& request != null && response != null)
			commonValidations.saveLogData(templateEditRQ.getObjectId(),
					templateEditRQ.getTemplateId(), 0, null, request, response,
					5, status, processtime);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return templateEditRS;
	}

	/**
	 * This method is required to process Member Messages
	 * 
	 * @param listOfMemberMessagesRQ
	 * @return
	 */
	@RequestMapping(value = "/listMemberMessages", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
	public ListOfMemberMessagesRS procesMemeberMessages(
			@RequestBody ListOfMemberMessagesRQ listOfMemberMessagesRQ) {
		long startTime = System.currentTimeMillis();
		ListOfMemberMessagesRS listOfMemberMessagesRS = null;
		listOfMemberMessagesRS = listOfMemeberMessagesHelper
				.getMemberMessages(listOfMemberMessagesRQ);

		String request = marshallingJAXB.objToXml(listOfMemberMessagesRQ);
		String response = null;
		int status = 0;

		if (null != listOfMemberMessagesRS) {
			response = marshallingJAXB.objToXml(listOfMemberMessagesRS);
			status = 1;
		} else {
			listOfMemberMessagesRS = new ListOfMemberMessagesRS();
			listOfMemberMessagesRS.setAck("Failure");
			status = 0;
		}
		long endtime = System.currentTimeMillis();
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		
		try{
		if (listOfMemberMessagesRQ.isSetObjectId() && request != null
				&& response != null) {
			commonValidations.saveLogData(listOfMemberMessagesRQ.getObjectId(),
					0, 0, null, request,
					response, 17, status, processtime);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return listOfMemberMessagesRS;
	}

	@RequestMapping(value = "/voucherRedeem", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
	public VoucherRedemptionRS processVoucherRedeemption(
			@RequestBody VoucherRedemptionRQ voucherRedeemptionRQ) {
		long startTime = System.currentTimeMillis();
		LOGGER.debug("This is for voucher redeemption");

		VoucherRedemptionRS voucherRedeemptionRS = null;

		voucherRedeemptionRS = voucherReddemValidator
				.validateVoucherRedeemption(voucherRedeemptionRQ);
		if (voucherRedeemptionRS.getAck().equals("Success")) {
			voucherRedeemptionRS = voucherReddemHelper
					.processVoucherRedeemptionHelper(voucherRedeemptionRQ);
		}

		long endtime = System.currentTimeMillis();
		String request = marshallingJAXB.objToXml(voucherRedeemptionRQ);
		// System.out.println("=========="+request);
		String response = marshallingJAXB.objToXml(voucherRedeemptionRS);
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		int status = 0;
		if (voucherRedeemptionRS.getAck().equals("Success"))
			status = 1;
		else
			status = 0;

		// long ebayitemid=0;
		// try{
		// ebayitemid=Long.parseLong(voucherRedeemptionRQ.getItemId());
		// }catch(Exception e){
		// e.printStackTrace();
		// }

		try{
		if (voucherRedeemptionRQ.isSetObjectId() && voucherRedeemptionRQ.isSetItemId() 
				&& !voucherRedeemptionRQ.getItemId().isEmpty() && request != null
				&& response != null)
			commonValidations.saveLogData(voucherRedeemptionRQ.getObjectId(),
					0, 0, voucherRedeemptionRQ.getItemId(), request, response,
					14, status, processtime);
		}catch(Exception e){
			e.printStackTrace();
		}
		return voucherRedeemptionRS;
	}

	@RequestMapping(value = "/answerMemberMessages", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
	public AnswerBuyerMessageRS answerMemberMessage(
			@RequestBody AnswerBuyerMessageRQ answerBuyerMessageRQ) {

		long startTime = System.currentTimeMillis();
		AnswerBuyerMessageRS answerBuyerMessageRS = null;

		LOGGER.debug("Enetred into answerMemberMessage ");

		LOGGER.debug("Before Calling validateOfferReviseRequest method... ");
		answerBuyerMessageRS = answerToBuyerMessageHelperImpl
				.validateAnswerToBuyerMessage(answerBuyerMessageRQ);
		LOGGER.debug("Ack value from helper is:::"
				+ answerBuyerMessageRS.getAck());

		if (answerBuyerMessageRS.getAck() != null) {
			if (answerBuyerMessageRS.getAck().equals("Success")) {
				LOGGER.debug("Enetered inside Success case fro Process Member Messages");

				answerBuyerMessageRS = answerToBuyerMessageHelperImpl
						.processAnswerToBuyerMessage(answerBuyerMessageRQ);
				LOGGER.debug("Response is " + answerBuyerMessageRS);
			}
		} else {
			answerBuyerMessageRS.setAck("Failure");
			// return answerBuyerMessageRS;
		}
		LOGGER.debug("End Of AnswerBuyerMessage Request Processing");
		// return answerBuyerMessageRS;
		long endtime = System.currentTimeMillis();
		String request = marshallingJAXB.objToXml(answerBuyerMessageRQ);
		// System.out.println("=========="+request);
		String response = marshallingJAXB.objToXml(answerBuyerMessageRS);
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		int status = 0;
		if (answerBuyerMessageRS.getAck().equals("Success"))
			status = 1;
		else
			status = 0;

		// long ebayitemid = 0;
		// if (answerBuyerMessageRQ.isSetEbayItemId()) {
		// try {
		// ebayitemid = Long.parseLong(answerBuyerMessageRQ
		// .getEbayItemId());
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }

		if (answerBuyerMessageRS.getAck().equals("Success")) {
			response = "Success";
		} else
			response = "Failure";

		try{
		if (answerBuyerMessageRQ.isSetObjectId() && request != null
				&& response != null)
			commonValidations.saveLogData(answerBuyerMessageRQ.getObjectId(),
					0, 0, null, request, response, 16, status, processtime);
		}catch(Exception e){
			e.printStackTrace();
		}
		return answerBuyerMessageRS;

	}

	@RequestMapping(value = "/voucherService", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
	public VoucherServiceRS processVoucherService(
			@RequestBody VoucherServiceRQ voucherServiceRQ) {

		LOGGER.debug("This is for voucher service");
		long startTime = System.currentTimeMillis();
		VoucherServiceRS voucherServiceRS = null;

		voucherServiceRS = voucherServiceValidator
				.validateVoucherService(voucherServiceRQ);
		// voucherServiceRS.setAck("Success");
		if (voucherServiceRS.getAck().equals("Success")) {
			voucherServiceRS = voucherServiceHelper
					.processsVoucherServiceHelper(voucherServiceRQ);
		}

		long endtime = System.currentTimeMillis();
		String request = marshallingJAXB.objToXml(voucherServiceRQ);
		// System.out.println("=========="+request);
		String response = marshallingJAXB.objToXml(voucherServiceRS);
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		int status = 0;
		if (voucherServiceRS.getAck().equals("Success"))
			status = 1;
		else
			status = 0;

		// long ebayitemid=0;
		// if(voucherServiceRQ.isSetItemId()){
		// try{
		// ebayitemid=Long.parseLong(voucherServiceRQ.getItemId());
		// }catch(Exception e){
		// e.printStackTrace();
		// }
		// }

		if (voucherServiceRS.getAck().equals("Success")) {
			response = "Success";
		} else
			response = "Failure";

		try{
		if (voucherServiceRQ.isSetObjectId() && voucherServiceRQ.isSetItemId() &&
			!voucherServiceRQ.getItemId().isEmpty()	&& request != null && response != null)
			commonValidations.saveLogData(voucherServiceRQ.getObjectId(), 0, 0,
					voucherServiceRQ.getItemId(), request, response, 15,
					status, processtime);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return voucherServiceRS;
	}

	@RequestMapping(value = "/MemberDetails", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
	public MemberMessageDetailsRS processMemberDetails(
			@RequestBody MemberMessageDetailsRQ memberMessageDetailsRQ) {

		long startTime = System.currentTimeMillis();
		LOGGER.debug("================================Beggining Of Member details Request Processing===================================");

		LOGGER.debug("This is Member Details for an Item beggining");

		MemberMessageDetailsRS memberMessageDetailsRS = null;

		memberMessageDetailsRS = memberDetailsHelper
				.validateMemberMessageDetailsRequest(memberMessageDetailsRQ);

		LOGGER.debug("Ack value from helper is:::"
				+ memberMessageDetailsRS.getAck());
		if (memberMessageDetailsRS.getAck() != null) {
			if (memberMessageDetailsRS.getAck().equals("Success")) {
				memberMessageDetailsRS = memberDetailsHelper
						.processMemberMessageDetailsRequest(memberMessageDetailsRQ);
			}
			LOGGER.debug("This is memberMessageDetails for an Item ");
			LOGGER.debug("memberMessageDetails Response value is:::::: "
					+ memberMessageDetailsRS);
			LOGGER.debug("================================End Of Member details Request Processing===================================");
			// return memberMessageDetailsRS;
		} else {
			memberMessageDetailsRS.setAck("Failure");
			// return memberMessageDetailsRS;
		}

		long endtime = System.currentTimeMillis();
		String request = marshallingJAXB.objToXml(memberMessageDetailsRQ);
		// System.out.println("=========="+request);
		String response = marshallingJAXB.objToXml(memberMessageDetailsRS);
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		int status = 0;
		if (memberMessageDetailsRS.getAck().equals("Success"))
			status = 1;
		else
			status = 0;

		// long ebayitemid = 0;
		// if (memberMessageDetailsRQ.isSetMessageId()) {
		// try {
		// ebayitemid = Long.parseLong(memberMessageDetailsRQ
		// .getEbayItemId());
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }

		if (memberMessageDetailsRS.getAck().equals("Success")) {
			response = "Success";
		} else
			response = "Failure";

		try{
		if (memberMessageDetailsRQ.isSetObjectId() && request != null
				&& response != null)
			commonValidations.saveLogData(memberMessageDetailsRQ.getObjectId(),
					0, 0, null, request, response, 18, status, processtime);
		}catch(Exception e){
			e.printStackTrace();
		}
		return memberMessageDetailsRS;

	}

	@RequestMapping(value = "/ListOfVouchers", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
	public ListOfVouchersRS processListOfVouchers(
			@RequestBody ListOfVouchersRQ listOfVouchersRQ) {

		LOGGER.debug("This is for listOfVouchers");

		ListOfVouchersRS listOfVouchersRS = null;
		long startTime = System.currentTimeMillis();

		listOfVouchersRS = listOfVouchersValidator
				.validateListOfVouchers(listOfVouchersRQ);

		if (listOfVouchersRS.getAck().compareToIgnoreCase("success") == 0) {

			listOfVouchersRS = listOfVouchersHelper
					.processListOfVouchersHelper(listOfVouchersRQ);

		}
		LOGGER.debug("this is for logging");
		long endtime = System.currentTimeMillis();
		String request = marshallingJAXB.objToXml(listOfVouchersRQ);
		// System.out.println("=========="+request);
		String response = marshallingJAXB.objToXml(listOfVouchersRS);
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		int status = 0;
		if (listOfVouchersRS.getAck().equals("Success"))
			status = 1;
		else
			status = 0;

		if (status == 1) {
			response = "Success";
		}
		
		try{
		if (listOfVouchersRQ.isSetObjectId() && request != null && response != null)
			commonValidations.saveLogData(listOfVouchersRQ.getObjectId(), 0, 0,
					null, request, response, 12,
					status, processtime);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return listOfVouchersRS;
	}

	@RequestMapping(value = "/VoucherDetails", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
	public VoucherDetailsRS processVoucherDetails(
			@RequestBody VoucherDetailsRQ voucherDetailsRQ) {

		LOGGER.debug("This is for voucherDetails");

		VoucherDetailsRS voucherDetailsRS = null;
		long startTime = System.currentTimeMillis();

		voucherDetailsRS = voucherDetailsValidator
				.validateVoucherDetails(voucherDetailsRQ);

		if (voucherDetailsRS.getAck().compareToIgnoreCase("success") == 0) {

			voucherDetailsRS = voucherDetailsHelper
					.processVoucherDetailsHelper(voucherDetailsRQ);

		}
		LOGGER.debug("this is for logging");
		long endtime = System.currentTimeMillis();
		String request = marshallingJAXB.objToXml(voucherDetailsRQ);
		// System.out.println("=========="+request);
		String response = marshallingJAXB.objToXml(voucherDetailsRS);
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		int status = 0;
		if (voucherDetailsRS.getAck().equals("Success"))
			status = 1;
		else
			status = 0;

		try{
		if (voucherDetailsRQ.isSetObjectId() && voucherDetailsRQ.isSetItemId() && 
			!voucherDetailsRQ.getItemId().isEmpty()	&& request != null && response != null)
			commonValidations.saveLogData(voucherDetailsRQ.getObjectId(), 0, 0,
					voucherDetailsRQ.getItemId(), request, response, 13,
					status, processtime);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return voucherDetailsRS;
	}

	@RequestMapping(value = "/objectConfiguration", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
	public ObjectConfigurationRS processObjectConfiguration(
			@RequestBody ObjectConfigurationRQ objectConfigurationRQ) {
		
		//System.out.println("DesignTemplateid is"+properties.getDesignTemplateid());
		
		LOGGER.debug("This is for Object Configuration service");
		long startTime = System.currentTimeMillis();
		ObjectConfigurationRS objectConfigurationRS = null;

		objectConfigurationRS = objectConfigurationHelper
				.validateObjectConfigurationRQ(objectConfigurationRQ);

		if (objectConfigurationRS.getAck().equals("Success")) {
			objectConfigurationRS = objectConfigurationHelper
					.processObjectConfigurationRQ(objectConfigurationRQ);
		}

		long endtime = System.currentTimeMillis();
		String request = marshallingJAXB.objToXml(objectConfigurationRQ);
		// System.out.println("=========="+request);
		String response = marshallingJAXB.objToXml(objectConfigurationRS);
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		int status = 0;
		if (objectConfigurationRS.getAck().equals("Success"))
			status = 1;
		else
			status = 0;

		if (objectConfigurationRS.getAck().equals("Success")) {
			response = "Success";
		} else
			response = "Failure";

		try{
		if (objectConfigurationRQ.isSetObjectId() && request != null
				&& response != null)
			commonValidations.saveLogData(objectConfigurationRQ.getObjectId(),
					0, 0, null, request, response, 19, status, processtime);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return objectConfigurationRS;
	}
	
	
	@RequestMapping(value = "/objectConfigurationDetails", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
	public ObjectConfigurationDetailsRS processObjectConfigurationDetails(
			@RequestBody ObjectConfigurationDetailsRQ objectConfigurationDetailsRQ) {
		
		//System.out.println("DesignTemplateid is"+properties.getDesignTemplateid());
		
		LOGGER.debug("This is for ObjectConfigurationsDetails service");
		long startTime = System.currentTimeMillis();
		ObjectConfigurationDetailsRS objectConfigurationDetailsRS = null;

		objectConfigurationDetailsRS = objectConfigurationDetailsHelper
				.validateObjectConfigurationDetailsRq(objectConfigurationDetailsRQ);

		if (objectConfigurationDetailsRS.getAck().equalsIgnoreCase("Success")) {
			objectConfigurationDetailsRS = objectConfigurationDetailsHelper
					.processObjectConfigurationDetailsRq(objectConfigurationDetailsRQ);
		}

		long endtime = System.currentTimeMillis();
		String request = marshallingJAXB.objToXml(objectConfigurationDetailsRQ);
		// System.out.println("=========="+request);
		String response = marshallingJAXB.objToXml(objectConfigurationDetailsRS);
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		int status = 0;
		if (objectConfigurationDetailsRS.getAck().equals("Success"))
			status = 1;
		else
			status = 0;

		if (objectConfigurationDetailsRS.getAck().equals("Success")) {
			response = "Success";
		} else
			response = "Failure";

		try{
		if (objectConfigurationDetailsRQ.isSetObjectId() && request != null
				&& response != null)
			commonValidations.saveLogData(objectConfigurationDetailsRQ.getObjectId(),
					0, 0, null, request, response, 20, status, processtime);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return objectConfigurationDetailsRS;
	}
	
	
	
	
	@RequestMapping(value = "/RepushLiveOffers", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
	public RepushLiveOffersRS processRepushLiveOffersDetails(
			@RequestBody RepushLiveOffersRQ repushLiveOffersRQ) {

		long startTime = System.currentTimeMillis();
		LOGGER.debug("====-==============================Beggining Of repushLiveOffersRS Request Processing===================================");

		LOGGER.debug("This is repushLiveOffersRSfor an Item beggining");

		RepushLiveOffersRS repushLiveOffersRS = null;

		repushLiveOffersRS = repushLiveOffersHelperImpl.validateRepushLiveOfferRequest(repushLiveOffersRQ);

		LOGGER.debug("Ack value from helper is:::"
				+ repushLiveOffersRS.getAck());
		if (repushLiveOffersRS.getAck() != null) {
			if (repushLiveOffersRS.getAck().equals("Success")) {
				repushLiveOffersRS = repushLiveOffersHelperImpl
						.processRepushLiveOfferRequest(repushLiveOffersRQ);
			}
			LOGGER.debug("This is repushLiveOffersRS for an Item ");
			LOGGER.debug("repushLiveOffersRS Response value is:::::: "
					+ repushLiveOffersRS);
			LOGGER.debug("================================End Of Member details Request Processing===================================");
			// return memberMessageDetailsRS;
		} else {
			repushLiveOffersRS.setAck("Failure");
			// return memberMessageDetailsRS;
		}

		
		long endtime = System.currentTimeMillis();
		String request = marshallingJAXB.objToXml(repushLiveOffersRS);
		// System.out.println("=========="+request);
		String response = marshallingJAXB.objToXml(repushLiveOffersRS);
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		int status = 0;
		if (repushLiveOffersRS.getAck().equals("Success"))
			status = 1;
		else
			status = 0;

		// long ebayitemid = 0;
		// if (memberMessageDetailsRQ.isSetMessageId()) {
		// try {
		// ebayitemid = Long.parseLong(memberMessageDetailsRQ
		// .getEbayItemId());
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }

		

		try{
		if (repushLiveOffersRQ.isSetObjectId() && request != null
				&& response != null)
			commonValidations.saveLogData(repushLiveOffersRQ.getObjectId(),
					0, 0, null, request, response, 20, status, processtime);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return repushLiveOffersRS;

	}

	@RequestMapping(value = "/ListOfDesignTemplates", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ListOfDesignTemplatesRS processListOfDesignTemplates(
			@RequestBody ListOfDesignTemplatesRQ listOfDesignTemplatesRQ) {

		LOGGER.debug("This is for listOfDesignTemplates");

		ListOfDesignTemplatesRS listOfDesignTemplatesRS = null;
		long startTime = System.currentTimeMillis();

		listOfDesignTemplatesRS = designTemplateHelperImpl.validateListOfDesignTemplate(listOfDesignTemplatesRQ); 
	
		if (listOfDesignTemplatesRS.getAck().compareToIgnoreCase("success") == 0) {

			LOGGER.debug("completed the listofdesigntemplates validation");

			listOfDesignTemplatesRS = designTemplateHelperImpl
					.processListOfDesigntemplates(listOfDesignTemplatesRQ);

		}
		LOGGER.debug("this is forlogging the tables");

		long endtime = System.currentTimeMillis();
		String request = marshallingJAXB.objToXml(listOfDesignTemplatesRQ);
		String response = marshallingJAXB.objToXml(listOfDesignTemplatesRS);
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		int status = 0;
		if(listOfDesignTemplatesRS!=null){
		if (listOfDesignTemplatesRS.getAck().equals("Success"))
			status = 1;
		else
			status = 0;
		}
		if (status == 1) {
			response = "Success";
		}

		try{
		if (request != null
				&& response != null)
			commonValidations.saveLogData(1,
					0, 0, null, request,
					response, 21, status, processtime);
		}catch(Exception e){
			e.printStackTrace();
		}
		return listOfDesignTemplatesRS;
	}

	@RequestMapping(value = "/ObjectMetaData", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ObjectMetaDataRS procesObjectMetaDataRS(
			@RequestBody ObjectMetaDataRQ objectMetaDataRQ) {

		LOGGER.debug("This is for objectMetaData");

		ObjectMetaDataRS objectMetaDataRS = null;
		long startTime = System.currentTimeMillis();

		objectMetaDataRS = objectMetaDataHelperImpl.validateObjectMetaDataRQ(objectMetaDataRQ); 
	
		if (objectMetaDataRS.getAck().compareToIgnoreCase("success") == 0) {

			LOGGER.debug("completed the objectMetaData validation");

			objectMetaDataRS = objectMetaDataHelperImpl.processObjectMetaDataRQ(objectMetaDataRQ);

		}
		LOGGER.debug("this is forlogging the tables");

		long endtime = System.currentTimeMillis();
		String request = marshallingJAXB.objToXml(objectMetaDataRQ);
		String response = marshallingJAXB.objToXml(objectMetaDataRS);
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		int status = 0;
		if(objectMetaDataRS!=null){
		if (objectMetaDataRS.getAck().equals("Success"))
			status = 1;
		else
			status = 0;
		}
		if (status == 1) {
			response = "Success";
		}

		try{
		if (request != null
				&& response != null)
			commonValidations.saveLogData(1,
					0, 0, null, request,
					response, 21, status, processtime);
		}catch(Exception e){
			e.printStackTrace();
		}
		return objectMetaDataRS;
	}
	
	@RequestMapping(value = "/addpicture", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE}, produces = { MediaType.APPLICATION_XML_VALUE})
	public AddPictureRS addPicture(@RequestBody AddPictureRQ addPicture) {
		
		AddPictureRS addPictureRS=null;
		long startTime = System.currentTimeMillis();
		LOGGER.debug("This is for addPicture");
		if(addPicture!=null)
		  addPictureRS=pictureAdminHelper.validateAddPicture(addPicture);
		
		if(addPictureRS.getAck().equalsIgnoreCase("Success")){
			addPictureRS=pictureAdminHelper.processAddPicture(addPicture);
		}else{
			
		}
		
			try{
			
			long endtime = System.currentTimeMillis();
			String request = marshallingJAXB.objToXml(addPicture);
			String response = marshallingJAXB.objToXml(addPictureRS);
			long processtime = commonValidations.calculateProcessTime(startTime,endtime);
			
			int status=0;
			
			if(addPictureRS.getAck().equalsIgnoreCase("Success"))
				status=1;
			
			if (request != null	&& response != null)
				commonValidations.saveLogData(1,0, 0, null, request,response, 25, status, processtime);
			
			}catch(Exception e){
				//e.printStackTrace();
				LOGGER.debug("Error occured in Addpicture");
			}
		
		
		return addPictureRS;
	}
	
	@RequestMapping(value = "/pictureslist", method = { RequestMethod.POST },consumes = { MediaType.APPLICATION_XML_VALUE},  produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ListOfPicturesRS picturesList(@RequestBody ListOfPicturesRQ pictureListRQ) {

		ListOfPicturesRS listOfPicturesRS=null;
		LOGGER.debug("This is for PictureList");
		listOfPicturesRS=pictureAdminHelper.validatePicturesList(pictureListRQ);
		long startTime = System.currentTimeMillis();
		if(listOfPicturesRS!=null && listOfPicturesRS.getAck().equalsIgnoreCase("Success")){
			listOfPicturesRS=pictureAdminHelper.getAllPictures(pictureListRQ);
		}
		
		try{
			//if(deletePictureRS!=null && deletePictureRQ!=null){
			long endtime = System.currentTimeMillis();
			String request = marshallingJAXB.objToXml(pictureListRQ);
			String response = marshallingJAXB.objToXml(listOfPicturesRS);
			long processtime = commonValidations.calculateProcessTime(startTime,endtime);
			
			int status=0;
			
			if(listOfPicturesRS.getAck().equalsIgnoreCase("Success"))
				status=1;
			
			if (request != null	&& response != null)
				commonValidations.saveLogData(1,0, 0, null, request,response, 26, status, processtime);
			
			
			}catch(Exception e){
				//e.printStackTrace();
				LOGGER.debug("Error occured in Addpicture");
			}
		
		return listOfPicturesRS;
	}
	
	@RequestMapping(value = "/picturedelete", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE},  produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public DeletePictureRS pictureDelete(@RequestBody DeletePictureRQ deletePictureRQ) {

		LOGGER.debug("This is for Picture Delete"+deletePictureRQ);
		//System.out.println("this is for delete imageid"+imageid);
		long startTime = System.currentTimeMillis();
		DeletePictureRS deletePictureRS=null;
		
		
		deletePictureRS=pictureAdminHelper.validateDeletePicture(deletePictureRQ);
		
		if(deletePictureRS.getAck().equalsIgnoreCase("Success")){
			pictureAdminHelper.processDeletePicture(deletePictureRQ);
		}
		
		
		try{
			//if(deletePictureRS!=null && deletePictureRQ!=null){
			long endtime = System.currentTimeMillis();
			String request = marshallingJAXB.objToXml(deletePictureRQ);
			String response = marshallingJAXB.objToXml(deletePictureRS);
			long processtime = commonValidations.calculateProcessTime(startTime,endtime);
			
			int status=0;
			
			if(deletePictureRS.getAck().equalsIgnoreCase("Success"))
				status=1;
			
			if (request != null	&& response != null)
				commonValidations.saveLogData(1,0, 0, null, request,response, 27, status, processtime);
			
			
			}catch(Exception e){
				//e.printStackTrace();
				LOGGER.debug("Error occured in Addpicture");
			}
		
		return deletePictureRS;
	}
	
	
	
	
	@RequestMapping(value = "/setHotelData", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public SetHotelDataRS procesSetHotelData(
			@RequestBody SetHotelDataRQ setHotelDataRQ) {

		LOGGER.debug("This is for setHotelData");

		SetHotelDataRS setHotelDataRS = null;
		long startTime = System.currentTimeMillis();

		setHotelDataRS = hotelDataHelperImpl.validateSetHotelData(setHotelDataRQ); 
	
		if (setHotelDataRS.getAck().compareToIgnoreCase("success") == 0) {

			LOGGER.debug("completed the setHotelData validation");

			setHotelDataRS = hotelDataHelperImpl.processSetHotelData(setHotelDataRQ);

		}
		LOGGER.debug("this is forlogging the tables");

		long endtime = System.currentTimeMillis();
		String request = marshallingJAXB.objToXml(setHotelDataRQ);
		String response = marshallingJAXB.objToXml(setHotelDataRS);
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		int status = 0;
		if(setHotelDataRS!=null){
		if (setHotelDataRS.getAck().equals("Success"))
			status = 1;
		else
			status = 0;
		}
		if (status == 1) {
			response = "Success";
		}

		try{
		if (request != null
				&& response != null)
			commonValidations.saveLogData(1,
					0, 0, null, request,
					response, 21, status, processtime);
		}catch(Exception e){
			e.printStackTrace();
		}
		return setHotelDataRS;
	}

	
	@RequestMapping(value = "/getHotelData", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public GetHotelDataRS procesGetHotelData(
			@RequestBody GetHotelDataRQ getHotelDataRQ) {

		LOGGER.debug("This is for getHotelData");

		GetHotelDataRS getHotelDataRS = null;
		long startTime = System.currentTimeMillis();

		getHotelDataRS = hotelDataHelperImpl.validateGetHotelData(getHotelDataRQ); 
	
		if (getHotelDataRS.getAck().compareToIgnoreCase("success") == 0) {

			LOGGER.debug("completed the getHotelData validation");

			getHotelDataRS = hotelDataHelperImpl.processGetHotelData(getHotelDataRQ);

		}
		LOGGER.debug("this is forlogging the tables");

		long endtime = System.currentTimeMillis();
		String request = marshallingJAXB.objToXml(getHotelDataRQ);
		String response = marshallingJAXB.objToXml(getHotelDataRS);
		long processtime = commonValidations.calculateProcessTime(startTime,
				endtime);
		int status = 0;
		if(getHotelDataRS!=null){
		if (getHotelDataRS.getAck().equals("Success"))
			status = 1;
		else
			status = 0;
		}
		if (status == 1) {
			response = "Success";
		}

		try{
		if (request != null
				&& response != null)
			commonValidations.saveLogData(1,
					0, 0, null, request,
					response, 21, status, processtime);
		}catch(Exception e){
			e.printStackTrace();
		}
		return getHotelDataRS;
	}
}
