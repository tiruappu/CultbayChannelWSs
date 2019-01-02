
package com.cultuzz.channel.helper.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.DAO.OfferEndDAO;
import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.XMLpojo.OfferEndItemRQ;
import com.cultuzz.channel.XMLpojo.OfferEndItemRS;
import com.cultuzz.channel.helper.OfferEndHelper;
import com.cultuzz.channel.jdbcTemplate.impl.EbayJdbcTemplate;
import com.cultuzz.channel.util.CommonValidations;
import com.cultuzz.channel.util.DateUtil;
import com.cultuzz.utils.PropertiesLoader;

/**
 * This class is used to implement all the validation for END of offer
 * 
 * @author sowmya
 * 
 */
@Component
public class OfferEndHelperImpl implements OfferEndHelper {

	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(OfferEndHelperImpl.class);

	@Autowired
	OfferEndDAO offerEndDAO;

	@Autowired
	GetErrorMessagesDAOImpl getErrorMessagesDAOImpl;
	
	@Autowired
	CommonValidations commonValidations;
	
	@Autowired
	@Qualifier("propertiesRead")
	PropertiesLoader properties;

	/**
	 * This method is used to validate the End of Offer Request
	 * 
	 * @throws IOException
	 */
	public OfferEndItemRS validateOferEndRequest(OfferEndItemRQ endItemRQ,
			OfferEndItemRS endItemRS) {
		LOGGER.debug("Enterd inside validateOferEndRequest ");
		ErrorsType errorsType = new ErrorsType();
		List<ErrorType> errorMessages = errorsType.getError();
		
		String status = null;
		String siteId = null;
		String offerId = null;
		String vorlageId = null;
		String objectId = null;
		String timeStampRegx = "^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";
		try {
			// boolean updatedStatus = false;
			
			endItemRS= new OfferEndItemRS();
			int langId = validateLangId(endItemRQ, errorMessages);
			if (langId != 0) {
				endItemRS.setTimeStamp(DateUtil.convertDateToString(new Date()));
				if (endItemRQ.isSetTimeStamp()
						&& !endItemRQ.getTimeStamp().isEmpty()) {

					if (!this.valiateRegx(endItemRQ.getTimeStamp(),
							timeStampRegx)) {
						LOGGER.debug("TimeStamp is not valid ");
						ErrorType error = new ErrorType();

						error.setErrorCode(1104);

						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(1104, langId));
						errorMessages.add(error);

					}
					 List<Map<String,Object>> listCrendentials=commonValidations.fetchCredential(endItemRQ.getAuthenticationCode());
					 if(listCrendentials.size()>0){
						 int source=0;
						 int channelId=0;
					 for(Map<String, Object> entry:listCrendentials){
						 source=Integer.parseInt(entry.get("sourceId").toString());
						 channelId=Integer.parseInt(entry.get("channelId").toString());
					 }
					/*
					if (validateAuthenticationCode(endItemRQ, errorMessages,
							langId,authCode)) {*/
						if (validateSourceId(endItemRQ, errorMessages, langId,source)) {
							if (validateChannelId(endItemRQ, errorMessages,
									langId,channelId)) {
								if (validateObjectId(endItemRQ, errorMessages,
										langId)) {
									if (endItemRQ.isSetOfferId()
											&& endItemRQ.getOfferId() != 0) {
										if (endItemRQ.isSetItemId()
												&& endItemRQ.getItemId() != null && !endItemRQ.getItemId().isEmpty()) {
											processItemDetails(endItemRQ,
													endItemRS, errorMessages,
													status, siteId, offerId,
													vorlageId, objectId, langId);
										} else if (endItemRQ.isSetOfferId()
												&& endItemRQ.getOfferId() != 0) {
											processOfferDetails(endItemRQ,
													endItemRS, errorMessages,
													status, siteId, vorlageId,
													objectId, langId);
										} else {
											endItemRS.setAck("Failure");
											ErrorType error = new ErrorType();
											LOGGER.debug("Mandatory Fields either offer Id or Item id should be specified");
											error.setErrorCode(5110);
											error.setErrorMessage(getErrorMessagesDAOImpl
													.getErrorMessage(5110,
															langId));
											errorMessages.add(error);
										}
									} else {
										endItemRS.setAck("Failure");
										ErrorType error = new ErrorType();
										LOGGER.debug("Mandatory Fields offer Id should be specified");
										error.setErrorCode(5112);
										error.setErrorMessage(getErrorMessagesDAOImpl
												.getErrorMessage(5112, langId));
										errorMessages.add(error);
									}
								}

							}
						}
					//}
					 }else{
							LOGGER.debug("Fetch Credentials for Source::"
									+ endItemRQ.getSourceId());
							LOGGER.debug("AuthCode is invalid::"
									+ endItemRQ.getSourceId());
							ErrorType error = new ErrorType();
							error.setErrorCode(1100);
							error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1100,
									langId));
							errorMessages.add(error);
					 }
				} else {
					endItemRS.setAck("Failure");
					LOGGER.debug("Time Stamp is not Specified for Itemid ::"
							+ endItemRQ.getItemId() + "Or for Offer id ::"
							+ endItemRQ.getOfferId());
					ErrorType error = new ErrorType();
					error.setErrorCode(1104);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(1104, langId));
					errorMessages.add(error);
				}
				// endItemRS.setErrors(errorsType);
			}
			
			if (errorMessages.size() > 0) {
				endItemRS.setErrors(errorsType);
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return endItemRS;
	}

	/**
	 * This method is used to End the Live or Future offer for OfferId
	 * 
	 * @param endItemRQ
	 * @param endItemRS
	 * @param errorMessages
	 * @param status
	 * @param siteId
	 * @param vorlageId
	 * @param objectId
	 * @param langId
	 * @throws Exception
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private void processOfferDetails(OfferEndItemRQ endItemRQ,
			OfferEndItemRS endItemRS, List<ErrorType> errorMessages,
			String status, String siteId, String vorlageId, String objectId,
			int langId) throws Exception, MalformedURLException, IOException {
		// End for that particular offer
		LOGGER.debug("Entered inside Process Offer Details"
				+ endItemRQ.getOfferId());
		List<Map<String, Object>> offerDetails = offerEndDAO
				.getOfferDetails(endItemRQ.getOfferId());
		String ItemId = null;
		int bidCount=0;
		if (offerDetails != null && offerDetails.size() > 0) {
			for (Map<String, Object> entry : offerDetails) {
				if(null!=entry.get("status")){
				status = entry.get("status").toString();
				}
				if(null!=entry.get("ebayitemid")){
				ItemId = entry.get("ebayitemid").toString();
				}
				if(null!=entry.get("ebaysiteid")){
				siteId = entry.get("ebaysiteid").toString();
				}
				if(null!=entry.get("vorlage_id")){
				vorlageId = entry.get("vorlage_id").toString();
				}
				if(null!=entry.get("cusebeda_objekt_id")){
				objectId = entry.get("cusebeda_objekt_id").toString();
				}
				if(null!=entry.get("anzahlgebote")){
					bidCount=Integer.parseInt(entry.get("anzahlgebote").toString());
				}
			}
			if (objectId.equalsIgnoreCase(String.valueOf(endItemRQ
					.getObjectId()))) {
				if (endItemRQ.isSetOfferStatus()) {
					if (endItemRQ.getOfferStatus().isEmpty()
							|| !status.equalsIgnoreCase(String
									.valueOf(endItemRQ.getOfferStatus()))|| endItemRQ.getOfferStatus().equalsIgnoreCase("null")) {
						endItemRS.setAck("Failure");
						ErrorType error = new ErrorType();
						LOGGER.debug("Status does not belong to specified itemid"
								+ status);
						error.setErrorCode(5106);
						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(5106, langId));
						errorMessages.add(error);
					}
				}

				if (endItemRQ.isSetTemplateId()) {
					if (endItemRQ.getTemplateId().isEmpty()
							|| !vorlageId.equalsIgnoreCase(String
									.valueOf(endItemRQ.getTemplateId())) || endItemRQ.getTemplateId().equalsIgnoreCase("null")) {
						endItemRS.setAck("Failure");
						ErrorType error = new ErrorType();
						LOGGER.debug("Template Id does not belong to specified OfferId"
								+ endItemRQ.getOfferId());
						error.setErrorCode(5109);
						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(5109, langId));
						errorMessages.add(error);
					}
				}

				if (endItemRQ.isSetItemId()) {
					if (endItemRQ.getItemId().isEmpty()
							|| !(endItemRQ.getItemId() == ItemId) || endItemRQ.getItemId().equalsIgnoreCase("null")) {
						endItemRS.setAck("Failure");
						ErrorType error = new ErrorType();
						LOGGER.debug("ItemId Specified does not belong to Offer Id"
								+ endItemRQ.getItemId());
						error.setErrorCode(5113);
						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(5113, langId));
						errorMessages.add(error);
					}
				}
				if(bidCount>0){
					endItemRS.setAck("Failure");
					ErrorType error = new ErrorType();
					LOGGER.debug("Bids are there for this offer"
							+ endItemRQ.getItemId());
					error.setErrorCode(5116);

					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(5116, langId));
					errorMessages.add(error);
				}

				if (!(errorMessages.size() > 0)) {
					LOGGER.debug("" + endItemRQ.getOfferId());
					if (status.equalsIgnoreCase("1")) {
						LOGGER.debug("Processing Live Offer  for Offer"
								+ endItemRQ.getOfferId());
						endItemRQ.setItemId(ItemId);
						int requestObject=endItemRQ.getObjectId();
						
						List<Map<String, Object>> objectDetails = offerEndDAO
								.getShoporCollectionObjektId(endItemRQ
										.getItemId());
						if (objectDetails.size() > 0) {
							int	shoporcollectionObject=0;
							for (Map<String, Object> entry : objectDetails) {
								shoporcollectionObject = Integer.parseInt(entry.get(
										"cusebeda_objekt_id").toString());	
							}
							endItemRQ.setObjectId(shoporcollectionObject);
						}
						//processLiveOfferDetails(endItemRQ, endItemRS,errorMessages, siteId, langId, ItemId);
						
						endItemRQ.setObjectId(requestObject);
					} else if (status.equalsIgnoreCase("0")) {
						LOGGER.debug("Processing Future Offer  for Offer"
								+ endItemRQ.getOfferId());
						processFutureOfferDetails(endItemRQ, endItemRS,
								errorMessages, langId);
					} else if (status.equalsIgnoreCase("2") || status.equalsIgnoreCase("3")) {
						endItemRS.setAck("Failure");
						ErrorType error = new ErrorType();
						LOGGER.debug("Offer is Already Ended" + status
								+ "For offer id" + endItemRQ.getOfferId());
						error.setErrorCode(5103);
						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(5103, langId));
						errorMessages.add(error);
					}
				}

			} else {
				endItemRS.setAck("Failure");
				LOGGER.debug("Offer Id does not belong to object Id specified"
						+ endItemRQ.getOfferId());
				ErrorType error = new ErrorType();
				error.setErrorCode(5105);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						5105, langId));
				errorMessages.add(error);

			}
		} else {
			endItemRS.setAck("Failure");
			ErrorType error = new ErrorType();
			LOGGER.debug("Unable to retrieve Offer Id from DB"
					+ endItemRQ.getOfferId());
			error.setErrorCode(5112);
			error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(5112,
					langId));
			errorMessages.add(error);
		}
	}

	/**
	 * This Method is required to end the Future Offer
	 * 
	 * @param endItemRQ
	 * @param endItemRS
	 * @param errorMessages
	 * @param langId
	 * @throws Exception
	 */
	private void processFutureOfferDetails(OfferEndItemRQ endItemRQ,
			OfferEndItemRS endItemRS, List<ErrorType> errorMessages, int langId)
			throws Exception {
		boolean updatedStatus;
		// Future Offer
		// Update Auction
		// Table
		updatedStatus = offerEndDAO.updateItemStatus(endItemRQ.getItemId(),
				endItemRQ.getOfferId(),endItemRQ.getObjectId());
		if (updatedStatus) {
			LOGGER.debug("Successfully update status for Future offer"
					+ endItemRQ.getOfferId());
			offerEndDAO.updateRepeatStatus(endItemRQ.getOfferId());
			endItemRS.setAck("Success");
		} else {
			endItemRS.setAck("Failure");
			LOGGER.debug("Unable to end offer Future Offer"
					+ endItemRQ.getOfferId());
			ErrorType error = new ErrorType();

			LOGGER.debug("Unable to end for Future Offer"
					+ endItemRQ.getOfferId());
			error.setErrorCode(5102);
			error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(5102,
					langId));
			errorMessages.add(error);

		}
	}

	/**
	 * This method is used to End the Live Offer
	 * 
	 * @param endItemRQ
	 * @param endItemRS
	 * @param errorMessages
	 * @param siteId
	 * @param langId
	 * @param ItemId
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws Exception
	 */
	private void processLiveOfferDetails(OfferEndItemRQ endItemRQ,
			OfferEndItemRS endItemRS, List<ErrorType> errorMessages,
			String siteId, int langId, String ItemId)
			throws MalformedURLException, IOException, Exception {
		// Call to Ebay
		// Service
		LOGGER.debug("Call to CutuzzEbayWss for Offer Id"
				+ endItemRQ.getOfferId());
		
		ErrorType error = new ErrorType();
		
		
		String ack=null;
		 String reason=null;
		 Map<String, String> reasonMap= callEbayWSs(endItemRQ, siteId);

		 for (String key : reasonMap.keySet()) {
			    ack = reasonMap.get("ack");
			    reason=  reasonMap.get("reason");
			}

		if (ack!=null && ack.equalsIgnoreCase("Success")) {
			offerEndDAO.updateRepeatStatus(endItemRQ.getOfferId());
			endItemRS.setAck("Success");
		} else if (ack!=null && ack.contains("Failure")) {
			// LOGGER.debug("Offer is Already Ended For offer id"
			// + endItemRQ.getOfferId());
			// error.setErrorCode(5103);
			LOGGER.debug("Unable to end for Live Offer"
					+ endItemRQ.getOfferId());
			error.setErrorCode(5101);
			if (reason != null) {
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						5101, langId) + ", Reason ::" + reason);
			} else {
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						5101, langId));
			}
			errorMessages.add(error);
		} else {
			LOGGER.debug("Unable to end for Live Offer"
					+ endItemRQ.getOfferId());
			error.setErrorCode(5101);
			if (reason != null) {
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						5101, langId) + ", Reason ::" + reason);
			} else {
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						5101, langId));
			}
			errorMessages.add(error);
		}
	}

	/**
	 * This method is to End the live or future offer for ItemId
	 * 
	 * @param endItemRQ
	 * @param endItemRS
	 * @param errorMessages
	 * @param status
	 * @param siteId
	 * @param offerId
	 * @param vorlageId
	 * @param objectId
	 * @param langId
	 * @throws Exception
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private void processItemDetails(OfferEndItemRQ endItemRQ,
			OfferEndItemRS endItemRS, List<ErrorType> errorMessages,
			String status, String siteId, String offerId, String vorlageId,
			String objectId, int langId) throws Exception,
			MalformedURLException, IOException {
		LOGGER.debug("Processing the Offer with Item id :: "
				+ endItemRQ.getItemId());
		int bidCount=0;
		List<Map<String, Object>> itemDetails = offerEndDAO
				.getItemDetails(endItemRQ.getItemId());
		if (itemDetails != null && itemDetails.size() > 0) {
			for (Map<String, Object> entry : itemDetails) {
				if(null!=entry.get("status")){
				status = entry.get("status").toString();
				}
				if(null!= entry.get("ebaysiteid")){
				siteId = entry.get("ebaysiteid").toString();
				}
				if(null!=entry.get("id")){
				offerId = entry.get("id").toString();
				}
				if(null!=entry.get("vorlage_id")){
				vorlageId = entry.get("vorlage_id").toString();
				}
				if(null!=entry.get("cusebeda_objekt_id")){
				objectId = entry.get("cusebeda_objekt_id").toString();
				}
				if(null!=entry.get("anzahlgebote")){
					bidCount=Integer.parseInt(entry.get("anzahlgebote").toString());
				}
			}
			if (objectId.equalsIgnoreCase(String.valueOf(endItemRQ
					.getObjectId()))) {
				if (endItemRQ.isSetOfferStatus()) {
					if (endItemRQ.getOfferStatus() == null||endItemRQ.getOfferStatus().equalsIgnoreCase("null")
							|| endItemRQ.getOfferStatus().isEmpty()
							|| !status.equalsIgnoreCase(String
									.valueOf(endItemRQ.getOfferStatus()))) {
						endItemRS.setAck("Failure");
						ErrorType error = new ErrorType();
						LOGGER.debug("Status does not belong to specified itemid"
								+ status);
						error.setErrorCode(5106);
						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(5106, langId));
						errorMessages.add(error);
					}
				}
				if (endItemRQ.isSetTemplateId()) {
					if (endItemRQ.getTemplateId() == null ||endItemRQ.getTemplateId().equalsIgnoreCase("null")
							|| endItemRQ.getTemplateId().isEmpty()
							|| !vorlageId.equalsIgnoreCase(String
									.valueOf(endItemRQ.getTemplateId()))) {
						endItemRS.setAck("Failure");
						ErrorType error = new ErrorType();
						LOGGER.debug("Template Id does not belong to specified itemid"
								+ status);
						error.setErrorCode(5108);

						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(5108, langId));
						errorMessages.add(error);
					}
				}
				if (endItemRQ.isSetOfferId()) {
					if (endItemRQ.getOfferId() == 0
							|| !(endItemRQ.getOfferId() == Integer
									.parseInt(offerId))) {
						endItemRS.setAck("Failure");
						ErrorType error = new ErrorType();
						LOGGER.debug("Offer Id is Invalid"
								+ endItemRQ.getItemId());
						error.setErrorCode(5112);

						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(5112, langId));
						errorMessages.add(error);
					}
				}
				if(bidCount>0){
					endItemRS.setAck("Failure");
					ErrorType error = new ErrorType();
					LOGGER.debug("Bids are there for this offer"
							+ endItemRQ.getItemId());
					error.setErrorCode(5116);

					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(5116, langId));
					errorMessages.add(error);
				}
				if (!(errorMessages.size() > 0)) {
					// Live offer
					if (status.equalsIgnoreCase("1")) {
						LOGGER.debug("Processing the Live Offer :: "
								+ endItemRQ.getItemId());
						
						int requestObject=endItemRQ.getObjectId();
						List<Map<String, Object>> objectDetails = offerEndDAO
								.getShoporCollectionObjektId(endItemRQ
										.getItemId());
						if (objectDetails.size() > 0) {
							int	shoporcollectionObject=0;
							for (Map<String, Object> entry : objectDetails) {
								shoporcollectionObject = Integer.parseInt(entry.get(
										"cusebeda_objekt_id").toString());	
							}
							endItemRQ.setObjectId(shoporcollectionObject);
						}
						processLiveOffer(endItemRQ, endItemRS, errorMessages,
								siteId, offerId, langId);
						
						endItemRQ.setObjectId(requestObject);
					} else if (status.equalsIgnoreCase("0")) {
						LOGGER.debug("Processing the FutureOffer Offer :: "
								+ endItemRQ.getItemId());
						//processFutureOffer(endItemRQ, endItemRS, errorMessages,langId);
					} else if (status.equalsIgnoreCase("2")||status.equalsIgnoreCase("3")) {
						endItemRS.setAck("Failure");
						ErrorType error = new ErrorType();
						LOGGER.debug("Offer is Already Ended" + status
								+ "For Item Id id" + endItemRQ.getItemId());
						error.setErrorCode(5103);
						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(5103, langId));
						errorMessages.add(error);
					}
				}

			} else {
				endItemRS.setAck("Failure");
				LOGGER.debug("Item Id does not belong to object Id specified"
						+ endItemRQ.getItemId());
				ErrorType error = new ErrorType();
				error.setErrorCode(5104);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						5104, langId));
				errorMessages.add(error);

			}

		} else {
			endItemRS.setAck("Failure");
			ErrorType error = new ErrorType();
			LOGGER.debug("Unable to retrieve Item Id details from DB"
					+ endItemRQ.getItemId());
			error.setErrorCode(5111);
			error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(5111,
					langId));
			errorMessages.add(error);
		}
	}

	/**
	 * This method is used to process the Future Offer
	 * 
	 * @param endItemRQ
	 * @param endItemRS
	 * @param errorMessages
	 * @param langId
	 * @throws Exception
	 */
	private void processFutureOffer(OfferEndItemRQ endItemRQ,
			OfferEndItemRS endItemRS, List<ErrorType> errorMessages, int langId)
			throws Exception {
		boolean updatedStatus;
		ErrorType error = new ErrorType();
		// Future Offer
		// Update Auction Table
		LOGGER.debug("Updating status for Future Offer" + endItemRQ.getItemId());
		updatedStatus = offerEndDAO.updateItemStatus(endItemRQ.getItemId(),
				endItemRQ.getOfferId(),endItemRQ.getObjectId());
		if (updatedStatus) {
			LOGGER.debug("Successfully update status for Future offer"
					+ endItemRQ.getItemId());
			offerEndDAO.updateRepeatStatus(endItemRQ.getOfferId());
			endItemRS.setAck("Success");
		} else {
			LOGGER.debug("Unable to end for Future Offer"
					+ endItemRQ.getItemId());
			error.setErrorCode(5102);
			error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(5102,
					langId));
			errorMessages.add(error);
		}
	}

	/**
	 * This method is required to process Live offer
	 * 
	 * @param endItemRQ
	 * @param endItemRS
	 * @param errorMessages
	 * @param siteId
	 * @param offerId
	 * @param langId
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws Exception
	 */

	private void processLiveOffer(OfferEndItemRQ endItemRQ,
			OfferEndItemRS endItemRS, List<ErrorType> errorMessages,
			String siteId, String offerId, int langId)
			throws MalformedURLException, IOException, Exception {
		// Insert in new table
		LOGGER.debug("Call to CultuzzWSS to End Live offer for"
				+ endItemRQ.getItemId());
		ErrorType error = new ErrorType();
		String ack=null;
		 String reason=null;
		 System.out.println("before End call");
		 Map<String, String> reasonMap = callEbayWSs(endItemRQ, siteId);
		
		 for (String key : reasonMap.keySet()) {
			    ack = reasonMap.get("ack");
			    reason=  reasonMap.get("reason");
			}

		if (ack!=null && ack.equalsIgnoreCase("Success")) {
			offerEndDAO.updateRepeatStatus(Integer.parseInt(offerId));
			endItemRS.setAck("Success");
		} else if (ack!=null && ack.contains("Failure")) {
			LOGGER.debug("Unable to end for Live Offer"
					+ endItemRQ.getOfferId());
			error.setErrorCode(5101);

			if (reason != null) {
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						5101, langId) + ", Reason ::" + reason);
			} else {
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						5101, langId));
			}
			errorMessages.add(error);
		} else {
			LOGGER.debug("Unable to end for Live Offer" + endItemRQ.getItemId());
			error.setErrorCode(5101);
			if (reason != null) {
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						5101, langId) + ", Reason ::" + reason);
			} else {
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						5101, langId));
			}
			errorMessages.add(error);
		}
	}

	/**
	 * This method is to notify ebay to end the offer
	 * 
	 * @param endItemRQ
	 * @param siteId
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private Map<String, String> callEbayWSs(OfferEndItemRQ endItemRQ, String siteId)
			throws MalformedURLException, IOException {
		String response = null;
		//String value = null;
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		URL url = new URL(properties.getOfferEndURL());
		URLConnection con = url.openConnection();
		// activate the output
		con.setDoOutput(true);
		PrintStream ps = new PrintStream(con.getOutputStream());
		// send input parameters
		ps.print("ebayItemId=" + endItemRQ.getItemId());
		ps.print("&offerId=" + endItemRQ.getOfferId());
		ps.print("&objectId=" + endItemRQ.getObjectId());
		ps.print("&siteId=" + siteId);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String line = null;
		StringBuilder sb = new StringBuilder();
		String ack=null;
		String reason=null;
		Map<String, String> responseMap = new HashMap<String, String>();
		System.out.println("Response End map"+responseMap);
		while ((line = in.readLine()) != null) {
			//System.out.println(line);
			//response=sb.append(line).toString();
			response=line;
			if (response.contains("<Ack>")) {
				int startIndex = response.indexOf("<Ack>") + 5;
				int endIndex = response.lastIndexOf("</Ack>");
				
				ack = response.substring(startIndex, endIndex);
				responseMap.put("ack", ack);
			}
			if(response.contains("<ShortMessage>")){
				 int indexbeg = response.indexOf("<ShortMessage>") + 14;
	            int indexend = response.lastIndexOf("</ShortMessage>");
	            reason = response.substring(indexbeg, indexend);
	            responseMap.put("reason", reason);
			}
		}
		//System.out.println(response);
		ps.close();
		return responseMap;
	}

	/**
	 * This method is required to validate the objectId
	 * 
	 * @param endItemRQ
	 * @return
	 */
	private boolean validateObjectId(OfferEndItemRQ endItemRQ,
			List<ErrorType> errorMessages, int langId) {
		ErrorType error = new ErrorType();
		try {
			if (endItemRQ.isSetObjectId()) {
				LOGGER.debug("ObjectId value is set ::"
						+ endItemRQ.getObjectId());

				if (offerEndDAO.checkForObjectValidity(endItemRQ.getObjectId())) {
					return true;
				} else {
					error.setErrorCode(1103);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(1103, langId));
					errorMessages.add(error);
					return false;
				}

			} else {
				LOGGER.debug("ObjectId value is not set ::"
						+ endItemRQ.getObjectId());
				// errorMessages.add("Object Id is mandatory");
				error.setErrorCode(1103);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						1103, langId));
				errorMessages.add(error);
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * This method is used to validate the channelId
	 * 
	 * @param endItemRQ
	 * @param langId
	 * @return
	 */
	private boolean validateChannelId(OfferEndItemRQ endItemRQ,
			List<ErrorType> errorMessages, int langId,int channelId) {
		ErrorType error = new ErrorType();
		if (endItemRQ.isSetChannelId()) {
			LOGGER.debug("ChannelId is set ::" + endItemRQ.getChannelId());
			if (endItemRQ.getChannelId() == channelId) {
				LOGGER.debug("ChannelId value is ::" + endItemRQ.getSourceId());
				return true;
			} else {
				LOGGER.debug("ChannelId value is invalid ::"
						+ endItemRQ.getSourceId());
				error.setErrorCode(1102);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						1102, langId));
				errorMessages.add(error);
				return false;
			}

		} else {
			LOGGER.debug("ChannelId is not set ::" + endItemRQ.getChannelId());
			error.setErrorCode(1102);
			error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1102,
					langId));
			errorMessages.add(error);
			return false;
		}
	}

	/**
	 * This method id used to validate the AuthenticationCode
	 * 
	 * @param endItemRQ
	 * @param langId
	 * @return
	 *//*
	private boolean validateAuthenticationCode(OfferEndItemRQ endItemRQ,
			List<ErrorType> errorMessages, int langId, String authCode) {
		ErrorType error = new ErrorType();
		if (endItemRQ.isSetAuthenticationCode()) {
			if (endItemRQ.getAuthenticationCode().equalsIgnoreCase(authCode)) {
				LOGGER.debug("AuthenticationCode is ::"
						+ endItemRQ.getAuthenticationCode());
				return true;
			} else {
				error.setErrorCode(1100);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						1100, langId));
				errorMessages.add(error);
				return false;

			}
		} else {
			LOGGER.debug("AuthenticationCode is not set ::"
					+ endItemRQ.getAuthenticationCode());
			error.setErrorCode(1100);
			error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1100,
					langId));
			errorMessages.add(error);
			return false;
		}
	}*/

	/**
	 * This method is used to validate the Source Id
	 * 
	 * @param endItemRQ
	 * @param langId
	 * @return
	 */
	private boolean validateSourceId(OfferEndItemRQ endItemRQ,
			List<ErrorType> errorMessages, int langId, int source) {
		ErrorType error = new ErrorType();
		if (endItemRQ.isSetSourceId()) {
			if (endItemRQ.getSourceId() == source) {
				LOGGER.debug("SourceId value is ::" + endItemRQ.getSourceId());
				return true;
			} else {
				LOGGER.debug("SourceId value is invalid ::"
						+ endItemRQ.getSourceId());
				error.setErrorCode(1101);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						1101, langId));
				errorMessages.add(error);
				return false;
			}

		} else {
			LOGGER.debug("SourceId value is not set ::"
					+ endItemRQ.getSourceId());
			error.setErrorCode(1101);
			error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1101,
					langId));
			errorMessages.add(error);
			return false;
		}
	}

	private int validateLangId(OfferEndItemRQ endItemRQ,
			List<ErrorType> errorMessages) {
		int langId = 0;
		ErrorType error = new ErrorType();
		if (endItemRQ.isSetErrorLang()
				&& !endItemRQ.getErrorLang().equalsIgnoreCase("")) {
			langId = getErrorMessagesDAOImpl.getLanguageId(endItemRQ
					.getErrorLang());
			if (langId != 0) {
				return langId;
			} else {
				error.setErrorCode(1106);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						1106, 2));
				errorMessages.add(error);
				return langId;
			}

		} else {
			LOGGER.debug("Error Language value is not set ::"
					+ endItemRQ.getErrorLang());
			error.setErrorCode(1106);
			error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1106,
					2));
			errorMessages.add(error);

			return langId;
		}

	}

	/**
	 * This method used to validate the values with regular expressions
	 * 
	 * @param regx
	 *            ,expression
	 * @return true/false
	 */
	public boolean valiateRegx(String expression, String regx) {
		Pattern p = null;
		Matcher m = null;
		p = Pattern.compile(regx);
		m = p.matcher(expression);
		if (!m.matches()) {
			LOGGER.error("Invalid time stamp", expression);
			return false;
		}
		return true;
	}

}
