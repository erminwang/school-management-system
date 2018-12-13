package com.erminray.polls.repository.user;

import com.erminray.polls.model.user.Administrator;
import com.erminray.polls.model.user.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Administrator, Long> {
    List<Administrator> findAll();
}
