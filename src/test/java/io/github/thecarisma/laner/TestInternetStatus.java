package io.github.thecarisma.laner;

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

    public static void main(String[] args) throws UnknownHostException {
        new InternetStatus("thecarisma.github.io", new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof InternetStatus.Status) {
                    System.out.println(o);
                }
            }
        }).run();
    }

}
