package com.ytrsoft.http;

import com.ytrsoft.convert.IConvert;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Response {
    Class<? extends IConvert> value();
}