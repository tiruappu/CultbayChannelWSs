package com.cultuzz.channel.helper;

import com.cultuzz.channel.XMLpojo.ListOfDesignTemplatesRQ;
import com.cultuzz.channel.XMLpojo.ListOfDesignTemplatesRS;

public interface DesignTemplateHelper {

	public ListOfDesignTemplatesRS validateListOfDesignTemplate(ListOfDesignTemplatesRQ listOfDesignTemplatesRQ);
	public ListOfDesignTemplatesRS processListOfDesigntemplates(ListOfDesignTemplatesRQ listOfDesignTemplatesRQ);
	
}
