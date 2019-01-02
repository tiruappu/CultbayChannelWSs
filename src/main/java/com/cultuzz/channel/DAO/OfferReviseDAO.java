package com.cultuzz.channel.DAO;


import java.util.List;
import java.util.Map;

import com.cultuzz.channel.XMLpojo.OfferReviseRQ;
public interface OfferReviseDAO {

	public boolean checkIfAlreadyrevised(double price, String quantity, String time, String quantiyResriction,String itemId,double last_price);
 
	
	public String getPrice(String itemid);
	public String getRetailPrice(String itemid);
	public boolean checkEbayItemId(String itemId) ;
	public boolean checkRetailPrice(String itemId);
	
	//public int saveReviseDetails(int aucktionId, String endDate,float lastprice,double price, OfferReviseRQ offerReviseReq);
	public int saveReviseDetails(int aucktionId, String endDate,double lastprice,double price, String quantity,String quantityRes,int sourceId,OfferReviseRQ offerReviseReq, String retailPrice);
	
	public List<Map<String, Object>> getItemDetails(String ebayItemid) throws Exception;
	
	public boolean checkReviseDetails(long ebayItemid, int quantityRestriction,
			double price, String reviseTime, int quantity) throws Exception;
	
	public boolean checkObjectWithItemId(int objectId,String itemId);
	public String getEndDate(String itemId);
	public int getQuantityAuktion(String itemId);
	public boolean updateIdReviseItemPrice(int id,long insertedId);

	public int saveInReviseItemDetails(int id,int insertedId,String title,String subTitle,String htmlDescription,String offerText,String offerText2,String voucherValidityText,String AdditionalValidityText,String reviseTitle,String reviseSubTitle,String reviseHtmlDescription,String reviseOfferText,String reviseOfferText2,String reviseVoucherValidityText,String reviseAdditionalValidityText) throws Exception;
	
	
	public List<Map<String, Object>> getOfferDetails(int auktionId);
	
	
	public String getHtmlDescription(int auktionId,int insertedId);
	
	public int saveGalleryURL(int insertedId,String gallery,int auktionId,String objectId);
	
	public int saveAuktionURL(int insertedId,List<String> auktionurl,int auktionId,String objectId);
	public int getDuration(String ebayitemid);
	public int getQuantityRestriction(String ebayitemid);
	public boolean checkOutOfStockStatus(String ebayitemid);
	
}
