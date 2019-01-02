package com.cultuzz.channel.DAO.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import com.cultuzz.channel.DAO.OfferReviseDAO;
import com.cultuzz.channel.XMLpojo.AdditionalOptionsType;
import com.cultuzz.channel.XMLpojo.OfferReviseRQ;
import com.cultuzz.channel.helper.impl.OfferReviseHelperImpl;
import com.cultuzz.channel.jdbcTemplate.JDBCTemplate;
import com.cultuzz.channel.template.pojo.OfferBO;
import com.cultuzz.channel.template.pojo.PriceType;
import com.cultuzz.channel.template.pojo.OfferMapper;
import com.cultuzz.channel.helper.OfferReviseMapper;
import com.cultuzz.channel.helper.PriceTypeMapper;

/**
 * This class is a DAO class to interact with database the Revise request
 * 
 * @author rohith
 * 
 */

@Component
public class OfferReviseDAOImpl implements OfferReviseDAO {

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

	// rquantity = revise_quantity and rtime = revise_time...
	public int getQuantityAuktion(String itemId) {

		int quantity = 0;
		Boolean statusForQuantity = false;
		try {
			LOGGER.debug("Control inside checkChangeQuantity method...");
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			String sqlForQuantity = "SELECT quantity FROM ebay.auktion WHERE STATUS=1 AND ebayitemid= ?";
			LOGGER.debug("Statemetsssssss for quantity " + sqlForQuantity);

			quantity = jdbcTemplate.queryForInt(sqlForQuantity,
					new Object[] { itemId });
			LOGGER.debug("Control BEFORE countQuantity block method...");
			LOGGER.debug("-------------------------------------------------------------------");
			LOGGER.debug("Count Of Quan from revise table:::" + quantity);
			LOGGER.debug("-------------------------------------------------------------------");

		} catch (EmptyResultDataAccessException e) {
			LOGGER.debug("Control inside catch block method fro quantity...");
			e.printStackTrace();
			return quantity;

		} catch (IncorrectResultSetColumnCountException ex) {
			ex.printStackTrace();
			LOGGER.debug("Control inside catch block method for quantity IncorrectResultSetColumnCountException...");
			return quantity;
		}
		LOGGER.debug("Status For Quantity..." + statusForQuantity);
		return quantity;
	}

	public int saveReviseDetails(int aucktionId, String endDate,
			double lastprice, double price, String quantity, String quantityRes,int sourceId,
			OfferReviseRQ offerReviseReq, String retailPrice) {

		int id = 0;

		LOGGER.debug("Inside save details Method.....");

		namedJdbcTemplate = ebayJdbcTemplate.getNamedParameterJdbcTemplate();

		StringBuffer insertReviseDetails = new StringBuffer(
				"insert into ebay.reviseItemPrice(AuctionID,itemId,lastPrice,changePrice,revise_Time,userId,quantity_restriction,revise_quantity,sourceId,retailprice) ");
		insertReviseDetails
				.append(" values(:aucktionId,:itemId,:lastprice,:price,:revise_Time,:userId,:quantity_restriction,:quantity,:sourceId,:retailPrice)");

		Map<String, Object> bindData = new HashMap<String, Object>();
		bindData.put("aucktionId", aucktionId);

		bindData.put("itemId", offerReviseReq.getEbayItemId());
		bindData.put("lastprice", lastprice);
		bindData.put("revise_Time", endDate);
		bindData.put("userId", 2); // For CHE UserId yet to be decided.For
									// Testing purpose value is 2.
		bindData.put("price", price);

		bindData.put("quantity_restriction", quantityRes);
		bindData.put("quantity", quantity);
		bindData.put("sourceId", sourceId);
		bindData.put("retailPrice", retailPrice);
		SqlParameterSource paramSource = new MapSqlParameterSource(bindData);

		KeyHolder keyHolder = new GeneratedKeyHolder();

		this.namedJdbcTemplate.update(insertReviseDetails.toString(),
				paramSource, keyHolder);
		id = keyHolder.getKey().intValue();

		return id;

	}

	public boolean checkEbayItemId(String itemId) {

		LOGGER.debug("Inside checkEbayItemId method with itemID..." + itemId);

		boolean checkItemIdStatus = false;

		String checkItemIdSql = "SELECT count(*) FROM ebay.auktion WHERE status=1 AND auktion.ebayitemid= ?";
		try {
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			int checkItemIdCount = jdbcTemplate.queryForInt(checkItemIdSql,
					new Object[] { itemId });

			LOGGER.debug("Count for Inside checkEbayItemId method with itemID..."
					+ checkItemIdCount);

			if (checkItemIdCount == 0) {
				checkItemIdStatus = true;
			}

			return checkItemIdStatus;
		} catch (EmptyResultDataAccessException e) {

			e.printStackTrace();
			checkItemIdStatus = false;
			LOGGER.debug("Control inside catch block method of CheckItemId method...");

		} catch (IncorrectResultSetColumnCountException ex) {
			ex.printStackTrace();
			LOGGER.debug("Control inside catch block method for CheckItemId method IncorrectResultSetColumnCountException...");
			checkItemIdStatus = false;
		}
		return checkItemIdStatus;
	}
	
	/**
	 * This method is used to Check retail Price exists or not  
	 * @param itemId
	 * @return
	 */
	public boolean checkRetailPrice(String itemId){
		boolean retailExissts=false;
		try{
		String checkRetail="select retailprice from ebay.auktion where ebayitemid=?";
		
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		
		
		String retailPrice = jdbcTemplate.queryForObject(checkRetail, String.class, new Object[] { itemId });
		
		if(retailPrice!=null){
			retailExissts=true;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return retailExissts;
	}
	
	/**
	 * This method is used to get the retail price
	 * @param itemid
	 * @return
	 */
	public String getRetailPrice(String itemid){
		String retailPrice=null;
		try{
		String checkRetail="select retailprice from ebay.auktion where ebayitemid=?";
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		
		 retailPrice = jdbcTemplate.queryForObject(checkRetail, String.class, new Object[] { itemid });
		
		if(retailPrice!=null){
			return retailPrice;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return retailPrice;
		
		
	}
	
	/**
	 * This method is used to get the retail price
	 * @param itemid
	 * @return
	 */
	public String getPrice(String itemid){
		String price=null;
		try{
		String checkRetail="select startpreis from ebay.auktion where ebayitemid=?";
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		
		price = jdbcTemplate.queryForObject(checkRetail, String.class, new Object[] { itemid });
		
		if(price!=null && Double.parseDouble(price)>0){
			return price;
		}else{
			String ebayRetail="select ebaysofortkauf from ebay.auktion where ebayitemid=?";
			price = jdbcTemplate.queryForObject(ebayRetail, String.class, new Object[] { itemid });
			if(price!=null && Double.parseDouble(price)>0){
				return price;
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return price;
		
		
	}
	
	public boolean checkObjectWithItemId(int objectId, String itemId) {
		boolean objectWithItemIdstatus = false;

		try {
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			String objectWithItemIdSql = "SELECT count(*) FROM ebay.auktion WHERE auktion.cusebeda_objekt_id = ? AND auktion.ebayitemid= ?";
			int objectWithItemIdCount = jdbcTemplate.queryForInt(
					objectWithItemIdSql, new Object[] { objectId, itemId });
			if (objectWithItemIdCount != 0) {
				objectWithItemIdstatus = true;
			}
		} catch (EmptyResultDataAccessException e) {

			e.printStackTrace();
			objectWithItemIdstatus = false;
			LOGGER.debug("Control inside Catch block method of EmptyResultDataAccessException for previous price...");
		}
		return objectWithItemIdstatus;
	}

	/**
	 * This method is required to check whether the item is already revidsed
	 * 
	 * @return
	 */
	public boolean checkIfAlreadyrevised(double price, String quantity,
			String time, String quantiyResriction, String itemId, double last_price) {
		LOGGER.debug("Inside checkIfAlreadyrevised....");
		LOGGER.debug("Price::" + price + "Quantity" + quantity + "Time" + time
				+ "QR" + quantiyResriction + "ItemId" + itemId + "LastPrice"
				+ last_price);

		int counter=0;
		boolean alreadyRevised = false;
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();

		StringBuffer reviseQuery = new StringBuffer(
				"SELECT count(*) FROM ebay.reviseItemPrice WHERE status=0 and itemId=? ");

		if (quantiyResriction != null) {
			reviseQuery.append(" and quantity_restriction="
					+ quantiyResriction + " and revise_Time ='" + time + "'");
			counter++;
		}
		if (price != 0) {
			reviseQuery.append(" and changePrice =" + price
					+ " and revise_Time ='" + time + "'");
			counter++;
		}
		if (quantity != null) {
			reviseQuery.append(" and revise_quantity =" + quantity
					+ " and revise_Time ='" + time + "'");
			counter++;
		}
		/*if (last_price != 0) {
			reviseQuery.append(" and lastPrice =" + last_price
					+ " and revise_Time ='" + time + "'");
			counter++;
		}*/

		LOGGER.debug("Sql Query For Insertion is:::" + reviseQuery.toString());
		
		if(counter>0){
			int countOfPrice = jdbcTemplate.queryForInt(reviseQuery.toString(),
				new Object[] { itemId });

			LOGGER.debug("Count Of Price :::" + countOfPrice);

			LOGGER.debug("Revsie Query :::" + reviseQuery.toString());
		
			if (countOfPrice == 0) {
				LOGGER.debug("Inside Count Of Price block");
				alreadyRevised = true;
			}
			LOGGER.debug("Status for already Revised." + alreadyRevised);
			return alreadyRevised;
		}
		else{
			alreadyRevised = true;
			return alreadyRevised;
		}
	}

	/**
	 * This method is used to get the details for particular item id
	 */
	public List<Map<String, Object>> getItemDetails(String ebayItemid)
			throws Exception {
		// TODO Auto-generated method stub
		// java.util.List<Map<String, Object>> itemValues= new
		// ArrayList<Map<String,Object>>();
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();

		String sql = "SELECT id,startpreis,cusebeda_objekt_id,ebaysofortkauf,AuctionMasterTypeID, DATE_ADD(auktion.startdatum, INTERVAL auktion.dauer DAY) as enddate,vorlage_id,ebayueberschrift,untertitel,anzahlgebote FROM ebay.auktion WHERE ebayItemid = ?";
		List<Map<String, Object>> itemValues = jdbcTemplate.queryForList(sql,
				new Object[] { ebayItemid });

		return itemValues;
	}

	/**
	 * This method is used to check whether revise details already Exists
	 * 
	 * @param ebayItemid
	 */

	public boolean checkReviseDetails(long ebayItemid, int quantityRestriction,
			double price, String reviseTime, int quantity) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.debug("EbayItemID" + ebayItemid + "QR::" + quantityRestriction
				+ "Price" + price + "RT" + reviseTime + "Quantity" + quantity);
		boolean reviseValuesStatus = false;
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		String sql = "Select count(*) from ebay.reviseItemPrice where  itemid=? and lastPrice=? and quantity_restriction=? and revise_quantity=? and revise_Time=?";
		@SuppressWarnings("deprecation")
		int reviseValuesCount = jdbcTemplate.queryForInt(sql, new Object[] {
				ebayItemid, price, quantityRestriction, quantity, reviseTime });
		LOGGER.debug("reviseValuesCount:::" + reviseValuesCount);

		if (reviseValuesCount == 0) {
			LOGGER.debug("Inside reviseValuesCount block");
			reviseValuesStatus = true;
		}
		LOGGER.debug("reviseValuesStatus:::" + reviseValuesStatus);
		return reviseValuesStatus;
	}

	public String getEndDate(String itemId) {
		String endDate = "";

		try {
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			String endDateQuery = "SELECT DATE_ADD(auktion.startdatum, INTERVAL auktion.dauer DAY) endDate FROM ebay.auktion WHERE  auktion.ebayitemid= ?";
			endDate = jdbcTemplate.queryForObject(endDateQuery,
					new Object[] { itemId }, String.class);

		} catch (Exception e) {

			e.printStackTrace();
			// objectWithItemIdstatus = false;
			LOGGER.debug("Control inside Catch block method of EmptyResultDataAccessException for previous price...");
			return endDate;
		}

		return endDate;
	}
	
	//getting duration of the item
		public int getDuration(String itemId) {

			int duration = 0;
			
			try {
				LOGGER.debug("Control inside getting duration method...");
				jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
				String sqlForDuration = "SELECT dauer FROM ebay.auktion WHERE STATUS=1 AND ebayitemid= ?";
				LOGGER.debug("Statemetsssssss for duration " + sqlForDuration);

				duration = jdbcTemplate.queryForInt(sqlForDuration,
						new Object[] { itemId });
				
				LOGGER.debug("-------------------------------------------------------------------");

			} catch (EmptyResultDataAccessException e) {
				LOGGER.debug("Control inside catch block method for duration...");
				e.printStackTrace();
				return duration;

			} catch (IncorrectResultSetColumnCountException ex) {
				ex.printStackTrace();
				LOGGER.debug("Control inside catch block method for duration IncorrectResultSetColumnCountException...");
				return duration;
			}
			
			return duration;
		}
	//this is getting for old qr value
		public int getQuantityRestriction(String ebayitemid){
				
				int oldQR = 0;
			
			try {
				LOGGER.debug("Control inside getting old qr method...");
				jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
				String sqlForoldQR= "select quantity_restriction from ebay.reviseItemPrice where itemId=? and status=1 and quantity_restriction is not null order by id desc limit 1";
				LOGGER.debug("Statemetsssssss for old qr " + sqlForoldQR);

				oldQR = jdbcTemplate.queryForInt(sqlForoldQR,new Object[] { ebayitemid });
				
				LOGGER.debug("-------------------------------------------------------------------");

			} catch (EmptyResultDataAccessException e) {
				LOGGER.debug("Control inside catch block method for old qr...");
				e.printStackTrace();
				return oldQR;

			} catch (IncorrectResultSetColumnCountException ex) {
				ex.printStackTrace();
				LOGGER.debug("Control inside catch block method for old qr IncorrectResultSetColumnCountException...");
				return oldQR;
			}
			
			return oldQR;
		}
		
		
		public boolean checkOutOfStockStatus(String ebayitemid){
			
				boolean outofstockStatus=false;
				int totalQuantity=0;
				int sumTQuantity=0;
			try {
				LOGGER.debug("Control inside gettin total transactions for item ...");
				jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
				String sqlForTransactionQuantityQuery= "select if( sum(quantity_purchased)>0,sum(quantity_purchased),0) as qp from ebay.transaction where ebayitemid=?";
				LOGGER.debug("Statemetsssssss for old qr " + sqlForTransactionQuantityQuery);

				 sumTQuantity = jdbcTemplate.queryForInt(sqlForTransactionQuantityQuery,new Object[] { ebayitemid });
				
				
				
				LOGGER.debug("-------------------------------------------------------------------TransactionQuantity"+sumTQuantity);

			} catch (EmptyResultDataAccessException e) {
				LOGGER.debug("Control inside catch block method for old qr...");
				e.printStackTrace();
				outofstockStatus=false;

			} catch (IncorrectResultSetColumnCountException ex) {
				ex.printStackTrace();
				LOGGER.debug("Control inside catch block method for old qr IncorrectResultSetColumnCountException...");
				outofstockStatus=false;
			}
			
			int quantity=0;
			try {
				LOGGER.debug("Control inside getting quantity total quantity for item ...");
				jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
				String sqlForQuantityQuery= "select quantity from ebay.auktion where ebayitemid=?";
				LOGGER.debug("Statemetsssssss for old qr " + sqlForQuantityQuery);

				quantity = jdbcTemplate.queryForInt(sqlForQuantityQuery,new Object[] { ebayitemid });
				
				
				
				LOGGER.debug("-------------------------------------------------------------------auktion quantity"+quantity);

			} catch (EmptyResultDataAccessException e) {
				LOGGER.debug("Control inside catch block method for old qr...");
				e.printStackTrace();
				outofstockStatus=false;

			} catch (IncorrectResultSetColumnCountException ex) {
				ex.printStackTrace();
				LOGGER.debug("Control inside catch block method for old qr IncorrectResultSetColumnCountException...");
				outofstockStatus=false;
			}
			
			totalQuantity=quantity-sumTQuantity;
			LOGGER.debug("total quantity of the item is"+ebayitemid+" quantity"+totalQuantity);
			if(totalQuantity==0){
				LOGGER.debug("Given itemid is"+ebayitemid+" OUTOFSTOCK");
				outofstockStatus=true;
			}
			
			return outofstockStatus;
			
		}
		
		
		
	/**
	 * This method is used to insert details in reviseItemDetails table
	 */
	public int saveInReviseItemDetails(int id, int revIp, String title,
			String subTitle, String htmlDescription, String offerText,
			String offerText2, String voucherValidityText,
			String AdditionalValidityText, String reviseTitle,
			String reviseSubTitle, String reviseHtmlDescription,
			String reviseOfferText, String reviseOfferText2,
			String reviseVoucherValidityText,
			String reviseAdditionalValidityText) throws Exception {

		// int id = 0;
		
		LOGGER.debug("Inside ReviseItemDetails Method for reviseTitle:::" + reviseTitle);


		LOGGER.debug("Inside ReviseItemDetails Method for auktionId" + id);

		namedJdbcTemplate = ebayJdbcTemplate.getNamedParameterJdbcTemplate();

		StringBuffer insertReviseDetails = new StringBuffer(
				"replace into ebay.reviseItemDetails(AuctionID,revIp,old_title,old_subtitle,old_htmlDescription,old_offerText,old_offerText2,old_voucherValidityText,old_additionalValidityText,"
						+ "revise_title,revise_subtitle,revise_htmlDescription,revise_offerText,revise_offerText2,revise_voucherValidityText,revise_additionalValidityText) ");
		insertReviseDetails
				.append(" values(:id,:revIp,:old_title,:old_subtitle,:old_htmlDescription,:old_offerText,:old_offerText2,:old_voucherValidityText,:old_additionalValidityText,:revise_title,"
						+ "	:revise_subtitle,:revise_htmlDescription,:revise_offerText,:revise_offerText2,:revise_voucherValidityText,:revise_additionalValidityText)");

		Map<String, Object> bindData = new HashMap<String, Object>();
		bindData.put("id", id);
		bindData.put("revIp", revIp);
		if(!title.equals(null))
		bindData.put("old_title", title);
		else
			bindData.put("old_title", null);
		bindData.put("old_subtitle", subTitle);
		bindData.put("old_htmlDescription", htmlDescription);
		// if(null!=offerText){
		bindData.put("old_offerText", offerText);
		// }
		/* if(null!=offerText2){ */
		bindData.put("old_offerText2",offerText2);
		/* } */
		// if(voucherValidityText!=null){
		bindData.put("old_voucherValidityText",voucherValidityText);
		// }
		// if(AdditionalValidityText!=null){
		bindData.put("old_additionalValidityText", AdditionalValidityText);
		// }
		// if(reviseTitle!=null){
		if(reviseTitle!=null){
			LOGGER.debug("Inside title not null block");
			bindData.put("revise_title", reviseTitle);
		}
		else{
			LOGGER.debug("Inside title not null elsee block");
			bindData.put("revise_title", null);
		}
		// }
		// if(reviseSubTitle!=null){
		if(reviseSubTitle!=null){
			bindData.put("revise_subtitle", reviseSubTitle);
		}else{
			LOGGER.debug("Inside title not null elsee block");
			bindData.put("revise_subtitle", null);
		}
		
		// }
		// if(null!=reviseHtmlDescription){
		
		if(reviseHtmlDescription!=null){
			bindData.put("revise_htmlDescription", reviseHtmlDescription);
		}else{
			LOGGER.debug("Inside title not null elsee block");
			bindData.put("revise_htmlDescription", null);
		}
		// }
		// if(null!=reviseOfferText){
		if(reviseOfferText!=null){
			bindData.put("revise_offerText", reviseOfferText);
		}else{
			LOGGER.debug("Inside title not null elsee block");
			bindData.put("revise_offerText", null);
		}
		// }
		// if(null!=reviseOfferText2){
		if(reviseOfferText2!=null){
			bindData.put("revise_offerText2", reviseOfferText2);
		}else{
			LOGGER.debug("Inside title not null elsee block");
			bindData.put("revise_offerText2", null);
		}
		// }
		// if(null!=reviseVoucherValidityText){
		
		if(reviseVoucherValidityText!=null){
			bindData.put("revise_voucherValidityText", reviseVoucherValidityText);
		}else{
			LOGGER.debug("Inside title not null elsee block");
			bindData.put("revise_voucherValidityText", null);
		}
		// }
		// if(null!=reviseAdditionalValidityText){
		
		if(reviseAdditionalValidityText!=null){
			bindData.put("revise_additionalValidityText",reviseAdditionalValidityText);
		}else{
			LOGGER.debug("Inside title not null elsee block");
			bindData.put("revise_additionalValidityText", null);
		}
		// }
		
		LOGGER.debug("==============="+insertReviseDetails);
		
		SqlParameterSource paramSource = new MapSqlParameterSource(bindData);

		// SqlParameterSource paramSource = new MapSqlParameterSource(bindData);
		KeyHolder keyHolder = new GeneratedKeyHolder();

		this.namedJdbcTemplate.update(insertReviseDetails.toString(),
				paramSource, keyHolder);
		id = keyHolder.getKey().intValue();

		return id;
	}

	/***
	 * public int getReviseItemDetailsId(){ int id=0;
	 * 
	 * String sqlID = "select id from ";
	 * 
	 * return id; }
	 ***/

	/**
	 * This method is used to update the reviseItemPrice table with id in
	 * reviseItemDetails,gallery,auktionbild table
	 */
	@SuppressWarnings("deprecation")
	public boolean updateIdReviseItemPrice(int id, long insertedId) {

		LOGGER.debug("Entered into updateIdReviseItemPrice");
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();

		String updateQuery = "update ebay.reviseItemPrice set rdId = " + id
				+ " where  id=" + insertedId;
		LOGGER.debug("Query For Updating the ReviseDetails Id ,GalleryId and AuktionBildId"
				+ updateQuery);
		int updatedCount = jdbcTemplate.update(updateQuery);
		if (updatedCount > 0) {
			LOGGER.debug("SuccessFully Updated the status");
			return true;
		} else {
			LOGGER.debug("Unable to update the status");
			return false;
		}

	}

	/**
	 * This method is used to get Offer Details
	 */
	public List<Map<String, Object>> getOfferDetails(int auktionId) {
		LOGGER.debug("Auktion ID inside getOfferDetailsss is:::" + auktionId);

		// TODO Auto-generated method stub
		List<Map<String, Object>> offerValues = null;
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();

		try {
			String sql = "Select auktions_texte_id from ebay.auktion_zusatz where auktion_id="
					+ auktionId;
			int id = jdbcTemplate.queryForInt(sql);

			LOGGER.debug("ID inside getOfferDetailsss is:::" + id);

			if (id != 0) {
				String sql1 = "Select angebotstext,gutschein_text,gueltigkeit_text,angebotstext2 from ebay.auktions_texte where id=? ";

				offerValues = jdbcTemplate.queryForList(sql1,
						new Object[] { id });
			}
			LOGGER.debug("OfferValues inside getOfferDetailsss:::"
					+ offerValues);
		} catch (Exception e) {
			LOGGER.debug("inside exception");

		}
		return offerValues;
	}

	/**
	 * This method is used to get the HtmlDescription
	 */
	public String getHtmlDescription(int auktionId, int insertedId) {
		// TODO Auto-generated method stub

		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		String htmlSql = "Select max(id)  from ebay.reviseItemDetails where AuctionID="
				+ auktionId;

		int id = jdbcTemplate.queryForInt(htmlSql);
		if (id > 0) {
			LOGGER.debug("Auction Id from reviseItemDetails" + id);
			htmlSql = "Select revise_htmlDescription from ebay.reviseItemDetails where id="
					+ id;
			String htmlDescription = jdbcTemplate.queryForObject(htmlSql,
					String.class);
			return htmlDescription;

		} else {
			
			jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();
			String afterDecoding = null;
			String sql = "SELECT request " + "FROM ebay3.apiCallRequest"
					+ " WHERE uuid like '" + auktionId + "%'";
			String apiCallRequest = jdbcTemplate.queryForObject(sql,
					String.class);
			if (apiCallRequest.contains("<Description>")) {
				int indexbeg = apiCallRequest.indexOf("<Description>") + 13;
				int indexend = apiCallRequest.indexOf("</Description>");
				String desc = apiCallRequest.substring(indexbeg, indexend);
				String output = desc.replace("´", "'");
				output = desc.replace("�", "'");
				if (null != output) {
					afterDecoding = HtmlUtils.htmlUnescape("<![CDATA[" + output
							+ "]]>");
				}
			}
			return afterDecoding;
		}

	}

	/**
	 * This method is used to insert into GalleryURL
	 */
	public int saveGalleryURL(int insertedId, String galleryURL, int auktionId,
			String objectid) {
		// TODO Auto-generated method stub
		int galleryInsertedCount = 0;
		namedJdbcTemplate = ebayJdbcTemplate.getNamedParameterJdbcTemplate();
		StringBuffer sql = new StringBuffer(
				"insert into ebay.auktion_revise_galeriebild (revIp,galeriebild_path,auktionId,cusebeda_objekt_id)");
		sql.append(" values(:revIp,:galeriebild_path,:auktionId,:cusebeda_objekt_id)");
		Map<String, Object> bindData = new HashMap<String, Object>();
		bindData.put("revIp", insertedId);
		bindData.put("galeriebild_path", galleryURL);
		bindData.put("auktionId", auktionId);
		bindData.put("cusebeda_objekt_id", objectid);
		SqlParameterSource paramSource = new MapSqlParameterSource(bindData);

		// SqlParameterSource paramSource = new MapSqlParameterSource(bindData);
		KeyHolder keyHolder = new GeneratedKeyHolder();

		this.namedJdbcTemplate.update(sql.toString(), paramSource, keyHolder);
		galleryInsertedCount = keyHolder.getKey().intValue();

		return galleryInsertedCount;
	}

	/**
	 * This method is used to insert Auktion URLs
	 */
	public int saveAuktionURL(int insertedId, List<String> auktionURL,
			int auktionId, String objectId) {
		// TODO Auto-generated method stub

		LOGGER.debug("Entered into saveAuktionURL");
		int auktionInsertedCount = 0;
		//jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		namedJdbcTemplate = ebayJdbcTemplate
				.getNamedParameterJdbcTemplate();
		int picsCount = 0;
		for (int i = 0; i < auktionURL.size(); i++) {
			StringBuffer saveItemPicsQuery = new StringBuffer(
					"insert into ebay.auktion_revise_auktionbild(auktionId,sequenceId,auktionbildpath,cusebeda_objekt_id,revIp)");

			saveItemPicsQuery
					.append(" values(:auktionId,:sequenceid,:auktionbildpath,:objectid,:revIp)");

			Map<String, Object> auktionPics = new HashMap<String, Object>();
			auktionPics.put("auktionId", auktionId);
			auktionPics.put("sequenceid", i);
			auktionPics.put("auktionbildpath", auktionURL.get(i));
			auktionPics.put("objectid", objectId);
			auktionPics.put("revIp", insertedId);

			SqlParameterSource auktionPicsParam = new MapSqlParameterSource(
					auktionPics);

			auktionInsertedCount = this.namedJdbcTemplate.update(
					saveItemPicsQuery.toString(), auktionPicsParam);
			picsCount++;

		}
		return auktionInsertedCount;
	}


}