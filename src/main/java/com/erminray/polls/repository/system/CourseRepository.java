package com.erminray.polls.repository.system;

import com.erminray.polls.model.system.Course;
import com.erminray.polls.model.system.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAll();

    Course findCourseByCourseType(CourseType courseType);

    Course findCourseById(long id);
}
