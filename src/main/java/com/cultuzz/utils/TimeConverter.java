package com.cultuzz.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class TimeConverter {

	 public static void main(String args[]){
		 try{
		String ss="2018-05-04 15:00:00";
		 String sDate1="2018-02-25 15:00:00";  
		   // Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);  
		  Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate1);
		  DateFormat cetFormat = new SimpleDateFormat();
		  DateFormat gmtFormat = new SimpleDateFormat();
		  TimeZone gmtTime = TimeZone.getTimeZone("PDT");
		  TimeZone cetTime = TimeZone.getTimeZone("MESZ");
		  cetFormat.setTimeZone(gmtTime);
		  gmtFormat.setTimeZone(cetTime);
		  System.out.println(date);
		  
		  System.out.println("GMT Time: " + cetFormat.format(date));
		  System.out.println("CET Time: " + gmtFormat.format(date));
		  
		  int i=90/100;
		  int p=90%100;
		  System.out.println("sdfsdaaaaa"+i+"dssssssssss"+p);
		  
		  
		  
		  
		  
		  
		  
		  
		  TimeZone timeZ = TimeZone.getTimeZone("PDT");
		    DateFormat dateFormat=new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
	 		dateFormat.setTimeZone(timeZ);
	 		
	 		Date sdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-05-04 15:00:00");
	  		String startDate=dateFormat.format(sdate);
		  
		  System.out.println("start timeee issss"+startDate);
		  Date newdateobj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate1);
		  Calendar germanyTime = new GregorianCalendar(TimeZone.getTimeZone("Europe/Berlin"));
	        
	       
	        
	        Calendar cal = Calendar.getInstance();
	        //SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
	        cal.setTime(newdateobj);
	        germanyTime.setTimeInMillis(cal.getTimeInMillis());
		  
	        Date outputcal=germanyTime.getTime();
	        System.out.println("output"+outputcal);
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	 }
	 
	 
	 public String getConvertedtime(String timezone,String time){
		 String covertedTime=null;
		 try{
			 
			 TimeZone timeZ = TimeZone.getTimeZone(timezone);
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 return covertedTime;
	 }
	
	
}
