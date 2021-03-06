package com.bytesaim.lan;

import com.bytesaim.exceptions.Exceptor;
import com.bytesaim.util.TRunnable;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class NetworkInterfaceStatus implements TRunnable {

    private ArrayList<LanerListener> lanerListeners = new ArrayList<>();
    private int delayInSeconds = 1;
    private ConnectionStatus status = ConnectionStatus.DISCONNECTED;
    private Timer timer;
    private ArrayList<Exceptor> exceptors = new ArrayList<>();
    private NetworkInterface networkInterface;
    private boolean isListening = false;

    public NetworkInterfaceStatus(NetworkInterface networkInterface, LanerListener lanerListener, int delayInSeconds) {
        this.networkInterface = networkInterface;
        this.lanerListeners.add(lanerListener);
        this.delayInSeconds = delayInSeconds;
    }

    public NetworkInterfaceStatus(NetworkInterface networkInterface, int delayInSeconds) {
        this.networkInterface = networkInterface;
        this.delayInSeconds = delayInSeconds;
    }

    public NetworkInterfaceStatus(NetworkInterface networkInterface, LanerListener lanerListener) {
        this.networkInterface = networkInterface;
        this.lanerListeners.add(lanerListener);
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

    public NetworkInterface getNetworkInterface() {
        return networkInterface;
    }

    public void setNetworkInterface(NetworkInterface networkInterface) {
        this.networkInterface = networkInterface;
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
        ArrayList<NetworkInterface> networkInterfaces =  LanerNetworkInterface.getNetworkInterfaces();
        for (NetworkInterface networkInterface : networkInterfaces) {
            if (this.networkInterface != null) {
                if (this.networkInterface.getDisplayName().equals(networkInterface.getDisplayName())) {
                    ArrayList<InetAddress> addresses1 = LanerNetworkInterface.getInetAddresses(networkInterface);
                    ArrayList<InetAddress> addresses2 = LanerNetworkInterface.getInetAddresses(this.networkInterface);
                    if (addresses1.size() != addresses2.size()) {
                        continue;
                    }
                    for (int i = 0; i < addresses1.size(); ++i) {
                        if (!addresses1.get(i).getHostAddress().equals(addresses1.get(i).getHostAddress())) {
                            continue;
                        }
                    }
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
            exceptor.threw(this, ex);
        }
    }

}
