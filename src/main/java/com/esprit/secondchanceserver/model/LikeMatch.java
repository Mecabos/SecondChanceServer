package com.esprit.secondchanceserver.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LikeMatch {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime matchDate;
    @ManyToOne
    @JoinColumn(name = "source_user_id")
    @JsonIgnore
    private AppUser sourceUser;
    @ManyToOne
    @JoinColumn(name = "target_user_id")
    @JsonIgnore
    private AppUser targetUser;

    public LikeMatch() {
    }

    public LikeMatch(LocalDateTime matchDate, AppUser sourceUser, AppUser targetUser) {
        this.matchDate = matchDate;
        this.sourceUser = sourceUser;
        this.targetUser = targetUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDateTime matchDate) {
        this.matchDate = matchDate;
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
