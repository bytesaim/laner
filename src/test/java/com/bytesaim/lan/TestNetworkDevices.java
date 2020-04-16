package com.bytesaim.lan;

import com.bytesaim.util.TimedTRunnableKiller;
import com.bytesaim.exceptions.InvalidArgumentException;
import org.junit.Test;

import java.io.IOException;
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
        NetworkDevices networkDevices = new NetworkDevices(LanerNetworkInterface.getIPV4Address(), new Listener());
        new Thread(networkDevices).start();
        TimedTRunnableKiller.timeTRunnableDeath(networkDevices, 10);
    }

    @Test
    public void TestConnectedNetworkDevicesExtraPorts() throws UnknownHostException {
        NetworkDevices networkDevices = new NetworkDevices(LanerNetworkInterface.getIPV4Address(), new Listener(), new int[]{ 12345, 8021});
        new Thread(networkDevices).start();
        TimedTRunnableKiller.timeTRunnableDeath(networkDevices, 10);
    }

    public static void main(String[] args) throws IOException, InvalidArgumentException {
        new NetworkDevices("172.16.40.27", new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof NetworkDevices.NetworkDevice) {
                    System.out.println(o + "->" + ((NetworkDevices.NetworkDevice) o).openedPort);
                }
            }
        }, new int[]{7510}).run();
    }

}
