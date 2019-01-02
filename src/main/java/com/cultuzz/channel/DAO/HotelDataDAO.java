package com.cultuzz.channel.DAO;

import com.cultuzz.channel.XMLpojo.AddressType;
import com.cultuzz.channel.XMLpojo.SetHotelDataRQ.HotelDetails;

public interface HotelDataDAO {

	public boolean saveHotelDetails(long hotelId, HotelDetails hotelDetails);
	public boolean saveBillingAddress(long hotelId, com.cultuzz.channel.XMLpojo.SetHotelDataRQ.BillingAddress billingAddress);
	public boolean saveLegalDetails(long hotelId,com.cultuzz.channel.XMLpojo.SetHotelDataRQ.LegalAddress legalAddress);
	public boolean saveAddress(long hotelId, AddressType address,int type);
	public boolean saveDescription(long hotelId, com.cultuzz.channel.XMLpojo.SetHotelDataRQ.Descriptions.Description description);
	public boolean getHotelInfoCount(long objectId);
	public boolean getBillingInfoCount(long objectId);
	public boolean getLegalInfoCount(long objectId);
	public boolean getAddressCount(long objectId, int type);
	public boolean getDescriptionCount(long hotelId, String language);
	public com.cultuzz.channel.XMLpojo.GetHotelDataRS.HotelDetails getHotelInfo(long hotelId);
	public AddressType getAddress(long HotelId, int type);
	public com.cultuzz.channel.XMLpojo.GetHotelDataRS.BillingAddress getBillingDetails(long hotelId);
	public com.cultuzz.channel.XMLpojo.GetHotelDataRS.LegalAddress getLegalDetails(long hotelId);
	public com.cultuzz.channel.XMLpojo.GetHotelDataRS.Descriptions getDescriptions(long hotelId);
	public com.cultuzz.channel.XMLpojo.GetHotelDataRS.Descriptions.Description getLangDescription(long hotelId, String language);
	public boolean saveARHotelDetails(long hotelId, com.cultuzz.channel.XMLpojo.GetHotelDataRS.HotelDetails hotelDetails,String lastupdated);
	public boolean saveArBillingDetails(long hotelId, com.cultuzz.channel.XMLpojo.GetHotelDataRS.BillingAddress billingAddress,String lastupdated);
	public boolean saveArLegalDetails(long hotelId, com.cultuzz.channel.XMLpojo.GetHotelDataRS.LegalAddress legalAddress,String lastupdated);
	public boolean saveArAddressInfo(long hotelId, AddressType address,String lastupdated, int type);
	public boolean saveArDescription(long hotelId, com.cultuzz.channel.XMLpojo.GetHotelDataRS.Descriptions.Description description ,String lastupdated);
	public String getLastUpdated(long hotelId, int type, int addresstype);
	
	
}
