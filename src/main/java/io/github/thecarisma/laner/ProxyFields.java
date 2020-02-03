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

    public ProxyFields() {
        System.setProperty("java.net.useSystemProxies", "true");
        Proxy proxy = getProxyConfig();
        if (proxy != null) {
            InetSocketAddress addr = (InetSocketAddress) proxy.address();
            hostName = addr.getHostName();
            hostPort = addr.getPort();
        } else {
            hostName = "";
            hostPort = 8080 ;
        }
    }

    private Proxy getProxyConfig() {
        List<Proxy> l = null;
        try {
            ProxySelector def = ProxySelector.getDefault();

            l = def.select(new URI("http://example.com"));
            ProxySelector.setDefault(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (l != null) {
            for (Proxy proxy : l) {
                return proxy;
            }
        }
        return null;
    }

}
