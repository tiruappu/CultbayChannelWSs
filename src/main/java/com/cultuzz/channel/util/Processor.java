package com.cultuzz.channel.util;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.regex.Pattern;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.XMLpojo.OfferReviseRQ;
import com.cultuzz.channel.handler.CultbayChannelController;
import com.mysql.jdbc.log.Log;

@Component
public class Processor {
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    private static final Logger LOGGER = LoggerFactory
			.getLogger(Processor.class);

    
    public void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }
    //Converts Object to XML file
    public void objectToXML(String fileName, Object graph) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileName);
           // marshaller.marshal(graph, new StreamResult(fos));
        } finally {
        	fos.close();
        }
    }
    
    public String objectToXMLString(Object graph) throws XmlMappingException, IOException  {
        StringWriter xmlWriter = new StringWriter();
        
        try {
        	String xml = new String();
        	 
           //  return str;
        	marshaller.marshal(graph, new StreamResult(xmlWriter));
            //String str ="\\";
        	String regex = "&amp;lt;!\\[CDATA\\[(.*?)\\]\\]&amp;gt;";
        	//xml = xmlWriter.toString().replaceAll("&amp;lt;!\\[CDATA\\[", "<![CDATA[");
        	//xml = xmlWriter.toString().replaceAll("]]&amp;&gt;", "]]>");
        	 xml = Pattern.compile(regex, Pattern.MULTILINE | Pattern.UNIX_LINES | Pattern.DOTALL).matcher(xmlWriter.toString()).replaceAll("<![CDATA[$1]]>");
           // xml = xmlWriter.toString();
            return xml;
        } finally {
        	xmlWriter= null;
        }
    }
    //Converts XML to Java Object
    public Object xmlToObject(String fileName) throws IOException {
    	
    	LOGGER.debug("Inside xmlToObject"+fileName);
        FileInputStream fis = null;
        try {
            
        	fis = new FileInputStream(fileName);
//            LOGGER.debug("");
           
            return unmarshaller.unmarshal(new StreamSource(fis));
        }catch(Exception e){
        	e.getCause();
        	LOGGER.debug("Inside Exception");
        	return new Object();
        }
//        finally {
//        	fis.close();
//        }
    }
    public Object stringToObject(String string) throws IOException{
    	
    	LOGGER.debug("Inside stringToObject:::"+string);
    	try{
    		return unmarshaller.unmarshal(new StreamSource(new StringReader(string)));
    		//return unmarshaller.unmarshal(string,OfferReviseRQ.class);
    	} finally{
    		
    	}
    		
    }
}