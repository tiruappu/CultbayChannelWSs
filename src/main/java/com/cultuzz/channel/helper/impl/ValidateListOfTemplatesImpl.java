package com.cultuzz.channel.helper.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.DAO.impl.TemplateModuleDAOImpl;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.XMLpojo.ListOfTemplatesRQ;
import com.cultuzz.channel.XMLpojo.ListOfTemplatesRS;
import com.cultuzz.channel.helper.ListOfTemplatesValidator;
import com.cultuzz.channel.util.CommonValidations;
import com.cultuzz.channel.util.DateUtil;

@Configuration
public class ValidateListOfTemplatesImpl implements ListOfTemplatesValidator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidateListOfTemplatesImpl.class);

	private static final String timeStampRegx = "^[2-9]{1}[0-9]{3}-(([0]{1}[1-9]{1})|([1]{1}[0-2]{1}))-(([0]{1}[0-9]{1})|([1-2]{1}[0-9]{1})|([3]{1}[0-1]{1})) (([0]{1}[0-9]{1})|([1]{1}[0-9]{1})|([2]{1}[0-3]{1})):(([0]{1}[0-9]{1})|([1-5]{1}[0-9]{1})):(([0]{1}[0-9]{1})|([1-5]{1}[0-9]{1}))";
	
	@Autowired
	private GetErrorMessagesDAOImpl getErrorMessagesDAOImpl;
	
	@Autowired
	private TemplateModuleDAOImpl templateModuleDAOImpl;
	
	@Autowired
	private CommonValidations commonValidations;
	
	public ListOfTemplatesRS validateListOfTemplates(
			ListOfTemplatesRQ listOfTemplatesRQ) {
		// TODO Auto-generated method stub
		
		LOGGER.info("Inside the List of Templates validation");
		
		ListOfTemplatesRS listOfTemplatesRS = null;
		
		 String IdRegx = "[0-9]{1,20}$";
        int langId = 0;
        List<Map<String,Object>> rows = null;
        String authCode =null;
        int channelId = 0;
		int sourceId = 0;
		
	try{
		
		listOfTemplatesRS = new ListOfTemplatesRS();
		
		ErrorsType errorsType = new ErrorsType();
		List<ErrorType> errorType= errorsType.getError();
		
		LOGGER.debug("error language is :{}",listOfTemplatesRQ.getErrorLang());
		langId = getErrorMessagesDAOImpl.getLanguageId(listOfTemplatesRQ.getErrorLang());
		
		LOGGER.debug("langId is :{}",langId);
		
		listOfTemplatesRS.setTimeStamp(DateUtil.convertDateToString(new Date()));
		
		if(langId >0){
			
		if(listOfTemplatesRQ == null){
			
				ErrorType error = new ErrorType();
				error.setErrorCode(3139);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3139, langId));
				errorType.add(error);

		}else{
			
			if(listOfTemplatesRQ.isSetTimeStamp() && !listOfTemplatesRQ.getTimeStamp().isEmpty() && listOfTemplatesRQ.getTimeStamp()!=null){
				if (!this.valiateRegx(
							listOfTemplatesRQ.getTimeStamp(),
							timeStampRegx)) {
						ErrorType error = new ErrorType();
      					error.setErrorCode(1104);
      					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1104, langId));
      					errorType.add(error);
      					listOfTemplatesRS.setErrors(errorsType);
      					listOfTemplatesRS.setAck("Failure");
      					return listOfTemplatesRS;
					}
			}else{
					ErrorType error = new ErrorType();
					error.setErrorCode(1105);
					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1105, langId));
					errorType.add(error);
					listOfTemplatesRS.setErrors(errorsType);
  					listOfTemplatesRS.setAck("Failure");
  					return listOfTemplatesRS;
			}
			
			
			if(listOfTemplatesRQ.isSetAuthenticationCode() && !listOfTemplatesRQ.getAuthenticationCode().isEmpty() && listOfTemplatesRQ.getAuthenticationCode()!=null){
				
				LOGGER.debug("Authentication code is :{}",listOfTemplatesRQ.getAuthenticationCode());
				
				
				rows = commonValidations.fetchCredential(listOfTemplatesRQ.getAuthenticationCode());
		        
		        LOGGER.debug("Size of List is:::"+rows.size());
		        
		        if(rows.size()>0){
		        for(Map<String, Object> credentails : rows){
//		                 authCode = credentails.get("auth_code").toString();
		                 sourceId = Integer.parseInt(credentails.get("sourceId").toString());
		                 channelId = Integer.parseInt(credentails.get("channelId").toString());
		        }
		        }else{
		        	
		        	ErrorType error = new ErrorType();
					error.setErrorCode(1100);
					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1100, langId));
					errorType.add(error);
					listOfTemplatesRS.setErrors(errorsType);
  					listOfTemplatesRS.setAck("Failure");
  					return listOfTemplatesRS;
		        }
				
			}else{

			    ErrorType error = new ErrorType();
				error.setErrorCode(1100);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1100, langId));
				errorType.add(error);
				listOfTemplatesRS.setErrors(errorsType);
					listOfTemplatesRS.setAck("Failure");
					return listOfTemplatesRS;
			}
			
		if(listOfTemplatesRQ.isSetSourceId() && listOfTemplatesRQ.getSourceId()==sourceId){
			
			LOGGER.debug("source Id is :{}",listOfTemplatesRQ.getSourceId());
			
		}else{
			
			ErrorType error = new ErrorType();
			error.setErrorCode(1101);
			error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1101, langId));
			errorType.add(error);
			listOfTemplatesRS.setErrors(errorsType);
				listOfTemplatesRS.setAck("Failure");
				return listOfTemplatesRS;
		}
			
			
			if(listOfTemplatesRQ.isSetChannelId() && listOfTemplatesRQ.getChannelId()==channelId){
			
				LOGGER.debug("channel id is :{}",listOfTemplatesRQ.getChannelId());
			}else{
			    
				ErrorType error = new ErrorType();
					error.setErrorCode(1102);
					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1102, langId));
					errorType.add(error);
					listOfTemplatesRS.setErrors(errorsType);
					listOfTemplatesRS.setAck("Failure");
					return listOfTemplatesRS;
			}
		
			if(listOfTemplatesRQ.isSetObjectId() && listOfTemplatesRQ.getObjectId()>0){
		    	  
		    	  LOGGER.debug("object Id is :{}",listOfTemplatesRQ.getObjectId());
		    	  if (this.valiateRegx( String.valueOf(listOfTemplatesRQ.getObjectId())
               		 , IdRegx)) {
               	 
               	 if(!templateModuleDAOImpl.checkObjectId(listOfTemplatesRQ.getObjectId())){
               		ErrorType error = new ErrorType();
	  					error.setErrorCode(3108);
	  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3108, langId));
	  					errorType.add(error);
	  					listOfTemplatesRS.setErrors(errorsType);
	  					listOfTemplatesRS.setAck("Failure");
	  					return listOfTemplatesRS;
	  					
               	 }else{
               	 LOGGER.debug("object Id after validation is :{}",listOfTemplatesRQ.getObjectId());
               	 } }else{
               	 
               		ErrorType error = new ErrorType();
	  					error.setErrorCode(1103);
	  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1103, langId));
	  					errorType.add(error);
	  					listOfTemplatesRS.setErrors(errorsType);
	  					listOfTemplatesRS.setAck("Failure");
	  					return listOfTemplatesRS;
                }
		    	  
		    	}else{
		    		
		    		ErrorType error = new ErrorType();
					error.setErrorCode(3108);
					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3108, langId));
					errorType.add(error);
					listOfTemplatesRS.setErrors(errorsType);
  					listOfTemplatesRS.setAck("Failure");
  					return listOfTemplatesRS;
		    	}
		    
		//	LOGGER.debug("lower limiti is:{}",Integer.parseInt(listOfTemplatesRQ.getRange().getLowerLimit()));
			if(listOfTemplatesRQ.isSetRange() && listOfTemplatesRQ.getRange() != null ){
				
				LOGGER.debug("lower limiti is:{}",listOfTemplatesRQ.getRange().getLowerLimit());
				
						if(listOfTemplatesRQ.getRange().isSetLowerLimit() && !listOfTemplatesRQ.getRange().getLowerLimit().isEmpty() && listOfTemplatesRQ.getRange().getLowerLimit()!=null){
							
							LOGGER.debug("lower limit is :{}",listOfTemplatesRQ.getRange().getLowerLimit());

							if(this.isNumeric(listOfTemplatesRQ.getRange().getLowerLimit()) && Integer.parseInt(listOfTemplatesRQ.getRange().getLowerLimit()) >=0){
						
						if(listOfTemplatesRQ.getRange().isSetUpperLimit() && this.isNumeric(listOfTemplatesRQ.getRange().getUpperLimit())
								&& Integer.parseInt(listOfTemplatesRQ.getRange().getUpperLimit())>0){
							
							LOGGER.debug("upper limit is :{}",listOfTemplatesRQ.getRange().getUpperLimit());
						
						int range = Integer.parseInt(listOfTemplatesRQ.getRange().getUpperLimit()) - Integer.parseInt(listOfTemplatesRQ.getRange().getLowerLimit());
						
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
							  }}else{
					
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
			
			if(listOfTemplatesRQ.isSetPeriod() && listOfTemplatesRQ.getPeriod() != null){
				
				if(listOfTemplatesRQ.getPeriod().isSetFrom() && !listOfTemplatesRQ.getPeriod().getFrom().isEmpty() && listOfTemplatesRQ.getPeriod().getFrom()!=null){
					
					if (!this.valiateRegx(
	  							listOfTemplatesRQ.getPeriod().getFrom(),
	  							timeStampRegx)) {
						ErrorType error = new ErrorType();
	  					error.setErrorCode(3121);
	  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3121, langId));
	  					errorType.add(error);
	  					}else{
	  						
	  						LOGGER.debug("from date is :{}",listOfTemplatesRQ.getPeriod().getFrom());
	  					}
				}else{
					
					ErrorType error = new ErrorType();
  					error.setErrorCode(3121);
  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3121, langId));
  					errorType.add(error);
				}
				
				if(listOfTemplatesRQ.getPeriod().isSetTo() && !listOfTemplatesRQ.getPeriod().getTo().isEmpty() && listOfTemplatesRQ.getPeriod().getTo()!=null){
					
					if (!this.valiateRegx(
  							listOfTemplatesRQ.getPeriod().getTo(),
  							timeStampRegx)) {
						ErrorType error = new ErrorType();
	  					error.setErrorCode(3123);
	  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3123, langId));
	  					errorType.add(error);
						
  					}else{
  						
  						LOGGER.debug("To date is :{}",listOfTemplatesRQ.getPeriod().getTo());
  						if (listOfTemplatesRQ.getPeriod().getFrom().compareTo(listOfTemplatesRQ.getPeriod().getTo()) >= 0) {
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
			
		}
		
		}else{
			
		    	ErrorType error = new ErrorType();
					error.setErrorCode(1106);
					LOGGER.debug("error message is:{}",getErrorMessagesDAOImpl.getErrorMessage(1106, 2));
					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1106, 2));
					errorType.add(error);
						
		}
		
		 if(errorType.size()>0){
			  listOfTemplatesRS.setErrors(errorsType);
				listOfTemplatesRS.setAck("Failure");
		  }else{
				listOfTemplatesRS.setAck("Success");
		  }
		
	}catch(Exception e){
		
		e.printStackTrace();

		}
	
			return listOfTemplatesRS;

	}

	public boolean valiateRegx(String expression, String regx) {
	Pattern p = null;
	Matcher m = null;
	p = Pattern.compile(regx);
	m = p.matcher(expression);
	if (!m.matches()) {
		LOGGER.error("date is is not valid", expression);
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
