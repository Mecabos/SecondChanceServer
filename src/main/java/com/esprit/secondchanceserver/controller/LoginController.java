package com.esprit.secondchanceserver.controller;


import javax.validation.Valid;

import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {

    @Autowired
    private AppUserService appUserService;

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        AppUser appUser = new AppUser();
        modelAndView.addObject("user", appUser);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid AppUser appUser, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        AppUser userExists = appUserService.findUserByEmail(appUser.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            appUserService.saveUser(appUser);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new AppUser());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @RequestMapping(value="/user/home", method = RequestMethod.GET)
    public ModelAndView UserHome(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + appUser.getName() + " " + appUser.getLastName() + " (" + appUser.getEmail() + ")");
        modelAndView.addObject("user","Successfully logged to the application");
        modelAndView.setViewName("user/home");
        return modelAndView;
    }

    @RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + appUser.getName() + " " + appUser.getLastName() + " (" + appUser.getEmail() + ")");
        modelAndView.addObject("adminMessage","Successfully logged to the application");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

}

