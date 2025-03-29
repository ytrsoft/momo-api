package com.ytrsoft.controller;

import com.ytrsoft.config.Query;
import com.ytrsoft.domain.News;
import com.ytrsoft.service.NewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/new")
public class NewController {

    private final NewService ns;

    public NewController(NewService ns) {
        this.ns = ns;
    }

    @GetMapping("/list/{exp}")
    public Query<List<News>> list(@PathVariable String exp) {
        return new Query<>(ns.list(exp));
    }

}
