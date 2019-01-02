package com.cultuzz.channel.template.pojo;


//This class is define to know type of price ie auktion or fixed...
public class PriceType {

	float ebaysofortkauf;
	float startpreis;
	int  AuctionMasterTypeID;
	
	public int getAuctionMasterTypeID() {
		return AuctionMasterTypeID;
	}
	public void setAuctionMasterTypeID(int auctionMasterTypeID) {
		AuctionMasterTypeID = auctionMasterTypeID;
	}
	public float getEbaysofortkauf() {
		return ebaysofortkauf;
	}
	public void setEbaysofortkauf(float ebaysofortkauf) {
		this.ebaysofortkauf = ebaysofortkauf;
	}
	public float getStartpreis() {
		return startpreis;
	}
	public void setStartpreis(float startpreis) {
		this.startpreis = startpreis;
	}
	
}
