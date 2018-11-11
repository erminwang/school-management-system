package com.erminray.polls.controller.system;

import com.erminray.polls.model.system.Semester;
import com.erminray.polls.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/system")
public class SystemController {

    @Autowired
    SystemService systemService;

    @GetMapping("/semesters")
    public List<Semester> getSemesters() {
        return systemService.getSemesters();
    }


}
