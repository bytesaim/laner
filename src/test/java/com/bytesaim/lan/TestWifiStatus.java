package com.bytesaim.lan;

import com.bytesaim.util.TimedTRunnableKiller;
import org.junit.Test;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;

public class TestWifiStatus {

    @Test
    public void Test1() throws SocketException {
        ArrayList<NetworkInterface> networkInterfaces =  LanerNetworkInterface.getNetworkInterfacesNoLoopback();
        for (NetworkInterface networkInterface : networkInterfaces) {
            System.out.println(networkInterface.isUp() + ":" + networkInterface.getName());
        }
    }

    @Test
    public void Test2() {
        WifiStatus wifiStatus = new WifiStatus(new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof ConnectionStatus) {
                    System.out.println(o);
                }
            }
        });
        wifiStatus.run();
        TimedTRunnableKiller.timeTRunnableDeath(wifiStatus, 10);
    }

    @Test
    public void Test3() {
        WifiStatus wifiStatus = new WifiStatus(new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof ConnectionStatus) {
                    System.out.println(o);
                }
            }
        });
        wifiStatus.run();
        wifiStatus.onlyCheckForInterfaceWith("172.16.40.27");
        TimedTRunnableKiller.timeTRunnableDeath(wifiStatus, 10);
    }

}
