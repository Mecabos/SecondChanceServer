package com.esprit.secondchanceserver.service;

import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.Filter;

public interface FilterService {
    public Filter findFilterByAppUser(AppUser appUser);
    public void saveFilter(AppUser appUser);
    public void updateFilter(AppUser appUser, Filter filter);
}
