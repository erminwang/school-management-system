package com.erminray.polls.security;


import com.erminray.polls.model.user.User;
import com.erminray.polls.repository.old.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//UserDetailsService is an external interface that provides service about prompt upon login attempt(Eg. User name not valid)
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    // This method is used by AuthenticationManager
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
        throws UsernameNotFoundException {
        // Let people login with either username or email
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
            .orElseThrow(() ->
                new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
            );

        return UserPrincipal.create(user);
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return UserPrincipal.create(user);
    }
}