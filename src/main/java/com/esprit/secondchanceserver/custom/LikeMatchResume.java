package com.esprit.secondchanceserver.custom;

import com.esprit.secondchanceserver.model.Picture;

public class LikeMatchResume {

    private int id ;
    private String name;
    private String lastMessage;
    private String timeSinceLastMessage;
    private boolean lastMessageSender = false;
    private int nbrUnseenMessages;
    private Picture profilePicture;

    public LikeMatchResume() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeSinceLastMessage() {
        return timeSinceLastMessage;
    }

    public void setTimeSinceLastMessage(String timeSinceLastMessage) {
        this.timeSinceLastMessage = timeSinceLastMessage;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getNbrUnseenMessages() {
        return nbrUnseenMessages;
    }

    public void setNbrUnseenMessages(int nbrUnseenMessages) {
        this.nbrUnseenMessages = nbrUnseenMessages;
    }

    public boolean isLastMessageSender() {
        return lastMessageSender;
    }

    public void setLastMessageSender(boolean lastMessageSender) {
        this.lastMessageSender = lastMessageSender;
    }

    public Picture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Picture profilePicture) {
        this.profilePicture = profilePicture;
    }
}
