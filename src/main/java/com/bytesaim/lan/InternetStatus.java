package com.bytesaim.lan;

import com.bytesaim.util.TRunnable;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class InternetStatus implements TRunnable {

    private ArrayList<LanerListener> lanerListeners = new ArrayList<>();
    private String urlIp = "thecarisma.github.io";
    private int delayInSeconds = 1;
    private ConnectionStatus status = ConnectionStatus.DISCONNECTED;
    private Timer timer;
    private boolean isListening = false;
    private boolean useProxy_ = false;

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

    public void useProxy(boolean useProxy) {
        this.useProxy_ = useProxy;
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
        return LanerNetworkInterface.isReachable(urlIp, 80, 1000, useProxy_);
    }

    @Override
    public void run() {
        broadcastToListeners(status);
        isListening = true;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
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

}
