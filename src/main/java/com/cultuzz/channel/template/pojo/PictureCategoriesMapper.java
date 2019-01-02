package com.cultuzz.channel.template.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PictureCategoriesMapper implements RowMapper<PictureCategories>  {

	
	PictureCategories pictureCategories = new PictureCategories();
	
	
	public PictureCategories mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		
		pictureCategories.setCategoryId(rs.getInt("categoryId"));
		pictureCategories.setHotelId(rs.getInt("hotelId"));
		pictureCategories.setName(rs.getString("name"));
		pictureCategories.setStatus(rs.getInt("status"));
		pictureCategories.setLastUpdated(rs.getString("lastUpdated"));
		
		
		return pictureCategories;
	}
	
	
	
}
