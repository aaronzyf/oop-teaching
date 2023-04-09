package com.cabbage03.studentManagement.tools;

public class StringUtils {
    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        char[] chars = str.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }


    public enum StringAlignment {
        LEFT,
        RIGHT,
        MIDDLE
    }
}
