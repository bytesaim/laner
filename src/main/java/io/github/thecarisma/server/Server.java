package io.github.thecarisma.server;

import io.github.thecarisma.laner.LanerNetworkInterface;
import io.github.thecarisma.laner.LanerPrintWriter;
import io.github.thecarisma.util.TRunnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * @author Adewale Azeez "azeezadewale98@gmail.com"
 */
public class Server implements TRunnable {

    private ArrayList<ServerListener> serverListener = new ArrayList<>();
    ServerSocket serverSocket;
    private String ipAddress;
    private int port;
    private int backlog = 50;
    private boolean mIsRunning = false;

    public Server(String ipAddress, int port, int backlog) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.backlog = backlog;
    }

    public Server(String ipAddress, int port, int backlog, ServerListener ServerListener) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.backlog = backlog;
        this.serverListener.add(ServerListener);
    }

    public Server(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public Server(String ipAddress, int port, ServerListener ServerListener) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.serverListener.add(ServerListener);
    }

    public Server(int port) throws UnknownHostException {
        this.ipAddress = LanerNetworkInterface.getIPV4Address();
        this.port = port;
    }

    public Server(int port, ServerListener ServerListener) throws UnknownHostException {
        this.ipAddress = LanerNetworkInterface.getIPV4Address();
        this.port = port;
        this.serverListener.add(ServerListener);
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

    public ArrayList<ServerListener> getserverListener() {
        return serverListener;
    }

    public void addServerListener(ServerListener ServerListener) {
        this.serverListener.add(ServerListener);
    }

    public void removeServerListener(ServerListener ServerListener) {
        this.serverListener.remove(ServerListener);
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
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine, outputLine;
                broadcastToListeners(new Request(in), new Response(out));
                broadcastToListeners(in, out);
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
        for (ServerListener serverListener : serverListener) {
            serverListener.report(request, response);
        }
    }

    private void broadcastToListeners(BufferedReader in, PrintWriter out) {
        for (ServerListener serverListener : serverListener) {
            serverListener.report(in, out);
        }
    }

}
