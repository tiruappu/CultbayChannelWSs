package com.cultuzz.channel.helper.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.DAO.MemberDetailsDAO;
import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.DAO.impl.ValidationsDAO;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.XMLpojo.MemberMessageDetailsRQ;
import com.cultuzz.channel.XMLpojo.MemberMessageDetailsRS;
import com.cultuzz.channel.XMLpojo.MemberMessageDetailsRS.MemberMessage;
import com.cultuzz.channel.helper.MemberDetailsHelper;
import com.cultuzz.channel.util.CommonValidations;
import com.cultuzz.channel.util.DateUtil;

@Component
public class MemberDetailsHelperImpl implements MemberDetailsHelper {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MemberMessageDetailsRQ.class);

	private static final String timeStampRegx = "^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";

	@Autowired
	MemberDetailsDAO memberDetailsDAO;

	@Autowired
	GetErrorMessagesDAOImpl getErrorMessagesDAOImpl;

	@Autowired
	CommonValidations commonValidations;

	

	public MemberMessageDetailsRS processMemberMessageDetailsRequest(
			MemberMessageDetailsRQ memberMessageDetailsRQ) {

		MemberMessageDetailsRS memberMessageDetailsRS = new MemberMessageDetailsRS();
		
		List<Map<String, Object>> allDetails = null;
		MemberMessage memberMessage = new MemberMessage();
		String message = null;
		String creationDate = null;
		String response_date = null;
		int isAnswerd = 0;

		try {

			int langId = getErrorMessagesDAOImpl
					.getLanguageId(memberMessageDetailsRQ.getErrorLang());
			ErrorsType errorsType = new ErrorsType();
			List<ErrorType> errorTypes = errorsType.getError();

			List<Map<String, Object>> questionList = memberDetailsDAO
					.getQuestions(memberMessageDetailsRQ.getMessageId());

			LOGGER.debug("questionList value is:::" + questionList);

			if (questionList != null) {
				for (Map<String, Object> entry : questionList) {
					LOGGER.debug("Inside for loop");
					if (entry.get("message") != null) {
						message = entry.get("message").toString();
					}
					if (entry.get("beantwortet") != null) {
						isAnswerd = Integer.parseInt(entry.get("beantwortet")
								.toString());
					}

				}

				allDetails = memberDetailsDAO.getAllDetails(
						memberMessageDetailsRQ.getMessageId(), isAnswerd);

				LOGGER.debug("allDetails values:::" + allDetails);
				LOGGER.debug("allDetails values:::" + allDetails.size());

				if (allDetails != null && allDetails.size() > 0) {

					String buyerName = null;
					String subject = null;
					String itemId = null;
					String answer = null;

					for (Map<String, Object> entry : allDetails) {

						if (entry.get("ebayname") != null) {
							buyerName = entry.get("ebayname").toString();
						}
						if (entry.get("subject") != null) {
							subject = entry.get("subject").toString();
						}
						if (entry.get("ebayitemid") != null) {
							itemId = entry.get("ebayitemid").toString();
						}
						// if (entry.get("ebayitemid") != null) {
						// itemId = entry.get("ebayitemid").toString();
						// }
						if (entry.get("response") != null) {
							answer = entry.get("response").toString();
							memberMessage.setAnswer(answer);
						}
						if (entry.get("creation_date") != null) {
							LOGGER.debug("Inside creation_date block");
							creationDate = entry.get("creation_date")
									.toString();

							// memberMessage.setCreationDate(creationDate);
							memberMessage.setCreationDate(creationDate.replace(
									".0", ""));

						}
						if (entry.get("response_date") != null) {
							LOGGER.debug("Inside response_date block");

							response_date = entry.get("response_date")
									.toString();
							// memberMessage.setRepliedDate(response_date);
							memberMessage.setRepliedDate(response_date.replace(
									".0", ""));
						}

					}

					memberMessage.setBuyerName(buyerName);
					memberMessage.setSubject(subject);
					memberMessage.setItemId(itemId);
					memberMessage.setQuestion(message);
					memberMessage.setId(memberMessageDetailsRQ.getMessageId());
					memberMessageDetailsRS.setMemberMessage(memberMessage);
				} else {

					LOGGER.debug("Error Message to be thrown...");

					ErrorType error = new ErrorType();
					error.setErrorCode(7114);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(7114, langId));
					errorTypes.add(error);
				}

				if (errorTypes.size() > 0) {
					LOGGER.debug("4");
					memberMessageDetailsRS.setAck("Failure");
					memberMessageDetailsRS.setErrors(errorsType);
				} else {
					LOGGER.debug("5");
					memberMessageDetailsRS.setAck("Success");
				}
				
			} else {
				// Throw an error..

			}
		} catch (Exception e) {
			// e.getMessage();
			e.printStackTrace();
			LOGGER.debug("Inside Exception for memberMessage");
		}

		memberMessageDetailsRS.setTimeStamp(DateUtil
				.convertDateToString(new Date()));
		return memberMessageDetailsRS;
	}

	/**
	 * This method is used to validate the Revise Offer Request
	 */
	public MemberMessageDetailsRS validateMemberMessageDetailsRequest(
			MemberMessageDetailsRQ memberMessageDetailsRQ) {

		// All the validations required in M2M...
		LOGGER.debug("inside OfferReviseRequest method");

		String authCode = null;
		int sourceId = 0;
		int channelId = 0;

		MemberMessageDetailsRS memberMessageDetailsRS = null;
		memberMessageDetailsRS = new MemberMessageDetailsRS();
		ErrorsType errorsType = new ErrorsType();

		List<ErrorType> errorTypes = errorsType.getError();

		List<Map<String, Object>> rows = null;
		sourceId = memberMessageDetailsRQ.getSourceId();

		// int langId = Integer.parseInt(memberMessageDetailsRQ.getErrorLang());

		int langId = 0;
		if (memberMessageDetailsRQ.getErrorLang() != null) {
			if (!memberMessageDetailsRQ.getErrorLang().isEmpty()) {
				if (memberMessageDetailsRQ.getErrorLang()
						.equalsIgnoreCase("en")) {
					langId = getErrorMessagesDAOImpl
							.getLanguageId(memberMessageDetailsRQ
									.getErrorLang());
					if (langId == 0) {
						ErrorType error = new ErrorType();
						error.setErrorCode(1106);
						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(1106, 2));
						errorTypes.add(error);
					}
				} else {
					ErrorType error = new ErrorType();
					error.setErrorCode(1106);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(1106, 2));
					errorTypes.add(error);
				}
			} else {
				ErrorType error = new ErrorType();
				error.setErrorCode(1106);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						1106, 2));
				errorTypes.add(error);
			}
		} else {
			ErrorType error = new ErrorType();
			error.setErrorCode(1106);
			error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1106,
					2));
			errorTypes.add(error);
		}

		// LOGGER.debug("__ERROR LANG___"+offerReviseRQ.getErrorLang());

		LOGGER.debug("LangId value is" + langId);
		if (langId == 0) {
			LOGGER.debug("LangId value is" + langId);

			/*
			 * ErrorType error = new ErrorType(); error.setErrorCode(1106);
			 * error
			 * .setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1106,
			 * 2)); errorTypes.add(error);
			 */
		} else {

			memberMessageDetailsRS.setTimeStamp(DateUtil
					.convertDateToString(new Date()));
			if (!commonValidations.checkTimeStamp(memberMessageDetailsRQ
					.getTimeStamp())) {
				LOGGER.debug("TimeStamp is not valid ");
				memberMessageDetailsRS.setAck("Failure");
				ErrorType error = new ErrorType();

				error.setErrorCode(1104);

				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						1104, langId));
				errorTypes.add(error);

			}

			if (errorTypes.size() > 0) {
				memberMessageDetailsRS.setAck("Failure");
				memberMessageDetailsRS.setErrors(errorsType);
			} else {

				rows = commonValidations.fetchCredential(memberMessageDetailsRQ.getAuthenticationCode());
				LOGGER.debug("Size of List is:::" + rows.size());

				if (rows.size() > 0) {
					for (Map<String, Object> credentails : rows) {
						//authCode = credentails.get("auth_code").toString();
						sourceId = Integer.parseInt(credentails.get("sourceId").toString());
						channelId = Integer.parseInt(credentails.get("channelId").toString());
					}
				} else {
					ErrorType error = new ErrorType();
					error.setErrorCode(1100);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(1100, langId));
					errorTypes.add(error);

				}

				if (errorTypes.size() > 0) {
					memberMessageDetailsRS.setAck("Failure");
					memberMessageDetailsRS.setErrors(errorsType);
				} else {

					/*
					 * Authentication Code Validation...
					 */
					authCode = memberMessageDetailsRQ.getAuthenticationCode();
					LOGGER.debug("hello for authcode"+authCode);
					if (memberMessageDetailsRQ.isSetAuthenticationCode()
							&& memberMessageDetailsRQ.getAuthenticationCode() != "") {
						LOGGER.debug("Auth Code is:::"
								+ memberMessageDetailsRQ
										.getAuthenticationCode());
						
					//	LOGGER.debug("AUTHCODE IS:::");
						
						if (!memberMessageDetailsRQ.getAuthenticationCode()
								.equals(authCode)) {
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

					if (errorTypes.size() > 0) {
						memberMessageDetailsRS.setAck("Failure");
						memberMessageDetailsRS.setErrors(errorsType);
					} else {

						/*
						 * SourceId Validation
						 */
						if (memberMessageDetailsRQ.isSetSourceId()) {
							if (memberMessageDetailsRQ.getSourceId() != sourceId) {
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
						if (errorTypes.size() > 0) {
							memberMessageDetailsRS.setAck("Failure");
							memberMessageDetailsRS.setErrors(errorsType);
						} else {

							/*
							 * EbayItemId is valid or not
							 */
							//
							// if (memberMessageDetailsRQ.isSetItemId()
							// && memberMessageDetailsRQ.getItemId() != null) {
							// if
							// (!memberMessageDetailsRQ.getItemId().isEmpty()) {
							// LOGGER.debug("*****Inside ItemId Checking*****");
							// boolean ebayStatus = memberDetailsDAO
							// .checkEbayItemId(memberMessageDetailsRQ.getItemId());
							// LOGGER.debug("ebayStatus is:::" + ebayStatus);
							// if (ebayStatus) {
							// LOGGER.debug("Inside ebaystatus block...");
							// ErrorType error = new ErrorType();
							// error.setErrorCode(4104);
							// error.setErrorMessage(getErrorMessagesDAOImpl
							// .getErrorMessage(4104, langId));
							// errorTypes.add(error);
							// }
							// }
							// }

							/*
							 * MessageId Validation. //
							 */
							LOGGER.debug("MessageId:::"
									+ memberMessageDetailsRQ.getMessageId());
							if (memberMessageDetailsRQ.isSetMessageId()
									&& memberMessageDetailsRQ.getMessageId() != null) {
								LOGGER.debug("MessageId is valid");
								boolean status = memberDetailsDAO
										.validMessageId(memberMessageDetailsRQ
												.getMessageId());

								if (!status) {
									LOGGER.debug("Inside messageId validation false");

									ErrorType error = new ErrorType();
									error.setErrorCode(9101);
									error.setErrorMessage(getErrorMessagesDAOImpl
											.getErrorMessage(9101, langId));
									errorTypes.add(error);
								}
							} else if (memberMessageDetailsRQ.getMessageId() == null
									|| memberMessageDetailsRQ.getMessageId()
											.isEmpty()) {

								LOGGER.debug("Inside messageId Is not given");
								ErrorType error = new ErrorType();
								error.setErrorCode(9101);
								error.setErrorMessage(getErrorMessagesDAOImpl
										.getErrorMessage(9101, langId));
								errorTypes.add(error);
							}

							/*
							 * ObjectId Validation.
							 */

							if (memberMessageDetailsRQ.isSetObjectId()
									&& memberMessageDetailsRQ.getObjectId() != 0) {
								LOGGER.debug("OOOOObejectID:::"
										+ memberMessageDetailsRQ.getObjectId());
								boolean objectIdStatus = commonValidations
										.checkObjectId(memberMessageDetailsRQ
												.getObjectId());
								if (objectIdStatus == false) {
									LOGGER.debug("Object id is invalid");
									ErrorType error = new ErrorType();
									error.setErrorCode(1103);
									error.setErrorMessage(getErrorMessagesDAOImpl
											.getErrorMessage(1103, langId));
									errorTypes.add(error);
								}
							} else {
								LOGGER.debug("Object id is Missing");
								ErrorType error = new ErrorType();
								error.setErrorCode(1103);
								error.setErrorMessage(getErrorMessagesDAOImpl
										.getErrorMessage(1103, langId));
								errorTypes.add(error);
							}

							/*
							 * ChannelId Validation.
							 */
							if (memberMessageDetailsRQ.isSetChannelId()) {
								if (memberMessageDetailsRQ.getChannelId() != channelId) {
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
							if (errorTypes.size() > 0) {
								memberMessageDetailsRS.setAck("Failure");
								memberMessageDetailsRS.setErrors(errorsType);
							} else {
								// memberMessageDetailsRS.setAck("Success");

								/*
								 * messageId corresponds to objectId validation
								 */

								LOGGER.debug("**MessageId**"
										+ memberMessageDetailsRQ.getMessageId());
								if (!memberDetailsDAO.checkMessageId(
										memberMessageDetailsRQ.getMessageId(),
										memberMessageDetailsRQ.getObjectId())) {

									LOGGER.debug("MessageId not related to objectid");

									ErrorType error = new ErrorType();
									error.setErrorCode(9100);
									error.setErrorMessage(getErrorMessagesDAOImpl
											.getErrorMessage(9100, langId));
									errorTypes.add(error);

								}
							}
						}
					}
				}
			}
		}
		if (errorTypes.size() > 0) {
			memberMessageDetailsRS.setAck("Failure");
			memberMessageDetailsRS.setErrors(errorsType);
		} else {
			memberMessageDetailsRS.setAck("Success");
		}

		memberMessageDetailsRS.setTimeStamp(DateUtil
				.convertDateToString(new Date()));
		return memberMessageDetailsRS;
	}

}
