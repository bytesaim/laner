package dev.sourcerersproject.codewalker.util;

import java.util.regex.Pattern;

/**
 * :copyright: MIT LICENSE (c) 2019 watchersproject
 * :author: Adewale Azeez <azeezadewale98@gmail.com>
 * :date: 08 September 2019
 * :time: 02:05 AM
 * :filename: Type.java
 */
public class Type {

    /**
     *
     * @param c
     * @return
     */
    public static boolean isPunctuation(char c) {
        return isPunctuation(String.format("%c", c));
    }

    /**
     *
     * @param str
     * @return
     */
    public static boolean isPunctuation(String str) {
        return str.equals("=") || Pattern.matches("\\p{IsPunctuation}", str);
    }

    /**
     *
     * @param c
     * @return
     */
    public static boolean isLetter(char c) {
        return Character.isLetter(c);
    }

    /**
     *
     * @param str
     * @return
     */
    public static boolean isLetter(String str) {
        return isLetter(str.toCharArray()[0]);
    }

    /**
     *
     * @param c
     * @return
     */
    public static boolean isDigit(char c) {
        return Character.isDigit(c);
    }

    /**
     *
     * @param str
     * @return
     */
    public static boolean isDigit(String str) {
        return isDigit(str.toCharArray()[0]);
    }

    /**
     *
     * @param c
     * @return
     */
    public static boolean isWhitespace(char c) {
        return Character.isWhitespace(c);
    }

    /**
     *
     * @param str
     * @return
     */
    public static boolean isWhiteSpace(String str) {
        return isWhitespace(str.toCharArray()[0]);
    }

}
