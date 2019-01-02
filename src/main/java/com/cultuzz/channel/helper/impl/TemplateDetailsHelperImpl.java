package com.cultuzz.channel.helper.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cultuzz.channel.DAO.TemplateDetailsDAO;
import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.XMLpojo.AdditionalOptionsType;
import com.cultuzz.channel.XMLpojo.AmenitiesType;
import com.cultuzz.channel.XMLpojo.CategoriesType;
import com.cultuzz.channel.XMLpojo.DescriptionPicturesType;
import com.cultuzz.channel.XMLpojo.DescriptionType;
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
import com.cultuzz.channel.XMLpojo.TemplateDetailsRQ;
import com.cultuzz.channel.XMLpojo.TemplateDetailsRS;
import com.cultuzz.channel.helper.TemplateDetailsHelper;
import com.cultuzz.channel.util.CommonValidations;

@Repository
public class TemplateDetailsHelperImpl implements TemplateDetailsHelper {

	@Autowired
	TemplateDetailsDAO templateDetailsDAO;

	@Autowired
	CommonValidations commonValidations;

	@Autowired
	GetErrorMessagesDAOImpl getErrormessages;

	// java.util.Date date = new java.util.Date();
	// Timestamp timestamp = new Timestamp(date.getTime());

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TemplateDetailsHelperImpl.class);

	public TemplateDetailsRS validateTemplateDetailsRQ(
			TemplateDetailsRQ templateDetailsRQ) {

		TemplateDetailsRS templateDetailsRS = new TemplateDetailsRS();
		templateDetailsRS.setTimeStamp(commonValidations.getCurrentTimeStamp());

		LOGGER.debug("inside validateTamplateCreationRQ method");

		int objectid = templateDetailsRQ.getObjectId();
		ErrorsType error = new ErrorsType();

		// ErrorType error=templateCreationRS.getErrors();
		boolean objectIdFlag = false;
		boolean productIdStatus = false;
		List<ErrorType> errorsType = error.getError();
		int langid = 0;

		if (templateDetailsRQ.isSetAuthenticationCode()) {

			boolean authCodeStatus = commonValidations.checkAuthCode(templateDetailsRQ.getAuthenticationCode());
			if (!authCodeStatus) {
				ErrorType errorType = new ErrorType();
				errorType.setErrorCode(1100);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1100, 2));
				errorsType.add(errorType);
				templateDetailsRS.setErrors(error);
				templateDetailsRS.setAck("Failure");
				return templateDetailsRS;
			}

		} else {
			ErrorType errorType = new ErrorType();
			errorType.setErrorCode(1100);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1100, 2));
			errorsType.add(errorType);
			templateDetailsRS.setErrors(error);
			templateDetailsRS.setAck("Failure");
			return templateDetailsRS;

		}
		if (templateDetailsRQ.isSetErrorLang()) {

			langid = commonValidations.checkErrorLangCode(templateDetailsRQ.getErrorLang());

			if (langid > 0) {

			} else {
				ErrorType errorType = new ErrorType();
				errorType.setErrorCode(1106);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1106, 2));
				errorsType.add(errorType);
				templateDetailsRS.setErrors(error);
				templateDetailsRS.setAck("Failure");
				return templateDetailsRS;
			}

		} else {
			ErrorType errorType = new ErrorType();
			errorType.setErrorCode(1106);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,2));
			errorsType.add(errorType);
			templateDetailsRS.setErrors(error);
			templateDetailsRS.setAck("Failure");
			return templateDetailsRS;
		}

		if (templateDetailsRQ.isSetTimeStamp()) {
			if(!templateDetailsRQ.getTimeStamp().trim().isEmpty()){
			boolean timestampStatus = commonValidations.checkTimeStamp(templateDetailsRQ.getTimeStamp());
			if (!timestampStatus) {
				ErrorType errorType = new ErrorType();
				errorType.setErrorCode(1104);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1104, langid));
				errorsType.add(errorType);
				templateDetailsRS.setErrors(error);
				templateDetailsRS.setAck("Failure");
				return templateDetailsRS;
			}
			}else{
				ErrorType errorType = new ErrorType();
				errorType.setErrorCode(1105);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1105, langid));
				errorsType.add(errorType);
				templateDetailsRS.setErrors(error);
				templateDetailsRS.setAck("Failure");
				return templateDetailsRS;
			}
		} else {
			ErrorType errorType = new ErrorType();
			errorType.setErrorCode(1105);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1105,langid));
			errorsType.add(errorType);
			templateDetailsRS.setErrors(error);
			templateDetailsRS.setAck("Failure");
			return templateDetailsRS;
		}

		if (templateDetailsRQ.isSetSourceId()) {
			if (!commonValidations.checkSourceId(templateDetailsRQ.getSourceId())) {
				ErrorType errorType = new ErrorType();
				errorType.setErrorCode(1101);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1101, langid));
				errorsType.add(errorType);
				templateDetailsRS.setErrors(error);
				templateDetailsRS.setAck("Failure");
				return templateDetailsRS;
			}

		} else {
			ErrorType errorType = new ErrorType();
			errorType.setErrorCode(1101);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langid));
			errorsType.add(errorType);
			templateDetailsRS.setErrors(error);
			templateDetailsRS.setAck("Failure");
			return templateDetailsRS;

		}

		if (templateDetailsRQ.isSetChannelId()) {

			if (!commonValidations.checkChannelId(templateDetailsRQ.getChannelId())) {
				ErrorType errorType = new ErrorType();
				errorType.setErrorCode(1102);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1102, langid));
				errorsType.add(errorType);
				templateDetailsRS.setErrors(error);
				templateDetailsRS.setAck("Failure");
				return templateDetailsRS;
			}

		} else {

			ErrorType errorType = new ErrorType();
			errorType.setErrorCode(1102);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langid));
			errorsType.add(errorType);
			templateDetailsRS.setErrors(error);
			templateDetailsRS.setAck("Failure");
			return templateDetailsRS;

		}

		if (templateDetailsRQ.isSetObjectId()) {

			LOGGER.debug("Checking objectid");
			if (!commonValidations.checkObjectId(templateDetailsRQ.getObjectId())) {
				ErrorType errorType = new ErrorType();
				errorType.setErrorCode(1103);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1103, langid));
				errorsType.add(errorType);
				templateDetailsRS.setErrors(error);
				templateDetailsRS.setAck("Failure");
				return templateDetailsRS;
			} else
				objectIdFlag = true;

		} else {

			ErrorType errorType = new ErrorType();
			errorType.setErrorCode(1103);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
			errorsType.add(errorType);
			templateDetailsRS.setErrors(error);
			templateDetailsRS.setAck("Failure");
			return templateDetailsRS;

		}

		int templateId = 0;
		if (templateDetailsRQ.isSetTemplateId()) {
			templateId = templateDetailsRQ.getTemplateId();
			boolean templateflag = false;
			LOGGER.debug("Checking templateid");
			templateflag = templateDetailsDAO.checkTemplateId(templateDetailsRQ.getTemplateId(),templateDetailsRQ.getObjectId());
			LOGGER.debug("templateid check status" + templateflag);

			if (!templateflag) {

				ErrorType errorType = new ErrorType();
				errorType.setErrorCode(3128);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(3128, langid));
				errorsType.add(errorType);
				templateDetailsRS.setErrors(error);
				templateDetailsRS.setAck("Failure");

				return templateDetailsRS;

			}
		} else {

			ErrorType errorType = new ErrorType();
			errorType.setErrorCode(3109);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(3109,langid));
			errorsType.add(errorType);
			templateDetailsRS.setErrors(error);
			templateDetailsRS.setAck("Failure");
			return templateDetailsRS;

		}

		if (errorsType.size() > 0) {
			// System.out.println("Inside errorsssssssssssssssss iffffffffffff");
			templateDetailsRS.setAck("Failure");
		} else {
			// System.out.println("Inside errorsssssssssssssssss elseeeeeeeeeeee");

			templateDetailsRS.setErrors(error);
			templateDetailsRS.setAck("Success");
		}

		return templateDetailsRS;
	}

	public TemplateDetailsRS processTemplateDetailsRQ(TemplateDetailsRQ templateDetailsRQ) {
		// TemplateDetailsDAOImpl detailsDAOImpl=new TemplateDetailsDAOImpl();
		// templateDetailsDAO detailsDAOImpl;
		TemplateDetailsRS templateDetailsRS = null;
		try {
			templateDetailsRS = templateDetailsDAO.getTemplateDetails(templateDetailsRQ.getTemplateId());
			// System.out.println("Inside the process method::::::::::::::::::::::::::::::::::::"+templateDetailsRS.getAck());
			templateDetailsRS.setAck("Success");
			templateDetailsRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
			PropertiesType propertiesType = new PropertiesType();
			DescriptionType descriptionType = new DescriptionType();
			PictureDetailsType pictureDetailsType = new PictureDetailsType();
			DescriptionPicturesType descriptionPicturesType = new DescriptionPicturesType();
		//	PrimaryCategoryDetailsType primaryCategoryType = new PrimaryCategoryDetailsType();
		//	SecondaryCategoryDetailsType secondaryCategoryType = new SecondaryCategoryDetailsType();
			AdditionalOptionsType additionalOptionsType = new AdditionalOptionsType();
			CategoriesType categoriesType = new CategoriesType();
			PrimaryCategoryDetailsType primaryCategoryDetailsType = new PrimaryCategoryDetailsType();
			SecondaryCategoryDetailsType secondaryCategoryDetailsType = new SecondaryCategoryDetailsType();
			PrimaryItemSpecificsType primaryItemSpecificsType = new PrimaryItemSpecificsType();
			SecondaryItemSpecificsType secondaryItemSpecificsType = new SecondaryItemSpecificsType();
			AmenitiesType amenitiesType=new AmenitiesType();
			StoreCategoriesType storeCategoriesType=new StoreCategoriesType();
            
			List<Map<String, Object>> propertiesDetails = templateDetailsDAO.getArrangementDetails(templateDetailsRQ.getTemplateId());
		//	System.out.println("Arrangement List :::::"+propertiesDetails);
			// String ItemId = null;
			if(propertiesDetails!=null && !propertiesDetails.isEmpty()){
			for (Map<String, Object> entry : propertiesDetails) {
				if(entry.get("naechte")!=null)
				propertiesType.setNights(entry.get("naechte").toString());
				if(entry.get("personen")!=null)
				propertiesType.setPersons(entry.get("personen").toString());
				if(entry.get("hofesoda_zimmerart_id")!=null)
				propertiesType.setTypeOfRoom(entry.get("hofesoda_zimmerart_id").toString());
				if(entry.get("hofesoda_verpflegung_id")!=null)
				propertiesType.setCateringType(entry.get("hofesoda_verpflegung_id").toString());

				/*
				 * descriptionType.setOfferText(rs.getString("text"));
				 * descriptionType
				 * .setOfferAdditionalText(rs.getString("text2"));
				 */

				// ItemId = entry.get("ebayitemid").toString();
				// siteId = entry.get("ebaysiteid").toString();
			}
		}

			// String ItemId = null;
			List<Map<String, Object>> voucherDetails = templateDetailsDAO.getVoucherDetails(templateDetailsRQ.getTemplateId());
			if(voucherDetails!=null && !voucherDetails.isEmpty()){
			for (Map<String, Object> voucher : voucherDetails) {
				if(voucher.get("gutschein_text")!=null)
				descriptionType.setVoucherText((String) voucher.get("gutschein_text"));
				if(voucher.get("gueltigkeit_text")!=null)
				descriptionType.setVoucherValidityText((String) voucher.get("gueltigkeit_text"));
				if(voucher.get("gueltigkeit")!=null)
				templateDetailsRS.setVoucherValidity((Integer) voucher.get("gueltigkeit"));

				/*
				 * bind.put("gutschein_text",templateCreationRQ.getDescription().
				 * getVoucherText());
				 * bind.put("gueltigkeit_text",templateCreationRQ
				 * .getDescription().getVoucherValidityText());
				 * bind.put("gueltigkeit"
				 * ,templateCreationRQ.getVoucherValidity());
				 */

				// ItemId = entry.get("ebayitemid").toString();
				// siteId = entry.get("ebaysiteid").toString();
			}
			 }
			List<Map<String, Object>> offerDetails = templateDetailsDAO.getOfferTextDetails(templateDetailsRQ.getTemplateId());
			if(offerDetails!=null && !offerDetails.isEmpty()){
			for (Map<String, Object> offerDetail : offerDetails) {
				if(offerDetail.get("text")!=null)
				descriptionType.setOfferText((String) offerDetail.get("text"));
				if(offerDetail.get("text2")!=null)
				descriptionType.setOfferAdditionalText((String) offerDetail.get("text2"));
				if(offerDetail.get("ueberschrift")!=null)
				descriptionType.setTitle((String) offerDetail.get("ueberschrift"));
				if(offerDetail.get("ueberschrift2")!=null)
				descriptionType.setSubTitle((String) offerDetail.get("ueberschrift2"));

			}
			
			 }
			List PicsList = pictureDetailsType.getPictureURL();
			List<Map<String, Object>> itemPicsDetails = templateDetailsDAO.getItemPictures(templateDetailsRQ.getTemplateId());
			if(itemPicsDetails!=null && !itemPicsDetails.isEmpty()){
			for (Map<String, Object> itemPicsDetail : itemPicsDetails) {
				if(itemPicsDetail.get("auktionbildpath")!=null)
				PicsList.add((String) itemPicsDetail.get("auktionbildpath"));

			}
			 }
			List DesPicsList = descriptionPicturesType.getDescriptionPictureURL();
			List<Map<String, Object>> descPicDetails = templateDetailsDAO.getDescriptionPictures(templateDetailsRQ.getTemplateId());
			if(descPicDetails!=null && !descPicDetails.isEmpty()){
			for (Map<String, Object> descPicDetail : descPicDetails) {
				if(descPicDetail.get("image_path")!=null)
				DesPicsList.add((String) descPicDetail.get("image_path"));

			}
			}
			List OfferSliderList = descriptionPicturesType.getOfferSliderPictureURL();
			List<Map<String, Object>> offerSliderPicDetails = templateDetailsDAO.getOfferSliderPictures(templateDetailsRQ.getTemplateId());
			if(offerSliderPicDetails!=null && !offerSliderPicDetails.isEmpty()){
			for (Map<String, Object> offerSliderPicDetail : offerSliderPicDetails) {
				// System.out.println("Inside Offer sliderrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"+
				// offerSliderPicDetail);
				if(offerSliderPicDetail.get("image_path")!=null)
				OfferSliderList.add((String) offerSliderPicDetail.get("image_path"));

			}
			 }
			///List ObjectSliderList = descriptionPicturesType.getObjectSliderPictureURL();
			//List<Map<String, Object>> objectSliderPicDetails = templateDetailsDAO.getObjectSliderPictures(templateDetailsRQ.getTemplateId());
			/*if(objectSliderPicDetails!=null && !objectSliderPicDetails.isEmpty()){
			for (Map<String, Object> objectSliderPicDetail : objectSliderPicDetails) {
				//if(objectSliderPicDetail.get("image_path")!=null)
				//ObjectSliderList.add((String) objectSliderPicDetail.get("image_path"));

			}
			 }*/
			//descriptionPicturesType.setLogoPictureURL(templateDetailsDAO.getLogoPicture(templateDetailsRQ.getTemplateId()));

			//List descriptionPics = descriptionPicturesType.getDescriptionPictureURL();
			// pictureDetailsType.getPictureURL().addAll(PicsList);
			pictureDetailsType.setGalleryURL(templateDetailsDAO.getGalleryPicture(templateDetailsRQ.getTemplateId()));
			//pictureDetailsType.setVoucherPictureURL(templateDetailsDAO.getVoucherPicture(templateDetailsRQ.getTemplateId()));
			// descriptionPicturesType.getDescriptionPictureURL().addAll(DesPicsList);
			// System.out.println("Before setting to the objectttttttttttttttttttt"+OfferSliderList);
			// descriptionPicturesType.getOfferSliderPictureURL().addAll(OfferSliderList);
			// descriptionPicturesType.getObjectSliderPictureURL().addAll(ObjectSliderList);
            
			int ebayoptionen = templateDetailsDAO.getAdditionalOption(templateDetailsRQ.getTemplateId());
			// System.out.println("additional optionsssssssssssssssssssssssssssssssssssssssssssssssss::::::::::::::::"+
			// ebayoptionen);
			String ebayoptionen_bin;
			if (ebayoptionen != 0) {

				ebayoptionen_bin = new StringBuffer(Integer.toBinaryString(ebayoptionen)).reverse().toString();
				try {
					if ((Integer.parseInt(ebayoptionen_bin.substring(0, 1)) == 1)) {						
						additionalOptionsType.getOptionValue().add("1");

					}
					if ((Integer.parseInt(ebayoptionen_bin.substring(1, 2)) == 1)) {
						additionalOptionsType.getOptionValue().add("2");
					}

					if ((Integer.parseInt(ebayoptionen_bin.substring(2, 3)) == 1)) {
						additionalOptionsType.getOptionValue().add("4");
					}

					if ((Integer.parseInt(ebayoptionen_bin.substring(3, 4)) == 1)) {
						additionalOptionsType.getOptionValue().add("8");
					}

					if ((Integer.parseInt(ebayoptionen_bin.substring(4, 5)) == 1)) {
						additionalOptionsType.getOptionValue().add("16");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			String primarycatid=templateDetailsDAO.getPrimaryCategory(templateDetailsRQ.getTemplateId());
			primaryCategoryDetailsType.setPrimaryCategoryId(primarycatid);
			if(primarycatid!=null)
			primaryCategoryDetailsType.setPrimaryCategoryName(templateDetailsDAO.getCategoryName(templateDetailsRQ.getObjectId(),primarycatid,templateDetailsRS.getSiteId()));
			
			String secondaryCatid=templateDetailsDAO.getSecondaryCategory(templateDetailsRQ.getTemplateId());
			secondaryCategoryDetailsType.setSecondaryCategoryId(secondaryCatid);
			
			if(secondaryCatid!=null)
			secondaryCategoryDetailsType.setSecondaryCategoryName(templateDetailsDAO.getCategoryName(templateDetailsRQ.getObjectId(),secondaryCatid,templateDetailsRS.getSiteId()));
			// primaryCategoryDetailsType.

			categoriesType.setPrimaryCategoryDetails(primaryCategoryDetailsType);
			categoriesType.setSecondaryCategoryDetails(secondaryCategoryDetailsType);
			// nameValueListType

			List<Map<String, Object>> specsMap = templateDetailsDAO.getItemSpecIds(templateDetailsRQ.getTemplateId());
			List<Map<String, Object>> specNames = null;
			List<Map<String, Object>> specValues = null;
			if(!specsMap.isEmpty())
			for (Map<String, Object> specMap : specsMap) {
				if (specMap!=null && (Integer) specMap.get("categoryLevel") == 1) {
					specNames = templateDetailsDAO.getItemSpecNames((Integer) specMap.get("id"));
					if(specNames!=null && !specNames.isEmpty()){

					for (Map<String, Object> specName : specNames) {
						int item_specID = 0;
						NameValueListType nameValueListType = new NameValueListType();
						if(specName.get("name")!=null)
						nameValueListType.setName((String) specName.get("name"));
						item_specID = (Integer) specName.get("id");
						specValues = templateDetailsDAO.getItemSpecValues(item_specID);
						if(specValues!=null && !specValues.isEmpty()){
						for (Map<String, Object> specValue : specValues) {
							if(specValue.get("value")!=null)
							nameValueListType.getValue().add((String) specValue.get("value"));
							// nameValueListType.getValue().add((String)
							// specValue.get("value"));
							if (specValue!=null && specValue.get("svalue") != null) {								
								nameValueListType.setSValue((String) specValue.get("svalue"));
							}

						}
					}
						primaryItemSpecificsType.getNameValueList().add(nameValueListType);

					}
				}
					
					
				}
                else if ((Integer) specMap.get("categoryLevel") == 2){
					
						specNames = templateDetailsDAO
								.getItemSpecNames((Integer) specMap.get("id"));
						if(specNames!=null && !specNames.isEmpty()){
						for (Map<String, Object> specName : specNames) {
							int item_specID = 0;
							NameValueListType nameValueListType = new NameValueListType();
							if(specName.get("name")!=null)
							nameValueListType.setName((String) specName.get("name"));
							item_specID = (Integer) specName.get("id");
							specValues = templateDetailsDAO.getItemSpecValues(item_specID);
							if(specValues!=null && !specValues.isEmpty()){
							for (Map<String, Object> specValue : specValues) {
								if(specValue.get("value")!=null)
								nameValueListType.getValue().add((String) specValue.get("value"));
								// nameValueListType.getValue().add((String)
								// specValue.get("value"));
								if (specValue!=null && specValue.get("svalue") != null) {
									if(specValue.get("svalue")!=null)
									nameValueListType.setSValue((String) specValue.get("svalue"));
								}

							}
						}
							secondaryItemSpecificsType.getNameValueList().add(nameValueListType);

						
			}
                }
                }
			}
			
			//Amenities setting
			try{
			List<Map<String, Object>> amenitiesList = templateDetailsDAO.getAmenities(templateDetailsRQ.getTemplateId());
			// String ItemId = null;
			if(amenitiesList!=null && !amenitiesList.isEmpty()){
			for (Map<String, Object> entry : amenitiesList) {
				if(entry.get("rubrik_id")!=null)
				amenitiesType.getAmenityId().add((String)entry.get("rubrik_id").toString());				
			
			}
			}
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			String currency=templateDetailsDAO.getCurrency(templateDetailsRS.getSiteId());	
			List<String> currencySet=new ArrayList(); 
			currencySet.add("EUR");
			currencySet.add("GBP");
			currencySet.add("AUD");
			currencySet.add("USD");
			currencySet.add("CHF");
			boolean shopstatus=false;
			//Shop setting
		try{	
	    List<Map<String, Object>> shopdetails = templateDetailsDAO.getShopDetails(templateDetailsRQ.getTemplateId());
	    
		if(shopdetails!=null && !shopdetails.isEmpty()){
		for (Map<String, Object> shopdetail : shopdetails) {
			
			int shopObject=Integer.parseInt(shopdetail.get("ShopObjectID").toString());
			if(shopObject>0){
				shopstatus=true;
				templateDetailsRS.setSellerObject(shopObject);
				templateDetailsRS.setSellerAccount(templateDetailsDAO.getShopAccountObjectName(shopObject));
				String paypalMail=templateDetailsDAO.getPaypalAccount(shopObject,templateDetailsRS.getSiteId());
				if(paypalMail.equals("")){
					String selfPaypal=templateDetailsDAO.getSelfServicePaypalAccount(templateDetailsRQ.getObjectId(),templateDetailsRS.getSiteId());
					if(selfPaypal.equals("")){
						if(currencySet.contains(currency)){
							templateDetailsRS.setPayeeAccount(currency.toLowerCase()+"@cultuzz.com");
							
						}
					}else
						templateDetailsRS.setPayeeAccount(selfPaypal);
				}else
				templateDetailsRS.setPayeeAccount(paypalMail);
				
				//array("EUR", "GBP", "AUD", "USD", "CHF");
			}
			
			if(shopdetail.get("ShopObjectID")!=null)
			templateDetailsRS.setShopObjectId(shopdetail.get("ShopObjectID").toString());
			if(shopdetail.get("ShopCategoryID")!=null)
			storeCategoriesType.setStoreCategoryId(shopdetail.get("ShopCategoryID").toString());
			if(shopdetail.get("ShopCategory2ID")!=null)
			storeCategoriesType.setStoreCategory2Id(shopdetail.get("ShopCategory2ID").toString());
			templateDetailsRS.setStoreCategories(storeCategoriesType);
		}
		 }
		}
		catch(Exception e)
		{
			LOGGER.error("this is occured at shop object dealing"+e);
			//e.printStackTrace();
		}
		
		int collectionObjectid=templateDetailsDAO.getCollectionAccountObjectId(templateDetailsRQ.getTemplateId());
		if(collectionObjectid>0 && !shopstatus){
			templateDetailsRS.setSellerObject(collectionObjectid);
			templateDetailsRS.setSellerAccount(templateDetailsDAO.getCollectionAccountObjectName(collectionObjectid));
			
			String paypalMail=templateDetailsDAO.getPaypalAccount(collectionObjectid,templateDetailsRS.getSiteId());
			if(paypalMail.equals("")){
				String selfPaypal=templateDetailsDAO.getSelfServicePaypalAccount(templateDetailsRQ.getObjectId(),templateDetailsRS.getSiteId());
				if(selfPaypal.equals("")){
					if(currencySet.contains(currency)){
						templateDetailsRS.setPayeeAccount(currency.toLowerCase()+"@cultuzz.com");
						
					}
				}else
					templateDetailsRS.setPayeeAccount(selfPaypal);
			}else
			templateDetailsRS.setPayeeAccount(paypalMail);
			
			
		}else if(!shopstatus){
			templateDetailsRS.setSellerObject(templateDetailsRQ.getObjectId());
			templateDetailsRS.setSellerAccount(templateDetailsDAO.getObjectSellerName(templateDetailsRQ.getObjectId()));
			//templateDetailsRS.setPayeeAccount(templateDetailsDAO.getPaypalAccount(templateDetailsRQ.getObjectId(),templateDetailsRS.getSiteId()));
			//System.out.println("mydetails*********"+collectionObjectid+"********"+templateDetailsRS.getSiteId());
			String paypalMail=templateDetailsDAO.getPaypalAccount(templateDetailsRQ.getObjectId(),templateDetailsRS.getSiteId());
			if(paypalMail.equals("")){
				String selfPaypal=templateDetailsDAO.getSelfServicePaypalAccount(templateDetailsRQ.getObjectId(),templateDetailsRS.getSiteId());
				if(selfPaypal.equals("")){
					if(currencySet.contains(currency)){
						templateDetailsRS.setPayeeAccount(currency.toLowerCase()+"@cultuzz.com");
						
					}
				}else
					templateDetailsRS.setPayeeAccount(selfPaypal);
			}else
			templateDetailsRS.setPayeeAccount(paypalMail);
		}
		
			primaryCategoryDetailsType.setPrimaryItemSpecifics(primaryItemSpecificsType);
			secondaryCategoryDetailsType.setSecondaryItemSpecifics(secondaryItemSpecificsType);

			templateDetailsRS.setCategories(categoriesType);
			templateDetailsRS.setPictureDetails(pictureDetailsType);
			templateDetailsRS.setDescription(descriptionType);
			templateDetailsRS.setProperties(propertiesType);
			templateDetailsRS.setDescriptionPictures(descriptionPicturesType);
			templateDetailsRS.setAdditionalOptions(additionalOptionsType);
			templateDetailsRS.setAmenities(amenitiesType);		
			
				
			templateDetailsRS.setCurrency(currency);
		    	 
		    	/* if(currency != null){	
		    	   if(currency.equalsIgnoreCase("EUR")){
		    		   templateDetailsRS.setCurrency(CurrencyCodeType.EUR);
		    		   
		    	   }else if(currency.equalsIgnoreCase("USD")){
		    		   
		    		   templateDetailsRS.setCurrency(CurrencyCodeType.USD);	    		   
		    	   }else if(currency.equalsIgnoreCase("GBP")){
		    		   
		    		   templateDetailsRS.setCurrency(CurrencyCodeType.GBP);	    	   
		    	   }else if(currency.equalsIgnoreCase("CHF")){
		    		   
		    		   templateDetailsRS.setCurrency(CurrencyCodeType.CHF);	    	   
		    	   }else if(currency.equalsIgnoreCase("AUD")){
		    		   
		    		   templateDetailsRS.setCurrency(CurrencyCodeType.AUD);	    	   
		    	   }else if(currency.equalsIgnoreCase("INR")){
		    		   
		    		   templateDetailsRS.setCurrency(CurrencyCodeType.INR);	    	   }
		    	 }*/
		    
			
		
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		// templateDetailsRS.get
		// TemplateDetailsRS
		// templateDetailsRS=detailsDAOImpl.getTemplateDetails(templateDetailsRQ.getTemplateId());

		/*
		 * CategoriesType categories=templateCreationRQ.getCategories(); int
		 * objectid=0; if(templateCreationRQ.isSetObjectId())
		 * objectid=templateCreationRQ.getObjectId();
		 * 
		 * categories.getPrimaryCategoryId(); int
		 * vorlageId=templateModuleDAO.saveVorlageData(templateCreationRQ);
		 * 
		 * if(vorlageId>0) templateModuleDAO.saveVocherData(vorlageId,
		 * templateCreationRQ);
		 * 
		 * if(templateCreationRQ.getProductId()==0 && vorlageId>0)
		 * templateModuleDAO.saveVorlageArrangementData(vorlageId,
		 * templateCreationRQ);
		 * 
		 * PictureDetailsType
		 * itemPicturesAndGallaryPicture=templateCreationRQ.getPictureDetails();
		 * DescriptionPicturesType
		 * descriptionPictures=templateCreationRQ.getDescriptionPictures();
		 * 
		 * if(vorlageId>0) templateModuleDAO.savePictureDetails(vorlageId,
		 * itemPicturesAndGallaryPicture,objectid);
		 * 
		 * if(vorlageId>0)
		 * templateModuleDAO.saveDescriptionPictures(vorlageId,descriptionPictures
		 * ,objectid);
		 * 
		 * 
		 * ItemSpecificsType specificname=templateCreationRQ.getItemSpecifics();
		 * List<NameValueListType>
		 * nameValueList=specificname.getNameValueList();
		 * 
		 * 
		 * if(vorlageId!=0 && categories.isSetPrimaryCategoryId()){
		 * templateModuleDAO
		 * .saveItemCategories(vorlageId,categories.getPrimaryCategoryId(),1); }
		 * if(vorlageId!=0 && categories.isSetSecondaryCategoryId()){
		 * templateModuleDAO
		 * .saveItemCategories(vorlageId,categories.getSecondaryCategoryId(),2);
		 * }
		 * 
		 * /*if(nameValueList.size()>0 && categories.isSetPrimaryCategoryId()){
		 * 
		 * for(NameValueListType nameValues: nameValueList){
		 * 
		 * nameValues.getName(); List <String> values=nameValues.getValue();
		 * 
		 * templateModuleDAO.saveItemSpecifics(nameValues.getName(),values);
		 * 
		 * }
		 * 
		 * }
		 * 
		 * 
		 * 
		 * templateCreationRS.setAck("Fail");
		 */
		return templateDetailsRS;

	}

}