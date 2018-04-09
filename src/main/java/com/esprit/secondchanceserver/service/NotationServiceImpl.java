package com.esprit.secondchanceserver.service;

import com.esprit.secondchanceserver.Util.DateUtil;
import com.esprit.secondchanceserver.Util.DebugUtil;
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

    @Autowired
    private LikeMatchService likeMatchService;


    @Override
    public Notation findNotationById(int id) {
        return notationRepository.findById(id);
    }

    @Override
    public void saveNotation(Notation newNotation) {
        newNotation.setNotationDate(DateUtil.getCurrentDateTime());
        notationRepository.save(newNotation);

        Notation reverseNotation = notationRepository.findBySourceUserAndTargetUser(
                newNotation.getTargetUser(),
                newNotation.getSourceUser());

        if (newNotation.getValue() > 0) {
            if (newNotation.getValue() == 1) {
                if (reverseNotation != null) {
                    if (reverseNotation.getValue() > 0)
                        likeMatchService.saveLikeMatch(newNotation.getSourceUser(), newNotation.getTargetUser());
                }
            } else {
                //CREATE NOTIFICATION FOR SUPER NOTATION
            }

        }


    }

    @Override
    public List<Notation> getNotationListBySourceUser(AppUser sourceUser) {
        return notationRepository.findAllBySourceUser(sourceUser);
    }
}
