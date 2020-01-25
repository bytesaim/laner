package io.github.thecarisma;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class UpNetworkInterface {

    public static ArrayList<NetworkInterface> getNetworkInterfaces() throws SocketException {
        ArrayList<NetworkInterface> networkInterfaces = new ArrayList<>();
        Enumeration<NetworkInterface> eni = NetworkInterface.getNetworkInterfaces();
        while(eni.hasMoreElements()) {
            NetworkInterface networkInterface = eni.nextElement();
            if (!networkInterface.isUp()) {
                continue;
            }
            networkInterfaces.add(networkInterface);
        }
        return networkInterfaces;
    }

    public static ArrayList<NetworkInterface> getNetworkInterfacesNoLoopback() throws SocketException {
        ArrayList<NetworkInterface> networkInterfaces = new ArrayList<>();
        Enumeration<NetworkInterface> eni = NetworkInterface.getNetworkInterfaces();
        while(eni.hasMoreElements()) {
            NetworkInterface networkInterface = eni.nextElement();
            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue;
            }
            networkInterfaces.add(networkInterface);
        }
        return networkInterfaces;
    }

    public static ArrayList<InetAddress> getInetAddresses(NetworkInterface networkInterface) {
        return Collections.list(networkInterface.getInetAddresses());
    }

    public static String getDefaultGateway(InetAddress inetAddress) throws UnknownHostException, SocketException {
        try(DatagramSocket s=new DatagramSocket())
        {
            s.connect(inetAddress, 0);
            return new String(NetworkInterface.getByInetAddress(s.getLocalAddress()).getHardwareAddress(), StandardCharsets.UTF_8);
        }
    }

}
