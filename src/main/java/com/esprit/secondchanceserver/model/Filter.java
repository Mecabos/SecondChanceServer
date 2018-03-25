package com.esprit.secondchanceserver.model;

import com.esprit.secondchanceserver.enumeration.GenderType;
import com.esprit.secondchanceserver.enumeration.RelationshipType;
import com.esprit.secondchanceserver.enumeration.StatusType;

import javax.persistence.*;

@Entity
public class Filter {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    private int minAge;
    private int maxAge;
    private String country;
    private String town;
    private boolean hasChildren;
    @Enumerated(EnumType.STRING)
    private StatusType status;
    @Enumerated(EnumType.STRING)
    private RelationshipType relationshipType;
    @OneToOne
    private AppUser appUser;

    public Filter() {
    }

    public Filter(GenderType gender, int minAge, int maxAge, String country, String town, boolean hasChildren, StatusType status, RelationshipType relationshipType, AppUser appUser) {
        this.gender = gender;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.country = country;
        this.town = town;
        this.hasChildren = hasChildren;
        this.status = status;
        this.relationshipType = relationshipType;
        this.appUser = appUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
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

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
