package com.cultuzz.channel.DAO.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.DAO.PictureAdministrationDAO;
import com.cultuzz.channel.XMLpojo.AddPictureRQ;
import com.cultuzz.channel.jdbcTemplate.JDBCTemplate;
import com.cultuzz.channel.template.pojo.HotelPictures;
import com.cultuzz.channel.template.pojo.PictureCategories;

@Component
public class PictureAdministrationDAOImpl implements PictureAdministrationDAO{

	@Autowired
	@Qualifier("ebayTemplate")
	private JDBCTemplate ebayJdbcTemplate;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	@Qualifier("cusebedaTemplate")
	private JDBCTemplate cusebedaJdbcTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PictureAdministrationDAOImpl.class);
	
	
	public boolean checkImageId(int imageid){
		boolean imageStatus=false;
		try{
			LOGGER.debug("imageid checking ");
			String imageidchecking="select id from HotelPictures where id=? and status=1";
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		
		Integer imageidDB=jdbcTemplate.queryForObject(imageidchecking, new Object[] { imageid },Integer.class);
		if(imageidDB>0){
			imageStatus=true;
		}
		
		}catch(Exception e){
			LOGGER.debug("imageid checking error");
		}
		return imageStatus;
	}
	
	public List<HotelPictures> getAllImages(int objectid,int categoryId){
		List<HotelPictures> hotelPicturesList = null;
		
		LOGGER.debug("get all categories objectid:{}",objectid);
		
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		
		StringBuffer sql=new StringBuffer("select * from ebay.HotelPictures where hotelId="+objectid+" and status=1 order by id desc");
		if(categoryId>0){
			sql.append(" and categoryId="+categoryId);
		}
		
		try{
			//pictureCategoriesList = jdbcTemplate.queryForList(sql.toString(),new Object[] {objectid}, new PictureCategoriesMapper());
		
			
			
			hotelPicturesList= jdbcTemplate.query(sql.toString(),new RowMapper<HotelPictures>(){  
			      
			      public HotelPictures mapRow(ResultSet rs, int rownumber) throws  SQLException    {  
			    	  HotelPictures hotelPictures=new HotelPictures();  
			    	  hotelPictures.setCategoryId(rs.getInt("categoryId"));
			    	  hotelPictures.setDimensions(rs.getString("dimensions"));
			    	  hotelPictures.setId(rs.getInt("id"));
			    	  hotelPictures.setHotelid(rs.getInt("hotelId"));
			    	  hotelPictures.setName(rs.getString("name"));
			    	  hotelPictures.setURL(rs.getString("URL"));
			    	  hotelPictures.setThumbnailURL(rs.getString("ThumbnailURL"));
			            return hotelPictures;  
			          }  
			      
			      });  
			      
			
		LOGGER.debug("after result PictureAdministrationDAO is:{}",hotelPicturesList);
		        
		   }catch(EmptyResultDataAccessException e){
        	  
			   	//e.printStackTrace();
			   LOGGER.debug("error occured at getting empty pictures:{}");
			   	hotelPicturesList = null;		   
		   }catch(Exception exception){
			   LOGGER.debug("error occured at getting all pictures:{}");
			   	hotelPicturesList = null;	
		   }

		return hotelPicturesList;
		
		
		
	}
	
	public int savePictureData(AddPictureRQ addpicRQ){
		
		int pictureId=0;
		
			
			LOGGER.debug("this is save picture data");
			
			try{
			namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
			
			//Long id = 0l;

				StringBuffer savePictureQuery = new StringBuffer("insert into ebay.HotelPictures(categoryId,name,URL,ThumbnailURL,hotelId,dimensions,status)");

				savePictureQuery.append(" values(:categoryId,:name,:URL,:thumbnail,:hotelId,:dimensions,status)");
				
				String thumbnailurl=this.imageFormatConvert("Actual_","Thumb_",addpicRQ.getURL());
						

				Map<String, Object> bind = new HashMap<String, Object>();
				bind.put("categoryId",addpicRQ.getCategory());
				bind.put("name",addpicRQ.getImageName());
				bind.put("URL",addpicRQ.getURL());
				bind.put("thumbnail",thumbnailurl);
				bind.put("hotelId",addpicRQ.getObjectId());
				bind.put("dimensions",addpicRQ.getDimensions());
				bind.put("status", 1);
				
				LOGGER.debug("save picture query"+savePictureQuery.toString());
				SqlParameterSource paramSource = new MapSqlParameterSource(bind);
				KeyHolder keyHolder = new GeneratedKeyHolder();

				int var=this.namedJdbcTemplate.update(savePictureQuery.toString(), paramSource,keyHolder);
				pictureId=keyHolder.getKey().intValue();
				
				
				
			}catch(Exception e){
				
				LOGGER.debug("problem occured savePictureData insertion");
				//e.printStackTrace();
			}
		
		return pictureId;
	}
	
	
	public List<PictureCategories> getAllPictureCategories(int objectid){
		
		List<PictureCategories> pictureCategoriesList = null;
		
		
		LOGGER.debug("get all categories objectid:{}",objectid);
		
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		
		String sql="select * from ebay.PictureCategories where hotelId in (1,"+objectid+") and status=1";
		
		try{
			//pictureCategoriesList = jdbcTemplate.queryForList(sql.toString(),new Object[] {objectid}, new PictureCategoriesMapper());
		
			
			
			pictureCategoriesList= jdbcTemplate.query(sql,new RowMapper<PictureCategories>(){  
			      
			      public PictureCategories mapRow(ResultSet rs, int rownumber) throws  SQLException    {  
			    	  PictureCategories pictureCategories=new PictureCategories();  
			    	  pictureCategories.setCategoryId(rs.getInt("categoryId"));
			  		pictureCategories.setHotelId(rs.getInt("hotelId"));
			  		pictureCategories.setName(rs.getString("name"));
			  		pictureCategories.setStatus(rs.getInt("status"));
			  		pictureCategories.setLastUpdated(rs.getString("lastUpdated"));
			            return pictureCategories;  
			          }  
			      });  
			      
			
		LOGGER.debug("after result PictureAdministrationDAO is:{}",pictureCategoriesList);
		        
		   }catch(EmptyResultDataAccessException e){
        	  
			   	//e.printStackTrace();
          
			   	pictureCategoriesList = null;	
			   	LOGGER.debug("getting error inside picturecategories");
			   	
		   }catch(Exception eexception){
			   pictureCategoriesList = null;	
			   	LOGGER.debug("getting error inside picturecategories");
			   	
		   }

		return pictureCategoriesList;
	}
	
	public boolean updateImageStatus(int imageid){
			
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			boolean deleteImageStatus=false;
			try{
			String updatePictureSql="UPDATE ebay.HotelPictures SET status = 0 WHERE id = ?";
			jdbcTemplate.update(updatePictureSql, new Object[] { imageid });
			
			deleteImageStatus=true;
			}catch(Exception e){
				LOGGER.debug("problem occured image delete");
				//e.printStackTrace();
			}
			return deleteImageStatus;
		
		
		
	}
	
	public String imageFormatConvert(String fromFormat,String toFormat,String imageurl){
        //System.out.println("image converter URL"+imageurl);
        String imageArray[]=imageurl.split("/");
        
        String myimg=imageArray[imageArray.length-1];
        String convertedUrl=imageurl;
        if(myimg.startsWith(fromFormat)){
        	String originalImg=myimg.replaceFirst(fromFormat, toFormat);
        	convertedUrl=imageurl.replaceFirst(myimg, originalImg);
        }
        return convertedUrl;
    }
	
}
