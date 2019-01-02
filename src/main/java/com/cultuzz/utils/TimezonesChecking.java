package com.cultuzz.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class TimezonesChecking {

	public static void main(String[] args)throws Exception {
		TimezonesChecking myobject=new TimezonesChecking();
		Date tt=myobject.stringToDateAnotherFormat("2018-08-10 11:50:00");
		Date dd=myobject.convertFromOneTimeZoneToOhter(tt,"IST","MESZ");
		System.out.println("mydate"+dd);
		
		System.out.println("mytimeee"+myobject.convTimeZone("2018-08-10 17:00:00", "MESZ","GMT","yyyy-MM-dd HH:mm:ss"));
	}
	
	public Date stringToDateAnotherFormat(String dateString) throws ParseException {
        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sourceFormat.parse(dateString);
    }
	
	public Date convertFromOneTimeZoneToOhter(Date dt,String from,String to ) {

	      TimeZone fromTimezone =TimeZone.getTimeZone(from);//get Timezone object
	      TimeZone toTimezone=TimeZone.getTimeZone(to);
	             
	      long fromOffset = fromTimezone.getOffset(dt.getTime());//get offset
	      long toOffset = toTimezone.getOffset(dt.getTime());
	     
	      //calculate offset difference and calculate the actual time
	      long convertedTime = dt.getTime() - (fromOffset - toOffset);
	      Date d2 = new Date(convertedTime);
	     
	      return d2;
	    }
	
	   /*public XMLGregorianCalendar CETtoGMT(GregorianCalendar gc) {
	       XMLGregorianCalendar gmtTime = null;
	       try {
	           DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	           DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	           DateFormat df3 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	           String dateTime = df1.format(gc.getTime());
	           df2.setTimeZone(TimeZone.getTimeZone("CET"));
	           Date d1 = df2.parse(dateTime);
	           df3.setTimeZone(TimeZone.getTimeZone("GMT"));
	           gmtTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(df3.format(d1).trim());
	           return gmtTime;
	       } catch (Exception ex) {
	           Logger.getLogger(TimeZoneConverter.class.getName()).log(Level.SEVERE, null, ex);
	       }
	       return gmtTime;
	   }
	}*/
	
	 public static String convTimeZone(String time, String sourceTZ, String destTZ, String format) {

	       SimpleDateFormat sdf = new SimpleDateFormat(format);

	       Date specifiedTime = null;
	       try {
	           if (sourceTZ != null) {
	               sdf.setTimeZone(TimeZone.getTimeZone(sourceTZ));
	           } else {
	               sdf.setTimeZone(TimeZone.getDefault()); // default to server's timezone
	           }
	           specifiedTime = sdf.parse(time);
	       } catch (Exception e1) {
	           e1.printStackTrace();
	       }

	       // switch timezone
	       if (destTZ != null) {
	           sdf.setTimeZone(TimeZone.getTimeZone(destTZ));
	       } else {
	           sdf.setTimeZone(TimeZone.getDefault()); // default to server's timezone
	       }
	// System.out.println("converted date is" + sdf.format(specifiedTime));
	       return sdf.format(specifiedTime);
	   }
	
}
