package com.cabbage03.studentManagement.annotations;

public @interface EnumField {
    Class<? extends Enum> value();
}