package com.erminray.polls.controller;

import com.erminray.polls.exception.ResourceNotFoundException;
import com.erminray.polls.model.user.RoleName;
import com.erminray.polls.model.user.User;
import com.erminray.polls.payload.*;
import com.erminray.polls.repository.old.PollRepository;
import com.erminray.polls.repository.old.UserRepository;
import com.erminray.polls.repository.old.VoteRepository;
import com.erminray.polls.security.UserPrincipal;
import com.erminray.polls.service.PollService;
import com.erminray.polls.security.CurrentUser;
import com.erminray.polls.service.UserService;
import com.erminray.polls.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private PollService pollService;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        String userType = "";
        for(GrantedAuthority authority : currentUser.getAuthorities()) {
            if(authority.getAuthority().equals(RoleName.ROLE_ADMIN.toString())) {
                userType = "ADMIN";
                break;
            } else if(authority.getAuthority().equals(RoleName.ROLE_INSTRUCTOR.toString())) {
                userType = "INSTRUCTOR";
                break;
            } else {
                userType = "STUDENT";
            }
        }
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getFirstName() + " " + currentUser.getLastName(), userType);
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        long pollCount = pollRepository.countByCreatedBy(user.getId());
        long voteCount = voteRepository.countByUserId(user.getId());

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getGender(), user.getCreatedAt(), pollCount, voteCount);

        return userProfile;
    }

    @GetMapping("/users/{username}/polls")
    public PagedResponse<PollResponse> getPollsCreatedBy(@PathVariable(value = "username") String username,
                                                         @CurrentUser UserPrincipal currentUser,
                                                         @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                         @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getPollsCreatedBy(username, currentUser, page, size);
    }


    @GetMapping("/users/{username}/votes")
    public PagedResponse<PollResponse> getPollsVotedBy(@PathVariable(value = "username") String username,
                                                       @CurrentUser UserPrincipal currentUser,
                                                       @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getPollsVotedBy(username, currentUser, page, size);
    }

    // following are new urls

    @GetMapping("/users/search")
    @PreAuthorize("hasRole('ADMIN')")
    public PagedResponse<User> searchUsersByParameters(
            @RequestParam(value = "types", defaultValue = "student,instructor,admin") String[] userTypes,
            @RequestParam(value = "usernames", defaultValue = "") String[] usernames,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort_by", defaultValue = "created") String sortBy,
            @RequestParam(value = "sort_order", defaultValue = "desc") String sortOrder
    ) {
        return userService.getUsersByUsernamesAndTypes(usernames, userTypes, page, size, sortBy, sortOrder);
    }
}