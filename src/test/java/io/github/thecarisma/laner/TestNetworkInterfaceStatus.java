package io.github.thecarisma.laner;

import io.github.thecarisma.util.TimedTRunnableKiller;
import org.junit.Test;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;

public class TestNetworkInterfaceStatus {

    @Test
    public void Test1() throws SocketException {
        ArrayList<NetworkInterface> networkInterfaces =  LanerNetworkInterface.getNetworkInterfacesNoLoopback();
        for (NetworkInterface networkInterface : networkInterfaces) {
            System.out.println(networkInterface.isUp() + ":" + networkInterface.getName());
        }
    }

    @Test
    public void Test2() {
        NetworkInterfaceStatus networkInterfaceStatus = new NetworkInterfaceStatus(new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof ConnectionStatus) {
                    System.out.println(o);
                }
            }
        });
        networkInterfaceStatus.run();
        TimedTRunnableKiller.timeTRunnableDeath(networkInterfaceStatus, 20);
    }

    //@Test
    public static void main(String[] args) {
        NetworkInterfaceStatus networkInterfaceStatus = new NetworkInterfaceStatus(new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof ConnectionStatus) {
                    System.out.println(o);
                }
            }
        });
        networkInterfaceStatus.run();
        TimedTRunnableKiller.timeTRunnableDeath(networkInterfaceStatus, 20);
    }

}
