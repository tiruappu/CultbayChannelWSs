package com.cultuzz.channel.DAO;

import java.util.List;
import java.util.Map;

public interface DesignTemplateDAO {

	public List<Map<String, Object>> getDesignTemplates(int objectId);
	public List<Map<String,Object>> getDesingTemplateCategories(int templateid);
	public int getDesignTemplatesCount(int objectId);
}
