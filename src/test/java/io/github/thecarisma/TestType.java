package io.github.thecarisma;

import org.junit.Test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class TestType {

    @Test
    public void TestInterfaceType() throws SocketException {
        Enumeration<NetworkInterface> eni = NetworkInterface.getNetworkInterfaces();
        while(eni.hasMoreElements()) {
            NetworkInterface networkInterface = eni.nextElement();
            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue;
            }
            System.out.println(networkInterface.getDisplayName());
            System.out.println(networkInterface.getInetAddresses().nextElement().getHostAddress());
            Enumeration<InetAddress> eia = networkInterface.getInetAddresses();
            while(eia.hasMoreElements()) {
                InetAddress ia = eia.nextElement();
                if (!ia.isAnyLocalAddress() && !ia.isLoopbackAddress() && !ia.isSiteLocalAddress()) {
                    if (!ia.getHostName().equals(ia.getHostAddress())) {
                        //System.out.println("Hello World");
                    }
                }
            }
        }
    }

}
