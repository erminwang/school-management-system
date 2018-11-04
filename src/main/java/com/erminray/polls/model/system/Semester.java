package com.erminray.polls.model.system;

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
    private Set<Course> courses;

    public Semester(){

    }

    public Semester(String name, @NotNull LocalDate startDate, @NotNull LocalDate endDate, Set<Course> courses) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courses = courses;
    }

    public Semester(String name, @NotNull LocalDate startDate, @NotNull LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
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
}
