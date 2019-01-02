package com.cultuzz.channel.DAO.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.DAO.AnswerToBuyerMessageDAO;
import com.cultuzz.channel.XMLpojo.AnswerBuyerMessageRQ;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.OfferEndItemRQ;
import com.cultuzz.channel.XMLpojo.OfferEndItemRS;
import com.cultuzz.channel.jdbcTemplate.JDBCTemplate;

@Component
public class AnswerToBuyerMessageDAOImpl implements AnswerToBuyerMessageDAO 
{
	
	@Autowired
	@Qualifier("ebayTemplate")
	private JDBCTemplate ebayJdbcTemplate;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	@Qualifier("cusebedaTemplate")
	private JDBCTemplate cusebedaJdbcTemplate;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AnswerToBuyerMessageDAOImpl.class);

	public int saveDetails(String messageId,String answer,String sellerName){
		
		int id = 0;

		LOGGER.debug("Inside save details Method.....");

		namedJdbcTemplate = cusebedaJdbcTemplate.getNamedParameterJdbcTemplate();

		StringBuffer insertDetails = new StringBuffer(
				"insert into ebay_messages.nachrichten_x_source(message_id,answer,userId)");
		insertDetails
				.append(" values(:message_id,:answer,:userId)");

		Map<String, Object> bindData = new HashMap<String, Object>();
		bindData.put("message_id",messageId);

		bindData.put("answer",answer);
		bindData.put("userId",sellerName);
		
		
		SqlParameterSource paramSource = new MapSqlParameterSource(bindData);

		KeyHolder keyHolder = new GeneratedKeyHolder();

		this.namedJdbcTemplate.update(insertDetails.toString(),
				paramSource, keyHolder);
		id = keyHolder.getKey().intValue();

		return id;
	}
	public boolean checkMessageId(String msgId, int objectId) {

		LOGGER.debug("Message Id:::" +msgId );
		LOGGER.debug("Object Id:::" +objectId );
		
		boolean checkMessageIdStatus = false;
		try {
			String checkMessageIdSql = "select count(*) from ebay_messages.meine_nachrichten where message_id="
					+ msgId + " and cusebeda_objekt_id=? ";

			LOGGER.debug("checkMessageIdSql Query:::" +checkMessageIdSql );

			
			jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();
			int checkMessageIdCount = jdbcTemplate.queryForInt(
					checkMessageIdSql, new Object[] { objectId });

			LOGGER.debug("Count for Inside checkEbayItemId method with itemID..."
					+ checkMessageIdCount);

			if (checkMessageIdCount > 0) {
				checkMessageIdStatus = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			checkMessageIdStatus = false;
		}

		LOGGER.debug("checkMessageIdStatus for checkMessageId is:::"
				+ checkMessageIdStatus);
		return checkMessageIdStatus;
	}
	
	public boolean checkMessageIdWithDetails(String msgId, AnswerBuyerMessageRQ answerBuyerMessageRQ) {
		
			String message = answerBuyerMessageRQ.getMemberMessages().getMemberMessage().getQuestion();
			String buyername = answerBuyerMessageRQ.getMemberMessages().getMemberMessage().getBuyerName();
			String subject = answerBuyerMessageRQ.getMemberMessages().getMemberMessage().getSubject();
 
		boolean checkMessageIdDetailsStatus = false;
		try {
			String checkMessageIdSql = "select count(*) from ebay_messages.meine_nachrichten where message='"+message+"'  and message_id=?";

			LOGGER.debug("checkMessageIdSql Query:::" +checkMessageIdSql );
			jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();
			int checkMessageIdCount = jdbcTemplate.queryForInt(
					checkMessageIdSql, new Object[] { msgId });

			if (checkMessageIdCount > 0) {
				checkMessageIdDetailsStatus = true;
			}
		
			return checkMessageIdDetailsStatus;
		}catch (Exception e) {
			e.printStackTrace();
			checkMessageIdDetailsStatus = false;
			return checkMessageIdDetailsStatus;
		}
	}
	
}
