package com.cultuzz.channel.helper.impl;


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

import com.cultuzz.channel.DAO.OfferCreationDAO;
import com.cultuzz.channel.DAO.TemplateModuleDAO;
import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.XMLpojo.AmenitiesType;
import com.cultuzz.channel.XMLpojo.CategoriesType;
import com.cultuzz.channel.XMLpojo.DescriptionPicturesType;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.XMLpojo.NameValueListType;
import com.cultuzz.channel.XMLpojo.PictureDetailsType;
import com.cultuzz.channel.XMLpojo.PrimaryCategoryDetailsType;
import com.cultuzz.channel.XMLpojo.PrimaryItemSpecificsType;
import com.cultuzz.channel.XMLpojo.PropertiesType;
import com.cultuzz.channel.XMLpojo.SecondaryCategoryDetailsType;
import com.cultuzz.channel.XMLpojo.SecondaryItemSpecificsType;
import com.cultuzz.channel.XMLpojo.StoreCategoriesType;
import com.cultuzz.channel.XMLpojo.TemplateCreationRQ;
import com.cultuzz.channel.XMLpojo.TemplateCreationRS;
import com.cultuzz.channel.helper.TemplateCreationHelper;
import com.cultuzz.channel.util.CommonValidations;
import com.cultuzz.utils.PropertiesLoader;


@Repository
public class TemplateCreationHelperImpl implements TemplateCreationHelper{
	
	@Autowired
	TemplateModuleDAO templateModuleDAO;
	
	@Autowired
	CommonValidations commonValidations;
	
	@Autowired
	GetErrorMessagesDAOImpl getErrormessages;
	
	@Autowired
	OfferCreationDAO offerCreationDAO;
	
	@Autowired
	@Qualifier("propertiesRead")
	PropertiesLoader properties;
	
	//private static final String timeStampRegx = "^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";
	
	/*java.util.Date date = new java.util.Date();
	Timestamp timestamp = new Timestamp(date.getTime());*/
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TemplateCreationHelperImpl.class);
	
	public TemplateCreationRS validateTemplateCreationRQ(TemplateCreationRQ templateCreationRQ) {
		
		TemplateCreationRS templateCreationRS=new TemplateCreationRS();
		templateCreationRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		LOGGER.debug("inside validateTamplateCreationRQ method");
		
		int objectid=0;
		
		
		Integer ebaysiteid=null;
		ErrorsType error=new ErrorsType();
		
		//ErrorType error=templateCreationRS.getErrors();
		boolean objectIdFlag=false;
		boolean productIdStatus=false;
		List<ErrorType> errorsType=error.getError();
		int langid=0;
		
		
		if(templateCreationRQ.isSetAuthenticationCode()){
			
		boolean authCodeStatus=	commonValidations.checkAuthCode(templateCreationRQ.getAuthenticationCode());
			if(!authCodeStatus){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1100);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,2));
				errorsType.add(errorType);
				templateCreationRS.setErrors(error);
				templateCreationRS.setAck("Failure");
				return templateCreationRS;
			}
				
			
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1100);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,2));
			errorsType.add(errorType);
			templateCreationRS.setErrors(error);
			templateCreationRS.setAck("Failure");
			return templateCreationRS;
			
		}
		
		if(templateCreationRQ.isSetErrorLang()){
			
			langid=commonValidations.checkErrorLangCode(templateCreationRQ.getErrorLang());
			
			if(langid>0){
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1106);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,2));
				errorsType.add(errorType);
				templateCreationRS.setErrors(error);
				templateCreationRS.setAck("Failure");
				return templateCreationRS;
			}
			
		}else{				
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1106);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,2));
			errorsType.add(errorType);
			templateCreationRS.setErrors(error);
			templateCreationRS.setAck("Failure");
			return templateCreationRS;
		}
		
		
		//List<String> TCErrorMassagesList=new ArrayList<String>();
		
		if(templateCreationRQ.isSetTimeStamp() && !templateCreationRQ.getTimeStamp().trim().isEmpty()){
			
		boolean timestampStatus = commonValidations.checkTimeStamp(templateCreationRQ.getTimeStamp());
		if(!timestampStatus){	
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1104);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1104,langid));
			errorsType.add(errorType);
			templateCreationRS.setErrors(error);
			templateCreationRS.setAck("Failure");
			return templateCreationRS;
		}
		
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1105);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1105,langid));
			errorsType.add(errorType);
			templateCreationRS.setErrors(error);
			templateCreationRS.setAck("Failure");
			return templateCreationRS;
		}
		
		if(templateCreationRQ.isSetSourceId() ){
			if(!commonValidations.checkSourceId(templateCreationRQ.getSourceId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1101);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langid));
				errorsType.add(errorType);
				templateCreationRS.setErrors(error);
				templateCreationRS.setAck("Failure");
				return templateCreationRS;
			}
		
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1101);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langid));
			errorsType.add(errorType);
			templateCreationRS.setErrors(error);
			templateCreationRS.setAck("Failure");
			return templateCreationRS;
			
		}
		
		if(templateCreationRQ.isSetChannelId()){
		
			if(!commonValidations.checkChannelId(templateCreationRQ.getChannelId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1102);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langid));
				errorsType.add(errorType);
				templateCreationRS.setErrors(error);
				templateCreationRS.setAck("Failure");
				return templateCreationRS;
			}
			
		}else{
			
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1102);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langid));
			errorsType.add(errorType);
			templateCreationRS.setErrors(error);
			templateCreationRS.setAck("Failure");
			return templateCreationRS;
			
		}
		
		if(templateCreationRQ.isSetObjectId()){
			
			LOGGER.debug("Checking objectid");
			if(!commonValidations.checkObjectId(templateCreationRQ.getObjectId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1103);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
				errorsType.add(errorType);
				templateCreationRS.setErrors(error);
				templateCreationRS.setAck("Failure");
				return templateCreationRS;
			}else{
				objectIdFlag=true;
				objectid=templateCreationRQ.getObjectId();
			}
			
		}else{
			
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1103);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
			errorsType.add(errorType);
			templateCreationRS.setErrors(error);
			templateCreationRS.setAck("Failure");
			return templateCreationRS;
			
		}
		boolean siteidflag=false;
		if(templateCreationRQ.isSetSiteId()){
		
		LOGGER.debug("Checking ebaysiteid"+templateCreationRQ.getSiteId());
		
		if(templateCreationRQ.isSetSiteId())
			siteidflag=templateModuleDAO.checkSiteId(templateCreationRQ.getSiteId());
		
			LOGGER.debug("ebaysiteid result "+siteidflag);
			if(!siteidflag){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2101);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2101,langid));
				errorsType.add(errorType);
				templateCreationRS.setErrors(error);
				templateCreationRS.setAck("Failure");
				return templateCreationRS;
				
			}else
				ebaysiteid=templateCreationRQ.getSiteId();
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2101);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2101,langid));
			errorsType.add(errorType);
			templateCreationRS.setErrors(error);
			templateCreationRS.setAck("Failure");
			return templateCreationRS;
			
		}
		
		boolean liveServer=false;
		
		
		int designTemplateId=0;
		if(templateCreationRQ.isSetDesignTemplate()){
			LOGGER.debug("Design Template");
			if(!templateCreationRQ.getDesignTemplate().trim().isEmpty()){
				try{
						designTemplateId=Integer.parseInt(templateCreationRQ.getDesignTemplate());
				}catch(Exception e){
					LOGGER.debug("Checking templateid is getting exception");
				}
			boolean templateflag=false;
			LOGGER.debug("Checking templateid");
			if(designTemplateId>0){
			templateflag=templateModuleDAO.checkDesignTemplateId(designTemplateId);
			LOGGER.debug("templateid status"+templateflag);
			if(!templateflag){
			
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2100);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2100,langid));
				errorsType.add(errorType);
				
			}
			}else{
				//Here default design Template value
				/*designTemplateId=1;
				templateCreationRQ.setDesignTemplate("1");*/
				
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2100);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2100,langid));
				errorsType.add(errorType);
			}
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2167);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2167,langid));
				errorsType.add(errorType);
			}
		}else{
			
			designTemplateId=properties.getDesignTemplateid();
			LOGGER.debug("This is design template id from properties file===="+designTemplateId);
			templateCreationRQ.setDesignTemplate(String.valueOf(designTemplateId));
			
			/*if(!liveServer){
				designTemplateId=1;
				templateCreationRQ.setDesignTemplate("1");
			}else if(liveServer){
				//560
			designTemplateId=560;
			
			templateCreationRQ.setDesignTemplate("560");
			}	*/
			/*ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2100);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2100,langid));
			errorsType.add(errorType);*/
		}
		
		if(templateCreationRQ.isSetListingType() && !templateCreationRQ.getListingType().isEmpty() && templateCreationRQ.getListingType()!=null){
			
			LOGGER.debug("listing type is:{}",templateCreationRQ.getListingType());
			if(templateCreationRQ.getListingType().compareToIgnoreCase("Auction")==0 ||
					templateCreationRQ.getListingType().compareToIgnoreCase("Fixed Price Offer")==0){
				
				LOGGER.debug("listing type is valid");
				
			}else{
				
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(3117);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(3117,langid));
					errorsType.add(errorType);
			}
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(3117);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(3117,langid));
			errorsType.add(errorType);
		}
		
		if(templateCreationRQ.isSetProductId() && templateCreationRQ.isSetObjectId()){
			
			LOGGER.debug("Checking productid");
			if(!templateCreationRQ.getProductId().trim().isEmpty()){
				try{
			productIdStatus=templateModuleDAO.checkproductId(templateCreationRQ.getObjectId(), Integer.parseInt(templateCreationRQ.getProductId()));
			LOGGER.debug("Checking productid status"+productIdStatus);
			int productId = Integer.parseInt(templateCreationRQ.getProductId());
			if( productId > 0){
 			   
 			   LOGGER.debug("productId is:{}",productId);
 			   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 				Date date = new Date();
 				String startdate = dateFormat.format(date);
 			   if(!templateModuleDAO.checkProductValidity(productId,startdate)){
 				 
 					ErrorType errorType=new ErrorType();
 					errorType.setErrorCode(3117);
 					errorType.setErrorMessage(getErrormessages.getErrorMessage(3140,langid));
 					errorsType.add(errorType);
 			   }
 			 }else{
 			   
 			   LOGGER.debug("productId is zero");
 			 }
			
				}catch(Exception e){
					LOGGER.debug("Checking productid is getting Exception");
				}
			if(!productIdStatus){
				
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2115);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2115,langid));
				errorsType.add(errorType);
			}
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2115);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2115,langid));
				errorsType.add(errorType);
			}
		}else{
			/*ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2115);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2115,langid));
			errorsType.add(errorType);*/
		
		}
		
		if(templateCreationRQ.isSetVoucherValidity() && templateCreationRQ.getVoucherValidity()>0){
			LOGGER.debug("Checking vouchervalidity");
			int i=0;
			switch(templateCreationRQ.getVoucherValidity()){
			
			case 1095:
				i++;
			case 730:
				i++;
			case 365:
				i++;
			case 182:
				i++;
			case 91:
				i++;
			case 31:
				i++;
				
			}
			
			if(i==0){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2112);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2112,langid));
				errorsType.add(errorType);
			}
				
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2112);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2112,langid));
			errorsType.add(errorType);
		
		}
		if(siteidflag){
		CategoriesType categoriesType=null;
		boolean categoriesTagStatus=false;
		
		if(templateCreationRQ.isSetCategories()){
		categoriesType=templateCreationRQ.getCategories();
		categoriesTagStatus=true;
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2141);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2141,langid));
			errorsType.add(errorType);
		}
		
		PrimaryCategoryDetailsType primaryCategory=null;
		
		boolean primaryCategoryDetailsStatus=false;
		
		if(categoriesTagStatus && categoriesType.isSetPrimaryCategoryDetails()){		
		primaryCategory=categoriesType.getPrimaryCategoryDetails();
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2142);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2142,langid));
			errorsType.add(errorType);
		}
			
			
		if(primaryCategory!=null){
			primaryCategoryDetailsStatus=true;
		}
		
		if(categoriesTagStatus && primaryCategoryDetailsStatus){
		
		if(primaryCategory.isSetPrimaryCategoryId()){
			if(!primaryCategory.getPrimaryCategoryId().trim().isEmpty() && siteidflag){
			
			int fenameErrorsCount=0;
			LOGGER.debug("Checking primarycategoryid");
			boolean primaryCategoryIdflag=false;
			try{
			primaryCategoryIdflag=templateModuleDAO.checkPrimaryCategory(Integer.parseInt(primaryCategory.getPrimaryCategoryId()),objectid,ebaysiteid);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(!primaryCategoryIdflag){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2102);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2102,langid));
				errorsType.add(errorType);
				
			}else if(primaryCategoryIdflag){
				
				PrimaryItemSpecificsType primaryItemSpecifics=null;
				if(primaryCategory.isSetPrimaryItemSpecifics()){
				primaryItemSpecifics=primaryCategory.getPrimaryItemSpecifics();
				}
				List<NameValueListType> fiNamevaluesList=null;
				if(primaryItemSpecifics!=null && primaryItemSpecifics.isSetNameValueList() ){
				fiNamevaluesList=primaryItemSpecifics.getNameValueList();
				}
				boolean fiNameValueExistStatus=false;
				if(fiNamevaluesList!=null && primaryItemSpecifics!=null){
					fiNameValueExistStatus=true;
				}
				
				if(fiNameValueExistStatus){
				if(fiNamevaluesList.size()>0){
					
					for(NameValueListType fiNameValues: fiNamevaluesList){
					//	System.out.println(fiNameValues+"***************");
					//	System.out.println(fiNameValues.isSetName()+"========"+fiNameValues.getName());
						if(fiNameValues.isSetName()){
							
							if(!fiNameValues.getName().trim().isEmpty()){
								
							if(fiNameValues.isSetSValue()){
							
								
								if(!fiNameValues.getSValue().trim().isEmpty()){
									
									if(fiNameValues.isSetValue()){
											if(fiNameValues.getValue().size()==1){
												
											}else
											fenameErrorsCount++;
									}else
										fenameErrorsCount++;
								
								}else{
									if(fiNameValues.isSetValue()){
									if(fiNameValues.getValue().size()>0){
										
									}else
										fenameErrorsCount++;
									}else
										fenameErrorsCount++;
								}
								
							}else{
								if(fiNameValues.isSetValue()){
									if(fiNameValues.getValue().size()>0){
									
									}else
									fenameErrorsCount++;
							
								}else
								fenameErrorsCount++;
							}
							
						}else
							fenameErrorsCount++;
							
						}else{
							fenameErrorsCount++;
						}
						
					}
					
				}
			}
				
			}
			
			if(fenameErrorsCount>0){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2139);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2139,langid));
				errorsType.add(errorType);
			}
			
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2102);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2102,langid));
			errorsType.add(errorType);
			
		}
			
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2102);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2102,langid));
			errorsType.add(errorType);
		}
		
	}
		
		
		SecondaryCategoryDetailsType secondaryCategory=null;
		
		if(categoriesTagStatus && categoriesType.isSetSecondaryCategoryDetails())
		 secondaryCategory=categoriesType.getSecondaryCategoryDetails();
		
		//LOGGER.debug("This is Secondary category details"+secondaryCategory+"==="+secondaryCategory.isSetSecondaryCategoryId());
		
		if(secondaryCategory!=null){
		if(secondaryCategory.isSetSecondaryCategoryId() ){
			LOGGER.debug("Checking secondaryCategoryid setted"+secondaryCategory.getSecondaryCategoryId());
			if(!secondaryCategory.getSecondaryCategoryId().trim().isEmpty() && siteidflag){
			LOGGER.debug("Checking secondaryCategoryid"+secondaryCategory.getSecondaryCategoryId());
			boolean secondaryCateoryIdflag=false;
			int senameserrorCount=0;
			try{
			secondaryCateoryIdflag=templateModuleDAO.checkSecondaryCategory(Integer.parseInt(secondaryCategory.getSecondaryCategoryId()),objectid,ebaysiteid);
			}catch(Exception e){
				e.printStackTrace();
			}
			if(!secondaryCateoryIdflag){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2103);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2103,langid));
				errorsType.add(errorType);
			}else if(secondaryCateoryIdflag){
				
				SecondaryItemSpecificsType secondarySpecifics=null;
				if(secondaryCategory.isSetSecondaryItemSpecifics() && secondaryCategory!=null)
				secondarySpecifics= secondaryCategory.getSecondaryItemSpecifics();
				
				List<NameValueListType> secSpecsNamevaluesList=null;
				if(secondarySpecifics!=null && secondarySpecifics.isSetNameValueList()){
				 secSpecsNamevaluesList= secondarySpecifics.getNameValueList();
				}
				if(secSpecsNamevaluesList!=null && secSpecsNamevaluesList.size()>0){
					
				for(NameValueListType namevaluesList : secSpecsNamevaluesList){
					
					if(namevaluesList.isSetName()){
						
						if(!namevaluesList.getName().trim().isEmpty()){
							
						if(namevaluesList.isSetSValue()){
						
							
							if(!namevaluesList.getSValue().trim().isEmpty()){
								
								if(namevaluesList.isSetValue()){
										if(namevaluesList.getValue().size()==1){
											
										}else
											senameserrorCount++;
								}else
									senameserrorCount++;
							
							}else{
								if(namevaluesList.isSetValue()){
								if(namevaluesList.getValue().size()>0){
									
								}else
									senameserrorCount++;
								}else
									senameserrorCount++;
							}
							
						}else{
							if(namevaluesList.isSetValue()){
								if(namevaluesList.getValue().size()>0){
								
								}else
									senameserrorCount++;
						
							}else
								senameserrorCount++;
						}
						
					}else
						senameserrorCount++;
						
					}else{
						senameserrorCount++;
					}
					
				}
				}
				
			}
			
			if(senameserrorCount>0){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2140);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2140,langid));
				errorsType.add(errorType);
			}
			
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2103);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2103,langid));
			errorsType.add(errorType);
			
		}
			
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2103);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2103,langid));
			errorsType.add(errorType);
		}
		
	}
		
		if(categoriesTagStatus && primaryCategoryDetailsStatus && secondaryCategory!=null){
		
		if(primaryCategory.getPrimaryCategoryId().equals(secondaryCategory.getSecondaryCategoryId())){
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2104);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2104,langid));
			errorsType.add(errorType);
		
		}
		}
			
	}
	
		
		PropertiesType properties=null;
		if(templateCreationRQ.isSetProperties()){
			properties=templateCreationRQ.getProperties();
		}
		if(properties!=null && templateCreationRQ.isSetProperties()){
			boolean propStatus=true;
			if(!properties.isSetCateringType() && !properties.isSetNights() && !properties.isSetPersons() && !properties.isSetTypeOfRoom()){
				propStatus=false;
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2154);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2154,langid));
				errorsType.add(errorType);
			}
		if(propStatus){
		if(properties!=null && properties.isSetCateringType()){
			LOGGER.debug("inside checking catering type room");
			boolean cateringFlag=false;
			try{
			cateringFlag=templateModuleDAO.checkCateringType(Integer.parseInt(properties.getCateringType()));
			}catch(Exception e){
				LOGGER.debug("inside checking catering type is problem");
			}
			if(!cateringFlag){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2106);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2106,langid));
				errorsType.add(errorType);
			
			}
		}
	
		if(properties.isSetTypeOfRoom()){
			LOGGER.debug("inside checking type of room");
			boolean typeOfRoomFlag=false;
			try{
			typeOfRoomFlag=templateModuleDAO.checkTypeOfRoom(Integer.parseInt(properties.getTypeOfRoom()));
			}catch(Exception e){
				LOGGER.debug("Exception occured in type of room checking");
			}
			if(!typeOfRoomFlag){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2107);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2107,langid));
				errorsType.add(errorType);
			}
			}
		
		if(!properties.isSetNights() || properties.getNights().trim().isEmpty()){
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2116);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2116,langid));
			errorsType.add(errorType);
		}else{
			
			try{
				Integer.parseInt(properties.getNights());
			}catch(Exception e){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2116);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2116,langid));
				errorsType.add(errorType);
				LOGGER.debug("This is problem inside no of nights invalid");
			}
			
		}
		
		if(!properties.isSetPersons() || properties.getPersons().trim().isEmpty()){
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2117);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2117,langid));
			errorsType.add(errorType);
		}else{
			
			try{
				Integer.parseInt(properties.getPersons());
			}catch(Exception e){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2117);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2117,langid));
				errorsType.add(errorType);
				LOGGER.debug("This is problem inside no of persons invalid");
			}
			
		}
		
		}
		}else{
			
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2154);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2154,langid));
			errorsType.add(errorType);
			
		}
		
		if(!templateCreationRQ.isSetTemplateName() || templateCreationRQ.getTemplateName().isEmpty()){
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2138);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2138,langid));
			errorsType.add(errorType);
		}else if(templateCreationRQ.isSetTemplateName() && templateCreationRQ.getTemplateName().trim().length()>80){
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2132);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2132,langid));
			errorsType.add(errorType);
		}
		
		if(!templateCreationRQ.isSetOfferTitle() || templateCreationRQ.getOfferTitle().isEmpty()){
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2105);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2105,langid));
			errorsType.add(errorType);
		}else if(templateCreationRQ.isSetOfferTitle() && templateCreationRQ.getOfferTitle().trim().length()>80){
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2133);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2133,langid));
			errorsType.add(errorType);	
		}
		
		if(templateCreationRQ.isSetOfferSubTitle()){
				
			if(!templateCreationRQ.getOfferSubTitle().trim().isEmpty()){
				
				if(templateCreationRQ.getOfferSubTitle().trim().length()>80){
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2134);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2134,langid));
			errorsType.add(errorType);	
				}
			}else{
				
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2165);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2165,langid));
				errorsType.add(errorType);
			}
		}
		
		//int listingTypeVar=0;
		/*if(!templateCreationRQ.isSetListingType() ){
			listingTypeVar++;
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2144);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2144,langid));
			errorsType.add(errorType);
		}*/
		/*boolean listingTypeStatus=false;
		int listEmpty=0;
		if(templateCreationRQ.isSetListingType() && templateCreationRQ.getListingType().isEmpty()){
			listEmpty++;
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(3117);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(3117,langid));
			errorsType.add(errorType);
		}*/
		
		/*int listingtypeChecking =0;
		if(templateCreationRQ.isSetListingType() && templateCreationRQ.getListingType().trim().equals("Fixed Price Offer"))
			listingtypeChecking++;
		else if(templateCreationRQ.isSetListingType() && templateCreationRQ.getListingType().trim().equals("Store Fixed Price Offer"))
			listingtypeChecking++;
		else if(templateCreationRQ.isSetListingType() && templateCreationRQ.getListingType().trim().equals("Auction"))
			listingtypeChecking++;*/
		
			/*if(listingtypeChecking==0 && listingTypeVar==0 && listEmpty==0){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2144);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2144,langid));
				errorsType.add(errorType);
			}else if(listingtypeChecking!=0){
				listingTypeStatus=true;
				if(templateCreationRQ.isSetListingType() && templateCreationRQ.getListingType().trim().equals("Store Fixed Price Offer")){
					
					if(!templateCreationRQ.isSetShopObjectId()){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2177);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2177,langid));
						errorsType.add(errorType);
					}
					
					if(!templateCreationRQ.isSetStoreCategories()){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2180);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2180,langid));
						errorsType.add(errorType);
					}
					
					
				}
			}*/
			
			
			int shopObjectid=0;
			boolean shopObjectiIDStatus=false;
			if(templateCreationRQ.isSetShopObjectId() ){
			
				//if(templateCreationRQ.isSetListingType() && templateCreationRQ.getListingType().equals("Store Fixed Price Offer")){
				
					if(templateCreationRQ.getShopObjectId().isEmpty()){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2185);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2185,langid));
						errorsType.add(errorType);
						
					}else{
						
					try{
						shopObjectid=Integer.parseInt(templateCreationRQ.getShopObjectId());
					}catch(Exception e){
						LOGGER.debug("this is shop objectid invalid");
						e.printStackTrace();
					}
				boolean shopObjStatus=false;
				
					if(shopObjectid>0)
				 	shopObjStatus=offerCreationDAO.checkShopObjectId(objectid, shopObjectid);
					else
						shopObjStatus=false;
				 
				if(!shopObjStatus){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2176);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2176,langid));
					errorsType.add(errorType);
				}else
					shopObjectiIDStatus=true;
					
				}
					if(shopObjectiIDStatus){
						if(!templateCreationRQ.isSetStoreCategories()){							
							ErrorType errorType=new ErrorType();
							errorType.setErrorCode(2180);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(2180,langid));
							errorsType.add(errorType);
						}
					}
				
				/*}else{
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2178);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2178,langid));
					errorsType.add(errorType);
					
				}*/
			}
			
			
			
			//offerCreationDAO.checkShopObjectId(objectId, shopObjectId);
			StoreCategoriesType storeCategoriesType=null;
			boolean storecatsEmpty=true;
			
			//if(templateCreationRQ.isSetListingType() && templateCreationRQ.getListingType().equals("Store Fixed Price Offer")){
				
			if(templateCreationRQ.isSetStoreCategories()){
				if(shopObjectiIDStatus){
			 storeCategoriesType=templateCreationRQ.getStoreCategories();
			 
			 if(!storeCategoriesType.isSetStoreCategoryId() && !storeCategoriesType.isSetStoreCategory2Id()){
				 storecatsEmpty=false;
				 ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2184);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2184,langid));
					errorsType.add(errorType);
			 }
			 
			}else{
				 ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2185);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2185,langid));
					errorsType.add(errorType);
			}}else{
				
				/*if((templateCreationRQ.isSetListingType() && templateCreationRQ.getListingType().equals("Store Fixed Price Offer")) || (templateCreationRQ.isSetShopObjectId()&& !templateCreationRQ.getShopObjectId().trim().isEmpty() )){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2177);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2177,langid));
					errorsType.add(errorType);
				}*/
			}
				/*}else{
					 ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2185);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2185,langid));
						errorsType.add(errorType);
				}*/
			/*}else if(listingTypeStatus){
				
				if(templateCreationRQ.isSetStoreCategories()){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2179);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2179,langid));
					errorsType.add(errorType);
				}
				
			}*/
			
			
			long ebaycatid=0;
			// handle for store categories	
			if(templateCreationRQ.isSetStoreCategories() && storeCategoriesType!=null && storecatsEmpty){
				boolean storePrimaryCategoryIdflag=false;	
			if(storeCategoriesType.isSetStoreCategoryId() && !storeCategoriesType.getStoreCategoryId().trim().isEmpty()){
				//boolean storePrimaryCategoryIdflag=false;
				
				try{
					ebaycatid=Long.parseLong(storeCategoriesType.getStoreCategoryId());
				}catch(NumberFormatException nfe){
					LOGGER.debug("Invalid storeebaycategoryid");
					nfe.printStackTrace();
				}
				
				LOGGER.debug("Checking store primarycategoryid");
				if(ebaycatid>0 && ebaycatid!=1 && shopObjectid>0)
				storePrimaryCategoryIdflag=templateModuleDAO.checkStoreCategoryId(ebaycatid,shopObjectid);
				else if(ebaycatid==1)
					storePrimaryCategoryIdflag=true;
				else
					storePrimaryCategoryIdflag=false;
				
				if(!storePrimaryCategoryIdflag){	
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2174);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2174,langid));
				errorsType.add(errorType);
				}
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2181);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2181,langid));
				errorsType.add(errorType);
			}
			long ebaycat2id=0;
			boolean storeSecondaryCategoryIdflag=false;
			if(storeCategoriesType.isSetStoreCategory2Id() && !storeCategoriesType.getStoreCategory2Id().trim().isEmpty() ){
				//boolean storeSecondaryCategoryIdflag=false;
				
				try{
					ebaycat2id=Long.parseLong(storeCategoriesType.getStoreCategory2Id());
				}catch(NumberFormatException nfe){
					LOGGER.debug("Invalid storeebaycategory2id");
					nfe.printStackTrace();
				}
				LOGGER.debug("Checking store secondary categoryid"+storeCategoriesType.getStoreCategory2Id());
				if(ebaycat2id>0 && ebaycat2id!=1 && shopObjectid>0)
				storeSecondaryCategoryIdflag=templateModuleDAO.checkStoreCategoryId(ebaycat2id,shopObjectid);
				else if(ebaycat2id==1)
					storeSecondaryCategoryIdflag=true;
				else
					storeSecondaryCategoryIdflag=false;
				
				if(!storeSecondaryCategoryIdflag){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2175);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2175,langid));
					errorsType.add(errorType);
				
				}
			}else{
				if(storeCategoriesType.isSetStoreCategory2Id() && storeCategoriesType.getStoreCategory2Id().isEmpty()){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2183);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2183,langid));
					errorsType.add(errorType);
				}
			}
			
			LOGGER.debug("this is for store categories####"+storePrimaryCategoryIdflag +"===="+storeSecondaryCategoryIdflag);
			
			if(storePrimaryCategoryIdflag && storeSecondaryCategoryIdflag){
				LOGGER.debug("Both== 1"+storeCategoriesType.isSetStoreCategoryId());
				if(storeCategoriesType.isSetStoreCategoryId() && storeCategoriesType.isSetStoreCategory2Id()){
					LOGGER.debug("Both == 2 "+storeCategoriesType.getStoreCategoryId());
					if(ebaycatid>0 && ebaycat2id>0){
						LOGGER.debug("Both == 3 "+storeCategoriesType.getStoreCategoryId()+"=="+storeCategoriesType.getStoreCategory2Id());
						if(storeCategoriesType.getStoreCategoryId().equals(storeCategoriesType.getStoreCategory2Id())){
							ErrorType errorType=new ErrorType();
							errorType.setErrorCode(2182);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(2182,langid));
							errorsType.add(errorType);
						}
							
					}
				}
			}
			
			}else{
				
				/*if(templateCreationRQ.isSetShopObjectId()){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2178);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2178,langid));
					errorsType.add(errorType);
				}
				*/
			}
			
			
			
			
			if(templateCreationRQ.isSetPictureDetails()){
				
				if(!templateCreationRQ.getPictureDetails().isSetGalleryURL() || (templateCreationRQ.getPictureDetails().isSetGalleryURL() && templateCreationRQ.getPictureDetails().getGalleryURL().trim().isEmpty())){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2108);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2108,langid));
					errorsType.add(errorType);
				
				}
				
				/*if(!templateCreationRQ.getPictureDetails().isSetVoucherPictureURL() || templateCreationRQ.getPictureDetails().getVoucherPictureURL().trim().isEmpty()){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2186);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2186,langid));
					errorsType.add(errorType);
				
				}*/
				
				int count=0;
				if(templateCreationRQ.getPictureDetails().getPictureURL().size()>0){
					
					for(String itemPics:templateCreationRQ.getPictureDetails().getPictureURL()){
						
						if(itemPics.trim().isEmpty()){
							count++;
						}
					}
					
					if(count>0){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2151);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2151,langid));
						errorsType.add(errorType);
					}
				}
				
				if(!templateCreationRQ.getPictureDetails().isSetGalleryURL() && !templateCreationRQ.getPictureDetails().isSetPictureURL() ){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2152);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2152,langid));
					errorsType.add(errorType);
				}
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2118);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2118,langid));
				errorsType.add(errorType);
			
			}
			
			if(templateCreationRQ.isSetDescription()){
				boolean descEmptyCheck=false;
				 if(!templateCreationRQ.getDescription().isSetTitle() && !templateCreationRQ.getDescription().isSetOfferText() && !templateCreationRQ.getDescription().isSetSubTitle() && !templateCreationRQ.getDescription().isSetTitle() &&!templateCreationRQ.getDescription().isSetVoucherText() && !templateCreationRQ.getDescription().isSetVoucherValidityText()){
					 descEmptyCheck=true;
					 ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2149);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2149,langid));
						errorsType.add(errorType);
				 }
				if(!descEmptyCheck){
					
				if(templateCreationRQ.getDescription().isSetTitle() && templateCreationRQ.getDescription().getTitle().trim().isEmpty()){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2109);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2109,langid));
					errorsType.add(errorType);
					
				}else if(templateCreationRQ.getDescription().isSetTitle() && templateCreationRQ.getDescription().getTitle().length()>80){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2135);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2135,langid));
					errorsType.add(errorType);
				}
				
				if(templateCreationRQ.getDescription().isSetSubTitle() && templateCreationRQ.getDescription().getSubTitle().trim().isEmpty()){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2147);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2147,langid));
					errorsType.add(errorType);
				}else if(templateCreationRQ.getDescription().isSetSubTitle() && templateCreationRQ.getDescription().getSubTitle().length()>80){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2172);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2172,langid));
					errorsType.add(errorType);
				}
				
				if(!templateCreationRQ.getDescription().isSetOfferText() || templateCreationRQ.getDescription().getOfferText().isEmpty() ){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2110);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2110,langid));
					errorsType.add(errorType);
				
				}
				
				if(templateCreationRQ.getDescription().isSetOfferAdditionalText() && templateCreationRQ.getDescription().getOfferAdditionalText().trim().isEmpty()){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2146);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2146,langid));
					errorsType.add(errorType);
				}
				
				if(!templateCreationRQ.getDescription().isSetVoucherText() || templateCreationRQ.getDescription().getVoucherText().isEmpty() ){
					//this checking for it is exist or not
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2111);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2111,langid));
					errorsType.add(errorType);
				}else if(templateCreationRQ.getDescription().isSetVoucherText() && templateCreationRQ.getDescription().getVoucherText().trim().length()>932){
					//this is checking for length of voucher text
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2136);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2136,langid));
					errorsType.add(errorType);
				}
				
				 if(templateCreationRQ.getDescription().isSetVoucherValidityText() && templateCreationRQ.getDescription().getVoucherValidityText().trim().length()>450){
						//this is checking for length of voucher text
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2137);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2137,langid));
						errorsType.add(errorType);
					}else if(templateCreationRQ.getDescription().isSetVoucherValidityText() && templateCreationRQ.getDescription().getVoucherValidityText().trim().isEmpty()){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2166);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2166,langid));
						errorsType.add(errorType);
					}
			}
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2119);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2119,langid));
				errorsType.add(errorType);
			
			}
			
			if(templateCreationRQ.isSetDescriptionPictures()){
				
				/*if(templateCreationRQ.getDescriptionPictures().isSetLogoPictureURL() && templateCreationRQ.getDescriptionPictures().getLogoPictureURL().trim().isEmpty()){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2162);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2162,langid));
					errorsType.add(errorType);
				
				}else if(!templateCreationRQ.getDescriptionPictures().isSetLogoPictureURL()){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2121);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2121,langid));
					errorsType.add(errorType);
				}*/
//				if(templateCreationRQ.getDescriptionPictures().isSetLogoPictureURL() && templateCreationRQ.getDescriptionPictures().getLogoPictureURL().trim().isEmpty()){
//					ErrorType errorType=new ErrorType();
//					errorType.setErrorCode(2162);
//					errorType.setErrorMessage(getErrormessages.getErrorMessage(2162,langid));
//					errorsType.add(errorType);
//				
//				}
//				else if(!templateCreationRQ.getDescriptionPictures().isSetLogoPictureURL()){
//					ErrorType errorType=new ErrorType();
//					errorType.setErrorCode(2121);
//					errorType.setErrorMessage(getErrormessages.getErrorMessage(2121,langid));
//					errorsType.add(errorType);
//				}
				
				if(templateCreationRQ.getDescriptionPictures().isSetDescriptionPictureURL()){
					
					int counter=0;
					for(String picsCheck:templateCreationRQ.getDescriptionPictures().getDescriptionPictureURL()){
						if(picsCheck.trim().isEmpty()){
							counter++;
						}
				
					}
					
					if(counter>0){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2150);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2150,langid));
						errorsType.add(errorType);
					}
					
					//if(designTemplateId!=0 && counter==0){
					if(designTemplateId!=0){
						int descPicsCount=templateModuleDAO.getDescImagesCount(designTemplateId);
						if(descPicsCount>0){
							
					if(templateCreationRQ.getDescriptionPictures().getDescriptionPictureURL().size()!=descPicsCount){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2122);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2122,langid)+" "+descPicsCount);
						errorsType.add(errorType);
					}
					}
					}
					
					
				}else{
					/*if(templateModuleDAO.getDescImagesCount(designTemplateId)>0){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2125);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2125,langid));
						errorsType.add(errorType);
					}*/
					
				}
				
				if(templateCreationRQ.getDescriptionPictures().isSetOfferSliderPictureURL()){
					
					int counter=0;
					for(String picsCheck:templateCreationRQ.getDescriptionPictures().getOfferSliderPictureURL()){
						if(picsCheck.isEmpty()){
							counter++;
						}
				
					}
					
					if(counter>0){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2158);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2158,langid));
						errorsType.add(errorType);
					}
					
					
//					if(templateCreationRQ.getDescriptionPictures().getOfferSliderPictureURL().size()<2 && counter==0){
//						ErrorType errorType=new ErrorType();
//						errorType.setErrorCode(2123);
//						errorType.setErrorMessage(getErrormessages.getErrorMessage(2123,langid));
//						errorsType.add(errorType);
//					}
					
				}
				
				/*if(templateCreationRQ.getDescriptionPictures().isSetObjectSliderPictureURL()){
					
					int counter=0;
					for(String picsCheck:templateCreationRQ.getDescriptionPictures().getObjectSliderPictureURL()){
						if(picsCheck.isEmpty()){
							counter++;
						}
				
					}
					
					if(counter>0){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2159);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2159,langid));
						errorsType.add(errorType);
					}
					
					if(templateCreationRQ.getDescriptionPictures().getObjectSliderPictureURL().size()<2 && counter==0){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2124);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2124,langid));
					errorsType.add(errorType);
					}
				}*/
				

				/*if(!templateCreationRQ.getDescriptionPictures().isSetDescriptionPictureURL()){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2148);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2148,langid));
					errorsType.add(errorType);
				}*/
				
			}
			/*else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2120);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2120,langid));
				errorsType.add(errorType);
			}*/

			
			if(templateCreationRQ.isSetAdditionalOptions()){
				int counter=0;
				if(templateCreationRQ.getAdditionalOptions().isSetOptionValue() && templateCreationRQ.getAdditionalOptions().getOptionValue().size()>0){
					
					for(String optionvalue:templateCreationRQ.getAdditionalOptions().getOptionValue()){
						
						if(optionvalue.trim().isEmpty())
							counter++;
					}
				}
				
				if(counter>0){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2114);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2114,langid));
					errorsType.add(errorType);
				}
				
				if(counter==0){
				List<String> listAdditionalOptions=templateCreationRQ.getAdditionalOptions().getOptionValue();
				int i=0;
				List<Integer> duplicateList=new ArrayList<Integer>();
				for(String listvalue:listAdditionalOptions){
					
					try{
					
					if(duplicateList.contains(Integer.parseInt(listvalue)))
						i++;
					else
						duplicateList.add(Integer.parseInt(listvalue));
					
					if(Integer.parseInt(listvalue)==1 || Integer.parseInt(listvalue)==2 || Integer.parseInt(listvalue)==4 || Integer.parseInt(listvalue)==8 || Integer.parseInt(listvalue) ==16 ){
						
					}else
						i++;
					
					}catch(Exception e){
						i++;
					}
				}
				
				if(i>0){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2114);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2114,langid));
					errorsType.add(errorType);
				}
				
				}
				
				if(!templateCreationRQ.getAdditionalOptions().isSetOptionValue()){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2160);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2160,langid));
					errorsType.add(errorType);
				}
				
			}
			
			
			if(templateCreationRQ.isSetPrice()){
				LOGGER.debug("this is price"+templateCreationRQ.getPrice());
				if(templateCreationRQ.getPrice()>0){
					
				Double price=templateCreationRQ.getPrice();
						
				if(String.valueOf(price).split("\\.")[1].length()>2){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2131);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2131,langid));
					errorsType.add(errorType);
				}else if(String.valueOf(price).length()>10){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2131);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2131,langid));
					errorsType.add(errorType);
				}
					
				}else if(templateCreationRQ.getPrice()==0){
					
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(4113);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(4113,langid));
					errorsType.add(errorType);
				}else{
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2131);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2131,langid));
					errorsType.add(errorType);
				}
				
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2131);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2131,langid));
				errorsType.add(errorType);
			}
			
			LOGGER.debug("Outside the RetailsPrice"+templateCreationRQ.getRetailPrice());
			if(templateCreationRQ.isSetRetailPrice()){
			if(!templateCreationRQ.getRetailPrice().isEmpty()){
				LOGGER.debug("Retail Price is not emplty"+templateCreationRQ.getRetailPrice());
				if(templateCreationRQ.isSetRetailPrice() && templateCreationRQ.getRetailPrice()!=null && Double.parseDouble(templateCreationRQ.getRetailPrice())>0.0){
				
				LOGGER.debug("Inside the RetailsPrice"+templateCreationRQ.getRetailPrice());
				if(templateCreationRQ.getRetailPrice().contains(".")){
					LOGGER.debug("Retail Price contains decimals"+templateCreationRQ.getRetailPrice());
					if(templateCreationRQ.getRetailPrice().split("\\.")[1].length()>2){
						LOGGER.debug("Retail Price contains more than 2 precisions"+templateCreationRQ.getRetailPrice());
  	    				 ErrorType retailError = new ErrorType();
  	    				retailError.setErrorCode(2190);
  		  					
  	    				retailError.setErrorMessage(getErrormessages.getErrorMessage(2190, langid));
  	    				errorsType.add(retailError);
  	    			}
				}
				
				if(!commonValidations.allowedMarketplaces(templateCreationRQ.getSiteId())){
					LOGGER.debug("Retail Price is not allowed for marktplace"+templateCreationRQ.getRetailPrice()+"Site ID"+templateCreationRQ.getSiteId());
					ErrorType retailError = new ErrorType();
	    				retailError.setErrorCode(1112);
		  				retailError.setErrorMessage(getErrormessages.getErrorMessage(1112, langid));
	    				errorsType.add(retailError);
				}
				}else{
					LOGGER.debug("Retail Price is invalid"+templateCreationRQ.getRetailPrice());
					ErrorType retailError = new ErrorType();
					retailError.setErrorCode(1113);
						retailError.setErrorMessage(getErrormessages.getErrorMessage(1113, langid));
					errorsType.add(retailError);
				}
				if(!(errorsType.size()>0)){
					LOGGER.debug("No Errors are set for Price"+templateCreationRQ.getRetailPrice());
				if(!commonValidations.checkRetailPrice(templateCreationRQ.getPrice(), Double.parseDouble(templateCreationRQ.getRetailPrice()))){
					LOGGER.debug("Retail Price is less than Price"+templateCreationRQ.getRetailPrice());
					ErrorType retailError = new ErrorType();
    				retailError.setErrorCode(1111);
	  				retailError.setErrorMessage(getErrormessages.getErrorMessage(1111, langid));
    				errorsType.add(retailError);
				}
				}
			}else{
				LOGGER.debug("Retail Price is invalid"+templateCreationRQ.getRetailPrice());
				ErrorType retailError = new ErrorType();
				retailError.setErrorCode(1113);
					retailError.setErrorMessage(getErrormessages.getErrorMessage(1113, langid));
				errorsType.add(retailError);
			}	
	}	
			if(templateCreationRQ.isSetCurrency()){
				boolean currencyStatus=false;
				//CurrencyCodeType cc=CurrencyCodeType.AUD; 
				String cct=templateCreationRQ.getCurrency();
				
				LOGGER.debug("This is currency code=="+cct);
			
				currencyStatus =templateModuleDAO.checkCurrency(ebaysiteid,cct.toString());
				if(!currencyStatus){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2130);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2130,langid));
					errorsType.add(errorType);
				}
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2130);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2130,langid));
				errorsType.add(errorType);
			}
			
			
			if(templateCreationRQ.isSetAmenities()){
				int emenityCount=0;
				int aminityidcounter=0;
				//LOGGER.debug("Emenities checking "+templateCreationRQ.isSetAmenities()+"Amenities array"+templateCreationRQ.getAmenities()+"Amenity idsss size"+templateCreationRQ.getAmenities().getAmenityId().size());
				if(templateCreationRQ.getAmenities().isSetAmenityId() &&templateCreationRQ.getAmenities().getAmenityId().size()>0){
					emenityCount++;
					int amenitycounter=0;
					for(String amenityId:templateCreationRQ.getAmenities().getAmenityId()){
						boolean amenityStatus=false;
						boolean amenityIDStatus=false;
					if(!amenityId.trim().isEmpty()){
						amenityStatus=true;
						try{
							amenityIDStatus=templateModuleDAO.checkAmenityId(Integer.parseInt(amenityId));
						}catch(Exception e){
							amenityIDStatus=false;
							e.printStackTrace();
						}
					}else
						amenityStatus=false;
				
					if(amenityStatus==false){
						amenityIDStatus=true;
					}
					
					if(!amenityStatus){
						amenitycounter++;
					}
					if(!amenityIDStatus){
						aminityidcounter++;
					}
					}
					LOGGER.debug("Amenityies counter values empty status"+amenitycounter +" invalid status"+aminityidcounter);
					//System.out.println("Amenityies counter values empty status"+amenitycounter +" invalid status"+aminityidcounter);
					if(amenitycounter>0){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2173);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2173,langid));
						errorsType.add(errorType);
					}
					
					if(aminityidcounter>0){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2157);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2157,langid));
						errorsType.add(errorType);
					}
					
				}
				
				if(emenityCount==0){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2156);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2156,langid));
					errorsType.add(errorType);
				}
				
				
			}
			
			
			
	
			/*if(!shopObjectiIDStatus){
				if(templateCreationRQ.isSetRetailPrice() && Double.parseDouble(templateCreationRQ.getRetailPrice())>0.0 && templateCreationRQ.getRetailPrice()!=null){
					 ErrorType retailError = new ErrorType();
	    				retailError.setErrorCode(2191);		
	    				retailError.setErrorMessage(getErrormessages.getErrorMessage(2191, langid));
	    				errorsType.add(retailError);
				}
			}*/
		if(errorsType.size()>0){
			templateCreationRS.setAck("Failure");
			templateCreationRS.setErrors(error);
		}else{
			templateCreationRS.setAck("Success");
		}
		
		
		//templateCreationRS.setErrors(value);;
		
		return templateCreationRS;
	} 
	
	
	
	public TemplateCreationRS processTemplateCreationRQ(TemplateCreationRQ templateCreationRQ){
		
		LOGGER.debug("this is inside process template creation Time is======="+commonValidations.getCurrentTimeStamp());
		
		TemplateCreationRS templateCreationRS=new TemplateCreationRS();
		templateCreationRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		boolean saveFlag=false;
		boolean voucherdataFlag=false;
		boolean vorlageArrangementFlag=false;
		boolean savePicturesFlag=false;
		boolean hfdataFlag = false;
		boolean saveDescPicturesFlag=false;
		boolean saveEminities=false;
		int sourceid=templateCreationRQ.getSourceId();
		
		int objectid=0;
		if(templateCreationRQ.isSetObjectId())
		objectid=templateCreationRQ.getObjectId();
		
		int langid=commonValidations.checkErrorLangCode(templateCreationRQ.getErrorLang());
		
		ErrorsType TCErrors=new ErrorsType();
		List<ErrorType> tcErrorTypeList=TCErrors.getError();
		
		int vorlageId=templateModuleDAO.saveVorlageData(templateCreationRQ);
		
		if(vorlageId<=0){
			ErrorType tcErrorType=new ErrorType();
			tcErrorType.setErrorCode(2143);
			tcErrorType.setErrorMessage(getErrormessages.getErrorMessage(2143,langid));
			tcErrorTypeList.add(tcErrorType);
		}else
			saveFlag=true;
		
		if(vorlageId>0){
			voucherdataFlag = templateModuleDAO.saveVocherData(vorlageId, templateCreationRQ);
		}
		
		if(vorlageId>0){
			System.out.println("he%%% "+templateCreationRQ.getHeaderFooter()+" sfs");
			System.out.println("new header url"+templateCreationRQ.getNewHeaderURL());
			System.out.println("new footer url"+templateCreationRQ.getNewFooterURL());
		   if(templateCreationRQ.getNewHeaderURL()!=null && !templateCreationRQ.getNewHeaderURL().isEmpty()
					 && templateCreationRQ.getNewFooterURL()!=null && !templateCreationRQ.getNewFooterURL().isEmpty()){
			if(templateCreationRQ.getHeaderFooter()!=null && !templateCreationRQ.getHeaderFooter().isEmpty()){
			hfdataFlag = templateModuleDAO.saveHeaderFooterData(vorlageId, templateCreationRQ.getHeaderFooter(),templateCreationRQ.getNewHeaderURL(),templateCreationRQ.getNewFooterURL());
			  System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			}else{
				System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			hfdataFlag = templateModuleDAO.saveHeaderFooterData(vorlageId,"new",templateCreationRQ.getNewHeaderURL(),templateCreationRQ.getNewFooterURL());
			}
		  }
		}
		
		if( vorlageId>0){
			vorlageArrangementFlag=templateModuleDAO.saveVorlageArrangementData(vorlageId, templateCreationRQ);
		}
		
		PictureDetailsType itemPicturesAndGallaryPicture=templateCreationRQ.getPictureDetails();
		DescriptionPicturesType descriptionPictures=templateCreationRQ.getDescriptionPictures();
		
		if(vorlageId>0){
			savePicturesFlag=templateModuleDAO.savePictureDetails(vorlageId, itemPicturesAndGallaryPicture,objectid);
		}
		
		if(vorlageId>0){
			saveDescPicturesFlag=templateModuleDAO.saveDescriptionPictures(vorlageId,descriptionPictures,objectid);
		}
	
		CategoriesType categories=null;
		if(templateCreationRQ.isSetCategories())
			categories=templateCreationRQ.getCategories();
		
		PrimaryCategoryDetailsType primaryCategoryDetails=null;
		
		if(categories!=null && categories.isSetPrimaryCategoryDetails())
			primaryCategoryDetails=categories.getPrimaryCategoryDetails();
		
		
		PrimaryItemSpecificsType primaryItemSpecifics=null;
		if(primaryCategoryDetails!=null && primaryCategoryDetails.isSetPrimaryItemSpecifics())
			primaryItemSpecifics=primaryCategoryDetails.getPrimaryItemSpecifics();
		
		List<NameValueListType> primaryNameValueList=null;
		if(primaryItemSpecifics!=null && primaryItemSpecifics.isSetNameValueList())
			primaryNameValueList=primaryItemSpecifics.getNameValueList();
		
		//List<PrimaryItemSpecificsType>  primaryItemSpecifics = primaryCategoryDetails.getPrimaryItemSpecifics();
		int primaryCatId=0;
		if(categories.isSetPrimaryCategoryDetails() && categories.getPrimaryCategoryDetails().isSetPrimaryCategoryId()){
		if(vorlageId>0 && Integer.parseInt(categories.getPrimaryCategoryDetails().getPrimaryCategoryId())>0){	
			primaryCatId=templateModuleDAO.saveItemCategories(vorlageId,Integer.parseInt(categories.getPrimaryCategoryDetails().getPrimaryCategoryId()),1);
		}
		}
		if(primaryCatId>0){
					if(primaryNameValueList!=null)
						templateModuleDAO.saveItemSpecifics(primaryCatId,primaryNameValueList);
		}
		
		SecondaryCategoryDetailsType secondaryCategoryDetails=null;
		if(categories!=null && categories.isSetSecondaryCategoryDetails())
			secondaryCategoryDetails=categories. getSecondaryCategoryDetails();
		
		SecondaryItemSpecificsType secondaryItemSpecifics=null;
		if(secondaryCategoryDetails!=null && secondaryCategoryDetails.isSetSecondaryItemSpecifics())
		secondaryItemSpecifics= secondaryCategoryDetails.getSecondaryItemSpecifics();
		
		List<NameValueListType>  secondaryNameValueList=null;
		
		if(secondaryItemSpecifics!=null && secondaryItemSpecifics.isSetNameValueList())
		secondaryNameValueList =secondaryItemSpecifics.getNameValueList();
		
				
		int secondaryCatId=0;
		if(categories.isSetSecondaryCategoryDetails() && categories.getSecondaryCategoryDetails().isSetSecondaryCategoryId()){
		if(vorlageId>0 && Integer.parseInt(categories.getSecondaryCategoryDetails().getSecondaryCategoryId())>0){
			secondaryCatId=templateModuleDAO.saveItemCategories(vorlageId,Integer.parseInt(categories.getSecondaryCategoryDetails().getSecondaryCategoryId()),2);
		}
		}
		if(secondaryCatId>0){
			if(secondaryNameValueList!=null)
			templateModuleDAO.saveItemSpecifics(secondaryCatId,secondaryNameValueList);
		
		}
		
		AmenitiesType amenities=null;
		if(templateCreationRQ.isSetAmenities())
		 amenities=templateCreationRQ.getAmenities();
		
		if(vorlageId>0 && amenities!=null){
			saveEminities=templateModuleDAO.saveAmenities(vorlageId, amenities);
		}
		int shopObjectid=0;
		if(vorlageId>0 && templateCreationRQ.isSetShopObjectId()){
			try{
			shopObjectid=Integer.parseInt(templateCreationRQ.getShopObjectId());
			}catch(NumberFormatException e){
				e.printStackTrace();
			}
			
			templateModuleDAO.saveShopCategories(templateCreationRQ.getStoreCategories(),vorlageId,shopObjectid);
		}
		
		int collectionObjectid=0;
		if(vorlageId>0 && templateCreationRQ.isSetCollectionObjectId()){
			
			try{
				collectionObjectid=Integer.parseInt(templateCreationRQ.getCollectionObjectId());
			}catch(NumberFormatException e){
				e.printStackTrace();
			}
			
			templateModuleDAO.saveCollectionObject(vorlageId,collectionObjectid);
		}
		
	/*	if(vorlageId>0 && templateCreationRQ.isSetPictureDetails() && templateCreationRQ.getPictureDetails().isSetVoucherPictureURL()){
			templateModuleDAO.saveCheVoucherPicture(vorlageId,templateCreationRQ.getPictureDetails().getVoucherPictureURL(),objectid);
		}*/
		
	/*	boolean saveThemeIdStatus=false;
		if(vorlageId>0){
			int themeid=templateModuleDAO.getThemeId(objectid);
			if(themeid==0){
				
				if(templateCreationRQ.getSourceId()==2){
				 int themeInsertStatus=  templateModuleDAO.saveVoucherThemeId(objectid,9);
				    if(themeInsertStatus>0)
				    	saveThemeIdStatus=true;
				}else{
					 int themeInsertStatus=templateModuleDAO.saveVoucherThemeId(objectid,1);
					if(themeInsertStatus>0)
				    	saveThemeIdStatus=true;
				}
			}else if(themeid==1){
				if(templateCreationRQ.getSourceId()==2){
					 int themeInsertStatus=  templateModuleDAO.saveUpdateThemeId(objectid,9);
					    if(themeInsertStatus>0)
					    	saveThemeIdStatus=true;
					}else{
						 int themeInsertStatus=templateModuleDAO.saveUpdateThemeId(objectid,1);
						if(themeInsertStatus>0)
					    	saveThemeIdStatus=true;
					}
			}
				
		}*/
		
		LOGGER.debug("Template overall Status");
		LOGGER.debug("Vorlage Data status "+saveFlag);
		LOGGER.debug("Voucher Data status "+voucherdataFlag);
		LOGGER.debug("Vorlage Arrangement status "+vorlageArrangementFlag);
		LOGGER.debug("Pictures Data status "+savePicturesFlag);
		LOGGER.debug("Save Desc Pictures status "+saveDescPicturesFlag);
		
		LOGGER.debug("Save Aminities status "+saveEminities);
		//LOGGER.debug("Save VoucherThemeid "+saveThemeIdStatus);
		
		if(tcErrorTypeList.size()>0){
			templateCreationRS.setAck("Failure");
			templateCreationRS.setErrors(TCErrors);
			
		}else{
			templateModuleDAO.saveVorlageSource(vorlageId,sourceid);
			templateCreationRS.setAck("Success");
		    templateCreationRS.setTemplateId(vorlageId);
		}
		/*try{
		 DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         String startDate =timestamp.toString();
         Date frmDate = sdf.parse(startDate);
         DateFormat sdff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         String frmDateStr = sdff.format(frmDate);
         templateCreationRS.setTimeStamp(frmDateStr);
		}catch(Exception e){
			e.printStackTrace();
		}*/
		
	
		return templateCreationRS;
		
	}
	
	/**
	 * This method used to validate the values with regular expressions
	 * 
	 * @param regx,expression
	 * @return true/false
	 */
	/*public boolean valiateRegx(String expression, String regx) {
		Pattern p = null;
		Matcher m = null;
		p = Pattern.compile(regx);
		m = p.matcher(expression);
		if (!m.matches()) {
			LOGGER.error("Invalid time stamp", expression);
			return false;
		}
		return true;
	}*/

}
