package com.cultuzz.channel.helper.impl;

import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.DAO.PictureAdministrationDAO;
import com.cultuzz.channel.DAO.impl.GetErrorMessagesDAOImpl;
import com.cultuzz.channel.XMLpojo.AddPictureRQ;
import com.cultuzz.channel.XMLpojo.AddPictureRS;
import com.cultuzz.channel.XMLpojo.DeletePictureRQ;
import com.cultuzz.channel.XMLpojo.DeletePictureRS;
import com.cultuzz.channel.XMLpojo.ErrorType;
import com.cultuzz.channel.XMLpojo.ErrorsType;
import com.cultuzz.channel.XMLpojo.ListOfPicturesRQ;
import com.cultuzz.channel.XMLpojo.ListOfPicturesRS;
import com.cultuzz.channel.XMLpojo.ListOfPicturesRS.Images;
import com.cultuzz.channel.XMLpojo.ListOfPicturesRS.Images.Image;
import com.cultuzz.channel.helper.PictureAdministrationHelper;
import com.cultuzz.channel.template.pojo.HotelPictures;
import com.cultuzz.channel.template.pojo.PictureCategories;
import com.cultuzz.channel.util.CommonValidations;

@Component
public class PictureAdministrationHelperImpl implements PictureAdministrationHelper {

	

	@Autowired
	GetErrorMessagesDAOImpl getErrormessages;
	
	@Autowired
	PictureAdministrationDAO pictureAdministrationDAO;

	
	@Autowired
	CommonValidations commonValidations;
	
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PictureAdministrationHelperImpl.class);
	
	public AddPictureRS validateAddPicture(AddPictureRQ addPicture){
		
		
		AddPictureRS addPictureRS=new AddPictureRS();
		addPictureRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		LOGGER.debug("inside validate addpicture method");
		
		int objectid=0;
		
		ErrorsType error=new ErrorsType();
		
		
		List<ErrorType> errorsType=error.getError();
		int langid=0;
		
		if(addPicture.isSetAuthenticationCode()){
			
			boolean authCodeStatus=	commonValidations.checkAuthCode(addPicture.getAuthenticationCode());
				if(!authCodeStatus){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1100);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,2));
					errorsType.add(errorType);
					addPictureRS.setErrors(error);
					addPictureRS.setAck("Failure");
					return addPictureRS;
				}
					
				
			}else{
				//return addPictureRS;
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1100);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,2));
				errorsType.add(errorType);
				addPictureRS.setErrors(error);
				addPictureRS.setAck("Failure");
				return addPictureRS;
			}
			
		
			if(addPicture.isSetErrorLang()){
			
			langid=commonValidations.checkErrorLangCode(addPicture.getErrorLang());
			
			if(langid>0){
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1106);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,2));
				errorsType.add(errorType);
				addPictureRS.setErrors(error);
				addPictureRS.setAck("Failure");
				return addPictureRS;
			}
			
		}else{				
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1106);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,2));
			errorsType.add(errorType);
			addPictureRS.setErrors(error);
			addPictureRS.setAck("Failure");
			return addPictureRS;
		}
		
			if(addPicture.isSetTimeStamp() && !addPicture.getTimeStamp().trim().isEmpty()){
				
				boolean timestampStatus = commonValidations.checkTimeStamp(addPicture.getTimeStamp());
				if(!timestampStatus){	
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1104);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1104,langid));
					errorsType.add(errorType);
					addPictureRS.setErrors(error);
					addPictureRS.setAck("Failure");
					return addPictureRS;
				}
				
				}else{
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1105);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1105,langid));
					errorsType.add(errorType);
					addPictureRS.setErrors(error);
					addPictureRS.setAck("Failure");
					return addPictureRS;
				}	
		
			if(addPicture.isSetSourceId() ){
				if(!commonValidations.checkSourceId(addPicture.getSourceId())){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1101);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langid));
					errorsType.add(errorType);
					addPictureRS.setErrors(error);
					addPictureRS.setAck("Failure");
					return addPictureRS;
				}
			
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1101);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langid));
				errorsType.add(errorType);
				addPictureRS.setErrors(error);
				addPictureRS.setAck("Failure");
				return addPictureRS;
				
			}
			
			
			if(addPicture.isSetChannelId()){
				
				if(!commonValidations.checkChannelId(addPicture.getChannelId())){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1102);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langid));
					errorsType.add(errorType);
					addPictureRS.setErrors(error);
					addPictureRS.setAck("Failure");
					return addPictureRS;
				}
				
			}else{
				
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1102);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langid));
				errorsType.add(errorType);
				addPictureRS.setErrors(error);
				addPictureRS.setAck("Failure");
				return addPictureRS;
				
			}
			
			if(addPicture.isSetObjectId()){
				
				LOGGER.debug("Checking objectid");
				if(!commonValidations.checkObjectId(addPicture.getObjectId())){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1103);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
					errorsType.add(errorType);
					addPictureRS.setErrors(error);
					addPictureRS.setAck("Failure");
					return addPictureRS;
				}else{
					//objectIdFlag=true;
					objectid=addPicture.getObjectId();
				}
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1103);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
				errorsType.add(errorType);
				addPictureRS.setErrors(error);
				addPictureRS.setAck("Failure");
				return addPictureRS;
				
			}
			
			
			
			if(addPicture.isSetCategory() && addPicture.getCategory()!=null ){
				
					if(!this.checkCategory(addPicture.getCategory(),addPicture.getObjectId())){
						//invalid category
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(17006);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(17006,langid));
						errorsType.add(errorType);
					}
				
			}else{
				//category is required
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(17006);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(17006,langid));
				errorsType.add(errorType);
			}
			
			if(addPicture.isSetURL()){
				
				String imgURL=addPicture.getURL();
				
				//System.out.println("img1"+imgURL.indexOf("https://"));
				//System.out.println("img2"+imgURL.substring(0, 5));
				//System.out.println("img2"+imgURL.substring(0, 4));
				//imgURL.substring(0, 5).equals("https")
				if(imgURL.substring(0, 5).equals("https")){
					if(isValidURL(imgURL)){
						
					}else{
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(17001);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(17001,langid));
						errorsType.add(errorType);
					}
					
				}else{
					//URL should be with https
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(17007);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(17007,langid));
					errorsType.add(errorType);
				}
				
			}else{
				//URL is required
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(17001);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(17001,langid));
				errorsType.add(errorType);
			}
			
			if(addPicture.isSetDimensions()){
				String dimesion=addPicture.getDimensions();
				
				String dimensionArray[]=dimesion.split("X");
				
				if(dimesion.split("X").length==2){
					
					if(checkNumbers(dimensionArray[0]) && checkNumbers(dimensionArray[1])){
						
					}else{
						ErrorType errorType=new ErrorType();
						errorType.setErrorCode(17002);
						errorType.setErrorMessage(getErrormessages.getErrorMessage(17002,langid));
						errorsType.add(errorType);
					}
					
				}else{
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(17002);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(17002,langid));
					errorsType.add(errorType);
				}
				
				//checkNumbers(String inputString)
				
				//invalid dimensions 17002
			}
			
			if(addPicture.isSetImageName() && !addPicture.getImageName().equals("")){
				String imageName=addPicture.getImageName();
				if(!checkImageName(imageName)){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(17003);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(17003,langid));
					errorsType.add(errorType);
				}
				
			}else{
				//Name is required
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(17003);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(17003,langid));
				errorsType.add(errorType);
			}
		
			
			
			if(errorsType.size()>0){
				addPictureRS.setErrors(error);
				addPictureRS.setAck("Failure");
				//AddPictureRq is failed
			}else
				addPictureRS.setAck("Success");
			
			
		return addPictureRS;
	}
	
	
public AddPictureRS processAddPicture(AddPictureRQ pictureRq){
		
	AddPictureRS addPictureRS=new AddPictureRS();
	addPictureRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
	
		int picid=pictureAdministrationDAO.savePictureData(pictureRq);
		
		if(picid>0){
			addPictureRS.setAck("Success");
			addPictureRS.setImageId(picid);
		}else{
			addPictureRS.setAck("Failure");
		}
		return addPictureRS;
	}
	
	
	public boolean validateImageId(){
		
		return false;
	}
	
	
	public DeletePictureRS validateDeletePicture(DeletePictureRQ deletePictureRq){
		
		DeletePictureRS deletePictureRS=new DeletePictureRS();
		deletePictureRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		
		int objectid=0;
		ErrorsType error=new ErrorsType();
		List<ErrorType> errorsType=error.getError();
		int langid=0;
		
		if(deletePictureRq.isSetAuthenticationCode()){
			
			boolean authCodeStatus=	commonValidations.checkAuthCode(deletePictureRq.getAuthenticationCode());
				if(!authCodeStatus){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1100);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,2));
					errorsType.add(errorType);
					deletePictureRS.setErrors(error);
					deletePictureRS.setAck("Failure");
					return deletePictureRS;
				}
					
				
			}else{
				//return addPictureRS;
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1100);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,2));
				errorsType.add(errorType);
				deletePictureRS.setErrors(error);
				deletePictureRS.setAck("Failure");
				return deletePictureRS;
			}
			
		
			if(deletePictureRq.isSetErrorLang()){
			
			langid=commonValidations.checkErrorLangCode(deletePictureRq.getErrorLang());
			
			if(langid>0){
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1106);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,2));
				errorsType.add(errorType);
				deletePictureRS.setErrors(error);
				deletePictureRS.setAck("Failure");
				return deletePictureRS;
			}
			
		}else{				
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1106);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,2));
			errorsType.add(errorType);
			deletePictureRS.setErrors(error);
			deletePictureRS.setAck("Failure");
			return deletePictureRS;
		}
		
			if(deletePictureRq.isSetTimeStamp() && !deletePictureRq.getTimeStamp().trim().isEmpty()){
				
				boolean timestampStatus = commonValidations.checkTimeStamp(deletePictureRq.getTimeStamp());
				if(!timestampStatus){	
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1104);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1104,langid));
					errorsType.add(errorType);
					deletePictureRS.setErrors(error);
					deletePictureRS.setAck("Failure");
					return deletePictureRS;
				}
				
				}else{
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1105);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1105,langid));
					errorsType.add(errorType);
					deletePictureRS.setErrors(error);
					deletePictureRS.setAck("Failure");
					return deletePictureRS;
				}	
		
			if(deletePictureRq.isSetSourceId() ){
				if(!commonValidations.checkSourceId(deletePictureRq.getSourceId())){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1101);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langid));
					errorsType.add(errorType);
					deletePictureRS.setErrors(error);
					deletePictureRS.setAck("Failure");
					return deletePictureRS;
				}
			
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1101);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langid));
				errorsType.add(errorType);
				deletePictureRS.setErrors(error);
				deletePictureRS.setAck("Failure");
				return deletePictureRS;
				
			}
			
			
			if(deletePictureRq.isSetChannelId()){
				
				if(!commonValidations.checkChannelId(deletePictureRq.getChannelId())){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1102);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langid));
					errorsType.add(errorType);
					deletePictureRS.setErrors(error);
					deletePictureRS.setAck("Failure");
					return deletePictureRS;
				}
				
			}else{
				
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1102);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langid));
				errorsType.add(errorType);
				deletePictureRS.setErrors(error);
				deletePictureRS.setAck("Failure");
				return deletePictureRS;
				
			}
			
			if(deletePictureRq.isSetObjectId()){
				
				LOGGER.debug("Checking objectid");
				if(!commonValidations.checkObjectId(deletePictureRq.getObjectId())){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1103);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
					errorsType.add(errorType);
					deletePictureRS.setErrors(error);
					deletePictureRS.setAck("Failure");
					return deletePictureRS;
				}else{
					//objectIdFlag=true;
					objectid=deletePictureRq.getObjectId();
				}
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1103);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
				errorsType.add(errorType);
				deletePictureRS.setErrors(error);
				deletePictureRS.setAck("Failure");
				return deletePictureRS;
				
			}
		
			if(deletePictureRq.isSetImageId()){
				
				if(!pictureAdministrationDAO.checkImageId(deletePictureRq.getImageId())){
					//invalid imageid
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(17004);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(17004,langid));
					errorsType.add(errorType);
				}
				
			}else{
				//imageid is required
				ErrorType errorType=new ErrorType();
			errorType.setErrorCode(17004);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(17004,langid));
			errorsType.add(errorType);
			}
			if(errorsType.size()>0){
			deletePictureRS.setErrors(error);
			deletePictureRS.setAck("Failure");
			}else
				deletePictureRS.setAck("Success");
		
		return deletePictureRS;
	}
	
	
	public DeletePictureRS processDeletePicture(DeletePictureRQ deletePictureRq){
		
		DeletePictureRS deletePictureRS=new DeletePictureRS();
		deletePictureRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		
		boolean deleteStatus=pictureAdministrationDAO.updateImageStatus(deletePictureRq.getImageId());
		
		if(deleteStatus){
			
			deletePictureRS.setAck("Success");
			
			
		}else{
			deletePictureRS.setAck("Failure");

			ErrorsType error=new ErrorsType();
			int langid=commonValidations.checkErrorLangCode(deletePictureRq.getErrorLang());
			//deletePicturesRQ failed
			List<ErrorType> errorsType=error.getError();
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1103);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
			errorsType.add(errorType);
			deletePictureRS.setErrors(error);
			
			
		}
		
		return deletePictureRS;
		
	}
	
	public ListOfPicturesRS validatePicturesList(ListOfPicturesRQ pictureListRQ){
		ListOfPicturesRS listOfPicturesRS=new ListOfPicturesRS();
		listOfPicturesRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		
		int objectid=0;
		ErrorsType error=new ErrorsType();
		List<ErrorType> errorsType=error.getError();
		int langid=0;
		
		if(pictureListRQ.isSetAuthenticationCode()){
			
			boolean authCodeStatus=	commonValidations.checkAuthCode(pictureListRQ.getAuthenticationCode());
				if(!authCodeStatus){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1100);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,2));
					errorsType.add(errorType);
					listOfPicturesRS.setErrors(error);
					listOfPicturesRS.setAck("Failure");
					return listOfPicturesRS;
				}
					
				
			}else{
				//return addPictureRS;
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1100);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1100,2));
				errorsType.add(errorType);
				listOfPicturesRS.setErrors(error);
				listOfPicturesRS.setAck("Failure");
				return listOfPicturesRS;
			}
			
		
			if(pictureListRQ.isSetErrorLang()){
			
			langid=commonValidations.checkErrorLangCode(pictureListRQ.getErrorLang());
			
			if(langid>0){
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1106);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,2));
				errorsType.add(errorType);
				listOfPicturesRS.setErrors(error);
				listOfPicturesRS.setAck("Failure");
				return listOfPicturesRS;
			}
			
		}else{				
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1106);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1106,2));
			errorsType.add(errorType);
			listOfPicturesRS.setErrors(error);
			listOfPicturesRS.setAck("Failure");
			return listOfPicturesRS;
		}
		
			if(pictureListRQ.isSetTimeStamp() && !pictureListRQ.getTimeStamp().trim().isEmpty()){
				
				boolean timestampStatus = commonValidations.checkTimeStamp(pictureListRQ.getTimeStamp());
				if(!timestampStatus){	
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1104);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1104,langid));
					errorsType.add(errorType);
					listOfPicturesRS.setErrors(error);
					listOfPicturesRS.setAck("Failure");
					return listOfPicturesRS;
				}
				
				}else{
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1105);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1105,langid));
					errorsType.add(errorType);
					listOfPicturesRS.setErrors(error);
					listOfPicturesRS.setAck("Failure");
					return listOfPicturesRS;
				}	
		
			if(pictureListRQ.isSetSourceId() ){
				if(!commonValidations.checkSourceId(pictureListRQ.getSourceId())){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1101);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langid));
					errorsType.add(errorType);
					listOfPicturesRS.setErrors(error);
					listOfPicturesRS.setAck("Failure");
					return listOfPicturesRS;
				}
			
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1101);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1101,langid));
				errorsType.add(errorType);
				listOfPicturesRS.setErrors(error);
				listOfPicturesRS.setAck("Failure");
				return listOfPicturesRS;
				
			}
			
			
			if(pictureListRQ.isSetChannelId()){
				
				if(!commonValidations.checkChannelId(pictureListRQ.getChannelId())){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1102);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langid));
					errorsType.add(errorType);
					listOfPicturesRS.setErrors(error);
					listOfPicturesRS.setAck("Failure");
					return listOfPicturesRS;
				}
				
			}else{
				
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1102);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1102,langid));
				errorsType.add(errorType);
				listOfPicturesRS.setErrors(error);
				listOfPicturesRS.setAck("Failure");
				return listOfPicturesRS;
				
			}
			
			if(pictureListRQ.isSetObjectId()){
				
				LOGGER.debug("Checking objectid");
				if(!commonValidations.checkObjectId(pictureListRQ.getObjectId())){
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(1103);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
					errorsType.add(errorType);
					listOfPicturesRS.setErrors(error);
					listOfPicturesRS.setAck("Failure");
					return listOfPicturesRS;
				}else{
					//objectIdFlag=true;
					objectid=pictureListRQ.getObjectId();
				}
				
			}else{
				ErrorType errorType=new ErrorType();
				errorType.setErrorCode(1103);
				errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
				errorsType.add(errorType);
				listOfPicturesRS.setErrors(error);
				listOfPicturesRS.setAck("Failure");
				return listOfPicturesRS;
				
			}
			
			if(pictureListRQ.isSetCategory() && pictureListRQ.getCategory()!=null){
				if(!this.checkCategory(pictureListRQ.getCategory(),pictureListRQ.getObjectId())){
					//invalid category
					ErrorType errorType=new ErrorType();
					errorType.setErrorCode(17005);
					errorType.setErrorMessage(getErrormessages.getErrorMessage(17005,langid));
					errorsType.add(errorType);
				}
			}else if(pictureListRQ.getCategory()==null){
				//checkNumbers(String inputString)
				pictureListRQ.setCategory(0);
			}
			
			if(errorsType.size()>0){
				listOfPicturesRS.setErrors(error);
				listOfPicturesRS.setAck("Failure");
			}else
				listOfPicturesRS.setAck("Success");
		
		return listOfPicturesRS;
	}
	
	
	
	
	public ListOfPicturesRS getAllPictures(ListOfPicturesRQ pictureListRQ){
		
		ListOfPicturesRS listOfPicturesRS=new ListOfPicturesRS();
		listOfPicturesRS.setTimeStamp(commonValidations.getCurrentTimeStamp());
		
		List<HotelPictures> picturesList=pictureAdministrationDAO.getAllImages(pictureListRQ.getObjectId(), pictureListRQ.getCategory());
		
		if(picturesList!=null){
			listOfPicturesRS.setTotalNoOfImages(picturesList.size());
			listOfPicturesRS.setAck("Success");
			Images allImages=new Images();
			List<Image> imageList=allImages.getImage();
			for(HotelPictures hotelPicture:picturesList){
				Image img=new Image();
				img.setDimensions(hotelPicture.getDimensions());
				img.setImageId(hotelPicture.getId());
				img.setName(hotelPicture.getName());
				img.setURL(hotelPicture.getURL());
				img.setThumbnailURL(hotelPicture.getThumbnailURL());
				imageList.add(img);
			}
			listOfPicturesRS.setImages(allImages);
		}else{
			listOfPicturesRS.setAck("Failure");

			ErrorsType error=new ErrorsType();
			int langid=commonValidations.checkErrorLangCode(pictureListRQ.getErrorLang());
			//listOfPicturesRQ failed
			List<ErrorType> errorsType=error.getError();
			ErrorType errorType=new ErrorType();
			errorType.setErrorCode(1103);
			errorType.setErrorMessage(getErrormessages.getErrorMessage(1103,langid));
			errorsType.add(errorType);
			listOfPicturesRS.setErrors(error);
			
			
		}
		
		return listOfPicturesRS;
	}
	
	
	
	
	
	public boolean checkCategory(int categoryname,int objectid){
		boolean categoryStatus=false;
		List<PictureCategories>  picturecats=pictureAdministrationDAO.getAllPictureCategories(objectid);
		
		for(PictureCategories picCats:picturecats){
			if(picCats.getCategoryId() == categoryname){
				categoryStatus=true;
			}
		}
		
		return categoryStatus;
	}
	
	
	public boolean checkNumbers(String inputString){
		Pattern pattern = Pattern.compile("^[0-9]*$");
		
		return pattern.matcher(inputString).matches();
	}
	
	public boolean checkImageName(String imgName){
		
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9-_ ]*$");
		
		return pattern.matcher(imgName).matches();
	}
	
	
	 public static boolean isValidURL(String url) 
	    { 
	        /* Try creating a valid URL */
	        try { 
	            new URL(url).toURI(); 
	            return true; 
	        } 
	          
	        // If there was an Exception 
	        // while creating URL object 
	        catch (Exception e) { 
	            return false; 
	        } 
	    } 
	
}
