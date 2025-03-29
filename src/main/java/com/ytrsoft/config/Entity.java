package com.ytrsoft.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Entity<T> extends ResponseEntity<T> {

    public Entity(T t) {
        super(t, HttpStatus.OK);
    }

}
