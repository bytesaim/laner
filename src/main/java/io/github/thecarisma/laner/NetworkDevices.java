package io.github.thecarisma.laner;

import io.github.thecarisma.util.TRunnable;

import java.net.InetAddress;
import java.util.*;

public class NetworkDevices implements TRunnable {

    private ArrayList<LanerListener> lanerListeners = new ArrayList<>();
    private ArrayList<String> ipAddresses = new ArrayList<>();
    private Map<String, NetworkDevice> networkDevices = new HashMap<>();
    final int[] ports = { 22, 25, 80, 5555, 7680  };
    final int[] forePorts ;
    private boolean stopListening = false;

    public NetworkDevices(String ipAddress, LanerListener lanerListener, int[] forePorts) {
        this.lanerListeners.add(lanerListener);
        this.ipAddresses.add(ipAddress);
        this.forePorts = forePorts;
    }

    public NetworkDevices(String ipAddress, LanerListener lanerListener) {
        this.lanerListeners.add(lanerListener);
        this.ipAddresses.add(ipAddress);
        this.forePorts = new int[]{};
    }

    public NetworkDevices(String ipAddress) {
        this.ipAddresses.add(ipAddress);
        this.forePorts = new int[]{};
    }

    public NetworkDevices(String[] ipAddresses, LanerListener lanerListener, int[] forePorts) {
        this.lanerListeners.add(lanerListener);
        this.ipAddresses.addAll(Arrays.asList(ipAddresses));
        this.forePorts = forePorts;
    }

    public NetworkDevices(String[] ipAddresses, LanerListener lanerListener) {
        this.lanerListeners.add(lanerListener);
        this.ipAddresses.addAll(Arrays.asList(ipAddresses));
        this.forePorts = new int[]{};
    }

    public NetworkDevices(String[] ipAddresses) {
        this.ipAddresses.addAll(Arrays.asList(ipAddresses));
        this.forePorts = new int[]{};
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

    public ArrayList<String> getIpAddresses() {
        return ipAddresses;
    }

    public void addIpAddress(String ipAddress) {
        this.ipAddresses.add(ipAddress);
    }

    public void removeIpAddress(String ipAddress) {
        this.ipAddresses.remove(ipAddress);
    }

    @Override
    public void run() {
        try {
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
                                for (String ipAddress : ipAddresses) {
                                    String preDeviceAddr = ipAddress.substring(0, ipAddress.lastIndexOf(".") + 1);
                                    InetAddress addr = InetAddress.getByName(preDeviceAddr + j);
                                    NetworkDevice networkDevice;
                                    if (networkDevices.containsKey(preDeviceAddr + j)) {
                                        networkDevice = networkDevices.get(preDeviceAddr + j);
                                    } else {
                                        networkDevice = new NetworkDevice(Status.UNKNOWN, addr);
                                        networkDevices.put(preDeviceAddr + j, networkDevice);
                                    }
                                    boolean continuePing = true;
                                    for (int port : forePorts) {
                                        if (LanerNetworkInterface.isReachable(preDeviceAddr + j, port, 1000)) {
                                            if (networkDevice.status != Status.CONNECTED) {
                                                networkDevice.status = Status.CONNECTED;
                                                networkDevice.openedPort = port;
                                                networkDevice.statusChanged = true;
                                            }
                                            continuePing = false;
                                            break;
                                        }
                                    }
                                    if (continuePing) {
                                        if (addr.isReachable(1000)) {
                                            if (networkDevice.status != Status.CONNECTED) {
                                                networkDevice.status = Status.CONNECTED;
                                                networkDevice.statusChanged = true;
                                            }
                                        } else {
                                            for (int port : ports) {
                                                if (LanerNetworkInterface.isReachable(preDeviceAddr + j, port, 1000)) {
                                                    if (networkDevice.status != Status.CONNECTED) {
                                                        networkDevice.status = Status.CONNECTED;
                                                        networkDevice.openedPort = port;
                                                        networkDevice.statusChanged = true;
                                                    }
                                                    break;
                                                }
                                            }
                                            if (networkDevice.status == Status.CONNECTED) {
                                                networkDevice.status = Status.DISCONNECTED;
                                                networkDevice.statusChanged = true;
                                            }
                                        }
                                    }
                                    if (networkDevice.status != Status.UNKNOWN && networkDevice.statusChanged) {
                                        networkDevice.statusChanged = false;
                                        broadcastToListeners(networkDevice);
                                    }
                                }
                            } catch (Throwable e) {}
                        }
                    }
                });
                threads.add(t);
                t.start();
            }
            for (Thread t : threads){
                t.join();
            }
            if (lanerListeners.size() > 0 && !stopListening) run();
        } catch (Throwable e) {}
    }

    private void broadcastToListeners(Object o) {
        for (LanerListener lanerListener : lanerListeners) {
            lanerListener.report(o);
        }
    }

    public void stop() {
        stopListening = true;
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
        public int openedPort = 1;

        private NetworkDevice(Status status, InetAddress inetAddress) {
            this.status = status;
            this.inetAddress = inetAddress;
        }

        public String toString() {
            return super.toString() + "#" + status + "," + inetAddress.getHostAddress();
        }
    }

}
