package io.github.thecarisma.laner;

import org.junit.Test;

public class TestProxy {

    @Test
    public void Test1() {
        Proxy.setProxyAddress("google.com");
        Proxy.setProxyPort(8080);
        System.out.println(Proxy.ToString());
    }

}
