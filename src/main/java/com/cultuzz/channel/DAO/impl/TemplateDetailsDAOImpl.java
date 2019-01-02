package com.cultuzz.channel.DAO.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cultuzz.channel.DAO.TemplateDetailsDAO;
import com.cultuzz.channel.XMLpojo.TemplateDetailsRS;
import com.cultuzz.channel.jdbcTemplate.JDBCTemplate;
import com.cultuzz.channel.template.pojo.TemplateMapper;
import com.cultuzz.channel.template.pojo.Vorlage;
import com.cultuzz.channel.template.pojo.VorlageMapper;
import com.cultuzz.channel.util.CommonValidations;



@Repository
public class TemplateDetailsDAOImpl  implements  TemplateDetailsDAO{

	/* this method used to fetch the offer related details */
	
	
	@Autowired
	@Qualifier("ebayTemplate")
	private JDBCTemplate ebayJdbcTemplate;	
	
	
	@Autowired
	@Qualifier("cusebedaTemplate")
	private JDBCTemplate cusebedaJdbcTemplate;
	
	TemplateDetailsRS templateDetailsRS = new TemplateDetailsRS();
	
	

	
		
	/*@Autowired
	@Qualifier("cusebedaTemplate")
	private JDBCTemplate cusebedaJdbcTemplate;*/
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TemplateDetailsDAOImpl.class);
	private JdbcTemplate jdbcTemplate;	
	
	
	/*public Offer getOfferDetails(int offerId) {
		// TODO Auto-generated method stub
	
		Offer offer = new Offer();
		
		
		LOGGER.debug("offerId is :{}",offerId);
		
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		
		String sql="select cusebeda_objekt_id,id,ebayitemid,ebayueberschrift,untertitel,startdatum,endpreis,dauer,quantity,startpreis,ebaysofortkauf,AuctionMasterTypeID,ebaysiteid,hoechstbietender,DATE_ADD(auktion.startdatum, INTERVAL auktion.dauer DAY) as enddate from ebay.auktion where id =?";
		
		try{
		offer = jdbcTemplate.queryForObject(sql.toString(),
				new Object[] {offerId}, new OfferMapper());
		
		LOGGER.debug("offer in offerDAO is:{}",offer);
		        
		   }catch(EmptyResultDataAccessException e){
        	  
			   	e.printStackTrace();
          
                offer = null;		   
		   }

		return offer;

	   }*/
	public boolean checkTemplateId(int templateId,int objectId){
		boolean templateDeleteflag=false;
		LOGGER.debug("inside template checking inside Template Details DAO");
		try{
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		//String queryForDelete="select count(auktion.id) from ebay.auktion where auktion.vorlage_id=? and (status=0 OR status=-1)";
		//String queryForTemplate="select count(*) from ebay.vorlage v join ebay.auktion a on v.id=a.vorlage_id where v.id=? and a.cusebeda_objekt=? ";
		String queryForTemplate="select count(*) from ebay.vorlage where id=? and cusebeda_objekt_id=? ";
		int templateid=jdbcTemplate.queryForInt(queryForTemplate,new Object[] {templateId,objectId});
		
		LOGGER.debug("no of auktionids for templateid"+templateid);
		
		if(templateid>0)
			templateDeleteflag=true;
		}catch(Exception e){
			templateDeleteflag=false;
			LOGGER.error("Error occured"+e);
			//e.printStackTrace();
		}
		
		return templateDeleteflag;
		
	}
	
	// Get the Template Id and fetch the details
	public TemplateDetailsRS getTemplateDetails(int templateId) {
		// TODO Auto-generated method stub
	
		
		
		LOGGER.debug("templateId is :{}",templateId);
		//System.out.println("ebayJdbc Template::::::::::::::::::%%%%%%%%%"+ebayJdbcTemplate);
		 
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		
		
		String sql="select ebaysiteid,bezeichnung,cusebeda_objekt_id,template_id,erstellt,geaendert,status,ebayoptionen,ebaybetreffzeile,untertitel,ebaykategorieid1,ebaykategorieid2,ueberschrift,ueberschrift2,startpreis,ebaysofortkauf,arrangement_id,buchungsschluessel_gueltigkeit,auctionMasterTypeID,third_party_uniqueCodes,retailprice from ebay.vorlage where id =?";
		
	
		try{
			/*offerDetailsRS = jdbcTemplate.queryForObject(sql.toString(),
					new Object[] { offerId}, new OfferMapper());*/
			
			templateDetailsRS =jdbcTemplate.queryForObject(sql.toString(),new Object[] { templateId},new TemplateMapper());

			
			/*Customer customer = (Customer)getJdbcTemplate().queryForObject(
					sql, new Object[] { custId },
		
		LOGGER.debug("template in TemplateDAO is:{}",templateId);*/
		        
		   }catch(EmptyResultDataAccessException e){
			   LOGGER.error("Error occured"+e);
			   	//e.printStackTrace();
          
			   	templateDetailsRS = null;		   
		   }

		return templateDetailsRS;

	   }
	
	
	// Get Product Details 
		public List<Map<String, Object>> getArrangementDetails(int templateId) {
		// TODO Auto-generated method stub
	
		
		
		LOGGER.debug("templateId is :{}",templateId);
		//System.out.println("ebayJdbc Template::::::::::::::::::%%%%%%%%%"+ebayJdbcTemplate);
		// System.out.println("Template Id is $$$$$$$$$$$$$$$$ "+templateId);
		jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
		
		
		String sql="select personen,naechte,hofesoda_zimmerart_id,hofesoda_verpflegung_id from ebay.vorlagen_arrangement where vorlage_id=?";
		
		List<Map<String, Object>> arrangementDetails=null;
		try{
			/*offerDetailsRS = jdbcTemplate.queryForObject(sql.toString(),
					new Object[] { offerId}, new OfferMapper());*/
			
			
				// TODO Auto-generated method stub
				jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
				
				 arrangementDetails = jdbcTemplate.queryForList(sql,new Object[] { templateId });
				 
//			     if(arrangementDetails.size()==0){
//			     String sqlProduct=""	 
//			    	 
//			     }
				 
			}

			
			/*Customer customer = (Customer)getJdbcTemplate().queryForObject(
					sql, new Object[] { custId },
		
		LOGGER.debug("template in TemplateDAO is:{}",templateId);*/
		        
		   catch(Exception e){
			   LOGGER.error("Error occured"+e);
			   	//e.printStackTrace();
          
			   	templateDetailsRS = null;		   
		   }

		return arrangementDetails;

	   }
		// Get Voucher Details
		public List getVoucherDetails(int templateId)
		{
			LOGGER.debug("templateId is :{}",templateId);
			//System.out.println("ebayJdbc Template::::::::::::::::::%%%%%%%%%"+ebayJdbcTemplate);
			 
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			
			
			String sql="select gutschein_text,gueltigkeit_text,gueltigkeit from ebay.vorlage_gutscheine where vorlage_id=?";
			
			List<Map<String, Object>> voucherDetails=null;
			try{
				/*offerDetailsRS = jdbcTemplate.queryForObject(sql.toString(),
						new Object[] { offerId}, new OfferMapper());*/
				
				
					// TODO Auto-generated method stub
					jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
					
					 voucherDetails = jdbcTemplate.queryForList(sql,new Object[] { templateId });
				
				}

				
				/*Customer customer = (Customer)getJdbcTemplate().queryForObject(
						sql, new Object[] { custId },
			
			LOGGER.debug("template in TemplateDAO is:{}",templateId);*/
			        
			   catch(Exception e){
				   LOGGER.error("Error occured"+e);
				   	//e.printStackTrace();
	          
				   	templateDetailsRS = null;		   
			   }

			return voucherDetails;

		}
     
		// Get Offer Text
		public List getOfferTextDetails(int templateId)
		{
			LOGGER.debug("templateId is :{}",templateId);
			//System.out.println("ebayJdbc Template::::::::::::::::::%%%%%%%%%"+ebayJdbcTemplate);
			 
			jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
			
			
			String sql="select text,text2, ueberschrift,ueberschrift2  from ebay.vorlage where id =?";
			
			List<Map<String, Object>> offerTextDetails=null;
			try{
				/*offerDetailsRS = jdbcTemplate.queryForObject(sql.toString(),
						new Object[] { offerId}, new OfferMapper());*/
				
				
					// TODO Auto-generated method stub
					jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
					
					 offerTextDetails = jdbcTemplate.queryForList(sql,new Object[] { templateId });
				
				}

				
				/*Customer customer = (Customer)getJdbcTemplate().queryForObject(
						sql, new Object[] { custId },
			
			LOGGER.debug("template in TemplateDAO is:{}",templateId);*/
			        
			   catch(Exception e){
				   LOGGER.error("Error occured"+e);
				   	//e.printStackTrace();
	          
				   	templateDetailsRS = null;		   
			   }

			return offerTextDetails;

		}
		
		
		// To Get the Gallery Pic for CHE source template
		public String getGalleryPicture(int templateId) {

			LOGGER.debug("Gallery Pic Template Details Template id is :{}",
					templateId);
			String galleryUrl;
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			String sql = "select galeriebild_path from ebay.vorlage_x_che_galeriebild where vorlage_id=?";

			try {

				galleryUrl = (String) jdbcTemplate.queryForObject(sql,
						new Object[] { templateId }, String.class);

				LOGGER.debug("Gallery Url is :{}", galleryUrl);

				return galleryUrl;
			} catch (EmptyResultDataAccessException e) {
				LOGGER.error("Error occured"+e);
				//e.printStackTrace();
				return null;
			}

		}
		
		// To Get the Voucher Pic for CHE source template
				public String getVoucherPicture(int templateId) {

					LOGGER.debug("Voucher Pic Template Details Template id is :{}",
							templateId);
					String voucherUrl;
					jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
					String sql = "select imagepath from ebay.vorlage_x_che_voucherpicture where vorlage_id=?";

					try {

						voucherUrl = (String) jdbcTemplate.queryForObject(sql,
								new Object[] { templateId }, String.class);

						LOGGER.debug("Voucher Url is :{}", voucherUrl);

						return voucherUrl;
					} catch (EmptyResultDataAccessException e) {
						LOGGER.error("Error occured"+e);
						//e.printStackTrace();
						return null;
					}

				}

		public List getItemPictures(int templateId) {

			LOGGER.debug("Item Pictures templateId is :{}", templateId);
			//System.out.println("ebayJdbc Template::::::::::::::::::%%%%%%%%%"
			//		+ ebayJdbcTemplate);

			String auctionPicsSql = "select auktionbildpath from ebay.vorlage_x_che_auktionbild where vorlage_id=?";

			List<Map<String, Object>> itemPictures = null;
			try {
				/*
				 * offerDetailsRS = jdbcTemplate.queryForObject(sql.toString(), new
				 * Object[] { offerId}, new OfferMapper());
				 */

				// TODO Auto-generated method stub
				jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();

				itemPictures = jdbcTemplate.queryForList(auctionPicsSql,
						new Object[] { templateId });

			}

			/*
			 * Customer customer = (Customer)getJdbcTemplate().queryForObject( sql,
			 * new Object[] { custId },
			 * 
			 * LOGGER.debug("template in TemplateDAO is:{}",templateId);
			 */

			catch (Exception e) {
				LOGGER.error("Error occured"+e);
				//e.printStackTrace();

			}

			return itemPictures;

		}

		public List getDescriptionPictures(int templateId) {

			LOGGER.debug("Description Pictures templateId is :{}", templateId);

			String descriptionPicsSql = "select image_path from ebay.che_vorlagebilder where vorlage_id=?";

			List<Map<String, Object>> descriptionPictures = null;
			try {
				/*
				 * offerDetailsRS = jdbcTemplate.queryForObject(sql.toString(), new
				 * Object[] { offerId}, new OfferMapper());
				 */

				// TODO Auto-generated method stub
				jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();

				descriptionPictures = jdbcTemplate.queryForList(descriptionPicsSql,
						new Object[] { templateId });

			}

			/*
			 * Customer customer = (Customer)getJdbcTemplate().queryForObject( sql,
			 * new Object[] { custId },
			 * 
			 * LOGGER.debug("template in TemplateDAO is:{}",templateId);
			 */

			catch (Exception e) {
				LOGGER.error("Error occured"+e);
				//e.printStackTrace();

			}

			return descriptionPictures;

		}

		public List getOfferSliderPictures(int templateId) {

			LOGGER.debug("OfferSlider Pictures templateId is :{}", templateId);

			String offerSliderPicsSql = "select image_path from ebay.vorlage_x_che_offerslider where vorlage_id=?";

			List<Map<String, Object>> offerSliderPictures = null;
			try {
				/*
				 * offerDetailsRS = jdbcTemplate.queryForObject(sql.toString(), new
				 * Object[] { offerId}, new OfferMapper());
				 */

				// TODO Auto-generated method stub
				jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();

				offerSliderPictures = jdbcTemplate.queryForList(offerSliderPicsSql,
						new Object[] { templateId });
				//System.out.println("offerSliderPictures::::::::::::::::"+offerSliderPictures);

			}

			/*
			 * Customer customer = (Customer)getJdbcTemplate().queryForObject( sql,
			 * new Object[] { custId },
			 * 
			 * LOGGER.debug("template in TemplateDAO is:{}",templateId);
			 */

			catch (Exception e) {
				LOGGER.error("Error occured"+e);
				//e.printStackTrace();

			}

			return offerSliderPictures;

		}

		public List getObjectSliderPictures(int templateId) {
			

				LOGGER.debug("Object Slider Pictures templateId is :{}", templateId);

				String objectSliderPicsSql = "select image_path from ebay.vorlage_x_che_objectslider where vorlage_id=?";

				List<Map<String, Object>> objectSliderPictures = null;
				try {
					/*
					 * offerDetailsRS = jdbcTemplate.queryForObject(sql.toString(), new
					 * Object[] { offerId}, new OfferMapper());
					 */

					// TODO Auto-generated method stub
					jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();

					objectSliderPictures = jdbcTemplate.queryForList(objectSliderPicsSql,
							new Object[] { templateId });

				}
				catch (Exception e) {
					LOGGER.error("Error occured"+e);
					//e.printStackTrace();

				}
			
			return objectSliderPictures;
		}
		
		public String getPrimaryCategory(int templateId){
			LOGGER.debug("Categories templateId is :{}", templateId);

			String primaryCategorySql = "select categoryID from ebay.vorlage_x_itemSpecifics where categoryLevel=1 and auctionMasterID=?";

			String primary = null;
			try {
				/*
				 * offerDetailsRS = jdbcTemplate.queryForObject(sql.toString(), new
				 * Object[] { offerId}, new OfferMapper());
				 */

				// TODO Auto-generated method stub
				jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();

				primary = (String)jdbcTemplate.queryForObject(primaryCategorySql,
						new Object[] { templateId }, String.class);

			}
			catch (Exception e) {
				LOGGER.error("Error occured"+e);
				//e.printStackTrace();

			}
			return primary;
			
		}
		public String getSecondaryCategory(int templateId){
			LOGGER.debug("Categories templateId is :{}", templateId);

			String secondaryCategorySql = "select categoryID from ebay.vorlage_x_itemSpecifics where categoryLevel=2 and auctionMasterID=?";

			String secondary = null ;
			try {
				/*
				 * offerDetailsRS = jdbcTemplate.queryForObject(sql.toString(), new
				 * Object[] { offerId}, new OfferMapper());
				 */

				// TODO Auto-generated method stub
				jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();

				secondary = jdbcTemplate.queryForObject(secondaryCategorySql,
						new Object[] { templateId }, String.class);

			}
			catch (Exception e) {
				LOGGER.error("Error occured"+e);
				//e.printStackTrace();

			}
			return secondary;
			
		}
		
		
		public List getItemSpecIds(int templateId){
			LOGGER.debug("Item Specs templateId is :{}", templateId);

			String itemSpecSql = "select id,categoryLevel from ebay.vorlage_x_itemSpecifics where  auctionMasterID=?";
			List<Map<String, Object>> specs = null;
			try {
				/*
				 * offerDetailsRS = jdbcTemplate.queryForObject(sql.toString(), new
				 * Object[] { offerId}, new OfferMapper());
				 */

				// TODO Auto-generated method stub
				jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
				
				specs = jdbcTemplate.queryForList(itemSpecSql,
						new Object[] { templateId });

			}
			catch (Exception e) {
				LOGGER.error("Error occured"+e);
				//e.printStackTrace();

			}
			return specs;
			
		}

		
		public List getItemSpecNames(int specId){
			LOGGER.debug("Item Specs templateId is :{}", specId);

			String itemSpecName = "select id,name from ebay.vorlage_x_itemSpecName where  item_specID=?";
			List<Map<String, Object>> specNames = null;
			try {
				/*
				 * offerDetailsRS = jdbcTemplate.queryForObject(sql.toString(), new
				 * Object[] { offerId}, new OfferMapper());
				 */

				// TODO Auto-generated method stub
				jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
				
				specNames = jdbcTemplate.queryForList(itemSpecName,
						new Object[] { specId });

			}
			catch (Exception e) {
				LOGGER.error("Error occured"+e);
				//e.printStackTrace();

			}
			return specNames;
			
		}
		
		public List getItemSpecValues(int specnameID){
			LOGGER.debug("Item Values templateId is :{}", specnameID);

			String itemSpecValue= "select value,sValue from ebay.vorlage_x_itemSpecValue where item_specnameID =?";
			//System.out.println("Values============="+itemSpecValue+ " val:  "+specnameID);
			List<Map<String, Object>> specValues = null;
			try {
				/*
				 * offerDetailsRS = jdbcTemplate.queryForObject(sql.toString(), new
				 * Object[] { offerId}, new OfferMapper());
				 */

				// TODO Auto-generated method stub
				jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
				
				specValues = jdbcTemplate.queryForList(itemSpecValue,
						new Object[] { specnameID });

			}
			catch (Exception e) {
				LOGGER.error("Error occured"+e);
				//e.printStackTrace();

			}
			return specValues;
			
		}

		
		
		
		public int getAdditionalOption(int templateId){
			
	        int ebayoptionen=0;
	    	LOGGER.debug("Additional Option id is :{}",
					templateId);
			String galleryUrl;
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			String sql = "select ebayoptionen from ebay.vorlage where id =?";

			try {

				ebayoptionen = jdbcTemplate.queryForInt(sql,
						new Object[] { templateId });
			//	System.out.println("inside the DAO option value=================="+ebayoptionen);

			
			} catch (EmptyResultDataAccessException e) {
				LOGGER.error("Error occured"+e);
				//e.printStackTrace();
				}
	        
	        return ebayoptionen;
	    }

		
		
	 public List getAmenities(int templateId){
		 LOGGER.debug("Amenities templateId is :{}", templateId);

			String amenitiesValue= "select rubrik_id from ebay.vorlage_arrangement_rubrik where vorlage_id =?";
			//System.out.println("Values============="+itemSpecValue+ " val:  "+specnameID);
			List<Map<String, Object>> amenities = null;
			try {
				/*
				 * offerDetailsRS = jdbcTemplate.queryForObject(sql.toString(), new
				 * Object[] { offerId}, new OfferMapper());
				 */

				// TODO Auto-generated method stub
				jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
				
				amenities = jdbcTemplate.queryForList(amenitiesValue,
						new Object[] { templateId });

			}
			catch (Exception e) {
				LOGGER.error("Error occured"+e);
				//e.printStackTrace();

			}
			return amenities;
		 

	 }
	 
	 public  String getCurrency(int ebaysiteId){
			
			String currency ="";
			jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();
			
			String sql="select DISTINCT currency.kurz FROM ebaystammdaten.currency, ebaystammdaten.siteid WHERE siteid.id = ? AND currency.id = siteid.currency_id";
			
			LOGGER.debug("template details currency checking");
			
			try{
				
				currency = (String)jdbcTemplate.queryForObject(sql,new Object[]{ ebaysiteId },String.class);
				currency=currency.trim();
				
				
			}catch(Exception e){
				LOGGER.error("Error occured"+e);
				//e.printStackTrace();
			}
			
			return currency;
		}

	public String getLogoPicture(int templateId) {
		// TODO Auto-generated method stub
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		//insert into ebay.vorlage_x_che_logopicture(vorlage_id,imagepath,cusebeda_objekt_id)");
		String logoSql="select imagepath from ebay.vorlage_x_che_logopicture where vorlage_id=?";
		String logoPic="";
		
		try{
			logoPic = (String)jdbcTemplate.queryForObject(logoSql,new Object[]{ templateId },String.class);
		}catch(Exception e){	
			LOGGER.error("Error occured"+e);
			//e.printStackTrace();
		}
		
		LOGGER.debug("template details Logo Pic checking"+templateId);
		
		return logoPic;
	}
		
	public List<Map<String, Object>> getShopDetails(int templateId)
	{
		String shopValues= "select ShopObjectID,ShopCategoryID,ShopCategory2ID from ebay.vorlage_x_shop where AuctionMasterID=?";
		//System.out.println("Values============="+itemSpecValue+ " val:  "+specnameID);
		List<Map<String, Object>> shopVal = null;
		try {
			/*
			 * offerDetailsRS = jdbcTemplate.queryForObject(sql.toString(), new
			 * Object[] { offerId}, new OfferMapper());
			 */

			// TODO Auto-generated method stub
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			
			shopVal = jdbcTemplate.queryForList(shopValues,
					new Object[] { templateId });

		}
		catch (Exception e) {
			LOGGER.error("Error occured"+e);
			//e.printStackTrace();

		}
		return shopVal;
		
	}
	
	
	public String eBayOfferLink(int siteid){
		String viewSite=null;
		
		jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();
		
		String sql="SELECT kuerzel FROM ebaystammdaten.siteid_data WHERE site_id=?";
		
		LOGGER.debug("template details site link checking");
		
		try{
			
		String	sitecode = (String)jdbcTemplate.queryForObject(sql,new Object[]{ siteid },String.class);
		
		String site=null;
		
		 if( sitecode=="" || sitecode.equals("de") )
	        {
			 site="de";
	        }
	
	        if( sitecode.equals("uk"))
	        {
	        	site="co.uk";
	        }
	
	        if( sitecode=="us")
	        {
	        	site="com";
	        }

	        if( sitecode=="au")
	        {
	        	site="com.au";
	        }
	        
	        viewSite="http://cgi.ebay."+site+"/";

	        if(sitecode=="befr"){
	        	viewSite="http://cgi.befr.ebay.be/";
	        }

	        if(sitecode=="benl"){
	        	viewSite="http://cgi.benl.ebay.be/";
	        }

			
		}catch(Exception e){
			LOGGER.error("Error occured"+e);
			//e.printStackTrace();
		}
		
		
	
		return viewSite;
	}
	
	public String getPaypalAccount(int objectid,int marketplace){
		String payaplAcc="";
		String paypalSql="select paypal_email from ebay.object_paypalDetails where objectid=? and ebaysiteid="+marketplace;
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		
		try{
			payaplAcc = (String)jdbcTemplate.queryForObject(paypalSql,new Object[]{ objectid },String.class);
		}catch(Exception e){
			LOGGER.error("error occured at paypal name getting"+e);
			//e.printStackTrace();
		}
		
		LOGGER.debug("template details Logo Pic checking"+objectid);
		
		return payaplAcc;
	}
	
	public String getSelfServicePaypalAccount(int objectid,int marketplace){
		String payaplAcc="";
		String paypalSql="SELECT paypalAccount FROM ebay3.selfservicePaypalAccounts WHERE status = 1 AND cusebeda_objekt_id =?";
		jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();
		
		try{
			payaplAcc = (String)jdbcTemplate.queryForObject(paypalSql,new Object[]{ objectid },String.class);
		}catch(Exception e){
			LOGGER.error("error occured at paypal name getting"+e);
			//e.printStackTrace();
		}
		
		LOGGER.debug("template details Logo Pic checking"+objectid);
		
		return payaplAcc;
	}
	
	
	
	
	
	public String getShopAccountObjectName(int objectid){
		String shopname="";
		String shopNameSql="select ShopName from eBayShops.BasicData where ObjectID=?";
		
		jdbcTemplate = cusebedaJdbcTemplate.getJdbcTemplate();
		try{
			shopname = (String)jdbcTemplate.queryForObject(shopNameSql,new Object[]{ objectid },String.class);
		}catch(Exception e){
			LOGGER.error("error occured at shop name"+e);
			//e.printStackTrace();
		}
		
		LOGGER.debug("getting shopobject name"+objectid);
		
		return shopname;
	}
	
	public String getCollectionAccountObjectName(int objectid){
		String collectionName="";
		String collectionNameSql="select CAccName from eBayCollectionAccount.BasicData where ObjectID=?";
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		
		try{
			collectionName = (String)jdbcTemplate.queryForObject(collectionNameSql,new Object[]{ objectid },String.class);
		}catch(Exception e){
			LOGGER.error("error occured at collection name"+e);
			//e.printStackTrace();
		}
		
		LOGGER.debug("getting collection account object name"+objectid);
		
		return collectionName;
	}
	
	public String getObjectSellerName(int objectid){
		String objectName="";
		String ebayNameSql="select ebayname from ebay.ebaydaten where cusebeda_objekt_id=?";
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		
		try{
			objectName = (String)jdbcTemplate.queryForObject(ebayNameSql,new Object[]{ objectid },String.class);
		}catch(Exception e){
			LOGGER.error("error occured at eBay name"+e);
			//e.printStackTrace();
		}
		
		LOGGER.debug("getting account object name"+objectid);
		
		return objectName;
	}
	
	public Integer getCollectionAccountObjectId(int templateid){
		Integer collectionObjectId=0;
		String collectionIdSql="select CollectionAccountObjectID from ebay.vorlage_x_collectionAccount where AuctionMasterID=?";
		
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		try{
			collectionObjectId = jdbcTemplate.queryForObject(collectionIdSql,new Object[]{ templateid },Integer.class);
		}catch(Exception e){
			LOGGER.error("error occured at collection name"+e);
			//e.printStackTrace();
		}
		
		LOGGER.debug("getting collection account object name"+templateid);
		
		return collectionObjectId;
	}
	
	public String getCategoryName(int objectid,String categoryid,int siteid){
		String categoryName="";
		String categoryNameSql="select bezeichnung from ebay.kunden_kategorien where cusebeda_objekt_id=? and ebaysite_id="+siteid+" and ebaykategorie2_id="+categoryid;
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		
		try{
			categoryName = (String)jdbcTemplate.queryForObject(categoryNameSql,new Object[]{ objectid },String.class);
		}catch(Exception e){
			LOGGER.error("error occured at eBay name"+e);
			//e.printStackTrace();
		}
		
		LOGGER.debug("getting category name"+objectid);
		
		return categoryName;
	}
}


