package com.esprit.secondchanceserver.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.esprit.secondchanceserver.enumeration.GenderType;
import com.esprit.secondchanceserver.enumeration.StatusType;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

@Entity
@Table(name = "appUser")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "appUser_id")
    private int id;
    @Column(name = "email")
    /*@Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")*/
    private String email;
    @Column(name = "password")
    /*@Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")*/
    @Transient
    private String password;
    @Column(name = "name")
    /*@NotEmpty(message = "*Please provide your name")*/
    private String name;
    @Column(name = "last_name")
    /*@NotEmpty(message = "*Please provide your last name")*/
    private String lastName;
    @Column(name = "active")
    private int active;
    private String description = "";
    private String university = "";
    private String profession = "";
    private int numberOfChildren;
    private int age = 20;
    @Enumerated(EnumType.STRING)
    private GenderType gender = GenderType.Man;
    @Enumerated(EnumType.STRING)
    private StatusType status;
    private String country = "";
    private String town = "";
    private boolean livesAlone = true;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "appUser_role", joinColumns = @JoinColumn(name = "appUser_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @OneToMany(mappedBy = "sourceUser", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<LikeMatch> likeMatches = new ArrayList<LikeMatch>();
    /*@OneToMany(mappedBy = "sourceUser", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Notation> createdNotations = new ArrayList<Notation>();
    @OneToMany(mappedBy = "targetUser", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Notation> receivedNotations = new ArrayList<Notation>();*/
    @OneToMany(mappedBy = "sourceUser", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Message> sentMessages = new ArrayList<Message>();
    @OneToMany(mappedBy = "targetUser", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Message> receivedMessages = new ArrayList<Message>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Picture> pictures = new ArrayList<Picture>();

    public AppUser() {
    }

    public AppUser(@Email(message = "*Please provide a valid Email") @NotEmpty(message = "*Please provide an email") String email, @Length(min = 5, message = "*Your password must have at least 5 characters") @NotEmpty(message = "*Please provide your password") String password, @NotEmpty(message = "*Please provide your name") String name, @NotEmpty(message = "*Please provide your last name") String lastName, int active, String description, String university, String profession, int numberOfChildren, int age, GenderType gender, StatusType status, String country, String town, boolean livesAlone, Set<Role> roles, List<LikeMatch> likeMatches, List<Notation> createdNotations, List<Notation> receivedNotations, List<Message> sentMessages, List<Message> receivedMessages, List<Picture> pictures) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.active = active;
        this.description = description;
        this.university = university;
        this.profession = profession;
        this.numberOfChildren = numberOfChildren;
        this.age = age;
        this.gender = gender;
        this.status = status;
        this.country = country;
        this.town = town;
        this.livesAlone = livesAlone;
        this.roles = roles;
        this.likeMatches = likeMatches;
        /*this.createdNotations = createdNotations;
        this.receivedNotations = receivedNotations;*/
        this.sentMessages = sentMessages;
        this.receivedMessages = receivedMessages;
        this.pictures = pictures;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<LikeMatch> getMatches() {
        return likeMatches;
    }

    public void setMatches(List<LikeMatch> matches) {
        this.likeMatches = matches;
    }

    /*public List<Notation> getCreatedNotations() {
        return createdNotations;
    }

    public void setCreatedNotations(List<Notation> createdNotations) {
        this.createdNotations = createdNotations;
    }

    public List<Notation> getReceivedNotations() {
        return receivedNotations;
    }

    public void setReceivedNotations(List<Notation> receivedNotations) {
        this.receivedNotations = receivedNotations;
    }*/

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public boolean isLivesAlone() {
        return livesAlone;
    }

    public void setLivesAlone(boolean livesAlone) {
        this.livesAlone = livesAlone;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", active=" + active +
                ", description='" + description + '\'' +
                ", university='" + university + '\'' +
                ", profession='" + profession + '\'' +
                ", numberOfChildren=" + numberOfChildren +
                ", age=" + age +
                ", gender=" + gender +
                ", status=" + status +
                ", country='" + country + '\'' +
                ", town='" + town + '\'' +
                ", livesAlone=" + livesAlone +
                '}';
    }
}