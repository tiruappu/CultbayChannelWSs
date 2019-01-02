package com.cultuzz.channel.helper.impl;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class HTMLEntitiesEscape {

	
	  
    private static Map<String, Character> map = new LinkedHashMap<String, Character>();
 
    static 
    {
    	map.put("&quot;", (char) 34);
    	map.put("&amp;", (char) 38);
    	map.put("&lt;", (char) 60);
    	map.put("&gt;", (char) 62);
        map.put("&nbsp;", (char) 160);
        map.put("&iexcl;", (char) 161);
        map.put("&cent;", (char) 162);
        map.put("&pound;", (char) 163);
        map.put("&curren;", (char) 164);
        map.put("&yen;", (char) 165);
        map.put("&brvbar;", (char) 166);
        map.put("&sect;", (char) 167);
        map.put("&uml;", (char) 168);
        map.put("&copy;", (char) 169);
        map.put("&ordf;", (char) 170);
        map.put("&laquo;", (char) 171);
        map.put("&not;", (char) 172);
        map.put("&shy;", (char) 173);
        map.put("&reg;", (char) 174);
        map.put("&macr;", (char) 175);
        map.put("&deg;", (char) 176);
        map.put("&plusmn;", (char) 177);
        map.put("&sup2;", (char) 178);
        map.put("&sup3;", (char) 179);
        map.put("&acute;", (char) 180);
        map.put("&micro;", (char) 181);
        map.put("&para;", (char) 182);
        map.put("&middot;", (char) 183);
        map.put("&cedil;", (char) 184);
        map.put("&sup1;", (char) 185);
        map.put("&ordm;", (char) 186);
        map.put("&raquo;", (char) 187);
        map.put("&frac14;", (char) 188);
        map.put("&frac12;", (char) 189);
        map.put("&frac34;", (char) 190);
        map.put("&iquest;", (char) 191);
        map.put("&times;", (char) 215);
        map.put("&divide;", (char) 247);
        map.put("&Agrave;", (char) 192);
        map.put("&Aacute;", (char) 193);
        map.put("&Acirc;", (char) 194);
        map.put("&Atilde;", (char) 195);
        map.put("&Auml;", (char) 196);
        map.put("&Aring;", (char) 197);
        map.put("&AElig;", (char) 198);
        map.put("&Ccedil;", (char) 199);
        map.put("&Egrave;", (char) 200);
        map.put("&Eacute;", (char) 201);
        map.put("&Ecirc;", (char) 202);
        map.put("&Euml;", (char) 203);
        map.put("&Igrave;", (char) 204);
        map.put("&Iacute;", (char) 205);
        map.put("&Icirc;", (char) 206);
        map.put("&Iuml;", (char) 207);
        map.put("&ETH;", (char) 208);
        map.put("&Ntilde;", (char) 209);
        map.put("&Ograve;", (char) 210);
        map.put("&Oacute;", (char) 211);
        map.put("&Ocirc;", (char) 212);
        map.put("&Otilde;", (char) 213);
        map.put("&Ouml;", (char) 214);
        map.put("&Oslash;", (char) 216);
        map.put("&Ugrave;", (char) 217);
        map.put("&Uacute;", (char) 218);
        map.put("&Ucirc;", (char) 219);
        map.put("&Uuml;", (char) 220);
        map.put("&Yacute;", (char) 221);
        map.put("&THORN;", (char) 222);
        map.put("&szlig;", (char) 223);
        map.put("&agrave;", (char) 224);
        map.put("&aacute;", (char) 225);
        map.put("&acirc;", (char) 226);
        map.put("&atilde;", (char) 227);
        map.put("&auml;", (char) 228);
        map.put("&aring;", (char) 229);
        map.put("&aelig;", (char) 230);
        map.put("&ccedil;", (char) 231);
        map.put("&egrave;", (char) 232);
        map.put("&eacute;", (char) 233);
        map.put("&ecirc;", (char) 234);
        map.put("&euml;", (char) 235);
        map.put("&igrave;", (char) 236);
        map.put("&iacute;", (char) 237);
        map.put("&icirc;", (char) 238);
        map.put("&iuml;", (char) 239);
        map.put("&eth;", (char) 240);
        map.put("&ntilde;", (char) 241);
        map.put("&ograve;", (char) 242);
        map.put("&oacute;", (char) 243);
        map.put("&ocirc;", (char) 244);
        map.put("&otilde;", (char) 245);
        map.put("&ouml;", (char) 246);
        map.put("&oslash;", (char) 248);
        map.put("&ugrave;", (char) 249);
        map.put("&uacute;", (char) 250);
        map.put("&ucirc;", (char) 251);
        map.put("&uuml;", (char) 252);
        map.put("&yacute;", (char) 253);
        map.put("&thorn;", (char) 254);
        map.put("&yuml;", (char) 255);
    }
    
    public static void main(String x[]){
        HTMLEntitiesEscape hh=new HTMLEntitiesEscape();
        String myvalue="<><>&quot;<>>>DSFDSFDSAF&quot;<DSF>DS<FDSFDSA<FDSF> &quot; dfgfd ";
        System.out.println(hh.escapeHTMLEntities(myvalue));
    }
    
    public String escapeHTMLEntities(String convertingString){
     
        Set<String> charsSet=map.keySet();
        Iterator<String> itr=charsSet.iterator();
        while(itr.hasNext()){
            String mychar=itr.next();
            if(convertingString.contains(mychar)){
            	convertingString=convertingString.replace(mychar, map.get(mychar).toString());
            }
        }
        return convertingString;
    }
}
