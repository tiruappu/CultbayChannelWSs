package com.cultuzz.channel.util;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class Base64Converter {
	
	public String converter(String fileName) throws IOException{
		String encodedString = null;
		try {
			// File file = new File(fileName);
			// byte[] bytes = loadFile(file);
			
			byte[] bytes = getBytes(fileName);
			byte[] encoded = Base64.encodeBase64(bytes);
			encodedString = new String(encoded);
			byte[] decoded=Base64.decodeBase64(encodedString);
			String output=decoded.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return encodedString;
	}

	private static byte[] getBytes(String url) throws IOException {
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		URL u = new URL(url);
		URLConnection uc = u.openConnection();
		String contentType = uc.getContentType();
		int contentLength = uc.getContentLength();
		//System.out.println(uc.getContentType());
		if(contentType!=null){
		if (contentType.startsWith("text/") || contentLength == -1) {
			throw new IOException("This is not a binary file.");
		}}
		InputStream raw = uc.getInputStream();
		InputStream in = new BufferedInputStream(raw);
		byte[] data = new byte[contentLength];
		System.out.println(contentLength);
		int bytesRead = 0;
		int offset = 0;
		while (offset < contentLength) {
			bytesRead = in.read(data, offset, data.length - offset);
			if (bytesRead == -1)
				break;
			offset += bytesRead;
		}
		in.close();

		if (offset != contentLength) {
			throw new IOException("Only read " + offset + " bytes; Expected "
					+ contentLength + " bytes");
		}
		return data;
	}
}
