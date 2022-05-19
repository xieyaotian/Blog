package com.tian.blog.common.cache;


import java.lang.annotation.*;

@Target({ElementType.METHOD}) //注解的作用范围
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    long expire() default 1 * 60 * 1000;

    String name() default "";

}