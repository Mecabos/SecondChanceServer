package com.esprit.secondchanceserver.service;

import com.esprit.secondchanceserver.Util.DateUtil;
import com.esprit.secondchanceserver.enumeration.DurationType;
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

            int timeSinceMessageSent = (int)DateUtil.getDurationBetween(currentMessage.getSendingDate(), DateUtil.getCurrentDateTime(), DurationType.Minutes);
            String text = "";
            if (timeSinceMessageSent <= 0){
                text = " < 1 min";
            }else if(timeSinceMessageSent > 0 && timeSinceMessageSent < 60){
                text = timeSinceMessageSent +" min";
            }else if (timeSinceMessageSent >= 60 &&  timeSinceMessageSent < 1440) {
                if (timeSinceMessageSent/60 == 1){
                    text = " 1 hour" ;
                }else{
                    text = (timeSinceMessageSent/60) + " hours" ;
                }
            }else{
                if (timeSinceMessageSent/ 1140 == 1){
                    text = "1 day";
                }else{
                    text = (timeSinceMessageSent/ 1140) + " days";
                }
            }
            currentMessage.setTimeSinceSent(text);
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
