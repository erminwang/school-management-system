package com.erminray.polls.repository.system;

import com.erminray.polls.model.system.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<Semester, String> {

}
