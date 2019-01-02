package com.cultuzz.channel.helper.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.DAO.OfferReviseDAO;
import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.DAO.impl.ValidationsDAO;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.XMLpojo.OfferReviseRQ;
import com.cultuzz.channel.XMLpojo.OfferReviseRS;
import com.cultuzz.channel.XMLpojo.PictureDetailsType;
import com.cultuzz.channel.helper.OfferReviseHelper;
import com.cultuzz.channel.util.CommonValidations;

/**
 * This class is required to validate and process the Revise request
 * 
 * @author rohith
 * 
 */
@Component
public class OfferReviseHelperImpl implements OfferReviseHelper {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OfferReviseHelperImpl.class);

	private static final String timeStampRegx = "^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";

	@Autowired
	OfferReviseDAO offerReviseDAO;

	@Autowired
	GetErrorMessagesDAOImpl getErrorMessagesDAOImpl;

	@Autowired
	CommonValidations commonValidations;

	List<String> allowedStrings=new ArrayList<String>();
	
	
	/**
	 * This method is used to process the revise request
	 */
	public OfferReviseRS processOfferReviseRequest(OfferReviseRQ offerReviseRQ) {
		
		LOGGER.debug("Inside proecess Offer Revise Request ");
		OfferReviseRS offerReviseRS = new OfferReviseRS();
		try {
			ErrorsType errorsType = new ErrorsType();
			List<ErrorType> errorTypes = errorsType.getError();

			// int langId = Integer.valueOf(offerReviseRQ.getErrorLang());

			int langId = commonValidations.checkErrorLangCode(offerReviseRQ
					.getErrorLang());
			LOGGER.debug("Language iD is:::" + langId);

			if (langId == 0) {

				LOGGER.debug("Inside langId block if block");
				ErrorType error = new ErrorType();
				error.setErrorCode(1106);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						1106, 2));
				errorTypes.add(error);
			} else {

				LOGGER.debug("Inside langId block");
				Date date = new Date();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String currenTime = simpleDateFormat.format(date.getTime());

				String reviseTime = offerReviseRQ.getReviseTime();

				if (reviseTime.compareTo(currenTime) >= 0) {
					LOGGER.debug("Inside revise time compare....");
					if (offerReviseRQ.isSetEbayItemId()
							&& offerReviseRQ.getEbayItemId() != null) {

						offerReviseRS
								.setTimeStamp(offerReviseRQ.getTimeStamp());
						String itemId = offerReviseRQ.getEbayItemId();
						List<Map<String, Object>> ItemExistsDetails;

						ItemExistsDetails = offerReviseDAO
								.getItemDetails(itemId);

						int id = 0;
						int auctionMasterTypeId = 0;
						double ebaysofortkauf = 0;
						double startpreis = 0;
						double last_price = 0;
						String endDate = null;
						String title = null;
						String subtitle = null;
						String objectId = null;
						int anzahlgebote = 0;
						if (ItemExistsDetails != null
								&& ItemExistsDetails.size() > 0) {
							for (Map<String, Object> entry : ItemExistsDetails) {
								if (null != entry.get("id")) {
									id = Integer.parseInt(entry.get("id")
											.toString());
								}
								if (null != entry.get("AuctionMasterTypeID")) {
									auctionMasterTypeId = Integer
											.parseInt(entry.get(
													"AuctionMasterTypeID")
													.toString());
								}
								LOGGER.debug("ebaysofortkauf value is:::::"
										+ entry.get("ebaysofortkauf"));

								if (entry.get("ebaysofortkauf").toString()
										.isEmpty()) {

								}

								if (!entry.get("startpreis").toString()
										.isEmpty()
										&& entry.get("startpreis") != null
										&& Double.parseDouble(entry.get(
												"startpreis").toString()) > 0.0) {
									LOGGER.debug("---------------");
//									if (null != entry.get("startpreis")
//											&& !entry.get("startpreis")
//													.toString().isEmpty()) {
									
										startpreis = Double.parseDouble(entry
												.get("startpreis").toString());
										LOGGER.debug("Ebaysofortkauf value is inside inner 'if block'  :");
										
//									}
								} else if (!entry.get("ebaysofortkauf")
										.toString().isEmpty()
										&& Double.parseDouble(entry.get(
												"ebaysofortkauf").toString()) > 0.0) {
									LOGGER.debug("Ebaysofortkauf value is inside 'else if block'  :");
									if (null != entry.get("ebaysofortkauf")) {
										ebaysofortkauf = Double
												.parseDouble(entry.get(
														"ebaysofortkauf")
														.toString());
										LOGGER.debug("Ebaysofortkauf value is inside inner else block  :"
												+ ebaysofortkauf);

									}
								}
								LOGGER.debug("Ebaysofortkauf value is OUTSIDE else block  :"
										+ ebaysofortkauf);
								if (null != entry.get("enddate")) {
									endDate = entry.get("enddate").toString();
								}
								/*if (null != entry.get("ebayueberschrift")) {
									title = entry.get("ebayueberschrift")
											.toString();
								}
								if (null != entry.get("untertitel")) {
									subtitle = entry.get("untertitel")
											.toString();
								}*/
								if (null != entry.get("cusebeda_objekt_id")) {
									objectId = entry.get("cusebeda_objekt_id")
											.toString();
								}
								if (null != entry.get("anzahlgebote")) {
									anzahlgebote = Integer.parseInt(entry.get(
											"anzahlgebote").toString());
								}
							}

							LOGGER.debug("Anzahlgebote is::: " + anzahlgebote);
							LOGGER.debug("startpries is::: " + startpreis);

							if (anzahlgebote == 0) {

								LOGGER.debug("Quantity"
										+ offerReviseRQ.getQuantity());
								if (startpreis > 0 && ebaysofortkauf == 0
										&& auctionMasterTypeId == 1) {

									LOGGER.debug("Inside Auktion Type checking...");
									if (offerReviseRQ.isSetQuantity()) {
										if (Integer.parseInt(offerReviseRQ
												.getQuantity()) > 1) {
											LOGGER.debug("Quantity/EbayItemid is of Auktion type ");
											ErrorType error = new ErrorType();
											error.setErrorCode(4139);
											error.setErrorMessage(getErrorMessagesDAOImpl
													.getErrorMessage(4139,
															langId));
											errorTypes.add(error);
										}
									}
									if (offerReviseRQ
											.isSetQuantityRestriction()
											&& Integer.parseInt(offerReviseRQ
													.getQuantityRestriction()) > 0) {

										LOGGER.debug("QuantityRestriction/EbayItemid is not appilicable for Auktion type ");
										ErrorType error = new ErrorType();
										error.setErrorCode(4141);
										error.setErrorMessage(getErrorMessagesDAOImpl
												.getErrorMessage(4141, langId));
										errorTypes.add(error);

									}
								}

								if (errorTypes.size() > 0) {
									offerReviseRS.setAck("Failure");
									offerReviseRS.setErrors(errorsType);
								} else {

									// if ((offerReviseRQ.isSetPrice() &&
									// offerReviseRQ
									// .getPrice() != null)
									// || !(offerReviseRQ.isSetQuantity() &&
									// offerReviseRQ
									// .getQuantity().isEmpty())
									// || (offerReviseRQ
									// .isSetQuantityRestriction() &&
									// offerReviseRQ
									// .getQuantityRestriction() != null)) {
									double price = 0;
									if (offerReviseRQ.isSetPrice()
											&& !(offerReviseRQ.getPrice()
													.isEmpty())) {
										price = Double
												.parseDouble(offerReviseRQ
														.getPrice());
										LOGGER.debug("PRICE:::::" + price);
									} else {
										// price = 0;
									}
									String quantity = null;
									String quantityRestriction = null;
									String time = null;
									if (offerReviseRQ.isSetQuantity()
											&& !(offerReviseRQ.getQuantity()
													.isEmpty())) {
										LOGGER.debug("Quantity is set and not zero");
										 Integer
												.parseInt(offerReviseRQ
														.getQuantity());
										 quantity=offerReviseRQ.getQuantity();
									}

									LOGGER.debug("Quantity value before insertion:::"
											+ quantity);

									if (offerReviseRQ.isSetReviseTime()
											&& offerReviseRQ.getReviseTime() != null) {
										LOGGER.debug("ReviseTime is set and not null");

										time = offerReviseRQ.getReviseTime();
									}
									//System.out.println("before qr checking");
									if (offerReviseRQ
											.isSetQuantityRestriction()
											&& !(offerReviseRQ
													.getQuantityRestriction()
													.isEmpty())) {
										//System.out.println("before qr checking");
										LOGGER.debug("Quantity Restriction is set and not zero");
										quantityRestriction = offerReviseRQ.getQuantityRestriction();
										
										
									}else{
										//System.out.println("first value");
										if(offerReviseRQ.isSetRemoveFields()  && offerReviseRQ.getRemoveFields()!=null && offerReviseRQ.getRemoveFields().isSetRemoveField() && offerReviseRQ.getRemoveFields().getRemoveField()!=null && offerReviseRQ.getRemoveFields().getRemoveField().size()>0){
											//System.out.println("second value");
											if(offerReviseRQ.getRemoveFields().getRemoveField().contains("QuantityRestriction")){
												//System.out.println("third value");
												quantityRestriction="0";
											}
										}
										
										
									}
									
									
									
									if (offerReviseDAO.checkIfAlreadyrevised(
											price, quantity, time,
											quantityRestriction, itemId,
											last_price)) {
										// if (offerReviseDAO
										// .checkReviseDetails(
										// itemId,
										// quantityRestriction,
										// price, time,
										// quantity)) {

										if (auctionMasterTypeId == 1) {
											if (ebaysofortkauf == 0) {
												if (startpreis > 0) {
													last_price = startpreis;
												}
											} else {
												last_price = ebaysofortkauf;
											}

										} else if (auctionMasterTypeId == 4) {
											if (ebaysofortkauf == 0) {
												if (startpreis > 0) {
													last_price = startpreis;
												}
											}
										}

										LOGGER.debug("Value of Quantity Before insertion is"
												+ quantity);
										boolean status = offerReviseDAO
												.checkIfAlreadyrevised(price,
														quantity, time,
														quantityRestriction,
														itemId, last_price);

										if (status) {
											int sourceId = 0;
											if (offerReviseRQ.isSetSourceId()) {
												sourceId = offerReviseRQ.getSourceId();
											}
											String retailPrice=null;
											 retailPrice=offerReviseRQ.getRetailPrice();
																			
											retailPrice = checkRemoveFields(
													offerReviseRQ, retailPrice);
											
											LOGGER.debug("Quantity Restriction is:::"
													+ quantityRestriction);
											int insertedId = offerReviseDAO
													.saveReviseDetails(
															id,
															time,
															last_price,
															price,
															quantity,
															quantityRestriction,
															sourceId,
															offerReviseRQ,retailPrice);
											// if (insertedId > 0) {
											LOGGER.debug("InsertedId for ReviseItemPrice Table"
													+ insertedId);

											/*String reviseTitle = null;
											String reviseSubTitle = null;
											String reviseHtmlDescription = null;
											String reviseOfferText = null;
											String reviseOfferText2 = null;
											String reviseVoucherValidityText = null;
											String reviseAdditionalValidityText = null;*/

											/*if (offerReviseRQ.isSetTitle()) {
												if (!offerReviseRQ.getTitle()
														.isEmpty()
														&& !offerReviseRQ
																.getTitle()
																.equalsIgnoreCase(
																		null)) {
													reviseTitle = offerReviseRQ
															.getTitle();
													LOGGER.debug("ReviseTitle:::"
															+ reviseTitle);
												} else {
													LOGGER.debug("Title is invalid");
													ErrorType error = new ErrorType();
													error.setErrorCode(4123);
													error.setErrorMessage(getErrorMessagesDAOImpl
															.getErrorMessage(
																	4123,
																	langId));
													errorTypes.add(error);
												}
											}*/

										/*	if (offerReviseRQ.isSetSubTitle()) {
												if (!offerReviseRQ
														.getSubTitle()
														.isEmpty()
														&& !offerReviseRQ
																.getSubTitle()
																.equalsIgnoreCase(
																		"null")) {
													reviseSubTitle = offerReviseRQ
															.getSubTitle();
													LOGGER.debug("ReviseSubTitle:::"
															+ reviseSubTitle);

												} else {
													LOGGER.debug("SubTitle is invalid");
													ErrorType error = new ErrorType();
													error.setErrorCode(4124);
													error.setErrorMessage(getErrorMessagesDAOImpl
															.getErrorMessage(
																	4124,
																	langId));
													errorTypes.add(error);
												}
											}*/
											/*if (offerReviseRQ
													.isSetHtmlDescription()
													&& !offerReviseRQ
															.getHtmlDescription()
															.equalsIgnoreCase(
																	"null")) {
												if (!offerReviseRQ
														.getHtmlDescription()
														.isEmpty()) {

													reviseHtmlDescription = offerReviseRQ
															.getHtmlDescription();
													LOGGER.debug("HtmlDescript:::"
															+ reviseHtmlDescription);
												} else {
													LOGGER.debug("HtmlDes is invalid");
													ErrorType error = new ErrorType();
													error.setErrorCode(4125);
													error.setErrorMessage(getErrorMessagesDAOImpl
															.getErrorMessage(
																	4125,
																	langId));
													errorTypes.add(error);

												}
											}*/
/*
											if (offerReviseRQ.isSetOfferText()) {
												if (!offerReviseRQ
														.getOfferText()
														.isEmpty()
														&& !offerReviseRQ
																.getOfferText()
																.equalsIgnoreCase(
																		"null")) {

													reviseOfferText = offerReviseRQ
															.getOfferText();
													LOGGER.debug("OfferText:::"
															+ reviseOfferText);
												} else {
													LOGGER.debug("Offer Text is invalid");
													ErrorType error = new ErrorType();
													error.setErrorCode(4126);
													error.setErrorMessage(getErrorMessagesDAOImpl
															.getErrorMessage(
																	4126,
																	langId));
													errorTypes.add(error);
												}
											}
*/
											/*if (offerReviseRQ
													.isSetOfferAdditionalText()) {
												if (!offerReviseRQ
														.getOfferAdditionalText()
														.isEmpty()
														&& !offerReviseRQ
																.getOfferAdditionalText()
																.equalsIgnoreCase(
																		"null")) {

													reviseOfferText2 = offerReviseRQ
															.getOfferAdditionalText();
													LOGGER.debug("OfferAdditionalText:::"
															+ reviseOfferText2);
												} else {
													LOGGER.debug("Additional offer Text is invalid");
													ErrorType error = new ErrorType();
													error.setErrorCode(4127);
													error.setErrorMessage(getErrorMessagesDAOImpl
															.getErrorMessage(
																	4127,
																	langId));
													errorTypes.add(error);
												}
											}*/

											/*if (offerReviseRQ
													.isSetVoucherText()) {
												if (!offerReviseRQ
														.getVoucherText()
														.isEmpty()
														&& !offerReviseRQ
																.getVoucherText()
																.equalsIgnoreCase(
																		"null")) {
													reviseVoucherValidityText = offerReviseRQ
															.getVoucherText();
													LOGGER.debug("VoucherText:::"
															+ reviseVoucherValidityText);
												} else {
													LOGGER.debug("Invalid Voucher text");
													ErrorType error = new ErrorType();
													error.setErrorCode(4128);
													error.setErrorMessage(getErrorMessagesDAOImpl
															.getErrorMessage(
																	4128,
																	langId));
													errorTypes.add(error);
												}
											}*/

										/*	if (offerReviseRQ
													.isSetVoucherValidityText()) {
												if (!offerReviseRQ
														.getVoucherValidityText()
														.isEmpty()
														&& !offerReviseRQ
																.getVoucherValidityText()
																.equalsIgnoreCase(
																		"null")) {
													reviseAdditionalValidityText = offerReviseRQ
															.getVoucherValidityText();
													LOGGER.debug("VoucherValidityText:::"
															+ reviseAdditionalValidityText);
												} else {
													LOGGER.debug("Invalid Voucher text");
													ErrorType error = new ErrorType();
													error.setErrorCode(4129);
													error.setErrorMessage(getErrorMessagesDAOImpl
															.getErrorMessage(
																	4129,
																	langId));
													errorTypes.add(error);
												}
											}*/

											/*String htmlDescription = null;
											String offerText = null;
											String offerText2 = null;
											String voucherValidityText = null;
											String additionalValidityText = null;*/

											/*List<Map<String, Object>> offerValues = offerReviseDAO
													.getOfferDetails(id);*/

											/*if (null != offerValues) {
												LOGGER.debug("Retreived Offer Details Values for auktionId"
														+ id);
												for (Map<String, Object> entry : offerValues) {
													if (null != entry
															.get("angebotstext")) {
														offerText = entry.get(
																"angebotstext")
																.toString();
													}
													if (null != entry
															.get("angebotstext2")) {
														offerText2 = entry
																.get("angebotstext2")
																.toString();
													}
													if (null != entry
															.get("gutschein_text")) {
														voucherValidityText = entry
																.get("gutschein_text")
																.toString();
													}
													if (null != entry
															.get("gueltigkeit_text")) {
														additionalValidityText = entry
																.get("gueltigkeit_text")
																.toString();
													}
												}

											} else {
												LOGGER.debug("Unable to recieve Offer text Details for auktionId"
														+ id);
											}*/

										/*	htmlDescription = offerReviseDAO
													.getHtmlDescription(id,
															insertedId);
											LOGGER.debug("Html Description"
													+ htmlDescription);*/
											/**
											 * To insert into ReviseItemDetails
											 **/
											/*int insertedReviseId = offerReviseDAO
													.saveInReviseItemDetails(
															id,
															insertedId,
															title,
															subtitle,
															htmlDescription,
															offerText,
															offerText2,
															voucherValidityText,
															additionalValidityText,
															reviseTitle,
															reviseSubTitle,
															reviseHtmlDescription,
															reviseOfferText,
															reviseOfferText2,
															reviseVoucherValidityText,
															reviseAdditionalValidityText);*/
												
											//LOGGER.debug("INSERTEDREVISEID is:::"+insertedReviseId);
											// if (insertedReviseId > 0)
											// {
											/*PictureDetailsType detailsType = new PictureDetailsType();

											List<String> auktionUrl = new ArrayList<String>();*/

											// LOGGER.debug("GalleryURL is For the request:::"+detailsType.getGalleryURL());
											//
											// LOGGER.debug("Picture SET OR NOT:::"
											// + offerReviseRQ
											// .getPictureDetails()
											// .isSetPictureURL());
										/*	if (offerReviseRQ
													.isSetPictureDetails()) {
												LOGGER.debug("Inside isSetPictureDetails method");
												if (offerReviseRQ
														.getPictureDetails() != null) {
													LOGGER.debug("Inside getPictureDetails method");
													detailsType = offerReviseRQ
															.getPictureDetails();
//													if(!detailsType.getPictureURL().get(0).isEmpty()){
//													LOGGER.debug("Inside details method:"+detailsType);
//													LOGGER.debug("PIC URL IS EMPTY OR NOT:::"+ detailsType.getPictureURL().get(0).isEmpty());
//													LOGGER.debug("Picture URL size is:::"
//															+ detailsType
//																	.getPictureURL()
//																	.size());
//													}

													// if
													// (detailsType.getGalleryURL()
													// != null
													// && detailsType
													// .getPictureURL()
													// .size() > 0) {

													if (detailsType
															.isSetPictureURL()) {
														LOGGER.debug("Picture URLS are set for Revision");
														auktionUrl = detailsType
																.getPictureURL();
														if (null != detailsType
																.getPictureURL()) {
															offerReviseDAO
																	.saveAuktionURL(
																			insertedId,
																			auktionUrl,
																			id,
																			objectId);
														} else if (detailsType
																.getPictureURL()
																.toString()
																.equalsIgnoreCase(
																		"null")) {

															LOGGER.debug("Picture URLS inside NULL block");

															offerReviseRS
																	.setAck("Failure");
															ErrorType error = new ErrorType();
															error.setErrorCode(4141);
															error.setErrorMessage(getErrorMessagesDAOImpl
																	.getErrorMessage(
																			4141,
																			langId));
															errorTypes
																	.add(error);

														}

													}
												}
											}*/
											if (errorTypes.size() > 0) {
												offerReviseRS.setAck("Failure");
												offerReviseRS
														.setErrors(errorsType);
											} else {/*

												if (detailsType
														.isSetGalleryURL()) {
													if (!detailsType
															.getGalleryURL()
															.equalsIgnoreCase(
																	"null")) {
														if (!detailsType
																.getGalleryURL()
																.isEmpty()) {
															String galleryUrl = detailsType
																	.getGalleryURL();
															LOGGER.debug("Gallery URl is set for Revision");
															offerReviseDAO
																	.saveGalleryURL(
																			insertedId,
																			galleryUrl,
																			id,
																			objectId);
														} else {
															LOGGER.debug("Gallery URl is not set for Revision");
															offerReviseRS
																	.setAck("Failure");
															ErrorType error = new ErrorType();
															error.setErrorCode(4131);
															error.setErrorMessage(getErrorMessagesDAOImpl
																	.getErrorMessage(
																			4131,
																			langId));
															errorTypes
																	.add(error);
														}
													}
												}
*/
												/**
												 * To Update rdID in
												 * reviseItemPrice for
												 * ReviseItemDetails
												 **/
												/*boolean updateStatus = offerReviseDAO
														.updateIdReviseItemPrice(
																insertedReviseId,
																insertedId);*/

											}
										} else {
											LOGGER.debug("ERROR::: Details already exist For price"
													+ itemId);

											offerReviseRS.setAck("Failure");

											ErrorType error = new ErrorType();
											error.setErrorCode(4105);
											error.setErrorMessage(getErrorMessagesDAOImpl
													.getErrorMessage(4105,
															langId));
											errorTypes.add(error);
										}

										// } else {
										// LOGGER.debug("Revise Details Already Exists"
										// + itemId);
										// offerReviseRS.setAck("Failure");
										//
										// ErrorType error = new ErrorType();
										// error.setErrorCode(4109);
										// error.setErrorMessage(getErrorMessagesDAOImpl
										// .getErrorMessage(4109,
										// langId));
										// errorTypes.add(error);
										//
										// }
									} else {
										LOGGER.debug("Inside ERROR::: Details already exist For price block"
												+ itemId);
										offerReviseRS.setAck("Failure");

										ErrorType error = new ErrorType();
										error.setErrorCode(4105);
										error.setErrorMessage(getErrorMessagesDAOImpl
												.getErrorMessage(4105, langId));
										errorTypes.add(error);
									}

								}
							} else {
								LOGGER.debug("Bids are already Place for this Itemid ::"
										+ itemId);
								offerReviseRS.setAck("Failure");
								ErrorType error = new ErrorType();
								error.setErrorCode(4140);
								error.setErrorMessage(getErrorMessagesDAOImpl
										.getErrorMessage(4140, langId));
								errorTypes.add(error);
							}
						} else {
							LOGGER.debug("EbayItemid specified is invalid");
							offerReviseRS.setAck("Failure");
							ErrorType error = new ErrorType();
							error.setErrorCode(4104);
							error.setErrorMessage(getErrorMessagesDAOImpl
									.getErrorMessage(4104, langId));
							errorTypes.add(error);
						}

					} else {
						LOGGER.debug("EbayItemID is not provided:::"
								+ offerReviseRQ.getEbayItemId());
						offerReviseRS.setAck("Failure");
						ErrorType error = new ErrorType();
						error.setErrorCode(4013);
						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(4013, langId));

						errorTypes.add(error);
					}

				}

			}

			if (errorTypes.size() > 0) {
				offerReviseRS.setAck("Failure");
				offerReviseRS.setErrors(errorsType);
			} else {
				offerReviseRS.setAck("Success");
			}
			// offerReviseRS.setErrors(errorsType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		offerReviseRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		return offerReviseRS;

	}

	private String checkRemoveFields(OfferReviseRQ offerReviseRQ,
			String retailPrice) {
		if(null!=offerReviseRQ.getRemoveFields() && null!=offerReviseRQ.getRemoveFields().getRemoveField() && offerReviseRQ.getRemoveFields().getRemoveField().size()>0){
		allowedStrings=offerReviseRQ.getRemoveFields().getRemoveField();
		if(allowedStrings.contains("RetailPrice")){
			retailPrice="0";
		}
		}
		return retailPrice;
	}

	/**
	 * This method is used to validate the Revise Offer Request
	 */
	@SuppressWarnings("deprecation")
	public OfferReviseRS validateOfferReviseRequest(OfferReviseRQ offerReviseRQ) {

		LOGGER.debug("inside OfferReviseRequest method");

		String authCode = null;
		int sourceId = 0;
		int channelId = 0;

		OfferReviseRS offerReviseRS = null;
		offerReviseRS = new OfferReviseRS();
		ErrorsType errorsType = new ErrorsType();

		List<ErrorType> errorTypes = errorsType.getError();

		List<Map<String, Object>> rows = null;
		
		// sourceId = offerReviseRQ.getSourceId();

		

//		if (rows.size() > 0) {
//			for (Map<String, Object> credentails : rows) {
//				sourceId = Integer.parseInt(credentails.get("source")
//						.toString());
//				channelId = Integer.parseInt(credentails.get("channelId")
//						.toString());
//			}
//		}else{
//			ErrorType error = new ErrorType();
//			error.setErrorCode(1100);
//			error.setErrorMessage(getErrorMessagesDAOImpl
//					.getErrorMessage(1100, 2));
//			errorTypes.add(error);
//
//		}
		/***
		 * LOGGER.debug("AUTHCODE FROM TABLE IS 1:::"+authCode);
		 * LOGGER.debug("SOURCEID FROM TABLE IS 1:::"+sourceId);
		 * LOGGER.debug("CHANNELID FROM TABLE IS 1:::"+channelId);
		 ***/

		int langId = validateLangId(offerReviseRQ, errorTypes);
		LOGGER.debug("LangId value is" + langId);
		if (langId == 0) {
			/*ErrorType error = new ErrorType();
			error.setErrorCode(1106);
			error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1106,
					2));
			errorTypes.add(error);*/
		} else {
			if (offerReviseRQ.isSetTimeStamp()
					&& offerReviseRQ.getTimeStamp() != "") {

				LOGGER.debug("Inside Timestamp validation...");

				if (!this.valiateRegx(offerReviseRQ.getTimeStamp(),
						timeStampRegx)) {

					LOGGER.debug("Invalid TimeStamp");

					ErrorType error = new ErrorType();
					error.setErrorCode(4101);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(4101, langId));
					errorTypes.add(error);
				}
			} else {

				LOGGER.debug("TimeStamp is Missing");
				ErrorType error = new ErrorType();
				error.setErrorCode(4102);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						4102, langId));
				errorTypes.add(error);

			}
			LOGGER.debug("Revise Time validation:::"
					+ offerReviseRQ.getReviseTime());

			// checking reviseTime Valid or Not...

			if (!(offerReviseRQ.isSetPrice() || offerReviseRQ.isSetQuantity()
					|| offerReviseRQ.isSetQuantityRestriction()
					 || offerReviseRQ.isSetRetailPrice() || offerReviseRQ.isSetRemoveFields())) {

				LOGGER.debug("Inside feild validations...");
				ErrorType error = new ErrorType();
				error.setErrorCode(4108);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						4108, langId));
				errorTypes.add(error);
			}
			if (errorTypes.size() > 0) {
				offerReviseRS.setAck("Failure");
				offerReviseRS.setErrors(errorsType);
			} else {
				

				if (offerReviseRQ.getReviseTime() != null) {
					if (!offerReviseRQ.getReviseTime().isEmpty()
							&& offerReviseRQ.isSetReviseTime()) {

						LOGGER.debug("Validating Revise Time..");

						Date date = new Date();
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						String currenTime = simpleDateFormat.format(date
								.getTime());

						LOGGER.debug("Revise Time"
								+ offerReviseRQ.getReviseTime());

						String reviseTime = offerReviseRQ.getReviseTime();
						LOGGER.debug("Current Time" + currenTime);
						// Timestamp time= new Timestamp(time);
						Date d = null;
						try {
							d = simpleDateFormat.parse(reviseTime);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						LOGGER.debug("VCOmapring Dates for Today"
								+ currenTime.compareTo(reviseTime));

						// if(reviseTime.equalsIgnoreCase(simpleDateFormat.toString()))
						// {
						if (this.valiateRegx(reviseTime, timeStampRegx)) {
							if (currenTime.compareTo(reviseTime) >= 0) {
								ErrorType error = new ErrorType();
								error.setErrorCode(4110);
								error.setErrorMessage(getErrorMessagesDAOImpl
										.getErrorMessage(4110, langId));
								errorTypes.add(error);
							}
						} else {
							LOGGER.debug("Please select valid date and time");

							ErrorType error = new ErrorType();
							error.setErrorCode(4111);
							error.setErrorMessage(getErrorMessagesDAOImpl
									.getErrorMessage(4111, langId));
							errorTypes.add(error);
						}

						if (offerReviseDAO.getEndDate(offerReviseRQ
								.getEbayItemId()) != "") {

							String endDate = offerReviseDAO
									.getEndDate(offerReviseRQ.getEbayItemId());
							String revDate = offerReviseRQ.getReviseTime();

							LOGGER.debug("End Date is:::" + endDate);
							LOGGER.debug("Revisee Date is:::" + revDate);

							if (revDate.compareTo(endDate) >= 0) {
								LOGGER.debug("REVISE DATE should be Less Than ENDATE...");

								ErrorType error = new ErrorType();
								error.setErrorCode(4117);
								error.setErrorMessage(getErrorMessagesDAOImpl
										.getErrorMessage(4117, langId));
								errorTypes.add(error);
							}
						}

					} else {
						ErrorType error = new ErrorType();
						error.setErrorCode(4118);
						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(4118, langId));
						errorTypes.add(error);

					}
				} else {

					LOGGER.debug("Inside ReviseTime is nulll block");

					ErrorType error = new ErrorType();
					error.setErrorCode(4118);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(4118, langId));
					errorTypes.add(error);
				}
				
				rows = commonValidations.fetchCredential(offerReviseRQ
						.getAuthenticationCode());

				LOGGER.debug("Size of List is:::" + rows.size());
				
				if (rows.size() > 0) {
					for (Map<String, Object> credentails : rows) {
						sourceId = Integer.parseInt(credentails.get("sourceId")
								.toString());
						channelId = Integer.parseInt(credentails.get("channelId")
								.toString());
					}
				}else{
					ErrorType error = new ErrorType();
					error.setErrorCode(1100);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(1100, langId));
					errorTypes.add(error);

				}

				if (offerReviseRQ.isSetAuthenticationCode()
						&& offerReviseRQ.getAuthenticationCode() != "") {
					
					authCode = offerReviseRQ.getAuthenticationCode();
					LOGGER.debug("AUTHCODE FROM TABLE IS 2:::" + authCode);
					
					LOGGER.debug("Auth Code is:::"
							+ offerReviseRQ.getAuthenticationCode());
					if (!offerReviseRQ.getAuthenticationCode().equals(authCode)) {
						LOGGER.debug("Invalid authentication id");
						ErrorType error = new ErrorType();
						error.setErrorCode(1100);
						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(1100, langId));
						errorTypes.add(error);
					}
				} else {
					LOGGER.debug("AuthCode  is missed");
					ErrorType error = new ErrorType();
					error.setErrorCode(1100);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(1100, langId));
					errorTypes.add(error);
				}

				LOGGER.debug("SOURCEID FROM TABLE IS 2:::" + sourceId);

				if (offerReviseRQ.isSetSourceId()) {
					if (offerReviseRQ.getSourceId() != sourceId) {
						LOGGER.debug("Invalid source id");
						ErrorType error = new ErrorType();
						error.setErrorCode(1101);
						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(1101, langId));
						errorTypes.add(error);

					}
				} else {
					LOGGER.debug("Source id is missing");
					ErrorType error = new ErrorType();
					error.setErrorCode(1101);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(1101, langId));
					errorTypes.add(error);

				}

				LOGGER.debug("CHANNELID FROM TABLE IS 3:::" + channelId);

				LOGGER.debug("::::EbayItemId:::::::::"
						+ offerReviseRQ.getEbayItemId());
				boolean ebayStatus=false;
				if (offerReviseRQ.isSetEbayItemId()
						&& offerReviseRQ.getEbayItemId() != null) {
					if (!offerReviseRQ.getEbayItemId().isEmpty()) {
						LOGGER.debug("*****Inside ItemId Checking*****");
						ebayStatus = offerReviseDAO
								.checkEbayItemId(offerReviseRQ.getEbayItemId());
						LOGGER.debug("ebayStatus is:::" + ebayStatus);
						if (ebayStatus) {
							LOGGER.debug("Inside ebaystatus block...");
							ErrorType error = new ErrorType();
							error.setErrorCode(4104);
							error.setErrorMessage(getErrorMessagesDAOImpl
									.getErrorMessage(4104, langId));
							errorTypes.add(error);
						}
					} else {
						LOGGER.debug("EbayItemId should not be Empty");
						ErrorType error = new ErrorType();
						error.setErrorCode(4103);
						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(4103, langId));
						errorTypes.add(error);

					}
				} else if (offerReviseRQ.getEbayItemId() == null) {

					LOGGER.debug("*****Inside ItemId Checking else condition*****");

					ErrorType error = new ErrorType();
					error.setErrorCode(4103);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(4103, langId));
					errorTypes.add(error);

				}

				if (errorTypes.size() > 0) {
					offerReviseRS.setAck("Failure");
					offerReviseRS.setErrors(errorsType);
				} else {

					LOGGER.debug("Price Vluee is:::::"
							+ offerReviseRQ.getPrice());

					Double price = 0.0;

					if (offerReviseRQ.getPrice() != null) {
						if (!(offerReviseRQ.getPrice().isEmpty())) {
							if (offerReviseRQ.isSetPrice()) {
								if (isDouble(offerReviseRQ.getPrice())) {
									if (offerReviseRQ.getPrice().contains(".")) {
										// price =
										// Double.parseDouble(offerReviseRQ
										// .getPrice());
										LOGGER.debug("PRICE*******"
												+ offerReviseRQ.getPrice());
										if (offerReviseRQ.getPrice().split(
												"\\.")[1].length() <=  2) {

											if (offerReviseRQ.getPrice()
													.length() < 9) {
												LOGGER.debug("Price Vluee is inside block:::::"
														+ price);
												price = Double
														.parseDouble(offerReviseRQ
																.getPrice());
												if (price < 0 || price == 0) {
													LOGGER.debug("Price must greater than 0");
													ErrorType error = new ErrorType();
													error.setErrorCode(4113);
													error.setErrorMessage(getErrorMessagesDAOImpl
															.getErrorMessage(
																	4113,
																	langId));
													errorTypes.add(error);

												} else if (price > 0.0
														&& price < 1.0) {

													LOGGER.debug("***Invalid price***");
													ErrorType error = new ErrorType();
													error.setErrorCode(4136);
													error.setErrorMessage(getErrorMessagesDAOImpl
															.getErrorMessage(
																	4136,
																	langId));
													errorTypes.add(error);

												}
											} else {
												LOGGER.debug("Price value is toooo large:::"
														+ price);
												ErrorType error = new ErrorType();
												error.setErrorCode(4143);
												error.setErrorMessage(getErrorMessagesDAOImpl
														.getErrorMessage(4143,
																langId));
												errorTypes.add(error);
											}

										} else {
											LOGGER.debug("Price Entered Is Not after decimal points..."
													+ price);
											ErrorType error = new ErrorType();
											error.setErrorCode(3156);
											error.setErrorMessage(getErrorMessagesDAOImpl
													.getErrorMessage(3156,
															langId));
											errorTypes.add(error);
										}
									} else {

										if (offerReviseRQ.getPrice().length() < 9) {
											price = Double
													.parseDouble(offerReviseRQ
															.getPrice());
											if (price <= 99999999) {
												LOGGER.debug("Price Vluee is inside block:::::"
														+ price);
												if (price < 0 || price == 0) {
													LOGGER.debug("Price must greater than 0");
													ErrorType error = new ErrorType();
													error.setErrorCode(4113);
													error.setErrorMessage(getErrorMessagesDAOImpl
															.getErrorMessage(
																	4113,
																	langId));
													errorTypes.add(error);

												} else if (price > 0.0
														&& price < 1.0) {

													LOGGER.debug("***Invalid price***");
													ErrorType error = new ErrorType();
													error.setErrorCode(4136);
													error.setErrorMessage(getErrorMessagesDAOImpl
															.getErrorMessage(
																	4136,
																	langId));
													errorTypes.add(error);

												}
											} else {
												LOGGER.debug("Price value is toooo large:::"
														+ price);
												ErrorType error = new ErrorType();
												error.setErrorCode(4143);
												error.setErrorMessage(getErrorMessagesDAOImpl
														.getErrorMessage(4143,
																langId));
												errorTypes.add(error);
											}
										} else {
											LOGGER.debug("Price value is toooo large:::"
													+ price);
											ErrorType error = new ErrorType();
											error.setErrorCode(4143);
											error.setErrorMessage(getErrorMessagesDAOImpl
													.getErrorMessage(4143,
															langId));
											errorTypes.add(error);
										}

									}
								} else {

									LOGGER.debug("Invalid Price Entered...");
									LOGGER.debug("price from XML in else block:::"
											+ offerReviseRQ.getQuantity());
									LOGGER.debug(":::::::");

									LOGGER.debug("Enter valid Price."
											+ offerReviseRQ.getEbayItemId());
									ErrorType error = new ErrorType();
									error.setErrorCode(4132);
									error.setErrorMessage(getErrorMessagesDAOImpl
											.getErrorMessage(4132, langId));
									errorTypes.add(error);

								}
							}
						} else {
							LOGGER.debug("Price Is Invalid:::"
									+ offerReviseRQ.getEbayItemId());
							LOGGER.debug("Price value is toooo large:::");
							ErrorType error = new ErrorType();
							error.setErrorCode(4135);
							error.setErrorMessage(getErrorMessagesDAOImpl
									.getErrorMessage(4135, langId));
							errorTypes.add(error);
						}
					}

					/*************** FOR QUANTITY TESTING ************************/

					LOGGER.debug("Quantity Vluee is:::::"
							+ offerReviseRQ.getQuantity());
					LOGGER.debug("QuantityRestriction Vluee is:::::"
							+ offerReviseRQ.getQuantityRestriction());
					int quantity = 0;
					int quantityRestriction = 0;
					
					if (offerReviseRQ.getQuantity() != null) {
						if (!(offerReviseRQ.getQuantity().isEmpty())) {
							if (offerReviseRQ.isSetQuantity()) {
								LOGGER.debug("Quantity length is:::"
										+ offerReviseRQ.getQuantity().length());
								if (!offerReviseRQ.getQuantity().contains(".")) {
									if (offerReviseRQ.getQuantity().length() <= 6) {
										if (isNumeric(offerReviseRQ
												.getQuantity())) {
											quantity = Integer
													.parseInt(offerReviseRQ
															.getQuantity());
											
											if(quantity==0 && offerReviseDAO.getDuration(offerReviseRQ.getEbayItemId())!=9999 ){
												LOGGER.debug("Quantity if 0 and it shoud be GTC");
												ErrorType error = new ErrorType();
												error.setErrorCode(4149);
												error.setErrorMessage(getErrorMessagesDAOImpl
														.getErrorMessage(4149,
																langId));
												errorTypes.add(error);
											}else if(quantity==0 && offerReviseDAO.getDuration(offerReviseRQ.getEbayItemId())==9999){
												
												if(offerReviseDAO.checkOutOfStockStatus(offerReviseRQ.getEbayItemId())){
												ErrorType error = new ErrorType();
												error.setErrorCode(4154);
												error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(4154,langId));
												errorTypes.add(error);
												}
												
											}
											if (quantity < 0) {
												LOGGER.debug("Quantity must greater than 0");
												ErrorType error = new ErrorType();
												error.setErrorCode(4114);
												error.setErrorMessage(getErrorMessagesDAOImpl
														.getErrorMessage(4114,
																langId));
												errorTypes.add(error);
											}
										} else {
											LOGGER.debug("Invalid quantity Else block for is Numeric...");
											LOGGER.debug("Quantity from XML in else block:::"
													+ quantity);

											LOGGER.debug("Enter valid quantity. quantity should not in decimals:::"
													+ offerReviseRQ
															.getEbayItemId());
											ErrorType error = new ErrorType();
											error.setErrorCode(4120);
											error.setErrorMessage(getErrorMessagesDAOImpl
													.getErrorMessage(4120,
															langId));
											errorTypes.add(error);
										}

									} else {
										LOGGER.debug("Quantity is too large");
										ErrorType error = new ErrorType();
										error.setErrorCode(4122);
										error.setErrorMessage(getErrorMessagesDAOImpl
												.getErrorMessage(4122, langId));
										errorTypes.add(error);
									}
								} else {
									LOGGER.debug("Invalid quantity Entered...");
									LOGGER.debug("Quantity from XML in else block:::"
											+ quantity);

									LOGGER.debug("Enter valid quantity. quantity should not in decimals:::"
											+ offerReviseRQ.getEbayItemId());
									ErrorType error = new ErrorType();
									error.setErrorCode(4120);
									error.setErrorMessage(getErrorMessagesDAOImpl
											.getErrorMessage(4120, langId));
									errorTypes.add(error);
								}

							}
						} else {
							LOGGER.debug("Quantity Invalid,should not be empty:::"
									+ offerReviseRQ.getEbayItemId());
							ErrorType error = new ErrorType();
							error.setErrorCode(4133);
							error.setErrorMessage(getErrorMessagesDAOImpl
									.getErrorMessage(4133, langId));
							errorTypes.add(error);
						}
					}else{
						//here we should set old quantity
						//quantity
						// no need to check this condition allow in ebay
						/*if(ebayStatus)
							quantity=offerReviseDAO.getQuantityAuktion(offerReviseRQ.getEbayItemId());*/
						
						
					}

					if (errorTypes.size() > 0) {
						offerReviseRS.setAck("Failure");
						offerReviseRS.setErrors(errorsType);
					} else {

						LOGGER.debug("QR vlue is for different i/p's are:::"
								+ offerReviseRQ.isSetQuantityRestriction());
						if (offerReviseRQ.isSetQuantityRestriction()) {
							if (!(offerReviseRQ.getQuantityRestriction()
									.isEmpty())) {
								if (isNumeric(offerReviseRQ
										.getQuantityRestriction())) {
									quantityRestriction = Integer
											.parseInt(offerReviseRQ
													.getQuantityRestriction());

									LOGGER.debug("QuantityRestriction from XML is:::"
											+ quantityRestriction);

									if (quantityRestriction < 1
											|| quantityRestriction == 0) {
										LOGGER.debug("QuantityRestriction must greater than 0");
										ErrorType error = new ErrorType();
										error.setErrorCode(4115);
										error.setErrorMessage(getErrorMessagesDAOImpl
												.getErrorMessage(4115, langId));
										errorTypes.add(error);

									}else{
										
										int oldQunatityRestriction=offerReviseDAO.getQuantityRestriction(offerReviseRQ.getEbayItemId());
										if(oldQunatityRestriction!=0 && quantityRestriction==oldQunatityRestriction){
											ErrorType error = new ErrorType();
											error.setErrorCode(4152);
											error.setErrorMessage(getErrorMessagesDAOImpl
													.getErrorMessage(4152, langId));
											errorTypes.add(error);
										}
										
									}
									if (offerReviseRQ.isSetQuantity()
											&& !offerReviseRQ.getQuantity()
													.isEmpty()) {
										if ((quantity < quantityRestriction)) {

											LOGGER.debug("QuantityRestriction must less than quantity");
											ErrorType error = new ErrorType();
											error.setErrorCode(4116);
											error.setErrorMessage(getErrorMessagesDAOImpl
													.getErrorMessage(4116,
															langId));
											errorTypes.add(error);

										}
									}
									
									
								} else {

									LOGGER.debug("Invalid quantity Restriction Entered...");
									LOGGER.debug("Quantity Restriction from XML in else block:::"
											+ offerReviseRQ.getQuantity());
									LOGGER.debug(":::::::");

									LOGGER.debug("Enter valid quantity. quantity restriction should not in decimals:::"
											+ offerReviseRQ.getEbayItemId());
									ErrorType error = new ErrorType();
									error.setErrorCode(4121);
									error.setErrorMessage(getErrorMessagesDAOImpl
											.getErrorMessage(4121, langId));
									errorTypes.add(error);
								}

							} else {

								LOGGER.debug("Enter valid quantity Restriction.Should not be empty"
										+ offerReviseRQ.getEbayItemId());
								ErrorType error = new ErrorType();
								error.setErrorCode(4134);
								error.setErrorMessage(getErrorMessagesDAOImpl
										.getErrorMessage(4134, langId));
								errorTypes.add(error);
							}
						}

						LOGGER.debug("CHANNELID FROM TABLE IS 2:::" + channelId);
						if (offerReviseRQ.isSetChannelId()) {
							if (offerReviseRQ.getChannelId() != channelId) {
								ErrorType error = new ErrorType();
								error.setErrorCode(1102);
								error.setErrorMessage(getErrorMessagesDAOImpl
										.getErrorMessage(1102, langId));
								errorTypes.add(error);
							}
						} else {
							LOGGER.debug("Channel id is mandatory");
							ErrorType error = new ErrorType();
							error.setErrorCode(1102);
							error.setErrorMessage(getErrorMessagesDAOImpl
									.getErrorMessage(1102, langId));
							errorTypes.add(error);

						}

						LOGGER.debug("ObjectId setting is:::"
								+ offerReviseRQ.isSetObjectId());

						if (offerReviseRQ.isSetObjectId()
								&& offerReviseRQ.getObjectId() != 0) {
							LOGGER.debug("OOOOObejectID:::"
									+ offerReviseRQ.getObjectId());
							boolean objectIdStatus = commonValidations
									.checkObjectId(offerReviseRQ.getObjectId());
							if (objectIdStatus == false) {
								LOGGER.debug("Object id is invalid");
								ErrorType error = new ErrorType();
								error.setErrorCode(1103);
								error.setErrorMessage(getErrorMessagesDAOImpl
										.getErrorMessage(1103, langId));
								errorTypes.add(error);
							}
							if (errorTypes.size() > 0) {
								offerReviseRS.setAck("Failure");
								offerReviseRS.setErrors(errorsType);
							} else {
								if (offerReviseRQ.getEbayItemId() != null) {
									if (!(offerReviseDAO.checkObjectWithItemId(
											offerReviseRQ.getObjectId(),
											offerReviseRQ.getEbayItemId()))) {
										LOGGER.debug("Itemid does not belong to objectId");
										ErrorType error = new ErrorType();
										error.setErrorCode(4119);
										error.setErrorMessage(getErrorMessagesDAOImpl
												.getErrorMessage(4119, langId));
										errorTypes.add(error);
									}
								}
							}
						} else {
							LOGGER.debug("Object id is missing");
							ErrorType error = new ErrorType();
							error.setErrorCode(1103);
							error.setErrorMessage(getErrorMessagesDAOImpl
									.getErrorMessage(1103, langId));
							errorTypes.add(error);
						}

						// Title...
						/*if (offerReviseRQ.isSetTitle()) {
							if (offerReviseRQ.getTitle() != null) {
								if (!(offerReviseRQ.getTitle().isEmpty())) {
									if (!(offerReviseRQ.getTitle().length() <= 80)) {
										LOGGER.debug("Offer Title Length should not be more than 80");
										ErrorType error = new ErrorType();
										error.setErrorCode(4137);
										error.setErrorMessage(getErrorMessagesDAOImpl
												.getErrorMessage(4137, langId));
										errorTypes.add(error);
									}
								}
							}
						}*/
						/*if (offerReviseRQ.isSetSubTitle()) {
							if (offerReviseRQ.getSubTitle() != null) {
								if (!(offerReviseRQ.getSubTitle().isEmpty())) {
									if (!(offerReviseRQ.getSubTitle().length() <= 80)) {
										LOGGER.debug("Offer SubTitle Length should not be more than 80");
										ErrorType error = new ErrorType();
										error.setErrorCode(4138);
										error.setErrorMessage(getErrorMessagesDAOImpl
												.getErrorMessage(4138, langId));
										errorTypes.add(error);
									}
								}
							}
						}*/
						int count = 0;
					/*	LOGGER.debug("Picture Details ais set or not:::"
								+ offerReviseRQ.isSetPictureDetails());
						if (offerReviseRQ.isSetPictureDetails()) {

							if (offerReviseRQ.getPictureDetails()
									.isSetGalleryURL()
									&& offerReviseRQ.getPictureDetails()
											.getGalleryURL().trim().isEmpty()) {
								ErrorType error = new ErrorType();
								error.setErrorCode(2108);
								error.setErrorMessage(getErrorMessagesDAOImpl
										.getErrorMessage(2108, langId));
								errorTypes.add(error);

							}
							if (offerReviseRQ.getPictureDetails()
									.getPictureURL().size() > 0) {

								for (String itemPics : offerReviseRQ
										.getPictureDetails().getPictureURL()) {

									if (itemPics.isEmpty()) {
										count++;
									}
								}
								LOGGER.debug("COunt For PicutreURL is:::"
										+ count);
								if (count > 0) {
									ErrorType error = new ErrorType();
									error.setErrorCode(2151);
									error.setErrorMessage(getErrorMessagesDAOImpl
											.getErrorMessage(2151, langId));
									errorTypes.add(error);
								}
							}
							LOGGER.debug("GALLERY URL is SET or Not:::"
									+ offerReviseRQ.getPictureDetails()
											.isSetGalleryURL());
							LOGGER.debug("Item Pics is SET or Not:::"
									+ offerReviseRQ.getPictureDetails()
											.isSetPictureURL());
							if (!offerReviseRQ.getPictureDetails()
									.isSetGalleryURL()
									&& !offerReviseRQ.getPictureDetails()
											.isSetPictureURL()) {
								ErrorType error = new ErrorType();
								error.setErrorCode(2152);
								error.setErrorMessage(getErrorMessagesDAOImpl
										.getErrorMessage(2152, langId));
								errorTypes.add(error);
							}
						}*/
					}
					String alreadyVerrified=null;
					if(offerReviseRQ.isSetRetailPrice()){
					if(!offerReviseRQ.getRetailPrice().isEmpty()){
					if(checkRetailPrice(offerReviseRQ)){
						
						if(offerReviseRQ.getRetailPrice().contains(".")){
							
							if(offerReviseRQ.getRetailPrice().split("\\.")[1].length()>2){
			    				
		  	    				 ErrorType retailError = new ErrorType();
		  	    				retailError.setErrorCode(4144);
		  		  					
		  	    				retailError.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(4144, langId));
		  	    				errorTypes.add(retailError);
		  	    			}
						}
						
						
						if(!(errorTypes.size()>0)){
							if(offerReviseRQ.isSetPrice()){
								
								if(offerReviseRQ.getPrice()!=null && !offerReviseRQ.getPrice().isEmpty() && Double.parseDouble(offerReviseRQ.getPrice())>0){
									alreadyVerrified="verified";
						if(!commonValidations.checkRetailPrice(Double.parseDouble(offerReviseRQ.getPrice()), Double.parseDouble(offerReviseRQ.getRetailPrice()))){
							ErrorType retailError = new ErrorType();
		    				retailError.setErrorCode(1111);
			  				retailError.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1111, langId));
		    				errorTypes.add(retailError);
						}
								}
							}else{
						String startPrice=offerReviseDAO.getPrice(offerReviseRQ.getEbayItemId());
							if(!commonValidations.checkRetailPrice(Double.parseDouble(startPrice), Double.parseDouble(offerReviseRQ.getRetailPrice()))){
								ErrorType retailError = new ErrorType();
			    				retailError.setErrorCode(1111);
				  				retailError.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1111, langId));
			    				errorTypes.add(retailError);
							}
							}
						}
					}else{
						LOGGER.debug("Retail Price is empty"+offerReviseRQ.getRetailPrice());
						ErrorType retailError = new ErrorType();
						retailError.setErrorCode(1113);
							retailError.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1113, langId));
						errorTypes.add(retailError);
					}
				}else{
					LOGGER.debug("Retail Price is empty"+offerReviseRQ.getRetailPrice());
					ErrorType retailError = new ErrorType();
					retailError.setErrorCode(1113);
						retailError.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1113, langId));
						errorTypes.add(retailError);
				}
					}
					if(offerReviseRQ.isSetRemoveFields()){
					if( null!=offerReviseRQ.getRemoveFields() && offerReviseRQ.getRemoveFields().isSetRemoveField()){
					if( null!=offerReviseRQ.getRemoveFields().getRemoveField() && offerReviseRQ.getRemoveFields().getRemoveField().size()>0){
					allowedStrings=offerReviseRQ.getRemoveFields().getRemoveField();
						
						int counter=0;
						int rpCounter=0;
						int rpcCounter=0;
						int qrCounter=0;
						int qrcCounter=0;
						for(String removeFieldName:allowedStrings){
							//System.out.println("remove field string name"+removeFieldName);
							if(removeFieldName.equals("RetailPrice")){
								rpcCounter=rpcCounter+1;
								//System.out.println("this is rpcCounter"+rpcCounter);
								if(rpCounter==0){
									rpCounter=rpCounter++;
								if(!offerReviseRQ.isSetRetailPrice()){
									if(offerReviseRQ.isSetEbayItemId() && !ebayStatus && !offerReviseRQ.getEbayItemId().isEmpty() && offerReviseRQ.getEbayItemId()!=null){
										if(!offerReviseDAO.checkRetailPrice(offerReviseRQ.getEbayItemId())){
										LOGGER.debug("Retail Price is mandatory"+offerReviseRQ.getRetailPrice());
										ErrorType retailError = new ErrorType();
										retailError.setErrorCode(4147);
											retailError.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(4147, langId));
											errorTypes.add(retailError);
									}
									}
									}else{
									LOGGER.debug("Retail Price should not be specified"+offerReviseRQ.getRetailPrice());
									ErrorType retailError = new ErrorType();
									retailError.setErrorCode(4148);
										retailError.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(4148, langId));
										errorTypes.add(retailError);
								}
								}
								
							/*	if(checkremoveFields(offerReviseRQ)){
								else{
									LOGGER.debug("Remove Field is invalid"+offerReviseRQ.getRetailPrice());
									ErrorType retailError = new ErrorType();
									retailError.setErrorCode(4145);
										retailError.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(4145, langId));
										errorTypes.add(retailError);
								}*/
								
								
							}else if(removeFieldName.equals("QuantityRestriction")){
								qrcCounter=qrcCounter+1;
								if(qrCounter==0){
									qrCounter=qrCounter++;
								int oldQunatityRestriction=offerReviseDAO.getQuantityRestriction(offerReviseRQ.getEbayItemId());
								if(oldQunatityRestriction>0){
									
									
									
									
								}else{
									//qr field is not exist
									ErrorType retailError = new ErrorType();
									retailError.setErrorCode(4151);
										retailError.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(4151, langId));
										errorTypes.add(retailError);
								}
								
								if(offerReviseRQ.isSetQuantityRestriction() && !offerReviseRQ.getQuantity().isEmpty()){
									try{
									int gqr= Integer.parseInt(offerReviseRQ.getQuantityRestriction());
									if(gqr>0){
										ErrorType retailError = new ErrorType();
										retailError.setErrorCode(4150);
											retailError.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(4150, langId));
											errorTypes.add(retailError);
									}
									
									}catch(Exception e){
										e.printStackTrace();
									}
								}
								}
								
							}else{
								if(counter==0){
									//System.out.println("this is for invalid string");
									counter=counter+1;
								LOGGER.debug("Remove Field is invalid");
								ErrorType retailError = new ErrorType();
								retailError.setErrorCode(4145);
								retailError.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(4145, langId));
								errorTypes.add(retailError);
								}
							}
							
							
						}
						
						if(rpcCounter>1 || qrcCounter>1){
							LOGGER.debug("Remove Field is repeated");
							ErrorType retailError = new ErrorType();
							retailError.setErrorCode(4153);
							retailError.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(4153, langId));
							errorTypes.add(retailError);
						}
						
									
					
					}
				}else{
					LOGGER.debug("Remove Fields is invalid"+offerReviseRQ.getRetailPrice());
					ErrorType retailError = new ErrorType();
					retailError.setErrorCode(4146);
						retailError.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(4146, langId));
						errorTypes.add(retailError);
				}
				}
					if (!(errorTypes.size() > 0)) {	
						if(offerReviseRQ.isSetPrice()){
							if(alreadyVerrified==null){
							String stprice=offerReviseDAO.getRetailPrice(offerReviseRQ.getEbayItemId());
							if(!commonValidations.checkRetailPrice(Double.parseDouble(offerReviseRQ.getPrice()), Double.parseDouble(stprice))){
								ErrorType retailError = new ErrorType();
			    				retailError.setErrorCode(1114);
				  				retailError.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1114, langId));
			    				errorTypes.add(retailError);
							}	
						}	
							}
						}
				}
			}

		}
		if (errorTypes.size() > 0) {
			offerReviseRS.setAck("Failure");
			offerReviseRS.setErrors(errorsType);
		} else {
			offerReviseRS.setAck("Success");
		}

		// offerReviseRQ.setErrorLang(String.valueOf(langId));
		offerReviseRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		return offerReviseRS;
	}

	private boolean checkRetailPrice(OfferReviseRQ offerReviseRQ) {
		return offerReviseRQ.isSetRetailPrice() && offerReviseRQ.getRetailPrice()!=null && Double.parseDouble(offerReviseRQ.getRetailPrice())>0.0;
	}

	private boolean checkremoveFields(OfferReviseRQ offerReviseRQ) {
		return offerReviseRQ.isSetRemoveFields() && !offerReviseRQ.getRemoveFields().getRemoveField().isEmpty() && offerReviseRQ.getRemoveFields().getRemoveField()!=null;
	}

	// This is method is check the time stamp..

	/**
	 * This method is used to validate the Time stamp format
	 * 
	 * @param expression
	 * @param regx
	 * @return
	 */
	public boolean valiateRegx(String expression, String regx) {
		Pattern p = null;
		Matcher m = null;
		p = Pattern.compile(regx);
		m = p.matcher(expression);
		if (!m.matches()) {
			LOGGER.error("itemid:: {} is is not valid", expression);
			return false;
		}
		return true;
	}

	/**
	 * This method is used to validate the Error langId
	 * 
	 * @param offerReviseRQ
	 * @param errorMessages
	 * @return
	 */

	private int validateLangId(OfferReviseRQ offerReviseRQ,
			List<ErrorType> errorMessages) {

		LOGGER.debug("Inside validateLangId");

		int langId = 0;
		ErrorType error = new ErrorType();
		if (offerReviseRQ.isSetErrorLang()
				&& !(offerReviseRQ.getErrorLang().isEmpty())) {
			if (offerReviseRQ.getErrorLang() != null
					&& offerReviseRQ.getErrorLang().equalsIgnoreCase("en")) {
				langId = getErrorMessagesDAOImpl.getLanguageId(offerReviseRQ
						.getErrorLang());
				if (langId != 0) {
					return langId;
				} else {
					error.setErrorCode(1106);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(1106, 2));
					errorMessages.add(error);
					return langId;
				}
			} else {

				error.setErrorCode(1106);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						1106, 2));
				errorMessages.add(error);

				return langId;
			}
		} else {
			langId = 0;

			LOGGER.debug("Error Language value is not set ::" + langId);

			// LOGGER.debug("Error Language value is not set ::"+
			// offerReviseRQ.getErrorLang());
			// error.setErrorCode(1106);
			// error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1106,
			// langId));
			// errorMessages.add(error);

			return langId;
		}

	}

	public boolean isNumeric(String str) {
		try {
			Integer quantity = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public boolean isDouble(String str) {
		try {
			Double quantity = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

}
