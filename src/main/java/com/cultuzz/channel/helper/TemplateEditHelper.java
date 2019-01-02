package com.cultuzz.channel.helper;

import com.cultuzz.channel.XMLpojo.TemplateEditRQ;
import com.cultuzz.channel.XMLpojo.TemplateEditRS;

public interface TemplateEditHelper {

	TemplateEditRS validateTemplateEditRQ(TemplateEditRQ templateEditRQ);
	
	TemplateEditRS processTemplateEditRQ(TemplateEditRQ templateEditRQ);
	
	
}
