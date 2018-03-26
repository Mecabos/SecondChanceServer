package com.esprit.secondchanceserver.service;

import com.esprit.secondchanceserver.model.AppUser;

public interface AppUserService {
    public AppUser findUserByEmail(String email);
    public AppUser findUserById(int id);
    public void saveUser(AppUser user);
    public void updateInfo(AppUser user);
}
