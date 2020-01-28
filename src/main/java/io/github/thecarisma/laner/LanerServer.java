package io.github.thecarisma.laner;

import java.net.ServerSocket;
import java.net.UnknownHostException;

public class LanerServer {

    ServerSocket serverSocket;
    String ipAddress;
    int port;
    int backlog = 50;

    public LanerServer(String ipAddress, int port, int backlog) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.backlog = backlog;
    }

    public LanerServer(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public LanerServer(int port) throws UnknownHostException {
        this.ipAddress = LanerNetworkInterface.getIPV4Address();
        this.port = port;
    }

}
