package com.cultuzz.channel.helper.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import com.cultuzz.channel.DAO.VoucherDetailsDAO;
import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.DAO.impl.TemplateDetailsDAOImpl;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.XMLpojo.ListOfVouchersRQ;
import com.cultuzz.channel.XMLpojo.ListOfVouchersRS;
import com.cultuzz.channel.XMLpojo.ListOfVouchersRS.Vouchers;
import com.cultuzz.channel.XMLpojo.ListOfVouchersRS.Vouchers.Voucher;
import com.cultuzz.channel.XMLpojo.VoucherDetailsRQ;
import com.cultuzz.channel.XMLpojo.VoucherDetailsRS;
import com.cultuzz.channel.XMLpojo.VoucherRedemptionRQ;
import com.cultuzz.channel.XMLpojo.VoucherRedemptionRS;
import com.cultuzz.channel.XMLpojo.VoucherServiceRQ;
import com.cultuzz.channel.XMLpojo.VoucherServiceRS;
import com.cultuzz.channel.helper.VoucherHelper;
import com.cultuzz.channel.util.DateUtil;


@Configuration
@Qualifier("listOfVouchersHelper")
public class ListOfVouchersHelperImpl implements VoucherHelper{

	
	private static final Logger LOGGER = LoggerFactory.getLogger(ListOfVouchersHelperImpl.class);
	
	@Autowired
	@Qualifier("voucherDetailsHelper")
	private VoucherDetailsHelperImpl voucherDetailsHelper;
	
	@Autowired
	private GetErrorMessagesDAOImpl getErrorMessagesDAOImpl;
	
	@Autowired
	private VoucherDetailsDAO voucherDetailsDAOImpl;
	
	@Autowired
	private TemplateDetailsDAOImpl templateDAOImpl;
	
	public VoucherDetailsRS processVoucherDetailsHelper(
			VoucherDetailsRQ voucherDetailsRQ) {
		// TODO Auto-generated method stub
		return null;
	}

	public ListOfVouchersRS processListOfVouchersHelper(
			ListOfVouchersRQ listOfVouchersRQ) {
		// TODO Auto-generated method stub
		
		List<Map<String, Object>> voucherIds = null;
		ListOfVouchersRS listOfVouchersRS = null;
		
		int langId = 0;
		
		LOGGER.info("inside the ListOfVouchersHelper");

		if(listOfVouchersRQ!=null){
			
			try{
				listOfVouchersRS = new ListOfVouchersRS();
				
				ErrorsType errorsType = new ErrorsType();
				List<ErrorType> errorType= errorsType.getError();
				langId = getErrorMessagesDAOImpl.getLanguageId(listOfVouchersRQ.getErrorLang());
				
				listOfVouchersRS.setTimeStamp(DateUtil.convertDateToString(new Date()));
				if(langId >0){
				
					if(listOfVouchersRQ.isSetObjectId() && listOfVouchersRQ.getObjectId()>0){
						
					LOGGER.debug("object Id in list of vouchers helper is:{}",listOfVouchersRQ.getObjectId());
					
					String voucherIdsQuery = this.getQueryString(listOfVouchersRQ);
					
					Vouchers vouchers = new Vouchers();
					
					if(voucherIdsQuery!=null){
						
						LOGGER.debug("voucherIdsQuery in helper is:{}",voucherIdsQuery);
						
						String voucherIdsQueryCount = this.getQueryStringCount(listOfVouchersRQ);
						
						LOGGER.debug("for count query is:{}",voucherIdsQueryCount);
						int countOfVouchers = voucherDetailsDAOImpl.getTotlaVouchers(voucherIdsQueryCount);
						
						LOGGER.debug("count of vouchers is:{}",countOfVouchers);
						
						listOfVouchersRS.setTotalVouchers(countOfVouchers);
						
						voucherIds = voucherDetailsDAOImpl.getVoucherIds(voucherIdsQuery);
						
						  	if(voucherIds !=null && !voucherIds.isEmpty()){
							 
							 LOGGER.debug("offerId list is :{}",voucherIds.toString());
                    		 
                    		 LOGGER.debug("No of Offers :::::{}",voucherIds.size());	
                    		 
                    		 for(Map<String, Object> voucherKeys : voucherIds){
                    			 
                    			 VoucherDetailsRS voucherDetailsRS = null;

        						 Voucher voucher = new Voucher();
        					
        					LOGGER.debug("ebayitemid is:{}",voucherKeys.get("posten"));
                          voucherDetailsRS = voucherDetailsHelper.voucherManager
                        		  (String.valueOf(voucherKeys.get("posten")), String.valueOf(voucherKeys.get("czInternalTransID")));
                    			 
                         
                          
                          LOGGER.debug("voucherDetails rs is :{}",voucherDetailsRS);
                          
                          		if(voucherDetailsRS!=null){
                          			
                          		   voucher.setItemId(String.valueOf(voucherKeys.get("posten")));
                          		   if(voucherKeys.get("czInternalTransID")!=null){
                          			   
                          			   String czid=voucherKeys.get("czInternalTransID").toString();
                          			   boolean auctionStatus=voucherDetailsDAOImpl.checkAuctionOffer(voucherKeys.get("posten").toString());
                          			  if(auctionStatus)
                          				voucher.setVoucherId("");
                          			  else{
                          			   int lsize=czid.length();
                          					   if(lsize<4){
                          						 czid="0"+czid;
                          						 if(czid.length()<4){
                          							czid="0"+czid;
                          							if(czid.length()<4){
                              							czid="0"+czid;
                              							
                              						 }
                          						 }
                          							 
                          					   }
                          		   voucher.setVoucherId(czid);
                          			  }
                          		   }
                          		   if(voucherDetailsRS.getOrderId()!=null){
                          			
                          			 voucher.setOrderId(voucherDetailsRS.getOrderId());
                          			 LOGGER.debug("orderId is:{}",voucher.getOrderId());
                          		   }else{
                          			   
                          			   LOGGER.error("order is null :{}",voucherDetailsRS.getOrderId());
                          		   }
                          		   if(voucherDetailsRS.getStatus()!=0){
                          			   voucher.setStatus(voucherDetailsRS.getStatus());
                          			   LOGGER.debug("status is:{}",voucherDetailsRS.getStatus());
                          		   }else{
                          			   
                          			   LOGGER.error("voucehr status is zero:{}",voucherDetailsRS.getStatus());
                          		   }
                          		   if(voucherDetailsRS.getBuyerName()!=null){
                          			   voucher.setBuyerName(voucherDetailsRS.getBuyerName());
                          			   LOGGER.debug("buyer name is :{}",voucher.getBuyerName());
                          		   }else{
                          			   LOGGER.error("buyer name is null");
                          		   }
                          		   if(voucherDetailsRS.getPaidDate()!=null){
                          			   voucher.setPaymentDate(voucherDetailsRS.getPaidDate());
                          			   LOGGER.debug("paid status is:{}",voucher.getPaymentDate());
                          		   }else{
                          			   LOGGER.error("payment date is null");
                          		   }
                          		   if(voucherDetailsRS.getValidTill()!=null){
                          			   voucher.setValidUntil(voucherDetailsRS.getValidTill());
                          			   LOGGER.debug("valid till is:{}",voucher.getValidUntil());
                          		   }else{
                          			   LOGGER.error("valid till is null");
                          		   }
                          		   
                          		   LOGGER.debug("price isss:{}",voucherDetailsRS.getPrice());
//                          		   LOGGER.debug("price isssss for condition:{}",voucherDetailsRS.getPrice()!=0);
                          		   //if(voucherDetailsRS.getPrice()!=null && voucherDetailsRS.getPrice()>0){
                          			 if(voucherDetailsRS.getPrice()!=null){
                          			   voucher.setPrice(voucherDetailsRS.getPrice());
                          			   LOGGER.debug("price is :{}",voucher.getPrice());
                          		   }else{
                          			   LOGGER.error("price is zero");
                          		   }
                          		   if(voucherDetailsRS.getRedeemedDate()!=null){
                          			   voucher.setRedeemedDate(voucherDetailsRS.getRedeemedDate());
                          			   LOGGER.debug("redeemed date is:{}",voucher.getRedeemedDate());
                          		   }
                          		   if(voucherDetailsRS.getCancelledDate()!=null){
                          			   voucher.setCancelledDate(voucherDetailsRS.getCancelledDate());
                          			   LOGGER.debug("cancelled date is:{}",voucher.getCancelledDate());
                          		   }
                          	       if(voucherDetailsRS.getBuyerId()!=null){
                          	    	   voucher.setBuyerId(voucherDetailsRS.getBuyerId());
                          	    	   LOGGER.debug("buyerId is:{}",voucher.getBuyerId());
                          	       }
                          	       if(voucherDetailsRS.getCheckIn()!=null){
                          	    	 voucher.setCheckIn(voucherDetailsRS.getCheckIn());
                          	    	LOGGER.debug("checkindate is:{}",voucherDetailsRS.getCheckIn());
                          	       }
                          	       
                          	     if(voucherDetailsRS.getCheckOut()!=null){
                          	    	 voucher.setCheckOut(voucherDetailsRS.getCheckOut());
                          	    	LOGGER.debug("checkout is:{}",voucherDetailsRS.getCheckOut());
                          	       }
                          	     if(voucherDetailsRS.getCurrency()!=null){
                          	    	voucher.setCurrency(voucherDetailsRS.getCurrency());
                          	    	LOGGER.debug("currency is:{}",voucherDetailsRS.getCurrency());
                          	     }
                          	   if(voucherDetailsRS.getOfferSiteId()!=null){
                         	    	voucher.setOfferSiteId(voucherDetailsRS.getOfferSiteId());
                         	    	LOGGER.debug("siteid is:{}",voucherDetailsRS.getOfferSiteId());
                         	     }
                          	 
                      	    	voucher.setProductOffer(voucherDetailsRS.getProductOffer());
                      	    	LOGGER.debug("product offer status is:{}",voucherDetailsRS.isSetProductOffer());
                      	     
                          		 vouchers.getVoucher().add(voucher);
                          		}
                          		
                             }
                    		 listOfVouchersRS.setVouchers(vouchers);
                    	 }
					}
						
					}else{
						
						LOGGER.error("object is not greater than zero");
					}
					
				}else{
			    	
			    	ErrorType error = new ErrorType();
						error.setErrorCode(1106);
						
						error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1106, 0));
						errorType.add(error);
			    }
				
				 if(errorType.size()>0){
	       			  listOfVouchersRS.setErrors(errorsType);
	     				listOfVouchersRS.setAck("Failure");
	       		  }else{
	     				listOfVouchersRS.setAck("Success");
	       		  }

				
			}catch(Exception e){
				
				e.printStackTrace();
			}
			
		}
		
		return listOfVouchersRS;
	}

	public VoucherRedemptionRS processVoucherRedeemptionHelper(
			VoucherRedemptionRQ voucherRedeemptionRQ) {
		// TODO Auto-generated method stub
		return null;
	}

	public VoucherServiceRS processsVoucherServiceHelper(
			VoucherServiceRQ voucherServiceRQ) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method is used to get the query string
	 * 
	 * @param listOfOffersRQ
	 * @return listOfOffersQuery
	 */
   public String getQueryString(ListOfVouchersRQ listOfVouchersRQ){
	   
	   
	   
	   String listOfVouchersQuery = null ;
	   
			if(listOfVouchersRQ !=null){
				
				try{
					
				
				 if(listOfVouchersRQ.isSetObjectId() && listOfVouchersRQ.getObjectId()>0){
				 
                          listOfVouchersQuery = " and a.cusebeda_objekt_id = "+listOfVouchersRQ.getObjectId();
                         
				}
				 if(listOfVouchersRQ.isSetItemId() && listOfVouchersRQ.getItemId()!=null){
					 
					 listOfVouchersQuery = " and s.posten = "+listOfVouchersRQ.getItemId();
				 }
				 if(listOfVouchersRQ.isSetStatus() && listOfVouchersRQ.getStatus()>0){
				
					 listOfVouchersQuery += " and g.binaerstatus = "+listOfVouchersRQ.getStatus();
				 }else
					 listOfVouchersQuery += " and g.binaerstatus >1 and g.binaerstatus!=5";
				
				 if(listOfVouchersRQ.isSetPeriod() && listOfVouchersRQ.getPeriod()!=null){
					 
					 if(listOfVouchersRQ.getPeriod().isSetFrom() && listOfVouchersRQ.getPeriod().getFrom()!=null
						&& listOfVouchersRQ.getPeriod().isSetTo() && listOfVouchersRQ.getPeriod().getTo()!=null){
						 
						 listOfVouchersQuery += " and (g.gueltig_bis between '"+listOfVouchersRQ.getPeriod().getFrom()+
								     "' and '"+listOfVouchersRQ.getPeriod().getTo()+"')";
					 }
				 }
				 
				 if(listOfVouchersRQ.isSetBuyerId() && listOfVouchersRQ.getBuyerId()!=null){
					 listOfVouchersQuery +=" and k.ebay_auktion_hoechstbietender='"+listOfVouchersRQ.getBuyerId()+"'";
				 }
				 
				 if(listOfVouchersRQ.isSetRange() && listOfVouchersRQ.getRange()!=null){
					 
					 if(listOfVouchersRQ.getRange().isSetLowerLimit() && !listOfVouchersRQ.getRange().getLowerLimit().isEmpty() 
							 && listOfVouchersRQ.getRange().getLowerLimit()!=null && listOfVouchersRQ.getRange().isSetUpperLimit()
							 && Integer.parseInt(listOfVouchersRQ.getRange().getUpperLimit())>0){
						 
						 listOfVouchersQuery += " order by s.bezahlt_datum desc,s.timestamp desc limit "+listOfVouchersRQ.getRange().getLowerLimit()+","
								 				+(Integer.parseInt(listOfVouchersRQ.getRange().getUpperLimit())-Integer.parseInt(listOfVouchersRQ.getRange().getLowerLimit()));
					 }
				 }
				 
				 
				
				}catch(Exception e){
			   
				 		LOGGER.error("objectId is null :{}",listOfVouchersRQ.getObjectId());
				 		e.printStackTrace();
				 	}
			 
				}

                  return listOfVouchersQuery;																			
   		}

   public String getQueryStringCount(ListOfVouchersRQ listOfVouchersRQ){
	   
	   
	   
	   String listOfVouchersQuery1 = null ;
	   
			if(listOfVouchersRQ !=null){
				
				try{
					
				
				 if(listOfVouchersRQ.isSetObjectId() && listOfVouchersRQ.getObjectId()>0){
				 
                          listOfVouchersQuery1 = " and a.cusebeda_objekt_id = "+listOfVouchersRQ.getObjectId();
                         
				}
				 if(listOfVouchersRQ.isSetItemId() && listOfVouchersRQ.getItemId()!=null){
					 
					 LOGGER.debug("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:{}",listOfVouchersRQ.getItemId());
					 listOfVouchersQuery1 += " and s.posten = "+listOfVouchersRQ.getItemId();
				 }
				 if(listOfVouchersRQ.isSetStatus() && listOfVouchersRQ.getStatus()>0){
				
					 listOfVouchersQuery1 += " and g.binaerstatus = "+listOfVouchersRQ.getStatus();
				 }else
					 listOfVouchersQuery1 += " and g.binaerstatus > 1 and g.binaerstatus!=5";
				
				 if(listOfVouchersRQ.isSetPeriod() && listOfVouchersRQ.getPeriod()!=null){
					 
					 if(listOfVouchersRQ.getPeriod().isSetFrom() && listOfVouchersRQ.getPeriod().getFrom()!=null
						&& listOfVouchersRQ.getPeriod().isSetTo() && listOfVouchersRQ.getPeriod().getTo()!=null){
						 
						 listOfVouchersQuery1 += " and (g.gueltig_bis between '"+listOfVouchersRQ.getPeriod().getFrom()+
								     "' and '"+listOfVouchersRQ.getPeriod().getTo()+"')";
					 }
				 }
				 
				 if(listOfVouchersRQ.isSetBuyerId() && listOfVouchersRQ.getBuyerId()!=null){
					 listOfVouchersQuery1 +=" and k.ebay_auktion_hoechstbietender='"+listOfVouchersRQ.getBuyerId()+"'";
				 }
				 
				}catch(Exception e){
			   
				 		LOGGER.error("objectId is null :{}",listOfVouchersRQ.getObjectId());
				 		e.printStackTrace();
				 	}
			 
				}

                  return listOfVouchersQuery1;																			
   		}

	
}
