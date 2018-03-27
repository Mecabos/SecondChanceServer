package com.esprit.secondchanceserver.repository;

import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.Notation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotationRepository extends CrudRepository<Notation, Integer> {
    Notation findById (int id);
    Notation findAllBySourceUserAndTargetUser(AppUser sourceUser, AppUser targetUser);
    List<Notation> findAllBySourceUser(AppUser sourceUser);
}
