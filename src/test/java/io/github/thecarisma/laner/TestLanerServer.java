package io.github.thecarisma.laner;

import org.junit.Test;

import java.io.IOException;
import java.net.UnknownHostException;

public class TestLanerServer {

    //@Test
    public void Test1() throws UnknownHostException {
        LanerServer lanerServer = new LanerServer("192.168.8.102",7510, new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof LanerServer.LanerServerRequest) {
                    ((LanerServer.LanerServerRequest) o).out.write("HTTP/1.0 200 OK\r\n");
                    ((LanerServer.LanerServerRequest) o).out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
                    ((LanerServer.LanerServerRequest) o).out.write("Server: Apache/0.8.4\r\n");
                    ((LanerServer.LanerServerRequest) o).out.write("Content-Type: text/html\r\n");
                    ((LanerServer.LanerServerRequest) o).out.write("Content-Length: 59\r\n");
                    ((LanerServer.LanerServerRequest) o).out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
                    ((LanerServer.LanerServerRequest) o).out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
                    ((LanerServer.LanerServerRequest) o).out.write("\r\n");
                    ((LanerServer.LanerServerRequest) o).out.write("<html><head><title>Exemple</title></head>");
                    ((LanerServer.LanerServerRequest) o).out.write("<body>Yahoo</body></html>");
                }
            }
        });
        System.out.println(lanerServer.getIpAddress());
        lanerServer.run();
    }

    //@Test
    public static void main(String[] args) {
        LanerServer lanerServer = new LanerServer("192.168.8.102",7510, new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof LanerServer.LanerServerRequest) {
                    System.out.println("Method: " + ((LanerServer.LanerServerRequest) o).method);
                    System.out.println("Endpoint: " + ((LanerServer.LanerServerRequest) o).endpoint);
                    System.out.println("HTTPversion: " + ((LanerServer.LanerServerRequest) o).HTTPversion);
                    System.out.println("Parameters:");
                    for (String s : ((LanerServer.LanerServerRequest) o).parameters.keySet()) {
                        System.out.println("    " + s + "=" + ((LanerServer.LanerServerRequest) o).parameters.get(s));
                    }
                    System.out.println("Headers:");
                    for (String s : ((LanerServer.LanerServerRequest) o).headers.keySet()) {
                        System.out.println("    " + s + "=" + ((LanerServer.LanerServerRequest) o).headers.get(s));
                    }
                    try {
                        System.out.println("Body: " + ((LanerServer.LanerServerRequest) o).getRequestBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.out.println(lanerServer.getIpAddress());
        lanerServer.run();
    }

}
