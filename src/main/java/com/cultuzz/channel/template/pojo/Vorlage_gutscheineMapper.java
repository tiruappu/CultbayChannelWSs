package com.cultuzz.channel.template.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class Vorlage_gutscheineMapper implements RowMapper<Vorlage_gutscheine>{

	public Vorlage_gutscheine mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		// TODO Auto-generated method stub
		
		Vorlage_gutscheine vorlage_gutscheine = new Vorlage_gutscheine();
		
		if(rs.getString("text")==null){
			
			vorlage_gutscheine.setText("");
		}else{
		vorlage_gutscheine.setText(rs.getString("text"));
		}
		if(rs.getString("text2")==null){
		vorlage_gutscheine.setText2("");
		}else{
			vorlage_gutscheine.setText2(rs.getString("text2"));
		}
		if(rs.getString("gutschein_text")==null){
		vorlage_gutscheine.setGutschein_text("");
		}else{
			vorlage_gutscheine.setGutschein_text(rs.getString("gutschein_text"));
		}
		if(rs.getString("gueltigkeit_text")==null){
			vorlage_gutscheine.setGueltigkeit_text("");
		}else{
		vorlage_gutscheine.setGueltigkeit_text(rs.getString("gueltigkeit_text"));
		}
		vorlage_gutscheine.setGueltigkeit(rs.getInt("gueltigkeit"));
		
		return vorlage_gutscheine;
	}

}
