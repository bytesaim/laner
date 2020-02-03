package io.github.thecarisma.laner;

import org.junit.Test;

public class TestProxy {

    @Test
    public void Test1() {
        Proxy.setProxyAddress("google.com");
        Proxy.setProxyPort(8080);
        System.out.println(Proxy.ToString());
    }

    @Test
    public void Test2() {
        Proxy.setProxyAddress("google.com");
        Proxy.setProxyPort(8080);
        Proxy.setProxyUsername("adewale");
        Proxy.setProxyPassword("password");
        System.out.println(Proxy.ToString());
    }

    @Test
    public void Test3() {
        //Proxy proxy = new Proxy();
        Proxy proxy = null;
        System.err.println(proxy);
    }

}
