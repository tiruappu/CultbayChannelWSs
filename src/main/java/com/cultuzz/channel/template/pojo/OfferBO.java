package com.cultuzz.channel.template.pojo;



public class OfferBO {

	int objectId;
	int id;
	long itemId;
	String title;
	String subTitle;
	String startTime;
	String endTime;
	int duration;
	int quantity;
	double price;
	int siteId;
	String ListingType;
	String bidderName;
	int quantityPurchased;
	int noOfViews;
	String description;
	int vorlageId;
	double highestBid;
    String currency;
    
    
	public String getCurrency() {

		return currency;
	}

	public int getVorlageId() {
		return vorlageId;
	}

	public void setVorlageId(int vorlageId) {
		this.vorlageId = vorlageId;
	}

	public int getQuantityPurchased() {
		return quantityPurchased;
	}

	public int getNoOfViews() {
		return noOfViews;
	}

	public String getDescription() {
		return description;
	}
	public void setCurrency(String currency) {

		this.currency = currency;
	}

	public void setQuantityPurchased(int quantityPurchased) {
		this.quantityPurchased = quantityPurchased;
	}

	public void setNoOfViews(int noOfViews) {
		this.noOfViews = noOfViews;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getObjectId() {
		return objectId;
	}

	public int getId() {
		return id;
	}

	public long getItemId() {
		return itemId;
	}

	public String getTitle() {
		return title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public int getDuration() {
		return duration;
	}

	public int getQuantity() {
		return quantity;
	}
	public double getPrice() {

		return price;
	}

	public int getSiteId() {
		return siteId;
	}

	public String getListingType() {
		return ListingType;
	}
	public double getHighestBid() {

		return highestBid;
	}

	public String getBidderName() {
		return bidderName;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void setPrice(double price) {

		this.price = price;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public void setListingType(String listingType) {
		ListingType = listingType;
	}
	public void setHighestBid(double highestBid) {

		this.highestBid = highestBid;
	}

	public void setBidderName(String bidderName) {
		this.bidderName = bidderName;
	}

	public boolean isSetObjectId() {
        return true;
    }
	
	public boolean isSetId() {
        return true;
    }

	public boolean isSetItemId() {
        return true;
    }
	public boolean isSetTitle() {
        return (this.title!= null);
    }
	
	public boolean isSetSubTitle() {
        return (this.subTitle!= null);
    }
	public boolean isSetStartTime() {
        return (this.startTime!= null);
    }
	public boolean isSetEndTime() {
        return (this.endTime!= null);
    }
	
	public boolean isSetDuration() {
        return true;
    }
	public boolean isSetSiteId() {
        return true;
    }
}
