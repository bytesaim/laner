package com.bytesaim.lan;

import com.bytesaim.exceptions.InvalidArgumentException;
import org.junit.Test;

public class TestLanerProxyConfig {

    @Test
    public void TestException() {
        LanerProxyConfig.enableProxy(true);
        try {
            LanerProxyConfig.setProxyHost("https://google.com");
            LanerProxyConfig.setProxyPort(8080);
            System.out.println(LanerProxyConfig.ToString());
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test1() {
        LanerProxyConfig.enableProxy(true);
        try {
            LanerProxyConfig.setProxyHost("google.com");
            LanerProxyConfig.setProxyPort(8080);
            System.out.println(LanerProxyConfig.ToString());
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test2() throws InvalidArgumentException {
        LanerProxyConfig.setProxyHost("google.com");
        LanerProxyConfig.setProxyPort(8080);
        LanerProxyConfig.setProxyUsername("adewale");
        LanerProxyConfig.setProxyPassword("password");
        System.out.println(LanerProxyConfig.ToString());
    }

    @Test
    public void Test3() {
        //Proxy proxy = new Proxy();
        LanerProxyConfig lanerProxyConfig = null;
        System.err.println(lanerProxyConfig);
    }

}
