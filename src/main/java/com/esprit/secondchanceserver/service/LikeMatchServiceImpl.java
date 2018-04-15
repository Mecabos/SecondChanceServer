package com.esprit.secondchanceserver.service;

import com.esprit.secondchanceserver.Util.DateUtil;
import com.esprit.secondchanceserver.custom.LikeMatchResume;
import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.LikeMatch;
import com.esprit.secondchanceserver.model.Message;
import com.esprit.secondchanceserver.model.Notation;
import com.esprit.secondchanceserver.repository.LikeMatchRepository;
import com.esprit.secondchanceserver.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("likeMatchService")
public class LikeMatchServiceImpl implements LikeMatchService {

    @Autowired
    private LikeMatchRepository likeMatchRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void saveLikeMatch(AppUser sourceAppUser, AppUser targetAppUser) {
        LikeMatch newLikeMatch = new LikeMatch(DateUtil.getCurrentDateTime(), sourceAppUser, targetAppUser);
        likeMatchRepository.save(newLikeMatch);
        LikeMatch reverseNewLikeMatch = new LikeMatch(DateUtil.getCurrentDateTime(), targetAppUser, sourceAppUser);
        likeMatchRepository.save(reverseNewLikeMatch);
    }

    @Override
    public List<LikeMatch> getLikeMatchListBySourceUser(AppUser sourceAppUser) {
        return likeMatchRepository.findAllBySourceUser(sourceAppUser);
    }

    @Override
    public List<AppUser> getMatchedAppUserListFor(AppUser sourceAppUser) {
        return getLikeMatchListBySourceUser(sourceAppUser).stream()
                .map(LikeMatch::getTargetUser)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void deleteLikeMatch(AppUser sourceAppUser, AppUser targetAppUser) {
        LikeMatch likeMatchToDelete = likeMatchRepository.findBySourceUserAndTargetUser(sourceAppUser,targetAppUser);
        likeMatchRepository.delete(likeMatchToDelete);
        LikeMatch reverseLikeMatchToDelete = likeMatchRepository.findBySourceUserAndTargetUser(targetAppUser,sourceAppUser);
        likeMatchRepository.delete(reverseLikeMatchToDelete);
    }

    @Override
    public List<LikeMatchResume> getMatchResume(AppUser sourceAppUser) {
        List<AppUser> matchedUsers = getMatchedAppUserListFor(sourceAppUser);
        List<LikeMatchResume> likeMatchResumeList = new ArrayList<>();
        for (AppUser matchedAppUser: matchedUsers) {
            LikeMatchResume newResume = new LikeMatchResume();
            newResume.setId(matchedAppUser.getId());
            newResume.setName(matchedAppUser.getName());
            newResume.setNbrUnseenMessages(messageRepository.countBySourceUserAndTargetUserAndIsSeenIsFalse(matchedAppUser,sourceAppUser));
            Message lastMessage = messageRepository.getLastMessage(sourceAppUser,matchedAppUser);
            if (lastMessage != null){
                newResume.setLastMessage(lastMessage.getText());
                newResume.setLastMessageSender(lastMessage.getSourceUser() == sourceAppUser);
            }

            likeMatchResumeList.add(newResume);
        }
        return likeMatchResumeList ;
    }


}
