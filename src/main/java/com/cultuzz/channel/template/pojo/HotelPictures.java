package com.cultuzz.channel.template.pojo;

public class HotelPictures {
	
	private int id;
	private int categoryId;
	private String name;
	private String URL;
	private String ThumbnailURL;
	private int hotelid;
	private String dimensions;
	private int status;
	private String lastUpdate;
	
	
	
	
	public String getThumbnailURL() {
		return ThumbnailURL;
	}
	public void setThumbnailURL(String thumbnailURL) {
		ThumbnailURL = thumbnailURL;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public int getHotelid() {
		return hotelid;
	}
	public void setHotelid(int hotelid) {
		this.hotelid = hotelid;
	}
	public String getDimensions() {
		return dimensions;
	}
	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	
	
	
	

}
