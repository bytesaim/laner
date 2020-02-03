package io.github.thecarisma.laner;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.util.Iterator;
import java.util.List;

public class ProxyFields {

    public String hostName;

    public int hostPort;

    public boolean proxyEnabled;

    public ProxyFields() {
        System.setProperty("java.net.useSystemProxies", "true");
        Proxy proxy = getProxyConfig();
        if (proxy != null) {
            InetSocketAddress addr = (InetSocketAddress) proxy.address();
            if (addr != null) {
                proxyEnabled = true ;
                hostName = addr.getHostName();
                hostPort = addr.getPort();
                System.out.println(addr.getHostString());
                System.out.println(addr.getAddress());
                return;
            }
        }
        hostName = "";
        hostPort = 8080 ;
        proxyEnabled = false;
    }

    private Proxy getProxyConfig() {
        List<Proxy> l = null;
        try {
            l = ProxySelector.getDefault().select(new URI("http://www.google.com"));
            if (l != null) {
                for (Proxy proxy : l) {
                    return proxy;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public String toString() {
        return ProxyFields.class.getName() + "@" + ":Host=" + hostName + ",Port=" + hostPort;
    }

}
