package com.ytrsoft.controller;

import com.ytrsoft.service.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsService ns;

    public NewsController(NewsService ns) {
        this.ns = ns;
    }

    @GetMapping("/query")
    public ResponseEntity<List<Object>> query() {
        List<Object> queried = ns.query();
        return new ResponseEntity<>(queried, HttpStatus.OK);
    }

}
