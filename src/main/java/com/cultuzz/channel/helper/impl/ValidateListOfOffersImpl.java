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

import com.cultuzz.channel.DAO.TemplateModuleDAO;
import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.DAO.impl.OfferIdDAOImpl;
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
@Qualifier("listOfOffersValidator")
public class ValidateListOfOffersImpl implements OfferValidator {

	@Autowired
	OfferIdDAOImpl offerIdDAOImpl;
	
	@Autowired
	TemplateModuleDAO templateModuleDAOImpl;
	
	@Autowired
	GetErrorMessagesDAOImpl getErrorMessagesDAOImpl;
	
	@Autowired
	CommonValidations commonValidations;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidateListOfOffersImpl.class);
	
	private static final String timeStampRegx = "^[2-9]{1}[0-9]{3}-(([0]{1}[1-9]{1})|([1]{1}[0-2]{1}))-(([0]{1}[0-9]{1})|([1-2]{1}[0-9]{1})|([3]{1}[0-1]{1})) (([0]{1}[0-9]{1})|([1]{1}[0-9]{1})|([2]{1}[0-3]{1})):(([0]{1}[0-9]{1})|([1-5]{1}[0-9]{1})):(([0]{1}[0-9]{1})|([1-5]{1}[0-9]{1}))";
	
	
	public OfferCreationRS validateOfferCreation(OfferCreationRQ offerCreationRQ) {
		// TODO Auto-generated method stub
		
		return null;
	}

	public OfferDetailsRS validateOfferDetails(OfferDetailsRQ offerDetailsRQ) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * This method used to validate the listOfOffers request
	 * 
	 * @param listOfOffersRQ
	 * @return listOfOffersRS
	 */
	
	public ListOfOffersRS validateListOfOffers(ListOfOffersRQ listOfOffersRQ) {
		// TODO Auto-generated method stub
		
			LOGGER.info("Inside the List of offers validation");
		
			ListOfOffersRS listOfOffersRS = null;
			
			 String IdRegx = "[0-9]{1,20}$";
	        int langId = 0;
	        List<Map<String,Object>> rows = null;
//	        String authCode =null;
	        int channelId = 0;
	        int sourceId = 0;
			
		try{
			
			listOfOffersRS = new ListOfOffersRS();
			
			ErrorsType errorsType = new ErrorsType();
			List<ErrorType> errorType= errorsType.getError();
			langId = getErrorMessagesDAOImpl.getLanguageId(listOfOffersRQ.getErrorLang());
			
			listOfOffersRS.setTimeStamp(DateUtil.convertDateToString(new Date()));
			
			if(langId >0){
			
			if(listOfOffersRQ == null){

				ErrorType error = new ErrorType();
				error.setErrorCode(3101);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3101, langId));
				errorType.add(error);

			}else{
				
				if(listOfOffersRQ.isSetTimeStamp() && !listOfOffersRQ.getTimeStamp().isEmpty() && listOfOffersRQ.getTimeStamp()!=null){
					if (!this.valiateRegx(
  							listOfOffersRQ.getTimeStamp(),
  							timeStampRegx)) {
							ErrorType error = new ErrorType();
	      					error.setErrorCode(1104);
	      					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1104, langId));
	      					errorType.add(error);
	      					listOfOffersRS.setErrors(errorsType);
	      					listOfOffersRS.setAck("Failure");
	      					return listOfOffersRS;
  					}
				}else{
						ErrorType error = new ErrorType();
						error.setErrorCode(1105);
						error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1105, langId));
						errorType.add(error);
						listOfOffersRS.setErrors(errorsType);
      					listOfOffersRS.setAck("Failure");
      					return listOfOffersRS;
				}
				
				
			if(listOfOffersRQ.isSetAuthenticationCode() && !listOfOffersRQ.getAuthenticationCode().isEmpty() && listOfOffersRQ.getAuthenticationCode()!=null){
					
					LOGGER.debug("Authentication code is :{}",listOfOffersRQ.getAuthenticationCode());
					
					
					rows = commonValidations.fetchCredential(listOfOffersRQ.getAuthenticationCode());
			        
			        LOGGER.debug("Size of List is:::"+rows.size());
			        
			        if(rows.size()>0){
			        for(Map<String, Object> credentails : rows){
//			                 authCode = credentails.get("auth_code").toString();
			                 sourceId = Integer.parseInt(credentails.get("sourceId").toString());
			                 channelId = Integer.parseInt(credentails.get("channelId").toString());
			        }
			        }else{
			        	
			        	ErrorType error = new ErrorType();
						error.setErrorCode(1100);
						error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1100, langId));
						errorType.add(error);
						listOfOffersRS.setErrors(errorsType);
	  					listOfOffersRS.setAck("Failure");
	  					return listOfOffersRS;
			        }
					
				}else{

    			    ErrorType error = new ErrorType();
					error.setErrorCode(1100);
					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1100, langId));
					errorType.add(error);
					listOfOffersRS.setErrors(errorsType);
  					listOfOffersRS.setAck("Failure");
  					return listOfOffersRS;
				}
				
			if(listOfOffersRQ.isSetSourceId() && listOfOffersRQ.getSourceId()==sourceId){
				
				LOGGER.debug("source Id is :{}",listOfOffersRQ.getSourceId());
				
			}else{
				
				ErrorType error = new ErrorType();
				error.setErrorCode(1101);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1101, langId));
				errorType.add(error);
				listOfOffersRS.setErrors(errorsType);
					listOfOffersRS.setAck("Failure");
					return listOfOffersRS;
			}
				
				
				if(listOfOffersRQ.isSetChannelId() && listOfOffersRQ.getChannelId()==channelId){
				
					LOGGER.debug("channel id is :{}",listOfOffersRQ.getChannelId());
				}else{
				    
					ErrorType error = new ErrorType();
  					error.setErrorCode(1102);
  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1102, langId));
  					errorType.add(error);
  					listOfOffersRS.setErrors(errorsType);
  					listOfOffersRS.setAck("Failure");
  					return listOfOffersRS;
				}
			
				if(listOfOffersRQ.isSetObjectId() && listOfOffersRQ.getObjectId()>0){
			    	  
			    	  LOGGER.debug("object Id is :{}",listOfOffersRQ.getObjectId());
			    	  if (this.valiateRegx( String.valueOf(listOfOffersRQ.getObjectId())
                   		 , IdRegx)) {
                   	 
                   	 if(!templateModuleDAOImpl.checkObjectId(listOfOffersRQ.getObjectId())){
                   		ErrorType error = new ErrorType();
  	  					error.setErrorCode(3108);
  	  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3108, langId));
  	  					errorType.add(error);
  	  					listOfOffersRS.setErrors(errorsType);
  	  					listOfOffersRS.setAck("Failure");
  	  					return listOfOffersRS;
  	  					
                   	 }else{
                   	 LOGGER.debug("object Id after validation is :{}",listOfOffersRQ.getObjectId());
                   	 } }else{
                   	 
                   		ErrorType error = new ErrorType();
  	  					error.setErrorCode(1103);
  	  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1103, langId));
  	  					errorType.add(error);
  	  					listOfOffersRS.setErrors(errorsType);
  	  					listOfOffersRS.setAck("Failure");
  	  					return listOfOffersRS;
                    }
			    	  
			    	}else{
			    		
			    		ErrorType error = new ErrorType();
	  					error.setErrorCode(3108);
	  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3108, langId));
	  					errorType.add(error);
	  					listOfOffersRS.setErrors(errorsType);
	  					listOfOffersRS.setAck("Failure");
	  					return listOfOffersRS;
			    	}
				
				if(listOfOffersRQ.isSetRange() && listOfOffersRQ.getRange() != null){
					
					if(listOfOffersRQ.getRange().isSetLowerLimit() && !listOfOffersRQ.getRange().getLowerLimit().isEmpty() && listOfOffersRQ.getRange().getLowerLimit()!=null ){
						
						if(this.isNumeric(listOfOffersRQ.getRange().getLowerLimit()) && Integer.parseInt(listOfOffersRQ.getRange().getLowerLimit())>=0){
					
						
						LOGGER.debug("lower limit is :{}",listOfOffersRQ.getRange().getLowerLimit());
					
					
					if(listOfOffersRQ.getRange().isSetUpperLimit() && this.isNumeric(listOfOffersRQ.getRange().getUpperLimit())
							&& Integer.parseInt(listOfOffersRQ.getRange().getUpperLimit())>0){
						
						LOGGER.debug("upper limit is :{}",listOfOffersRQ.getRange().getUpperLimit());
					
					
					int range = Integer.parseInt(listOfOffersRQ.getRange().getUpperLimit()) - Integer.parseInt(listOfOffersRQ.getRange().getLowerLimit());
					
					   if( range <= 100 && range >0){
						   
						   LOGGER.debug("range is:{}",range);
					   }else{
						   ErrorType error = new ErrorType();
		  					error.setErrorCode(3129);
		  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3129, langId));
		  					errorType.add(error);
					   }
					}else{
						ErrorType error = new ErrorType();
	  					error.setErrorCode(3119);
	  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3119, langId));
	  					errorType.add(error);
					}
						}else{
							ErrorType error = new ErrorType();
		  					error.setErrorCode(3118);
		  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3118, langId));
		  					errorType.add(error);
						}
						}else{
						ErrorType error = new ErrorType();
	  					error.setErrorCode(3118);
	  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3118, langId));
	  					errorType.add(error);
					}
					
				}else{
					ErrorType error = new ErrorType();
  					error.setErrorCode(3120);
  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3120, langId));
  					errorType.add(error);
				}
				
				if(listOfOffersRQ.isSetPeriod() && listOfOffersRQ.getPeriod() != null){
					
					if(listOfOffersRQ.getPeriod().isSetFrom() && !listOfOffersRQ.getPeriod().getFrom().isEmpty() && listOfOffersRQ.getPeriod().getFrom()!=null){
						
						if (!this.valiateRegx(
		  							listOfOffersRQ.getPeriod().getFrom(),
		  							timeStampRegx)) {
							ErrorType error = new ErrorType();
		  					error.setErrorCode(3121);
		  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3121, langId));
		  					errorType.add(error);
		  					}else{
		  						
		  						LOGGER.debug("from date is :{}",listOfOffersRQ.getPeriod().getFrom());
		  					}
					}else{
						
						ErrorType error = new ErrorType();
	  					error.setErrorCode(3121);
	  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3121, langId));
	  					errorType.add(error);
					}
					
					if(listOfOffersRQ.getPeriod().isSetTo() && !listOfOffersRQ.getPeriod().getTo().isEmpty() && listOfOffersRQ.getPeriod().getTo()!=null){
						
						if (!this.valiateRegx(
	  							listOfOffersRQ.getPeriod().getTo(),
	  							timeStampRegx)) {
							ErrorType error = new ErrorType();
		  					error.setErrorCode(3123);
		  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3123, langId));
		  					errorType.add(error);
							
	  					}else{
	  						
	  						LOGGER.debug("To date is :{}",listOfOffersRQ.getPeriod().getTo());
	  						if (listOfOffersRQ.getPeriod().getFrom().compareTo(listOfOffersRQ.getPeriod().getTo()) >= 0) {
	  							LOGGER.debug("From DATE should be Less Than toDATE...");

	  							ErrorType error = new ErrorType();
	  							error.setErrorCode(3151);
	  							error.setErrorMessage(getErrorMessagesDAOImpl
	  									.getErrorMessage(3151, langId));
	  							errorType.add(error);
	  						}
	  					}
					}else{
						
						ErrorType error = new ErrorType();
	  					error.setErrorCode(3123);
	  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3123, langId));
	  					errorType.add(error);
					}
					
					
				}

			      if(listOfOffersRQ.isSetTemplateId() && listOfOffersRQ.getTemplateId()>0){
			    
			    	  LOGGER.debug("Template id is :{}",listOfOffersRQ.getTemplateId());
			    	  
			    	  if (this.valiateRegx( String.valueOf(listOfOffersRQ.getTemplateId())
	                     		 , IdRegx)) {
	                     	 
	                     	 if(!offerIdDAOImpl.checkTemplateId(listOfOffersRQ.getTemplateId())){
	                     		ErrorType error = new ErrorType();
	    	  					error.setErrorCode(3109);
	    	  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3109, langId));
	    	  					errorType.add(error);
	                     	 }else{
	                     	 LOGGER.debug("template Id after validation is :{}",listOfOffersRQ.getTemplateId());
	                     	 
	                     	 if(listOfOffersRQ.getObjectId() == templateModuleDAOImpl.getObjectid(listOfOffersRQ.getTemplateId())){
	                     	
	                     		LOGGER.debug("templateId is match with the objectId");
	                     	 }else{
	                     		 
	                     		ErrorType error = new ErrorType();
	    	  					error.setErrorCode(3146);
	    	  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3146, langId));
	    	  					errorType.add(error); 
	                     	 }
	                     	} 
			    	  }else{
	                     	 
	                     		ErrorType error = new ErrorType();
	    	  					error.setErrorCode(3109);
	    	  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3109, langId));
	    	  					errorType.add(error);
	                     	 }
			      }else{
			    	  
			    	 /* ErrorType error = new ErrorType();
	  					error.setErrorCode(3109);
	  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3109, langId));
	  					errorType.add(error);*/
			      }
			      
			      if(listOfOffersRQ.isSetStatus()){
			    	  String rStatus=listOfOffersRQ.getStatus();
			    	  if(rStatus!=null && (rStatus.equalsIgnoreCase("All")|| rStatus.equalsIgnoreCase("Running") || rStatus.equalsIgnoreCase("Past") || rStatus.equalsIgnoreCase("Future") )){
			    		  
			    	  }else{
			    		  //error message invalid status code
			    		  ErrorType error = new ErrorType();
		  				  error.setErrorCode(3164);
		  				  error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3164, langId));
		  				  errorType.add(error);
			    	  }
			    	  
			      }
			     			      
			}
			
			}else{
    	    	
    	    	ErrorType error = new ErrorType();
    				error.setErrorCode(1106);
    				
    				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1106, 2));
    				errorType.add(error);
    	    }
			 if(errorType.size()>0){
   			  listOfOffersRS.setErrors(errorsType);
 				listOfOffersRS.setAck("Failure");
   		  	}else{
 				listOfOffersRS.setAck("Success");
   		  	}
			
			}catch(Exception e){
			
			e.printStackTrace();
	
			}
		
		return listOfOffersRS;
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
	
	public boolean isNumeric(String str) {
		 
		boolean flag = false;
		try {
            Integer num = Integer.parseInt(str);
            LOGGER.debug("isssss numeric:{}",num);
            
            if(num>=0){
            	
            	flag = true;
            }
            
        } catch (NumberFormatException nfe) {
           nfe.printStackTrace();
        }
        return flag;
    }
}
