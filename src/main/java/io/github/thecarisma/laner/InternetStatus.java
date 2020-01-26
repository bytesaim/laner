package io.github.thecarisma.laner;

public class InternetStatus implements Runnable {

    private String urlIp;

    public InternetStatus(String urlIp) {
        this.urlIp = urlIp;
    }

    public static boolean isConnected() {
        return LanerNetworkInterface.isReachable("thecarisma.github.io", 80, 1000);
    }

    @Override
    public void run() {

    }
}
