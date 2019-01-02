package com.cultuzz.channel.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	private static DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static DateFormat dateFormat2 = new SimpleDateFormat(
			"yyyy-MM-dd");
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DateUtil.class);

	public static Date covertStringToDate(String stringDate) {

		try {
			LOGGER.debug("Date:: {} {} ", stringDate, dateFormat);
			LOGGER.debug("Date:: {}", stringDate);
			return dateFormat.parse(stringDate.trim());

		} catch (ParseException exception) {
			exception.printStackTrace();
			LOGGER.error("Eexception @ {}", exception);
			return null;
		} catch (Exception e) {
			LOGGER.error("Exception @ {} ", e);
			return null;
			// TODO: handle exception

		}

	}

	public static Date covertStringToDateDMY(String stringDate) {

		try {
			LOGGER.debug("Date:: {} {} ", stringDate, dateFormat2);
			LOGGER.debug("Date:: {}", stringDate);
			return dateFormat2.parse(stringDate.trim());

		} catch (ParseException exception) {
			exception.printStackTrace();
			LOGGER.error("Eexception @ {}", exception);
			return null;
		} catch (Exception e) {
			LOGGER.error("Exception @ {} ", e);
			return null;
			// TODO: handle exception

		}

	}
	public static String convertDateToString(Date date) {

		return dateFormat.format(date);

	}

	public static String convertDateToStringDMY(Date date) {

		return dateFormat2.format(date);

	}
	public static XMLGregorianCalendar convertDateToXMLGregorianCalendar(
			Date date) {
		XMLGregorianCalendar gregorianCalendarDate = null;
		try {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(date);
			gregorianCalendarDate = DatatypeFactory.newInstance()
					.newXMLGregorianCalendarDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1 ,c.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);

		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return gregorianCalendarDate;
	}

	public static XMLGregorianCalendar covertStringToXMLGregorianCalendar(
			String stringDate) {
		XMLGregorianCalendar gregorianCalendarDate = null;
		Date date = null;
		try {
			date = covertStringToDate(stringDate);
			gregorianCalendarDate = convertDateToXMLGregorianCalendar(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gregorianCalendarDate;
	}
}
