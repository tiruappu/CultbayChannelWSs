package com.cultuzz.channel.DAO.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.cultuzz.channel.DAO.ListOfTemplatesDAO;
import com.cultuzz.channel.XMLpojo.OfferDetailsRS;
import com.cultuzz.channel.XMLpojo.ListOfTemplatesRS.Templates.Template;
import com.cultuzz.channel.jdbcTemplate.JDBCTemplate;
import com.cultuzz.channel.template.pojo.ListOfTemplateMapper;
import com.cultuzz.channel.template.pojo.OfferMapper;

@Configuration
public class ListOfTemplatesDAOImpl implements ListOfTemplatesDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ListOfTemplatesDAOImpl.class);
	

	@Autowired
	@Qualifier("ebayTemplate")
	private JDBCTemplate ebayJdbcTemplate;
	
	private JdbcTemplate jdbcTemplate;
	
	public int getNoOfTemplates(int objectId) {
		// TODO Auto-generated method stub
		
		int templateIdsCount =0;
		try{
			LOGGER.debug("inside getNoOfOffers");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String templateidsql="SELECT count(id) FROM ebay.vorlage where cusebeda_objekt_id =?";
			 templateIdsCount =jdbcTemplate.queryForObject(templateidsql, new Object[] { objectId },Integer.class);
			
		}catch(Exception e){
			LOGGER.error("Empty results"+e);
			//e.printStackTrace();
		}
		
		return templateIdsCount;
		
	
	}

	public int getNoOfTemplates(String lisOfTemplatesQueryCount) {
		// TODO Auto-generated method stub
		
		int templateIdsCount =0;
		try{
			LOGGER.debug("inside getNoOfOffers");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String templateidsql="SELECT count(id) FROM ebay.vorlage where"+lisOfTemplatesQueryCount;
			 templateIdsCount =jdbcTemplate.queryForObject(templateidsql,Integer.class);
			
		}catch(Exception e){
			LOGGER.error("Empty results"+e);
			//e.printStackTrace();
		}
		
		return templateIdsCount;
		
	
	}

	public List<Integer> getTemplateIds(String templateIdQuery){
		
		LOGGER.debug("offerIdQuery is :{}",templateIdQuery);
		List<Integer> templateIds = new ArrayList<Integer>();
		
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		
		String listOfTemplatesSql = "select id from ebay.vorlage where"+templateIdQuery;
		
		try{
			
			templateIds = (List<Integer>)jdbcTemplate.queryForList(listOfTemplatesSql, Integer.class);
			
			LOGGER.debug("templateIDs in ListOfTEmplatesDAO is:{}",templateIds.toArray());
			        
			}catch(EmptyResultDataAccessException e){
				LOGGER.error("Empty results"+e);
				   	//e.printStackTrace();
	          
	                templateIds = null;		   
			}

			return templateIds;

		   }
	
	
    public Template getTemplateDetails(int templateId) {
		// TODO Auto-generated method stub
	
		Template template = null;
		
		LOGGER.debug("templateId is :{}",templateId);
		
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		
		String sql="select ebaysiteid, bezeichnung, template_id, status,ebaysofortkauf,startpreis,auctionMasterTypeID from ebay.vorlage where id = ?";
		
		try{
			
			LOGGER.debug("sql in getTemplate details:{}",sql);
		
			template = jdbcTemplate.queryForObject(sql.toString(),
				new Object[] { templateId }, new ListOfTemplateMapper());
		
		LOGGER.debug("template details is:{}",template.toString());
		       
		   }catch(EmptyResultDataAccessException e){
        	  LOGGER.error("Empty results"+e);
			  // 	e.printStackTrace();
          
           }
           
		  
		return template;

	   }
    
    public int getSourceId(int templateId){
		   
		   LOGGER.debug("templateId is:{}",templateId);
		   
		   int source = 1;
		   try{
			   
			   LOGGER.debug("inside getSourceId");
			   jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			   
			   String sourceSql="SELECT sourceid from ebay.vorlage_x_source where vorlage_id = ?";
				 source =jdbcTemplate.queryForObject(sourceSql, new Object[] {templateId},Integer.class);

			   
		   }catch(Exception e){
			   LOGGER.error("error occured in get sourceid"+e);
			   
		   }
		   
		   return source;
	   }
    
    public int getNoOfLiveOffers(int templateId){
    	int liveOffersCount=0;
    	try{
    		LOGGER.debug("inside getlive offer count");
			   jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			   
			   String sourceSql="select count(id) as live from ebay.auktion where status in (0,1) and vorlage_id=?";
			   liveOffersCount =jdbcTemplate.queryForObject(sourceSql, new Object[] {templateId},Integer.class);

			   
		   }catch(Exception e){
			   LOGGER.error("error occured in get liveofferids"+e);
			   
		   }
    	return liveOffersCount;
    }
}
