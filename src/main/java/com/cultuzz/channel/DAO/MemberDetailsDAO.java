package com.cultuzz.channel.DAO;

import java.util.List;
import java.util.Map;

public interface MemberDetailsDAO {
	public List<Map<String, Object>> getQuestions(String msgId);
	//public String getAnswer(int msgId);	
	//public boolean checkEbayItemId(String itemId) ;
	public List<Map<String, Object>> getAllDetails(String msgId,int IsAnswered);
	public boolean validMessageId(String msgId);
	public boolean checkMessageId(String msgId,int objectId);
}
