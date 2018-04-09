package com.esprit.secondchanceserver.repository;

import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.LikeMatch;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LikeMatchRepository extends CrudRepository<LikeMatch, Integer> {

    List<LikeMatch> findAllBySourceUser (AppUser sourceAppUser);
    LikeMatch findBySourceUserAndTargetUser(AppUser sourceUser, AppUser targetUser);

    /*@Query("select m.targetUser from LikeMatch m where (m.sourceUser = ?1)")
    List<AppUser> getMatchedUsers(AppUser sourceUser);*/

}
