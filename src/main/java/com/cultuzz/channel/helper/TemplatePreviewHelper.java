package com.cultuzz.channel.helper;

import com.cultuzz.channel.XMLpojo.TemplatePreviewRQ;
import com.cultuzz.channel.XMLpojo.TemplatePreviewRS;

public interface TemplatePreviewHelper {
	
	TemplatePreviewRS validatePreviewRQ(TemplatePreviewRQ templatePreviewRQ);
	
	TemplatePreviewRS processPreviewRQ(TemplatePreviewRQ templatePreviewRQ);

}
