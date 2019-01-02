package com.cultuzz.channel.helper.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cultuzz.channel.DAO.ObjectConfigurationDAO;
import com.cultuzz.channel.DAO.OfferCreationDAO;
import com.cultuzz.channel.DAO.TemplateModuleDAO;
import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.XMLpojo.AdditionalType;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.XMLpojo.FAQType;
import com.cultuzz.channel.XMLpojo.InformationsTypes;
import com.cultuzz.channel.XMLpojo.MapType;
import com.cultuzz.channel.XMLpojo.ObjectConfigurationDetailsRQ;
import com.cultuzz.channel.XMLpojo.ObjectConfigurationDetailsRS;
import com.cultuzz.channel.XMLpojo.Objsliderpics;
import com.cultuzz.channel.XMLpojo.PaymentOptionType;
import com.cultuzz.channel.XMLpojo.PaymentOptionsType;
import com.cultuzz.channel.XMLpojo.SettingType;
import com.cultuzz.channel.XMLpojo.SettingsType;
import com.cultuzz.channel.XMLpojo.TermsAndConditionsType;
import com.cultuzz.channel.XMLpojo.TermsAndConditionsType.CheckoutService;
import com.cultuzz.channel.XMLpojo.TermsAndConditionsType.PaymentService;
import com.cultuzz.channel.XMLpojo.TermsAndConditionsType.StandardBusinessTerms;
import com.cultuzz.channel.XMLpojo.TermsAndConditionsType.VoucherService;
import com.cultuzz.channel.XMLpojo.ValueType;
import com.cultuzz.channel.XMLpojo.VoucherType;
import com.cultuzz.channel.helper.ObjectConfigurationDetailsHelper;
import com.cultuzz.channel.util.CommonValidations;

@Repository
public class ObjectConfigurationDetailsHelperImpl implements ObjectConfigurationDetailsHelper{
	
	@Autowired
	TemplateModuleDAO templateModuleDAO;
	
	@Autowired
	CommonValidations commonValidations;
	
	@Autowired
	GetErrorMessagesDAOImpl getErrormessages;
	
	@Autowired
	OfferCreationDAO offerCreationDAO;
	
	@Autowired
	ObjectConfigurationDAO objectConfigurationDAO;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ObjectConfigurationDetailsHelperImpl.class);
	
	public ObjectConfigurationDetailsRS validateObjectConfigurationDetailsRq(ObjectConfigurationDetailsRQ objectConfigurationDetailsRq){
		ObjectConfigurationDetailsRS objectConfigurationDetailsRS=new ObjectConfigurationDetailsRS();
		
		objectConfigurationDetailsRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		
		int objectid=0;
		ErrorsType error=new ErrorsType();
		List<ErrorType> errorsType=error.getError();
		int langid=0;
		
		if(objectConfigurationDetailsRq.isSetAuthenticationCode()){
			
			boolean authCodeStatus=	commonValidations.checkAuthCode(objectConfigurationDetailsRq.getAuthenticationCode());
				if(!authCodeStatus){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1100);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,2));
					errorsType.add(errorType);
					objectConfigurationDetailsRS.setErrors(error);
					objectConfigurationDetailsRS.setAck("Failure");
					return objectConfigurationDetailsRS;
				}
					
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1100);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,2));
				errorsType.add(errorType);
				objectConfigurationDetailsRS.setErrors(error);
				objectConfigurationDetailsRS.setAck("Failure");
				return objectConfigurationDetailsRS;
				
			}
		
		if(objectConfigurationDetailsRq.isSetErrorLang()){
			
			langid=commonValidations.checkErrorLangCode(objectConfigurationDetailsRq.getErrorLang());
			
			if(langid>0){
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1106);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,2));
				errorsType.add(errorType);
				objectConfigurationDetailsRS.setErrors(error);
				objectConfigurationDetailsRS.setAck("Failure");
				return objectConfigurationDetailsRS;
			}
			
		}else{				
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1106);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,2));
			errorsType.add(errorType);
			objectConfigurationDetailsRS.setErrors(error);
			objectConfigurationDetailsRS.setAck("Failure");
			return objectConfigurationDetailsRS;
		}
		
		if(objectConfigurationDetailsRq.isSetTimeStamp() && !objectConfigurationDetailsRq.getTimeStamp().trim().isEmpty()){
			
			boolean timestampStatus = commonValidations.checkTimeStamp(objectConfigurationDetailsRq.getTimeStamp());
			if(!timestampStatus){	
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1104);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1104,langid));
				errorsType.add(errorType);
				objectConfigurationDetailsRS.setErrors(error);
				objectConfigurationDetailsRS.setAck("Failure");
				return objectConfigurationDetailsRS;
			}
			
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1105);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1105,langid));
				errorsType.add(errorType);
				objectConfigurationDetailsRS.setErrors(error);
				objectConfigurationDetailsRS.setAck("Failure");
				return objectConfigurationDetailsRS;
			}
		
		if(objectConfigurationDetailsRq.isSetSourceId() ){
			if(!commonValidations.checkSourceId(objectConfigurationDetailsRq.getSourceId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1101);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langid));
				errorsType.add(errorType);
				objectConfigurationDetailsRS.setErrors(error);
				objectConfigurationDetailsRS.setAck("Failure");
				return objectConfigurationDetailsRS;
			}
		
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1101);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langid));
			errorsType.add(errorType);
			objectConfigurationDetailsRS.setErrors(error);
			objectConfigurationDetailsRS.setAck("Failure");
			return objectConfigurationDetailsRS;
			
		}
		
		if(objectConfigurationDetailsRq.isSetChannelId()){
			
			if(!commonValidations.checkChannelId(objectConfigurationDetailsRq.getChannelId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1102);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langid));
				errorsType.add(errorType);
				objectConfigurationDetailsRS.setErrors(error);
				objectConfigurationDetailsRS.setAck("Failure");
				return objectConfigurationDetailsRS;
			}
			
		}else{
			
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1102);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langid));
			errorsType.add(errorType);
			objectConfigurationDetailsRS.setErrors(error);
			objectConfigurationDetailsRS.setAck("Failure");
			return objectConfigurationDetailsRS;
			
		}
		
		
		if(objectConfigurationDetailsRq.isSetObjectId()){
			
			LOGGER.debug("Checking objectid");
			if(!commonValidations.checkObjectId(objectConfigurationDetailsRq.getObjectId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1103);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
				errorsType.add(errorType);
				objectConfigurationDetailsRS.setErrors(error);
				objectConfigurationDetailsRS.setAck("Failure");
				return objectConfigurationDetailsRS;
			}else{
				
				objectid=objectConfigurationDetailsRq.getObjectId();
				
				boolean ebaydatenStatus=objectConfigurationDAO.checkEbayDaten(objectid);
				if(!ebaydatenStatus){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1107);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1107,langid));
					errorsType.add(errorType);
					objectConfigurationDetailsRS.setErrors(error);
					objectConfigurationDetailsRS.setAck("Failure");
					return objectConfigurationDetailsRS;
				}
			}
			
		}else{
			
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1103);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
			errorsType.add(errorType);
			objectConfigurationDetailsRS.setErrors(error);
			objectConfigurationDetailsRS.setAck("Failure");
			return objectConfigurationDetailsRS;
			
		}
		
		objectConfigurationDetailsRS.setAck("Success");
		return objectConfigurationDetailsRS;
	}
	public ObjectConfigurationDetailsRS processObjectConfigurationDetailsRq(ObjectConfigurationDetailsRQ objectConfigurationDetailsRq){
		ObjectConfigurationDetailsRS objectConfigurationDetailsRS=new ObjectConfigurationDetailsRS();
		objectConfigurationDetailsRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		int objectid=objectConfigurationDetailsRq.getObjectId();
		
		objectConfigurationDetailsRS.setObjectId(objectid);
		int voucherTemplateid=objectConfigurationDAO.checkGutscheinXObjekt(objectid);
		if(voucherTemplateid>0){
		VoucherType vocuherType=new VoucherType();
		vocuherType.setVoucherPdfTemplateId(voucherTemplateid);
		Map<String,Object> voucherconfData=objectConfigurationDAO.getVoucherConfData(objectid);
		if(voucherconfData!=null){
		vocuherType.setVoucherLogoPictureURL(voucherconfData.get("logopath").toString());
		vocuherType.setVoucherPictureURL(voucherconfData.get("imagepath").toString());
		}
		objectConfigurationDetailsRS.setVoucher(vocuherType);
		}
		
		String reputizeToken=objectConfigurationDAO.checkTokenExistance(objectid);
		//System.out.println(reputizeToken);
		if(reputizeToken!=null){
		SettingsType sstype=new SettingsType();
		SettingType stype=new SettingType();
		stype.setKey(reputizeToken);
		stype.setName("Reputize");
		List<SettingType> settingTypeList=sstype.getSetting();
		settingTypeList.add(stype);
		objectConfigurationDetailsRS.setSettings(sstype);
		}
		
		String logopictureUrl=objectConfigurationDAO.getOLogoImagePath(objectid);
		if(logopictureUrl!=null){
			objectConfigurationDetailsRS.setLogoPictureURL(logopictureUrl);
		}
		
		List<String> objSliderpicsData=objectConfigurationDAO.getObjectSliderPics(objectid);
		if(objSliderpicsData!=null){
		Objsliderpics objSliderpics=new Objsliderpics();
		List<String> objSliderPicsList=objSliderpics.getObjectSliderPictureURL();
		objSliderPicsList.clear();
		objSliderPicsList.addAll(objSliderpicsData);
		//objSliderPicsList.
		objectConfigurationDetailsRS.setObjectSlider(objSliderpics);
		}
		
		Map<String,Object> mapcoordinates=objectConfigurationDAO.getMapCoordinates(objectid);
		
		if(mapcoordinates!=null){
			//latitude,logititude
			MapType mapType=new MapType();
			mapType.setLatitude(mapcoordinates.get("latitude").toString());
			mapType.setLongitude(mapcoordinates.get("logititude").toString());
			
			int zoomLevel=objectConfigurationDAO.getZoomLevel(objectid);
			if(zoomLevel!=0){
				mapType.setZoomLevel(zoomLevel);
			}
			objectConfigurationDetailsRS.setMap(mapType);
		}
		
		
		List<Map<String,Object>> paymentDataList=objectConfigurationDAO.getPaymentData(objectid);
		if(paymentDataList!=null){
			String status="false";
		PaymentOptionsType paymentOptions=new PaymentOptionsType();
		List<PaymentOptionType> paymentOptionTypeList=paymentOptions.getPaymentOption();
		for(Map<String,Object> popmap:paymentDataList){
			int paymentStatus=Integer.parseInt(popmap.get("status").toString());
			
			if(paymentStatus==1)
					status="true";
			
		PaymentOptionType paymentOptionType=new PaymentOptionType();
		
		paymentOptionType.setMarketPlace(popmap.get("ebaysiteid").toString());
		paymentOptionType.setPaymentId(popmap.get("paypal_email").toString());
		paymentOptionType.setPaymentType("Paypal");
		boolean changestatus=objectConfigurationDAO.checkFutureOffersLiveSiteId(popmap.get("ebaysiteid").toString(),objectid);
		if(changestatus)
		paymentOptionType.setChangePaymentId("No");
		else
			paymentOptionType.setChangePaymentId("Yes");
		paymentOptionTypeList.add(paymentOptionType);
		}
		paymentOptions.setPaymentToHotelier(status);
		objectConfigurationDetailsRS.setPaymentOptions(paymentOptions);
		}
		
		List<Map<String,Object>> faqDataList=objectConfigurationDAO.getFaqData(objectid, 1);
		List<Map<String,Object>> tacDefaultList=objectConfigurationDAO.getDefaultTACData(objectid, 1);
		List<Map<String,Object>> tacAdditionalDataList=objectConfigurationDAO.getAdditionalTACData(objectid, 1);
		List<Map<String,Object>> faqDataEngList=objectConfigurationDAO.getFaqData(objectid, 2);
		List<Map<String,Object>> tacDefaultEngList=objectConfigurationDAO.getDefaultTACData(objectid, 2);
		List<Map<String,Object>> tacAdditionalDataEngList=objectConfigurationDAO.getAdditionalTACData(objectid, 2);
		boolean infoTypesStatus=false;
		
		
		if(faqDataList!=null || tacDefaultList!=null || tacAdditionalDataList!=null || faqDataEngList!=null ||  tacDefaultEngList!=null || tacAdditionalDataEngList!=null)
			infoTypesStatus=true;
		
		if(infoTypesStatus){
		InformationsTypes informationsTypes=new InformationsTypes();
		
		if(faqDataList!=null || faqDataEngList!=null){
			
				FAQType faqType=new FAQType();
				List<ValueType> faqValueType=faqType.getValue();
				if(faqDataList!=null){
					for(Map<String,Object> germanFaq:faqDataList){
						ValueType vt=new ValueType();
						vt.setLanguage("de");
						if(germanFaq.get("fragetext")!=null)
						vt.setName(germanFaq.get("fragetext").toString());
						if(germanFaq.get("antworttext")!=null)
						vt.setValue("<![CDATA["+germanFaq.get("antworttext").toString()+"]]>");
						
						faqValueType.add(vt);
					}
				}
				if(faqDataEngList!=null){
					for(Map<String,Object> engFaq:faqDataEngList){
						ValueType vt=new ValueType();
						vt.setLanguage("en");
						if(engFaq.get("fragetext")!=null)
						vt.setName(engFaq.get("fragetext").toString());
						if(engFaq.get("antworttext")!=null)
						vt.setValue("<![CDATA["+engFaq.get("antworttext").toString()+"]]>");
						
						faqValueType.add(vt);
					}
				}
				informationsTypes.setFAQ(faqType);
		}
		
		if(tacDefaultList!=null || tacAdditionalDataList!=null || tacDefaultEngList!=null || tacAdditionalDataEngList!=null){
		TermsAndConditionsType tactType=new TermsAndConditionsType();
		
		List<StandardBusinessTerms> standardBusinessList=tactType.getStandardBusinessTerms();
		List<CheckoutService> checkoutService=tactType.getCheckoutService();
		List<PaymentService> paymentServiceList=tactType.getPaymentService();
		List<VoucherService> voucherServiceList=tactType.getVoucherService();
		
		if(tacDefaultList!=null){
		
			for(Map<String,Object> tacMap:tacDefaultList){
				if(tacMap.get("pos_id")!=null){
					int positionid=0;
					try{
						positionid=Integer.parseInt(tacMap.get("pos_id").toString());
					}catch(Exception tace){
						LOGGER.error("exception occured in TAC position{}",tace);
					}
					if(positionid==3){
						
						StandardBusinessTerms sbt=new StandardBusinessTerms();
						sbt.setLanguage("de");
						if(tacMap.get("des")!=null)
						sbt.setValue("<![CDATA["+tacMap.get("des").toString()+"]]>");
						standardBusinessList.add(sbt);
					}else if(positionid==4){
						
						CheckoutService checkoutSer=new CheckoutService();
						checkoutSer.setLanguage("de");
						if(tacMap.get("des")!=null)
							checkoutSer.setValue("<![CDATA["+tacMap.get("des").toString()+"]]>");
						checkoutService.add(checkoutSer);
					}else if(positionid==5){
						
						PaymentService paymentServ=new PaymentService();
						paymentServ.setLanguage("de");
						if(tacMap.get("des")!=null)
							paymentServ.setValue("<![CDATA["+tacMap.get("des").toString()+"]]>");
						paymentServiceList.add(paymentServ);
					}else if(positionid==6){
						
						VoucherService vService=new VoucherService();
						vService.setLanguage("de");
							if(tacMap.get("des")!=null)
								vService.setValue("<![CDATA["+tacMap.get("des").toString()+"]]>");
							
							voucherServiceList.add(vService);
					}
				}
			}
		}
		if(tacDefaultEngList!=null){
			for(Map<String,Object> tacEngMap:tacDefaultEngList){
				if(tacEngMap.get("pos_id")!=null){
					int positionid=0;
					try{
						positionid=Integer.parseInt(tacEngMap.get("pos_id").toString());
					}catch(Exception tace){
						LOGGER.error("exception occured in TAC position{}",tace);
					}
					if(positionid==3){
						
						StandardBusinessTerms sbt=new StandardBusinessTerms();
						sbt.setLanguage("en");
						if(tacEngMap.get("des")!=null)
						sbt.setValue("<![CDATA["+tacEngMap.get("des").toString()+"]]>");
						standardBusinessList.add(sbt);
					}else if(positionid==4){
						
						CheckoutService checkoutSer=new CheckoutService();
						checkoutSer.setLanguage("en");
						if(tacEngMap.get("des")!=null)
							checkoutSer.setValue("<![CDATA["+tacEngMap.get("des").toString()+"]]>");
						checkoutService.add(checkoutSer);
					}else if(positionid==5){
						
						PaymentService paymentServ=new PaymentService();
						paymentServ.setLanguage("en");
						if(tacEngMap.get("des")!=null)
							paymentServ.setValue("<![CDATA["+tacEngMap.get("des").toString()+"]]>");
						paymentServiceList.add(paymentServ);
					}else if(positionid==6){
						
						VoucherService vService=new VoucherService();
						vService.setLanguage("en");
							if(tacEngMap.get("des")!=null)
								vService.setValue("<![CDATA["+tacEngMap.get("des").toString()+"]]>");
							
							voucherServiceList.add(vService);
					}
				}
			}
		}
		if(tacAdditionalDataList!=null || tacAdditionalDataEngList!=null){
				AdditionalType addValues=new AdditionalType();
				List<ValueType> addValueType=addValues.getValue();
				
				if(tacAdditionalDataList!=null){
					for(Map<String,Object> tacAdditional:tacAdditionalDataList){
						ValueType addValue=new ValueType();
						addValue.setLanguage("de");
						if(tacAdditional.get("HeadlineText")!=null)
						addValue.setName(tacAdditional.get("HeadlineText").toString());
						if(tacAdditional.get("BodyText")!=null)
						addValue.setValue("<![CDATA["+tacAdditional.get("BodyText").toString()+"]]>");
						addValueType.add(addValue);
					}
				}
				
				if(tacAdditionalDataEngList!=null){
					for(Map<String,Object> tacEngAdditional:tacAdditionalDataEngList){
						ValueType addValue=new ValueType();
						addValue.setLanguage("en");
						if(tacEngAdditional.get("HeadlineText")!=null)
						addValue.setName(tacEngAdditional.get("HeadlineText").toString());
						if(tacEngAdditional.get("BodyText")!=null)
						addValue.setValue("<![CDATA["+tacEngAdditional.get("BodyText").toString()+"]]>");
						
						addValueType.add(addValue);
					}
				}
				
				tactType.setAdditionalValues(addValues);
		}
		informationsTypes.setTermsAndConditions(tactType);
		}
		
		objectConfigurationDetailsRS.setInformationTypes(informationsTypes);
		}
		
		objectConfigurationDetailsRS.setAck("Success");
		return objectConfigurationDetailsRS;
	}

}
