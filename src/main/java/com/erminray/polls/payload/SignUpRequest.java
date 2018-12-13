package com.erminray.polls.payload;

import com.erminray.polls.model.user.Gender;

import javax.validation.constraints.*;

public class SignUpRequest {
    @NotBlank
    @Size(min = 2, max = 40)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 40)
    private String lastName;

    @NotBlank
    private String gender;

    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    @NotBlank
    private String userType;

    private String typeStu;

    private String typeInst;

    private String typeAdmin;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public String getTypeStu() {
        return typeStu;
    }

    public String getTypeInst() {
        return typeInst;
    }

    public String getTypeAdmin() {
        return typeAdmin;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setTypeStu(String typeStu) {
        this.typeStu = typeStu;
    }

    public void setTypeInst(String typeInst) {
        this.typeInst = typeInst;
    }

    public void setTypeAdmin(String typeAdmin) {
        this.typeAdmin = typeAdmin;
    }
}