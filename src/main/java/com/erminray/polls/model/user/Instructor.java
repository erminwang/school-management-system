package com.erminray.polls.model.user;

import com.erminray.polls.model.system.Course;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "instructors")
public class Instructor extends User {
    private String typeInst;

    private SecondaryUserType secondaryUserType;

    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name="instructor_courses",
            joinColumns={@JoinColumn(name="instructor_id")},
            inverseJoinColumns={@JoinColumn(name="course_id")})
    @JsonManagedReference
    private Set<Course> coursesOwned;

    public Instructor() {

    }

    public Instructor(String firstName, String lastName, String gender, String username, String email, String password, String typeInst) {
        super(firstName, lastName, gender, username, email, password);
        this.userType = UserType.INSTRUCTOR;
        this.secondaryUserType = SecondaryUserType.NOT_ASSIGNED;
        this.typeInst = typeInst;
    }

    public String getTypeInst() {
        return typeInst;
    }

    public void setTypeInst(String typeInst) {
        this.typeInst = typeInst;
    }

    public SecondaryUserType getSecondaryUserType() {
        return secondaryUserType;
    }

    public void setSecondaryUserType(SecondaryUserType secondaryUserType) {
        this.secondaryUserType = secondaryUserType;
    }

    public Set<Course> getCoursesOwned() {
        return coursesOwned;
    }

    public void setCoursesOwned(Set<Course> coursesOwned) {
        this.coursesOwned = coursesOwned;
    }
}
