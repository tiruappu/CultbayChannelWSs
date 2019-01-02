package com.cultuzz.channel.template.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.XMLpojo.OfferDetailsRS;
import com.cultuzz.channel.util.CommonUtil;

@Component
public class OfferMapperNew {

	@Autowired
	CommonUtil commonutils;
	
private static final Logger LOGGER = LoggerFactory.getLogger("OfferMapperNew.class");
	
	public OfferDetailsRS dataSet(ResultSet rs, int rowNum) throws SQLException {
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
	    //System.out.println("time zoneeee isss"+commonutils.getTimezoneMap().get(77));
	    //System.out.println("ebay site idssss"+rs.getInt("ebaysiteid"));
	    //String timezone=commonutils.getTimezoneMap().get(rs.getInt("ebaysiteid"));
	    SimpleDateFormat timeformatobj = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
	    TimeZone timeZoneObj=null;
	    int siteid=rs.getInt("ebaysiteid");
	    if(siteid==186 || siteid==146 || siteid==193 ||siteid==101 || siteid==77 || siteid==16 || siteid==23 || siteid==71 || siteid==123){
	    	//timezone.equals("MESZ")
	    	 timeZoneObj = TimeZone.getTimeZone("Europe/Berlin");
	    }else if(siteid==0 || siteid==2){
	    	//PDT
	    	 timeZoneObj = TimeZone.getTimeZone("America/Los_Angeles");
	    }else if(siteid==3){
	    	//BST
	    	 timeZoneObj = TimeZone.getTimeZone("Europe/London");
	    }else if(siteid==15){
	    	//AEDST
	    	 timeZoneObj = TimeZone.getTimeZone("Australia/Brisbane");
	    }else if(siteid==201 || siteid==196 || siteid==207 ||siteid==216 || siteid==211 || siteid==223){
	    	//CCT
	    	 timeZoneObj = TimeZone.getTimeZone("Asia/Manila");
	    }else if(siteid==203){
	    	//IST
	    	 timeZoneObj = TimeZone.getTimeZone("Asia/Kolkata");
	    }
	    timeformatobj.setTimeZone(timeZoneObj);
	    
	    
	   /* TimeZone timeZ = TimeZone.getTimeZone(timezone);
	    DateFormat dateFormat=new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
	    
 		dateFormat.setTimeZone(timeZ);*/
 		
	    if(rs.getString("startdatum")!=null){
	    //Date sdate=rs.getDate("startdatum");
	    	try{
	    		
	    		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("startdatum"));
	    	    Calendar calendar = new GregorianCalendar();
	            calendar.setTime(date);
	            calendar.setTimeZone(timeZoneObj);
	    		
	   /* Date sdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("startdatum"));
 		String startDate=dateFormat.format(sdate);*/
	    offerDetailsRS.setStartTime(timeformatobj.format(date));
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }
	    
	    offerDetailsRS.setDuration(rs.getInt("dauer"));
	    //System.out.println("Duration isss"+rs.getInt("dauer"));
		if(rs.getInt("dauer")==9999 && rs.getInt("status")<2){
			offerDetailsRS.setEndTime("GTC");
		}else if(rs.getInt("dauer") != 9999 || rs.getInt("status")>=2){
			//Date edate=rs.getDate("enddate");
			try{
				
				Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("enddate"));
	    	    Calendar calendar = new GregorianCalendar();
	            calendar.setTime(date);
	            calendar.setTimeZone(timeZoneObj);
	            offerDetailsRS.setEndTime(timeformatobj.format(date));
			/*Date edate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("enddate"));
			String endDate=dateFormat.format(edate);	 		
			offerDetailsRS.setEndTime(endDate);*/
			//offerDetailsRS.setDuration(rs.getInt("dauer"));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
        offerDetailsRS.setQuantity(rs.getInt("quantity"));
		offerDetailsRS.setSiteId(rs.getInt("ebaysiteid"));
		
		offerDetailsRS.setStatus(rs.getInt("status"));
		if(rs.getInt("arrangement_id")!=0)
		offerDetailsRS.setProductId(rs.getInt("arrangement_id"));
		
		LOGGER.debug("AuctionMasterTypeId is:{}",rs.getInt("AuctionMasterTypeID"));
		if(rs.getDouble("startpreis")>0 && (rs.getDouble("ebaysofortkauf")==0 || rs.getString("ebaysofortkauf")==null) && rs.getInt("AuctionMasterTypeID")==1){
			
			LOGGER.debug("Inside if");
			LOGGER.debug("start price for offermapper is:{}",rs.getString("startpreis"));
			//String.format("%.2f", Double.parseDouble(rs.getString("startpreis")))
			//offerDetailsRS.setPrice(Double.parseDouble(rs.getString("startpreis")));
			offerDetailsRS.setPrice(String.format("%.2f", Double.parseDouble(rs.getString("startpreis"))));
			LOGGER.debug("offerDetails start price in offer mapper is:{}",offerDetailsRS.getPrice());
            offerDetailsRS.setListingType("Auction");	
            if(rs.getString("currentbid")!=null && !rs.getString("currentbid").isEmpty()){
            	//String.format("%.2f", Double.parseDouble(rs.getString("startpreis")))
            //offerDetailsRS.setHighestBid(Double.parseDouble(rs.getString("currentbid")));
            	offerDetailsRS.setHighestBid(String.format("%.2f", Double.parseDouble(rs.getString("currentbid"))));
            }
            offerDetailsRS.setNoOfBids(rs.getInt("anzahlgebote"));
            if(rs.getString("hoechstbietender")!=null && !rs.getString("hoechstbietender").isEmpty()){
            offerDetailsRS.setBidderName(rs.getString("hoechstbietender"));
            }
		}else if(rs.getFloat("startpreis")==0 && rs.getFloat("ebaysofortkauf")>0 && rs.getInt("AuctionMasterTypeID")==1){
		
			LOGGER.debug("inside second if");
			//String.format("%.2f", Double.parseDouble(rs.getString("startpreis")))
			//offerDetailsRS.setPrice(Double.parseDouble(rs.getString("ebaysofortkauf")));
			offerDetailsRS.setPrice(String.format("%.2f", Double.parseDouble(rs.getString("ebaysofortkauf"))));
			offerDetailsRS.setListingType("Fixed Price Offer");
		
		}else if(rs.getFloat("startpreis")>0 && (rs.getFloat("ebaysofortkauf")==0 || rs.getString("ebaysofortkauf")==null) && rs.getInt("AuctionMasterTypeID")==4){
		
			LOGGER.debug("Inside third if");
			//String.format("%.2f", Double.parseDouble(rs.getString("startpreis")));
			//offerDetailsRS.setPrice(Double.parseDouble(rs.getString("startpreis")));
			offerDetailsRS.setPrice(String.format("%.2f", Double.parseDouble(rs.getString("startpreis"))));
			offerDetailsRS.setListingType("Store Fixed Price Offer");
		}else if(rs.getFloat("startpreis")>0 && rs.getFloat("ebaysofortkauf")>0 && rs.getInt("AuctionMasterTypeID")==1){
			
			offerDetailsRS.setPrice(String.format("%.2f", Double.parseDouble(rs.getString("ebaysofortkauf"))));
			offerDetailsRS.setListingType("Both Offer");
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
