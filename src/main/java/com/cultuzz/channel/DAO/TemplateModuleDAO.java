package com.cultuzz.channel.DAO;

import java.util.List;

import com.cultuzz.channel.XMLpojo.AmenitiesType;
import com.cultuzz.channel.XMLpojo.DescriptionPicturesType;
import com.cultuzz.channel.XMLpojo.NameValueListType;
import com.cultuzz.channel.XMLpojo.PictureDetailsType;
import com.cultuzz.channel.XMLpojo.StoreCategoriesType;
import com.cultuzz.channel.XMLpojo.TemplateCreationRQ;
import com.cultuzz.channel.XMLpojo.TemplateEditRQ;

public interface TemplateModuleDAO {
	
	public boolean checkSiteId(int siteid);
	
	public boolean checkDesignTemplateId(int templateId);
	
	public boolean checkObjectId(int objectId);
	
	public boolean checkproductId(int objectid,int productId);
	
	public boolean checkPrimaryCategory(int primaryCategoryId,int objectid,int ebaysiteid);
	
	public boolean checkSecondaryCategory(int secondaryCategoryId,int objectid,int ebaysiteid);
	
	public boolean checkStoreCategoryId(long storePrimaryCategoryId,int shopobjectid);
	
	public boolean checkChildStoreCategoryId(long storeSecondaryCategoryid);
	
	public boolean checkTypeOfRoom(int typeOfRoomid);
	
	public boolean checkCateringType(int cateringTypeid);
	
	public boolean checkTemplateId(int templateid,int objectid);
	
	public boolean saveAmenities(int vorlageid,AmenitiesType amenities);
	
	public int saveItemCategories(int vorlageid,int categoryId,int level);
	public int saveItemSpecifics(int itemspecId,List<NameValueListType> namevalueList);
	public boolean saveItemSpecificNamesValues(int itemspecId,NameValueListType namevalueList);
	public boolean savePictureDetails(int vorlageid,PictureDetailsType itemPictures,int objectid);
	//public int saveItemPictures();
	public boolean saveDescriptionPictures(int vorlageid,DescriptionPicturesType descriptionPictures,int objectid);
	//public int saveOfferSliderPictures();
	//public int saveObjectSliderPictures();
	
	public int saveVorlageData(TemplateCreationRQ templateCreationRQ);
	
	public boolean saveVorlageArrangementData(int vorlageid,TemplateCreationRQ templateCreationRQ);
	
	public boolean saveVocherData(int vorlageid,TemplateCreationRQ templateCreationRQ);
	
	public boolean deleteTemplate(int templateid);
	
	public int getObjectId(int templateid);
	
	public boolean checkTemplateDeleteStatus(int templateid);
	
	public int getDescImagesCount(int templateid);
	
	public boolean checkCurrency(int ebaysiteId,String currency);
	
	public boolean saveVorlageSource(int templateid,int sourceid);
	public String getTemplateSiteid(int templateid);
	
	public int getDesignTemplateId(int templateid);
	public boolean checkAmenityId(int amenityId);
	
	public boolean updateVorlageData(TemplateEditRQ templateEditRq);
	public boolean updateVorlageArrangement(TemplateEditRQ templateEditRq);
	public boolean deleteAmenities(int vorlageid);
	//public boolean updateLogoPicture(TemplateEditRQ templateEditRq);
	public boolean deleteSpecValues(int specnameid);
	public boolean deleteSpecNames(int specid);
	public boolean deleteItemSpecs(int vorlageid,int categoryLevel);
	public boolean deleteDescPics(int vorlageid);
	public boolean editDescriptionPictures(int vorlageid,List<String> descUpics,int objectid);
	public boolean deleteObjectSliderPics(int vorlageid);
	public boolean editObjectSliderPics(int vorlageid,List<String> objSliderPics,int objectid);
	public boolean deleteOfferSliderPics(int vorlageid);
	public boolean editOfferSliderPics(int vorlageid,List<String> offerSliderPics,int objectid);
	public boolean deleteItemPictures(int vorlageid);
	public boolean editItemPictures(int vorlageid,List<String> itemPics,int objectid);
	public boolean updateGalleryPic(TemplateEditRQ templateEditRq);
	//public boolean updateVoucherPic(TemplateEditRQ templateEditRq);
	public boolean updateVoucherData(TemplateEditRQ templateEditRq);
	public int getObjectid(int templateid);
	public boolean saveShopCategories(StoreCategoriesType storeCategories,int templateid,int shopObjectid);
	public double getStartPrice(int templateid);
	public double getEbaysofortkauf(int templateid);
	public int getAuctionmasterTypeid(int templateid);
	public int getShopObjectId(int vorlageid);
	public boolean updateShopCategories(TemplateEditRQ templateEditRQ,int vorlageid,int shopObjectid,StoreCategoriesType storeCategories);
	public void deleteShopCategories(int vorlageid);
	public int getVorlageId(long itemid);
	public int getThemeId(int objId);
	public int getLangidByItemid(long ebayitemid);
	public int saveCheVoucherPicture(int vorlageid,String imagepath,int objectid);
	public int saveVoucherThemeId(int objectid,int themeid);
	public int saveUpdateThemeId(int objectid,int themeid);
	public boolean checkLiveOffers(int templateid);
	public boolean updateForRemoveFields(TemplateEditRQ templateEditRQ);
	public double getRetailPrice(int templateId);
	public boolean saveHeaderFooterData(int vorlageid,String categoryid,String headerurl,String footerurl);
	public boolean saveCollectionObject(int templateid,int collectionObjectid);
	public boolean checkProductValidity(int productId, String startDate);
}

