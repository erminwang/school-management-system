package com.erminray.polls.controller.system;

import com.erminray.polls.model.user.Role;
import com.erminray.polls.model.user.RoleName;
import com.erminray.polls.model.system.*;
import com.erminray.polls.model.user.User;
import com.erminray.polls.model.user.Administrator;
import com.erminray.polls.model.user.Gender;
import com.erminray.polls.model.user.Instructor;
import com.erminray.polls.model.user.Student;
import com.erminray.polls.repository.old.RoleRepository;
import com.erminray.polls.repository.old.UserRepository;
import com.erminray.polls.repository.system.*;
import com.erminray.polls.repository.user.InstructorRepository;
import com.erminray.polls.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/testdata")
public class PopDataController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseResultRepository courseResultRepository;

    @Autowired
    CourseTypeRepository courseTypeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    SemesterRepository semesterRepository;

    private Random random = new Random();

    @PostMapping("/populate/users")
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

        for (int i = 0; i < studentNum; i++) {
            User student = new Student(generateRandomName(), generateRandomName(),
                genders[random.nextInt(gendersLen)].toString(), "stu" + i, "stu" + i + "@sfu.ca",
                passwordEncoder.encode("password"), "ts");
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            roles.add(stuRole);
            student.setRoles(roles);
            userRepository.save(student);
        }

        for (int i = 0; i < instructorNum; i++) {
            User instructor = new Instructor(generateRandomName(), generateRandomName(),
                genders[random.nextInt(gendersLen)].toString(), "inst" + i, "inst" + i + "@sfu.ca",
                passwordEncoder.encode("password"), "ti");
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            roles.add(instRole);
            instructor.setRoles(roles);
            userRepository.save(instructor);
        }

        for (int i = 0; i < adminNum; i++) {
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

    @PostMapping("/populate/system")
    public ResponseEntity<?> populateSystemData() throws Exception {
        String[] departmentFullnames = {"Computer Science","Mathematics","Science","Statistics","Education","Business","Engineering Science","History"};
        String[] departmentShortnames = {"CMPT","MATH","SCI","STAT","EDU","BUS","ENSC","HIST"};
        int departmentNum = departmentFullnames.length;

        if (departmentFullnames.length != departmentShortnames.length){
            throw new Exception("length not equal in both arrays");
        }

        for (int i = 0; i<departmentNum;i++){
            Department department = new Department(departmentFullnames[i],departmentShortnames[i]);
            departmentRepository.save(department);
        }

        //create courseTypes
        int[] courseNumberList = {700, 725, 737, 800, 811, 900, 930};
        String description = "This is an introduction course to ";
        List<Department> departments = departmentRepository.findAll();
        String title = "Title";
        String departmentName;
        for (Department department : departments) {
            departmentName = department.getShortName();
            for (int i=0;i<courseNumberList.length;i++){
                int courseNumber = courseNumberList[i];
                String courseName = departmentName + courseNumber;
                CourseType courseType = new
                    CourseType(courseName, department, courseNumber, title,description + courseName, BreadthType.Q,3);
                courseTypeRepository.save(courseType);
            }
        }
        //create semesters
        Semester semester1 = new Semester("SPRING2018",LocalDate.of(2018,1,1),LocalDate.of(2018,4,30));
        Semester semester2 = new Semester("SUMMER2018",LocalDate.of(2018,5,1),LocalDate.of(2018,8,31));
        Semester semester3 = new Semester("FALL2018",LocalDate.of(2018,9,1),LocalDate.of(2018,12,31));
        Semester semester4 = new Semester("SPRING2019",LocalDate.of(2019,1,1),LocalDate.of(2019,4,30));
        semesterRepository.save(semester1);
        semesterRepository.save(semester2);
        semesterRepository.save(semester3);
        semesterRepository.save(semester4);

        List<Instructor> instructors = instructorRepository.findAll();
        int instructorsSize = instructors.size();

        //create courses (instances)
        Semester[] semesterArr = {semester1, semester2, semester3, semester4};
        for (Semester semester : semesterArr) {
            for (CourseType courseType : courseTypeRepository.findAll()){
                Course course = new Course(courseType, random.nextInt(200) + 30, random.nextInt(60), semester);
                instructors.get(random.nextInt(instructorsSize)).getCoursesOwned().add(course);
                courseRepository.save(course);
            }
        }

        for(Instructor instructor : instructors) {
            instructorRepository.save(instructor);
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