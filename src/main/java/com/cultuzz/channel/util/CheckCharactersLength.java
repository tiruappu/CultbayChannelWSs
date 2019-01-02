package com.cultuzz.channel.util;

import org.springframework.stereotype.Component;

@Component
public class CheckCharactersLength {
	
	public boolean checkMaxLength(String checkData,int maxsize){	
		boolean maxLengthStatus=false;
		if(checkData.length()>maxsize)
			maxLengthStatus=true;
		
		return maxLengthStatus;
	}
	
public boolean checkMinLength(String checkData,int minsize){
		
		boolean minLengthStatus=false;
		
		if(checkData.length()<minsize)
			minLengthStatus=true;
		
		return minLengthStatus;
	}

}
