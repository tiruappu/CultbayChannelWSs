package com.cultuzz.channel.DAO;

import java.util.List;
import java.util.Map;

public interface OfferEndDAO {

	public List<Map<String, Object>> getItemDetails(String ebayItemId)
			throws Exception;

	public List<Map<String, Object>> getOfferDetails(int offerId)
			throws Exception;

	public boolean updateItemStatus(String ebayItemId, int offerId,int objectId)
			throws Exception;

	public void updateRepeatStatus(int offerId) throws Exception;

	public boolean checkForObjectValidity(int objektId) throws Exception;
	public  List<Map<String, Object>> getShoporCollectionObjektId(String ebayItemId);

}
