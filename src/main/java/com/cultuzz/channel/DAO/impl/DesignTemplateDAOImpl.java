package com.cultuzz.channel.DAO.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.DAO.DesignTemplateDAO;
import com.cultuzz.channel.jdbcTemplate.JDBCTemplate;


@Component
public class DesignTemplateDAOImpl implements DesignTemplateDAO{

private static final Logger LOGGER = LoggerFactory.getLogger(DesignTemplateDAOImpl.class);

@Autowired
@Qualifier("ebayTemplate")
private JDBCTemplate ebayJdbcTemplate;

private JdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> getDesignTemplates(int objectId) {
		// TODO Auto-generated method stub
		
		LOGGER.debug("inside DesignTemplatesDAO");
		
		List<Map<String, Object>> designTemplates = null;
		
		try{
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			
			String sql = "select id, bezeichnung, sampledesign from ebay.template where cusebeda_objekt_id in ("+objectId+",1) and ActiveStatus = 1 order by id desc";
			
			LOGGER.debug("sql query for voucherIds :{}",sql);

			designTemplates = jdbcTemplate.queryForList(sql);
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		
		return designTemplates;
	}
	
	
	public List<Map<String,Object>> getDesingTemplateCategories(int templateid){
		
		LOGGER.debug("inside get desing template categories");
		
		List<Map<String,Object>> categories = null;
		try{
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			
			String categoriesSql = "select id, category,header_url,footer_url from ebay.template_categories where template_id ="+templateid;
			
			LOGGER.debug("caegories sql is :{}",categoriesSql);
			
			categories = jdbcTemplate.queryForList(categoriesSql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
 		
		return categories;
		
	}
	
	public int getDesignTemplatesCount(int objectId){
		
		int count = 0 ;
		
		LOGGER.debug("inside get design templates count");
		
		try{
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			
			String designTemplatesCountSql = "select count(id) from ebay.template where cusebeda_objekt_id in("+objectId+",1) and ActiveStatus = 1";

			LOGGER.debug("designTemplates count sql is :{}",designTemplatesCountSql);
			
			count = jdbcTemplate.queryForInt(designTemplatesCountSql);
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return count;
	}

}
