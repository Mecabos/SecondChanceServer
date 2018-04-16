package com.esprit.secondchanceserver.service;

import com.esprit.secondchanceserver.enumeration.GenderType;
import com.esprit.secondchanceserver.enumeration.StatusType;
import com.esprit.secondchanceserver.model.AppUser;

import java.util.List;

public interface AppUserService {
    AppUser login(AppUser appUser);
    AppUser findUserByEmail(String email);
    AppUser findUserById(int id);
    List<AppUser> findUsersFor(int active, GenderType gender, int ageMin, int ageMax, int childrenNumberMin, int childrenNumberMax, List<StatusType> statusList, String country, int id);
    AppUser saveUser(AppUser user);
    void updateInfo(AppUser user);
}
