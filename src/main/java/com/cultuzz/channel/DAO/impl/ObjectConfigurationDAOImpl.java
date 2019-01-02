package com.cultuzz.channel.DAO.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.cultuzz.channel.DAO.ObjectConfigurationDAO;
import com.cultuzz.channel.XMLpojo.ObjectConfigurationRQ;
import com.cultuzz.channel.jdbcTemplate.JDBCTemplate;

@Repository
public class ObjectConfigurationDAOImpl implements ObjectConfigurationDAO {

	
	@Autowired
	@Qualifier("ebayTemplate")
	private JDBCTemplate ebayJdbcTemplate;
	
	@Autowired
	@Qualifier("cusebedaTemplate")
	private JDBCTemplate cusebedaJdbcTemplate;
	
	private JdbcTemplate jdbcTemplate;
	
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(ObjectConfigurationDAOImpl.class);
	public boolean checkEbayDaten(int objectid){
		
		boolean ebayDatenStatus=false;
		
		try{
			logger.debug("inside ebaydaten table checking{}",objectid);
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="select count(id) from ebay.ebaydaten where cusebeda_objekt_id=?";
			int countOfobjectids =jdbcTemplate.queryForObject(sql, new Object[] { objectid },Integer.class);
			
			if(countOfobjectids>0)
				ebayDatenStatus=true;
			
			}catch(Exception e){
				ebayDatenStatus=false;
				logger.error("inside ebaydaten table error{}",e);
				//e.printStackTrace();
				
			}

		return ebayDatenStatus;
	}
	
	
	public List<Map<String, Object>> getListOfVoucherTemplateids()
	{
		logger.debug("inside getting voucher template ids");
		 
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		String sql="select id from ebay.pdf_template";
		
		List<Map<String, Object>> templateids=null;
		try{
			
				jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
				
				templateids = jdbcTemplate.queryForList(sql);
			
			}
		   catch(Exception e){
			   	//e.printStackTrace();
			   	logger.error("problem inside voucherTemplate ids{}",e);
		   }

		return templateids;

	}
	
	public int checkGutscheinXObjekt(int objectid){
		
		//boolean gXoStatus=false;
		int pdfTemplateid=0;
		try{
			logger.debug("inside GutscheinXObjekt table checking{}",objectid);
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="select pdf_template_id from ebay.gutschein_x_objekt where cusebeda_objekt_id=?";
			pdfTemplateid =jdbcTemplate.queryForObject(sql, new Object[] { objectid },Integer.class);
			
			if(pdfTemplateid>0)
				logger.debug("inside GutscheinXObjekt data is existed");
			
			}catch(Exception e){
				//gXoStatus=false;
				logger.error("inside GutscheinXObjekt table Error{}",e);
				//e.printStackTrace();
				
			}

		return pdfTemplateid;
	}
	
	public boolean checkVoucherConf(int objectid){
		
		boolean vcStatus=false;
		
		try{
			logger.debug("inside GutscheinXObjekt table checking{}",objectid);
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="select count(cusebeda_objekt_id) from ebay.voucherconfiguration where cusebeda_objekt_id=?";
			int countOfobjectids =jdbcTemplate.queryForObject(sql, new Object[] { objectid },Integer.class);
			
			if(countOfobjectids>0)
				vcStatus=true;
			
			}catch(Exception e){
				logger.error("inside GutscheinXObjekt table error{}",e);
				vcStatus=false;
				//e.printStackTrace();
				
			}

		return vcStatus;
	}
	
	public Map<String,Object> getVoucherConfData(int objectid){
		Map<String,Object> voucherConfData=null;
		
		try{
			logger.debug("inside GutscheinXObjekt table gettingData{}",objectid);
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="select imagepath,logopath from ebay.voucherconfiguration where cusebeda_objekt_id=? limit 1";
			List<Map<String,Object>> voucherConfigData=jdbcTemplate.queryForList(sql, new Object[] { objectid });
			
			if(voucherConfigData!=null && voucherConfigData.size()>0){
				voucherConfData=voucherConfigData.get(0);
			}
			
		}catch(NullPointerException npe){
			logger.error("inside GutscheinXObjekt table npe error{}",npe);
		}catch(Exception e){
			logger.error("inside GutscheinXObjekt table error{}",e);
		}
		
		return voucherConfData;
	}
	
	
	public int getEbayDatenId(int objectid){
		int ebaydatenid=0;
		
		logger.debug("inside getting ebaydaten id");
		try{
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		String sql="select id from ebay.ebaydaten where cusebeda_objekt_id=?";
		 objectid=jdbcTemplate.queryForObject(sql, new Object[] { objectid },Integer.class);
		
		}catch(Exception e){
			logger.error("inside exception ebaydaten id error "+e);
			//e.printStackTrace();
		}
		return ebaydatenid;
	}
	
	public boolean saveGutscheinXObjekt(ObjectConfigurationRQ objectConfigurationRQ){
		boolean saveGXOStatus=false;
		try{
			logger.debug("inside save saveGutscheinXObjekt method");
			java.util.Date date = new java.util.Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
			StringBuffer insertGXOQuery = new StringBuffer("insert into ebay.gutschein_x_objekt(ebaydaten_id,cusebeda_objekt_id,pdf_template_id,aktivieren,uebertragbar,service_status,gutscheinimage_id,auktionen_update,aktiviert_datum)");
			
			insertGXOQuery.append(" values(:ebaydaten_id,:cusebeda_objekt_id,:pdf_template_id,:aktivieren,:uebertragbar,:service_status,:gutscheinimage_id,:auktionen_update,:aktiviert_datum)");
			
			Map<String, Object> sourceGXO = new HashMap<String, Object>();
			sourceGXO.put("ebaydaten_id", this.getEbayDatenId(objectConfigurationRQ.getObjectId()));
			sourceGXO.put("cusebeda_objekt_id",objectConfigurationRQ.getObjectId());
			sourceGXO.put("pdf_template_id",objectConfigurationRQ.getVoucher().getVoucherPdfTemplateId());
			sourceGXO.put("aktivieren",1);
			sourceGXO.put("uebertragbar",1);
			sourceGXO.put("service_status",1);
			sourceGXO.put("gutscheinimage_id",0);
			sourceGXO.put("auktionen_update",1);
			sourceGXO.put("aktiviert_datum",timestamp);
			
			SqlParameterSource gxoparam = new MapSqlParameterSource(sourceGXO);
			
			int insertCount=this.namedJdbcTemplate.update(insertGXOQuery.toString(), gxoparam);
				
			if(insertCount>0)
				saveGXOStatus=true;
			
			
		}catch(Exception e){
			logger.error("inside problem saveGutscheinXObjekt method"+e);
			//e.printStackTrace();
		}
		return saveGXOStatus;
	}
	
	public boolean updateGutscheinXObjekt(int pdfTemplateid,int objectid){
		boolean updateGXOStatus=false;
		try{
			logger.debug("inside update updateGutscheinXObjekt method");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			
			String updateGXOSql="UPDATE ebay.gutschein_x_objekt SET pdf_template_id = "+pdfTemplateid+" WHERE cusebeda_objekt_id = ?";
			int auktionTableUpdate =jdbcTemplate.update(updateGXOSql, new Object[] { objectid });
			
			if(auktionTableUpdate>0)
				updateGXOStatus=true;
			}catch(Exception e){
				logger.error("inside error updateGutscheinXObjekt method"+e);
				//e.printStackTrace();
			}
			
		return updateGXOStatus;
	}
	
	public boolean saveVoucherConfiguration(ObjectConfigurationRQ objectConfigurationRQ){
		boolean saveVCStatus=false;
		try{
			logger.debug("inside save saveVoucherConfiguration method");
			java.util.Date date = new java.util.Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
			StringBuffer insertGXOQuery = new StringBuffer("insert into ebay.voucherconfiguration(cusebeda_objekt_id,imagepath,logopath,sourceid,insertDate)");
			
			insertGXOQuery.append(" values(:cusebeda_objekt_id,:imagepath,:logopath,:sourceid,:insertDate)");
			
			Map<String, Object> sourceVC = new HashMap<String, Object>();
			sourceVC.put("cusebeda_objekt_id", objectConfigurationRQ.getObjectId());
			sourceVC.put("imagepath",objectConfigurationRQ.getVoucher().getVoucherPictureURL());
			sourceVC.put("logopath",objectConfigurationRQ.getVoucher().getVoucherLogoPictureURL());
			sourceVC.put("sourceid",objectConfigurationRQ.getSourceId());
			sourceVC.put("insertDate",timestamp);
			
			SqlParameterSource vcparam = new MapSqlParameterSource(sourceVC);
			
			int insertCount=this.namedJdbcTemplate.update(insertGXOQuery.toString(), vcparam);
				
			if(insertCount>0)
				saveVCStatus=true;
			
		}catch(Exception e){
			logger.error("inside exception saveVoucherConfiguration method"+e);
			//e.printStackTrace();
		}
		return saveVCStatus;
	}
	
	public boolean updateVoucherConfiguration(ObjectConfigurationRQ objectConfigurationRQ){
		boolean updateVCStatus=false;
	try{
		logger.debug("inside update updateVoucherConfiguration method");
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();

		try{
		String updateAuktionSql="UPDATE ebay.voucherconfiguration SET imagepath='"+objectConfigurationRQ.getVoucher().getVoucherPictureURL()+"', logopath='"+objectConfigurationRQ.getVoucher().getVoucherLogoPictureURL()+"', sourceid="+objectConfigurationRQ.getSourceId()+"  WHERE cusebeda_objekt_id = ?";
		int auktionTableUpdate =jdbcTemplate.update(updateAuktionSql, new Object[] { objectConfigurationRQ.getObjectId() });
		
		if(auktionTableUpdate>0)
			updateVCStatus=true;
		}catch(Exception e){
			logger.error("inside problem update updateVoucherConfiguration method"+e);
			//e.printStackTrace();
		}
		
	}catch(Exception e){
		logger.error("inside problem at jdbc Template updateVoucherConfiguration method"+e);
		//e.printStackTrace();
	}
		return updateVCStatus;
	}
	
	public boolean updateReputizeToken(String token,int objectid){
		boolean updateRtokenstatus=false;
		logger.debug("inside save update updateReputizeToken method");
		Map<String,String> tokendata=getReputizeTokenData(objectid);
		boolean artokenstatus=false;
		if(tokendata.size()>0){
		 try{
			artokenstatus=insertarchiveReputizeToken(Integer.parseInt(tokendata.get("cusebeda_objekt_id").toString()),tokendata.get("token").toString(),tokendata.get("lastUpdated").toString(),Integer.parseInt(tokendata.get("source").toString()));
			logger.debug("update reputize token status{}",artokenstatus);
		 }catch(Exception e){
			 logger.error("inside exception update updateReputizeToken method "+e);
			 //e.printStackTrace();
		 }
		 }
		
		try{
			
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			
				StringBuffer updateRtokenSql=new StringBuffer("update ebay.object_ReputizeToken set token='").append(token).append("' ,source=2 where cusebeda_objekt_id=?");
				
			int objectTokenTableUpdate =jdbcTemplate.update(updateRtokenSql.toString(), new Object[] { objectid});
						
			if(objectTokenTableUpdate>0)
				updateRtokenstatus=true;
			
		}catch(Exception e){
			logger.error("inside problem save update updateReputizeToken method"+e);
			//e.printStackTrace();
		}
		
		
		return updateRtokenstatus;
	}
	
	public boolean insertReputizeToken(ObjectConfigurationRQ objectConfigurationRQ,String token){
		boolean insertRtokenstatus=false;
		
		try{	
			namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
			logger.debug("inside save update insertReputizeToken method");
				StringBuffer rtokeninsertQuery = new StringBuffer("insert into ebay.object_ReputizeToken(cusebeda_objekt_id,token,source)").append(" values(:cusebeda_objekt_id,:token,:source)");
				
				Map<String, Object> rtokenConf = new HashMap<String, Object>();
				rtokenConf.put("cusebeda_objekt_id", objectConfigurationRQ.getObjectId());
				rtokenConf.put("token",token);
				rtokenConf.put("source",objectConfigurationRQ.getSourceId());
			
				SqlParameterSource rtokenparam = new MapSqlParameterSource(rtokenConf);
				
				int insertCount=this.namedJdbcTemplate.update(rtokeninsertQuery.toString(), rtokenparam);
					
				if(insertCount>0)
					insertRtokenstatus=true;
				
		}catch(Exception e){
			logger.error("inside save problem update insertReputizeToken method"+e);
			//e.printStackTrace();
		}
		
		return insertRtokenstatus;
	}
	
	public String checkTokenExistance(int objectid){
		logger.debug("inside  checkTokenExistance method");	
		
		String objectReputizeToken=null;
		try{
			logger.debug("inside token checking{}",objectid);
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="select token from ebay.object_ReputizeToken where cusebeda_objekt_id=?";
			objectReputizeToken =jdbcTemplate.queryForObject(sql, new Object[] { objectid },String.class);
			
			}catch(Exception e){
				logger.error("inside problem checkTokenExistance method");	
				
				//e.printStackTrace();
				
			}

		return objectReputizeToken;
	}
	
	public boolean checkToken(String token,int objectid){
		
		boolean checkTokenStatus=false;
		logger.debug("inside checking checkToken method");	
		try{
			logger.debug("inside token checking{}",objectid);
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="select count(*) as tokens from ebay.object_ReputizeToken where cusebeda_objekt_id!=? and token like '"+token+"'";
			int countOftokens =jdbcTemplate.queryForObject(sql, new Object[] { objectid },Integer.class);
			
			if(countOftokens>0)
				checkTokenStatus=true;
			
			}catch(Exception e){
				logger.error("inside problem checking checkToken method");
				checkTokenStatus=false;
				e.printStackTrace();
				
			}
		
		return checkTokenStatus;
	}
	
	public Map<String,String> getReputizeTokenData(int objectid){
		logger.debug("inside getting reputize data getReputizeTokenData method");
		Map<String,String> tokenData=new HashMap<String,String>();	
		try{
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			StringBuilder tokenDeatilsQuery=new StringBuilder("select cusebeda_objekt_id,token,lastUpdated,source from ebay.object_ReputizeToken where cusebeda_objekt_id=?");
			
			List<Map<String,Object>> tokentextdata=jdbcTemplate.queryForList(tokenDeatilsQuery.toString(),objectid);
			
			for(Map<String,Object> tokenMap:tokentextdata){
				tokenData.put("cusebeda_objekt_id", tokenMap.get("cusebeda_objekt_id").toString());
				tokenData.put("token", tokenMap.get("token").toString());
				tokenData.put("lastUpdated", tokenMap.get("lastUpdated").toString());
				tokenData.put("source",tokenMap.get("source").toString());	
			}
			
		}catch(Exception tokenException){
			logger.error("inside problem getting reputize data getReputizeTokenData method");
			tokenException.printStackTrace();
		}
		
		return tokenData;
		
	}
	
	public boolean insertarchiveReputizeToken(int objectid,String token,String lastupdate,int source){
		boolean insertARtokenstatus=false;
		
		try{	
				namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
				logger.debug("inside save insertarchiveReputizeToken method");
				StringBuffer artokeninsertQuery = new StringBuffer("insert into ebay.ar_object_ReputizeToken(cusebeda_objekt_id,token,insertedtime,source)").append(" values(:cusebeda_objekt_id,:token,:insertedtime,:source)");
				
				Map<String, Object> artokenConf = new HashMap<String, Object>();
				artokenConf.put("cusebeda_objekt_id", objectid);
				artokenConf.put("token",token);
				artokenConf.put("insertedtime",lastupdate);
				artokenConf.put("source",source);
			
				SqlParameterSource artokenparam = new MapSqlParameterSource(artokenConf);
				
				int insertCount=this.namedJdbcTemplate.update(artokeninsertQuery.toString(), artokenparam);
					
				if(insertCount>0)
					insertARtokenstatus=true;
				
		}catch(Exception e){
			logger.error("inside problem save insertarchiveReputizeToken method error{}",e);
			//e.printStackTrace();
		}
		
		return insertARtokenstatus;
	}
	
	public int saveTopic(int positionid,int langid,String description,int objectid){
		boolean saveStatus=false;
		int generatedTopicId=0;
		try{
			namedJdbcTemplate=cusebedaJdbcTemplate.getNamedParameterJdbcTemplate();
			StringBuilder topicInsertQuery = new StringBuilder("insert into auction_details.topics(pos_id,description,lang_id,object_id)").append(" values(:pos_id,:description,:lang_id,:object_id)");
			Map<String, Object> topicSaveMap = new HashMap<String, Object>();
			topicSaveMap.put("pos_id", positionid);
			topicSaveMap.put("description",description);
			topicSaveMap.put("lang_id",langid);
			topicSaveMap.put("object_id",objectid);
			
		
			SqlParameterSource topicSaveParams = new MapSqlParameterSource(topicSaveMap);
			KeyHolder keyHolder = new GeneratedKeyHolder();
			int insertCount=this.namedJdbcTemplate.update(topicInsertQuery.toString(), topicSaveParams,keyHolder);
			generatedTopicId = keyHolder.getKey().intValue();
			if(insertCount>0)
				saveStatus=true;
			
			logger.debug("topic saved status{}",saveStatus);
		}catch(Exception e){
			logger.error("inside problem save saveTopicStates method error{}",e);
		}
		return generatedTopicId;
	}
	
	
	public boolean saveTopicStates(int topicid,int state,String description){
		boolean saveStatus=false;
		try{
			namedJdbcTemplate=cusebedaJdbcTemplate.getNamedParameterJdbcTemplate();
			StringBuilder stateinsertQuery = new StringBuilder("insert into auction_details.topic_states(topic_id,state,description)").append(" values(:topic_id,:state,:description)");
			Map<String, Object> stateSaveMap = new HashMap<String, Object>();
			stateSaveMap.put("topic_id", topicid);
			stateSaveMap.put("state",state);
			stateSaveMap.put("description",description);
			
		
			SqlParameterSource stateSaveParams = new MapSqlParameterSource(stateSaveMap);
			
			int insertCount=this.namedJdbcTemplate.update(stateinsertQuery.toString(), stateSaveParams);
			if(insertCount>0)
				saveStatus=true;
			
		}catch(Exception e){
			logger.error("inside problem save saveTopicStates method error{}",e);
		}
		return saveStatus;
	}
	
	public boolean saveAdditionaTAC(int posid,int langid,String headtitle,String bodytext,int objectid){
		boolean saveStatus=false;
		try{
			namedJdbcTemplate=cusebedaJdbcTemplate.getNamedParameterJdbcTemplate();
			StringBuilder tacinsertQuery = new StringBuilder("insert into auction_details.Widerrufsbelehrung(LangID,PosID,HeadlineText,BodyText,object_id)").append(" values(:LangID,:PosID,:HeadlineText,:BodyText,:object_id)");
			Map<String, Object> tacSaveMap = new HashMap<String, Object>();
			tacSaveMap.put("LangID", langid);
			tacSaveMap.put("PosID",posid);
			tacSaveMap.put("HeadlineText",headtitle);
			tacSaveMap.put("BodyText",bodytext);
			tacSaveMap.put("object_id",objectid);
			
		
			SqlParameterSource tacSaveParams = new MapSqlParameterSource(tacSaveMap);
			
			int insertCount=this.namedJdbcTemplate.update(tacinsertQuery.toString(), tacSaveParams);
			//System.out.println("Entered to save additional data"+insertCount);
			if(insertCount>0)
				saveStatus=true;
		}catch(Exception e){
			logger.error("inside problem save savefaq method error{}",e);
		}
		return saveStatus;
	}
	
	
	public boolean deleteOldAdditionaltac(int objectid,int language){
		boolean deleteOldATAVStatus=false;
		
		try{	
			logger.debug("inside delete old additional tac pics");
			jdbcTemplate=cusebedaJdbcTemplate.getJdbcTemplate();
			String sql="delete from auction_details.Widerrufsbelehrung where object_id=? and LangID="+language;
			int deleteCount=jdbcTemplate.update(sql, new Object[] { objectid });
			logger.debug("No of old additional tac delete"+deleteCount);
			//System.out.println("deleted rows of tac"+deleteCount);
			if(deleteCount>0){
				deleteOldATAVStatus=true;
			}
			
		}catch(Exception e){
			logger.error("Exception occured at deleteoldATAC{}",e);
			//e.printStackTrace();
		}
		
		return deleteOldATAVStatus;
	}
	
	
	public boolean updateTopicTitle(int objectid,int langid,String title,int posid){
		logger.debug("update updateTopicTitle method log objectid"+objectid+"title"+title);
		boolean updateStatus=false;
		try{
			jdbcTemplate=cusebedaJdbcTemplate.getJdbcTemplate();
			StringBuilder topicTitleupdateQuery = new StringBuilder("update auction_details.topics set description='"+title+"' where pos_id="+posid+" and object_id=? and lang_id="+langid);
			
			this.jdbcTemplate.update(topicTitleupdateQuery.toString(), new Object[] { objectid});
			
			updateStatus=true;
			
		}catch(Exception e){
			updateStatus=false;
			logger.error("update updateTopicTitle method error{}",e);
		}
		return updateStatus;
	}
	
	
	public boolean updateTopicText(int topicid,String description){
		logger.debug("update updateTopicText method log topic id"+topicid+" descrption"+description);
		boolean updateStatus=false;
		try{
			jdbcTemplate=cusebedaJdbcTemplate.getJdbcTemplate();
			StringBuilder topicTextupdateQuery = new StringBuilder("update auction_details.topic_states set description='"+description+"' where topic_id=?");
			
			this.jdbcTemplate.update(topicTextupdateQuery.toString(), new Object[] {topicid});
			
			updateStatus=true;
			
		}catch(Exception e){
			updateStatus=false;
			logger.error("update updateTopicText method error{}",e);
		}
		return updateStatus;
	}
	
	public Integer getTACTopicId(int objectid,int langid,int posid){
		int topicid=0;
		
		try{
			jdbcTemplate=cusebedaJdbcTemplate.getJdbcTemplate();
			StringBuilder getTopcidQuery=new StringBuilder("select id from auction_details.topics where pos_id="+posid+" and object_id=? and lang_id="+langid);
			topicid=jdbcTemplate.queryForObject(getTopcidQuery.toString(), new Object[] { objectid},Integer.class);
		}catch(Exception e){
			logger.error("get getTACTopicId method error{}",e);
		}
		
		return topicid;
	}
	
	public List<Map<String,Object>> getAdditionalTACData(int objectid,int language){
		List<Map<String,Object>> additionalTACData=null;
		try{
			jdbcTemplate=cusebedaJdbcTemplate.getJdbcTemplate();
			StringBuilder getAdditionalTACQuery=new StringBuilder("select LangID,PosID,HeadlineText,BodyText from auction_details.Widerrufsbelehrung where object_id=?  and LangID="+language);
			additionalTACData=jdbcTemplate.queryForList(getAdditionalTACQuery.toString(), new Object[] { objectid});
		}catch(Exception e){
			logger.error("get getTACadditional data method error{}",e);
		}
		return additionalTACData;
	}
	
	
	/*select t.id,t.pos_id,t.lang_id,t.description,t.lang_id,s.state,s.description from auction_details.topics t left join auction_details.topic_states s on t.id=s.topic_id where t.object_id=122 and t.lang_id=2*/
	
	public List<Map<String,Object>> getDefaultTACData(int objectid,int language){
		List<Map<String,Object>> defaultTACData=null;
		try{
			jdbcTemplate=cusebedaJdbcTemplate.getJdbcTemplate();
			StringBuilder getDefaultTACQuery=new StringBuilder("select t.id,t.pos_id,t.lang_id,t.description as title,s.state,s.description as des from auction_details.topics t left join auction_details.topic_states s on t.id=s.topic_id where t.object_id=? and t.lang_id="+language+" and t.pos_id in (3,4,5,6) and s.state=1");
			defaultTACData=jdbcTemplate.queryForList(getDefaultTACQuery.toString(), new Object[] { objectid});
		}catch(Exception e){
			logger.error("get getDefaultTAC data method error{}",e);
		}
		return defaultTACData;
	}
	
	
	
	public boolean deleteOldFaq(int objectid,int langid){
		logger.debug("update deleteOldFaq method log objectid id"+objectid+" language id "+langid);
		boolean deleteStatus=false;
		try{	
			
			jdbcTemplate=cusebedaJdbcTemplate.getJdbcTemplate();
			String sql="delete from auction_details.faq_texte where object_id=? and sprache_id="+langid;
			int deleteCount=jdbcTemplate.update(sql, new Object[] { objectid });
			logger.debug("No of Faqs delete"+deleteCount);
			
			
			}catch(Exception e){
				deleteStatus=false;
				logger.error("update updateTopicText method error{}",e);
			}
		return deleteStatus;
		
	}
	
	
	public boolean saveFaqText(int objectid,int langid,String title,String description){
		boolean saveStatus=false;
		try{
			namedJdbcTemplate=cusebedaJdbcTemplate.getNamedParameterJdbcTemplate();
			StringBuilder faqinsertQuery = new StringBuilder("insert into auction_details.faq_texte(fragetext,antworttext,sprache_id,object_id)").append(" values(:fragetext,:antworttext,:sprache_id,:object_id)");
			
			Map<String, Object> faqSaveMap = new HashMap<String, Object>();
			faqSaveMap.put("fragetext", title);
			faqSaveMap.put("antworttext",description);
			faqSaveMap.put("sprache_id",langid);
			faqSaveMap.put("object_id",objectid);
			
			
		
			SqlParameterSource faqSaveParams = new MapSqlParameterSource(faqSaveMap);
			
			int insertCount=this.namedJdbcTemplate.update(faqinsertQuery.toString(), faqSaveParams);
			if(insertCount>0)
				saveStatus=true;
			
		}catch(Exception e){
			logger.error("inside problem save savefaq method error{}",e);
		}
		return saveStatus;
	}
	
	public List<Map<String,Object>> getFaqData(int objectid,int language){
		List<Map<String,Object>> faqData=null;
		try{
			jdbcTemplate=cusebedaJdbcTemplate.getJdbcTemplate();
			StringBuilder getFaqQuery=new StringBuilder("select fragetext,antworttext,sprache_id,object_id from auction_details.faq_texte where object_id=? and sprache_id="+language+" order by id");
			faqData=jdbcTemplate.queryForList(getFaqQuery.toString(), new Object[] { objectid});
		}catch(Exception e){
			logger.error("get getFaqData method error{}",e);
		}
		return faqData;
	}
	
	
	public boolean savePaymentData(int objectid,int siteid,String paypalid,int status,String changer){
		
		System.out.println(paypalid);
		
		boolean saveStatus=false;
		try{
			namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
			StringBuilder paymentinsertQuery = new StringBuilder("insert into ebay.object_paypalDetails(objectid,ebaysiteid,paypal_email,status,lastChanger)").append(" values(:objectid,:ebaysiteid,:paypal_email,:status,:last_changer)");
			
			Map<String, Object> paymentSaveMap = new HashMap<String, Object>();
			paymentSaveMap.put("objectid", objectid);
			paymentSaveMap.put("ebaysiteid",siteid);
			paymentSaveMap.put("paypal_email",paypalid);
			paymentSaveMap.put("status",status);
			paymentSaveMap.put("last_changer",changer);
			
		
			SqlParameterSource paymentSaveParams = new MapSqlParameterSource(paymentSaveMap);
			
			int insertCount=this.namedJdbcTemplate.update(paymentinsertQuery.toString(), paymentSaveParams);
			if(insertCount>0)
				saveStatus=true;
		}catch(Exception e){
			logger.error("inside problem save savepayment method error{}",e);
		}
		return saveStatus;
		
	}
	
	
	public boolean saveObjectLogoPicture(int objectid,String urlpath){
		boolean saveLogoStatus=false;
		try{
			namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
			StringBuilder savelogopicture = new StringBuilder("insert into ebay.object_logo(objectid,image_path)");
			savelogopicture.append(" values(:objectid,:image_path)");
			
			Map<String, Object> logopic = new HashMap<String, Object>();
			logopic.put("objectid",objectid);
			logopic.put("image_path",urlpath);
			
			SqlParameterSource logoPicParamSource = new MapSqlParameterSource(logopic);
			logger.debug("Logo picture save Query"+savelogopicture.toString());
			int logopicSave=this.namedJdbcTemplate.update(savelogopicture.toString(), logoPicParamSource);
			
			if(logopicSave>0){
				saveLogoStatus=true;
				logger.debug("Logo picture saved successfully");
			}
				
		}catch(Exception e){
			saveLogoStatus=false;
			logger.error("Problem occured at Logo picture save");
			//e.printStackTrace();
		}
		return saveLogoStatus;
	}
	//ebay.object_paypalDetails
	
	
	public boolean saveObjectSliderPics(int objectid,String imagepath,int seqid){
		boolean objSliderStatus=false;
		
		try{
			namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
			StringBuilder saveObjSliderPicture = new StringBuilder("insert into ebay.objectslider_pics(objectid,image_path,sequenceId) values(:objectid,:imagepath,:sequenceid)");
			
			
			Map<String, Object> sliderPic = new HashMap<String, Object>();
			sliderPic.put("objectid", objectid);
			sliderPic.put("imagepath", imagepath);
			sliderPic.put("sequenceid", seqid);
			
			SqlParameterSource objSliderPicsParamSource = new MapSqlParameterSource(sliderPic);
			logger.debug("slider picture save Query"+saveObjSliderPicture.toString());
			int sliderPicSave=this.namedJdbcTemplate.update(saveObjSliderPicture.toString(), objSliderPicsParamSource);
			
			if(sliderPicSave>0){
				objSliderStatus=true;
				logger.debug("object slider picture saved successfully");
			}
				
			
		}catch(Exception e){
			objSliderStatus=false;
			logger.error("Problem occured at object slider picture save");
			//e.printStackTrace();
		}
		
		
		return objSliderStatus;
	}
	
	public List<String> getObjectSliderPics(int objectid){
		List<String> objSliderPicsList=null;
			try{
			
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			StringBuilder getSliderPicsDataQuery=new StringBuilder("select image_path from ebay.objectslider_pics where objectid=? order by sequenceId asc");
			objSliderPicsList=jdbcTemplate.queryForList(getSliderPicsDataQuery.toString(), new Object[] { objectid},String.class);
		}catch(Exception e){
			logger.error("get paymentdata method error{}",e);
		}
		
		return objSliderPicsList;
	}
	
	public Map<String,Object> getMapCoordinates(int objectid){
		Map<String,Object> mapCoordinates=null;
		try {
			jdbcTemplate=cusebedaJdbcTemplate.getJdbcTemplate();
			StringBuilder mapCoordinatesQuery = new StringBuilder("select latitude,logititude from geocoordinates.dimension where objectid=? limit 1");
			
			mapCoordinates=jdbcTemplate.queryForMap(mapCoordinatesQuery.toString(), new Object[] { objectid});
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception occured at getting geo map coordinates",e);
		}
		return mapCoordinates;
	}
	
	public boolean saveMapCoordinates(int objectid,String longititude,String latitude,String time){
		
			boolean saveStatus=false;
			try{
				namedJdbcTemplate=cusebedaJdbcTemplate.getNamedParameterJdbcTemplate();
				StringBuilder mapinsertQuery = new StringBuilder("insert into geocoordinates.dimension(objectid,latitude,logititude,inserted_time)").append(" values(:objectid,:latitude,:logititude,:inserted_time)");
				Map<String, Object> mapSaveMap = new HashMap<String, Object>();
				mapSaveMap.put("objectid", objectid);
				mapSaveMap.put("latitude",latitude);
				mapSaveMap.put("logititude",longititude);
				mapSaveMap.put("inserted_time",time);
				
			
				SqlParameterSource mapSaveParams = new MapSqlParameterSource(mapSaveMap);
				
				int insertCount=this.namedJdbcTemplate.update(mapinsertQuery.toString(), mapSaveParams);
				
				if(insertCount>0)
					saveStatus=true;
				
			}catch(Exception e){
				logger.error("inside problem save save map coordinates method error{}",e);
			}
			return saveStatus;
		
	}
	
	public boolean updateMapCoordinates(int objectid,String longititude,String latitude){
		logger.debug("update map coordinates method log objectid"+objectid+"longitude"+longititude+"latitude"+latitude);
		boolean saveStatus=false;
		try{
			jdbcTemplate=cusebedaJdbcTemplate.getJdbcTemplate();
			StringBuilder mapinsertQuery = new StringBuilder("update geocoordinates.dimension set latitude="+latitude+" , logititude="+longititude+" where objectid=?");
			
			this.jdbcTemplate.update(mapinsertQuery.toString(), new Object[] { objectid});
			
			saveStatus=true;
		}catch(Exception e){
			logger.error("update map coordinates method error{}",e);
		}
		return saveStatus;
	
}
	
	//SELECT ebaydaten.google_maps_used FROM ebay.ebaydaten WHERE ebaydaten.cusebeda_objekt_id =?  LIMIT 1
	
	public boolean updateMapZoomLevel(int objectid,int zoomlevel){
		logger.debug("update map ZoomLevel");
		boolean saveStatus=false;
		try{
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			StringBuilder updateZoomLevelQuery = new StringBuilder("update ebay.ebaydaten set ebaydaten.google_maps_used="+zoomlevel+" WHERE ebaydaten.cusebeda_objekt_id =?");
			
			this.jdbcTemplate.update(updateZoomLevelQuery.toString(), new Object[] { objectid});
			
			saveStatus=true;
		}catch(Exception e){
			logger.error("update map zoom level method error{}",e);
		}
		return saveStatus;
	
	}
	
	public int getZoomLevel(int objectid){
		int zoomLevel=0;
		try{
			
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			StringBuilder getZoomLevelQuery=new StringBuilder("SELECT ebaydaten.google_maps_used FROM ebay.ebaydaten WHERE ebaydaten.cusebeda_objekt_id =? LIMIT 1");
			zoomLevel=jdbcTemplate.queryForObject(getZoomLevelQuery.toString(), new Object[] { objectid},Integer.class);
		}catch(Exception e){
			logger.error("get zoom level method error{}",e);
		}
		
		
		return zoomLevel;
		
	}
	
	
	//ebay.objekt_x_ebayspecifications
	/*     $sqlGMZoom = "SELECT ebaydaten.google_maps_used 
                      FROM ebay.ebaydaten
                      WHERE ebaydaten.cusebeda_objekt_id = '".$this->ObjectID."'
                      LIMIT 1"       */
	
	
	public boolean updatePaymentData(int objectid,String paymentid,int siteid){
		logger.debug("update payment data method log objectid"+objectid+"payment id"+paymentid+"siteid"+siteid);
		boolean saveStatus=false;
		try{
			
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			StringBuilder paymentupdateQuery = new StringBuilder("update ebay.object_paypalDetails set paypal_email='"+paymentid+"' where objectid=?  and ebaysiteid="+siteid);
			
			this.jdbcTemplate.update(paymentupdateQuery.toString(), new Object[] { objectid});
			
			saveStatus=true;
			
		}catch(Exception e){
			saveStatus=false;
			logger.error("update paymentdata method error{}",e);
		}
		return saveStatus;
	
}
	
	public boolean updateTotalPaymentData(int objectid){
		boolean saveStatus=false;
		try{
			
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			StringBuilder paymentTotalDataupdateQuery = new StringBuilder("update ebay.object_paypalDetails set paypal_email='' where objectid=? ");
			
			this.jdbcTemplate.update(paymentTotalDataupdateQuery.toString(), new Object[] { objectid});
			
			saveStatus=true;
			
		}catch(Exception e){
			saveStatus=false;
			logger.error("update paymentdata method error{}",e);
		}
		return saveStatus;
	}
	
	public List<Map<String,Object>> getPaymentData(int objectid){
		
		List<Map<String,Object>> objectPaymentData=null;
		
		try{
			
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			StringBuilder getPaymentDataQuery=new StringBuilder("select ebaysiteid,paypal_email,status from ebay.object_paypalDetails where objectid=?");
			objectPaymentData=jdbcTemplate.queryForList(getPaymentDataQuery.toString(), new Object[] { objectid});
		}catch(Exception e){
			logger.error("get paymentdata method error{}",e);
		}
		
		return objectPaymentData;
	}
	
	public boolean deleteOldSliderPics(int objectid){
		boolean deleteOldSliderStatus=false;
		
		try{	
			logger.debug("inside delete old object slider pics");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="delete from ebay.objectslider_pics where objectid=?";
			int deleteCount=jdbcTemplate.update(sql, new Object[] { objectid });
			logger.debug("No of Amenities delete"+deleteCount);
			
			
				deleteOldSliderStatus=true;
			
			
		}catch(Exception e){
			logger.error("Exception occured at deletesliderpics{}",e);
			//e.printStackTrace();
		}
		
		return deleteOldSliderStatus;
	}
	
	
	public boolean updateObjectLogoPic(int objectid,String imagepath){
		logger.debug("update objectlogo method log objectid"+objectid+"image path"+imagepath);
		boolean updateStatus=false;
		try{
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			StringBuilder objectLogoupdateQuery = new StringBuilder("update ebay.object_logo set image_path='"+imagepath+"' where objectid=?");
			
			this.jdbcTemplate.update(objectLogoupdateQuery.toString(), new Object[] { objectid});
			
			updateStatus=true;
			
		}catch(Exception e){
			updateStatus=false;
			logger.error("update objectlogo method error{}",e);
		}
		return updateStatus;
	}
	
	public String getOLogoImagePath(int objectid){
		String logoImageUrl=null;
		
		try{
			
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			StringBuilder getOLogopathQuery=new StringBuilder("select image_path from ebay.object_logo where objectid=?");
			logoImageUrl=jdbcTemplate.queryForObject(getOLogopathQuery.toString(), new Object[] { objectid},String.class);
		}catch(Exception e){
			logger.error("get getOLogoImagePath method error{}",e);
		}
		
		return logoImageUrl;
	}
	
	public boolean checkSiteId(int siteid){
		boolean siteIdStatus=false;
		
		try{
		logger.debug("insid DAO checking siteid"+siteid);
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		String sql="SELECT count(id) FROM ebay.ebaysite  WHERE id=? and status = 1 ";
		int countOfSiteids =jdbcTemplate.queryForObject(sql, new Object[] { siteid },Integer.class);
		
		if(countOfSiteids>0)
			siteIdStatus=true;
		
		}catch(Exception e){
			siteIdStatus=false;
			logger.debug("Problem occured in checking site id");
			e.printStackTrace();
			
		}
		return siteIdStatus;
	}

	public boolean checkFutureOffersLiveSiteId(String siteid,int objectid){
		boolean siteIdStatus=false;
		
		try{
		logger.debug("insid DAOcheck FutureOffersLiveSiteId"+siteid);
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		String sql="select count(id) from ebay.auktion where cusebeda_objekt_id=? and status in (1,0) and ebaysiteid="+siteid;
		int countOfOffers =jdbcTemplate.queryForObject(sql, new Object[] { objectid },Integer.class);
		
		if(countOfOffers>0)
			siteIdStatus=true;
		
		}catch(Exception e){
			siteIdStatus=false;
			logger.debug("Problem occured in checking site id");
			e.printStackTrace();
			
		}
		return siteIdStatus;
	}
	
	public boolean checkFutureOffersLiveOffersObject(int objectid){
			boolean offersStatus=false;
		
		try{
		logger.debug("insid DAOcheck FutureOffersLiveObject"+objectid);
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		String sql="select count(id) from ebay.auktion where cusebeda_objekt_id=? and status in (1,0)";
		int countOfOffers =jdbcTemplate.queryForObject(sql, new Object[] { objectid },Integer.class);
		
		if(countOfOffers>0)
			offersStatus=true;
		
		}catch(Exception e){
			offersStatus=false;
			logger.debug("Problem occured in checking offers");
			e.printStackTrace();
			
		}
		return offersStatus;
	}
	
	
	//checkSiteId
}
