package com.cultuzz.channel.DAO;

import java.util.List;
import java.util.Map;

import com.cultuzz.channel.XMLpojo.VoucherDetailsRS;

public interface VoucherDetailsDAO {

	public VoucherDetailsRS getVoucherDetails(long ebayItemId, String voucherId);
	public double getVoucherPrice(long ebayItemId, String voucherId);
	public List<Map<String, Object>> getVoucherIds(String voucherIdsQuery);
	public int getTotlaVouchers(String voucherIdsQuery);
	public List<Map<String,Object>> getVoucherOtherdata(String itemid);
	public boolean checkAuctionOffer(String ebayitemid);
	}
