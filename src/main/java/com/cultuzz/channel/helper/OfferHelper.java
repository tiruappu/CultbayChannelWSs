package com.cultuzz.channel.helper;

import com.cultuzz.channel.XMLpojo.ListOfOffersRQ;
import com.cultuzz.channel.XMLpojo.OfferCreationRQ;
import com.cultuzz.channel.XMLpojo.OfferCreationRS;
import com.cultuzz.channel.XMLpojo.OfferDetailsRQ;
import com.cultuzz.channel.XMLpojo.OfferDetailsRS;
import com.cultuzz.channel.XMLpojo.ListOfOffersRS;


public interface OfferHelper {
	
OfferCreationRS processOfferCreationHelper(OfferCreationRQ offerCreationRQ );	
OfferDetailsRS processOfferDetailsHelper(OfferDetailsRQ offerDetailsRQ);
ListOfOffersRS processListOfOffersHelper(ListOfOffersRQ listOfOffersRQ);


}
