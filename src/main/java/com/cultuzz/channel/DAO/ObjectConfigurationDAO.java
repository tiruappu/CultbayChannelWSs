package com.cultuzz.channel.DAO;

import java.util.List;
import java.util.Map;

import com.cultuzz.channel.XMLpojo.ObjectConfigurationRQ;

public interface ObjectConfigurationDAO {

	public boolean checkEbayDaten(int objectid);
	public List<Map<String, Object>> getListOfVoucherTemplateids();
	public int checkGutscheinXObjekt(int objectid);
	public boolean checkVoucherConf(int objectid);
	public int getEbayDatenId(int objectid);
	public boolean saveGutscheinXObjekt(ObjectConfigurationRQ objectConfigurationRQ);
	public boolean updateGutscheinXObjekt(int pdfTemplateid,int objectid);
	public boolean saveVoucherConfiguration(ObjectConfigurationRQ objectConfigurationRQ);
	public boolean updateVoucherConfiguration(ObjectConfigurationRQ objectConfigurationRQ);
	public boolean checkToken(String token,int objectid);
	public String checkTokenExistance(int objectid);
	public boolean insertReputizeToken(ObjectConfigurationRQ objectConfigurationRQ,String token);
	public boolean updateReputizeToken(String token,int objectid);
	public String getOLogoImagePath(int objectid);
	public boolean deleteOldSliderPics(int objectid);
	public boolean saveObjectSliderPics(int objectid,String imagepath,int seqid);
	public List<Map<String,Object>> getDefaultTACData(int objectid,int language);
	public List<Map<String,Object>> getAdditionalTACData(int objectid,int language);
	public Integer getTACTopicId(int objectid,int langid,int posid);
	public int saveTopic(int positionid,int langid,String description,int objectid);
	public boolean saveTopicStates(int topicid,int state,String description);
	public boolean updateTopicText(int topicid,String description);
	public boolean saveAdditionaTAC(int posid,int langid,String headtitle,String bodytext,int objectid);
	public boolean deleteOldAdditionaltac(int objectid,int language);
	public boolean deleteOldFaq(int objectid,int langid);
	public boolean saveFaqText(int objectid,int langid,String title,String description);
	public List<Map<String,Object>> getFaqData(int objectid,int language);
	public boolean saveObjectLogoPicture(int objectid,String urlpath);
	public boolean updateObjectLogoPic(int objectid,String imagepath);
	public List<Map<String,Object>> getPaymentData(int objectid);
	public boolean updatePaymentData(int objectid,String paymentid,int siteid);
	public boolean savePaymentData(int objectid,int siteid,String paypalid,int status,String changer);
	public boolean checkSiteId(int siteid);
	public Map<String,Object> getVoucherConfData(int objectid);
	public List<String> getObjectSliderPics(int objectid);
	public boolean saveMapCoordinates(int objectid,String longititude,String latitude,String time);
	public boolean updateMapCoordinates(int objectid,String longititude,String latitude);
	public Map<String,Object> getMapCoordinates(int objectid);
	public boolean updateMapZoomLevel(int objectid,int zoomlevel);
	public boolean updateTotalPaymentData(int objectid);
	public int getZoomLevel(int objectid);
	public boolean checkFutureOffersLiveSiteId(String siteid,int objectid);
	public boolean checkFutureOffersLiveOffersObject(int objectid);
}
