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
    @NotBlank
    @JoinColumn(name = "department_id", nullable = false)
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

    @NotBlank
    @Size(max = 999)
    private int courseNumber;

    @NotBlank
    @Size(max = 60)
    private String title;

    @NotBlank
    @Size(max = 500)
    private String description;

    //enum
    @NotBlank
    @Enumerated(EnumType.STRING)
    private BreadthType breadthType;

    @Max(10)
    @Min(0)
    private int unit;

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
