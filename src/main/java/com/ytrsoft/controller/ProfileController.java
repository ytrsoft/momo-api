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

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> query(@PathVariable String id) {
        Map<String, Object> checked = ps.query(id);
        return new ResponseEntity<>(checked, HttpStatus.OK);
    }

}
