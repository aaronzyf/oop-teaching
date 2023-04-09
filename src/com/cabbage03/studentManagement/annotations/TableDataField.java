package com.cabbage03.studentManagement.annotations;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TableDataField {
    String value();
}
