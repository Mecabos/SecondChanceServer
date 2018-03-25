package com.esprit.secondchanceserver.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime sendingDate;
    private String text;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime seeingDate;
    private boolean isSeen;@ManyToOne
    @JoinColumn(name = "source_user_id")
    @JsonIgnore
    private AppUser sourceUser;

    @ManyToOne
    @JoinColumn(name = "target_user_id")
    @JsonIgnore
    private AppUser targetUser;

    public Message() {
    }

    public Message(LocalDateTime sendingDate, String text, LocalDateTime seeingDate, boolean isSeen) {
        this.sendingDate = sendingDate;
        this.text = text;
        this.seeingDate = seeingDate;
        this.isSeen = isSeen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(LocalDateTime sendingDate) {
        this.sendingDate = sendingDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getSeeingDate() {
        return seeingDate;
    }

    public void setSeeingDate(LocalDateTime seeingDate) {
        this.seeingDate = seeingDate;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public AppUser getSourceUser() {
        return sourceUser;
    }

    public void setSourceUser(AppUser sourceUser) {
        this.sourceUser = sourceUser;
    }

    public AppUser getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(AppUser targetUser) {
        this.targetUser = targetUser;
    }
}
