package com.cultuzz.channel.DAO;

import java.util.List;
import java.util.Map;


/**
 * This class is used to get the Member Messages from DAO
 * @author sowmya
 *
 */

public interface ListOfMemberMessagesDAO {

	
	public List<Map<String, Object>> getMemberMessages(String objectId, String ItemId,String periodFrom,String periodTo,String upperLimit,String lowerLimit,String Status);
	
	public List<Map<String, Object>> getMemberMessagesForItemId(String ebayItemId,String periodFrom,String periodTo,String upperLimit,String lowerLimit,String status);
	
	public int getMemeberMessagesCount(String objectId, String ItemId,String periodFrom,String periodTo,String upperLimit,String lowerLimit,String Status);
	
	public int getMemberMessagesForItemIdCount(String ebayItemId,String periodFrom,String periodTo,String upperLimit,String lowerLimit,String status);
	
	public boolean validateItemId(String ItemId,String objectId);
	public boolean validateObjectId(String objectId,String itemId);
	public boolean checkForObjectValidity(String objektId) throws Exception;
	
	
}
