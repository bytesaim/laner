package com.bytesaim.server;

import com.bytesaim.laner.LanerNetworkInterface;
import com.bytesaim.util.TimedTRunnableKiller;
import com.bytesaim.exceptions.ResponseHeaderException;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class TestServer {

    @Test
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
                out.close();
            }
        });
        System.out.println(server.getIpAddress());
        new Thread(server).start();
        TimedTRunnableKiller.timeTRunnableDeath(server, 10);
    }

    @Test
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
                    System.out.println("Body: " + new String(request.getBody(), StandardCharsets.UTF_8));
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(server.getIpAddress());
        new Thread(server).start();
        TimedTRunnableKiller.timeTRunnableDeath(server, 10);
    }

    @Test
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
                    System.out.println("Body: " + new String(request.getBody(), StandardCharsets.UTF_8));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println("Multipart Body:");
                    while (request.getBodyMultipartStream().hasNext()) {
                        MultipartData multipartData = request.getBodyMultipartStream().next();
                        System.out.println("    Name: " + multipartData.getName());
                        System.out.println("    Headers: ");
                        for (String s : multipartData.getHeaders().keySet()) {
                            System.out.println("        " + s + "=" + multipartData.getHeaders().get(s));
                        }
                        System.out.println("    Body Length: " + multipartData.getBody().length);
                    }
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(server.getIpAddress());
        new Thread(server).start();
        TimedTRunnableKiller.timeTRunnableDeath(server, 10);
    }

    @Test
    public void TestResponse() throws UnknownHostException {
        Server server = new Server(LanerNetworkInterface.getIPV4Address(),7510, new ServerListener() {
            @Override
            public void report(Request request, Response response) {
                try {
                    System.out.println("Body: " + new String(request.getBody(), StandardCharsets.UTF_8));
                    response.write("Hello World".getBytes());
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(server.getIpAddress());
        new Thread(server).start();
        TimedTRunnableKiller.timeTRunnableDeath(server, 10);
    }

    @Test
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
                    response.close();
                } catch (ResponseHeaderException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(server.getIpAddress());
        new Thread(server).start();
        TimedTRunnableKiller.timeTRunnableDeath(server, 10);
    }

    @Test
    public void TestSendFile() throws UnknownHostException {
        Server server = new Server(LanerNetworkInterface.getIPV4Address(),7510, new ServerListener() {
            @Override
            public void report(Request request, Response response) {
                try {
                    response.sendFile(new File(".\\src\\main\\resources\\logo.png"));
                    response.close();
                } catch (ResponseHeaderException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(server.getIpAddress());
        new Thread(server).start();
        TimedTRunnableKiller.timeTRunnableDeath(server, 10);
    }

    @Test
    public void TestDownload() throws UnknownHostException {
    //public static void main(String[] args) throws UnknownHostException {
        Server server = new Server(LanerNetworkInterface.getIPV4Address(),7510, new ServerListener() {
            @Override
            public void report(Request request, Response response) {
                try {
                    response.setBufferSize(50000);
                    response.downloadFile(new File(".\\src\\main\\resources\\logo.png"), "logo.png");
                    response.close();
                } catch (ResponseHeaderException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(server.getIpAddress());
        new Thread(server).start();
        TimedTRunnableKiller.timeTRunnableDeath(server, 10);
    }

    public static void main1(String[] args) throws Exception {
        Server server = new Server("192.168.8.102",12345, new ServerListener() {
            @Override
            public void report(Request request, Response response) {
                try {
                    //System.out.println(new String(new String(request.getBody(), StandardCharsets.UTF_8)));
                    MultipartStream multipartStream = request.getBodyMultipartStream();
                    int i = 1;
                    while (multipartStream.hasNext()) {
                        MultipartData multipartData = multipartStream.next();
                        try (FileOutputStream fos = new FileOutputStream("C:\\Users\\azeez\\Documents\\JUNKS\\3\\" + (++i) + ".png")) {
                            fos.write(multipartData.getBody());
                        }
                    }
                    response.close("ok");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        new Thread(server).start();
        TimedTRunnableKiller.timeTRunnableDeath(server, 10);
    }

    public static String getRawBody(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        rd.close();
        return result.toString();
    }

    //this test honors the Range header
    //TODO: needs keep alive connection
    public static void main2(String[] args) throws UnknownHostException {
        Server server = new Server(LanerNetworkInterface.getIPV4Address(),7510, new ServerListener() {
            @Override
            public void report(Request request, Response response) {
                try {
                    response.setBufferSize(512);
                    if (request.getHeaders().get("Range") != null) {
                        String range = request.getHeaders().get("Range");
                        int from = Integer.parseInt(range.substring(range.indexOf("=")+1, range.indexOf("-")));
                        String to_ = range.substring(range.indexOf("-")+1);
                        int to = Integer.parseInt((!to_.isEmpty() ? to_ : "0"));
                        response.setStatusCode(StatusCode.PARTIAL_CONTENT);
                        response.sendFileInRange(new File("C:\\Users\\azeez\\Videos\\Video\\Identity_Thief.mp4"),
                                from, to);
                    } else {
                        response.sendFile(new File("C:\\Users\\azeez\\Videos\\Video\\Identity_Thief"));
                    }
                    response.close();
                } catch (ResponseHeaderException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(server.getIpAddress());
        new Thread(server).start();
        //TimedTRunnableKiller.timeTRunnableDeath(server, 10);
    }

}
