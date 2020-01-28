package io.github.thecarisma.laner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class LanerServer implements Runnable {

    private ArrayList<LanerListener> lanerListeners = new ArrayList<>();
    ServerSocket serverSocket;
    String ipAddress;
    int port;
    int backlog = 50;
    private boolean isRunning = false;

    public LanerServer(String ipAddress, int port, int backlog) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.backlog = backlog;
    }

    public LanerServer(String ipAddress, int port, int backlog, LanerListener lanerListener) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.backlog = backlog;
        this.lanerListeners.add(lanerListener);
    }

    public LanerServer(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public LanerServer(String ipAddress, int port, LanerListener lanerListener) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.lanerListeners.add(lanerListener);
    }

    public LanerServer(int port) throws UnknownHostException {
        this.ipAddress = LanerNetworkInterface.getIPV4Address();
        this.port = port;
    }

    public LanerServer(int port, LanerListener lanerListener) throws UnknownHostException {
        this.ipAddress = LanerNetworkInterface.getIPV4Address();
        this.port = port;
        this.lanerListeners.add(lanerListener);
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }

    public int getBacklog() {
        return backlog;
    }

    public ArrayList<LanerListener> getLanerListeners() {
        return lanerListeners;
    }

    public void addLanerListener(LanerListener lanerListener) {
        this.lanerListeners.add(lanerListener);
    }

    public void removeLanerListener(LanerListener lanerListener) {
        this.lanerListeners.remove(lanerListener);
    }

    @Override
    public void run() {
        startServer();
        try {
            while (isRunning) {
                Socket clientSocket = null;
                try {
                    clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    System.err.println("Accept failed.");
                    System.exit(1);
                }
                LanerPrintWriter out = new LanerPrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine, outputLine;
                broadcastToListeners(new LanerServerStream(out, in));
                KnockKnockProtocol kkp = new KnockKnockProtocol();

                outputLine = kkp.processInput(null);
                out.write("HTTP/1.0 200 OK\r\n");
                out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
                out.write("Server: Apache/0.8.4\r\n");
                out.write("Content-Type: text/html\r\n");
                out.write("Content-Length: 59\r\n");
                out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
                out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
                out.write("\r\n");
                out.write("<html><head><title>Exemple</title></head>");
                out.write("<body>Ceci est une page d'exemple.</body></html>");
                //out.write(outputLine);

                while ((inputLine = in.readLine()) != null) {
                    //outputLine = kkp.processInput(inputLine);
                    //out.println(outputLine);
                    //if (outputLine.equals("Bye."))
                    //    break;
                    //System.out.println(inputLine);
                    break;
                }
                if (out.isOpen()) {
                    out.close();
                }
                if (in.isOpen()) {
                    in.close();
                }
                clientSocket.close();
            }
        } catch (IOException ex) {
            //broadcastToListeners(Object o);
            ex.printStackTrace();
            try {
                stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void startServer() {
        try {
            serverSocket = new ServerSocket(port, backlog, InetAddress.getByName(ipAddress));
            isRunning = true;
        } catch (IOException e) {
            //broadcastToListeners(Object o);
            e.printStackTrace();
        }

    }

    public void stop() throws IOException {
        //possible send a closing byte to the server to
        //initiate immediate closing
        if (isRunning) {
            LanerNetworkInterface.isReachable(ipAddress, port, 100);
            if (serverSocket != null) {
                serverSocket.close();
                isRunning = false;
            }
        }
    }

    private void broadcastToListeners(Object o) {
        for (LanerListener lanerListener : lanerListeners) {
            lanerListener.report(o);
        }
    }

    public static class LanerServerStream {
        public PrintWriter out;
        public BufferedReader in;

        public LanerServerStream(PrintWriter out, BufferedReader in) {
            this.out = out;
            this.in = in;
        }
    }

}
