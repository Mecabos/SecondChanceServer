package com.esprit.secondchanceserver.controller;

import com.esprit.secondchanceserver.exceptions.NotFoundException;
import com.esprit.secondchanceserver.model.AppUser;
import com.esprit.secondchanceserver.model.Message;
import com.esprit.secondchanceserver.service.AppUserService;
import com.esprit.secondchanceserver.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private MessageService messageService;

    @RequestMapping(method = RequestMethod.POST, value = "/user/message/saveNewMessage")
    public String saveNewMessage(@RequestBody Message newMessage) throws NotFoundException {
        AppUser sourceUser = appUserService.findUserById(newMessage.getSourceUser().getId());
        AppUser targetUser = appUserService.findUserById(newMessage.getTargetUser().getId());
        if (sourceUser != null && targetUser != null) {
            messageService.saveNewMessage(newMessage);
            return "User " + sourceUser.getName() + " Sent \"" + newMessage.getText() + "\" To " + targetUser.getName();
        } else {
            String error = "";
            if (sourceUser == null)
                error += "AppUser of Id : " + newMessage.getSourceUser().getId() + " Not found ! ";
            if (targetUser == null)
                error += "AppUser of Id : " + newMessage.getTargetUser().getId() + " Not found ! ";
            throw new NotFoundException(error);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/message/markMessageAsSeen")
    public String markMessageAsSeen(@RequestBody Message seenMessage) throws NotFoundException {
        Message messageToMarkAsSeen = messageService.findMessageById(seenMessage.getId());
        if (messageToMarkAsSeen != null) {
            messageService.markMessageAsSeen(messageToMarkAsSeen);
            return "User " + messageToMarkAsSeen.getTargetUser().getName() + " Saw  " + messageToMarkAsSeen.getSourceUser().getName() + "'s message \""+ messageToMarkAsSeen.getText() + "\"";
        } else {
            throw new NotFoundException("Message of Id : " + seenMessage.getId() + " Not found ! ");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/message/markMessageListAsSeen")
    public String markMessageListAsSeen(@RequestBody Message messageToGetSourceAndTargetFrom) throws NotFoundException {
        AppUser sourceUser = appUserService.findUserById(messageToGetSourceAndTargetFrom.getSourceUser().getId());
        AppUser targetUser = appUserService.findUserById(messageToGetSourceAndTargetFrom.getTargetUser().getId());
        if (sourceUser != null && targetUser != null) {
            messageService.seeMessages(messageToGetSourceAndTargetFrom);
            return targetUser.getName() + " saw " + sourceUser.getName() + "'s messages";
        } else {
            String error = "";
            if (sourceUser == null)
                error += "AppUser of Id : " + messageToGetSourceAndTargetFrom.getSourceUser().getId() + " Not found ! ";
            if (targetUser == null)
                error += "AppUser of Id : " + messageToGetSourceAndTargetFrom.getTargetUser().getId() + " Not found ! ";
            throw new NotFoundException(error);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/message/getMessageList")
    public List<Message> getMessageList(@RequestBody Message messageToGetSourceAndTargetFrom) throws NotFoundException {
        AppUser sourceUser = appUserService.findUserById(messageToGetSourceAndTargetFrom.getSourceUser().getId());
        AppUser targetUser = appUserService.findUserById(messageToGetSourceAndTargetFrom.getTargetUser().getId());
        if (sourceUser != null && targetUser != null) {
            return messageService.getMessageList(messageToGetSourceAndTargetFrom);
        } else {
            String error = "";
            if (sourceUser == null)
                error += "AppUser of Id : " + messageToGetSourceAndTargetFrom.getSourceUser().getId() + " Not found ! ";
            if (targetUser == null)
                error += "AppUser of Id : " + messageToGetSourceAndTargetFrom.getTargetUser().getId() + " Not found ! ";
            throw new NotFoundException(error);
        }
    }

}
