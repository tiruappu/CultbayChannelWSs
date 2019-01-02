package com.cultuzz.channel.helper.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.DAO.AnswerToBuyerMessageDAO;
import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.XMLpojo.AnswerBuyerMessageRQ;
import com.cultuzz.channel.XMLpojo.AnswerBuyerMessageRS;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.helper.AnswerToBuyerMessageHelper;
import com.cultuzz.channel.util.CommonValidations;
import com.cultuzz.channel.util.DateUtil;
import com.cultuzz.utils.PropertiesLoader;

@Component
public class AnswerToBuyerMessageHelperImpl implements
		AnswerToBuyerMessageHelper {

	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(AnswerToBuyerMessageHelperImpl.class);

	//private static final String timeStampRegx = "^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";

	@Autowired
	GetErrorMessagesDAOImpl getErrorMessagesDAOImpl;

	@Autowired
	CommonValidations commonValidations;

	@Autowired
	AnswerToBuyerMessageDAO answerToBuyerMessageDAO;
	

	@Autowired
	CommonValidations commonValidation;
	
	@Autowired
	@Qualifier("propertiesRead")
	PropertiesLoader properties;
	
	/**
	 * This method is used process the Answer for Question given
	 * @param answerBuyerMessageRQ
	 * @return
	 */
	public AnswerBuyerMessageRS processAnswerToBuyerMessage(
			AnswerBuyerMessageRQ answerBuyerMessageRQ) {

		
		AnswerBuyerMessageRS answerBuyerMessageRS = null;
		String answer = answerBuyerMessageRQ.getMemberMessages()
				.getMemberMessage().getAnswer();

		LOGGER.debug("Answer From request "
				+ answerBuyerMessageRQ.getMemberMessages().getMemberMessage()
						.getAnswer());
		ErrorsType errorsType = new ErrorsType();
		answerBuyerMessageRS = new AnswerBuyerMessageRS();
		
		List<ErrorType> errorTypes = errorsType.getError();
		try {
			LOGGER.debug("Length of Answer is from request:::"
					+ answer.length());
			int langId = validateLangId(answerBuyerMessageRQ, errorTypes);
			if (answer.getBytes("UTF-8").length <=1000) {
				
				String messageId = answerBuyerMessageRQ.getMemberMessages()
						.getMemberMessage().getId();
				answer = answerBuyerMessageRQ.getMemberMessages()
						.getMemberMessage().getAnswer();
				String sellerName = answerBuyerMessageRQ.getMemberMessages()
						.getMemberMessage().getSellerName();

				int id = answerToBuyerMessageDAO.saveDetails(messageId, answer,
						sellerName);
					LOGGER.debug("Answer to buyer message id iss{}",id);
					LOGGER.debug("Answer length is less than 1000");
					String response = null;
					LOGGER.debug("Answer to buyer messages"+properties.getAnswerToBuyerMessages());
					System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
					URL url = new URL(properties.getAnswerToBuyerMessages());

					URLConnection con = url.openConnection();

					// activate the output
					con.setDoOutput(true);

					PrintStream ps = new PrintStream(con.getOutputStream());
					// send input parameters
					ps.print("messageId="
							+ answerBuyerMessageRQ.getMemberMessages()
									.getMemberMessage().getId());
					ps.print("&messageText="
							+ answerBuyerMessageRQ.getMemberMessages()
									.getMemberMessage().getAnswer());
					ps.print("&objectId=" + answerBuyerMessageRQ.getObjectId());

					ps.print("&sellerId="
							+ answerBuyerMessageRQ.getMemberMessages()
									.getMemberMessage().getSellerName());

					BufferedReader in = new BufferedReader(
							new InputStreamReader(con.getInputStream()));
					

					String line = null;

					while ((line = in.readLine()) != null) {
						response = line;
						System.out.println(response);
					}

					LOGGER.debug("Response Retreived from Answer:::" + response);
					System.out.println(response);
					ps.close();
					if (response.contains("Success")) {
						LOGGER.debug("Success is retrieved from Response");
						
					} else if(response.contains("Failure")) {
						
						LOGGER.debug("Failure is retreived from Response");
						ErrorType error = new ErrorType();
						error.setErrorCode(10007);
						error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(10007,
								langId));
						errorTypes.add(error);
					}else{
						LOGGER.debug("Unable to retrieve response from ebay");
						ErrorType error = new ErrorType();
						error.setErrorCode(10007);
						error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(10004,
								langId));
						errorTypes.add(error);
					}
				

			} else {
				LOGGER.debug("Answer from request is more than 1000 characters");
				ErrorType error = new ErrorType();
				error.setErrorCode(10004);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(10004,
						langId));
				errorTypes.add(error);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.debug("Exception");
			return null;
		}
		answerBuyerMessageRS.setTimeStamp(commonValidations
				.getCurrentTimeStamp());
		if(errorTypes.size()>0){
			answerBuyerMessageRS.setAck("Failure");
			answerBuyerMessageRS.setErrors(errorsType);
		}else{
			answerBuyerMessageRS.setAck("Success");
		}
		return answerBuyerMessageRS;
	}

	/**
	 * This method is used to validate the AnswerToMemberMessage Offer Request
	 */

	public AnswerBuyerMessageRS validateAnswerToBuyerMessage(
			AnswerBuyerMessageRQ answerBuyerMessageRQ) {

		LOGGER.debug("inside AnswerBuyerMessageRS method");

		AnswerBuyerMessageRS answerBuyerMessageRS = null;
		answerBuyerMessageRS = new AnswerBuyerMessageRS();

		ErrorsType errorsType = new ErrorsType();

		List<ErrorType> errorTypes = errorsType.getError();

		// LOGGER.debug("__ERROR LANG___"+offerReviseRQ.getErrorLang());

		answerBuyerMessageRS.setTimeStamp(DateUtil
				.convertDateToString(new Date()));

		int langId = validateLangId(answerBuyerMessageRQ, errorTypes);
		LOGGER.debug("LangId value is" + langId);
		if (langId == 0) {
			ErrorType error = new ErrorType();
			error.setErrorCode(1106);
			error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1106,
					2));
			errorTypes.add(error);
		} else {
			List<Map<String,Object>> listCrendentials=commonValidation.fetchCredential(answerBuyerMessageRQ.getAuthenticationCode());
			if(listCrendentials.size()>0){
				 //String authCode=null;
				 int channelId=0;
				 int source=0;
			 for(Map<String, Object> entry:listCrendentials){
				 source=Integer.parseInt(entry.get("sourceId").toString());
				 channelId=Integer.parseInt(entry.get("channelId").toString());
			 }
			if (answerBuyerMessageRQ.isSetTimeStamp()
					&& answerBuyerMessageRQ.getTimeStamp() != "") {

				LOGGER.debug("Inside Timestamp validation...");

				if (!commonValidations.checkTimeStamp(answerBuyerMessageRQ.getTimeStamp())) {

					LOGGER.debug("Invalid TimeStamp");

					ErrorType error = new ErrorType();
					error.setErrorCode(1104);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(1104, langId));
					errorTypes.add(error);
				}
			} else {

				LOGGER.debug("TimeStamp is Missing");
				ErrorType error = new ErrorType();
				error.setErrorCode(1105);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						1105, langId));
				errorTypes.add(error);

			}
			if (answerBuyerMessageRQ.isSetObjectId()
					&& answerBuyerMessageRQ.getObjectId() != 0) {
				LOGGER.debug("OOOOObejectID:::"
						+ answerBuyerMessageRQ.getObjectId());
				boolean objectIdStatus = commonValidations
						.checkObjectId(answerBuyerMessageRQ.getObjectId());
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
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						1103, langId));
				errorTypes.add(error);
			}

			// answerBuyerMessageRQ.getAuthenticationCode();

			String authCode = null;
			if (answerBuyerMessageRQ.isSetAuthenticationCode()
					&& answerBuyerMessageRQ.getAuthenticationCode() != "") {
				LOGGER.debug("Auth Code is:::"
						+ answerBuyerMessageRQ.getAuthenticationCode());
				authCode = answerBuyerMessageRQ.getAuthenticationCode();
				if (!answerBuyerMessageRQ.getAuthenticationCode().equals(
						authCode)) {
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
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						1100, langId));
				errorTypes.add(error);
			}

			if (answerBuyerMessageRQ.isSetSourceId()) {
				if (answerBuyerMessageRQ.getSourceId() != source) {
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
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						1101, langId));
				errorTypes.add(error);

			}
			if (answerBuyerMessageRQ.isSetChannelId()) {
				if (answerBuyerMessageRQ.getChannelId() != channelId) {
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
				answerBuyerMessageRS.setAck("Failure");
				answerBuyerMessageRS.setErrors(errorsType);
			} else {

				if (answerBuyerMessageRQ.getMemberMessages().getMemberMessage()
						.getSellerName() != null) {

					if (answerBuyerMessageRQ.getMemberMessages()
							.getMemberMessage().isSetSellerName()
							&& answerBuyerMessageRQ.getMemberMessages()
									.getMemberMessage().getSellerName()
									.isEmpty()) {

						LOGGER.debug("Seller Name is Empty");
						ErrorType error = new ErrorType();
						error.setErrorCode(10003);
						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(10003, langId));
						errorTypes.add(error);
					}
				} else {
					LOGGER.debug("Seller Name is Missing");
					ErrorType error = new ErrorType();
					error.setErrorCode(10003);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(10003, langId));
					errorTypes.add(error);
				}

				

				if (answerBuyerMessageRQ.getMemberMessages().getMemberMessage()
						.getQuestion() != null) {

					if (answerBuyerMessageRQ.getMemberMessages()
							.getMemberMessage().isSetQuestion()
							&& answerBuyerMessageRQ.getMemberMessages()
									.getMemberMessage().getQuestion().isEmpty()) {

						LOGGER.debug("Question is Empty");
						ErrorType error = new ErrorType();
						error.setErrorCode(10005);
						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(10005, langId));
						errorTypes.add(error);

					}
				} else {
					LOGGER.debug("Question is Missing");
					ErrorType error = new ErrorType();
					error.setErrorCode(10005);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(10005, langId));
					errorTypes.add(error);
				}

				LOGGER.debug("**MessageId**"
						+ answerBuyerMessageRQ.getMemberMessages()
								.getMemberMessage().getId());
				if (!answerToBuyerMessageDAO.checkMessageId(
						answerBuyerMessageRQ.getMemberMessages()
								.getMemberMessage().getId(),
						answerBuyerMessageRQ.getObjectId())) {

					LOGGER.debug("MessageId not related to objectid");

					ErrorType error = new ErrorType();
					error.setErrorCode(10002);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(10002, langId));
					errorTypes.add(error);

				}

				/**
				 * Checking messageId with details
				 */

				if (!answerToBuyerMessageDAO.checkMessageIdWithDetails(
						answerBuyerMessageRQ.getMemberMessages()
								.getMemberMessage().getId(),
						answerBuyerMessageRQ)) {

					LOGGER.debug("Questions not related to MessageId");

					ErrorType error = new ErrorType();
					error.setErrorCode(10006);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(10006, langId));
					errorTypes.add(error);

				}

				if (answerBuyerMessageRQ.getMemberMessages().getMemberMessage()
						.getAnswer() != null) {
					if (answerBuyerMessageRQ.getMemberMessages()
							.getMemberMessage().isSetAnswer()
							&& answerBuyerMessageRQ.getMemberMessages()
									.getMemberMessage().getAnswer().isEmpty()) {

						LOGGER.debug("Answer is Empty");
						ErrorType error = new ErrorType();
						error.setErrorCode(10001);
						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(10001, langId));
						errorTypes.add(error);

					}
				} else {
					LOGGER.debug("Answer is Missing");
					ErrorType error = new ErrorType();
					error.setErrorCode(10001);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(10001, langId));
					errorTypes.add(error);
				}

			//}
		}
			}else{
				 LOGGER.debug("Fetch Credentials for Source::"
							+ answerBuyerMessageRQ.getSourceId());
					LOGGER.debug("AuthCode is invalid::"
							+ answerBuyerMessageRQ.getSourceId());
					ErrorType error = new ErrorType();
					error.setErrorCode(1100);
					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1100,
							langId));
					errorTypes.add(error);
			}
		}
		if (errorTypes.size() > 0) {
			answerBuyerMessageRS.setAck("Failure");
			answerBuyerMessageRS.setErrors(errorsType);
		} else {
			answerBuyerMessageRS.setAck("Success");
		}

		answerBuyerMessageRS.setTimeStamp(commonValidations
				.getCurrentTimeStamp());
		return answerBuyerMessageRS;
	}

	private int validateLangId(AnswerBuyerMessageRQ answerBuyerMessageRQ,
			List<ErrorType> errorMessages) {

		LOGGER.debug("Inside validateLangId");

		int langId = 0;
		ErrorType error = new ErrorType();
		if (answerBuyerMessageRQ.isSetErrorLang()
				&& !(answerBuyerMessageRQ.getErrorLang().isEmpty())) {

			LOGGER.debug("1");
			if (answerBuyerMessageRQ.getErrorLang() != null
					&& answerBuyerMessageRQ.getErrorLang().equalsIgnoreCase(
							"en")) {
				LOGGER.debug("2");

				langId = getErrorMessagesDAOImpl
						.getLanguageId(answerBuyerMessageRQ.getErrorLang());
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
				LOGGER.debug("3");
				// error.setErrorCode(1106);
				// error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
				// 1106, 2));
				// errorMessages.add(error);

				return langId;
			}
		} else {
			LOGGER.debug("4");
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
}
