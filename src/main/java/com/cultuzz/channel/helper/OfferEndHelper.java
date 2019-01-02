package com.cultuzz.channel.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import com.cultuzz.channel.XMLpojo.OfferEndItemRQ;
import com.cultuzz.channel.XMLpojo.OfferEndItemRS;

public interface OfferEndHelper {

	OfferEndItemRS validateOferEndRequest(OfferEndItemRQ endItemRQ,
	OfferEndItemRS endItemRS) throws MalformedURLException, IOException;

}
