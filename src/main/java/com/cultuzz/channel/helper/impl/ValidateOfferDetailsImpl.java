package com.cultuzz.channel.helper.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.DAO.OfferDAO;
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


@Component
@Qualifier("offerDetailsValidator")
public class ValidateOfferDetailsImpl implements OfferValidator {

	
	@Autowired
	private OfferDAO offerDAOImpl;
	
	@Autowired
    private GetErrorMessagesDAOImpl getErrorMessagesDAOImpl;
	
	@Autowired
	TemplateModuleDAO templateModuleDAOImpl;
	
	@Autowired
	CommonValidations commonValidations;
	
private static final Logger LOGGER = LoggerFactory.getLogger(ValidateOfferDetailsImpl.class);
	
private static final String timeStampRegx = "^[2-9]{1}[0-9]{3}-(([0]{1}[1-9]{1})|([1]{1}[0-2]{1}))-(([0]{1}[0-9]{1})|([1-2]{1}[0-9]{1})|([3]{1}[0-1]{1})) (([0]{1}[0-9]{1})|([1]{1}[0-9]{1})|([2]{1}[0-3]{1})):(([0]{1}[0-9]{1})|([1-5]{1}[0-9]{1})):(([0]{1}[0-9]{1})|([1-5]{1}[0-9]{1}))";
	
	
	public OfferCreationRS validateOfferCreation(OfferCreationRQ offerCreationRQ) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * This method used to validate the offer details request
	 * 
	 * @param offerDetailsRQ
	 * @return offerDetailsRS
	 */
	
	public OfferDetailsRS validateOfferDetails(OfferDetailsRQ offerDetailsRQ) {
		// TODO Auto-generated method stub
          LOGGER.info("Inside the offer details validation");
          
          OfferDetailsRS offerDetailsRS = null;
          
          String offerIdRegx = "[0-9]{1,20}$";
          String itemIdRegx = "[0-9]{1,20}$";
          int langId = 0;
          List<Map<String,Object>> rows = null;
//	        String authCode =null;
	        int channelId = 0;
	        int sourceId =0;
	        
          try{
        	  
        	  offerDetailsRS = new OfferDetailsRS();
        	  
        	  ErrorsType errorsType = new ErrorsType();
				List<ErrorType> errorType= errorsType.getError();
        		
				langId = getErrorMessagesDAOImpl.getLanguageId(offerDetailsRQ.getErrorLang());
			
				offerDetailsRS.setTimeStamp(DateUtil.convertDateToString(new Date()));
				
				if(langId >0){
				
			  if(offerDetailsRQ == null){
        		  
        		  ErrorType error = new ErrorType();
					error.setErrorCode(3100);
					
					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3100, langId));
					errorType.add(error);
        		  
//       		  throw new Exception("1-offer details request is null");
        	 }else{
        		  
        		  if(offerDetailsRQ.isSetTimeStamp() && !offerDetailsRQ.getTimeStamp().isEmpty() && offerDetailsRQ.getTimeStamp()!=null){
        			  
        			  if (!this.valiateRegx(
  							offerDetailsRQ.getTimeStamp(),
  							timeStampRegx)) {
  						
        				  ErrorType error = new ErrorType();
      					error.setErrorCode(1104);
      					
      					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1104, langId));
      					errorType.add(error);
      					offerDetailsRS.setErrors(errorsType);
      					offerDetailsRS.setAck("Failure");
      					return offerDetailsRS;
              		  
        			}
  				} else {
//  					
  						ErrorType error = new ErrorType();
  						error.setErrorCode(1105);
					
  						error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1105, langId));
  						errorType.add(error);
  						offerDetailsRS.setErrors(errorsType);
      					offerDetailsRS.setAck("Failure");
      					return offerDetailsRS;
  				}
        		  if(offerDetailsRQ.isSetAuthenticationCode() && !offerDetailsRQ.getAuthenticationCode().isEmpty() && offerDetailsRQ.getAuthenticationCode()!=null){
            		  
        			  LOGGER.info("authentication code is :{}",offerDetailsRQ.getAuthenticationCode());
        			  rows = commonValidations.fetchCredential(offerDetailsRQ.getAuthenticationCode());
				        
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
							offerDetailsRS.setErrors(errorsType);
		  					offerDetailsRS.setAck("Failure");
		  					return offerDetailsRS;
				        }
        			  
        		 }else{
        			  
        			    ErrorType error = new ErrorType();
    					error.setErrorCode(1100);
    					
    					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1100, langId));
    					errorType.add(error);
    					offerDetailsRS.setErrors(errorsType);
      					offerDetailsRS.setAck("Failure");
      					return offerDetailsRS;
        			}
        		  if(offerDetailsRQ.isSetSourceId() && offerDetailsRQ.getSourceId()==sourceId){
  					
  					LOGGER.debug("source Id is :{}",offerDetailsRQ.getSourceId());
  					
  					 
  				}else{
  					
  					ErrorType error = new ErrorType();
  					error.setErrorCode(1101);
  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1101, langId));
  					errorType.add(error);
  					offerDetailsRS.setErrors(errorsType);
    					offerDetailsRS.setAck("Failure");
    					return offerDetailsRS;
  				}
        		  
        		
        		  if(offerDetailsRQ.isSetChannelId() && offerDetailsRQ.getChannelId()==channelId){
        			  
        			  LOGGER.info("channel id is :{}",offerDetailsRQ.getChannelId());
        		  }else{
        			  
        			  ErrorType error = new ErrorType();
  					error.setErrorCode(1102);
  					
  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1102, langId));
  					errorType.add(error);
  					offerDetailsRS.setErrors(errorsType);
  					offerDetailsRS.setAck("Failure");
  					return offerDetailsRS;
        		  }
        		  
        		 
        		  
        		  if(offerDetailsRQ.isSetObjectId() && offerDetailsRQ.getObjectId() >0){
  	    			
  	    			LOGGER.debug("object id is :{}",offerDetailsRQ.getObjectId());
  	    			if(templateModuleDAOImpl.checkObjectId(offerDetailsRQ.getObjectId())){
  	    				
  	    				LOGGER.debug("objectId is valid");
  	    			}else{
  	    				
  	    				 ErrorType error = new ErrorType();
  	  					error.setErrorCode(3108);
  	  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3108, langId));
  	  					errorType.add(error);
  	  					offerDetailsRS.setErrors(errorsType);
        					offerDetailsRS.setAck("Failure");
        					return offerDetailsRS;
  	    			}
  	    		}else{
  	    			
  	    			 ErrorType error = new ErrorType();
   					error.setErrorCode(3108);
   					
   					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3108, langId));
   					errorType.add(error);
   					offerDetailsRS.setErrors(errorsType);
    					offerDetailsRS.setAck("Failure");
    					return offerDetailsRS;
  	    		}
  	    		

        		  
        		  if(offerDetailsRQ.isSetOfferId() && offerDetailsRQ.getOfferId()>0){
        			  
                         LOGGER.debug("offer Id is :{}",offerDetailsRQ.getOfferId());        			
    					
                         if (this.valiateRegx( String.valueOf(offerDetailsRQ.getOfferId())
                        		 , offerIdRegx)) {
                        	 
                        	 if(!offerDAOImpl.checkOfferId(offerDetailsRQ.getOfferId(),offerDetailsRQ.getObjectId())){
                        	 
                        		 ErrorType error = new ErrorType();
                        		 error.setErrorCode(3102);
                        		 error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3102, langId));
                        		 errorType.add(error);
                        	
                        	 }else{
                        	 LOGGER.debug("offer Id after validation is :{}",offerDetailsRQ.getOfferId());
                        	 } }else{
                        	 
                        		 ErrorType error = new ErrorType();
                        		 error.setErrorCode(3102);
                        		 error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3102, langId));
                        		 errorType.add(error);
                         }
        		  }else{
        			 
        			  ErrorType error = new ErrorType();
             		 error.setErrorCode(3105);
             		 error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3105, langId));
             		errorType.add(error);
        			}
        		  LOGGER.debug("item id is :{}",offerDetailsRQ.getItemId());
        		  
        		  if(offerDetailsRQ.isSetItemId() && !offerDetailsRQ.getItemId().isEmpty() && offerDetailsRQ.getItemId()!=null){
        			
        			  LOGGER.debug("item id is :{}",offerDetailsRQ.getItemId());
        			  if(this.isNumeric(offerDetailsRQ.getItemId())){
        		  
        	
        			  LOGGER.debug("item id is :{}",offerDetailsRQ.getItemId());
        			  
        			  if (this.valiateRegx( String.valueOf(offerDetailsRQ.getItemId())
                       		 , itemIdRegx)) {
                       	 
                       	 if(!offerDAOImpl.checkItemId(offerDetailsRQ.getOfferId(), Long.parseLong(offerDetailsRQ.getItemId()))){
                    	 
                        	LOGGER.debug("second check");
                    	   ErrorType error = new ErrorType();
                      	 	error.setErrorCode(3106);
                      	 	error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3106, langId));
                      	 errorType.add(error);
                    	   
                       }
        			  }else{
                       	 
                        	ErrorType error = new ErrorType();
                       	 	error.setErrorCode(3107);
                       	 	error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3107, langId));
                       	 	errorType.add(error);
                     }
        			  
        			  }else{
        				  
        				  ErrorType error = new ErrorType();
                     	 	error.setErrorCode(3107);
                     	 	error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3107, langId));
                     	 	errorType.add(error);
        			  }
        	 }}
        		    
        	}else{
    	    	
    	    	ErrorType error = new ErrorType();
    				error.setErrorCode(1106);
    				
    				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1106, 2));
    				errorType.add(error);
    	    }
        	
        	  if(errorType.size()>0){
    			  offerDetailsRS.setErrors(errorsType);
  				offerDetailsRS.setAck("Failure");
    		  }else{
  				offerDetailsRS.setAck("Success");
    		  }
    		  

        	
  			 }catch(Exception e){
        	  
        	  e.printStackTrace();
        	  
//        	  return null;
          }
          
          return offerDetailsRS;
	}

	public ListOfOffersRS validateListOfOffers(ListOfOffersRQ listOfOffersRQ) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * This method used to validate the values with regular expressions
	 * 
	 * @param regx,expression
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
		 
		boolean flag = false;
		try {
            Long num = Long.parseLong(str);
            if(num>0){
            	
            	flag = true;
            }
            
        } catch (NumberFormatException nfe) {
           nfe.printStackTrace();
        }
        return flag;
    }
	
}
