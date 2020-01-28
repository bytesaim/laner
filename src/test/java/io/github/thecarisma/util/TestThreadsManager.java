package io.github.thecarisma.util;

import io.github.thecarisma.laner.LanerListener;
import io.github.thecarisma.laner.LanerNetworkInterface;
import io.github.thecarisma.laner.NetworkDevices;
import org.junit.Test;

import java.net.UnknownHostException;

public class TestThreadsManager {

    @Test
    public void Test1() throws UnknownHostException {
        final ThreadsManager threadsManager = new ThreadsManager();
        final int[] index = {0};
        NetworkDevices nd = new NetworkDevices(LanerNetworkInterface.getIPV4Address(), new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof NetworkDevices.NetworkDevice) {
                    if (index[0] > 0) {
                        try {
                            threadsManager.killAll();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(o);
                    index[0]++;
                }
            }
        });
        threadsManager.registerTRunnable("testnetworddevices", nd);
        nd.run();
    }

}
