package io.github.thecarisma.server;

import io.github.thecarisma.exceptions.ResponseHeaderException;
import io.github.thecarisma.laner.LanerNetworkInterface;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;

public class TestServer {

    //@Test
    public void Test1() throws UnknownHostException {
        Server server = new Server(LanerNetworkInterface.getIPV4Address(),7510, new ServerListener() {
            @Override
            public void report(Request request, Response response) {
            }

            @Override
            public void report(BufferedReader in, PrintWriter out) {
                out.write("HTTP/1.1 200 OK\r\n");
                out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
                out.write("Server: Apache/0.8.4\r\n");
                out.write("Content-Type: text/html\r\n");
                out.write("Content-Length: 59\r\n");
                out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
                out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
                out.write("\r\n");
                out.write("<html><head><title>Exemple</title></head>");
                out.write("<body>Yahoo</body></html>");
            }
        });
        System.out.println(server.getIpAddress());
        server.run();
    }

    //@Test
    public void TestGetRequest() throws UnknownHostException {
        Server server = new Server(LanerNetworkInterface.getIPV4Address(),7510, new ServerListener() {
            @Override
            public void report(Request request, Response response) {
                System.out.println("Method: " + request.getMethod());
                System.out.println("Endpoint: " + request.getEndpoint());
                System.out.println("HTTPversion: " + request.getHttpVersion());
                System.out.println("Parameters:");
                for (String s : request.getParameters().keySet()) {
                    System.out.println("    " + s + "=" + request.getParameters().get(s));
                }
                System.out.println("Headers:");
                for (String s : request.getHeaders().keySet()) {
                    System.out.println("    " + s + "=" + request.getHeaders().get(s));
                }
                try {
                    System.out.println("Body: " + request.getBody());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void report(BufferedReader in, PrintWriter out) {

            }
        });
        System.out.println(server.getIpAddress());
        server.run();
    }

    //@Test
    public void TestPost() throws UnknownHostException {
        Server server = new Server(LanerNetworkInterface.getIPV4Address(),7510, new ServerListener() {
            @Override
            public void report(Request request, Response response) {
                System.out.println("Method: " + request.getMethod());
                System.out.println("Endpoint: " + request.getEndpoint());
                System.out.println("HTTPversion: " + request.getHttpVersion());
                System.out.println("Parameters:");
                for (String s : request.getParameters().keySet()) {
                    System.out.println("    " + s + "=" + request.getParameters().get(s));
                }
                System.out.println("Headers:");
                for (String s : request.getHeaders().keySet()) {
                    System.out.println("    " + s + "=" + request.getHeaders().get(s));
                }
                try {
                    System.out.println("Body: " + request.getBody());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println("Multipart Body:");
                    while (request.getBodyMultipartStream().hasnext()) {
                        MultipartData multipartData = request.getBodyMultipartStream().next();
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

            @Override
            public void report(BufferedReader in, PrintWriter out) {

            }
        });
        System.out.println(server.getIpAddress());
        server.run();
    }

    //@Test
    public void TestResponse() throws UnknownHostException {
        Server server = new Server(LanerNetworkInterface.getIPV4Address(),7510, new ServerListener() {
            @Override
            public void report(Request request, Response response) {
                try {
                    System.out.println("Body: " + request.getBody());
                    response.write("Hello World");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void report(BufferedReader in, PrintWriter out) {

            }
        });
        System.out.println(server.getIpAddress());
        server.run();
    }

    //@Test
    public static void main(String[] args) {
        Server server = new Server("192.168.8.100",7510, new ServerListener() {
            @Override
            public void report(Request request, Response response) {
                try {
                    response.appendHeader("", "");
                    response.write("Hello World");
                } catch (ResponseHeaderException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void report(BufferedReader in, PrintWriter out) {

            }
        });
        System.out.println(server.getIpAddress());
        server.run();
    }

}
