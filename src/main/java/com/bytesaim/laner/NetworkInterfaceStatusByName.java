package com.bytesaim.laner;

import com.bytesaim.exceptions.Exceptor;
import com.bytesaim.util.TRunnable;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class NetworkInterfaceStatusByName implements TRunnable {

    ArrayList<String> interfacesNames = new ArrayList<>();
    protected String networkInterfaceIPV4Address = "";
    private ArrayList<LanerListener> lanerListeners = new ArrayList<>();
    private int delayInSeconds = 1;
    private ConnectionStatus status = ConnectionStatus.DISCONNECTED;
    private Timer timer;
    private ArrayList<Exceptor> exceptors = new ArrayList<>();
    private boolean isListening = false;

    public NetworkInterfaceStatusByName(LanerListener lanerListener, int delayInSeconds, String... names) {
        interfacesNames.addAll(Arrays.asList(names));
        this.lanerListeners.add(lanerListener);
        this.delayInSeconds = delayInSeconds;
    }

    public NetworkInterfaceStatusByName(int delayInSeconds, String... names) {
        interfacesNames.addAll(Arrays.asList(names));
        this.delayInSeconds = delayInSeconds;
    }

    public NetworkInterfaceStatusByName(LanerListener lanerListener, String... names) {
        interfacesNames.addAll(Arrays.asList(names));
        this.lanerListeners.add(lanerListener);
    }

    public ArrayList<String> getInterfacesNames() {
        return interfacesNames;
    }

    public void clearInterfacesNames() {
        interfacesNames = new ArrayList<>();
    }

    public void addInterfacesName(String interfaceName) {
        this.interfacesNames.add(interfaceName);
    }

    public void removeInterfacesNames(String interfaceName) {
        this.interfacesNames.remove(interfaceName);
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
        isListening = true;
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

    protected boolean isConnected() throws SocketException {
        boolean containsEth = false;
        ArrayList<NetworkInterface> networkInterfaces =  LanerNetworkInterface.getNetworkInterfacesNoLoopback();
        for (NetworkInterface networkInterface : networkInterfaces) {
            boolean check = false;
            for (String interfaceName : interfacesNames) {
                if (networkInterface.getName().startsWith(interfaceName)) {
                    check  = true;
                    break;
                }
            }
            if (check) {
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
        return isListening;
    }

    public void stop() {
        if (isListening) {
            if (timer != null) {
                timer.cancel();
            }
            isListening = false;
        }
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
            exceptor.thrown(this, ex);
        }
    }


}
