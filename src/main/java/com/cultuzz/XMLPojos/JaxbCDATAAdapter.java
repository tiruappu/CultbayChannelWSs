/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cultuzz.XMLPojos;
//package com.cultuzz.XMLPojos;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author padmini
 */
public class JaxbCDATAAdapter extends XmlAdapter<String, String>{

    @Override
    public String marshal(String v) throws Exception {
        return "<![CDATA[" + v + "]]>";
    }

    @Override
    public String unmarshal(String v) throws Exception {
        return v;
    }
    
}
