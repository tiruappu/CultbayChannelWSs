package com.cultuzz.channel.DAO;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.cultuzz.channel.XMLpojo.OfferCreationRQ;
import com.cultuzz.channel.template.pojo.Vorlage;

public interface OfferCreationDAO {

	public Vorlage getAuktionDetails(int templateId);
    public String getCusebeda_objekt_ort(int objectId);
    public int saveAuktion(OfferCreationRQ offerCreationRQ, Vorlage vorlage, String cusebeda_objekt_ort);
    public int saveAuktion_x_source(int offerId, int source, boolean repeat_status);
    public void saveAuktions_texte(int templateId, int offerId);
    public void saveAuction_x_collectionAccount(int offerId, int templateId, int collectionObjectId);
    public void saveAuction_x_shopCategory(int offerId, int shopObjectId, BigInteger shopCategory1, BigInteger shopCategory2);
    public boolean checkProductValidity(int productId, String startDate);
    public int getProductId(int templateId);
//    public int updateVorlageAuktions_texte(int auktion_texteId, int templateId);
    public List<Map<String, Object>> getAuktionPath(int objectId, int templateId);
    public String getGaleryPath(int objectId, int templateId);
    public int saveAuktion_x_pictures(int offerId, int templateId, int sourceId, int type, String url);
    public int getAuctionMasterTypeID(int templateId);
    public boolean checkShopObjectId(int objectId, int shopObjectId);
    public boolean checkCollectionObjectId(int objectId, int collectionObjectId);
//    public int saveAuktion_zusatz(int offerId, int auktion_texte_id);
    public int getVorlageShopObjectID(int templateId);
    public List<Map<String, Object>> getVorlage_x_ShopDetails(int templateId,int shopObjectId);
//    public int saveAuktion_texte(int templateId, int offerId);
    public List<Map<String,Object>> getOffersData(int objectid,Integer templateid);
}
