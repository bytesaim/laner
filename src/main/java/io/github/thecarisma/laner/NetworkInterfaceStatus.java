package io.github.thecarisma.laner;

import io.github.thecarisma.exceptions.Exceptor;
import io.github.thecarisma.util.TRunnable;

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
        this.networkInterface = networkInterface;
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
