package com.cultuzz.channel.helper;

import com.cultuzz.channel.XMLpojo.ListOfTemplatesRQ;
import com.cultuzz.channel.XMLpojo.ListOfTemplatesRS;

public interface ListOfTemplatesValidator {

	
	ListOfTemplatesRS validateListOfTemplates(ListOfTemplatesRQ listOfTemplatesRQ);
	
}
