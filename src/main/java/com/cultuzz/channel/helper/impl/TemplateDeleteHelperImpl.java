package com.cultuzz.channel.helper.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cultuzz.channel.DAO.TemplateModuleDAO;
import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.XMLpojo.TemplateDeletionRQ;
import com.cultuzz.channel.XMLpojo.TemplateDeletionRS;
import com.cultuzz.channel.helper.TemplateDeleteHelper;
import com.cultuzz.channel.util.CommonValidations;

@Repository
public class TemplateDeleteHelperImpl implements TemplateDeleteHelper{
	
	@Autowired
	TemplateModuleDAO templateModuleDAO;
	
	@Autowired
	CommonValidations commonValidations;
	
	@Autowired
	GetErrorMessagesDAOImpl getErrormessages;

	/*java.util.Date date = new java.util.Date();
	Timestamp timestamp = new Timestamp(date.getTime());*/
	private static final Logger LOGGER = LoggerFactory.getLogger(TemplateDeleteHelperImpl.class);
	
	public TemplateDeletionRS validateTemplateDeleteRQ(TemplateDeletionRQ templateDeletionRQ){
		
		TemplateDeletionRS templateDeletionRS=new TemplateDeletionRS();
		templateDeletionRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		ErrorsType deleteErrors=new ErrorsType();
		List<ErrorType> deleteErrorsList= deleteErrors.getError();
		ErrorType deleteError=new ErrorType();
		//List<String> listOfDeleteErrors=new ArrayList<String>();
		boolean tamplateidflag=false;
		int langid=0;
		
		
		if(templateDeletionRQ.isSetAuthenticationCode()){
			
			boolean authCodeStatus=	commonValidations.checkAuthCode(templateDeletionRQ.getAuthenticationCode());
				if(!authCodeStatus){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1100);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,2));
					deleteErrorsList.add(errorType);
					templateDeletionRS.setErrors(deleteErrors);
					templateDeletionRS.setAck("Failure");
					return templateDeletionRS;
				}
					
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1100);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,2));
				deleteErrorsList.add(errorType);
				templateDeletionRS.setErrors(deleteErrors);
				templateDeletionRS.setAck("Failure");
				return templateDeletionRS;
				
			}
		
		if(templateDeletionRQ.isSetErrorLang()){
			
			langid=commonValidations.checkErrorLangCode(templateDeletionRQ.getErrorLang());
			
			if(langid>0){
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1106);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,2));
				deleteErrorsList.add(errorType);
				templateDeletionRS.setErrors(deleteErrors);
				templateDeletionRS.setAck("Failure");
				return templateDeletionRS;
			}
			
		}else{				
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1106);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,2));
			deleteErrorsList.add(errorType);
			templateDeletionRS.setErrors(deleteErrors);
			templateDeletionRS.setAck("Failure");
			return templateDeletionRS;
		}
		
		
		if(templateDeletionRQ.isSetTimeStamp()){
			
			boolean timestampStatus = commonValidations.checkTimeStamp(templateDeletionRQ.getTimeStamp());
			if(!timestampStatus){	
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1104);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1104,langid));
				deleteErrorsList.add(errorType);
				templateDeletionRS.setErrors(deleteErrors);
				templateDeletionRS.setAck("Failure");
				return templateDeletionRS;
			}
			
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1105);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1105,langid));
				deleteErrorsList.add(errorType);
				templateDeletionRS.setErrors(deleteErrors);
				templateDeletionRS.setAck("Failure");
				return templateDeletionRS;
			}
		
		
		/*if(templateDeletionRQ.isSetAuthenticationCode() && !templateDeletionRQ.getAuthenticationCode().isEmpty()){
			
		}else
			listOfDeleteErrors.add("Authentication Code missed");*/
		
		if(templateDeletionRQ.isSetSourceId() ){
			if(!commonValidations.checkSourceId(templateDeletionRQ.getSourceId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1101);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langid));
				deleteErrorsList.add(errorType);
				templateDeletionRS.setErrors(deleteErrors);
				templateDeletionRS.setAck("Failure");
				return templateDeletionRS;
			}
		
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1101);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langid));
			deleteErrorsList.add(errorType);
			templateDeletionRS.setErrors(deleteErrors);
			templateDeletionRS.setAck("Failure");
			return templateDeletionRS;
			
		}
		
		if(templateDeletionRQ.isSetChannelId()){
			
			if(!commonValidations.checkChannelId(templateDeletionRQ.getChannelId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1102);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langid));
				deleteErrorsList.add(errorType);
				templateDeletionRS.setErrors(deleteErrors);
				templateDeletionRS.setAck("Failure");
				return templateDeletionRS;
			}
			
		}else{
			
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1102);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langid));
			deleteErrorsList.add(errorType);
			templateDeletionRS.setErrors(deleteErrors);
			templateDeletionRS.setAck("Failure");
			return templateDeletionRS;
			
		}
		
		if(templateDeletionRQ.isSetObjectId()){
			
			LOGGER.debug("Checking objectid");
			if(!commonValidations.checkObjectId(templateDeletionRQ.getObjectId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1103);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
				deleteErrorsList.add(errorType);
				templateDeletionRS.setErrors(deleteErrors);
				templateDeletionRS.setAck("Failure");
				return templateDeletionRS;
			}
			
			
		}else{
			
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1103);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
			deleteErrorsList.add(errorType);
			templateDeletionRS.setErrors(deleteErrors);
			templateDeletionRS.setAck("Failure");
			return templateDeletionRS;
			
		}
		
		if(templateDeletionRQ.isSetTemplateId()){
			
			tamplateidflag=templateModuleDAO.checkTemplateId(templateDeletionRQ.getTemplateId(),templateDeletionRQ.getObjectId());
			
			LOGGER.debug("Status of tamplateid inside delete template"+tamplateidflag);
			
			if(!tamplateidflag){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2113);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2113,langid));
				deleteErrorsList.add(errorType);
			}
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2113);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2113,langid));
			deleteErrorsList.add(errorType);
		}
		
		 if(tamplateidflag){
			 boolean deleteflag=templateModuleDAO.checkTemplateDeleteStatus(templateDeletionRQ.getTemplateId());
			 LOGGER.debug("Template Delete flag status==="+deleteflag);
			 if(deleteflag){
				 ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2127);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2127,langid));
					deleteErrorsList.add(errorType);
			 
			 }
		 }
			 
		 
		 
		 if(deleteErrorsList.size()>0){
		 templateDeletionRS.setAck("Failure");
		 templateDeletionRS.setErrors(deleteErrors);
		 }else
			 templateDeletionRS.setAck("Success"); 
		 
		 //templateDeletionRS.setTimeStamp(timestamp.toString());
		 return templateDeletionRS;
		
	}
	 
	 public TemplateDeletionRS processTemplateDeleteRQ(TemplateDeletionRQ templateDeletionRQ){
		 TemplateDeletionRS templateDeletionRS=new TemplateDeletionRS();
			templateDeletionRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		 ErrorsType delteTemplateErrors=new ErrorsType();
		 List<ErrorType> deleteErrorsList=delteTemplateErrors.getError();
		int langid=commonValidations.checkErrorLangCode(templateDeletionRQ.getErrorLang());
		 if(templateDeletionRQ.getTemplateId()!=0){
			 boolean templateDeleteFlag=templateModuleDAO.deleteTemplate(templateDeletionRQ.getTemplateId());
			 
			 if(templateDeleteFlag){
				 templateDeletionRS.setAck("Success");
				
			 }else{
				 templateDeletionRS.setAck("Failure");
				 ErrorType deleteErrors=new ErrorType();
				 deleteErrors.setErrorCode(2126);
				 deleteErrors.setErrorMessage(getErrormessages.getErrorMessage(2126,langid));
				 deleteErrorsList.add(deleteErrors);
				 templateDeletionRS.setErrors(delteTemplateErrors);
				 
			 }
		 }
		 
		 /*try{
			 DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	         String startDate =timestamp.toString();
	         Date frmDate = sdf.parse(startDate);
	         DateFormat sdff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	         String frmDateStr = sdff.format(frmDate);
	         templateDeletionRS.setTimeStamp(frmDateStr);
			}catch(Exception e){
				e.printStackTrace();
			}*/
		 
		
		 
		 return templateDeletionRS;
	 }
}