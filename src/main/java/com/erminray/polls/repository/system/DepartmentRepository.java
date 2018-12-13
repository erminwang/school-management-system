package com.erminray.polls.repository.system;

import com.erminray.polls.model.system.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface DepartmentRepository  extends JpaRepository<Department, Long> {
    List<Department> findAll();
}
