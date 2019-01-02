package com.cultuzz.channel.helper;

import org.springframework.stereotype.Component;

import com.cultuzz.channel.XMLpojo.ListOfMemberMessagesRQ;
import com.cultuzz.channel.XMLpojo.ListOfMemberMessagesRS;

/**
 * This class is required to process the Member Messages
 * @author sowmya
 *
 */
@Component
public interface ListOfMemeberMessagesHelper {

	ListOfMemberMessagesRS getMemberMessages(ListOfMemberMessagesRQ listOfMemberMessagesRQ);
	
}
