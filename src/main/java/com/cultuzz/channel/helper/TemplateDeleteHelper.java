package com.cultuzz.channel.helper;

import com.cultuzz.channel.XMLpojo.TemplateDeletionRQ;
import com.cultuzz.channel.XMLpojo.TemplateDeletionRS;

public interface TemplateDeleteHelper {
	
	 TemplateDeletionRS validateTemplateDeleteRQ(TemplateDeletionRQ templateDeletionRQ);
	 
	 TemplateDeletionRS processTemplateDeleteRQ(TemplateDeletionRQ templateDeletionRQ);
}
