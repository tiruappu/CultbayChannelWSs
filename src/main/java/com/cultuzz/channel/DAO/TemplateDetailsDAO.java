package com.cultuzz.channel.DAO;

import com.cultuzz.channel.XMLpojo.TemplateDetailsRS;

import java.util.List;

public interface TemplateDetailsDAO {

	public TemplateDetailsRS getTemplateDetails(int templateId);
	public List getArrangementDetails(int templateId);
	public List getVoucherDetails(int templateId);
	public String getGalleryPicture(int templateId);
	public String getVoucherPicture(int templateId);
	public List getItemPictures(int templateId);
	public List getDescriptionPictures(int templateId);
	public List getOfferSliderPictures(int templateId);
	//public List getObjectSliderPictures(int templateId);
	public List getOfferTextDetails(int templateId);
	public String getPrimaryCategory(int templateId);
	public String getSecondaryCategory(int templateId);
	public int getAdditionalOption(int templateId);
	public boolean checkTemplateId(int templateId,int objectId);
	public List getItemSpecIds(int templateId);
	public List getItemSpecNames(int specId);
	public List getItemSpecValues(int nameId);
	public List getAmenities(int templateId);
	public String getCurrency(int ebaysiteId);
	public String getLogoPicture(int templateId);
	public List getShopDetails(int templateId);


	public String getShopAccountObjectName(int objectid);
	public String getCollectionAccountObjectName(int objectid);
	public String getObjectSellerName(int objectid);
	public String getPaypalAccount(int objectid,int marketplace);
	public Integer getCollectionAccountObjectId(int templateid);
	public String getSelfServicePaypalAccount(int objectid,int marketplace);
	public String getCategoryName(int objectid,String categoryid,int siteid);
	//public void testmethod();
	/*public boolean checkSiteId(int siteid);
	
	public boolean checkDesignTemplateId(int templateId);
	
	public boolean checkObjectId(int objectId);
	
	public boolean checkproductId(int objectid,int productId);
	
	public boolean checkPrimaryCategory(int primaryCategoryId,int objectid,int ebaysiteid);
	
	public boolean checkSecondaryCategory(int secondaryCategoryId,int objectid,int ebaysiteid);
	
	public boolean checkPrimaryStoreCategoryId(int storePrimaryCategoryId);
	
	public boolean checkSecondaryStoreCategoryId(int storeSecondaryCategoryid);
	
	public boolean checkTypeOfRoom(int typeOfRoomid);
	
	public boolean checkCateringType(int cateringTypeid);
	
	public boolean checkTemplateId(int templateid);
	
	public boolean saveAmenities(int vorlageid,AmenitiesType amenities);
	
	public int saveItemCategories(int vorlageid,int categoryId,int level);
	public int saveItemSpecifics(String specificname,List<String> values);
	
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
	
	public boolean checkTemplateDeleteStatus(int templateid);*/
	
	

}