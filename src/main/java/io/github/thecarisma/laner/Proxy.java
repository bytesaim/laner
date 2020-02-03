package io.github.thecarisma.laner;

public class Proxy {

    public static boolean USE_PROXY = false;

    public static String PROXY_ADDRESS = "";

    public static int PROXY_PORT = 8080;

    public static String PROXY_USERNAME;

    public static boolean isUseProxy() {
        return USE_PROXY;
    }

    public static void setUseProxy(boolean useProxy) {
        USE_PROXY = useProxy;
    }

    public static String getProxyAddress() {
        return PROXY_ADDRESS;
    }

    public static void setProxyAddress(String proxyAddress) {
        PROXY_ADDRESS = proxyAddress;
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

    public static String PROXY_PASSWORD;

}
