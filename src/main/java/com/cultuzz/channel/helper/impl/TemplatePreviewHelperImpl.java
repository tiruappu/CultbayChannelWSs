package com.cultuzz.channel.helper.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.cultuzz.channel.DAO.TemplateModuleDAO;
import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.XMLpojo.TemplatePreviewRQ;
import com.cultuzz.channel.XMLpojo.TemplatePreviewRS;
import com.cultuzz.channel.helper.TemplatePreviewHelper;
import com.cultuzz.channel.util.CommonValidations;
import com.cultuzz.utils.PropertiesLoader;

@Repository
public class TemplatePreviewHelperImpl implements TemplatePreviewHelper{
	
	@Autowired
	TemplateModuleDAO templateModuleDAO;
	
	@Autowired
	CommonValidations commonValidations;
	
	@Autowired
	GetErrorMessagesDAOImpl getErrormessages;
	
	@Autowired
	@Qualifier("propertiesRead")
	PropertiesLoader properties;
	
	/*java.util.Date date = new java.util.Date();
	Timestamp timestamp = new Timestamp(date.getTime());
	*/
	private static final Logger logger = LoggerFactory.getLogger(TemplatePreviewHelperImpl.class);
	public TemplatePreviewRS validatePreviewRQ(TemplatePreviewRQ templatePreviewRQ){
		
		TemplatePreviewRS templatePreviewRS=new TemplatePreviewRS();
		templatePreviewRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		ErrorsType errors=new ErrorsType();
		
		List<ErrorType> errortypeList=errors.getError();
		
		//List<String> previewErrors=new ArrayList<String>();
		
	int langid=0;
		
	if(templatePreviewRQ.isSetAuthenticationCode()){
		
		boolean authCodeStatus=	commonValidations.checkAuthCode(templatePreviewRQ.getAuthenticationCode());
			if(!authCodeStatus){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1100);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,2));
				errortypeList.add(errorType);
				templatePreviewRS.setErrors(errors);
				templatePreviewRS.setAck("Failure");
				return templatePreviewRS;
			}
				
			
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1100);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,2));
			errortypeList.add(errorType);
			templatePreviewRS.setErrors(errors);
			templatePreviewRS.setAck("Failure");
			return templatePreviewRS;
			
		}
		
		if(templatePreviewRQ.isSetErrorLang()){
			
			langid=commonValidations.checkErrorLangCode(templatePreviewRQ.getErrorLang());
			
			if(langid>0){
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1106);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,2));
				errortypeList.add(errorType);
				templatePreviewRS.setErrors(errors);
				templatePreviewRS.setAck("Failure");
				return templatePreviewRS;
			}
			
		}else{				
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1106);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,2));
			errortypeList.add(errorType);
			templatePreviewRS.setErrors(errors);
			templatePreviewRS.setAck("Failure");
			return templatePreviewRS;
		}
		
		
		if(templatePreviewRQ.isSetTimeStamp()){
			
			boolean timestampStatus = commonValidations.checkTimeStamp(templatePreviewRQ.getTimeStamp());
			if(!timestampStatus){	
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1104);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1104,langid));
				errortypeList.add(errorType);
				templatePreviewRS.setErrors(errors);
				templatePreviewRS.setAck("Failure");
				return templatePreviewRS;
			}
			
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1105);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1105,langid));
				errortypeList.add(errorType);
				templatePreviewRS.setErrors(errors);
				templatePreviewRS.setAck("Failure");
				return templatePreviewRS;
			}
		
		
		/*if(templateDeletionRQ.isSetAuthenticationCode() && !templateDeletionRQ.getAuthenticationCode().isEmpty()){
			
		}else
			listOfDeleteErrors.add("Authentication Code missed");*/
		
		if(templatePreviewRQ.isSetSourceId() ){
			if(!commonValidations.checkSourceId(templatePreviewRQ.getSourceId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1101);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langid));
				errortypeList.add(errorType);
				templatePreviewRS.setErrors(errors);
				templatePreviewRS.setAck("Failure");
				return templatePreviewRS;
			}
		
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1101);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langid));
			errortypeList.add(errorType);
			templatePreviewRS.setErrors(errors);
			templatePreviewRS.setAck("Failure");
			return templatePreviewRS;
			
		}
		
		if(templatePreviewRQ.isSetChannelId()){
			
			if(!commonValidations.checkChannelId(templatePreviewRQ.getChannelId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1102);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langid));
				errortypeList.add(errorType);
				templatePreviewRS.setErrors(errors);
				templatePreviewRS.setAck("Failure");
				return templatePreviewRS;
			}
			
		}else{
			
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1102);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langid));
			errortypeList.add(errorType);
			
		}
		boolean objectIdFlag=false;
		if(templatePreviewRQ.isSetObjectId()){
			objectIdFlag=commonValidations.checkObjectId(templatePreviewRQ.getObjectId());
			logger.debug("Checking objectid");
			if(!commonValidations.checkObjectId(templatePreviewRQ.getObjectId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1103);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
				errortypeList.add(errorType);
				templatePreviewRS.setErrors(errors);
				templatePreviewRS.setAck("Failure");
				return templatePreviewRS;
			}
			
			
		}else{
			
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1103);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
			errortypeList.add(errorType);
			templatePreviewRS.setErrors(errors);
			templatePreviewRS.setAck("Failure");
			return templatePreviewRS;
			
		}
	
		boolean templateidStatus=false;
		if(templatePreviewRQ.isSetTemplateId()&& templatePreviewRQ.getTemplateId()>0){
			
			templateidStatus=templateModuleDAO.checkTemplateId(templatePreviewRQ.getTemplateId(),templatePreviewRQ.getObjectId());
			
			if(!templateidStatus){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2113);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2113,langid));
				errortypeList.add(errorType);
			}
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2113);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2113,langid));
			errortypeList.add(errorType);
		}
			
		if(templateidStatus && objectIdFlag){
			int objectid=templateModuleDAO.getObjectId(templatePreviewRQ.getTemplateId());
			
			if(objectid==templatePreviewRQ.getObjectId()){
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2128);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2128,langid));
				errortypeList.add(errorType);
			}
		}
		
		if(errortypeList.size()>0){
			templatePreviewRS.setAck("Fail");
			templatePreviewRS.setErrors(errors);
			
		}else
			templatePreviewRS.setAck("Success");
		
		//templatePreviewRS.setErrors(errors);
		//templatePreviewRS.setTimeStamp(timestamp.toString());
		
		return templatePreviewRS;
	}
	
	public TemplatePreviewRS processPreviewRQ(TemplatePreviewRQ templatePreviewRQ){
		logger.debug("inside template preview process in helper");
		TemplatePreviewRS templatePreviewRS=new TemplatePreviewRS();
		templatePreviewRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		int langid=commonValidations.checkErrorLangCode(templatePreviewRQ.getErrorLang());

		/*try{
			 DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	         String startDate =timestamp.toString();
	         Date frmDate = sdf.parse(startDate);
	         DateFormat sdff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	         String frmDateStr = sdff.format(frmDate);
	         templatePreviewRS.setTimeStamp(frmDateStr);
			}catch(Exception e){
				e.printStackTrace();
			}*/
		String htmlString="";
		ErrorsType errors=new ErrorsType();
		//List<String> previewErrors=new ArrayList<String>();
		List<ErrorType> errorTypeList=errors.getError();
		if(templatePreviewRQ.getTemplateId()>0){
			logger.debug("before getting object id ");
			int templateid=templatePreviewRQ.getTemplateId();
		int objectid=templateModuleDAO.getObjectId(templatePreviewRQ.getTemplateId());
		logger.debug("object id is"+objectid);
	
		//http://backups.cultuzz.de/marktplaetze/ebay/einstellungen/AuctionMasterPreview.php?objectID=122&auctionMasterID=49689&userLanguage=2
		try{
			
		//String url="http://albatros.cultuzz.de/marktplaetze/ebay/einstellungen/AuctionMasterPreview.php?objectID="+objectid+"&auctionMasterID="+templateid+"&userLanguage=2&sourceid="+templatePreviewRQ.getSourceId();	
		//String url ="http://backups.cultuzz.de/marktplaetze/ebay/einstellungen/AuctionMasterPreview.php?objectID="+objectid+"&auctionMasterID="+templateid+"&userLanguage=2&sourceid="+templatePreviewRQ.getSourceId();
			logger.debug("this is template preview url from properties file"+properties.getTemplatePreviewURL());	
		String url =properties.getTemplatePreviewURL()+"?objectID="+objectid+"&auctionMasterID="+templateid+"&userLanguage=2&sourceid="+templatePreviewRQ.getSourceId();
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		//con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'GET' request to URL : " + url);
		//System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		htmlString=response.toString();
		
		templatePreviewRS.setHtmlContent(htmlString);
		
		//print result
		//System.out.println("============="+response.toString());
		
		}catch(Exception e){
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2129);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2129,langid));
			errorTypeList.add(errorType);
			e.printStackTrace();
		}
		
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2113);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2113,langid));
			errorTypeList.add(errorType);
		}
		
		if(errorTypeList.size()>0){
		templatePreviewRS.setAck("Failure");
		templatePreviewRS.setErrors(errors);
		}else
			templatePreviewRS.setAck("Success");
		
		 
		
		
		return templatePreviewRS;
	}

}