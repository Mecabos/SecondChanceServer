package com.esprit.secondchanceserver.service;

import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.Filter;
import com.esprit.secondchanceserver.model.Notation;

import java.util.List;

public interface NotationService {

    Notation findNotationById(int id);
    void SaveNotation(Notation newNotation);
    List<Notation> GetNotationListBySourceUser (AppUser sourceUser);


}
