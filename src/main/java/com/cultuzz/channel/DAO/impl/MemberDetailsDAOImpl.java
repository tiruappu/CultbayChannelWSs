package com.cultuzz.channel.DAO.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cultuzz.channel.DAO.MemberDetailsDAO;
import com.cultuzz.channel.jdbcTemplate.JDBCTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MemberDetailsDAOImpl implements MemberDetailsDAO {

	@Autowired
	@Qualifier("ebayTemplate")
	private JDBCTemplate ebayJdbcTemplate;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Autowired
	@Qualifier("cusebedaTemplate")
	private JDBCTemplate cusebedaJdbcTemplate;

	int isAnswred = 0;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MemberDetailsDAOImpl.class);

	public List<Map<String, Object>> getQuestions(String msgId) {
		// To get questions for the message Id...
		LOGGER.debug("MessageId is Inside Question Method is:::" + msgId);
		List<Map<String, Object>> questionDetails = null;
		String question = null;
		int count = 0;
		String answer = null;

		try {
			jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();
			String sqlForQuestion = "SELECT meine_nachrichten.message,meine_nachrichten.beantwortet FROM ebay_messages.meine_nachrichten WHERE meine_nachrichten.message_id = ? ";
			//String messageIdQuery = "select count(*) from ebay_messages.meine_nachrichten where message_id=?";
			LOGGER.debug("sqlForQuestion Query:::" + sqlForQuestion);

//			count = jdbcTemplate.queryForInt(messageIdQuery,
//					new Object[] { msgId });
//			LOGGER.debug("messageId count:::" + count);
//			
			questionDetails = jdbcTemplate.queryForList(sqlForQuestion,
					new Object[] { msgId });

			
				LOGGER.debug("Question Details1 :::" + questionDetails);
				
					LOGGER.debug("12");
					return questionDetails;
		} catch (Exception e) {
			LOGGER.debug("Inside Exception case for question...");
			LOGGER.debug("Inside Exception case for answer...");
			//question = "";
			return questionDetails;
		}
	}

	// public String getAnswer(int msgId) {
	// // To get answer for the message Id...
	//
	// LOGGER.debug("MessageId is Inside Answer Method is:::" + msgId);
	// String answer = null;
	// try {
	// jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
	// String sqlForAnswer =
	// "SELECT nachrichten_x_antworten.response FROM ebay_messages.nachrichten_x_antworten WHERE nachrichten_x_antworten.message_id = ?";
	// LOGGER.debug("sqlForAnswer Query:::" + sqlForAnswer);
	//
	// answer = jdbcTemplate.queryForObject(sqlForAnswer,
	// new Object[] { msgId }, String.class);
	//
	// isAnswred = 1;
	//
	// LOGGER.debug("Answer inside getAnswer is:::" + answer);
	// } catch (Exception e) {
	// LOGGER.debug("Inside Exception case for answer...");
	// answer = "";
	//
	// }
	// return answer;
	// }

	public List<Map<String, Object>> getAllDetails(String msgId, int isAnswered) {

		LOGGER.debug("IsAnswered inside getAllDetails:::" + isAnswered);

		List<Map<String, Object>> fullDetails = null;
		String sqlForDetails = null;
		String from = null;
		String datum = null;
		String order = null;
		String	answer=null;
		String bedingung=null;
		int beantwortet = 0;

		// LOGGER.debug("isAnswred value is:::"+isAnswred);

		LOGGER.debug("MessageId is Inside getAllDetails Method is:::" + msgId);

		try {
			jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();

			if (isAnswered == 0) {

				LOGGER.debug("Inside isAnswred == 0 block");

				from = " ebay_messages.meine_nachrichten";
				beantwortet = 0;
				datum = " meine_nachrichten.creation_date";
				order = " ORDER BY ebayitemid, ebayname, creation_date desc";
				bedingung= "";
			} else if (isAnswered == 1) {

				LOGGER.debug("Inside isAnswred == 1 block");

				from = " ebay_messages.meine_nachrichten, ebay_messages.nachrichten_x_antworten";
				beantwortet = 1;
				datum = " ebay_messages.nachrichten_x_antworten.response_date";
				order = " ORDER BY ebayitemid, ebayname, creation_date desc";
				answer = " ebay_messages.nachrichten_x_antworten.response";
				bedingung = " AND nachrichten_x_antworten.message_id = meine_nachrichten.message_id AND sichtbar = 1";
			}

			sqlForDetails = "SELECT meine_nachrichten.message_id,"
					+ answer
					+ ", meine_nachrichten.ebayname,"
					+ " meine_nachrichten.subject,"
					+ " meine_nachrichten.message,"
					+ " meine_nachrichten.ebayitemid," + datum + " FROM "
					+ from + " WHERE beantwortet=" + beantwortet
					+ bedingung
					+ " AND meine_nachrichten.message_id= ?";

			LOGGER.debug("Sql Query Details:::" + sqlForDetails);

			fullDetails = jdbcTemplate.queryForList(sqlForDetails,
					new Object[] { msgId });

		} catch (Exception e) {

			e.printStackTrace();
			LOGGER.debug("Inside Exception case of getAllDetails method");
			fullDetails = null;
		}

		// try {
		// jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		//
		// if(isAnswred == 0){
		// sqlForDetails =
		// "SELECT meine_nachrichten.ebayname,meine_nachrichten.subject,meine_nachrichten.message,meine_nachrichten.ebayitemid"
		// +
		// " FROM ebay_messages.meine_nachrichten, ebay_messages.nachrichten_x_antworten WHERE"
		// +
		// " meine_nachrichten.message_id=? AND nachrichten_x_antworten.message_id = meine_nachrichten.message_id AND sichtbar = 1 AND beantwortet=1";
		//
		// LOGGER.debug("sqlForQuestion Query:::" + sqlForDetails);
		// }else if(isAnswred == 1){
		// LOGGER.debug("sqlForQuestion Query:::" + sqlForDetails);
		// }
		// fullDetails = jdbcTemplate.queryForList(sqlForDetails,
		// new Object[] { msgId });
		// } catch (Exception e) {
		//
		// e.printStackTrace();
		// LOGGER.debug("Inside Exception case of getAllDetails method");
		// fullDetails = null;
		// }
		return fullDetails;

	}

	public boolean validMessageId(String msgId) {

		// String messageId =
		// "select count(*) from ebay_messages.meine_nachrichten where message_id=?";
		int count = 0;
		boolean validationStatus = false;
		try {
			jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();

			String messageIdQuery = "select count(*) from ebay_messages.meine_nachrichten where message_id=?";
			LOGGER.debug("sqlForQuestion Query:::" + messageIdQuery);

			count = jdbcTemplate.queryForInt(messageIdQuery,
					new Object[] { msgId });
			LOGGER.debug("Count for message validation is:::" + count);
			if (count > 0) {
				validationStatus = true;
			}

		} catch (Exception e) {

			e.printStackTrace();
			LOGGER.debug("Inside Exception case of getAllDetails method");
			validationStatus = false;
		}
		LOGGER.debug("validation of status:::" + validationStatus);
		return validationStatus;
	}

	public boolean checkMessageId(String msgId, int objectId) {

		LOGGER.debug("Message Id:::" +msgId );
		LOGGER.debug("Object Id:::" +objectId );
		
		boolean checkMessageIdStatus = false;
		try {
			String checkMessageIdSql = "select count(*) from ebay_messages.meine_nachrichten where message_id="
					+ msgId + " and cusebeda_objekt_id=?";

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

}
