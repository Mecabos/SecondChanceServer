package com.esprit.secondchanceserver.repository;

import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.Picture;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PictureRepository extends CrudRepository<Picture, Integer> {

    List<Picture> findByAppUser (AppUser appUser);
    Picture findFirstByName (String fileName);
    Picture findFirstByAppUserAndPosition (AppUser appUser, int position);
}
