package io.github.thecarisma.laner;

import io.github.thecarisma.exceptions.InvalidArgumentException;
import org.junit.Test;

public class TestProxy {

    @Test
    public void TestException() {
        Proxy.setUseProxy(true);
        try {
            Proxy.setProxyAddress("https://google.com");
            Proxy.setProxyPort(8080);
            System.out.println(Proxy.ToString());
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test1() {
        Proxy.setUseProxy(true);
        try {
            Proxy.setProxyAddress("google.com");
            Proxy.setProxyPort(8080);
            System.out.println(Proxy.ToString());
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test2() throws InvalidArgumentException {
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
