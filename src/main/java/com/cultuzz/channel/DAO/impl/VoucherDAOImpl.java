package com.cultuzz.channel.DAO.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.cultuzz.channel.DAO.VoucherDAO;
import com.cultuzz.channel.jdbcTemplate.JDBCTemplate;

@Configuration
public class VoucherDAOImpl implements VoucherDAO {

private static final Logger LOGGER = LoggerFactory.getLogger(VoucherDetailsDAOImpl.class);
	
	@Autowired
	@Qualifier("ebayTemplate")
	private JDBCTemplate ebayJdbcTemplate;
	
	private JdbcTemplate jdbcTemplate;
	
	
	public boolean checkEbayItemId(long ebayItemId,int objectId) {
		// TODO Auto-generated method stub
		
		boolean ebayItemIdFlag=false;
        try{
            
            jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
            String sql="select auktion_sich.ebayitemid from ebay.auktion_sich where auktion_sich.cusebeda_objekt_id=? and auktion_sich.ebayitemid=?";
            long ebayItemIdcount =jdbcTemplate.queryForLong(sql, new Object[] {objectId,ebayItemId});
            
            LOGGER.debug("Checking ebayItemId count:{}",ebayItemIdcount);
            if(ebayItemIdcount>0)
                ebayItemIdFlag=true;
            
        }catch(Exception e){
            ebayItemIdFlag=false;
            e.printStackTrace();
        }
        
        return ebayItemIdFlag;
	}
	
	public boolean checkOrderId(long orderId, long ebayItemId,String voucherid){
		
		boolean orderIdFlag=false;
		long orderIdcount = 0;
        try{
            
            jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
            StringBuffer sql = new StringBuffer();
             sql.append("select count(ebayorderid) from ebay.status where ebayorderid = ? and posten = ?");
             if(voucherid!=null){
             if(Integer.parseInt(voucherid) > 0){
            	 sql.append(" and czInternalTransID=?");
            	  orderIdcount =jdbcTemplate.queryForLong(sql.toString(), new Object[] {orderId, ebayItemId,voucherid});
             }else{
            	  orderIdcount =jdbcTemplate.queryForLong(sql.toString(), new Object[] {orderId, ebayItemId}); 
             }}
            
            
            LOGGER.debug("Checking orderId count:{}",orderIdcount);
            if(orderIdcount>0)
                orderIdFlag=true;
            
        }catch(Exception e){
            orderIdFlag=false;
            e.printStackTrace();
        }
        
        return orderIdFlag;
	}

	public boolean checkVoucherId(long ebayItemId, String voucherId){
		
		boolean voucherIdFlag=false;
        try{
            
            jdbcTemplate=ebayJdbcTemplate.getJdbcTemplate();
            String sql="select count(czInternalTransID) from ebay.status where posten = ? and 	czInternalTransID =?";
            long voucherIdcount =jdbcTemplate.queryForLong(sql, new Object[] {ebayItemId, voucherId});
            
            LOGGER.debug("Checking voucherId count:{}",voucherIdcount);
            if(voucherIdcount>0)
                voucherIdFlag=true;
            
        }catch(Exception e){
            voucherIdFlag=false;
            e.printStackTrace();
        }
        
        return voucherIdFlag;
	}

	public boolean checkPaidStatus(long ebayItemId, String voucherId){
		
		boolean paidStatusFlag = false;
		try{
			
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			String sql = "select binaerstatus from ebay.status where posten = ? and czInternalTransID =?";
			int paidStatus = jdbcTemplate.queryForInt(sql, new Object[] {ebayItemId, voucherId});
			
			LOGGER.debug("checking paid status in DAO is:{}",paidStatus);
		
			if(paidStatus % 2!=0 || paidStatus == 34){
				
				paidStatusFlag=true;
			}
			
		}catch(Exception e){
			paidStatusFlag = false;
			e.printStackTrace();
		}
		
		return paidStatusFlag;
	}
	
	
}
