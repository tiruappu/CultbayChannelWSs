package com.cultuzz.channel.helper.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.DAO.impl.OfferIdDAOImpl;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.XMLpojo.ListOfOffersRQ;
import com.cultuzz.channel.XMLpojo.ListOfOffersRS;
import com.cultuzz.channel.XMLpojo.ListOfOffersRS.Offers;
import com.cultuzz.channel.XMLpojo.ListOfOffersRS.Offers.Offer;
import com.cultuzz.channel.XMLpojo.OfferCreationRQ;
import com.cultuzz.channel.XMLpojo.OfferCreationRS;
import com.cultuzz.channel.XMLpojo.OfferDetailsRQ;
import com.cultuzz.channel.XMLpojo.OfferDetailsRS;
import com.cultuzz.channel.XMLpojo.TransactionType;
import com.cultuzz.channel.XMLpojo.TransactionsType;
import com.cultuzz.channel.helper.OfferHelper;
import com.cultuzz.channel.util.DateUtil;



@Component
@Qualifier("listOfOffersHelper")
public class ListOfOffersHelperImpl implements OfferHelper{

	
	@Autowired
	private OfferIdDAOImpl offerIdDAOImpl;
	
	@Autowired
	private OfferDetailsHelperImpl offerDetailsHelper;
	
	@Autowired
	private GetErrorMessagesDAOImpl getErrorMessagesDAOImpl;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ListOfOffersHelperImpl.class);
	
	
	public OfferCreationRS processOfferCreationHelper(
			OfferCreationRQ offerCreationRQ) {
		// TODO Auto-generated method stub
		return null;
	}

	public OfferDetailsRS processOfferDetailsHelper(
			OfferDetailsRQ offerDetailsRQ) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * This method is used to get the listOfOffers related to particlar objectId and templateId
	 * 
	 * @param listOfOffersRQ
	 * @return listOfOffersRS
	 */
	public ListOfOffersRS processListOfOffersHelper1(
			ListOfOffersRQ listOfOffersRQ) {
		// TODO Auto-generated method stub
		
		
		
		List<Integer> offerIds = null;
		
		ListOfOffersRS listOfOffersRS = null;
		int langId = 0;
		
		LOGGER.info("inside the ListOfOffersHelper");
		
		
			if(listOfOffersRQ!= null){
		
			 try{
				listOfOffersRS = new ListOfOffersRS();
				ErrorsType errorsType = new ErrorsType();
				List<ErrorType> errorType= errorsType.getError();
				langId = getErrorMessagesDAOImpl.getLanguageId(listOfOffersRQ.getErrorLang());
				
				listOfOffersRS.setTimeStamp(DateUtil.convertDateToString(new Date()));
				if(langId >0){
				
					if(listOfOffersRQ.isSetObjectId() && listOfOffersRQ.getObjectId()>0){
					
					LOGGER.debug("object id in helper is :{}",listOfOffersRQ.getObjectId());
				    listOfOffersRS.setObjectId(listOfOffersRQ.getObjectId());
				
				  //  if(listOfOffersRQ.isSetTemplateId() && listOfOffersRQ.getTemplateId()>0){
				    
				    	listOfOffersRS.setTemplateId(listOfOffersRQ.getTemplateId());
				    	
				String listOfOffersQuery = this.getQueryString(listOfOffersRQ);
				//String listOfOffersCountQuery = this.getQueryStringCount(listOfOffersRQ);
				
				//LOGGER.debug("listOfOffers for count query is:{}",listOfOffersCountQuery);
				LOGGER.debug("listOfOffers query is:{}",listOfOffersQuery);
				
				if(listOfOffersQuery != null){
					
//					int countOfOffers = offerIdDAOImpl.getNoOfOffers(listOfOffersRQ.getObjectId(),listOfOffersRQ.getTemplateId());
					
					//int countOfOffers = offerIdDAOImpl.getNoOfOffers(listOfOffersCountQuery);
 					
 					//LOGGER.debug("countOfOffers is:{}",countOfOffers);
 					
 					//listOfOffersRS.setTotalOffers(countOfOffers);
 					
					
					offerIds = offerIdDAOImpl.getOfferIds(listOfOffersQuery);
					offerDetailsHelper.getitemdetails(listOfOffersQuery);
					 		Offers offers = new Offers();
                            	
					 		
					   			if(offerIds!=null && !offerIds.isEmpty()){
					   				listOfOffersRS.setTotalOffers(offerIds.size());
					   				
                            		 LOGGER.debug("offerId list is :{}",offerIds.toString());
                            		 
                            		 LOGGER.debug("No of Offers :::::{}",offerIds.size());
                            	 
                            		 for(int offerId : offerIds){
                            			 
                            			 LOGGER.debug("offerId is in listOfOffers :{}",offerId);
//                            			 offerBO = new OfferBO();
                            			 OfferDetailsRS offerDetailsRS = null;
                            			 
                            			 Offer offer = new Offer();
                            			 
                            			 offerDetailsRS = offerDetailsHelper.offerManager(offerId,langId);
     
                            			 LOGGER.debug("*********************************** :{}",offerDetailsRS);
                            			 
                            			 if (offerDetailsRS != null){
                         					
                            				 LOGGER.debug("------------INSIDE NOT EQUAL TO NULL BLOCK----------------");
                            				 
                            				 //offerDetailsRS.setTimeStamp(DateUtil.convertDateToString(new Timestamp(date.getTime())));
                         					offerDetailsRS.setTimeStamp(DateUtil.convertDateToString(new Date()));
                         					
                         					
                         					
                         					if(offerDetailsRS.isSetId() && offerDetailsRS.getId() >0){
                         						
                         						LOGGER.debug("**************(((((((( :{}",offerDetailsRS.getId());
                         						
                        						offer.setId(offerDetailsRS.getId());
                        						
                        							
                        						//LOGGER.debug(":::::::::offer detais List issss::::: {}",offer.getId());
                        						
                        					}else{
                         						
                        						LOGGER.error("Id is null");
                         						
                         					}
                         					
                         					if(offerIdDAOImpl.getSourceId(offer.getId())>0){
                         						
                         						offer.setSource(offerIdDAOImpl.getSourceId(offer.getId()));
                         					}else{
                         						
                         						LOGGER.error("source is:{}",offerIdDAOImpl.getSourceId(offer.getId()));
                         					}
                         					
                         					LOGGER.debug("!!!!!!!!!!!!!:{}",offerDetailsRS.getItemId());
                         					if(offerDetailsRS.isSetItemId()){
                         						
                         						offer.setItemId(offerDetailsRS.getItemId());
                         					}else{
                         						LOGGER.error("Item Id Zero");
                         					}
                         					if(offerDetailsRS.isSetTitle() && offerDetailsRS.getTitle()!=null){
                         						
                         						LOGGER.debug("title before st :{}",offerDetailsRS.getTitle());
                         						
                         						offer.setTitle(offerDetailsRS.getTitle());
                         						
                         						LOGGER.debug("set title :{}",offer.getTitle());
                         					}else{
                         						
                         						LOGGER.error("title is null");
                         						
                         					}
                         					if(offerDetailsRS.isSetSubTitle() && offerDetailsRS.getSubTitle()!=null){
                         						
                         						offer.setSubTitle(offerDetailsRS.getSubTitle());
                         					}
                         					if(offerDetailsRS.isSetStartTime() && offerDetailsRS.getStartTime()!=null){
                         						
                         						offer.setStartTime(offerDetailsRS.getStartTime());
                         					}else{

                         						LOGGER.error("start time is null");
                         					}
                         					if(offerDetailsRS.isSetEndTime()){
                         						
                         						offer.setEndTime(offerDetailsRS.getEndTime());
                         					}
                         					
                         					if(offerDetailsRS.isSetQuantity() && offerDetailsRS.getQuantity()>=0){
                         						
                         						offer.setQuantity(offerDetailsRS.getQuantity());
                         					}else{
                         						
                         						LOGGER.error("quantity is null");
                         					}
                         					if(offerDetailsRS.isSetPrice() && offerDetailsRS.getPrice()!=null ){
                         						
                         						offer.setPrice(offerDetailsRS.getPrice());
                         					}else{

                         						LOGGER.error("price is null");
                         						
                         					}
                         					if(offerDetailsRS.isSetCurrency() && offerDetailsRS.getCurrency()!=null){
                         						
                         						offer.setCurrency(offerDetailsRS.getCurrency());
                         					}else{
                         						
                         						LOGGER.error("currency code is null");
                         					}
                         			        if(offerDetailsRS.isSetSiteId()){
                         			        	
                         			        	offer.setSiteId(offerDetailsRS.getSiteId());
                         			        }else{
                         			        	
                         			        	LOGGER.error("site id is null");
                         			        
                         			        }
                         					if(offerDetailsRS.isSetListingType() && offerDetailsRS.getListingType()!=null){
                         						
                         						offer.setListingType(offerDetailsRS.getListingType());
                         					}else{
                         					
                         					     LOGGER.error("ListingType is:{}",offerDetailsRS.getListingType());
                         					}
                         					
                         					
                         					if(offerDetailsRS.isSetQuantitySold()){
                         						
                         						offer.setQuantitySold(offerDetailsRS.getQuantitySold());
                         					}
                         					if(offerDetailsRS.isSetNoOfViews()){
                         						
                         						offer.setNoOfViews(offerDetailsRS.getNoOfViews());
                         					}
                         					if(offerDetailsRS.isSetHighestBid() && offerDetailsRS.getHighestBid()!=null){
                         						
                         						offer.setHighestBid(offerDetailsRS.getHighestBid());
                         					}
                         					if(offerDetailsRS.isSetBidderName() && offerDetailsRS.getBidderName()!=null){
                         						
                         						offer.setBidderName(offerDetailsRS.getBidderName());
                         					}
                         					
                         					if(offerDetailsRS.isSetStatus()){
                         						
                         						offer.setStatus(offerDetailsRS.getStatus());
                         					}else{
                         						
                         						LOGGER.error("status is in listOfOffers:{}",offerDetailsRS.getStatus());
                         					}
                         					
                         					if(offerDetailsRS.isSetNoOfBids()){
                         						
                         						offer.setNoOfBids(offerDetailsRS.getNoOfBids());
                         					}
                         					if(offerDetailsRS.isSetRetailPrice()){
                         						
                         						offer.setRetailPrice(offerDetailsRS.getRetailPrice());
                         					}
                         					
                         					if(offerDetailsRS.isSetWatchCount()){
                         						offer.setWatchCount(offerDetailsRS.getWatchCount());
                         					}
                         					
                         					
                         					
                         					TransactionsType tt=new TransactionsType();
                         					List<TransactionType> transactionslist=tt.getTransaction();
                         					try{
                         						LOGGER.debug("Transactions data{}",offerDetailsRS.getTransactions().getTransaction());	
                         						
                         					transactionslist.addAll(offerDetailsRS.getTransactions().getTransaction());
                         					}catch(NullPointerException npe){
                         						npe.printStackTrace();
                         					}
                         					offer.setTransactions(tt);
                         					//offer.setTransactions(transactions);
                         					
                         					/*if(offerDetailsRS.isSetItemId()){
                         						long watcount=offerIdDAOImpl.getItemWatchCount(Long.parseLong((offer.getItemId())));
                         						System.out.println("Changes for watchcount"+watcount);
                         						offer.setWatchCount(String.valueOf(watcount));
                         					}
                         					*/
                         				}else{
                         					
                         					LOGGER.debug("---------------Inside else-----------------------------------");
                         					LOGGER.debug("offerdetails response is null");
                             				
                         				}
                         				
                            			 
                            			 offers.getOffer().add(offer);
     							       
//                            			 offer = null;
                            			
                            		 }
                            		 
                            		 listOfOffersRS.setOffers(offers);
                            		 
                            	 }else{
                            		 listOfOffersRS.setTotalOffers(0);
                            		LOGGER.error("list is empty");
                            		 
                            	 }
                            	 
            }
			
				  /*  }else{
				
				LOGGER.error("templateId is null"); 
			}*/
				}else{
					 
					LOGGER.error("object Id is null");
				}
				
				
				
				}else{
			    	
			    	ErrorType error = new ErrorType();
						error.setErrorCode(1106);
						
						error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1106, 0));
						errorType.add(error);
			    }
				
				 if(errorType.size()>0){
	       			  listOfOffersRS.setErrors(errorsType);
	     				listOfOffersRS.setAck("Failure");
	       		  }else{
	     				listOfOffersRS.setAck("Success");
	       		  }
				
				}catch(Exception e){
				//e.initCause(e);
				
				e.getCause();
				LOGGER.debug("Inside Exception");
				
				e.printStackTrace();
				
			}
			 
			}
	
		return listOfOffersRS;

	}

	/**
	 * This method is used to get the query string
	 * 
	 * @param listOfOffersRQ
	 * @return listOfOffersQuery
	 */
   public String getQueryString(ListOfOffersRQ listOfOffersRQ){
	   
	   
	   
	   String listOfOffersQuery = null ;
	   
			if(listOfOffersRQ !=null){
				
				try{
					
				
				 if(listOfOffersRQ.isSetObjectId() && listOfOffersRQ.getObjectId()>0){
				 
                          listOfOffersQuery = " a.cusebeda_objekt_id = "+listOfOffersRQ.getObjectId();
                         
				}
				 if(listOfOffersRQ.isSetTemplateId() && listOfOffersRQ.getTemplateId()>0){
					 
			listOfOffersQuery += " and a.vorlage_id = "+listOfOffersRQ.getTemplateId();
				 }
				 
				 if(listOfOffersRQ.isSetStatus() ){
					 String statusvalue=listOfOffersRQ.getStatus();
					
					 if(statusvalue.equalsIgnoreCase("Running"))
						 listOfOffersQuery += " and a.status =1";
					 else if(statusvalue.equalsIgnoreCase("Past"))
						 listOfOffersQuery += " and a.status >=2";
					 else if(statusvalue.equalsIgnoreCase("Future"))
						 listOfOffersQuery += " and a.status =0";
						
				}
				 
				 if(listOfOffersRQ.isSetPeriod() && listOfOffersRQ.getPeriod()!=null){
					 
					 if(listOfOffersRQ.getPeriod().isSetFrom() && listOfOffersRQ.getPeriod().getFrom()!=null
						&& listOfOffersRQ.getPeriod().isSetTo() && listOfOffersRQ.getPeriod().getTo()!=null){
						 
						 listOfOffersQuery += " and (a.startdatum between '"+listOfOffersRQ.getPeriod().getFrom()+
								     "' and '"+listOfOffersRQ.getPeriod().getTo()+"')";
					 }
				 }
				 
				 if(listOfOffersRQ.isSetRange() && listOfOffersRQ.getRange()!=null){
					 
					 if(listOfOffersRQ.getRange().isSetLowerLimit() && !listOfOffersRQ.getRange().getLowerLimit().isEmpty() && listOfOffersRQ.getRange().getLowerLimit()!=null && listOfOffersRQ.getRange().isSetUpperLimit()
							 && Integer.parseInt(listOfOffersRQ.getRange().getUpperLimit())>0){
						 
						 if(listOfOffersRQ.isSetStatus() ){
							 String statusvalue=listOfOffersRQ.getStatus();
							
							 if(statusvalue.equalsIgnoreCase("Running") || statusvalue.equalsIgnoreCase("Future"))
								 listOfOffersQuery += " order by a.startdatum desc";
							 else if(statusvalue.equalsIgnoreCase("Past"))
								 listOfOffersQuery += " order by enddate desc";
							 
								
						}else
							listOfOffersQuery +=" order by a.id desc";
						 
						 listOfOffersQuery += " limit "+listOfOffersRQ.getRange().getLowerLimit()+","
								 				+(Integer.parseInt(listOfOffersRQ.getRange().getUpperLimit())-Integer.parseInt(listOfOffersRQ.getRange().getLowerLimit()));
					 }
				 }
				
				}catch(Exception e){
			   
				 		LOGGER.error("objectId is null :{}",listOfOffersRQ.getObjectId());
				 		e.printStackTrace();
				 	}
			 
				}

                  return listOfOffersQuery;																			
   		}
   
 
   public String getQueryStringCount(ListOfOffersRQ listOfOffersRQ){
	   
	   
	   
	   String listOfOffersQueryCount = null ;
	   
			if(listOfOffersRQ !=null){
				
				try{
				 if(listOfOffersRQ.isSetObjectId() && listOfOffersRQ.getObjectId()>0){
				 
                          listOfOffersQueryCount = " cusebeda_objekt_id = "+listOfOffersRQ.getObjectId();
                         
				}
				 if(listOfOffersRQ.isSetTemplateId() && listOfOffersRQ.getTemplateId()>0){
					 
			listOfOffersQueryCount += " and vorlage_id = "+listOfOffersRQ.getTemplateId();
				 }
				 
				 if(listOfOffersRQ.isSetStatus() ){
					 String statusvalue=listOfOffersRQ.getStatus();
					
					 if(statusvalue.equalsIgnoreCase("Running"))
						 listOfOffersQueryCount += " and status =1";
					 else if(statusvalue.equalsIgnoreCase("Past"))
						 listOfOffersQueryCount += " and status >=2";
					 else if(statusvalue.equalsIgnoreCase("Future"))
						 listOfOffersQueryCount += " and status =0";
						
				}
				 
				 if(listOfOffersRQ.isSetPeriod() && listOfOffersRQ.getPeriod()!=null){
					 
					 if(listOfOffersRQ.getPeriod().isSetFrom() && listOfOffersRQ.getPeriod().getFrom()!=null
						&& listOfOffersRQ.getPeriod().isSetTo() && listOfOffersRQ.getPeriod().getTo()!=null){
						 
						 listOfOffersQueryCount += " and (startdatum between '"+listOfOffersRQ.getPeriod().getFrom()+
								     "' and '"+listOfOffersRQ.getPeriod().getTo()+"')";
					 }
				 }
				 
				}catch(Exception e){
			   
				 		LOGGER.error("objectId is null :{}",listOfOffersRQ.getObjectId());
				 		e.printStackTrace();
				 	}
			 
				}

                  return listOfOffersQueryCount;																			
   		}
   
   		public ListOfOffersRS processListOfOffersHelper(ListOfOffersRQ listOfOffersRQ){
   			ListOfOffersRS listOfOffersRs=new ListOfOffersRS();
   			ErrorsType errorsType = new ErrorsType();
   			List<ErrorType> errorType= errorsType.getError();
   			String listOfOffersQuery = this.getQueryString(listOfOffersRQ);
   			String listOfOffersCountQuery = this.getQueryStringCount(listOfOffersRQ);
   			int countOfOffers = offerIdDAOImpl.getNoOfOffers(listOfOffersCountQuery);
   			listOfOffersRs.setTotalOffers(countOfOffers);
   			List<OfferDetailsRS> totalOffers=offerDetailsHelper.getitemdetails(listOfOffersQuery);
   			
   			if(totalOffers!=null){
   				Offers offers = new Offers();	
   				List<Offer> offersList=offers.getOffer();
   				LOGGER.info("total offers list{}",totalOffers);
   				for(OfferDetailsRS offerdetailsData:totalOffers){
   					
   					Offer offer = new Offer();
   					if(offerdetailsData.isSetId() && offerdetailsData.getId()!=null)
   						offer.setId(offerdetailsData.getId());
   					
   						if(offerIdDAOImpl.getSourceId(offer.getId())>0){
						offer.setSource(offerIdDAOImpl.getSourceId(offer.getId()));
   						}else{
						LOGGER.error("source is:{}",offerIdDAOImpl.getSourceId(offer.getId()));
   						}
   						
   						if(offerdetailsData.isSetItemId() && offerdetailsData.getItemId()!=null)
   							offer.setItemId(offerdetailsData.getItemId());
   						
   						if(offerdetailsData.isSetTitle() && offerdetailsData.getTitle()!=null)
   							offer.setTitle(offerdetailsData.getTitle());
   						
   						if(offerdetailsData.isSetSubTitle() && offerdetailsData.getSubTitle()!=null)
   							offer.setSubTitle(offerdetailsData.getSubTitle());
   						
   						if(offerdetailsData.isSetStartTime() && offerdetailsData.getStartTime()!=null)
   							offer.setStartTime(offerdetailsData.getStartTime());
   						
   						if(offerdetailsData.isSetEndTime() && offerdetailsData.getEndTime()!=null)
   							offer.setEndTime(offerdetailsData.getEndTime());
   						
   						if(offerdetailsData.isSetQuantity() && offerdetailsData.getQuantity()!=null)
   							offer.setQuantity(offerdetailsData.getQuantity());
   						
   						if(offerdetailsData.isSetPrice() && offerdetailsData.getPrice()!=null)
   							offer.setPrice(offerdetailsData.getPrice());
   						
   						if(offerdetailsData.isSetCurrency() && offerdetailsData.getCurrency()!=null)
   							offer.setCurrency(offerdetailsData.getCurrency());
   						
   						if(offerdetailsData.isSetSiteId() && offerdetailsData.getSiteId()!=null)
   							offer.setSiteId(offerdetailsData.getSiteId());
   						
   						if(offerdetailsData.isSetListingType() && offerdetailsData.getListingType()!=null)
   							offer.setListingType(offerdetailsData.getListingType());
   						
   						if(offerdetailsData.isSetQuantitySold() && offerdetailsData.getQuantitySold()!=null)
   							offer.setQuantitySold(offerdetailsData.getQuantitySold());
   						
   						if(offerdetailsData.isSetNoOfViews() && offerdetailsData.getNoOfViews()!=null)
   							offer.setNoOfViews(offerdetailsData.getNoOfViews());
   						
   						if(offerdetailsData.isSetStatus() && offerdetailsData.getStatus()!=null)
   							offer.setStatus(offerdetailsData.getStatus());
   						
   						if(offerdetailsData.isSetNoOfBids() && offerdetailsData.getNoOfBids()!=null)
   							offer.setNoOfBids(offerdetailsData.getNoOfBids());
   						
   						if(offerdetailsData.isSetRetailPrice() && offerdetailsData.getRetailPrice()!=null)
   							offer.setRetailPrice(offerdetailsData.getRetailPrice());
   						
   						if(offerdetailsData.isSetWatchCount() && offerdetailsData.getWatchCount()!=null)
   							offer.setWatchCount(offerdetailsData.getWatchCount());
   						
   						if(offerdetailsData.isSetDuration() && offerdetailsData.getDuration()!=null){
   							offer.setDuration(offerdetailsData.getDuration());
   						}
   						
   						if(offerdetailsData.isSetProductId() && offerdetailsData.getProductId()!=0){
   							offer.setProductId(offerdetailsData.getProductId());
   							
   							if(offerdetailsData.isSetProductName() && offerdetailsData.getProductName()!=null){
   	   						offer.setProductName(offerdetailsData.getProductName());	
   	   						}
   						}
   						
   						if(offerdetailsData.isSetPayeeAccount() && offerdetailsData.getPayeeAccount()!=null){
   							offer.setPayeeAccount(offerdetailsData.getPayeeAccount());
   						}
   						
   						TransactionsType tt=new TransactionsType();
     					List<TransactionType> transactionslist=tt.getTransaction();
     					try{
     							
     						if(offerdetailsData.getTransactions().getTransaction()!=null)
     							transactionslist.addAll(offerdetailsData.getTransactions().getTransaction());
     					}catch(NullPointerException npe){
     						//npe.printStackTrace();
     						LOGGER.debug("Transactions data not available{}",offerdetailsData.getItemId());
     					}
     					offer.setTransactions(tt);
   					
   					offersList.add(offer);
   				}
   				
   				listOfOffersRs.setObjectId(listOfOffersRQ.getObjectId());
   				listOfOffersRs.setOffers(offers);
   				
   			}else{
   				ErrorType error = new ErrorType();
				error.setErrorCode(1106);
				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1106, 0));
				errorType.add(error);
   			}
   			
   		 if(errorType.size()>0){
   			listOfOffersRs.setErrors(errorsType);
   			listOfOffersRs.setAck("Failure");
  		  }else{
  			listOfOffersRs.setAck("Success");
  		  }
   			listOfOffersRs.setTimeStamp(DateUtil.convertDateToString(new Date()));
   			return listOfOffersRs;
   		}
   }
