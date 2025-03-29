package com.ytrsoft.controller;

import com.ytrsoft.config.Result;
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
    public Result query(@PathVariable String id) {
        return new Result(ps.query(id));
    }
}
