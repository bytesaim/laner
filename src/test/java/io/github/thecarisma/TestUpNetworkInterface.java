package io.github.thecarisma;

import org.junit.Test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class TestUpNetworkInterface {

    @Test
    public void TestGetNetworkInterfaces() throws SocketException {
        ArrayList<NetworkInterface> networkInterfaces = UpNetworkInterface.getNetworkInterfaces();
        for (NetworkInterface networkInterface : networkInterfaces) {
            System.out.println(networkInterface.getDisplayName());
            System.out.println(networkInterface.getName());
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                System.out.printf("InetAddress: %s\n", inetAddress);
            }
        }
    }

}
