package com.erminray.polls.model.user;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admins")
public class Administrator extends User {
    private String typeAdmin;

    public Administrator() {

    }

    public Administrator(String firstName, String lastName, String gender, String username, String email, String password, String typeAdmin) {
        super(firstName, lastName, gender, username, email, password);
        this.userType = UserType.ADMIN;
        this.typeAdmin = typeAdmin;
    }

    public String getTypeAdmin() {
        return typeAdmin;
    }

    public void setTypeAdmin(String typeAdmin) {
        this.typeAdmin = typeAdmin;
    }
}
