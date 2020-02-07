package io.github.thecarisma.server;

import io.github.thecarisma.laner.LanerNetworkInterface;
import io.github.thecarisma.laner.LanerProxyConfig;
import io.github.thecarisma.util.TRunnable;

import java.io.*;
import java.net.*;
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
                if (!mIsRunning) {
                    serverSocket.close();
                    break;
                }
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
                if (serverSocket == null) {
                    System.setProperty("sun.net.useExclusiveBind", "true");
                    serverSocket = new ServerSocket();
                    serverSocket.setReuseAddress(true);
                    serverSocket.bind(new InetSocketAddress(InetAddress.getByName(ipAddress), port), backlog);
                }
                mIsRunning = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean isRunning() {
        return mIsRunning;
    }

    public void stop() throws IOException {
        if (mIsRunning) {
            if (serverSocket != null && !serverSocket.isClosed()) {
                mIsRunning = false;
                if (LanerProxyConfig.isProxyEnabled()) {
                    Proxy proxy = new Proxy(
                            Proxy.Type.HTTP,
                            new InetSocketAddress(LanerProxyConfig.getProxyHost(), LanerProxyConfig.getProxyPort()));
                    if (!LanerProxyConfig.getProxyUsername().isEmpty()) {
                        Authenticator authenticator = new Authenticator() {
                            public PasswordAuthentication getPasswordAuthentication() {
                                return (new PasswordAuthentication(
                                        LanerProxyConfig.getProxyUsername(),
                                        LanerProxyConfig.getProxyPassword().toCharArray()));
                            }
                        };
                        Authenticator.setDefault(authenticator);
                    }
                    Socket server = new Socket(proxy);
                    server.connect(new InetSocketAddress(serverSocket.getInetAddress(), serverSocket.getLocalPort()));
                    server.close();
                } else {
                    new Socket(serverSocket.getInetAddress(), serverSocket.getLocalPort()).close();
                }
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

    public void changeHost(String ipAddress, int port) {
        if (mIsRunning) {
            throw new IllegalStateException("You cannot change the host while the server is running. call stop() first.");
        }
        this.ipAddress = ipAddress;
        this.port = port;
        serverSocket = null;
    }

    public String getHost() {
        return ipAddress + ":" + port;
    }

}
