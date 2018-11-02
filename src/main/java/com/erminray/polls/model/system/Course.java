package com.erminray.polls.model.system;

import com.erminray.polls.model.user.Instructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_type")
    private CourseType courseType;

    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name="course_instructors",
        joinColumns={@JoinColumn(name="course_id")},
        inverseJoinColumns={@JoinColumn(name="instructor_id")})
    private Set<Instructor> instructor;

    @Max(1000)
    @Min(0)
    private int courseSize;

    @Max(60)
    @Min(0)
    private int waitlistSize;

    @NotBlank
    @Size(max = 15)
    private String semester;

    @NotBlank
    @ElementCollection
    @CollectionTable(name="schedules", joinColumns=@JoinColumn(name="course_id"))
    @Column(name="schedule")
    private List<String> schedule;

    public Course(@Max(1000) @Min(0) int courseSize, @Max(60) @Min(0) int waitlistSize, @NotBlank @Size(max = 15) String semester, @NotBlank List<String> schedule) {
        this.courseSize = courseSize;
        this.waitlistSize = waitlistSize;
        this.semester = semester;
        this.schedule = schedule;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCourseSize() {
        return courseSize;
    }

    public void setCourseSize(int courseSize) {
        this.courseSize = courseSize;
    }

    public int getWaitlistSize() {
        return waitlistSize;
    }

    public void setWaitlistSize(int waitlistSize) {
        this.waitlistSize = waitlistSize;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public List<String> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<String> schedule) {
        this.schedule = schedule;
    }

}
