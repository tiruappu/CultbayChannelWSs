package com.cultuzz.channel.helper;

import com.cultuzz.channel.XMLpojo.ListOfOffersRQ;
import com.cultuzz.channel.XMLpojo.ListOfOffersRS;
import com.cultuzz.channel.XMLpojo.OfferCreationRQ;
import com.cultuzz.channel.XMLpojo.OfferCreationRS;
import com.cultuzz.channel.XMLpojo.OfferDetailsRQ;
import com.cultuzz.channel.XMLpojo.OfferDetailsRS;

public interface OfferValidator {

	OfferCreationRS validateOfferCreation(OfferCreationRQ offerCreationRQ);
	OfferDetailsRS validateOfferDetails(OfferDetailsRQ offerDetailsRQ);
	ListOfOffersRS validateListOfOffers(ListOfOffersRQ listOfOffersRQ);
}
