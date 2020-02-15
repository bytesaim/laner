package com.bytesaim.laner;

import com.bytesaim.util.TRunnable;

import java.net.InetAddress;
import java.util.*;

public class NetworkDevices implements TRunnable {

    private ArrayList<LanerListener> lanerListeners = new ArrayList<>();
    private ArrayList<String> ipAddresses = new ArrayList<>();
    private Map<String, NetworkDevice> networkDevices = new HashMap<>();
    final int[] ports = { 22, 25, 80, 5555, 7680  };
    final int[] forePorts ;
    private boolean continueListening = false;
    private boolean reallyListening = false;
    private int runningSubThreadsCount = 0;
    private boolean useProxy_ = false;

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

    public void useProxy(boolean useProxy_) {
        this.useProxy_ = useProxy_;
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
            networkDevices = new HashMap<>();
            continueListening = true;
            reallyListening = true;
            int addlimit = 254;
            int cores = (Runtime.getRuntime().availableProcessors() / 2) + 20;
            final int devPerThread = addlimit / cores;
            while (continueListening) {
                ArrayList<Thread> threads = new ArrayList<>();
                for (int i = 0; i < cores; ++i) {
                    if (!continueListening) {
                        trulyDead();
                        break;
                    }
                    final int finalI = i;
                    final Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            runningSubThreadsCount++;
                            for (int j = devPerThread * finalI; j < devPerThread * (finalI + 1); ++j) {
                                if (!continueListening) {
                                    trulyDead();
                                    break;
                                }
                                try {
                                    for (String ipAddress : ipAddresses) {
                                        if (!continueListening)  {
                                            break;
                                        }
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
                                            if (LanerNetworkInterface.isReachable(preDeviceAddr + j, port, 1000, useProxy_)) {
                                                if (networkDevice.status != Status.CONNECTED || networkDevice.openedPort != port) {
                                                    networkDevice.status = Status.CONNECTED;
                                                    networkDevice.openedPort = port;
                                                    networkDevice.statusChanged = true;
                                                    continuePing = false;
                                                }
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
                                                    if (LanerNetworkInterface.isReachable(preDeviceAddr + j, port, 1000, useProxy_)) {
                                                        if (networkDevice.status != Status.CONNECTED || networkDevice.openedPort != port) {
                                                            networkDevice.status = Status.CONNECTED;
                                                            networkDevice.openedPort = port;
                                                            networkDevice.statusChanged = true;
                                                            continuePing = false;
                                                        }
                                                        break;
                                                    }
                                                }

                                                if (continuePing) {
                                                    if (networkDevice.status == Status.CONNECTED) {
                                                        networkDevice.status = Status.DISCONNECTED;
                                                        networkDevice.statusChanged = true;
                                                        networkDevice.openedPort = 1;
                                                    }
                                                }
                                            }
                                        }
                                        if (networkDevice.status != Status.UNKNOWN && networkDevice.statusChanged) {
                                            networkDevice.statusChanged = false;
                                            broadcastToListeners(networkDevice);
                                        }
                                    }
                                } catch (Throwable e) {
                                }
                            }
                            runningSubThreadsCount--;
                        }
                    });
                    threads.add(t);
                    t.start();
                }
                for (Thread t : threads) {
                    t.join();
                }
            }
            trulyDead();
        } catch (Throwable e) {}
    }

    private void broadcastToListeners(Object o) {
        for (LanerListener lanerListener : lanerListeners) {
            lanerListener.report(o);
        }
    }

    @Override
    public boolean isRunning() {
        return reallyListening;
    }

    public void stop() {
        continueListening = false;
    }

    private void trulyDead() {
        if (runningSubThreadsCount == 0) {
            reallyListening = false;
        }
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
