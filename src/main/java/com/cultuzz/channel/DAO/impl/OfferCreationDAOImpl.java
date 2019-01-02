package com.cultuzz.channel.DAO.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.DAO.OfferCreationDAO;
import com.cultuzz.channel.XMLpojo.OfferCreationRQ;
import com.cultuzz.channel.jdbcTemplate.JDBCTemplate;
import com.cultuzz.channel.template.pojo.Vorlage;
import com.cultuzz.channel.template.pojo.VorlageMapper;
import com.cultuzz.channel.template.pojo.Vorlage_gutscheine;
import com.cultuzz.channel.template.pojo.Vorlage_gutscheineMapper;


@Component
public class OfferCreationDAOImpl implements OfferCreationDAO{

	
	private static final Logger LOGGER = LoggerFactory.getLogger(OfferCreationDAOImpl.class);
	
	@Autowired
	@Qualifier("ebayTemplate")
	private JDBCTemplate ebayJdbcTemplate;
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("cusebedaTemplate")
	private JDBCTemplate cusebedaJdbcTemplate;
	
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	public Vorlage getAuktionDetails(int templateId) {
		// TODO Auto-generated method stub
	
		Vorlage vorlage = null;
		
		
		LOGGER.debug("templateId for AuktionDetails is :{}",templateId);
		
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		
		String sql="select ebayoptionen, ebaybetreffzeile, template_id, untertitel, ebaykategorieid1, ebaykategorieid2, ueberschrift, text from ebay.vorlage where id =?";
		
		try{
		vorlage = jdbcTemplate.queryForObject(sql.toString(),
				new Object[] {templateId}, new VorlageMapper());
		
		LOGGER.debug("offer in offerDAO is:{}",vorlage);
		        
		   }catch(EmptyResultDataAccessException e){
        	  
			   	e.printStackTrace();
          
                vorlage = null;		   
		   }

		return vorlage;

	   }
	
	public String getCusebeda_objekt_ort(int objectId){
		
		LOGGER.debug("objectId in offerDAO is :{}",objectId);
		
		String cusebeda_objekt_ort = null;
		
		jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();
		
		String sql="SELECT cusebeda.objekt.ort FROM cusebeda.objekt WHERE objekt.id =?";
		
		
		try{
			
			cusebeda_objekt_ort = jdbcTemplate.queryForObject(sql.toString(),new Object[]{objectId},String.class);
         
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		return cusebeda_objekt_ort;
	}
	
	public int saveAuktion(OfferCreationRQ offerCreationRQ, Vorlage vorlage, String cusebeda_objekt_ort) {
		// TODO Auto-generated method stub
		int offerId = 0;
		
		LOGGER.debug("vorlage details is:{}",vorlage.toString());
		
		StringBuffer sql = new StringBuffer(
				"insert into ebay.auktion(vorlage_id,ebaysiteid,ebaykategorieid,ebaykategorieid2,cusebeda_objekt_id,cusebeda_objekt_ort,template_id,startdatum,dauer,ebayueberschrift,untertitel,ebayueberschrifthighlight,ebayueberschriftfett,ebaygaleriebild,top_startseite,ebaytop,ueberschrift,text,startpreis,ebaysofortkauf,status,AuctionMasterTypeID,MoneyBackGuarantee,quantity,retailprice)");

		sql.append(" values(:vorlage_id,:ebaysiteid,:ebaykategorieid,:ebaykategorieid2,:cusebeda_objekt_id,:cusebeda_objekt_ort,:template_id,:startdatum,:dauer,:ebayueberschrift,:untertitel,:ebayueberschrifthighlight,:ebayueberschriftfett,:ebaygaleriebild,:top_startseite,:ebaytop,:ueberschrift,:text,:startpreis,:ebaysofortkauf,:status,:AuctionMasterTypeID,:MoneyBackGuarantee,:quantity,:retailprice)");
 
		namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();

		Map<String, Object> bind = new HashMap<String, Object>();
		bind.put("vorlage_id", offerCreationRQ.getTemplateId());
		bind.put("ebaysiteid", offerCreationRQ.getSiteId());
		bind.put("ebaykategorieid", vorlage.getEbaykategorieid1());
		if(vorlage.getEbaykategorieid2()==null){
			bind.put("ebaykategorieid2", 0);	
		}else{
		bind.put("ebaykategorieid2", vorlage.getEbaykategorieid2());
		}
		bind.put("cusebeda_objekt_id", offerCreationRQ.getObjectId());
		bind.put("cusebeda_objekt_ort", cusebeda_objekt_ort);
		bind.put("template_id", vorlage.getTemplate_id());
		bind.put("startdatum", offerCreationRQ.getStartTime());
		if(offerCreationRQ.getListingDuration().compareToIgnoreCase("GTC")==0){
			
			bind.put("dauer",9999);
		}else{
		bind.put("dauer",offerCreationRQ.getListingDuration());
		}
		bind.put("ebayueberschrift", vorlage.getEbaybetreffzeile());
		bind.put("untertitel", vorlage.getUntertitel());
		
		bind.put("ebayueberschrifthighlight", vorlage.getEbayueberschrifthighlight());
		bind.put("ebayueberschriftfett", vorlage.getEbayueberschriftfett());
		bind.put("ebaygaleriebild", vorlage.getEbaygaleriebild());
		bind.put("top_startseite", vorlage.getTop_startseite());
		bind.put("ebaytop", vorlage.getEbaytop());
		bind.put("ueberschrift", vorlage.getUeberschrift());
		bind.put("text", vorlage.getText());
		bind.put("status", 0);
		if(offerCreationRQ.getListingType().compareToIgnoreCase("Auction")==0){
			bind.put("AuctionMasterTypeID", 1);
			bind.put("startpreis", offerCreationRQ.getPrice());
			bind.put("ebaysofortkauf", null);
		}else if(offerCreationRQ.isSetShopObjectId() && Integer.parseInt(offerCreationRQ.getShopObjectId())>0){
				
				bind.put("AuctionMasterTypeID",4);
				bind.put("startpreis", offerCreationRQ.getPrice());
				bind.put("ebaysofortkauf", null);
			
		}else{
			
			bind.put("AuctionMasterTypeID", 1);
			bind.put("ebaysofortkauf", offerCreationRQ.getPrice());
			bind.put("startpreis", 0);
		}
		if (offerCreationRQ.isSetSiteId()) {
               if (offerCreationRQ.getSiteId() == 0) {
            	   bind.put("MoneyBackGuarantee",1);
                } else {
                	bind.put("MoneyBackGuarantee",0);
                }
            
        }
		
		bind.put("quantity", offerCreationRQ.getQuantity());
		if(offerCreationRQ.isSetRetailPrice() && offerCreationRQ.getRetailPrice()!=null){

			bind.put("retailprice",offerCreationRQ.getRetailPrice());
		}else{
			
			bind.put("retailprice",null);
		}
		SqlParameterSource paramSource = new MapSqlParameterSource(bind);
		KeyHolder keyHolder = new GeneratedKeyHolder();

		this.namedJdbcTemplate.update(sql.toString(), paramSource,
				keyHolder);
		offerId = keyHolder.getKey().intValue();
		// project.setId(id);
		return offerId;
	}
	
	
	public int saveAuktion_x_source(int offerId, int source, boolean repeat_status){
		
		LOGGER.debug("offer id for auktion_x_series is:{}",offerId);
		LOGGER.debug("source for auction_x_series is:{}",source);
		LOGGER.debug("repeat status for auction_x_series is:{}",repeat_status);
		
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		
		int auction_x_seriesCount = 0;
		
		String sql="insert into ebay.auktion_x_source(auktion_id, source, repeat_status) values(?,?,?)";
		
		try{
			if(repeat_status == true){
			
			auction_x_seriesCount = jdbcTemplate.update(sql.toString(), new Object[] {offerId, source,1});
           
			LOGGER.debug("auction_x_series is is::{}", auction_x_seriesCount);
			}else{
				
			auction_x_seriesCount = jdbcTemplate.update(sql.toString(), new Object[] {offerId, source,0});
		           
			LOGGER.debug("auction_x_series is is::{}", auction_x_seriesCount);
			}
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		return auction_x_seriesCount;
	}
	
	/*public int saveAuktion_texte(int templateId, int offerId){
		
			Vorlage_gutscheine vorlage_gutscheine = null;
		
		
		LOGGER.debug("templateId for Auktion_x_texte is :{}",templateId);
		
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		
		String vorlage_gutscheineSql="SELECT gutschein_text, gueltigkeit_text, gueltigkeit FROM ebay.vorlage_gutscheine WHERE vorlage_id =?";
		
		String auktion_texteSql = "REPLACE INTO ebay.auktion_texte SET auktion_id =?, vorlage_id =?, gutschein_text  =?, gueltigkeit_text =?, gueltigkeit =?";
		
		int auktion_texteCount = 0;
		
		try{
		
		vorlage_gutscheine = jdbcTemplate.queryForObject(vorlage_gutscheineSql.toString(),
				new Object[] {templateId}, new Vorlage_gutscheineMapper());
		
		LOGGER.debug("vorlage_gutscheine in offerCreationDAO is:{}",vorlage_gutscheine);
		
		         if(vorlage_gutscheine != null){
		     
		auktion_texteCount = jdbcTemplate.update(auktion_texteSql.toString(),
								new Object[] {offerId, templateId,vorlage_gutscheine.getGutschein_text(),
								vorlage_gutscheine.getGueltigkeit_text(),vorlage_gutscheine.getGueltigkeit()});    	 
		          
				LOGGER.debug("auktion_texteCount is :{}",auktion_texteCount);
		  
		         }
		
		        
		   }catch(EmptyResultDataAccessException e){
        	  
			   	e.printStackTrace();
          
           }

			return auktion_texteCount;
	}*/
	
	public void saveAuction_x_collectionAccount(int offerId, int templateId, int collectionObjectId){
		
		LOGGER.debug("offer id for auktion_x_CollectionAccount is:{}",offerId);
		LOGGER.debug("source for auction_x_CollectionAccount is:{}",templateId);
		LOGGER.debug("repeat status for auction_x_CollectionAccount is:{}",collectionObjectId);
		
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		
		int auction_x_collectionAccountCount = 0;
		
		String sql="INSERT INTO ebay.auction_x_collectionAccount(AuctionID, AuctionMasterID, CollectionAccountObjectID) values(?,?,?)";
		
		try{
			
			auction_x_collectionAccountCount = jdbcTemplate.update(sql.toString(), new Object[] {offerId, templateId,collectionObjectId});
           
			LOGGER.debug("auction_x_collection is is::{}", auction_x_collectionAccountCount);
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
	}
	
		public void saveAuction_x_shopCategory(int offerId, int shopObjectId, BigInteger shopCategory1, BigInteger shopCategory2){
		
		LOGGER.debug("offer id for auktion_x_shopCategory is:{}",offerId);
		LOGGER.debug("repeat status for auction_x_shopCategory is:{}",shopObjectId);
		LOGGER.debug("shop category1 for auction_x_shopCategory is:{}",shopCategory1);
		LOGGER.debug("shop category2 for auction_x_shopCategory is:{}",shopCategory2);
		
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		
		int auction_x_shopCategoryCount = 0;
		
		String sql="INSERT INTO ebay.auction_x_shopCategory(AuctionID, ShopObjectID, ShopCategoryID, ShopCategoryID2) values(?,?,?,?)";
		
		try{
			LOGGER.debug("auction_x_shopCategory is is::{}", sql);
			auction_x_shopCategoryCount = jdbcTemplate.update(sql.toString(), new Object[] {offerId, shopObjectId, shopCategory1, shopCategory2});
           
			LOGGER.debug("auction_x_shopCategory is is::{}", auction_x_shopCategoryCount);
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
	}

		
		
		public int getProductId(int templateId){
			
			
			LOGGER.debug("templateId for productId is:{}",templateId);
			
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			
			int productId = 0;
			
			String productIdSql = "select arrangement_id from ebay.vorlage where id = ?";
			
			LOGGER.debug("before try for productId");
			
			try{
				
				LOGGER.debug("productId sql for:{}",productIdSql);
				productId = jdbcTemplate.queryForInt(productIdSql.toString(), new Object[] {templateId});
		           
				LOGGER.debug("productId is is::{}", productId);
				
			}catch(Exception e){
				
				e.printStackTrace();
			}
			
			return productId;
		}
		
	
		public boolean checkProductValidity(int productId, String startDate){
			
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
	
	public void saveAuktions_texte(int templateId, int offerId){
			
			Vorlage_gutscheine vorlage_gutscheine = null;
		
		
		LOGGER.debug("templateId for Auktion_x_texte is :{}",templateId);
		
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		
		String vorlage_gutscheineSql="select v.text, v.text2, vg.gutschein_text,vg.gueltigkeit_text,vg.gueltigkeit from ebay.vorlage v join ebay.vorlage_gutscheine vg where vorlage_id = ? and v.id = vg.vorlage_id";
		
	//	int auktion_texteId = 0;
		int auktion_texteInsertCount=0;
		
		try{
		
		vorlage_gutscheine = jdbcTemplate.queryForObject(vorlage_gutscheineSql.toString(),
				new Object[] {templateId}, new Vorlage_gutscheineMapper());
		
		LOGGER.debug("vorlage_gutscheine in offerCreationDAO is:{}",vorlage_gutscheine);
		
		         if(vorlage_gutscheine != null){
		     
		        	 
//		  StringBuffer sql = new StringBuffer(
//		     "insert into ebay.auktions_texte(angebotstext, gutschein_text, gueltigkeit_text, gueltigkeit_tage, angebotstext2)");
//
//		     sql.append(" values(:angebotstext,:gutschein_text,:gueltigkeit_text,:gueltigkeit_tage,:angebotstext2)");
//		      
//		     namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate(); 	
//		     
//		     Map<String, Object> bind = new HashMap<String, Object>();
//				bind.put("angebotstext",vorlage_gutscheine.getText());
//				bind.put("gutschein_text",vorlage_gutscheine.getGutschein_text());
//				bind.put("gueltigkeit_text",vorlage_gutscheine.getGueltigkeit_text());
//				bind.put("gueltigkeit_tage",vorlage_gutscheine.getGueltigkeit());
//				bind.put("angebotstext2",vorlage_gutscheine.getText2());
//		        	 
//				SqlParameterSource paramSource = new MapSqlParameterSource(bind);
//				KeyHolder keyHolder = new GeneratedKeyHolder();
//
//				this.namedJdbcTemplate.update(sql.toString(), paramSource,
//						keyHolder);
//				auktion_texteId = keyHolder.getKey().intValue();
		  
				boolean auction_texteCount = this.getAuction_texteCount(offerId);
		         
		         LOGGER.debug("auction_texteCount boolean value is:{}",auction_texteCount);
		         
		         if(auction_texteCount){
		        	 
		        	 String auktion_texteSql = "update ebay.auktion_texte set vorlage_id = ? , gutschein_text =?, gueltigkeit_text = ?,gueltigkeit = ? where auktion_id = ?";
		        	 
		        	  auktion_texteInsertCount = jdbcTemplate.update(auktion_texteSql.toString(), new Object[] {templateId,vorlage_gutscheine.getGutschein_text(),
							vorlage_gutscheine.getGueltigkeit_text(),vorlage_gutscheine.getGueltigkeit()});
		             
		 			LOGGER.debug("auction_texteInsertCount is is::{}", auktion_texteInsertCount);
		         }else{
		        	 
		        	 String auktion_texteSql = "INSERT INTO ebay.auktion_texte(auktion_id, vorlage_id, gutschein_text, gueltigkeit_text, gueltigkeit) values(?,?,?,?,?)";
		        	 
		        	 auktion_texteInsertCount = jdbcTemplate.update(auktion_texteSql.toString(),
								new Object[] {offerId, templateId,vorlage_gutscheine.getGutschein_text(),
								vorlage_gutscheine.getGueltigkeit_text(),vorlage_gutscheine.getGueltigkeit()});    	 
	
		        	 LOGGER.debug("auction_texteInsertCount is is::{}", auktion_texteInsertCount);

		         }
		         
		        }}catch(EmptyResultDataAccessException e){
        	  
			   	e.printStackTrace();
          
           }

	}

//	public int updateVorlageAuktions_texte(int auktion_texteId, int templateId) {
//		// TODO Auto-generated method stub
//		
//		LOGGER.debug("auktion_texteId is:{}",auktion_texteId);
//		
//		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
//		
//		int vorlageAuktions_texteCount = 0;
//		
//		String sql="update ebay.vorlage set auktions_texte_id = ? where id = ?";
//		
//		try{
//			
//			vorlageAuktions_texteCount = jdbcTemplate.update(sql.toString(), new Object[] {auktion_texteId, templateId});
//           
//			LOGGER.debug("auction_x_series is is::{}", vorlageAuktions_texteCount);
//			
//		}catch(Exception e){
//			
//			e.printStackTrace();
//		}
//		
//		return vorlageAuktions_texteCount;
//	}
	
	
	public List<Map<String, Object>> getAuktionPath(int objectId, int templateId){
		
		LOGGER.debug("objectId in offerCreationDAOImpl for getPath is :{}",objectId);
		LOGGER.debug("objectId in offerCreationDAOImpl for getPath is :{}",templateId);
		
		List<Map<String, Object>> urlAuktionPath=null;
		
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		
		String sql="SELECT auktionbildpath FROM ebay.vorlage_x_che_auktionbild WHERE cusebeda_objekt_id =? and vorlage_id = ?";
		
		
		try{
			
			urlAuktionPath = jdbcTemplate.queryForList(sql,new Object[] { objectId, templateId });

			LOGGER.debug("urlAuktion path list is:{}",urlAuktionPath.toString());
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		return urlAuktionPath;
	}
	
	public String getGaleryPath(int objectId, int templateId){
		
		LOGGER.debug("objectId in offerCreationDAOImpl for getPath is :{}",objectId);
		LOGGER.debug("objectId in offerCreationDAOImpl for getPath is :{}",templateId);
		
		String urlGaleryPath = null;
		
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		
		String sql="SELECT galeriebild_path FROM ebay.vorlage_x_che_galeriebild WHERE cusebeda_objekt_id =? and vorlage_id = ?";
		
		
		try{
			
			urlGaleryPath = jdbcTemplate.queryForObject(sql.toString(),new Object[]{objectId, templateId},String.class);
         
			LOGGER.debug("galeryBild path isssssss:{}",urlGaleryPath);
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		return urlGaleryPath;
	}
	
	public int saveAuktion_x_pictures(int offerId, int templateId, int sourceId, int type, String url){
		
		LOGGER.debug("offer id for auktion_x_pictures is:{}",offerId);
		LOGGER.debug("sourceId for auktion_x_pictures is:{}",sourceId);
		LOGGER.debug("type for auktion_x_pictures is:{}",type);
		LOGGER.debug("url for auktion_x_pictures is:{}",url);
		
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		
		int auction_x_picturesCount = 0;
		
		String sql="INSERT INTO ebay.auktion_x_pictures(auktionId, vorlageId, sourceId, type, URL) values(?,?,?,?,?)";
		
		try{
			
			auction_x_picturesCount = jdbcTemplate.update(sql.toString(), new Object[] {offerId, templateId, sourceId, type, url});
           
			LOGGER.debug("auction_x_pictures count is::{}", auction_x_picturesCount);
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		return auction_x_picturesCount;
	}
	
	public int getAuctionMasterTypeID(int templateId){
		
		
		LOGGER.debug("templateId for auctionMasterTypeID is:{}",templateId);
		
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		
		int auctionMasterTypeID = 0;
		
		String auctionMasterTypeIDSql = "select auctionMasterTypeID from ebay.vorlage where id = ?";
		
		LOGGER.debug("before try for auctionMasterTypeID");
		
		try{
			
			LOGGER.debug("auctionMasterTypeID sql for:{}",auctionMasterTypeIDSql);
			auctionMasterTypeID = jdbcTemplate.queryForInt(auctionMasterTypeIDSql.toString(), new Object[] {templateId});
	           
			LOGGER.debug("auctionMasterTypeID is is::{}", auctionMasterTypeID);
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		return auctionMasterTypeID;
	}
	
	public boolean checkShopObjectId(int objectId, int shopObjectId){
	
		boolean shopObjectIdFlag=false;
		
		try{
			LOGGER.debug("inside checking shop object id");
			jdbcTemplate=cusebedaJdbcTemplate.getJdbcTemplate();
			String shopObjectIdSql="SELECT count(ObjectID) FROM eBayShops.BasicData join eBayShops.eBayShops_x_objekt on eBayShops_x_objekt.shop_objekt_id = BasicData.ObjectID WHERE eBayShops_x_objekt.cusebeda_objekt_id =? AND eBayShops_x_objekt.shop_objekt_id =? AND BasicData.Status = 1";
			int shopObjectIdCount =jdbcTemplate.queryForInt(shopObjectIdSql, new Object[] { objectId,shopObjectId });
			
			if(shopObjectIdCount>0)
				shopObjectIdFlag=true;
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		return shopObjectIdFlag;
	}
	
	public boolean checkCollectionObjectId(int objectId, int collectionObjectId){
		
		boolean collectionObjectIdFlag=false;
		
		try{
			LOGGER.debug("inside checking collection object id");
			jdbcTemplate=cusebedaJdbcTemplate.getJdbcTemplate();
			String collectionObjectIdSql="SELECT count(ObjectID) FROM eBayCollectionAccount.BasicData join eBayCollectionAccount.eBayCollectionAccount_x_objekt on eBayCollectionAccount_x_objekt.cacc_objekt_id = BasicData.ObjectID WHERE eBayCollectionAccount_x_objekt.cusebeda_objekt_id =? AND eBayCollectionAccount_x_objekt.cacc_objekt_id =? AND BasicData.Status = 1";
			int collectionObjectIdCount =jdbcTemplate.queryForInt(collectionObjectIdSql, new Object[] { objectId, collectionObjectId });
			
			if(collectionObjectIdCount>0)
				collectionObjectIdFlag=true;
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		return collectionObjectIdFlag;
	}

//	public int saveAuktion_zusatz(int offerId, int auktion_texte_id){
//		
//		LOGGER.debug("offer id for auktion_zusatz is:{}",offerId);
//		LOGGER.debug("auktions_texte_Id for auktion_zusatz is{}",auktion_texte_id);
//		
//		
//		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
//		
//		int auktion_zusatzCount = 0;
//		
//		String sql="INSERT INTO ebay.auktion_zusatz(auktion_id, auktions_texte_id) values(?,?)";
//		
//		try{
//			
//			auktion_zusatzCount = jdbcTemplate.update(sql.toString(), new Object[] {offerId, auktion_texte_id});
//           
//			LOGGER.debug("auction_zusatzCount in DAO is::{}", auktion_zusatzCount);
//			
//		}catch(Exception e){
//			
//			e.printStackTrace();
//		}
//		c
//		return auktion_zusatzCount;
//	}
	
	public int getVorlageShopObjectID(int templateId){
		
		LOGGER.debug("templateId in offerCreationDAO is :{}",templateId);
		
		int shopObjectId = 0;
		
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		
		String sql="SELECT ShopObjectID FROM ebay.vorlage_x_shop WHERE AuctionMasterID =?";
		
		
		try{
			
			shopObjectId = jdbcTemplate.queryForInt(sql.toString(),new Object[]{templateId});
         
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		return shopObjectId;
	}
	
	public List<Map<String, Object>> getVorlage_x_ShopDetails(int templateId,int shopObjectId){
		// TODO Auto-generated method stub
	
		LOGGER.debug("templateId in offerCreationDAO is :{}",templateId);
		LOGGER.debug("shopObjectId in offerCreationDAO is :{}",shopObjectId);
		
		String sql="select ShopCategoryID,ShopCategory2ID from ebay.vorlage_x_shop where AuctionMasterID=? and ShopObjectID=?";
		
		List<Map<String, Object>> Vorlage_x_ShopDetails=null;
		try{
			  LOGGER.debug("sql for shop:{}",sql);
			
				jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
				
				Vorlage_x_ShopDetails = jdbcTemplate.queryForList(sql,new Object[] { templateId, shopObjectId });
				 
			}
			catch(Exception e){
        	  
			   	e.printStackTrace();
          
			}

		return Vorlage_x_ShopDetails;

	   }
	
	public boolean getAuction_texteCount(int offerId){
		
		LOGGER.debug("offerId for auction_texte count is :{}",offerId);
		
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		
		int count = 0;
		
		boolean flag = false;
	
		
		try{
			
		
			if(offerId !=0){
				
				LOGGER.debug("Inside try for count of auction_texte");
				
				String auction_texteCountSql = "select count(*) from ebay.auktion_texte where auktion_id = ?";
			
				count = jdbcTemplate.queryForInt(auction_texteCountSql.toString(),offerId);
				
				LOGGER.debug("count is:{}",count);
				
				if(count >0){
					
					flag = true;
				
				}
			}
		}catch(Exception e){
			
			e.printStackTrace();
		}
	
		
		return flag;
	}
	
	
	public List<Map<String,Object>> getOffersData(int objectid,Integer templateid){
		List<Map<String, Object>> offersdata=null;
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		
		try{
			String offerDataQuery="select startdatum,dauer,status,DATE_ADD(startdatum,INTERVAL dauer DAY) as enddate from ebay.auktion where cusebeda_objekt_id=? and vorlage_id=? and status in (0,1)";
			offersdata=jdbcTemplate.queryForList(offerDataQuery, new Object[] {objectid,templateid});
		}catch(Exception e){
			LOGGER.debug("offers data templateid {}",templateid);
		}
		return offersdata;
	}
	
	
	}
