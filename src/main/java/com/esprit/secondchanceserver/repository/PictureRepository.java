package com.esprit.secondchanceserver.repository;

import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.Picture;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PictureRepository extends CrudRepository<Picture, Integer> {

    List<Picture> findByAppUserAndPositionNot (AppUser appUser, int position);
    Picture findFirstByName (String fileName);
    Picture findFirstByAppUserAndPosition (AppUser appUser, int position);
    @Query("select max(p.position) from Picture p where p.appUser = ?1 ")
    int getLastPicturePosition (AppUser appUser);
}
