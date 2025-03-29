package com.ytrsoft.convert;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class NearlyConvert implements IConvert {

    @Override
    public JSONObject convert(JSONObject input) {
        return input;
    }

}
