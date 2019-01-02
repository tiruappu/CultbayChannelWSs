package com.cultuzz.channel.util;

public class GetCzids {
	public static String getCzId(String czId) {
		String czIdStr = "";
		switch (czId.length()) {
		case 1:
			czIdStr = "000" + czId;
			break;
		case 2:
			czIdStr = "00" + czId;
			break;
		case 3:
			czIdStr = "0" + czId;
			break;
		default:
			czIdStr = czId;
		}
		return czIdStr;
	}
}
