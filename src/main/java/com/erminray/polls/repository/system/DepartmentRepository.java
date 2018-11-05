package com.erminray.polls.repository.system;

import com.erminray.polls.model.system.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface DepartmentRepository  extends JpaRepository<Department, Long> {
    List<Department> findAll();
}
