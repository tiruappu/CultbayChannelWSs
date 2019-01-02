package com.cultuzz.channel.DAO.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.cultuzz.channel.DAO.ObjectMetaDataDAO;
import com.cultuzz.channel.jdbcTemplate.JDBCTemplate;

@Repository
public class ObjectMetaDataDAOImpl implements ObjectMetaDataDAO{

	@Autowired
	@Qualifier("ebayTemplate")
	private JDBCTemplate ebayJdbcTemplate;
	
	@Autowired
	@Qualifier("cusebedaTemplate")
	private JDBCTemplate cusebedaJdbcTemplate;
	
	private JdbcTemplate jdbcTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ObjectMetaDataDAOImpl.class);
	
	
	public List<Map<String, Object>> getSellerAccounts(int objectId) {
		// TODO Auto-generated method stub
		
		LOGGER.debug("inside the get seller accounts");
	   List<Map<String,Object>> sellerAccs = null;
	   try{
		   jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();
		   String sellerAccSql = "SELECT ObjectID, ShopName FROM eBayShops.BasicData, "
		   		+ "eBayShops.eBayShops_x_objekt WHERE BasicData.Status = 1 AND "
		   		+ "eBayShops_x_objekt.cusebeda_objekt_id =? AND "
		   		+ "eBayShops_x_objekt.shop_objekt_id = BasicData.ObjectID ORDER BY id";
		   
		   LOGGER.debug("sql query for getSellerAccounts :{}",sellerAccSql);
		   
		   sellerAccs =   jdbcTemplate.queryForList(sellerAccSql,new Object[]{objectId});
		   
	   }catch(Exception e){
		   e.printStackTrace();
	   }
		return sellerAccs;
	}

	public List<Map<String, Object>> getCollectionAccounts(int objectId){
		// TODO Auto-generated method stub
		
		LOGGER.debug("inside the get collection accounts");
	   List<Map<String,Object>> ccAccs = null;
	   try{
		   jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();
		   String ccAccSql = "select BasicData.CAccName AS CollectionAccount,"
		   		+ " BasicData.ObjectID AS CAccObjekt FROM eBayCollectionAccount.BasicData,"
		   		+ " eBayCollectionAccount.eBayCollectionAccount_x_objekt WHERE "
		   		+ "eBayCollectionAccount_x_objekt.cusebeda_objekt_id = ? AND BasicData.Status = 1 AND "
		   		+ "eBayCollectionAccount_x_objekt.cacc_objekt_id = BasicData.ObjectID";
		   
		   LOGGER.debug("sql query for getSellerAccounts :{}",ccAccSql);
		   
		   ccAccs =   jdbcTemplate.queryForList(ccAccSql,new Object[]{objectId});
		   
	   }catch(Exception e){
		   e.printStackTrace();
	   }
		return ccAccs;
	}
	
	public String getObjectName(int objectId) {
		// TODO Auto-generated method stub
		
		String objectName = null;
		
		try{
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			String objectSql = "select ebayname FROM ebay.ebaydaten WHERE cusebeda_objekt_id=?";
			objectName = (String)jdbcTemplate.queryForObject(objectSql, new Object[] { objectId },String.class);
			
			LOGGER.debug("object name is"+objectName);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return objectName;
	}

	 public String getPaypalEmail(long objectId, int siteId){
		 
		 String paypalEmail = null;
		 boolean countFlag = false;
		 try{
			 LOGGER.debug("objectId is "+objectId+" site id is"+siteId);
			 jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			 countFlag = getPaypalCount(objectId, siteId);
			 if(countFlag){
			 String paypalSql= "select paypal_email from ebay.object_paypalDetails where "
			 		+ "objectid=? and ebaysiteid=? and status=1";
			 paypalEmail = (String)jdbcTemplate.queryForObject(paypalSql, new Object[]{objectId,siteId},String.class);
			 
			 LOGGER.debug("paypal email is"+paypalEmail);
			 LOGGER.debug("!!!!!!!!!!!!!");
			 }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 return paypalEmail;
	 }
	
	 public boolean getPaypalCount(long objectId, int siteId){
		 boolean countfalg =false;
		 try{
			 jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			 String countSql = "select count(*) as count from ebay.object_paypalDetails "
			 		+ "where objectid=? and ebaysiteid=? and status=1";
			 int count = jdbcTemplate.queryForInt(countSql,new Object[]{objectId,siteId});
			  
			  if(count > 0){
				  countfalg = true;
			  }
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return countfalg;
	 }
	 
	 public List<Map<String,Object>> getProductsList(int objectId, int langId){
		 
		 List<Map<String,Object>> productlist =null;
		 
		 try{
			 
			 jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			 if(langId > 0){
			String productSql = "select p.id as id, pt.name as bezeichnung, p.price,p.standardOccupancy, p.lengthOfStay FROM ebay_product.product p,"
					+ " ebay_product.product_text pt WHERE p.id = pt.productId and p.objectId = ? "
					+ "AND pt.languageId = ? and noOfOffers = 0 order by p.id desc";
			productlist = jdbcTemplate.queryForList(productSql,new Object[]{objectId, langId});
			 }else{
			String productSql = "select p.id as id, pt.name as bezeichnung, p.price,p.standardOccupancy, p.lengthOfStay FROM ebay_product.product p,"
					+ " ebay_product.product_text pt WHERE p.id = pt.productId and p.objectId = ? "
					+ "AND pt.languageId = 1 and noOfOffers = 0 order by p.id desc";
			productlist = jdbcTemplate.queryForList(productSql,new Object[]{objectId});
			 }
		}catch(Exception e){
			 e.printStackTrace();
		 }
		 return productlist;
	 }
	 
	 public List<Map<String,Object>> getValiditiesList(long productId){
		 
		 List<Map<String,Object>> validitieslist = null;
		 try{
			 
			 jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			 String validitiesSql = "select CONCAT(DATE_FORMAT(startDate,'%d %b %Y'),' - ',DATE_FORMAT(endDate, '%d %b %Y')) "
			 		+ "AS validity, p.status FROM ebay_product.product_validity p, ebay_product.validityPeriods v "
			 		+ "WHERE p.validityPeriodsId = v.id AND productId = ?";
			 validitieslist = jdbcTemplate.queryForList(validitiesSql, new Object[]{productId});
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 return validitieslist;
	 }
	 
	 public List<Map<String,Object>> getEbayCategories(int objectId, int siteId){
		 
		 List<Map<String,Object>> categorieslist = null;
		 
		 try{
			 jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			 String categoriesSql = "select distinct kunden_kategorien.ebaykategorie2_id AS kat_id, "
			 		+ "kunden_kategorien.bezeichnung FROM "
			 		+ "ebay.kunden_kategorien, ebay.kunden_x_kategorie WHERE "
			 		+ "kunden_kategorien.ebaysite_id =? "
			 		+ "AND kunden_kategorien.id=ebay.kunden_x_kategorie.kunden_kategorie_id ORDER BY "
			 		+ "kunden_kategorien.sort";
			 categorieslist = jdbcTemplate.queryForList(categoriesSql, new Object[]{siteId});
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 return categorieslist;
	 }
	 
	 public List<Map<String,Object>> getShopCategories(long shopObjectId){
	
		 List<Map<String,Object>> shopCategorieslist = null;
		 
		 try{
			 
			 jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();
			 
			 String shoCategoriesSql = "SELECT CategoryID, CategoryName, ParentCategoryID FROM eBayShops.Categories "
			 		+ "WHERE ObjectID = ? ORDER BY OrderID";
			 shopCategorieslist = jdbcTemplate.queryForList(shoCategoriesSql, new Object[]{shopObjectId});
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 return shopCategorieslist;
	 }
	 
	 
	 public boolean checkProductValidity(long productId, String startDate){
			
			LOGGER.debug("productId for validity is :{}",productId);
			
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			
			int validity = 0;
			String validityEndDate = null;
			boolean flag = false;
		
			
			try{
				
			
				if(productId !=0){
					
					LOGGER.debug("Inside try for validity period");
					
					String validitySql = "select count(*) from ebay_product.validityPeriods vp join ebay_product.product_validity pv on pv.validityPeriodsId = vp.id where  pv.productId = "+productId +" and date(vp.endDate)>='"+startDate+"' and pv.status=1";
				
					validity = jdbcTemplate.queryForInt(validitySql.toString());
					
					LOGGER.debug("validity is:{}",validity);
					
					if(validity >0){
						
						flag = true;
					
					}
				}
			}catch(Exception e){
				
				e.printStackTrace();
			}
			
			
			return flag;
		}
}
