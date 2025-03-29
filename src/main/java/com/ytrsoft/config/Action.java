package com.ytrsoft.config;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class Action extends ResponseEntity<Map<String, Object>> {

    public Action(JSONObject json) {
        super(json.toMap(), HttpStatus.OK);
    }

}
