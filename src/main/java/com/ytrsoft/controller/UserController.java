package com.ytrsoft.controller;

import com.ytrsoft.core.Global;
import com.ytrsoft.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService us;

    public UserController(UserService us) {
        this.us = us;
    }

    @GetMapping("/login/{account}/{password}")
    public ResponseEntity<Map<String, Object>> query(@PathVariable String account, @PathVariable String password) {
        Map<String, Object> login = us.login(account, password);
        return new ResponseEntity<>(login, HttpStatus.OK);
    }

}
