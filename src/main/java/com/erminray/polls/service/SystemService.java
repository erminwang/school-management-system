package com.erminray.polls.service;

import com.erminray.polls.model.system.CourseType;
import com.erminray.polls.model.system.Semester;
import com.erminray.polls.repository.system.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemService {

    @Autowired
    SemesterRepository semesterRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseTypeRepository courseTypeRepository;

    @Autowired
    CourseResultRepository courseResultRepository;

    public List<Semester> getSemesters() {
        return semesterRepository.findAll();
    }

    public Semester createSemester() {
        return new Semester();
    }
}
