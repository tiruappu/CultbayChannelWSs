package com.cultuzz.channel.template.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cultuzz.channel.XMLpojo.DescriptionPicturesType;
import com.cultuzz.channel.XMLpojo.DescriptionType;
import com.cultuzz.channel.XMLpojo.OfferDetailsRS;
import com.cultuzz.channel.XMLpojo.TemplateDetailsRS;
import com.cultuzz.channel.template.pojo.OfferBO;
import com.cultuzz.channel.DAO.TemplateDetailsDAO;
import com.cultuzz.channel.DAO.TemplateModuleDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateMapper implements RowMapper<TemplateDetailsRS> {
	
	

	// Map the Object filed with the db fields
	public TemplateDetailsRS mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		// TODO Auto-generated method stub
        
		TemplateDetailsRS templateDetailsRS = new TemplateDetailsRS();
	//	CurrencyCodeType codeType=new CurrencyCodeType("");
		DescriptionType description = new DescriptionType();
		// "select (ebaysiteid,bezeichnung,cusebeda_objekt_id,template_id,erstellt,geaendert,status,ebayoptionen,ebaybetreffzeile,untertitel,ebaykategorieid1,ebaykategorieid2,ueberschrift,ueberschrift2,text,text2,startpreis,ebaysofortkauf,arrangement_id,buchungsschluessel_gueltigkeit,auctionMasterTypeID,third_party_uniqueCodes) from ebay.vorlage where id =?";
		// templateDetailsRS.setObjectId(rs.getInt("cusebeda_objekt_id"));
		templateDetailsRS.setDesignTemplate(rs.getInt("template_id"));
		templateDetailsRS.setSiteId(rs.getInt("ebaysiteid"));		
		templateDetailsRS.setTemplateName(rs.getString("bezeichnung"));
		templateDetailsRS.setProductId(rs.getInt("arrangement_id"));
		templateDetailsRS.setOfferTitle(rs.getString("ebaybetreffzeile"));
		//templateDetailsRS.setPrice(rs.getDouble("startpreis"));
		templateDetailsRS.setProductId(rs.getInt("arrangement_id"));
		templateDetailsRS.setOfferTitle(rs.getString("ebaybetreffzeile"));
		templateDetailsRS.setOfferSubTitle(rs.getString("untertitel"));
		if(rs.getString("retailprice")!=null){
			try{
			String price=String.format("%.2f", Double.parseDouble(rs.getString("retailprice")));
		templateDetailsRS.setRetailPrice(price);
			}catch(Exception e){
				templateDetailsRS.setRetailPrice(rs.getString("retailprice"));
			}
		}
		//description.setVoucherText(rs.getString("gueltigkeit_text"));
		//description.setVoucherValidityText(rs.getString("gueltigkeit"));
		// templateDetailsRS.setS
		//templateDetailsRS.setDescription(description);
		if (rs.getInt("auctionMasterTypeID") ==1 && rs.getInt("ebaysofortkauf")>0){
			templateDetailsRS.setListingType("Fixed Price Offer");
			String price=String.format("%.2f", Double.parseDouble(rs.getString("ebaysofortkauf")));
		    templateDetailsRS.setPrice(price);
		}
		else if(rs.getInt("auctionMasterTypeID") == 1 && rs.getInt("startpreis")>0){
			String price=String.format("%.2f", Double.parseDouble(rs.getString("startpreis")));
			templateDetailsRS.setPrice(price);
			templateDetailsRS.setListingType("Auction Offer");
		}
		else if(rs.getInt("auctionMasterTypeID") ==4 && rs.getInt("startpreis")>0)
		{
			templateDetailsRS.setListingType("Fixed Price Offer");
			String price=String.format("%.2f", Double.parseDouble(rs.getString("startpreis")));
		    templateDetailsRS.setPrice(price);
		}
       
		

		/*
		 * 
		 * bind.put("ebayoptionen",8); bind.put("ebaybetreffzeile",
		 * templateCreationRQ.getOfferTitle()); bind.put("untertitel",
		 * templateCreationRQ.getOfferSubTitle());
		 * bind.put("ebaykategorieid1",templateCreationRQ
		 * .getCategories().getPrimaryCategoryDetails().getPrimaryCategoryId());
		 * bind.put("ebaykategorieid2",templateCreationRQ.getCategories().
		 * getSecondaryCategoryDetails().getSecondaryCategoryId());
		 * bind.put("ueberschrift",
		 * templateCreationRQ.getDescription().getTitle());
		 * bind.put("ueberschrift2"
		 * ,templateCreationRQ.getDescription().getSubTitle()); bind.put("text",
		 * templateCreationRQ.getDescription().getOfferText());
		 * bind.put("text2",
		 * templateCreationRQ.getDescription().getOfferAdditionalText());
		 * bind.put("startpreis", templateCreationRQ.getPrice());
		 * bind.put("ebaysofortkauf",""); bind.put("arrangement_id",
		 * templateCreationRQ.getProductId());
		 * bind.put("buchungsschluessel_gueltigkeit", 14);
		 */

		// templateDetailsRS.getCategories().getPrimaryCategoryDetails().setPrimaryCategoryId(rs.getInt("ebaykategorieid1"));
		// templateDetailsRS.getCategories().getPrimaryCategoryDetails().setPrimaryCategoryId(rs.getInt("ebaykategorieid2"));
		/*
		 * offerDetailsRS.setItemId(rs.getLong("ebayitemid"));
		 * offerDetailsRS.setTitle(rs.getString("ebayueberschrift"));
		 * offerDetailsRS.setSubTitle(rs.getString("untertitel"));
		 * offerDetailsRS.setStartTime(rs.getString("startdatum"));
		 * offerDetailsRS.setDuration(rs.getInt("dauer"));
		 * offerDetailsRS.setBidderName(rs.getString("hoechstbietender"));
		 * offerDetailsRS.setQuantity(rs.getInt("quantity"));
		 * offerDetailsRS.setSiteId(rs.getInt("ebaysiteid"));
		 * offerDetailsRS.setEndTime(rs.getString("enddate"));
		 * 
		 * if(rs.getFloat("startpreis")>0 && rs.getFloat("ebaysofortkauf")==0 &&
		 * rs.getString("ebaysofortkauf")==null &&
		 * rs.getInt("AuctionMasterTypeID")==1){
		 * 
		 * offerDetailsRS.setPrice(rs.getDouble("startpreis"));
		 * offerDetailsRS.setListingType("Auction");
		 * offerDetailsRS.setHighestBid(rs.getDouble("endpreis"));
		 * 
		 * }else if(rs.getFloat("startpreis")==0 &&
		 * rs.getFloat("ebaysofortkauf")>0 &&
		 * rs.getInt("AuctionMasterTypeID")==1){
		 * 
		 * offerDetailsRS.setPrice(rs.getFloat("ebaysofortkauf"));
		 * offerDetailsRS.setListingType("ShopOffer");
		 * 
		 * }else if(rs.getFloat("startpreis")>0 &&
		 * rs.getFloat("ebaysofortkauf")==0 &&
		 * rs.getString("ebaysofortkauf")==null &&
		 * rs.getInt("AuctionMasterTypeID")==4){
		 * 
		 * offerDetailsRS.setPrice(rs.getFloat("startpreis"));
		 * offerDetailsRS.setListingType("ShopOffer"); }
		 */

		return templateDetailsRS;

	}

}
