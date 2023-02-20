package com.esprit.secondchanceserver.service;

import com.esprit.secondchanceserver.Util.DateUtil;
import com.esprit.secondchanceserver.Util.DebugUtil;
import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.Notation;
import com.esprit.secondchanceserver.repository.NotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

package com.esprit.secondchanceserver.service;

import com.esprit.secondchanceserver.Util.DateUtil;
import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.Notation;
import com.esprit.secondchanceserver.repository.NotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotationService {

    private static final int LIKE_MATCH_VALUE = 1;
    private static final int SUPER_NOTATION_VALUE = 3;

    @Autowired
    private NotationRepository notationRepo;

    @Autowired
    private LikeMatchService likeMatchSvc;


    public Notation findNotationById(int id) {
        if (notationRepo != null) {
            return notationRepo.findById(id);
        }
        return null;
    }

    public void saveNotation(Notation newNotation) {
        if (notationRepo != null) {
            newNotation.setNotationDate(DateUtil.getCurrentDateTime());
            notationRepo.save(newNotation);

            Notation reverseNotation = notationRepo.findBySourceUserAndTargetUser(
                    newNotation.getTargetUser(),
                    newNotation.getSourceUser());

            saveLikeMatchIfNecessary(newNotation, reverseNotation);
        }
    }

    public List<Notation> getNotationListBySourceUser(AppUser sourceUser) {
        if (notationRepo != null) {
            return notationRepo.findAllBySourceUser(sourceUser);
        }
        return null;
    }

    private void saveLikeMatchIfNecessary(Notation newNotation, Notation reverseNotation) {
        if (newNotation.getValue() > 0) {
            if (newNotation.getValue() == LIKE_MATCH_VALUE || newNotation.getValue() == LIKE_MATCH_VALUE + 1) {
                if (reverseNotation != null) {
                    if (reverseNotation.getValue() > 0) {
                        likeMatchSvc.saveLikeMatch(newNotation.getSourceUser(), newNotation.getTargetUser());
                    }
                }
            } else if (newNotation.getValue() == SUPER_NOTATION_VALUE) {
                // CREATE NOTIFICATION FOR SUPER NOTATION
            }
        }
    }
}
