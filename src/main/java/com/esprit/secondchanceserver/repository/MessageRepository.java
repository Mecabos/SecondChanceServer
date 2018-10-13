package com.esprit.secondchanceserver.repository;

import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Integer> {

    Message findById(int id);

    List<Message> findBySourceUserAndTargetUser(AppUser sourceAppUser, AppUser targetAppUser);

    @Query("select m from Message m where (m.sourceUser = ?1 and m.targetUser = ?2) or ( m.sourceUser = ?2 and m.targetUser = ?1) order by m.sendingDate")
    List<Message> getAppUserMessageList (AppUser sourceAppUser, AppUser targetAppUser);

    int countBySourceUserAndTargetUserAndIsSeenIsFalse (AppUser sourceAppUser, AppUser targeAppUser);

    @Query("select m from Message m where m.sendingDate = (select max(mm.sendingDate) from Message mm where (mm.sourceUser = ?1 and mm.targetUser = ?2) or (mm.sourceUser = ?2 and mm.targetUser = ?1)) and ((m.sourceUser = ?1 and m.targetUser = ?2) or (m.sourceUser = ?2 and m.targetUser = ?1)) ")
    Message getLastMessage(AppUser sourceAppUser, AppUser targeAppUser);


}
