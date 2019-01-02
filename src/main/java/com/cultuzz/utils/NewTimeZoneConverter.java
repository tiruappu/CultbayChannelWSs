package com.cultuzz.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class NewTimeZoneConverter {

	 private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	    public static void main(String[] args) throws ParseException {
	    	
	    	String dateInString = "2018-04-27 17:15:00";
	    	Date date = new SimpleDateFormat(DATE_FORMAT).parse(dateInString);

	        
	        //Date date = formatter.parse(dateInString);
	        
	        SimpleDateFormat sdfAmerica = new SimpleDateFormat(DATE_FORMAT);
	        TimeZone tzInAmerica = TimeZone.getTimeZone("Europe/Berlin");
	        sdfAmerica.setTimeZone(tzInAmerica);
	        
	        SimpleDateFormat UKformat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
	        TimeZone tzInUK = TimeZone.getTimeZone("Europe/London");
	        UKformat.setTimeZone(tzInUK);
	        
	        
	        //USA America/Los_Angeles
	        //AUS Australia/Adelaide
	        //IN Asia/Kolkata
	        //Asia/Manila
	        
	        Calendar calendar = new GregorianCalendar();
	        calendar.setTime(date);
	        calendar.setTimeZone(tzInAmerica);
	        
	        System.out.println("German time " + sdfAmerica.format(calendar.getTime()));
	        System.out.println("Uk time " + UKformat.format(calendar.getTime()));
	        
	       /* TimeZone tz = TimeZone.getDefault();

	        // From TimeZone Asia/Singapore
	        System.out.println("TimeZone : " + tz.getID() + " - " + tz.getDisplayName());
	        System.out.println("TimeZone : " + tz);
	        System.out.println("Date (Singapore) : " + formatter.format(date));*/

	        // To TimeZone America/New_York
	        

	        

	     /*   System.out.println("\nTimeZone : " + tzInAmerica.getID() + " - " + tzInAmerica.getDisplayName());
	        System.out.println("TimeZone : " + tzInAmerica);

	        //Wrong! It will print the date with the system default time zone
	        System.out.println("Date (New York) (Wrong!): " + calendar.getTime());*/

	        //Correct! need formatter
	        
	        
	        
	        
	        


	    }
}
