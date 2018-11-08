package com.esprit.secondchanceserver.service;

import com.esprit.secondchanceserver.model.Picture;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PictureService {

    void saveNewPicture(Picture newPicture);
    List<Picture> getPictureList (Picture pictureToGetUserFrom);
    void deletePicture (Picture pictureToDelete);
    Picture getPicture (Picture pictureToGet);
    void createImage(MultipartFile file, String fileName) throws IOException;
    Resource loadFile(String filename);
}
