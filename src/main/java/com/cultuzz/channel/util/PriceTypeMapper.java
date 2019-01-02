package com.cultuzz.channel.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.cultuzz.channel.template.pojo.PriceType;

public class PriceTypeMapper implements RowMapper<PriceType>{

	public PriceType mapRow(ResultSet resultSet, int arg1) throws SQLException {
		
		PriceType priceType = new PriceType();
		
		priceType.setEbaysofortkauf(resultSet.getFloat("ebaysofortkauf"));
		priceType.setStartpreis(resultSet.getFloat("startpreis"));
		priceType.setAuctionMasterTypeID(resultSet.getInt("AuctionMasterTypeID"));
		
		return priceType;
	}
	
}
