package io.github.thecarisma.laner;

import org.junit.Test;

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

    @Test
    public void TestConnectedNetworkDevices() throws UnknownHostException {
        new NetworkDevices(LanerNetworkInterface.getIPV4Address(), new Listener()).run();
    }

    public static void main(String[] args) throws UnknownHostException {
        new Thread(new NetworkDevices(LanerNetworkInterface.getIPV4Address(), new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof NetworkDevices.NetworkDevice) {
                    System.out.println(o);
                }
            }
        })).start();
    }

}
