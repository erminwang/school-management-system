package com.erminray.polls.controller;

import com.erminray.polls.exception.AppException;
import com.erminray.polls.model.Role;
import com.erminray.polls.model.RoleName;
import com.erminray.polls.model.User;
import com.erminray.polls.model.user.Administrator;
import com.erminray.polls.model.user.Gender;
import com.erminray.polls.model.user.Instructor;
import com.erminray.polls.model.user.Student;
import com.erminray.polls.payload.ApiResponse;
import com.erminray.polls.payload.JwtAuthenticationResponse;
import com.erminray.polls.payload.LoginRequest;
import com.erminray.polls.payload.SignUpRequest;
import com.erminray.polls.repository.RoleRepository;
import com.erminray.polls.repository.UserRepository;
import com.erminray.polls.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    private Random random = new Random();

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        /**
         * AuthenticationManager uses userDetailsService to get the user based on username and
         * compare that user's password with the one in the authentication token
         */
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsernameOrEmail(),
                loginRequest.getPassword()
            )
        );

        // Store user info into HttpSession
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = null;
        Set<Role> roles = new HashSet<>();
        switch(signUpRequest.getUserType()) {
            case "student":
                user = new Student(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getGender(), signUpRequest.getUsername(),
                        signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.getTypeStu());
                Role studentRole = roleRepository.findByName(RoleName.ROLE_STUDENT)
                        .orElseThrow(() -> new AppException("Student Role not set."));
                roles.add(studentRole);
                break;

            case "instructor":
                user = new Instructor(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getGender(), signUpRequest.getUsername(),
                        signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.getTypeInst());
                Role instructorRole = roleRepository.findByName(RoleName.ROLE_INSTRUCTOR)
                        .orElseThrow(() -> new AppException("Instructor Role not set."));
                roles.add(instructorRole);
                break;

            case "admin":
                user = new Administrator(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getGender(), signUpRequest.getUsername(),
                        signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.getTypeAdmin());
                Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                        .orElseThrow(() -> new AppException("Admin Role not set."));
                roles.add(adminRole);
                break;

            default:
                return new ResponseEntity(new ApiResponse(false, "User type was not specified!"),
                        HttpStatus.BAD_REQUEST);
        }

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
            .orElseThrow(() -> new AppException("User Role not set."));

        roles.add(userRole);

        user.setRoles(roles);

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/api/users/{username}")
            .buildAndExpand(result.getUsername()).toUri();

        // "created" returns response with 201 status code
        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @PostMapping("/populate/examples")
    public ResponseEntity<?> populateExampleUsers() {
        int studentNum = 200;
        int instructorNum = 25;
        int adminNum = 3;

        Gender[] genders = Gender.values();
        int gendersLen = genders.length;

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER).get();
        Role stuRole = roleRepository.findByName(RoleName.ROLE_STUDENT).get();
        Role instRole = roleRepository.findByName(RoleName.ROLE_INSTRUCTOR).get();
        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN).get();

        for(int i = 0; i < studentNum; i++) {
            User student = new Student(generateRandomName(), generateRandomName(),
                    genders[random.nextInt(gendersLen)].toString(), "stu" + i, "stu" + i + "@sfu.ca",
                    passwordEncoder.encode("password"), "ts");
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            roles.add(stuRole);
            student.setRoles(roles);
            userRepository.save(student);
        }

        for(int i = 0; i < instructorNum; i++) {
            User instructor = new Instructor(generateRandomName(), generateRandomName(),
                    genders[random.nextInt(gendersLen)].toString(), "inst" + i, "inst" + i + "@sfu.ca",
                    passwordEncoder.encode("password"), "ti");
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            roles.add(instRole);
            instructor.setRoles(roles);
            userRepository.save(instructor);
        }

        for(int i = 0; i < adminNum; i++) {
            User administrator = new Administrator(generateRandomName(), generateRandomName(),
                    genders[random.nextInt(gendersLen)].toString(), "admin" + i, "admin" + i + "@sfu.ca",
                    passwordEncoder.encode("password"), "ta");
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            roles.add(adminRole);
            administrator.setRoles(roles);
            userRepository.save(administrator);
        }

        return new ResponseEntity<String>("Successfully Created", HttpStatus.CREATED);
    }

    private String generateRandomName() {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        int charsLen = chars.length();

        StringBuilder sb = new StringBuilder(
                Character.toString(
                    Character.toUpperCase(
                           chars.charAt(random.nextInt(charsLen))
                    )
                )
        );

        for(int i = 0; i < 6; i++) {
            sb.append(chars.charAt(random.nextInt(charsLen)));
        }

        return sb.toString();
    }
}