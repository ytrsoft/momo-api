package com.ytrsoft.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Query<T> extends ResponseEntity<T> {

    public Query(T t) {
        super(t, HttpStatus.OK);
    }

}
