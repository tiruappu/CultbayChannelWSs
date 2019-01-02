package com.cultuzz.channel.helper.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cultuzz.channel.DAO.OfferCreationDAO;
import com.cultuzz.channel.DAO.TemplateDetailsDAO;
import com.cultuzz.channel.DAO.TemplateModuleDAO;
import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.XMLpojo.CategoriesType;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.XMLpojo.NameValueListType;
import com.cultuzz.channel.XMLpojo.OfferReviseRQ;
import com.cultuzz.channel.XMLpojo.PrimaryCategoryDetailsType;
import com.cultuzz.channel.XMLpojo.PrimaryItemSpecificsType;
import com.cultuzz.channel.XMLpojo.PropertiesType;
import com.cultuzz.channel.XMLpojo.SecondaryCategoryDetailsType;
import com.cultuzz.channel.XMLpojo.SecondaryItemSpecificsType;
import com.cultuzz.channel.XMLpojo.TemplateEditRQ;
import com.cultuzz.channel.XMLpojo.TemplateEditRS;
import com.cultuzz.channel.helper.TemplateEditHelper;
import com.cultuzz.channel.marshalling.MarshallingJAXB;
import com.cultuzz.channel.util.CommonValidations;

@Repository
public class TemplateEditHelperImpl implements TemplateEditHelper{
	
	@Autowired
	TemplateModuleDAO templateModuleDAO;
	
	@Autowired
	CommonValidations commonValidations;
	
	@Autowired
	GetErrorMessagesDAOImpl getErrormessages;
	
	@Autowired
	TemplateDetailsDAO templateDetailsDAO;
	
	@Autowired
	OfferCreationDAO offerCreationDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(TemplateEditHelperImpl.class);
	
	int amenitiesCount=0;
	int shopObjectid=0;
	List<String> allowedStrings=new ArrayList<String>();
	
	public TemplateEditRS validateTemplateEditRQ(TemplateEditRQ templateEditRQ){
		TemplateEditRS templateEditRS=new TemplateEditRS();
		templateEditRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		logger.debug("enter into TemplateEditHelperImpl for validation");
		ErrorsType error=new ErrorsType();
		List<ErrorType> errorsType=error.getError();
		int langid=0;
		boolean objectIdFlag=false;
		
		if(templateEditRQ.isSetAuthenticationCode()){
			
		boolean authCodeStatus=	commonValidations.checkAuthCode(templateEditRQ.getAuthenticationCode());
			if(!authCodeStatus){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1100);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,2));
				errorsType.add(errorType);
				templateEditRS.setErrors(error);
				templateEditRS.setAck("Failure");
				return templateEditRS;
			}
				
			
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1100);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,2));
			errorsType.add(errorType);
			templateEditRS.setErrors(error);
			templateEditRS.setAck("Failure");
			return templateEditRS;
			
		}
		
		if(templateEditRQ.isSetErrorLang()){
			
			langid=commonValidations.checkErrorLangCode(templateEditRQ.getErrorLang());
			
			if(langid>0){
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1106);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,2));
				errorsType.add(errorType);
				templateEditRS.setErrors(error);
				templateEditRS.setAck("Failure");
				return templateEditRS;
			}
			
		}else{				
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1106);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,2));
			errorsType.add(errorType);
			templateEditRS.setErrors(error);
			templateEditRS.setAck("Failure");
			return templateEditRS;
		}
		
		
		//List<String> TCErrorMassagesList=new ArrayList<String>();
		
		if(templateEditRQ.isSetTimeStamp()){
			
		boolean timestampStatus = commonValidations.checkTimeStamp(templateEditRQ.getTimeStamp());
		if(!timestampStatus){	
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1104);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1104,langid));
			errorsType.add(errorType);
			templateEditRS.setErrors(error);
			templateEditRS.setAck("Failure");
			return templateEditRS;
		}
		
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1105);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1105,langid));
			errorsType.add(errorType);
			templateEditRS.setErrors(error);
			templateEditRS.setAck("Failure");
			return templateEditRS;
		}
		
		if(templateEditRQ.isSetSourceId() ){
			if(!commonValidations.checkSourceId(templateEditRQ.getSourceId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1101);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langid));
				errorsType.add(errorType);
				templateEditRS.setErrors(error);
				templateEditRS.setAck("Failure");
				return templateEditRS;
			}
		
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1101);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langid));
			errorsType.add(errorType);
			templateEditRS.setErrors(error);
			templateEditRS.setAck("Failure");
			return templateEditRS;
			
		}
		
		if(templateEditRQ.isSetChannelId()){
		
			if(!commonValidations.checkChannelId(templateEditRQ.getChannelId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1102);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langid));
				errorsType.add(errorType);
				templateEditRS.setErrors(error);
				templateEditRS.setAck("Failure");
				return templateEditRS;
			}
			
		}else{
			
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1102);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langid));
			errorsType.add(errorType);
			templateEditRS.setErrors(error);
			templateEditRS.setAck("Failure");
			return templateEditRS;
			
		}
		
		
		
		if(templateEditRQ.isSetObjectId()){
			
			logger.debug("Checking objectid");
			if(!commonValidations.checkObjectId(templateEditRQ.getObjectId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1103);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
				errorsType.add(errorType);
				templateEditRS.setErrors(error);
				templateEditRS.setAck("Failure");
				return templateEditRS;
			}else
				objectIdFlag=true;
			
			
		}else{
			logger.debug(" Checking object id else block");
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1103);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
			errorsType.add(errorType);
			templateEditRS.setErrors(error);
			templateEditRS.setAck("Failure");
			return templateEditRS;
			
		}
		
		if(templateEditRQ.isSetTemplateId()){
			
			logger.debug("Checking Template id");
			if(!templateModuleDAO.checkTemplateId(templateEditRQ.getTemplateId(),templateEditRQ.getObjectId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2113);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2113,langid));
				errorsType.add(errorType);
				templateEditRS.setErrors(error);
				templateEditRS.setAck("Failure");
				return templateEditRS;
			}else
				objectIdFlag=true;
			
			
		}else{
			
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2113);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2113,langid));
			errorsType.add(errorType);
			templateEditRS.setErrors(error);
			templateEditRS.setAck("Failure");
			return templateEditRS;
			
		}
		
		
		int objectid=templateEditRQ.getObjectId();
		int templateid=templateEditRQ.getTemplateId();
		int ebaysiteid=Integer.parseInt(templateModuleDAO.getTemplateSiteid(templateid));
		
		logger.debug("Template Edit ===Templateid"+templateid+"=====Ebaysiteid"+ebaysiteid);
		
		/*if(templateEditRQ.isSetSiteId()){
			boolean siteidflag=false;
			logger.debug("Checking ebaysiteid");
				siteidflag=templateModuleDAO.checkSiteId(templateEditRQ.getSiteId());
				logger.debug("ebaysiteid result "+siteidflag);
				if(!siteidflag){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2101);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2101,langid));
					errorsType.add(errorType);
					
				}else
					ebaysiteid=templateEditRQ.getSiteId();
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2101);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2101,langid));
				errorsType.add(errorType);
				
			}*/
		
			int designTemplateId=0;
			
			logger.debug("Checking design templateid status=="+templateEditRQ.isSetDesignTemplate());
			if(templateEditRQ.isSetDesignTemplate()){
				logger.debug("Checking design templateid =={}",templateEditRQ.getDesignTemplate());
				if(!templateEditRQ.getDesignTemplate().trim().isEmpty()){
					
					try{
						designTemplateId=Integer.parseInt(templateEditRQ.getDesignTemplate());
				}catch(Exception e){
					logger.debug("Checking templateid is getting exception");
				}
				boolean templateflag=false;
				logger.debug("Checking templateid");
				if(designTemplateId>0){
				templateflag=templateModuleDAO.checkDesignTemplateId(Integer.parseInt(templateEditRQ.getDesignTemplate()));
				logger.debug("templateid status"+templateflag);
				if(!templateflag){
				
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2100);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2100,langid));
					errorsType.add(errorType);
					
				}
				}else{
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2100);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2100,langid));
					errorsType.add(errorType);
				}
				}else{
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2161);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2161,langid));
					errorsType.add(errorType);
					
				}
			}else
				designTemplateId=templateModuleDAO.getDesignTemplateId(templateid);
			
			boolean productIdStatus=false;
			logger.debug("this is product based testin");
			if(templateEditRQ.isSetProductId() && templateEditRQ.isSetObjectId()){
				
				if(!templateEditRQ.getProductId().trim().isEmpty()){
				logger.debug("Checking productid"+templateEditRQ.getProductId());
			
				
				try{
					int productId=0;
					productId=Integer.parseInt(templateEditRQ.getProductId());
					
					if(productId>0)
					productIdStatus=templateModuleDAO.checkproductId(templateEditRQ.getObjectId(),productId);
					logger.debug("Checking productid status"+productIdStatus);
						}catch(Exception e){
							logger.debug("Checking productid is getting Exception");
						}
				logger.debug("Checking productid status"+productIdStatus);
				if(!productIdStatus){
					
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2115);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2115,langid));
					errorsType.add(errorType);
				}else if(productIdStatus){
					
				boolean liveCheck=templateModuleDAO.checkLiveOffers(templateEditRQ.getTemplateId());
				if(liveCheck){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2189);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2189,langid));
					errorsType.add(errorType);
				}
				}
				}else{
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2115);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2115,langid));
					errorsType.add(errorType);
				}
			}
			
			if(templateEditRQ.isSetVoucherValidity()){
				logger.debug("Checking vouchervalidity"+templateEditRQ.getVoucherValidity());
				if(!templateEditRQ.getVoucherValidity().trim().isEmpty()){
					int voucherValidity=0;
					try{
					 voucherValidity=Integer.parseInt(templateEditRQ.getVoucherValidity());
					}catch(Exception e){
						logger.debug("This is exception at voucher validity text");
					}
					
				int i=0;
				switch(voucherValidity){
				
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
					
			}
			
			CategoriesType categoriesType=null;
			boolean categoriesTagStatus=false;
			
			if(templateEditRQ.isSetCategories()){
			categoriesType=templateEditRQ.getCategories();
			categoriesTagStatus=true;
			logger.debug("Categories=="+templateEditRQ.isSetCategories());
			}else{
				/*ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2141);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2141,langid));
				errorsType.add(errorType);*/
			}
			
			PrimaryCategoryDetailsType primaryCategory=null;
			
			boolean primaryCategoryDetailsStatus=false;
			
			if(categoriesTagStatus && categoriesType.isSetPrimaryCategoryDetails()){	
				logger.debug("Primary Categories Details=="+categoriesType.isSetPrimaryCategoryDetails());
			primaryCategory=categoriesType.getPrimaryCategoryDetails();
			}
				
				
			if(primaryCategory!=null){
				primaryCategoryDetailsStatus=true;
			}
			
			if(categoriesTagStatus && primaryCategoryDetailsStatus){
			logger.debug("Primary Category id"+primaryCategory.isSetPrimaryCategoryId() +"===="+primaryCategory.getPrimaryCategoryId());
			if(primaryCategory.isSetPrimaryCategoryId()){
				int primaryCategoryId=0;
				
				try{
					primaryCategoryId=Integer.parseInt(primaryCategory.getPrimaryCategoryId());
				}catch(NumberFormatException n){
					logger.debug("Problem inside primary cat id");
				}
				
				if(primaryCategoryId>0){
				
				int fenameErrorsCount=0;
				logger.debug("Checking primarycategoryid");
				boolean primaryCategoryIdflag=false;
				try{
				primaryCategoryIdflag=templateModuleDAO.checkPrimaryCategory(Integer.parseInt(primaryCategory.getPrimaryCategoryId()),objectid,ebaysiteid);
				}catch(NumberFormatException n){
					logger.debug("Exception occured inside primary category checking");
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
				errorType.setErrorCode(2112);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2102,langid));
				errorsType.add(errorType);
				
			}
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2163);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2163,langid));
				errorsType.add(errorType);
			}
			
		}
			
			
			SecondaryCategoryDetailsType secondaryCategory=null;
			
			if(categoriesTagStatus && categoriesType.isSetSecondaryCategoryDetails())
			 secondaryCategory=categoriesType.getSecondaryCategoryDetails();
			if(secondaryCategory!=null){
			if(secondaryCategory.isSetSecondaryCategoryId() ){
				int secondaryCatId=0;
				try {
					secondaryCatId=Integer.parseInt(secondaryCategory.getSecondaryCategoryId());
				} catch (NumberFormatException e) {
					// TODO: handle exception
				}
				
				
				if(secondaryCatId>0){
				logger.debug("Checking secondaryCategoryid");
				boolean secondaryCateoryIdflag=false;
				int senameserrorCount=0;
				
				secondaryCateoryIdflag=templateModuleDAO.checkSecondaryCategory(Integer.parseInt(secondaryCategory.getSecondaryCategoryId()),objectid,ebaysiteid);
				
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
				
			//StoreCategoriesType storeCategoriesType=templateEditRQ.getStoreCategories();
		
			//not handle for store categories	
			/*if(storeCategoriesType.isSetStoreCategoryId()){
				boolean storePrimaryCategoryIdflag=false;
				logger.debug("Checking store primarycategoryid");
				storePrimaryCategoryIdflag=templateModuleDAO.checkPrimaryStoreCategoryId(storeCategoriesType.getStoreCategoryId());
				if(!storePrimaryCategoryIdflag)
					TCErrorMassagesList.add("Invalid PrimaryStoreCategory");
				
			}
			
			if(storeCategoriesType.isSetStoreCategory2Id()){
				boolean storeSecondaryCategoryIdflag=false;
				logger.debug("Checking store secondary categoryid");
				storeSecondaryCategoryIdflag=templateModuleDAO.checkSecondaryStoreCategoryId(storeCategoriesType.getStoreCategory2Id());
				
				if(!storeSecondaryCategoryIdflag)
					TCErrorMassagesList.add("Invalid SecondaryStoreCategory");
				
				
			}*/
			if(templateEditRQ.isSetProperties()){
				
			PropertiesType properties=templateEditRQ.getProperties();
			
			//if(!productIdStatus){
			
			if(properties.isSetCateringType()){
				logger.debug("inside checking catering type room");
				boolean cateringFlag=false;
				if(!properties.getCateringType().trim().isEmpty()){
				try{
				cateringFlag=templateModuleDAO.checkCateringType(Integer.parseInt(properties.getCateringType()));
				}catch(NumberFormatException n){
					logger.debug("This is number format exception in type of catering");
				}
				if(!cateringFlag){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2106);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2106,langid));
					errorsType.add(errorType);
				
				}
				}else{
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2168);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2168,langid));
					errorsType.add(errorType);
				}
			}else{
				/*ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2106);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2106,langid));
				errorsType.add(errorType);*/
			}
		
			if(properties.isSetTypeOfRoom()){
				logger.debug("inside checking type of room");
				boolean typeOfRoomFlag=false;
				if(properties.getTypeOfRoom()!=null && !properties.getTypeOfRoom().trim().isEmpty()){
				try{
				typeOfRoomFlag=templateModuleDAO.checkTypeOfRoom(Integer.parseInt(properties.getTypeOfRoom()));
				}catch(NumberFormatException n){
					logger.debug("This is exception of type of room");
				}
				if(!typeOfRoomFlag){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2107);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2107,langid));
					errorsType.add(errorType);
				}
				}else{
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2169);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2169,langid));
					errorsType.add(errorType);
					
				}
				
				}else{
					/*ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2107);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2107,langid));
					errorsType.add(errorType);*/
				}
			
			if(properties.isSetNights()){
				
				if(properties.getNights()!=null && !properties.getNights().trim().isEmpty()){
					int nights=0;
					try{
						nights=Integer.parseInt(properties.getNights());
					}catch(NumberFormatException n){
						logger.debug("Exception occured in no of nights");
					}
					if(nights<=0){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2116);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2116,langid));
				errorsType.add(errorType);
					}
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2170);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2170,langid));
				errorsType.add(errorType);
			}
			}
			
			if(properties.isSetPersons()){
				if(properties.getPersons()!=null && !properties.getPersons().trim().isEmpty()){
				
					int persons=0;
					try{
						persons=Integer.parseInt(properties.getPersons());
					}catch(NumberFormatException n){
						logger.debug("This is problem occured in no of persons");
					}
					if(persons<=0){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2117);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2117,langid));
					errorsType.add(errorType);
					}
				}else{
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2171);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2171,langid));
					errorsType.add(errorType);
				}
			}
			//}
			
			if(!properties.isSetCateringType() && !properties.isSetNights() && !properties.isSetPersons() && !properties.isSetTypeOfRoom()){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2154);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2154,langid));
				errorsType.add(errorType);
			}
			}
			
			
			
			if(templateEditRQ.isSetTemplateName() && templateEditRQ.getTemplateName().isEmpty()){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2138);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2138,langid));
				errorsType.add(errorType);
			}else if(templateEditRQ.isSetTemplateName() && templateEditRQ.getTemplateName().trim().length()>80){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2132);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2132,langid));
				errorsType.add(errorType);
			}
			
			if(templateEditRQ.isSetOfferTitle() && templateEditRQ.getOfferTitle().isEmpty()){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2105);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2105,langid));
				errorsType.add(errorType);
			}else if(templateEditRQ.isSetOfferTitle() && templateEditRQ.getOfferTitle().trim().length()>80){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2133);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2133,langid));
				errorsType.add(errorType);	
			}
			
			if(templateEditRQ.isSetOfferSubTitle() && templateEditRQ.getOfferSubTitle().isEmpty()){
				
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2153);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2153,langid));
				errorsType.add(errorType);
				
			}
			if(templateEditRQ.isSetOfferSubTitle() && templateEditRQ.getOfferSubTitle().trim().length()>80){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2134);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2134,langid));
				errorsType.add(errorType);	
			}
			
		/*	int listingtypeChecking =0;
			int listingtypeValidation=0;
			if(templateEditRQ.isSetListingType() && templateEditRQ.getListingType().isEmpty()){
				logger.debug("Listing type problem");
				listingtypeChecking++;
				listingtypeValidation++;
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(3117);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(3117,langid));
				errorsType.add(errorType);
			}*/
			
			
			
			
			/*if(templateEditRQ.isSetListingType() && !templateEditRQ.getListingType().trim().equals("Fixed Price Offer")){
				//listingtypeChecking++;
				if(!templateEditRQ.getListingType().trim().equals("Store Fixed Price Offer")){
					if(!templateEditRQ.getListingType().trim().equals("Auction")){
				logger.debug("Listing type problem fixed price offer");
				if(listingtypeChecking==0){
					listingtypeValidation++;
					listingtypeValidation++;
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2144);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2144,langid));
				errorsType.add(errorType);
				}
					}
				}
			}else if(templateEditRQ.isSetListingType() && !templateEditRQ.getListingType().trim().equals("Store Fixed Price Offer")){
				//listingtypeChecking++;
				logger.debug("Listing type problem Store fixed price offer");
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2144);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2144,langid));
				errorsType.add(errorType);
			}else if(templateEditRQ.isSetListingType() && !templateEditRQ.getListingType().trim().equals("Auction")){
				//listingtypeChecking++;
				logger.debug("Listing type problem Action");
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2144);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2144,langid));
				errorsType.add(errorType);
			}*/
				/*if(listingtypeChecking==0){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2144);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2144,langid));
					errorsType.add(errorType);
				}*/
				
			int auctionmastertypeid=templateModuleDAO.getAuctionmasterTypeid(templateid);
			double startprice=templateModuleDAO.getStartPrice(templateid);
			double ebaysofortkauf=templateModuleDAO.getEbaysofortkauf(templateid);
			double retailPrice=templateModuleDAO.getRetailPrice(templateid);
			
			String oldListingType=null;
			
			if(auctionmastertypeid==4 && startprice>0 && ebaysofortkauf==0){
				oldListingType="Store Fixed Price Offer";
			}else if(auctionmastertypeid==1 && startprice>0 && ebaysofortkauf==0){
				oldListingType="Auction";
			}else if(auctionmastertypeid==1 && startprice==0 && ebaysofortkauf>0){
				oldListingType="Fixed Price Offer";
			}
			boolean shopObjStatus=false;
			//int oldShopObjectid=templateModuleDAO.getShopObjectId(templateid);
			/*boolean shopObjStatus=false;
			boolean shopValidations=false;
			
			boolean shopRemoveValidate=false;
			
			if(templateEditRQ.isSetListingType() && listingtypeValidation==0 && templateEditRQ.getListingType().equals("Store Fixed Price Offer")){
				//here should be check categories validations
				shopValidations=true;
				
			}else{
				//System.out.println(templateEditRQ.isSetListingType()+ "====="+oldListingType.equals("Store Fixed Price Offer"));
				if(!templateEditRQ.isSetListingType() && oldListingType.equals("Store Fixed Price Offer")){
					
					if(!templateEditRQ.isSetShopObjectId())
						shopObjectid=templateModuleDAO.getShopObjectId(templateid);
					
					shopValidations=true;
					
				}else{
					
					shopRemoveValidate=true;
				
				}
			}*/
			
			
			
			/*if(shopRemoveValidate){

				if(templateEditRQ.isSetShopObjectId()){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2178);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2178,langid));
					errorsType.add(errorType);
				}
				
				if(templateEditRQ.isSetStoreCategories()){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2179);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2179,langid));
					errorsType.add(errorType);
				}
			}*/
			
			
			
			//if(shopValidations){
				
				if(templateEditRQ.isSetShopObjectId() && templateEditRQ.getShopObjectId().isEmpty()){
					logger.debug("Checking shop objectid checking");
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2185);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2185,langid));
					errorsType.add(errorType);
					
					
				}else if(templateEditRQ.isSetShopObjectId()){
						
						try{
							shopObjectid=Integer.parseInt(templateEditRQ.getShopObjectId());
						}catch(Exception e){
							logger.debug("this is shop objectid invalid");
							e.printStackTrace();
						}
					
					
						if(shopObjectid>0)
					 	shopObjStatus=offerCreationDAO.checkShopObjectId(objectid, shopObjectid);
						else
							shopObjStatus=false;
					 
					if(!shopObjStatus){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2176);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2176,langid));
						errorsType.add(errorType);
					}
				}else if(templateEditRQ.isSetStoreCategories() ){
					logger.debug("Checking shop objectid store categories are setted*****");
					if(templateEditRQ.getStoreCategories().isSetStoreCategoryId() || templateEditRQ.getStoreCategories().isSetStoreCategory2Id()){
						logger.debug("Checking shop objectid getting=============");
						shopObjectid=templateModuleDAO.getShopObjectId(templateid);
						if(shopObjectid==0){
							shopObjStatus=false;
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2177);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2177,langid));
						errorsType.add(errorType);
						}else
							shopObjStatus=true;
					}
					/*if(templateEditRQ.isSetListingType() && templateEditRQ.getListingType().equals("Store Fixed Price Offer")){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2177);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2177,langid));
					errorsType.add(errorType);
					}*/
				}
				
				boolean storePrimaryCategoryIdflag=false;
				boolean storeSecondaryCategoryIdflag=false;
					if(templateEditRQ.isSetStoreCategories() && shopObjStatus){
						
						if(!templateEditRQ.getStoreCategories().isSetStoreCategoryId() && ! templateEditRQ.getStoreCategories().isSetStoreCategory2Id()){
							ErrorType errorType=new ErrorType();
							errorType.setErrorCode(2184);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(2184,langid));
							errorsType.add(errorType);
						}else{
							
							if(templateEditRQ.getStoreCategories().isSetStoreCategoryId() && templateEditRQ.getStoreCategories().getStoreCategoryId().isEmpty()){
								ErrorType errorType=new ErrorType();
								errorType.setErrorCode(2181);
								errorType.setErrorMessage(getErrormessages.getErrorMessage(2181,langid));
								errorsType.add(errorType);
							}else if(templateEditRQ.getStoreCategories().isSetStoreCategoryId()){
								long shopcatid=0;
								try {
									shopcatid=Long.parseLong(templateEditRQ.getStoreCategories().getStoreCategoryId());
								} catch (NumberFormatException nfe) {
									// TODO: handle exception
									nfe.printStackTrace();
									logger.debug("Invalid store categories");
								}
								logger.debug("Checking shop objectid checking============="+shopObjectid);
								if(shopcatid>0 && shopcatid!=1 && shopObjectid>0)
									storePrimaryCategoryIdflag=templateModuleDAO.checkStoreCategoryId(shopcatid,shopObjectid);
									else if(shopcatid==1)
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
							
							
							if(templateEditRQ.getStoreCategories().isSetStoreCategory2Id() && templateEditRQ.getStoreCategories().getStoreCategory2Id().isEmpty()){
								ErrorType errorType=new ErrorType();
								errorType.setErrorCode(2183);
								errorType.setErrorMessage(getErrormessages.getErrorMessage(2183,langid));
								errorsType.add(errorType);
							}else if(templateEditRQ.getStoreCategories().isSetStoreCategory2Id()){
								long shopcatid2=0;
								try {
									shopcatid2=Long.parseLong(templateEditRQ.getStoreCategories().getStoreCategory2Id());
								} catch (NumberFormatException nfe) {
									// TODO: handle exception
									nfe.printStackTrace();
									logger.debug("Invalid store categories");
								}
								
								if(shopcatid2>0 && shopcatid2!=1 && shopObjectid>0)
									storeSecondaryCategoryIdflag=templateModuleDAO.checkStoreCategoryId(shopcatid2,shopObjectid);
									else if(shopcatid2==1)
										storeSecondaryCategoryIdflag=true;
									else
										storeSecondaryCategoryIdflag=false;
									
									if(!storeSecondaryCategoryIdflag){	
									ErrorType errorType=new ErrorType();
									errorType.setErrorCode(2175);
									errorType.setErrorMessage(getErrormessages.getErrorMessage(2175,langid));
									errorsType.add(errorType);
									}
								
							}						
							
							
						}
						
						if(storePrimaryCategoryIdflag && storeSecondaryCategoryIdflag && templateEditRQ.getStoreCategories().isSetStoreCategoryId() && templateEditRQ.getStoreCategories().isSetStoreCategory2Id()){
							
							if(templateEditRQ.getStoreCategories().getStoreCategoryId().trim().equals(templateEditRQ.getStoreCategories().getStoreCategory2Id().trim())){
								ErrorType errorType=new ErrorType();
								errorType.setErrorCode(2182);
								errorType.setErrorMessage(getErrormessages.getErrorMessage(2182,langid));
								errorsType.add(errorType);
							}
						}
						
						
					}else{
						
						if(templateEditRQ.isSetShopObjectId()){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2180);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2180,langid));
						errorsType.add(errorType);
						}
					}
			//}
			
			
				if(templateEditRQ.isSetPictureDetails()){
					
					if(templateEditRQ.getPictureDetails().isSetGalleryURL() && templateEditRQ.getPictureDetails().getGalleryURL().trim().isEmpty()){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2108);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2108,langid));
						errorsType.add(errorType);
					
					}
					
					/*if(templateEditRQ.getPictureDetails().isSetVoucherPictureURL() && templateEditRQ.getPictureDetails().getVoucherPictureURL().trim().isEmpty()){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2186);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2186,langid));
						errorsType.add(errorType);
					
					}*/
					
					int count=0;
					if(templateEditRQ.getPictureDetails().getPictureURL().size()>0){
						
						/*for(String itemPics:templateEditRQ.getPictureDetails().getPictureURL()){
							
							if(itemPics.isEmpty()){
								count++;
							}
						}
						
						if(count>0){
							ErrorType errorType=new ErrorType();
							errorType.setErrorCode(2151);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(2151,langid));
							errorsType.add(errorType);
						}*/
					}
					
					if(!templateEditRQ.getPictureDetails().isSetGalleryURL() && !templateEditRQ.getPictureDetails().isSetPictureURL()){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2152);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2152,langid));
						errorsType.add(errorType);
					}
					
				}else{
					/*ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2118);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2118,langid));
					errorsType.add(errorType);*/
				
				}
				
				if(templateEditRQ.isSetDescription()){
					
					if(templateEditRQ.getDescription().isSetTitle() && templateEditRQ.getDescription().getTitle().trim().isEmpty()){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2109);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2109,langid));
						errorsType.add(errorType);
					}else if(templateEditRQ.getDescription().isSetTitle() && templateEditRQ.getDescription().getTitle().length()>80){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2135);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2135,langid));
						errorsType.add(errorType);
					}
					
					if(templateEditRQ.getDescription().isSetSubTitle() && templateEditRQ.getDescription().getSubTitle().trim().isEmpty()){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2147);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2147,langid));
						errorsType.add(errorType);
					}else if(templateEditRQ.getDescription().isSetSubTitle() && templateEditRQ.getDescription().getSubTitle().length()>80){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2172);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2172,langid));
						errorsType.add(errorType);
					}
					
					if(templateEditRQ.getDescription().isSetOfferText() && templateEditRQ.getDescription().getOfferText().isEmpty() ){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2110);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2110,langid));
						errorsType.add(errorType);
					
					}
					
					if(templateEditRQ.getDescription().isSetOfferAdditionalText() && templateEditRQ.getDescription().getOfferAdditionalText().isEmpty() ){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2146);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2146,langid));
						errorsType.add(errorType);
					
					}
					
					if(templateEditRQ.getDescription().isSetVoucherText() && templateEditRQ.getDescription().getVoucherText().isEmpty()){
						//this checking for it is exist or not
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2111);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2111,langid));
						errorsType.add(errorType);
					}else if(templateEditRQ.getDescription().isSetVoucherText() && templateEditRQ.getDescription().getVoucherText().trim().length()>932){
						//this is checking for length of voucher text
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2136);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2136,langid));
						errorsType.add(errorType);
					}
					
					 if(templateEditRQ.getDescription().isSetVoucherValidityText() && templateEditRQ.getDescription().getVoucherValidityText().trim().isEmpty()){
							//this is checking for length of voucher text
							ErrorType errorType=new ErrorType();
							errorType.setErrorCode(2145);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(2145,langid));
							errorsType.add(errorType);
						}else if(templateEditRQ.getDescription().isSetVoucherValidityText() && templateEditRQ.getDescription().getVoucherValidityText().trim().length()>450){
							ErrorType errorType=new ErrorType();
							errorType.setErrorCode(2137);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(2137,langid));
							errorsType.add(errorType);
						}
					
					 if(!templateEditRQ.getDescription().isSetTitle() && !templateEditRQ.getDescription().isSetOfferText() && !templateEditRQ.getDescription().isSetSubTitle() && !templateEditRQ.getDescription().isSetTitle() &&!templateEditRQ.getDescription().isSetVoucherText() && !templateEditRQ.getDescription().isSetVoucherValidityText()){
						 ErrorType errorType=new ErrorType();
							errorType.setErrorCode(2149);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(2149,langid));
							errorsType.add(errorType);
					 }
					
				}else{
					/*ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2119);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2119,langid));
					errorsType.add(errorType);
				*/
				}
				
				if(templateEditRQ.isSetDescriptionPictures()){
					
					logger.debug("This is TEdit description pictures");
					
					/*if(templateEditRQ.getDescriptionPictures().isSetLogoPictureURL() && templateEditRQ.getDescriptionPictures().getLogoPictureURL().trim().isEmpty()){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2162);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2162,langid));
						errorsType.add(errorType);
					
					}*/
					
					logger.debug("Design Template id id "+templateEditRQ.getDesignTemplate());
					
					if(templateEditRQ.getDescriptionPictures().isSetDescriptionPictureURL() && templateEditRQ.getDescriptionPictures().getDescriptionPictureURL().size()>0){
						
						if(designTemplateId!=0){
							int descPicsCount=templateModuleDAO.getDescImagesCount(designTemplateId);
							if(descPicsCount>0){
						if(templateEditRQ.getDescriptionPictures().getDescriptionPictureURL().size()!=descPicsCount){
							ErrorType errorType=new ErrorType();
							errorType.setErrorCode(2122);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(2122,langid)+" "+descPicsCount);
							errorsType.add(errorType);
						}
						}
							
						}
						int counter=0;
						for(String picsCheck:templateEditRQ.getDescriptionPictures().getDescriptionPictureURL()){
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
						
					}else{
						/*if(templateModuleDAO.getDescImagesCount(designTemplateId)>0){
							ErrorType errorType=new ErrorType();
							errorType.setErrorCode(2125);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(2125,langid));
							errorsType.add(errorType);
						}*/
						
					}
					
					if(templateEditRQ.getDescriptionPictures().isSetOfferSliderPictureURL()){
						
						int counter=0;
						for(String sliderpicUrl:templateEditRQ.getDescriptionPictures().getOfferSliderPictureURL()){
							
							if(sliderpicUrl.trim().isEmpty()){
								counter++;
							}
							
						}
						
						if(counter>0){
							ErrorType errorType=new ErrorType();
							errorType.setErrorCode(2158);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(2158,langid));
							errorsType.add(errorType);
						}
												
						
						if(templateEditRQ.getDescriptionPictures().getOfferSliderPictureURL().size()<2){
							ErrorType errorType=new ErrorType();
							errorType.setErrorCode(2123);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(2123,langid));
							errorsType.add(errorType);
						}
						
					}
					
					/*if(templateEditRQ.getDescriptionPictures().isSetObjectSliderPictureURL()){
						
						int counter=0;
						for(String sliderpicUrl:templateEditRQ.getDescriptionPictures().getObjectSliderPictureURL()){
							
							if(sliderpicUrl.trim().isEmpty()){
								counter++;
							}
							
						}
						
						if(counter>0){
							ErrorType errorType=new ErrorType();
							errorType.setErrorCode(2159);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(2159,langid));
							errorsType.add(errorType);
						}
						
						if(templateEditRQ.getDescriptionPictures().getObjectSliderPictureURL().size()<2){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2124);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2124,langid));
						errorsType.add(errorType);
						}
					}*/
					
					if(!templateEditRQ.getDescriptionPictures().isSetDescriptionPictureURL() && !templateEditRQ.getDescriptionPictures().isSetOfferSliderPictureURL()){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2148);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2148,langid));
						errorsType.add(errorType);
					}
					
				}else{
					/*ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2120);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2120,langid));
					errorsType.add(errorType);*/
				}
				
				if(templateEditRQ.isSetAdditionalOptions()){
					List<String> listAdditionalOptions=templateEditRQ.getAdditionalOptions().getOptionValue();
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
						}catch(NumberFormatException n){
							logger.debug("This is problem in additional options");
							i++;
						}
					}
					
					if(i>0){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2114);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2114,langid));
						errorsType.add(errorType);
					}
					
					if(templateEditRQ.getAdditionalOptions().getOptionValue().size()==0){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2114);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2114,langid));
						errorsType.add(errorType);
					}
					
				}
				
				if(templateEditRQ.isSetAmenities()){
					int emenityCount=0;
					if(templateEditRQ.getAmenities().getAmenityId().size()>0){
						emenityCount++;
						int amenitycounter=0;
						int amenityEmpty=0;
						for(String amenityId:templateEditRQ.getAmenities().getAmenityId()){
							
							if(!amenityId.trim().isEmpty()){
							
							boolean amenityStatus=false;
							try{
								amenityStatus=templateModuleDAO.checkAmenityId( Integer.parseInt(amenityId));
							}catch(NumberFormatException n){
								logger.debug("Problem occured in emenities are empty");
							}
						if(!amenityStatus){
							amenitycounter++;
						}else
							amenitiesCount++;
						}else
							amenityEmpty++;
						
						}
						
						if(amenityEmpty>0){
							ErrorType errorType=new ErrorType();
							errorType.setErrorCode(2173);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(2173,langid));
							errorsType.add(errorType);
						}
						
						if(amenitycounter>0){
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
				
				int pricecounter=0;
				int currencycounter=0;
				if(templateEditRQ.isSetPrice()){
					
					double price=0;
					try {
						price=Double.parseDouble(templateEditRQ.getPrice());
						logger.debug("Price value is "+price);
					} catch (NumberFormatException e) {
						// TODO: handle exception
						
						logger.debug("price value is problem");
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2131);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2131,langid));
						errorsType.add(errorType);
					}
					
					if(price>0){
						
						if(!templateEditRQ.isSetCurrency()){
							pricecounter++;
							ErrorType errorType=new ErrorType();
							errorType.setErrorCode(2130);
							errorType.setErrorMessage(getErrormessages.getErrorMessage(2130,langid));
							errorsType.add(errorType);							
						}
						
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
						
					}else if(price==0){
						//currencycounter++;
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
					
					
				}
				
				
			
				try{
				if(!(errorsType.size()>0)){
				if(templateEditRQ.isSetRemoveFields()){
				if(null!= templateEditRQ.getRemoveFields()  && templateEditRQ.getRemoveFields().isSetRemoveField() ){
				if( null!=templateEditRQ.getRemoveFields().getRemoveField() && templateEditRQ.getRemoveFields().getRemoveField().size()>0){
					allowedStrings=	templateEditRQ.getRemoveFields().getRemoveField();
					if(validateRemoveField(templateEditRQ)){
					if(!(allowedStrings.contains("RetailPrice") || allowedStrings.contains("SecondaryCategoryId") || allowedStrings.contains("StoreCategory2Id") || allowedStrings.contains("OfferSubTitle"))){
							logger.debug("Remove Field is invalid"+templateEditRQ.getRetailPrice());
							ErrorType retailError = new ErrorType();
							retailError.setErrorCode(2192);
								retailError.setErrorMessage(getErrormessages.getErrorMessage(2192, langid));
								errorsType.add(retailError);
						}else{
							if(allowedStrings.contains("RetailPrice") && templateEditRQ.isSetRetailPrice()){
								ErrorType retailError = new ErrorType();
								retailError.setErrorCode(2193);
									retailError.setErrorMessage(getErrormessages.getErrorMessage(2193, langid));
									errorsType.add(retailError);
							}else if(allowedStrings.contains("SecondaryCategoryId") && templateEditRQ.isSetCategories()  && templateEditRQ.getCategories().isSetSecondaryCategoryDetails()){
								ErrorType retailError = new ErrorType();
								retailError.setErrorCode(2194);
									retailError.setErrorMessage(getErrormessages.getErrorMessage(2194, langid));
									errorsType.add(retailError);
							}else if(allowedStrings.contains("StoreCategory2Id")&& templateEditRQ.isSetStoreCategories() && templateEditRQ.getStoreCategories().isSetStoreCategory2Id()){
								ErrorType retailError = new ErrorType();
								retailError.setErrorCode(2195);
									retailError.setErrorMessage(getErrormessages.getErrorMessage(2195, langid));
									errorsType.add(retailError);
							}else if(allowedStrings.contains("OfferSubTitle") && templateEditRQ.isSetOfferSubTitle()){
								ErrorType retailError = new ErrorType();
								retailError.setErrorCode(2196);
									retailError.setErrorMessage(getErrormessages.getErrorMessage(2196, langid));
									errorsType.add(retailError);
							}
						}
					
					}else{
						ErrorType retailError = new ErrorType();
						retailError.setErrorCode(2192);
							retailError.setErrorMessage(getErrormessages.getErrorMessage(2192, langid));
							errorsType.add(retailError);
					}
					}
					}else{
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2191);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2191,langid));
						errorsType.add(errorType);
					}
					
				}
				}
				}catch(Exception e){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(2197);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(2197,langid));
					errorsType.add(errorType);
				}

				
				logger.debug("Outside the RetailsPrice"+templateEditRQ.getRetailPrice());
				if(templateEditRQ.isSetRetailPrice()){
				if(!templateEditRQ.getRetailPrice().isEmpty()){
					if(!templateEditRQ.isSetCurrency()){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(2130);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(2130,langid));
						errorsType.add(errorType);							
					}else{
						if(templateEditRQ.isSetCurrency()){
							String cct=templateEditRQ.getCurrency();					
							logger.debug("This is currency code=="+cct);									
						boolean currencyStatus =templateModuleDAO.checkCurrency(ebaysiteid,cct.toString());					
							if(!currencyStatus){		
								ErrorType errorType=new ErrorType();
								errorType.setErrorCode(2130);
								errorType.setErrorMessage(getErrormessages.getErrorMessage(2130,langid));
								errorsType.add(errorType);
							}					
						}
					}
					logger.debug("Retail Price is not empty"+templateEditRQ.getRetailPrice());
				if(templateEditRQ.isSetRetailPrice() && templateEditRQ.getRetailPrice()!=null){
					logger.debug("Inside the RetailsPrice"+templateEditRQ.getRetailPrice());
					if(templateEditRQ.getRetailPrice().contains(".")){
						logger.debug("Retail Price contains decimals"+templateEditRQ.getRetailPrice());
						if(templateEditRQ.getRetailPrice().split("\\.")[1].length()>2){
							logger.debug("Retail Price contains more than 2 precisions"+templateEditRQ.getRetailPrice());
	  	    				 ErrorType retailError = new ErrorType();
	  	    				retailError.setErrorCode(2190);	  		  					
	  	    				retailError.setErrorMessage(getErrormessages.getErrorMessage(2190, langid));
	  	    				errorsType.add(retailError);
	  	    			}
					}
				}else{
					logger.debug("Retail Price is invalid"+templateEditRQ.getRetailPrice());

					ErrorType retailError = new ErrorType();
					retailError.setErrorCode(1113);
						retailError.setErrorMessage(getErrormessages.getErrorMessage(1113, langid));
					errorsType.add(retailError);
				}
				
				double price=0;
					if(!(errorsType.size()>0)){
						logger.debug("No Errors are set for Price"+templateEditRQ.getRetailPrice());
						price=commonValidations.checkPriceExists(templateEditRQ.getTemplateId());
						if(templateEditRQ.isSetPrice()){
						if(!commonValidations.checkRetailPrice(Double.parseDouble(templateEditRQ.getPrice()), Double.parseDouble(templateEditRQ.getRetailPrice()))){
							logger.debug("Retail Price is less than Price"+templateEditRQ.getRetailPrice());
							ErrorType retailError = new ErrorType();
		    				retailError.setErrorCode(1111);
			  				retailError.setErrorMessage(getErrormessages.getErrorMessage(1111, langid));
		    				errorsType.add(retailError);
						}
						}else{
						
					if(!commonValidations.checkRetailPrice(price, Double.parseDouble(templateEditRQ.getRetailPrice()))){
						logger.debug("Retail Price is less than Price"+templateEditRQ.getRetailPrice());
						ErrorType retailError = new ErrorType();
						retailError.setErrorCode(1111);
		  				retailError.setErrorMessage(getErrormessages.getErrorMessage(1111, langid));
	    				errorsType.add(retailError);
					}
					
						}
					}
				}else{
					logger.debug("Retail Price is empty"+templateEditRQ.getRetailPrice());
					ErrorType retailError = new ErrorType();
					retailError.setErrorCode(1113);
						retailError.setErrorMessage(getErrormessages.getErrorMessage(1113, langid));
					errorsType.add(retailError);
				}
	}else {
		double retailPriceValue=0;
		retailPriceValue=templateModuleDAO.getRetailPrice(templateid);
		if(templateEditRQ.isSetPrice()){
			if(!commonValidations.checkRetailPrice(Double.parseDouble(templateEditRQ.getPrice()), retailPriceValue)){
				logger.debug("Retail Price is less than Price"+templateEditRQ.getRetailPrice());
				ErrorType retailError = new ErrorType();
				retailError.setErrorCode(1111);
  				retailError.setErrorMessage(getErrormessages.getErrorMessage(1111, langid));
				errorsType.add(retailError);
			}
			}
	}
				
			if(errorsType.size()>0){
				templateEditRS.setAck("Failure");
				templateEditRS.setErrors(error);
			}else{
				templateEditRS.setAck("Success");
			}
		
		
		return templateEditRS;
	}
	
	
	
	public TemplateEditRS processTemplateEditRQ(TemplateEditRQ templateEditRQ){
		
		logger.debug("This is template Edit process");
		boolean  vorlageDataUpdateStatus=false;
		ErrorsType error=new ErrorsType();
		List<ErrorType> errorsType=error.getError();
		int langid=commonValidations.checkErrorLangCode(templateEditRQ.getErrorLang());;
		
		TemplateEditRS templateEditRS=new TemplateEditRS();
		templateEditRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		if(templateEditRQ.isSetTemplateId()){
		//	if(templateEditRQ.isSetDesignTemplate() || templateEditRQ.isSetProductId() || templateEditRQ.isSetOfferTitle() || templateEditRQ.isSetOfferSubTitle() || templateEditRQ.isSetAdditionalOptions() || templateEditRQ.isSetDescription() || templateEditRQ.isSetCategories() ){
			
			  vorlageDataUpdateStatus=	templateModuleDAO.updateVorlageData(templateEditRQ);
			logger.debug("This is vorlage data update status"+vorlageDataUpdateStatus);
			//}
		}
		
		if(templateEditRQ.isSetProperties()){
			//update
		boolean vorlageArrangementUpdateStatus=	templateModuleDAO.updateVorlageArrangement(templateEditRQ);
		
		logger.debug("This is vorlage arrangement data update status"+vorlageArrangementUpdateStatus);
		}
		
		
		
		if(templateEditRQ.isSetCategories()){
			
			if(templateEditRQ.getCategories().isSetPrimaryCategoryDetails()){
				
				//primary category details delete
				
				List<Map<String, Object>> itemSpecids=templateDetailsDAO.getItemSpecIds(templateEditRQ.getTemplateId());
				
				for(Map<String, Object> maps:itemSpecids){
					if(Integer.parseInt(maps.get("categoryLevel").toString())==1){
						
					List<Map<String, Object>> itemSpecName=templateDetailsDAO.getItemSpecNames(Integer.parseInt(maps.get("id").toString()));
				
					for(Map<String, Object> names:itemSpecName){
						//List<Map<String, Object>> itemSpecValues=templateDetailsDAO.getItemSpecValues(Integer.parseInt(names.get("id").toString()));
						
						boolean deleteSpecValues=	templateModuleDAO.deleteSpecValues(Integer.parseInt(names.get("id").toString()));
						logger.debug("P Delete spect Values"+deleteSpecValues);
						
					}
					
					boolean deleteSpecNames=templateModuleDAO.deleteSpecNames(Integer.parseInt(maps.get("id").toString()));
					logger.debug("P Delete spec names"+deleteSpecNames);
				}
					
				}
				boolean deleteItemSpecs=templateModuleDAO.deleteItemSpecs(templateEditRQ.getTemplateId(), 1);
				logger.debug("P Delete item specs "+deleteItemSpecs);
				
				//secondary category details delete.
				List<Map<String, Object>> itemSpecSids=templateDetailsDAO.getItemSpecIds(templateEditRQ.getTemplateId());
				
				for(Map<String, Object> maps:itemSpecSids){
					if(Integer.parseInt(maps.get("categoryLevel").toString())==2){
						
					List<Map<String, Object>> itemSpecName=templateDetailsDAO.getItemSpecNames(Integer.parseInt(maps.get("id").toString()));
				
					for(Map<String, Object> names:itemSpecName){
						//List<Map<String, Object>> itemSpecValues=templateDetailsDAO.getItemSpecValues(Integer.parseInt(names.get("id").toString()));
						
						boolean deleteSpecValues=	templateModuleDAO.deleteSpecValues(Integer.parseInt(names.get("id").toString()));
						logger.debug("S Delete spect Values"+deleteSpecValues);
						
					}
					
					boolean deleteSpecNames=templateModuleDAO.deleteSpecNames(Integer.parseInt(maps.get("id").toString()));
					logger.debug("S Delete spec names"+deleteSpecNames);
				}
					
				}
				boolean deleteItemSpecsS=templateModuleDAO.deleteItemSpecs(templateEditRQ.getTemplateId(), 2);
				logger.debug("S Delete item specs "+deleteItemSpecs);
				
				
			}else if(templateEditRQ.getCategories().isSetSecondaryCategoryDetails()){

				List<Map<String, Object>> itemSpecids=templateDetailsDAO.getItemSpecIds(templateEditRQ.getTemplateId());
				
				for(Map<String, Object> maps:itemSpecids){
					if(Integer.parseInt(maps.get("categoryLevel").toString())==1){
						
					List<Map<String, Object>> itemSpecName=templateDetailsDAO.getItemSpecNames(Integer.parseInt(maps.get("id").toString()));
				
					for(Map<String, Object> names:itemSpecName){
						//List<Map<String, Object>> itemSpecValues=templateDetailsDAO.getItemSpecValues(Integer.parseInt(names.get("id").toString()));
						
						boolean deleteSpecValues=	templateModuleDAO.deleteSpecValues(Integer.parseInt(names.get("id").toString()));
						logger.debug("P Delete spect Values"+deleteSpecValues);
						
					}
					
					boolean deleteSpecNames=templateModuleDAO.deleteSpecNames(Integer.parseInt(maps.get("id").toString()));
					logger.debug("P Delete spec names"+deleteSpecNames);
				}
					
				}
				boolean deleteItemSpecs=templateModuleDAO.deleteItemSpecs(templateEditRQ.getTemplateId(), 1);
				logger.debug("P Delete item specs "+deleteItemSpecs);
				//secondary category details delete.
				List<Map<String, Object>> itemSpecSids=templateDetailsDAO.getItemSpecIds(templateEditRQ.getTemplateId());
				
				for(Map<String, Object> maps:itemSpecSids){
					if(Integer.parseInt(maps.get("categoryLevel").toString())==2){
						
					List<Map<String, Object>> itemSpecName=templateDetailsDAO.getItemSpecNames(Integer.parseInt(maps.get("id").toString()));
				
					for(Map<String, Object> names:itemSpecName){
						//List<Map<String, Object>> itemSpecValues=templateDetailsDAO.getItemSpecValues(Integer.parseInt(names.get("id").toString()));
						
						boolean deleteSpecValues=	templateModuleDAO.deleteSpecValues(Integer.parseInt(names.get("id").toString()));
						logger.debug("S Delete spect Values"+deleteSpecValues);
						
					}
					
					boolean deleteSpecNames=templateModuleDAO.deleteSpecNames(Integer.parseInt(maps.get("id").toString()));
					logger.debug("S Delete spec names"+deleteSpecNames);
				}
					
				}
				boolean deleteItemSpecsS=templateModuleDAO.deleteItemSpecs(templateEditRQ.getTemplateId(), 2);
				logger.debug("S Delete item specs "+deleteItemSpecsS);
				
			}else{
				String secondayCat=null;
				 secondayCat=checkSecondaryCatRemove(templateEditRQ,  secondayCat);
				 if(secondayCat!=null){

						
						//secondary category details delete.
						List<Map<String, Object>> itemSpecSids=templateDetailsDAO.getItemSpecIds(templateEditRQ.getTemplateId());
						
						for(Map<String, Object> maps:itemSpecSids){
							if(Integer.parseInt(maps.get("categoryLevel").toString())==2){
								
							List<Map<String, Object>> itemSpecName=templateDetailsDAO.getItemSpecNames(Integer.parseInt(maps.get("id").toString()));
						
							for(Map<String, Object> names:itemSpecName){
								//List<Map<String, Object>> itemSpecValues=templateDetailsDAO.getItemSpecValues(Integer.parseInt(names.get("id").toString()));
								
								boolean deleteSpecValues=	templateModuleDAO.deleteSpecValues(Integer.parseInt(names.get("id").toString()));
								logger.debug("S Delete spect Values"+deleteSpecValues);
								
							}
							
							boolean deleteSpecNames=templateModuleDAO.deleteSpecNames(Integer.parseInt(maps.get("id").toString()));
							logger.debug("S Delete spec names"+deleteSpecNames);
						}
							
						}
						boolean deleteItemSpecsS=templateModuleDAO.deleteItemSpecs(templateEditRQ.getTemplateId(), 2);
						logger.debug("S Delete item specs "+deleteItemSpecsS);
				 }
			}
			
		}else{
			
				String secondayCat=null;
				 secondayCat=checkSecondaryCatRemove(templateEditRQ,  secondayCat);
				 if(secondayCat!=null){
				
						//secondary category details delete.
						List<Map<String, Object>> itemSpecSids=templateDetailsDAO.getItemSpecIds(templateEditRQ.getTemplateId());
						
						for(Map<String, Object> maps:itemSpecSids){
							if(Integer.parseInt(maps.get("categoryLevel").toString())==2){
								
							List<Map<String, Object>> itemSpecName=templateDetailsDAO.getItemSpecNames(Integer.parseInt(maps.get("id").toString()));
						
							for(Map<String, Object> names:itemSpecName){
								//List<Map<String, Object>> itemSpecValues=templateDetailsDAO.getItemSpecValues(Integer.parseInt(names.get("id").toString()));
								
								boolean deleteSpecValues=	templateModuleDAO.deleteSpecValues(Integer.parseInt(names.get("id").toString()));
								logger.debug("S Delete spect Values"+deleteSpecValues);
								
							}
							
							boolean deleteSpecNames=templateModuleDAO.deleteSpecNames(Integer.parseInt(maps.get("id").toString()));
							logger.debug("S Delete spec names"+deleteSpecNames);
						}
							
						}
						boolean deleteItemSpecsS=templateModuleDAO.deleteItemSpecs(templateEditRQ.getTemplateId(), 2);
						logger.debug("S Delete item specs "+deleteItemSpecsS);
				
		}
		}
		
		
		
		
		
		int primaryCatId=0;
		logger.debug(" Before This is inside Template Edit categories");
		if(templateEditRQ.isSetCategories() && templateEditRQ.getCategories().isSetPrimaryCategoryDetails() && templateEditRQ.getCategories().getPrimaryCategoryDetails().isSetPrimaryCategoryId() &&   Integer.parseInt(templateEditRQ.getCategories().getPrimaryCategoryDetails().getPrimaryCategoryId())>0){
			
			logger.debug("This is inside Template Edit categories");
			
			

		
			primaryCatId=templateModuleDAO.saveItemCategories(templateEditRQ.getTemplateId(),Integer.parseInt(templateEditRQ.getCategories().getPrimaryCategoryDetails().getPrimaryCategoryId()),1);
			
			//saveItemSpecifics
			
				if(templateEditRQ.getCategories().getPrimaryCategoryDetails().isSetPrimaryItemSpecifics() && templateEditRQ.getCategories().getPrimaryCategoryDetails().getPrimaryItemSpecifics().isSetNameValueList() && templateEditRQ.getCategories().getPrimaryCategoryDetails().getPrimaryItemSpecifics().getNameValueList().size()>0 && primaryCatId>0){
					templateModuleDAO.saveItemSpecifics(primaryCatId,templateEditRQ.getCategories().getPrimaryCategoryDetails().getPrimaryItemSpecifics().getNameValueList());
				}
			
			
			
		}
		
		int secondaryCatId=0;
		
		if(templateEditRQ.isSetCategories() && templateEditRQ.getCategories().isSetSecondaryCategoryDetails() &&  templateEditRQ.getCategories().getSecondaryCategoryDetails().isSetSecondaryCategoryId() &&  Integer.parseInt(templateEditRQ.getCategories().getSecondaryCategoryDetails().getSecondaryCategoryId())>0 ){
			
		
		
			
			secondaryCatId=templateModuleDAO.saveItemCategories(templateEditRQ.getTemplateId(),Integer.parseInt(templateEditRQ.getCategories().getSecondaryCategoryDetails().getSecondaryCategoryId()),2);
			
			if(templateEditRQ.getCategories().getSecondaryCategoryDetails().isSetSecondaryItemSpecifics() && templateEditRQ.getCategories().getSecondaryCategoryDetails().getSecondaryItemSpecifics().isSetNameValueList() && templateEditRQ.getCategories().getSecondaryCategoryDetails().getSecondaryItemSpecifics().getNameValueList().size()>0 &&secondaryCatId>0){
				templateModuleDAO.saveItemSpecifics(secondaryCatId,templateEditRQ.getCategories().getSecondaryCategoryDetails().getSecondaryItemSpecifics().getNameValueList());

			}
		
		}
		
		if(templateEditRQ.isSetAmenities() && templateEditRQ.getAmenities().isSetAmenityId()){
			
		//delete
			boolean deleteFlag=templateModuleDAO.deleteAmenities(templateEditRQ.getTemplateId());
			//insert
			if(deleteFlag || amenitiesCount>0)
			templateModuleDAO.saveAmenities(templateEditRQ.getTemplateId(),templateEditRQ.getAmenities());
			
		}
		
		if(templateEditRQ.isSetDescriptionPictures()){
			
			/*if(templateEditRQ.getDescriptionPictures().isSetLogoPictureURL()){
				//logo update
			boolean logoPictureUStatus=	templateModuleDAO.updateLogoPicture(templateEditRQ);
			logger.debug("Logo picture Update Status "+logoPictureUStatus);
			}*/
			
			if(templateEditRQ.getDescriptionPictures().isSetDescriptionPictureURL() && templateEditRQ.getDescriptionPictures().getDescriptionPictureURL().size()>0){
				//delete and insert
				boolean descDeleteStatus=templateModuleDAO.deleteDescPics(templateEditRQ.getTemplateId());
				logger.debug("This is template Desc pics Delete status"+descDeleteStatus);
				
			boolean editDescPics=	templateModuleDAO.editDescriptionPictures(templateEditRQ.getTemplateId(),templateEditRQ.getDescriptionPictures().getDescriptionPictureURL() , templateEditRQ.getObjectId());
			logger.debug("This is template Desc pics edit status"+editDescPics);
			}
			
			/*if(templateEditRQ.getDescriptionPictures().isSetObjectSliderPictureURL() && templateEditRQ.getDescriptionPictures().getObjectSliderPictureURL().size()>0){
				//delete and insert
				
				boolean deleteObjSStatus=templateModuleDAO.deleteObjectSliderPics(templateEditRQ.getTemplateId());
				logger.debug("This is template object slider pics Delete status"+deleteObjSStatus);
				
				boolean editObjSliderPics=templateModuleDAO.editObjectSliderPics(templateEditRQ.getTemplateId(),templateEditRQ.getDescriptionPictures().getObjectSliderPictureURL() , templateEditRQ.getObjectId());
				logger.debug("This is template object slider pics update status"+editObjSliderPics);
			}*/
			
			if(templateEditRQ.getDescriptionPictures().isSetOfferSliderPictureURL() && templateEditRQ.getDescriptionPictures().getOfferSliderPictureURL().size()>0){
				//delete and insert
				
				boolean deleteOfferSliderPics=templateModuleDAO.deleteOfferSliderPics(templateEditRQ.getTemplateId());
				logger.debug("This is template offer slider pics Delete status"+deleteOfferSliderPics);
				
				boolean editOfferSliderPics=templateModuleDAO.editOfferSliderPics(templateEditRQ.getTemplateId(),templateEditRQ.getDescriptionPictures().getOfferSliderPictureURL(),templateEditRQ.getObjectId() );
				logger.debug("This is template object slider pics update status"+editOfferSliderPics);
			}
			
		}
		
		if(templateEditRQ.isSetPictureDetails()){
			
			if(templateEditRQ.getPictureDetails().isSetGalleryURL()){
				//update
				boolean galleryPicUpdateStatus=templateModuleDAO.updateGalleryPic(templateEditRQ);
				logger.debug("This is template gallery pic update status"+galleryPicUpdateStatus);
			}
			
			if(templateEditRQ.getPictureDetails().isSetPictureURL() && templateEditRQ.getPictureDetails().getPictureURL().size()>0){
				//delete and insert
				boolean deleteItemPics=templateModuleDAO.deleteItemPictures(templateEditRQ.getTemplateId());
				logger.debug("This is template item pic delete status"+deleteItemPics);
				
				boolean editItempicsstatus=templateModuleDAO.editItemPictures(templateEditRQ.getTemplateId(), templateEditRQ.getPictureDetails().getPictureURL(), templateEditRQ.getObjectId());
				logger.debug("This is template item pic update status"+editItempicsstatus);
			}
			
			
			/*if(templateEditRQ.getPictureDetails().isSetVoucherPictureURL()){
				boolean voucherPicUpdateStatus=templateModuleDAO.updateVoucherPic(templateEditRQ);
				logger.debug("This is template voucher pic update status"+voucherPicUpdateStatus);
			}*/
		}
		
		if(templateEditRQ.isSetDescription()){
		if(templateEditRQ.getDescription().isSetVoucherText() || templateEditRQ.getDescription().isSetVoucherValidityText() || templateEditRQ.isSetVoucherValidity()){
			//update
			
			boolean voucherUpdateData=templateModuleDAO.updateVoucherData(templateEditRQ);
			logger.debug("This is template voucher data update status"+voucherUpdateData);
		}
		}else if(templateEditRQ.isSetVoucherValidity()){
			boolean voucherUpdateData=templateModuleDAO.updateVoucherData(templateEditRQ);
			logger.debug("This is template voucher data update status"+voucherUpdateData);
		}
		
		int oldShopObjectid=templateModuleDAO.getShopObjectId(templateEditRQ.getTemplateId());
		if(templateEditRQ.isSetStoreCategories() && shopObjectid>0){
			boolean storeStatus=false;
			//update
			if(oldShopObjectid>0){
				storeStatus=templateModuleDAO.updateShopCategories(templateEditRQ,templateEditRQ.getTemplateId(),shopObjectid,templateEditRQ.getStoreCategories());
			}else{
				//insert
				templateModuleDAO.saveShopCategories(templateEditRQ.getStoreCategories(), templateEditRQ.getTemplateId(),shopObjectid);
			}

			logger.debug("This is template store categories saved data update status"+storeStatus);
		}
		boolean removeFileds=false;
		removeFileds=templateModuleDAO.updateForRemoveFields(templateEditRQ);
		
		//int auctionMasterId=templateModuleDAO.getAuctionmasterTypeid(templateEditRQ.getTemplateId());
		
		
			/*if(auctionMasterId==1){
				templateModuleDAO.deleteShopCategories(templateEditRQ.getTemplateId());
			}*/
		//if(templateEditRQ.isSetDesignTemplate() || templateEditRQ.isSetProductId() || templateEditRQ.isSetOfferTitle() || templateEditRQ.isSetOfferSubTitle() || templateEditRQ.isSetAdditionalOptions() || templateEditRQ.isSetDescription() || templateEditRQ.isSetCategories()){
		
	if(!removeFileds){
			if(!vorlageDataUpdateStatus){
				//templateEditRS.setAck("Success");
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2188);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2188,langid));
				errorsType.add(errorType);
			}
	}	
		//}
		
		if(errorsType.size()>0){
			templateEditRS.setAck("Failure");
			//templateEditRS.setTemplateId(templateEditRQ.getTemplateId());
			templateEditRS.setErrors(error);
			
		}else{		
		    templateEditRS.setAck("Success");
		    templateEditRS.setTemplateId(templateEditRQ.getTemplateId());
		}
		
		return templateEditRS;
	}
	
	private boolean validateRemoveField(TemplateEditRQ templateEditRQ) {
		return templateEditRQ.isSetRemoveFields() && !templateEditRQ.getRemoveFields().getRemoveField().isEmpty() && null!=templateEditRQ.getRemoveFields().getRemoveField();
	}
	

	private String checkRetailRemoveFields(TemplateEditRQ templateEditRQ,
			String retailPrice) {
		if(null!=templateEditRQ.getRemoveFields() && null!=templateEditRQ.getRemoveFields().getRemoveField() && templateEditRQ.getRemoveFields().getRemoveField().size()>0){
		allowedStrings=templateEditRQ.getRemoveFields().getRemoveField();
		if(allowedStrings.contains("RetailPrice")){
			retailPrice="0";
		}
		}
		return retailPrice;
	}
	
	public String checkSubTitleRemove(TemplateEditRQ templateEditRQ,
			String subTitle){
		if(null!=templateEditRQ.getRemoveFields() && null!=templateEditRQ.getRemoveFields().getRemoveField() && templateEditRQ.getRemoveFields().getRemoveField().size()>0){
			allowedStrings=templateEditRQ.getRemoveFields().getRemoveField();
			if(allowedStrings.contains("OfferSubTitle")){
				subTitle="";
			}
			}
		return subTitle;
	}
	
	public int checkStoreCatRemove(TemplateEditRQ templateEditRQ, int storeCat2){
		if(null!=templateEditRQ.getRemoveFields() && null!=templateEditRQ.getRemoveFields().getRemoveField() && templateEditRQ.getRemoveFields().getRemoveField().size()>0){
			allowedStrings=templateEditRQ.getRemoveFields().getRemoveField();
			if(allowedStrings.contains("StoreCategory2Id")){
				storeCat2=0;
			}
			}
		return storeCat2;
	}
	public String checkSecondaryCatRemove(TemplateEditRQ templateEditRQ, String SecondaryCat2){
		if(null!=templateEditRQ.getRemoveFields() && null!=templateEditRQ.getRemoveFields().getRemoveField() && templateEditRQ.getRemoveFields().getRemoveField().size()>0){
			allowedStrings=templateEditRQ.getRemoveFields().getRemoveField();
			if(allowedStrings.contains("SecondaryCategoryId")){
				SecondaryCat2="0";
			}
			}
		return SecondaryCat2;
	}
	
	
}
