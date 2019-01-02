package com.cultuzz.channel.DAO.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.cultuzz.channel.DAO.HotelDataDAO;
import com.cultuzz.channel.XMLpojo.AddressType;
import com.cultuzz.channel.XMLpojo.GetHotelDataRS.BillingAddress;
import com.cultuzz.channel.XMLpojo.GetHotelDataRS.Descriptions;
import com.cultuzz.channel.XMLpojo.GetHotelDataRS.Descriptions.Description;
import com.cultuzz.channel.XMLpojo.SetHotelDataRQ.HotelDetails;
import com.cultuzz.channel.jdbcTemplate.JDBCTemplate;

@Repository
public class HotelDataDAOImpl implements HotelDataDAO{


	@Autowired
	@Qualifier("ebayTemplate")
	private JDBCTemplate ebayJdbcTemplate;
	
	private JdbcTemplate jdbcTemplate;
	
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger("HotelDataDAOImpl.class");
	public boolean saveHotelDetails(long hotelId, HotelDetails hotelDetails) {
		// TODO Auto-generated method stub
		LOGGER.debug("save in main hotel info table"+hotelId);
		
		com.cultuzz.channel.XMLpojo.GetHotelDataRS.HotelDetails ghotelDetails = null;
		
		boolean flag = false;
		
		try{
			if(hotelId  > 0){
		
			int hotelsave = 0;
			
			boolean hotelInfocount = this.getHotelInfoCount(hotelId);
			
			LOGGER.debug("hotelinfo count iss "+hotelInfocount);
			if(hotelInfocount){
				
			ghotelDetails = this.getHotelInfo(hotelId);
			String lastupdated = this.getLastUpdated(hotelId, 1,0);
			boolean arflag = this.saveARHotelDetails(hotelId, ghotelDetails , lastupdated);
			  if(arflag){
			 	
			 	  jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			 	 StringBuffer sql = new StringBuffer("update ebay.HotelInfo set");
			 	if(hotelDetails.isSetName() && !hotelDetails.getName().isEmpty()){
			 		sql.append("Name ='"+hotelDetails.getName()+"',");
			 	}else{
			 		sql.append("Name ='',");
			 	}
			 	if(hotelDetails.isSetPhoneNumber() && !hotelDetails.getPhoneNumber().isEmpty()){
			 		sql.append("ContactNumber ='"+hotelDetails.getPhoneNumber()+"',");
				}else{
					sql.append("ContactNumber ='',");
				}
				if(hotelDetails.isSetEmail() && !hotelDetails.getEmail().isEmpty()){
					sql.append("EmailID ='"+hotelDetails.getEmail()+"',");
				}else{
					sql.append("EmailID ='',");
				}
				if(hotelDetails.isSetAlternateEmail() && !hotelDetails.getAlternateEmail().isEmpty()){
					sql.append("AlternateEmailID ='"+hotelDetails.getAlternateEmail()+"',");
				}else{
					sql.append("AlternateEmailID ='',");
				}
				if(hotelDetails.isSetFaxNumber() && !hotelDetails.getFaxNumber().isEmpty()){
					sql.append("FaxNumber ='"+hotelDetails.getFaxNumber()+"',");
				}else{
					sql.append("FaxNumber ='',");
				}
				if(hotelDetails.isSetLatitude() && !hotelDetails.getLatitude().isEmpty()){
					sql.append("Latitude ='"+Double.parseDouble(hotelDetails.getLatitude())+"',");
				}else{
					sql.append("Latitude ='',");
				}
				if(hotelDetails.isSetLongitude() && !hotelDetails.getLongitude().isEmpty()){
					sql.append("Longitude'"+Double.parseDouble(hotelDetails.getLongitude())+"',");
				}else{
					sql.append("Longitude ='',");
				}
				if(hotelDetails.isSetZoomLevel() && !hotelDetails.getZoomLevel().isEmpty()){
					sql.append("ZoomLevel ='"+hotelDetails.getZoomLevel()+"',");
				}else{
					sql.append("ZoomLevel='',");
				}
				if(hotelDetails.isSetLanguage() && !hotelDetails.getLanguage().isEmpty()){
					sql.append("Language ='"+hotelDetails.getLanguage()+"',");
				}else{
					sql.append("Language ='',");
				}
			    if(hotelDetails.isSetCurrency() && !hotelDetails.getCurrency().isEmpty()){
			    	sql.append("Currency ='"+hotelDetails.getCurrency()+"',");
			    }else{
			    	sql.append("Currency ='',");
			    }
			    if(hotelDetails.isSetBillingStatus() && hotelDetails.isBillingStatus()){
			    	sql.append("BillingStatus = 1,");
			    }else{
			    	sql.append("BillingStatus = 0,");
			    }
			    if(hotelDetails.isSetWebsite() && !hotelDetails.getWebsite().isEmpty()){
			    	sql.append("Website ='"+hotelDetails.getWebsite()+"',");
			    }else{
			    	sql.append("Website ='',");
			    }
				if(hotelDetails.isSetUserName() && !hotelDetails.getUserName().isEmpty()){
					sql.append("UserName ='"+hotelDetails.getUserName()+"',");
				}else{
					sql.append("UserName ='',");
				}
				if(hotelDetails.isSetRegistrationDate() && !hotelDetails.getRegistrationDate().isEmpty()){
					sql.append("RegistrationDate ='"+hotelDetails.getRegistrationDate()+"'");
				}else{
					sql.append("RegistrationDate =''");
				}
			 	 
				   sql.append(" where HotelID = ?");
				   
				 hotelsave =this.jdbcTemplate.update(sql.toString(), new Object[] { hotelId});
			  }
			
		    }else{
				
				namedJdbcTemplate = ebayJdbcTemplate.getNamedParameterJdbcTemplate();
				LOGGER.debug("named jdbc "+namedJdbcTemplate.hashCode());
				
			StringBuffer sql = new StringBuffer(
				"insert into ebay.HotelInfo(HotelID,Name,ContactNumber,EmailID,AlternateEmailID,FaxNumber,Latitude,Longitude,ZoomLevel,Language,Currency,BillingStatus,Website,UserName,RegistrationDate,ActivationDate)");
			sql.append(" values(:HotelID,:Name,:ContactNumber,:EmailID,:AlternateEmailID,:FaxNumber,:Latitude,:Longitude,:ZoomLevel,:Language,:Currency,:BillingStatus,:Website,:UserName,:RegistrationDate,:ActivationDate)");
			

			Map<String, Object> bind = new HashMap<String, Object>();
			bind.put("HotelID", hotelId);
			if(hotelDetails.isSetName() && !hotelDetails.getName().isEmpty()){
			 bind.put("Name", hotelDetails.getName());	
			}else{
				bind.put("Name", "");	
			}
			if(hotelDetails.isSetPhoneNumber() && !hotelDetails.getPhoneNumber().isEmpty()){
				bind.put("ContactNumber", hotelDetails.getPhoneNumber());
			}else{
				bind.put("ContactNumber","");
			}
			if(hotelDetails.isSetEmail() && !hotelDetails.getEmail().isEmpty()){
				bind.put("EmailID", hotelDetails.getEmail());
			}else{
				bind.put("EmailID","");
			}
			if(hotelDetails.isSetAlternateEmail() && !hotelDetails.getAlternateEmail().isEmpty()){
				bind.put("AlternateEmailID",hotelDetails.getAlternateEmail());
			}else{
				bind.put("AlternateEmailID","");
			}
			if(hotelDetails.isSetFaxNumber() && !hotelDetails.getFaxNumber().isEmpty()){
				bind.put("FaxNumber", hotelDetails.getFaxNumber());
			}else{
			  bind.put("FaxNumber", "");
			}
			if(hotelDetails.isSetLatitude() && !hotelDetails.getLatitude().isEmpty()){
				bind.put("Latitude", Double.parseDouble(hotelDetails.getLatitude()));
			}else{
				bind.put("Latitude","");
			}
			if(hotelDetails.isSetLongitude() && !hotelDetails.getLongitude().isEmpty()){
				bind.put("Longitude",Double.parseDouble(hotelDetails.getLongitude()));
			}else{
				bind.put("Longitude", "");
			}
			if(hotelDetails.isSetZoomLevel() && !hotelDetails.getZoomLevel().isEmpty()){
				bind.put("ZoomLevel", hotelDetails.getZoomLevel());
			}else{
				bind.put("ZoomLevel","");
			}
			if(hotelDetails.isSetLanguage() && !hotelDetails.getLanguage().isEmpty()){
				bind.put("Language", hotelDetails.getLanguage());
			}else{
				bind.put("Language", "");
			}
		    if(hotelDetails.isSetCurrency() && !hotelDetails.getCurrency().isEmpty()){
		    	bind.put("Currency",hotelDetails.getCurrency());
		    }else{
		    	bind.put("Currency","");
		    }
		    if(hotelDetails.isSetBillingStatus() && hotelDetails.isBillingStatus()){
		    	bind.put("BillingStatus",1);
		    }else{
		    	bind.put("BillingStatus",0);
		    }
		    if(hotelDetails.isSetWebsite() && !hotelDetails.getWebsite().isEmpty()){
		    	bind.put("Website",hotelDetails.getWebsite());
		    }else{
		    	bind.put("Website","");
		    }
			if(hotelDetails.isSetUserName() && !hotelDetails.getUserName().isEmpty()){
				bind.put("UserName", hotelDetails.getUserName());
			}else{
				bind.put("UserName","");
			}
			if(hotelDetails.isSetRegistrationDate() && !hotelDetails.getRegistrationDate().isEmpty()){
				bind.put("RegistrationDate",hotelDetails.getRegistrationDate());
			}else{
				bind.put("RegistrationDate","");
			}
			if(hotelDetails.isSetActivationDate() && !hotelDetails.getActivationDate().isEmpty()){
				bind.put("ActivationDate", hotelDetails.getActivationDate());
			}else{
				bind.put("ActivationDate","");
			}
			LOGGER.debug("sql in hotel info "+sql.toString());
			
			 SqlParameterSource paramSource = new MapSqlParameterSource(bind);
				KeyHolder keyHolder = new GeneratedKeyHolder();

				LOGGER.debug("named jdbc "+namedJdbcTemplate.hashCode());
				hotelsave = namedJdbcTemplate.update(sql.toString(), paramSource);
			
		    }
			
			if(hotelDetails.isSetAddress() && hotelDetails.getAddress()!=null){
			boolean addressFlag = this.saveAddress(hotelId, hotelDetails.getAddress(),1);
			LOGGER.debug("address saving in hoteldeatils"+addressFlag);
			}			
		if(hotelsave > 0){
			flag =true;  
		 }
		
	      }
		}catch(Exception e){
			LOGGER.debug("inside catch of hotelinfo");
			e.printStackTrace();
		}
		return flag;
	}

	public boolean saveBillingAddress(long hotelId, com.cultuzz.channel.XMLpojo.SetHotelDataRQ.BillingAddress billingAddress) {
		// TODO Auto-generated method stub
		
		LOGGER.debug("inside billing address saving");
		com.cultuzz.channel.XMLpojo.GetHotelDataRS.BillingAddress gbillingdetails  = null;
		boolean flag = false;
		try{
			
			if(hotelId  > 0){
			
				int billingsave = 0;
				
				boolean billingInfocount = this.getBillingInfoCount(hotelId);
				
				if(billingInfocount){
					
					gbillingdetails = this.getBillingDetails(hotelId);
					String lastupdated = this.getLastUpdated(hotelId, 2,0);
					boolean arflag = this.saveArBillingDetails(hotelId,gbillingdetails, lastupdated);
				  if(arflag){
					  jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
					 StringBuffer sql = new StringBuffer("update ebay.HotelInfo set");
					 
					 if(billingAddress.isSetInvoiceRecipient() && !billingAddress.getInvoiceRecipient().isEmpty()){
						 sql.append("InvoiceRecipient ='"+billingAddress.getInvoiceRecipient()+"',");	
						}else{
							sql.append("InvoiceRecipient='',");	
						}
						if(billingAddress.isSetEmail() && !billingAddress.getEmail().isEmpty()){
							sql.append("EmailID ='"+billingAddress.getEmail()+"',");	
						}else{
							sql.append("EmailID ='',");
						}
						if(billingAddress.isSetInvoiceLanguage() && !billingAddress.getInvoiceLanguage().isEmpty()){
							sql.append("Language ='"+billingAddress.getInvoiceLanguage()+"',");
						}else{
							sql.append("Language ='',");
						}
						if(billingAddress.isSetUserName() && !billingAddress.getUserName().isEmpty()){
							sql.append("UserName ='"+billingAddress.getUserName()+"'");
						}else{
							sql.append("UserName =''");
						}
						
						 sql.append(" where HotelID = ?");
						   
						 billingsave =this.jdbcTemplate.update(sql.toString(), new Object[] { hotelId});
				  }
				
				}else{
					namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
					
					StringBuffer sql = new StringBuffer(
							"insert into ebay.BillingDetails(HotelID,InvoiceRecipient,EmailID,UserName,Language)");
					sql.append(" values(:HotelID,:InvoiceRecipient,:EmailID,:UserName,:Language)");
				       
					Map<String, Object> bind = new HashMap<String, Object>();
					bind.put("HotelID", hotelId);
					if(billingAddress.isSetInvoiceRecipient() && !billingAddress.getInvoiceRecipient().isEmpty()){
					 bind.put("InvoiceRecipient", billingAddress.getInvoiceRecipient());	
					}else{
						 bind.put("InvoiceRecipient","");	
					}
					if(billingAddress.isSetEmail() && !billingAddress.getEmail().isEmpty()){
					  bind.put("EmailID",billingAddress.getEmail());	
					}else{
						bind.put("EmailID", "");
					}
					if(billingAddress.isSetInvoiceLanguage() && !billingAddress.getInvoiceLanguage().isEmpty()){
						bind.put("Language",billingAddress.getInvoiceLanguage());
					}else{
						bind.put("Language","");
					}
					if(billingAddress.isSetUserName() && !billingAddress.getUserName().isEmpty()){
						bind.put("UserName", billingAddress.getUserName());
					}else{
						bind.put("UserName","");
					}
					SqlParameterSource paramSource = new MapSqlParameterSource(bind);
					KeyHolder keyHolder = new GeneratedKeyHolder();

					this.namedJdbcTemplate.update(sql.toString(), paramSource,
							keyHolder);
					billingsave = keyHolder.getKey().intValue();
				 
				}
				if(billingAddress.isSetAddress() && billingAddress.getAddress()!=null){
					boolean addressinfoflag = this.saveAddress(hotelId, billingAddress.getAddress(), 2);
					LOGGER.debug("save address flag in billing address"+addressinfoflag);
				}
				
				if(billingsave > 0){
					flag =true;  
				}
			}
				
		}catch(Exception e){
			
		}
		
		return flag;
	}

	public boolean saveLegalDetails(long hotelId,com.cultuzz.channel.XMLpojo.SetHotelDataRQ.LegalAddress legalAddress) {
		// TODO Auto-generated method stub
		LOGGER.debug("inside legal address saving");
		com.cultuzz.channel.XMLpojo.GetHotelDataRS.LegalAddress glegaladdress  = null;
		boolean flag = false;
		
		if(hotelId  > 0){
			
			try{
				int legalsave = 0;
				
				boolean legalInfocount = this.getLegalInfoCount(hotelId);
				
				if(legalInfocount){
					
					glegaladdress = this.getLegalDetails(hotelId);
					String lastupdated = this.getLastUpdated(hotelId, 3,0);
					boolean arflag = this.saveArLegalDetails(hotelId,glegaladdress, lastupdated);
				  if(arflag){
					  jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
						 StringBuffer sql = new StringBuffer("update ebay.HotelInfo set");
						 if(legalAddress.isSetLegalName() && !legalAddress.getLegalName().isEmpty()){
							 sql.append("LegalName ='"+legalAddress.getLegalName()+"',");
							}else{
								sql.append("LegalName ='',");
							}
							if(legalAddress.isSetCompanyName() && !legalAddress.getCompanyName().isEmpty()){
								sql.append("CompanyName ='"+legalAddress.getCompanyName()+"',");
							}else{
								sql.append("CompanyName ='',");
							}
							if(legalAddress.isSetEmail() && !legalAddress.getEmail().isEmpty()){
								sql.append("EmailID ='"+legalAddress.getEmail()+"',");
							}else{
								sql.append("EmailID ='',");
							}
							if(legalAddress.isSetLegalRepresentative() && !legalAddress.getLegalRepresentative().isEmpty()){
								sql.append("LegalRepresentative ='"+legalAddress.getLegalRepresentative()+"',");
							}else{
								sql.append("LegalRepresentative ='',");
							}
							if(legalAddress.isSetVATNumber() && !legalAddress.getVATNumber().isEmpty()){
								sql.append("VATNumber ='"+legalAddress.getVATNumber()+"',");
							}else{
								sql.append("VATNumber ='',");
							}
							if(legalAddress.isSetRegisterNumber() && !legalAddress.getRegisterNumber().isEmpty()){
								sql.append("RegisterNumber ='"+legalAddress.getRegisterNumber()+"',");
							}else{
								sql.append("RegisterNumber ='',");
							}
							if(legalAddress.isSetUserName() && !legalAddress.getUserName().isEmpty()){
								sql.append("UserName ='"+legalAddress.getUserName()+"'");
							} else{
								sql.append("UserName =''");
							}
							
							sql.append(" where HotelID = ?");
							   
							 legalsave =this.jdbcTemplate.update(sql.toString(), new Object[] { hotelId});
				      }
					
				}else{
					namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
					
					StringBuffer sql = new StringBuffer(
							"insert into ebay.Ar_LegalDetails(HotelID,LegalName,CompanyName,EmailID,VATNumber,LegalRepresentative,RegisterNumber,UserName)");
					sql.append(" values(:HotelID,:LegalName,:CompanyName,:EmailID,:VATNumber,:LegalRepresentative,:RegisterNumber,:UserName)");
					
					Map<String, Object> bind = new HashMap<String, Object>();
					bind.put("HotelID", hotelId);
					if(legalAddress.isSetLegalName() && !legalAddress.getLegalName().isEmpty()){
						bind.put("LegalName", legalAddress.getLegalName());
					}else{
						bind.put("LegalName", "");
					}
					if(legalAddress.isSetCompanyName() && !legalAddress.getCompanyName().isEmpty()){
						bind.put("CompanyName", legalAddress.getCompanyName());
					}else{
						bind.put("CompanyName","");
					}
					if(legalAddress.isSetEmail() && !legalAddress.getEmail().isEmpty()){
						bind.put("EmailID", legalAddress.getEmail());
					}else{
						bind.put("EmailID","");
					}
					if(legalAddress.isSetLegalRepresentative() && !legalAddress.getLegalRepresentative().isEmpty()){
						bind.put("LegalRepresentative", legalAddress.getLegalRepresentative());
					}else{
						bind.put("LegalRepresentative", "");
					}
					if(legalAddress.isSetVATNumber() && !legalAddress.getVATNumber().isEmpty()){
						bind.put("VATNumber",legalAddress.getVATNumber());
					}else{
						bind.put("VATNumber","");
					}
					if(legalAddress.isSetRegisterNumber() && !legalAddress.getRegisterNumber().isEmpty()){
						bind.put("RegisterNumber",legalAddress.getRegisterNumber());
					}else{
						bind.put("RegisterNumber","");
					}
					if(legalAddress.isSetUserName() && !legalAddress.getUserName().isEmpty()){
						bind.put("UserName", legalAddress.getUserName());
					}
					  
					   SqlParameterSource paramSource = new MapSqlParameterSource(bind);
						KeyHolder keyHolder = new GeneratedKeyHolder();

						this.namedJdbcTemplate.update(sql.toString(), paramSource,
								keyHolder);
						legalsave = keyHolder.getKey().intValue();
						
				}		
				if(legalAddress.isSetAddress() && legalAddress.getAddress()!=null){
					boolean addressinfoflag = this.saveAddress(hotelId, legalAddress.getAddress(), 3);
					LOGGER.debug("save address flag in billing address"+addressinfoflag);
				}
						if(legalsave > 0){
							flag =true;  
						}
				
			 	
			}catch(Exception e){
				
			}
			
		}	
		return flag;
	}
	
	
	public boolean saveAddress(long hotelId, AddressType address,int type){
		boolean flag = false;
		LOGGER.debug("inside address saving");
		AddressType araddress = null;
		if(hotelId  > 0){
			
			try{
				int addresssave = 0;
				
           boolean addressInfocount = this.getAddressCount(hotelId, type);
				LOGGER.debug("inside address count "+addressInfocount);
				if(addressInfocount){
					
					araddress = this.getAddress(hotelId, type);
					String lastupdated = this.getLastUpdated(hotelId, 5,type);
					boolean arflag = this.saveArAddressInfo(hotelId, araddress, lastupdated,type);
					if(arflag){
						jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
						 StringBuffer sql = new StringBuffer("update ebay.AddressInfo set");
						 if(address.isSetStreet() && !address.getStreet().isEmpty()){
							 sql.append("Street ='"+address.getStreet()+"',");
							}else{
								sql.append("Street ='',");
							}
							if(address.isSetStreetNumber() && !address.getStreetNumber().isEmpty()){
								sql.append("StreetNumber ='"+address.getStreetNumber()+"',");
							}else{
								sql.append("StreetNumber ='',");
							}
							if(address.isSetSecondStreet() && !address.getSecondStreet().isEmpty()){
								sql.append("SecondStreet ='"+address.getSecondStreet()+"',");
							}else{
								sql.append("SecondStreet ='',");
							}
							if(address.isSetCity() && !address.getCity().isEmpty()){
								sql.append("City ='"+address.getCity()+"',");
							}else{
								sql.append("City ='',");
							}
							if(address.isSetRegion() && !address.getRegion().isEmpty()){
								sql.append("Region ='"+address.getRegion()+"',");
							}else{
								sql.append("Region ='',");
							}
							if(address.isSetCountry() && !address.getCountry().isEmpty()){
								sql.append("Country ='"+address.getCountry()+"',");
							}else{
								sql.append("Country ='',");
							}
							if(address.isSetCountryCode() && !address.getCountryCode().isEmpty()){
								sql.append("countryCode ='"+address.getCountryCode()+"',");
							}else{
								sql.append("countryCode ='',");
							}
							if(address.isSetPostalCode() && !address.getPostalCode().isEmpty()){
								sql.append("PostalCode ='"+address.getPostalCode()+"'");
							}else{
								sql.append("PostalCode =''");
							}
							
							sql.append(" where HotelID = ? and Type = ?");
							   
							 addresssave =this.jdbcTemplate.update(sql.toString(), new Object[] { hotelId,type});
					}
				}else{

					namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
					
				StringBuffer sql = new StringBuffer(
						"insert into ebay.AddressInfo(HotelID,Type,Street,StreetNumber,SecondStreet,City,Region,Country,countryCode,PostalCode)");
				sql.append(" values(:HotelID,:Type,:Street,:StreetNumber,:SecondStreet,:City,:Region,:Country,:countryCode,:PostalCode)");
				
				Map<String, Object> bind = new HashMap<String, Object>();
				bind.put("HotelID", hotelId);
				bind.put("Type", type);
				if(address.isSetStreet() && !address.getStreet().isEmpty()){
					bind.put("Street", address.getStreet());
				}else{
					bind.put("Street", "");
				}
				if(address.isSetStreetNumber() && !address.getStreetNumber().isEmpty()){
					bind.put("StreetNumber",address.getStreetNumber());
				}else{
					bind.put("StreetNumber","");
				}
				if(address.isSetSecondStreet() && !address.getSecondStreet().isEmpty()){
					bind.put("SecondStreet",address.getSecondStreet());
				}else{
					bind.put("SecondStreet","");
				}
				if(address.isSetCity() && !address.getCity().isEmpty()){
					bind.put("City", address.getCity());
				}else{
					bind.put("City","");
				}
				if(address.isSetRegion() && !address.getRegion().isEmpty()){
					bind.put("Region",address.getRegion());
				}else{
					bind.put("Region","");
				}
				if(address.isSetCountry() && !address.getCountry().isEmpty()){
					bind.put("Country", address.getCountry());
				}else{
					bind.put("Country","");
				}
				LOGGER.debug("countrtcode is "+address.getCountryCode());
				if(address.isSetCountryCode() && !address.getCountryCode().isEmpty()){
					
					bind.put("countryCode", address.getCountryCode());
				}else{
					bind.put("countryCode", "");
				}
				if(address.isSetPostalCode() && !address.getPostalCode().isEmpty()){
					bind.put("PostalCode",address.getPostalCode());
				}else{
					bind.put("PostalCode","");
				}
				
				LOGGER.debug("sql for address saving is "+sql.toString());
				  
				   SqlParameterSource paramSource = new MapSqlParameterSource(bind);
					KeyHolder keyHolder = new GeneratedKeyHolder();

					addresssave = namedJdbcTemplate.update(sql.toString(), paramSource);
					
				}
		
				if(addresssave > 0){
					flag = true;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}	
		return flag;
	}
	
	public boolean saveDescription(long hotelId, com.cultuzz.channel.XMLpojo.SetHotelDataRQ.Descriptions.Description description){
		
		boolean flag = false;
		LOGGER.debug("inside save description");
		int descriptionsave =0 ;
		com.cultuzz.channel.XMLpojo.GetHotelDataRS.Descriptions.Description ardescription = null;
		
		try{
			
			if(hotelId > 0){
			
				if(description.isSetLanguage() && !description.getLanguage().isEmpty()){
					
					boolean descriptioncount = this.getDescriptionCount(hotelId, description.getLanguage());
					
					if(descriptioncount){
					ardescription = this.getLangDescription(hotelId,description.getLanguage());
					String lastupdated = this.getLastUpdated(hotelId, 4,0);
					boolean arflag = this.saveArDescription(hotelId, ardescription, lastupdated);
					if(arflag){
						
						jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
						StringBuffer sql = new StringBuffer("update ebaydesc set");
						if(description.isSetLanguage() && !description.getLanguage().isEmpty()){
							sql.append("Language ='"+description.getLanguage()+"',");
						}else{
							sql.append("Language ='',");
						}
						if(description.isSetText() && !description.getText().isEmpty()){
							sql.append("Description ='"+description.getText()+"',");
						}else{
							sql.append("Description ='',");
						}
						if(description.isSetUserName() && !description.getUserName().isEmpty()){
							sql.append("UserName ='"+description.getUserName()+"'");
						}else{
							sql.append("UserName =''");
						}
						sql.append(" where HotelID = ? and Language = ?");
						   
						descriptionsave =this.jdbcTemplate.update(sql.toString(), new Object[] { hotelId,description.getLanguage()});
					}
				 }else{
					 namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
						
						StringBuffer sql = new StringBuffer(
								"insert into ebay.Ar_HotelDescription(HotelID,Description,Language,UserName,LastUpdated)");

						sql.append(" values(:HotelID,:Description,:Language,:UserName,:LastUpdated)");
						Map<String, Object> bind = new HashMap<String, Object>();
						bind.put("HotelID", hotelId);
						if(description.isSetLanguage() && !description.getLanguage().isEmpty()){
							bind.put("Language",description.getLanguage());
						}else{
							bind.put("Language","");
						}
						if(description.isSetText() && !description.getText().isEmpty()){
							bind.put("Description", description.getText());
						}else{
							bind.put("Description", "");
						}
						if(description.isSetUserName() && !description.getUserName().isEmpty()){
							bind.put("UserName", description.getUserName());
						}else{
							bind.put("UserName", "");
						}
						
						
						SqlParameterSource paramSource = new MapSqlParameterSource(bind);
						KeyHolder keyHolder = new GeneratedKeyHolder();

						this.namedJdbcTemplate.update(sql.toString(), paramSource,
								keyHolder);
						descriptionsave = keyHolder.getKey().intValue();
				 }
				}	
				
				if(descriptionsave >0 ){
					flag = true;
				}
			} 
		}catch(Exception e){
			
		}
		
		return false;
	}

	public boolean getHotelInfoCount(long objectId){
		
		boolean flag = false;
		int count = 0;
		
		try{
			LOGGER.debug("inside get HotelInfo count");
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			
			String hotelInfocountsql = "select count(*) from ebay.HotelInfo where HotelID ="+objectId;
		       count = jdbcTemplate.queryForInt(hotelInfocountsql);
		       
		       if(count > 0){
		    	   flag = true;
		       }
			LOGGER.debug("hotel count iss "+count);
		}catch(NullPointerException ne){
		    ne.printStackTrace();
		}
		
		return flag;
	}
	
	public boolean getBillingInfoCount(long objectId){
		
		boolean flag = false;
		int count = 0;
		
		try{
			LOGGER.debug("inside get BillingInfo count");
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			
			String hotelInfocountsql = "select count(*) from ebay.BillingDetails where HotelID ="+objectId;
		       count = jdbcTemplate.queryForInt(hotelInfocountsql);
		       
		       if(count > 0){
		    	   flag = true;
		       }
			
		}catch(NullPointerException ne){
		    ne.printStackTrace();
		}
		
		return flag;
	}

	public boolean getLegalInfoCount(long objectId){
	
	boolean flag = false;
	int count = 0;
	
	try{
		LOGGER.debug("inside get LegalInfo count");
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		
		String hotelInfocountsql = "select count(*) from ebay.HotelInfo where HotelID ="+objectId;
	       count = jdbcTemplate.queryForInt(hotelInfocountsql);
	       
	       if(count > 0){
	    	   flag = true;
	       }
		
	}catch(NullPointerException ne){
	    ne.printStackTrace();
	}
	
	return flag;
	}
	
	public boolean getAddressCount(long objectId, int type){
		
		boolean flag = false;
		int count = 0;
		
		try{
			LOGGER.debug("inside get address count");
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			
			String addresscountsql = "select count(*) from ebay.AddressInfo where HotelID = ? and Type = ?";
		       count = jdbcTemplate.queryForInt(addresscountsql,new Object[]{objectId,type});
		       
		       if(count > 0){
		    	   flag = true;
		       }
			
		}catch(NullPointerException ne){
		    ne.printStackTrace();
		}
		
		return flag;
	}
	
public boolean getDescriptionCount(long hotelId, String language){
		
		boolean flag = false;
		int count = 0;
		
		try{
			LOGGER.debug("inside get Description count");
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			
			String hotelInfocountsql = "select count(*) from ebay.HotelDescription where HotelID =? and Language =?";
		       count = jdbcTemplate.queryForInt(hotelInfocountsql,new Object[]{hotelId,language});
		       
		       if(count > 0){
		    	   flag = true;
		       }
			
		}catch(NullPointerException ne){
		    ne.printStackTrace();
		}
		
		return flag;
	}
	public com.cultuzz.channel.XMLpojo.GetHotelDataRS.HotelDetails getHotelInfo(long hotelId){
		
		com.cultuzz.channel.XMLpojo.GetHotelDataRS.HotelDetails gHotelDetails = null;
		
		List<Map<String,Object>> hotelinfo = null;
		AddressType hotelAddress =null;
		try{
			
			gHotelDetails = new com.cultuzz.channel.XMLpojo.GetHotelDataRS.HotelDetails();
			
			LOGGER.debug("get HotelInfo DAO Impl");
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			
			String getHotelInfosql = "select * from ebay.HotelInfo where HotelID = ?";
			 hotelinfo = jdbcTemplate.queryForList(getHotelInfosql, new Object[]{hotelId});
			
			 if(hotelinfo!=null){
				 
				 for(Map<String,Object> hotelinf : hotelinfo){
					 
					 if(hotelinf.get("Name")!=null && !hotelinf.get("Name").toString().isEmpty()){
						gHotelDetails.setName(hotelinf.get("Name").toString()); 
					 }
					 
					 if(hotelinf.get("ContactNumber")!=null && !hotelinf.get("ContactNumber").toString().isEmpty()){
						 gHotelDetails.setPhoneNumber(hotelinf.get("ContactNumber").toString());
					 }
					 
					 if(hotelinf.get("AlternateEmailID")!=null && !hotelinf.get("AlternateEmailID").toString().isEmpty()){
						 gHotelDetails.setAlternateEmail(hotelinf.get("AlternateEmailID").toString());
					 }
					 
					 if(hotelinf.get("FaxNumber")!=null && !hotelinf.get("FaxNumber").toString().isEmpty()){
						 gHotelDetails.setFaxNumber(hotelinf.get("FaxNumber").toString());
					 }
					 if(hotelinf.get("Latitude")!=null && !hotelinf.get("Latitude").toString().isEmpty()){
						 gHotelDetails.setLatitude(hotelinf.get("Latitude").toString());
					 }
					 if(hotelinf.get("Longitude")!=null && !hotelinf.get("Longitude").toString().isEmpty()){
						 gHotelDetails.setLongitude(hotelinf.get("Longitude").toString());
					 }
					 if(hotelinf.get("ZoomLevel")!=null && !hotelinf.get("ZoomLevel").toString().isEmpty()){
						 gHotelDetails.setZoomLevel(hotelinf.get("ZoomLevel").toString());
					 }
					 if(hotelinf.get("Language")!=null && !hotelinf.get("Language").toString().isEmpty()){
						 gHotelDetails.setLanguage(hotelinf.get("Language").toString());
					 }
					 if(hotelinf.get("Currency")!=null && !hotelinf.get("Currency").toString().isEmpty()){
						 gHotelDetails.setLanguage(hotelinf.get("Currency").toString());
					 }
					 if(hotelinf.get("BillingStatus")!=null && !hotelinf.get("BillingStatus").toString().isEmpty()){
						 if(Integer.parseInt(hotelinf.get("BillingStatus").toString()) == 1){
							 gHotelDetails.setBillingStatus(true);	 
						 }else{
							 gHotelDetails.setBillingStatus(false);
						 }
					}
					if(hotelinf.get("Website")!=null && !hotelinf.get("Website").toString().isEmpty()){
						gHotelDetails.setWebsite(hotelinf.get("Website").toString());
					}
					if(hotelinf.get("UserName")!=null && !hotelinf.get("UserName").toString().isEmpty()){
						gHotelDetails.setUserName(hotelinf.get("UserName").toString());
					}
					if(hotelinf.get("RegistrationDate")!=null && !hotelinf.get("RegistrationDate").toString().isEmpty()){
						gHotelDetails.setRegistrationDate(hotelinf.get("RegistrationDate").toString());
					}
					if(hotelinf.get("ActivationDate")!=null && !hotelinf.get("ActivationDate").toString().isEmpty()){
						gHotelDetails.setActivationDate(hotelinf.get("ActivationDate").toString());
					}
					if(hotelinf.get("EmailID")!=null && !hotelinf.get("EmailID").toString().isEmpty()){
						gHotelDetails.setEmail(hotelinf.get("EmailID").toString());
					}
					hotelAddress = this.getAddress(hotelId, 1);
					if(hotelAddress!=null){
						gHotelDetails.setAddress(hotelAddress);
					}
				 }
				 
			 }
			
			
		}catch(Exception e){
			
		}
		
		return gHotelDetails;
	}
	
	public AddressType getAddress(long HotelId, int type){
		
		AddressType address = null;
		List<Map<String,Object>> addressinfo = null;
		
		try{
			address = new AddressType();
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			
			String addressSql = "select * from ebay.AddressInfo where HotelID = ? and Type = ?";
			addressinfo = jdbcTemplate.queryForList(addressSql, new Object[]{HotelId,type});
			
			if(addressinfo!=null){
				
				for(Map<String,Object> addressinf : addressinfo){
					
					if(addressinf.get("Street")!=null && !addressinf.get("Street").toString().isEmpty()){
						address.setStreet(addressinf.get("Street").toString());
					}
					if(addressinf.get("StreetNumber")!=null && !addressinf.get("StreetNumber").toString().isEmpty()){
						address.setStreetNumber(addressinf.get("StreetNumber").toString());
					}
					if(addressinf.get("SecondStreet")!=null && !addressinf.get("SecondStreet").toString().isEmpty()){
						address.setStreetNumber(addressinf.get("SecondStreet").toString());
					}
					if(addressinf.get("City")!=null && !addressinf.get("City").toString().isEmpty()){
						address.setCity(addressinf.get("City").toString());
					}
					if(addressinf.get("Region")!=null && addressinf.get("Region").toString().isEmpty()){
						address.setRegion(addressinf.get("Region").toString());
					}
					if(addressinf.get("Country")!=null && addressinf.get("Country").toString().isEmpty()){
						address.setCountry(addressinf.get("Country").toString());
					}
					if(addressinf.get("countryCode")!=null && addressinf.get("countryCode").toString().isEmpty()){
						address.setCountryCode(addressinf.get("countryCode").toString());
					}
					if(addressinf.get("PostalCode")!=null && addressinf.get("PostalCode").toString().isEmpty()){
						address.setPostalCode(addressinf.get("PostalCode").toString());
					}
					
				}
			}
			
		}catch(Exception e){
			
		}
		
		return address;
	}
	
	public com.cultuzz.channel.XMLpojo.GetHotelDataRS.BillingAddress getBillingDetails(long hotelId){
		
		com.cultuzz.channel.XMLpojo.GetHotelDataRS.BillingAddress gbillingDetails = null;
		List<Map<String,Object>> billinginfo = null;
		AddressType billingAddress = null;
		try{
			gbillingDetails = new com.cultuzz.channel.XMLpojo.GetHotelDataRS.BillingAddress();
			LOGGER.debug("get BillingInfo DAO Impl");
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			
			String getBillingInfosql = "select * from ebay.BillingDetails where HotelID = ?";
			 billinginfo = jdbcTemplate.queryForList(getBillingInfosql, new Object[]{hotelId});
			 if(billinginfo!=null){
		
				 for(Map<String,Object> billinginf : billinginfo){
					 
					 if(billinginf.get("InvoiceRecipient")!=null && !billinginf.get("InvoiceRecipient").toString().isEmpty()){
						 gbillingDetails.setInvoiceRecipient(billinginf.get("InvoiceRecipient").toString());
					 }
					 if(billinginf.get("Language")!=null && !billinginf.get("Language").toString().isEmpty()){
						gbillingDetails.setInvoiceLanguage(billinginf.get("Language").toString()); 
					 }
					 if(billinginf.get("EmailID")!=null && billinginf.get("EmailID").toString().isEmpty()){
						 gbillingDetails.setEmail(billinginf.get("EmailID").toString());
					 }
					 if(billinginf.get("UserName")!=null && billinginf.get("UserName").toString().isEmpty()){
						 gbillingDetails.setUserName(billinginf.get("UserName").toString());
					 }
					 billingAddress = this.getAddress(hotelId, 2);
					 if(billingAddress!=null){
						 gbillingDetails.setAddress(billingAddress);
					 }
				 }
			 }
			
		}catch(Exception e){
			
		}
		return gbillingDetails;
	}
	
	public com.cultuzz.channel.XMLpojo.GetHotelDataRS.LegalAddress getLegalDetails(long hotelId){
		
		com.cultuzz.channel.XMLpojo.GetHotelDataRS.LegalAddress glegalDeatils = null;
		List<Map<String,Object>> legalinfo = null;
		AddressType legaladdress = null;
		try{
			
			glegalDeatils  = new com.cultuzz.channel.XMLpojo.GetHotelDataRS.LegalAddress();
			LOGGER.debug("get LegalInfo DAO Impl");
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			
			String getLegalInfosql = "select * from ebay.LegalDetails where HotelID = ?";
			 legalinfo = jdbcTemplate.queryForList(getLegalInfosql, new Object[]{hotelId});
			 if(legalinfo!=null){
				 
				 for(Map<String,Object> legalinf : legalinfo){
					if(legalinf.get("LegalName")!=null && !legalinf.get("LegalName").toString().isEmpty()){
						glegalDeatils.setLegalName(legalinf.get("LegalName").toString());
					}
					if(legalinf.get("CompanyName")!=null && !legalinf.get("CompanyName").toString().isEmpty()){
						glegalDeatils.setCompanyName(legalinf.get("CompanyName").toString());
					}
					if(legalinf.get("EmailID")!=null && !legalinf.get("EmailID").toString().isEmpty()){
						glegalDeatils.setEmail(legalinf.get("EmailID").toString());
					}
					if(legalinf.get("VATNumber")!=null && !legalinf.get("VATNumber").toString().isEmpty()){
						glegalDeatils.setVATNumber(legalinf.get("VATNumber").toString());
					}
					if(legalinf.get("LegalRepresentative")!=null && !legalinf.get("LegalRepresentative").toString().isEmpty()){
						glegalDeatils.setLegalRepresentative(legalinf.get("LegalRepresentative").toString());
					}
					if(legalinf.get("RegisterNumber")!=null && !legalinf.get("RegisterNumber").toString().isEmpty()){
						glegalDeatils.setRegisterNumber(legalinf.get("RegisterNumber").toString());
					}
					if(legalinf.get("UserName")!=null && !legalinf.get("UserName").toString().isEmpty()){
						glegalDeatils.setUserName(legalinf.get("UserName").toString());
					}
					legaladdress = this.getAddress(hotelId, 3);
					if(legaladdress!=null){
						glegalDeatils.setAddress(legaladdress);
					}
				 }
			}
		}catch(Exception e){
			
		}
		
		return glegalDeatils;		
	}
	
	public com.cultuzz.channel.XMLpojo.GetHotelDataRS.Descriptions getDescriptions(long hotelId){
		
		com.cultuzz.channel.XMLpojo.GetHotelDataRS.Descriptions gdescriptions = null;
		List<Map<String,Object>> descriptions = null;
		List<Descriptions.Description> descriptionlist  = null;
		try{
			gdescriptions  = new com.cultuzz.channel.XMLpojo.GetHotelDataRS.Descriptions();
			LOGGER.debug("get descriptions DAO Impl");
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			
			String getDescriptionInfosql = "select * from ebay.HotelDescription where HotelID = ?";
			descriptions = jdbcTemplate.queryForList(getDescriptionInfosql, new Object[]{hotelId});
			
			if(descriptions!=null){
				descriptionlist = gdescriptions.getDescription();
				
				for(Map<String,Object> desc : descriptions){
					
					Description description  = new Description();
					
					if(desc.get("Description")!=null && !desc.get("Description").toString().isEmpty()){
						description.setText(desc.get("Description").toString());
					}
					if(desc.get("Language")!=null && !desc.get("Language").toString().isEmpty()){
						description.setLanguage(desc.get("Language").toString());
					}
					if(desc.get("UserName")!=null && !desc.get("UserName").toString().isEmpty()){
						description.setUserName(desc.get("UserName").toString());
					}
					descriptionlist.add(description);
				}
			}
		
		}catch(Exception e){
			
		}
		
		return gdescriptions;
	}
	
 public com.cultuzz.channel.XMLpojo.GetHotelDataRS.Descriptions.Description getLangDescription(long hotelId, String language){
		
		com.cultuzz.channel.XMLpojo.GetHotelDataRS.Descriptions.Description gdescription = null;
		List<Map<String,Object>> descriptions =null;
		try{
			gdescription  = new com.cultuzz.channel.XMLpojo.GetHotelDataRS.Descriptions.Description();
			LOGGER.debug("get description lang DAO Impl");
			jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
			
			String getDescriptionInfosql = "select * from ebay.HotelDescription where HotelID = ? and Language =?";
			descriptions = jdbcTemplate.queryForList(getDescriptionInfosql, new Object[]{hotelId});
			
			if(descriptions!=null){
				
				for(Map<String,Object> desc : descriptions){
					
					if(desc.get("Description")!=null && !desc.get("Description").toString().isEmpty()){
						gdescription.setText(desc.get("Description").toString());
					}
					if(desc.get("Language")!=null && !desc.get("Language").toString().isEmpty()){
						gdescription.setLanguage(desc.get("Language").toString());
					}
					if(desc.get("UserName")!=null && !desc.get("UserName").toString().isEmpty()){
						gdescription.setUserName(desc.get("UserName").toString());
					}
					
				}
			}
		
		}catch(Exception e){
			
		}
		
		return gdescription;
	}
	
	public boolean saveARHotelDetails(long hotelId, com.cultuzz.channel.XMLpojo.GetHotelDataRS.HotelDetails hotelDetails,String lastupdated) {
		// TODO Auto-generated method stub
		boolean flag = false;
		int hotelsave = 0;
		
		LOGGER.debug("Hotel info archeving");
		
		if(hotelDetails!=null){
			
			namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
			
		StringBuffer sql = new StringBuffer(
			"insert into ebay.Ar_HotelInfo(HotelID,Name,ContactNumber,EmailID,AlternateEmailID,FaxNumber,Latitude,Longitude,ZoomLevel,Language,Currency,BillingStatus,Website,UserName,RegistrationDate,lastUpdated)");
		sql.append(" values(:HotelID,:Name,:ContactNumber,:EmailID,:AlternateEmailID,:FaxNumber,:Latitude,:Longitude,:ZoomLevel,:Language,:Currency,:BillingStatus,:Website,:UserName,:RegistrationDate,:lastUpdated)");
		

		Map<String, Object> bind = new HashMap<String, Object>();
		bind.put("HotelID", hotelId);
		if(hotelDetails.isSetName() && !hotelDetails.getName().isEmpty()){
		 bind.put("Name", hotelDetails.getName());	
		}else{
			bind.put("Name", "");	
		}
		if(hotelDetails.isSetPhoneNumber() && !hotelDetails.getPhoneNumber().isEmpty()){
			bind.put("ContactNumber", hotelDetails.getPhoneNumber());
		}else{
			bind.put("ContactNumber","");
		}
		if(hotelDetails.isSetEmail() && !hotelDetails.getEmail().isEmpty()){
			bind.put("EmailID", hotelDetails.getEmail());
		}else{
			bind.put("EmailID","");
		}
		if(hotelDetails.isSetAlternateEmail() && !hotelDetails.getAlternateEmail().isEmpty()){
			bind.put("AlternateEmailID",hotelDetails.getAlternateEmail());
		}else{
			bind.put("AlternateEmailID","");
		}
		if(hotelDetails.isSetFaxNumber() && !hotelDetails.getFaxNumber().isEmpty()){
			bind.put("FaxNumber", hotelDetails.getFaxNumber());
		}else{
		  bind.put("FaxNumber", "");
		}
		if(hotelDetails.isSetLatitude() && !hotelDetails.getLatitude().isEmpty()){
			bind.put("Latitude", Double.parseDouble(hotelDetails.getLatitude()));
		}else{
			bind.put("Latitude","");
		}
		if(hotelDetails.isSetLongitude() && !hotelDetails.getLongitude().isEmpty()){
			bind.put("Longitude",Double.parseDouble(hotelDetails.getLongitude()));
		}else{
			bind.put("Longitude", "");
		}
		if(hotelDetails.isSetZoomLevel() && !hotelDetails.getZoomLevel().isEmpty()){
			bind.put("ZoomLevel", hotelDetails.getZoomLevel());
		}else{
			bind.put("ZoomLevel","");
		}
		if(hotelDetails.isSetLanguage() && !hotelDetails.getLanguage().isEmpty()){
			bind.put("Language", hotelDetails.getLanguage());
		}else{
			bind.put("Language", "");
		}
	    if(hotelDetails.isSetCurrency() && !hotelDetails.getCurrency().isEmpty()){
	    	bind.put("Currency",hotelDetails.getCurrency());
	    }else{
	    	bind.put("Currency","");
	    }
	    if(hotelDetails.isSetBillingStatus() && hotelDetails.isBillingStatus()){
	    	bind.put("BillingStatus",1);
	    }else{
	    	bind.put("BillingStatus",0);
	    }
	    if(hotelDetails.isSetWebsite() && !hotelDetails.getWebsite().isEmpty()){
	    	bind.put("Website",hotelDetails.getWebsite());
	    }else{
	    	bind.put("Website","");
	    }
		if(hotelDetails.isSetUserName() && !hotelDetails.getUserName().isEmpty()){
			bind.put("UserName", hotelDetails.getUserName());
		}else{
			bind.put("UserName","");
		}
		if(hotelDetails.isSetRegistrationDate() && !hotelDetails.getRegistrationDate().isEmpty()){
			bind.put("RegistrationDate",hotelDetails.getRegistrationDate());
		}else{
			bind.put("RegistrationDate","");
		}
		 bind.put("lastUpdated", lastupdated);
		 
		 SqlParameterSource paramSource = new MapSqlParameterSource(bind);
			KeyHolder keyHolder = new GeneratedKeyHolder();

			this.namedJdbcTemplate.update(sql.toString(), paramSource,
					keyHolder);
			hotelsave = keyHolder.getKey().intValue();
		 
		  if(hotelsave > 0){
			flag =true;  
		  }
	  }
		
	return flag;
	}
	
	public boolean saveArBillingDetails(long hotelId, com.cultuzz.channel.XMLpojo.GetHotelDataRS.BillingAddress billingAddress,String lastupdated){
		
	 boolean flag = false;
		
	 int billingsave = 0;
		
		LOGGER.debug("Billing info archeving");
		
		if(billingAddress!=null){
			
			namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
			
		StringBuffer sql = new StringBuffer(
				"insert into ebay.Ar_BillingDetails(HotelID,InvoiceRecipient,EmailID,UserName,Language,LastUpdated)");
		sql.append(" values(:HotelID,:InvoiceRecipient,:EmailID,:UserName,:Language,:LastUpdated)");
	       
		Map<String, Object> bind = new HashMap<String, Object>();
		bind.put("HotelID", hotelId);
		if(billingAddress.isSetInvoiceRecipient() && !billingAddress.getInvoiceRecipient().isEmpty()){
		 bind.put("InvoiceRecipient", billingAddress.getInvoiceRecipient());	
		}else{
			 bind.put("InvoiceRecipient","");	
		}
		if(billingAddress.isSetEmail() && !billingAddress.getEmail().isEmpty()){
		  bind.put("EmailID",billingAddress.getEmail());	
		}else{
			bind.put("EmailID", "");
		}
		if(billingAddress.isSetInvoiceLanguage() && !billingAddress.getInvoiceLanguage().isEmpty()){
			bind.put("Language",billingAddress.getInvoiceLanguage());
		}else{
			bind.put("Language","");
		}
		if(billingAddress.isSetUserName() && !billingAddress.getUserName().isEmpty()){
			bind.put("UserName", billingAddress.getUserName());
		}else{
			bind.put("UserName","");
		}
		
		bind.put("LastUpdated", lastupdated);
		
		SqlParameterSource paramSource = new MapSqlParameterSource(bind);
		KeyHolder keyHolder = new GeneratedKeyHolder();

		this.namedJdbcTemplate.update(sql.toString(), paramSource,
				keyHolder);
		billingsave = keyHolder.getKey().intValue();
	 
		if(billingsave > 0){
			flag =true;  
		}
	  }	
	  return flag;
	}
	
	public boolean saveArLegalDetails(long hotelId, com.cultuzz.channel.XMLpojo.GetHotelDataRS.LegalAddress legalAddress,String lastupdated){
		
		 boolean flag = false;
			
		 int legalsave = 0;
			
			LOGGER.debug("legal info archeving");
			
			if(legalAddress!=null){
				
				namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
				
			StringBuffer sql = new StringBuffer(
					"insert into ebay.Ar_LegalDetails(HotelID,LegalName,CompanyName,EmailID,VATNumber,LegalRepresentative,RegisterNumber,UserName,LastUpdated)");
			sql.append(" values(:HotelID,:LegalName,:CompanyName,:EmailID,:VATNumber,:LegalRepresentative,:RegisterNumber,:UserName,:LastUpdated)");
			
			Map<String, Object> bind = new HashMap<String, Object>();
			bind.put("HotelID", hotelId);
			if(legalAddress.isSetLegalName() && !legalAddress.getLegalName().isEmpty()){
				bind.put("LegalName", legalAddress.getLegalName());
			}else{
				bind.put("LegalName", "");
			}
			if(legalAddress.isSetCompanyName() && !legalAddress.getCompanyName().isEmpty()){
				bind.put("CompanyName", legalAddress.getCompanyName());
			}else{
				bind.put("CompanyName","");
			}
			if(legalAddress.isSetEmail() && !legalAddress.getEmail().isEmpty()){
				bind.put("EmailID", legalAddress.getEmail());
			}else{
				bind.put("EmailID","");
			}
			if(legalAddress.isSetLegalRepresentative() && !legalAddress.getLegalRepresentative().isEmpty()){
				bind.put("LegalRepresentative", legalAddress.getLegalRepresentative());
			}else{
				bind.put("LegalRepresentative", "");
			}
			if(legalAddress.isSetVATNumber() && !legalAddress.getVATNumber().isEmpty()){
				bind.put("VATNumber",legalAddress.getVATNumber());
			}else{
				bind.put("VATNumber","");
			}
			if(legalAddress.isSetRegisterNumber() && !legalAddress.getRegisterNumber().isEmpty()){
				bind.put("RegisterNumber",legalAddress.getRegisterNumber());
			}else{
				bind.put("RegisterNumber","");
			}
			if(legalAddress.isSetUserName() && !legalAddress.getUserName().isEmpty()){
				bind.put("UserName", legalAddress.getUserName());
			}
			   bind.put("LastUpdated", lastupdated);
			   
			   SqlParameterSource paramSource = new MapSqlParameterSource(bind);
				KeyHolder keyHolder = new GeneratedKeyHolder();

				this.namedJdbcTemplate.update(sql.toString(), paramSource,
						keyHolder);
				legalsave = keyHolder.getKey().intValue();
			 
				if(legalsave > 0){
					flag =true;  
				}
			   
			}
		return flag;
	}	
	
	public boolean saveArAddressInfo(long hotelId, AddressType address,String lastupdated, int type){
		
		 boolean flag = false;
			
		 int addresssave = 0;
			
			LOGGER.debug("address info archeving");
			
			if(address!=null){
				
				namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
				
			StringBuffer sql = new StringBuffer(
					"insert into ebay.Ar_AddressInfo(HotelID,Type,Street,StreetNumber,SecondStreet,City,Region,Country,countryCode,PostalCode,LastUpdated)");
			sql.append(" values(:HotelID,:Type,:Street,:StreetNumber,:SecondStreet,:City,:Region,:Country,:countryCode,:PostalCode,:LastUpdated)");
			
			Map<String, Object> bind = new HashMap<String, Object>();
			bind.put("HotelID", hotelId);
			bind.put("Type", type);
			if(address.isSetStreet() && !address.getStreet().isEmpty()){
				bind.put("Street", address.getStreet());
			}else{
				bind.put("Street", "");
			}
			if(address.isSetStreetNumber() && !address.getStreetNumber().isEmpty()){
				bind.put("StreetNumber",address.getStreetNumber());
			}else{
				bind.put("StreetNumber","");
			}
			if(address.isSetSecondStreet() && !address.getSecondStreet().isEmpty()){
				bind.put("SecondStreet",address.getSecondStreet());
			}else{
				bind.put("SecondStreet","");
			}
			if(address.isSetCity() && !address.getCity().isEmpty()){
				bind.put("City", address.getCity());
			}else{
				bind.put("City","");
			}
			if(address.isSetRegion() && !address.getRegion().isEmpty()){
				bind.put("Region",address.getRegion());
			}else{
				bind.put("Region","");
			}
			if(address.isSetCountry() && !address.getCountry().isEmpty()){
				bind.put("Country", address.getCountry());
			}else{
				bind.put("Country","");
			}
			if(address.isSetCountryCode() && !address.getCountryCode().isEmpty()){
				bind.put("countryCode", address.getCountryCode());
			}else{
				bind.put("countryCode", "");
			}
			if(address.isSetPostalCode() && !address.getPostalCode().isEmpty()){
				bind.put("PostalCode",address.getPostalCode());
			}else{
				bind.put("PostalCode","");
			}
			    bind.put("LastUpdated", lastupdated);
			  
			    SqlParameterSource paramSource = new MapSqlParameterSource(bind);
				KeyHolder keyHolder = new GeneratedKeyHolder();

				this.namedJdbcTemplate.update(sql.toString(), paramSource,
						keyHolder);
				addresssave = keyHolder.getKey().intValue();
			 
				if(addresssave > 0){
					flag =true;  
				}
			}
		 return flag;
	}		 
	
	public boolean saveArDescription(long hotelId, com.cultuzz.channel.XMLpojo.GetHotelDataRS.Descriptions.Description description ,String lastupdated){
		
		boolean flag =true;
		int descriptionsave = 0;
		
		LOGGER.debug("description archeving");
		
		if(description!=null){
			
			namedJdbcTemplate=ebayJdbcTemplate.getNamedParameterJdbcTemplate();
			
			StringBuffer sql = new StringBuffer(
					"insert into ebay.Ar_HotelDescription(HotelID,Description,Language,UserName,LastUpdated)");

			sql.append(" values(:HotelID,:Description,:Language,:UserName,:LastUpdated)");
			Map<String, Object> bind = new HashMap<String, Object>();
			bind.put("HotelID", hotelId);
			if(description.isSetLanguage() && !description.getLanguage().isEmpty()){
				bind.put("Language",description.getLanguage());
			}else{
				bind.put("Language","");
			}
			if(description.isSetText() && !description.getText().isEmpty()){
				bind.put("Description", description.getText());
			}else{
				bind.put("Description", "");
			}
			if(description.isSetUserName() && !description.getUserName().isEmpty()){
				bind.put("UserName", description.getUserName());
			}else{
				bind.put("UserName", "");
			}
			bind.put("LastUpdated", lastupdated);
			
			SqlParameterSource paramSource = new MapSqlParameterSource(bind);
			KeyHolder keyHolder = new GeneratedKeyHolder();

			this.namedJdbcTemplate.update(sql.toString(), paramSource,
					keyHolder);
			descriptionsave = keyHolder.getKey().intValue();
		 
			if(descriptionsave > 0){
				flag =true;  
			}
			
		}
		
		
		return flag;
	}
	
	
	public String getLastUpdated(long hotelId, int type, int addresstype){
		String lastupdated =null;
		String tablename = "";
		if(type == 1 && addresstype ==0){
			tablename = "HotelInfo";
		}else if(type == 2 && addresstype ==0){
			tablename = "BillingDetails";
		}else if(type == 3 && addresstype ==0){
			tablename = "LegalDetails";
		}else if(type == 4 && addresstype ==0){
			tablename = "HotelDescription";
		}else if(type ==5 && addresstype >0){
			tablename = "AddressInfo";
		}
		
		jdbcTemplate = ebayJdbcTemplate.getJdbcTemplate();
		StringBuffer sql = new StringBuffer("select lastUpdated from ebay."+tablename+" where HotelID = ?");
		if(type ==5 && addresstype >0){
		  	sql.append(" and Type ="+addresstype);
		}
		
		
		lastupdated = jdbcTemplate.queryForObject(sql.toString(), new Object[]{hotelId}, String.class);
		
		return lastupdated;
	}
	
	
}
