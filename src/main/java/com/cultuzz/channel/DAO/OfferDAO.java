package com.cultuzz.channel.DAO;


import java.util.List;
import java.util.Map;

import com.cultuzz.channel.XMLpojo.OfferDetailsRS;
import com.cultuzz.channel.util.CommonUtil;



public interface OfferDAO {

       OfferDetailsRS getOfferDetails(int offerId);
       String getCurrency(int siteId);
       int getQuantityPurchased(long itemId);
       int getNoOfViews(int offerId);
       String getDescription(int offerId);
       boolean checkOfferId(int offerId, int objectId); 
       boolean checkItemId(long itemId);
       boolean checkItemId(int offerId, long itemId);
       long getItemWatchCount(long itemid);
       List<Map<String,Object>> getTransactionData(String itemid);
       List<OfferDetailsRS> getOfferDetailsAll(String condition,CommonUtil commonutils);
       List<Map<String,Object>> getAllCurrencies();
       List<Map<String,Object>> getTimeZones();
       String getPayeeAccount(long auktionid);
       String getProductName(Integer productid,Integer langid);
       public String getFuturePayeeAccount(long auktionid,int ebaysiteid);
}
