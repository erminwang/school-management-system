package com.erminray.polls.controller.system;

import com.erminray.polls.model.system.Course;
import com.erminray.polls.repository.system.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/")
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

}
