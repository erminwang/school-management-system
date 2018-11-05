package com.erminray.polls.model.system;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "courses_type")
public class CourseType {
    @Id
    @Column(unique = true)
    private String name;

    @OneToMany(
        mappedBy = "courseType",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        //when set choice to null or to another Address object -> disconnecting relationship, this will remove the choice objects
        orphanRemoval = true
    )
    private Set<Course> courses;

    @ManyToOne
    @JoinColumn(name = "department_name", nullable = false)
    private Department department;

    //parent is the the course
    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name="course_dependencies",
        joinColumns={@JoinColumn(name="successor_id")},
        inverseJoinColumns={@JoinColumn(name="prerequisite_id")})
    private Set<CourseType> successor;
    @ManyToMany
    @JoinTable(name = "course_dependencies",
        joinColumns={@JoinColumn(name="prerequisite_id")},
        inverseJoinColumns={@JoinColumn(name="successor_id")})
    private Set<CourseType> prerequisite;

    private int courseNumber;

    @Size(max = 60)
    private String title;

    @Size(max = 500)
    private String description;

    //enum
    @Enumerated(EnumType.STRING)
    private BreadthType breadthType;

    private int unit;

    public CourseType() {

    }

    public CourseType(String name, Set<Course> courses, Department department, Set<CourseType> successor, Set<CourseType> prerequisite, @NotBlank @Size(max = 999) int courseNumber, @NotBlank @Size(max = 60) String title, @NotBlank @Size(max = 500) String description, @NotBlank BreadthType breadthType, @Max(10) @Min(0) int unit) {
        this.name = name;
        this.courses = courses;
        this.department = department;
        this.successor = successor;
        this.prerequisite = prerequisite;
        this.courseNumber = courseNumber;
        this.title = title;
        this.description = description;
        this.breadthType = breadthType;
        this.unit = unit;
    }

    public CourseType(String name, Department department, @NotBlank @Size(max = 999) int courseNumber, @NotBlank @Size(max = 60) String title, @NotBlank @Size(max = 500) String description, @NotBlank BreadthType breadthType, @Max(10) @Min(0) int unit) {
        this.name = name;
        this.department = department;
        this.courseNumber = courseNumber;
        this.title = title;
        this.description = description;
        this.breadthType = breadthType;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<CourseType> getSuccessors() {
        return successor;
    }

    public void setSuccessors(Set<CourseType> successors) {
        this.successor = successors;
    }

    public Set<CourseType> getPrerequisite() {
        return prerequisite;
    }

    public void setPrerequisite(Set<CourseType> prerequisite) {
        this.prerequisite = prerequisite;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BreadthType getBreadthType() {
        return breadthType;
    }

    public void setBreadthType(BreadthType breadthType) {
        this.breadthType = breadthType;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }
}
