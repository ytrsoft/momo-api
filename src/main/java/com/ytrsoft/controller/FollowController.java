package com.ytrsoft.controller;

import com.ytrsoft.config.Action;
import com.ytrsoft.service.FollowService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follow")
public class FollowController {

    private final FollowService fs;

    public FollowController(FollowService fs) {
        this.fs = fs;
    }

    @GetMapping("/{id}")
    public Action follow(@PathVariable String id) {
        return new Action(fs.follow(id));
    }

    @GetMapping("/remove/{id}")
    public Action remove(@PathVariable String id) {
        return new Action(fs.remove(id));
    }

}
