package com.erminray.polls.payload;

import com.erminray.polls.model.user.*;

import java.time.Instant;
import java.util.Set;

public class UserProfile {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private Instant joinedAt;
    private Instant updatedAt;
    private Set<Role> roles;
    private PrimaryUserType primaryUserType;
    private SecondaryUserType secondaryUserType;
    private Long pollCount;
    private Long voteCount;

    public UserProfile(Long id, String username, String firstName, String lastName, String email, Gender gender,
                       Instant joinedAt, Instant updatedAt, Set<Role> roles, PrimaryUserType primaryUserType, Long pollCount, Long voteCount) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.joinedAt = joinedAt;
        this.updatedAt = updatedAt;
        this.roles = roles;
        this.primaryUserType = primaryUserType;
        this.pollCount = pollCount;
        this.voteCount = voteCount;
    }

    public UserProfile(User user, long pollCount, long voteCount){
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.joinedAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.roles = user.getRoles();
        this.primaryUserType = user.getPrimaryUserType();
        this.pollCount = pollCount;
        if(user.getClass() == Instructor.class) {
            this.secondaryUserType = ((Instructor) user).getSecondaryUserType();
        } else if (user.getClass() == Administrator.class) {
            this.secondaryUserType = ((Administrator) user).getSecondaryUserType();
        }
        this.voteCount = voteCount;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Long getPollCount() {
        return pollCount;
    }

    public void setPollCount(Long pollCount) {
        this.pollCount = pollCount;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
