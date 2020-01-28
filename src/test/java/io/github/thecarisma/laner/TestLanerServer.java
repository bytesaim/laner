package io.github.thecarisma.laner;

import org.junit.Test;

import java.net.UnknownHostException;

public class TestLanerServer {

    //@Test
    public void Test1() throws UnknownHostException {
        LanerServer lanerServer = new LanerServer(2020, new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof LanerServer.LanerServerStream) {
                    ((LanerServer.LanerServerStream) o).out.write("HTTP/1.0 200 OK\r\n");
                    ((LanerServer.LanerServerStream) o).out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
                    ((LanerServer.LanerServerStream) o).out.write("Server: Apache/0.8.4\r\n");
                    ((LanerServer.LanerServerStream) o).out.write("Content-Type: text/html\r\n");
                    ((LanerServer.LanerServerStream) o).out.write("Content-Length: 59\r\n");
                    ((LanerServer.LanerServerStream) o).out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
                    ((LanerServer.LanerServerStream) o).out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
                    ((LanerServer.LanerServerStream) o).out.write("\r\n");
                    ((LanerServer.LanerServerStream) o).out.write("<html><head><title>Exemple</title></head>");
                    ((LanerServer.LanerServerStream) o).out.write("<body>Yahoo</body></html>");
                }
            }
        });
        System.out.println(lanerServer.getIpAddress());
        lanerServer.run();
    }

}
