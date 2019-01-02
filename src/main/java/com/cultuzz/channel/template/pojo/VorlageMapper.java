package com.cultuzz.channel.template.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;


public class VorlageMapper implements RowMapper<Vorlage> {

	private static final Logger LOGGER = LoggerFactory.getLogger("VorlageMapper.class");
	
	Vorlage vorlage = new Vorlage();
	
	
	public Vorlage mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		
		vorlage.setEbayoptionen(rs.getInt("ebayoptionen"));
		vorlage.setEbaykategorieid1(rs.getInt("ebaykategorieid1"));
		vorlage.setEbaykategorieid2(rs.getString("ebaykategorieid2"));
		if(rs.getString("ebaybetreffzeile")==null){
			vorlage.setEbaybetreffzeile("");
		}else{
		vorlage.setEbaybetreffzeile(rs.getString("ebaybetreffzeile"));
		}
		if(rs.getString("ueberschrift")==null){
			
			vorlage.setUeberschrift("");
		}else{
		vorlage.setUeberschrift(rs.getString("ueberschrift"));
		}
		if(rs.getString("untertitel")==null){
			
			vorlage.setUntertitel("");
		}else{
		vorlage.setUntertitel(rs.getString("untertitel"));
		}
		if(rs.getString("text")==null){
		vorlage.setText("");
		}else{
			vorlage.setText(rs.getString("text"));
		}
		vorlage.setTemplate_id(rs.getInt("template_id"));
		
		this.setAdditionalOptions(rs.getInt("ebayoptionen"));
		
		return vorlage;
	}
    
	public Vorlage setAdditionalOptions(int ebayoptionen){
		
		String ebayoptionen_bin ;
		
		if(ebayoptionen !=0){
			
			
			ebayoptionen_bin = new StringBuffer(Integer.toBinaryString(ebayoptionen)).reverse().toString();
			try{
			vorlage.setEbayueberschrifthighlight(Integer.parseInt(ebayoptionen_bin.substring(0, 1)));
			LOGGER.debug("Ebayueberschrifthighlight is:{}",vorlage.getEbayueberschrifthighlight());
			vorlage.setEbayueberschriftfett(Integer.parseInt(ebayoptionen_bin.substring(1, 2)));
			LOGGER.debug("Ebayueberschriftfett is:{}",vorlage.getEbayueberschriftfett());
			vorlage.setEbaytop(Integer.parseInt(ebayoptionen_bin.substring(2, 3)));
			LOGGER.debug("Ebaytop is{}",vorlage.getEbaytop());
			vorlage.setEbaygaleriebild(Integer.parseInt(ebayoptionen_bin.substring(3, 4)));
			LOGGER.debug("Ebaygaleriebild is:{}",vorlage.getEbaygaleriebild());
			vorlage.setTop_startseite(Integer.parseInt(ebayoptionen_bin.substring(4, 5)));
			LOGGER.debug("Top_startseite is:{}",vorlage.getTop_startseite());
		}
		 
			
		  catch (Exception e) {
			// TODO: handle exception
			  LOGGER.debug("Inside vorlage exception");
			  e.printStackTrace();
		 }
			
			LOGGER.debug("after try");
	}
		
		return vorlage;
	}

	
	
}
