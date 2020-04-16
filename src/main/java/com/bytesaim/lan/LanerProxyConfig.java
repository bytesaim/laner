package com.bytesaim.lan;

import com.bytesaim.exceptions.InvalidArgumentException;

public class LanerProxyConfig {

    private static boolean PROXY_ENABLED = false;

    private static String PROXY_HOST = "";

    private static int PROXY_PORT = 8080;

    private static String PROXY_USERNAME = "";

    private static String PROXY_PASSWORD = "";

    private LanerProxyConfig() {

    }

    public static boolean isProxyEnabled() {
        return PROXY_ENABLED;
    }

    public static void enableProxy(boolean useProxy) {
        PROXY_ENABLED = useProxy;
    }

    public static String getProxyHost() {
        return PROXY_HOST;
    }

    public static void setProxyHost(String proxyAddress) throws InvalidArgumentException {
        if (proxyAddress.startsWith("https://") || proxyAddress.startsWith("http://")) {
            throw new InvalidArgumentException(new String[]{proxyAddress}, "The address should not prefix 'http://' and 'https://'");
        }
        PROXY_HOST = proxyAddress;
    }

    public static int getProxyPort() {
        return PROXY_PORT;
    }

    public static void setProxyPort(int proxyPort) {
        PROXY_PORT = proxyPort;
    }

    public static String getProxyUsername() {
        return PROXY_USERNAME;
    }

    public static void setProxyUsername(String proxyUsername) {
        PROXY_USERNAME = proxyUsername;
    }

    public static String getProxyPassword() {
        return PROXY_PASSWORD;
    }

    public static void setProxyPassword(String proxyPassword) {
        PROXY_PASSWORD = proxyPassword;
    }

    public static String ToString() {
        return LanerProxyConfig.class.getName() + "@" + "UseProxy=" + PROXY_ENABLED + ",Address=" + PROXY_HOST + ",Port=" + PROXY_PORT;
    }

}
