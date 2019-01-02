package com.cultuzz.XMLPojos;
//package com.cultuzz.XMLPojos;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ramakrishnakadali
 */

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Utility class to correctly render the xml types used in JAXB.
 */
public class JaxbBooleanAdapter extends XmlAdapter<String, Boolean> {

    @Override
    public Boolean unmarshal(String v) throws Exception {
        if ("1".equals(v) || "true".equalsIgnoreCase(v)) {
            return true;
        }
        return false;
    }

    @Override
    public String marshal(Boolean v) throws Exception {
        if (v == null) {
            return null;
        }
        if (v) {
            return "true";
        }
        return "false";
    }
}