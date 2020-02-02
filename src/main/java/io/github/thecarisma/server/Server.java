package io.github.thecarisma.server;

import io.github.thecarisma.laner.LanerNetworkInterface;
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
    private EndpointRouter mEndpointRouter;
    private Server mServer ;

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
        while (mIsRunning) {
            mServer = this;
            Socket clientSocket = null;
            try {
                if (serverSocket.isClosed()) {
                    continue;
                }
                clientSocket = serverSocket.accept();
                final Socket finalClientSocket = clientSocket;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Request request = new Request(finalClientSocket.getInputStream());
                            Response response = new Response(mServer, finalClientSocket.getOutputStream());
                            broadcastToListeners(request, response);
                            broadcastToRouter(request, response);
                            /*//causes java.net.SocketException: Unexpected end of file from server
                            if (!finalClientSocket.isClosed()) {
                                finalClientSocket.close();
                            }*/
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            try {
                                stop();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void startServer() {
        try {
            if (!mIsRunning) {
                serverSocket = new ServerSocket(port, backlog, InetAddress.getByName(ipAddress));
                mIsRunning = true;
            }
        } catch (IOException e) {
            //broadcastToListeners(Object o);
            e.printStackTrace();
        }

    }

    @Override
    public boolean isRunning() {
        return !mIsRunning;
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

    protected void setRouter(EndpointRouter endpointRouter) {
        this.mEndpointRouter = endpointRouter;
    }

    private void broadcastToListeners(Request request, Response response) throws IOException {
        for (ServerListenerFactory serverListener : serverListener) {
            if (serverListener instanceof ServerListener) {
                ((ServerListener) serverListener).report(request, response);
            }
            if (serverListener instanceof ServerRawListener) {
                ((ServerRawListener) serverListener).report(request.in, response.out);
            }
        }
    }

    private void broadcastToRouter(Request request, Response response) throws IOException {
        if (mEndpointRouter != null) {
            mEndpointRouter.treatRequest(request, response);
        }
    }

    public String getHost() {
        return ipAddress + ":" + port;
    }


}
