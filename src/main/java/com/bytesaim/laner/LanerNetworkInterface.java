package com.bytesaim.laner;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class LanerNetworkInterface {

    public static NetworkInterface findByHostAddress(String hostAddress) throws SocketException {
        ArrayList<NetworkInterface> networkInterfaces =  LanerNetworkInterface.getNetworkInterfaces();
        for (NetworkInterface networkInterface : networkInterfaces) {
            ArrayList<InetAddress> addresses = LanerNetworkInterface.getInetAddresses(networkInterface);
            for (InetAddress address : addresses) {
                if (hostAddress.equals(address.getHostAddress())) {
                    return networkInterface;
                }
            }
        }
        return null;
    }

    public static ArrayList<NetworkInterface> getNetworkInterfaces() throws SocketException {
        ArrayList<NetworkInterface> networkInterfaces = new ArrayList<>();
        Enumeration<NetworkInterface> eni = NetworkInterface.getNetworkInterfaces();
        while(eni.hasMoreElements()) {
            NetworkInterface networkInterface = eni.nextElement();
            if (!networkInterface.isUp()) {
                continue;
            }
            networkInterfaces.add(networkInterface);
        }
        return networkInterfaces;
    }

    public static ArrayList<NetworkInterface> getNetworkInterfacesNoLoopback() throws SocketException {
        ArrayList<NetworkInterface> networkInterfaces = new ArrayList<>();
        Enumeration<NetworkInterface> eni = NetworkInterface.getNetworkInterfaces();
        while(eni.hasMoreElements()) {
            NetworkInterface networkInterface = eni.nextElement();
            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue;
            }
            networkInterfaces.add(networkInterface);
        }
        return networkInterfaces;
    }

    public static ArrayList<InetAddress> getInetAddresses(NetworkInterface networkInterface) {
        return Collections.list(networkInterface.getInetAddresses());
    }

    public static String getIPV4Address() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    public static boolean isReachable(String addr, int openPort, int timeOutMillis, boolean useProxy) {
        return useProxy ? isReachable(addr, openPort, timeOutMillis) : isReachableWithoutProxy(addr, openPort, timeOutMillis);
    }

    public static boolean isReachable(String addr, int openPort, int timeOutMillis) {
        if (!LanerProxyConfig.isProxyEnabled()) {
            return isReachableWithoutProxy(addr, openPort, timeOutMillis);
        }
        Proxy proxy = new Proxy(
                Proxy.Type.HTTP,
                new InetSocketAddress(LanerProxyConfig.getProxyHost(), LanerProxyConfig.getProxyPort()));
        if (!LanerProxyConfig.getProxyUsername().isEmpty()) {
            Authenticator authenticator = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return (new PasswordAuthentication(
                            LanerProxyConfig.getProxyUsername(),
                            LanerProxyConfig.getProxyPassword().toCharArray()));
                }
            };
            Authenticator.setDefault(authenticator);
        }
        try {
            Socket server = new Socket(proxy);
            server.connect(new InetSocketAddress(addr, openPort), timeOutMillis);
            server.close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public static boolean isReachableWithoutProxy(String addr, int openPort, int timeOutMillis) {
        try {
            try (Socket soc = new Socket()) {
                soc.connect(new InetSocketAddress(addr, openPort), timeOutMillis);
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * Pings a HTTP URL. This effectively sends a HEAD request and returns <code>true</code> if the response code is in
     * the 200-399 range.
     * @param url The HTTP URL to be pinged.
     * @param timeout The timeout in millis for both the connection timeout and the response read timeout. Note that
     * the total timeout is effectively two times the given timeout.
     * @return <code>true</code> if the given HTTP URL has returned response code 200-399 on a HEAD request within the
     * given timeout, otherwise <code>false</code>.
     */
    public static boolean pingURL(String url, int timeout) {
        if (url.startsWith("http")) {
            url = url.replaceFirst("^https", "http"); // Otherwise an exception may be thrown on invalid SSL certificates.
        } else {
            url = "http://" + url;
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            connection.disconnect();
            return (200 <= responseCode && responseCode <= 399);
        } catch (IOException exception) {
            return false;
        }
    }

    /**
     * Pings a HTTP URL. This effectively sends a HEAD request and returns <code>true</code> if the response code is in
     * the 200-399 range.
     * @param url The HTTP URL to be pinged.
     * @param timeout The timeout in millis for both the connection timeout and the response read timeout. Note that
     * the total timeout is effectively two times the given timeout.
     * @param proxy The proxy object if the device is using proxy connection.
     * @return <code>true</code> if the given HTTP URL has returned response code 200-399 on a HEAD request within the
     * given timeout, otherwise <code>false</code>.
     */
    public static boolean pingURL(String url, int timeout, Proxy proxy) {
        if (url.startsWith("http")) {
            url = url.replaceFirst("^https", "http"); // Otherwise an exception may be thrown on invalid SSL certificates.
        } else {
            url = "http://" + url;
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection(proxy);
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (200 <= responseCode && responseCode <= 399);
        } catch (IOException exception) {
            return false;
        }
    }

}
