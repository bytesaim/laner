package io.github.thecarisma;

public class NetworkDevices {

    private LannerListener lannerListener;

    public NetworkDevices(LannerListener lannerListener) {
        this.lannerListener = lannerListener;
    }

    public void listen() {
        while (true) {
            lannerListener.report("Hello");
        }
    }

}
