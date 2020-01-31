package io.github.thecarisma.server;

import io.github.thecarisma.laner.LanerBufferedReader;
import io.github.thecarisma.laner.LanerNetworkInterface;
import io.github.thecarisma.laner.LanerPrintWriter;
import io.github.thecarisma.util.TRunnable;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * @author Adewale Azeez "azeezadewale98@gmail.com"
 */
public class Server implements TRunnable {

    private ArrayList<ServerListenerFactory> serverListener = new ArrayList<>();
    private ServerSocket serverSocket;
    private String ipAddress;
    private int port;
    private int backlog = 50;
    private boolean mIsRunning = false;

    public Server(String ipAddress, int port, int backlog) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.backlog = backlog;
    }

    public Server(String ipAddress, int port, int backlog, ServerListenerFactory ServerListenerFactory) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.backlog = backlog;
        this.serverListener.add(ServerListenerFactory);
    }

    public Server(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public Server(String ipAddress, int port, ServerListenerFactory ServerListenerFactory) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.serverListener.add(ServerListenerFactory);
    }

    public Server(int port) throws UnknownHostException {
        this.ipAddress = LanerNetworkInterface.getIPV4Address();
        this.port = port;
    }

    public Server(int port, ServerListenerFactory ServerListenerFactory) throws UnknownHostException {
        this.ipAddress = LanerNetworkInterface.getIPV4Address();
        this.port = port;
        this.serverListener.add(ServerListenerFactory);
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

    public ArrayList<ServerListenerFactory> getserverListener() {
        return serverListener;
    }

    public void addServerListenerFactory(ServerListenerFactory ServerListenerFactory) {
        this.serverListener.add(ServerListenerFactory);
    }

    public void removeServerListenerFactory(ServerListenerFactory ServerListenerFactory) {
        this.serverListener.remove(ServerListenerFactory);
    }

    @Override
    public void run() {
        startServer();
        try {
            while (mIsRunning) {
                Socket clientSocket = null;
                try {
                    clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    System.err.println("Accept failed.");
                    System.exit(1);
                }
                LanerPrintWriter out = new LanerPrintWriter(clientSocket.getOutputStream(), true);
                LanerBufferedReader in = new LanerBufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine, outputLine;
                broadcastToListeners(new Request(in), new Response(this, out));
                if (out.isOpen()) {
                    out.close();
                }
                try {
                    in.close();
                } catch (IOException ex){}
                if (!clientSocket.isClosed()) {
                    clientSocket.close();
                }
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
            mIsRunning = true;
        } catch (IOException e) {
            //broadcastToListeners(Object o);
            e.printStackTrace();
        }

    }

    @Override
    public boolean isRunning() {
        return mIsRunning;
    }

    public void stop() throws IOException {
        //possible send a closing byte to the server to
        //initiate immediate closing
        if (mIsRunning) {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                mIsRunning = false;
            }
        }
    }

    private void broadcastToListeners(Request request, Response response) {
        for (ServerListenerFactory serverListener : serverListener) {
            if (serverListener instanceof ServerListener) {
                ((ServerListener) serverListener).report(request, response);
            }
            if (serverListener instanceof ServerReadyListener) {
                ((ServerReadyListener) serverListener).report(request.in, response.out);
            }
            if (serverListener instanceof ServerRawListener) {
                ((ServerRawListener) serverListener).report(request.in.getReader(), response.out.getWriter());
            }
        }
    }

    public String getHost() {
        return ipAddress + ":" + port;
    }


}
