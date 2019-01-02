package com.cultuzz.channel.helper.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			/*Date date = new Date();
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			date = calendar.getTime();*/
			
	/*	java.util.Date date = new java.util.Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
         String startDate =timestamp.toString();
         Date frmDate = sdf.parse(startDate);
         SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdff.setTimeZone(TimeZone.getTimeZone("GMT"));
         String dateInString = sdff.format(frmDate);
         System.out.println("This is for date string"+dateInString);
         
         String date1="2016-02-28 04:22:40";
         String date2="2017-02-28 04:22:40";
         
         System.out.println("compare toooo:"+date1.compareTo(date2));*/
			
			
			//Double.parseDouble
        //System.out.println("my value isss"+String.format("%.2f", Double.parseDouble("10.0")));
		//String.format("%.2f", Double.parseDouble("10.0"));
         
        final String regExp = "([+-])?[0-9]{1,3}+([.][0-9]{0,8})?";
        final Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher("-9.41545555"); 
        System.out.println(matcher.matches());
         
        List<String> li=new ArrayList<String>();
        
        li.add("a");
        li.add("b");
        li.add("c");
        li.add("d");
        li.add("e");
        li.add("f");
        li.add("g");
        li.add("h");
        li.add("i");
        li.add("d");
        
        System.out.println(li.size());
        System.out.println(li.subList(4, li.size()).contains("c"));
        
        
        
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}



