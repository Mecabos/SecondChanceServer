package com.esprit.secondchanceserver.service;

import com.esprit.secondchanceserver.Util.DateUtil;
import com.esprit.secondchanceserver.Util.DebugUtil;
import com.esprit.secondchanceserver.custom.LikeMatchResume;
import com.esprit.secondchanceserver.enumeration.DurationType;
import com.esprit.secondchanceserver.model.*;
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

    @Autowired
    private PictureService pictureService;

    @Autowired
    private AppUserService appUserService;

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
        List<AppUser> matchedAppUserList = getLikeMatchListBySourceUser(sourceAppUser).stream()
                .map(LikeMatch::getTargetUser)
                .collect(Collectors.toCollection(ArrayList::new));
        for (AppUser appUser : matchedAppUserList){
            appUser.setProfilePicture(pictureService.getPicture(new Picture(0,appUser)));
        }
        return matchedAppUserList;
    }

    @Override
    public void deleteLikeMatch(AppUser sourceAppUser, AppUser targetAppUser) {
        LikeMatch likeMatchToDelete = likeMatchRepository.findBySourceUserAndTargetUser(sourceAppUser,targetAppUser);
        likeMatchRepository.delete(likeMatchToDelete);
        LikeMatch reverseLikeMatchToDelete = likeMatchRepository.findBySourceUserAndTargetUser(targetAppUser,sourceAppUser);
        likeMatchRepository.delete(reverseLikeMatchToDelete);
    }

    @Override
    public void banLikeMatch(AppUser sourceAppUser, AppUser targetAppUser) {
        LikeMatch likeMatchToDelete = likeMatchRepository.findBySourceUserAndTargetUser(sourceAppUser,targetAppUser);
        likeMatchRepository.delete(likeMatchToDelete);
        LikeMatch reverseLikeMatchToDelete = likeMatchRepository.findBySourceUserAndTargetUser(targetAppUser,sourceAppUser);
        likeMatchRepository.delete(reverseLikeMatchToDelete);
        appUserService.incrementBanCount(targetAppUser);
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
            newResume.setProfilePicture(pictureService.getPicture(new Picture(0,matchedAppUser)));
            Message lastMessage = messageRepository.getLastMessage(sourceAppUser,matchedAppUser);
            if (lastMessage != null){
                newResume.setLastMessage(lastMessage.getText());
                newResume.setLastMessageSender(lastMessage.getSourceUser() == sourceAppUser);
                int timeSinceLastMessage = (int)DateUtil.getDurationBetween(lastMessage.getSendingDate(), DateUtil.getCurrentDateTime(), DurationType.Minutes);
                String text = "";
                if (timeSinceLastMessage <= 0){
                    text = " < 1 min";
                }else if(timeSinceLastMessage > 0 && timeSinceLastMessage < 60){
                    text = timeSinceLastMessage +" min";
                }else if (timeSinceLastMessage >= 60 &&  timeSinceLastMessage < 1440) {
                    if (timeSinceLastMessage/60 == 1){
                        text = " 1 hour" ;
                    }else{
                        text = (timeSinceLastMessage/60) + " hours" ;
                    }
                }else{
                    text = " > 1 day";
                }
                newResume.setTimeSinceLastMessage(text);

                if (lastMessage.getSourceUser().getId() == sourceAppUser.getId()){
                    newResume.setNbrUnseenMessages(messageRepository.countBySourceUserAndTargetUserAndIsSeenIsFalse(sourceAppUser,matchedAppUser));
                }
            }
            likeMatchResumeList.add(newResume);
        }
        return likeMatchResumeList ;
    }


}
