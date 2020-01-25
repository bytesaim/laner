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
