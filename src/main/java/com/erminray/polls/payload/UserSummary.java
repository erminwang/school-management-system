package com.erminray.polls.payload;

import com.erminray.polls.model.user.PrimaryUserType;
import com.erminray.polls.model.user.SecondaryUserType;
import com.erminray.polls.security.UserPrincipal;

public class UserSummary {
    private Long id;
    private String username;
    private String name;
    private PrimaryUserType primaryUserType;
    private SecondaryUserType secondaryUserType;

    public UserSummary(Long id, String username, String name, PrimaryUserType primaryUserType, SecondaryUserType secondaryUserType) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.primaryUserType = primaryUserType;
        this.secondaryUserType = secondaryUserType;
    }

    public UserSummary(UserPrincipal userPrincipal) {
        this.id = userPrincipal.getId();
        this.username = userPrincipal.getUsername();
        this.name = userPrincipal.getFirstName() + userPrincipal.getLastName();
        this.primaryUserType = userPrincipal.getPrimaryUserType();
        this.secondaryUserType = userPrincipal.getSecondaryUserType();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PrimaryUserType getPrimaryUserType() {
        return primaryUserType;
    }

    public void setPrimaryUserType(PrimaryUserType primaryUserType) {
        this.primaryUserType = primaryUserType;
    }

    public SecondaryUserType getSecondaryUserType() {
        return secondaryUserType;
    }

    public void setSecondaryUserType(SecondaryUserType secondaryUserType) {
        this.secondaryUserType = secondaryUserType;
    }
}