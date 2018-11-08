package com.esprit.secondchanceserver.service;

import com.esprit.secondchanceserver.Util.Base64ImageUtil;
import com.esprit.secondchanceserver.Util.DebugUtil;
import com.esprit.secondchanceserver.Util.FileUtil;
import com.esprit.secondchanceserver.Util.storage.LocalFileStorage;
import com.esprit.secondchanceserver.model.Picture;
import com.esprit.secondchanceserver.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("pictureService")
public class PictureServiceImpl implements PictureService {

    //private static String UPLOAD_ROOT = "upload-dir";
    private final Path rootLocation = Paths.get("upload-dir");

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private ResourceLoader resourceLoader;

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
                //CLOUD UPLOAD
                //picture.setLink(FileUtil.getFileUrlFromCloud(picture.getName()));
                //LOCAL UPLOAD
                picture.setLink(findOneImageURL(picture.getName()));
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
            deleteImage(actualPictureToDelete.getName());
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
                searchedPicture.setLink(findOneImageURL(searchedPicture.getName()));
            } catch (Exception e) {
                DebugUtil.logError(e.getMessage());
            }
        return searchedPicture;
    }

    //******************************
    public void createImage(MultipartFile file, String fileName) throws IOException {
        if (!file.isEmpty()){
            Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName));
        }
    }

    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }else{
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error! -> message = " + e.getMessage());
        }
    }

    public String findOneImageURL(String filename){
        return("/user/picture/"+filename);
    }

    public boolean deleteImage(String filename) throws IOException{
        return Files.deleteIfExists(rootLocation.resolve(filename));
    }
}
