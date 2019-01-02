package com.cultuzz.channel.helper.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cultuzz.channel.DAO.ObjectConfigurationDAO;
import com.cultuzz.channel.DAO.ObjectMetaDataDAO;
import com.cultuzz.channel.DAO.OfferCreationDAO;
import com.cultuzz.channel.DAO.TemplateModuleDAO;
import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.XMLpojo.AdditionalType;
import com.cultuzz.channel.DAO.impl.ObjectMetaDataDAOImpl;
import com.cultuzz.channel.DAO.impl.TemplateModuleDAOImpl;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.XMLpojo.FAQType;
import com.cultuzz.channel.XMLpojo.InformationsTypes;
import com.cultuzz.channel.XMLpojo.ObjectConfigurationRQ;
import com.cultuzz.channel.XMLpojo.ObjectConfigurationRS;
import com.cultuzz.channel.XMLpojo.PaymentOptionType;
import com.cultuzz.channel.XMLpojo.PaymentOptionsType;
import com.cultuzz.channel.XMLpojo.SettingType;
import com.cultuzz.channel.XMLpojo.TermsAndConditionsType;
import com.cultuzz.channel.XMLpojo.ValueType;
import com.cultuzz.channel.helper.ObjectConfigurationHelper;
import com.cultuzz.channel.util.CommonValidations;
import com.cultuzz.channel.util.DateUtil;

@Repository
public class ObjectConfigurationHelperImpl implements ObjectConfigurationHelper{

	
	@Autowired
	TemplateModuleDAO templateModuleDAO;
	
	@Autowired
	CommonValidations commonValidations;
	
	@Autowired
	GetErrorMessagesDAOImpl getErrormessages;
	
	@Autowired
	OfferCreationDAO offerCreationDAO;
	
	@Autowired
	ObjectConfigurationDAO objectConfigurationDAO;
	
	@Autowired
	TemplateModuleDAOImpl templateModuleDAOImpl;
	
	@Autowired
	ObjectMetaDataDAO objectMetaDataDAOImpl;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ObjectConfigurationHelperImpl.class);
	
	public ObjectConfigurationRS validateObjectConfigurationRQ(ObjectConfigurationRQ objectConfigurationRQ) {
		// TODO Auto-generated method stub
		ObjectConfigurationRS objectConfigurationRS=new ObjectConfigurationRS();
		
		objectConfigurationRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		LOGGER.debug("inside validateTamplateCreationRQ method");
		
		int objectid=0;
		
		ErrorsType error=new ErrorsType();
		
		boolean objectIdFlag=false;
		
		List<ErrorType> errorsType=error.getError();
		int langid=0;
		
		
		if(objectConfigurationRQ.isSetAuthenticationCode()){
			
		boolean authCodeStatus=	commonValidations.checkAuthCode(objectConfigurationRQ.getAuthenticationCode());
			if(!authCodeStatus){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1100);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,2));
				errorsType.add(errorType);
				objectConfigurationRS.setErrors(error);
				objectConfigurationRS.setAck("Failure");
				return objectConfigurationRS;
			}
				
			
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1100);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,2));
			errorsType.add(errorType);
			objectConfigurationRS.setErrors(error);
			objectConfigurationRS.setAck("Failure");
			return objectConfigurationRS;
			
		}
		
		if(objectConfigurationRQ.isSetErrorLang()){
			
			langid=commonValidations.checkErrorLangCode(objectConfigurationRQ.getErrorLang());
			
			if(langid>0){
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1106);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,2));
				errorsType.add(errorType);
				objectConfigurationRS.setErrors(error);
				objectConfigurationRS.setAck("Failure");
				return objectConfigurationRS;
			}
			
		}else{				
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1106);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,2));
			errorsType.add(errorType);
			objectConfigurationRS.setErrors(error);
			objectConfigurationRS.setAck("Failure");
			return objectConfigurationRS;
		}
		
		
		
		if(objectConfigurationRQ.isSetTimeStamp() && !objectConfigurationRQ.getTimeStamp().trim().isEmpty()){
			
		boolean timestampStatus = commonValidations.checkTimeStamp(objectConfigurationRQ.getTimeStamp());
		if(!timestampStatus){	
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1104);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1104,langid));
			errorsType.add(errorType);
			objectConfigurationRS.setErrors(error);
			objectConfigurationRS.setAck("Failure");
			return objectConfigurationRS;
		}
		
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1105);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1105,langid));
			errorsType.add(errorType);
			objectConfigurationRS.setErrors(error);
			objectConfigurationRS.setAck("Failure");
			return objectConfigurationRS;
		}
		
		if(objectConfigurationRQ.isSetSourceId() ){
			if(!commonValidations.checkSourceId(objectConfigurationRQ.getSourceId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1101);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langid));
				errorsType.add(errorType);
				objectConfigurationRS.setErrors(error);
				objectConfigurationRS.setAck("Failure");
				return objectConfigurationRS;
			}
		
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1101);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langid));
			errorsType.add(errorType);
			objectConfigurationRS.setErrors(error);
			objectConfigurationRS.setAck("Failure");
			return objectConfigurationRS;
			
		}
		
		if(objectConfigurationRQ.isSetChannelId()){
		
			if(!commonValidations.checkChannelId(objectConfigurationRQ.getChannelId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1102);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langid));
				errorsType.add(errorType);
				objectConfigurationRS.setErrors(error);
				objectConfigurationRS.setAck("Failure");
				return objectConfigurationRS;
			}
			
		}else{
			
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1102);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langid));
			errorsType.add(errorType);
			objectConfigurationRS.setErrors(error);
			objectConfigurationRS.setAck("Failure");
			return objectConfigurationRS;
			
		}
		
		if(objectConfigurationRQ.isSetObjectId()){
			
			LOGGER.debug("Checking objectid");
			if(!commonValidations.checkObjectId(objectConfigurationRQ.getObjectId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1103);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
				errorsType.add(errorType);
				objectConfigurationRS.setErrors(error);
				objectConfigurationRS.setAck("Failure");
				return objectConfigurationRS;
			}else{
				objectIdFlag=true;
				LOGGER.debug("object id flag{}",objectIdFlag);
				objectid=objectConfigurationRQ.getObjectId();
				
				boolean ebaydatenStatus=objectConfigurationDAO.checkEbayDaten(objectid);
				if(!ebaydatenStatus){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1107);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1107,langid));
					errorsType.add(errorType);
					objectConfigurationRS.setErrors(error);
					objectConfigurationRS.setAck("Failure");
					return objectConfigurationRS;
				}
			}
			
		}else{
			
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1103);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
			errorsType.add(errorType);
			objectConfigurationRS.setErrors(error);
			objectConfigurationRS.setAck("Failure");
			return objectConfigurationRS;
			
		}
		
		if(objectConfigurationRQ.isSetVoucher()){
			
			if(objectConfigurationRQ.getVoucher().isSetVoucherPdfTemplateId()){
				
				if(objectConfigurationRQ.getVoucher().getVoucherPdfTemplateId()>0){
					List<Integer> templateidsList=new ArrayList<Integer>();
				List<Map<String,Object>> templateids=objectConfigurationDAO.getListOfVoucherTemplateids();
				if(templateids!=null){
				for(Map<String,Object> rows:templateids){
					try{
					templateidsList.add(Integer.parseInt(rows.get("id").toString()));
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
				if(!templateidsList.contains(objectConfigurationRQ.getVoucher().getVoucherPdfTemplateId())){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1108);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1108,langid));
					errorsType.add(errorType);
				}
				
				}
				
				}else{
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1108);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1108,langid));
					errorsType.add(errorType);
				}
				
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1108);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1108,langid));
				errorsType.add(errorType);
			}
			
			
			if(!objectConfigurationRQ.getVoucher().isSetVoucherLogoPictureURL() || objectConfigurationRQ.getVoucher().getVoucherLogoPictureURL().isEmpty()){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2187);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2187,langid));
				errorsType.add(errorType);
			}
			
			if(!objectConfigurationRQ.getVoucher().isSetVoucherPictureURL() || objectConfigurationRQ.getVoucher().getVoucherPictureURL().isEmpty()){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2186);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2186,langid));
					errorsType.add(errorType);
			}
			
			if(!objectConfigurationRQ.getVoucher().isSetVoucherLogoPictureURL() && !objectConfigurationRQ.getVoucher().isSetVoucherPdfTemplateId() && !objectConfigurationRQ.getVoucher().isSetVoucherPictureURL()){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1110);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1110,langid));
				errorsType.add(errorType);
			}
			
		}else{
			/*ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1110);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1110,langid));
			errorsType.add(errorType);*/
		}
		
		if(objectConfigurationRQ.isSetSettings()){
			List<SettingType> settingslist=objectConfigurationRQ.getSettings().getSetting();
			
			if(objectConfigurationRQ.getSettings().isSetSetting() && settingslist.size()>0){
				
				if(settingslist.size()==1){
				List<String> settingnames=new ArrayList<String>();
				
				for(SettingType setting:settingslist){
					if(!settingnames.contains(setting.getName())){
						settingnames.add(setting.getName());
						
						//if more settings are add then change code here
						if(setting.getName()!=null && setting.getName().equals("Reputize")){
							String token=setting.getKey();
						
							if(token!=null){
							if(!token.equals("")){
							
							if(token.length()<=32){
								LOGGER.debug("inside  reputize token length checked{}",token);
								
							if(Pattern.matches("\\w+",token)){
								LOGGER.debug("inside invalid  reputize token first condition");
								if(!token.contains("_")){
									LOGGER.debug("inside invalid  reputize token second condition");
									/*if(objectConfigurationDAO.checkToken(token, objectid)){
										//This token already exist
										ErrorType errorType=new ErrorType();
										errorType.setErrorCode(12007);
										errorType.setErrorMessage(getErrormessages.getErrorMessage(12007,langid));
										errorsType.add(errorType);
									}*/
									
								}else{
									//invalid characters
									ErrorType errorType=new ErrorType();
									errorType.setErrorCode(12006);
									errorType.setErrorMessage(getErrormessages.getErrorMessage(12006,langid));
									errorsType.add(errorType);
								}
								
							}else{
								//invalid characters
								ErrorType errorType=new ErrorType();
								errorType.setErrorCode(12006);
								errorType.setErrorMessage(getErrormessages.getErrorMessage(12006,langid));
								errorsType.add(errorType);
								
							}
							}else{
								//invalid length
								ErrorType errorType=new ErrorType();
								errorType.setErrorCode(12005);
								errorType.setErrorMessage(getErrormessages.getErrorMessage(12005,langid));
								errorsType.add(errorType);
							}
							}
							}else{
								ErrorType errorType=new ErrorType();
								errorType.setErrorCode(12004);
								errorType.setErrorMessage(getErrormessages.getErrorMessage(12004,langid));
								errorsType.add(errorType);
							}
							
							
							
						}else{
							//setting name is invalid
							ErrorType errorType=new ErrorType();
							errorType.setErrorCode(12003);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(12003,langid));
							errorsType.add(errorType);
							
						}
						
						
					}else{
						//stting name repeated
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(12008);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(12008,langid));
						errorsType.add(errorType);
					}
				}
				
				}else{
					
					//invalid settings
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(12002);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(12002,langid));
					errorsType.add(errorType);
					
				}
				
				
			}else{
				//need to set at least one setting
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(12002);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(12002,langid));
				errorsType.add(errorType);
			}
			
		}else{
			
		}
		
		if(objectConfigurationRQ.isSetLogoPictureURL() && !objectConfigurationRQ.getLogoPictureURL().equals("")){
			if(!this.checkHttps(objectConfigurationRQ.getLogoPictureURL())){
				//Logo url path should be https
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(12009);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(12009,langid));
				errorsType.add(errorType);
				
			}else if(objectConfigurationRQ.getLogoPictureURL().length()>100){
				//Logo picture url length should less than or equal to 100
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(12010);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(12010,langid));
				errorsType.add(errorType);
			}
				
		}else{
			//Logo picture url is mandatory
			/*ErrorType errorType=new ErrorType();
			errorType.setErrorCode(12011);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(12011,langid));
			errorsType.add(errorType);*/
		}
		
		
		if(objectConfigurationRQ.isSetObjectSlider()){
			
			List<String>  sliderurlList=objectConfigurationRQ.getObjectSlider().getObjectSliderPictureURL();
			
			
			
			if(sliderurlList.size()>0){
			for(String url:sliderurlList){
				//url ristrinctions
				if(!this.checkHttps(url)){
					//object slider pic should contain https
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(12012);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(12012,langid));
					errorsType.add(errorType);
					break;
				}else if(url.length()>100){
					//object slider picture url length should less than or equal to 100
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(12013);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(12013,langid));
					errorsType.add(errorType);
					break;
				}
			}
			
			}else{
				//object slider pics atleast 2
				/*ErrorType errorType=new ErrorType();
				errorType.setErrorCode(12014);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(12014,langid));
				errorsType.add(errorType);*/
			}
				
			
		}else{
			//here error message for need to set object slider pics
			/*ErrorType errorType=new ErrorType();
			errorType.setErrorCode(12015);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(12015,langid));
			errorsType.add(errorType);*/
		}
		
		if(objectConfigurationRQ.isSetPaymentOptions()){
			
			PaymentOptionsType po=objectConfigurationRQ.getPaymentOptions();
			List<PaymentOptionType> lop=po.getPaymentOption();
			boolean marktPaymenterror=false;
			boolean paymentTypeError=false;
			boolean paymentIdEmptyMsg=false;
			boolean paymentIdLegth=false;
			boolean hotelierToPayment=true;
			String paymentToHotelier1=po.getPaymentToHotelier();
			
			if(paymentToHotelier1.equals("true") || paymentToHotelier1.equals("false")){
				
				List<Map<String,Object>> paymentData1=objectConfigurationDAO.getPaymentData(objectid);
				Iterator<Map<String, Object>> itr=null;
				if(paymentData1!=null){
				 itr=paymentData1.iterator();
				if(itr.hasNext()){
					Map<String,Object> mypaymentDetails=(Map<String, Object>) itr.next();
					try{
					int statusval=Integer.parseInt(mypaymentDetails.get("status").toString());
					String oldStatus="false";
					if(statusval==1){
						oldStatus="true";
					}else
						oldStatus="false";
					if(paymentToHotelier1.equals(oldStatus)){
						
					}else{
						if(objectConfigurationDAO.checkFutureOffersLiveOffersObject(objectid)){
							hotelierToPayment=false;
							ErrorType errorType=new ErrorType();
							errorType.setErrorCode(12068);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(12068,langid));
							errorsType.add(errorType);
						}
					}
						
					
					}catch(Exception e){
						
					}
				}
			
				}
				
			}else{
				//Invalid paymentToHotelier
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(12067);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(12067,langid));
				errorsType.add(errorType);
			}
			
			if(lop!=null && lop.size()>0){
				String paymentToHotelier=po.getPaymentToHotelier();
				
				
				Map<Integer,String> siteidhashmap=new HashMap<Integer,String>();
				for(PaymentOptionType popt:lop){
					if(popt!=null){
					//System.out.println("payment to hotelier iss"+paymentToHotelier);
					if(paymentToHotelier.equals("true")){
						if(popt.isSetPaymentId()){
							String paymentId=popt.getPaymentId();
							if(!paymentIdLegth){
							if(paymentId.length()>128){
								//paymentid length should be lessthan 128 characters
								ErrorType errorType=new ErrorType();
								errorType.setErrorCode(12046);
								errorType.setErrorMessage(getErrormessages.getErrorMessage(12046,langid));
								errorsType.add(errorType);
							}
							}
							//check special characters
							/*if(!paymentIdEmptyMsg){
							if(popt.getPaymentId().equals("")){
								paymentIdEmptyMsg=true;
								ErrorType errorType=new ErrorType();
								errorType.setErrorCode(12060);
								errorType.setErrorMessage(getErrormessages.getErrorMessage(12060,langid));
								errorsType.add(errorType);
							}
							}*/
						}else{
							paymentIdEmptyMsg=true;
							ErrorType errorType=new ErrorType();
							errorType.setErrorCode(12060);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(12060,langid));
							errorsType.add(errorType);
						}
					}else if(popt.isSetPaymentId() && !popt.getPaymentId().equals("") && hotelierToPayment){
						//payment to hotelier is false so please set marketplace payment ids as empty.
						//System.out.println("Inside error message");
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(12045);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(12045,langid));
						errorsType.add(errorType);
						break;
					}
					
					
					if(!paymentTypeError){
						
						if(popt.getPaymentType()==null || (popt.isSetPaymentType() && !popt.getPaymentType().equalsIgnoreCase("Paypal") )){
							paymentTypeError=true;
							//invalid payment type
							ErrorType errorType=new ErrorType();
							errorType.setErrorCode(12016);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(12016,langid));
							errorsType.add(errorType);
						}
						
						}
					
					
					Integer marketplace=null;
					//check marktplace is valid or not
					if(!marktPaymenterror){
					if(popt.isSetMarketPlace()){
						try{
					marketplace=Integer.parseInt(popt.getMarketPlace());
					
					if(!objectConfigurationDAO.checkSiteId(marketplace)){
						marktPaymenterror=true;
						//Invalid payment marketplace
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(12044);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(12044,langid));
						errorsType.add(errorType);
					}else{
						int oldsize=siteidhashmap.size();
						siteidhashmap.put(marketplace,popt.getPaymentId() );
						
						if(oldsize==siteidhashmap.size()){
							ErrorType errorType=new ErrorType();
							errorType.setErrorCode(12061);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(12061,langid));
							errorsType.add(errorType);
						}
					}
					
						}catch(Exception e){
							marktPaymenterror=true;
							//Invalid payment marketplace
							ErrorType errorType=new ErrorType();
							errorType.setErrorCode(12044);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(12044,langid));
							errorsType.add(errorType);
						}
					}else{
						marktPaymenterror=true;
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(12063);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(12063,langid));
						errorsType.add(errorType);
					}
				}
				}else{
					//Invalid PaymentOption tag.
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(12062);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(12062,langid));
					errorsType.add(errorType);
				}
				}
			}else{
				//Payment option is requiered
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(12018);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(12018,langid));
				errorsType.add(errorType);
			}
				
		}else{
			//payment option is need to set for every object
		}
		
	
		
		if(objectConfigurationRQ.isSetMap()){
			if(objectConfigurationRQ.getMap().isSetLatitude()){
				final String regExp = "([+-])?[0-9]{1,2}+([.][0-9]{0,8})?";
		        final Pattern pattern = Pattern.compile(regExp);
		        Matcher matcher = pattern.matcher(objectConfigurationRQ.getMap().getLatitude()); 
		       // System.out.println(matcher.matches());
				if(objectConfigurationRQ.getMap().getLatitude().equals("") || !matcher.matches()){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(12059);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(12059,langid));
					errorsType.add(errorType);
				}
			}else{
				//Latitude is requiered
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(12049);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(12049,langid));
				errorsType.add(errorType);
			}
			
			if(objectConfigurationRQ.getMap().isSetLongitude()){
				final String regExp = "([+-])?[0-9]{1,3}+([.][0-9]{0,8})?";
		        final Pattern pattern = Pattern.compile(regExp);
		        Matcher matcher = pattern.matcher(objectConfigurationRQ.getMap().getLatitude()); 
				if(objectConfigurationRQ.getMap().getLongitude().equals("") || !matcher.matches()){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(12058);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(12058,langid));
					errorsType.add(errorType);
				}
			}else{
				//Longitude is requiered
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(12048);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(12048,langid));
				errorsType.add(errorType);
			}
			
			if(objectConfigurationRQ.getMap().isSetZoomLevel()){
			int zoomLevelVal=objectConfigurationRQ.getMap().getZoomLevel();
			
			if(zoomLevelVal<3 || zoomLevelVal>18){
				//zoomLevel should be greater than 2 or less than 19 
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(12051);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(12051,langid));
				errorsType.add(errorType);
			}
				
			}else{
				//Zoomlevel is requiered
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(12050);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(12050,langid));
				errorsType.add(errorType);
			}
		}
		
		
		if(objectConfigurationRQ.isSetInformationTypes() ){
					
					if(objectConfigurationRQ.getInformationTypes().isSetTermsAndConditions()){
						InformationsTypes its=objectConfigurationRQ.getInformationTypes();
						TermsAndConditionsType tac=its.getTermsAndConditions();
						if(tac.isSetStandardBusinessTerms()){
							
							if(tac.getStandardBusinessTerms().size()<=2){
								List<com.cultuzz.channel.XMLpojo.TermsAndConditionsType.StandardBusinessTerms> ssbt=tac.getStandardBusinessTerms();
								boolean errorMessageSet=false;
								for(com.cultuzz.channel.XMLpojo.TermsAndConditionsType.StandardBusinessTerms sbt:ssbt){
									if(!errorMessageSet){
							if(sbt.isSetLanguage()){
								if(!this.checkLanguage(sbt.getLanguage())){
									//invalid language
									errorMessageSet=true;
									ErrorType errorType=new ErrorType();
									errorType.setErrorCode(12033);
									errorType.setErrorMessage(getErrormessages.getErrorMessage(12033,langid));
									errorsType.add(errorType);
								}
							}else{
								//standardbusinessterms language is mandatory 
								errorMessageSet=true;
								ErrorType errorType=new ErrorType();
								errorType.setErrorCode(12020);
								errorType.setErrorMessage(getErrormessages.getErrorMessage(12020,langid));
								errorsType.add(errorType);
							}}
								}
								//System.out.println("size of the standard business tags"+tac.getStandardBusinessTerms().size());
								if(tac.getStandardBusinessTerms().size()==2){
								if(tac.getStandardBusinessTerms().get(0).isSetLanguage() && tac.getStandardBusinessTerms().get(1).isSetLanguage() && tac.getStandardBusinessTerms().get(0).getLanguage().equalsIgnoreCase(tac.getStandardBusinessTerms().get(1).getLanguage())){
									//same tags are repeated
									ErrorType errorType=new ErrorType();
									errorType.setErrorCode(12034);
									errorType.setErrorMessage(getErrormessages.getErrorMessage(12034,langid));
									errorsType.add(errorType);
								}
								}
								
							}else{
								//invalid standard business terms
								ErrorType errorType=new ErrorType();
								errorType.setErrorCode(12032);
								errorType.setErrorMessage(getErrormessages.getErrorMessage(12032,langid));
								errorsType.add(errorType);
							}
							
						}else{
							//standardbusinessterms is mandatory
							/*ErrorType errorType=new ErrorType();
							errorType.setErrorCode(12021);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(12021,langid));
							errorsType.add(errorType);*/
						}
						
						if(tac.isSetPaymentService()){
							if(tac.getPaymentService().size()<=2){
								boolean paymentService=false;
								for(com.cultuzz.channel.XMLpojo.TermsAndConditionsType.PaymentService  ps:tac.getPaymentService()){
									if(!paymentService){
							if(ps.isSetLanguage()){
								if(!this.checkLanguage(ps.getLanguage())){
									//error
									paymentService=true;
									ErrorType errorType=new ErrorType();
									errorType.setErrorCode(12039);
									errorType.setErrorMessage(getErrormessages.getErrorMessage(12039,langid));
									errorsType.add(errorType);
								}
							}else{
								//payment service tag language is requiered
								paymentService=true;
								ErrorType errorType=new ErrorType();
								errorType.setErrorCode(12022);
								errorType.setErrorMessage(getErrormessages.getErrorMessage(12022,langid));
								errorsType.add(errorType);
							}}
								}
								if(tac.getPaymentService().size()==2){
									
								if(tac.getPaymentService().get(0).isSetLanguage() && tac.getPaymentService().get(1).isSetLanguage() && tac.getPaymentService().get(0).getLanguage().equalsIgnoreCase(tac.getPaymentService().get(1).getLanguage())){
									//same tags are repeated
									ErrorType errorType=new ErrorType();
									errorType.setErrorCode(12040);
									errorType.setErrorMessage(getErrormessages.getErrorMessage(12040,langid));
									errorsType.add(errorType);
								}
								}
								
							}else{
								//
								ErrorType errorType=new ErrorType();
								errorType.setErrorCode(12038);
								errorType.setErrorMessage(getErrormessages.getErrorMessage(12038,langid));
								errorsType.add(errorType);
							}
							
						}else{
							//payment service is mandatory
							/*ErrorType errorType=new ErrorType();
							errorType.setErrorCode(12023);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(12023,langid));
							errorsType.add(errorType);*/
						}
						
						if(tac.isSetCheckoutService()){
							
							if(tac.getCheckoutService().size()<=2){
								boolean errorMesgSet=false;
							for(com.cultuzz.channel.XMLpojo.TermsAndConditionsType.CheckoutService cs:tac.getCheckoutService()){
								if(!errorMesgSet){
							if(cs.isSetLanguage()){
								if(!this.checkLanguage(cs.getLanguage())){
									errorMesgSet=true;
									ErrorType errorType=new ErrorType();
									errorType.setErrorCode(12036);
									errorType.setErrorMessage(getErrormessages.getErrorMessage(12036,langid));
									errorsType.add(errorType);
								}
							}else{
								//checkoutservice language is mandatory
								errorMesgSet=true;
								ErrorType errorType=new ErrorType();
								errorType.setErrorCode(12024);
								errorType.setErrorMessage(getErrormessages.getErrorMessage(12024,langid));
								errorsType.add(errorType);
							}
								}
							}
							if(tac.getCheckoutService().size()==2){
							if(tac.getCheckoutService().get(0).isSetLanguage() && tac.getCheckoutService().get(1).isSetLanguage() && tac.getCheckoutService().get(0).getLanguage().equalsIgnoreCase(tac.getCheckoutService().get(1).getLanguage())){
								//same tags are repeated
								ErrorType errorType=new ErrorType();
								errorType.setErrorCode(12037);
								errorType.setErrorMessage(getErrormessages.getErrorMessage(12037,langid));
								errorsType.add(errorType);
							}
							}
							}else{
								ErrorType errorType=new ErrorType();
								errorType.setErrorCode(12035);
								errorType.setErrorMessage(getErrormessages.getErrorMessage(12035,langid));
								errorsType.add(errorType);
							}
							
						}else{
							//checkoutservice is mandatory
							/*ErrorType errorType=new ErrorType();
							errorType.setErrorCode(12025);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(12025,langid));
							errorsType.add(errorType);*/
						}
						
						if(tac.isSetVoucherService()){
							if(tac.getVoucherService().size()<=2){
								boolean langErrorSetted=false;
								//boolean emptyMessageSetted=false;
							for(com.cultuzz.channel.XMLpojo.TermsAndConditionsType.VoucherService vs:tac.getVoucherService()){
								if(!langErrorSetted){
							if(vs.isSetLanguage()){
								if(!this.checkLanguage(vs.getLanguage())){
									langErrorSetted=true;
									ErrorType errorType=new ErrorType();
									errorType.setErrorCode(12042);
									errorType.setErrorMessage(getErrormessages.getErrorMessage(12042,langid));
									errorsType.add(errorType);
								}
							}else{
								//voucher service language tag is requiered
								langErrorSetted=true;
								ErrorType errorType=new ErrorType();
								errorType.setErrorCode(12026);
								errorType.setErrorMessage(getErrormessages.getErrorMessage(12026,langid));
								errorsType.add(errorType);
							}}
							
								/*if(!emptyMessageSetted){
									if(vs.getValue().equals("")){
										emptyMessageSetted=true;
										//Voucher service tag is empty
										ErrorType errorType=new ErrorType();
										errorType.setErrorCode(12056);
										errorType.setErrorMessage(getErrormessages.getErrorMessage(12056,langid));
										errorsType.add(errorType);
									}
								}*/
								
							}
							if(tac.getVoucherService().size()==2){
							if(tac.getVoucherService().get(0).isSetLanguage() && tac.getVoucherService().get(1).isSetLanguage() && tac.getVoucherService().get(0).getLanguage().equalsIgnoreCase(tac.getVoucherService().get(1).getLanguage())){
								//same tags are repeated
								ErrorType errorType=new ErrorType();
								errorType.setErrorCode(12043);
								errorType.setErrorMessage(getErrormessages.getErrorMessage(12043,langid));
								errorsType.add(errorType);
							}
							}
							
							
							}else{
								ErrorType errorType=new ErrorType();
								errorType.setErrorCode(12041);
								errorType.setErrorMessage(getErrormessages.getErrorMessage(12041,langid));
								errorsType.add(errorType);
							}
							
						}else{
							//voucher service is requiered
							/*ErrorType errorType=new ErrorType();
							errorType.setErrorCode(12027);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(12027,langid));
							errorsType.add(errorType);*/
						}
						
						
						if(tac.isSetAdditionalValues()){
							
							AdditionalType additionalValues=tac.getAdditionalValues();
							if(additionalValues.isSetValue()){
							
							List<ValueType> addvList=additionalValues.getValue();
							if(addvList.size()>0){
							
							boolean errormsgaddlang=false;
							for(ValueType val:addvList){
								if(!val.isSetName()){
									//name is mandatory
									ErrorType errorType=new ErrorType();
									errorType.setErrorCode(12028);
									errorType.setErrorMessage(getErrormessages.getErrorMessage(12028,langid));
									errorsType.add(errorType);
								}
								if(!errormsgaddlang){
								if(val.isSetLanguage()){
									if(val.getLanguage().equalsIgnoreCase("en") || val.getLanguage().equalsIgnoreCase("de")){
										
									}else{
										errormsgaddlang=true;
										//invalid language
										ErrorType errorType=new ErrorType();
										errorType.setErrorCode(12053);
										errorType.setErrorMessage(getErrormessages.getErrorMessage(12053,langid));
										errorsType.add(errorType);
									}
									
								}else{
									errormsgaddlang=true;
									//language is mandatory
									ErrorType errorType=new ErrorType();
									errorType.setErrorCode(12030);
									errorType.setErrorMessage(getErrormessages.getErrorMessage(12030,langid));
									errorsType.add(errorType);
								}
								}
							}
							}else{
								ErrorType errorType=new ErrorType();
								errorType.setErrorCode(12066);
								errorType.setErrorMessage(getErrormessages.getErrorMessage(12066,langid));
								errorsType.add(errorType);
							}
						}else{
							ErrorType errorType=new ErrorType();
							errorType.setErrorCode(12066);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(12066,langid));
							errorsType.add(errorType);
						}
						}
						
						if(objectConfigurationRQ.getInformationTypes().getTermsAndConditions().isSetAdditionalValues() || objectConfigurationRQ.getInformationTypes().getTermsAndConditions().isSetCheckoutService() || objectConfigurationRQ.getInformationTypes().getTermsAndConditions().isSetPaymentService() || objectConfigurationRQ.getInformationTypes().getTermsAndConditions().isSetStandardBusinessTerms() || objectConfigurationRQ.getInformationTypes().getTermsAndConditions().isSetVoucherService()){
							
						}else{
							//Invalid TAC
							ErrorType errorType=new ErrorType();
							errorType.setErrorCode(12065);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(12065,langid));
							errorsType.add(errorType);
						}
						
					}
					
					if(objectConfigurationRQ.getInformationTypes().isSetFAQ()){
						FAQType faq=objectConfigurationRQ.getInformationTypes().getFAQ();
						if(faq.isSetValue()){
							List<ValueType> faqValues=faq.getValue();
							if(faqValues.size()>0){
							
							boolean errorMsgLang=false;
							for(ValueType faqvals:faqValues){
								if(!faqvals.isSetName()){
									//name is mandatory
									ErrorType errorType=new ErrorType();
									errorType.setErrorCode(12028);
									errorType.setErrorMessage(getErrormessages.getErrorMessage(12028,langid));
									errorsType.add(errorType);
								}
								
								if(!errorMsgLang){
								if(faqvals.isSetLanguage()){
									if(faqvals.getLanguage().equalsIgnoreCase("en") || faqvals.getLanguage().equalsIgnoreCase("de")){
										
									}else{
										errorMsgLang=true;
										//invalid language
										ErrorType errorType=new ErrorType();
										errorType.setErrorCode(12052);
										errorType.setErrorMessage(getErrormessages.getErrorMessage(12052,langid));
										errorsType.add(errorType);
									}
									
								}else{
									errorMsgLang=true;
									//language is mandatory
									ErrorType errorType=new ErrorType();
									errorType.setErrorCode(12030);
									errorType.setErrorMessage(getErrormessages.getErrorMessage(12030,langid));
									errorsType.add(errorType);
								}
								}
							}
						}else{
							//Invalid FAQ
							ErrorType errorType=new ErrorType();
							errorType.setErrorCode(12064);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(12064,langid));
							errorsType.add(errorType);
						}
						}else{
							//Invalid FAQ
							ErrorType errorType=new ErrorType();
							errorType.setErrorCode(12064);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(12064,langid));
							errorsType.add(errorType);
						}
						
					}
					
					if(objectConfigurationRQ.getInformationTypes().isSetFAQ() || objectConfigurationRQ.getInformationTypes().isSetTermsAndConditions()){
						
					}else{
						//invalid information types tag
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(12047);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(12047,langid));
						errorsType.add(errorType);
					}
						
					
				
			
			
		}else{
			//information types are mandatory
			/*ErrorType errorType=new ErrorType();
			errorType.setErrorCode(12031);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(12031,langid));
			errorsType.add(errorType);*/
		}
		
		
		
		
		if(objectConfigurationRQ.isSetVoucher() || objectConfigurationRQ.isSetSettings() || objectConfigurationRQ.isSetInformationTypes() || objectConfigurationRQ.isSetLogoPictureURL() || objectConfigurationRQ.isSetObjectSlider() || objectConfigurationRQ.isSetMap() || objectConfigurationRQ.isSetPaymentOptions() ){
			
		}else{
			//please set atleast one 
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(12001);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(12001,langid));
			errorsType.add(errorType);
		}
		
		if(errorsType.size()>0){
			objectConfigurationRS.setAck("Failure");
			objectConfigurationRS.setErrors(error);
		}else{
			objectConfigurationRS.setAck("Success");
		}
		
		return objectConfigurationRS;
	}

	public ObjectConfigurationRS processObjectConfigurationRQ(ObjectConfigurationRQ objectConfigurationRQ) {
		
		
		ErrorsType error=new ErrorsType();
		List<ErrorType> errorsType=error.getError();
		
		ObjectConfigurationRS objectConfigurationRS=new ObjectConfigurationRS();
		objectConfigurationRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		LOGGER.debug("inside validateTamplateCreationRQ method");
	
		//voucher configuration
		
		int objectId=objectConfigurationRQ.getObjectId();
		if(objectConfigurationRQ.isSetVoucher()){
		int pdfTemplateId=objectConfigurationDAO.checkGutscheinXObjekt(objectId);
		
		Map<String,Object> voucherConfData=objectConfigurationDAO.getVoucherConfData(objectId);
		// if this variable modeInsertOrUpdate is false then insert otherwise update.
		
		LOGGER.debug("pdf Template id{}",pdfTemplateId);
		
		if(pdfTemplateId==0){
			//savedata
			boolean saveStatusofGXO=objectConfigurationDAO.saveGutscheinXObjekt(objectConfigurationRQ);
			LOGGER.debug("save gxo status{}",saveStatusofGXO);
		}else if(objectConfigurationRQ.getVoucher().isSetVoucherPdfTemplateId() && objectConfigurationRQ.getVoucher().getVoucherPdfTemplateId()>0 && objectConfigurationRQ.getVoucher().getVoucherPdfTemplateId()!=pdfTemplateId){
			//update data
			boolean updategxo=objectConfigurationDAO.updateGutscheinXObjekt(objectConfigurationRQ.getVoucher().getVoucherPdfTemplateId(),objectConfigurationRQ.getObjectId() );
			LOGGER.debug("update gxo status{}",updategxo);
		}
		
		if(voucherConfData==null){
			boolean savevcstatus=objectConfigurationDAO.saveVoucherConfiguration(objectConfigurationRQ);
			LOGGER.debug("save vc status{}",savevcstatus);
		}else if(voucherConfData!=null && voucherConfData.get("imagepath") !=null && voucherConfData.get("logopath")!=null && (!voucherConfData.get("logopath").toString().equals(objectConfigurationRQ.getVoucher().getVoucherLogoPictureURL()) || !voucherConfData.get("imagepath").toString().equals(objectConfigurationRQ.getVoucher().getVoucherPictureURL()) )){
			boolean updatevcStatus=objectConfigurationDAO.updateVoucherConfiguration(objectConfigurationRQ);
			LOGGER.debug("update vc status{}",updatevcStatus);
		}
		}
		
		if(objectConfigurationRQ.isSetSettings()){
			
			List<SettingType> settingslist=objectConfigurationRQ.getSettings().getSetting();
			
			if(settingslist.size()>0){
				
				for(SettingType setting:settingslist){
						String settingname=setting.getName();
						String settingkey=setting.getKey();
						if(settingname.equals("Reputize")){
							this.processReputizeSetting(objectConfigurationRQ,settingkey, objectConfigurationRS,errorsType);
						}
				}
				
			}
			
		}
		
		if(objectConfigurationRQ.isSetLogoPictureURL()){
			
			String logopath =objectConfigurationDAO.getOLogoImagePath(objectId);
			if(logopath!=null && objectConfigurationRQ.getLogoPictureURL()!=null && logopath.equals(objectConfigurationRQ.getLogoPictureURL())){
				//this logo path already exist
			}else if(logopath!=null){
				//update logo path
				objectConfigurationDAO.updateObjectLogoPic(objectId,objectConfigurationRQ.getLogoPictureURL());
			}else if(logopath==null && objectConfigurationRQ.getLogoPictureURL()!=null){
				//save logo path
				objectConfigurationDAO.saveObjectLogoPicture(objectId,objectConfigurationRQ.getLogoPictureURL());
			}
		}
		
		if(objectConfigurationRQ.isSetObjectSlider()){
			if(objectConfigurationRQ.getObjectSlider().isSetObjectSliderPictureURL()){
				//Remove old object slider pics
				if(objectConfigurationRQ.getObjectSlider().getObjectSliderPictureURL().size()>0)
					if(objectConfigurationDAO.deleteOldSliderPics(objectId)){
						List<String> objPicUrl=objectConfigurationRQ.getObjectSlider().getObjectSliderPictureURL();
						int counter=1;
						for(String objpicurl:objPicUrl){
						//save objectslider pictures
							if(!objpicurl.equalsIgnoreCase("Delete"))
								objectConfigurationDAO.saveObjectSliderPics(objectId,objpicurl,counter);
						counter++;
						}
					}else{
						//failed in object slider pics delete
					}
				
				
			}
		}
		
		if(objectConfigurationRQ.isSetMap()){
			
			if(objectConfigurationRQ.getMap().isSetLatitude() && objectConfigurationRQ.getMap().isSetLongitude()){
			//save or update for map data
			if(objectConfigurationDAO.getMapCoordinates(objectId)!=null)
				objectConfigurationDAO.updateMapCoordinates(objectId,objectConfigurationRQ.getMap().getLongitude(),objectConfigurationRQ.getMap().getLatitude());
			else
				objectConfigurationDAO.saveMapCoordinates(objectId,objectConfigurationRQ.getMap().getLongitude(),objectConfigurationRQ.getMap().getLatitude(),commonValidations.getCurrentTimeStamp());
			
			}
			if(objectConfigurationRQ.getMap().isSetZoomLevel())
				objectConfigurationDAO.updateMapZoomLevel(objectId,objectConfigurationRQ.getMap().getZoomLevel());
			
		}
		
		if(objectConfigurationRQ.isSetPaymentOptions()){
			
			if(objectConfigurationRQ.getPaymentOptions().isSetPaymentOption()){
				List<Map<String,Object>> paymentDataMap=objectConfigurationDAO.getPaymentData(objectId);
				if(paymentDataMap!=null){
					objectConfigurationDAO.updateTotalPaymentData(objectId);
				}
				if(objectConfigurationRQ.getPaymentOptions().getPaymentOption().size()>0){
					for(PaymentOptionType paymentOption:objectConfigurationRQ.getPaymentOptions().getPaymentOption()){
						
						if(paymentOption!=null){
						int marketp=Integer.parseInt(paymentOption.getMarketPlace().toString());
						String oldPaymentid=null;
						if(paymentDataMap!=null){
							
							oldPaymentid=this.checkPaymentExistOrNot(marketp,paymentDataMap);
						}
						//System.out.println("mypaymentiddd"+paymentOption.getPaymentId());
						if(oldPaymentid!=null){
							if(oldPaymentid.equals(paymentOption.getPaymentId())){
								//same data
							}
							
							objectConfigurationDAO.updatePaymentData(objectId, paymentOption.getPaymentId().trim(), marketp);
						}else{
							objectConfigurationDAO.savePaymentData(objectId,marketp,paymentOption.getPaymentId().trim(), 1, "");
						}
						}
					}
				}
				
			}
			//public boolean updatePaymentData(int objectid,String paymentid,int siteid)
			//public boolean savePaymentData(int objectid,int siteid,String paypalid,int status,String changer)
		}
		
		if(objectConfigurationRQ.isSetInformationTypes()){
			
			if(objectConfigurationRQ.getInformationTypes().isSetTermsAndConditions()){
				
				/* 
				 * 
				 * 
				 * 
				 * Standard business terms			Standard Geschäftsbedingungen
					Checkout Service			Angebotsabwicklung / Check-Out
					Payment Service				Zahlungsabwicklung
					Voucher/Shipment			Gutschein / Versand

				 */
				//terms and conditions save
				List<Map<String,Object>> germanTAC=null;
				List<Map<String,Object>> englishTAC=null;
				if(objectConfigurationRQ.getInformationTypes().getTermsAndConditions().isSetStandardBusinessTerms() || objectConfigurationRQ.getInformationTypes().getTermsAndConditions().isSetCheckoutService() || objectConfigurationRQ.getInformationTypes().getTermsAndConditions().isSetPaymentService() || objectConfigurationRQ.getInformationTypes().getTermsAndConditions().isSetVoucherService()){
					//for german based default TAC data
					germanTAC=objectConfigurationDAO.getDefaultTACData(objectId,1);
					englishTAC=objectConfigurationDAO.getDefaultTACData(objectId,2);
				}
				
				if(objectConfigurationRQ.getInformationTypes().getTermsAndConditions().isSetStandardBusinessTerms()){
					
				//position 1 Standard business terms
					List<com.cultuzz.channel.XMLpojo.TermsAndConditionsType.StandardBusinessTerms> stanardTAC=objectConfigurationRQ.getInformationTypes().getTermsAndConditions().getStandardBusinessTerms();
					for(com.cultuzz.channel.XMLpojo.TermsAndConditionsType.StandardBusinessTerms sbt:stanardTAC){
						if(sbt.getLanguage().equalsIgnoreCase("en")){
							if(this.checkTACExistorNot(3,englishTAC)>0){
								//update data
								int topicid=this.checkTACExistorNot(3,englishTAC);
								objectConfigurationDAO.updateTopicText(topicid,sbt.getValue());
							}else{
								//insert data
								Integer generatedTopicid=objectConfigurationDAO.saveTopic(3,2,"Standard business terms",objectId);
								objectConfigurationDAO.saveTopicStates(generatedTopicid,1,sbt.getValue());
							}
						}else if(sbt.getLanguage().equalsIgnoreCase("de")){
							if(this.checkTACExistorNot(3,germanTAC)>0){
								//update data
								int topicid=this.checkTACExistorNot(3,germanTAC);
								objectConfigurationDAO.updateTopicText(topicid,sbt.getValue());
							}else{
								//insert data
								Integer generatedTopicid=objectConfigurationDAO.saveTopic(3,1,"Standard Geschäftsbedingungen",objectId);
								objectConfigurationDAO.saveTopicStates(generatedTopicid,1,sbt.getValue());
							}
						}
					}
				}
				if(objectConfigurationRQ.getInformationTypes().getTermsAndConditions().isSetCheckoutService()){
				//position 2 Checkout Service terms
					List<com.cultuzz.channel.XMLpojo.TermsAndConditionsType.CheckoutService> cslist=objectConfigurationRQ.getInformationTypes().getTermsAndConditions().getCheckoutService();
					for(com.cultuzz.channel.XMLpojo.TermsAndConditionsType.CheckoutService cs:cslist){
						if(cs.getLanguage().equalsIgnoreCase("en")){
							if(this.checkTACExistorNot(4,englishTAC)>0){
								//update data
								int topicid=this.checkTACExistorNot(4,englishTAC);
								objectConfigurationDAO.updateTopicText(topicid,cs.getValue());
							}else{
								//insert data
								Integer generatedTopicid=objectConfigurationDAO.saveTopic(4,2,"Checkout Service",objectId);
								objectConfigurationDAO.saveTopicStates(generatedTopicid,1,cs.getValue());
							}
						}else if(cs.getLanguage().equalsIgnoreCase("de")){
							if(this.checkTACExistorNot(4,germanTAC)>0){
								//update data
								int topicid=this.checkTACExistorNot(4,germanTAC);
								objectConfigurationDAO.updateTopicText(topicid,cs.getValue());
							}else{
								//insert data
								Integer generatedTopicid=objectConfigurationDAO.saveTopic(4,1,"Angebotsabwicklung / Check-Out",objectId);
								objectConfigurationDAO.saveTopicStates(generatedTopicid,1,cs.getValue());
							}
						}
					}
					
				}
				if(objectConfigurationRQ.getInformationTypes().getTermsAndConditions().isSetPaymentService()){
				//position 3 Payment Service terms
					List<com.cultuzz.channel.XMLpojo.TermsAndConditionsType.PaymentService> psList=objectConfigurationRQ.getInformationTypes().getTermsAndConditions().getPaymentService();
					for(com.cultuzz.channel.XMLpojo.TermsAndConditionsType.PaymentService ps:psList){
						if(ps.getLanguage().equalsIgnoreCase("en")){
							if(this.checkTACExistorNot(5,englishTAC)>0){
								//update data
								int topicid=this.checkTACExistorNot(5,englishTAC);
								objectConfigurationDAO.updateTopicText(topicid,ps.getValue());
							}else{
								//insert data
								Integer generatedTopicid=objectConfigurationDAO.saveTopic(5,2,"Payment Service",objectId);
								objectConfigurationDAO.saveTopicStates(generatedTopicid,1,ps.getValue());
							}
						}else if(ps.getLanguage().equalsIgnoreCase("de")){
							if(this.checkTACExistorNot(5,germanTAC)>0){
								//update data
								int topicid=this.checkTACExistorNot(5,germanTAC);
								objectConfigurationDAO.updateTopicText(topicid,ps.getValue());
							}else{
								//insert data
								Integer generatedTopicid=objectConfigurationDAO.saveTopic(5,1,"Zahlungsabwicklung",objectId);
								objectConfigurationDAO.saveTopicStates(generatedTopicid,0,ps.getValue());
							}
						}
					}
					
				}
				if(objectConfigurationRQ.getInformationTypes().getTermsAndConditions().isSetVoucherService()){
				//position 4 Voucher/Shipment terms
					List<com.cultuzz.channel.XMLpojo.TermsAndConditionsType.VoucherService> vsList=objectConfigurationRQ.getInformationTypes().getTermsAndConditions().getVoucherService();
					for(com.cultuzz.channel.XMLpojo.TermsAndConditionsType.VoucherService vs:vsList){
						if(vs.getLanguage().equalsIgnoreCase("en")){
							if(this.checkTACExistorNot(6,englishTAC)>0){
								//update data
								int topicid=this.checkTACExistorNot(6,englishTAC);
								objectConfigurationDAO.updateTopicText(topicid,vs.getValue());
							}else{
								//insert data
								Integer generatedTopicid=objectConfigurationDAO.saveTopic(6,2,"Voucher/Shipment",objectId);
								objectConfigurationDAO.saveTopicStates(generatedTopicid,0,vs.getValue());
							}
						}else if(vs.getLanguage().equalsIgnoreCase("de")){
							if(this.checkTACExistorNot(6,germanTAC)>0){
								//update data
								int topicid=this.checkTACExistorNot(6,germanTAC);
								objectConfigurationDAO.updateTopicText(topicid,vs.getValue());
							}else{
								//insert data
								Integer generatedTopicid=objectConfigurationDAO.saveTopic(6,1,"Gutschein / Versand",objectId);
								objectConfigurationDAO.saveTopicStates(generatedTopicid,0,vs.getValue());
							}
						}
					}
				}
				
				if(objectConfigurationRQ.getInformationTypes().getTermsAndConditions().isSetAdditionalValues()){
					List<ValueType> additionalValuesList=objectConfigurationRQ.getInformationTypes().getTermsAndConditions().getAdditionalValues().getValue();
					List<Map<String,Object>> germanAdditionalTACData=null;
					List<Map<String,Object>> englishAdditionalTACData=null;
					boolean engAddTACStatus=false;
					boolean germanAddTACStatus=false;
					
					int engCounter=7;
					int germanCounter=7;
					
					for(ValueType additionalTACValue:additionalValuesList){
						if(additionalTACValue.getLanguage().equalsIgnoreCase("en")){
							System.out.println("Entered to english terms save");
							if(!engAddTACStatus){
								englishAdditionalTACData=objectConfigurationDAO.getAdditionalTACData(objectId,2);
								if(englishAdditionalTACData!=null)
									objectConfigurationDAO.deleteOldAdditionaltac(objectId,2);
								engAddTACStatus=true;
							}
							objectConfigurationDAO.saveAdditionaTAC(engCounter,2,additionalTACValue.getName(),additionalTACValue.getValue(),objectId);
							engCounter++;
						}
						
						if(additionalTACValue.getLanguage().equalsIgnoreCase("de")){
							if(!germanAddTACStatus){
								germanAdditionalTACData=objectConfigurationDAO.getAdditionalTACData(objectId,1);
								if(germanAdditionalTACData!=null)
									objectConfigurationDAO.deleteOldAdditionaltac(objectId,1);
								germanAddTACStatus=true;
							}
							
							objectConfigurationDAO.saveAdditionaTAC(germanCounter,1,additionalTACValue.getName(),additionalTACValue.getValue(),objectId);
							germanCounter++;
						}
					}
					
					
				}
				
			}
			
			if(objectConfigurationRQ.getInformationTypes().isSetFAQ()){
				if(objectConfigurationRQ.getInformationTypes().getFAQ().isSetValue()){
					if(objectConfigurationRQ.getInformationTypes().getFAQ().getValue().size()>0){
						boolean engFaqStatus=false;
						boolean germanFaqStatus=false;
						List<Map<String,Object>> engFaqData=null;
						List<Map<String,Object>> germanFaqData=null;
						for(ValueType val:objectConfigurationRQ.getInformationTypes().getFAQ().getValue()){
							if(val.getLanguage().equalsIgnoreCase("en")){
								
								if(!engFaqStatus){
									engFaqData=objectConfigurationDAO.getFaqData(objectId,2);
									if(engFaqData!=null)
										objectConfigurationDAO.deleteOldFaq(objectId,2);
									engFaqStatus=true;
								}
								
								objectConfigurationDAO.saveFaqText(objectId,2,val.getName(),val.getValue());
							
							}
							
							if(val.getLanguage().equalsIgnoreCase("de")){
								
								if(!germanFaqStatus){
									germanFaqData=objectConfigurationDAO.getFaqData(objectId,2);
									germanFaqStatus=true;
									if(germanFaqData!=null)
										objectConfigurationDAO.deleteOldFaq(objectId,1);
								}
								objectConfigurationDAO.saveFaqText(objectId,1,val.getName(),val.getValue());
							
							}
						}
						
					}
				}
			}
		}
		
		objectConfigurationRS.setAck("Success");
		
		return objectConfigurationRS;
	}
	
	public ObjectConfigurationRS processReputizeSetting(ObjectConfigurationRQ objectConfigurationRQ,String token,ObjectConfigurationRS objectConfigurationrs,List<ErrorType> errorsType){
				int objectid=objectConfigurationRQ.getObjectId();
		String reputizeToken=objectConfigurationDAO.checkTokenExistance(objectid);
		
		if(reputizeToken!=null){
			//call to update
			objectConfigurationDAO.updateReputizeToken(token, objectid);
		}else{
			//call to insertion
			objectConfigurationDAO.insertReputizeToken(objectConfigurationRQ, token);
		}
			
		return objectConfigurationrs;
	}
	
	public boolean checkHttps(String imageurl){
		boolean httpStatus=false;
		if(imageurl!=null){
			if(imageurl.startsWith("https://")){
			
				httpStatus=true;
			}else if(imageurl.equalsIgnoreCase("Delete")){
				httpStatus=true;
			}
		}
		return httpStatus;
	}
	
	public boolean checkLanguage(String language){
		if(language.equalsIgnoreCase("en") || language.equalsIgnoreCase("de"))
			return true;
		else
			return false;
	}
	
	public int checkTACExistorNot(int posid,List<Map<String,Object>> checkingMap){
		//boolean tacExistingStatus=false;
		int defaultTACTopicId=0;
		for(Map<String,Object> checkmap:checkingMap){
			int positionid=Integer.parseInt(checkmap.get("pos_id").toString());
			if(positionid==posid){
				defaultTACTopicId=Integer.parseInt(checkmap.get("id").toString());
				break;
			}
		}
		
		return defaultTACTopicId;
	}
	
	public String checkPaymentExistOrNot(int marketplace,List<Map<String,Object>> paymentoptionsMap){
		String paymentidold=null;
		for(Map<String,Object> paymentids:paymentoptionsMap){
			Integer siteid=null;
			try{
			siteid=Integer.parseInt(paymentids.get("ebaysiteid").toString());
			if(siteid==marketplace){
				paymentidold=paymentids.get("paypal_email").toString();
			}
			}catch(Exception e){
				LOGGER.debug("this is excetion occured at payment checking"+e);
			}
			
		}
		
		return paymentidold;
	}

	
}
