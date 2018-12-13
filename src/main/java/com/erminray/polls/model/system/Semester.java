package com.erminray.polls.model.system;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "semesters")
public class Semester {
    @Id
    private String name;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;

    @OneToMany(
        mappedBy = "semester",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @JsonBackReference
    private Set<Course> courses;

    @NonNull
    private LocalDate enrollmentStartDate;

    @NonNull
    private LocalDate enrollmentEndDate;

    public Semester(){

    }

    public Semester(String name, @NotNull LocalDate startDate, @NotNull LocalDate endDate, Set<Course> courses,
                    @NonNull LocalDate enrollmentStartDate, @NonNull LocalDate enrollmentEndDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courses = courses;
        this.enrollmentStartDate = enrollmentStartDate;
        this.enrollmentEndDate = enrollmentEndDate;
    }

    public Semester(String name, @NotNull LocalDate startDate, @NotNull LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.enrollmentStartDate = startDate.minusMonths(1);
        this.enrollmentEndDate = startDate.plusMonths(1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public LocalDate getEnrollmentStartDate() {
        return enrollmentStartDate;
    }

    public void setEnrollmentStartDate(LocalDate enrollmentStartDate) {
        this.enrollmentStartDate = enrollmentStartDate;
    }

    public LocalDate getEnrollmentEndDate() {
        return enrollmentEndDate;
    }

    public void setEnrollmentEndDate(LocalDate enrollmentEndDate) {
        this.enrollmentEndDate = enrollmentEndDate;
    }
}
