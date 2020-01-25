package io.github.thecarisma;

import org.junit.Test;

import java.net.UnknownHostException;

public class TestNetworkDevices {

    static class Listener implements LannerListener {

        @Override
        public void report(Object o) {
            if (o instanceof NetworkDevices.NetworkDevice) {
                System.out.println(((NetworkDevices.NetworkDevice) o).inetAddress.getCanonicalHostName());
            }
        }
    }

    @Test
    public void TestConnectedNetworkDevices() throws UnknownHostException {
        new NetworkDevices(UpNetworkInterface.getIPV4Address(), new Listener()).run();
    }

    public static void main(String[] args) throws UnknownHostException {
        new Thread(new NetworkDevices(UpNetworkInterface.getIPV4Address(), new Listener())).start();
    }

}
