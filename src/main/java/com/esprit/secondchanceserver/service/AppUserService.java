package com.esprit.secondchanceserver.service;

import com.esprit.secondchanceserver.model.AppUser;

public interface AppUserService {
    public AppUser findUserByEmail(String email);
    public void saveUser(AppUser user);
}
