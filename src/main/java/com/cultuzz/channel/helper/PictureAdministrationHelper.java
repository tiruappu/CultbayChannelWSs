package com.cultuzz.channel.helper;

import com.cultuzz.channel.XMLpojo.AddPictureRQ;
import com.cultuzz.channel.XMLpojo.AddPictureRS;
import com.cultuzz.channel.XMLpojo.DeletePictureRQ;
import com.cultuzz.channel.XMLpojo.DeletePictureRS;
import com.cultuzz.channel.XMLpojo.ListOfPicturesRQ;
import com.cultuzz.channel.XMLpojo.ListOfPicturesRS;

public interface PictureAdministrationHelper {

	public AddPictureRS validateAddPicture(AddPictureRQ addPicture);
	
	public ListOfPicturesRS validatePicturesList(ListOfPicturesRQ pictureListRQ);
	
	public boolean validateImageId();
	
	public AddPictureRS processAddPicture(AddPictureRQ pictureRq);
	
	public ListOfPicturesRS getAllPictures(ListOfPicturesRQ pictureListRQ);
	
	public DeletePictureRS validateDeletePicture(DeletePictureRQ deletePictureRq);
	
	public DeletePictureRS processDeletePicture(DeletePictureRQ deletePictureRq);
}
