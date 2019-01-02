package com.cultuzz.channel.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import com.cultuzz.services.GetVoucherPdf_ServiceLocator;
import com.cultuzz.utils.PropertiesLoader;

@Configuration
public class HttpUrlConnection {
	
	@Autowired
	@Qualifier("propertiesRead")
	PropertiesLoader properties;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpUrlConnection.class);
	public String sendPOSTRequest(String xml) {
		String res = "";
		try {
			GetVoucherPdf_ServiceLocator service = new GetVoucherPdf_ServiceLocator();
	        com.cultuzz.services.GetVoucherPdf_PortType port = service.getGetVoucherPdfPort();
	        //BindingProvider bp = (BindingProvider) port;
	       // bp.getRequestContext().put("javax.xml.ws.client.receiveTimeout", "600000");
	         res=port.generateVoucher(xml);
	        //System.out.println(res);
		} catch (Exception ioException) {
			ioException.printStackTrace();
		}

		return res;
	}
	
	public String sendPOSTBasicVoucherRequest(String ebayitemid,String czid,int objectid,int languageid){
		//backups
		//http://backups.cultuzz.de/mycultuzz/pdf/voucherChePreview.php?ItemID=230650176776&ObjectID=122&Language=2
		//live
		//http://albatros.cultuzz.de/mycultuzz/pdf/voucherChePreview.php
		//StringBuffer vouchergetUrl=new StringBuffer("http://backups.cultuzz.de/mycultuzz/pdf/voucherChePreview.php?");
		//StringBuffer vouchergetUrl=new StringBuffer("http://albatros.cultuzz.de/mycultuzz/pdf/voucherChePreview.php?");
		LOGGER.debug("This is voucher service url from properties==="+properties.getVoucherServiceURL());
		StringBuffer vouchergetUrl=new StringBuffer(properties.getVoucherServiceURL());
		vouchergetUrl.append("ItemID="+ebayitemid+"&");
		vouchergetUrl.append("czTransID="+czid+"&");
		vouchergetUrl.append("ObjectID="+objectid+"&");
		vouchergetUrl.append("Language="+languageid);
		
		String response="";
		
		try{
			LOGGER.debug("input url is {}",vouchergetUrl.toString());
			
			 //long startTime = System.currentTimeMillis();
			System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
	            URL url = new URL(vouchergetUrl.toString());
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setDoInput(true);
	            conn.setDoOutput(true);
	           // conn.setInstanceFollowRedirects(false);
	            conn.setRequestMethod("GET");
	            conn.setRequestProperty("Content-Type", "text");
	            conn.setRequestProperty("charset", "utf-8");
	            conn.setDoOutput(true);

	            /*OutputStream os = conn.getOutputStream();
	            os.write(requestXml.getBytes());*/

	            conn.connect();
	            conn.getResponseCode();
	            

	            StringBuffer responsetext = null;
	           
	            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
	                BufferedReader in = new BufferedReader(new InputStreamReader(
	                        conn.getInputStream()));
	                String inputLine;

	                responsetext = new StringBuffer();
	                while ((inputLine = in.readLine()) != null) {
	                	responsetext.append(inputLine);
	                }
	                in.close();
	                response=responsetext.toString();
	               
	            }
	            LOGGER.debug("Voucher url{}",response);
			System.out.println("response of"+response);
	            
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return response;
	}
	
	
	/*public static void main(String x[]){
		HttpUrlConnection huc=new HttpUrlConnection();
		huc.sendPOSTBasicVoucherRequest("230685775579","",122);
	}
	*/
}
