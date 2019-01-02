package com.cultuzz.channel.DAO.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.cultuzz.channel.DAO.VoucherDetailsDAO;
import com.cultuzz.channel.XMLpojo.VoucherDetailsRS;
import com.cultuzz.channel.jdbcTemplate.JDBCTemplate;
import com.cultuzz.channel.template.pojo.VoucherMapper;


@Configuration
public class VoucherDetailsDAOImpl implements VoucherDetailsDAO{

	private static final Logger LOGGER = LoggerFactory.getLogger(VoucherDetailsDAOImpl.class);
	
	@Autowired
	@Qualifier("ebayTemplate")
	private JDBCTemplate ebayJdbcTemplate;
	
	
	private JdbcTemplate jdbcTemplate;
	
	public VoucherDetailsRS getVoucherDetails(long ebayItemId, String voucherId){
		
		VoucherDetailsRS voucherDetailsRS = null;
	
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			
			String sql = "select ebayitemid, g.gueltig_bis, g.binaerstatus, g.versendet, g.anreise, g.abreise, "
					+ "g.besucher, g.last_change, k.vorname, k.nachname, k.ebay_auktion_hoechstbietender, s.bezahlt_datum, g.ebayorderid "
					+ "from ebay.status s join checkout.gutscheine g "
					+ "on s.posten = g.ebayitemid "
					+ "left join checkout.kaeufer k "
					+ "on k.id = g.kaeufer_id "
					+ "where s.czInternalTransID = g.czInternalTransID and "
					+ "g.ebayitemid = ? and g.czInternalTransID = ?";
           try{		
			
			LOGGER.debug("string sql for getting voucher details is:{}",sql);
			
			voucherDetailsRS = jdbcTemplate.queryForObject(sql.toString(), 
					new Object[] { ebayItemId, voucherId},new VoucherMapper());
			
			LOGGER.debug("voucher details is :{}",voucherDetailsRS);
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		return voucherDetailsRS;
	}
	
	public double getVoucherPrice(long ebayItemId, String voucherId){
		
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		
		double price =0;
		String sql = "select t.endprice/t.quantity_purchased as price "
				+ "from ebay.transaction t join ebay.order_x_voucher o "
				+ "on t.saleID = o.saleID  where t.ebayitemid = o.ebayitemid "
				+ "and o.ebayitemid = ? and o.czInternalTransID = ?";
		
		try{		
			
			LOGGER.debug("string sql for getting voucher details is:{}",sql);
			
			price = jdbcTemplate.queryForObject(sql.toString(), 
					new Object[] {ebayItemId, voucherId},Integer.class);
			
			LOGGER.debug("price is :{}",price);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return price;
}
	
	public List<Map<String,Object>> getVoucherOtherdata(String itemid){
		List<Map<String, Object>> voucherOtherdata = null;
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		
		String getDataQuery="select v.arrangement_id,a.ebaysiteid from ebay.auktion a join ebay.vorlage v on v.id=a.vorlage_id where a.ebayitemid="+itemid;
		
		try{
			
			voucherOtherdata=jdbcTemplate.queryForList(getDataQuery);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return voucherOtherdata;
	}
	
	
	
	
	
	/*
	 * 
	 * 
	 * $sql.= " SELECT DISTINCT currency.kurz, currency.kurz_pdf ";
$sql.= " FROM ebaystammdaten.currency, ebaystammdaten.siteid ";
$sql.= " WHERE siteid.id=$ebaysiteid ";
$sql.= " AND currency.id=siteid.currency_id ";

SELECT DISTINCT currency.kurz, currency.kurz_pdf FROM ebaystammdaten.currency, ebaystammdaten.siteid WHERE siteid.id=$ebaysiteid AND currency.id=siteid.currency_id;
select v.arrangement_id,a.ebaysiteid,s.url from ebay.auktion a join ebay.vorlage v on v.id=a.vorlage_id left join ebay.ebaysite s on v.ebaysiteid=s.id where a.ebayitemid=110256605547;
	 * (non-Javadoc)
	 * @see com.cultuzz.channel.DAO.VoucherDetailsDAO#getVoucherIds(java.lang.String)
	 */
	
	
	public List<Map<String, Object>> getVoucherIds(String voucherIdsQuery){
		
		LOGGER.debug("voucherIds query is :{}",voucherIdsQuery);

		List<Map<String, Object>> voucherIds = null;
		
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		
		String listOfVouchersSql = "select s.posten,s.czInternalTransID from ebay.status s "
				+"join ebay.auktion_sich a on s.posten = a.ebayitemid join checkout.gutscheine g "
				+ "on s.posten = g.ebayitemid left join checkout.kaeufer k on k.id=g.kaeufer_id where g.czInternalTransID = s.czInternalTransID and "
				+ "g.objekt_id = a.cusebeda_objekt_id "+voucherIdsQuery;
		try{
			
			LOGGER.debug("sql query for voucherIds :{}",listOfVouchersSql);

			voucherIds = jdbcTemplate.queryForList(listOfVouchersSql);
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		return voucherIds;
	}

	public int getTotlaVouchers(String voucherIdsQuery){
		
		LOGGER.debug("object Id for total count of vouchers :{}",voucherIdsQuery);
		
		int totalVouchers = 0;
		
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		
		String totalVouchersQuery = "select count(s.id) from ebay.status s "
				+"join ebay.auktion_sich a on s.posten = a.ebayitemid join checkout.gutscheine g "
				+ "on s.posten = g.ebayitemid left join checkout.kaeufer k on k.id=g.kaeufer_id where g.czInternalTransID = s.czInternalTransID and "
				+ "g.objekt_id = a.cusebeda_objekt_id "+voucherIdsQuery;
		
		try{
			
			LOGGER.debug("sql for total vouchers is :{}",totalVouchersQuery);
			
			totalVouchers = jdbcTemplate.queryForObject(totalVouchersQuery,Integer.class);
		
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		return totalVouchers;
	}
	
	public boolean checkAuctionOffer(String ebayitemid){
		boolean auctionStatus=false;
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		//String auctionCheckQuery="select count(id) from ebay.auktion where ebayitemid="+ebayitemid+" and AuctionMasterTypeID=1 and quantity=1 and startpreis>0";
		String auctionCheckQuery="select count(id) from ebay.auktion where ebayitemid="+ebayitemid+" and quantity=1 ";

		try{
			
			LOGGER.debug("sql for auction voucher is :{}",auctionCheckQuery);
			
		int offerscount= jdbcTemplate.queryForObject(auctionCheckQuery,Integer.class);
		if(offerscount>0)
			auctionStatus=true;
		
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		return auctionStatus;
	}
}