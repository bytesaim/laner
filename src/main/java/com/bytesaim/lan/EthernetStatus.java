package com.bytesaim.lan;

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
