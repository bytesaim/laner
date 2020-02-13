package io.github.thecarisma.laner;

import io.github.thecarisma.exceptions.InvalidArgumentException;
import io.github.thecarisma.util.TimedTRunnableKiller;
import org.junit.Test;

import java.net.UnknownHostException;

public class TestInternetStatus {

    @Test
    public void TestIsConnected() {
        System.out.println(InternetStatus.IsConnected());
    }

    @Test
    public void TestIsConnected2() {
        System.out.println(LanerNetworkInterface.isReachable("google.com", 80, 1000));
    }

    @Test
    public void Test1() {
        InternetStatus internetStatus = new InternetStatus("thecarisma.github.io", new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof ConnectionStatus) {
                    System.out.println(o);
                }
            }
        });
        internetStatus.run();
        TimedTRunnableKiller.timeTRunnableDeath(internetStatus, 10);
    }

    @Test
    public void TestProxy() throws InvalidArgumentException {
        LanerProxyConfig.setProxyHost("test.example.com");
        LanerProxyConfig.setProxyPort(8080);
        LanerProxyConfig.enableProxy(true);
        InternetStatus internetStatus = new InternetStatus("thecarisma.github.io", new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof ConnectionStatus) {
                    System.out.println(o);
                }
            }
        });
        internetStatus.useProxy(true);
        internetStatus.run();
        TimedTRunnableKiller.timeTRunnableDeath(internetStatus, 10);
    }

}
