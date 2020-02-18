package com.bytesaim.laner;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;

public class WifiStatus extends NetworkInterfaceStatusByName {

    public WifiStatus(LanerListener lanerListener, int delayInSeconds) {
        super(lanerListener, delayInSeconds, "wlan");
    }

    public WifiStatus(int delayInSeconds) {
        super(delayInSeconds, "wlan");
    }

    public WifiStatus(LanerListener lanerListener) {
        super(lanerListener, "wlan");
    }

}
