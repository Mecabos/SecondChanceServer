package com.esprit.secondchanceserver.controller;

import com.esprit.secondchanceserver.custom.LikeMatchResume;
import com.esprit.secondchanceserver.exceptions.NotFoundException;
import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.LikeMatch;
import com.esprit.secondchanceserver.service.AppUserService;
import com.esprit.secondchanceserver.service.LikeMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LikeMatchController {

    @Autowired
    private LikeMatchService likeMatchService;

    @Autowired
    private AppUserService appUserService;

    @RequestMapping(method = RequestMethod.POST, value = "/user/likeMatch/getMatchedAppUserListFor")
    public List<AppUser> getMatchedAppUserListFor (@RequestBody AppUser appUser) throws NotFoundException {
        AppUser appUserToWhomToSearchMatchedList = appUserService.findUserById(appUser.getId());
        if (appUserToWhomToSearchMatchedList != null){
            return likeMatchService.getMatchedAppUserListFor(appUserToWhomToSearchMatchedList);
        }else{
            throw new NotFoundException("AppUser of Id : "+appUser.getId()+" Not found");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/likeMatch/delete")
    public String deleteLikeMatch (@RequestBody LikeMatch likeMatchToDelete) throws NotFoundException {
        AppUser sourceUser = appUserService.findUserById(likeMatchToDelete.getSourceUser().getId());
        AppUser targetUser = appUserService.findUserById(likeMatchToDelete.getTargetUser().getId());
        if (sourceUser != null && targetUser != null) {
            likeMatchService.deleteLikeMatch(sourceUser,targetUser);
            return "User " + sourceUser.getName() + " Deleted " + targetUser.getName();
        } else {
            String error = "";
            if (sourceUser == null)
                error += "AppUser of Id : " + likeMatchToDelete.getSourceUser().getId() + " Not found ! ";
            if (targetUser == null)
                error += "AppUser of Id : " + likeMatchToDelete.getTargetUser().getId() + " Not found ! ";
            throw new NotFoundException(error);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/likeMatch/getLikeMatchListResume")
    public List<LikeMatchResume> getLikeMatchListResume (@RequestBody AppUser appUser) throws NotFoundException {
        AppUser appUserToWhomToSearchMatchedResumeList = appUserService.findUserById(appUser.getId());
        if (appUserToWhomToSearchMatchedResumeList != null){
            return likeMatchService.getMatchResume(appUserToWhomToSearchMatchedResumeList);
        }else{
            throw new NotFoundException("AppUser of Id : "+appUser.getId()+" Not found");
        }
    }

}
