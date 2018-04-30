package com.esprit.secondchanceserver.controller;

import com.esprit.secondchanceserver.Util.DateUtil;
import com.esprit.secondchanceserver.Util.DebugUtil;
import com.esprit.secondchanceserver.Util.FileUtil;
import com.esprit.secondchanceserver.custom.RequestResult;
import com.esprit.secondchanceserver.exceptions.NotFoundException;
import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.Picture;
import com.esprit.secondchanceserver.service.AppUserService;
import com.esprit.secondchanceserver.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
public class PictureController {

    @Autowired
    private AppUserService appUserService;
    @Autowired
    private PictureService pictureService;

    @RequestMapping(method = RequestMethod.POST, value = "/user/picture/upload/{position}/{id}")
    public RequestResult uploadFile(@RequestParam("file") MultipartFile uploadfile, @PathVariable int position, @PathVariable int id) {
        LocalDateTime currentDate = DateUtil.getCurrentDateTime();
        RequestResult requestResult = new RequestResult();
        String imageName = id + "_" + position + "_" + "_at_" + currentDate.toString();
        imageName += FileUtil.getFileExtension(uploadfile.getOriginalFilename());
        imageName = imageName.replace(":", ".");

        if (uploadfile.isEmpty()) {
            requestResult.success = false;
            requestResult.errors = "File empty !";
            return requestResult;
        }
        try{
            FileUtil.saveFileToCloud(uploadfile, imageName);
        }catch (Exception e){
            requestResult.success = false;
            requestResult.errors = "Problem while uploading file to cloud";
            DebugUtil.logError(e.getMessage());
            return requestResult;
        }

        /*
        try {
            FileUtil.saveFiles(Arrays.asList(uploadfile), Arrays.asList(imageName));
        } catch (IOException e) {
            requestResult.success = false;
            requestResult.errors = "Problem while converting file";
            DebugUtil.logError(e.getMessage());
            return requestResult;
        }*/
        Picture newPicture = new Picture();
        newPicture.setAppUser(appUserService.findUserById(id));
        if (newPicture.getAppUser() == null) {
            requestResult.success = false;
            requestResult.errors = "AppUser of Id : " + id + " Not found ! ";
            return requestResult;
        }
        newPicture.setName(imageName);
        newPicture.setUploadDate(currentDate);
        newPicture.setPosition(position);
        pictureService.saveNewPicture(newPicture);
        requestResult.result = "Successfully uploaded - " + imageName;
        return requestResult;
    }

    /*@RequestMapping(method = RequestMethod.POST, value = "/user/picture/upload/{id}")
    public RequestResult uploadFile(@RequestParam("file") MultipartFile uploadedFile, @PathVariable int id) {
        LocalDateTime currentDate = DateUtil.getCurrentDateTime();
        RequestResult requestResult = new RequestResult();
        Picture newPicture = new Picture();
        newPicture.setAppUser(appUserService.findUserById(id));
        if (newPicture.getAppUser() == null) {
            requestResult.success = false;
            requestResult.errors = "AppUser of Id : " + id + " Not found ! ";
            return requestResult;
        }
        String imageName = id + "_at_" + currentDate.toString();
        imageName += FileUtil.getFileExtension(uploadedFile.getOriginalFilename());
        imageName = imageName.replace(":", "x");

        storageService.store(uploadedFile,imageName);

        newPicture.setName(imageName);
        newPicture.setUploadDate(currentDate);
        newPicture.setPosition(0);
        pictureService.saveNewPicture(newPicture);
        requestResult.result = "Successfully uploaded - " + imageName;
        return requestResult;
    }*/

    @RequestMapping(method = RequestMethod.POST, value = "/user/picture/getPictureList/{id}")
    public List<Picture> getPictureList(@PathVariable int id) throws NotFoundException {
        AppUser appUser = appUserService.findUserById(id);
        try {
            DebugUtil.logError(FileUtil.getFileUrlFromCloud("test"));
        }catch (Exception e){
            DebugUtil.logError(e.getMessage());
        }
        if (appUser != null) {
            return pictureService.getPictureList(new Picture(1,appUser));
        } else {
            String error = "";
            error += "AppUser of Id : " + id + " Not found ! ";
           throw new NotFoundException(error);
        }


    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/picture/delete/{position}/{id}")
    public RequestResult deletePicture(@PathVariable int position, @PathVariable int id) throws NotFoundException {
        RequestResult requestResult = new RequestResult();
        AppUser appUser = appUserService.findUserById(id);
        if (appUser != null) {
            Picture existingPicture = pictureService.getPicture(new Picture(position,appUser));
            if (existingPicture != null){
                pictureService.deletePicture(existingPicture);
                requestResult.result = "Successfully deleted picture at position " +position+" For user with id " + id;
            }else{
                requestResult.success = false;
                requestResult.errors = "No picture found at position " + position +" For user with id " + id;
            }

        } else {
            String error = "";
            error += "AppUser of Id : " + id + " Not found ! ";
            throw new NotFoundException(error);
        }
        return requestResult;
    }

}
