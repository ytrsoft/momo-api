package com.ytrsoft.controller;

import com.ytrsoft.config.Result;
import com.ytrsoft.service.NewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/new")
public class NewController {

    private final NewService ns;

    public NewController(NewService ns) {
        this.ns = ns;
    }

    @GetMapping("/list")
    public Result list() {
        return new Result(ns.list());
    }

}
