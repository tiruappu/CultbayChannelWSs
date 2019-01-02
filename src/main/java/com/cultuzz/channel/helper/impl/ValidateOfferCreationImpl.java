package com.cultuzz.channel.helper.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.DAO.OfferCreationDAO;
import com.cultuzz.channel.DAO.TemplateModuleDAO;

import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.XMLpojo.ListOfOffersRQ;
import com.cultuzz.channel.XMLpojo.ListOfOffersRS;
import com.cultuzz.channel.XMLpojo.OfferCreationRQ;
import com.cultuzz.channel.XMLpojo.OfferCreationRS;
import com.cultuzz.channel.XMLpojo.OfferDetailsRQ;
import com.cultuzz.channel.XMLpojo.OfferDetailsRS;
import com.cultuzz.channel.helper.OfferValidator;
import com.cultuzz.channel.util.CommonValidations;
import com.cultuzz.channel.util.DateUtil;

/* In this we validate the request xmls.*/

@Component
@Qualifier("offerCreationValidator")
public class ValidateOfferCreationImpl implements OfferValidator {
	
	@Autowired
	GetErrorMessagesDAOImpl getErrorMessagesDAOImpl;
	
	@Autowired
	TemplateModuleDAO templateModuleDAOImpl;
	
	@Autowired
	OfferCreationDAO offerCreationDAOImpl;
	
	@Autowired
	CommonValidations commonValidations;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidateOfferCreationImpl.class);
	
	private static final String timeStampRegx = "^[2-9]{1}[0-9]{3}-(([0]{1}[1-9]{1})|([1]{1}[0-2]{1}))-(([0]{1}[0-9]{1})|([1-2]{1}[0-9]{1})|([3]{1}[0-1]{1})) (([0]{1}[0-9]{1})|([1]{1}[0-9]{1})|([2]{1}[0-3]{1})):(([0]{1}[0-9]{1})|([1-5]{1}[0-9]{1})):(([0]{1}[0-9]{1})|([1-5]{1}[0-9]{1}))";

	// This method used to validate the OfferCreationRQ 
	public OfferCreationRS validateOfferCreation(OfferCreationRQ offerCreationRQ) {
		// TODO Auto-generated method stub
		
	     LOGGER.info("Inside the offerCreation validation");
	     
	     OfferCreationRS offerCreationRS = null;
	     int langId = 0;
	     List<Map<String,Object>> rows = null;
	        String authCode =null;
	        int channelId = 0;
	        int sourceId=0;
		
	    try{
	    	
	    	offerCreationRS = new OfferCreationRS();
      	  
	    	  ErrorsType errorsType = new ErrorsType();
				List<ErrorType> errorType= errorsType.getError();
      		
				langId = getErrorMessagesDAOImpl.getLanguageId(offerCreationRQ.getErrorLang());
				LOGGER.debug("langId is in validation:{}",langId);
				
				offerCreationRS.setTimeStamp(DateUtil.convertDateToString(new Date()));
				if(langId >0){
				
					LOGGER.debug("languageId is:{}",langId);
				
	    	
	    	if(offerCreationRQ == null){
	    		 ErrorType error = new ErrorType();
					error.setErrorCode(3131);
					
					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3131, langId));
					errorType.add(error);
	    	}else{
	    		
	    		if(offerCreationRQ.isSetTimeStamp() && !offerCreationRQ.getTimeStamp().isEmpty() && offerCreationRQ.getTimeStamp()!=null){
	    			if (!this.valiateRegx(
  							offerCreationRQ.getTimeStamp(),
  							timeStampRegx)) {
	    				ErrorType error = new ErrorType();
      					error.setErrorCode(1104);
      					
      					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1104, langId));
      					errorType.add(error);
      					offerCreationRS.setErrors(errorsType);
      					offerCreationRS.setAck("Failure");
      					return offerCreationRS;
	    		}
	    		}else{
	    			
	    			ErrorType error = new ErrorType();
						error.setErrorCode(1105);
				
						error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1105, langId));
						errorType.add(error);
						offerCreationRS.setErrors(errorsType);
      					offerCreationRS.setAck("Failure");
      					return offerCreationRS;
	    		}

	    		if(offerCreationRQ.isSetAuthenticationCode() && !offerCreationRQ.getAuthenticationCode().isEmpty() && offerCreationRQ.getAuthenticationCode()!=null){
	    			
	    			LOGGER.debug("Authentication code is :{}",offerCreationRQ.getAuthenticationCode());
	    			
	    			 rows = commonValidations.fetchCredential(offerCreationRQ.getAuthenticationCode());
				        
				        LOGGER.debug("Size of List is:::"+rows.size());
				        
				        if(rows.size()>0){
				        for(Map<String, Object> credentails : rows){
//				                 authCode = credentails.get("auth_code").toString();
				                 sourceId = Integer.parseInt(credentails.get("sourceId").toString());
				                 channelId = Integer.parseInt(credentails.get("channelId").toString());
				        }
				        }else{
				        	
				        	ErrorType error = new ErrorType();
							error.setErrorCode(1100);
							error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1100, langId));
							errorType.add(error);
							offerCreationRS.setErrors(errorsType);
		  					offerCreationRS.setAck("Failure");
		  					return offerCreationRS;
				        }
	    		}else{
	    			 ErrorType error = new ErrorType();
 					error.setErrorCode(1100);
 					
 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1100, langId));
 					errorType.add(error);
 					offerCreationRS.setErrors(errorsType);
  					offerCreationRS.setAck("Failure");
  					return offerCreationRS;
	    		}
	    		
	    		
	    		if(offerCreationRQ.isSetSourceId() && offerCreationRQ.getSourceId()==sourceId){
					
					LOGGER.debug("source Id is :{}",offerCreationRQ.getSourceId());
					
					  
				}else{
					
					ErrorType error = new ErrorType();
					error.setErrorCode(1101);
					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1101, langId));
					errorType.add(error);
					offerCreationRS.setErrors(errorsType);
  					offerCreationRS.setAck("Failure");
  					return offerCreationRS;
				}

	    		
	    		
	    		if(offerCreationRQ.isSetChannelId() && offerCreationRQ.getChannelId()==channelId){
	    			
	    			LOGGER.debug("channelId is :{}",offerCreationRQ.getChannelId());
	    		}else{
	    			  ErrorType error = new ErrorType();
	  					error.setErrorCode(1102);
	  					
	  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1102, langId));
	  					errorType.add(error);
	  					offerCreationRS.setErrors(errorsType);
      					offerCreationRS.setAck("Failure");
      					return offerCreationRS;
	    		}
	    		
	    		if(offerCreationRQ.isSetObjectId() && offerCreationRQ.getObjectId() >0){
	    			
	    			LOGGER.debug("object id is :{}",offerCreationRQ.getObjectId());
	    			if(templateModuleDAOImpl.checkObjectId(offerCreationRQ.getObjectId())){
	    				
	    				LOGGER.debug("objectId is valid");
	    			}else{
	    				
	    				 ErrorType error = new ErrorType();
	  					error.setErrorCode(3108);
	  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3108, langId));
	  					errorType.add(error);
	  					offerCreationRS.setErrors(errorsType);
      					offerCreationRS.setAck("Failure");
      					return offerCreationRS;
	    			}
	    		}else{
	    			
	    			ErrorType error = new ErrorType();
 					error.setErrorCode(3108);
 					
 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3108, langId));
 					errorType.add(error);
 					offerCreationRS.setErrors(errorsType);
  					offerCreationRS.setAck("Failure");
  					return offerCreationRS;
	    		}
	    		
	    		if(offerCreationRQ.isSetTemplateId() && offerCreationRQ.getTemplateId()>0){
	    			
	    			LOGGER.debug("template id is:{}",offerCreationRQ.getTemplateId());
	    			if(templateModuleDAOImpl.checkTemplateId(offerCreationRQ.getTemplateId(),offerCreationRQ.getObjectId())){
	    				
	    				LOGGER.debug("templateId is valid");
	    				//check here this template id contains live or feature offers
	    				List<Map<String,Object>> offersList=offerCreationDAOImpl.getOffersData(offerCreationRQ.getObjectId(), offerCreationRQ.getTemplateId());
	    				if(offersList!=null && offersList.size()>0){
	    					ErrorType error = new ErrorType();
		 					error.setErrorCode(3165);
		 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3165, langId));
		 					errorType.add(error);
	    				}
	    			}else{
	    				
	    				ErrorType error = new ErrorType();
	 					error.setErrorCode(3109);
	 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3109, langId));
	 					errorType.add(error);
	    			}
	    		}else{
	    			
	    			 ErrorType error = new ErrorType();
 					error.setErrorCode(3109);
 					
 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3109, langId));
 					errorType.add(error);
	    		}
	    		

	    		if(offerCreationRQ.isSetSiteId() && offerCreationRQ.getSiteId()!=null){
	    			
	    			LOGGER.debug("site id is :{}",offerCreationRQ.getSiteId());
	    			if(templateModuleDAOImpl.checkSiteId(offerCreationRQ.getSiteId())){
	    				
	    				LOGGER.debug("siteId is valid");
	    				
	    				  LOGGER.debug("template Id in site validation is:{}",offerCreationRQ.getTemplateId());
	    		
	    				  if(offerCreationRQ.getTemplateId()>0){
	    				  String templateSiteId = templateModuleDAOImpl.getTemplateSiteid
		 							(offerCreationRQ.getTemplateId());
	    				  LOGGER.debug("template site id is:{}",templateSiteId);
	    				  
	    				  if(templateSiteId !=null){
	    				if(offerCreationRQ.getSiteId() == Integer.parseInt(templateSiteId)){
	    					 
	    					 LOGGER.debug("template site id is valid with offer creation siteId");
	    				 }else{
	    					 
	    					 ErrorType error = new ErrorType();
		 					 error.setErrorCode(3153);
		 					
		 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3153, langId));
		 					errorType.add(error);
	    				 }}}
	    			}else{
	    				
	    				ErrorType error = new ErrorType();
	 					 error.setErrorCode(3116);
	 					
	 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3116, langId));
	 					errorType.add(error);
	    			}
	    		}else{
	    			
	    			 ErrorType error = new ErrorType();
 					 error.setErrorCode(3116);
 					
 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3116, langId));
 					errorType.add(error);
	    		}
	    		
	    		if(offerCreationRQ.isSetQuantity() && offerCreationRQ.getQuantity()>0 && offerCreationRQ.getQuantity()<=449900){
	    			
	    			LOGGER.debug("quantity is :{}",offerCreationRQ.getQuantity());
	    			
	    			if(offerCreationRQ.getListingType()!=null && offerCreationRQ.getListingType().compareToIgnoreCase("Auction")==0){
	    				
	    				if(offerCreationRQ.getQuantity()==1){
		    				
		    				LOGGER.debug("quantity after type checking  is :{}",offerCreationRQ.getQuantity());
		    			}else{
		    				
		    				ErrorType error = new ErrorType();
		 					error.setErrorCode(3137);
		 					
		 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3137, langId));
		 					errorType.add(error);
		    			}
	    			}
	    			
	    		}else{
	    			 ErrorType error = new ErrorType();
 					error.setErrorCode(3113);
 					
 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3113, langId));
 					errorType.add(error);
	    		}
	    		if(offerCreationRQ.isSetListingType() && !offerCreationRQ.getListingType().isEmpty() && offerCreationRQ.getListingType()!=null){
	    			
	    			LOGGER.debug("listing type is:{}",offerCreationRQ.getListingType());
	    			if(offerCreationRQ.getListingType().compareToIgnoreCase("Auction")==0 ||
	    					offerCreationRQ.getListingType().compareToIgnoreCase("Fixed Price Offer")==0){
	    				
	    				LOGGER.debug("listing type is valid");
	    				
	    			}else{
	    				
	    				ErrorType error = new ErrorType();
	 					error.setErrorCode(3117);
	 					
	 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3117, langId));
	 					errorType.add(error);
	    			}
	    		}else{
	    			 ErrorType error = new ErrorType();
 					error.setErrorCode(3117);
 					
 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3117, langId));
 					errorType.add(error);
	    		}
	    		
	    		if(offerCreationRQ.isSetListingDuration() && !offerCreationRQ.getListingDuration().isEmpty() && offerCreationRQ.getListingDuration()!=null){
	    			
	    			LOGGER.debug("listing duration is:{}",offerCreationRQ.getListingDuration());
	    			
	    			if(offerCreationRQ.getListingDuration().compareToIgnoreCase("1")==0 || offerCreationRQ.getListingDuration().compareToIgnoreCase("3")==0  ||
	    			offerCreationRQ.getListingDuration().compareToIgnoreCase("5")==0 || offerCreationRQ.getListingDuration().compareToIgnoreCase("7")==0 ||
	    			offerCreationRQ.getListingDuration().compareToIgnoreCase("10")==0 || offerCreationRQ.getListingDuration().compareToIgnoreCase("30")==0 ||
	    			 offerCreationRQ.getListingDuration().compareToIgnoreCase("GTC")==0){
	    				
	    				LOGGER.debug("listing duration is valid");
	    			}else{
	    				
	    			//	LOGGER.debug("hiiiii in else block");
	    				 ErrorType error = new ErrorType();
	  					error.setErrorCode(3112);
	  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3112, langId));
	  					errorType.add(error);
	    			}
	    			
	    		}else{
	    			 ErrorType error = new ErrorType();
 					error.setErrorCode(3112);
 					
 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3112, langId));
 					errorType.add(error);
	    		}
	    		if(offerCreationRQ.isSetStartTime() && !offerCreationRQ.getStartTime().isEmpty() && offerCreationRQ.getStartTime()!=null){
	    			
	    			LOGGER.debug("start time is:{}",offerCreationRQ.getStartTime());
	    			
//	    			LOGGER.debug("current time in string format is :{}",DateUtil
//						.convertDateToString(new Date()));
//	    		
//	    			Date startDate = DateUtil.covertStringToDate(offerCreationRQ.getStartTime());
//	    			
//	    			LOGGER.debug("start date after conversion is:{}",startDate);
	    			
	    			if(!this.valiateRegx(offerCreationRQ.getStartTime(),
  							timeStampRegx)){
	    				
	    				ErrorType error = new ErrorType();
	 					error.setErrorCode(3111);
	 					
	 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3111, langId));
	 					errorType.add(error);
	    			}else if(!this.validateStartTime(offerCreationRQ.getStartTime())){
	    				
	    				
	    				ErrorType error = new ErrorType();
	 					error.setErrorCode(3111);
	 					
	 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3111, langId));
	 					errorType.add(error);
	    				
	    			}
	    		}else{
	    			
	    			 ErrorType error = new ErrorType();
 					error.setErrorCode(3111);
 					
 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3111, langId));
 					errorType.add(error);
	    		}
	    		
	    		if(offerCreationRQ.getTemplateId()>0){
	    		int productId = offerCreationDAOImpl.getProductId(offerCreationRQ.getTemplateId());
	    		
	    		   if(productId > 0){
	    			   
	    			   LOGGER.debug("productId is:{}",productId);
	    			   if(!offerCreationDAOImpl.checkProductValidity(productId,offerCreationRQ.getStartTime())){
	    				 
	    				   ErrorType error = new ErrorType();
	    					error.setErrorCode(3140);
	    					
	    					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3140, langId));
	    					errorType.add(error);
	    			   }
	    			   
	    		   }else{
	    			   
	    			   LOGGER.debug("productId is zero");
	    		   }}
	    		
	    		if(offerCreationRQ.isSetPrice() && offerCreationRQ.getPrice()>0){
	    			
	    			LOGGER.debug("price is :{}",offerCreationRQ.getPrice());
	    			
	    			if(String.valueOf(offerCreationRQ.getPrice()).split("\\.")[1].length()>2){
	    				
	    				 ErrorType error = new ErrorType();
		  					error.setErrorCode(3156);
		  					
		  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3156, langId));
		  					errorType.add(error);
	    			}else{
	    				
	    				LOGGER.debug("price after decimal values is lessthan or equal to two");
	    				
	    			}
	    			
	    		}else{
	    			 ErrorType error = new ErrorType();
 					error.setErrorCode(3114);
 					
 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3114, langId));
 					errorType.add(error);

	    		}
	    		if(offerCreationRQ.isSetCurrency() && offerCreationRQ.getCurrency()!=null && offerCreationRQ.getSiteId()!=null){
	    			
	    			LOGGER.debug("currency value is :{}",offerCreationRQ.getCurrency());
	    			
	    			if(!templateModuleDAOImpl.checkCurrency(offerCreationRQ.getSiteId(), offerCreationRQ.getCurrency())){
	    				
	    				ErrorType error = new ErrorType();
	 					error.setErrorCode(3115);
	 					
	 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3115, langId));
	 					errorType.add(error);
	    			}
	    			
	    		}else{
	    			 ErrorType error = new ErrorType();
 					error.setErrorCode(3115);
 					
 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3115, langId));
 					errorType.add(error);
	    		}
	    		
	    		LOGGER.debug("offer repeat value is :{}",offerCreationRQ.isOfferRepeat());
               
	    		if(offerCreationRQ.isSetOfferRepeat() && offerCreationRQ.isOfferRepeat()){
	    			
	    			LOGGER.debug("offer repeat value is :{}",offerCreationRQ.isOfferRepeat());
	    			if(offerCreationRQ.getListingType()!=null){
	    		
	    			if(offerCreationRQ.getListingType().compareToIgnoreCase("Auction")!=0){
	    				
	    				ErrorType error = new ErrorType();
	 					error.setErrorCode(3136);
	 					
	 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3136, langId));
	 					errorType.add(error);
	    			}}
	    		
	    		}else{
	    			
	    			offerCreationRQ.setOfferRepeat(false);
	    			LOGGER.debug("after set the offer repeat vale is:{}",offerCreationRQ.isOfferRepeat());
	    		}
	    		if(offerCreationRQ.getListingType()!=null && offerCreationRQ.getListingType().compareToIgnoreCase("Auction")==0){
	    			
	    			if(offerCreationRQ.getListingDuration()!=null && offerCreationRQ.getListingDuration().compareToIgnoreCase("GTC")==0){
	    				
	    				ErrorType error = new ErrorType();
	 					error.setErrorCode(3145);
	 					
	 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3145, langId));
	 					errorType.add(error);
	    			}
	    			if(offerCreationRQ.isSetRetailPrice() && !offerCreationRQ.getRetailPrice().isEmpty() && offerCreationRQ.getRetailPrice()!=null){

	    				ErrorType error = new ErrorType();
	 					error.setErrorCode(3163);
	 					
	 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3163, langId));
	 					errorType.add(error);
	    			}
	    		}
	    		if(offerCreationRQ.getListingType()!=null){
	    		if(offerCreationRQ.getListingType().compareToIgnoreCase("Auction")==0){
	    				
	    				LOGGER.debug("check for or cases");
	    				
	    				if(offerCreationRQ.isSetShopObjectId() && !offerCreationRQ.getShopObjectId().isEmpty()){
		    				
	    					LOGGER.debug("check for shoptemplate not valid");
	    					
		    				ErrorType error = new ErrorType();
		 					error.setErrorCode(3148);
		 					
		 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3148, langId));
		 					errorType.add(error);
		    			}

	    			}}
	    		
	    		if((offerCreationRQ.isSetShopObjectId() && !offerCreationRQ.getShopObjectId().isEmpty()) &&
	    				(offerCreationRQ.isSetCollectionObjectId() && !offerCreationRQ.getCollectionObjectId().isEmpty())){
	    			
	    			//LOGGER.debug("31433333333333333333 error");
	    			
	    			ErrorType error = new ErrorType();
 					error.setErrorCode(3143);
 					
 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3143, langId));
 					errorType.add(error);
	    		}
	    		//LOGGER.debug("shop object Id for validation is:{}",offerCreationRQ.getShopObjectId());
	    	
	    		
	    		
	    		if(offerCreationRQ.isSetShopObjectId()){
	    			
	    			LOGGER.debug("shop object ID is:{}",this.isNumeric(offerCreationRQ.getShopObjectId()));
	    			
	    		if(!offerCreationRQ.getShopObjectId().isEmpty() && offerCreationRQ.getShopObjectId()!=null && this.isNumeric(offerCreationRQ.getShopObjectId())){
	    		
	    			
	    			LOGGER.debug("shop object Id for validation is:{}",offerCreationRQ.getShopObjectId());
	    			
	    			if(offerCreationDAOImpl.checkShopObjectId(offerCreationRQ.getObjectId(),Integer.parseInt(offerCreationRQ.getShopObjectId()))){
	    				
	    					LOGGER.debug("shop object id is valid");
	    					if(offerCreationRQ.getTemplateId()>0){
	    					if(Integer.parseInt(offerCreationRQ.getShopObjectId())==offerCreationDAOImpl.getVorlageShopObjectID(offerCreationRQ.getTemplateId())){
	    			
	    			         LOGGER.debug("given shop objectId is match with template shop objectId");
	    					}else{
	    						
	    						ErrorType error = new ErrorType();
	    	 					error.setErrorCode(3155);
	    	 					
	    	 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3155, langId));
	    	 					errorType.add(error);

	    					}}
	    			  }else{
	    				
	    				ErrorType error = new ErrorType();
	 					error.setErrorCode(3149);
	 					
	 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3149, langId));
	 					errorType.add(error);
	    			}
	    		  
	    		}else{
	    			
	    			ErrorType error = new ErrorType();
 					error.setErrorCode(3149);
 					
 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3149, langId));
 					errorType.add(error);
	    			
	    			
	    		}}
	    		
	    		//LOGGER.debug("collection object id for validation is:{}",offerCreationRQ.getCollectionObjectId());
	    		if(offerCreationRQ.isSetCollectionObjectId()){
	    			
	    			LOGGER.debug("collection object ID is:{}",this.isNumeric(offerCreationRQ.getCollectionObjectId()));
	    			if(!offerCreationRQ.getCollectionObjectId().isEmpty() && offerCreationRQ.getCollectionObjectId()!=null && this.isNumeric(offerCreationRQ.getCollectionObjectId())){
	    		
	    			LOGGER.debug("collection object id for validation is:{}",offerCreationRQ.getCollectionObjectId());
	    			
	    			if(offerCreationDAOImpl.checkCollectionObjectId(offerCreationRQ.getObjectId(), Integer.parseInt(offerCreationRQ.getCollectionObjectId()))){
	    				
	    				LOGGER.debug("collection object id is valid");
	    			}else{
	    				
	    				ErrorType error = new ErrorType();
	 					error.setErrorCode(3150);
	 					
	 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3150, langId));
	 					errorType.add(error);
	    			}
	    		}else{
	    			
	    			ErrorType error = new ErrorType();
 					error.setErrorCode(3150);
 					
 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3150, langId));
 					errorType.add(error);
	    			
	    		}}
	    		if(offerCreationRQ.isSetRetailPrice()){
	    			
	    			LOGGER.debug("retail price is:{}",this.isDouble(offerCreationRQ.getRetailPrice()));
	    			if(!offerCreationRQ.getRetailPrice().isEmpty() && offerCreationRQ.getRetailPrice()!=null && this.isDouble(offerCreationRQ.getRetailPrice())){
	    		
	    				if(Double.parseDouble(offerCreationRQ.getRetailPrice()) > 0.0){
	    			
	    					LOGGER.debug("Retail price for validation is:{}",offerCreationRQ.getRetailPrice());
	    					
	    					 if(offerCreationRQ.getRetailPrice().contains(".")){
	    						 
//	    						 offerCreationRQ.setRetailPrice(offerCreationRQ.getRetailPrice()+".00");
//	    						 LOGGER.debug("after adding . to retail price:{}",offerCreationRQ.getRetailPrice());
	    					 
	    					if(offerCreationRQ.getRetailPrice().split("\\.")[1].length()>2){
	    	    				
	   	    				 ErrorType error = new ErrorType();
	   		  					error.setErrorCode(3156);
	   		  					
	   		  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3156, langId));
	   		  					errorType.add(error);
	   	    			}else{
	   	    				
	   	    				LOGGER.debug("retail price after decimal values is lessthan or equal to two");
	   	    				
	   	    			}}
	    					 LOGGER.debug("price value is:{}",(Double)offerCreationRQ.getPrice());
	    					 LOGGER.debug("retail price is:{}",Double.parseDouble(offerCreationRQ.getRetailPrice()));
	    					 
	    					if(Double.parseDouble(offerCreationRQ.getRetailPrice()) <= (Double)offerCreationRQ.getPrice()){
	    						
	    						LOGGER.debug("retail price not greater than price");
	    						ErrorType error = new ErrorType();
	   		  					error.setErrorCode(3162);
	   		  					
	   		  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3162, langId));
	   		  					errorType.add(error);
	    						
	    					}
	    					if(!commonValidations.allowedMarketplaces(offerCreationRQ.getSiteId())){
	    						
	    						LOGGER.debug("allowed siteId is not%%%%%%%%%%");
	    						ErrorType error = new ErrorType();
	   		  					error.setErrorCode(1112);
	   		  					
	   		  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1112, langId));
	   		  					errorType.add(error);
	    					}
	   	    		}else{
	    					
	    					ErrorType error = new ErrorType();
	     					error.setErrorCode(3161);
	     					//LOGGER.debug("%%%%%%%%%%%%%%55:{}",langId);
	     					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3161, langId));
	     					errorType.add(error);
	    				}
	    			
	    		}else{
	    			
	    			ErrorType error = new ErrorType();
 					error.setErrorCode(3161);
 					LOGGER.debug("%%%%%%%%%%%%%%55:{}",langId);
 					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3161, langId));
 					errorType.add(error);
	    			
	    		}}
	    		
	    	}
	  
	    }else{
	    	
	    	ErrorType error = new ErrorType();
				error.setErrorCode(1106);
				
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1106, 2));
				errorType.add(error);
	    }
				
				if(errorType.size()>0){
	    			  offerCreationRS.setErrors(errorsType);
	  				offerCreationRS.setAck("Failure");
	    		  }else{
	  				offerCreationRS.setAck("Success");
	    		  }
				
	    }catch(Exception e){
	    	
	    	e.printStackTrace();
	    
	    }
		
		return offerCreationRS;
	}

	public OfferDetailsRS validateOfferDetails(OfferDetailsRQ offerDetailsRQ) {
		// TODO Auto-generated method stub
		return null;
	}

	public ListOfOffersRS validateListOfOffers(ListOfOffersRQ listOfOffersRQ) {
		// TODO Auto-generated method stub
		return null;
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
	
	public boolean isNumeric(String str) {
 
		boolean flag = false;
		try {
            Integer num = Integer.parseInt(str);
            if(num>0){
            	
            	flag = true;
            }
            
        } catch (NumberFormatException nfe) {
           nfe.printStackTrace();
        }
        return flag;
    }
	public boolean isDouble(String str) {
		 
		boolean flag = false;
		try {
            Double num = Double.parseDouble(str);
            if(num>0.0){
            	
            	flag = true;
            }
            
        } catch (NumberFormatException nfe) {
           nfe.printStackTrace();
        }
        return flag;
    }
}
