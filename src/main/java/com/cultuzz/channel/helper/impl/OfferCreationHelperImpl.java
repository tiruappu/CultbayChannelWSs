package com.cultuzz.channel.helper.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.DAO.OfferCreationDAO;
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
import com.cultuzz.channel.helper.OfferHelper;
import com.cultuzz.channel.template.pojo.Vorlage;
import com.cultuzz.channel.util.DateUtil;

@Component
@Qualifier("offerCreationHelper")
public class OfferCreationHelperImpl implements OfferHelper {

	@Autowired
	OfferCreationDAO offerCreationDAOImpl;

	@Autowired
	GetErrorMessagesDAOImpl getErrorMessagesDAOImpl;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OfferCreationHelperImpl.class);

	public OfferCreationRS processOfferCreationHelper(
			OfferCreationRQ offerCreationRQ) {
		// TODO Auto-generated method stub

		OfferCreationRS offerCreationRS = null;
		Vorlage vorlage = null;
		String cusebeda_objekt_ort = null;
		int langId = 0;
		int productId = 0;
		int auktionPathCount = 0;
		int galeryPathCount = 0;

		LOGGER.debug("inside the offerCreation helper");

		try {

			offerCreationRS = new OfferCreationRS();
			ErrorsType errorsType = new ErrorsType();
			List<ErrorType> errorType = errorsType.getError();

			langId = getErrorMessagesDAOImpl.getLanguageId(offerCreationRQ.getErrorLang());

			
			offerCreationRS.setTimeStamp(DateUtil
					.convertDateToString(new Date()));
			
			if (langId > 0) {

				LOGGER.debug("language id is:{}", langId);

				

				if (offerCreationRQ == null) {
					ErrorType error = new ErrorType();
					error.setErrorCode(3131);
					error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(3131, langId));
					errorType.add(error);
					// throw new Exception("offer creation is null");
				} else {

					if (offerCreationRQ.getTemplateId() > 0) {

						offerCreationRS.setTemplateId(offerCreationRQ
								.getTemplateId());

						vorlage = offerCreationDAOImpl
								.getAuktionDetails(offerCreationRQ
										.getTemplateId());
							LOGGER.debug("vorlage details after get");
						cusebeda_objekt_ort = offerCreationDAOImpl
								.getCusebeda_objekt_ort(offerCreationRQ
										.getObjectId());

						if (offerCreationRQ != null && vorlage != null) {

							int offerId = offerCreationDAOImpl.saveAuktion(
									offerCreationRQ, vorlage,
									cusebeda_objekt_ort);

							if (offerId > 0) {

								
								LOGGER.debug("offerId is:{}",offerId);
								offerCreationRS.setOfferId(offerId);

								
								int auction_x_seriesCount = offerCreationDAOImpl
										.saveAuktion_x_source(offerId,
												offerCreationRQ.getSourceId(),
												offerCreationRQ.isOfferRepeat());

								if (auction_x_seriesCount > 0) {

									LOGGER.debug(
											"auction_x_seriesCount is :{}",
											auction_x_seriesCount);
								} else {

									LOGGER.error("auktion_x_series count is:{}",auction_x_seriesCount);
								}

////								int auktion_texteId = offerCreationDAOImpl.saveAuktion_texte(offerCreationRQ.getTemplateId(),
//										offerId);
//								
//								if(auktion_texteId > 0){
//									
//									LOGGER.debug("auktion_texteId is:{}",auktion_texteId);
//								}else{
//									
//									LOGGER.debug("auktion_texteId is zero");
//								}
//								
								 offerCreationDAOImpl.saveAuktions_texte(offerCreationRQ.getTemplateId(),offerId);
//
//								if (auktions_texteId > 0) {
//
//									LOGGER.debug("auktion_texteCount is :{}",
//											auktions_texteId);
//									int vorlageAuktion_texte_Id = offerCreationDAOImpl.updateVorlageAuktions_texte(
//																	auktions_texteId, offerCreationRQ.getTemplateId());
//									if(vorlageAuktion_texte_Id > 0){
//										
//										LOGGER.debug("vorlageAuktion_texte_Id is :{}",vorlageAuktion_texte_Id);
//									}else{
//										
//										LOGGER.error("vorlageAuktion_texte_Id is zero");
//									}
//									int auktion_zusatzCount = offerCreationDAOImpl.saveAuktion_zusatz(offerId, auktions_texteId);
//									
//									if(auktion_zusatzCount > 0){
//										
//										LOGGER.debug("auktion_zusatz count is :{}",auktion_zusatzCount);
//									}else{
//										
//										LOGGER.error("auktion_zusatz count is :{}",auktion_zusatzCount);
//									}
//									
//								} else {
//
//									LOGGER.error("auktion_texte_Id is:{}",auktions_texteId);
//								}

								if (offerCreationRQ.isSetCollectionObjectId()
										&& !offerCreationRQ.getCollectionObjectId().isEmpty() && offerCreationRQ.getCollectionObjectId()!=null && Integer.parseInt(offerCreationRQ
												.getCollectionObjectId()) > 0) {
									
									LOGGER.debug("enter into the loop of collection object");

									offerCreationDAOImpl
											.saveAuction_x_collectionAccount(
													offerId,
													offerCreationRQ
															.getTemplateId(),
													Integer.parseInt(offerCreationRQ
															.getCollectionObjectId()));
								}

								if (offerCreationRQ.isSetShopObjectId()
										&& !offerCreationRQ.getShopObjectId().isEmpty() && offerCreationRQ.getShopObjectId()!=null && Integer.parseInt(offerCreationRQ.getShopObjectId()) > 0) {

									LOGGER.debug("enter into the loop of shop object");
									List<Map<String, Object>> shopCategoryDetails = offerCreationDAOImpl.getVorlage_x_ShopDetails(offerCreationRQ.getTemplateId(), Integer.parseInt(offerCreationRQ.getShopObjectId()));
									
									if(shopCategoryDetails!=null && !shopCategoryDetails.isEmpty()){
										
										Map<String, Object> shopCategories = shopCategoryDetails.get(0);
										LOGGER.debug("map object for categories is:{}",shopCategories.toString());
									
										BigInteger shopCategory1 = (BigInteger)shopCategories.get("ShopCategoryID");
										BigInteger shopCategory2 = (BigInteger)shopCategories.get("ShopCategory2ID");
										
										LOGGER.debug("category1:{}",shopCategory1);
										LOGGER.debug("category2:{}",shopCategory2);
										
										
										if(shopCategory1!=null && !this.isLong(shopCategory1.toString())){
											
											LOGGER.debug("shop category1 is true");
											  shopCategory1 = null;
										}
										
										if(shopCategory2!=null && !this.isLong(shopCategory2.toString())){
											
											LOGGER.debug("shop category2 is true");
											shopCategory2 = null;
										}
										
										offerCreationDAOImpl
											.saveAuction_x_shopCategory(
													offerId, Integer.parseInt(offerCreationRQ
															.getShopObjectId()),shopCategory1,shopCategory2);
								}}
							} else {
								
								LOGGER.error("auktion id afterSave is:{}",offerId);
	
							}
							
							List<Map<String, Object>> auktionPath = offerCreationDAOImpl.getAuktionPath(offerCreationRQ.getObjectId(), offerCreationRQ.getTemplateId());
								
							if(auktionPath!=null && !auktionPath.isEmpty()){
								
								for(Map<String, Object> auktionURL : auktionPath){
									
								
								auktionPathCount = offerCreationDAOImpl.saveAuktion_x_pictures(offerId, offerCreationRQ.getTemplateId(), offerCreationRQ.getSourceId(), 2, auktionURL.get("auktionbildpath").toString());
							
								}
								 LOGGER.debug("auktion path count is :{}",auktionPathCount);
							}else{
								
								LOGGER.error("auktion path is null");
							}
							
						   String galeryPath = offerCreationDAOImpl.getGaleryPath(offerCreationRQ.getObjectId(), offerCreationRQ.getTemplateId());
							
						   if(galeryPath!=null){
								
								 galeryPathCount = offerCreationDAOImpl.saveAuktion_x_pictures(offerId, offerCreationRQ.getTemplateId(), offerCreationRQ.getSourceId(), 1, galeryPath);
							
								 LOGGER.debug("galery path count is :{}",galeryPathCount);
							}else{
								
								LOGGER.error("galery path is null");
							}
						   
						   
						} else {

							LOGGER.error("offercreation request or vorlage details is null");
						}

						// -----------------
					} else {

					   LOGGER.error("template Id is null in offerCreation");
					}
				}

			} else {

				ErrorType error = new ErrorType();
				error.setErrorCode(1106);

				error.setErrorMessage(getErrorMessagesDAOImpl.getErrorMessage(
						1106, 2));
				errorType.add(error);
			}

			if (errorType.size() > 0) {
				offerCreationRS.setErrors(errorsType);
				offerCreationRS.setAck("Failure");
			} else {
				offerCreationRS.setAck("Success");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return offerCreationRS;
	}

	public OfferDetailsRS processOfferDetailsHelper(
			OfferDetailsRQ offerDetailsRQ) {
		// TODO Auto-generated method stub
		return null;
	}

	public ListOfOffersRS processListOfOffersHelper(
			ListOfOffersRQ listOfOffersRQ) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isLong(String str) {
		 
		boolean flag = false;
		try {
            Long num = Long.parseLong(str);
            if(num>0.0){
            	
            	flag = true;
            }
            
        } catch (NumberFormatException nfe) {
           nfe.printStackTrace();
        }
        return flag;
    }
}
