/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cultuzz.XMLPojos;
//package com.cultuzz.XMLPojos;
import com.cultuzz.utils.Converter;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author padmini
 */
public class DateTypeAdapter extends XmlAdapter<XMLGregorianCalendar, Date>{

    @Override
    public Date unmarshal(XMLGregorianCalendar vt) throws Exception {
        System.out.println("date sttring::"+Converter.xMLGregorianCalendarToDate(vt));
        System.out.println("date sttring::"+Converter.xMLGregorianCalendarToDate(vt).toString());
        return  Converter.xMLGregorianCalendarToDate(vt);
    }

    @Override
    public XMLGregorianCalendar marshal(Date bt) throws Exception {
       // System.out.println("date in marshall:::"+Converter.dateToXMLGregorianCalendar(bt));
        //System.out.println("date in marshall with tostring::"+Converter.dateToXMLGregorianCalendar(bt).toString());
        return Converter.dateToXMLGregorianCalendar(bt);
    }
    
}
