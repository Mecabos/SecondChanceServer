package com.esprit.secondchanceserver.service;

import com.esprit.secondchanceserver.Util.Base64ImageUtil;
import com.esprit.secondchanceserver.Util.FileUtil;
import com.esprit.secondchanceserver.model.Picture;
import com.esprit.secondchanceserver.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Comparator;
import java.util.List;

@Service("pictureService")
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureRepository pictureRepository;

    @Override
    public void saveNewPicture(Picture newPicture) {
        Picture oldPicture = pictureRepository.findFirstByAppUserAndPosition(newPicture.getAppUser(),newPicture.getPosition());
        if (oldPicture != null)
            deletePicture(oldPicture);
        pictureRepository.save(newPicture);

    }

    @Override
    public List<Picture> getPictureList(Picture pictureToGetUserFrom) {
        List<Picture> pictureList = pictureRepository.findByAppUser(pictureToGetUserFrom.getAppUser());
        for (Picture picture : pictureList){
            picture.setLink(FileUtil.DOWNLOAD_URL+picture.getName());
            picture.setAppUser(null);
        }
        pictureList.sort(Comparator.comparing(Picture::getPosition));
        return pictureList;
    }

    @Override
    public void deletePicture(Picture pictureToDelete) {
        FileUtil.deleteFile(pictureToDelete.getName());
        pictureRepository.delete(pictureToDelete);
    }

    @Override
    public Picture getPicture(Picture pictureToGet) {
        Picture searchedPicture = pictureRepository.findFirstByAppUserAndPosition(pictureToGet.getAppUser(),pictureToGet.getPosition());
        if (searchedPicture != null)
            searchedPicture.setLink(FileUtil.DOWNLOAD_URL+searchedPicture.getName());
        return searchedPicture;
    }
}
