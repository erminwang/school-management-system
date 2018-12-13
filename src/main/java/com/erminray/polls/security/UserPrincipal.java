package com.erminray.polls.security;

import com.erminray.polls.model.user.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {
    private Long id;

    private String firstName;

    private String lastName;

    private Gender gender;

    private String username;

    private PrimaryUserType primaryUserType;

    private SecondaryUserType secondaryUserType;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, String firstName, String lastName, Gender gender, String username,
                         PrimaryUserType primaryUserType, SecondaryUserType secondaryUserType, String email,
                         String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.username = username;
        this.email = email;
        this.primaryUserType = primaryUserType;
        this.secondaryUserType = secondaryUserType;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
            new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        SecondaryUserType secondaryUserType;

        if(user.getClass() == Instructor.class) {
            secondaryUserType = ((Instructor) user).getSecondaryUserType();
        } else if (user.getClass() == Administrator.class) {
            secondaryUserType = ((Administrator) user).getSecondaryUserType();
        } else {
            secondaryUserType = null;
        }

        return new UserPrincipal(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getGender(),
            user.getUsername(),
            user.getPrimaryUserType(),
            secondaryUserType,
            user.getEmail(),
            user.getPassword(),
            authorities
        );
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public PrimaryUserType getPrimaryUserType() {
        return primaryUserType;
    }

    public SecondaryUserType getSecondaryUserType() {
        return secondaryUserType;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
