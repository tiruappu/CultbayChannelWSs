package com.cultuzz.channel.helper;

import com.cultuzz.channel.XMLpojo.ListOfVouchersRQ;
import com.cultuzz.channel.XMLpojo.ListOfVouchersRS;
import com.cultuzz.channel.XMLpojo.VoucherDetailsRQ;
import com.cultuzz.channel.XMLpojo.VoucherDetailsRS;
import com.cultuzz.channel.XMLpojo.VoucherRedemptionRQ;
import com.cultuzz.channel.XMLpojo.VoucherRedemptionRS;
import com.cultuzz.channel.XMLpojo.VoucherServiceRQ;
import com.cultuzz.channel.XMLpojo.VoucherServiceRS;

public interface VoucherHelper {

	VoucherDetailsRS processVoucherDetailsHelper(VoucherDetailsRQ voucherDetailsRQ);
	ListOfVouchersRS processListOfVouchersHelper(ListOfVouchersRQ listOfVouchersRQ);
	VoucherRedemptionRS processVoucherRedeemptionHelper(VoucherRedemptionRQ voucherRedeemptionRQ);
	VoucherServiceRS processsVoucherServiceHelper(VoucherServiceRQ voucherServiceRQ);
	
}
