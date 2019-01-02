package com.cultuzz.channel.helper.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.DAO.ListOfMemberMessagesDAO;
import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.XMLpojo.ListOfMemberMessagesRQ;
import com.cultuzz.channel.XMLpojo.ListOfMemberMessagesRQ.Period;
import com.cultuzz.channel.XMLpojo.ListOfMemberMessagesRQ.Range;
import com.cultuzz.channel.XMLpojo.ListOfMemberMessagesRS;
import com.cultuzz.channel.XMLpojo.ListOfMemberMessagesRS.MemberMessages;
import com.cultuzz.channel.XMLpojo.ListOfMemberMessagesRS.MemberMessages.MemberMessage;
import com.cultuzz.channel.helper.ListOfMemeberMessagesHelper;
import com.cultuzz.channel.util.CommonValidations;
import com.cultuzz.channel.util.DateUtil;

/**
 * This class is required to process Member Messages
 * 
 * @author sowmya
 * 
 */
@Component
public class ListOfMemeberMessagesHelperImpl implements
		ListOfMemeberMessagesHelper {

	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(ListOfMemeberMessagesHelperImpl.class);
	@Autowired
	ListOfMemberMessagesDAO listOfMemberMessagesDAO;

	@Autowired
	GetErrorMessagesDAOImpl getErrorMessagesDAOImpl;

	@Autowired
	CommonValidations commonValidation;

	/**
	 * This method is used to getMemberMessages for objectId or ItemId
	 */
	public ListOfMemberMessagesRS getMemberMessages(
			ListOfMemberMessagesRQ listOfMemberMessagesRQ) {
		// TODO Auto-generated method stub

		ListOfMemberMessagesRS listOfMemberMessagesRS = null;
		String periodFrom = null;
		String periodTo = null;
		String upperLimit = null;
		String lowerLimit = null;
		try {
			listOfMemberMessagesRS = new ListOfMemberMessagesRS();

			listOfMemberMessagesRS.setTimeStamp(DateUtil
					.convertDateToString(new Date()));
			MemberMessage memberData = null;
			String status = null;
			int langId = 0;
			String timeStampRegx = "^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";
			ErrorsType errorsType = new ErrorsType();
			List<ErrorType> errorMessages = errorsType.getError();
			if (listOfMemberMessagesRQ.isSetErrorLang()) {
				langId = validateLangId(listOfMemberMessagesRQ, errorMessages);

				if (langId != 0) {

					if (listOfMemberMessagesRQ.isSetTimeStamp()
							&& !listOfMemberMessagesRQ.getTimeStamp().isEmpty()) {

						if (!commonValidation
								.checkTimeStamp(listOfMemberMessagesRQ
										.getTimeStamp())) {
							LOGGER.debug("TimeStamp is not valid ");
							listOfMemberMessagesRS.setAck("Failure");
							ErrorType error = new ErrorType();

							error.setErrorCode(1104);

							error.setErrorMessage(getErrorMessagesDAOImpl
									.getErrorMessage(1104, langId));
							errorMessages.add(error);

						}
						
						List<Map<String,Object>> listCrendentials=commonValidation.fetchCredential(listOfMemberMessagesRQ.getAuthenticationCode());
						 if(listCrendentials.size()>0){
							 String authCode=null;
							 int channelId=0;
							 int sourceCode=0;
						 for(Map<String, Object> entry:listCrendentials){
							 sourceCode=Integer.parseInt(entry.get("sourceId").toString());
							 channelId=Integer.parseInt(entry.get("channelId").toString());
						 }
						if (!(errorMessages.size() > 0)) {
							/*if (validateAuthenticationCode(
									listOfMemberMessagesRQ, errorMessages,
									langId,authCode)) {*/
								if (validateSourceId(listOfMemberMessagesRQ,
										errorMessages, langId,sourceCode)) {
									if (validateChannelId(
											listOfMemberMessagesRQ,
											errorMessages, langId,channelId)) {
										if (validateObjectId(
												listOfMemberMessagesRQ,
												errorMessages, langId)) {
											if (null != listOfMemberMessagesRQ
													.getPeriod()) {

												Period period = listOfMemberMessagesRQ
														.getPeriod();
												if (null != period.getFrom()
														&& !period.getFrom()
																.isEmpty()
														&& null != period
																.getTo()
														&& !period.getTo()
																.isEmpty()) {
													if (!commonValidation
															.checkTimeStamp(period
																	.getFrom())
															|| !commonValidation
																	.checkTimeStamp(period
																			.getTo())) {
														LOGGER.debug("TimeStamp is not valid ");
														listOfMemberMessagesRS
																.setAck("Failure");
														ErrorType error = new ErrorType();
														error.setErrorCode(8104);
														error.setErrorMessage(getErrorMessagesDAOImpl
																.getErrorMessage(
																		8104,
																		langId));
														errorMessages
																.add(error);

													} else {
														periodFrom = period
																.getFrom();
														periodTo = period
																.getTo();
													}
												} else {
													listOfMemberMessagesRS
															.setAck("Failure");
													ErrorType error = new ErrorType();
													error.setErrorCode(8104);
													error.setErrorMessage(getErrorMessagesDAOImpl
															.getErrorMessage(
																	8104,
																	langId));
													errorMessages.add(error);
												}
											}
											if (null != listOfMemberMessagesRQ
													.getRange()
													&& null != listOfMemberMessagesRQ
															.getRange()
															.getUpperLimit()
													&& !listOfMemberMessagesRQ
															.getRange()
															.getUpperLimit()
															.isEmpty()
													&& null != listOfMemberMessagesRQ
															.getRange()
															.getLowerLimit()
													&& !listOfMemberMessagesRQ
															.getRange()
															.getLowerLimit()
															.isEmpty()) {
												Range range = listOfMemberMessagesRQ
														.getRange();
												
												upperLimit = range
														.getUpperLimit();
												lowerLimit = range
														.getLowerLimit();
												if(!isNumeric(upperLimit) ){
													ErrorType error = new ErrorType();
													error.setErrorCode(8107);
													error.setErrorMessage(getErrorMessagesDAOImpl
															.getErrorMessage(8107,
																	langId));
													errorMessages.add(error);
												}
												
													if(!isNumeric(lowerLimit)){
													ErrorType error = new ErrorType();
													error.setErrorCode(8108);
													error.setErrorMessage(getErrorMessagesDAOImpl
															.getErrorMessage(8108,
																	langId));
													errorMessages.add(error);
												}
													
												if(isNumeric(upperLimit)){ 
														if(isNumeric(lowerLimit)){
												if(upperLimit.contains(".") || !(Integer.parseInt(upperLimit)>0) ){
													ErrorType error = new ErrorType();
													error.setErrorCode(8107);
													error.setErrorMessage(getErrorMessagesDAOImpl
															.getErrorMessage(8107,
																	langId));
													errorMessages.add(error);
												}
												
												if(lowerLimit.contains(".") || !(Integer.parseInt(lowerLimit)>=0) ){
													ErrorType error = new ErrorType();
													error.setErrorCode(8108);
													error.setErrorMessage(getErrorMessagesDAOImpl
															.getErrorMessage(8108,
																	langId));
													errorMessages.add(error);
												}
												 if(!(Integer.parseInt(upperLimit)>(Integer.parseInt(lowerLimit)))){
	                                                    listOfMemberMessagesRS
	                                                    .setAck("Failure");
	                                            ErrorType error = new ErrorType();
	                                            error.setErrorCode(8109);
	                                            error.setErrorMessage(getErrorMessagesDAOImpl
	                                                    .getErrorMessage(8109,
	                                                            langId));
	                                            errorMessages.add(error);
	                                                }
											}/*else{
												ErrorType error = new ErrorType();
												error.setErrorCode(8110);
												error.setErrorMessage(getErrorMessagesDAOImpl
														.getErrorMessage(8110,
																langId));
												errorMessages.add(error);
											}*/
										}/*else{
											ErrorType error = new ErrorType();
											error.setErrorCode(8110);
											error.setErrorMessage(getErrorMessagesDAOImpl
													.getErrorMessage(8110,
															langId));
											errorMessages.add(error);
										}*/
											} else {
												listOfMemberMessagesRS
														.setAck("Failure");
												ErrorType error = new ErrorType();
												error.setErrorCode(8103);
												error.setErrorMessage(getErrorMessagesDAOImpl
														.getErrorMessage(8103,
																langId));
												errorMessages.add(error);
											}
										
											if (null != listOfMemberMessagesRQ
													.getStatus()) {
												status = listOfMemberMessagesRQ
														.getStatus();
												if (!listOfMemberMessagesRQ
														.getStatus().isEmpty()) {
													if(isNumeric(listOfMemberMessagesRQ
															.getStatus())){
														if(status.equalsIgnoreCase("1") || status.equalsIgnoreCase("0")){
														if(listOfMemberMessagesRQ
																.getStatus().contains(".") || !(Integer.parseInt(listOfMemberMessagesRQ
																		.getStatus())>=0)){
													listOfMemberMessagesRS
															.setAck("Failure");
													ErrorType error = new ErrorType();
													error.setErrorCode(8106);
													error.setErrorMessage(getErrorMessagesDAOImpl
															.getErrorMessage(
																	8106,
																	langId));
													errorMessages.add(error);
													}
														}else{
															ErrorType error = new ErrorType();
															error.setErrorCode(8106);
															error.setErrorMessage(getErrorMessagesDAOImpl
																	.getErrorMessage(
																			8106,
																			langId));
															errorMessages.add(error);
														}
													}else{
														ErrorType error = new ErrorType();
														error.setErrorCode(8106);
														error.setErrorMessage(getErrorMessagesDAOImpl
																.getErrorMessage(
																		8106,
																		langId));
														errorMessages.add(error);
													}
												}else{
													ErrorType error = new ErrorType();
													error.setErrorCode(8106);
													error.setErrorMessage(getErrorMessagesDAOImpl
															.getErrorMessage(
																	8106,
																	langId));
													errorMessages.add(error);
												}
												/*if(isNumeric(listOfMemberMessagesRQ
														.getStatus())){
													ErrorType error = new ErrorType();
													error.setErrorCode(8106);
													error.setErrorMessage(getErrorMessagesDAOImpl
															.getErrorMessage(
																	8106,
																	langId));
													errorMessages.add(error);
												}
												if(listOfMemberMessagesRQ
														.getStatus().contains(".") || !(Integer.parseInt(listOfMemberMessagesRQ
																.getStatus())>0)){
													ErrorType error = new ErrorType();
													error.setErrorCode(8106);
													error.setErrorMessage(getErrorMessagesDAOImpl
															.getErrorMessage(
																	8106,
																	langId));
													errorMessages.add(error);
												}*/
											}
											if (!(errorMessages.size() > 0)) {
												MemberMessages messages = null;
												List<MemberMessage> mem = null;
												if (null != listOfMemberMessagesRQ
														.getItemId()) {
													if (listOfMemberMessagesRQ
															.getItemId()
															.isEmpty()) {
														LOGGER.debug("ItemId is invalid or Empty ");
														ErrorType error = new ErrorType();
														error.setErrorCode(8101);
														error.setErrorMessage(getErrorMessagesDAOImpl
																.getErrorMessage(
																		8101,
																		langId));
														errorMessages
																.add(error);
													}
													if (!(errorMessages.size() > 0)) {
														boolean validityStatus = listOfMemberMessagesDAO
																.validateItemId(
																		listOfMemberMessagesRQ
																				.getItemId(),
																		String.valueOf(listOfMemberMessagesRQ
																				.getObjectId()));
														if (validityStatus) {
															boolean validObject = listOfMemberMessagesDAO
																	.validateObjectId(
																			String.valueOf(listOfMemberMessagesRQ
																					.getObjectId()),
																			listOfMemberMessagesRQ
																					.getItemId());
															if (validObject) {
																List<Map<String, Object>> itemMessages = listOfMemberMessagesDAO
																		.getMemberMessagesForItemId(
																				listOfMemberMessagesRQ
																						.getItemId(),
																				periodFrom,
																				periodTo,
																				upperLimit,
																				lowerLimit,
																				status);

																if (itemMessages
																		.size() > 0
																		&& itemMessages != null) {
																	mem = new ArrayList<MemberMessage>();
																	String messageId = null;
																	String subject = null;
																	String ebayItemId = null;
																	String creationDate = null;
																	String ebayName = null;
																	String isAnswered = null;
																	String question = null;

																	for (Map<String, Object> entry : itemMessages) {
																		messages = new MemberMessages();

																		memberData = new MemberMessage();
																		if (null != entry
																				.get("message_id")) {
																			messageId = entry
																					.get("message_id")
																					.toString();
																			memberData
																					.setId(messageId);
																		}
																		if (null != entry
																				.get("message")) {
																			question = entry
																					.get("message")
																					.toString();
																			memberData
																					.setQuestion(question);
																		}
																		if (null != entry
																				.get("subject")) {
																			subject = entry
																					.get("subject")
																					.toString();
																			memberData
																					.setSubject(subject);
																		}

																		if (null != entry
																				.get("ebayitemid")) {
																			ebayItemId = entry
																					.get("ebayitemid")
																					.toString();
																			memberData
																					.setItemId(ebayItemId);
																		}
																		if (null != entry
																				.get("creation_date")) {
																			creationDate = entry
																					.get("creation_date")
																					.toString();
																			memberData
																					.setCreationDate(creationDate.replace(".0", ""));
																		}
																		if (null != entry
																				.get("ebayname")) {
																			ebayName = entry
																					.get("ebayname")
																					.toString();
																			memberData
																					.setBuyerName(ebayName);
																		}
																		if (null != entry
																				.get("beantwortet")) {
																			isAnswered = entry
																					.get("beantwortet")
																					.toString();
																			memberData
																					.setStatus(isAnswered);
																		}
																		if (memberData
																				.getItemId() != null
																				|| memberData
																						.getSubject() != null
																				|| memberData
																						.getId() != null
																				|| memberData
																						.getCreationDate() != null
																				|| memberData
																						.getStatus() != null) {
																			memberData.getCreationDate().replace(".0", "");
																			mem.add(memberData);
																		}
																		if (mem != null
																				&& mem.size() > 0) {
																			messages.getMemberMessage().addAll(mem);
																		}
																	}

																	if (null != messages
																			&& messages
																					.getMemberMessage() != null) {
																		listOfMemberMessagesRS
																				.setMemberMessages(messages);
																		int count = listOfMemberMessagesDAO
																				.getMemberMessagesForItemIdCount(
																						listOfMemberMessagesRQ
																								.getItemId(),
																						periodFrom,
																						periodTo,
																						upperLimit,
																						lowerLimit,
																						status);
																		if (count > 0) {
																			listOfMemberMessagesRS
																					.setTotalMessagesCount(String
																							.valueOf(count));
																		}
																	} else {
																		listOfMemberMessagesRS
																				.setAck("Failure");
																		ErrorType error = new ErrorType();
																		error.setErrorCode(8105);
																		error.setErrorMessage(getErrorMessagesDAOImpl
																				.getErrorMessage(
																						8105,
																						langId));
																		errorMessages
																				.add(error);
																	}
																} else {
																	listOfMemberMessagesRS
																			.setAck("Failure");
																	ErrorType error = new ErrorType();
																	error.setErrorCode(8105);
																	error.setErrorMessage(getErrorMessagesDAOImpl
																			.getErrorMessage(
																					8105,
																					langId));
																	errorMessages
																			.add(error);
																}

															} else {
																LOGGER.debug("ItemId doesnot belong to objectid");
																listOfMemberMessagesRS
																		.setAck("Failure");
																ErrorType error = new ErrorType();
																error.setErrorCode(8102);
																error.setErrorMessage(getErrorMessagesDAOImpl
																		.getErrorMessage(
																				8102,
																				langId));
																errorMessages
																		.add(error);
															}
														} else {
															LOGGER.debug("ItemId is invalid or Empty ");
															listOfMemberMessagesRS
																	.setAck("Failure");
															ErrorType error = new ErrorType();
															error.setErrorCode(8101);
															error.setErrorMessage(getErrorMessagesDAOImpl
																	.getErrorMessage(
																			8101,
																			langId));
															errorMessages
																	.add(error);
														}
													}
												} else {

													List<Map<String, Object>> memberMessages = listOfMemberMessagesDAO
															.getMemberMessages(
																	String.valueOf(listOfMemberMessagesRQ
																			.getObjectId()),
																	listOfMemberMessagesRQ
																			.getItemId(),
																	periodFrom,
																	periodTo,
																	upperLimit,
																	lowerLimit,
																	status);
													if (memberMessages.size() > 0
															&& memberMessages != null) {
														mem = new ArrayList<MemberMessage>();
														String messageId = null;
														String subject = null;
														String ebayItemId = null;
														String creationDate = null;
														String ebayName = null;
														String isAnswered = null;
														String question = null;
														mem = new ArrayList<MemberMessage>();
														/*
														 * for (Map<String,
														 * Object> entry :
														 * memberMessages) { if
														 * (null !=
														 * entry.get("message_id"
														 * )) { messageId =
														 * entry.get(
														 * "message_id")
														 * .toString(); } if
														 * (null !=
														 * entry.get("subject"))
														 * { subject =
														 * entry.get("subject")
														 * .toString(); }
														 * 
														 * if (null !=
														 * entry.get(
														 * "ebayitemid")) {
														 * ebayItemId =
														 * entry.get(
														 * "ebayitemid")
														 * .toString(); } if
														 * (null != entry
														 * .get("creation_date"
														 * )) { creationDate =
														 * entry.get(
														 * "creation_date")
														 * .toString(); } if
														 * (null !=
														 * entry.get("ebayname"
														 * )) { ebayName = entry
														 * .get("ebayname")
														 * .toString(); } if
														 * (null != entry
														 * .get("beantwortet"))
														 * { isAnswered =
														 * entry.get(
														 * "beantwortet")
														 * .toString(); }
														 * 
														 * MemberMessage
														 * memberData = new
														 * MemberMessage();
														 * 
														 * memberData.setId(
														 * messageId );
														 * memberData
														 * .setItemId(
														 * ebayItemId);
														 * memberData
														 * .setBuyerName
														 * (ebayName);
														 * memberData
														 * .setSubject(subject);
														 * memberData
														 * .setCreationDate
														 * (creationDate );
														 * memberData
														 * .setStatus(
														 * isAnswered);
														 * 
														 * mem.add(memberData);
														 * messages.
														 * setMemberMessage
														 * (mem); }
														 * 
														 * listOfMemberMessagesRS
														 * .setMemberMessages
														 * (messages);
														 */
														for (Map<String, Object> entry : memberMessages) {
															messages = new MemberMessages();

															memberData = new MemberMessage();
															if (null != entry
																	.get("message_id")) {
																messageId = entry
																		.get("message_id")
																		.toString();
																memberData
																		.setId(messageId);
															}
															if (null != entry
																	.get("message")) {
																question = entry
																		.get("message")
																		.toString();
																memberData
																		.setQuestion(question);
															}
															if (null != entry
																	.get("subject")) {
																subject = entry
																		.get("subject")
																		.toString();
																memberData
																		.setSubject(subject);
															}

															if (null != entry
																	.get("ebayitemid")) {
																ebayItemId = entry
																		.get("ebayitemid")
																		.toString();
																memberData
																		.setItemId(ebayItemId);
															}
															if (null != entry
																	.get("creation_date")) {
																creationDate = entry
																		.get("creation_date")
																		.toString();
																memberData
																.setCreationDate(creationDate.replace(".0", ""));
															}
															if (null != entry
																	.get("ebayname")) {
																ebayName = entry
																		.get("ebayname")
																		.toString();
																memberData
																		.setBuyerName(ebayName);
															}
															if (null != entry
																	.get("beantwortet")) {
																isAnswered = entry
																		.get("beantwortet")
																		.toString();
																memberData
																		.setStatus(isAnswered);
															}
															if (memberData
																	.getItemId() != null
																	|| memberData
																			.getSubject() != null
																	|| memberData
																			.getId() != null
																	|| memberData
																			.getCreationDate() != null
																	|| memberData
																			.getStatus() != null
																	|| memberData
																			.getQuestion() != null) {
																memberData.getCreationDate().replace(".0", "");
																mem.add(memberData);
															}
															if (mem != null
																	&& mem.size() > 0) {
																messages.getMemberMessage().addAll(mem);
															}

														}
														if (null != messages
																&& messages
																		.getMemberMessage() != null) {
															listOfMemberMessagesRS
																	.setMemberMessages(messages);
															int count = listOfMemberMessagesDAO
																	.getMemeberMessagesCount(
																			String.valueOf(listOfMemberMessagesRQ
																					.getObjectId()),
																			listOfMemberMessagesRQ
																					.getItemId(),
																			periodFrom,
																			periodTo,
																			upperLimit,
																			lowerLimit,
																			status);
															if (count > 0) {
																listOfMemberMessagesRS
																		.setTotalMessagesCount(String
																				.valueOf(count));
															}
														} else {
															listOfMemberMessagesRS
																	.setAck("Failure");
															ErrorType error = new ErrorType();
															error.setErrorCode(8105);
															error.setErrorMessage(getErrorMessagesDAOImpl
																	.getErrorMessage(
																			8105,
																			langId));
															errorMessages
																	.add(error);
														}

													} else {
														listOfMemberMessagesRS
																.setAck("Failure");
														ErrorType error = new ErrorType();
														error.setErrorCode(8105);
														error.setErrorMessage(getErrorMessagesDAOImpl
																.getErrorMessage(
																		8105,
																		langId));
														errorMessages
																.add(error);
													}

												}
											}
										}
									}
								}
							//}
						}
						 }else{
							 LOGGER.debug("Fetch Credentials for Source::"
										+ listOfMemberMessagesRQ.getSourceId());
								LOGGER.debug("AuthCode is invalid::"
										+ listOfMemberMessagesRQ.getSourceId());
								ErrorType error = new ErrorType();
								error.setErrorCode(1100);
								error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1100,
										langId));
								errorMessages.add(error);
						 }
					} else {

						LOGGER.debug("TimeStamp is Missing");
						ErrorType error = new ErrorType();
						error.setErrorCode(1105);
						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(1105, langId));
						errorMessages.add(error);
					}
				}
			} else {
				LOGGER.debug("Error Language value is not set ::"
						+ listOfMemberMessagesRQ.getErrorLang());
				ErrorType error = new ErrorType();
				error.setErrorCode(1106);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						1106, 2));
				errorMessages.add(error);

			}
			if ((errorMessages.size() > 0)) {
				listOfMemberMessagesRS.setAck("Failure");
				listOfMemberMessagesRS.setErrors(errorsType);
			} else {
				listOfMemberMessagesRS.setAck("Success");
			}
		} catch (EmptyResultDataAccessException ed) {
			ed.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfMemberMessagesRS;
	}

	/**
	 * This method is used to validate the channelId
	 * 
	 * @param endItemRQ
	 * @param langId
	 * @return
	 */
	private boolean validateChannelId(
			ListOfMemberMessagesRQ listOfMemberMessagesRQ,
			List<ErrorType> errorMessages, int langId, int channelId) {
		ErrorType error = new ErrorType();
		if (listOfMemberMessagesRQ.isSetChannelId()) {
			LOGGER.debug("ChannelId is set ::"
					+ listOfMemberMessagesRQ.getChannelId());
			if (listOfMemberMessagesRQ.getChannelId() == channelId) {
				LOGGER.debug("ChannelId value is ::"
						+ listOfMemberMessagesRQ.getSourceId());
				return true;
			} else {
				LOGGER.debug("ChannelId value is invalid ::"
						+ listOfMemberMessagesRQ.getSourceId());
				error.setErrorCode(1102);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						1102, langId));
				errorMessages.add(error);
				return false;
			}

		} else {
			LOGGER.debug("ChannelId is not set ::"
					+ listOfMemberMessagesRQ.getChannelId());
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
	 */
	private boolean validateAuthenticationCode(
			ListOfMemberMessagesRQ listOfMemberMessagesRQ,
			List<ErrorType> errorMessages, int langId, String authCode) {
		ErrorType error = new ErrorType();
		if (listOfMemberMessagesRQ.isSetAuthenticationCode()) {
			if (listOfMemberMessagesRQ.getAuthenticationCode().equalsIgnoreCase(authCode)){
					if(! listOfMemberMessagesRQ.getAuthenticationCode().isEmpty()){ 
				LOGGER.debug("AuthenticationCode is ::"
						+ listOfMemberMessagesRQ.getAuthenticationCode());
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
					+ listOfMemberMessagesRQ.getAuthenticationCode());
			error.setErrorCode(1100);
			error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1100,
					langId));
			errorMessages.add(error);
			return false;
		}
	}else {
		LOGGER.debug("AuthenticationCode is not set ::"
				+ listOfMemberMessagesRQ.getAuthenticationCode());
		error.setErrorCode(1100);
		error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1100,
				langId));
		errorMessages.add(error);
		return false;
	}
	}
	/**
	 * This method is used to validate the Source Id
	 * 
	 * @param endItemRQ
	 * @param langId
	 * @return
	 */
	private boolean validateSourceId(
			ListOfMemberMessagesRQ listOfMemberMessagesRQ,
			List<ErrorType> errorMessages, int langId, int sourceCode) {
		ErrorType error = new ErrorType();
		if (listOfMemberMessagesRQ.isSetSourceId()) {
			if (listOfMemberMessagesRQ.getSourceId() == sourceCode) {
				LOGGER.debug("SourceId value is ::"
						+ listOfMemberMessagesRQ.getSourceId());
				return true;
			} else {
				LOGGER.debug("SourceId value is invalid ::"
						+ listOfMemberMessagesRQ.getSourceId());
				error.setErrorCode(1101);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						1101, langId));
				errorMessages.add(error);
				return false;
			}

		} else {
			LOGGER.debug("SourceId value is not set ::"
					+ listOfMemberMessagesRQ.getSourceId());
			error.setErrorCode(1101);
			error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1101,
					langId));
			errorMessages.add(error);
			return false;
		}
	}

	private int validateLangId(ListOfMemberMessagesRQ listOfMemberMessagesRQ,
			List<ErrorType> errorMessages) {
		int langId = 0;
		ErrorType error = new ErrorType();
		if (!listOfMemberMessagesRQ.getErrorLang().isEmpty()) {
			if (listOfMemberMessagesRQ.getErrorLang().equalsIgnoreCase("en")) {
				langId = getErrorMessagesDAOImpl
						.getLanguageId(listOfMemberMessagesRQ.getErrorLang());
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
				LOGGER.debug("Error Language value is not set ::"
						+ listOfMemberMessagesRQ.getErrorLang());
				error.setErrorCode(1106);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						1106, 2));
				errorMessages.add(error);

				return langId;
			}
		} else {
			LOGGER.debug("Error Language value is not set ::"
					+ listOfMemberMessagesRQ.getErrorLang());
			error.setErrorCode(1106);
			error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1106,
					2));
			errorMessages.add(error);

			return langId;
		}
	}

	/**
	 * This method is required to validate the objectId
	 * 
	 * @param endItemRQ
	 * @return
	 */
	private boolean validateObjectId(
			ListOfMemberMessagesRQ listOfMemberMessagesRQ,
			List<ErrorType> errorMessages, int langId) {
		ErrorType error = new ErrorType();
		try {
			if (listOfMemberMessagesRQ.isSetObjectId()) {
				LOGGER.debug("ObjectId value is set ::"
						+ listOfMemberMessagesRQ.getObjectId());

				if (listOfMemberMessagesDAO.checkForObjectValidity(String
						.valueOf(listOfMemberMessagesRQ.getObjectId()))) {
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
						+ listOfMemberMessagesRQ.getObjectId());
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
	
	public boolean isNumeric(String str) {
		try {
			Integer quantity = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

}
