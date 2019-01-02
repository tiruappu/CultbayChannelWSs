package com.cultuzz.channel.helper;

import com.cultuzz.channel.XMLpojo.TemplateDetailsRQ;
import com.cultuzz.channel.XMLpojo.TemplateDetailsRS;


public interface TemplateDetailsHelper {
	
	TemplateDetailsRS validateTemplateDetailsRQ(TemplateDetailsRQ templateDetailsRQ);
	
	TemplateDetailsRS processTemplateDetailsRQ(TemplateDetailsRQ templateDetailaRQ);
	
}
