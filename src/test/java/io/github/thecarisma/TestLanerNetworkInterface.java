package io.github.thecarisma;

import org.junit.Test;

import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class TestLannerNetworkInterface {

    @Test
    public void TestGetNetworkInterfaces() throws SocketException {
        ArrayList<NetworkInterface> networkInterfaces = LannerNetworkInterface.getNetworkInterfaces();
        for (NetworkInterface networkInterface : networkInterfaces) {
            System.out.println(networkInterface.getDisplayName());
            System.out.println(networkInterface.getName());
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                System.out.printf("InetAddress: %s\n", inetAddress);
            }
        }
    }

    @Test
    public void TestGetNetworkInterfacesInet4Address() throws SocketException {
        ArrayList<NetworkInterface> networkInterfaces = LannerNetworkInterface.getNetworkInterfacesNoLoopback();
        for (NetworkInterface networkInterface : networkInterfaces) {
            System.out.println(networkInterface.getDisplayName());
            System.out.println(networkInterface.getName());
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                if (inetAddress instanceof Inet4Address)
                    System.out.printf("InetAddress: %s\n", inetAddress.getHostAddress());
            }
        }
    }

    @Test
    public void TestGetNetworkInterfacesInet6Address() throws SocketException {
        ArrayList<NetworkInterface> networkInterfaces = LannerNetworkInterface.getNetworkInterfaces();
        for (NetworkInterface networkInterface : networkInterfaces) {
            System.out.println(networkInterface.getDisplayName());
            System.out.println(networkInterface.getName());
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                if (inetAddress instanceof Inet6Address)
                    System.out.printf("InetAddress: %s\n", inetAddress);
            }
        }
    }

    @Test
    public void TestGetInetAddresses() throws SocketException, UnknownHostException {
        ArrayList<NetworkInterface> networkInterfaces = LannerNetworkInterface.getNetworkInterfaces();
        for (NetworkInterface networkInterface : networkInterfaces) {
            for (InetAddress inetAddress : LannerNetworkInterface.getInetAddresses(networkInterface)) {
                System.out.println(inetAddress.getHostAddress());
            }
        }
    }

    @Test
    public void TestGetInetAddresses0() throws SocketException, UnknownHostException {
        ArrayList<NetworkInterface> networkInterfaces = LannerNetworkInterface.getNetworkInterfaces();
        for (NetworkInterface networkInterface : networkInterfaces) {
            System.out.println(LannerNetworkInterface.getInetAddresses(networkInterface).get(0));
        }
    }

    @Test
    public void TestGetIPV4Address() throws UnknownHostException {
        System.out.println(LannerNetworkInterface.getIPV4Address());
    }

    @Test
    public void TestFindOpenPorts() throws UnknownHostException {
        for (int port = 0; port < 100000; ++port) {
            if (LannerNetworkInterface.isReachable(LannerNetworkInterface.getIPV4Address(), port, 10))
                System.out.println("Port: " + port + " is open");
        }
    }

}
