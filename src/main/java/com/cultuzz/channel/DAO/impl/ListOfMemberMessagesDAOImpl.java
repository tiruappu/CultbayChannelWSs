package com.cultuzz.channel.DAO.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.DAO.ListOfMemberMessagesDAO;
import com.cultuzz.channel.helper.impl.OfferEndHelperImpl;
import com.cultuzz.channel.jdbcTemplate.JDBCTemplate;


@Component
/**
 * This class is used to connect to database to retrieve the member messages
 * 
 * @author sowmya
 * 
 */
public class ListOfMemberMessagesDAOImpl implements ListOfMemberMessagesDAO {

	@Autowired
	@Qualifier("ebayTemplate")
	private JDBCTemplate ebayJdbcTemplate;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("cusebedaTemplate")
	private JDBCTemplate cusebedaJdbcTemplate;

	
	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(ListOfMemberMessagesDAOImpl.class);
	/**
	 * This method is Required to get the member messages from table
	 */
	public List<Map<String, Object>> getMemberMessages(String objectId,
			String ItemId, String periodFrom, String periodTo,
			String upperLimit, String lowerLimit, String status) {
		// TODO Auto-generated method stub
		int maxValue = Integer.parseInt(upperLimit);
		int minValue = Integer.parseInt(lowerLimit);
		int diffLimit = maxValue - minValue;
		
		String from = null;
		String datum = null;
		String order = null;
		String datum_string = null;
		String last_changer = null;
		String verfall = null;
		int beantwortet = 0;
		String bedingung = null;
		jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();

		StringBuffer sb = new StringBuffer(
				"SELECT meine_nachrichten.message_id,meine_nachrichten.ebayname,meine_nachrichten.beantwortet,meine_nachrichten.message");
		if (status != null) {
			//int isAnswered = Integer.parseInt(status);
			if (status.equalsIgnoreCase("0")) {
				beantwortet = 0;
				from = " ebay_messages.meine_nachrichten";
				datum = " meine_nachrichten.creation_date";
				order = " ORDER BY message_id asc";
				datum_string = " creation_date";
				verfall = " ,date_add(meine_nachrichten.creation_date, INTERVAL 3 MONTH) as verfall";
			} else if (status.equalsIgnoreCase("1")) {
				beantwortet = 1;
				from = " ebay_messages.meine_nachrichten, ebay_messages.nachrichten_x_antworten";
				datum = " nachrichten_x_antworten.response_date,meine_nachrichten.creation_date";
				last_changer = " ,nachrichten_x_antworten.last_changer";
				order = " ORDER BY message_id asc";
				datum_string = " response_date";
				bedingung = " AND nachrichten_x_antworten.message_id = meine_nachrichten.message_id AND sichtbar = 1";
				verfall = "";
			}else{
				from = " ebay_messages.meine_nachrichten";
			}
			sb.append(","
					+ datum
					+ ",meine_nachrichten.subject,meine_nachrichten.message, meine_nachrichten.ebayitemid");
					if(null!=last_changer){
						
						sb.append(last_changer);
					}
					if(null!=verfall){
					
						sb.append(verfall);
					}
					sb.append(" from " + from
					+ " WHERE meine_nachrichten.cusebeda_objekt_id ="
					+ objectId + "");
			if (null != bedingung) {
				sb.append(" AND beantwortet = " + beantwortet + bedingung+ "");
			} else {
				if(status.equalsIgnoreCase("0") || status.equalsIgnoreCase("1")){
				sb.append(" AND beantwortet = " + beantwortet + "");
				}
			}
			if (null != periodFrom && null != periodTo) {
				/*sb.append(" AND meine_nachrichten.creation_date between "
						+ periodFrom
						+ "and"
						+ periodTo
						+ " ORDER BY ebayitemid, ebayname, creation_date asc limit "
						+ diffLimit + "");*/
				sb.append(" AND meine_nachrichten.creation_date between '"
						+ periodFrom
						+ "' and '"
						+ periodTo
						+ "' ORDER BY  message_id asc limit "
						+ lowerLimit+","+diffLimit + "");
			} else {
				/*sb.append(" ORDER BY ebayitemid, ebayname, creation_date asc limit "
						+ diffLimit + "");*/
				sb.append(" ORDER BY  message_id asc  limit "
						+ lowerLimit+","+diffLimit + "");
			}
		} else {
			sb.append(",meine_nachrichten.subject,meine_nachrichten.message,meine_nachrichten.beantwortet, meine_nachrichten.ebayitemid, meine_nachrichten.creation_date"
					+ " from ebay_messages.meine_nachrichten WHERE meine_nachrichten.cusebeda_objekt_id ="
					+ objectId + "");
			if (null != periodFrom && null != periodTo) {
				/*sb.append(" AND meine_nachrichten.creation_date between "
						+ periodFrom + "and" + periodTo
						+ " ORDER BY ebayitemid, ebayname, creation_date asc limit "
						+ diffLimit + "");*/
				sb.append(" AND meine_nachrichten.creation_date between '"
						+ periodFrom + "' and '" + periodTo
						+ "' ORDER BY  message_id asc limit "
						+ lowerLimit+","+diffLimit + "");
			} else {
				/*sb.append(" ORDER BY ebayitemid, ebayname, creation_date asc limit "
						+ diffLimit + "");*/
				sb.append(" ORDER BY  message_id asc limit "
						+ lowerLimit+","+diffLimit + "");
			}
		}

		List<Map<String, Object>> listValues = jdbcTemplate.queryForList(sb
				.toString());

		return listValues;
	}
	
	
	
	
	/**
	 * This method is used to get all the member messages for ItemId
	 */

	public List<Map<String, Object>> getMemberMessagesForItemId(
			String ebayItemId,String periodFrom,String periodTo,String upperLimit,String lowerLimit,String status) {
		// TODO Auto-generated method stub
		int maxValue = Integer.parseInt(upperLimit);
		int minValue = Integer.parseInt(lowerLimit);
		int diffLimit = maxValue - minValue;
		jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();
		StringBuffer sb=new StringBuffer("SELECT meine_nachrichten.ebayitemid,meine_nachrichten.ebayname,meine_nachrichten.subject,"
				+"meine_nachrichten.message,meine_nachrichten.beantwortet,meine_nachrichten.message_id,meine_nachrichten.creation_date, "
				+ "nachrichten_x_antworten.response_date, "
				+ "nachrichten_x_antworten.response, "
				+ "nachrichten_x_antworten.last_changer"
				+ " FROM    ebay_messages.meine_nachrichten "
				+ "LEFT JOIN  ebay_messages.nachrichten_x_antworten ON meine_nachrichten.message_id = nachrichten_x_antworten.message_id "
				+ "WHERE meine_nachrichten.ebayitemid ="+ebayItemId);
		if(status!=null){
			if(status.equalsIgnoreCase("0")){
				sb.append(" AND meine_nachrichten.beantwortet="+status);
			}else if(status.equalsIgnoreCase("1")){
				sb.append(" AND meine_nachrichten.beantwortet="+status);
			}
		}
		if(null!=periodFrom && null!=periodTo){
			sb.append(" AND meine_nachrichten.creation_date between '"
					+ periodFrom
					+ "' and '"
					+ periodTo
					+ "' ORDER BY  message_id asc limit "
					+ lowerLimit+","+diffLimit + "");
		}else{
			sb.append(" ORDER BY  message_id asc limit "
					+ lowerLimit+","+diffLimit + "");
		}
		List<Map<String, Object>> itemListValues = jdbcTemplate.queryForList(sb
				.toString());
		
		return itemListValues;
	}
	
	/**
	 * This method is used to validate the itemId
	 * @param cusebedaObjectId
	 * @param ItemId
	 * @return 
	 */
	public boolean validateItemId(String ItemId, String cusebedaObjectId) {
		// TODO Auto-generated method stub
		
		try {
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			String sql = "select count(*) from ebay.auktion where ebayitemid=" + ItemId;
			int count = 0;
			count = jdbcTemplate.queryForInt(sql);

			if (count > 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	
	
	/**
	 * This method is used to validate the objectId
	 * @param objectId
	 * @param itemId
	 * @return
	 */
	public boolean validateObjectId(String objectId,String itemId){
		boolean isValid=false;
		String objectid=null;
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		String sql="select cusebeda_objekt_id from ebay.auktion where ebayitemid="+itemId;
		
		 //sql="select cusebeda_objekt_id from ebay.auktion where ebayitemid="+itemId;
		 objectid=jdbcTemplate.queryForObject(sql, String.class);
		 if(objectid.equalsIgnoreCase(objectId)){
			 isValid=true;
		 }
		return isValid;
		
	}

	

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	
	/**
	 * This method is required to check the Object validity
	 * 
	 * @param objectId
	 * @return
	 */
	public boolean checkForObjectValidity(String objectId) {
		boolean objectIdstatus = false;
		LOGGER.debug("Entered checkForObjectValidity ");

		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		String objectIdSql = "SELECT count(*) FROM ebay.ebaydaten_token WHERE ebaydaten_token.cusebeda_objekt_id =? ";
		@SuppressWarnings("deprecation")
		int objectWithItemIdCount = jdbcTemplate.queryForInt(objectIdSql,
				new Object[] { Integer.parseInt(objectId) });
		LOGGER.debug("Count for objekt Id :::" + objectWithItemIdCount);
		if (objectWithItemIdCount != 0) {
			objectIdstatus = true;
		}

		return objectIdstatus;
	}




	public int getMemeberMessagesCount(String objectId, String ItemId,
			String periodFrom, String periodTo, String upperLimit,
			String lowerLimit, String status) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				int maxValue = Integer.parseInt(upperLimit);
				int minValue = Integer.parseInt(lowerLimit);
				int diffLimit = maxValue - minValue;
				
				String from = null;
				String datum = null;
				String order = null;
				String datum_string = null;
				String last_changer = null;
				String verfall = null;
				int beantwortet = 0;
				String bedingung = null;
				jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();

				StringBuffer sb = new StringBuffer(
						"SELECT count(meine_nachrichten.message_id) as maxCount");
				if (status != null) {
					//int isAnswered = Integer.parseInt(status);
					if (status.equalsIgnoreCase("0")) {
						beantwortet = 0;
						from = " ebay_messages.meine_nachrichten";
						datum = " meine_nachrichten.creation_date";
						order = " ORDER BY meine_nachrichten.message_id asc";
						datum_string = " creation_date";
						verfall = " ,date_add(meine_nachrichten.creation_date, INTERVAL 3 MONTH) as verfall";
					} else if (status.equalsIgnoreCase("1")) {
						beantwortet = 1;
						from = " ebay_messages.meine_nachrichten, ebay_messages.nachrichten_x_antworten";
						datum = " nachrichten_x_antworten.response_date,meine_nachrichten.creation_date";
						last_changer = " ,nachrichten_x_antworten.last_changer";
						order = " ORDER BY meine_nachrichten.message_id asc";
						datum_string = " response_date";
						bedingung = " AND nachrichten_x_antworten.message_id = meine_nachrichten.message_id AND sichtbar = 1";
						verfall = "";
					}
				/*	sb.append(","
							+ datum
							+ ",meine_nachrichten.subject,meine_nachrichten.message, meine_nachrichten.ebayitemid");
							if(null!=last_changer){
								
								sb.append(last_changer);
							}
							if(null!=verfall){
							
								sb.append(verfall);
							}*/
							sb.append(" from " + from
							+ " WHERE meine_nachrichten.cusebeda_objekt_id ="
							+ objectId + "");
					if (null != bedingung) {
						sb.append(" AND beantwortet = " + beantwortet + bedingung+ "");
					} else {
						sb.append(" AND beantwortet = " + beantwortet + "");
					}
					if (null != periodFrom && null != periodTo) {
						/*sb.append(" AND meine_nachrichten.creation_date between "
								+ periodFrom
								+ "and"
								+ periodTo
								+ " ORDER BY ebayitemid, ebayname, creation_date asc limit "
								+ diffLimit + "");*/
						sb.append(" AND meine_nachrichten.creation_date between '"
								+ periodFrom
								+ "' and '"
								+ periodTo
								+ "' ORDER BY  meine_nachrichten.message_id asc ");
					} else {
						/*sb.append(" ORDER BY ebayitemid, ebayname, creation_date asc limit "
								+ diffLimit + "");*/
						sb.append(" ORDER BY  meine_nachrichten.message_id asc ");
					}
				} else {
					sb.append(" from ebay_messages.meine_nachrichten WHERE meine_nachrichten.cusebeda_objekt_id ="
							+ objectId + "");
					if (null != periodFrom && null != periodTo) {
						/*sb.append(" AND meine_nachrichten.creation_date between "
								+ periodFrom + "and" + periodTo
								+ " ORDER BY ebayitemid, ebayname, creation_date asc limit "
								+ diffLimit + "");*/
						sb.append(" AND meine_nachrichten.creation_date between '"
								+ periodFrom + "' and '" + periodTo
								+ "' ORDER BY  meine_nachrichten.message_id asc ");
					} else {
						/*sb.append(" ORDER BY ebayitemid, ebayname, creation_date asc limit "
								+ diffLimit + "");*/
						sb.append(" ORDER BY  meine_nachrichten.message_id asc ");
					}
				}

				int count = jdbcTemplate.queryForInt(sb
						.toString());

				return count;
	}




	public int getMemberMessagesForItemIdCount(String ebayItemId,
			String periodFrom, String periodTo, String upperLimit,
			String lowerLimit, String status) {
		// TODO Auto-generated method stub
		
		int maxValue = Integer.parseInt(upperLimit);
		int minValue = Integer.parseInt(lowerLimit);
		int diffLimit = maxValue - minValue;
		jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();
		StringBuffer sb=new StringBuffer("SELECT count(meine_nachrichten.message_id) as maxCount"
				+ " FROM    ebay_messages.meine_nachrichten "
				+ "LEFT JOIN  ebay_messages.nachrichten_x_antworten ON meine_nachrichten.message_id = nachrichten_x_antworten.message_id "
				+ "WHERE meine_nachrichten.ebayitemid ="+ebayItemId);
		if(status!=null){
			if(status.equalsIgnoreCase("0")){
				sb.append(" AND meine_nachrichten.beantwortet="+status);
			}else if(status.equalsIgnoreCase("1")){
				sb.append(" AND meine_nachrichten.beantwortet="+status);
			}
		}
		if(null!=periodFrom && null!=periodTo){
			sb.append(" AND meine_nachrichten.creation_date between '"
					+ periodFrom
					+ "' and '"
					+ periodTo
					+ "' ORDER BY  meine_nachrichten.message_id asc ");
		}else{
			sb.append(" ORDER BY  meine_nachrichten.message_id asc ");
		}
		int count = jdbcTemplate.queryForInt(sb
				.toString());
		
		return count;
		
	}






}