package io.github.thecarisma.server;

import io.github.thecarisma.laner.LanerListener;

import java.io.IOException;
import java.net.UnknownHostException;

public class TestServer {

    //@Test
    public void Test1() throws UnknownHostException {
        Server server = new Server("192.168.8.102",7510, new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof Request) {
                    ((Request) o).out.write("HTTP/1.0 200 OK\r\n");
                    ((Request) o).out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
                    ((Request) o).out.write("Server: Apache/0.8.4\r\n");
                    ((Request) o).out.write("Content-Type: text/html\r\n");
                    ((Request) o).out.write("Content-Length: 59\r\n");
                    ((Request) o).out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
                    ((Request) o).out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
                    ((Request) o).out.write("\r\n");
                    ((Request) o).out.write("<html><head><title>Exemple</title></head>");
                    ((Request) o).out.write("<body>Yahoo</body></html>");
                }
            }
        });
        System.out.println(server.getIpAddress());
        server.run();
    }

    //@Test
    public void TestGetRequest() {
        Server server = new Server("192.168.8.102",7510, new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof Request) {
                    System.out.println("Method: " + ((Request) o).getMethod());
                    System.out.println("Endpoint: " + ((Request) o).getEndpoint());
                    System.out.println("HTTPversion: " + ((Request) o).getHTTPversion());
                    System.out.println("Parameters:");
                    for (String s : ((Request) o).getParameters().keySet()) {
                        System.out.println("    " + s + "=" + ((Request) o).getParameters().get(s));
                    }
                    System.out.println("Headers:");
                    for (String s : ((Request) o).getHeaders().keySet()) {
                        System.out.println("    " + s + "=" + ((Request) o).getHeaders().get(s));
                    }
                    try {
                        System.out.println("Body: " + ((Request) o).getRawBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.out.println(server.getIpAddress());
        server.run();
    }

    //@Test
    public static void main(String[] args) {
        Server server = new Server("192.168.8.101",7510, new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof Request) {
                    System.out.println("Method: " + ((Request) o).getMethod());
                    System.out.println("Endpoint: " + ((Request) o).getEndpoint());
                    System.out.println("HTTPversion: " + ((Request) o).getHTTPversion());
                    System.out.println("Parameters:");
                    for (String s : ((Request) o).getParameters().keySet()) {
                        System.out.println("    " + s + "=" + ((Request) o).getParameters().get(s));
                    }
                    System.out.println("Headers:");
                    for (String s : ((Request) o).getHeaders().keySet()) {
                        System.out.println("    " + s + "=" + ((Request) o).getHeaders().get(s));
                    }
                    try {
                        System.out.println("Multipart Body:");
                        while (((Request) o).getBodyMultipartStream().hasnext()) {
                            MultipartData multipartData = ((Request) o).getBodyMultipartStream().next();
                            System.out.println("    Name: " + multipartData.getName());
                            System.out.println("    Headers: ");
                            for (String s : multipartData.getHeaders().keySet()) {
                                System.out.println("        " + s + "=" + multipartData.getHeaders().get(s));
                            }
                            System.out.println("    Body Length: " + multipartData.getBody().length());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.out.println(server.getIpAddress());
        server.run();
    }

}
