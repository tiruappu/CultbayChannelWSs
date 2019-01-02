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
@Qualifier("listOfVouchersValidator")
public class ValidateListOfVouchersImpl implements VoucherValidator {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidateListOfVouchersImpl.class);
	
	private static final String timeStampRegx = "^[2-9]{1}[0-9]{3}-(([0]{1}[1-9]{1})|([1]{1}[0-2]{1}))-(([0]{1}[0-9]{1})|([1-2]{1}[0-9]{1})|([3]{1}[0-1]{1})) (([0]{1}[0-9]{1})|([1]{1}[0-9]{1})|([2]{1}[0-3]{1})):(([0]{1}[0-9]{1})|([1-5]{1}[0-9]{1})):(([0]{1}[0-9]{1})|([1-5]{1}[0-9]{1}))";
	
	@Autowired
    private GetErrorMessagesDAOImpl getErrorMessagesDAOImpl;
	
	@Autowired
    private TemplateModuleDAOImpl templateModuleDAOImpl;
	
	@Autowired
	private VoucherDAO voucherDAOImpl;
	
	@Autowired
	private CommonValidations commonValidations;
	
	public VoucherDetailsRS validateVoucherDetails(
			VoucherDetailsRQ voucherDetailsRQ) {
		// TODO Auto-generated method stub
		return null;
	}

	public ListOfVouchersRS validateListOfVouchers(
			ListOfVouchersRQ listOfVouchersRQ) {
		// TODO Auto-generated method stub
		
		 ListOfVouchersRS listOfVouchersRS = null;
		 int langId = 0;
		 List<Map<String,Object>> rows = null;
//	        String authCode =null;
	        int channelId = 0;
	        int sourceId = 0;
	        
		 if(listOfVouchersRQ!=null){
			 
			 try{
				
				 listOfVouchersRS = new ListOfVouchersRS();
				 ErrorsType errorsType = new ErrorsType();
				  List<ErrorType> errorType = errorsType.getError();
				 langId = getErrorMessagesDAOImpl.getLanguageId(listOfVouchersRQ.getErrorLang());
				
				  listOfVouchersRS.setTimeStamp(DateUtil.convertDateToString(new Date()));
				 if(langId>0){
					  
					  if(listOfVouchersRQ.isSetTimeStamp() && !listOfVouchersRQ.getTimeStamp().isEmpty() 
							  && listOfVouchersRQ.getTimeStamp()!=null){
					
						  if (!this.valiateRegx(
		  							listOfVouchersRQ.getTimeStamp(),
		  							timeStampRegx)) {
		  						
		        				  ErrorType error = new ErrorType();
		      					error.setErrorCode(1104);
		      					
		      					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1104, langId));
		      					errorType.add(error);
		      					listOfVouchersRS.setErrors(errorsType);
		      					listOfVouchersRS.setAck("Failure");
		      					return listOfVouchersRS;
		              		}
						}else{
									ErrorType error = new ErrorType();
			  						error.setErrorCode(1105);
								
			  						error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1105, langId));
			  						errorType.add(error);
			  						listOfVouchersRS.setErrors(errorsType);
			      					listOfVouchersRS.setAck("Failure");
			      					return listOfVouchersRS;
			  			}
					  
					  if(listOfVouchersRQ.isSetAuthenticationCode() && !listOfVouchersRQ.getAuthenticationCode().isEmpty() 
							  && listOfVouchersRQ.getAuthenticationCode()!=null){
		        		  
	        			  LOGGER.info("authentication code is :{}",listOfVouchersRQ.getAuthenticationCode());
	        			  rows = commonValidations.fetchCredential(listOfVouchersRQ.getAuthenticationCode());
					        
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
								listOfVouchersRS.setErrors(errorsType);
			  					listOfVouchersRS.setAck("Failure");
			  					return listOfVouchersRS;
					        }
	        			  
	        		 }else{
	        			  
	        			    ErrorType error = new ErrorType();
	    					error.setErrorCode(1100);
	    					
	    					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1100, langId));
	    					errorType.add(error);
	    					listOfVouchersRS.setErrors(errorsType);
	      					listOfVouchersRS.setAck("Failure");
	      					return listOfVouchersRS;
	        		}
					  if(listOfVouchersRQ.isSetSourceId() && listOfVouchersRQ.getSourceId()==sourceId){
							
							LOGGER.debug("source Id is :{}",listOfVouchersRQ.getSourceId());
							
							   
						}else{
							
							ErrorType error = new ErrorType();
							error.setErrorCode(1101);
							error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1101, langId));
							errorType.add(error);
							listOfVouchersRS.setErrors(errorsType);
		  					listOfVouchersRS.setAck("Failure");
		  					return listOfVouchersRS;
						}
					  
					 
					  
					  if(listOfVouchersRQ.isSetChannelId() && listOfVouchersRQ.getChannelId()==channelId){
	        			  
	        			  LOGGER.info("channel id is :{}",listOfVouchersRQ.getChannelId());
	        		  }else{
	        			  
	        			  ErrorType error = new ErrorType();
	  					error.setErrorCode(1102);
	  					
	  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1102, langId));
	  					errorType.add(error);
	  					listOfVouchersRS.setErrors(errorsType);
	  					listOfVouchersRS.setAck("Failure");
	  					return listOfVouchersRS;
	        		  }
					  
					  if(listOfVouchersRQ.isSetObjectId() && listOfVouchersRQ.getObjectId() >0){
		  	    			
		  	    			LOGGER.debug("object id is :{}",listOfVouchersRQ.getObjectId());
		  	    			if(templateModuleDAOImpl.checkObjectId(listOfVouchersRQ.getObjectId())){
		  	    				
		  	    				LOGGER.debug("objectId is valid");
		  	    			}else{
		  	    				
		  	    				 ErrorType error = new ErrorType();
		  	  					error.setErrorCode(3108);
		  	  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3108, langId));
		  	  					errorType.add(error);
		  	  					listOfVouchersRS.setErrors(errorsType);
		        					listOfVouchersRS.setAck("Failure");
		        					return listOfVouchersRS;
		  	    			}
		  	    		}else{
		  	    			
		  	    			 ErrorType error = new ErrorType();
		   					error.setErrorCode(3108);
		   					
		   					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3108, langId));
		   					errorType.add(error);
		   					listOfVouchersRS.setErrors(errorsType);
		    					listOfVouchersRS.setAck("Failure");
		    					return listOfVouchersRS;
		  	    	  }
					  
					  if(listOfVouchersRQ.isSetItemId() && 
							  listOfVouchersRQ.getItemId()!=null){
						  
						  if(!listOfVouchersRQ.getItemId().isEmpty() && this.isNumeric(listOfVouchersRQ.getItemId())){
						  if(!voucherDAOImpl.checkEbayItemId(Long.parseLong(listOfVouchersRQ.getItemId()),
								    listOfVouchersRQ.getObjectId())){
							  
							  	ErrorType error = new ErrorType();
			   					error.setErrorCode(3107);
			   					
			   					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3107, langId));
			   					errorType.add(error);
			   				}
						  if(listOfVouchersRQ.isSetOrderId() && listOfVouchersRQ.getOrderId()!=null)
								  {
							   if(!listOfVouchersRQ.getOrderId().isEmpty() &&
									   this.isNumeric(listOfVouchersRQ.getOrderId())){
								   LOGGER.debug("orderId is valid");
							   if(!voucherDAOImpl.checkOrderId(Long.parseLong(listOfVouchersRQ.getOrderId()),
									   Long.parseLong(listOfVouchersRQ.getItemId()),"0")){
								   
								   ErrorType error = new ErrorType();
				   					error.setErrorCode(3158);
				   					
				   					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3158, langId));
				   					errorType.add(error);
							   }
						   }else{
								ErrorType error = new ErrorType();
			   					error.setErrorCode(3158);
			   					
			   					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3158, langId));
			   					errorType.add(error);
							}}
						}else{
							ErrorType error = new ErrorType();
		   					error.setErrorCode(3107);
		   					
		   					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3107, langId));
		   					errorType.add(error);
						}}
					  
					  if(listOfVouchersRQ.isSetStatus() && listOfVouchersRQ.getStatus() >0){
						  
						    boolean statusFlag = false;
							  if(listOfVouchersRQ.getStatus()==1){
								  
								   statusFlag = true;
							  }else if(listOfVouchersRQ.getStatus()==3){
								  
							      statusFlag = true;
							  }else if(listOfVouchersRQ.getStatus()==5){
								  
								   statusFlag = true;
							  }else if(listOfVouchersRQ.getStatus() == 7){
								  
								  statusFlag = true;
							  }else if(listOfVouchersRQ.getStatus() ==9){
								  
							       statusFlag = true;
							  }
							  if(!statusFlag){
							  ErrorType error = new ErrorType();
			   					error.setErrorCode(7123);
			   					
			   					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(7123, langId));
			   					errorType.add(error);
							  }
					  }
					  if(listOfVouchersRQ.isSetRange() && listOfVouchersRQ.getRange() != null){
							
							if(listOfVouchersRQ.getRange().isSetLowerLimit() && !listOfVouchersRQ.getRange().getLowerLimit().isEmpty() 
									&& listOfVouchersRQ.getRange().getLowerLimit()!=null){
								
								LOGGER.debug("befor is numeric"+this.isNumeric(listOfVouchersRQ.getRange().getLowerLimit()));
								if(this.isNumeric(listOfVouchersRQ.getRange().getLowerLimit()) && Integer.parseInt(listOfVouchersRQ.getRange().getLowerLimit())>=0){
							
								
								LOGGER.debug("lower limit is :{}",listOfVouchersRQ.getRange().getLowerLimit());
							
								LOGGER.debug("upper limit is:{}",listOfVouchersRQ.getRange().isSetUpperLimit());
							
							if(listOfVouchersRQ.getRange().isSetUpperLimit() && 
									this.isNumeric(listOfVouchersRQ.getRange().getUpperLimit()) 
									&& Integer.parseInt(listOfVouchersRQ.getRange().getUpperLimit())>0){
								LOGGER.debug("upper limit is :{}",listOfVouchersRQ.getRange().getUpperLimit());
							
							
							int range = Integer.parseInt(listOfVouchersRQ.getRange().getUpperLimit()) - Integer.parseInt(listOfVouchersRQ.getRange().getLowerLimit());
							
							   if( range <= 100 && range >0){
								   
								   LOGGER.debug("range is:{}",range);
							   }else{
								   ErrorType error = new ErrorType();
				  					error.setErrorCode(3129);
				  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3129, langId));
				  					errorType.add(error);
							   }
							}  else{
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
						
						if(listOfVouchersRQ.isSetPeriod() && listOfVouchersRQ.getPeriod() != null){
							
							if(listOfVouchersRQ.getPeriod().isSetFrom() && !listOfVouchersRQ.getPeriod().getFrom().isEmpty() && listOfVouchersRQ.getPeriod().getFrom()!=null){
								
								if (!this.valiateRegx(
				  							listOfVouchersRQ.getPeriod().getFrom(),
				  							timeStampRegx)) {
									ErrorType error = new ErrorType();
				  					error.setErrorCode(3121);
				  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3121, langId));
				  					errorType.add(error);
				  					}else{
				  						
				  						LOGGER.debug("from date is :{}",listOfVouchersRQ.getPeriod().getFrom());
				  					}
							}else{
								
								ErrorType error = new ErrorType();
			  					error.setErrorCode(3121);
			  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3121, langId));
			  					errorType.add(error);
							}
							
							if(listOfVouchersRQ.getPeriod().isSetTo() && !listOfVouchersRQ.getPeriod().getTo().isEmpty() && listOfVouchersRQ.getPeriod().getTo()!=null){
								
								if (!this.valiateRegx(
			  							listOfVouchersRQ.getPeriod().getTo(),
			  							timeStampRegx)) {
									ErrorType error = new ErrorType();
				  					error.setErrorCode(3123);
				  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3123, langId));
				  					errorType.add(error);
									
			  					}else{
			  						
			  						LOGGER.debug("To date is :{}",listOfVouchersRQ.getPeriod().getTo());
			  						if (listOfVouchersRQ.getPeriod().getFrom().compareTo(listOfVouchersRQ.getPeriod().getTo()) >= 0) {
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
						
						if(listOfVouchersRQ.isSetBuyerId()){
							
							if(listOfVouchersRQ.getBuyerId().length()>100){
								ErrorType error = new ErrorType();
			  					error.setErrorCode(7135);
			  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(7135, langId));
			  					errorType.add(error);
							}else if(!listOfVouchersRQ.getBuyerId().equals("")){
								String t=" !@$%^&#()+\\=\\[\\]{};':\\\"\\\\|,<>\\/?";
								int theCount=0;
								//int buyeridlength=listOfVouchersRQ.getBuyerId().length()+1;
								for (int i = 0; i < t.length(); i++) {
							         
							        // System.out.println("couner"+i);
									//if(i<=buyeridlength){
							         if (listOfVouchersRQ.getBuyerId().contains(t.substring(i, i+1))) {
							             theCount++;
							         }
									//}
							     }
								if(theCount>0){
									ErrorType error = new ErrorType();
				  					error.setErrorCode(7136);
				  					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(7136, langId));
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
		   			  listOfVouchersRS.setErrors(errorsType);
		 				listOfVouchersRS.setAck("Failure");
		   		  	}else{
		 				listOfVouchersRS.setAck("Success");
		   		  	}
			 }catch(Exception e){
				 
				 e.printStackTrace();
			 }
			 
		}
		
		return listOfVouchersRS;
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
