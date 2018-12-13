package com.erminray.polls.controller.system;

import com.erminray.polls.model.system.Course;
import com.erminray.polls.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> getCourses(@RequestParam(value = "courseLevel", required = false) Integer courseLevel,
                                   @RequestParam(value = "compare", required = false) String compare) throws Exception{
        if (courseLevel == null) return courseService.getAllCourses();
        if (compare == null) return courseService.getAllCoursesByCourseLevel(courseLevel,"eq");
        return courseService.getAllCoursesByCourseLevel(courseLevel,compare);
    }

    @GetMapping("/{courseId}")
    public Course getCourseById(@PathVariable(value = "courseId") Long courseId) {
        return courseService.getCourseById(courseId);
    }

}
