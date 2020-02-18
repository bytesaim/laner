package com.bytesaim.laner;

import com.bytesaim.util.TimedTRunnableKiller;
import org.junit.Test;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;

public class TestLanStatus {

    @Test
    public void Test1() throws SocketException {
        ArrayList<NetworkInterface> networkInterfaces =  LanerNetworkInterface.getNetworkInterfacesNoLoopback();
        for (NetworkInterface networkInterface : networkInterfaces) {
            System.out.println(networkInterface.isUp() + ":" + networkInterface.getName());
        }
    }

    //@Test
    public static void main(String[] args) {
        LanStatus lanStatus = new LanStatus(new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof ConnectionStatus) {
                    System.out.println(o);
                }
            }
        });
        lanStatus.run();
        TimedTRunnableKiller.timeTRunnableDeath(lanStatus, 10);
    }

    @Test
    public void Test3() {
        LanStatus lanStatus = new LanStatus(new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof ConnectionStatus) {
                    System.out.println(o);
                }
            }
        });
        lanStatus.run();
        lanStatus.onlyCheckForInterfaceWith("172.16.40.27");
        TimedTRunnableKiller.timeTRunnableDeath(lanStatus, 10);
    }

}
