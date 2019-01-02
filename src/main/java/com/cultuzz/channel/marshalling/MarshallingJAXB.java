package com.cultuzz.channel.marshalling;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.handler.CultbayChannelController;

@Component
public class MarshallingJAXB {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MarshallingJAXB.class);
	
	
	public String objToXml(Object cobject){
		String outputXml=null;
	try{
		StringWriter sw=new StringWriter();
		
		JAXBContext contextObj = JAXBContext.newInstance("com.cultuzz.channel.XMLpojo");  
	  
	    Marshaller marshallerObj = contextObj.createMarshaller();  
	    marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
	    marshallerObj.marshal(cobject,sw);  
	    outputXml=sw.toString();
	   // System.out.println("outpur::::::"+sw.toString());
	    LOGGER.debug("Output xml==={}",outputXml);
	}catch(Exception e){
		e.printStackTrace();
	}
	
	return outputXml;
	}
	
	public String objToXmlVoucher(Object cobject){
		String outputXml=null;
	try{
		StringWriter sw=new StringWriter();
		
		JAXBContext contextObj = JAXBContext.newInstance("com.cultuzz.XMLPojos");  
	  
	    Marshaller marshallerObj = contextObj.createMarshaller();  
	    marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
	    marshallerObj.marshal(cobject,sw);  
	    outputXml=sw.toString();
	    //System.out.println("outpur::::::"+sw.toString());
	}catch(Exception e){
		e.printStackTrace();
	}
	
	return outputXml;
	}
	
	public Object xmlToObjVoucher(String inputxml){
		Object obj=null;
	try{
		StringWriter sw=new StringWriter();
		
		JAXBContext contextObj = JAXBContext.newInstance("com.cultuzz.XMLPojos");  
	  
		Unmarshaller unMarshaller=contextObj.createUnmarshaller();
	    
		 obj=unMarshaller.unmarshal(new StreamSource(new StringReader(inputxml)));
	    
	}catch(Exception e){
		e.printStackTrace();
	}
	
	return obj;
	}
	
	public Object xmlToObj(String inputxml){
		Object obj=null;
	try{
		StringWriter sw=new StringWriter();
		
		JAXBContext contextObj = JAXBContext.newInstance("com.cultuzz.channel.XMLpojo");  
	 // System.out.println("This is obj to xml================================"+inputxml);
		Unmarshaller unMarshaller=contextObj.createUnmarshaller();
	    
		 obj=unMarshaller.unmarshal(new StreamSource(new StringReader(inputxml)));
	    
	}catch(Exception e){
		e.printStackTrace();
	}
	//System.out.println("This is return obj"+obj);
	return obj;
	}
}
