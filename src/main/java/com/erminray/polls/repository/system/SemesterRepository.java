package com.erminray.polls.repository.system;

import com.erminray.polls.model.system.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, String> {
    List<Semester> findAll();
}
