package com.cultuzz.channel.helper;

import com.cultuzz.channel.XMLpojo.ListOfVouchersRQ;
import com.cultuzz.channel.XMLpojo.ListOfVouchersRS;
import com.cultuzz.channel.XMLpojo.VoucherDetailsRQ;
import com.cultuzz.channel.XMLpojo.VoucherDetailsRS;
import com.cultuzz.channel.XMLpojo.VoucherRedemptionRQ;
import com.cultuzz.channel.XMLpojo.VoucherRedemptionRS;
import com.cultuzz.channel.XMLpojo.VoucherServiceRQ;
import com.cultuzz.channel.XMLpojo.VoucherServiceRS;

public interface VoucherValidator {

	VoucherDetailsRS validateVoucherDetails(VoucherDetailsRQ voucherDetailsRQ);
	ListOfVouchersRS validateListOfVouchers(ListOfVouchersRQ listOfVouchersRQ);
	VoucherRedemptionRS validateVoucherRedeemption(VoucherRedemptionRQ voucherRedeemptionRQ);
	VoucherServiceRS validateVoucherService(VoucherServiceRQ voucherServiceRQ);
}
