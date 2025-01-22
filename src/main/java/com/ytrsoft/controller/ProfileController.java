package com.ytrsoft.controller;

import com.ytrsoft.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService ps;

    public ProfileController(ProfileService ps) {
        this.ps = ps;
    }

    @GetMapping("/query")
    public ResponseEntity<Map<String, Object>> query() {
        Map<String, Object> checked = ps.query();
        return new ResponseEntity<>(checked, HttpStatus.OK);
    }

}
