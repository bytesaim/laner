package io.github.thecarisma.laner;

import org.junit.Test;

public class TestProxyFields {

    @Test
    public void Test1() {
        ProxyFields proxyFields = new ProxyFields();
        System.out.println(proxyFields.hostName);
        System.out.println(proxyFields.hostPort);
    }

    @Test
    public void TestToString() {
        ProxyFields proxyFields = new ProxyFields();
        System.out.println(proxyFields);
    }

}
