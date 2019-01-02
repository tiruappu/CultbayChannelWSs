package com.cultuzz.channel.template.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.cultuzz.channel.XMLpojo.VoucherDetailsRS;

public class VoucherMapper implements RowMapper<VoucherDetailsRS>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VorlageMapper.class);

	public VoucherDetailsRS mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		// TODO Auto-generated method stub
		
		LOGGER.debug("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		
		VoucherDetailsRS voucherDetailsRS = null;
		try{
			
		
		voucherDetailsRS = new VoucherDetailsRS();
		
		
		voucherDetailsRS.setItemId(String.valueOf(rs.getLong("ebayitemid")));
		voucherDetailsRS.setStatus(rs.getInt("binaerstatus"));
		

		try{
		if(rs.getTimestamp("versendet")!=null){
				voucherDetailsRS.setValidFrom(String.valueOf(rs.getTimestamp("versendet")).replace(".0", ""));
		}
		}catch(Exception e){
		
			e.printStackTrace();
		}
		try{
		if(rs.getTimestamp("gueltig_bis")!=null){
			Date date=rs.getDate("gueltig_bis");
	 		String validityDate=new SimpleDateFormat("dd MMM yyyy").format(date);
		LOGGER.debug("valid till :{}",String.valueOf(rs.getTimestamp("gueltig_bis")));
		voucherDetailsRS.setValidTill(validityDate);
		}}catch(Exception e){
			e.printStackTrace();
		}
		try{
		if(rs.getTimestamp("anreise")!=null){
			Date date=rs.getDate("anreise");
	 		String checkinDate=new SimpleDateFormat("dd MMM yyyy").format(date);
			LOGGER.debug("check in :{}",String.valueOf(rs.getTimestamp("anreise")));
		voucherDetailsRS.setCheckIn(checkinDate);
		}}catch(Exception e){
			e.printStackTrace();
		}
		try{
		if(rs.getTimestamp("abreise")!=null){
			LOGGER.debug("checkout :{}",String.valueOf(rs.getTimestamp("abreise")));
			Date date=rs.getDate("abreise");
	 		String checkoutDate=new SimpleDateFormat("dd MMM yyyy").format(date);
		voucherDetailsRS.setCheckOut(checkoutDate);
		}}catch(Exception e){
			e.printStackTrace();
		}
		
	    voucherDetailsRS.setOrderId(String.valueOf(rs.getLong("ebayorderid")));
	    if(rs.getString("besucher")!=null){
	    	LOGGER.debug("traveler :{}",rs.getString("besucher"));
	    voucherDetailsRS.setTravellerName(rs.getString("besucher"));
	    }
	    if(rs.getString("vorname")!=null || rs.getString("nachname")!=null){
	    	LOGGER.debug("buyer:{}",rs.getString("vorname")+""+rs.getString("nachname"));
	    voucherDetailsRS.setBuyerName(rs.getString("vorname")+""+rs.getString("nachname"));
	    }
	    try{
	    if(rs.getInt("binaerstatus")==7){
	    	LOGGER.debug("status is :{}",String.valueOf(rs.getTimestamp("last_change")));
	    	voucherDetailsRS.setRedeemedDate(String.valueOf(rs.getTimestamp("last_change")).replace(".0", ""));
	    }else if(rs.getInt("binaerstatus")==9){
	    	LOGGER.debug("cancelledDate :{}",String.valueOf(rs.getTimestamp("last_change")));
	    	voucherDetailsRS.setCancelledDate(String.valueOf(rs.getTimestamp("last_change")).replace(".0", ""));
	    }}catch(Exception e){
	    	e.printStackTrace();
	    }
	    try{
	 	if(rs.getDate("bezahlt_datum")!=null){
	 		LOGGER.debug("paid date:{}",String.valueOf(rs.getTimestamp("bezahlt_datum")));
	 		LOGGER.debug("paid dateeeeeee:{}",String.valueOf(rs.getDate("bezahlt_datum")));
	 		
	 		Date date=rs.getDate("bezahlt_datum");
	 		String paidDate=new SimpleDateFormat("dd MMM yyyy").format(date);
	 		LOGGER.debug("paid !!!!!!11dateeeeeee:{}",paidDate);
	 		voucherDetailsRS.setPaidDate(paidDate);
	 	}}catch(Exception e){
	 		e.printStackTrace();
	 	}
	    if(rs.getString("ebay_auktion_hoechstbietender")!=null){
	    	LOGGER.debug("buyerId is:{}",rs.getString("ebay_auktion_hoechstbietender"));
	    	voucherDetailsRS.setBuyerId(rs.getString("ebay_auktion_hoechstbietender"));
	    }
	}catch(Exception e){
			LOGGER.debug("outer catch");
			e.printStackTrace();
		}
		return voucherDetailsRS;
	}

}
