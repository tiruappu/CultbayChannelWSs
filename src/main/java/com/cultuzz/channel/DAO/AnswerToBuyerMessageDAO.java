package com.cultuzz.channel.DAO;

import com.cultuzz.channel.XMLpojo.AnswerBuyerMessageRQ;

public interface AnswerToBuyerMessageDAO {
	public int saveDetails(String messageId,String answer,String sellerName);
	public boolean checkMessageId(String msgId,int objectId);
	public boolean checkMessageIdWithDetails(String msgId, AnswerBuyerMessageRQ answerBuyerMessageRQ);
}
