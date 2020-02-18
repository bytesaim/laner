package com.bytesaim.laner;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;

public class EthernetStatus extends NetworkInterfaceStatusByName {

    public EthernetStatus(LanerListener lanerListener, int delayInSeconds) {
        super(lanerListener, delayInSeconds, "eth");
    }

    public EthernetStatus(int delayInSeconds) {
        super(delayInSeconds, "eth");
    }

    public EthernetStatus(LanerListener lanerListener) {
        super(lanerListener, "eth");
    }
}
