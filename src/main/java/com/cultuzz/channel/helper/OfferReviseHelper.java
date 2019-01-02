package com.cultuzz.channel.helper;

import com.cultuzz.channel.XMLpojo.OfferReviseRQ;
import com.cultuzz.channel.XMLpojo.OfferReviseRS;


public interface OfferReviseHelper {

	OfferReviseRS processOfferReviseRequest(OfferReviseRQ offerReviseRQ);
	
	OfferReviseRS validateOfferReviseRequest(OfferReviseRQ offerReviseRQ);
	
}
