package com.erminray.polls.repository.user;

import com.erminray.polls.model.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAll();
}
