package com.erminray.polls.model.user;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "instructors")
public class Instructor extends User {
    private String typeInst;

//    private Set<course> coursesOwned;
    public Instructor() {

    }

    public Instructor(String firstName, String lastName, String gender, String username, String email, String password, String typeInst) {
        super(firstName, lastName, gender, username, email, password);
        this.typeInst = typeInst;
    }

    public String getTypeInst() {
        return typeInst;
    }

    public void setTypeInst(String typeInst) {
        this.typeInst = typeInst;
    }
}
