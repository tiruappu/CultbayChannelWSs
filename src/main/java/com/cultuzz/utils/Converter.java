/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cultuzz.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.TimeZone;
import javax.xml.datatype.DatatypeConstants;

/**
 *
 * @author ramakrishnakadali
 */
public class Converter {

    //private final static String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";
    private final static String dateFormat = "yyyy-MM-dd";

    public static XMLGregorianCalendar stringToXMLGregorianCalendar(String date) throws ParseException, DatatypeConfigurationException {
        XMLGregorianCalendar result = null;
        SimpleDateFormat simpleDateFormat;
        GregorianCalendar gregorianCalendar;
        simpleDateFormat = new SimpleDateFormat(dateFormat);
        gregorianCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
        gregorianCalendar.setTime(simpleDateFormat.parse(date));
        result = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        return result;
    }

    public static XMLGregorianCalendar stringToXMLGregorianCalendar(String date, String format) throws ParseException, DatatypeConfigurationException {
        return stringToXMLGregorianCalendar(date);
    }

    public static Date xMLGregorianCalendarToDate(XMLGregorianCalendar xMLGregorianCalendar) {
        if (xMLGregorianCalendar == null) {
            return null;
        }
        return xMLGregorianCalendar.toGregorianCalendar().getTime();
    }

    public static XMLGregorianCalendar dateToXMLGregorianCalendar(Date date) {
//        DatatypeFactory df = null;
//        if (date == null) {
//            return null;
//        }
//        try {
//            df = DatatypeFactory.newInstance();
//        } catch (DatatypeConfigurationException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        GregorianCalendar gc = new GregorianCalendar();
//        gc.setTimeInMillis(date.getTime());
//        return df.newXMLGregorianCalendar(gc);
        DatatypeFactory df = null;
       if (date == null) {
           return null;
       }
       try {
           df = DatatypeFactory.newInstance();
       } catch (DatatypeConfigurationException ex) {
           ex.printStackTrace();
           return null;
       }
       GregorianCalendar gc = new GregorianCalendar();
       gc.setTimeInMillis(date.getTime());
       XMLGregorianCalendar xmlgc = df.newXMLGregorianCalendar(gc);
       xmlgc.setTimezone( DatatypeConstants.FIELD_UNDEFINED );
       xmlgc.setFractionalSecond( null );
       return xmlgc;
    }

    public static Date stringToDate(String dateString, String fromFormat) throws ParseException {
        SimpleDateFormat sourceFormat = new SimpleDateFormat(fromFormat);
        sourceFormat.setTimeZone(TimeZone.getTimeZone("PST"));
        
        SimpleDateFormat gmtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sourceFormat.parse(dateString);
        //gmtFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        return gmtFormat.parse(gmtFormat.format(date));
    }

    public static Date stringToDate(String dateString) throws ParseException {
        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd");
        return sourceFormat.parse(dateString);
    }
    
    public static String dateToString(Date date, String toFormat) {
        return date.toString();
    }

    public static void main(String[] args) throws ParseException, DatatypeConfigurationException {
        String payment_date = "06:58:23 Nov 29, 2013 PST";
        Converter converter = new Converter();//yyyy-MM-dd'T'HH:mm:ss
        //converter.setDateFormat("HH:mm:ss MMM dd, yyyy z");//2013-11-29T20:28:23.000+05:30
        System.out.println(Converter.stringToDate(payment_date, "HH:mm:ss MMM dd, yyyy zzz"));
    }
}
