package com.esprit.secondchanceserver.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime notationDate;
    private int value;
    @ManyToOne
    @JoinColumn(name = "source_user_id")
    private AppUser sourceUser;
    @ManyToOne
    @JoinColumn(name = "target_user_id")
    private AppUser targetUser;

    public Notation() {
    }

    public Notation(LocalDateTime notationDate, int value, AppUser sourceUser, AppUser targetUser) {
        this.notationDate = notationDate;
        this.value = value;
        this.sourceUser = sourceUser;
        this.targetUser = targetUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getNotationDate() {
        return notationDate;
    }

    public void setNotationDate(LocalDateTime notationDate) {
        this.notationDate = notationDate;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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

    @Override
    public String toString() {
        return "Notation{" +
                "id=" + id +
                ", notationDate=" + notationDate +
                ", value=" + value +
                ", sourceUser=" + sourceUser +
                ", targetUser=" + targetUser +
                '}';
    }
}
