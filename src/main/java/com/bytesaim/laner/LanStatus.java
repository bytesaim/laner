package com.bytesaim.laner;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;

public class LanStatus extends NetworkInterfaceStatusByName {

    public LanStatus(LanerListener lanerListener, int delayInSeconds) {
        super(lanerListener, delayInSeconds, "eth", "wlan");
    }

    public LanStatus(int delayInSeconds) {
        super(delayInSeconds, "eth", "wlan");
    }

    public LanStatus(LanerListener lanerListener) {
        super(lanerListener, "eth", "wlan");
    }


}
