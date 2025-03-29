package com.ytrsoft.controller;

import com.ytrsoft.config.Result;
import com.ytrsoft.service.NearlyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nearly")
public class NealyController {

    private final NearlyService ns;

    public NealyController(NearlyService ns) {
        this.ns = ns;
    }

    @GetMapping("/list/{exp}")
    public Result query(@PathVariable String exp) {
        return new Result(ns.list(exp));
    }

}
