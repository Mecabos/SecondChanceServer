package com.esprit.secondchanceserver.service;

import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.Filter;

import java.util.List;

public interface FilterService {
    Filter findFilterByAppUser(AppUser appUser);
    void saveFilter(AppUser appUser);
    void updateFilter(AppUser appUser, Filter filter);
    List<AppUser> getFilteredUsers(AppUser appUser);
}
