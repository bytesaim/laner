package io.github.thecarisma.laner;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class LanerServer implements Runnable {

    private ArrayList<LanerListener> lanerListeners = new ArrayList<>();
    ServerSocket serverSocket;
    String ipAddress;
    int port;
    int backlog = 50;
    private boolean serverStarted = false;
    private boolean stopServer = false;

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
        if (!serverStarted) {
            startServer();
        }
    }

    private void startServer() {
        try {
            serverSocket = new ServerSocket(port, backlog, InetAddress.getByName(ipAddress));
            serverStarted = true;
        } catch (IOException e) {
            //broadcastToListeners(Object o);
            e.printStackTrace();
        }

    }

    public void stop() {
        stopServer = true;
        //possible send a closing byte to the server to
        //initiate immediate closing
        LanerNetworkInterface.isReachable(ipAddress, port, 100);
    }

    private void broadcastToListeners(Object o) {
        for (LanerListener lanerListener : lanerListeners) {
            lanerListener.report(o);
        }
    }
}
