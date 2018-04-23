package com.esprit.secondchanceserver.custom;

import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.Picture;

public class UserWithProfilePicture {

    public AppUser appUser;
    public Picture profilePicture;

    public UserWithProfilePicture(AppUser appUser, Picture profilePicture) {
        this.appUser = appUser;
        this.profilePicture = profilePicture;
    }
}
