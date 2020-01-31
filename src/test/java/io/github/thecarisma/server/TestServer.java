package io.github.thecarisma.server;

import io.github.thecarisma.exceptions.ResponseHeaderException;
import io.github.thecarisma.laner.LanerNetworkInterface;
import org.junit.Test;

import java.io.*;
import java.net.UnknownHostException;

public class TestServer {

    //@Test
    public void Test1() throws UnknownHostException {
        Server server = new Server(LanerNetworkInterface.getIPV4Address(),7510, new ServerRawListener() {
            @Override
            public void report(InputStream in, OutputStream out) throws IOException {
                out.write("HTTP/1.1 200 OK\r\n".getBytes());
                out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n".getBytes());
                out.write("Server: Apache/0.8.4\r\n".getBytes());
                out.write("Content-Type: text/html\r\n".getBytes());
                out.write("Content-Length: 59\r\n".getBytes());
                out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n".getBytes());
                out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n".getBytes());
                out.write("\r\n".getBytes());
                out.write("<html><head><title>Exemple</title></head>".getBytes());
                out.write("<body>Yahoo</body></html>".getBytes());
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
                    response.write("Hello World".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(server.getIpAddress());
        server.run();
    }

    //@Test
    public void TestResponseHeader() throws UnknownHostException {
        Server server = new Server(LanerNetworkInterface.getIPV4Address(),7510, new ServerListener() {
            @Override
            public void report(Request request, Response response) {
                try {
                    response.setStatusCode(StatusCode.OK);
                    response.appendHeader("Accept", "application/json", "application/pdf");
                    response.appendHeader("Content-Type", "text/html");
                    response.write(("<html>\n" +
                            "<body>\n" +
                            "<h1>Hello, World!</h1>\n" +
                            "</body>\n" +
                            "</html>").getBytes());
                } catch (ResponseHeaderException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(server.getIpAddress());
        server.run();
    }

    //@Test
    public void TestSendFile() throws UnknownHostException {
        Server server = new Server(LanerNetworkInterface.getIPV4Address(),7510, new ServerListener() {
            @Override
            public void report(Request request, Response response) {
                try {
                    response.sendFile(new File(".\\src\\main\\resources\\logo.png"));
                } catch (ResponseHeaderException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(server.getIpAddress());
        server.run();
    }

    //@Test
    public void TestDownload() throws UnknownHostException {
        Server server = new Server(LanerNetworkInterface.getIPV4Address(),7510, new ServerListener() {
            @Override
            public void report(Request request, Response response) {
                try {
                    response.downloadFile(new File(".\\src\\main\\resources\\logo.png"), "logo.png");
                } catch (ResponseHeaderException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(server.getIpAddress());
        server.run();
    }

    //@Test
    public static void main(String[] args) throws UnknownHostException {
        Server server = new Server("172.16.40.27",7510, new ServerListener() {
            @Override
            public void report(Request request, Response response) {
                try {
                    System.out.print(request.getBody());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(server.getIpAddress());
        server.run();
    }

}
