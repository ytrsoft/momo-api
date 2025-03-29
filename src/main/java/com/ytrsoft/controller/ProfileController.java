package com.ytrsoft.controller;

import com.ytrsoft.config.Query;
import com.ytrsoft.domain.Profile;
import com.ytrsoft.service.ProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService ps;

    public ProfileController(ProfileService ps) {
        this.ps = ps;
    }

    @GetMapping("/{id}")
    public Query<Profile> query(@PathVariable String id) {
        return new Query<>(ps.query(id));
    }
}
