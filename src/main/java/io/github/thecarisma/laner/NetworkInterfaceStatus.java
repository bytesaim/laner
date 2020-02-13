package io.github.thecarisma.laner;

import io.github.thecarisma.exceptions.Exceptor;
import io.github.thecarisma.util.TRunnable;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

//TODO: listen for ethernet
public class NetworkInterfaceStatus implements TRunnable {

    private ArrayList<LanerListener> lanerListeners = new ArrayList<>();
    private int delayInSeconds = 1;
    private ConnectionStatus status = ConnectionStatus.DISCONNECTED;
    private Timer timer;
    private ArrayList<Exceptor> exceptors = new ArrayList<>();
    private String networkInterfaceIPV4Address = "";

    public NetworkInterfaceStatus(LanerListener lanerListener, int delayInSeconds) {
        this.lanerListeners.add(lanerListener);
        this.delayInSeconds = delayInSeconds;
    }

    public NetworkInterfaceStatus(int delayInSeconds) {
        this.delayInSeconds = delayInSeconds;
    }

    public NetworkInterfaceStatus(LanerListener lanerListener) {
        this.lanerListeners.add(lanerListener);
    }

    public static boolean IsConnected() {
        return LanerNetworkInterface.isReachable("thecarisma.github.io", 80, 1000);
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
        broadcastToListeners(status);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (isConnected()) {
                        if (status == ConnectionStatus.DISCONNECTED) {
                            status = ConnectionStatus.CONNECTED;
                            broadcastToListeners(status);
                        }
                    } else {
                        if (status == ConnectionStatus.CONNECTED) {
                            status = ConnectionStatus.DISCONNECTED;
                            broadcastToListeners(status);
                        }
                    }
                } catch (SocketException e) {
                    throwException(e);
                }
            }
        }, 0, (delayInSeconds * 1000));
    }

    private void broadcastToListeners(Object o) {
        for (LanerListener lanerListener : lanerListeners) {
            lanerListener.report(o);
        }
    }

    //if the device has lot of eth networkInterfaces up
    public void onlyCheckForInterfaceWith(String networkInterfaceIPV4Address) {
        this.networkInterfaceIPV4Address = networkInterfaceIPV4Address;
    }

    public void checkAllEthernet() {
        this.networkInterfaceIPV4Address = "";
    }

    private boolean isConnected() throws SocketException {
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

    @Override
    public boolean isRunning() {
        return true;
    }

    public void stop() {
        timer.cancel();
    }

    public ArrayList<Exceptor> getExceptors() {
        return exceptors;
    }

    public void addExceptor(Exceptor exceptor) {
        this.exceptors.add(exceptor);
    }

    public void removeExceptor(Exceptor exceptor) {
        this.exceptors.remove(exceptor);
    }

    private void throwException(Exception ex) {
        if (exceptors.size() == 0) {
            ex.printStackTrace();
            return;
        }
        for (Exceptor exceptor : exceptors) {
            exceptor.thrown(ex);
        }
    }

}
