package com.esprit.secondchanceserver.custom;

public class LikeMatchResume {

    private int id ;
    private String name;
    private String lastMessage;
    private int nbrUnseenMessages;

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
}
