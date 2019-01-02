package com.cultuzz.channel.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


import com.cultuzz.channel.template.pojo.OfferBO;



public class OfferReviseMapper implements RowMapper<OfferBO> {

	public OfferBO mapRow(ResultSet resultSet, int arg1) throws SQLException {
		
		OfferBO offerBO = new OfferBO();
		
		offerBO.setId(resultSet.getInt("id"));
		offerBO.setPrice(resultSet.getFloat("price"));

		offerBO.setVorlageId(resultSet.getInt("vorlage_id"));
		offerBO.setEndTime(resultSet.getString("enddate"));

		offerBO.setPrice(resultSet.getFloat("price"));
		//offer.setQuantity(resultSet.get);
		
		return offerBO;
	}

}
