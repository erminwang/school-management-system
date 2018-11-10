package com.erminray.polls.repository.old;

import com.erminray.polls.model.user.Gender;
import com.erminray.polls.model.user.Student;
import com.erminray.polls.model.user.User;
import com.erminray.polls.model.user.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    List<User> findByIdIn(List<Long> userIds);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    List<User> findByGender(Gender gender);

    Page<User> findByUserTypeIn(List<UserType> userTypes, Pageable pageable);

    Page<User> findByUsernameInAndUserTypeIn(List<String> usernames, List<UserType> userTypes, Pageable pageable);
}
