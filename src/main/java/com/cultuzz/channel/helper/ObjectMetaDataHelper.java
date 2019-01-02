package com.cultuzz.channel.helper;

import com.cultuzz.channel.XMLpojo.ObjectMetaDataRQ;
import com.cultuzz.channel.XMLpojo.ObjectMetaDataRS;

public interface ObjectMetaDataHelper {

	public ObjectMetaDataRS validateObjectMetaDataRQ(
			ObjectMetaDataRQ objectMetaDataRQ);
	public ObjectMetaDataRS processObjectMetaDataRQ(
			ObjectMetaDataRQ objectMetaDataRQ);
}
