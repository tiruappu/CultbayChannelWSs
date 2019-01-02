package com.cultuzz.channel.helper.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.DAO.impl.VoucherDAOImpl;
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
@Qualifier("voucherServiceValidator")
public class ValidateVoucherServiceImpl implements VoucherValidator{
	
	@Autowired
	CommonValidations commonValidations;
	
	@Autowired
	GetErrorMessagesDAOImpl getErrormessages;
	
	@Autowired
	VoucherDAOImpl voucherDAOImpl;
	private static final Logger logger = LoggerFactory.getLogger(ValidateVoucherReedemptionImpl.class);
	
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

	public VoucherRedemptionRS validateVoucherRedeemption(
			VoucherRedemptionRQ voucherRedeemptionRQ) {
		// TODO Auto-generated method stub
		return null;
	}

	public VoucherServiceRS validateVoucherService(
			VoucherServiceRQ voucherServiceRQ) {
		int langid=0;
		// TODO Auto-generated method stub
		VoucherServiceRS voucherServiceRS=new VoucherServiceRS();
		//System.out.println("sdfjsadj");
		voucherServiceRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		
		ErrorsType error=new ErrorsType();
		List<ErrorType> errorsType=error.getError();
		if(voucherServiceRQ.isSetAuthenticationCode()){
		boolean authCodeStatus=	commonValidations.checkAuthCode(voucherServiceRQ.getAuthenticationCode());
		if(!authCodeStatus){
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1100);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,2));
			errorsType.add(errorType);
			voucherServiceRS.setErrors(error);
			voucherServiceRS.setAck("Failure");
			return voucherServiceRS;
		}
			
		
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1100);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,2));
			errorsType.add(errorType);
			voucherServiceRS.setErrors(error);
			voucherServiceRS.setAck("Failure");
			return voucherServiceRS;
		
		}	
		
		if(voucherServiceRQ.isSetErrorLang()){
			
			langid=commonValidations.checkErrorLangCode(voucherServiceRQ.getErrorLang());
			
			if(langid>0){
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1106);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,2));
				errorsType.add(errorType);
				voucherServiceRS.setErrors(error);
				voucherServiceRS.setAck("Failure");
				return voucherServiceRS;
			}
			
		}else{				
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1106);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,2));
			errorsType.add(errorType);
			voucherServiceRS.setErrors(error);
			voucherServiceRS.setAck("Failure");
			return voucherServiceRS;
		}
		
		if(voucherServiceRQ.isSetTimeStamp()){
			boolean timestampStatus = commonValidations.checkTimeStamp(voucherServiceRQ.getTimeStamp());
			if(!timestampStatus){	
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1104);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1104,langid));
				errorsType.add(errorType);
				voucherServiceRS.setErrors(error);
				voucherServiceRS.setAck("Failure");
				return voucherServiceRS;
			}
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1105);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1105,langid));
			errorsType.add(errorType);
			voucherServiceRS.setErrors(error);
			voucherServiceRS.setAck("Failure");
			return voucherServiceRS;
		}
		
		if(voucherServiceRQ.isSetSourceId() ){
			if(!commonValidations.checkSourceId(voucherServiceRQ.getSourceId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1101);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langid));
				errorsType.add(errorType);
				voucherServiceRS.setErrors(error);
				voucherServiceRS.setAck("Failure");
				return voucherServiceRS;
			}
		
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1101);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langid));
			errorsType.add(errorType);
			voucherServiceRS.setErrors(error);
			voucherServiceRS.setAck("Failure");
			return voucherServiceRS;
			
		}
		
		if(voucherServiceRQ.isSetChannelId()){
		
			if(!commonValidations.checkChannelId(voucherServiceRQ.getChannelId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1102);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langid));
				errorsType.add(errorType);
				voucherServiceRS.setErrors(error);
				voucherServiceRS.setAck("Failure");
				return voucherServiceRS;
			}
			
		}else{
			
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1102);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langid));
			errorsType.add(errorType);
			voucherServiceRS.setErrors(error);
			voucherServiceRS.setAck("Failure");
			return voucherServiceRS;
			
		}
		
		if(voucherServiceRQ.isSetObjectId()){
			
			logger.debug("Checking objectid");
			if(!commonValidations.checkObjectId(voucherServiceRQ.getObjectId())){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1103);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
				errorsType.add(errorType);
				voucherServiceRS.setErrors(error);
				voucherServiceRS.setAck("Failure");
				return voucherServiceRS;
			}/*else
				objectIdFlag=true;
			*/
			
		}else{
			
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1103);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
			errorsType.add(errorType);
			voucherServiceRS.setErrors(error);
			voucherServiceRS.setAck("Failure");
			return voucherServiceRS;
			
		}
		
		int checkPaidStatus=0;
		long itemid=0;
		if(voucherServiceRQ.isSetItemId()){
			
			if(!voucherServiceRQ.getItemId().trim().isEmpty()){
				boolean itemidStatus=false;
				try{
					logger.debug("This is itemid checking");
					itemid=Long.parseLong(voucherServiceRQ.getItemId());
				}catch(Exception e){
					logger.debug("This is itemid checking failed");
				}
				
				if(itemid>0){
					itemidStatus=voucherDAOImpl.checkEbayItemId(itemid,voucherServiceRQ.getObjectId());
				}
				
				if(itemidStatus){
					
					checkPaidStatus++;
					
				}else if(!itemidStatus){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(7110);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(7110,langid));
					errorsType.add(errorType);
					voucherServiceRS.setErrors(error);
					voucherServiceRS.setAck("Failure");
					return voucherServiceRS;
				}
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(7116);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(7116,langid));
				errorsType.add(errorType);
				voucherServiceRS.setErrors(error);
				voucherServiceRS.setAck("Failure");
				return voucherServiceRS;
			}
			
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(7110);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(7110,langid));
			errorsType.add(errorType);
			voucherServiceRS.setErrors(error);
			voucherServiceRS.setAck("Failure");
			return voucherServiceRS;
		}
		
		String voucherid=null;
		
		if(voucherServiceRQ.isSetVoucherId()){
			boolean voucherIdStatus=false;
			if(!voucherServiceRQ.getVoucherId().trim().isEmpty()){
				
				voucherIdStatus=voucherDAOImpl.checkVoucherId(itemid, voucherServiceRQ.getVoucherId().trim());
				if(voucherIdStatus){
					checkPaidStatus++;
					voucherid=voucherServiceRQ.getVoucherId();
				}else if(!voucherIdStatus){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(7117);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(7117,langid));
					errorsType.add(errorType);
					voucherServiceRS.setErrors(error);
					voucherServiceRS.setAck("Failure");
					return voucherServiceRS;
				}
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(7118);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(7118,langid));
				errorsType.add(errorType);
				voucherServiceRS.setErrors(error);
				voucherServiceRS.setAck("Failure");
				return voucherServiceRS;
			}
			
			
		}else{
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(7117);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(7117,langid));
			errorsType.add(errorType);
			voucherServiceRS.setErrors(error);
			voucherServiceRS.setAck("Failure");
			return voucherServiceRS;
		}
		
		if(checkPaidStatus==2 && itemid>0){
			boolean paidStatus=voucherDAOImpl.checkPaidStatus(itemid, voucherServiceRQ.getVoucherId());
			if(!paidStatus){
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(7113);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(7113,langid));
				errorsType.add(errorType);
			}
		
		}
		
		Long orderid=null;
		
		if(voucherServiceRQ.isSetOrderId()){
			
			if(!voucherServiceRQ.getOrderId().trim().isEmpty()){
			try{
				orderid=Long.parseLong(voucherServiceRQ.getOrderId());
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
		
		if(errorsType.size()>0){
			voucherServiceRS.setAck("Failure");
			voucherServiceRS.setErrors(error);
		}else{
			voucherServiceRS.setAck("Success");
		}
		
		return voucherServiceRS;
	}

}
