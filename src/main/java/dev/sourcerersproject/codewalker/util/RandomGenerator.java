package dev.sourcerersproject.codewalker.util;

import java.util.Random;

/**
 * :copyright: MIT LICENSE (c) 2019 watchersproject
 * :author: Adewale Azeez <azeezadewale98@gmail.com>
 * :date: 08 September 2019
 * :time: 01:55 PM
 * :filename: RandomGenerator.java
 */
public class RandomGenerator {

    /**
     *
     * @param high
     * @param low
     * @return
     */
    public static int generateRandomNumber(int high, int low) {
        return new Random().nextInt(high - low) + low;
    }

    /**
     *
     * @param high
     * @return
     */
    public static int generateRandomNumber(int high) {
        return new Random().nextInt(high);
    }

    /**
     *
     * @return
     */
    public static int generateRandomNumber() {
        return new Random().nextInt();
    }

}
