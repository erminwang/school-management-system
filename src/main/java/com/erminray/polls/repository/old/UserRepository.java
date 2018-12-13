package com.erminray.polls.repository.old;

import com.erminray.polls.model.user.Gender;
import com.erminray.polls.model.user.User;
import com.erminray.polls.model.user.PrimaryUserType;
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

    Page<User> findByPrimaryUserTypeIn(List<PrimaryUserType> primaryUserTypes, Pageable pageable);

    Page<User> findByUsernameInAndPrimaryUserTypeIn(List<String> usernames, List<PrimaryUserType> primaryUserTypes, Pageable pageable);
}
