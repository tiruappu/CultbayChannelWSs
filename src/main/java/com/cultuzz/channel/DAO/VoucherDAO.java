package com.cultuzz.channel.DAO;

public interface VoucherDAO {

	public boolean checkEbayItemId(long ebayItemId, int objectId);
	public boolean checkOrderId(long orderId, long ebayItemId,String voucherid);
	public boolean checkVoucherId(long ebayItemId, String voucherId);
	public boolean checkPaidStatus(long ebayItemId, String voucherId);
}
