package io.github.thecarisma.laner;

import io.github.thecarisma.exceptions.InvalidArgumentException;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestNetworkDevices {

    static class Listener implements LanerListener {

        @Override
        public void report(Object o) {
            if (o instanceof NetworkDevices.NetworkDevice) {
                System.out.println(o);
            }
        }
    }

    //@Test
    public void TestConnectedNetworkDevices() throws UnknownHostException {
        new NetworkDevices(LanerNetworkInterface.getIPV4Address(), new Listener()).run();
    }

    //@Test
    public void TestConnectedNetworkDevicesExtraPorts() throws UnknownHostException {
        new NetworkDevices(LanerNetworkInterface.getIPV4Address(), new Listener(), new int[]{ 12345, 8021}).run();
    }

    public static void main(String[] args) throws IOException, InvalidArgumentException {
        new NetworkDevices("192.168.8.100", new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof NetworkDevices.NetworkDevice) {
                    System.out.println(o + "->" + ((NetworkDevices.NetworkDevice) o).openedPort);
                }
            }
        }, new int[]{7510}).run();
    }

}
