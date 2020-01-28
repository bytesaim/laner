package io.github.thecarisma.laner;

import org.junit.Test;

public class TestLanerServer {

    @Test
    public void Test1() {
        LanerServer lanerServer = new LanerServer("192.168.8.102", 2020, new LanerListener() {
            @Override
            public void report(Object o) {

            }
        });
        lanerServer.run();
    }

}
