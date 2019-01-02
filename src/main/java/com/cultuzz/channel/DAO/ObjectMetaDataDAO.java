package com.cultuzz.channel.DAO;

import java.util.List;
import java.util.Map;

public interface ObjectMetaDataDAO {


		public List<Map<String, Object>> getSellerAccounts(int objectId);
		public List<Map<String,Object>> getCollectionAccounts(int objectId);
		public String getObjectName(int objectId);
		public String getPaypalEmail(long objectId, int siteId);
		public List<Map<String,Object>> getProductsList(int objectId, int langId);
		public List<Map<String,Object>> getValiditiesList(long productId);
		public List<Map<String,Object>> getEbayCategories(int objectId, int siteId);
		public List<Map<String,Object>> getShopCategories(long shopObjectId);
		 public boolean checkProductValidity(long productId, String startDate);
}
