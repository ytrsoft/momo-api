package com.momo.sdk.http.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类型级标记注解,用于标识一个接口是 Momo API 定义。
 *
 * <p>当前主要起文档化与可发现性作用,不参与代理逻辑;{@code ApiProxyFactory} 不强制要求接口
 * 必须带 {@code @Api},但建议带上以提升可读性。
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Api {}
