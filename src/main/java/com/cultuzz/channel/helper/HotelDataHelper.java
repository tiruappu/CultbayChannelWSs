package com.cultuzz.channel.helper;

import com.cultuzz.channel.XMLpojo.GetHotelDataRQ;
import com.cultuzz.channel.XMLpojo.GetHotelDataRS;
import com.cultuzz.channel.XMLpojo.SetHotelDataRQ;
import com.cultuzz.channel.XMLpojo.SetHotelDataRS;

public interface HotelDataHelper {

  public SetHotelDataRS validateSetHotelData(SetHotelDataRQ setHotelDataRQ);
  public SetHotelDataRS processSetHotelData(SetHotelDataRQ setHotelDataRQ);
  public GetHotelDataRS validateGetHotelData(GetHotelDataRQ getHotelDataRQ);
  public GetHotelDataRS processGetHotelData(GetHotelDataRQ getHotelDataRQ);
	
}
