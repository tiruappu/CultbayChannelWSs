package com.cultuzz.channel.DAO.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.jdbcTemplate.JDBCTemplate;

@Component
public class GetErrorMessagesDAOImpl {
	
	
	@Autowired
	@Qualifier("ebayTemplate")
	private JDBCTemplate ebayJdbcTemplate;
	
	@Autowired
	@Qualifier("cusebedaTemplate")
	private JDBCTemplate cusebedaJdbcTemplate;
	
	private JdbcTemplate jdbcTemplate; 
	
	private static final Logger LOGGER = LoggerFactory.getLogger("GetErrorMessagesDAOImpl");
	
	public String getErrorMessage(int errorCode,int langid){
		//System.out.println("%%%%%%%%%%%%%%%%%%%%%"+errorCode+"langId is"+langid);
		LOGGER.debug("error code is:{}",errorCode);
		String errorMessgae="";
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		try{
		String errorMessageQuery="select errormessage from ebay.ErrorMessageseBayAsAChannel where errorcode =? and langid = "+langid;
		LOGGER.debug("Err Msz Query is :::"+errorMessageQuery);
		errorMessgae=jdbcTemplate.queryForObject(errorMessageQuery,new Object[]{errorCode}, String.class);
		
		LOGGER.debug("error message is:{}",errorMessgae);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return errorMessgae;
	}

	public int getLanguageId(String lang){
		
		int langId = 0; 
		
		LOGGER.debug("langId is:{}",lang);
		
		jdbcTemplate=cusebedaJdbcTemplate.getJdbcTemplate();
		try{
		String langIdQuery="select id from cusebeda.sprache where iso_639_1 =?";
		
		LOGGER.debug("langsql is:{}",langIdQuery);
	
		langId=jdbcTemplate.queryForInt(langIdQuery.toString(),new Object[]{lang});
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return langId;
	}
}
