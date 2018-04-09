package com.esprit.secondchanceserver.service;

import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.Message;

import java.util.List;

public interface MessageService {

    void saveNewMessage(Message newMessage);
    Message findMessageById(int id);
    List<Message> getMessageList (Message messageToGetSourceAndTargetFrom);
    void markMessageAsSeen (Message messageToMarkAsSeen);
}
