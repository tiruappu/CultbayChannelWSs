package com.cultuzz.channel.template.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cultuzz.channel.XMLpojo.OfferDetailsRS;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;



public class OfferMapper implements RowMapper<OfferDetailsRS> {

	private static final Logger LOGGER = LoggerFactory.getLogger("OfferMapper.class");
	
	public OfferDetailsRS mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		//System.out.println("this is row number"+rowNum);
		OfferDetailsRS offerDetailsRS = new OfferDetailsRS();
		
		offerDetailsRS.setObjectId(rs.getInt("cusebeda_objekt_id"));
		offerDetailsRS.setId(rs.getInt("id"));
		offerDetailsRS.setTemplateId(rs.getInt("vorlage_id"));
		offerDetailsRS.setItemId(String.valueOf(rs.getLong("ebayitemid")));
	    offerDetailsRS.setTitle(rs.getString("ebayueberschrift"));
	    if(rs.getString("ebayueberschrift")!=null){
	    offerDetailsRS.setSubTitle(rs.getString("untertitel"));
	    }
	    
	    rs.getDate("startdatum");
	    
		offerDetailsRS.setStartTime(rs.getString("startdatum"));
		
		if(rs.getInt("dauer") != 9999){
	
			offerDetailsRS.setEndTime(rs.getString("enddate"));
			offerDetailsRS.setDuration(rs.getInt("dauer"));
		}
        offerDetailsRS.setQuantity(rs.getInt("quantity"));
		offerDetailsRS.setSiteId(rs.getInt("ebaysiteid"));
		
		offerDetailsRS.setStatus(rs.getInt("status"));
		
		LOGGER.debug("AuctionMasterTypeId is:{}",rs.getInt("AuctionMasterTypeID"));
		if(rs.getDouble("startpreis")>0 && (rs.getDouble("ebaysofortkauf")==0 || rs.getString("ebaysofortkauf")==null) && rs.getInt("AuctionMasterTypeID")==1){
			
			LOGGER.debug("Inside if");
			LOGGER.debug("start price for offermapper is:{}",rs.getString("startpreis"));
			//offerDetailsRS.setPrice(Double.parseDouble(rs.getString("startpreis")));
			//String.format("%.2f", Double.parseDouble(rs.getString("startpreis")))
			offerDetailsRS.setPrice(String.format("%.2f", Double.parseDouble(rs.getString("startpreis"))));
			LOGGER.debug("offerDetails start price in offer mapper is:{}",offerDetailsRS.getPrice());
            offerDetailsRS.setListingType("Auction");	
            if(rs.getString("currentbid")!=null && !rs.getString("currentbid").isEmpty()){
            //offerDetailsRS.setHighestBid(Double.parseDouble(rs.getString("currentbid")));
            offerDetailsRS.setHighestBid(String.format("%.2f", Double.parseDouble(rs.getString("currentbid"))));
            
            }
            offerDetailsRS.setNoOfBids(rs.getInt("anzahlgebote"));
            if(rs.getString("hoechstbietender")!=null && !rs.getString("hoechstbietender").isEmpty()){
            offerDetailsRS.setBidderName(rs.getString("hoechstbietender"));
            }
		}else if(rs.getFloat("startpreis")==0 && rs.getFloat("ebaysofortkauf")>0 && rs.getInt("AuctionMasterTypeID")==1){
		
			LOGGER.debug("inside second if");
			//offerDetailsRS.setPrice(Double.parseDouble(rs.getString("ebaysofortkauf")));
			offerDetailsRS.setPrice(String.format("%.2f", Double.parseDouble(rs.getString("ebaysofortkauf"))));
			offerDetailsRS.setListingType("Fixed Price Offer");
		
		}else if(rs.getFloat("startpreis")>0 && rs.getFloat("ebaysofortkauf")==0 || rs.getString("ebaysofortkauf")==null && rs.getInt("AuctionMasterTypeID")==4){
		
			LOGGER.debug("Inside third if");
			//offerDetailsRS.setPrice(Double.parseDouble(rs.getString("startpreis")));
			offerDetailsRS.setPrice(String.format("%.2f", Double.parseDouble(rs.getString("ebaysofortkauf"))));
			offerDetailsRS.setListingType("Store Fixed Price Offer");
		}
		 
		  if(rs.getString("retailprice")!=null && !rs.getString("retailprice").isEmpty()){
			  
			  offerDetailsRS.setRetailPrice(rs.getString("retailprice"));
		  }
		  
		  if(rs.getString("watchcount")!=null && !rs.getString("watchcount").isEmpty()){
			  LOGGER.debug("Inside watchcount");
			  offerDetailsRS.setWatchCount(rs.getString("watchcount"));
		  }
		   	  
		return offerDetailsRS;
		
		
		
	}

}
