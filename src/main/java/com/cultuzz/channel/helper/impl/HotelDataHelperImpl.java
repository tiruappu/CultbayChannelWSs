package com.cultuzz.channel.helper.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cultuzz.channel.DAO.HotelDataDAO;
import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.XMLpojo.AddressType;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.XMLpojo.GetHotelDataRQ;
import com.cultuzz.channel.XMLpojo.GetHotelDataRS;
import com.cultuzz.channel.XMLpojo.ObjectMetaDataRS;
import com.cultuzz.channel.XMLpojo.GetHotelDataRS.BillingAddress;
import com.cultuzz.channel.XMLpojo.GetHotelDataRS.HotelDetails;
import com.cultuzz.channel.XMLpojo.SetHotelDataRQ;
import com.cultuzz.channel.XMLpojo.SetHotelDataRQ.Descriptions;
import com.cultuzz.channel.XMLpojo.SetHotelDataRQ.Descriptions.Description;
import com.cultuzz.channel.XMLpojo.SetHotelDataRS;
import com.cultuzz.channel.helper.HotelDataHelper;
import com.cultuzz.channel.util.CommonValidations;
import com.cultuzz.channel.util.DateUtil;

@Repository
public class HotelDataHelperImpl implements HotelDataHelper {

	 @Autowired
	CommonValidations commonValidations;
	 
	 @Autowired
	GetErrorMessagesDAOImpl getErrormessages;
	 
	 @Autowired
	 HotelDataDAO hotelDataDAOImpl;
	
	  private static final Logger LOGGER = LoggerFactory.getLogger("ObjectMetaDataHelperImpl.class");  
	  
    public SetHotelDataRS validateSetHotelData(SetHotelDataRQ setHotelDataRQ){
    	
    	LOGGER.debug("Inside the setHotelData validation");
    	SetHotelDataRS setHotelDataRS  = null;
    	try{
    		
    		
    		setHotelDataRS  = new SetHotelDataRS();
    	   setHotelDataRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
    	  
    	   ErrorsType error = new ErrorsType();
    	   
    	   List<ErrorType> errorsTypes = error.getError();
		   int langId = 0;
		   
		   if(setHotelDataRQ.isSetErrorLang()){
				
				langId=commonValidations.checkErrorLangCode(setHotelDataRQ.getErrorLang());
				
				if(langId>0){
					
					LOGGER.debug("language Id is "+langId);
				}else{
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1106);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,langId));
					errorsTypes.add(errorType);
					setHotelDataRS.setErrors(error);
					setHotelDataRS.setAck("Failure");
					return setHotelDataRS;
				}
				
			}else{				
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1106);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,langId));
				errorsTypes.add(errorType);
				setHotelDataRS.setErrors(error);
				setHotelDataRS.setAck("Failure");
				return setHotelDataRS;
			}
			if(setHotelDataRQ.isSetTimeStamp() && !setHotelDataRQ.getTimeStamp().trim().isEmpty()){
				
			boolean timestampStatus = commonValidations.checkTimeStamp(setHotelDataRQ.getTimeStamp());
			if(!timestampStatus){	
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1104);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1104,langId));
				errorsTypes.add(errorType);
				setHotelDataRS.setErrors(error);
				setHotelDataRS.setAck("Failure");
				return setHotelDataRS;
			}
			
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1105);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1105,langId));
				errorsTypes.add(errorType);
				setHotelDataRS.setErrors(error);
				setHotelDataRS.setAck("Failure");
				return setHotelDataRS;
			}

			
		     if(setHotelDataRQ.isSetAuthenticationCode()){
		    	 
		    	 boolean authCodeStatus = commonValidations.checkAuthCode(setHotelDataRQ.getAuthenticationCode());
		         if(!authCodeStatus){
		        	 
		        	 ErrorType errorType = new ErrorType();
		        	 errorType.setErrorCode(1100);
		        	 errorType.setErrorMessage(getErrormessages.getErrorMessage(1100, 2));
		        	 errorsTypes.add(errorType);
		        	 setHotelDataRS.setErrors(error);
		        	 setHotelDataRS.setAck("Failure");
		        	 return setHotelDataRS;
		         }
		     }else{
		    	 
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1100);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,langId));
					errorsTypes.add(errorType);
					setHotelDataRS.setErrors(error);
					setHotelDataRS.setAck("Failure");
					return setHotelDataRS;

		     }
			
		      if(setHotelDataRQ.isSetSourceId()){
		    	  
		    	  if(!commonValidations.checkSourceId(setHotelDataRQ.getSourceId())){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(1101);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langId));
						errorsTypes.add(errorType);
						setHotelDataRS.setErrors(error);
						setHotelDataRS.setAck("Failure");
						return setHotelDataRS;
					}
		      }else{
		    	  
		    		ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1101);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langId));
					errorsTypes.add(errorType);
					setHotelDataRS.setErrors(error);
					setHotelDataRS.setAck("Failure");
					return setHotelDataRS;
				
		      }
		
		      if(setHotelDataRQ.isSetChannelId()){
		    	  if(!commonValidations.checkChannelId(setHotelDataRQ.getChannelId())){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(1102);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langId));
						errorsTypes.add(errorType);
						setHotelDataRS.setErrors(error);
						setHotelDataRS.setAck("Failure");
						return setHotelDataRS;
					}
		    	  
		      }else{
		    	  ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1102);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langId));
					errorsTypes.add(errorType);
					setHotelDataRS.setErrors(error);
					setHotelDataRS.setAck("Failure");
					return setHotelDataRS;
		      }
		      
		      if(setHotelDataRQ.isSetHotelId() && setHotelDataRQ.getHotelId()>0){
		    	  
		    	  LOGGER.debug("hotel ID is "+setHotelDataRQ.getHotelId());  
		    	  
		      }else{
		    	  ErrorType errorType=new ErrorType();
					errorType.setErrorCode(16001);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(16001,langId));
					errorsTypes.add(errorType);
					setHotelDataRS.setErrors(error);
					setHotelDataRS.setAck("Failure");
					return setHotelDataRS;
		      }
		      
		      if(setHotelDataRQ.isSetHotelDetails()){
		    	 
		    	com.cultuzz.channel.XMLpojo.SetHotelDataRQ.HotelDetails hotelDetails = setHotelDataRQ.getHotelDetails(); 
		    	LOGGER.debug("hotel name is "+hotelDetails.getName());
		    	 if(hotelDetails.isSetName() && hotelDetails.getName()!=null){
		    		 if(hotelDetails.getName().trim().isEmpty()){
		    			 ErrorType errorType=new ErrorType();
		 				errorType.setErrorCode(16002);
		 				errorType.setErrorMessage(getErrormessages.getErrorMessage(16002,langId));
		 				errorsTypes.add(errorType);
		    		 }
		    	 }else{
		    		 ErrorType errorType=new ErrorType();
		 			 errorType.setErrorCode(16002);
		 			 errorType.setErrorMessage(getErrormessages.getErrorMessage(16002,langId));
		 			errorsTypes.add(errorType);
		    	 }
		    	 
		    	 if(hotelDetails.isSetPhoneNumber() && hotelDetails.getPhoneNumber()!=null){
		    		 
		    		 if(!hotelDetails.getPhoneNumber().isEmpty()){
		    			 LOGGER.debug("phone number is "+hotelDetails.getPhoneNumber()); 
		    		 }else{
		    			ErrorType errorType=new ErrorType();
			 			errorType.setErrorCode(16003);
			 			errorType.setErrorMessage(getErrormessages.getErrorMessage(16003,langId));
			 			errorsTypes.add(errorType);
		    		 }
		    	 }else{
		    			ErrorType errorType=new ErrorType();
			 			errorType.setErrorCode(16003);
			 			errorType.setErrorMessage(getErrormessages.getErrorMessage(16003,langId));
			 			errorsTypes.add(errorType);
		    	 }
		    	 
		    	 if(hotelDetails.isSetEmail() && hotelDetails.getEmail()!=null){
		    		 LOGGER.debug("Email is "+hotelDetails.getEmail());
		    		 if(!hotelDetails.getEmail().isEmpty()){
		    			 LOGGER.debug("Email is "+hotelDetails.getEmail());
		    		 }else{
		    			ErrorType errorType=new ErrorType();
			 			errorType.setErrorCode(16019);
			 			errorType.setErrorMessage(getErrormessages.getErrorMessage(16019,langId));
			 			errorsTypes.add(errorType);
		    		 }
		    	 }else{
		    			ErrorType errorType=new ErrorType();
			 			errorType.setErrorCode(16019);
			 			errorType.setErrorMessage(getErrormessages.getErrorMessage(16003,langId));
			 			errorsTypes.add(errorType);
		    	 }
		    	 if(hotelDetails.isSetLatitude() && hotelDetails.getLatitude()!=null){
		    		 
		    		 if(!hotelDetails.getLatitude().isEmpty()){
		    			 LOGGER.debug("lattitude is "+hotelDetails.getLatitude());
		    			 String latitude =  hotelDetails.getLatitude();
		    			 if(!this.isDouble(latitude)){
		    				 ErrorType errorType=new ErrorType();
					 		 errorType.setErrorCode(16004);
					 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16004,langId));
					 		errorsTypes.add(errorType);
		    			 }
		    			 
		    		 }else{
		    			ErrorType errorType=new ErrorType();
			 			errorType.setErrorCode(16004);
			 			errorType.setErrorMessage(getErrormessages.getErrorMessage(16004,langId));
			 			errorsTypes.add(errorType);
		    		 }
		    	 }else{
		    		    ErrorType errorType=new ErrorType();
			 			errorType.setErrorCode(16004);
			 			errorType.setErrorMessage(getErrormessages.getErrorMessage(16004,langId));
			 			errorsTypes.add(errorType);
		    	 }
		    	 
		    	 if(hotelDetails.isSetLongitude() && hotelDetails.getLongitude()!=null){
		    		 
		    		 if(!hotelDetails.getLongitude().isEmpty()){
		    			 LOGGER.debug("longitude is "+hotelDetails.getLongitude());
		    			 String longitude = hotelDetails.getLongitude();
		    			 if(!this.isDouble(longitude)){
		    				 ErrorType errorType=new ErrorType();
					 		 errorType.setErrorCode(16005);
					 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16005,langId));
					 		errorsTypes.add(errorType);
		    			 }
		    		 }else{
		    			 ErrorType errorType=new ErrorType();
				 		 errorType.setErrorCode(16005);
				 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16005,langId));
				 		errorsTypes.add(errorType);
		    		 }
		    	 }else{
		    		 ErrorType errorType=new ErrorType();
			 		 errorType.setErrorCode(16005);
			 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16005,langId));
			 		errorsTypes.add(errorType);
		    	 }
		    	if(hotelDetails.isSetZoomLevel() && hotelDetails.getZoomLevel()!=null){
		    		 
		    		 if(!hotelDetails.getZoomLevel().isEmpty()){
		    			 LOGGER.debug("zoomlevel is "+hotelDetails.getZoomLevel());
		    			 String zoomlevel = hotelDetails.getZoomLevel();
		    			 if(!this.isNumeric(zoomlevel)){
		    				 ErrorType errorType=new ErrorType();
					 		 errorType.setErrorCode(16006);
					 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16006,langId));
					 		errorsTypes.add(errorType);
		    			 }
		    		 }else{
		    			 ErrorType errorType=new ErrorType();
				 		 errorType.setErrorCode(16006);
				 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16006,langId));
				 		errorsTypes.add(errorType);
		    		 }
		    	 }else{
		    		 ErrorType errorType=new ErrorType();
			 		 errorType.setErrorCode(16006);
			 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16006,langId));
			 		errorsTypes.add(errorType);
		    	 }
		    	if(hotelDetails.isSetLanguage() && hotelDetails.getLanguage()!=null){
		    		 
		    		 if(!hotelDetails.getLanguage().isEmpty()){
		    			 LOGGER.debug("language is "+hotelDetails.getLanguage());
		    			
		    		 }else{
		    			 ErrorType errorType=new ErrorType();
				 		 errorType.setErrorCode(16007);
				 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16007,langId));
				 		errorsTypes.add(errorType);
		    		 }
		    	 }else{
		    		 ErrorType errorType=new ErrorType();
			 		 errorType.setErrorCode(16007);
			 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16007,langId));
			 		errorsTypes.add(errorType);
		    	 }
		    	if(hotelDetails.isSetCurrency() && hotelDetails.getCurrency()!=null){
		    		 
		    		 if(!hotelDetails.getCurrency().isEmpty()){
		    			 LOGGER.debug("currency is "+hotelDetails.getCurrency());
		    			
		    		 }else{
		    			 ErrorType errorType=new ErrorType();
				 		 errorType.setErrorCode(16008);
				 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16008,langId));
				 		errorsTypes.add(errorType);
		    		 }
		    	 }else{
		    		 ErrorType errorType=new ErrorType();
			 		 errorType.setErrorCode(16008);
			 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16008,langId));
			 		errorsTypes.add(errorType);
		    	 }
		    	if(hotelDetails.isSetActivationDate() && hotelDetails.getActivationDate()!=null){
		    		 LOGGER.debug("Activation Date is "+hotelDetails.getActivationDate());
		    			
		    		 if(!hotelDetails.getActivationDate().isEmpty()){
		    			 LOGGER.debug("Activation Date is "+hotelDetails.getActivationDate());
		    			
		    		 }else{
		    			 ErrorType errorType=new ErrorType();
				 		 errorType.setErrorCode(16009);
				 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16009,langId));
				 		errorsTypes.add(errorType);
		    		 }
		    	 }else{
		    		 ErrorType errorType=new ErrorType();
			 		 errorType.setErrorCode(16009);
			 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16009,langId));
			 		errorsTypes.add(errorType);
		    	 }
		    	if(hotelDetails.isSetRegistrationDate() && hotelDetails.getRegistrationDate()!=null){
		    		LOGGER.debug("Registration Date is "+hotelDetails.getRegistrationDate());
		    		 if(!hotelDetails.getRegistrationDate().isEmpty()){
		    			 LOGGER.debug("Registration Date is "+hotelDetails.getRegistrationDate());
		    			
		    		 }else{
		    			 ErrorType errorType=new ErrorType();
				 		 errorType.setErrorCode(16021);
				 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16021,langId));
				 		errorsTypes.add(errorType);
		    		 }
		    	 }else{
		    		 ErrorType errorType=new ErrorType();
			 		 errorType.setErrorCode(16021);
			 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16021,langId));
			 		errorsTypes.add(errorType);
		    	 }
		    	
		    	if(hotelDetails.isSetAddress()){
		    		AddressType address = hotelDetails.getAddress();
		    		 List<ErrorType> errorsTypes1 = this.validateAdress(address, langId);
		    		 errorsTypes.addAll(errorsTypes1);
		    	}
		      }
		      if(setHotelDataRQ.isSetBillingAddress()){
		    	 
		    	  com.cultuzz.channel.XMLpojo.SetHotelDataRQ.BillingAddress billingAddress = setHotelDataRQ.getBillingAddress();
		    	  
		    	  if(billingAddress.isSetInvoiceRecipient() && billingAddress.getInvoiceRecipient()!=null){
		    		  
		    		  if(!billingAddress.getInvoiceRecipient().isEmpty()){
		    			  LOGGER.debug("Invoice recepient is "+billingAddress.getInvoiceRecipient());
		    		  }else{
		    			    ErrorType errorType=new ErrorType();
					 		 errorType.setErrorCode(16010);
					 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16010,langId));
					 		errorsTypes.add(errorType);  
		    		  }
		    	  }else{
		    		    ErrorType errorType=new ErrorType();
				 		 errorType.setErrorCode(16010);
				 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16010,langId));
				 		errorsTypes.add(errorType);
		    	  }
		    	  if(billingAddress.isSetEmail() && billingAddress.getEmail()!=null){
			    		 
			    		 if(!billingAddress.getEmail().isEmpty()){
			    			 LOGGER.debug("Email is "+billingAddress.getEmail());
			    		 }else{
			    			ErrorType errorType=new ErrorType();
				 			errorType.setErrorCode(16019);
				 			errorType.setErrorMessage(getErrormessages.getErrorMessage(16003,langId));
				 			errorsTypes.add(errorType);
			    		 }
			    	 }else{
			    			ErrorType errorType=new ErrorType();
				 			errorType.setErrorCode(16019);
				 			errorType.setErrorMessage(getErrormessages.getErrorMessage(16003,langId));
				 			errorsTypes.add(errorType);
			    	 }
		    	  
		    	  if(billingAddress.isSetInvoiceLanguage() && billingAddress.getInvoiceLanguage()!=null){
		    		  
		    		  if(!billingAddress.getInvoiceLanguage().isEmpty()){
		    			  LOGGER.debug("language in billing address is "+billingAddress.getInvoiceLanguage());
		    		  }else{
		    			  ErrorType errorType=new ErrorType();
					 		 errorType.setErrorCode(16020);
					 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16020,langId));
					 		errorsTypes.add(errorType);
		    		  }
		    	  }else{
		    		  ErrorType errorType=new ErrorType();
				 		 errorType.setErrorCode(16020);
				 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16020,langId));
				 		errorsTypes.add(errorType);
		    	  }
		    	  
		    	  if(billingAddress.isSetAddress()){
			    		AddressType address = billingAddress.getAddress();
			    		 List<ErrorType> errorsTypes2 = this.validateAdress(address, langId);
			    		 errorsTypes.addAll(errorsTypes2);
			    	}
		      }
		      
		      if(setHotelDataRQ.isSetLegalAddress()){
		    	  
		    	  com.cultuzz.channel.XMLpojo.SetHotelDataRQ.LegalAddress legalAddress = setHotelDataRQ.getLegalAddress();
		    	  
		    	  if(legalAddress.isSetLegalName() && legalAddress.getLegalName()!=null){
		    		  
		    		  if(!legalAddress.getLegalName().isEmpty()){
		    			  LOGGER.debug("legal name is "+legalAddress.getLegalName());
		    		  }else{
		    			  ErrorType errorType=new ErrorType();
					 		 errorType.setErrorCode(16011);
					 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16011,langId));
					 		errorsTypes.add(errorType);
		    		}
		    	  }else{
		    		  ErrorType errorType=new ErrorType();
				 		 errorType.setErrorCode(16011);
				 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16011,langId));
				 		errorsTypes.add(errorType);  
		    	  }
		    	  
		    	 if(legalAddress.isSetCompanyName() && legalAddress.getCompanyName()!=null){
		    		 
		    		 if(!legalAddress.getCompanyName().isEmpty()){
		    			 LOGGER.debug("company name is "+legalAddress.getCompanyName());
		    		 }else{
		    			 ErrorType errorType=new ErrorType();
				 		 errorType.setErrorCode(16012);
				 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16012,langId));
				 		errorsTypes.add(errorType);
		    		 }
		    	 }else{
		    		 ErrorType errorType=new ErrorType();
			 		 errorType.setErrorCode(16012);
			 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16012,langId));
			 		errorsTypes.add(errorType);
		    	 }
		    	 
		    	  if(legalAddress.isSetEmail() && legalAddress.getEmail()!=null){
			    		 
			    		 if(!legalAddress.getEmail().isEmpty()){
			    			 LOGGER.debug("Email is "+legalAddress.getEmail());
			    		 }else{
			    			ErrorType errorType=new ErrorType();
				 			errorType.setErrorCode(16019);
				 			errorType.setErrorMessage(getErrormessages.getErrorMessage(16003,langId));
				 			errorsTypes.add(errorType);
			    		 }
			    	 }else{
			    			ErrorType errorType=new ErrorType();
				 			errorType.setErrorCode(16019);
				 			errorType.setErrorMessage(getErrormessages.getErrorMessage(16003,langId));
				 			errorsTypes.add(errorType);
			    	 }
		    	 if(legalAddress.isSetLegalRepresentative() && legalAddress.getLegalRepresentative()!=null){
		    		 if(!legalAddress.getLegalRepresentative().isEmpty()){
		    			 LOGGER.debug("legal representative is "+legalAddress.getLegalRepresentative());
		    		 }else{
		    			 ErrorType errorType=new ErrorType();
				 		 errorType.setErrorCode(16013);
				 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16013,langId));
				 		errorsTypes.add(errorType);
		    		 }
		    	 }else{
		    		 ErrorType errorType=new ErrorType();
			 		 errorType.setErrorCode(16013);
			 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16013,langId));
			 		errorsTypes.add(errorType);
		    	 }
		    	 
		    	  if(legalAddress.isSetAddress()){
			    		AddressType address = legalAddress.getAddress();
			    		 List<ErrorType> errorsTypes3 = this.validateAdress(address, langId);
			    		 errorsTypes.addAll(errorsTypes3);
			    	}
		      }
		      if(setHotelDataRQ.isSetDescriptions()){
		    	  
		    	  com.cultuzz.channel.XMLpojo.SetHotelDataRQ.Descriptions descriptions = setHotelDataRQ.getDescriptions();
		    	  if(descriptions.isSetDescription()){
		    		 List<com.cultuzz.channel.XMLpojo.SetHotelDataRQ.Descriptions.Description> description = descriptions.getDescription();
		    		 if(description.size() > 0){
		    			 for(Description desc :description){
		    				 
		    				 if(desc.isSetLanguage() && desc.getLanguage()!=null){
		    					 if(desc.getLanguage().isEmpty()){
		    						 ErrorType errorType=new ErrorType();
		    				 		 errorType.setErrorCode(16007);
		    				 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16007,langId));
		    				 		errorsTypes.add(errorType); 
		    					 }
		    				 }
							
						}
		    		 }
		    	  }
		      }
		      
		      
		      if(errorsTypes.size()>0){
				  setHotelDataRS.setErrors(error);
					setHotelDataRS.setAck("Failure");
			  }else{
					setHotelDataRS.setAck("Success");
			  }
    	}catch(Exception e){
    		
    	}
    	return setHotelDataRS;
    }
	
    public SetHotelDataRS processSetHotelData(SetHotelDataRQ setHotelDataRQ){
    	
    	SetHotelDataRS setHotelDataRS = null;
    	int langId = 0;
		
		LOGGER.debug("inside the process object meta data helper");
		
		ErrorsType error = new ErrorsType();
 	    List<ErrorType> errorsTypes = error.getError();
 	    List<Description> descriptionlist = null;
		try{
			setHotelDataRS = new SetHotelDataRS();
			langId = getErrormessages.getLanguageId(setHotelDataRQ.getErrorLang());

			setHotelDataRS.setTimeStamp(DateUtil
					.convertDateToString(new Date()));
			
			if(langId > 0){
				
				LOGGER.debug("language id is"+langId);
				
					if(setHotelDataRQ != null){
					
						if(setHotelDataRQ.isSetHotelId() && setHotelDataRQ.getHotelId()>0){
							long hotelId = setHotelDataRQ.getHotelId();
							LOGGER.debug("hotelId is "+hotelId);
							if(hotelId > 0){
							
								if(setHotelDataRQ.isSetHotelDetails() && setHotelDataRQ.getHotelDetails()!=null){
								
									boolean hotelinfoflag = hotelDataDAOImpl.saveHotelDetails(hotelId, setHotelDataRQ.getHotelDetails());
									LOGGER.debug("hotelData saving flag in helper "+hotelinfoflag);
									
								}
								
								if(setHotelDataRQ.isSetBillingAddress() && setHotelDataRQ.getBillingAddress()!=null){
									boolean billinginfoflag = hotelDataDAOImpl.saveBillingAddress(hotelId, setHotelDataRQ.getBillingAddress()); 
								}
								
							    if(setHotelDataRQ.isSetLegalAddress() && setHotelDataRQ.getLegalAddress()!=null){
							    	boolean legalinfoflag = hotelDataDAOImpl.saveLegalDetails(hotelId, setHotelDataRQ.getLegalAddress());
							    }
								
							    if(setHotelDataRQ.isSetDescriptions() && setHotelDataRQ.getDescriptions()!=null){
							    	
							    	 Descriptions  descriptions = setHotelDataRQ.getDescriptions();
							    	 descriptionlist = descriptions.getDescription();
							    	 
							    	 if(descriptionlist.size()>0){
							    	 for(Description description : descriptionlist){
							    		
							    		 boolean descriptionFlag = hotelDataDAOImpl.saveDescription(hotelId, description);
							    		 LOGGER.debug("description flag is "+descriptionFlag);
							    	 }
							    	 }
							    	 
							    }
							}
						 }
					}	
				}else{
					
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1106);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,langId));
					errorsTypes.add(errorType);
					setHotelDataRS.setErrors(error);
					setHotelDataRS.setAck("Failure");
					return setHotelDataRS;
				}
			
			  if(errorsTypes.size()>0){
				  setHotelDataRS.setErrors(error);
					setHotelDataRS.setAck("Failure");
			  }else{
					setHotelDataRS.setAck("Success");
			  }
		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return setHotelDataRS;
    }
	
    public GetHotelDataRS validateGetHotelData(GetHotelDataRQ getHotelDataRQ){
    	
    	GetHotelDataRS getHotelDataRS = null;
    	LOGGER.debug("Inside the getHotelData validation");
    	try{
    		
    		getHotelDataRS = new GetHotelDataRS();
    		
    		getHotelDataRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
       	  
      	   ErrorsType error = new ErrorsType();
      	   
      	   List<ErrorType> errorsTypes = error.getError();
  		   int langId = 0;
  		   
  		   if(getHotelDataRQ.isSetErrorLang()){
  				
  				langId=commonValidations.checkErrorLangCode(getHotelDataRQ.getErrorLang());
  				
  				if(langId>0){
  					
  					LOGGER.debug("language Id is "+langId);
  				}else{
  					ErrorType errorType=new ErrorType();
  					errorType.setErrorCode(1106);
  					errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,langId));
  					errorsTypes.add(errorType);
  					getHotelDataRS.setErrors(error);
  					getHotelDataRS.setAck("Failure");
  					return getHotelDataRS;
  				}
  				
  			}else{				
  				ErrorType errorType=new ErrorType();
  				errorType.setErrorCode(1106);
  				errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,langId));
  				errorsTypes.add(errorType);
  				getHotelDataRS.setErrors(error);
  				getHotelDataRS.setAck("Failure");
  				return getHotelDataRS;
  			}
  			if(getHotelDataRQ.isSetTimeStamp() && !getHotelDataRQ.getTimeStamp().trim().isEmpty()){
  				
  			boolean timestampStatus = commonValidations.checkTimeStamp(getHotelDataRQ.getTimeStamp());
  			if(!timestampStatus){	
  				ErrorType errorType=new ErrorType();
  				errorType.setErrorCode(1104);
  				errorType.setErrorMessage(getErrormessages.getErrorMessage(1104,langId));
  				errorsTypes.add(errorType);
  				getHotelDataRS.setErrors(error);
  				getHotelDataRS.setAck("Failure");
  				return getHotelDataRS;
  			}
  			
  			}else{
  				ErrorType errorType=new ErrorType();
  				errorType.setErrorCode(1105);
  				errorType.setErrorMessage(getErrormessages.getErrorMessage(1105,langId));
  				errorsTypes.add(errorType);
  				getHotelDataRS.setErrors(error);
  				getHotelDataRS.setAck("Failure");
  				return getHotelDataRS;
  			}

  			
  		     if(getHotelDataRQ.isSetAuthenticationCode()){
  		    	 
  		    	 boolean authCodeStatus = commonValidations.checkAuthCode(getHotelDataRQ.getAuthenticationCode());
  		         if(!authCodeStatus){
  		        	 
  		        	 ErrorType errorType = new ErrorType();
  		        	 errorType.setErrorCode(1100);
  		        	 errorType.setErrorMessage(getErrormessages.getErrorMessage(1100, 2));
  		        	 errorsTypes.add(errorType);
  		        	 getHotelDataRS.setErrors(error);
  		        	 getHotelDataRS.setAck("Failure");
  		        	 return getHotelDataRS;
  		         }
  		     }else{
  		    	 
  					ErrorType errorType=new ErrorType();
  					errorType.setErrorCode(1100);
  					errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,langId));
  					errorsTypes.add(errorType);
  					getHotelDataRS.setErrors(error);
  					getHotelDataRS.setAck("Failure");
  					return getHotelDataRS;

  		     }
  			
  		      if(getHotelDataRQ.isSetSourceId()){
  		    	  
  		    	  if(!commonValidations.checkSourceId(getHotelDataRQ.getSourceId())){
  						ErrorType errorType=new ErrorType();
  						errorType.setErrorCode(1101);
  						errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langId));
  						errorsTypes.add(errorType);
  						getHotelDataRS.setErrors(error);
  						getHotelDataRS.setAck("Failure");
  						return getHotelDataRS;
  					}
  		      }else{
  		    	  
  		    		ErrorType errorType=new ErrorType();
  					errorType.setErrorCode(1101);
  					errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langId));
  					errorsTypes.add(errorType);
  					getHotelDataRS.setErrors(error);
  					getHotelDataRS.setAck("Failure");
  					return getHotelDataRS;
  				
  		      }
  		
  		      if(getHotelDataRQ.isSetChannelId()){
  		    	  if(!commonValidations.checkChannelId(getHotelDataRQ.getChannelId())){
  						ErrorType errorType=new ErrorType();
  						errorType.setErrorCode(1102);
  						errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langId));
  						errorsTypes.add(errorType);
  						getHotelDataRS.setErrors(error);
  						getHotelDataRS.setAck("Failure");
  						return getHotelDataRS;
  					}
  		    	  
  		      }else{
  		    	  ErrorType errorType=new ErrorType();
  					errorType.setErrorCode(1102);
  					errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langId));
  					errorsTypes.add(errorType);
  					getHotelDataRS.setErrors(error);
  					getHotelDataRS.setAck("Failure");
  					return getHotelDataRS;
  		      }
  		    if(getHotelDataRQ.isSetHotelId() && getHotelDataRQ.getHotelId()>0){
		    	  
		    	  LOGGER.debug("hotel ID is "+getHotelDataRQ.getHotelId());  
		    	  long hotelId = getHotelDataRQ.getHotelId();
		    	
		      }else{
		    	  ErrorType errorType=new ErrorType();
					errorType.setErrorCode(16001);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(16001,langId));
					errorsTypes.add(errorType);
					getHotelDataRS.setErrors(error);
					getHotelDataRS.setAck("Failure");
					return getHotelDataRS;
		      }
  		  if(getHotelDataRQ.isSetFilter() && getHotelDataRQ.getFilter()!=null){
	    	  
	    	  LOGGER.debug("Filter is "+getHotelDataRQ.getFilter());  
	    	  String filter = getHotelDataRQ.getFilter();
	    	
	    	  if(!(filter.compareToIgnoreCase("HotelDetails")==0 || filter.compareToIgnoreCase("BillingAddress")==0 
	    			  || filter.compareToIgnoreCase("LegalAddress")==0 || filter.compareToIgnoreCase("Descriptions")==0
	    			  || filter.compareToIgnoreCase("All")==0)){
	    		  ErrorType errorType=new ErrorType();
					errorType.setErrorCode(16022);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(16022,langId));
					errorsTypes.add(errorType);
					getHotelDataRS.setErrors(error);
					getHotelDataRS.setAck("Failure");
					return getHotelDataRS;
	    	  }
	      }else{
	    	  ErrorType errorType=new ErrorType();
				errorType.setErrorCode(16022);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(16022,langId));
				errorsTypes.add(errorType);
				getHotelDataRS.setErrors(error);
				getHotelDataRS.setAck("Failure");
				return getHotelDataRS;
	      }
  		  if(errorsTypes.size()>0){
			  getHotelDataRS.setErrors(error);
				getHotelDataRS.setAck("Failure");
		  }else{
				getHotelDataRS.setAck("Success");
		  } 
  		    
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return getHotelDataRS;
    }
	
    public GetHotelDataRS processGetHotelData(GetHotelDataRQ getHotelDataRQ){
    	
    	GetHotelDataRS getHotelDataRS =null;
    	
    	int langId = 0;
		
		LOGGER.debug("inside the process get hotel data helper");
		
		ErrorsType error = new ErrorsType();
 	    List<ErrorType> errorsTypes = error.getError();
 	    List<Description> descriptionlist = null;
 	    com.cultuzz.channel.XMLpojo.GetHotelDataRS.HotelDetails hotelDetails = null;
 	    com.cultuzz.channel.XMLpojo.GetHotelDataRS.BillingAddress billingDetails = null;
 	    com.cultuzz.channel.XMLpojo.GetHotelDataRS.LegalAddress legalDetails = null;
 	    com.cultuzz.channel.XMLpojo.GetHotelDataRS.Descriptions descriptions = null;
		try{
			getHotelDataRS = new GetHotelDataRS();
			langId = getErrormessages.getLanguageId(getHotelDataRQ.getErrorLang());

			getHotelDataRS.setTimeStamp(DateUtil
					.convertDateToString(new Date()));
			
			if(langId > 0){
				
				LOGGER.debug("language id is"+langId);
				
					if(getHotelDataRQ != null){
			
						if(getHotelDataRQ.isSetHotelId() && getHotelDataRQ.getHotelId() >0){
							
						   if(getHotelDataRQ.isSetFilter() && !getHotelDataRQ.getFilter().isEmpty()){
							   
							   if(getHotelDataRQ.getFilter().compareToIgnoreCase("HotelDetails")==0){
								   
								   hotelDetails = hotelDataDAOImpl.getHotelInfo(getHotelDataRQ.getHotelId()); 
							   }else if(getHotelDataRQ.getFilter().compareToIgnoreCase("BillingDetails")==0){
								   billingDetails = hotelDataDAOImpl.getBillingDetails(getHotelDataRQ.getHotelId());
							   }else if(getHotelDataRQ.getFilter().compareToIgnoreCase("LegalDetails")==0){
								   legalDetails = hotelDataDAOImpl.getLegalDetails(getHotelDataRQ.getHotelId());
							   }else if(getHotelDataRQ.getFilter().compareToIgnoreCase("Description")==0){
								   descriptions = hotelDataDAOImpl.getDescriptions(getHotelDataRQ.getHotelId());
							   }else if(getHotelDataRQ.getFilter().compareToIgnoreCase("All")==0){
								   hotelDetails = hotelDataDAOImpl.getHotelInfo(getHotelDataRQ.getHotelId());
								   billingDetails = hotelDataDAOImpl.getBillingDetails(getHotelDataRQ.getHotelId());
								   legalDetails = hotelDataDAOImpl.getLegalDetails(getHotelDataRQ.getHotelId());
								   descriptions = hotelDataDAOImpl.getDescriptions(getHotelDataRQ.getHotelId());
							   }
							   
							   if(hotelDetails!=null){
								   getHotelDataRS.setHotelDetails(hotelDetails);
							   }
							   if(billingDetails!=null){
								   getHotelDataRS.setBillingAddress(billingDetails);
							   }
							   if(legalDetails!=null){
								   getHotelDataRS.setLegalAddress(legalDetails);
							   }
							   if(descriptions!=null){
								   getHotelDataRS.setDescriptions(descriptions);
							   }
							   
						   }
							
						}
					}
			}
			
			if(errorsTypes.size()>0){
				  getHotelDataRS.setErrors(error);
					getHotelDataRS.setAck("Failure");
			  }else{
					getHotelDataRS.setAck("Success");
			  } 
		}catch(Exception e){
			
		}
    	
    	
    	return getHotelDataRS;
    }
    
	public boolean isLong(String str) {
		 
		boolean flag = false;
		try {
            Long num = Long.parseLong(str);
            if(num>0.0){
            	
            	flag = true;
            }
            
        } catch (NumberFormatException nfe) {
           nfe.printStackTrace();
        }
        return flag;
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
	
	public List<ErrorType> validateAdress(AddressType address,int langId){
	
	  ErrorsType error = new ErrorsType();
	   
	   List<ErrorType> errorsTypes = error.getError();
	   
	   if(address.isSetStreet() && address.getStreet()!=null){
		   
		   if(!address.getStreet().isEmpty()){
			   LOGGER.debug("street iss "+address.getStreet());
		   }else{
			     ErrorType errorType=new ErrorType();
		 		 errorType.setErrorCode(16014);
		 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16014,langId));
		 		errorsTypes.add(errorType); 
		   }
	   }else{
		   ErrorType errorType=new ErrorType();
	 		 errorType.setErrorCode(16014);
	 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16014,langId));
	 		errorsTypes.add(errorType); 
	   }
	   
	   if(address.isSetPostalCode() && address.getPostalCode()!=null){
		   if(!address.getPostalCode().isEmpty()){
			   LOGGER.debug("postal code is "+address.getPostalCode());
		   }else{
			   ErrorType errorType=new ErrorType();
		 		 errorType.setErrorCode(16015);
		 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16015,langId));
		 		errorsTypes.add(errorType);  
		   }
	   }else{
		   ErrorType errorType=new ErrorType();
	 		 errorType.setErrorCode(16015);
	 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16015,langId));
	 		errorsTypes.add(errorType);  
	   }
	   
	   if(address.isSetCity() && address.getCity()!=null){
		   if(!address.getCity().isEmpty()){
			   LOGGER.debug("city is "+address.getCity());
		   }else{
			   ErrorType errorType=new ErrorType();
		 		 errorType.setErrorCode(16016);
		 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16016,langId));
		 		errorsTypes.add(errorType);
		   }
	   }else{
		   ErrorType errorType=new ErrorType();
	 		 errorType.setErrorCode(16016);
	 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16016,langId));
	 		errorsTypes.add(errorType);
	   }
	   
	   if(address.isSetCountry() && address.getCountry()!=null){
		   if(!address.getCountry().isEmpty()){
			   LOGGER.debug("country is "+address.getCountry());
		   }else{	   
			   ErrorType errorType=new ErrorType();
		 		 errorType.setErrorCode(16017);
		 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16017,langId));
		 		errorsTypes.add(errorType);
		   }
	   }else{
		   ErrorType errorType=new ErrorType();
	 		 errorType.setErrorCode(16017);
	 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16017,langId));
	 		errorsTypes.add(errorType);
	   }
	   
	   if(address.isSetRegion() && address.getRegion()!=null){
		   if(!address.getRegion().isEmpty()){
			   LOGGER.debug("region is "+address.getRegion());
		   }else{
			   ErrorType errorType=new ErrorType();
		 		 errorType.setErrorCode(16018);
		 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16018,langId));
		 		errorsTypes.add(errorType);  
		   }
	   }else{
		   ErrorType errorType=new ErrorType();
	 		 errorType.setErrorCode(16018);
	 		 errorType.setErrorMessage(getErrormessages.getErrorMessage(16018,langId));
	 		errorsTypes.add(errorType);
	   }

	   
	   return errorsTypes;
	   
	}   
}
