package io.github.thecarisma.laner;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;

public class EthernetStatus extends NetworkInterfaceStatus {

    private String networkInterfaceIPV4Address = "";

    public EthernetStatus(LanerListener lanerListener, int delayInSeconds) {
        super(lanerListener, delayInSeconds);
    }

    public EthernetStatus(int delayInSeconds) {
        super(delayInSeconds);
    }

    public EthernetStatus(LanerListener lanerListener) {
        super(lanerListener);
    }

    //if the device has lot of eth networkInterfaces up
    public void onlyCheckForInterfaceWith(String networkInterfaceIPV4Address) {
        this.networkInterfaceIPV4Address = networkInterfaceIPV4Address;
    }

    public void checkAllEthernet() {
        this.networkInterfaceIPV4Address = "";
    }

    @Override
    protected boolean isConnected() throws SocketException {
        boolean containsEth = false;
        ArrayList<NetworkInterface> networkInterfaces =  LanerNetworkInterface.getNetworkInterfacesNoLoopback();
        for (NetworkInterface networkInterface : networkInterfaces) {
            if (networkInterface.getName().startsWith("eth")) {
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
