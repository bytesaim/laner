package io.github.thecarisma.util;

import org.junit.Test;

import java.util.Properties;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 */
public class TestUserSystem {

    @Test
    public void Test1() {
        for (Object key : UserSystem.properties.keySet()) {
            System.out.println(key + "=" + UserSystem.properties.get(key));
        }
    }

}
