package io.github.thecarisma.laner;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class InternetStatus implements Runnable {

    private ArrayList<LanerListener> lanerListeners = new ArrayList<>();
    private String urlIp = "thecarisma.github.io";
    private int delayInSeconds = 1;
    private Status status = Status.DISCONNECTED;
    private Timer timer;

    public InternetStatus(String urlIp) {
        this.urlIp = urlIp;
    }

    public InternetStatus(String urlIp, LanerListener lanerListener, int delayInSeconds) {
        this.urlIp = urlIp;
        this.lanerListeners.add(lanerListener);
        this.delayInSeconds = delayInSeconds;
    }

    public InternetStatus(String urlIp, LanerListener lanerListener) {
        this.urlIp = urlIp;
        this.lanerListeners.add(lanerListener);
    }

    public InternetStatus(String urlIp, int delayInSeconds) {
        this.urlIp = urlIp;
        this.delayInSeconds = delayInSeconds;
    }

    public InternetStatus(LanerListener lanerListener) {
        this.lanerListeners.add(lanerListener);
    }

    public InternetStatus(LanerListener lanerListener, int delayInSeconds) {
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

    public void stop() {
        timer.cancel();
    }

    public enum Status {
        CONNECTED,
        DISCONNECTED
    }

}
