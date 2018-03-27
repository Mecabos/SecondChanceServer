package com.esprit.secondchanceserver.model;

import com.esprit.secondchanceserver.enumeration.GenderType;
import com.esprit.secondchanceserver.enumeration.RelationshipType;
import com.esprit.secondchanceserver.enumeration.StatusType;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Filter {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    private int minAge = 18;
    private int maxAge = 100;
    private String country = "";
    private String town = "";
    private boolean hasChildren = true;
    private boolean livesAlone = true;
    @ElementCollection(targetClass = StatusType.class)
    @CollectionTable(name = "app_user_status_type_filter",
            joinColumns = @JoinColumn(name = "app_user_filter_id"))
    @Column(name = "status_type_id")
    protected List<StatusType> statusList;
    @ElementCollection(targetClass = RelationshipType.class)
    @CollectionTable(name = "app_user_relationship_type_filter",
            joinColumns = @JoinColumn(name = "app_user_filter_id"))
    @Column(name = "relationship_type_id")
    protected List<RelationshipType> relationshipTypeList;
    @OneToOne
    private AppUser appUser;

    public Filter() {
    }

    public Filter(GenderType gender, int minAge, int maxAge, String country, String town, boolean hasChildren, boolean livesAlone, List<StatusType> statusList, List<RelationshipType> relationshipTypeList, AppUser appUser) {
        this.gender = gender;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.country = country;
        this.town = town;
        this.hasChildren = hasChildren;
        this.livesAlone = livesAlone;
        this.statusList = statusList;
        this.relationshipTypeList = relationshipTypeList;
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

    public List<StatusType> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<StatusType> statusList) {
        this.statusList = statusList;
    }

    public List<RelationshipType> getRelationshipTypeList() {
        return relationshipTypeList;
    }

    public void setRelationshipTypeList(List<RelationshipType> relationshipTypeList) {
        this.relationshipTypeList = relationshipTypeList;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public boolean isLivesAlone() {
        return livesAlone;
    }

    public void setLivesAlone(boolean livesAlone) {
        this.livesAlone = livesAlone;
    }
}
