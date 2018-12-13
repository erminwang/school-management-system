package com.erminray.polls.service;

import com.erminray.polls.model.user.User;
import com.erminray.polls.payload.PagedResponse;
import com.erminray.polls.repository.old.UserRepository;
import com.erminray.polls.repository.user.AdminRepository;
import com.erminray.polls.repository.user.InstructorRepository;
import com.erminray.polls.repository.user.StudentRepository;
import com.erminray.polls.model.user.PrimaryUserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    AdminRepository adminRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public PagedResponse<User> getUsersByUsernamesAndTypes(String[] usernamesParam, String[] userTypesParam,
                                                           int page, int size, String sortByParam, String sortOrderParam) {
        String sortBy;
        switch(sortByParam) {
            case "first":
                sortBy = "firstName";
                break;
            case "last":
                sortBy = "lastName";
                break;
            case "updated":
                sortBy = "updatedAt";
                break;
            case "username":
                sortBy = "username";
                break;
            case "id":
                sortBy = "id";
                break;
            default:
                sortBy = "createdAt";
        }

        Sort.Direction sortDirection = Sort.Direction.DESC;
        if(sortOrderParam.equals("asc")){
            sortDirection = Sort.Direction.ASC;
        }

        Pageable pageable = PageRequest.of(page, size, sortDirection, sortBy);
        List<PrimaryUserType> primaryUserTypes = new ArrayList<>();
        for(String rawType : userTypesParam) {
            try{
                PrimaryUserType primaryUserType = PrimaryUserType.valueOf(rawType.toUpperCase());
                primaryUserTypes.add(primaryUserType);
            } catch (Exception e) {
                logger.error("Error converting user type to enum: " + e.getMessage());
            }
        }

        Page<User> users;
        if(usernamesParam.length == 0) {
            users = userRepository.findByPrimaryUserTypeIn(primaryUserTypes, pageable);
        } else {
            List<String> usernames = new ArrayList<>(Arrays.asList(usernamesParam));
            users = userRepository.findByUsernameInAndPrimaryUserTypeIn(usernames, primaryUserTypes, pageable);
        }



        if(users.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), users.getNumber(),
                    users.getSize(), users.getTotalElements(), users.getTotalPages(), users.isLast());
        }

        return new PagedResponse<>(users.getContent(), users.getNumber(),
                users.getSize(), users.getTotalElements(), users.getTotalPages(), users.isLast());
    }
}
