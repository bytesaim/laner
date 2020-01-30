package io.github.thecarisma.util;

import java.util.Properties;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 */
public class UserSystem {
    public static Properties properties = System.getProperties();
    public static String OS = properties.getProperty("os.name");
    public static String CPU_ARCH = properties.getProperty("sun.arch.data.model");
}
