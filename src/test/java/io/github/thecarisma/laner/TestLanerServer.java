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
                if (o instanceof LanerServerRequest) {
                    ((LanerServerRequest) o).out.write("HTTP/1.0 200 OK\r\n");
                    ((LanerServerRequest) o).out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
                    ((LanerServerRequest) o).out.write("Server: Apache/0.8.4\r\n");
                    ((LanerServerRequest) o).out.write("Content-Type: text/html\r\n");
                    ((LanerServerRequest) o).out.write("Content-Length: 59\r\n");
                    ((LanerServerRequest) o).out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
                    ((LanerServerRequest) o).out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
                    ((LanerServerRequest) o).out.write("\r\n");
                    ((LanerServerRequest) o).out.write("<html><head><title>Exemple</title></head>");
                    ((LanerServerRequest) o).out.write("<body>Yahoo</body></html>");
                }
            }
        });
        System.out.println(lanerServer.getIpAddress());
        lanerServer.run();
    }

    //@Test
    public void TestGetRequest() {
        LanerServer lanerServer = new LanerServer("192.168.8.102",7510, new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof LanerServerRequest) {
                    System.out.println("Method: " + ((LanerServerRequest) o).method);
                    System.out.println("Endpoint: " + ((LanerServerRequest) o).endpoint);
                    System.out.println("HTTPversion: " + ((LanerServerRequest) o).HTTPversion);
                    System.out.println("Parameters:");
                    for (String s : ((LanerServerRequest) o).parameters.keySet()) {
                        System.out.println("    " + s + "=" + ((LanerServerRequest) o).parameters.get(s));
                    }
                    System.out.println("Headers:");
                    for (String s : ((LanerServerRequest) o).headers.keySet()) {
                        System.out.println("    " + s + "=" + ((LanerServerRequest) o).headers.get(s));
                    }
                    try {
                        System.out.println("Body: " + ((LanerServerRequest) o).getRawBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
                if (o instanceof LanerServerRequest) {
                    System.out.println("Method: " + ((LanerServerRequest) o).method);
                    System.out.println("Endpoint: " + ((LanerServerRequest) o).endpoint);
                    System.out.println("HTTPversion: " + ((LanerServerRequest) o).HTTPversion);
                    System.out.println("Parameters:");
                    for (String s : ((LanerServerRequest) o).parameters.keySet()) {
                        System.out.println("    " + s + "=" + ((LanerServerRequest) o).parameters.get(s));
                    }
                    System.out.println("Headers:");
                    for (String s : ((LanerServerRequest) o).headers.keySet()) {
                        System.out.println("    " + s + "=" + ((LanerServerRequest) o).headers.get(s));
                    }
                    try {
                        System.out.println("Multipart Body:");
                            System.out.println("Body: " + ((LanerServerRequest) o).getBodyAsMultipart());
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
