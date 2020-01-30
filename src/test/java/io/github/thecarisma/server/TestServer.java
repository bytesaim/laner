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
                if (o instanceof ServerRequest) {
                    ((ServerRequest) o).out.write("HTTP/1.0 200 OK\r\n");
                    ((ServerRequest) o).out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
                    ((ServerRequest) o).out.write("Server: Apache/0.8.4\r\n");
                    ((ServerRequest) o).out.write("Content-Type: text/html\r\n");
                    ((ServerRequest) o).out.write("Content-Length: 59\r\n");
                    ((ServerRequest) o).out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
                    ((ServerRequest) o).out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
                    ((ServerRequest) o).out.write("\r\n");
                    ((ServerRequest) o).out.write("<html><head><title>Exemple</title></head>");
                    ((ServerRequest) o).out.write("<body>Yahoo</body></html>");
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
                if (o instanceof ServerRequest) {
                    System.out.println("Method: " + ((ServerRequest) o).method);
                    System.out.println("Endpoint: " + ((ServerRequest) o).endpoint);
                    System.out.println("HTTPversion: " + ((ServerRequest) o).HTTPversion);
                    System.out.println("Parameters:");
                    for (String s : ((ServerRequest) o).getParameters().keySet()) {
                        System.out.println("    " + s + "=" + ((ServerRequest) o).getParameters().get(s));
                    }
                    System.out.println("Headers:");
                    for (String s : ((ServerRequest) o).getHeaders().keySet()) {
                        System.out.println("    " + s + "=" + ((ServerRequest) o).getHeaders().get(s));
                    }
                    try {
                        System.out.println("Body: " + ((ServerRequest) o).getRawBody());
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
                if (o instanceof ServerRequest) {
                    System.out.println("Method: " + ((ServerRequest) o).method);
                    System.out.println("Endpoint: " + ((ServerRequest) o).endpoint);
                    System.out.println("HTTPversion: " + ((ServerRequest) o).HTTPversion);
                    System.out.println("Parameters:");
                    for (String s : ((ServerRequest) o).getParameters().keySet()) {
                        System.out.println("    " + s + "=" + ((ServerRequest) o).getParameters().get(s));
                    }
                    System.out.println("Headers:");
                    for (String s : ((ServerRequest) o).getHeaders().keySet()) {
                        System.out.println("    " + s + "=" + ((ServerRequest) o).getHeaders().get(s));
                    }
                    try {
                        System.out.println("Multipart Body:");
                        while (((ServerRequest) o).getBodyMultipartStream().hasnext()) {
                            MultipartData multipartData = ((ServerRequest) o).getBodyMultipartStream().next();
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
