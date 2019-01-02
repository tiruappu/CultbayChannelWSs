package com.cultuzz.channel.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.DAO.impl.ValidationsDAO;

@Component
public class CommonValidations {
	
	
	@Autowired
	ValidationsDAO validationsDAO;
	private static final Logger logger = LoggerFactory.getLogger(CommonValidations.class);
	//these validations checking will be modified 
	
	//private static final String timeStampRegx = "^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";

	//private static final String timeStampRegx = "^[0-9]{4}-[0-1]{1}[0-9]{1}-[0-3]{1}[0-9]{1} [0-9]{2}:[0-9]{2}:[0-9]{2}";
	private static final String timeStampRegx = "^[2-9]{1}[0-9]{3}-(([0]{1}[1-9]{1})|([1]{1}[0-2]{1}))-(([0]{1}[0-9]{1})|([1-2]{1}[0-9]{1})|([3]{1}[0-1]{1})) (([0]{1}[0-9]{1})|([1]{1}[0-9]{1})|([2]{1}[0-3]{1})):(([0]{1}[0-9]{1})|([1-5]{1}[0-9]{1})):(([0]{1}[0-9]{1})|([1-5]{1}[0-9]{1}))";

	 List<Map<String,Object>> rows = null;
	 int channelId = 0;
     int sourceId = 0;
	
	public boolean checkTimeStamp(String rqTimeStamp){
		boolean timeStampStatus=false;
		
		if(!this.validateRegx(rqTimeStamp,timeStampRegx))
			timeStampStatus=false;
		else
			timeStampStatus=true;		
		
		return timeStampStatus;
	}
	
	public boolean checkDateStamp(String rqdateStamp){
		boolean timeStampStatus=false;
		
		if(!this.validateRegx(rqdateStamp,timeStampRegx))
			timeStampStatus=false;
		else
			timeStampStatus=true;		
		
		return timeStampStatus;
	}
	
	public boolean checkAuthCode(String authCode){
		boolean authCodeStatus=false;
		
		rows=this.fetchCredential(authCode);
		if(null!=rows){
		if(rows.size()>0){
	        for(Map<String, Object> credentails : rows){
//	                 authCode = credentails.get("auth_code").toString();
	                 sourceId = Integer.parseInt(credentails.get("sourceId").toString());
	                 channelId = Integer.parseInt(credentails.get("channelId").toString());
	        }
			authCodeStatus=true;
	        }
		}
		
		return authCodeStatus;
	}
	
	
	public boolean checkSourceId(int sourceId){
		
		boolean sourceIdStatus=false;
		//rows=this.fetchCredential(authCode);
		
		if(sourceId!=this.sourceId)
			sourceIdStatus=false;
		else
			sourceIdStatus=true;
		return sourceIdStatus;
	}
	
	public boolean checkChannelId(int channelId1){
		
		boolean channelidFlag=false;
		
		if(channelId1!=this.channelId)
			channelidFlag=false;
		else
			channelidFlag=true;
		return channelidFlag;
	}
	
	public boolean checkObjectId(int objectId){
		boolean objectIdFlag=false;
		
		objectIdFlag=validationsDAO.checkObjectId(objectId);
		
		return objectIdFlag;
	}
	
	public int checkErrorLangCode(String errorCode){
		
	int langid=	validationsDAO.getLanguageId(errorCode);
		
		return langid;
	}
	
	
	
	
	/**
	 * This method used to validate the values with regular expressions
	 * 
	 * @param regx,expression
	 * @return true/false
	 */
	public boolean validateRegx(String expression, String regx) {
		Pattern p = null;
		Matcher m = null;
		p = Pattern.compile(regx);
		m = p.matcher(expression);
		if (!m.matches()) {
			return false;
		}
		return true;
	}
	
	
	public String getCurrentTimeStamp(){
		
		String dateInString="";
		try{
			java.util.Date date = new java.util.Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			 DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
	         String startDate =timestamp.toString();
	         Date frmDate = sdf.parse(startDate);
	         DateFormat sdff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	         sdff.setTimeZone(TimeZone.getTimeZone("GMT"));
	         dateInString = sdff.format(frmDate);
	         //templatePreviewRS.setTimeStamp(frmDateStr);
			}catch(Exception e){
				e.printStackTrace();
				
			}
		logger.debug("Current GMT time stamp is "+dateInString);
		return dateInString;
	}
	
	public void saveLogData(int objectid,int vorlageid,int auctionid,String ebayitemid,String request,String response,int type,int status,long processtime){
		logger.debug("Logger values in sequence objectid==="+objectid+ "vorlageid==="+vorlageid+" auctionid=="+auctionid+" ebayitemid=="+ebayitemid+" request=="+request+" response=="+response+" type=="+type+" status=="+status+" processtime=="+processtime);
		LogPojo logPojo=new LogPojo();
		
		logPojo.setObjectid(objectid);
		logPojo.setVorlageid(vorlageid);
		logPojo.setAuctionid(auctionid);
		logPojo.setEbayitemid(ebayitemid);
		logPojo.setRequest(request);
		logPojo.setResponse(response);
		logPojo.setType(type);
		logPojo.setStatus(status);
		logPojo.setProcesstime(processtime);
		
			validationsDAO.saveLogDatetoDb(logPojo);
		
	}

	public long calculateProcessTime(long startTime,long endtime){
		long processtime=0;
		processtime=endtime-startTime;
		return processtime;
	}
	
	
	public  List<Map<String,Object>> fetchCredential(String authCode){
		return validationsDAO.fetchCredential(authCode);
		
	}
	
	/**
	 * This method is used to validate the marketplace for retail Price 
	 * @param ebaySiteId
	 * @return
	 */
	public boolean allowedMarketplaces( int ebaySiteId){
		boolean allowedType=false;
		allowedType= validationsDAO.validateRetailPrice(ebaySiteId);
		return allowedType;
	}
	
	/**
	 * This method is used to check the RetailPrice is greater than price 
	 * @param price
	 * @param retailPrice
	 * @return
	 */
	public boolean checkRetailPrice(double price,double retailPrice){
		boolean validRetailPrice=false;
		
		if(retailPrice>price){
			validRetailPrice=true;
		}
		
		return validRetailPrice;
	}
	
	
	public int checkPriceExists( int templateId){
		int price=0;
		
		price= validationsDAO.getPrice(templateId);
		return price;
	}
	
}
