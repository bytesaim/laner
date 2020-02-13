package io.github.thecarisma.laner;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;

public class WifiStatus extends EthernetStatus {

    public WifiStatus(LanerListener lanerListener, int delayInSeconds) {
        super(lanerListener, delayInSeconds);
    }

    public WifiStatus(int delayInSeconds) {
        super(delayInSeconds);
    }

    public WifiStatus(LanerListener lanerListener) {
        super(lanerListener);
    }

    @Override
    protected boolean isConnected() throws SocketException {
        boolean containsEth = false;
        ArrayList<NetworkInterface> networkInterfaces =  LanerNetworkInterface.getNetworkInterfacesNoLoopback();
        for (NetworkInterface networkInterface : networkInterfaces) {
            if (networkInterface.getName().startsWith("wlan")) {
                if (!this.networkInterfaceIPV4Address.isEmpty()) {
                    ArrayList<InetAddress> addresses = LanerNetworkInterface.getInetAddresses(networkInterface);
                    for (InetAddress address : addresses) {
                        if (this.networkInterfaceIPV4Address.equals(address.getHostAddress())) {
                            containsEth = true;
                            break;
                        }
                    }
                } else {
                    containsEth = true;
                    break;
                }
            }
        }
        return containsEth;
    }


}
