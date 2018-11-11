package com.erminray.polls.repository.system;

import com.erminray.polls.model.system.Course;
import com.erminray.polls.model.system.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAll();

    Course findCourseByCourseType(CourseType courseType);

    Course findCourseById(long id);
}