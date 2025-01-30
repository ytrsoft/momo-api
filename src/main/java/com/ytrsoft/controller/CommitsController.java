package com.ytrsoft.controller;

import com.ytrsoft.service.CommitsService;
import com.ytrsoft.service.NearlyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/commits")
public class CommitsController {

    private final CommitsService cs;

    public CommitsController(CommitsService cs) {
        this.cs = cs;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Object>> query(@PathVariable String id) {
        List<Object> queried = cs.query(id);
        return new ResponseEntity<>(queried, HttpStatus.OK);
    }

}
