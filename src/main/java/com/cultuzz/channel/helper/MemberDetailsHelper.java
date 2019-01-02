package com.cultuzz.channel.helper;

import com.cultuzz.channel.XMLpojo.MemberMessageDetailsRQ;
import com.cultuzz.channel.XMLpojo.MemberMessageDetailsRS;

public interface MemberDetailsHelper {

	MemberMessageDetailsRS processMemberMessageDetailsRequest(MemberMessageDetailsRQ memberMessageDetailsRQ);
	MemberMessageDetailsRS validateMemberMessageDetailsRequest(MemberMessageDetailsRQ offerReviseRQ);
	
}
