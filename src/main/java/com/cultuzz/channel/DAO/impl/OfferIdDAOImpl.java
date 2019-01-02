package com.cultuzz.channel.DAO.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.jdbcTemplate.JDBCTemplate;





@Component
public class OfferIdDAOImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(OfferIdDAOImpl.class);
	
	
	@Autowired
	@Qualifier("ebayTemplate")
	private JDBCTemplate ebayJdbcTemplate;
	
	private JdbcTemplate jdbcTemplate;
	
	
	public List<Integer> getOfferIds(String offerIdQuery){
		
		LOGGER.debug("offerIdQuery is :{}",offerIdQuery);
		
		List<Integer> offerIds = null;
		
	  jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		
		String listOffersSql = "select id from ebay.auktion a where"+offerIdQuery;
		
		try{
			
			LOGGER.debug("sql for listOfOfferIds is:{}",listOffersSql);
			offerIds = (List<Integer>)jdbcTemplate.queryForList(listOffersSql, Integer.class);
			
			LOGGER.debug("offer in offerDAO is:{}",offerIds.toArray());
			        
			}catch(EmptyResultDataAccessException e){
	        	  
				   	e.printStackTrace();
	          
	                offerIds = null;		   
			}

			return offerIds;

		   }




		public String getTemplateName(int templateId){
			
			LOGGER.debug("templateId is :{}",templateId);

			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			
			String templateName = null;
			
			String templateNameSql = "select bezeichnung from ebay.vorlage where id =?";
			
			try{
				
				templateName = jdbcTemplate.queryForObject(templateNameSql.toString(),new Object[]{templateId},String.class);
			
			}catch(EmptyResultDataAccessException e){
	        	  
				   	e.printStackTrace();
	          
	        }

			
			return templateName;
		}
	
		public boolean checkTemplateId(int templateid){
			boolean templateidFlag=false;
			
			try{
				LOGGER.debug("inside checking template id");
				jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
				String templateidsql="SELECT count(id) FROM ebay.vorlage where id=?";
				int templateidsCount =jdbcTemplate.queryForInt(templateidsql, new Object[] { templateid });
				
				if(templateidsCount>0)
					templateidFlag=true;
				
			}catch(Exception e){
				templateidFlag=false;
				e.printStackTrace();
			}
			
			return templateidFlag;
		}
		
		public int getNoOfOffers(int objectId,int templateid){
			
			 int offerIdsCount =0;
			try{
				LOGGER.debug("inside getNoOfOffers");
				jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
				String templateidsql="SELECT count(id) from ebay.auktion where cusebeda_objekt_id =? and vorlage_id = ?";
				 offerIdsCount =jdbcTemplate.queryForInt(templateidsql, new Object[] { objectId, templateid});
				
			}catch(Exception e){
				
				e.printStackTrace();
			}
			
			return offerIdsCount;
		}
		
		public int getNoOfOffers(String listOfOffersCountQuery){
			
			 int offerIdsCount =0;
			try{
				LOGGER.debug("inside getNoOfOffers query"+listOfOffersCountQuery);
				jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
				String offeridsql="SELECT count(id) from ebay.auktion where"+listOfOffersCountQuery;
				 offerIdsCount =jdbcTemplate.queryForInt(offeridsql);
				
			}catch(Exception e){
				
				e.printStackTrace();
			}
			
			return offerIdsCount;
		}
		
		  public int getSourceId(int offerId){
			   
			   LOGGER.debug("offerId is:{}",offerId);
			   
			   int source = 1;
			   
			   try{
				   
				   LOGGER.debug("inside getSourceId");
				   jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
				   
				   String sourceSql="SELECT source from ebay.auktion_x_source where auktion_id=?";
					 source =jdbcTemplate.queryForInt(sourceSql, new Object[] {offerId});

				   
			   }catch(EmptyResultDataAccessException ersdae){
				   LOGGER.error("Empty data found{}",offerId);
			   }catch(Exception e){
				   
				   e.printStackTrace();
			   }
			   
			   return source;
		   }
		  
		  public long getItemWatchCount(long itemid){
				long watchcount=0;
		try{	
					jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
					String sql = "select watchcount from ebay.object_ItemWatchCount where ebayitemid=?";
					watchcount = jdbcTemplate.queryForLong(sql, new Object[] {itemid});		
					LOGGER.debug("Watch count from db:{}",watchcount);
					//System.out.println("Watch count from db:"+watchcount);
				}catch(Exception e){
					
					e.printStackTrace();
				}
				
				return watchcount;
				
					
			}
		
		}
