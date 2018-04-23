package com.esprit.secondchanceserver.service;

import com.esprit.secondchanceserver.Util.DateUtil;
import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.Message;
import com.esprit.secondchanceserver.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void saveNewMessage(Message newMessage) {
        newMessage.setSendingDate(DateUtil.getCurrentDateTime());
        messageRepository.save(newMessage);
    }

    @Override
    public Message findMessageById(int id) {
        return messageRepository.findById(id);
    }

    @Override
    public List<Message> getMessageList(Message messageToGetSourceAndTargetFrom) {
        List<Message> messageList = messageRepository.getAppUserMessageList(messageToGetSourceAndTargetFrom.getSourceUser(),messageToGetSourceAndTargetFrom.getTargetUser());
        for (Message currentMessage : messageList){
            if (currentMessage.getSourceUser().getId() == messageToGetSourceAndTargetFrom.getSourceUser().getId()) {
                currentMessage.setSender(true);
            } else {
                currentMessage.setSender(false);
            }
        }
        return messageList;
    }

    @Override
    public void markMessageAsSeen(Message messageToMarkAsSeen) {
        messageToMarkAsSeen.setSeen(true);
        messageToMarkAsSeen.setSeeingDate(DateUtil.getCurrentDateTime());
        messageRepository.save(messageToMarkAsSeen);
    }

    @Override
    public void seeMessages(Message messageToGetSourceAndTargetFrom) {
        List<Message> messageListToSee = messageRepository.findBySourceUserAndTargetUser(messageToGetSourceAndTargetFrom.getSourceUser(),messageToGetSourceAndTargetFrom.getTargetUser());
        for (Message message : messageListToSee){
            markMessageAsSeen(message);
        }
    }
}
