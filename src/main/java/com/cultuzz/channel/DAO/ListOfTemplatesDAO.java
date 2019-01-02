package com.cultuzz.channel.DAO;

import java.util.List;

import com.cultuzz.channel.XMLpojo.ListOfTemplatesRS.Templates.Template;


public interface ListOfTemplatesDAO {

	public int getNoOfTemplates(int objectId);
	
	public List<Integer> getTemplateIds(String templateIdQuery);
	
	 public Template getTemplateDetails(int templateId) ;
	 
	 public int getSourceId(int templateId);
	
	 public int getNoOfTemplates(String lisOfTemplatesQueryCount);
	 
	 public int getNoOfLiveOffers(int templateId);
}
