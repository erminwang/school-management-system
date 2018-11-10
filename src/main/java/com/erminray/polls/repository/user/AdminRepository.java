package com.erminray.polls.repository.user;

import com.erminray.polls.model.user.Administrator;
import com.erminray.polls.model.user.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRepository extends JpaRepository<Administrator, Long> {
    List<Administrator> findAll();
}
