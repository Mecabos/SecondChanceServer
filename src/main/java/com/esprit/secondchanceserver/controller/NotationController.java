package com.esprit.secondchanceserver.controller;

import com.esprit.secondchanceserver.Util.DebugUtil;
import com.esprit.secondchanceserver.exceptions.NotFoundException;
import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.Notation;
import com.esprit.secondchanceserver.service.AppUserService;
import com.esprit.secondchanceserver.service.FilterService;
import com.esprit.secondchanceserver.service.NotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotationController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private FilterService filterService;

    @Autowired
    private NotationService notationService;

    @RequestMapping(method = RequestMethod.POST, value = "/user/notation/getNotation")
    public Notation getNotation(@RequestBody Notation notation) throws NotFoundException {
        Notation notationToGet = notationService.findNotationById(notation.getId());
        if (notationToGet != null) {
            return notationToGet;
        } else {
            throw new NotFoundException("Notation of Id : " + notationToGet.getId() + " Not found");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/notation/getFilteredUsers")
    public List<AppUser> getFilteredUsers(@RequestBody AppUser appUser) throws NotFoundException {
        AppUser appUserForWhomToFilter = appUserService.findUserById(appUser.getId());
        if (appUserForWhomToFilter != null) {
            return filterService.getFilteredUsers(appUserForWhomToFilter);
        } else {
            throw new NotFoundException("AppUser of Id : " + appUser.getId() + " Not found");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/notation/saveNewNotation")
    public String saveNewNotation(@RequestBody Notation notation) throws NotFoundException {
        AppUser sourceUser = appUserService.findUserById(notation.getSourceUser().getId());
        AppUser targetUser = appUserService.findUserById(notation.getTargetUser().getId());
        if (sourceUser != null && targetUser != null) {
            notationService.saveNotation(notation);
            return "User " + sourceUser.getName() + " liked " + targetUser.getName();
        } else {
            String error = "";
            if (sourceUser == null)
                error += "AppUser of Id : " + notation.getSourceUser().getId() + " Not found ! ";
            if (targetUser == null)
                error += "AppUser of Id : " + notation.getTargetUser().getId() + " Not found ! ";
            throw new NotFoundException(error);
        }
    }
}
