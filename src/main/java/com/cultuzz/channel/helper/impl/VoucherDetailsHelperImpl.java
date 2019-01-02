package com.cultuzz.channel.helper.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import com.cultuzz.channel.DAO.VoucherDetailsDAO;
import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.DAO.impl.TemplateDetailsDAOImpl;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.XMLpojo.ListOfVouchersRQ;
import com.cultuzz.channel.XMLpojo.ListOfVouchersRS;
import com.cultuzz.channel.XMLpojo.VoucherDetailsRQ;
import com.cultuzz.channel.XMLpojo.VoucherDetailsRS;
import com.cultuzz.channel.XMLpojo.VoucherRedemptionRQ;
import com.cultuzz.channel.XMLpojo.VoucherRedemptionRS;
import com.cultuzz.channel.XMLpojo.VoucherServiceRQ;
import com.cultuzz.channel.XMLpojo.VoucherServiceRS;
import com.cultuzz.channel.helper.VoucherHelper;
import com.cultuzz.channel.util.DateUtil;

@Configuration
@Qualifier("voucherDetailsHelper")
public class VoucherDetailsHelperImpl implements VoucherHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(VoucherDetailsHelperImpl.class);
	
	@Autowired
	private GetErrorMessagesDAOImpl getErrorMessagesDAOImpl;
	
	@Autowired
	private VoucherDetailsDAO voucherDetailsDAOImpl;
	
	@Autowired
	private TemplateDetailsDAOImpl templateDAOImpl;
	
	VoucherDetailsRS voucherDetailsRS = null;
	
	public VoucherDetailsRS processVoucherDetailsHelper(
			VoucherDetailsRQ voucherDetailsRQ) {
		// TODO Auto-generated method stub
		LOGGER.info("Inside voucher details helper");
		
		int langId =0;
		
		if(voucherDetailsRQ!=null){
		
			try{
			
				voucherDetailsRS = new VoucherDetailsRS();
				  
				  ErrorsType errorsType = new ErrorsType();
				  List<ErrorType> errorType = errorsType.getError();
				  
				 
				  
				  langId = getErrorMessagesDAOImpl.getLanguageId(voucherDetailsRQ.getErrorLang());
				  if(langId > 0){
					  
					  LOGGER.debug("voucher details rq itemid is:{}",voucherDetailsRQ.getItemId());
					  LOGGER.debug("voucher details rq voucher id is:{}",voucherDetailsRQ.getVoucherId());
					  
				      voucherDetailsRS = this.voucherManager(voucherDetailsRQ.getItemId(),voucherDetailsRQ.getVoucherId());
					  if(voucherDetailsRS==null){
						  voucherDetailsRS=new VoucherDetailsRS();
						  ErrorType error = new ErrorType();
		    				error.setErrorCode(7138);
		    				
		    				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(7138, 2));
		    				errorType.add(error);
					  }
				      
				  }else{
					  
					  ErrorType error = new ErrorType();
	    				error.setErrorCode(1106);
	    				
	    				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1106, 2));
	    				errorType.add(error);
				  }
				  
				  if(errorType.size()>0){
	       			  voucherDetailsRS.setErrors(errorsType);
	     				voucherDetailsRS.setAck("Failure");
	       		  }else{
	     				voucherDetailsRS.setAck("Success");
	       		  }
				
			}catch(Exception e){
				
				e.printStackTrace();
			}
			
		}
		return voucherDetailsRS;
	}

	public ListOfVouchersRS processListOfVouchersHelper(
			ListOfVouchersRQ listOfVouchersRQ) {
		// TODO Auto-generated method stub
		return null;
	}

	public VoucherRedemptionRS processVoucherRedeemptionHelper(
			VoucherRedemptionRQ voucherRedeemptionRQ) {
		// TODO Auto-generated method stub
		return null;
	}

	public VoucherServiceRS processsVoucherServiceHelper(
			VoucherServiceRQ voucherServiceRQ) {
		// TODO Auto-generated method stub
		return null;
	}
	//private static DecimalFormat df2 = new DecimalFormat(".##");
	public VoucherDetailsRS voucherManager(String itemId, String voucherId){
		
	   try{
		LOGGER.debug("Inside this.voucherManager");
			
		LOGGER.debug("itemId in offerManager is:{}",itemId);
	    LOGGER.debug("voucherId in voucherManager is:{}",voucherId);
		
	    if(itemId!=null && voucherId!=null){
	
			voucherDetailsRS = voucherDetailsDAOImpl.getVoucherDetails(Long.parseLong(itemId), voucherId);
			
			 voucherDetailsRS.setTimeStamp(DateUtil.convertDateToString(new Date()));
			if(voucherDetailsRS!=null){
				voucherDetailsRS.setVoucherId(voucherId);
				double price = voucherDetailsDAOImpl.getVoucherPrice(Long.parseLong(itemId), voucherId);
				if(price>0){
					
					voucherDetailsRS.setPrice(String.format("%.2f", price));
				}
				
				
				 List<Map<String, Object>> vdata= voucherDetailsDAOImpl.getVoucherOtherdata(itemId);
                 
                 for(Map<String, Object> voucherDatakeys :vdata){
                	 
               	  
               	  if(voucherDatakeys.get("arrangement_id")!=null){
               		int pid=  Integer.parseInt(voucherDatakeys.get("arrangement_id").toString());
               		if(pid>0)
               			voucherDetailsRS.setProductOffer("true");
               		else
               			voucherDetailsRS.setProductOffer("false");
               	  }
               	  
               	  if(voucherDatakeys.get("ebaysiteid")!=null){
               		  try{
               		  int ebaysiteId=Integer.parseInt(voucherDatakeys.get("ebaysiteid").toString());
               		  String currency=templateDAOImpl.getCurrency(ebaysiteId);
               		  
               		  voucherDetailsRS.setCurrency(currency);
               		  voucherDetailsRS.setOfferSiteId(templateDAOImpl.eBayOfferLink(ebaysiteId));
               		  
               		  }catch(NullPointerException npe){
               			  npe.printStackTrace();
               		  }catch (Exception e) {
							// TODO: handle exception
               			  e.printStackTrace();
						}
               	  }
                 }
				
			}
		  
	    }
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			voucherDetailsRS =null;
		}
		
		return voucherDetailsRS;
	}
	
}
