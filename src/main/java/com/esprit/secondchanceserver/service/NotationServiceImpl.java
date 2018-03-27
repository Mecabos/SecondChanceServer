package com.esprit.secondchanceserver.service;

import com.esprit.secondchanceserver.Util.DateUtil;
import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.Notation;
import com.esprit.secondchanceserver.repository.NotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("notationService")
public class NotationServiceImpl implements NotationService {

    @Autowired
    private NotationRepository notationRepository;


    @Override
    public Notation findNotationById(int id) {
        return notationRepository.findById(id);
    }

    @Override
    public void SaveNotation(Notation newNotation) {
        newNotation.setNotationDate(DateUtil.getCurrentDateTime());
        notationRepository.save(newNotation);
    }

    @Override
    public List<Notation> GetNotationListBySourceUser(AppUser sourceUser) {
        return notationRepository.findAllBySourceUser(sourceUser);
    }
}
