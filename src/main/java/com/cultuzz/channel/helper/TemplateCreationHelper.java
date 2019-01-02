package com.cultuzz.channel.helper;

import com.cultuzz.channel.XMLpojo.TemplateCreationRQ;
import com.cultuzz.channel.XMLpojo.TemplateCreationRS;


public interface TemplateCreationHelper {
	
	TemplateCreationRS validateTemplateCreationRQ(TemplateCreationRQ templateCreationRQ);
	
	TemplateCreationRS processTemplateCreationRQ(TemplateCreationRQ templateCreationRQ);
	
	

}
