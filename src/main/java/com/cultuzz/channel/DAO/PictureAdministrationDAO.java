package com.cultuzz.channel.DAO;

import java.util.List;

import com.cultuzz.channel.XMLpojo.AddPictureRQ;
import com.cultuzz.channel.template.pojo.HotelPictures;
import com.cultuzz.channel.template.pojo.PictureCategories;

public interface PictureAdministrationDAO {

	public boolean checkImageId(int imageid);
	
	public List<HotelPictures> getAllImages(int objectid,int categoryId);
	
	public int savePictureData(AddPictureRQ addpic);
	
	public List<PictureCategories> getAllPictureCategories(int objectid);
	
	public boolean updateImageStatus(int imageid);
	
}
