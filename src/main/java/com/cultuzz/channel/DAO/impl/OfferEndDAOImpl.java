package com.cultuzz.channel.DAO.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.DAO.OfferEndDAO;
import com.cultuzz.channel.helper.impl.OfferEndHelperImpl;
import com.cultuzz.channel.jdbcTemplate.JDBCTemplate;

/**
 * This class is used to connect to DB to retrieve the offer information
 * 
 * @author sowmya
 * 
 */
@Component
public class OfferEndDAOImpl implements OfferEndDAO {

	@Autowired
	@Qualifier("ebayTemplate")
	private JDBCTemplate ebayJdbcTemplate;

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("cusebedaTemplate")
	private JDBCTemplate cusebedaJdbcTemplate;

	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(OfferEndDAOImpl.class);

	/**
	 * This method is used to get the details for particular item id
	 */
	public List<Map<String, Object>> getItemDetails(String ebayItemid)
			throws Exception {
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();

		String sql = "SELECT status,ebaysiteid,id,vorlage_id,cusebeda_objekt_id,anzahlgebote FROM ebay.auktion WHERE ebayItemid = ?";
		List<Map<String, Object>> itemValues = jdbcTemplate.queryForList(sql,
				new Object[] { ebayItemid });

		return itemValues;
	}

	/**
	 * This method is required to get the offer details for particular offer id
	 */
	public List<Map<String, Object>> getOfferDetails(int offerId) {

		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();

		String sql = "SELECT status, ebayitemid,ebaysiteid,vorlage_id,cusebeda_objekt_id,anzahlgebote FROM ebay.auktion WHERE id = ?";
		List<Map<String, Object>> offerValues = jdbcTemplate.queryForList(sql,
				new Object[] { offerId });
		return offerValues;
	}

	/**
	 * This method is used to update the status in auktion table
	 */
	public boolean updateItemStatus(String ebayItemId, int offerId, int objecId)
			throws Exception {
		String sql = null;
		int status = 0;

		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		if (ebayItemId != null) {
			sql = "update ebay.auktion set status = 2 where ebayitemid = ?";
			status = jdbcTemplate.update(sql, new Object[] { ebayItemId });
		} else if (offerId != 0) {
			
			int productid=this.getIsProductOffer(offerId);
			
			if(productid>0){
				boolean insertStat=insertOfferDetails(offerId,objecId,productid);
				
				if(insertStat){
					boolean deleteStat=	this.deleteFutureOffersAuktion(offerId);
					if(deleteStat)
						status=1;
					
				}
			}else{
			boolean deleteStat=this.deleteFutureOffersAuktion(offerId);
			if(deleteStat)
				status=1;
			}
			/*sql = "update ebay.auktion set status = 2 where id = ?";
			status = jdbcTemplate.update(sql, new Object[] { offerId });*/
		}
		if (status > 0) {
			//updateAuctionSichStatus(ebayItemId, offerId);
			return true;
		} else {
			return false;
		}

	}
	
	public int getIsProductOffer(int offerid){
		
		int productid=0;
		try{
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			String query="select v.arrangement_id from ebay.auktion a left join ebay.vorlage v on a.vorlage_id=v.id where a.id=? and v.arrangement_id>0";
			 productid=jdbcTemplate.queryForObject(query, new Object[]{offerid},Integer.class);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return productid;
	}
	
	public boolean insertOfferDetails(int offerid,int objectid,int productid){
		boolean insertStatus=false;
		
		int carierid=this.getCarierObject(offerid);
		
		try{
			namedJdbcTemplate = ebayJdbcTemplate.getNamedParameterJdbcTemplate();
			StringBuffer insertOfferDetailsQuery = new StringBuffer("INSERT INTO ebay.offerDetails(auction_id, objectId, productId, carrierID, type)");
			insertOfferDetailsQuery.append(" values(:auctionid,:objectid,:productid,:carierid,:type)");
			
			Map<String, Object> offerDetails = new HashMap<String, Object>();
			offerDetails.put("auctionid", offerid);
			offerDetails.put("objectid",objectid);
			offerDetails.put("productid",productid);
			offerDetails.put("carierid",carierid);
			offerDetails.put("type",3);
			
			SqlParameterSource sourcevorlageparam = new MapSqlParameterSource(offerDetails);
			LOGGER.debug("insert offerdetails save query"+insertOfferDetailsQuery.toString());
			int offerdetailscount=namedJdbcTemplate.update(insertOfferDetailsQuery.toString(), sourcevorlageparam);
			
			if(offerdetailscount>0)
				insertStatus=true;
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return insertStatus;
	}
	
	public int getCarierObject(int offerid){
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
	
	
	
	public boolean deleteFutureOffersAuktion(int offerid){
		boolean deleteStatus=false;
		
		try{
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			String sql="delete from ebay.auktion where id=? and status=0";
			int effectedrows = jdbcTemplate.update(sql, new Object[] {offerid});
			
			if(effectedrows>0)
					deleteStatus=true;
			
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.debug("Exception occured at offer delete");
		}
		
		return deleteStatus;
	}

	/**
	 * This method is required to update the status in auktion_sich table
	 * 
	 * @param ebayItemId
	 * @param offerId
	 * @return
	 * @throws Exception
	 */
	private void updateAuctionSichStatus(String ebayItemId, int offerId)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = null;
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		if (ebayItemId != null) {
			sql = "update ebay.auktion_sich set status = 2 where ebayitemid = ?";
			jdbcTemplate.update(sql, new Object[] { ebayItemId });
		} else if (offerId != 0) {
			sql = "update ebay.auktion_sich set status = 2 where id = ?";
			jdbcTemplate.update(sql, new Object[] { offerId });
		}

	}

	/**
	 * This method is used to update the repeat status after force end of the
	 * offer
	 */
	public void updateRepeatStatus(int offerId) {
		// TODO Auto-generated method stub
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		String sql = "update ebay.auktion_x_source set repeat_status=0 where auktion_id=?";
		jdbcTemplate.update(sql, new Object[] { offerId });
	}

	/**
	 * This method is required to check the Object validity
	 * 
	 * @param objectId
	 * @return
	 */
	public boolean checkForObjectValidity(int objectId) {
		boolean objectIdstatus = false;
		LOGGER.debug("Entered checkForObjectValidity ");

		jdbcTemplate=cusebedaJdbcTemplate.getJdbcTemplate();
		String objectIdSql = "select count(id) from  cusebeda.objekt where id=? ";
		@SuppressWarnings("deprecation")
		int objectWithItemIdCount = jdbcTemplate.queryForInt(objectIdSql,
				new Object[] { objectId });
		LOGGER.debug("Count for objekt Id :::" + objectWithItemIdCount);
		if (objectWithItemIdCount != 0) {
			objectIdstatus = true;
		}

		return objectIdstatus;
	}

	
	/**
	 * This method is used to determine the shop object and related token
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getShoporCollectionObjektId(
			String ebayItemId) {

		String objectIdSql = "SELECT auktion.ebaysofortkauf,auktion.ebaysiteid,auktion.startpreis,ebaydaten_token.token,"
				+ " ebaydaten_token.cusebeda_objekt_id FROM ebay.ebaydaten_token,ebay.ebaydaten, ebay.auktion "
				+ " LEFT JOIN ebay.auction_x_shopCategory ON auction_x_shopCategory.AuctionID = auktion.id "
				+ " LEFT JOIN ebay.auction_x_collectionAccount ON auction_x_collectionAccount.AuctionID = auktion.id "
				+ " WHERE auktion.ebayitemid =?"
				+ " AND ebaydaten.cusebeda_objekt_id = IFNULL(auction_x_shopCategory.ShopObjectID,"
				+ " IFNULL(auction_x_collectionAccount.CollectionAccountObjectID,auktion.cusebeda_objekt_id)) "
				+ " AND ebaydaten_token.cusebeda_objekt_id=ebaydaten.cusebeda_objekt_id limit 1";

		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		List<Map<String, Object>> objectValues = jdbcTemplate.queryForList(
				objectIdSql, new Object[] { ebayItemId });

		return objectValues;

	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
