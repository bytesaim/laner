package io.github.thecarisma.laner;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class LanerNetworkInterface {

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

    public static boolean isReachable(String addr, int openPort, int timeOutMillis) {
        if (!LanerProxyConfig.isProxyEnabled()) {
            return isReachableWithoutProxy(addr, openPort, timeOutMillis);
        }
        try {
            java.net.Proxy proxy = new java.net.Proxy(
                    java.net.Proxy.Type.HTTP,
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
            if (!addr.startsWith("https://") && !addr.startsWith("http://")) {
                addr = "http://" + addr;
            }
            HttpURLConnection conn = (HttpURLConnection) new URL(addr).openConnection(proxy);
            conn.setConnectTimeout(timeOutMillis);
            conn.disconnect();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
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

}
