package com.esprit.secondchanceserver.repository;

import com.esprit.secondchanceserver.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
    AppUser findByEmail(String email);
    AppUser findById (int id);
}