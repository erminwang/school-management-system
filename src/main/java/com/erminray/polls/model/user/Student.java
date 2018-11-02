package com.erminray.polls.model.user;

import com.erminray.polls.model.User;
import com.erminray.polls.model.system.Course;
import com.erminray.polls.model.system.CourseResult;
import com.erminray.polls.model.system.Department;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends User {
    private String typeStu;

//    @ManyToMany
//    private Set<Course> currentCourses;

    @OneToMany(
        mappedBy = "student",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<CourseResult> coursesTaken;

    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

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
