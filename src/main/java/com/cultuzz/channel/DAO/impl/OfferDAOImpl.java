package com.cultuzz.channel.DAO.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.DAO.OfferDAO;
import com.cultuzz.channel.XMLpojo.OfferDetailsRS;
import com.cultuzz.channel.jdbcTemplate.JDBCTemplate;
import com.cultuzz.channel.template.pojo.OfferMapper;
import com.cultuzz.channel.template.pojo.OfferMapperNew;
import com.cultuzz.channel.util.CommonUtil;

@Component
public class OfferDAOImpl implements OfferDAO {

	/* this method used to fetch the offer related details */
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OfferDAOImpl.class);
	
	@Autowired
	@Qualifier("ebayTemplate")
	private JDBCTemplate ebayJdbcTemplate;
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("cusebedaTemplate")
	private JDBCTemplate cusebedaJdbcTemplate;
	
	public OfferDetailsRS getOfferDetails(int offerId) {
		// TODO Auto-generated method stub
	
		OfferDetailsRS offerDetailsRS = new OfferDetailsRS();
		
		LOGGER.debug("offerId is :{}",offerId);
		
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		
		String sql="select a.id,a.cusebeda_objekt_id,a.vorlage_id,a.ebayitemid,a.ebayueberschrift,a.untertitel,a.startdatum,a.currentbid,a.dauer,a.quantity,a.startpreis,a.ebaysofortkauf,a.AuctionMasterTypeID,a.ebaysiteid,a.hoechstbietender,DATE_ADD(a.startdatum, INTERVAL a.dauer DAY) as enddate, a.status, a.anzahlgebote, a.retailprice,w.watchcount from ebay.auktion a left join ebay.object_ItemWatchCount w on w.ebayitemid=a.ebayitemid where a.id =?";
		
		try{
		offerDetailsRS = jdbcTemplate.queryForObject(sql.toString(),
				new Object[] { offerId}, new OfferMapper());
		
		LOGGER.debug("offer in offerDAO is:{}",offerDetailsRS);
		        
		   }catch(EmptyResultDataAccessException e){
        	  
			   	e.printStackTrace();
          
                offerDetailsRS = null;		   
		   }
           
		  
		return offerDetailsRS;

	   }
	@Autowired
	OfferMapperNew ofd;

	 public List<OfferDetailsRS> getOfferDetailsAll(String condition,CommonUtil commonutils){
		 jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		// OfferDetailsRS offerDetailsRS = new OfferDetailsRS();
			String sql="select a.id,a.cusebeda_objekt_id,a.vorlage_id,a.ebayitemid,a.ebayueberschrift,a.untertitel,a.startdatum,a.currentbid,a.dauer,a.quantity,a.startpreis,a.ebaysofortkauf,a.AuctionMasterTypeID,a.ebaysiteid,a.hoechstbietender,DATE_ADD(a.startdatum, INTERVAL a.dauer DAY) as enddate, a.status, a.anzahlgebote, a.retailprice,w.watchcount,v.arrangement_id from ebay.auktion a left join ebay.object_ItemWatchCount w on w.ebayitemid=a.ebayitemid left join ebay.vorlage v on v.id=a.vorlage_id where "+condition;
			
			LOGGER.debug("select query in offersdao{}",sql);
			List<OfferDetailsRS> res=null;
			try{
			res= jdbcTemplate.query(sql.toString(),
					new Object[] {}, new RowMapper<OfferDetailsRS>(){
				public OfferDetailsRS mapRow(ResultSet rs, int rowNum) throws SQLException {
					OfferDetailsRS offers = null;
		            
					//OfferMapperNew ofd=new OfferMapperNew();
					offers=ofd.dataSet(rs, rowNum);
					
		            return offers;
		        }
			});
			
			LOGGER.debug("offers in offersDAO 8888:{}",res.size());
			        
			   }catch(EmptyResultDataAccessException e){
	        	  
				   	e.printStackTrace();
	             
			   }
			
			return res;
	 }
	 
	 public String getPayeeAccount(long auktionid) {
			// TODO Auto-generated method stub
			LOGGER.debug("auktionid in OfferDAO is :{}",auktionid);
			
			String payeeAccount ="";
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			
			String sql="select paypal_email from ebay.auktion_paypaldetails where auktion_id=?";
			
			LOGGER.debug("sql of payee account is :{}",sql.toString());
			
			try{
				
				payeeAccount = (String)jdbcTemplate.queryForObject(sql,new Object[]{ auktionid },String.class);

				LOGGER.debug("payee account is :{}",payeeAccount);
				
			     return payeeAccount;
			}catch(EmptyResultDataAccessException e){
				LOGGER.debug("payee account is not available ");
				//e.printStackTrace();
				return null;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		
		}
	 
	 public String getFuturePayeeAccount(long auktionid,int ebaysiteid) {
			// TODO Auto-generated method stub
			LOGGER.debug("auktionid in OfferDAO is :{}",auktionid);
			int objectid=this.getCarierObject(auktionid);
			String payeeAccount ="";
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			//System.out.println("offer carier id isss"+objectid);
			String sql="select paypal_email from ebay.object_paypalDetails where  objectid=? and ebaysiteid="+ebaysiteid;
			
			LOGGER.debug("sql of payee account is :{}",sql.toString());
			//System.out.println("id isss query kkk"+sql);
			try{
				
				payeeAccount = (String)jdbcTemplate.queryForObject(sql,new Object[]{ objectid },String.class);

				LOGGER.debug("payee account is :{}",payeeAccount);
				
			     return payeeAccount;
			}catch(EmptyResultDataAccessException e){
		
				e.printStackTrace();
				return null;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		
		}
	 
	 
	 public String getProductName(Integer productid,Integer langid){
		 
			// TODO Auto-generated method stub
			LOGGER.debug("productid in OfferDAO is :{}",productid);
			
			String productname ="";
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			
			String sql="select name from ebay_product.product_text where productId=? and languageId="+langid;
			
			LOGGER.debug("sql of payee account is :{}",sql.toString());
			
			try{
				
				productname = (String)jdbcTemplate.queryForObject(sql,new Object[]{productid},String.class);

				LOGGER.debug("product name is :{}",productname);
				
			     return productname;
			}catch(EmptyResultDataAccessException erda){
				LOGGER.debug("product name is not available");
				
				return null;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		 
		 
	 }
	 
	
	public String getCurrency(int siteId) {
		// TODO Auto-generated method stub
		LOGGER.debug("siteId in OfferDAO is :{}",siteId);
		
		String currency ="";
		jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();
		
		String sql="select DISTINCT currency.kurz FROM ebaystammdaten.currency join ebaystammdaten.siteid on currency.id = siteid.currency_id where siteid.id =?";
		
		LOGGER.debug("sql foe currency is :{}",sql.toString());
		
		try{
			
			currency = (String)jdbcTemplate.queryForObject(sql,new Object[]{ siteId },String.class);

			LOGGER.debug("currency is :{}",currency);
			
		     return currency;
		}catch(EmptyResultDataAccessException e){
	
			e.printStackTrace();
			return null;
		}
	
	}
	
	public List<Map<String,Object>> getAllCurrencies(){
		List<Map<String,Object>>  currenciesList=null;
		//System.out.println("called all currencyies");
		jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();
		
		String sql = "select currency.kurz,siteid.id FROM ebaystammdaten.currency join ebaystammdaten.siteid on currency.id = siteid.currency_id";
		
		try{
			currenciesList = jdbcTemplate.queryForList(sql.toString(),new Object[]{});
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return currenciesList;
	}

	public int getQuantityPurchased(long itemId) {
		// TODO Auto-generated method stub
		LOGGER.debug("itemId in OfferDAO is :{}",itemId);
		
		int quantityPurchased;
		
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		
		String sql = "select sum(quantity_purchased) from ebay.transaction where ebayitemid = ?";
		
		try{
			
			quantityPurchased = jdbcTemplate.queryForInt(sql.toString(),new Object[]{itemId});
			
			return quantityPurchased;
		}catch(Exception e){
			
			e.printStackTrace();
			
			return 0;
		}
	}

	public int getNoOfViews(int offerId) {
		// TODO Auto-generated method stub
		
		LOGGER.debug("offerId in offerDAO is :{}",offerId);
		
		int noOfViews = 0;
		
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		
		String sql = "select besucher.anzahlbesuche AS noOfViews from ebay.besucher where auktion_id =?";
		
		try{
			
			noOfViews = jdbcTemplate.queryForInt(sql.toString(),new Object[]{offerId});
			
			return noOfViews;
		}catch(org.springframework.dao.EmptyResultDataAccessException edae){
			LOGGER.debug("No of views not available");
			return noOfViews;
		}catch(Exception e){
			
			e.printStackTrace();
			
			return noOfViews;
		}
		
	}

	public String getDescription(int offerId) {
		// TODO Auto-generated method stub
		LOGGER.debug("offerId in offerDAO is :{}",offerId);
		
		jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();
		
		String sql="SELECT request FROM ebay3.apiCallRequest WHERE uuid like '"+offerId+"%'";
		
		try{
			
			String apiCallRequest = jdbcTemplate.queryForObject(sql.toString(),String.class);
			 String output = null;
			
			if (apiCallRequest.contains("<Description>")) {
                int indexbeg = apiCallRequest.indexOf("<Description>") + 13;
                int indexend = apiCallRequest.indexOf("</Description>");
                String description = apiCallRequest.substring(indexbeg, indexend);
                output = description.replace("´", "'");
                output = description.replace("�", "'");
                
                LOGGER.debug("description in DAO is:{}",output);
		}
		
			return output;
			
		}catch(Exception e){
	
			e.printStackTrace();
			return null;
		}
	
		
	}

	public boolean checkOfferId(int offerId, int objectId) {
		// TODO Auto-generated method stub
		boolean offeridFlag=false;
        try{
            
            jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
            String sql="select id from  ebay.auktion where id=? and cusebeda_objekt_id=?";
            int offeridscount =jdbcTemplate.queryForInt(sql, new Object[] {offerId,objectId});
            
            LOGGER.debug("Checking offerId count"+offeridscount);
            if(offeridscount>0)
                offeridFlag=true;
            
        }catch(Exception e){
            offeridFlag=false;
            e.printStackTrace();
        }
        
        return offeridFlag;
	}

	public boolean checkItemId(long itemId) {
		// TODO Auto-generated method stub
		
		boolean itemIdFlag=false;
        try{
            
        	LOGGER.debug("itemId isin try :{}",itemId);
            jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
            String sql="select ebayitemid from  ebay.auktion where ebayitemid=?";
            long itemidscount =jdbcTemplate.queryForLong(sql, new Object[] {itemId});
            
            LOGGER.debug("Checking itemId count:{}",itemidscount);
            if(itemidscount>0){
                itemIdFlag=true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        LOGGER.debug("itemIdFlag is :{}",itemIdFlag);
        
        return itemIdFlag;
		
	}
	
	public boolean checkItemId(int offerId, long itemId){
		
		boolean itemIdFlag = false;
		try{
			
			long ebayItemid=0;
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			String sql = "select ebayitemid from ebay.auktion where id=?";
			 ebayItemid = jdbcTemplate.queryForLong(sql, new Object[] {offerId});
			
			LOGGER.debug("ebay item id from db is :{}",itemId);
			
			if(ebayItemid == itemId){
				
				itemIdFlag = true;
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		return itemIdFlag;
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
			LOGGER.debug("Watch count from db:not available");
			e.printStackTrace();
		}
		
		return watchcount;
		
			
	}
	
	
	public List<Map<String,Object>> getTransactionData(String itemid){
		
		List<Map<String,Object>> transactiondata=null;
		try{
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			
			//String getBuyerData="select transdate, ebayitemid, quantity_purchased, buyer, buyeremail, buyeraddress, endprice, ebayorderid, status from ebay.transaction where ebayitemid ="+itemid;
			String getBuyerData="select t.transdate, t.ebayitemid, t.quantity_purchased, t.buyer, t.buyeremail, t.buyeraddress, t.endprice, t.ebayorderid, t.status, concat(k.vorname,' ',k.nachname) as buyername from ebay.transaction t left join checkout.gutscheine g on g.ebayitemid=t.ebayitemid left join checkout.kaeufer k on k.id=g.kaeufer_id where t.ebayitemid ="+itemid+" and t.ebayorderid=g.ebayorderid group by t.saleID order by t.transdate desc";
		    transactiondata=jdbcTemplate.queryForList(getBuyerData);
		    LOGGER.debug("Transactions list count {}",transactiondata);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return transactiondata;
	}
	
	
	public List<Map<String,Object>> getTimeZones(){
		List<Map<String,Object>> timeZonesList=null;
		
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		
		String sql = "select siteid,timezone from ebay.site_x_timezone";
		
		try{
			timeZonesList = jdbcTemplate.queryForList(sql.toString());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return timeZonesList;
	}
	
	public int getCarierObject(long offerid){
		int carierObject=0;
		try{
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			String sqlQuery="SELECT ifnull(ac.CollectionAccountObjectID, ifnull(s.ShopObjectID, a.cusebeda_objekt_id)) as carierID FROM ebay.auktion a LEFT JOIN ebay.auction_x_collectionAccount ac ON a.id = ac.AuctionID LEFT JOIN ebay.auction_x_shopCategory s ON a.id = s.AuctionID WHERE a.id =?";
			carierObject=jdbcTemplate.queryForObject(sqlQuery, new Object[]{offerid},Integer.class);
			
			
		}catch(Exception e){
			e.printStackTrace();	
		}
		
		return carierObject;
	}
	
	/*public List<Map<String,Object>> getRunningOffers(){
		
		select t.transdate, t.ebayitemid, t.quantity_purchased, t.buyer, t.buyeremail, t.buyeraddress, t.endprice, t.ebayorderid, t.status, concat(k.vorname," ",k.nachname) as buyername from ebay.transaction t left join checkout.gutscheine g on g.ebayitemid=t.ebayitemid left join checkout.kaeufer k on k.id=g.kaeufer_id where t.ebayitemid ="+itemid+" and t.ebayorderid=g.ebayorderid order by t.transdate desc";
		
	}*/
}


