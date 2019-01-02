package com.cultuzz.channel.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.DAO.OfferDAO;


@Component
@Scope("singleton")
public class CommonUtil {
	
	@Autowired	
    private OfferDAO offerDAO;

	private Map<Integer,String> currenciesMap=null;
	private Map<Integer,String> timezoneMap=null;
	//List<Map<String,Object>> timezones=offerDAO.getTimeZones();
	

	
	public Map<Integer,String> getCurrencyMap(){
		
		if(currenciesMap==null){
		Map<Integer,String> currencyMap=new HashMap<Integer,String>();
		List<Map<String,Object>> currenciesList=offerDAO.getAllCurrencies();
		if(currenciesList!=null){
			
			for(Map<String,Object> currecyrow:currenciesList ){
				currencyMap.put(Integer.parseInt(currecyrow.get("id").toString()), currecyrow.get("kurz").toString());
				}
			
		}
		this.currenciesMap=currencyMap;
		return currenciesMap;
		}else{
			return this.currenciesMap;
		}
		
	}
	
	public Map<Integer,String> getTimezoneMap(){
		//System.out.println("enter to time zone");
		if(timezoneMap==null){
			Map<Integer,String> timezonesMap=new HashMap<Integer,String>();
			List<Map<String,Object>> timezonessList=offerDAO.getTimeZones();
			if(timezonessList!=null){
				for(Map<String,Object> timezonerow:timezonessList ){
					timezonesMap.put(Integer.parseInt(timezonerow.get("siteid").toString()), timezonerow.get("timezone").toString());
					}
			}
			System.out.println("time zone map"+timezonesMap);
			this.timezoneMap=timezonesMap;
			return this.timezoneMap;
		}else
			return this.timezoneMap;
	}

}
