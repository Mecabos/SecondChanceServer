package com.esprit.secondchanceserver.service;

import com.esprit.secondchanceserver.Util.Base64ImageUtil;
import com.esprit.secondchanceserver.Util.DebugUtil;
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
        if (newPicture.getPosition() == 0) {
            Picture oldPicture = pictureRepository.findFirstByAppUserAndPosition(newPicture.getAppUser(), 0);
            if (oldPicture != null)
                deletePicture(oldPicture);
        } else {
            int newPicturePosition = pictureRepository.getLastPicturePosition(newPicture.getAppUser()) + 1;
            newPicture.setPosition(newPicturePosition);
        }

        pictureRepository.save(newPicture);

    }

    @Override
    public List<Picture> getPictureList(Picture pictureToGetUserFrom) {
        List<Picture> pictureList = pictureRepository.findByAppUserAndPositionNot(pictureToGetUserFrom.getAppUser(), 0);
        for (Picture picture : pictureList) {
            try {
                picture.setLink(FileUtil.getFileUrlFromCloud(picture.getName()));
            } catch (Exception e) {
                DebugUtil.logError(e.getMessage());
            }

        }
        pictureList.sort(Comparator.comparing(Picture::getPosition));
        return pictureList;
    }

    @Override
    public void deletePicture(Picture pictureToDelete) {
        Picture actualPictureToDelete = pictureRepository.findFirstByAppUserAndPosition(pictureToDelete.getAppUser(), pictureToDelete.getPosition());
        try {
            FileUtil.deleteFileFromCloud(actualPictureToDelete.getName());
        } catch (Exception e) {
            DebugUtil.logError(e.getMessage());
        }
        pictureRepository.delete(pictureToDelete);
    }

    @Override
    public Picture getPicture(Picture pictureToGet) {
        Picture searchedPicture = pictureRepository.findFirstByAppUserAndPosition(pictureToGet.getAppUser(), pictureToGet.getPosition());
        if (searchedPicture != null)
            try {
                searchedPicture.setLink(FileUtil.getFileUrlFromCloud(searchedPicture.getName()));
            } catch (Exception e) {
                DebugUtil.logError(e.getMessage());
            }
        return searchedPicture;
    }
}
