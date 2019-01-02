package com.cultuzz.channel.helper.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import com.cultuzz.channel.DAO.OfferDAO;
import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.XMLpojo.ListOfOffersRQ;
import com.cultuzz.channel.XMLpojo.ListOfOffersRS;
import com.cultuzz.channel.XMLpojo.OfferCreationRQ;
import com.cultuzz.channel.XMLpojo.OfferCreationRS;
import com.cultuzz.channel.XMLpojo.OfferDetailsRQ;
import com.cultuzz.channel.XMLpojo.OfferDetailsRS;
import com.cultuzz.channel.XMLpojo.TransactionType;
import com.cultuzz.channel.XMLpojo.TransactionsType;
import com.cultuzz.channel.helper.OfferHelper;
import com.cultuzz.channel.util.CommonUtil;
import com.cultuzz.channel.util.DateUtil;



@Configuration
@Qualifier("offerDetailsHelper")
public class OfferDetailsHelperImpl implements OfferHelper{

	private static final Logger LOGGER = LoggerFactory.getLogger(OfferDetailsHelperImpl.class);
	
     @Autowired	
     private OfferDAO offerDAO;
     
     @Autowired
     private CommonUtil commonutils;
     
     @Autowired
   private GetErrorMessagesDAOImpl getErrorMessagesDAOImpl; 
	
     //OfferDetailsRS offerDetailsRS = null;
     
	public OfferCreationRS processOfferCreationHelper(OfferCreationRQ offerCreationRQ) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * This method used to get the offer details of particular offerId
	 * 
	 * @param offerDetailsRQ
	 * @return offerDetailsRS
	 */
	public OfferDetailsRS processOfferDetailsHelper(OfferDetailsRQ offerDetailsRQ) {
		// TODO Auto-generated method stub
		LOGGER.info("Inside offer details helper");
		OfferDetailsRS offerDetailsRS = null;
		ErrorsType errorsType = new ErrorsType();
		List<ErrorType> errorType= errorsType.getError();
//			OfferDetailsRS offerDetailsRS = null;			
			int langId = 0;
			
			if(offerDetailsRQ !=null){
			try{	
				
				langId = getErrorMessagesDAOImpl.getLanguageId(offerDetailsRQ.getErrorLang());
				
				
				if(offerDetailsRQ.getOfferId() >0 && langId >0){
					
					LOGGER.debug("offer details rq id is:{}",offerDetailsRQ.getOfferId());
					StringBuffer condition=new StringBuffer(" a.id="+offerDetailsRQ.getOfferId());
					
					/*if(offerDetailsRQ.isSetItemId() && !offerDetailsRQ.getItemId().equals(""))
					condition.append(" and a.ebayitemid="+offerDetailsRQ.getItemId());*/
					
					List<OfferDetailsRS> offerDetails=this.getitemdetails(condition.toString());
					if(offerDetails!=null){
						offerDetailsRS=offerDetails.get(0);
						
						if(offerDetailsRS.isSetId() && offerDetailsRS.getId()!=0){
					    	  
					    	  LOGGER.debug("offerId for description is :{}",offerDetailsRS.getId());
					    	  
					    	  String description = offerDAO.getDescription(offerDetailsRS.getId());
					    	  
					    	  LOGGER.debug("description is:{}",description);
					    	  if(description !=null)
					    	  offerDetailsRS.setDescription(description);
					    	  LOGGER.debug("offerBO set in offerManager");
					      }
					}else{
						
						ErrorType error = new ErrorType();
						error.setErrorCode(1106);
						
						error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1106, 0));
						errorType.add(error);
					}
					offerDetailsRS.setTimeStamp(DateUtil.convertDateToString(new Date()));
					 if(errorType.size()>0){
			   			  offerDetailsRS.setErrors(errorsType);
			 				offerDetailsRS.setAck("Failure");
			   		  }else{
			 				offerDetailsRS.setAck("Success");
			   		  }
					
			      /*offerDetailsRS = this.offerManager(offerDetailsRQ.getOfferId(),langId);
			      
			      if(offerDetailsRS.isSetId() && offerDetailsRS.getId()!=0){
			    	  
			    	  LOGGER.debug("offerId for description is :{}",offerDetailsRS.getId());
			    	  
			    	  String description = offerDAO.getDescription(offerDetailsRS.getId());
			    	  
			    	  LOGGER.debug("description is:{}",description);
			    	  if(description !=null)
			    	  offerDetailsRS.setDescription(description);
			    	  LOGGER.debug("offerBO set in offerManager");
			      }*/
				}
				}catch(Exception e){
					
					e.printStackTrace();
				}
			}
		return offerDetailsRS;
	}

	public ListOfOffersRS processListOfOffersHelper(ListOfOffersRQ listOfOffersRQ) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method used to get the offer details of particular offerId
	 * 
	 * @param offerId
	 * @return offerDetailsRS
	 */
	public OfferDetailsRS offerManager(int offerId, int langId){
		OfferDetailsRS offerDetailsRS = null;
		try{
			
			LOGGER.debug("Inside this.offermanager");
			 ErrorsType errorsType = new ErrorsType();
				List<ErrorType> errorType= errorsType.getError();
			LOGGER.debug("offerId in offerManager is:{}",offerId);
		
			
			if(offerId >0 && langId >0){
		
				offerDetailsRS = offerDAO.getOfferDetails(offerId);
				
				offerDetailsRS.setTimeStamp(DateUtil.convertDateToString(new Date()));
		
				LOGGER.debug("offerrrrrrrrrrrr:{}");

				LOGGER.debug("before set the output");
				if (offerDetailsRS!= null){
			  
					if(offerDetailsRS.isSetStatus()){
						
						int status=	offerDetailsRS.getStatus();
						if(status>=1){
						List<Map<String,Object>> transactionList=offerDAO.getTransactionData(offerDetailsRS.getItemId());
						if(transactionList!=null){
							
							List<TransactionType> detailsTransaction=new ArrayList<TransactionType>();
							for(Map<String,Object> transactiondata:transactionList){
								TransactionType transaction=new TransactionType();
								if(transactiondata.get("transdate")!=null){
									
									String transDate=getConvertedTime(transactiondata.get("transdate").toString(),offerDetailsRS.getSiteId());
									transaction.setTransactionDate(transDate);
								}
								if(transactiondata.get("quantity_purchased")!=null){
									transaction.setQuantityPurchased(Integer.parseInt(transactiondata.get("quantity_purchased").toString()));
								}
								if(transactiondata.get("buyer")!=null){
									transaction.setBuyerId(transactiondata.get("buyer").toString());
								}
								if(transactiondata.get("buyeremail")!=null){
									//transaction.set(transactiondata.get("buyeremail").toString());
								}
								if(transactiondata.get("buyeraddress")!=null){
									//transaction.set(transactiondata.get("buyeraddress").toString());
								}
								if(transactiondata.get("endprice")!=null){
									//String.format("%.2f", Double.parseDouble(rs.getString("ebaysofortkauf")))
									//transaction.setTotalPrice(Double.parseDouble(transactiondata.get("endprice").toString()));
									transaction.setTotalPrice(String.format("%.2f", Double.parseDouble(transactiondata.get("endprice").toString())));
								}
								if(transactiondata.get("buyername")!=null){
									transaction.setBuyerName(transactiondata.get("buyername").toString());
								}
								
								detailsTransaction.add(transaction);
							}
							
							TransactionsType tts=new TransactionsType();
							List<TransactionType> tt=tts.getTransaction();
							
							tt.addAll(detailsTransaction);
							//System.out.println("transactions list size"+offerDetailsRS.getTransactions().getTransaction().size());
							offerDetailsRS.setTransactions(tts);
							
						}
						
						}
					}
					

			  LOGGER.debug("offerBO is :{}",offerDetailsRS.toString()); 
			//offerDetailsRS.setTimeStamp(DateUtil.convertDateToString(new Timestamp(date.getTime())));
			
			
			if(offerDetailsRS.isSetObjectId() && offerDetailsRS.getObjectId()>0){
				
				LOGGER.debug("objectId in process is:{}",offerDetailsRS.getObjectId());
				
				//offerDetailsRS.setObjectId(offerBO.getObjectId());
			}else{
				
				LOGGER.error("object Id is :{}",offerDetailsRS.getObjectId());
			}
			if(offerDetailsRS.isSetId() && offerDetailsRS.getId() >0){
				
				LOGGER.debug("id in process is:{}",offerDetailsRS.getId());
				//offerDetailsRS.setId(offerBO.getId());
			}else{
				
				LOGGER.error("siteId is :{}",offerDetailsRS.getSiteId());
				
			}
			if(offerDetailsRS.isSetTemplateId()){
				
				LOGGER.debug("templateId in process is:{}",offerDetailsRS.getTemplateId());
			}else{
				
				LOGGER.error("template id is :{}",offerDetailsRS.getTemplateId());
			}
			if(offerDetailsRS.isSetItemId()){
				
				LOGGER.debug("itemId in process is:{}",offerDetailsRS.getItemId());
			//	offerDetailsRS.setItemId(offerBO.getItemId());
			}
			if(offerDetailsRS.isSetTitle() && offerDetailsRS.getTitle()!=null){
				
				  LOGGER.debug("title in process is:{}",offerDetailsRS.getTitle());
//				offerDetailsRS.setTitle(offerBO.getTitle());
//				LOGGER.debug("title iss:{}",offerDetailsRS.getTitle());
			}else{
				
				LOGGER.error("title is:{}",offerDetailsRS.getTitle());
				
			}
			if(offerDetailsRS.isSetSubTitle() && offerDetailsRS.getSubTitle()!=null){
				
				LOGGER.debug("subtitle in process is:{}",offerDetailsRS.getSubTitle());
//				offerDetailsRS.setSubTitle(offerBO.getSubTitle());
			}
			if(offerDetailsRS.isSetStartTime() && offerDetailsRS.getStartTime()!=null){
				
				LOGGER.debug("start time in process is:{}",offerDetailsRS.getStartTime());
//				offerDetailsRS.setStartTime(offerBO.getStartTime());
			}else{
				
				LOGGER.error("start time is:{}",offerDetailsRS.getStartTime());
			}
			if(offerDetailsRS.isSetEndTime()){
				
				LOGGER.debug("End time in process is:{}",offerDetailsRS.getEndTime());
//				offerDetailsRS.setEndTime(offerDetailsRS.getEndTime());
			}
			if(offerDetailsRS.isSetDuration() && offerDetailsRS.getDuration()>0){
				
				LOGGER.debug("duration in process is:{}",offerDetailsRS.getDuration());
//				offerDetailsRS.setDuration(offerBO.getDuration());
			}else{
				
				LOGGER.error("duration is :{}",offerDetailsRS.getDuration());
			}
			if(offerDetailsRS.isSetQuantity() && offerDetailsRS.getQuantity()>=0){
				
				LOGGER.debug("quantity in process is:{}",offerDetailsRS.getQuantity());
				
				
//				offerDetailsRS.setQuantity(offerBO.getQuantity());
			}else{
				
				LOGGER.error("qunatity is:{}",offerDetailsRS.getQuantity());
			}
			/*if(offerDetailsRS.isSetPrice() && offerDetailsRS.getPrice()!=null && offerDetailsRS.getPrice()>0){
				
				LOGGER.debug("price in process is:{}",offerDetailsRS.getPrice());
//				offerDetailsRS.setPrice(offerBO.getPrice());
			}else{
				
				LOGGER.error("price is:{}",offerDetailsRS.getPrice());
			}*/
			if(offerDetailsRS.isSetSiteId()){
					
					String currency = offerDAO.getCurrency(offerDetailsRS.getSiteId());
					
					LOGGER.debug("currency is :{}",currency);
					
					offerDetailsRS.setCurrency(currency);
				    
				    if(offerDetailsRS.isSetCurrency()){
				    
				    	LOGGER.debug("currency is:{}",offerDetailsRS.getCurrency());
				    	
				    }else{
				
				    	LOGGER.error("siteId is:{}",offerDetailsRS.getCurrency());
				    }
			
			}
	        if(offerDetailsRS.isSetSiteId()){
	        	
	        	LOGGER.debug("siteId in process is:{}",offerDetailsRS.getSiteId());
//	        	offerDetailsRS.setSiteId(offerBO.getSiteId());
	        }else{
	        
	        	LOGGER.error("siteId is:{}",offerDetailsRS.getSiteId());
	        }
			if(offerDetailsRS.getListingType()!=null){
				
			LOGGER.debug("listingType in process is:{}",offerDetailsRS.getListingType());
//				offerDetailsRS.setListingType(offerDetailsRS.getListingType());
			}else{

				LOGGER.error("Listing type is:{}",offerDetailsRS.getListingType());
			}
			
			
			if(offerDetailsRS.isSetItemId() && offerDetailsRS.getItemId()!=null && Long.parseLong(offerDetailsRS.getItemId())>0){
		    	  
                int quantityPurchased = offerDAO.getQuantityPurchased(Long.parseLong(offerDetailsRS.getItemId()));
                
                LOGGER.debug("quantityPurchased is :{}",quantityPurchased);
                
                offerDetailsRS.setQuantitySold(quantityPurchased);
		      }
			if(offerDetailsRS.isSetId() && offerDetailsRS.getId()>0){
		    	  
			    	int noOfViews = offerDAO.getNoOfViews(offerDetailsRS.getId());
			    	
			    	     LOGGER.debug("noOfViews is :{}",noOfViews);
			    	
			    	 offerDetailsRS.setNoOfViews(noOfViews);
			      }
			
			if(offerDetailsRS.isSetStatus()){
				
				LOGGER.debug("status is:{}",offerDetailsRS.getStatus());
			}else{
				
				LOGGER.error("status is:{}",offerDetailsRS.getStatus());
			}
			
			if(offerDetailsRS.isSetHighestBid() && offerDetailsRS.getHighestBid()!=null){
			
				LOGGER.debug("highest bid is :{}",offerDetailsRS.getHighestBid());
//				offerDetailsRS.setHighestBid(offerBO.getHighestBid());
			}
			if(offerDetailsRS.isSetBidderName()){
				
				LOGGER.debug("bidder name is:{}",offerDetailsRS.getBidderName());
//				offerDetailsRS.setBidderName(offerDetailsRS.getBidderName());
			}
			if(offerDetailsRS.isSetNoOfBids()){
				
				LOGGER.debug("noOfBids is:{}",offerDetailsRS.getNoOfBids());
			}
			
			if(offerDetailsRS.isSetWatchCount()){
				
				LOGGER.debug("watch count is:{}",offerDetailsRS.getWatchCount());
			}
			
		   }
				/*if(offerDetailsRS.isSetItemId() && offerDetailsRS.getItemId()!=null && Long.parseLong(offerDetailsRS.getItemId())>0){
				long watcount=offerDAO.getItemWatchCount(Long.parseLong((offerDetailsRS.getItemId())));
				System.out.println("Changes for watchcount"+watcount);
				offerDetailsRS.setWatchCount(String.valueOf(watcount));
				
				}*/
		}else{
		    	
		    		ErrorType error = new ErrorType();
					error.setErrorCode(1106);
					
					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1106, 0));
					errorType.add(error);
		}
			
			
			 if(errorType.size()>0){
	   			  offerDetailsRS.setErrors(errorsType);
	 				offerDetailsRS.setAck("Failure");
	   		  }else{
	 				offerDetailsRS.setAck("Success");
	   		  }
			
			
	}catch(Exception e){
	   e.printStackTrace();
	 
	}
		return offerDetailsRS;
	}
	
	
	public List<OfferDetailsRS> getitemdetails(String condition){
		Map<Integer,String> currencyMap=new HashMap<Integer,String>(); 
		List<OfferDetailsRS> itemsData=offerDAO.getOfferDetailsAll(condition,commonutils);
		
		currencyMap=commonutils.getCurrencyMap();
		
		Map<Integer,String> timezoneMap=commonutils.getTimezoneMap();
		
		/*List<Map<String,Object>> currenciesList=offerDAO.getAllCurrencies();
		//currency.kurz,siteid.id
		if(currenciesList!=null){
			
			for(Map<String,Object> currecyrow:currenciesList ){
				
				currencyMap.put(Integer.parseInt(currecyrow.get("id").toString()), currecyrow.get("kurz").toString());
				
			}
		}*/
		
		for(OfferDetailsRS offer:itemsData){
			
		//String currency = offerDAO.getCurrency(offer.getSiteId());
		//System.out.println("currency"+currency);
		
			if(offer.isSetItemId() && offer.getItemId()!=null && Long.parseLong(offer.getItemId())>0){
		    	  
                int quantityPurchased = offerDAO.getQuantityPurchased(Long.parseLong(offer.getItemId()));
                
                LOGGER.debug("quantityPurchased is :{}",quantityPurchased);
                
                offer.setQuantitySold(quantityPurchased);
		      }
			
			if(offer.isSetProductId() && offer.getProductId()>0){
				int langid=2;
				if(offer.getSiteId()==77)
					langid=1;
				
				String productname=offerDAO.getProductName(offer.getProductId(),langid);
				if(productname!=null)
					offer.setProductName(productname);
			}
			
			if(offer.isSetId() && offer.getId()>0){
		    	  
		    	int noOfViews = offerDAO.getNoOfViews(offer.getId());
		    	
		    	     LOGGER.debug("noOfViews is :{}",noOfViews);
		    	
		    	 offer.setNoOfViews(noOfViews);
		      }
			
		String currency=null;
		if(currencyMap!=null)
			currency=currencyMap.get(offer.getSiteId());
		
		//public List<Map<String,Object>> getAllCurrencies();
		//System.out.println("offer status isss"+offer.getStatus());
		//System.out.println("offer id"+offer.getId()+"offer site id"+offer.getSiteId());
		if(offer.isSetStatus() && offer.getStatus()==0){
			String payeeid=offerDAO.getFuturePayeeAccount(offer.getId(),offer.getSiteId());
			//System.out.println("future payee id"+payeeid);
			if(payeeid==null || payeeid.equals(""))
				offer.setPayeeAccount(currency.toString().toLowerCase()+"@cultuzz.com");	
			else
			offer.setPayeeAccount(payeeid);
		}else{
			String payeeid=offerDAO.getPayeeAccount(offer.getId());
			//System.out.println("payee id"+payeeid);
			if(payeeid==null || payeeid.equals(""))
				offer.setPayeeAccount(currency.toString().toLowerCase()+"@cultuzz.com");	
			else
			offer.setPayeeAccount(payeeid);
		}
		
		if(offer.isSetStatus()){
			
			int status=	offer.getStatus();
			if(status>=1){
			List<Map<String,Object>> transactionList=offerDAO.getTransactionData(offer.getItemId());
			if(transactionList!=null){
				
				List<TransactionType> detailsTransaction=new ArrayList<TransactionType>();
				for(Map<String,Object> transactiondata:transactionList){
					TransactionType transaction=new TransactionType();
					if(transactiondata.get("transdate")!=null){
						try{
						String transDate=getConvertedTime(transactiondata.get("transdate").toString(),offer.getSiteId());
						transaction.setTransactionDate(transDate);
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					if(transactiondata.get("quantity_purchased")!=null){
						transaction.setQuantityPurchased(Integer.parseInt(transactiondata.get("quantity_purchased").toString()));
					}
					if(transactiondata.get("buyer")!=null){
						transaction.setBuyerId(transactiondata.get("buyer").toString());
					}
					if(transactiondata.get("buyeremail")!=null){
						//transaction.set(transactiondata.get("buyeremail").toString());
					}
					if(transactiondata.get("buyeraddress")!=null){
						//transaction.set(transactiondata.get("buyeraddress").toString());
					}
					if(transactiondata.get("endprice")!=null){
						
						//transaction.setTotalPrice(Double.parseDouble(transactiondata.get("endprice").toString()));
						transaction.setTotalPrice(String.format("%.2f", Double.parseDouble(transactiondata.get("endprice").toString())));
					}
					if(currency!=null){
						transaction.setCurrency(currency);
					}
					if(transactiondata.get("buyername")!=null){
						transaction.setBuyerName(transactiondata.get("buyername").toString());
					}
					
					detailsTransaction.add(transaction);
				}
				
				TransactionsType tts=new TransactionsType();
				List<TransactionType> tt=tts.getTransaction();
				
				tt.addAll(detailsTransaction);
				//System.out.println("transactions list size"+offerDetailsRS.getTransactions().getTransaction().size());
				offer.setTransactions(tts);
				
			}
			
			}
		}
		
		
		offer.setCurrency(currency);
		
		}
		return itemsData;
	}
	
	public String getConvertedTime(String time,int siteId){
		String convertedtime=null;
		try{
		SimpleDateFormat timeformatobj = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
	    TimeZone timeZoneObj=null;
	    int siteid=siteId;
	    if(siteid==186 || siteid==146 || siteid==193 ||siteid==101 || siteid==77 || siteid==16 || siteid==23 || siteid==71 || siteid==123){
	    	//timezone.equals("MESZ")
	    	 timeZoneObj = TimeZone.getTimeZone("Europe/Berlin");
	    }else if(siteid==0 || siteid==2){
	    	//PDT
	    	 timeZoneObj = TimeZone.getTimeZone("America/Los_Angeles");
	    }else if(siteid==3){
	    	//BST
	    	 timeZoneObj = TimeZone.getTimeZone("Europe/London");
	    }else if(siteid==15){
	    	//AEDST
	    	 timeZoneObj = TimeZone.getTimeZone("Australia/Brisbane");
	    }else if(siteid==201 || siteid==196 || siteid==207 ||siteid==216 || siteid==211 || siteid==223){
	    	//CCT
	    	 timeZoneObj = TimeZone.getTimeZone("Asia/Manila");
	    }else if(siteid==203){
	    	//IST
	    	 timeZoneObj = TimeZone.getTimeZone("Asia/Kolkata");
	    }
	    timeformatobj.setTimeZone(timeZoneObj);
	    
		
	Date tdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
	Calendar calendar = new GregorianCalendar();
    calendar.setTime(tdate);
    calendar.setTimeZone(timeZoneObj);
    
	//Date tdate=(Date)transactiondata.get("transdate");
	//String timezone=timezoneMap.get(offer.getSiteId());
	/*TimeZone timeZ = TimeZone.getTimeZone(timezone);
	DateFormat transactionDate=new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
	
	transactionDate.setTimeZone(timeZ);*/
	String transDate=timeformatobj.format(tdate);
	convertedtime=transDate;
		//String transDate=new SimpleDateFormat("dd MMM yyyy hh:mm:ss").format(tdate);
	}catch(Exception e){
			e.printStackTrace();
	}
		
		return convertedtime;
	}
	
}
