package com.cabbage03.studentManagement.tools;

import com.cabbage03.studentManagement.enums.StringAlignment;

import java.text.BreakIterator;

public class StringUtils {
    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        char[] chars = str.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    private static int getStringLength(String str) {
        BreakIterator boundary = BreakIterator.getCharacterInstance();
        boundary.setText(str);
        int start = boundary.first();
        int length = 0;
        for (int end = boundary.next(); end != BreakIterator.DONE; start = end, end = boundary.next()) {
            length += getCharWidth(str.substring(start, end));
        }
        return length;
    }

    private static int getCharWidth(String str) {
        return str.length() == str.getBytes().length ? 1 : 2;
    }


    public static String padString(String str, int length) {
        return padString(str, length, StringAlignment.RIGHT);
    }

    public static String padString(String str, int length, StringAlignment direction) {
        int diff = length - getStringLength(str);
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

        return String.format("%" + leftPad + "s%s%" + rightPad + "s", "", str, "");
    }


}
