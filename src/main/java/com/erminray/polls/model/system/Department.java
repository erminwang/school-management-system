package com.erminray.polls.model.system;

import com.erminray.polls.model.user.Instructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "departments")
public class Department {
    @Id
    @Size(max = 30)
    private String shortName;

    @NotBlank
    @Size(max = 60)
    private String fullName;



    @ManyToMany
    @JoinTable(name = "department_instructors",
        joinColumns = @JoinColumn(name = "department_name"),
        inverseJoinColumns = @JoinColumn(name = "instructor_id"))
    private Set<Instructor> instructors;

    @OneToMany(
    mappedBy = "department",
    cascade = CascadeType.ALL,
    fetch = FetchType.EAGER,
    //when set choice to null or to another Address object -> disconnecting relationship, this will remove the choice objects
    orphanRemoval = true
    )
    private Set<CourseType> courses;

//    @OneToOne
//    @JoinColumn(name = "graduation_requirement")
//    private GraduationRequirement graduationRequirement;

//    GraduationRequirement graduationRequirement;

    public Department() {

    }

    public Department(@NotBlank @Size(max = 60) String fullName, @NotBlank @Size(max = 30) String shortName) {
        this.fullName = fullName;
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Set<CourseType> getCourses() {
        return courses;
    }

    public void setCourses(Set<CourseType> courses) {
        this.courses = courses;
    }

//    public GraduationRequirement getGraduationRequirement() {
//        return graduationRequirement;
//    }
//
//    public void setGraduationRequirement(GraduationRequirement graduationRequirement) {
//        this.graduationRequirement = graduationRequirement;
//    }
}
