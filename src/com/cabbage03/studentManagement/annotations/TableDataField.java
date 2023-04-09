package com.cabbage03.studentManagement.annotations;

import com.cabbage03.studentManagement.enums.StringAlignment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TableDataField {
    String name();

    int width() default 10;

    StringAlignment direction() default StringAlignment.RIGHT;
}

