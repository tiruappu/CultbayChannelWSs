package com.cultuzz.channel.template.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cultuzz.channel.XMLpojo.ListOfTemplatesRS.Templates.Template;

public class ListOfTemplateMapper implements RowMapper<Template>{

	public Template mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		// TODO Auto-generated method stub
		
		Template template = new Template();
		//ebaysofortkauf,startpreis,auctionMasterTypeID
		template.setSiteId(rs.getInt("ebaysiteid"));
		template.setDesignTemplate(rs.getInt("template_id"));
		template.setStatus(rs.getInt("status"));
		template.setTemplateName(rs.getString("bezeichnung"));
		if(rs.getInt("auctionMasterTypeID")==4){
			String price=String.format("%.2f", Double.parseDouble(rs.getString("startpreis")));
		template.setPrice(price);
		}else if(rs.getInt("auctionMasterTypeID")==1){
			
			if(rs.getDouble("ebaysofortkauf")>0){
				String price=String.format("%.2f", Double.parseDouble(rs.getString("ebaysofortkauf")));
			template.setPrice(price);
			}else{
				String price=String.format("%.2f", Double.parseDouble(rs.getString("startpreis")));
				
				template.setPrice(price);
			}
		}
		
		
		return template;
	}

}
