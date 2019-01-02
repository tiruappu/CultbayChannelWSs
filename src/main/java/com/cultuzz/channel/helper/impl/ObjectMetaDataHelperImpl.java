package com.cultuzz.channel.helper.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cultuzz.channel.DAO.ObjectConfigurationDAO;
import com.cultuzz.channel.DAO.ObjectMetaDataDAO;
import com.cultuzz.channel.DAO.PictureAdministrationDAO;
import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.DAO.impl.TemplateModuleDAOImpl;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.XMLpojo.ObjectMetaDataRQ;
import com.cultuzz.channel.XMLpojo.ObjectMetaDataRS;
import com.cultuzz.channel.XMLpojo.ObjectMetaDataRS.Categories;
import com.cultuzz.channel.XMLpojo.ObjectMetaDataRS.Categories.Category;
import com.cultuzz.channel.XMLpojo.ObjectMetaDataRS.PictureCategories.PictureCategory;
import com.cultuzz.channel.XMLpojo.ObjectMetaDataRS.Products;
import com.cultuzz.channel.XMLpojo.ObjectMetaDataRS.Products.Product;
import com.cultuzz.channel.XMLpojo.ObjectMetaDataRS.Products.Product.Validities;
import com.cultuzz.channel.XMLpojo.ObjectMetaDataRS.SellerAccounts;
import com.cultuzz.channel.XMLpojo.ObjectMetaDataRS.SellerAccounts.SellerAccount;
import com.cultuzz.channel.XMLpojo.ObjectMetaDataRS.ShopCategories;
import com.cultuzz.channel.XMLpojo.ObjectMetaDataRS.ShopCategories.ShopCategory;
import com.cultuzz.channel.helper.ObjectMetaDataHelper;
import com.cultuzz.channel.template.pojo.PictureCategories;
import com.cultuzz.channel.util.CommonValidations;
import com.cultuzz.channel.util.DateUtil;

@Repository
public class ObjectMetaDataHelperImpl implements ObjectMetaDataHelper{

    @Autowired
	CommonValidations commonValidations;
    
    @Autowired
	GetErrorMessagesDAOImpl getErrormessages;
    
    @Autowired
	ObjectConfigurationDAO objectConfigurationDAO;
    
    @Autowired
	TemplateModuleDAOImpl templateModuleDAOImpl;
    
    @Autowired
    ObjectMetaDataDAO objectMetaDataDAOImpl;
    
    @Autowired
    PictureAdministrationDAO pictureAdministrationDAO;
    
    private static final Logger LOGGER = LoggerFactory.getLogger("ObjectMetaDataHelperImpl.class");  
	
	public ObjectMetaDataRS validateObjectMetaDataRQ(
			ObjectMetaDataRQ objectMetaDataRQ) {
		// TODO Auto-generated method stub
		
		ObjectMetaDataRS objectMetaDataRS = new ObjectMetaDataRS();
		
		   try{
			   
		   objectMetaDataRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
			LOGGER.debug("inside validate objectMetadataRQ validation");
			
			int objectId = 0;
			
			ErrorsType error = new ErrorsType();
			boolean objectIdFlag = false;
			
			List<ErrorType> errorsTypes = error.getError();
			int langId = 0;
			
			if(objectMetaDataRQ.isSetErrorLang()){
				
				langId=commonValidations.checkErrorLangCode(objectMetaDataRQ.getErrorLang());
				
				if(langId>0){
					
				}else{
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1106);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,langId));
					errorsTypes.add(errorType);
					objectMetaDataRS.setErrors(error);
					objectMetaDataRS.setAck("Failure");
					return objectMetaDataRS;
				}
				
			}else{				
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1106);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,langId));
				errorsTypes.add(errorType);
				objectMetaDataRS.setErrors(error);
				objectMetaDataRS.setAck("Failure");
				return objectMetaDataRS;
			}
			if(objectMetaDataRQ.isSetTimeStamp() && !objectMetaDataRQ.getTimeStamp().trim().isEmpty()){
				
			boolean timestampStatus = commonValidations.checkTimeStamp(objectMetaDataRQ.getTimeStamp());
			if(!timestampStatus){	
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1104);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1104,langId));
				errorsTypes.add(errorType);
				objectMetaDataRS.setErrors(error);
				objectMetaDataRS.setAck("Failure");
				return objectMetaDataRS;
			}
			
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1105);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1105,langId));
				errorsTypes.add(errorType);
				objectMetaDataRS.setErrors(error);
				objectMetaDataRS.setAck("Failure");
				return objectMetaDataRS;
			}

			
		     if(objectMetaDataRQ.isSetAuthenticationCode()){
		    	 
		    	 boolean authCodeStatus = commonValidations.checkAuthCode(objectMetaDataRQ.getAuthenticationCode());
		         if(!authCodeStatus){
		        	 
		        	 ErrorType errorType = new ErrorType();
		        	 errorType.setErrorCode(1100);
		        	 errorType.setErrorMessage(getErrormessages.getErrorMessage(1100, 2));
		        	 errorsTypes.add(errorType);
		        	 objectMetaDataRS.setErrors(error);
		        	 objectMetaDataRS.setAck("Failure");
		        	 return objectMetaDataRS;
		         }
		     }else{
		    	 
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1100);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,langId));
					errorsTypes.add(errorType);
					objectMetaDataRS.setErrors(error);
					objectMetaDataRS.setAck("Failure");
					return objectMetaDataRS;

		     }
			
		      if(objectMetaDataRQ.isSetSourceId()){
		    	  
		    	  if(!commonValidations.checkSourceId(objectMetaDataRQ.getSourceId())){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(1101);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langId));
						errorsTypes.add(errorType);
						objectMetaDataRS.setErrors(error);
						objectMetaDataRS.setAck("Failure");
						return objectMetaDataRS;
					}
		      }else{
		    	  
		    		ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1101);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langId));
					errorsTypes.add(errorType);
					objectMetaDataRS.setErrors(error);
					objectMetaDataRS.setAck("Failure");
					return objectMetaDataRS;
				
		      }
		
		      if(objectMetaDataRQ.isSetChannelId()){
		    	  if(!commonValidations.checkChannelId(objectMetaDataRQ.getChannelId())){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(1102);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langId));
						errorsTypes.add(errorType);
						objectMetaDataRS.setErrors(error);
						objectMetaDataRS.setAck("Failure");
						return objectMetaDataRS;
					}
		    	  
		      }else{
		    	  ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1102);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langId));
					errorsTypes.add(errorType);
					objectMetaDataRS.setErrors(error);
					objectMetaDataRS.setAck("Failure");
					return objectMetaDataRS;
		      }
		      
		if(objectMetaDataRQ.isSetObjectId()){
				
				LOGGER.debug("Checking objectid");
				if(!commonValidations.checkObjectId(objectMetaDataRQ.getObjectId())){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1103);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langId));
					errorsTypes.add(errorType);
					objectMetaDataRS.setErrors(error);
					objectMetaDataRS.setAck("Failure");
					return objectMetaDataRS;
				}else{
					objectIdFlag=true;
					objectId=objectMetaDataRQ.getObjectId();
					
					boolean ebaydatenStatus=objectConfigurationDAO.checkEbayDaten(objectId);
					if(!ebaydatenStatus){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(1107);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(1107,langId));
						errorsTypes.add(errorType);
						objectMetaDataRS.setErrors(error);
						objectMetaDataRS.setAck("Failure");
						return objectMetaDataRS;
					}
				}
				
			}else{
				
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1103);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langId));
				errorsTypes.add(errorType);
				objectMetaDataRS.setErrors(error);
				objectMetaDataRS.setAck("Failure");
				return objectMetaDataRS;
				
			}
			
		if(objectMetaDataRQ.isSetRequestParameters() && objectMetaDataRQ.getRequestParameters()!=null
				  && objectMetaDataRQ.getRequestParameters()!="" && !objectMetaDataRQ.getRequestParameters().equalsIgnoreCase("PictureCategories")){
		
		if(objectMetaDataRQ.isSetSiteId() && objectMetaDataRQ.getSiteId()!=null){
			
			LOGGER.debug("site id is :{}",objectMetaDataRQ.getSiteId());
			if(templateModuleDAOImpl.checkSiteId(objectMetaDataRQ.getSiteId())){
				
				LOGGER.debug("siteId is valid");
			}else{
				
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(2101);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(2101,langId));
				errorsTypes.add(errorType);
				objectMetaDataRS.setErrors(error);
				objectMetaDataRS.setAck("Failure");
				return objectMetaDataRS;		
			}
		}else{
			
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(2101);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(2101,langId));
			errorsTypes.add(errorType);
			objectMetaDataRS.setErrors(error);
			objectMetaDataRS.setAck("Failure");
			return objectMetaDataRS;		
		}
		
		}
		 LOGGER.debug("request parameter iss"+objectMetaDataRQ.getRequestParameters());
		if(objectMetaDataRQ.isSetRequestParameters() && objectMetaDataRQ.getRequestParameters()!=null
				  && objectMetaDataRQ.getRequestParameters()!=""){
			
		  
			  if(objectMetaDataRQ.getRequestParameters().equalsIgnoreCase("SellerAccounts") ||
				objectMetaDataRQ.getRequestParameters().equalsIgnoreCase("Products") ||
				objectMetaDataRQ.getRequestParameters().equalsIgnoreCase("ebayCategories") ||
				objectMetaDataRQ.getRequestParameters().equalsIgnoreCase("ShopCategories") || 
				objectMetaDataRQ.getRequestParameters().equalsIgnoreCase("PictureCategories") ){
				  
				  LOGGER.debug("valid request parameters");
				  
			  }else{
				  
				  ErrorType errorType=new ErrorType();
					errorType.setErrorCode(14001);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(14001,langId));
					errorsTypes.add(errorType);
					objectMetaDataRS.setErrors(error);
					objectMetaDataRS.setAck("Failure");
					return objectMetaDataRS;
				  
			  }
		}else{
			  
			  ErrorType errorType=new ErrorType();
				errorType.setErrorCode(14001);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(14001,langId));
				errorsTypes.add(errorType);
				objectMetaDataRS.setErrors(error);
				objectMetaDataRS.setAck("Failure");
				return objectMetaDataRS;
			
		}
		
		 if(errorsTypes.size()>0){
			  objectMetaDataRS.setErrors(error);
				objectMetaDataRS.setAck("Failure");
		  }else{
				objectMetaDataRS.setAck("Success");
		  }
		
		   }catch(Exception e){
			   
			   e.printStackTrace();
		   }
		 
			return objectMetaDataRS;
		
	
	}

	public ObjectMetaDataRS processObjectMetaDataRQ(
			ObjectMetaDataRQ objectMetaDataRQ) {
		// TODO Auto-generated method stub
		ObjectMetaDataRS objectMetaDataRS = new ObjectMetaDataRS();
		int langId = 0;
		List<Map<String,Object>> sellerAccs = null;
		List<Map<String,Object>> ccAccs = null;
		LOGGER.debug("inside the process object meta data helper");
		
		ErrorsType errorsType = new ErrorsType();
		List<ErrorType> errorType = errorsType.getError();
		List<Map<String,Object>> productslist = null;
		List<Map<String,Object>> categorieslist = null;
		List<Map<String,Object>> shopCategorieslist = null;
		try{
			langId = getErrormessages.getLanguageId(objectMetaDataRQ.getErrorLang());

			
			objectMetaDataRS.setTimeStamp(DateUtil
					.convertDateToString(new Date()));
			objectMetaDataRS.setObjectId(objectMetaDataRQ.getObjectId());
			if(langId > 0){
				
				LOGGER.debug("language id is"+langId);
				
				if(objectMetaDataRQ == null){
					
					ErrorType error = new ErrorType();
					error.setErrorCode(14002);

					error.setErrorMessage(getErrormessages.getErrorMessage(
							14002, 2));
					errorType.add(error);
				}else{
					
				if(objectMetaDataRQ.getRequestParameters().equalsIgnoreCase("SellerAccounts")){
				
					int siteId = objectMetaDataRQ.getSiteId();
					SellerAccounts sellerAccounts = new SellerAccounts();
					SellerAccount objectSellerAccount = new SellerAccount();
					objectSellerAccount.setSellerId(objectMetaDataRQ.getObjectId());
					String objectName = objectMetaDataDAOImpl.getObjectName(objectMetaDataRQ.getObjectId());
					
					objectSellerAccount.setSellerName(objectName);
					objectSellerAccount.setPayeeAccount(
							objectMetaDataDAOImpl.getPaypalEmail(
									objectMetaDataRQ.getObjectId(),siteId));
					
                     sellerAccounts.getSellerAccount().add(objectSellerAccount);
					
					sellerAccs = objectMetaDataDAOImpl.getSellerAccounts(objectMetaDataRQ.getObjectId());
					if(sellerAccs!=null){
					LOGGER.debug("seller accounts is"+sellerAccs.toString());
					for(Map<String,Object> sellerAcc : sellerAccs){
						String shopname = null;
							
							 shopname = sellerAcc.get("ShopName").toString();
							
							if(shopname!=null && shopname!=""){
							if(shopname.compareToIgnoreCase(objectName)!=0){
					    	SellerAccount sellerAccount = new SellerAccount();
					    	long shopObjectId = 0;
					    	if(sellerAcc.get("ObjectID")!=null)
					    	shopObjectId = Long.parseLong(sellerAcc.get("ObjectID").toString());
					    	if(shopObjectId > 0)
					    	sellerAccount.setSellerId(shopObjectId);
					    	if(sellerAcc.get("ShopName")!=null)
					    	sellerAccount.setSellerName(sellerAcc.get("ShopName").toString());
					    	sellerAccount.setPayeeAccount(objectMetaDataDAOImpl.getPaypalEmail(shopObjectId, siteId));
					    	sellerAccount.setShopAccount(1);
					    	sellerAccounts.getSellerAccount().add(sellerAccount);
							}}
					}}
					
					ccAccs = objectMetaDataDAOImpl.getCollectionAccounts(objectMetaDataRQ.getObjectId());
					LOGGER.debug("collection account is"+ccAccs.toString());
					if(ccAccs!=null){
					for(Map<String,Object> ccAcc : ccAccs){
						//System.out.println("collection acc "+ccAcc.get("CollectionAccount").toString());
						
						String collectionAcc = ccAcc.get("CollectionAccount").toString();
						boolean count = false;
						for(Map<String,Object> sellerAcc : sellerAccs){
							//System.out.println("seller acc "+sellerAcc.get("ShopName").toString());
						
							String sellerAcnt = sellerAcc.get("ShopName").toString();
							//System.out.println("equal"+sellerAcnt.equalsIgnoreCase(collectionAcc));
							//System.out.println("comp"+sellerAcnt.compareToIgnoreCase(collectionAcc));
						   	if(sellerAcnt.equalsIgnoreCase(collectionAcc)){		
								
						   		count = true;
						   		//System.out.println("iffffff"+count);
						   		break;
					    	}}
						if(!count){
							if(collectionAcc!=null && collectionAcc!=""){
							if(collectionAcc.compareToIgnoreCase(objectName)!=0){
				    	SellerAccount sellerAccount = new SellerAccount();
				    	long shopObjectId = 0;
				    	if(ccAcc.get("CAccObjekt")!=null)
				    	 shopObjectId = Long.parseLong(ccAcc.get("CAccObjekt").toString());
				    	if(shopObjectId > 0)
				    	sellerAccount.setSellerId(shopObjectId);
				    	if(ccAcc.get("CollectionAccount")!=null)
				    	sellerAccount.setSellerName(ccAcc.get("CollectionAccount").toString());
				    	sellerAccount.setPayeeAccount(objectMetaDataDAOImpl.getPaypalEmail(shopObjectId, siteId));
				    	sellerAccount.setShopAccount(0);
				    	sellerAccounts.getSellerAccount().add(sellerAccount);
						}}}
					}}
					
					objectMetaDataRS.setSellerAccounts(sellerAccounts);
				}
				}
	 if(objectMetaDataRQ.getRequestParameters().equalsIgnoreCase("products")){
					 
					 Products products = new Products();
					 productslist = objectMetaDataDAOImpl.getProductsList
							 (objectMetaDataRQ.getObjectId(), langId);
					 if(productslist!=null){
					 for(Map<String,Object> productlist : productslist){
						 Product product = new Product();
						 long productId = Long.parseLong(productlist.get("id").toString());
						 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 				Date date = new Date();
			 				String startdate1 = dateFormat.format(date);
			 			boolean productvalidityflag = objectMetaDataDAOImpl.checkProductValidity(productId,startdate1);
			 			if(productvalidityflag){
						 product.setId(productId);
						 product.setName(productlist.get("bezeichnung").toString());
						 product.setPrice(Double.parseDouble(productlist.get("price").toString()));
						 product.setNoOfPersons(Integer.parseInt(productlist.get("standardOccupancy").toString()));
						 product.setNoOfNights(Integer.parseInt(productlist.get("lengthOfStay").toString()));
						 List<Map<String,Object>> validitieslist = null;
						  validitieslist = objectMetaDataDAOImpl.getValiditiesList(productId);
						  Validities validities = new Validities();
						  if(validitieslist!=null){
						  for(Map<String,Object> validitylist : validitieslist){
							  if(validitylist.get("validity")!=null){
								  if(Integer.parseInt(validitylist.get("status").toString())==1){
							 validities.getValidity().add(validitylist.get("validity").toString());
								  }else{
									  validities.getValidity().add(validitylist.get("validity").toString()+" (blocked)");		  
								  }
							  }
						  }}
						 
						  product.setValidities(validities);
						  products.getProduct().add(product);
					    }
					 }}
					 
					 objectMetaDataRS.setProducts(products);
				 }
				 
				 if(objectMetaDataRQ.getRequestParameters().equalsIgnoreCase("ebayCategories")){
					 
					  Categories categories =  new Categories();
					  categorieslist = objectMetaDataDAOImpl.getEbayCategories
							  (objectMetaDataRQ.getObjectId(), objectMetaDataRQ.getSiteId());
					  if(categorieslist!=null){
					  for(Map<String,Object> categorylist : categorieslist){
						
						   Category category = new Category();
						   if(categorylist.get("kat_id")!=null){
						   category.setId(Long.parseLong(categorylist.get("kat_id").toString()));
						   if(categorylist.get("bezeichnung")!=null){
						   category.setName(categorylist.get("bezeichnung").toString());
						   }}
						    categories.getCategory().add(category);
					  }}
					  
					   objectMetaDataRS.setCategories(categories);
				 }
				 if(objectMetaDataRQ.getRequestParameters().equalsIgnoreCase("shopCategories")){
					 
					 ShopCategories shopCategories = new ShopCategories();
					 
					 if(objectMetaDataRQ.isSetShopObjectId()){
					 if(objectMetaDataRQ.getShopObjectId()>0){
						 
						 shopCategorieslist = objectMetaDataDAOImpl.getShopCategories(objectMetaDataRQ.getShopObjectId());
						if(shopCategorieslist!=null){
						 for(Map<String,Object> shopCategorylist : shopCategorieslist){
							
							 ShopCategory shopCategory = new ShopCategory();
							 if(shopCategorylist.get("CategoryID")!=null){
						     shopCategory.setId(Long.parseLong(shopCategorylist.get("CategoryID").toString()));
						     if(shopCategorylist.get("CategoryName")!=null)
							 shopCategory.setName(shopCategorylist.get("CategoryName").toString());
						     if(shopCategorylist.get("ParentCategoryID")!=null)
							 shopCategory.setParentCategoryId(Long.parseLong(shopCategorylist.get("ParentCategoryID").toString()));
							 }
							 shopCategories.getShopCategory().add(shopCategory);
						 }}
						 
						 objectMetaDataRS.setShopCategories(shopCategories);
					 }}
				 }
				 
				 if(objectMetaDataRQ.getRequestParameters().equalsIgnoreCase("PictureCategories")){
					 List<PictureCategories>  picturecats=pictureAdministrationDAO.getAllPictureCategories(objectMetaDataRQ.getObjectId());
					 
					 if(picturecats!=null){
						 
						 com.cultuzz.channel.XMLpojo.ObjectMetaDataRS.PictureCategories picCats=new com.cultuzz.channel.XMLpojo.ObjectMetaDataRS.PictureCategories();
						 
						 List<PictureCategory>  picCatsList=picCats.getPictureCategory();
						 
						 for(PictureCategories dbCats:picturecats){
							 PictureCategory catVal=new PictureCategory(); 
							 catVal.setId(dbCats.getCategoryId());
							 catVal.setName(dbCats.getName());
							 picCatsList.add(catVal);
						 }
						 
						 
						 objectMetaDataRS.setPictureCategories(picCats);
					 }
				 }
				 
				 
			}else{
				
				ErrorType error = new ErrorType();
				error.setErrorCode(1106);

				error.setErrorMessage(getErrormessages.getErrorMessage(
						1106, 2));
				errorType.add(error);
			}
			
			if(errorType.size()>0){
				  objectMetaDataRS.setErrors(errorsType);
					objectMetaDataRS.setAck("Failure");
			  }else{
					objectMetaDataRS.setAck("Success");
			  }
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		return objectMetaDataRS;
	}

	
}
