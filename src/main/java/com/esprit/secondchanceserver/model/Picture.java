package com.esprit.secondchanceserver.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Picture {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime uploadDate;
    private String name;
    private int position;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private AppUser appUser;

    @Transient
    private String link ;

    public Picture() {
    }

    public Picture(LocalDateTime uploadDate, String name, int position, AppUser appUser) {
        this.uploadDate = uploadDate;
        this.name = name;
        this.position = position;
        this.appUser = appUser;
    }

    public Picture(int position, AppUser appUser) {
        this.position = position;
        this.appUser = appUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
