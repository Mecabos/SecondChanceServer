package com.esprit.secondchanceserver.service;


import com.esprit.secondchanceserver.custom.LikeMatchResume;
import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.LikeMatch;

import java.util.List;

public interface LikeMatchService {

    void saveLikeMatch (AppUser sourceAppUser, AppUser targetAppUser);
    List<LikeMatch> getLikeMatchListBySourceUser(AppUser sourceAppUser);
    List<AppUser> getMatchedAppUserListFor (AppUser sourceAppUser);
    void deleteLikeMatch (AppUser sourceAppUser, AppUser targetAppUser);
    void banLikeMatch (AppUser sourceAppUser, AppUser targetAppUser);
    List<LikeMatchResume> getMatchResume (AppUser sourceAppUser);
}
