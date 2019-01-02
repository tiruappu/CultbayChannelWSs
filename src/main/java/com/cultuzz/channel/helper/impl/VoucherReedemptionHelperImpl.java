package com.cultuzz.channel.helper.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
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
import com.cultuzz.channel.helper.VoucherHelper;
import com.cultuzz.channel.util.CommonValidations;
@Configuration
@Qualifier("voucherReddemHelper")
public class VoucherReedemptionHelperImpl implements VoucherHelper{
	
	@Autowired
	CommonValidations commonValidations;
	
	@Autowired
	GetErrorMessagesDAOImpl getErrormessages;
	
	@Autowired
	VoucherReedemptionDAOImpl voucherReedemptionDAOImpl;
	
	private static final Logger logger = LoggerFactory.getLogger(VoucherReedemptionHelperImpl.class);
	
	public VoucherRedemptionRS processVoucherRedeemptionHelper(VoucherRedemptionRQ voucherRedeemptionRQ){
		boolean redeemStatus=false;
		boolean rollbackStatus=false;
		boolean gutcheineStatus=false;
		VoucherRedemptionRS voucherRedeemptionRS=new VoucherRedemptionRS();
		int langid=0;
		if(voucherRedeemptionRQ.isSetErrorLang()){
		
			langid=commonValidations.checkErrorLangCode(voucherRedeemptionRQ.getErrorLang());
		}
		
		voucherRedeemptionRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		long itemid=0;
		if(voucherRedeemptionRQ.getAction().equals("Redeem")){
			
			redeemStatus=voucherReedemptionDAOImpl.updateVoucherReedemption(voucherRedeemptionRQ);
			
			
		}else if(voucherRedeemptionRQ.getAction().equals("Rollback")){
			
			rollbackStatus=voucherReedemptionDAOImpl.updateRollback(voucherRedeemptionRQ);
			
		}
		
		if(voucherRedeemptionRQ.isSetItemId()){
			try {
				itemid=	Long.parseLong(voucherRedeemptionRQ.getItemId().trim());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		if(itemid>0)
			gutcheineStatus=voucherReedemptionDAOImpl.saveArGutscheine(itemid,voucherRedeemptionRQ.getVoucherId().trim());
		
		if((redeemStatus|| rollbackStatus) && gutcheineStatus){
			voucherRedeemptionRS.setAck("Success");
		}else{
			voucherRedeemptionRS.setAck("Failure");
			ErrorsType error=new ErrorsType();
			List<ErrorType>   errorTypeList=error.getError();
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(7132);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(7132,langid));
			errorTypeList.add(errorType);
			
			voucherRedeemptionRS.setErrors(error);
		}
		
		
		return voucherRedeemptionRS;
	}

	
	
	public VoucherDetailsRS processVoucherDetailsHelper(
			VoucherDetailsRQ voucherDetailsRQ) {
		// TODO Auto-generated method stub
		return null;
	}

	public ListOfVouchersRS processListOfVouchersHelper(
			ListOfVouchersRQ listOfVouchersRQ) {
		// TODO Auto-generated method stub
		return null;
	}

	public VoucherServiceRS processsVoucherServiceHelper(
			VoucherServiceRQ voucherServiceRQ) {
		// TODO Auto-generated method stub
		return null;
	}

}
