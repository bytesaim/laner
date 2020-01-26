package io.github.thecarisma.laner;

import org.junit.Test;

import java.net.UnknownHostException;

public class TestInternetStatus {

    @Test
    public void TestIsConnected() {
        System.out.println(InternetStatus.IsConnected());
    }

    public static void main(String[] args) throws UnknownHostException {
        new InternetStatus("thecarisma.github.com", new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof InternetStatus.Status) {
                    System.out.println(o);
                }
            }
        }).run();
    }

}
