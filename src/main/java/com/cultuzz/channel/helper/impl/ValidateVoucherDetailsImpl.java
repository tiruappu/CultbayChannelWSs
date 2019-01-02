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
import org.springframework.context.annotation.Configuration;

import com.cultuzz.channel.DAO.VoucherDAO;
import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.DAO.impl.TemplateModuleDAOImpl;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.XMLpojo.ListOfVouchersRQ;
import com.cultuzz.channel.XMLpojo.ListOfVouchersRS;
import com.cultuzz.channel.XMLpojo.VoucherDetailsRQ;
import com.cultuzz.channel.XMLpojo.VoucherDetailsRS;
import com.cultuzz.channel.XMLpojo.VoucherRedemptionRQ;
import com.cultuzz.channel.XMLpojo.VoucherRedemptionRS;
import com.cultuzz.channel.XMLpojo.VoucherServiceRQ;
import com.cultuzz.channel.XMLpojo.VoucherServiceRS;
import com.cultuzz.channel.helper.VoucherValidator;
import com.cultuzz.channel.util.CommonValidations;
import com.cultuzz.channel.util.DateUtil;

@Configuration
@Qualifier("voucherDetailsValidator")
public class ValidateVoucherDetailsImpl implements VoucherValidator {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidateVoucherDetailsImpl.class);
	
	private static final String timeStampRegx = "^[2-9]{1}[0-9]{3}-(([0]{1}[1-9]{1})|([1]{1}[0-2]{1}))-(([0]{1}[0-9]{1})|([1-2]{1}[0-9]{1})|([3]{1}[0-1]{1})) (([0]{1}[0-9]{1})|([1]{1}[0-9]{1})|([2]{1}[0-3]{1})):(([0]{1}[0-9]{1})|([1-5]{1}[0-9]{1})):(([0]{1}[0-9]{1})|([1-5]{1}[0-9]{1}))";
	
	@Autowired
    private GetErrorMessagesDAOImpl getErrorMessagesDAOImpl;
	
	@Autowired
    private TemplateModuleDAOImpl templateModuleDAOImpl;
	
	@Autowired
	private VoucherDAO voucherDAOImpl;
	
	@Autowired
	CommonValidations commonValidations;
	
	public VoucherDetailsRS validateVoucherDetails(
			VoucherDetailsRQ voucherDetailsRQ) {
		// TODO Auto-generated method stub
		
		
		VoucherDetailsRS voucherDetailsRS = null;
		int langId = 0;
		String voucherIdRegx = "[0-9]{1,11}$";
		List<Map<String,Object>> rows = null;
        String authCode =null;
        int channelId = 0;
		int sourceId =0 ;
		
		if(voucherDetailsRQ!=null){
		  try{
			  voucherDetailsRS = new VoucherDetailsRS();
			  
			  ErrorsType errorsType = new ErrorsType();
			  List<ErrorType> errorType = errorsType.getError();
			  
			  langId = getErrorMessagesDAOImpl.getLanguageId(voucherDetailsRQ.getErrorLang());
			  
			  voucherDetailsRS.setTimeStamp(DateUtil.convertDateToString(new Date()));
			  if(langId>0){
				  
				   if(voucherDetailsRQ.isSetTimeStamp() && !voucherDetailsRQ.getTimeStamp().isEmpty() 
							  && voucherDetailsRQ.getTimeStamp()!=null){
					
						  if (!this.valiateRegx(
		  							voucherDetailsRQ.getTimeStamp(),
		  							timeStampRegx)) {
		  						
		        				  ErrorType error = new ErrorType();
		      					error.setErrorCode(1104);
		      					
		      					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1104, langId));
		      					errorType.add(error);
		      					voucherDetailsRS.setErrors(errorsType);
		      					voucherDetailsRS.setAck("Failure");
		      					return voucherDetailsRS;
		              		}
						}else{
									ErrorType error = new ErrorType();
			  						error.setErrorCode(1105);
								
			  						error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1105, langId));
			  						errorType.add(error);
			  						voucherDetailsRS.setErrors(errorsType);
			      					voucherDetailsRS.setAck("Failure");
			      					return voucherDetailsRS;
			  			}
				   
				   if(voucherDetailsRQ.isSetAuthenticationCode() && !voucherDetailsRQ.getAuthenticationCode().isEmpty() 
							  && voucherDetailsRQ.getAuthenticationCode()!=null){
		        		  
	        			  LOGGER.info("authentication code is :{}",voucherDetailsRQ.getAuthenticationCode());
	        			  rows = commonValidations.fetchCredential(voucherDetailsRQ.getAuthenticationCode());
					        
					        LOGGER.debug("Size of List is:::"+rows.size());
					        
					        if(rows.size()>0){
					        for(Map<String, Object> credentails : rows){
//					                 authCode = credentails.get("auth_code").toString();
					                 sourceId = Integer.parseInt(credentails.get("sourceId").toString());
					                 channelId = Integer.parseInt(credentails.get("channelId").toString());
					        }
					        }else{
					        	
					        	ErrorType error = new ErrorType();
								error.setErrorCode(1100);
								error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1100, langId));
								errorType.add(error);
								voucherDetailsRS.setErrors(errorsType);
			  					voucherDetailsRS.setAck("Failure");
			  					return voucherDetailsRS;
					        }
	        			  
	        		 }else{
	        			  
	        			    ErrorType error = new ErrorType();
	    					error.setErrorCode(1100);
	    					
	    					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1100, langId));
	    					errorType.add(error);
	    					voucherDetailsRS.setErrors(errorsType);
	      					voucherDetailsRS.setAck("Failure");
	      					return voucherDetailsRS;
	        		}
				   if(voucherDetailsRQ.isSetSourceId() && voucherDetailsRQ.getSourceId()==sourceId){
						
						LOGGER.debug("source Id is :{}",voucherDetailsRQ.getSourceId());
						
						  
					}else{
						
						ErrorType error = new ErrorType();
						error.setErrorCode(1101);
						error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1101, langId));
						errorType.add(error);
						voucherDetailsRS.setErrors(errorsType);
	  					voucherDetailsRS.setAck("Failure");
	  					return voucherDetailsRS;
					}
					  if(voucherDetailsRQ.isSetChannelId() && voucherDetailsRQ.getChannelId()==channelId){
	        			  
	        			  LOGGER.info("channel id is :{}",voucherDetailsRQ.getChannelId());
	        		  }else{
	        			  
	        			  ErrorType error = new ErrorType();
	  					error.setErrorCode(1102);
	  					
	  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1102, langId));
	  					errorType.add(error);
	  					voucherDetailsRS.setErrors(errorsType);
	  					voucherDetailsRS.setAck("Failure");
	  					return voucherDetailsRS;
	        		  }
					 
					  if(voucherDetailsRQ.isSetObjectId() && voucherDetailsRQ.getObjectId() >0){
		  	    			
		  	    			LOGGER.debug("object id is :{}",voucherDetailsRQ.getObjectId());
		  	    			if(templateModuleDAOImpl.checkObjectId(voucherDetailsRQ.getObjectId())){
		  	    				
		  	    				LOGGER.debug("objectId is valid");
		  	    			}else{
		  	    				
		  	    				 ErrorType error = new ErrorType();
		  	  					error.setErrorCode(3108);
		  	  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3108, langId));
		  	  					errorType.add(error);
		  	  					voucherDetailsRS.setErrors(errorsType);
		        					voucherDetailsRS.setAck("Failure");
		        					return voucherDetailsRS;
		  	    			}
		  	    		}else{
		  	    			
		  	    			 ErrorType error = new ErrorType();
		   					error.setErrorCode(3108);
		   					
		   					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3108, langId));
		   					errorType.add(error);
		   					voucherDetailsRS.setErrors(errorsType);
		    					voucherDetailsRS.setAck("Failure");
		    					return voucherDetailsRS;
		  	    	  }
					  if(voucherDetailsRQ.isSetItemId() && !voucherDetailsRQ.getItemId().isEmpty() &&
							  voucherDetailsRQ.getItemId()!=null &&
							   this.isNumeric(voucherDetailsRQ.getItemId())){
						  
						  if(!voucherDAOImpl.checkEbayItemId(Long.parseLong(voucherDetailsRQ.getItemId()),
								    voucherDetailsRQ.getObjectId())){
							  
							  	ErrorType error = new ErrorType();
			   					error.setErrorCode(3107);
			   					
			   					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3107, langId));
			   					errorType.add(error);
			   				}else {
			   					
			   					if(voucherDetailsRQ.isSetVoucherId() && !voucherDetailsRQ.getVoucherId().isEmpty()
			   				
									  && voucherDetailsRQ.getVoucherId()!=null){
								   
								   if (!this.valiateRegx(
				  							voucherDetailsRQ.getVoucherId(),
				  							voucherIdRegx)){
									   
									   ErrorType error = new ErrorType();
					   					error.setErrorCode(3159);
					   					
					   					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3159, langId));
					   					errorType.add(error);
								   }else{
								   if(!voucherDAOImpl.checkVoucherId(Long.parseLong(voucherDetailsRQ.getItemId()),
										                                   voucherDetailsRQ.getVoucherId())){
									   
									   ErrorType error = new ErrorType();
					   					error.setErrorCode(3159);
					   					
					   					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3159, langId));
					   					errorType.add(error);
								   }else {
									   if(!voucherDAOImpl.checkPaidStatus(Long.parseLong(voucherDetailsRQ.getItemId()),
								   
											   voucherDetailsRQ.getVoucherId())){
										   
										   ErrorType error = new ErrorType();
						   					error.setErrorCode(3160);
						   					
						   					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3160, langId));
						   					errorType.add(error);
									   } 

					   					if(voucherDetailsRQ.isSetOrderId() && voucherDetailsRQ.getOrderId()!=null && 
												   voucherDetailsRQ.getItemId()!=null){
											   
											   if(!(this.isNumeric(voucherDetailsRQ.getOrderId()) && 
													   voucherDAOImpl.checkOrderId(Long.parseLong(voucherDetailsRQ.getOrderId()),
													   Long.parseLong(voucherDetailsRQ.getItemId()),voucherDetailsRQ.getVoucherId()))){
												   
												   ErrorType error = new ErrorType();
								   					error.setErrorCode(3158);
								   					
								   					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3158, langId));
								   					errorType.add(error);
											   }
										   }   
								   
								   }}
								   
							   }else{
								   
								   ErrorType error = new ErrorType();
				   					error.setErrorCode(3159);
				   					
				   					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3159, langId));
				   					errorType.add(error);
								}
			   					
			   				}
						  
			   			}else{
							
							ErrorType error = new ErrorType();
		   					error.setErrorCode(3157);
		   					
		   					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3157, langId));
		   					errorType.add(error);
						}
					   
					   
					  			 }else{
	    	    	
	    	    	ErrorType error = new ErrorType();
	    				error.setErrorCode(1106);
	    				
	    				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1106, 2));
	    				errorType.add(error);
	    	    }
	        	
	        	  if(errorType.size()>0){
	    			  voucherDetailsRS.setErrors(errorsType);
	  				voucherDetailsRS.setAck("Failure");
	    		  }else{
	  				voucherDetailsRS.setAck("Success");
	    		  }

	      }catch(Exception e){
			  
			  
			  e.printStackTrace();
		  }}
		
		return voucherDetailsRS;
	}

	public ListOfVouchersRS validateListOfVouchers(
			ListOfVouchersRQ listOfVouchersRQ) {
		// TODO Auto-generated method stub
		return null;
	}

	public VoucherRedemptionRS validateVoucherRedeemption(
			VoucherRedemptionRQ voucherRedeemptionRQ) {
		// TODO Auto-generated method stub
		return null;
	}

	public VoucherServiceRS validateVoucherService(
			VoucherServiceRQ voucherServiceRQ) {
		// TODO Auto-generated method stub
		return null;
	}

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
