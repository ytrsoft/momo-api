package com.ytrsoft.controller;

import com.ytrsoft.domain.Profile;
import com.ytrsoft.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService ps;

    public ProfileController(ProfileService ps) {
        this.ps = ps;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> query(@PathVariable String id) {
        return new ResponseEntity<>(ps.query(id), HttpStatus.OK);
    }
}
