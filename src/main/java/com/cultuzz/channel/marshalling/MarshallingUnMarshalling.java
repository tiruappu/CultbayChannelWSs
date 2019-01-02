package com.cultuzz.channel.marshalling;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;

import javax.xml.transform.stream.StreamSource;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

public class MarshallingUnMarshalling {
	
	private Marshaller marshaller;
    private Unmarshaller unmarshaller;
	
	public Object getUnMarshallerObj(String clientResponse) {

        int status=0;
        Object itemRs=null;
         
       try {
    	   Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
    	   unmarshaller=jaxb2Marshaller;
           
          
        	   jaxb2Marshaller.setPackagesToScan("com.cultuzz.channel.XMLpojo");
               //addItemJaxbContext = JAXBContext.newInstance("com.zuzzu");
           
           FileInputStream fis = new FileInputStream(clientResponse);
            itemRs= (Object) unmarshaller.unmarshal(new StreamSource(fis)); 
       
       } catch (Exception e) {
           e.printStackTrace();
          
       }
       return itemRs;
      

   }

}
