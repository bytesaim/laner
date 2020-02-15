package com.bytesaim.util;

import com.bytesaim.laner.*;
import com.bytesaim.server.Request;
import com.bytesaim.server.Response;
import com.bytesaim.server.Server;
import com.bytesaim.server.ServerListener;
import org.junit.Test;

import java.io.IOException;
import java.net.UnknownHostException;

public class TestThreadsManager {

    @Test
    public void Test1() throws UnknownHostException {
        final ThreadsManager threadsManager = new ThreadsManager();
        final int[] index = {0};
        NetworkDevices nd = new NetworkDevices(LanerNetworkInterface.getIPV4Address(), new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof NetworkDevices.NetworkDevice) {
                    if (index[0] >= 0) {
                        try {
                            System.out.println("Killing all the thread");
                            threadsManager.killAll();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(o);
                    index[0]++;
                }
            }
        });
        threadsManager.register("testnetworddevices", nd);
        nd.run();
    }

    @Test
    public void Test2() throws UnknownHostException {
        final ThreadsManager threadsManager = new ThreadsManager();
        final int[] index = {0};
        NetworkDevices nd = new NetworkDevices(LanerNetworkInterface.getIPV4Address(), new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof NetworkDevices.NetworkDevice) {
                    if (index[0] >= 0) {
                        try {
                            System.out.println("Killing all testnetworddevices1 runnables");
                            threadsManager.killAll("testnetworddevices1");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(o);
                    index[0]++;
                }
            }
        });
        final int[] index2 = {0};
        InternetStatus is = new InternetStatus("thecarisma.github.io", new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof ConnectionStatus) {
                    if (index2[0] >= 0) {
                        try {
                            System.out.println("Killing all testnetworddevices2 Runables");
                            threadsManager.killAll("testnetworddevices2");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(o);
                    index2[0]++;
                }
            }
        });
        threadsManager.register("testnetworddevices1", nd);
        threadsManager.register("testnetworddevices2", is);
        is.run();
        nd.run();
    }

    @Test
    public void Test3() throws UnknownHostException, InterruptedException {
        final ThreadsManager threadsManager = new ThreadsManager();
        final int[] index = {0};
        NetworkDevices nd = new NetworkDevices(LanerNetworkInterface.getIPV4Address(), new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof NetworkDevices.NetworkDevice) {
                    if (index[0] >= 0) {
                        try {
                            System.out.println("Killing all testnetworddevices runnables");
                            threadsManager.killAll("testnetworddevices");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(o);
                    index[0]++;
                }
            }
        });
        final int[] index2 = {0};
        InternetStatus is = new InternetStatus("thecarisma.github.io", new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof ConnectionStatus) {
                    if (index2[0] >= 0) {
                        try {
                            System.out.println("Don't care killing in NetworkDevice status");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(o);
                    index2[0]++;
                }
            }
        });
        threadsManager.register("testnetworddevices", nd);
        threadsManager.register("testnetworddevices", is);
        threadsManager.startAll("testnetworddevices");
    }

    @Test
    public void Test4() throws UnknownHostException, InterruptedException {
        final ThreadsManager threadsManager = new ThreadsManager();
        final int[] index = {0};
        NetworkDevices nd = new NetworkDevices(LanerNetworkInterface.getIPV4Address(), new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof NetworkDevices.NetworkDevice) {
                    if (index[0] >= 0) {
                        try {
                            System.out.println("Killing all testnetworddevices runnables");
                            threadsManager.killAll("testnetworddevices");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(o);
                    index[0]++;
                }
            }
        });
        final int[] index2 = {0};
        InternetStatus is = new InternetStatus("thecarisma.github.io", new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof ConnectionStatus) {
                    if (index2[0] >= 0) {
                        try {
                            System.out.println("Don't care killing in NetworkDevice status");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(o);
                    index2[0]++;
                }
            }
        });
        Server sv = new Server(LanerNetworkInterface.getIPV4Address(),7510, new ServerListener() {
            @Override
            public void report(Request request, Response response) {
                try {
                    response.close("hELLO ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        threadsManager.register("testnetworddevices", nd);
        threadsManager.register("testnetworddevices", is);
        threadsManager.register("testnetworddevices", sv);
        threadsManager.startAll("testnetworddevices");
    }

}
