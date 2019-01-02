package com.cultuzz.channel.helper.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.cultuzz.channel.DAO.ListOfTemplatesDAO;
import com.cultuzz.channel.DAO.TemplateDetailsDAO;
import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.XMLpojo.ListOfTemplatesRQ;
import com.cultuzz.channel.XMLpojo.ListOfTemplatesRS;
import com.cultuzz.channel.XMLpojo.ListOfTemplatesRS.Templates;
import com.cultuzz.channel.XMLpojo.ListOfTemplatesRS.Templates.Template;
import com.cultuzz.channel.helper.ListOfTemplatesHelper;
import com.cultuzz.channel.util.DateUtil;

@Configuration
public class ListOfTemplatesHelperImpl implements ListOfTemplatesHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ListOfOffersHelperImpl.class);
	
	@Autowired
	private GetErrorMessagesDAOImpl getErrorMessagesDAOImpl;
	
	@Autowired
	private ListOfTemplatesDAO listOfTemplatesDAOImpl;
	
	@Autowired
	private TemplateDetailsDAO templateDetails;
	
	
	public ListOfTemplatesRS processListOfTemplates(
			ListOfTemplatesRQ listOfTemplatesRQ) {
		// TODO Auto-generated method stub
		
		List<Integer> templateIds = null;
		
		ListOfTemplatesRS listOfTemplatesRS = null;
		int langId = 0;
		
		LOGGER.info("inside the ListOfTemplatesHelper");
		
		if(listOfTemplatesRQ != null){
			
			try{
				
				listOfTemplatesRS = new ListOfTemplatesRS();
				ErrorsType errorsType = new ErrorsType();
				List<ErrorType> errorType= errorsType.getError();
				langId = getErrorMessagesDAOImpl.getLanguageId(listOfTemplatesRQ.getErrorLang());
				
				listOfTemplatesRS.setTimeStamp(DateUtil.convertDateToString(new Date()));
				if(langId !=0){
					
					
					if(listOfTemplatesRQ.isSetObjectId() && listOfTemplatesRQ.getObjectId()!=0){
						
						LOGGER.debug("object id in helper is :{}",listOfTemplatesRQ.getObjectId());
					    listOfTemplatesRS.setObjectId(listOfTemplatesRQ.getObjectId());
					    
					    String listOfTemplatesQuery = this.getQueryString(listOfTemplatesRQ);
						
					    String listOfTemplateQueryCount = this.getQueryStringCount(listOfTemplatesRQ);
					    
					    LOGGER.debug("count of templates query is:{}",listOfTemplateQueryCount);
					    LOGGER.debug("listOfTemplates query is:{}",listOfTemplatesQuery);
					    
//					    int countOfTemplates = listOfTemplatesDAOImpl.getNoOfTemplates(listOfTemplatesRQ.getObjectId());
					    
					    int countOfTemplates = listOfTemplatesDAOImpl.getNoOfTemplates(listOfTemplateQueryCount);
	 					
	 					LOGGER.debug("countOfTemplates is:{}",countOfTemplates);
	 					
	 					listOfTemplatesRS.setTotalNoOfTemplates(countOfTemplates);
	 						
	 					templateIds = listOfTemplatesDAOImpl.getTemplateIds(listOfTemplatesQuery);	
					    
	 						Templates tempaltes = new Templates();
	 					
	 						if(templateIds!=null && !templateIds.isEmpty()){
	 					    	
	 					    	 LOGGER.debug("templateId list is :{}",templateIds.toString());
                        		 
                        		 LOGGER.debug("No of templates :::::{}",templateIds.size());
                        		 
                        		 for(int templateId : templateIds){
                        			 
                        			 LOGGER.debug("templateId is in listOfTemplates :{}",templateId);
                        			 
                        			 Template template =null; 
                        			 template = listOfTemplatesDAOImpl.getTemplateDetails(templateId);
                        		      
                        			 template.setId(templateId);
                        			 String currency=templateDetails.getCurrency(template.getSiteId());
                        			 template.setCurrency(currency);
                        			 if(listOfTemplatesDAOImpl.getSourceId(template.getId())!=0){
                        				 
                        				 template.setSource(listOfTemplatesDAOImpl.getSourceId(template.getId()));
                        			 }else{
                        				 
                        				 LOGGER.error("source is :{}",listOfTemplatesDAOImpl.getSourceId(template.getId()));
                        			 }

                        			  if(template.isSetDesignTemplate() && template.getDesignTemplate()!=0){
                        				  
                        				  LOGGER.debug("Design template is :{}",template.getDesignTemplate());
                        			  }else{
                        				  
                        				  LOGGER.error("DesignTemplate is null");
                        			  }
                        			  
                        			  if(template.isSetSiteId()){
                        				  
                        				  LOGGER.debug("siteId is{}",template.getSiteId());
                        			  }else{
                        				  
                        				  LOGGER.error("siteId is null");
                        			  }
                        			  
                        			  if(template.isSetStatus()){
                        				  
                        				  LOGGER.debug("status is:{}",template.getStatus());
                        			  }else{
                        				  
                        				  LOGGER.error("status is null");
                        			  }
                        			 
                        			  if(template.isSetTemplateName() && template.getTemplateName()!=null){
                        				  
                        				  LOGGER.debug("template name is:{}",template.getTemplateName());
                        			  }else{
                        				  
                        				  LOGGER.error("template name is null");
                        			  }
                        			  
                        			  template.setOfferCount(listOfTemplatesDAOImpl.getNoOfLiveOffers(templateId));
                        			  
                        			  tempaltes.getTemplate().add(template);
                        		 }
                        		 
                        		 listOfTemplatesRS.setTemplates(tempaltes);
                        		 
	 					    }else{
	 					    	
	 					    	LOGGER.error("templateIds list is empty");
	 					    	
	 					    }
	 						
					}else{
						 
						LOGGER.error("objectId is null");
					}
					
				}else{
			    	
			    	ErrorType error = new ErrorType();
						error.setErrorCode(1106);
						
						error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(1106, 0));
						errorType.add(error);
			    }
				
				 if(errorType.size()>0){
	       			  listOfTemplatesRS.setErrors(errorsType);
	     				listOfTemplatesRS.setAck("Failure");
	       		  }else{
	     				listOfTemplatesRS.setAck("Success");
	       		  }
				
			}catch(Exception e){
				
				e.printStackTrace();
			}
			
			
			
		}
		
		
		return listOfTemplatesRS;
	}

	public String getQueryString(ListOfTemplatesRQ listOfTemplatesRQ){
		   
		   String listOfTemplatesQuery = null ;
		   
				if(listOfTemplatesRQ !=null){
					
					try{
						
					
					 if(listOfTemplatesRQ.isSetObjectId() && listOfTemplatesRQ.getObjectId()!=0){
					 
	                          listOfTemplatesQuery = " cusebeda_objekt_id = "+listOfTemplatesRQ.getObjectId()+" and status=1";
	                         
					}
					if(listOfTemplatesRQ.isSetPeriod() && listOfTemplatesRQ.getPeriod()!=null){
						 
						 if(listOfTemplatesRQ.getPeriod().isSetFrom() && listOfTemplatesRQ.getPeriod().getFrom()!=null
							&& listOfTemplatesRQ.getPeriod().isSetTo() && listOfTemplatesRQ.getPeriod().getTo()!=null){
							 
							 listOfTemplatesQuery += " and (erstellt between '"+listOfTemplatesRQ.getPeriod().getFrom()+
									     "' and '"+listOfTemplatesRQ.getPeriod().getTo()+"')";
						 }
					 }
					 
					 if(listOfTemplatesRQ.isSetRange() && listOfTemplatesRQ.getRange()!=null){
						 
						 if(listOfTemplatesRQ.getRange().isSetLowerLimit() && listOfTemplatesRQ.getRange().getLowerLimit()!=null && listOfTemplatesRQ.getRange().isSetUpperLimit()
								 && Integer.parseInt(listOfTemplatesRQ.getRange().getUpperLimit())!=0){
							 
							 listOfTemplatesQuery += " order by id desc limit "+listOfTemplatesRQ.getRange().getLowerLimit()+","
									 				+(Integer.parseInt(listOfTemplatesRQ.getRange().getUpperLimit())-Integer.parseInt(listOfTemplatesRQ.getRange().getLowerLimit()));
						 }
					 }
					
					}catch(Exception e){
				   
					 		e.printStackTrace();
					 	}
				 
					}

	                  return listOfTemplatesQuery;																			
	   		}

	public String getQueryStringCount(ListOfTemplatesRQ listOfTemplatesRQ){
		   
		   String listOfTemplatesQueryCount = null ;
		   
				if(listOfTemplatesRQ !=null){
					
					try{
						
					
					 if(listOfTemplatesRQ.isSetObjectId() && listOfTemplatesRQ.getObjectId()!=0){
					 
	                          listOfTemplatesQueryCount = " cusebeda_objekt_id = "+listOfTemplatesRQ.getObjectId()+" and status=1";
	                         
					}
					if(listOfTemplatesRQ.isSetPeriod() && listOfTemplatesRQ.getPeriod()!=null){
						 
						 if(listOfTemplatesRQ.getPeriod().isSetFrom() && listOfTemplatesRQ.getPeriod().getFrom()!=null
							&& listOfTemplatesRQ.getPeriod().isSetTo() && listOfTemplatesRQ.getPeriod().getTo()!=null){
							 
							 listOfTemplatesQueryCount += " and (erstellt between '"+listOfTemplatesRQ.getPeriod().getFrom()+
									     "' and '"+listOfTemplatesRQ.getPeriod().getTo()+"')";
						 }
					 }
					 
				}catch(Exception e){
				   
					 		e.printStackTrace();
					 	}
				 
					}

	                  return listOfTemplatesQueryCount;																			
	   		}

	}
