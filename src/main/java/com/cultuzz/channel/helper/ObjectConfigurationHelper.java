package com.cultuzz.channel.helper;

import com.cultuzz.channel.XMLpojo.ObjectConfigurationRQ;
import com.cultuzz.channel.XMLpojo.ObjectConfigurationRS;
import com.cultuzz.channel.XMLpojo.ObjectMetaDataRQ;
import com.cultuzz.channel.XMLpojo.ObjectMetaDataRS;

public interface ObjectConfigurationHelper {

	
	ObjectConfigurationRS validateObjectConfigurationRQ(ObjectConfigurationRQ objectConfigurationRQ);
	
	ObjectConfigurationRS processObjectConfigurationRQ(ObjectConfigurationRQ objectConfigurationRQ);
}
