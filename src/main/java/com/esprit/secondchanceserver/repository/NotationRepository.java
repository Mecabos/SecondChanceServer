package com.esprit.secondchanceserver.repository;

import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.Notation;
import org.springframework.data.repository.CrudRepository;

public interface NotationRepository extends CrudRepository<Notation, Integer> {
    Notation findBySourceUserAndTargetUser(AppUser sourceUser, AppUser targetUser);
}
