package com.cultuzz.channel.helper.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.cultuzz.channel.DAO.impl.RepushLiveOffersImplDAO;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.XMLpojo.OfferReviseRQ;
import com.cultuzz.channel.XMLpojo.OfferReviseRS;
import com.cultuzz.channel.XMLpojo.RepushLiveOffersRQ;
import com.cultuzz.channel.XMLpojo.RepushLiveOffersRS;
import com.cultuzz.channel.util.CommonValidations;

@Component
public class RepushLiveOffersHelperImpl {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RepushLiveOffersHelperImpl.class);

	private static final String timeStampRegx = "^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";

	@Autowired
	GetErrorMessagesDAOImpl getErrorMessagesDAOImpl;

	@Autowired
	RepushLiveOffersImplDAO repushLiveOffersImplDAO;

	
	@Autowired
	CommonValidations commonValidations;

	public RepushLiveOffersRS processRepushLiveOfferRequest(
			RepushLiveOffersRQ repushLiveOffersRQ) {
		RepushLiveOffersRS repushLiveOffersRS = new RepushLiveOffersRS();

		boolean reviseTitle = false;
		boolean reviseSubTitle = false;
		boolean reviseHtmlDescription = false;
		String reviseOfferText = null;
		String reviseOfferText2 = null;
		String reviseVoucherValidityText = null;
		String reviseAdditionalValidityText = null;

		ErrorsType errorsType = new ErrorsType();
		List<ErrorType> errorTypes = errorsType.getError();

		// int langId = Integer.valueOf(offerReviseRQ.getErrorLang());

		int langId = commonValidations.checkErrorLangCode(repushLiveOffersRQ
				.getErrorLang());
		LOGGER.debug("Language iD is:::" + langId);

		if (langId == 0) {

			LOGGER.debug("Inside langId block if block");
			ErrorType error = new ErrorType();
			error.setErrorCode(1106);
			error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1106,
					2));
			errorTypes.add(error);
			
		} else {

			/*
			 * Assigning Title...
			 */

			if (repushLiveOffersRQ.isSetTitle()) {
				
				//if (repushLiveOffersRQ.isTitle()) {  Needs to CHECK .....
					reviseTitle = repushLiveOffersRQ.isTitle();
					LOGGER.debug("ReviseTitle:::" + reviseTitle);
//			} else {
//					LOGGER.debug("Title is invalid");
//					ErrorType error = new ErrorType();
//					error.setErrorCode(4123);
//					error.setErrorMessage(getErrorMessagesDAOImpl
//							.getErrorMessage(4123, langId));
//					errorTypes.add(error);
//				}
			}

			/*
			 * Assigning Subtitle...
			 */

			if (repushLiveOffersRQ.isSetSubTitle()) {
//				if (repushLiveOffersRQ.isSubTitle()) {  Needs to CHECK .....
					reviseSubTitle = repushLiveOffersRQ.isSubTitle();
					LOGGER.debug("ReviseSubTitle:::" + reviseSubTitle);

//			} else {
//					LOGGER.debug("SubTitle is invalid");
//					ErrorType error = new ErrorType();
//					error.setErrorCode(4124);
//					error.setErrorMessage(getErrorMessagesDAOImpl
//							.getErrorMessage(4124, langId));
//					errorTypes.add(error);
//				}
			}// closing subtitle

			if(repushLiveOffersRQ.isSetGalleryPicture()){
//				if (repushLiveOffersRQ.isGalleryPicture()) {  Needs to CHECK ....
					reviseHtmlDescription = repushLiveOffersRQ.isGalleryPicture();
//			} else {
//					ErrorType error = new ErrorType();
//					error.setErrorCode(4131);
//					error.setErrorMessage(getErrorMessagesDAOImpl
//							.getErrorMessage(4131, langId));
//					errorTypes.add(error);
//					}
			}
			
			if(repushLiveOffersRQ.isSetItemPictures()){
//				if (repushLiveOffersRQ.isItemPictures()) {  Needs to CHECK ....
					reviseHtmlDescription = repushLiveOffersRQ.isItemPictures();
//				} else {
//					ErrorType error = new ErrorType();
//					error.setErrorCode(4130);
//					error.setErrorMessage(getErrorMessagesDAOImpl
//							.getErrorMessage(4130, langId));
//					errorTypes.add(error);
//					}
			}
		
			if (repushLiveOffersRQ.isSetDescription()) {
//				if (repushLiveOffersRQ.isDescription()) {  Needs to CHECK ....
					reviseHtmlDescription = repushLiveOffersRQ.isDescription();
					LOGGER.debug("HtmlDescript:::" + reviseHtmlDescription);
//				} else {
//					LOGGER.debug("HtmlDes is invalid");
//					ErrorType error = new ErrorType();
//					error.setErrorCode(4125);
//					error.setErrorMessage(getErrorMessagesDAOImpl
//							.getErrorMessage(4125, langId));
//					errorTypes.add(error);

				}// closing Description
			}
			
			
			/*
			 * Logic to insert boolean values in table.
			 */
			
			int insertionId = repushLiveOffersImplDAO.saveTemplateDetails(repushLiveOffersRQ);
	
		// closing else
		
		if (errorTypes.size() > 0) {
			repushLiveOffersRS.setAck("Failure");
			repushLiveOffersRS.setErrors(errorsType);
		} else {
			repushLiveOffersRS.setAck("Success");
		}

		repushLiveOffersRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		return repushLiveOffersRS;
	}

	/**
	 * This method is used to process the revise request
	 */
	public RepushLiveOffersRS validateRepushLiveOfferRequest(RepushLiveOffersRQ repushLiveOffersRQ) {

		RepushLiveOffersRS repushLiveOffersRS = new RepushLiveOffersRS();
		repushLiveOffersRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		String authCode = null;
		int sourceId = 0;
		int channelId = 0;
		int langId=0;
		OfferReviseRS offerReviseRS = null;
		offerReviseRS = new OfferReviseRS();
		ErrorsType errorsType = new ErrorsType();

		List<ErrorType> errorTypes = errorsType.getError();

		List<Map<String, Object>> rows = null;

		try {

			// int langId = Integer.valueOf(offerReviseRQ.getErrorLang());

			 langId = commonValidations
					.checkErrorLangCode(repushLiveOffersRQ.getErrorLang());
			LOGGER.debug("Language iD is:::" + langId);
	

				rows = commonValidations.fetchCredential(repushLiveOffersRQ
						.getAuthenticationCode());

				LOGGER.debug("Size of List is:::" + rows.size());

				if (rows.size() > 0) {
					for (Map<String, Object> credentails : rows) {
						sourceId = Integer.parseInt(credentails.get("sourceId")
								.toString());
						channelId = Integer.parseInt(credentails.get(
								"channelId").toString());
					}
				} else {
					ErrorType error = new ErrorType();
					error.setErrorCode(1100);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(1100, langId));
					errorTypes.add(error);
					repushLiveOffersRS.setAck("Failure");
					repushLiveOffersRS.setErrors(errorsType);
					return repushLiveOffersRS;

				}

				if (repushLiveOffersRQ.isSetAuthenticationCode()
						&& repushLiveOffersRQ.getAuthenticationCode() != "") {

					authCode = repushLiveOffersRQ.getAuthenticationCode();
					LOGGER.debug("AUTHCODE FROM TABLE IS 2:::" + authCode);

					LOGGER.debug("Auth Code is:::"
							+ repushLiveOffersRQ.getAuthenticationCode());
					if (!repushLiveOffersRQ.getAuthenticationCode().equals(authCode)) {
						LOGGER.debug("Invalid authentication id");
						ErrorType error = new ErrorType();
						error.setErrorCode(1100);
						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(1100, langId));
						errorTypes.add(error);
						repushLiveOffersRS.setAck("Failure");
						repushLiveOffersRS.setErrors(errorsType);
						return repushLiveOffersRS;
					}
				} else {
					LOGGER.debug("AuthCode  is missed");
					ErrorType error = new ErrorType();
					error.setErrorCode(1100);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(1100, langId));
					errorTypes.add(error);
					repushLiveOffersRS.setAck("Failure");
					repushLiveOffersRS.setErrors(errorsType);
					return repushLiveOffersRS;
				}
				
				
				if (langId == 0) {

					LOGGER.debug("Inside langId block if block");
					ErrorType error = new ErrorType();
					error.setErrorCode(1106);
					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
							1106, 2));
					repushLiveOffersRS.setAck("Failure");
					repushLiveOffersRS.setErrors(errorsType);
					return repushLiveOffersRS;

				}
				
				if (repushLiveOffersRQ.isSetTimeStamp()
						&& repushLiveOffersRQ.getTimeStamp() != "") {

					LOGGER.debug("Inside Timestamp validation...");

					if (!this.valiateRegx(repushLiveOffersRQ.getTimeStamp(),
							timeStampRegx)) {

						LOGGER.debug("Invalid TimeStamp");

						ErrorType error = new ErrorType();
						error.setErrorCode(4101);
						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(4101, langId));
						errorTypes.add(error);
						repushLiveOffersRS.setAck("Failure");
						repushLiveOffersRS.setErrors(errorsType);
						return repushLiveOffersRS;
					}

				}else{
					ErrorType error = new ErrorType();
					error.setErrorCode(4101);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(4101, langId));
					errorTypes.add(error);
					repushLiveOffersRS.setAck("Failure");
					repushLiveOffersRS.setErrors(errorsType);
					return repushLiveOffersRS;
				}

				if (repushLiveOffersRQ.isSetSourceId()) {
					if (repushLiveOffersRQ.getSourceId() != sourceId) {
						LOGGER.debug("Invalid source id");
						ErrorType error = new ErrorType();
						error.setErrorCode(1101);
						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(1101, langId));
						errorTypes.add(error);
						repushLiveOffersRS.setAck("Failure");
						repushLiveOffersRS.setErrors(errorsType);
						return repushLiveOffersRS;

					}
				} else {
					LOGGER.debug("Source id is missing");
					ErrorType error = new ErrorType();
					error.setErrorCode(1101);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(1101, langId));
					errorTypes.add(error);
					repushLiveOffersRS.setAck("Failure");
					repushLiveOffersRS.setErrors(errorsType);
					return repushLiveOffersRS;

				}

				LOGGER.debug("CHANNELID FROM TABLE IS 2:::" + channelId);
				if (repushLiveOffersRQ.isSetChannelId()) {
					if (repushLiveOffersRQ.getChannelId() != channelId) {
						ErrorType error = new ErrorType();
						error.setErrorCode(1102);
						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(1102, langId));
						errorTypes.add(error);
						repushLiveOffersRS.setAck("Failure");
						repushLiveOffersRS.setErrors(errorsType);
						return repushLiveOffersRS;
					}
				} else {
					LOGGER.debug("Channel id is mandatory");
					ErrorType error = new ErrorType();
					error.setErrorCode(1102);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(1102, langId));
					errorTypes.add(error);
					repushLiveOffersRS.setAck("Failure");
					repushLiveOffersRS.setErrors(errorsType);
					return repushLiveOffersRS;

				}

				LOGGER.debug("ObjectId setting is:::"
						+ repushLiveOffersRQ.isSetObjectId());

				if (repushLiveOffersRQ.isSetObjectId()
						&& repushLiveOffersRQ.getObjectId() != 0) {
					LOGGER.debug("OOOOObejectID:::"
							+ repushLiveOffersRQ.getObjectId());
					boolean objectIdStatus = commonValidations
							.checkObjectId(repushLiveOffersRQ.getObjectId());
					LOGGER.debug("OBjectID status:::"+objectIdStatus);
					
					if (objectIdStatus == false) {
						LOGGER.debug("Object id is invalid");
						ErrorType error = new ErrorType();
						error.setErrorCode(1103);
						error.setErrorMessage(getErrorMessagesDAOImpl
								.getErrorMessage(1103, langId));
						errorTypes.add(error);
						repushLiveOffersRS.setAck("Failure");
						repushLiveOffersRS.setErrors(errorsType);
						return repushLiveOffersRS;
					}
//					if (errorTypes.size() > 0) {
//						repushLiveOffersRS.setAck("Failure");
//						repushLiveOffersRS.setErrors(errorsType);
//					}
				}else{
					LOGGER.debug("Object id is invalid");
					ErrorType error = new ErrorType();
					error.setErrorCode(1103);
					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(1103, langId));
					errorTypes.add(error);
					repushLiveOffersRS.setAck("Failure");
					repushLiveOffersRS.setErrors(errorsType);
					return repushLiveOffersRS;
				}
				
				if(repushLiveOffersRQ.isSetTemplateId()
						&& repushLiveOffersRQ.getTemplateId() != 0){
				boolean templateidStatus=repushLiveOffersImplDAO.checkTemplateId(repushLiveOffersRQ.getTemplateId(),repushLiveOffersRQ.getObjectId());
				
				if(templateidStatus){
				boolean liveOffersCheck=	repushLiveOffersImplDAO.checkLiveOffers(repushLiveOffersRQ.getTemplateId());
				if(!liveOffersCheck){
					ErrorType error = new ErrorType();
					error.setErrorCode(11008);
					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(11008, langId));
					errorTypes.add(error);
					repushLiveOffersRS.setAck("Failure");
					repushLiveOffersRS.setErrors(errorsType);
					return repushLiveOffersRS;
				}
				}
				
				if(!templateidStatus){
				ErrorType error = new ErrorType();
				error.setErrorCode(2128);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(2128, langId));
				errorTypes.add(error);
				repushLiveOffersRS.setAck("Failure");
				repushLiveOffersRS.setErrors(errorsType);
				return repushLiveOffersRS;
				}
				}else{
					ErrorType error = new ErrorType();
					error.setErrorCode(2113);
					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(2113, langId));
					errorTypes.add(error);
					repushLiveOffersRS.setAck("Failure");
					repushLiveOffersRS.setErrors(errorsType);
					return repushLiveOffersRS;
				}
				
			//}
				
				boolean liveFixedPriceOffers=repushLiveOffersImplDAO.checkFixedPriceLiveOffers(repushLiveOffersRQ.getTemplateId());
				
				if(liveFixedPriceOffers){
					ErrorType error = new ErrorType();
					error.setErrorCode(11009);
					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(11009, langId));
					errorTypes.add(error);
					repushLiveOffersRS.setAck("Failure");
					repushLiveOffersRS.setErrors(errorsType);
					return repushLiveOffersRS;
				}
				
				//public boolean checkLiveOffers(int templateid);
			
			if(repushLiveOffersRQ.isSetTitle()){
				/*if(repushLiveOffersRQ.isTitle()==false){
				ErrorType error = new ErrorType();
				error.setErrorCode(11001);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(11001, langId));
				errorTypes.add(error);
				}*/
			}
			if(repushLiveOffersRQ.isSetSubTitle()){
				/*if(repushLiveOffersRQ.isSubTitle()==false){
				ErrorType error = new ErrorType();
				error.setErrorCode(11002);
				error.setErrorMessage(getErrorMessagesDAOImpl
						.getErrorMessage(11002, langId));
				errorTypes.add(error);
				}*/
			}
			
			if(repushLiveOffersRQ.isSetGalleryPicture()){
				/*if(repushLiveOffersRQ.isGalleryPicture()==false){
				ErrorType error = new ErrorType();
				error.setErrorCode(11003);
				error.setErrorMessage(getErrorMessagesDAOImpl
						.getErrorMessage(11003, langId));
				errorTypes.add(error);
				}*/
			}
			
			if(repushLiveOffersRQ.isSetItemPictures()){
				/*if(repushLiveOffersRQ.isItemPictures()==false){
				ErrorType error = new ErrorType();
				error.setErrorCode(11004);
				error.setErrorMessage(getErrorMessagesDAOImpl
						.getErrorMessage(11004, langId));
				errorTypes.add(error);
				}*/
			}
			
			if(repushLiveOffersRQ.isSetCategoriesAndAttributes()){
				/*if(repushLiveOffersRQ.isCategoriesAndAttributes()==false){
				ErrorType error = new ErrorType();
				error.setErrorCode(11006);
				error.setErrorMessage(getErrorMessagesDAOImpl
						.getErrorMessage(11006, langId));
				errorTypes.add(error);
				}*/
				
			}
			if(repushLiveOffersRQ.isSetDescription()){
				/*if(repushLiveOffersRQ.isDescription()==false){
				ErrorType error = new ErrorType();
				error.setErrorCode(11005);
				error.setErrorMessage(getErrorMessagesDAOImpl
						.getErrorMessage(11005, langId));
				errorTypes.add(error);
				}*/
			}
			
			if(repushLiveOffersRQ.isSetRepushTime()){
				
				if (!this.valiateRegx(repushLiveOffersRQ.getRepushTime(),
						timeStampRegx)) {

					LOGGER.debug("Invalid TimeStamp");

					ErrorType error = new ErrorType();
					error.setErrorCode(11007);
					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(11007, langId));
					errorTypes.add(error);
					
				}else if(!this.validateStartTime(repushLiveOffersRQ.getRepushTime())){
					ErrorType error = new ErrorType();
					error.setErrorCode(11007);
					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(11007, langId));
					errorTypes.add(error);
				}else if(this.validateStartTime(repushLiveOffersRQ.getRepushTime())){
				String endDate=repushLiveOffersImplDAO.getMaxdate(repushLiveOffersRQ.getTemplateId());
				LOGGER.debug("End date value is "+endDate);
				
					if(endDate!=null && endDate.equals("GTC")){
						
					}else if(endDate !=null){
						LOGGER.debug("End date value is ==="+endDate+" Repush time isss==="+repushLiveOffersRQ.getRepushTime());
						if(repushLiveOffersRQ.getRepushTime().compareTo(endDate)>=0){
							ErrorType error = new ErrorType();
							error.setErrorCode(11010);
							error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(11010, langId));
							errorTypes.add(error);
						}
						
					}
				
				}
				
			}else{
				ErrorType error = new ErrorType();
				error.setErrorCode(11007);
				error.setErrorMessage(getErrorMessagesDAOImpl
						.getErrorMessage(11007, langId));
				errorTypes.add(error);
			}
			
			
			
			if (errorTypes.size() > 0) {
				repushLiveOffersRS.setAck("Failure");
				repushLiveOffersRS.setErrors(errorsType);
			}else {
				repushLiveOffersRS.setAck("Success");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return repushLiveOffersRS;
	}

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

	public boolean validateStartTime(String StartTime){
		
		LOGGER.debug("start time in validation is:{}",StartTime);
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		boolean dateFlag = false;
		
		try{
		
		Date dateObj1 = sdf.parse(StartTime);
		Date dateObj2 = new Date();
		LOGGER.debug("start time from xml after parsing is:{}",dateObj1);
		LOGGER.debug("now date is:{}",dateObj2);
		
		long diff = dateObj2.getTime() - dateObj1.getTime();
		LOGGER.debug("diff time is:{}",diff);
		
		int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
		LOGGER.debug("diffDays is:{}",diffDays);
		
		int diffhours = (int) (diff / (60 * 60 * 1000));
		LOGGER.debug("diffHours is:{}",diffhours);
		
		int diffmin = (int) (diff / (60 * 1000));
        LOGGER.debug("min is :{}",diffmin);
		
		if(diffDays <= 0 && diffmin <= 0){
			
			dateFlag = true;
		}
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		return dateFlag;
	}
}
