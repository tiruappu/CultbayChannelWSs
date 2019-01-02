package com.cultuzz.channel.helper.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.DAO.impl.VoucherDAOImpl;
import com.cultuzz.channel.DAO.impl.VoucherReedemptionDAOImpl;
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
import com.cultuzz.channel.helper.VoucherValidator;
import com.cultuzz.channel.util.CommonValidations;

@Component
@Qualifier("voucherReddemValidator")
public class ValidateVoucherReedemptionImpl implements VoucherValidator{

	@Autowired
	CommonValidations commonValidations;
	
	@Autowired
	GetErrorMessagesDAOImpl getErrormessages;
	
	@Autowired
	VoucherDAOImpl voucherDAOImpl;
	
	@Autowired
	VoucherReedemptionDAOImpl voucherReedemptionDAOImpl;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidateVoucherReedemptionImpl.class);
	
	public VoucherRedemptionRS validateVoucherRedeemption(VoucherRedemptionRQ voucherRedeemptionRQ){
		int langid=0;
		VoucherRedemptionRS voucherRedeemptionRS=new VoucherRedemptionRS();
		voucherRedeemptionRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		
		ErrorsType error=new ErrorsType();
		List<ErrorType> errorsType=error.getError();
		if(voucherRedeemptionRQ.isSetAuthenticationCode()){
		boolean authCodeStatus=	commonValidations.checkAuthCode(voucherRedeemptionRQ.getAuthenticationCode());
		if(!authCodeStatus){
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1100);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,2));
			errorsType.add(errorType);
			voucherRedeemptionRS.setErrors(error);
			voucherRedeemptionRS.setAck("Failure");
			return voucherRedeemptionRS;
		}
			
		
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1100);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,2));
			errorsType.add(errorType);
			voucherRedeemptionRS.setErrors(error);
			voucherRedeemptionRS.setAck("Failure");
			return voucherRedeemptionRS;
		
		}	
		
		if(voucherRedeemptionRQ.isSetErrorLang()){
			
			langid=commonValidations.checkErrorLangCode(voucherRedeemptionRQ.getErrorLang());
			
			if(langid>0){
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1106);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,2));
				errorsType.add(errorType);
				voucherRedeemptionRS.setErrors(error);
				voucherRedeemptionRS.setAck("Failure");
				return voucherRedeemptionRS;
			}
			
		}else{				
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1106);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,2));
			errorsType.add(errorType);
			voucherRedeemptionRS.setErrors(error);
			voucherRedeemptionRS.setAck("Failure");
			return voucherRedeemptionRS;
		}
		
		if(voucherRedeemptionRQ.isSetTimeStamp()){
			boolean timestampStatus = commonValidations.checkTimeStamp(voucherRedeemptionRQ.getTimeStamp());
			if(!timestampStatus){	
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1104);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1104,langid));
				errorsType.add(errorType);
				voucherRedeemptionRS.setErrors(error);
				voucherRedeemptionRS.setAck("Failure");
				return voucherRedeemptionRS;
			}
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1105);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1105,langid));
			errorsType.add(errorType);
			voucherRedeemptionRS.setErrors(error);
			voucherRedeemptionRS.setAck("Failure");
			return voucherRedeemptionRS;
		}
		
		if(voucherRedeemptionRQ.isSetSourceId() ){
			if(!commonValidations.checkSourceId(voucherRedeemptionRQ.getSourceId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1101);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langid));
				errorsType.add(errorType);
				voucherRedeemptionRS.setErrors(error);
				voucherRedeemptionRS.setAck("Failure");
				return voucherRedeemptionRS;
			}
		
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1101);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langid));
			errorsType.add(errorType);
			voucherRedeemptionRS.setErrors(error);
			voucherRedeemptionRS.setAck("Failure");
			return voucherRedeemptionRS;
			
		}
		
		if(voucherRedeemptionRQ.isSetChannelId()){
		
			if(!commonValidations.checkChannelId(voucherRedeemptionRQ.getChannelId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1102);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langid));
				errorsType.add(errorType);
				voucherRedeemptionRS.setErrors(error);
				voucherRedeemptionRS.setAck("Failure");
				return voucherRedeemptionRS;
			}
			
		}else{
			
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1102);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langid));
			errorsType.add(errorType);
			voucherRedeemptionRS.setErrors(error);
			voucherRedeemptionRS.setAck("Failure");
			return voucherRedeemptionRS;
			
		}
		
		if(voucherRedeemptionRQ.isSetObjectId()){
			
			LOGGER.debug("Checking objectid");
			if(!commonValidations.checkObjectId(voucherRedeemptionRQ.getObjectId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1103);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
				errorsType.add(errorType);
				voucherRedeemptionRS.setErrors(error);
				voucherRedeemptionRS.setAck("Failure");
				return voucherRedeemptionRS;
			}/*else
				objectIdFlag=true;
			*/
			
		}else{
			
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1103);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
			errorsType.add(errorType);
			voucherRedeemptionRS.setErrors(error);
			voucherRedeemptionRS.setAck("Failure");
			return voucherRedeemptionRS;
			
		}
		boolean actionStatus=false;
		if(voucherRedeemptionRQ.isSetAction()){
			
			if(!voucherRedeemptionRQ.getAction().trim().isEmpty()){
			
			if(voucherRedeemptionRQ.getAction().equals("Redeem") || voucherRedeemptionRQ.getAction().equals("Rollback")){
				actionStatus=true;
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(7114);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(7114,langid));
				errorsType.add(errorType);
				voucherRedeemptionRS.setErrors(error);
				voucherRedeemptionRS.setAck("Failure");
				return voucherRedeemptionRS;
				
			}
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(7115);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(7115,langid));
				errorsType.add(errorType);
				voucherRedeemptionRS.setErrors(error);
				voucherRedeemptionRS.setAck("Failure");
				return voucherRedeemptionRS;
			}
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(7115);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(7115,langid));
			errorsType.add(errorType);
			voucherRedeemptionRS.setErrors(error);
			voucherRedeemptionRS.setAck("Failure");
			return voucherRedeemptionRS;
		}
		
		int checkPaidStatus=0;
		long itemid=0;
		if(voucherRedeemptionRQ.isSetItemId()){
			
			if(!voucherRedeemptionRQ.getItemId().trim().isEmpty()){
				boolean itemidStatus=false;
				try{
					LOGGER.debug("This is itemid checking");
					itemid=Long.parseLong(voucherRedeemptionRQ.getItemId());
				}catch(Exception e){
					LOGGER.debug("This is itemid checking failed");
				}
				
				if(itemid>0){
					itemidStatus=voucherDAOImpl.checkEbayItemId(itemid,voucherRedeemptionRQ.getObjectId());
				}
				
				if(itemidStatus){
					checkPaidStatus++;
				}else if(!itemidStatus){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(7110);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(7110,langid));
					errorsType.add(errorType);
					voucherRedeemptionRS.setErrors(error);
					voucherRedeemptionRS.setAck("Failure");
					return voucherRedeemptionRS;
				}
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(7116);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(7116,langid));
				errorsType.add(errorType);
				voucherRedeemptionRS.setErrors(error);
				voucherRedeemptionRS.setAck("Failure");
				return voucherRedeemptionRS;
			}
			
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(7110);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(7110,langid));
			errorsType.add(errorType);
			voucherRedeemptionRS.setErrors(error);
			voucherRedeemptionRS.setAck("Failure");
			return voucherRedeemptionRS;
		}
		boolean voucherIdStatus=false;
		String voucherid=null;
		if(voucherRedeemptionRQ.isSetVoucherId()){
			
			if(!voucherRedeemptionRQ.getVoucherId().trim().isEmpty()){
				
				voucherIdStatus=voucherDAOImpl.checkVoucherId(itemid, voucherRedeemptionRQ.getVoucherId().trim());
				
				if(voucherIdStatus){
					checkPaidStatus++;
					try{
					voucherid=voucherRedeemptionRQ.getVoucherId();
					}catch(NumberFormatException nfe){
						nfe.printStackTrace();
					}catch(Exception e){
						e.printStackTrace();
					}
				}else if(!voucherIdStatus){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(7117);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(7117,langid));
					errorsType.add(errorType);
					voucherRedeemptionRS.setErrors(error);
					voucherRedeemptionRS.setAck("Failure");
					return voucherRedeemptionRS;
				}
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(7118);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(7118,langid));
				errorsType.add(errorType);
				voucherRedeemptionRS.setErrors(error);
				voucherRedeemptionRS.setAck("Failure");
				return voucherRedeemptionRS;
			}
			
			
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(7117);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(7117,langid));
			errorsType.add(errorType);
			voucherRedeemptionRS.setErrors(error);
			voucherRedeemptionRS.setAck("Failure");
			return voucherRedeemptionRS;
		}
		
		if(checkPaidStatus==2 && itemid>0){
			boolean paidStatus=voucherDAOImpl.checkPaidStatus(itemid, voucherRedeemptionRQ.getVoucherId());
			if(!paidStatus){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(7113);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(7113,langid));
				errorsType.add(errorType);
			}
		
		}
		
		Long orderid=null;
		
		if(voucherRedeemptionRQ.isSetOrderId()){
			
			if(!voucherRedeemptionRQ.getOrderId().trim().isEmpty()){
			try{
				orderid=Long.parseLong(voucherRedeemptionRQ.getOrderId());
			}catch(NumberFormatException nfe){
				nfe.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(orderid!=null && voucherid!=null){
			boolean orderidStatus=voucherDAOImpl.checkOrderId(orderid, itemid,voucherid);
			if(!orderidStatus){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(7130);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(7130,langid));
				errorsType.add(errorType);
			}
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(7130);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(7130,langid));
				errorsType.add(errorType);
			}
			
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(7119);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(7119,langid));
				errorsType.add(errorType);
			}
			
		}
		
		
			if(voucherRedeemptionRQ.isSetAction() && voucherRedeemptionRQ.getAction().equals("Redeem")){
				if(voucherRedeemptionRQ.isSetTravellerName()){
					
				if(voucherRedeemptionRQ.getTravellerName().trim().isEmpty()){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(7120);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(7120,langid));
					errorsType.add(errorType);
				}else if(voucherRedeemptionRQ.getTravellerName().length()>50){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(7133);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(7133,langid));
					errorsType.add(errorType);
				}else if(voucherRedeemptionRQ.getTravellerName() !=null ){
					
					String t="!@#$%^&*()+\\=\\[\\]{};':\\\"\\\\|,.<>\\/?";
					int theCount=0;
					for (int i = 0; i < voucherRedeemptionRQ.getTravellerName().length(); i++) {
				         
				         System.out.println("couner"+i);
				         if (voucherRedeemptionRQ.getTravellerName().contains(t.substring(i, i+1))) {
				             theCount++;
				         }
				     }
					if(theCount>0){
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(7137);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(7137,langid));
						errorsType.add(errorType);
					}
					
				}
				
				}else{
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(7120);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(7120,langid));
					errorsType.add(errorType);
				}
			}else{
				if(voucherRedeemptionRQ.isSetTravellerName()){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(7127);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(7127,langid));
					errorsType.add(errorType);
				}
			}
		
			boolean todateStatus=false;
			boolean fromdateStatus=false;
			boolean dateBetweenStatus=false;
		
		if(voucherRedeemptionRQ.isSetAction() && voucherRedeemptionRQ.getAction().equals("Redeem")){
		
			if(voucherRedeemptionRQ.isSetPeriod()){
			
			if(voucherRedeemptionRQ.getPeriod().isSetFrom()){
				
				if(!voucherRedeemptionRQ.getPeriod().getFrom().trim().isEmpty()){
					
					if(commonValidations.checkDateStamp(voucherRedeemptionRQ.getPeriod().getFrom())){
						fromdateStatus=true;
					}else{
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(3121);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(3121,langid));
						errorsType.add(errorType);
					}
					
				}else{
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(7128);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(7128,langid));
					errorsType.add(errorType);
				}
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(3121);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(3121,langid));
				errorsType.add(errorType);
			}
				
		
			
			
					
			if(voucherRedeemptionRQ.getPeriod().isSetTo()){
				
				if(!voucherRedeemptionRQ.getPeriod().getTo().trim().isEmpty()){
					
					if(commonValidations.checkDateStamp(voucherRedeemptionRQ.getPeriod().getTo())){
						todateStatus=true;
					}else{
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(3123);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(3123,langid));
						errorsType.add(errorType);
					}
			
				}else{
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(7129);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(7129,langid));
					errorsType.add(errorType);
				}
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(3123);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(3123,langid));
				errorsType.add(errorType);
			}
			
			
				if(voucherRedeemptionRQ.getPeriod().getFrom() !=null && voucherRedeemptionRQ.getPeriod().getTo() !=null && voucherRedeemptionRQ.getPeriod().getFrom().compareTo(voucherRedeemptionRQ.getPeriod().getTo())>=0){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(3151);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(3151,langid));
					errorsType.add(errorType);
				}else
					dateBetweenStatus=true;
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(7121);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(7121,langid));
				errorsType.add(errorType);
			}
		}else{
			if(voucherRedeemptionRQ.isSetPeriod()){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(7126);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(7126,langid));
				errorsType.add(errorType);
			}
		}
		
		
		if(voucherRedeemptionRQ.isSetAction() && voucherRedeemptionRQ.getAction().equals("Redeem") && itemid>0 && voucherRedeemptionRQ.isSetVoucherId() && voucherIdStatus){
			
		boolean itemRedeemStatus=voucherReedemptionDAOImpl.checkRedeemItemid(itemid, voucherRedeemptionRQ.getVoucherId().trim());
		
		if(!itemRedeemStatus){
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(7124);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(7124,langid));
			errorsType.add(errorType);
		}else if(itemRedeemStatus){
			List<Map<String,Object>> list=voucherReedemptionDAOImpl.getBookingData(voucherRedeemptionRQ.getItemId(), voucherRedeemptionRQ.getVoucherId());
			int bookingSize=list.size();
			if(list.size()>0){
		        for(Map<String, Object> credentails : list){
		        	
		        	int bookingid=0;
		        	int productid=0;
		        	int status=0;
		        	try{
		        		bookingid = Integer.parseInt(credentails.get("buchung_id").toString());
		        	}catch(Exception e){
		        		e.printStackTrace();
		        		LOGGER.debug("This problem inside booking id String");
		        	}

		        	try{
		        		productid = Integer.parseInt(credentails.get("arrangement_id").toString());
		        	}catch(Exception e){
		        		LOGGER.debug("This problem inside productid String");
		        		e.printStackTrace();
		        	}
		           
		        	try{
		        		status = Integer.parseInt(credentails.get("status").toString());
		        	}catch(Exception e){
		        		LOGGER.debug("This problem inside status String");
		        		e.printStackTrace();
		        	}
		        	
		        
		            if(productid>0){
		            	if(bookingid>0){
		            		ErrorType errorType=new ErrorType();
		    				errorType.setErrorCode(7140);
		    				errorType.setErrorMessage(getErrormessages.getErrorMessage(7140,langid));
		    				errorsType.add(errorType);
		            	}else{
		            		ErrorType errorType=new ErrorType();
		    				errorType.setErrorCode(7134);
		    				errorType.setErrorMessage(getErrormessages.getErrorMessage(7134,langid));
		    				errorsType.add(errorType);
		            	}
		            	
		            }
		            
		            
		        }
				
		        }
			LOGGER.debug("This fromdate status==="+fromdateStatus+" to dateStatus=="+todateStatus+"==datebetween status="+dateBetweenStatus+"==Bookingstatus");
			if(fromdateStatus && todateStatus && dateBetweenStatus && bookingSize==0){
            	
	            boolean validityStatus=	voucherReedemptionDAOImpl.checkVoucherValidity(voucherRedeemptionRQ.getItemId(), voucherRedeemptionRQ.getVoucherId(),voucherRedeemptionRQ.getPeriod().getTo());
	            if(!validityStatus){
	            	ErrorType errorType=new ErrorType();
    				errorType.setErrorCode(7139);
    				errorType.setErrorMessage(getErrormessages.getErrorMessage(7139,langid));
    				errorsType.add(errorType);
	            }
	            }
				
				
		}
		
		}else if(voucherRedeemptionRQ.isSetAction() && voucherRedeemptionRQ.getAction().equals("Rollback") && itemid>0 && voucherRedeemptionRQ.isSetVoucherId() && voucherIdStatus){
			
			boolean itemRollbackStatus=voucherReedemptionDAOImpl.checkRollbackItemid(itemid, voucherRedeemptionRQ.getVoucherId().trim());
			if(!itemRollbackStatus){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(7125);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(7125,langid));
				errorsType.add(errorType);
			}
		}
		
		if(errorsType.size()>0){
			voucherRedeemptionRS.setAck("Failure");
			voucherRedeemptionRS.setErrors(error);
			
		}else
			voucherRedeemptionRS.setAck("Success");
		
		return voucherRedeemptionRS;
	}

	public VoucherDetailsRS validateVoucherDetails(
			VoucherDetailsRQ voucherDetailsRQ) {
		// TODO Auto-generated method stub
		return null;
	}

	public ListOfVouchersRS validateListOfVouchers(
			ListOfVouchersRQ listOfVouchersRQ) {
		// TODO Auto-generated method stub
		return null;
	}

	/*public VoucherRedeemptionRS validateVoucherRedeemption(
			VoucherRedeemptionRQ voucherRedeemptionRQ) {
		// TODO Auto-generated method stub
		return null;
	}
*/
	public VoucherServiceRS validateVoucherService(
			VoucherServiceRQ voucherServiceRQ) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
