package com.esprit.secondchanceserver.repository;

import com.esprit.secondchanceserver.enumeration.GenderType;
import com.esprit.secondchanceserver.enumeration.StatusType;
import com.esprit.secondchanceserver.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
    AppUser findByEmail(String email);

    AppUser findById(int id);

    List<AppUser> findAllByActiveAndGenderAndAgeBetweenAndNumberOfChildrenBetweenAndStatusInAndCountryAndIdNot(
            int active,
            GenderType gender,
            int ageMin, int ageMax,
            int childrenNumberMin,
            int childrenNumberMax,
            List<StatusType> statusList,
            String country,
            int id);
}