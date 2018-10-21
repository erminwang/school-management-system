package com.erminray.polls.model.user;

import com.erminray.polls.model.User;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student extends User {
    private String typeStu;
//    private Set<Course> currentCourses;
//    private Set<CourseResult> coursesTaken;
//    private Department department;

    public Student() {

    }
    public Student(String firstName, String lastName, String gender, String username, String email, String password, String typeStu) {
        super(firstName, lastName, gender, username, email, password);
        this.typeStu = typeStu;
    }

    public String getTypeStu() {
        return typeStu;
    }

    public void setTypeStu(String typeStu) {
        this.typeStu = typeStu;
    }
}
