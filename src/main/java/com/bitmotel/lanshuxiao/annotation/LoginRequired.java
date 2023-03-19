package com.bitmotel.lanshuxiao.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LoginRequired {
    String description() default "Login checked";
    boolean required() default true;
}