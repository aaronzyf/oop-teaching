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


    public static String padString(String str, int length) {
        return padString(str,length,StringAlignment.RIGHT);
    }
    public static String padString(String str, int length, StringAlignment direction) {
        int diff = length - str.length();
        if (diff <= 0) {
            return str;
        }
        int leftPad = 1, rightPad = 1;
        if (direction == StringAlignment.LEFT) {
            leftPad = diff;
        } else if (direction == StringAlignment.RIGHT) {
            rightPad = diff;
        } else if (direction == StringAlignment.MIDDLE) {
            leftPad = diff / 2;
            rightPad = diff - leftPad;
        }
        return String.format("%" + leftPad + "s%s%" + rightPad + "s",  "",str,"");
    }

    public enum StringAlignment {
        LEFT,
        RIGHT,
        MIDDLE
    }
}
