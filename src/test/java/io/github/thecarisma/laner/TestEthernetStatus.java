package io.github.thecarisma.laner;

import io.github.thecarisma.util.TimedTRunnableKiller;
import org.junit.Test;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;

public class TestEthernetStatus {

    @Test
    public void Test1() throws SocketException {
        ArrayList<NetworkInterface> networkInterfaces =  LanerNetworkInterface.getNetworkInterfacesNoLoopback();
        for (NetworkInterface networkInterface : networkInterfaces) {
            System.out.println(networkInterface.isUp() + ":" + networkInterface.getName());
        }
    }

    @Test
    public void Test2() {
        EthernetStatus ethernetStatus = new EthernetStatus(new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof ConnectionStatus) {
                    System.out.println(o);
                }
            }
        });
        ethernetStatus.run();
        TimedTRunnableKiller.timeTRunnableDeath(ethernetStatus, 10);
    }

    //@Test
    public static void main(String[] args) {
        EthernetStatus ethernetStatus = new EthernetStatus(new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof ConnectionStatus) {
                    System.out.println(o);
                }
            }
        });
        ethernetStatus.run();
        ethernetStatus.onlyCheckForInterfaceWith("172.16.40.27");
        TimedTRunnableKiller.timeTRunnableDeath(ethernetStatus, 10);
    }

}
