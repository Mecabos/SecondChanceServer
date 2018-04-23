package com.esprit.secondchanceserver.service;

import com.esprit.secondchanceserver.model.Picture;

import java.util.List;

public interface PictureService {

    void saveNewPicture(Picture newPicture);
    List<Picture> getPictureList (Picture pictureToGetUserFrom);
    void deletePicture (Picture pictureToDelete);
    Picture getPicture (Picture pictureToGet);

}
