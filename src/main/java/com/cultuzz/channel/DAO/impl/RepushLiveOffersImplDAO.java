package com.cultuzz.channel.DAO.impl;

import java.sql.Timestamp;
import java.util.HashMap;
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

import com.cultuzz.channel.XMLpojo.RepushLiveOffersRQ;
import com.cultuzz.channel.jdbcTemplate.JDBCTemplate;

@Component
public class RepushLiveOffersImplDAO {

	@Autowired
	@Qualifier("ebayTemplate")
	private JDBCTemplate ebayJdbcTemplate;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Autowired
	@Qualifier("cusebedaTemplate")
	private JDBCTemplate cusebedaJdbcTemplate;

	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OfferReviseDAOImpl.class);
	
	
	public int saveTemplateDetails(RepushLiveOffersRQ repushLiveOffersRQ){

		int id = 0;
		java.util.Date date = new java.util.Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		LOGGER.debug("Inside save details Method.....");

		namedJdbcTemplate = ebayJdbcTemplate.getNamedParameterJdbcTemplate();

		StringBuffer insertReviseDetails = new StringBuffer(
				"insert into ebay.TemplateRepushData(TemplateId,Title,SubTitle,GalleryPicture,ItemPictures,CategoriesAndAttributes,Description,RepushTime,status,insertedTime) ");
		insertReviseDetails
				.append(" values(:TemplateId,:Title,:SubTitle,:GalleryPicture,:ItemPictures,:CategoriesAndAttributes,:Description,:RepushTime,:status,:insertedTime)");

		Map<String, Object> bindData = new HashMap<String, Object>();
		bindData.put("TemplateId", repushLiveOffersRQ.getTemplateId());

		bindData.put("Title", repushLiveOffersRQ.isTitle());
		bindData.put("SubTitle", repushLiveOffersRQ.isSubTitle());
		bindData.put("GalleryPicture", repushLiveOffersRQ.isGalleryPicture());
		bindData.put("ItemPictures", repushLiveOffersRQ.isItemPictures()); 
									
		bindData.put("CategoriesAndAttributes", repushLiveOffersRQ.isCategoriesAndAttributes());

		bindData.put("Description", repushLiveOffersRQ.isDescription());
		bindData.put("RepushTime", repushLiveOffersRQ.getRepushTime());
		
		bindData.put("status", 0);
		bindData.put("insertedTime",timestamp);
		

		SqlParameterSource paramSource = new MapSqlParameterSource(bindData);

		KeyHolder keyHolder = new GeneratedKeyHolder();

		this.namedJdbcTemplate.update(insertReviseDetails.toString(),
				paramSource, keyHolder);
		id = keyHolder.getKey().intValue();

		return id;

		
	}
	
	
	public boolean checkTemplateId(int templateid,int objectid){
		boolean templateidFlag=false;
		
		try{
			LOGGER.debug("inside checking template id");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String templateidsql="SELECT count(id) FROM ebay.vorlage where id=? and status=1 and cusebeda_objekt_id="+objectid;
			int templateidsCount =jdbcTemplate.queryForInt(templateidsql, new Object[] { templateid });
			
			if(templateidsCount>0)
				templateidFlag=true;
			
		}catch(Exception e){
			templateidFlag=false;
			e.printStackTrace();
		}
		
		return templateidFlag;
	}
	

	public boolean checkLiveOffers(int templateid){
		LOGGER.debug("This is enter to live offers "+templateid);
			boolean checkLiveOffers=false;
			int countOfLiveOffers=0;
			try{
				jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String checkLiveOffersQuery="select count(ebayitemid) from ebay.auktion where vorlage_id=? and status=1";
			 countOfLiveOffers =jdbcTemplate.queryForInt(checkLiveOffersQuery, new Object[] { templateid });
			 
			 if(countOfLiveOffers>0)
				 checkLiveOffers=true;
			 else
				 checkLiveOffers=false;
			
			}catch(Exception e){
				e.printStackTrace();
				checkLiveOffers=false;
			}
			return checkLiveOffers;
	}
	
	public boolean checkFixedPriceLiveOffers(int templateid){
		boolean checkFixedPriceLiveOffers=false;
		LOGGER.debug("This is enter to live offers fixed price offers");
		int countOfFixedPriceOffersS=0;
		try{
			LOGGER.debug("This is enter to live offers Store fixed price checking");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String checkLiveOffersQueryStore="select count(ebayitemid) from ebay.auktion where vorlage_id=? and status=1 and startpreis>0 and AuctionMasterTypeID=4";
			countOfFixedPriceOffersS =jdbcTemplate.queryForInt(checkLiveOffersQueryStore, new Object[] { templateid });
			 
			if(countOfFixedPriceOffersS>=2){
				checkFixedPriceLiveOffers=true;
			}else{
				LOGGER.debug("This is enter to live offers fixed price offers ");
				int countFixedPriceOffers=0;
				String checkLiveOffersQueryFixed="select count(ebayitemid) from ebay.auktion where vorlage_id=? and status=1 and ebaysofortkauf>0 and AuctionMasterTypeID=1";
				countFixedPriceOffers =jdbcTemplate.queryForInt(checkLiveOffersQueryFixed, new Object[] { templateid });
				 if(countFixedPriceOffers>=2){
					 checkFixedPriceLiveOffers=true;
				 }else
					 checkFixedPriceLiveOffers=false;
			}
			
			
		}catch(Exception e){
			checkFixedPriceLiveOffers=false;
			LOGGER.debug("Problem Occured at Fixed Price Offers live count");
			e.printStackTrace();
		}
		
		return checkFixedPriceLiveOffers;
	}
	
	
	public String getMaxdate(int vorlageid){
		 String endDate=null;
		 try{
		 jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
     String checkGTCQuery="SELECT count(*) from ebay.auktion where status=1 and dauer=9999 and vorlage_id=?";
     
     int gtcCount=jdbcTemplate.queryForInt(checkGTCQuery, new Object[] { vorlageid });
     
     if(gtcCount>0){
    	return endDate="GTC";
     }else{
    	 String endDateQuery="SELECT max(DATE_ADD(startdatum,interval dauer day)) as enddate from ebay.auktion where status=1 and vorlage_id=? and dauer!=9999";
    	 endDate = (String)jdbcTemplate.queryForObject(endDateQuery,new Object[]{ vorlageid },String.class);
    	 return endDate;
     }
    
		 }catch(Exception e){
			 return endDate; 
		 }
	}
	
	
}
