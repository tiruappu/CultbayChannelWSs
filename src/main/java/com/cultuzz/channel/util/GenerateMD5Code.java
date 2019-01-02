package com.cultuzz.channel.util;

import java.security.MessageDigest;

public class GenerateMD5Code {

	
	public String generateMD5String(String convertString){
		String md5String="";
		
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(convertString.getBytes("UTF-8"));
	        
	        byte byteData[] = md.digest();

	        //convert the byte to hex format method 1
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	       /* StringBuffer sb1 = new StringBuffer();
	        for (byte b : byteData) {
				sb1.append(String.format("%02x", b & 0xff));
			}
	        System.out.println(sb1.toString());*/
	        md5String=sb.toString();
	        System.out.println(sb);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return md5String;
	}
	
	public static void main(String x[]){
		GenerateMD5Code ss=new GenerateMD5Code();
		//991176f7c7a2d724e7ac46666823dba3
		ss.generateMD5String("171976425822 - 122 ->> sind jetzt absolut toll verschlüsselt");
	}
}
