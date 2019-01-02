package com.cultuzz.channel.helper.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import com.cultuzz.XMLPojos.VoucherCreationRQ;
import com.cultuzz.XMLPojos.VoucherCreationRS;
import com.cultuzz.channel.DAO.TemplateModuleDAO;
import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
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
import com.cultuzz.channel.XMLpojo.VoucherServiceRS.Voucher;
import com.cultuzz.channel.helper.VoucherHelper;
import com.cultuzz.channel.marshalling.MarshallingJAXB;
import com.cultuzz.channel.util.Base64Converter;
import com.cultuzz.channel.util.CommonValidations;
import com.cultuzz.channel.util.HttpUrlConnection;
import com.cultuzz.channel.util.Processor;

@Configuration
@Qualifier("voucherServiceHelper")
public class VoucherServiceHelperImpl implements VoucherHelper{
	
	@Autowired
	CommonValidations commonValidations;
	
	@Autowired
	GetErrorMessagesDAOImpl getErrormessages;
	
	@Autowired
	Base64Converter base64Converter;
	
	@Autowired
	HttpUrlConnection httpUrlConnection;
	
	@Autowired
	MarshallingJAXB marshallingJAXB;
	
	@Autowired
	TemplateModuleDAO templateModuleDAO;
	
	@Autowired
	Processor processor;
	
	private static final Logger logger = LoggerFactory.getLogger(VoucherServiceHelperImpl.class);
	
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

	public VoucherRedemptionRS processVoucherRedeemptionHelper(
			VoucherRedemptionRQ voucherRedeemptionRQ) {
		// TODO Auto-generated method stub
		return null;
	}

	public VoucherServiceRS processsVoucherServiceHelper(
			VoucherServiceRQ voucherServiceRQ) {
		// TODO Auto-generated method stub
		VoucherServiceRS voucherServiceRS=new VoucherServiceRS();
		voucherServiceRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		
		logger.debug("inside template preview process in helper");
		int objectid=0;
		try{
		 objectid=voucherServiceRQ.getObjectId();
		}catch(NumberFormatException nfe){
			nfe.printStackTrace();
		}
		
		int langid=commonValidations.checkErrorLangCode(voucherServiceRQ.getErrorLang());	
		
		ErrorsType errors=new ErrorsType();
		//List<String> previewErrors=new ArrayList<String>();
		List<ErrorType> errorTypeList=errors.getError();
		long itemid=0;
		boolean voucherStatus=false;
		try{
			itemid=Long.parseLong(voucherServiceRQ.getItemId());
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		//String url=http://localhost:8080/VoucherService/generateVoucher
		VoucherCreationRQ voucherCreationRQ=new VoucherCreationRQ();
		int pdftypeid=0;
		
		String czidString=voucherServiceRQ.getVoucherId();
		
		StringBuffer sb=new StringBuffer();
		
		if(czidString.length()==4){
			sb.append(czidString);
		}else if(czidString.length()==3){
			sb.append("0"+czidString);
		}else if(czidString.length()==2){
			sb.append("00"+czidString);
		}else if(czidString.length()==1){
			sb.append("000"+czidString);
		}
		
		String czid=sb.toString();
		
		if(objectid>0){
			
			if(templateModuleDAO.getThemeId(objectid)>0)
				pdftypeid=templateModuleDAO.getThemeId(objectid);
			
				/*if(pdftypeid>0)
				voucherCreationRQ.setThemeId(pdftypeid);
				else
				voucherCreationRQ.setThemeId(9);//this is 9 for che pdf theme type id
*/		}
		long ebayItemid=0;
		try{
			ebayItemid=Long.parseLong(voucherServiceRQ.getItemId());
		}catch(Exception e){
			e.printStackTrace();
		}
		int languageid=templateModuleDAO.getLangidByItemid(ebayItemid);
		Voucher voucher=new Voucher();
		if(pdftypeid>=7){
		
		
		if(templateModuleDAO.getVorlageId(itemid)>0)
		voucherCreationRQ.setVorlageId(templateModuleDAO.getVorlageId(itemid));
		
		
		voucherCreationRQ.setItemId(voucherServiceRQ.getItemId());
		voucherCreationRQ.setCzId(czid);
		voucherCreationRQ.setPreview(false);
		voucherCreationRQ.setThemeId(pdftypeid);
		
		
		
		//System.out.println("this is language id"+languageid);
		if(languageid>0)
		voucherCreationRQ.setLanguageId((byte)languageid);
		else
			voucherCreationRQ.setLanguageId((byte)1);
		
		
		try{
		/*	PDFAnzeige=1
					hand_auto=1
							sendHotelMail=0
									preview=1
											sig=	md5 ($itemid.' - '.$_GET['ObjectID'].' ->> sind jetzt absolut toll verschlüsselt')
			*///http://backups.cultuzz.de/mycultuzz/pdf/pdfErstellen.php?ebayItemID=110189806174&czTransID=0001&PDFAnzeige=1&hand_auto=1&sendHotelMail=0&Language=2&sig=d62ba6fbf1e4928f1372fcc412779bdd&preview=1
			String inputXml=marshallingJAXB.objToXmlVoucher(voucherCreationRQ);
			
			String respnseVoucherXml=httpUrlConnection.sendPOSTRequest(inputXml);
			
			VoucherCreationRS voucherCreationRS=(VoucherCreationRS) marshallingJAXB.xmlToObjVoucher(respnseVoucherXml);
		
			String pdfUrl=null;
			
			if(voucherCreationRS!=null &&voucherCreationRS.getSuccess()!=null && voucherCreationRS.getSuccess().trim().isEmpty()){
				pdfUrl=voucherCreationRS.getVoucherURL();
			}else{
				voucherStatus=true;
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(7131);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(7131,langid));
				errorTypeList.add(errorType);
			}
			
			String encodedPdf=null;
			if(pdfUrl!=null)
				encodedPdf=	base64Converter.converter(pdfUrl);
			
			if(encodedPdf!=null){
				voucher.setEncodedText(encodedPdf);
				voucher.setVoucherId(voucherServiceRQ.getVoucherId());
			}
			
			
			
			//voucher.setEncodedText();
		}catch(Exception e){
			if(!voucherStatus){
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(7131);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(7131,langid));
			errorTypeList.add(errorType);
			e.printStackTrace();
			}
		}
		
		}else{			
			String ebayitemid=voucherServiceRQ.getItemId();
			int objectId=voucherServiceRQ.getObjectId();
			if(languageid==0){
				languageid=1;
			}
			
			String responseurl=httpUrlConnection.sendPOSTBasicVoucherRequest(ebayitemid,czid,objectId,languageid);
			logger.debug("voucher pdf url{}",responseurl);
			String base64String=null;
			if(!responseurl.equals("")){
				try {
					base64String=	base64Converter.converter(responseurl);
					
					if(base64String!=null){
						voucher.setEncodedText(base64String);
						voucher.setVoucherId(voucherServiceRQ.getVoucherId());
						
					}
					
				} catch (Exception e) {
					// TODO: handle exception
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(7131);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(7131,langid));
					errorTypeList.add(errorType);
					e.printStackTrace();
					
					logger.debug("problem occured in getting pdf");
					e.printStackTrace();
				}
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(7131);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(7131,langid));
				errorTypeList.add(errorType);
				
			}
			
		}
		
		if(errorTypeList.size()>0){
			voucherServiceRS.setAck("Failure");
			voucherServiceRS.setErrors(errors);
		}else{
			voucherServiceRS.setAck("Success");
			voucherServiceRS.setVoucher(voucher);
			voucherServiceRS.setItemId(voucherServiceRQ.getItemId());
			//voucherServiceRS.setOrderId();
		}
	
		
		return voucherServiceRS;
	}

}