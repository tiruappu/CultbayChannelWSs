package com.cultuzz.channel.util;

import java.util.HashMap;
import java.util.Map;

public class JavaClass {
	public static void main(String[] args) {
		try{
		/*GetVoucherPdf_ServiceLocator service = new GetVoucherPdf_ServiceLocator();
	        com.cultuzz.services.GetVoucherPdf_PortType port = service.getGetVoucherPdfPort();
	        String res=port.generateVoucher("String");
			VoucherServiceRQ voucherServiceRQ=new VoucherServiceRQ();
			VoucherServiceHelperImpl vs=new VoucherServiceHelperImpl();
			vs.processsVoucherServiceHelper(voucherServiceRQ);*/
			
			
			String s1="abc";
			String s2="abc";
			String s3=new String("abc");
			String s4=new String("abc");
			System.out.println(s1==s2);
			System.out.println(s1.equals(s2));
			System.out.println(s1==s4);
			System.out.println(s1.equals(s4));
	       // System.out.println(res);
			
			Map mm=new HashMap();
			
			mm.put(1, "tiru");
			mm.put(2,"welcome");
			
			System.out.println(mm.get(2));
			System.out.println(mm.get(3));
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
