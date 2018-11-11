package com.erminray.polls.repository.system;

import com.erminray.polls.model.system.CourseResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseResultRepository  extends JpaRepository<CourseResult, Long> {

}
