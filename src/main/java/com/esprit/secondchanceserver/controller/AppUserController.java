package com.esprit.secondchanceserver.controller;

import com.esprit.secondchanceserver.exceptions.NotFoundException;
import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.Filter;
import com.esprit.secondchanceserver.repository.FilterRepository;
import com.esprit.secondchanceserver.service.AppUserService;
import com.esprit.secondchanceserver.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private FilterService filterService;

    @RequestMapping(method = RequestMethod.POST, value = "/user/appUser/getUser")
    public AppUser getUser (@RequestBody AppUser appUser) throws NotFoundException{
        AppUser appUserToGet = appUserService.findUserById(appUser.getId());
        if (appUserToGet != null){
            return appUserToGet;
        }else{
            throw new NotFoundException("AppUser of Id : "+appUser.getId()+" Not found");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/appUser/getFilter")
    public Filter getFilter (@RequestBody AppUser appUser) throws NotFoundException{
        AppUser appUserToGet = appUserService.findUserById(appUser.getId());
        if (appUserToGet != null){
            return filterService.findFilterByAppUser(appUserToGet);
        }else{
            throw new NotFoundException("AppUser of Id : "+appUser.getId()+" Not found");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/appUser/updateProfile")
    public String updateInfo (@RequestBody AppUser appUser) throws NotFoundException{
        AppUser appUserToUpdate = appUserService.findUserById(appUser.getId());
        if (appUserToUpdate != null){
            appUserService.updateInfo(appUser);
            return "success";
        }else{
            throw new NotFoundException("AppUser of Id : "+appUser.getId()+" Not found");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/appUser/updateFilter")
    public String updateFilter (@RequestBody Filter filter) throws NotFoundException{
        AppUser appUserToUpdate = appUserService.findUserById(filter.getAppUser().getId());
        if (appUserToUpdate != null){
            filterService.updateFilter(appUserToUpdate,filter);
            return "Success";
        }else{
            throw new NotFoundException("AppUser of Id : "+filter.getAppUser().getId()+" Not found");
        }
    }


}
