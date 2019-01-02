package com.cultuzz.channel.DAO.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.stereotype.Repository;

import com.cultuzz.channel.XMLpojo.VoucherRedemptionRQ;
import com.cultuzz.channel.jdbcTemplate.JDBCTemplate;
@Repository
public class VoucherReedemptionDAOImpl {

	@Autowired
	@Qualifier("ebayTemplate")
	private JDBCTemplate ebayJdbcTemplate;
	
	@Autowired
	@Qualifier("cusebedaTemplate")
	private JDBCTemplate cusebedaJdbcTemplate;
	
	private JdbcTemplate jdbcTemplate;
	
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	private static final Logger logger = LoggerFactory
			.getLogger(TemplateModuleDAOImpl.class);
	
	/*java.util.Date date = new java.util.Date();
	Timestamp timestamp = new Timestamp(date.getTime());
	*/
	public boolean updateVoucherReedemption(VoucherRedemptionRQ voucherRedeemptionRQ){
		
		boolean voucherReedemStatus=false;
		
		try{
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			StringBuffer updateRedeemSql= new StringBuffer("UPDATE checkout.gutscheine SET");
			
			updateRedeemSql.append(" gutscheine.besucher='"+ voucherRedeemptionRQ.getTravellerName().trim() +"' , gutscheine.anreise = '"+voucherRedeemptionRQ.getPeriod().getFrom()+"', gutscheine.abreise= '"+voucherRedeemptionRQ.getPeriod().getTo()+"' ,gutscheine.binaerstatus =7 , gutscheine.last_change =now() WHERE gutscheine.ebayitemid=?  and  !(gutscheine.binaerstatus & 4)");
			
			if(voucherRedeemptionRQ.isSetVoucherId()){
				updateRedeemSql.append(" and gutscheine.czInternalTransID ="+voucherRedeemptionRQ.getVoucherId());
			}
			
			int voucherReedeemUpdate=jdbcTemplate.update(updateRedeemSql.toString(), new Object[] { voucherRedeemptionRQ.getItemId() });
			
			if(voucherReedeemUpdate>0){
				voucherReedemStatus=true;
			}
			
			}catch(Exception e){
				e.printStackTrace();
			}
			
		return voucherReedemStatus;
	}
	
	public boolean checkRedeemItemid(long itemid,String czID){
		boolean redeemStatus=false;
		
		try {
			logger.debug("inside DAO checking itemid"+itemid);
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String redeemChecksql="SELECT count(ebayitemid)  FROM checkout.gutscheine where ebayitemid=? and czInternalTransID="+czID+" and binaerstatus=3";
			int itemstatusCount=jdbcTemplate.queryForInt(redeemChecksql, new Object[] { itemid });
			logger.debug("item ids count===="+itemstatusCount);
			if(itemstatusCount>0)
				redeemStatus=true;
				
		} catch (Exception e) {
			redeemStatus=false;
			// TODO: handle exception
			logger.debug("This item not possible to redeem");

		}
		
		
		return redeemStatus;
	}
	
	public boolean checkRollbackItemid(long itemid,String czID){
		boolean rollbackStatus=false;
		
		try {
			logger.debug("inside DAO checking itemid"+itemid);
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String rollbackChecksql="SELECT count(ebayitemid)  FROM checkout.gutscheine where ebayitemid=? and czInternalTransID="+czID+" and binaerstatus=7";
			int itemstatusCount=jdbcTemplate.queryForInt(rollbackChecksql, new Object[] { itemid });
			logger.debug("item ids count===="+itemstatusCount);
			if(itemstatusCount>0)
				rollbackStatus=true;
				
		} catch (Exception e) {
			rollbackStatus=false;
			// TODO: handle exception
			logger.debug("This item not possible to redeem");

		}
		
		
		return rollbackStatus;
	}
	
	
	public boolean updateRollback(VoucherRedemptionRQ voucherRedeemptionRQ){
		boolean rollbackStatus=false;	
		try{
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			StringBuffer updateRollbackSql= new StringBuffer("UPDATE checkout.gutscheine SET");
			
			updateRollbackSql.append(" gutscheine.binaerstatus = 3, gutscheine.last_change  = NOW() WHERE gutscheine.ebayitemid = ? ");
			
			if(voucherRedeemptionRQ.isSetVoucherId()){
				updateRollbackSql.append(" and gutscheine.czInternalTransID ="+voucherRedeemptionRQ.getVoucherId());
			}
			
			int voucherRollbackUpdate=jdbcTemplate.update(updateRollbackSql.toString(), new Object[] { voucherRedeemptionRQ.getItemId() });
			
			if(voucherRollbackUpdate>0){
				rollbackStatus=true;
			}
			
			}catch(Exception e){
				e.printStackTrace();
			}
		return rollbackStatus;
	}
	
	
	public boolean saveArGutscheine(long itemid,String czid){
		boolean saveArGutscheineStatus=false;
		long id=0;
		List<Map<String,Object>> getGutchineData=this.getGutscheine(itemid,czid);
		Map<String, Object> bind = new HashMap<String, Object>();
		if(getGutchineData!=null){
		if(getGutchineData.size()>0){
			
			for(Map<String,Object> rows : getGutchineData){
	
		bind.put("gueltig_bis",rows.get("gueltig_bis"));
		bind.put("binaerstatus",rows.get("binaerstatus"));
		bind.put("objekt_id",rows.get("objekt_id"));
		bind.put("versendet",rows.get("versendet"));
		bind.put("ebayitemid", rows.get("ebayitemid"));
		bind.put("ebayorderid",rows.get("ebayorderid"));
		bind.put("czInternalTransID", rows.get("czInternalTransID"));
		bind.put("benutzer_id",rows.get("benutzer_id"));
		bind.put("last_change", rows.get("last_change"));
		bind.put("anzeige_status", rows.get("anzeige_status"));
		bind.put("besucher", rows.get("besucher"));
		bind.put("anreise", rows.get("anreise"));
		bind.put("abreise", rows.get("abreise"));
		bind.put("kaeufer_id", rows.get("kaeufer_id"));
		
			}
		}else{
			saveArGutscheineStatus=true;
		}
		}else{
			saveArGutscheineStatus=true;
		}
		try {
			
			namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
			StringBuffer saveArGutscheineSql= new StringBuffer("INSERT INTO checkout.ar_gutscheine (gueltig_bis, binaerstatus, objekt_id, versendet, ebayitemid, ebayorderid, czInternalTransID, benutzer_id, last_change, anzeige_status, besucher, anreise, abreise, kaeufer_id) ");
			saveArGutscheineSql.append(" values(:gueltig_bis,:binaerstatus,:objekt_id,:versendet,:ebayitemid,:ebayorderid,:czInternalTransID,:benutzer_id,:last_change,:anzeige_status,:besucher,:anreise,:abreise,:kaeufer_id)");	
	
			SqlParameterSource paramSource = new MapSqlParameterSource(bind);
			KeyHolder keyHolder = new GeneratedKeyHolder();

				this.namedJdbcTemplate.update(saveArGutscheineSql.toString(), paramSource,keyHolder);
				id = keyHolder.getKey().longValue();
			
			
			if(id>0){
				saveArGutscheineStatus=true;
			}
			
		} catch (Exception e) {
			saveArGutscheineStatus=false;
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return saveArGutscheineStatus;
	}
	
	public List<Map<String,Object>> getGutscheine(long itemid,String czid){
		List<Map<String,Object>> gutchineRows = null;
		
		try{
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			logger.debug("Inside Try block of booking data method...");

			String fetchgutchineDataQuery = "SELECT gueltig_bis, binaerstatus, objekt_id, versendet, ebayitemid, ebayorderid, czInternalTransID, benutzer_id, last_change, anzeige_status, besucher, anreise, abreise, kaeufer_id FROM checkout.gutscheine WHERE gutscheine.ebayitemid =? AND gutscheine.czInternalTransID ="+czid;
			logger.debug("FETCH QUERY:::"+fetchgutchineDataQuery);
//			rows = jdbcTemplate.queryForList(fetchCredQuery);
			gutchineRows = jdbcTemplate.queryForList(fetchgutchineDataQuery,itemid);
		 
		}catch(Exception e){
			logger.debug("Inside Ctach block of fetchCredential method...");
			
			e.printStackTrace();
		}
		//System.out.println("Selected data is:::"+rows);
		return gutchineRows;
		
	}
	
	
	public List<Map<String,Object>> getBookingData(String ebayitemid,String czid){
		logger.debug("Inside getting booking data ");
		List<Map<String,Object>> rows = null;
		
		try{
			jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();
			logger.debug("Inside Try block of booking data method...");

			String fetchbookingDataQuery = "select buchung_id,arrangement_id,status from mycultuzz.buchung_x_auktion where ebayitemid=? and czInternalTransID="+czid;
			logger.debug("FETCH QUERY:::"+fetchbookingDataQuery);
//			rows = jdbcTemplate.queryForList(fetchCredQuery);
			rows = jdbcTemplate.queryForList(fetchbookingDataQuery,ebayitemid);
		 
		}catch(Exception e){
			logger.debug("Inside Ctach block of fetchCredential method...");
			
			e.printStackTrace();
		}
		//System.out.println("Selected data is:::"+rows);
		return rows;
	}
	
	public boolean checkVoucherValidity(String ebayitemid,String czid,String todate){
		boolean voucherValidityStatus=false;
		try{
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			logger.debug("Inside Try block of voucher validity method...");

			String checkValidityDataQuery = "select id from checkout.gutscheine where ebayitemid=? and czInternalTransID="+czid+" and gueltig_bis>='"+todate+"'";
			logger.debug("FETCH QUERY:::"+checkValidityDataQuery);
//			rows = jdbcTemplate.queryForList(fetchCredQuery);
			int id = jdbcTemplate.queryForInt(checkValidityDataQuery,ebayitemid);
			if(id>0)
				voucherValidityStatus=true;
			
		}catch(Exception e){
			voucherValidityStatus=false;
			logger.debug("Inside Ctach block of check voucher validity method...");
			
			e.printStackTrace();
		}
		return voucherValidityStatus;
	}
	
}