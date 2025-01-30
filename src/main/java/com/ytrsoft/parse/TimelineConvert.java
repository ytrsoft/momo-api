package com.ytrsoft.parse;

import org.springframework.stereotype.Component;

@Component
public class TimelineConvert extends FeedListConvert {

    @Override
    protected String getArrayKey() {
        return "feeds";
    }

}
