package io.github.thecarisma;

import org.junit.Test;

import java.io.IOException;
import java.net.*;
import java.util.Vector;

public class TestType {

    @Test
    public void TestInterfaceType() throws SocketException, UnknownHostException {
        Vector<String> Available_Devices=new Vector<>();
        String myip=InetAddress.getLocalHost().getHostAddress();
        String mynetworkips=new String();

        for(int i=myip.length();i>0;--i) {
            if(myip.charAt(i-1)=='.'){ mynetworkips=myip.substring(0,i); break; }
        }

        System.out.println("My Device IP: " + myip+"\n");

        System.out.println("Search log:");
        for(int i=100;i<=254;++i){
            try {
                InetAddress addr=InetAddress.getByName("192.168.8.101");
                if (addr.isReachable(100000)){
                    System.out.println("Available: " + addr.getHostAddress()+ ", Name: " + addr.getCanonicalHostName());
                    //Available_Devices.add(addr.getHostAddress());
                }
                else System.out.println("Not available: "+ addr.getHostAddress());

            }catch (IOException ioex){}
        }

        System.out.println("\nAll Connected devices(" + Available_Devices.size() +"):");
        for(int i=0;i<Available_Devices.size();++i) System.out.println(Available_Devices.get(i));
    }

    public static void main(String[] args)  {
        int[] ports = { 22, 25, 80, 5555, 7680  };
        for (int port : ports) {
            if (LanerNetworkInterface.isReachable("192.168.8.1", port, 10))
                System.out.println("Port: " + port + " is open");
        }
    }

}
