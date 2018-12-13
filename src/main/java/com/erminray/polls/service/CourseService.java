package com.erminray.polls.service;

import com.erminray.polls.model.system.Course;
import com.erminray.polls.repository.system.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id){
        return courseRepository.findCourseById(id);
    }

    public List<Course> getAllCoursesByCourseLevel(int level, String compareMethod) throws Exception {
        List<Course> courses = this.getAllCourses();
        List<Course> coursesReturned = new ArrayList<>();
        if (compareMethod.equals("eq")){
            for (Course course : courses) {
                if(course.getCourseType().getCourseNumber() == level){
                    coursesReturned.add(course);
                }
            }
        } else if (compareMethod.equals("gt")){
            for (Course course : courses) {
                if(course.getCourseType().getCourseNumber() > level){
                    coursesReturned.add(course);
                }
            }
        } else if (compareMethod.equals("ge")){
            for (Course course : courses) {
                if(course.getCourseType().getCourseNumber() >= level){
                    coursesReturned.add(course);
                }
            }
        } else if (compareMethod.equals("lt")){
            for (Course course : courses) {
                if(course.getCourseType().getCourseNumber() < level){
                    coursesReturned.add(course);
                }
            }
        } else if (compareMethod.equals("le")){
            for (Course course : courses) {
                if(course.getCourseType().getCourseNumber() <= level){
                    coursesReturned.add(course);
                }
            }
        } else {
            throw new Exception("Compare Method is invalid");
        }

        return coursesReturned;
    }

}
