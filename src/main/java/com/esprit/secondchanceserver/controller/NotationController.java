package com.esprit.secondchanceserver.controller;

import com.esprit.secondchanceserver.exceptions.NotFoundException;
import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.service.AppUserService;
import com.esprit.secondchanceserver.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotationController {

    @Autowired
    private FilterService filterService;

    @Autowired
    private AppUserService appUserService;

    @RequestMapping(method = RequestMethod.POST, value = "/user/notation/getFilteredUsers")
    public List<AppUser> GetFilteredUsers (@RequestBody AppUser appUser) throws NotFoundException {
        AppUser appUserForWhomToFilter = appUserService.findUserById(appUser.getId());
        if (appUserForWhomToFilter != null){
            return filterService.getFilteredUsers(appUserForWhomToFilter);
        }else{
            throw new NotFoundException("AppUser of Id : "+appUser.getId()+" Not found");
        }
    }
}
