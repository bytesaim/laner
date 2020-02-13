package io.github.thecarisma.laner;

import io.github.thecarisma.util.TRunnable;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

//TODO: listen for ethernet
public class EthernetStatus implements TRunnable {

    private ArrayList<LanerListener> lanerListeners = new ArrayList<>();
    private int delayInSeconds = 1;
    private ConnectionStatus status = ConnectionStatus.DISCONNECTED;
    private Timer timer;

    public EthernetStatus(LanerListener lanerListener, int delayInSeconds) {
        this.lanerListeners.add(lanerListener);
        this.delayInSeconds = delayInSeconds;
    }

    public EthernetStatus(int delayInSeconds) {
        this.delayInSeconds = delayInSeconds;
    }

    public EthernetStatus(LanerListener lanerListener) {
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

    private boolean isConnected() {
        return true;
    }

    @Override
    public boolean isRunning() {
        return true;
    }

    public void stop() {
        timer.cancel();
    }

}
