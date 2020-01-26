package io.github.thecarisma.laner;

import java.util.Timer;
import java.util.TimerTask;

public class InternetStatus implements Runnable {

    private String urlIp;

    int delayInSeconds = 5;

    public InternetStatus(String urlIp) {
        this.urlIp = urlIp;
    }

    public InternetStatus(String urlIp, int delayInSeconds) {
        this.urlIp = urlIp;
        this.delayInSeconds = delayInSeconds;
    }

    public static boolean isConnected() {
        return LanerNetworkInterface.isReachable("thecarisma.github.io", 80, 1000);
    }

    @Override
    public void run() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                
            }
        }, 0, (delayInSeconds * 1000));
    }
}
