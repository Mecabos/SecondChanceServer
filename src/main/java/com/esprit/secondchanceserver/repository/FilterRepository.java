package com.esprit.secondchanceserver.repository;

import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.Filter;
import org.springframework.data.repository.CrudRepository;

public interface FilterRepository extends CrudRepository<Filter, Integer> {
    Filter findByAppUser(AppUser appUser);
}
