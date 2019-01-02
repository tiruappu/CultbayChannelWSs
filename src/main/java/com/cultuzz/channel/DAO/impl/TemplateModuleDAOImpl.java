package com.cultuzz.channel.DAO.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.cultuzz.channel.DAO.TemplateModuleDAO;
import com.cultuzz.channel.XMLpojo.AdditionalOptionsType;
import com.cultuzz.channel.XMLpojo.AmenitiesType;
import com.cultuzz.channel.XMLpojo.DescriptionPicturesType;
import com.cultuzz.channel.XMLpojo.NameValueListType;
import com.cultuzz.channel.XMLpojo.PictureDetailsType;
import com.cultuzz.channel.XMLpojo.StoreCategoriesType;
import com.cultuzz.channel.XMLpojo.TemplateCreationRQ;
import com.cultuzz.channel.XMLpojo.TemplateEditRQ;
import com.cultuzz.channel.jdbcTemplate.JDBCTemplate;
import com.cultuzz.channel.util.CommonValidations;



@Repository
public class TemplateModuleDAOImpl implements TemplateModuleDAO{
	
	@Autowired
	@Qualifier("ebayTemplate")
	private JDBCTemplate ebayJdbcTemplate;
	
	@Autowired
	@Qualifier("cusebedaTemplate")
	private JDBCTemplate cusebedaJdbcTemplate;
	
	@Autowired
	CommonValidations commonValidations;
	
	private JdbcTemplate jdbcTemplate;
	
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	private static final Logger logger = LoggerFactory
			.getLogger(TemplateModuleDAOImpl.class);
	
	//java.util.Date date = new java.util.Date();
	
	List<String> allowedStrings=new ArrayList<String>();
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
	
	public boolean checkDesignTemplateId(int templateId){
		boolean templateIdStatus=false;
		try{
		logger.debug("insid DAO checking templateid"+templateId);
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		String sql="select count(template.id) from ebay.template where template.id=? and template.ActiveStatus=1 ";
		int countofTemplateids =jdbcTemplate.queryForObject(sql, new Object[] { templateId },Integer.class);
		
		
		
		if(countofTemplateids>0)
			templateIdStatus=true;
		
		}catch(Exception e){
			templateIdStatus=false;
			logger.debug("Problem occured in checking design template id");
			e.printStackTrace();
		}
		
		return templateIdStatus;
		
		
	}
	
	public boolean checkObjectId(int objectId){
		
		boolean objectidFlag=false;
		try{
			
			jdbcTemplate=cusebedaJdbcTemplate.getJdbcTemplate();
			String sql="select count(id) from  cusebeda.objekt where id=?";
			int objectidscount =jdbcTemplate.queryForObject(sql, new Object[] {objectId},Integer.class);
			
			logger.debug("Checking objectid count"+objectidscount);
			if(objectidscount>0)
				objectidFlag=true;
			
		}catch(Exception e){
			objectidFlag=false;
			logger.debug("Problem occured in checking object id");
			e.printStackTrace();
		}
		
		return objectidFlag;
	}
	
	public boolean checkproductId(int objectid,int productId){
		logger.debug("insid DAO checking productid"+productId+"===objectid"+objectid);
		boolean productIdstatus=false;
		try{
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		String sql="SELECT count(p.id) as id FROM ebay_product.product p WHERE p.id=? and p.objectId ="+objectid;
		int productidscount =jdbcTemplate.queryForObject(sql, new Object[] {productId},Integer.class);
		
		logger.debug("productids count"+productidscount);
		
		
		if(productidscount>0)
			productIdstatus=true;
		
		}catch(Exception e){
			productIdstatus=false;
			logger.debug("Problem occured in checking product id");
			e.printStackTrace();
		}
		return productIdstatus;
		
		
	}
	
	public boolean checkPrimaryCategory(int primaryCategoryId,int objectid,int ebaysiteid){
		logger.debug("inside DAO checking primaryCategoryid"+primaryCategoryId);
		boolean primaryCategoryIdStatus=false;
		try{
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		String sql="SELECT count(DISTINCT kunden_kategorien.ebaykategorie2_id) AS kat_id  FROM ebay.kunden_kategorien, ebay.kunden_x_kategorie   WHERE kunden_kategorien.ebaykategorie2_id=? AND kunden_kategorien.ebaysite_id ="+ebaysiteid+"  AND kunden_kategorien.id=ebay.kunden_x_kategorie.kunden_kategorie_id ";
		int primaryCategoryidsCount =jdbcTemplate.queryForObject(sql, new Object[] { primaryCategoryId },Integer.class);
		
		
		
		if(primaryCategoryidsCount>0)
			primaryCategoryIdStatus=true;
		
		}catch(Exception e){
			primaryCategoryIdStatus=false;
			logger.debug("Problem occured in checking primary category id");
			e.printStackTrace();
		}
		
		return primaryCategoryIdStatus;
		
	}
	
	public boolean checkSecondaryCategory(int secondaryCategoryId,int objectid,int ebaysiteid){
		logger.debug("inside checking secondary category");
		boolean secondaryCategoryIdStatus=false;
		try{
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		String sql="SELECT count( DISTINCT kunden_kategorien.ebaykategorie2_id) AS kat_id  FROM ebay.kunden_kategorien, ebay.kunden_x_kategorie   WHERE kunden_kategorien.ebaykategorie2_id=? AND kunden_kategorien.ebaysite_id = "+ebaysiteid+"  AND kunden_kategorien.id=ebay.kunden_x_kategorie.kunden_kategorie_id ";
		int secondaryCategoryidsCount =jdbcTemplate.queryForObject(sql, new Object[] { secondaryCategoryId },Integer.class);
		if(secondaryCategoryidsCount>0)
			secondaryCategoryIdStatus=true;
		
		}catch(Exception e){
			secondaryCategoryIdStatus=false;
			logger.debug("Problem occured in checking secondary category id");
			e.printStackTrace();
		}
		return secondaryCategoryIdStatus;
		
	}
	
	public boolean checkStoreCategoryId(long storeCategoryId,int shopObjectid){
		boolean storeCategoryIdStatus=false;
		logger.debug("This is checking primaryCategoryid"+storeCategoryId);
		try{
		jdbcTemplate=cusebedaJdbcTemplate.getJdbcTemplate();
		String sql="select count(*) from eBayShops.Categories where CategoryID=? and ObjectID="+shopObjectid;
		int storeCategoryidsCount =jdbcTemplate.queryForObject(sql, new Object[] { storeCategoryId },Integer.class);
		
		if(storeCategoryidsCount>0)
			storeCategoryIdStatus=true;
		
		}catch(Exception e){
			storeCategoryIdStatus=false;
			logger.debug("Problem occured in checking store category id");
			e.printStackTrace();
		}
		
		if(storeCategoryIdStatus)
		storeCategoryIdStatus=checkChildStoreCategoryId(storeCategoryId);
		
		return storeCategoryIdStatus;
	}
	
	public boolean checkChildStoreCategoryId(long storeChildCategoryid){
		boolean storeChildCategoryIdStatus=false;
		logger.debug("inside secondary store category");
		try{
		jdbcTemplate=cusebedaJdbcTemplate.getJdbcTemplate();
		String sql="select count(*) from eBayShops.Categories where ParentCategoryID=?";
		int storeChildCategoryidsCount =jdbcTemplate.queryForObject(sql, new Object[] { storeChildCategoryid },Integer.class);
		
		if(storeChildCategoryidsCount==0)
			storeChildCategoryIdStatus=true;
		
		}catch(Exception e){
			storeChildCategoryIdStatus=false;
			logger.debug("Problem occured in checking child category id");
			e.printStackTrace();
		}
		
		return storeChildCategoryIdStatus;
		
	}
	
	
	public boolean checkTypeOfRoom(int typeOfRoomid){
		
		boolean roomTypeflag=false;
		logger.debug("inside checking typeofroom id");
		try{
			jdbcTemplate=cusebedaJdbcTemplate.getJdbcTemplate();
			String sql="SELECT count(id)  FROM hofesoda.zimmerart  WHERE id=? and cultbay_kriterium = 1";
			int typeOfRoomidsCount =jdbcTemplate.queryForObject(sql, new Object[] { typeOfRoomid },Integer.class);
			
			if(typeOfRoomidsCount>0)
				roomTypeflag=true;
			
			logger.debug("inside checking typeofroom id completed");
		}catch(Exception e){
			roomTypeflag=false;
			logger.debug("Problem occured in checking type of room id");
			e.printStackTrace();
		}
		return roomTypeflag;
	}
	
	public boolean checkCateringType(int cateringTypeid){
		boolean cateringTypeflag=false;
		
		try{
			logger.debug("inside checking catering type id");
			jdbcTemplate=cusebedaJdbcTemplate.getJdbcTemplate();
			String sql="SELECT count(id) FROM hofesoda.verpflegung where id=?";
			int typeOfCateringidsCount =jdbcTemplate.queryForObject(sql, new Object[] { cateringTypeid },Integer.class);
			
			if(typeOfCateringidsCount>0)
				cateringTypeflag=true;
			
		}catch(Exception e){
			cateringTypeflag=false;
			logger.debug("Problem occured in checking catering id");
			e.printStackTrace();
		}
		
		return cateringTypeflag;
	}
	
	
	public boolean checkTemplateId(int templateid,int objectid){
		boolean templateidFlag=false;
		
		try{
			logger.debug("inside checking template id");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String templateidsql="SELECT count(id) FROM ebay.vorlage where id=? and status=1 and cusebeda_objekt_id="+objectid;
			int templateidsCount =jdbcTemplate.queryForObject(templateidsql, new Object[] { templateid },Integer.class);
			
			if(templateidsCount>0)
				templateidFlag=true;
			
		}catch(Exception e){
			templateidFlag=false;
			logger.debug("Problem occured in checking template id related object");
			e.printStackTrace();
		}
		
		return templateidFlag;
	}
	
	public boolean checkTemplateIdObj(int templateid,int objectid){
		boolean templateidFlag=false;
		
		try{
			logger.debug("inside checking template id");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String templateidsql="SELECT count(id) FROM ebay.vorlage where id=? and status=1 and cusebeda_objekt_id="+objectid;
			int templateidsCount =jdbcTemplate.queryForObject(templateidsql, new Object[] { templateid },Integer.class);
			
			if(templateidsCount>0)
				templateidFlag=true;
			
		}catch(Exception e){
			templateidFlag=false;
			logger.debug("Problem occured in checking template id related obj");
			e.printStackTrace();
		}
		
		return templateidFlag;
	}
	
	public int saveVorlageData(TemplateCreationRQ templateCreationRQ){
		//Timestamp timestamp = new Timestamp(date.getTime());
		
		String timestamp=commonValidations.getCurrentTimeStamp();
		logger.debug("This is save vorlage data Time is==="+timestamp);
		namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
		
		Long id = 0l;

			StringBuffer vorlageDataSql = new StringBuffer("insert into ebay.vorlage(ebaysiteid,bezeichnung,cusebeda_objekt_id,template_id,erstellt,geaendert,status,ebayoptionen,ebaybetreffzeile,untertitel,ebaykategorieid1,ebaykategorieid2,ueberschrift,ueberschrift2,text,text2,startpreis,ebaysofortkauf,arrangement_id,buchungsschluessel_gueltigkeit,auctionMasterTypeID,third_party_uniqueCodes,retailprice)");

			vorlageDataSql.append(" values(:ebaysiteId,:templateName,:objectid,:templateid,:createdTime,:updatedTime,:status,:ebayoptionen,:ebaybetreffzeile,:untertitel,:ebaykategorieid1,:ebaykategorieid2,:ueberschrift,:ueberschrift2,:text,:text2,:startpreis,:ebaysofortkauf,:arrangement_id,:buchungsschluessel_gueltigkeit,:auctionMasterTypeID,:third_party_uniqueCodes,:retailprice)");

			

			Map<String, Object> bind = new HashMap<String, Object>();
			bind.put("ebaysiteId", templateCreationRQ.getSiteId());
			bind.put("templateName",templateCreationRQ.getTemplateName());
			//bind.put("artikel_id","");
			bind.put("objectid",templateCreationRQ.getObjectId());
			bind.put("templateid", templateCreationRQ.getDesignTemplate());
			bind.put("createdTime",timestamp);
			bind.put("updatedTime", timestamp);
			bind.put("status",1);
			bind.put("retailprice", templateCreationRQ.getRetailPrice());
			
			
			AdditionalOptionsType additionalOptions=templateCreationRQ.getAdditionalOptions();
			//pending code for additional options
			//Highlight 1
			//Bold 2
			//Top offer (Gallery) 4
			//Gallery Picture 8
			//Top offer (Homepage) 16
			
			//additionalOptions.getOptionName();
			List<String> ebyoptionsList=new ArrayList<String>();
			if(additionalOptions!=null)		
			     ebyoptionsList= additionalOptions.getOptionValue();
			
			int optionentotalvalue=8;
			
			for(String optionvalue:ebyoptionsList){
				if(Integer.parseInt(optionvalue)!=8)
				optionentotalvalue+=Integer.parseInt(optionvalue);
			}
			
			bind.put("ebayoptionen",optionentotalvalue);
			bind.put("ebaybetreffzeile", templateCreationRQ.getOfferTitle());
			if(templateCreationRQ.isSetOfferSubTitle())
			bind.put("untertitel", templateCreationRQ.getOfferSubTitle());
			else
				bind.put("untertitel", null);
			bind.put("ebaykategorieid1",templateCreationRQ.getCategories().getPrimaryCategoryDetails().getPrimaryCategoryId());
			if(templateCreationRQ.getCategories().isSetSecondaryCategoryDetails() && templateCreationRQ.getCategories().getSecondaryCategoryDetails().isSetSecondaryCategoryId())
			bind.put("ebaykategorieid2",templateCreationRQ.getCategories().getSecondaryCategoryDetails().getSecondaryCategoryId());
			else
				bind.put("ebaykategorieid2",0);
			
			bind.put("ueberschrift", templateCreationRQ.getDescription().getTitle());
			bind.put("ueberschrift2",templateCreationRQ.getDescription().getSubTitle());
			
			String offertext = "";
			 offertext = templateCreationRQ.getDescription().getOfferText();
			 if(offertext!=null && offertext!=""){
			 offertext  = offertext.replace("<![CDATA[","");
			 int start = offertext.lastIndexOf("]]>");
			 offertext = offertext.substring(0, start);
			 }
			bind.put("text", offertext);
			if(templateCreationRQ.getDescription().isSetOfferAdditionalText())
			bind.put("text2", templateCreationRQ.getDescription().getOfferAdditionalText());
			else
				bind.put("text2", null);
			
			/*if(templateCreationRQ.isSetPrice())
			bind.put("startpreis", templateCreationRQ.getPrice());
			bind.put("ebaysofortkauf",0); */
			if(templateCreationRQ.getListingType().compareToIgnoreCase("Auction")==0){
				bind.put("auctionMasterTypeID", 1);
				bind.put("startpreis", templateCreationRQ.getPrice());
				bind.put("ebaysofortkauf", 0);
			}else if(templateCreationRQ.isSetShopObjectId() && Integer.parseInt(templateCreationRQ.getShopObjectId())>0){
					
					bind.put("auctionMasterTypeID",4);
					bind.put("startpreis", templateCreationRQ.getPrice());
					bind.put("ebaysofortkauf", 0);
				
			}else{
				
				bind.put("auctionMasterTypeID", 1);
				bind.put("ebaysofortkauf", templateCreationRQ.getPrice());
				bind.put("startpreis", 0);
			}
			
			/*if(!templateCreationRQ.isSetShopObjectId())
			bind.put("startpreis", templateCreationRQ.getPrice());
			else
				bind.put("startpreis", 0);
			
			if(templateCreationRQ.isSetShopObjectId())
			bind.put("ebaysofortkauf",templateCreationRQ.getPrice());
			else
				bind.put("ebaysofortkauf",0);*/
			
			if(templateCreationRQ.isSetProductId())
			bind.put("arrangement_id", templateCreationRQ.getProductId());
			else
				bind.put("arrangement_id", 0);
			
			bind.put("buchungsschluessel_gueltigkeit", 14);
			//bind.put("directbooking", "");
			//bind.put("reservePrice","");
			
		/*	if(templateCreationRQ.isSetShopObjectId())
			bind.put("auctionMasterTypeID","4");
			else
			bind.put("auctionMasterTypeID","1");*/
			
			//bind.put("auktions_texte_id", "");
			bind.put("retailprice",templateCreationRQ.getRetailPrice());
			bind.put("third_party_uniqueCodes", 0);
		
		
			
			SqlParameterSource paramSource = new MapSqlParameterSource(bind);
			KeyHolder keyHolder = new GeneratedKeyHolder();
			
			logger.debug("Query of vorlage insertion"+vorlageDataSql.toString());
			
			this.namedJdbcTemplate.update(vorlageDataSql.toString(), paramSource,keyHolder);
			id = keyHolder.getKey().longValue();
			return id.intValue();
		
		
	}
	
	public boolean saveVorlageArrangementData(int vorlageid,TemplateCreationRQ templateCreationRQ){
		
		logger.debug("this is vorlage arrangement");
		boolean vorlageArrangementFlag=false;
		try{
		namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
		
		//Long id = 0l;

			StringBuffer vorlageArrangementSql = new StringBuffer("insert into ebay.vorlagen_arrangement(vorlage_id,personen,naechte,hofesoda_zimmerart_id,hofesoda_verpflegung_id)");

			vorlageArrangementSql.append(" values(:vorlageid,:personen,:naechte,:hofesoda_zimmerart_id,:hofesoda_verpflegung_id)");
			

			Map<String, Object> bind = new HashMap<String, Object>();
			bind.put("vorlageid",vorlageid);
			bind.put("personen",templateCreationRQ.getProperties().getPersons());
			bind.put("naechte",templateCreationRQ.getProperties().getNights());
			bind.put("hofesoda_zimmerart_id",templateCreationRQ.getProperties().getTypeOfRoom());
			bind.put("hofesoda_verpflegung_id", templateCreationRQ.getProperties().getCateringType());
			
			logger.debug("vorlage arrangement insertion"+vorlageArrangementSql.toString());
			SqlParameterSource paramSource = new MapSqlParameterSource(bind);
			//KeyHolder keyHolder = new GeneratedKeyHolder();

			int var=this.namedJdbcTemplate.update(vorlageArrangementSql.toString(), paramSource);
			//id = keyHolder.getKey().longValue();
			//id.intValue();
			if(var>0)
			vorlageArrangementFlag=true;
		}catch(Exception e){
			vorlageArrangementFlag=false;
			logger.debug("problem occured vorlage arrangement insertion");
			e.printStackTrace();
		}
		return vorlageArrangementFlag;
	}
	
	public boolean saveVocherData(int vorlageid,TemplateCreationRQ templateCreationRQ){
		
		boolean voucherFlag=false;
		logger.debug("save voucher data");
		try{
		
			namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
		
		//Long id = 0l;

			StringBuffer voucherDataSql = new StringBuffer("insert into ebay.vorlage_gutscheine(vorlage_id,gutschein_text,gueltigkeit_text,gueltigkeit)");

			voucherDataSql.append(" values(:vorlageid,:gutschein_text,:gueltigkeit_text,:gueltigkeit)");
			

			Map<String, Object> bind = new HashMap<String, Object>();
			bind.put("vorlageid",vorlageid);
			String vouchertext = ""; 
			vouchertext = templateCreationRQ.getDescription().getVoucherText();
			if(vouchertext!=null && vouchertext!=""){
				vouchertext = vouchertext.replace("<![CDATA[","");
				int start = vouchertext.lastIndexOf("]]>");
				vouchertext = vouchertext.substring(0, start);
			}
			
			bind.put("gutschein_text",vouchertext);
			bind.put("gueltigkeit_text",templateCreationRQ.getDescription().getVoucherValidityText());
			bind.put("gueltigkeit",templateCreationRQ.getVoucherValidity());
			
			
			
			SqlParameterSource paramSource = new MapSqlParameterSource(bind);
			//KeyHolder keyHolder = new GeneratedKeyHolder();
			logger.debug("save voucher data query"+voucherDataSql.toString());
			int vocherdatasaved=this.namedJdbcTemplate.update(voucherDataSql.toString(), paramSource);
			
			if(vocherdatasaved>0){
				voucherFlag=true;
			}
			
		}catch(Exception e){
			voucherFlag=false;
			logger.debug("problem occured voucher data insertion");
			e.printStackTrace();
		}
		
		return voucherFlag;
		//return id.intValue();
	}
	
	public boolean saveHeaderFooterData(int vorlageid,String categoryid,String headerurl,String footerurl){
		
		boolean hfFlag=false;
		logger.debug("save header footer data"+categoryid);
		System.out.println("save header footer data "+categoryid);
		try{
		
			namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			
			List<Map<String, Object>> headerfooterurls = null;
			System.out.println("category id is boolean check"+categoryid.equalsIgnoreCase("new"));
			
			System.out.println("headeeeeee1111111 "+headerurl+" foteeeeee111111"+footerurl);
		//Long id = 0l;
			/*if(categoryid !=null && !(categoryid.equalsIgnoreCase("new"))){
				
				System.out.println("categorid not equal to null");
				String hfurlsql = "select header_url,footer_url from ebay.template_categories where id ="+categoryid;
				logger.debug("sql query for voucherIds :{}",hfurlsql);
				System.out.println("sql query for voucherIds :{}"+hfurlsql);
				headerfooterurls = jdbcTemplate.queryForList(hfurlsql.toString());
				
				if(headerfooterurls!=null && !(headerfooterurls.isEmpty())){
					System.out.println("header and footer not equal to null");
					for(Map<String,Object> hfurlsmap : headerfooterurls){
						
						headerurl = String.valueOf(hfurlsmap.get("header_url"));
						footerurl = String.valueOf(hfurlsmap.get("footer_url"));
					}
				} 

			}*/
			
			 if(categoryid.equalsIgnoreCase("new")){
				 categoryid = "0"; 
			 }
			
			System.out.println("headeeeeee1111111 "+headerurl+" foteeeeee111111"+footerurl);
			StringBuffer hfDataSql = new StringBuffer("insert into ebay.vorlage_x_templatecategories(vorlage_id,categorie_id,header_url,footer_url)");

			hfDataSql.append(" values(:vorlageid,:categoryid,:headerurl,:footerurl)");
			 
			
			Map<String, Object> bind = new HashMap<String, Object>();
			bind.put("vorlageid",vorlageid);
			bind.put("categoryid",categoryid);
			bind.put("headerurl",headerurl);
			bind.put("footerurl",footerurl);
			
			
			SqlParameterSource paramSource = new MapSqlParameterSource(bind);
			//KeyHolder keyHolder = new GeneratedKeyHolder();
			logger.debug("save hf data query"+hfDataSql.toString());
			System.out.println("save hf data query"+hfDataSql.toString());
			int hfdatasaved=this.namedJdbcTemplate.update(hfDataSql.toString(), paramSource);
			
			if(hfdatasaved>0){
				hfFlag=true;
			}
			
			
			
		}catch(Exception e){
			hfFlag=false;
			logger.debug("problem occured voucher data insertion");
			e.printStackTrace();
		}
		
		return hfFlag;
		//return id.intValue();
	}
	
	public boolean deleteTemplate(int templateid){
		
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		boolean deleteTemplateStatus=false;
		try{
		String updateAuktionSql="UPDATE ebay.vorlage SET vorlage.status = 2 WHERE vorlage.id = ?";
		int auktionTableUpdate =jdbcTemplate.update(updateAuktionSql, new Object[] { templateid });
		
		deleteTemplateStatus=false;
		
		if(auktionTableUpdate>0)
			deleteTemplateStatus=true;
		}catch(Exception e){
			logger.debug("problem occured template delete");
			e.printStackTrace();
		}
		return deleteTemplateStatus;
		
		
		
	}
	
	public int saveItemCategories(int vorlageid,int categoryId,int level){
		
		namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
		logger.debug("save itemCategories");
		Long id = 0l;
		try{
			StringBuffer sql = new StringBuffer("insert into ebay.vorlage_x_itemSpecifics(auctionMasterID,categoryID,categoryLevel)");

			sql.append(" values(:vorlageId,:categoryid,:categorylevel)");
			
			Map<String, Object> bind = new HashMap<String, Object>();
			bind.put("vorlageId", vorlageid);
			bind.put("categoryid",categoryId);
			bind.put("categorylevel", level);
			
			
			SqlParameterSource paramSource = new MapSqlParameterSource(bind);
			KeyHolder keyHolder = new GeneratedKeyHolder();

			this.namedJdbcTemplate.update(sql.toString(), paramSource,keyHolder);
			id = keyHolder.getKey().longValue();
			logger.debug("Item spec id "+id);
		}catch(Exception e){
			logger.debug("problem occured at Item spec saving");
			e.printStackTrace();
		}
		return id.intValue();
		
		
	}
	
	public int saveItemSpecifics(int itemspecId,List<NameValueListType> namevalueList){
		
		
		logger.debug("this is save itemspecifics");
		 long specnameId =0l ;
		 int specNameListCounter=0;
		 try{
			 
		 for(NameValueListType namevalues:namevalueList){
			 namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
			StringBuffer insertSpecnameQuery = new StringBuffer("insert into ebay.vorlage_x_itemSpecName(item_specID,name,elementType)");

			insertSpecnameQuery.append(" values(:specId,:specName,:elementType)");

			Map<String, Object> bind = new HashMap<String, Object>();
			bind.put("specId",itemspecId);
			bind.put("specName",namevalues.getName());
			bind.put("elementType", 1);
			
			
			SqlParameterSource paramSource = new MapSqlParameterSource(bind);
			KeyHolder keyHolder = new GeneratedKeyHolder();

			this.namedJdbcTemplate.update(insertSpecnameQuery.toString(), paramSource,keyHolder);
			specnameId = keyHolder.getKey().longValue();
			specNameListCounter++;
		boolean specValueSave=specValue(specnameId,namevalues.getValue(),namevalues.getSValue());
		logger.debug("this is save itemspecifics"+specValueSave);
		
		 }
		 
		 logger.debug("this is save itemspecifics");
		 
		 if(specNameListCounter==namevalueList.size())
			 logger.debug("this is save itemspecifics are saved successfully");
		 else
			 logger.debug("itemspecifics are failed");
		 
		 }catch(Exception e){
			 logger.debug("Problem occured at item specifics saving");
			 e.printStackTrace();
			 			 
		 }
			return 1;
		
		
	}
	
	public boolean saveItemSpecificNamesValues(int itemspecId,NameValueListType namevalueList){
		boolean specifics=false;
		
		
		return specifics;
	}

	
	public boolean specValue(long specid,List<String> value,String sValue){
		//Timestamp timestamp = new Timestamp(date.getTime());
		String timestamp=commonValidations.getCurrentTimeStamp();
		boolean specStatus=false;
		logger.debug("save specifics value");
		 //long specvalueId =0l ;
		int specsCounter=0;
		 try{
			 for(String specvalue:value){
				 namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
			StringBuffer insertSpecValueQuery = new StringBuffer("insert into ebay.vorlage_x_itemSpecValue(item_specnameID,value,sValue,inserted_time)");

			insertSpecValueQuery.append(" values(:specNameId,:specValue,:specSubValue,:currentTime)");
			
			Map<String, Object> bind = new HashMap<String, Object>();
			bind.put("specNameId", specid);
			
			bind.put("specValue",specvalue);
			
			if(sValue!=null){
				if(!sValue.isEmpty())
					bind.put("specSubValue", sValue);
				else
					bind.put("specSubValue", "");
			}else
			bind.put("specSubValue", "");	
			
			bind.put("currentTime",timestamp);
		
			SqlParameterSource paramSource = new MapSqlParameterSource(bind);
			//KeyHolder keyHolder = new GeneratedKeyHolder();

			this.namedJdbcTemplate.update(insertSpecValueQuery.toString(), paramSource);
			//specvalueId =  keyHolder.getKey().longValue();
			specsCounter++;
			 }
			 
			 if(value.size()==specsCounter)
				 specStatus=true;
				 
		 }catch(Exception e){
			 specStatus=false;
			 logger.debug("Problem occured at itemspecifics values saving");
			 e.printStackTrace();
		 }
		
		return specStatus;
	}
	
	public boolean savePictureDetails(int vorlageid,PictureDetailsType pictures,int objectid){
		
		boolean picturesFlag=false;
		logger.debug("save picture details");
		
		
		try{
			namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
			StringBuffer saveGallaryPicQuery = new StringBuffer("insert into ebay.vorlage_x_che_galeriebild(vorlage_id,galeriebild_path,cusebeda_objekt_id)");

			saveGallaryPicQuery.append(" values(:vorlage_id,:imagepath,:objectid)");

			Map<String, Object> bind = new HashMap<String, Object>();
			bind.put("vorlage_id", vorlageid);
			
			if(pictures.isSetGalleryURL()){
				
				/*if(pictures.getGalleryURL().contains("http://")){
					
					pictures.setGalleryURL(pictures.getGalleryURL().replaceFirst("http://", "https://"));
				}*/
				
			bind.put("imagepath",pictures.getGalleryURL());
			}
			
			bind.put("objectid", objectid);
			
			
			SqlParameterSource paramSource = new MapSqlParameterSource(bind);
			//KeyHolder keyHolder = new GeneratedKeyHolder();
			logger.debug("Gallary picture save query"+saveGallaryPicQuery.toString());
			int gallaryPic=this.namedJdbcTemplate.update(saveGallaryPicQuery.toString(), paramSource);
			
			if(gallaryPic>0)
				picturesFlag=true;
			
		}catch(Exception e){
			logger.debug("Problem occured at saving gallary picture");
			e.printStackTrace();
		}
		
		try{
			int picsCount=0;
			for(int i=1;i<=pictures.getPictureURL().size();i++){
				namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
			StringBuffer saveItemPicsQuery = new StringBuffer("insert into ebay.vorlage_x_che_auktionbild(vorlage_id,sequenceId,auktionbildpath,cusebeda_objekt_id)");

			saveItemPicsQuery.append(" values(:vorlage_id,:sequenceid,:imagepath,:objectid)");
			
			Map<String, Object> itempics = new HashMap<String, Object>();
			itempics.put("vorlage_id", vorlageid);
			itempics.put("sequenceid", i);
			/*if(pictures.getPictureURL().get(i-1).contains("http://")){
				
				pictures.getPictureURL().set(i-1, pictures.getPictureURL().get(i-1).replaceFirst("http://", "https://"));
			}*/
			itempics.put("imagepath",pictures.getPictureURL().get(i-1));
			itempics.put("objectid", objectid);
			
			SqlParameterSource itemPicsParamSource = new MapSqlParameterSource(itempics);
			logger.debug("ItemPictures save query"+saveItemPicsQuery.toString());
			int itempic=this.namedJdbcTemplate.update(saveItemPicsQuery.toString(), itemPicsParamSource);
			picsCount++;
			logger.debug("updated imapge path for item pics{}",itempic);
			}
			
			if(picsCount==pictures.getPictureURL().size())
				logger.debug("save item picture details saved successfully");
			else
				logger.debug("save item picture details not saved");
			
		}catch(Exception e){
			logger.debug("problem occured at save item picture");
			e.printStackTrace();
		}
			return picturesFlag;
		
		
		
	}
	
	
	
	public boolean saveDescriptionPictures(int vorlageid,DescriptionPicturesType descriptionPictures,int objectid){
		boolean descPicsFlag=false;
		logger.debug("save description pictures");
		
		
		/*try{
			namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
			StringBuffer savelogopicture = new StringBuffer("insert into ebay.vorlage_x_che_logopicture(vorlage_id,imagepath,cusebeda_objekt_id)");
			savelogopicture.append(" values(:vorlage_id,:imagepath,:objectid)");
			
			Map<String, Object> logopic = new HashMap<String, Object>();
			logopic.put("vorlage_id", vorlageid);
			if(descriptionPictures.getLogoPictureURL().contains("http://")){
				descriptionPictures.setLogoPictureURL(descriptionPictures.getLogoPictureURL().replaceFirst("http://", "https://"));
			}
			logopic.put("imagepath",descriptionPictures.getLogoPictureURL());
			logopic.put("objectid", objectid);
																																																																																																																																																																																						
			SqlParameterSource descPicsParamSource = new MapSqlParameterSource(logopic);
			logger.debug("Logo picture save Query"+savelogopicture.toString());
			int logopicSave=this.namedJdbcTemplate.update(savelogopicture.toString(), descPicsParamSource);
			
			if(logopicSave>0){
				descPicsFlag=true;
				logger.debug("Logo picture saved successfully");
			}
				
			
		}catch(Exception e){
			descPicsFlag=false;
			logger.debug("Problem occured at Logo picture save");
			e.printStackTrace();
		}*/
		
		
		
		try{
		for(int i=0;i<descriptionPictures.getDescriptionPictureURL().size();i++){
			namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
			StringBuffer saveDescriptionPicsQuery = new StringBuffer("insert into ebay.che_vorlagebilder(vorlage_id,image_path,nummerierung,cusebeda_objekt_id)");

			saveDescriptionPicsQuery.append(" values(:vorlage_id,:imagepath,:sequenceid,:objectid)");
			
			Map<String, Object> descriptionPics = new HashMap<String, Object>();
			descriptionPics.put("vorlage_id", vorlageid);
			/*if(descriptionPictures.getDescriptionPictureURL().get(i).contains("http://")){
				descriptionPictures.getDescriptionPictureURL().set
				(i,descriptionPictures.getDescriptionPictureURL().get(i).replaceFirst("http://","https://"));
			}*/
			descriptionPics.put("imagepath",descriptionPictures.getDescriptionPictureURL().get(i));
			descriptionPics.put("sequenceid", i+1);
			descriptionPics.put("objectid", objectid);
			
			SqlParameterSource descPicsParamSource = new MapSqlParameterSource(descriptionPics);
			logger.debug("Description Pictuers save query"+saveDescriptionPicsQuery.toString());
			this.namedJdbcTemplate.update(saveDescriptionPicsQuery.toString(), descPicsParamSource);
		
			}
		descPicsFlag=true;
		}catch(Exception e){
			descPicsFlag=false;
			logger.debug("Problem occured at Desc pictures save");
			e.printStackTrace();
		}
		
		try{
		for(int j=0;j<descriptionPictures.getOfferSliderPictureURL().size();j++){
			namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
			StringBuffer saveOfferSliderPicsQuery = new StringBuffer("insert into ebay.vorlage_x_che_offerslider(vorlage_id,image_path,cusebeda_objekt_id,sequenceId)");

			saveOfferSliderPicsQuery.append(" values(:vorlage_id,:imagepath,:objectid,:sequenceid)");
			
			Map<String, Object> offerSliderPics = new HashMap<String, Object>();
			offerSliderPics.put("vorlage_id", vorlageid);
			/*if(descriptionPictures.getOfferSliderPictureURL().get(j).contains("http://")){
				descriptionPictures.getOfferSliderPictureURL().set
				(j, descriptionPictures.getOfferSliderPictureURL().get(j).replaceFirst("http://", "https://"));
			}*/
			offerSliderPics.put("imagepath",descriptionPictures.getOfferSliderPictureURL().get(j));
			offerSliderPics.put("objectid", objectid);
			offerSliderPics.put("sequenceid", j+1);
			
			
			SqlParameterSource offerSliderPicsParamSource = new MapSqlParameterSource(offerSliderPics);
			logger.debug("OfferSliderPicter save query"+saveOfferSliderPicsQuery.toString());
			this.namedJdbcTemplate.update(saveOfferSliderPicsQuery.toString(), offerSliderPicsParamSource);
		
			}
		
		
		
		}catch(Exception e){
			logger.debug("Problem occured at Offer slider pictures save");
			e.printStackTrace();
		}
//		try{
//			for(int k=0;k<descriptionPictures.getObjectSliderPictureURL().size();k++){
//				namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
//			StringBuffer saveObjectSliderPicsQuery = new StringBuffer("insert into ebay.vorlage_x_che_objectslider(vorlage_id,image_path,cusebeda_objekt_id,sequenceId)");
//
//			saveObjectSliderPicsQuery.append(" values(:vorlage_id,:imagepath,:objectid,:sequenceid)");
//			
//			Map<String, Object> objectSliderPics = new HashMap<String, Object>();
//			objectSliderPics.put("vorlage_id", vorlageid);
//			
//			/*if(descriptionPictures.getObjectSliderPictureURL().get(k).contains("http://")){
//				descriptionPictures.getObjectSliderPictureURL().set
//				(k, descriptionPictures.getObjectSliderPictureURL().get(k).replaceFirst("http://","https://"));
//			
//			}*/
//			objectSliderPics.put("imagepath",descriptionPictures.getObjectSliderPictureURL().get(k));
//			objectSliderPics.put("objectid", objectid);
//			objectSliderPics.put("sequenceid", k+1);
//			
//			
//			SqlParameterSource objectSliderPicsParamSource = new MapSqlParameterSource(objectSliderPics);
//			logger.debug("Object slider pic save query"+saveObjectSliderPicsQuery.toString());
//			this.namedJdbcTemplate.update(saveObjectSliderPicsQuery.toString(), objectSliderPicsParamSource);
//		
//			}
//			
//		}catch(Exception e){
//			logger.debug("Problem occured at Object slider pictures save");
//			e.printStackTrace();
//		}
		
		return descPicsFlag;
		
	}
	
	public boolean saveAmenities(int vorlageid,AmenitiesType amenities){
		
		boolean aminitiesFlag=false;
		logger.debug("save amenities of vorlage level");
		try{
		for(int l=0;l<amenities.getAmenityId().size();l++){
			namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
			StringBuffer insertAmenitiesQuery = new StringBuffer("insert into ebay.vorlage_arrangement_rubrik(vorlage_id,rubrik_id)");

			insertAmenitiesQuery.append(" values(:vorlage_id,:rubrik_id)");
			
			Map<String, Object> amenitiesMap = new HashMap<String, Object>();
			amenitiesMap.put("vorlage_id", vorlageid);
			amenitiesMap.put("rubrik_id",amenities.getAmenityId().get(l));
			
			SqlParameterSource amenitiesParamSource = new MapSqlParameterSource(amenitiesMap);
			logger.debug("Save amenities query"+insertAmenitiesQuery.toString());
			int aminitiesCount=this.namedJdbcTemplate.update(insertAmenitiesQuery.toString(), amenitiesParamSource);
			if(aminitiesCount>0)
				aminitiesFlag=true;
			}
		}catch(Exception e){
			aminitiesFlag=false;
			logger.debug("Problem Occured at save amenities");
			e.printStackTrace();
		}
		return aminitiesFlag;
		
	}
	
	public int getObjectId(int templateid){
		int objectid=0;
		
		logger.debug("check object id");
		try{
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		String sql="SELECT cusebeda_objekt_id FROM ebay.vorlage where id=? and status=1";
		 objectid=jdbcTemplate.queryForObject(sql, new Object[] { templateid },Integer.class);
		
		}catch(Exception e){
			logger.debug("Problem occured at object id getting");
			e.printStackTrace();
		}
		return objectid;
	}
	
	public boolean checkTemplateDeleteStatus(int templateid){
		
		boolean templateDeleteflag=false;
		logger.debug("indside template delete checking inside DAO");
		try{
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		//String queryForDelete="select count(auktion.id) from ebay.auktion where auktion.vorlage_id=? and (status=0 OR status=-1)";
		String queryForDelete="select count(auktion.id) from ebay.auktion where auktion.vorlage_id=? and status=0";
		int auktionids=jdbcTemplate.queryForObject(queryForDelete,new Object[] {templateid},Integer.class);
		
		logger.debug("no of auktionids for templateid"+auktionids);
		
		if(auktionids>0)
			templateDeleteflag=true;
		}catch(Exception e){
			logger.debug("Problem occured at checking template delete status");
			templateDeleteflag=false;
			e.printStackTrace();
		}
		
		return templateDeleteflag;
	}
	
	public int getDescImagesCount(int templateid){
		int imagesCount=0;
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		try{
			
			String sql="SELECT template.anzahlbilder FROM ebay.template where id=? and ActiveStatus=1";
			imagesCount=jdbcTemplate.queryForObject(sql, new Object[] { templateid },Integer.class);
			
			
		}catch(Exception e){
			logger.debug("Problem occured at desc pics count");
			e.printStackTrace();
		}
		
		return imagesCount;
	}
	
	
	public boolean checkCurrency(int ebaysiteId,String currency){
		boolean currencyStatus=false;
		
		String currency1 ="";
		jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();
		
		String sql="select DISTINCT currency.kurz FROM ebaystammdaten.currency, ebaystammdaten.siteid WHERE siteid.id = ? AND currency.id = siteid.currency_id";
		
		logger.debug("template module currency checking");
		
		try{
			
			currency1 = (String)jdbcTemplate.queryForObject(sql,new Object[]{ ebaysiteId },String.class);
			currency1=currency1.trim();
			logger.debug("template module currency checking currency tag output is"+currency1);
			if(currency1.equalsIgnoreCase(currency.trim())){
				currencyStatus=true;
			}
		}catch(EmptyResultDataAccessException e){
			logger.debug("Problem occured at checking currency");
			e.printStackTrace();
		}
		
		return currencyStatus;
	}
	
	public boolean saveVorlageSource(int templateid,int sourceid){
		
		boolean saveSourceData=false;
		logger.debug("Inside vorlage source saving");
		try{
		namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
		StringBuffer insertSourceVorlageQuery = new StringBuffer("insert into ebay.vorlage_x_source(vorlage_id,sourceid)");

		insertSourceVorlageQuery.append(" values(:vorlage_id,:sourceid)");
		
		Map<String, Object> sourcevorlage = new HashMap<String, Object>();
		sourcevorlage.put("vorlage_id", templateid);
		sourcevorlage.put("sourceid",sourceid);
		
		SqlParameterSource sourcevorlageparam = new MapSqlParameterSource(sourcevorlage);
		logger.debug("vorlage sourcs save query"+insertSourceVorlageQuery.toString());
		int sourceCount=this.namedJdbcTemplate.update(insertSourceVorlageQuery.toString(), sourcevorlageparam);
		
		if(sourceCount>0)
			saveSourceData=true;
		}catch(Exception e){
			logger.debug("Problem occured in vorlage source");
			e.printStackTrace();
		}
		return saveSourceData;
	}
	
//**************TemplateEdit ****************************//	
	public boolean updateVorlageData(TemplateEditRQ templateEditRq){
		boolean vorlageData=false;
		String timestamp=commonValidations.getCurrentTimeStamp();
		String offerText=null;
		String offerText2=null;
		String subtitle=null;
		String sencodatCat=null;
		//String storeCat=null;
		String retailData=null;
		//int vorlage=
		
		StringBuilder vorlageUpdate=new StringBuilder("update ebay.vorlage set ");
		//StringBuilder auktionupdate=new StringBuilder("update ebay.auktion set ");
		/*if(templateEditRq.isSetSiteId() && templateEditRq.getSiteId()>0){
			vorlageUpdate.append(" ebaysiteid="+templateEditRq.getSiteId());
		}*/
		int vorlageUpdatecounter=0;
		int auktionUpdatecounter=0;
		
		if(templateEditRq.isSetTemplateName() && !templateEditRq.getTemplateName().isEmpty()){
			if(vorlageUpdatecounter>0){
				vorlageUpdatecounter++;
			vorlageUpdate.append(" ,bezeichnung='"+templateEditRq.getTemplateName()+"'");
			}else{
				vorlageUpdatecounter++;
				vorlageUpdate.append(" bezeichnung='"+templateEditRq.getTemplateName()+"'");
			}
		}
		
		if(templateEditRq.isSetDesignTemplate() && Integer.parseInt(templateEditRq.getDesignTemplate())>0 ){
			if(vorlageUpdatecounter>0){
				vorlageUpdatecounter++;
				auktionUpdatecounter++;
			vorlageUpdate.append(" ,template_id="+templateEditRq.getDesignTemplate());
			//auktionupdate.append(" ,template_id=").append(templateEditRq.getDesignTemplate());
			}else{
				vorlageUpdatecounter++;
				auktionUpdatecounter++;
				vorlageUpdate.append(" template_id="+templateEditRq.getDesignTemplate());
				//auktionupdate.append(" template_id=").append(templateEditRq.getDesignTemplate());
			}
		}
			
		if(templateEditRq.isSetOfferTitle() && !templateEditRq.getOfferTitle().isEmpty()){
			if(vorlageUpdatecounter>0){
				vorlageUpdatecounter++;
				auktionUpdatecounter++;
			vorlageUpdate.append(" ,ebaybetreffzeile='"+templateEditRq.getOfferTitle()+"'");
			//auktionupdate.append(" ,ebayueberschrift='"+templateEditRq.getOfferTitle()+"'");
			}else{
				vorlageUpdatecounter++;
				auktionUpdatecounter++;
				vorlageUpdate.append(" ebaybetreffzeile='"+templateEditRq.getOfferTitle()+"'");
				//auktionupdate.append(" ebayueberschrift='"+templateEditRq.getOfferTitle()+"'");
			}
		}
		
		if(templateEditRq.isSetRemoveFields() && templateEditRq.getRemoveFields().isSetRemoveField() ){
		
		subtitle=checkSubTitleRemove(templateEditRq,templateEditRq.getOfferSubTitle());
		
		/*if(subtitle!=null){
			templateEditRq.setOfferSubTitle(subtitle);
		}*/
		
		
	//	int sencodatCat=Integer.parseInt(templateEditRq.getCategories().getSecondaryCategoryDetails().getSecondaryCategoryId());
		
		sencodatCat=checkSecondaryCatRemove(templateEditRq ,sencodatCat);
		/*if(null!=sencodatCat){
		templateEditRq.getCategories().getSecondaryCategoryDetails().setSecondaryCategoryId(String.valueOf(sencodatCat));
		}*/
		retailData=checkRetailRemoveFields(templateEditRq);
		/*if(retailData!=null){
		templateEditRq.setRetailPrice(retailData);
		}*/
		
	
		/*if(null!=storeCat){
			templateEditRq.getStoreCategories().setStoreCategory2Id(storeCat);
		}*/
		}	
		if(templateEditRq.isSetOfferSubTitle()){
			//if(!templateEditRq.getOfferSubTitle().isEmpty()){
			
			if(vorlageUpdatecounter>0){
			vorlageUpdatecounter++;
			auktionUpdatecounter++;
			
			vorlageUpdate.append(" ,untertitel='"+templateEditRq.getOfferSubTitle()+"'");
			//auktionupdate.append(" ,untertitel='"+templateEditRq.getOfferSubTitle()+"'");
			//}
			//else
				//vorlageUpdate.append(" ,untertitel=null");
			}else{
				vorlageUpdatecounter++;
				auktionUpdatecounter++;
				//if(!templateEditRq.getOfferSubTitle().isEmpty()){
					vorlageUpdate.append(" untertitel='"+templateEditRq.getOfferSubTitle()+"'");
					//auktionupdate.append(" untertitel='"+templateEditRq.getOfferSubTitle()+"'");
				//}
					//else
						//vorlageUpdate.append(" untertitel=null");
			}
		//}
		}else{
			if(subtitle!=null){
			if(vorlageUpdatecounter>0){
				vorlageUpdatecounter++;
				auktionUpdatecounter++;
				
				vorlageUpdate.append(" ,untertitel='"+subtitle+"'");
				//auktionupdate.append(" ,untertitel='"+templateEditRq.getOfferSubTitle()+"'");
				//}
				//else
					//vorlageUpdate.append(" ,untertitel=null");
				}else{
					vorlageUpdatecounter++;
					auktionUpdatecounter++;
					//if(!templateEditRq.getOfferSubTitle().isEmpty()){
						vorlageUpdate.append(" untertitel='"+subtitle+"'");
						//auktionupdate.append(" untertitel='"+templateEditRq.getOfferSubTitle()+"'");
					//}
						//else
							//vorlageUpdate.append(" untertitel=null");
				}
			}
		}
		
		
		if(templateEditRq.isSetAdditionalOptions() &&templateEditRq.getAdditionalOptions().isSetOptionValue() && templateEditRq.getAdditionalOptions().getOptionValue().size()>0){
			int optionV=8;
			/*boolean highlight=false;
			boolean bold=false;
			boolean gallery=false;
			boolean topgallery=false;
			boolean tophomepage=false;*/
			
			for(String optionvalue:templateEditRq.getAdditionalOptions().getOptionValue()){
				int optionsValue=Integer.parseInt(optionvalue);
				if(optionsValue!=8)
				optionV+=optionsValue;
				
				/*switch (optionsValue) {
				case 1:
					highlight=true;
					if(auktionUpdatecounter>0){
						auktionUpdatecounter++;
						auktionupdate.append(" ,ebayueberschrifthighlight=1");
					}else{
						auktionUpdatecounter++;
						auktionupdate.append("ebayueberschrifthighlight=1");
					}
					break;
				case 2:
					bold=true;
					if(auktionUpdatecounter>0){
						auktionUpdatecounter++;
						auktionupdate.append(" ,ebayueberschriftfett=1");
					}else{
						auktionUpdatecounter++;
						auktionupdate.append(" ebayueberschriftfett=1");
					}
					break;
					
				case 4:
					topgallery=true;
					if(auktionUpdatecounter>0){
						auktionUpdatecounter++;
						auktionupdate.append(" ,ebaytop=1");
					}else{
						auktionUpdatecounter++;
						auktionupdate.append(" ebaytop=1");
					}
					
					break;
	
				case 8:
					gallery=true;
					if(auktionUpdatecounter>0){
						auktionUpdatecounter++;
						auktionupdate.append(" ,ebaygaleriebild=1");
					}else{
						auktionUpdatecounter++;
						auktionupdate.append(" ebaygaleriebild=1");
					}
					
					break;
					
				case 16:
					tophomepage=true;
					if(auktionUpdatecounter>0){
						auktionUpdatecounter++;
						auktionupdate.append(" ,top_startseite=1");
					}else{
						auktionUpdatecounter++;
						auktionupdate.append(" top_startseite=1");
					}
					
					break;
			
				}
				
				
			}
			
			if(auktionUpdatecounter>0){
			auktionUpdatecounter++;
			auktionupdate.append(" ,ebaygaleriebild=1");
		}else{
			auktionUpdatecounter++;
			auktionupdate.append(" ebaygaleriebild=1");
		}
			if(!highlight){
				if(auktionUpdatecounter>0){
					auktionUpdatecounter++;
					auktionupdate.append(" ,ebayueberschrifthighlight=0");
				}else{
					auktionUpdatecounter++;
					auktionupdate.append("ebayueberschrifthighlight=0");
				}
			}
				
			if(!bold){
				if(auktionUpdatecounter>0){
					auktionUpdatecounter++;
					auktionupdate.append(" ,ebayueberschriftfett=0");
				}else{
					auktionUpdatecounter++;
					auktionupdate.append(" ebayueberschriftfett=0");
				}
			}
			
			if(!topgallery){
				if(auktionUpdatecounter>0){
					auktionUpdatecounter++;
					auktionupdate.append(" ,ebaytop=0");
				}else{
					auktionUpdatecounter++;
					auktionupdate.append(" ebaytop=0");
				}
			}
			
			if(!tophomepage){
				if(auktionUpdatecounter>0){
					auktionUpdatecounter++;
					auktionupdate.append(" ,top_startseite=1");
				}else{
					auktionUpdatecounter++;
					auktionupdate.append(" top_startseite=1");
				}
			}
			*/
			
			
			
		}
			if(vorlageUpdatecounter>0){
				vorlageUpdatecounter++;
				vorlageUpdate.append(" ,ebayoptionen="+optionV);
				}else{
					vorlageUpdatecounter++;
					vorlageUpdate.append(" ebayoptionen="+optionV);
				}
		}
			
		
		if(templateEditRq.isSetCategories() && templateEditRq.getCategories().isSetPrimaryCategoryDetails() && templateEditRq.getCategories().getPrimaryCategoryDetails().isSetPrimaryCategoryId() && Integer.parseInt(templateEditRq.getCategories().getPrimaryCategoryDetails().getPrimaryCategoryId())>0){
			if(vorlageUpdatecounter>0){
				vorlageUpdatecounter++;
				auktionUpdatecounter++;
				vorlageUpdate.append(" ,ebaykategorieid1="+templateEditRq.getCategories().getPrimaryCategoryDetails().getPrimaryCategoryId());
				//auktionupdate.append(" ,ebaykategorieid=").append(templateEditRq.getCategories().getPrimaryCategoryDetails().getPrimaryCategoryId());
				}else{
					vorlageUpdatecounter++;
					auktionUpdatecounter++;
					vorlageUpdate.append(" ebaykategorieid1="+templateEditRq.getCategories().getPrimaryCategoryDetails().getPrimaryCategoryId());
					//auktionupdate.append(" ebaykategorieid=").append(templateEditRq.getCategories().getPrimaryCategoryDetails().getPrimaryCategoryId());
				}
		}
		
		
		if(templateEditRq.isSetCategories() && templateEditRq.getCategories().isSetSecondaryCategoryDetails() && templateEditRq.getCategories().getSecondaryCategoryDetails().isSetSecondaryCategoryId() && Integer.parseInt(templateEditRq.getCategories().getSecondaryCategoryDetails().getSecondaryCategoryId())>0){
			
			if(vorlageUpdatecounter>0){
				vorlageUpdatecounter++;
				auktionUpdatecounter++;
				if(sencodatCat!=null){
					vorlageUpdate.append(" ,ebaykategorieid2="+sencodatCat);
				}else{
				vorlageUpdate.append(" ,ebaykategorieid2="+templateEditRq.getCategories().getSecondaryCategoryDetails().getSecondaryCategoryId());
				}
				//auktionupdate.append(" ,ebaykategorieid2=").append(templateEditRq.getCategories().getSecondaryCategoryDetails().getSecondaryCategoryId());
				}else{
					vorlageUpdatecounter++;
					auktionUpdatecounter++;
					if(sencodatCat!=null){
						vorlageUpdate.append(" ebaykategorieid2="+sencodatCat);
					}else{
					vorlageUpdate.append(" ebaykategorieid2="+templateEditRq.getCategories().getSecondaryCategoryDetails().getSecondaryCategoryId());
					}
					//auktionupdate.append(" ebaykategorieid2=").append(templateEditRq.getCategories().getSecondaryCategoryDetails().getSecondaryCategoryId());
				}
				
			}else if(templateEditRq.isSetCategories()){
				if(vorlageUpdatecounter>0){
					vorlageUpdatecounter++;
					auktionUpdatecounter++;
				vorlageUpdate.append(" ,ebaykategorieid2=0");
				}else{
					vorlageUpdatecounter++;
					auktionUpdatecounter++;
					vorlageUpdate.append(" ebaykategorieid2=0");
				}
			}else{
				if(sencodatCat!=null){
					if(vorlageUpdatecounter>0){
						vorlageUpdatecounter++;
						auktionUpdatecounter++;
						
						vorlageUpdate.append(" ,ebaykategorieid2="+sencodatCat);
						//auktionupdate.append(" ,ebaykategorieid2=").append(templateEditRq.getCategories().getSecondaryCategoryDetails().getSecondaryCategoryId());
						}else{
							vorlageUpdatecounter++;
							auktionUpdatecounter++;
							vorlageUpdate.append(" ebaykategorieid2="+sencodatCat);
							//auktionupdate.append(" ebaykategorieid2=").append(templateEditRq.getCategories().getSecondaryCategoryDetails().getSecondaryCategoryId());
						}
				}
			}
		
		if(templateEditRq.isSetDescription()){
			
			if(templateEditRq.getDescription().isSetTitle() &&  !templateEditRq.getDescription().getTitle().isEmpty()){
				if(vorlageUpdatecounter>0){
					vorlageUpdatecounter++;
					auktionUpdatecounter++;
				vorlageUpdate.append(" ,ueberschrift='"+templateEditRq.getDescription().getTitle()+"'");	
				//auktionupdate.append(" ,ueberschrift='"+templateEditRq.getDescription().getTitle()+"'");
				}else{
					vorlageUpdatecounter++;
					auktionUpdatecounter++;
					vorlageUpdate.append(" ueberschrift='"+templateEditRq.getDescription().getTitle()+"'");	
					//auktionupdate.append(" ueberschrift='"+templateEditRq.getDescription().getTitle()+"'");
				}
			}
			
			if(templateEditRq.getDescription().isSetTitle() && !templateEditRq.getDescription().getSubTitle().isEmpty()){
				if(vorlageUpdatecounter>0){
					vorlageUpdatecounter++;
				vorlageUpdate.append(" ,ueberschrift2='"+templateEditRq.getDescription().getSubTitle()+"'");	
				}else{
					vorlageUpdatecounter++;
					vorlageUpdate.append(" ueberschrift2='"+templateEditRq.getDescription().getSubTitle()+"'");	
				}
			}
			
			if(templateEditRq.getDescription().isSetOfferText() ){
				
				if(!templateEditRq.getDescription().getOfferText().isEmpty()){
					offerText=templateEditRq.getDescription().getOfferText();
					if(vorlageUpdatecounter>0){
						vorlageUpdatecounter++;
						auktionUpdatecounter++;
					vorlageUpdate.append(" ,text='"+templateEditRq.getDescription().getOfferText()+"'");
					//auktionupdate.append(" ,text='"+templateEditRq.getDescription().getOfferText()+"'");
				}else{
					vorlageUpdatecounter++;
					auktionUpdatecounter++;
					vorlageUpdate.append(" text='"+templateEditRq.getDescription().getOfferText()+"'");
					//auktionupdate.append(" text='"+templateEditRq.getDescription().getOfferText()+"'");
				}
			}
			}
			if(templateEditRq.getDescription().isSetOfferAdditionalText() ){
				if(!templateEditRq.getDescription().getOfferAdditionalText().isEmpty()){
					offerText2=templateEditRq.getDescription().getOfferAdditionalText();
					
					if(vorlageUpdatecounter>0){
						vorlageUpdatecounter++;
					vorlageUpdate.append(" ,text2='"+templateEditRq.getDescription().getOfferAdditionalText()+"'");
					}else{
						vorlageUpdatecounter++;
						vorlageUpdate.append(" text2='"+templateEditRq.getDescription().getOfferAdditionalText()+"'");
					}
				}
			}
			
			
			
		}
		
		if(templateEditRq.isSetPrice() ){
			double price=0;
			try{
			 price=Double.parseDouble(templateEditRq.getPrice());
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(price>0){
				
				if(vorlageUpdatecounter>0){
					vorlageUpdatecounter++;
					vorlageUpdate.append(" ,startpreis="+templateEditRq.getPrice());
				}else{
					vorlageUpdatecounter++;
					vorlageUpdate.append(" startpreis="+templateEditRq.getPrice());
				}
				
			}
		}
		
		if(templateEditRq.isSetRetailPrice()){
			double retailPrice =0;
			try{
				retailPrice=Double.parseDouble(templateEditRq.getRetailPrice());
			}catch(Exception e){
				e.printStackTrace();
			}
			if(retailPrice>0){
				if(vorlageUpdatecounter>0){
					vorlageUpdatecounter++;
					vorlageUpdate.append(" ,retailprice="+retailPrice);
				}else{
					vorlageUpdatecounter++;
					vorlageUpdate.append(" retailprice="+retailPrice);
				}	
			}
		}else{
			if(retailData!=null){
				if(vorlageUpdatecounter>0){
					vorlageUpdatecounter++;
					vorlageUpdate.append(" ,retailprice="+retailData);
				}else{
					vorlageUpdatecounter++;
					vorlageUpdate.append(" retailprice="+retailData);
				}
			}
		}
		
		if(templateEditRq.isSetShopObjectId()){
			int auctionmastertypeId=getAuctionmasterTypeid(templateEditRq.getTemplateId());
			if(auctionmastertypeId!=4){
				if(vorlageUpdatecounter>0){
					vorlageUpdatecounter++;
					vorlageUpdate.append(" ,auctionMasterTypeID=4");
				}else{
					vorlageUpdatecounter++;
					vorlageUpdate.append(" auctionMasterTypeID=4");
				}
			}
		}
		
	
			
			if(templateEditRq.isSetProductId() && Integer.parseInt(templateEditRq.getProductId())>0){
				if(vorlageUpdatecounter>0){
					vorlageUpdatecounter++;
				vorlageUpdate.append(" ,arrangement_id="+templateEditRq.getProductId());
				}else{
					vorlageUpdatecounter++;
					vorlageUpdate.append(" arrangement_id="+templateEditRq.getProductId());
				}
			}
			
			if(vorlageUpdatecounter>0){
				
				vorlageUpdate.append(", geaendert='"+timestamp+"'");
				vorlageUpdate.append(" where id=?");
			}
			
			logger.debug("Vorlage data update QUERY=="+vorlageUpdate.toString());
			
			if(vorlageUpdatecounter>0){
			
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			logger.debug("Vorlage data update Query "+vorlageUpdate.toString());
			int vorlageUpdateCount=this.jdbcTemplate.update(vorlageUpdate.toString(), new Object[] { templateEditRq.getTemplateId() });
			
			if(vorlageUpdateCount>0){
				vorlageData=true;
			}
			
			}
		
			if(auktionUpdatecounter>0){
				
				//auktionupdate.append(" where ebayitemid=0 and status=0 and vorlage_id=?");
			}
			
			Map<String,String> offerTexts= getOfferTextData(templateEditRq.getTemplateId());
			
			if(offerText==null)
				offerText=offerTexts.get("offertext");
			
			if(offerText2==null)
				offerText2=offerTexts.get("offertext2");	
			
			/*if(templateEditRq.getDescription().isSetOfferText() || templateEditRq.getDescription().isSetOfferAdditionalText()){
				//call to auktions_texte table	
				//call to auktion table for offerText1 update	
					
				}*/
			if(templateEditRq.isSetDescription()){
			if(vorlageData &&(templateEditRq.getDescription().isSetOfferText() || templateEditRq.getDescription().isSetOfferAdditionalText() )){
				saveAuktionsText(offerText,offerTexts.get("vouchertext"),offerTexts.get("vouchervaliditytext"),Integer.parseInt(offerTexts.get("vouchervalidity")),templateEditRq.getSourceId(),offerText2,templateEditRq.getTemplateId());
			}
			}
			if(vorlageData){
			List<Integer> futureofferscount=getFutureAuktionids(templateEditRq.getTemplateId());
			
			if(futureofferscount.size()>0){
				updateFutureOffers(templateEditRq,futureofferscount);	
			}
			}
			
		
		return vorlageData;
	}
	
	
	public boolean updateVorlageArrangement(TemplateEditRQ templateEditRq){
		
		boolean updateVAData=false;
		
		StringBuffer updateVADQuery=new StringBuffer("update ebay.vorlagen_arrangement set ");
		
		if(templateEditRq.isSetProperties()){
			int updateCounter=0;
			if(templateEditRq.getProperties().isSetNights() && Integer.parseInt(templateEditRq.getProperties().getNights())>0){
				
				if(updateCounter>0){
				updateVADQuery.append(", naechte="+templateEditRq.getProperties().getNights());
				updateCounter++;
				}else{
					updateVADQuery.append(" naechte="+templateEditRq.getProperties().getNights());
					updateCounter++;
				}
				
			}
			if(templateEditRq.getProperties().isSetPersons() && Integer.parseInt(templateEditRq.getProperties().getPersons())>0){
				if(updateCounter>0){
				updateVADQuery.append(", personen="+templateEditRq.getProperties().getPersons());
				updateCounter++;
				}else{
					updateVADQuery.append(" personen="+templateEditRq.getProperties().getPersons());
					updateCounter++;
				}
			}
			if(templateEditRq.getProperties().isSetCateringType() && Integer.parseInt(templateEditRq.getProperties().getCateringType())>0){
				if(updateCounter>0){
				updateVADQuery.append(" , hofesoda_verpflegung_id="+templateEditRq.getProperties().getCateringType());
				updateCounter++;
				}else{
					updateVADQuery.append(" hofesoda_verpflegung_id="+templateEditRq.getProperties().getCateringType());
					updateCounter++;
				}
					
			}
			
			if(templateEditRq.getProperties().isSetTypeOfRoom() && Integer.parseInt(templateEditRq.getProperties().getTypeOfRoom())>0){
				if(updateCounter>0){
				updateVADQuery.append(", hofesoda_zimmerart_id="+templateEditRq.getProperties().getTypeOfRoom());
				updateCounter++;
				}else{
					updateVADQuery.append(" hofesoda_zimmerart_id="+templateEditRq.getProperties().getTypeOfRoom());
					updateCounter++;
				}
			}
			
			if(updateCounter>0){
				updateVADQuery.append(" where vorlage_id =?");
				
			}
			logger.debug("vorlage arrangement update query"+updateVADQuery.toString());
			
			if(updateCounter>0){
				
				jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
				logger.debug("Vorlage data update Query "+updateVADQuery.toString());
				int vaCount=this.jdbcTemplate.update(updateVADQuery.toString(), new Object[] { templateEditRq.getTemplateId() });
			
				if(vaCount>0){
					updateVAData=true;
				}
			}
		
			
		}
		
		return updateVAData;
	}
	
	public boolean updateVoucherData(TemplateEditRQ templateEditRq){
		
		String vouchertext=null;
		String validityText=null;
		int validity=0;
		
		boolean updateVoucherFlag=false;
		//boolean updateFutureOffers=false;
		
		
		
		StringBuilder updateVoucherData=new StringBuilder("update ebay.vorlage_gutscheine set");
		StringBuilder updateAuktionVoucherData=new StringBuilder("update ebay.auktion a join ebay.auktion_texte at on a.id=at.auktion_id set ");
		int updateCounter=0;
		int updateAuktionCounter=0;
		
		if(templateEditRq.isSetDescription() &&templateEditRq.getDescription().isSetVoucherText() && !templateEditRq.getDescription().getVoucherText().isEmpty()){
			vouchertext=templateEditRq.getDescription().getVoucherText();
			if(updateCounter>0){
			updateVoucherData.append(", gutschein_text='"+templateEditRq.getDescription().getVoucherText()+"'");
			updateAuktionVoucherData.append(", gutschein_text='"+templateEditRq.getDescription().getVoucherText()+"'");
			}else{
				updateVoucherData.append(" gutschein_text='"+templateEditRq.getDescription().getVoucherText()+"'");
				updateAuktionVoucherData.append(" gutschein_text='"+templateEditRq.getDescription().getVoucherText()+"'");
				updateCounter++;
				updateAuktionCounter++;
			}
		}
		
		if(templateEditRq.isSetDescription() && templateEditRq.getDescription().isSetVoucherValidityText() && !templateEditRq.getDescription().getVoucherValidityText().isEmpty()){
			validityText=templateEditRq.getDescription().getVoucherValidityText();
			if(updateCounter>0){
			updateVoucherData.append(", gueltigkeit_text='"+templateEditRq.getDescription().getVoucherValidityText()+"'");
			updateAuktionVoucherData.append(", gueltigkeit_text='"+templateEditRq.getDescription().getVoucherValidityText()+"'");
			}else{
				updateVoucherData.append(" gueltigkeit_text='"+templateEditRq.getDescription().getVoucherValidityText()+"'");
				updateAuktionVoucherData.append(" gueltigkeit_text='"+templateEditRq.getDescription().getVoucherValidityText()+"'");
				updateCounter++;
				updateAuktionCounter++;
			}
		}
		
		if(templateEditRq.isSetVoucherValidity() && Integer.parseInt(templateEditRq.getVoucherValidity())>0){			
			validity=Integer.parseInt(templateEditRq.getVoucherValidity());		
			if(updateCounter>0){
			updateVoucherData.append(", gueltigkeit="+templateEditRq.getVoucherValidity());
			updateAuktionVoucherData.append(", gueltigkeit="+templateEditRq.getVoucherValidity());
			}else{
				updateVoucherData.append(" gueltigkeit="+templateEditRq.getVoucherValidity());
				updateAuktionVoucherData.append(" gueltigkeit="+templateEditRq.getVoucherValidity());
				updateCounter++;
				updateAuktionCounter++;
			}
				
		}
		
		if(updateCounter>0)
			updateVoucherData.append(" where  vorlage_id=?");
		
		logger.debug("Voucher data update===="+updateVoucherData);
		
		if(updateCounter>0){
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			logger.debug("Voucher data update Query "+updateVoucherData.toString());
			int voucherDataUCount=this.jdbcTemplate.update(updateVoucherData.toString(), new Object[] { templateEditRq.getTemplateId() });
		
			if(voucherDataUCount>0){
				updateVoucherFlag=true;
			}
		}
		
		if(updateAuktionCounter>0){
			//updateVoucherFlag=false;
			updateAuktionVoucherData.append(" where a.status=0 and a.ebayitemid=0 and a.vorlage_id=?");
			logger.debug("Voucher data auktion level update query===="+updateAuktionVoucherData);
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			logger.debug("Voucher data update Query auktion texte table "+updateAuktionVoucherData.toString());
			int voucherAuktionDataUCount=this.jdbcTemplate.update(updateAuktionVoucherData.toString(), new Object[] { templateEditRq.getTemplateId() });
		
			if(voucherAuktionDataUCount>0){
				
				//updateFutureOffers=true;
				logger.debug("updated future offers voucher data");
			}
		}
		
		Map<String,String> offerTexts= getOfferTextData(templateEditRq.getTemplateId());
		
		if(vouchertext==null)
			vouchertext=offerTexts.get("vouchertext");
		
		if(validityText==null)
			validityText=offerTexts.get("vouchervaliditytext");
		
		if(validity==0){
		try{
			validity=Integer.parseInt(offerTexts.get("vouchervalidity"));
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		String offertext2=null;
		if(templateEditRq.isSetDescription() && templateEditRq.getDescription().isSetOfferAdditionalText()){
			offertext2=templateEditRq.getDescription().getOfferAdditionalText();
		}else{
			offertext2=offerTexts.get("offertext2");
		}
		
		if(updateVoucherFlag){
			saveAuktionsText(offerTexts.get("offertext"),vouchertext,validityText,validity,templateEditRq.getSourceId(),offertext2,templateEditRq.getTemplateId());
		}
		
		return updateVoucherFlag;
	}
	
	public Map<String,String> getOfferTextData(int vorlageid){
		
		Map<String,String> offerdata=new HashMap<String,String>();	
		try{
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			StringBuilder gettingVoucherTextData=new StringBuilder("select gutschein_text,gueltigkeit_text,gueltigkeit from ebay.vorlage_gutscheine where vorlage_id=?");
			
			List<Map<String,Object>> voucherdata=jdbcTemplate.queryForList(gettingVoucherTextData.toString(),vorlageid);
			
			for(Map<String,Object> voucherMap:voucherdata){
				offerdata.put("vouchertext", voucherMap.get("gutschein_text").toString());
				offerdata.put("vouchervaliditytext", voucherMap.get("gueltigkeit_text").toString());
				offerdata.put("vouchervalidity", voucherMap.get("gueltigkeit").toString());
			
			}
			
		}catch(Exception otd){
			otd.printStackTrace();
		}
		
		try{
			
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			StringBuilder gettingOfferTextData=new StringBuilder("select text,text2 from ebay.vorlage where id=?");
			
			List<Map<String,Object>> textdata=jdbcTemplate.queryForList(gettingOfferTextData.toString(),vorlageid);
			
			for(Map<String,Object> textMap:textdata){
				offerdata.put("offertext", textMap.get("text").toString());
				offerdata.put("offertext2", textMap.get("text2").toString());
			}
			
			
		}catch(Exception offerText){
			offerText.printStackTrace();
		}
		
		return offerdata;

	}
	
	
	//not in use
	public List<Integer> getFutureAuktionids(int vorlageid){
		List<Integer> futureAuktions=new ArrayList<Integer>();
		
		try{		
			List<Map<String,Object>> auktionIds = null;
		
				jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
				logger.debug("Inside Try block of booking data method...");

				String fetchFutureAuktionsQuery = "SELECT auktion.id as id from ebay.auktion where ebayitemid=0 and status=0 and vorlage_id=?";
				logger.debug("FETCH QUERY:::"+fetchFutureAuktionsQuery);
				auktionIds = jdbcTemplate.queryForList(fetchFutureAuktionsQuery,vorlageid);
		if(auktionIds.size()>0){
			for(Map<String,Object> ids:auktionIds){
				futureAuktions.add(Integer.parseInt(ids.get("id").toString()));
			}
		}
			
		}catch(Exception foe){
			foe.printStackTrace();
		}		
		return futureAuktions;
	}
	
	public boolean updateFutureOffers(TemplateEditRQ templateEditRq,List<Integer> futureAuctionids){
		boolean futureOffersUpdate=false;
		int success=0;
		int failure=0;
		for(Integer auktionid:futureAuctionids){
		int auktionUpdateCounter=0;
		StringBuilder futureOffersUpdateQuery=new StringBuilder("update ebay.auktion set ");
		
		if(templateEditRq.isSetDesignTemplate()){
			
			if(auktionUpdateCounter>0)
				futureOffersUpdateQuery.append(" ,template_id ="+templateEditRq.getDesignTemplate());
			else{
				auktionUpdateCounter++;
				futureOffersUpdateQuery.append(" template_id ="+templateEditRq.getDesignTemplate());
			}
		}
		
		if(templateEditRq.isSetCategories() && templateEditRq.getCategories().isSetPrimaryCategoryDetails() && templateEditRq.getCategories().getPrimaryCategoryDetails().isSetPrimaryCategoryId()){
			
			if(auktionUpdateCounter>0)
				futureOffersUpdateQuery.append(" ,ebaykategorieid="+templateEditRq.getCategories().getPrimaryCategoryDetails().getPrimaryCategoryId());
			else{
				auktionUpdateCounter++;
				futureOffersUpdateQuery.append(" ebaykategorieid ="+templateEditRq.getCategories().getPrimaryCategoryDetails().getPrimaryCategoryId());
			}
			
		}
		
		if(templateEditRq.isSetCategories() && templateEditRq.getCategories().isSetSecondaryCategoryDetails() && templateEditRq.getCategories().getSecondaryCategoryDetails().isSetSecondaryCategoryId()){
			String sencodatCat=null;
			sencodatCat=checkSecondaryCatRemove(templateEditRq ,sencodatCat);
			if(auktionUpdateCounter>0){
				if(null!=sencodatCat){
				futureOffersUpdateQuery.append(" ,ebaykategorieid2="+sencodatCat);
				}else{
					futureOffersUpdateQuery.append(" ,ebaykategorieid2="+templateEditRq.getCategories().getSecondaryCategoryDetails().getSecondaryCategoryId());
				}
			}else{
				auktionUpdateCounter++;
				if(null!=sencodatCat){
					futureOffersUpdateQuery.append(" ebaykategorieid2 ="+sencodatCat);
				}else{
				futureOffersUpdateQuery.append(" ebaykategorieid2 ="+templateEditRq.getCategories().getSecondaryCategoryDetails().getSecondaryCategoryId());
				}
			}
			
		}else if(templateEditRq.isSetCategories()){
			if(auktionUpdateCounter>0)
				futureOffersUpdateQuery.append(" ,ebaykategorieid2=0");
			else{
				auktionUpdateCounter++;
				futureOffersUpdateQuery.append(" ebaykategorieid2 =0");
			}
		}
		
		if(templateEditRq.isSetOfferTitle()){
			if(auktionUpdateCounter>0)
				futureOffersUpdateQuery.append(" ,ebayueberschrift='"+templateEditRq.getOfferTitle()+"'");
			else{
				auktionUpdateCounter++;
				futureOffersUpdateQuery.append(" ebayueberschrift ='"+templateEditRq.getOfferTitle()+"'");
			}
		}
		
		
		if(templateEditRq.isSetOfferSubTitle()){
			if(auktionUpdateCounter>0)
				futureOffersUpdateQuery.append(" ,untertitel='"+templateEditRq.getOfferSubTitle()+"'");
			else{
				auktionUpdateCounter++;
				futureOffersUpdateQuery.append(" untertitel ='"+templateEditRq.getOfferSubTitle()+"'");
			}
		}
		
		if(templateEditRq.isSetOfferSubTitle()){
			if(auktionUpdateCounter>0)
				futureOffersUpdateQuery.append(" ,untertitel='"+templateEditRq.getOfferSubTitle()+"'");
			else{
				auktionUpdateCounter++;
				futureOffersUpdateQuery.append(" untertitel ='"+templateEditRq.getOfferSubTitle()+"'");
			}
		}
		
		if(templateEditRq.isSetPrice()){
			double startprice=	getAuktionStartPrice(auktionid);
			double ebaysofortkauf=getAuktionEbaysofortkauf(auktionid);
			int auctionmastertypeId=getAuktionAuctionmasterTypeid(auktionid);	
			
			if(auctionmastertypeId==1){
				
				if(startprice>0 && ebaysofortkauf==0){
					//auction startprice
					if(auktionUpdateCounter>0)
						futureOffersUpdateQuery.append(" ,startpreis="+templateEditRq.getPrice());
					else{
						auktionUpdateCounter++;
						futureOffersUpdateQuery.append(" startpreis ="+templateEditRq.getPrice());
					}
				}else if(ebaysofortkauf>0 && startprice==0){
					//fixedprice with collection
					if(auktionUpdateCounter>0)
						futureOffersUpdateQuery.append(" ,ebaysofortkauf="+templateEditRq.getPrice());
					else{
						auktionUpdateCounter++;
						futureOffersUpdateQuery.append(" ebaysofortkauf ="+templateEditRq.getPrice());
					}
					
				}
				
			}else if(auctionmastertypeId==4){
				//shop offer 
				if(startprice>0 && ebaysofortkauf==0){
					if(auktionUpdateCounter>0)
						futureOffersUpdateQuery.append(" ,startpreis="+templateEditRq.getPrice());
					else{
						auktionUpdateCounter++;
						futureOffersUpdateQuery.append(" startpreis ="+templateEditRq.getPrice());
					}
				}
				
			}
			
		}
		
		
			if(templateEditRq.isSetAdditionalOptions() &&templateEditRq.getAdditionalOptions().isSetOptionValue() && templateEditRq.getAdditionalOptions().getOptionValue().size()>0){
				int optionV=8;
				boolean highlight=false;
				boolean bold=false;
				boolean gallery=false;
				boolean topgallery=false;
				boolean tophomepage=false;
				
				for(String optionvalue:templateEditRq.getAdditionalOptions().getOptionValue()){
					int optionsValue=Integer.parseInt(optionvalue);
					if(optionsValue!=8)
					optionV+=optionsValue;
					
					switch (optionsValue) {
					case 1:
						highlight=true;
						if(auktionUpdateCounter>0){
							
							futureOffersUpdateQuery.append(" ,ebayueberschrifthighlight=1");
						}else{
							auktionUpdateCounter++;
							futureOffersUpdateQuery.append("ebayueberschrifthighlight=1");
						}
						break;
					case 2:
						bold=true;
						if(auktionUpdateCounter>0){
							
							futureOffersUpdateQuery.append(" ,ebayueberschriftfett=1");
						}else{
							auktionUpdateCounter++;
							futureOffersUpdateQuery.append(" ebayueberschriftfett=1");
						}
						break;
						
					case 4:
						topgallery=true;
						if(auktionUpdateCounter>0){
							
							futureOffersUpdateQuery.append(" ,ebaytop=1");
						}else{
							auktionUpdateCounter++;
							futureOffersUpdateQuery.append(" ebaytop=1");
						}
						
						break;
		
					case 8:
						gallery=true;
										
						break;
						
					case 16:
						tophomepage=true;
						if(auktionUpdateCounter>0){
							
							futureOffersUpdateQuery.append(" ,top_startseite=1");
						}else{
							auktionUpdateCounter++;
							futureOffersUpdateQuery.append(" top_startseite=1");
						}
						
						break;
				
					}
					
					
				}
				
				if(auktionUpdateCounter>0){	
				futureOffersUpdateQuery.append(" ,ebaygaleriebild=1");
				}else{
				auktionUpdateCounter++;
				futureOffersUpdateQuery.append(" ebaygaleriebild=1");
				}
				if(!highlight){
					if(auktionUpdateCounter>0){
						
						futureOffersUpdateQuery.append(" ,ebayueberschrifthighlight=0");
					}else{
						auktionUpdateCounter++;
						futureOffersUpdateQuery.append("ebayueberschrifthighlight=0");
					}
				}
					
				if(!bold){
					if(auktionUpdateCounter>0){
						
						futureOffersUpdateQuery.append(" ,ebayueberschriftfett=0");
					}else{
						auktionUpdateCounter++;
						futureOffersUpdateQuery.append(" ebayueberschriftfett=0");
					}
				}
				
				if(!topgallery){
					if(auktionUpdateCounter>0){
						
						futureOffersUpdateQuery.append(" ,ebaytop=0");
					}else{
						auktionUpdateCounter++;
						futureOffersUpdateQuery.append(" ebaytop=0");
					}
				}
				
				if(!tophomepage){
					if(auktionUpdateCounter>0){
						
						futureOffersUpdateQuery.append(" ,top_startseite=0");
					}else{
						auktionUpdateCounter++;
						futureOffersUpdateQuery.append(" top_startseite=0");
					}
				}
			
			}
				
	
		if(templateEditRq.isSetDescription()){
			if(templateEditRq.getDescription().isSetOfferText()){
				if(auktionUpdateCounter>0){
					futureOffersUpdateQuery.append(" ,text='"+templateEditRq.getDescription().getOfferText()+"'");
				}else{
					auktionUpdateCounter++;
					futureOffersUpdateQuery.append(" text='"+templateEditRq.getDescription().getOfferText()+"'");
				}
			}
		}
		
	if(auktionUpdateCounter>0){
		futureOffersUpdateQuery.append(" where ebayitemid=0 and status=0 and vorlage_id="+templateEditRq.getTemplateId()+" and id=?");
	}
	
	logger.debug("future offers query {}",futureOffersUpdateQuery);
		try{
			
			
			boolean futureOfferUpdateFlag=false;
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			
			int auktionOffersCount=this.jdbcTemplate.update(futureOffersUpdateQuery.toString(), new Object[] { auktionid });
			
			if(auktionOffersCount>0){
				futureOfferUpdateFlag=true;
			}
			
			if(futureOfferUpdateFlag)
				success++;
			else
				failure++;
			
			logger.debug("failure counter value in future query{}",failure);
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		}
		
		
			if(futureAuctionids.size()==success)
				futureOffersUpdate=true;
			else
				futureOffersUpdate=false;
		
		return futureOffersUpdate;
	}
	
	public boolean updateOfferTextInAuktionTable(String offerText1,int vorlageid){
		boolean offerTextUpdateFlag=false;
		try{
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			StringBuilder offerTextUpdateQuery=new StringBuilder("update ebay.auktion set text='").append(offerText1).append("' where status=0 and ebayitemid=0 and vorlage_id=?");
			int auktionOfferTextCount=this.jdbcTemplate.update(offerTextUpdateQuery.toString(), new Object[] { vorlageid });
			
			if(auktionOfferTextCount>0){
				offerTextUpdateFlag=true;
			}
		}catch(Exception e){
			offerTextUpdateFlag=false;
		}
	
		return offerTextUpdateFlag;
	}
	
	public boolean saveAuktionsText(String offerText,String voucherText,String voucherValidityText,int validity,int changer,String offertext2,int vorlageid){
		//Timestamp timestamp = new Timestamp(date.getTime());
		String timestamp=commonValidations.getCurrentTimeStamp();
		boolean saveFlag=false;
		long auktionsTextid=0;
		try{
		namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
		StringBuilder saveAuktionsTextQuery = new StringBuilder("insert into ebay.auktions_texte (angebotstext,gutschein_text,gueltigkeit_text,gueltigkeit_tage,changer,change_date,angebotstext2)");

		saveAuktionsTextQuery.append(" values(:angebotstext,:gutschein_text,:gueltigkeit_text,:gueltigkeit_tage,:changer,:change_date,:angebotstext2)");
		
		Map<String, Object> auktionsTextMap = new HashMap<String, Object>();
		auktionsTextMap.put("angebotstext", offerText);
		auktionsTextMap.put("gutschein_text",voucherText);
		auktionsTextMap.put("gueltigkeit_text",voucherValidityText );
		auktionsTextMap.put("gueltigkeit_tage", validity);
		auktionsTextMap.put("changer",changer);
		auktionsTextMap.put("change_date",timestamp);
		auktionsTextMap.put("angebotstext2", offertext2);
		
		SqlParameterSource auktionsTextParamSource = new MapSqlParameterSource(auktionsTextMap);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		 this.namedJdbcTemplate.update(saveAuktionsTextQuery.toString(), auktionsTextParamSource,keyHolder);
		 auktionsTextid = keyHolder.getKey().longValue();
		 
		if(auktionsTextid>0){
			saveFlag=true;
			if(updateVorlageTexteid(auktionsTextid,vorlageid))
				saveFlag=true;
			else
				saveFlag=false;
		}
		
		}catch(Exception ate){
			saveFlag=false;
			ate.printStackTrace();
		}	
		
		logger.debug("Save date to auktions texte{}",saveFlag);
		
		return saveFlag;
	}
	
	public boolean updateVorlageTexteid(long auktionsTextid,int vorlageid){
		boolean auktionstextidflag=false;
		String timestamp=commonValidations.getCurrentTimeStamp();
		try{
		StringBuilder updateVorlagetextid=new StringBuilder("update ebay.vorlage set auktions_texte_id="+auktionsTextid+",geaendert='"+timestamp+"' where id=?");
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		int updatedRows= this.jdbcTemplate.update(updateVorlagetextid.toString(), new Object[] {vorlageid});
		
		if(updatedRows>0){
			auktionstextidflag=true;
		}
		
		}catch(Exception e){
			auktionstextidflag=false;
			e.printStackTrace();
		}
		return auktionstextidflag;
	}
	
	
	/*public boolean deleteLogoPic(TemplateEditRQ templateEditRq){
		boolean logoPicDelete=false;
		StringBuffer deleteLogoPic=new StringBuffer("delete from ebay.vorlage_x_che_logopicture where vorlage_id="+templateEditRq.getTemplateId());
		return logoPicDelete;
	}*/
	
	/*public boolean updateLogoPicture(TemplateEditRQ templateEditRq){
		boolean updateLogoPicture=false;
		logger.debug("This is logo picture updation");
		try{
		if(templateEditRq.isSetDescriptionPictures() && templateEditRq.getDescriptionPictures().isSetLogoPictureURL() && !templateEditRq.getDescriptionPictures().getLogoPictureURL().trim().isEmpty()){
			if(templateEditRq.getDescriptionPictures().getLogoPictureURL().contains("http://")){
				templateEditRq.getDescriptionPictures().setLogoPictureURL
				(templateEditRq.getDescriptionPictures().getLogoPictureURL().replaceFirst("http://", "https://"));
			}
			StringBuffer logoPicUpdateQuery=new StringBuffer("update ebay.vorlage_x_che_logopicture set imagepath='"+templateEditRq.getDescriptionPictures().getLogoPictureURL()+"' where vorlage_id =?");
			
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			logger.debug("Logo pic update Query "+logoPicUpdateQuery.toString());
			int logoPicUCount=this.jdbcTemplate.update(logoPicUpdateQuery.toString(), new Object[] { templateEditRq.getTemplateId() });
		
			if(logoPicUCount>0)
				updateLogoPicture=true;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return updateLogoPicture;
	}*/
	
	/*public boolean deleteGalleryPic(TemplateEditRQ templateEditRq){
		boolean galleryPicDelete=false;
		StringBuffer galleryPicDeleteQuery=new StringBuffer("delete from ebay.vorlage_x_che_galeriebild where vorlage_id="+templateEditRq.getTemplateId());
		
		return galleryPicDelete;
	}*/
	
	public boolean updateGalleryPic(TemplateEditRQ templateEditRq){
		boolean updateGalleryPic=false;
		try{
		if(templateEditRq.isSetPictureDetails() && templateEditRq.getPictureDetails().isSetGalleryURL() && !templateEditRq.getPictureDetails().getGalleryURL().trim().isEmpty()){
			/*if(templateEditRq.getPictureDetails().getGalleryURL().contains("http://")){
				templateEditRq.getPictureDetails().setGalleryURL
				(templateEditRq.getPictureDetails().getGalleryURL().replaceFirst("http://", "https://"));
			}*/
		StringBuffer galleryPicUpdateQuery=new StringBuffer("update ebay.vorlage_x_che_galeriebild set galeriebild_path='"+templateEditRq.getPictureDetails().getGalleryURL()+"' where vorlage_id=?");
		
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		logger.debug("Gallery pic update Query "+galleryPicUpdateQuery.toString());
		int galleryPicUCount=this.jdbcTemplate.update(galleryPicUpdateQuery.toString(), new Object[] { templateEditRq.getTemplateId() });
		if(galleryPicUCount>0)
			updateGalleryPic=true;
		
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return updateGalleryPic;
	  }
	
/*	public boolean updateVoucherPic(TemplateEditRQ templateEditRq){
		boolean updateVoucherPic=false;
		try{
		if(templateEditRq.isSetPictureDetails() && templateEditRq.getPictureDetails().isSetVoucherPictureURL() && !templateEditRq.getPictureDetails().getVoucherPictureURL().trim().isEmpty()){
		StringBuffer voucherPicUpdateQuery=new StringBuffer("update ebay.vorlage_x_che_voucherpicture set imagepath='"+templateEditRq.getPictureDetails().getVoucherPictureURL()+"' where vorlage_id=?");
		
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		logger.debug("Voucher pic update Query "+voucherPicUpdateQuery.toString());
		int voucherPicUCount=this.jdbcTemplate.update(voucherPicUpdateQuery.toString(), new Object[] { templateEditRq.getTemplateId() });
		if(voucherPicUCount>0)
			updateVoucherPic=true;
		
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return updateVoucherPic;
	  }
	*/
	
	public String getTemplateSiteid(int templateid){
		String ebaysiteString;
		try{
		logger.debug("inside DAO checking getting siteid");
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		String sql="SELECT ebaysiteid from ebay.vorlage where vorlage.id=?";
		int ebaysiteid=jdbcTemplate.queryForObject(sql, new Object[] { templateid },Integer.class);
		 ebaysiteString=String.valueOf(ebaysiteid);
		
		}catch(Exception e){
			e.printStackTrace();
			ebaysiteString=null;
		}
		return ebaysiteString;
	}
	
	public int getDesignTemplateId(int templateid){
		int designTemplateid=0;
		try{
			logger.debug("inside DAO checking getting design template id");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="SELECT template_id from ebay.vorlage where vorlage.id=?";
			designTemplateid=jdbcTemplate.queryForObject(sql, new Object[] { templateid },Integer.class);
			 
			
			}catch(Exception e){
				e.printStackTrace();
				
			}
		return designTemplateid;
	}
	
	public boolean checkAmenityId(int amenityId){
		boolean amenityIdStatus=false;
		try{
			logger.debug("inside DAO checking amenityid"+amenityId);
			jdbcTemplate=cusebedaJdbcTemplate.getJdbcTemplate();
			String sql="SELECT count(hofesoda.rubrikZuzzu.id)  FROM hofesoda.rubrikZuzzu where hofesoda.rubrikZuzzu.id=?";
			int amenityCount=jdbcTemplate.queryForObject(sql, new Object[] { amenityId },Integer.class);
			logger.debug("amenities count===="+amenityCount);
			if(amenityCount>0){
			 amenityIdStatus=true;
		 }
			
		}catch(Exception e){
			logger.debug("problem occured in amenity");
			e.printStackTrace();
		}
		return amenityIdStatus;
	}
	
	public double getStartPrice(int templateid){
		double startprice=0;
		try{
			
			logger.debug("inside DAO getting StartPrice ");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="SELECT  vorlage.startpreis FROM ebay.vorlage where vorlage.id=?";
			startprice=jdbcTemplate.queryForObject(sql, new Object[] { templateid },Double.class);
			logger.debug("Start price value"+startprice);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return startprice;
	}
	
	public double getRetailPrice(int templateId){
		double retailPrice=0;
           try{
			
			logger.debug("inside DAO getting StartPrice ");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="SELECT  vorlage.retailprice FROM ebay.vorlage where vorlage.id=?";
			retailPrice=jdbcTemplate.queryForObject(sql, new Object[] { templateId },Double.class);
			logger.debug("RetailPrice price value"+retailPrice);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return retailPrice;
	}
	
	public double getAuktionStartPrice(int auctionid){
		double startprice=0;
		try{
			
			logger.debug("inside DAO getting StartPrice ");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="SELECT  auktion.startpreis FROM ebay.auktion where auktion.id=?";
			startprice=jdbcTemplate.queryForObject(sql, new Object[] { auctionid },Double.class);
			logger.debug("Start price value"+startprice);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return startprice;
	}
	
	public double getEbaysofortkauf(int templateid){
		double ebaySofortkauf=0;
		try{
			
			logger.debug("inside DAO getting ebaysofortkauf,");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="SELECT  vorlage.ebaysofortkauf FROM ebay.vorlage where vorlage.id=?";
			ebaySofortkauf=jdbcTemplate.queryForObject(sql, new Object[] { templateid },Double.class);
			logger.debug("ebay sofortkauf value"+ebaySofortkauf);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ebaySofortkauf;
	}
	
	public double getAuktionEbaysofortkauf(int auctionid){
		double ebaySofortkauf=0;
		try{
			
			logger.debug("inside DAO getting ebaysofortkauf,");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="SELECT  auktion.ebaysofortkauf FROM ebay.auktion where auktion.id=?";
			ebaySofortkauf=jdbcTemplate.queryForObject(sql, new Object[] { auctionid },Double.class);
			logger.debug("ebay sofortkauf value"+ebaySofortkauf);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ebaySofortkauf;
	}
	
	public int getAuctionmasterTypeid(int templateid){
		int auctionmastertypeid=0;
		
		try{	
			logger.debug("inside DAO getting auctionmastertypeid");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="SELECT  vorlage.auctionMasterTypeID FROM ebay.vorlage where vorlage.id=?";
			auctionmastertypeid=jdbcTemplate.queryForObject(sql, new Object[] { templateid },Integer.class);
			logger.debug("AuctionmasterTypeid  value"+auctionmastertypeid);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return auctionmastertypeid;
	}
	
	public int getAuktionAuctionmasterTypeid(int auctionid){
		int auctionmastertypeid=0;
		
		try{	
			logger.debug("inside DAO getting auctionmastertypeid");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="SELECT  auktion.AuctionMasterTypeID FROM ebay.auktion where auktion.id=?";
			auctionmastertypeid=jdbcTemplate.queryForObject(sql, new Object[] { auctionid },Integer.class);
			logger.debug("AuctionmasterTypeid  value"+auctionmastertypeid);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return auctionmastertypeid;
	}
	
	//delete and insert
	public boolean deleteAmenities(int vorlageid){
		boolean deleteAmenityStatus=false;
		
		try{	
			logger.debug("inside delete amenities");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="delete from ebay.vorlage_arrangement_rubrik where vorlage_id=?";
			int amenitiesCount=jdbcTemplate.update(sql, new Object[] { vorlageid });
			logger.debug("No of Amenities delete"+amenitiesCount);
			
			if(amenitiesCount>0){
				deleteAmenityStatus=true;
			}
			
		}catch(Exception e){
			logger.error("Exception at delete amenities"+e);
			//e.printStackTrace();
		}
		
		return deleteAmenityStatus;
	}
	
	public boolean deleteDescPics(int vorlageid){
		boolean descPicsStatus=false;
		
		try{	
			logger.debug("inside delete desc pics");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="delete from ebay.che_vorlagebilder where vorlage_id=?";
			int descPicsCount=jdbcTemplate.update(sql, new Object[] { vorlageid });
			logger.debug("No of desc pics delete"+descPicsCount);
			
			if(descPicsCount>0){
				descPicsStatus=true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return descPicsStatus;
	}
	
	public boolean editDescriptionPictures(int vorlageid,List<String> descUpics,int objectid){
		boolean descPicsFlag=false;
		
		try{
			//List<String> descUpics=descPics.getDescriptionPictureURL();
			
			//List<String> descUpics=templateEditRq.getDescriptionPictures().getDescriptionPictureURL();
			
			for(int i=1;i<=descUpics.size();i++){
				
				logger.debug("This is loop number=========="+i);
				namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
				StringBuffer saveDescriptionPicsQuery = new StringBuffer("insert into ebay.che_vorlagebilder(vorlage_id,image_path,nummerierung,cusebeda_objekt_id)");

				saveDescriptionPicsQuery.append(" values(:vorlage_id,:imagepath,:sequenceid,:objectid)");
				
				Map<String, Object> descriptionPics = new HashMap<String, Object>();
				descriptionPics.put("vorlage_id",vorlageid);
				/*if(descUpics.get(i-1).contains("http://")){
					descUpics.set(i-1, descUpics.get(i-1).replaceFirst("http://", "https://"));
				}*/
				descriptionPics.put("imagepath",descUpics.get(i-1));
				descriptionPics.put("sequenceid", i);
				descriptionPics.put("objectid", objectid);
				
				SqlParameterSource descPicsParamSource = new MapSqlParameterSource(descriptionPics);
				
				this.namedJdbcTemplate.update(saveDescriptionPicsQuery.toString(), descPicsParamSource);
				
				}
			
				descPicsFlag=true;
				
			}catch(Exception e){
				descPicsFlag=false;
				e.printStackTrace();
			}
		return descPicsFlag;
		
	}
	
	public boolean deleteOfferSliderPics(int vorlageid){
		boolean deleteOfferSliderPics=false;
		
		try{	
			logger.debug("inside delete offer s pics");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="delete from ebay.vorlage_x_che_offerslider where vorlage_id=?";
			int offerSPicsCount=jdbcTemplate.update(sql, new Object[] { vorlageid });
			logger.debug("No of offer s pics delete"+offerSPicsCount);
			
			if(offerSPicsCount>0){
				deleteOfferSliderPics=true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return deleteOfferSliderPics;
	}
	
	public boolean editOfferSliderPics(int vorlageid,List<String> offerSliderPics,int objectid){
		boolean editOfferSliderPicsFlag=false;
		
		try{
			for(int j=1;j<=offerSliderPics.size();j++){
				try{
				namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
				StringBuffer saveOfferSliderPicsQuery = new StringBuffer("insert into ebay.vorlage_x_che_offerslider(vorlage_id,image_path,cusebeda_objekt_id,sequenceId)");

				saveOfferSliderPicsQuery.append(" values(:vorlage_id,:imagepath,:objectid,:sequenceid)");
				
				Map<String, Object> offerSliderPicsMap = new HashMap<String, Object>();
				offerSliderPicsMap.put("vorlage_id", vorlageid);
				/*if(offerSliderPics.get(j-1).contains("http://")){
					offerSliderPics.set(j-1, offerSliderPics.get(j-1).replaceFirst("http://", "https://"));
				}*/
				offerSliderPicsMap.put("imagepath",offerSliderPics.get(j-1));
				offerSliderPicsMap.put("objectid", objectid);
				offerSliderPicsMap.put("sequenceid", j);
				
				
				SqlParameterSource offerSliderPicsParamSource = new MapSqlParameterSource(offerSliderPicsMap);
				
				this.namedJdbcTemplate.update(saveOfferSliderPicsQuery.toString(), offerSliderPicsParamSource);
				editOfferSliderPicsFlag=true;
				}catch(Exception ose){
					editOfferSliderPicsFlag=false;
					ose.printStackTrace();
				}
				}
			
		
			}catch(Exception e){
				editOfferSliderPicsFlag=false;
				e.printStackTrace();
			}
			
		return editOfferSliderPicsFlag;
	}
	
	public boolean deleteObjectSliderPics(int vorlageid){
		
		boolean deleteObjectSlider=false;
		try{	
			logger.debug("inside delete objSlider pics");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="delete from ebay.vorlage_x_che_objectslider where vorlage_id=?";
			int objSPicsCount=jdbcTemplate.update(sql, new Object[] { vorlageid });
			logger.debug("No of objSlider pics delete"+objSPicsCount);
			
			if(objSPicsCount>0){
				deleteObjectSlider=true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return deleteObjectSlider;
	}
	
	public boolean editObjectSliderPics(int vorlageid,List<String> objSliderPics,int objectid){
		boolean obsPicsFlag=false;
		try{
			for(int k=1;k<=objSliderPics.size();k++){
				try{
				namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
			StringBuffer saveObjectSliderPicsQuery = new StringBuffer("insert into ebay.vorlage_x_che_objectslider(vorlage_id,image_path,cusebeda_objekt_id,sequenceId)");

			saveObjectSliderPicsQuery.append(" values(:vorlage_id,:imagepath,:objectid,:sequenceid)");
			
			Map<String, Object> objectSliderPics = new HashMap<String, Object>();
			objectSliderPics.put("vorlage_id", vorlageid);
			/*if(objSliderPics.get(k-1).contains("http://")){
				objSliderPics.set(k-1, objSliderPics.get(k-1).replaceFirst("http://", "https://"));
			}*/

			objectSliderPics.put("imagepath",objSliderPics.get(k-1));
			objectSliderPics.put("objectid", objectid);
			objectSliderPics.put("sequenceid", k);
			
			
			SqlParameterSource objectSliderPicsParamSource = new MapSqlParameterSource(objectSliderPics);
			
			this.namedJdbcTemplate.update(saveObjectSliderPicsQuery.toString(), objectSliderPicsParamSource);
			obsPicsFlag=true;
				}catch(Exception osp){
					obsPicsFlag=false;
					osp.printStackTrace();
				}
			}
			
		}catch(Exception e){
			obsPicsFlag=false;
			e.printStackTrace();
		}
	return obsPicsFlag;
		
	}
	
	
	public boolean deleteItemPictures(int vorlageid){
		boolean itemPicsDFlag=false;
		try{	
			logger.debug("inside delete item pics");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="delete from ebay.vorlage_x_che_auktionbild where vorlage_id=?";
			int itemPicsCount=jdbcTemplate.update(sql, new Object[] { vorlageid });
			logger.debug("No of item pics delete"+itemPicsCount);
			
			if(itemPicsCount>0){
				itemPicsDFlag=true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return itemPicsDFlag;
	}
	
	public boolean editItemPictures(int vorlageid,List<String> itemPics,int objectid){
		boolean itemPicsStatus=false;
		try{
			int picsCount=0;
			for(int i=1;i<=itemPics.size();i++){
				try{
				namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
			StringBuffer saveItemPicsQuery = new StringBuffer("insert into ebay.vorlage_x_che_auktionbild(vorlage_id,sequenceId,auktionbildpath,cusebeda_objekt_id)");

			saveItemPicsQuery.append(" values(:vorlage_id,:sequenceid,:imagepath,:objectid)");
			
			Map<String, Object> itempics = new HashMap<String, Object>();
			itempics.put("vorlage_id", vorlageid);
			itempics.put("sequenceid", i);
			/*if(itemPics.get(i-1).contains("http://")){
				itemPics.set(i-1, itemPics.get(i-1).replaceFirst("http://", "https://"));
			}*/
			itempics.put("imagepath",itemPics.get(i-1));
			itempics.put("objectid", objectid);
			
			SqlParameterSource itemPicsParamSource = new MapSqlParameterSource(itempics);
			int itempic=0;
					if(!itemPics.get(i-1).isEmpty())
					itempic=this.namedJdbcTemplate.update(saveItemPicsQuery.toString(), itemPicsParamSource);
			
					logger.debug("item pics updated rows value isss{}",itempic);
			picsCount++;
				}catch(Exception picse){
					picse.printStackTrace();
				}
			
			}
			itemPicsStatus=true;
			if(picsCount==itemPics.size())
				logger.debug("save item picture details saved successfully");
			else
				logger.debug("save item picture details not saved");
			
			
		}catch(Exception e){
			itemPicsStatus=false;
			e.printStackTrace();
		}
		return itemPicsStatus;
	}
	
	
	
/*	public boolean deletePrimaryItemSpecifics(int templateId){
		boolean primarySpecificsFlag=false;
		
		
		
	
		return primarySpecificsFlag;
	}*/
	
	public boolean deleteSpecValues(int specnameid){
		boolean specValueFlag=false;
		try{	
			logger.debug("inside delete spec value");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="delete from ebay.vorlage_x_itemSpecValue where item_specnameID=?";
			int specVCount=jdbcTemplate.update(sql, new Object[] { specnameid });
			logger.debug("No of spec value delete"+specVCount);
			
			if(specVCount>0){
				specValueFlag=true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return specValueFlag;
	}
		
	public boolean deleteSpecNames(int specid){
		boolean itemSpecNamesFlag=false;
		try{	
			logger.debug("inside delete item spec names");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="delete from ebay.vorlage_x_itemSpecName where item_specID=?";
			int specNamesCount=jdbcTemplate.update(sql, new Object[] { specid });
			logger.debug("No of item spec names delete"+specNamesCount);
			
			if(specNamesCount>0){
				itemSpecNamesFlag=true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return itemSpecNamesFlag;
	}
	
	public boolean deleteItemSpecs(int vorlageid,int categoryLevel){
		boolean itemPicsDFlag=false;
		try{	
			logger.debug("inside delete item Specs");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="delete from ebay.vorlage_x_itemSpecifics where auctionMasterID=? and categoryLevel="+categoryLevel;
			int itemPicsCount=jdbcTemplate.update(sql, new Object[] { vorlageid });
			logger.debug("No of item Specs delete"+itemPicsCount);
			
			if(itemPicsCount>0){
				itemPicsDFlag=true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return itemPicsDFlag;
	}
	
	public int getObjectid(int templateid){
		int objectid=0;
		
		logger.debug("check object id");
		try{
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		String sql="SELECT cusebeda_objekt_id FROM ebay.vorlage where id=?";
		 objectid=jdbcTemplate.queryForObject(sql, new Object[] { templateid },Integer.class);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return objectid;
	}
	
	
	public boolean saveShopCategories(StoreCategoriesType storeCategories,int templateid,int shopObjectid){
		boolean shopCategoriesStatus=false;
		
		try{
			namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
		StringBuffer saveShopCatQuery = new StringBuffer("insert into ebay.vorlage_x_shop(AuctionMasterID,ShopObjectID,ShopCategoryID,ShopCategory2ID)");

		saveShopCatQuery.append(" values(:AuctionMasterID,:ShopObjectID,:ShopCategoryID,:ShopCategory2ID)");
		
		Map<String, Object> shopCatsMap = new HashMap<String, Object>();
		shopCatsMap.put("AuctionMasterID",templateid);
		shopCatsMap.put("ShopObjectID",shopObjectid);
		shopCatsMap.put("ShopCategoryID",storeCategories.getStoreCategoryId());
		shopCatsMap.put("ShopCategory2ID", storeCategories.getStoreCategory2Id());
		
		SqlParameterSource shopCatsParamSource = new MapSqlParameterSource(shopCatsMap);
			int shopcats;
				shopcats=this.namedJdbcTemplate.update(saveShopCatQuery.toString(), shopCatsParamSource);
		
				if(shopcats>0){
					shopCategoriesStatus=true;
				}
				
			}catch(Exception shopCatsError){
				shopCatsError.printStackTrace();
			}
		
		return shopCategoriesStatus;
	}
	
	public boolean saveCollectionObject(int templateid,int collectionObjectid){
		boolean collectionStatus=false;
		
		try{
			namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
		StringBuffer saveCollectionQuery = new StringBuffer("insert into ebay.vorlage_x_collectionAccount(AuctionMasterID,CollectionAccountObjectID)");

		saveCollectionQuery.append(" values(:AuctionMasterID,:CollectionAccountObjectID)");
		
		Map<String, Object> collectionMap = new HashMap<String, Object>();
		collectionMap.put("AuctionMasterID",templateid);
		collectionMap.put("CollectionAccountObjectID",collectionObjectid);
		
		
		SqlParameterSource collectionParamSource = new MapSqlParameterSource(collectionMap);
			int collection;
			collection=this.namedJdbcTemplate.update(saveCollectionQuery.toString(), collectionParamSource);
		
				if(collection>0){
					collectionStatus=true;
				}
				
			}catch(Exception collectionError){
				collectionError.printStackTrace();
			}
		
		return collectionStatus;
	}
	
	public int getShopObjectId(int vorlageid){
		//boolean shopCategoryStatus=false;
		int shopObje=0;
		try{
			
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="select ShopObjectID from  ebay.vorlage_x_shop where AuctionMasterID=?";
			shopObje =jdbcTemplate.queryForObject(sql, new Object[] {vorlageid},Integer.class);
			
			logger.debug("Checking shop categories count"+shopObje);
			
		}catch(Exception e){
			//shopCategoryStatus=false;
			e.printStackTrace();
		}
		
		return shopObje;
	}
	
	
public boolean updateShopCategories(TemplateEditRQ templateEditRQ,int vorlageid,int shopObjectid,StoreCategoriesType storeCategories){
		boolean shopcatsUpdateStatus=false;
		String storeCat=null;
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		long shopCat2id=0;
		
		if(storeCategories.isSetStoreCategory2Id()){
			try{
			shopCat2id=Long.parseLong(storeCategories.getStoreCategory2Id());
			}catch(NumberFormatException nfe){
				nfe.printStackTrace();
			}
			
		}
			storeCat=checkStoreCatRemove(templateEditRQ, storeCat);
			//storeCategories.setStoreCategory2Id(storeCat);
		
			
		String updateShopCatsSql=null;
		try{
			updateShopCatsSql	="UPDATE ebay.vorlage_x_shop SET ShopCategoryID = "+storeCategories.getStoreCategoryId() +", ShopCategory2ID = "+shopCat2id+" ,ShopObjectID="+shopObjectid+" WHERE AuctionMasterID = ?";
		if(storeCat!=null){
			updateShopCatsSql="UPDATE ebay.vorlage_x_shop SET ShopCategory2ID = "+storeCat+" WHERE AuctionMasterID = ?";
		}
		int vorlageShopUpdate =jdbcTemplate.update(updateShopCatsSql, new Object[] { vorlageid });
		
		if(vorlageShopUpdate>0){
			shopcatsUpdateStatus=true;
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	 
		if(shopcatsUpdateStatus)
			updateFutureShopOffersOfShopObjectid(templateEditRQ,vorlageid,shopObjectid,storeCategories);
			
		
		return shopcatsUpdateStatus;
		
	}

	public boolean updateForRemoveFields(TemplateEditRQ templateEditRQ){
		boolean uppdatedStatus=false;
		try{
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
	
	String storeCat=null;
	
	storeCat=checkStoreCatRemove(templateEditRQ, storeCat);
	String updateShopCatsSql=null;
	if(storeCat!=null){

		updateShopCatsSql	="UPDATE ebay.vorlage_x_shop SET ShopCategory2ID = "+storeCat+" WHERE AuctionMasterID = ?";
		int vorlageShopUpdate =jdbcTemplate.update(updateShopCatsSql, new Object[] { templateEditRQ.getTemplateId() });
		if(vorlageShopUpdate>0){
			uppdatedStatus=true;
		}
	}}catch(Exception e){
		e.printStackTrace();
	}
	return uppdatedStatus;
}
	
	public void updateFutureShopOffersOfShopObjectid(TemplateEditRQ templateEditRq,int vorlageid,int shopObjectid,StoreCategoriesType storeCategories){
		
		boolean shopcatsUpdateStatus=false;
		
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		long shopCat2id=0;
		if(storeCategories.isSetStoreCategory2Id()){
			try{
			shopCat2id=Long.parseLong(storeCategories.getStoreCategory2Id());
			}catch(NumberFormatException nfe){
				nfe.printStackTrace();
			}
		}
		String sencodatCat=null;
		sencodatCat=checkSecondaryCatRemove(templateEditRq ,sencodatCat);
		String updateFutureShopCatsSql=null;
		try{
			if(null!=sencodatCat){
				updateFutureShopCatsSql="UPDATE ebay.auction_x_shopCategory JOIN  ebay.auktion on AuctionID = id  set  ShopObjectID = "+shopObjectid+",  ShopCategoryID = "+storeCategories.getStoreCategoryId()+",  ShopCategoryID2 = "+sencodatCat+" WHERE  auktion.vorlage_id = ? AND auktion.status = 0";
			}else{
			updateFutureShopCatsSql="UPDATE ebay.auction_x_shopCategory JOIN  ebay.auktion on AuctionID = id  set  ShopObjectID = "+shopObjectid+",  ShopCategoryID = "+storeCategories.getStoreCategoryId()+",  ShopCategoryID2 = "+shopCat2id+" WHERE  auktion.vorlage_id = ? AND auktion.status = 0";
			}
		int auctionShopUpdate =jdbcTemplate.update(updateFutureShopCatsSql, new Object[] { vorlageid });
		
		if(auctionShopUpdate>0){
			shopcatsUpdateStatus=true;
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.debug("Future offers shop categories changed status{}",shopcatsUpdateStatus);
	}
	
	
	public void deleteShopCategories(int vorlageid){
		//boolean deleteStatus=false;
		logger.debug("This is delete shop categories"+vorlageid);
		try{
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String deleteShopCatsSql="Delete from ebay.vorlage_x_shop where vorlage_x_shop.AuctionMasterID=?";
			int vorlageShopDelete =jdbcTemplate.update(deleteShopCatsSql, new Object[] { vorlageid });
			if(vorlageShopDelete>0){
				logger.debug("Vorlage id deleted from table "+vorlageid);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("Exception is occured in delete categories ");
		}
		
	}
	
	public int getVorlageId(long itemid){
		
		int vorlageid=0;
		try{
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="select vorlage_id from  ebay.auktion_sich where ebayitemid=?";
			vorlageid =jdbcTemplate.queryForObject(sql, new Object[] {itemid},Integer.class);
			logger.debug("get vorlageid"+vorlageid);
			
		}catch(Exception e){		
			e.printStackTrace();
		}
		return vorlageid;
	}
	
	public int getThemeId(int objId){
		int themeid=0;
		
		try{
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String sql="select pdf_template_id from ebay.gutschein_x_objekt where cusebeda_objekt_id=?";
			themeid =jdbcTemplate.queryForObject(sql, new Object[] {objId},Integer.class);
			logger.debug("get vorlageid"+themeid);
			
		}catch(Exception e){		
			e.printStackTrace();
		}
	
		return themeid;
	}
	
	
	public int getLangidByItemid(long ebayitemid){
		int languageid=0;
		
		try{
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String langSql="select e.cusebeda_sprache_id from ebay.ebaysite e join ebay.auktion a on e.id=a.ebaysiteid where a.ebayitemid=?";
			languageid =jdbcTemplate.queryForObject(langSql, new Object[] {ebayitemid},Integer.class);
			logger.debug("get languageid"+languageid);
			
		}catch(Exception e){		
			e.printStackTrace();
		}
		
		return languageid;
	}
	
	public int saveCheVoucherPicture(int vorlageid,String imagepath,int objectid){
		int cheVoucherServicePic=0;
		
		try{
		namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
		StringBuffer saveVoucherPicQuery = new StringBuffer("insert into ebay.vorlage_x_che_voucherpicture (vorlage_id,imagepath,cusebeda_objekt_id) ");

		saveVoucherPicQuery.append(" values(:vorlage_id,:imagepath,:cusebeda_objekt_id)");
		
		Map<String, Object> voucherPicMap = new HashMap<String, Object>();
		voucherPicMap.put("vorlage_id",vorlageid);
		voucherPicMap.put("imagepath",imagepath);
		voucherPicMap.put("cusebeda_objekt_id",objectid);
		
		
		SqlParameterSource voucherPicSource = new MapSqlParameterSource(voucherPicMap);
			
			cheVoucherServicePic=this.namedJdbcTemplate.update(saveVoucherPicQuery.toString(), voucherPicSource);
						
			}catch(Exception voucherPicsError){
				voucherPicsError.printStackTrace();
			}
		
		return cheVoucherServicePic;
	}
	
	public int saveVoucherThemeId(int objectid,int themeid){
		int themeStatus=0;
		
		try{
		namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
		StringBuffer saveVoucherThemeQuery = new StringBuffer("insert into ebay.gutschein_x_objekt (cusebeda_objekt_id,pdf_template_id) ");

		saveVoucherThemeQuery.append(" values(:cusebeda_objekt_id,:pdf_template_id)");
		
		Map<String, Object> voucherThemeMap = new HashMap<String, Object>();
		voucherThemeMap.put("cusebeda_objekt_id",objectid);
		voucherThemeMap.put("pdf_template_id",themeid);
		
		SqlParameterSource voucherPicSource = new MapSqlParameterSource(voucherThemeMap);
			
		themeStatus=this.namedJdbcTemplate.update(saveVoucherThemeQuery.toString(), voucherPicSource);
						
			}catch(Exception voucherPicsError){
				voucherPicsError.printStackTrace();
			}
		
		return themeStatus;
	}
	
	public int saveUpdateThemeId(int objectid,int themeid){
		
		int updateThemeid =0;
		try{
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		String updateThemeQuery="update ebay.gutschein_x_objekt set pdf_template_id="+themeid+" where cusebeda_objekt_id=?";
		 updateThemeid =jdbcTemplate.update(updateThemeQuery, new Object[] { objectid });
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return updateThemeid;
		
		
	}
	
	public boolean checkLiveOffers(int templateid){
		boolean checkLiveOffers=false;
		logger.debug("This is enter to live offers fixed price offers");
		int countOfliveOffers=0;
		try{
			logger.debug("This is enter to live offers checking");
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			String checkLiveOffersQueryStore="select count(ebayitemid) from ebay.auktion where vorlage_id=? and status>=1";
			countOfliveOffers =jdbcTemplate.queryForObject(checkLiveOffersQueryStore, new Object[] { templateid },Integer.class);
			 
			if(countOfliveOffers>0){
				checkLiveOffers=true;
			}
			}catch(Exception e){
				checkLiveOffers=false;
			logger.debug("problem occered inside checking live offers count");			
				e.printStackTrace();
			}
		return checkLiveOffers;
	}
	private String checkRetailRemoveFields(TemplateEditRQ templateEditRQ) {
		String retailPrice=null;
		if(null!=templateEditRQ.getRemoveFields() && null!=templateEditRQ.getRemoveFields().getRemoveField() && templateEditRQ.getRemoveFields().getRemoveField().size()>0){
		allowedStrings=templateEditRQ.getRemoveFields().getRemoveField();
		if(allowedStrings.contains("RetailPrice")){
			retailPrice="0";
		}
		}
		return retailPrice;
	}
	
	public String checkSubTitleRemove(TemplateEditRQ templateEditRQ,
			String subTitle){
		if(null!=templateEditRQ.getRemoveFields() && null!=templateEditRQ.getRemoveFields().getRemoveField() && templateEditRQ.getRemoveFields().getRemoveField().size()>0){
			allowedStrings=templateEditRQ.getRemoveFields().getRemoveField();
			if(allowedStrings.contains("OfferSubTitle")){
				subTitle="";
			}
			}
		return subTitle;
	}
	
	public String checkStoreCatRemove(TemplateEditRQ templateEditRQ, String storeCat2){
		if(null!=templateEditRQ.getRemoveFields() && null!=templateEditRQ.getRemoveFields().getRemoveField() && templateEditRQ.getRemoveFields().getRemoveField().size()>0){
			allowedStrings=templateEditRQ.getRemoveFields().getRemoveField();
			if(allowedStrings.contains("StoreCategory2Id")){
				storeCat2="0";
			}
			}
		return storeCat2;
	}
	public String checkSecondaryCatRemove(TemplateEditRQ templateEditRQ ,String SecondaryCat2){
		
		if(null!=templateEditRQ.getRemoveFields() && null!=templateEditRQ.getRemoveFields().getRemoveField() && templateEditRQ.getRemoveFields().getRemoveField().size()>0){
			allowedStrings=templateEditRQ.getRemoveFields().getRemoveField();
			if(allowedStrings.contains("SecondaryCategoryId")){
				SecondaryCat2="0";
			}
			}
		return SecondaryCat2;
	}
	
	public boolean checkProductValidity(int productId, String startDate){
		
		logger.debug("productId for validity is :{}",productId);
		
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		
		int validity = 0;
		String validityEndDate = null;
		boolean flag = false;
	
		
		try{
			
		
			if(productId !=0){
				
				logger.debug("Inside try for validity period");
				
				String validitySql = "select count(*) from ebay_product.validityPeriods vp join ebay_product.product_validity pv on pv.validityPeriodsId = vp.id where  pv.productId = "+productId +" and date(vp.endDate)>='"+startDate+"' and pv.status=1";
			
				validity = jdbcTemplate.queryForInt(validitySql.toString());
				
				logger.debug("validity is:{}",validity);
				
				if(validity >0){
					
					flag = true;
				
				}
			}
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		
		return flag;
	}
	
}