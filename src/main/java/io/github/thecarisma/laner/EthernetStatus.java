package io.github.thecarisma.laner;

import io.github.thecarisma.util.TRunnable;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

//TODO: listen for ethernet
public class EthernetStatus implements TRunnable {

    private ArrayList<LanerListener> lanerListeners = new ArrayList<>();
    private String urlIp = "thecarisma.github.io";
    private int delayInSeconds = 1;
    private Status status = Status.DISCONNECTED;
    private Timer timer;

    public EthernetStatus(String urlIp) {
        this.urlIp = urlIp;
    }

    public EthernetStatus(String urlIp, LanerListener lanerListener, int delayInSeconds) {
        this.urlIp = urlIp;
        this.lanerListeners.add(lanerListener);
        this.delayInSeconds = delayInSeconds;
    }

    public EthernetStatus(String urlIp, LanerListener lanerListener) {
        this.urlIp = urlIp;
        this.lanerListeners.add(lanerListener);
    }

    public EthernetStatus(String urlIp, int delayInSeconds) {
        this.urlIp = urlIp;
        this.delayInSeconds = delayInSeconds;
    }

    public EthernetStatus(LanerListener lanerListener) {
        this.lanerListeners.add(lanerListener);
    }

    public EthernetStatus(LanerListener lanerListener, int delayInSeconds) {
        this.lanerListeners.add(lanerListener);
        this.delayInSeconds = delayInSeconds;
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

    public boolean isConnected() {
        return LanerNetworkInterface.isReachable(urlIp, 80, 1000);
    }

    @Override
    public void run() {
        broadcastToListeners(status);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isConnected()) {
                    if (status == Status.DISCONNECTED) {
                        status = Status.CONNECTED;
                        broadcastToListeners(status);
                    }
                } else {
                    if (status == Status.CONNECTED) {
                        status = Status.DISCONNECTED;
                        broadcastToListeners(status);
                    }
                }
            }
        }, 0, (delayInSeconds * 1000));
    }

    private void broadcastToListeners(Object o) {
        for (LanerListener lanerListener : lanerListeners) {
            lanerListener.report(o);
        }
    }

    @Override
    public boolean isRunning() {
        return true;
    }

    public void stop() {
        timer.cancel();
    }

    public enum Status {
        CONNECTED,
        DISCONNECTED
    }

}
