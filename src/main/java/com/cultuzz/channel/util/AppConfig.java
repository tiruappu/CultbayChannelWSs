package com.cultuzz.channel.util;


import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.cultuzz.channel.util.Processor;

@Configuration
public class AppConfig {

	public Processor getHandler(String scan) {
		Processor handler = new Processor();
		handler.setMarshaller(getCastorMarshaller(scan));
		handler.setUnmarshaller(getCastorMarshaller(scan));
		return handler;
	}

	public Jaxb2Marshaller getCastorMarshaller(String scan) {
		// String scan = "manual";
		try {
			
			Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
			if (scan.equalsIgnoreCase("XMLpojo")) {
				jaxb2Marshaller.setPackagesToScan("com.cultuzz.channel.XMLpojo");
			} 
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxb2Marshaller.setMarshallerProperties(map);
			return jaxb2Marshaller;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}

}
