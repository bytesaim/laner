package io.github.thecarisma;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class NetworkDevices implements Runnable {

    private LannerListener lannerListener;
    private String ipAddress;
    private Map<String, NetworkDevice> networkDevices = new HashMap<>();

    public NetworkDevices(String ipAddress, LannerListener lannerListener) {
        this.lannerListener = lannerListener;
        this.ipAddress = ipAddress;
    }

    @Override
    public void run() {
        try {
            final String preDeviceAddr = ipAddress.substring(0, ipAddress.lastIndexOf(".") + 1);
            int addlimit = 254;
            int cores = (Runtime.getRuntime().availableProcessors() / 2) + 50;
            final int devPerThread = addlimit / cores;
            ArrayList<Thread> threads = new ArrayList<>();
            for (int i = 0; i < cores; ++i) {
                final int finalI = i;
                final Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int j = devPerThread * finalI; j < devPerThread * (finalI + 1); ++j) {
                            try {
                                InetAddress addr = InetAddress.getByName(preDeviceAddr + j);
                                NetworkDevice networkDevice ;
                                if (networkDevices.containsKey(preDeviceAddr + j)) {
                                    networkDevice = networkDevices.get(preDeviceAddr + j);
                                } else {
                                    networkDevice = new NetworkDevice(Status.UNKNOWN, addr);
                                    networkDevices.put(preDeviceAddr + j, networkDevice);
                                }
                                if (addr.isReachable(10000)) {
                                    if (networkDevice.status != Status.CONNECTED) {
                                        networkDevice.status = Status.CONNECTED;
                                        networkDevice.statusChanged = true;
                                    }
                                } else {
                                    if (networkDevice.status == Status.CONNECTED) {
                                        networkDevice.status = Status.DISCONNECTED;
                                        networkDevice.statusChanged = true;
                                    }
                                }
                                if (networkDevice.status != Status.UNKNOWN && networkDevice.statusChanged) {
                                    networkDevice.statusChanged = false;
                                    lannerListener.report(networkDevice);
                                }

                            } catch (Throwable e) {}
                        }
                    }
                });
                threads.add(t);
                t.start();
            }
            //System.out.println("ok");
            run();
        } catch (Throwable e) {}
    }

    public static enum Status {
        CONNECTED,
        DISCONNECTED,
        UNKNOWN
    }

    public static class NetworkDevice {
        public boolean statusChanged = true;
        public Status status = Status.UNKNOWN ;
        public InetAddress inetAddress;

        private NetworkDevice(Status status, InetAddress inetAddress) {
            this.status = status;
            this.inetAddress = inetAddress;
        }

        public String toString() {
            return super.toString() + "#" + status + "," + inetAddress.getHostAddress();
        }
    }

}
