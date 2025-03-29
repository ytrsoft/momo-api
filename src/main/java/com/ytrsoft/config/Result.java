package com.ytrsoft.config;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class Result extends ResponseEntity<Map<String, Object>> {

    public Result(JSONObject json) {
        super(json.toMap(), HttpStatus.OK);
    }

}
