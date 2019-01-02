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

import com.cultuzz.channel.DAO.DesignTemplateDAO;
import com.cultuzz.channel.DAO.ObjectConfigurationDAO;
import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.XMLpojo.ListOfDesignTemplatesRQ;
import com.cultuzz.channel.XMLpojo.ListOfDesignTemplatesRS;
import com.cultuzz.channel.XMLpojo.ListOfDesignTemplatesRS.DesignTemplates;
import com.cultuzz.channel.XMLpojo.ListOfDesignTemplatesRS.DesignTemplates.DesignTemplate;
import com.cultuzz.channel.XMLpojo.ListOfDesignTemplatesRS.DesignTemplates.DesignTemplate.Categories;
import com.cultuzz.channel.XMLpojo.ListOfDesignTemplatesRS.DesignTemplates.DesignTemplate.Categories.Category;
import com.cultuzz.channel.helper.DesignTemplateHelper;
import com.cultuzz.channel.util.CommonValidations;
import com.cultuzz.channel.util.DateUtil;

@Component
public class DesignTemplateHelperImpl implements DesignTemplateHelper{

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidateListOfTemplatesImpl.class);

	private static final String timeStampRegx = "^[2-9]{1}[0-9]{3}-(([0]{1}[1-9]{1})|([1]{1}[0-2]{1}))-(([0]{1}[0-9]{1})|([1-2]{1}[0-9]{1})|([3]{1}[0-1]{1})) (([0]{1}[0-9]{1})|([1]{1}[0-9]{1})|([2]{1}[0-3]{1})):(([0]{1}[0-9]{1})|([1-5]{1}[0-9]{1})):(([0]{1}[0-9]{1})|([1-5]{1}[0-9]{1}))";
	
	@Autowired
	private GetErrorMessagesDAOImpl getErrorMessagesDAOImpl;
	
	@Autowired
	private CommonValidations commonValidations;
	
	@Autowired
	private DesignTemplateDAO designTemplateDAOImpl;
	
	 @Autowired
	ObjectConfigurationDAO objectConfigurationDAO;
	
	public ListOfDesignTemplatesRS validateListOfDesignTemplate(
			ListOfDesignTemplatesRQ listOfDesignTemplatesRQ) {
		// TODO Auto-generated method stub
		
		LOGGER.info("Inside the List of Designtemplates validation");
				
				ListOfDesignTemplatesRS listOfDesignTemplatesRS = null;
				
				 String IdRegx = "[0-9]{1,20}$";
		        int langId = 0;
		        List<Map<String,Object>> rows = null;
		        String authCode =null;
		        int channelId = 0;
				int sourceId = 0;
				boolean objectIdFlag = false;
				int objectId = 0;
				
			try{
				
				listOfDesignTemplatesRS = new ListOfDesignTemplatesRS();
				
				ErrorsType errorsType = new ErrorsType();
				List<ErrorType> errorType= errorsType.getError();
				
				LOGGER.debug("error language is :{}",listOfDesignTemplatesRQ.getErrorLang());
				langId = getErrorMessagesDAOImpl.getLanguageId(listOfDesignTemplatesRQ.getErrorLang());
				
				LOGGER.debug("langId is :{}",langId);
				
				listOfDesignTemplatesRS.setTimeStamp(DateUtil.convertDateToString(new Date()));
				
				if(langId >0){
					
				if(listOfDesignTemplatesRQ == null){
					
						ErrorType error = new ErrorType();
						error.setErrorCode(13001);
						error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(13001, langId));
						errorType.add(error);

				}else{
					
					if(listOfDesignTemplatesRQ.isSetTimeStamp() && !listOfDesignTemplatesRQ.getTimeStamp().isEmpty() && listOfDesignTemplatesRQ.getTimeStamp()!=null){
						if (!this.valiateRegx(
									listOfDesignTemplatesRQ.getTimeStamp(),
									timeStampRegx)) {
								ErrorType error = new ErrorType();
		      					error.setErrorCode(1104);
		      					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1104, langId));
		      					errorType.add(error);
		      					listOfDesignTemplatesRS.setErrors(errorsType);
		      					listOfDesignTemplatesRS.setAck("Failure");
		      					return listOfDesignTemplatesRS;
							}
					}else{
							ErrorType error = new ErrorType();
							error.setErrorCode(1105);
							error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1105, langId));
							errorType.add(error);
							listOfDesignTemplatesRS.setErrors(errorsType);
		  					listOfDesignTemplatesRS.setAck("Failure");
		  					return listOfDesignTemplatesRS;
					}
					
					
					if(listOfDesignTemplatesRQ.isSetAuthenticationCode() && !listOfDesignTemplatesRQ.getAuthenticationCode().isEmpty() && listOfDesignTemplatesRQ.getAuthenticationCode()!=null){
						
						LOGGER.debug("Authentication code is :{}",listOfDesignTemplatesRQ.getAuthenticationCode());
						
						
						rows = commonValidations.fetchCredential(listOfDesignTemplatesRQ.getAuthenticationCode());
				        
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
							listOfDesignTemplatesRS.setErrors(errorsType);
		  					listOfDesignTemplatesRS.setAck("Failure");
		  					return listOfDesignTemplatesRS;
				        }
						
					}else{

					    ErrorType error = new ErrorType();
						error.setErrorCode(1100);
						error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1100, langId));
						errorType.add(error);
						listOfDesignTemplatesRS.setErrors(errorsType);
							listOfDesignTemplatesRS.setAck("Failure");
							return listOfDesignTemplatesRS;
					}
					
				if(listOfDesignTemplatesRQ.isSetSourceId() && listOfDesignTemplatesRQ.getSourceId()==sourceId){
					
					LOGGER.debug("source Id is :{}",listOfDesignTemplatesRQ.getSourceId());
					
				}else{
					
					ErrorType error = new ErrorType();
					error.setErrorCode(1101);
					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1101, langId));
					errorType.add(error);
					listOfDesignTemplatesRS.setErrors(errorsType);
						listOfDesignTemplatesRS.setAck("Failure");
						return listOfDesignTemplatesRS;
				}
					
					
					if(listOfDesignTemplatesRQ.isSetChannelId() && listOfDesignTemplatesRQ.getChannelId()==channelId){
					
						LOGGER.debug("channel id is :{}",listOfDesignTemplatesRQ.getChannelId());
					}else{
					    
						ErrorType error = new ErrorType();
							error.setErrorCode(1102);
							error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1102, langId));
							errorType.add(error);
							listOfDesignTemplatesRS.setErrors(errorsType);
							listOfDesignTemplatesRS.setAck("Failure");
							return listOfDesignTemplatesRS;
					}
					
					if(listOfDesignTemplatesRQ.isSetObjectId()){
						
						LOGGER.debug("Checking objectid");
						if(!commonValidations.checkObjectId(listOfDesignTemplatesRQ.getObjectId())){
							ErrorType error =new ErrorType();
							error.setErrorCode(1103);
							error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1103,langId));
							errorType.add(error);
							listOfDesignTemplatesRS.setErrors(errorsType);
							listOfDesignTemplatesRS.setAck("Failure");
							return listOfDesignTemplatesRS;
						}else{
							objectIdFlag=true;
							objectId=listOfDesignTemplatesRQ.getObjectId();
							
							boolean ebaydatenStatus=objectConfigurationDAO.checkEbayDaten(objectId);
							if(!ebaydatenStatus){
								ErrorType error=new ErrorType();
								error.setErrorCode(1107);
								error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1107,langId));
								errorType.add(error);
								listOfDesignTemplatesRS.setErrors(errorsType);
								listOfDesignTemplatesRS.setAck("Failure");
								return listOfDesignTemplatesRS;
							}
						}
						
					}else{
						
						ErrorType error=new ErrorType();
						error.setErrorCode(1103);
						error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1103,langId));
						errorType.add(error);
						listOfDesignTemplatesRS.setErrors(errorsType);
						listOfDesignTemplatesRS.setAck("Failure");
						return listOfDesignTemplatesRS;
						
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
					  listOfDesignTemplatesRS.setErrors(errorsType);
						listOfDesignTemplatesRS.setAck("Failure");
				  }else{
						listOfDesignTemplatesRS.setAck("Success");
				  }
				
			}catch(Exception e){
				
				e.printStackTrace();

				}
			
					return listOfDesignTemplatesRS;

			}


	public ListOfDesignTemplatesRS processListOfDesigntemplates(ListOfDesignTemplatesRQ listOfDesignTemplatesRQ){
		
		
		ListOfDesignTemplatesRS listOfDesignTemplatesRS = null;
		int langId = 0;
		LOGGER.debug("inside the list of designtemplates helper");
		int totalDesignTemplates = 0;
		
		try{
			
			listOfDesignTemplatesRS = new ListOfDesignTemplatesRS();
			ErrorsType errorsType = new ErrorsType();
			List<ErrorType> errorType = errorsType.getError();
			List<Map<String,Object>> listdesignTemplates = null;
			

			langId = getErrorMessagesDAOImpl.getLanguageId(listOfDesignTemplatesRQ.getErrorLang());

			listOfDesignTemplatesRS.setTimeStamp(DateUtil
					.convertDateToString(new Date()));
			
			if(langId > 0){
				
				LOGGER.debug("language id is:{}", langId);
				
				if (listOfDesignTemplatesRQ == null) {

					ErrorType error = new ErrorType();
					error.setErrorCode(13001);

					error.setErrorMessage(getErrorMessagesDAOImpl
							.getErrorMessage(13001, langId));
					errorType.add(error);
					// throw new Exception("offer creation is null");
				}else{
					
					totalDesignTemplates = designTemplateDAOImpl.getDesignTemplatesCount(listOfDesignTemplatesRQ.getObjectId());
					
					listOfDesignTemplatesRS.setTotalNoOfDesignTemplates(totalDesignTemplates);
					
					if(totalDesignTemplates > 0){
					DesignTemplates designTemplates = new DesignTemplates();
					
					listdesignTemplates = designTemplateDAOImpl.getDesignTemplates(listOfDesignTemplatesRQ.getObjectId());
					if(listdesignTemplates!=null){
					for(Map<String,Object> desinTemplateMap : listdesignTemplates){
						
						DesignTemplate designtemplate = new DesignTemplate();
						
						int templateId = (Integer)desinTemplateMap.get("id"); 
						designtemplate.setId(templateId);
						designtemplate.setName(String.valueOf(desinTemplateMap.get("bezeichnung")));
						designtemplate.setSampledesign(String.valueOf(desinTemplateMap.get("sampledesign")));
						
						List<Map<String,Object>> listcategories = null;
						listcategories = designTemplateDAOImpl.getDesingTemplateCategories(templateId);	
						if(listcategories!=null){
						Categories categories = new Categories();
						
						for(Map<String,Object> categoriesMap : listcategories){
							
							Category category = new Category();
							
							category.setCategoryId((Integer)categoriesMap.get("id"));
							
							category.setCategoryName(String.valueOf(categoriesMap.get("category")));
							category.setHeaderURL("https://"+String.valueOf(categoriesMap.get("header_url")));
							category.setFooterURL("https://"+String.valueOf(categoriesMap.get("footer_url")));
							
							categories.getCategory().add(category);
						}
						
						designtemplate.setCategories(categories);
						}
						designTemplates.getDesignTemplate().add(designtemplate);
					}}
					
						listOfDesignTemplatesRS.setDesignTemplates(designTemplates);
				
				}
				
				}
			}else {

				ErrorType error = new ErrorType();
				error.setErrorCode(1106);

				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						1106, langId));
				errorType.add(error);
			}

			if (errorType.size() > 0) {
				listOfDesignTemplatesRS.setErrors(errorsType);
				listOfDesignTemplatesRS.setAck("Failure");
			} else {
				listOfDesignTemplatesRS.setAck("Success");
			}
			
			
		}catch(Exception e){
			
			e.printStackTrace();
		}

		
		return listOfDesignTemplatesRS;
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

