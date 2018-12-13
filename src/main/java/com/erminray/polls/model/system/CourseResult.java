package com.erminray.polls.model.system;

import com.erminray.polls.model.user.Student;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "courseResults")
public class CourseResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Enumerated(EnumType.ORDINAL)
    private Grade grade;

}
