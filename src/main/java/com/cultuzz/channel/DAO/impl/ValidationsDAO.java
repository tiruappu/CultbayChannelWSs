package com.cultuzz.channel.DAO.impl;

import java.sql.SQLException;
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
import org.springframework.stereotype.Repository;

import com.cultuzz.channel.jdbcTemplate.JDBCTemplate;
import com.cultuzz.channel.util.LogPojo;

@Repository
public class ValidationsDAO {

	@Autowired
	@Qualifier("ebayTemplate")
	private JDBCTemplate ebayJdbcTemplate;

	@Autowired
	@Qualifier("cusebedaTemplate")
	private JDBCTemplate cusebedaJdbcTemplate;

	private JdbcTemplate jdbcTemplate;

	private NamedParameterJdbcTemplate namedJdbcTemplate;

	private static final Logger logger = LoggerFactory
			.getLogger(ValidationsDAO.class);

	public boolean checkObjectId(int objectId) {
		boolean checkObjectStatus = false;
		try {
			jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();
			String sql = "select count(id) from  cusebeda.objekt where id=?";
			int objectidscount = jdbcTemplate.queryForInt(sql,
					new Object[] { objectId });

			logger.debug("Checking objectid count" + objectidscount);
			if (objectidscount > 0)
				checkObjectStatus = true;

		} catch (Exception e) {
			checkObjectStatus = false;
			logger.error("Inside Ctach block of checkObjectId method..."+e);
			//e.printStackTrace();
		}

		return checkObjectStatus;
	}

	public int getLanguageId(String lang) {

		int langId = 0;
		jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();
		try {
			String langIdQuery = "select id from cusebeda.sprache where iso_639_1 = ?";

			langId = jdbcTemplate.queryForInt(langIdQuery,
					new Object[] { lang });
		} catch (Exception e) {
			logger.error("Inside Ctach block of getLanguageId method..."+e);
			//e.printStackTrace();
		}

		return langId;
	}

	public void saveLogDatetoDb(LogPojo logPojo) {

		namedJdbcTemplate = ebayJdbcTemplate.getNamedParameterJdbcTemplate();
		StringBuffer saveLogDataQuery = new StringBuffer(
				"insert into ebay.EbayAsAChannelLog(objectid,vorlageid,auctionid,ebayitemid,request,response,type,status,processTime)");

		saveLogDataQuery.append(" values(:objectid,:vorlageid,:auctionid,:ebayitemid,:request,:response,:type,:status,:processTime)");

		Map<String, Object> logdataMap = new HashMap<String, Object>();
		logdataMap.put("objectid", logPojo.getObjectid());
		logdataMap.put("vorlageid", logPojo.getVorlageid());
		logdataMap.put("auctionid", logPojo.getAuctionid());
		logdataMap.put("ebayitemid", logPojo.getEbayitemid());
		logdataMap.put("request", logPojo.getRequest());
		logdataMap.put("response", logPojo.getResponse());
		logdataMap.put("type", logPojo.getType());
		logdataMap.put("status", logPojo.getStatus());
		logdataMap.put("processTime", logPojo.getProcesstime());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource logDataParamSource = new MapSqlParameterSource(
				logdataMap);

		this.namedJdbcTemplate.update(saveLogDataQuery.toString(),
				logDataParamSource, keyHolder);
		logger.debug("Logger Id value", keyHolder.getKey().longValue());
	}

	/**
	 * To Fetch credentials from table...
	 */

	public List<Map<String,Object>> fetchCredential(String authCode){
		logger.debug("Inside fetchCredential method with authCode..."+authCode);
		List<Map<String,Object>> rows = null;
		
		try{
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			logger.debug("Inside Try block of fetchCredential method...");

			String fetchCredQuery = "select channelId,sourceId from ebay.EAAC_Configuration where auth_code=?";
			logger.debug("FETCH CRED QUERY:::"+fetchCredQuery);
//			rows = jdbcTemplate.queryForList(fetchCredQuery);
			rows = jdbcTemplate.queryForList(fetchCredQuery,authCode);
		 
		}catch(Exception e){
			logger.error("Inside Ctach block of fetchCredential method..."+e);
			
			//e.printStackTrace();
		}
		System.out.println("Selected data is:::"+rows);
		return rows;
	}
	
	/**
	 * This method is used to validate the marketplace for retail price
	 * @param siteId
	 * @return
	 */
	public boolean validateRetailPrice(int siteId){
		boolean allowedsite=false;
		int count=0;
		try{
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			String siteIdQuery="Select count(*) from ebay.retailprice_siteid where siteId=?";
			count=jdbcTemplate.queryForInt(siteIdQuery,new Object[] { siteId });
			if(count>0){
				allowedsite=true;
			}
		}catch(Exception e){
			logger.error("Inside Ctach block of validateretailprice method..."+e);
			//e.printStackTrace();
		}
		return allowedsite;
	
	}
	
	/**
	 * This method is used to get the price details
	 * @param templateId
	 * @return
	 */
	public int getPrice(int templateId){
		int price=0;
		
		try{
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			
			String priceQuery="Select startpreis from ebay.vorlage where  id=?";
			price=jdbcTemplate.queryForInt(priceQuery, new Object[] { templateId });
			
		}catch(Exception e){
			logger.debug("Inside Ctach block of getPrice method..."+e);
			//e.printStackTrace();
		}
		return price;
		
	}
}
