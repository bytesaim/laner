package io.github.thecarisma;

import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
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
        for(int i=1;i<=254;++i){
            try {
                InetAddress addr=InetAddress.getByName(mynetworkips + new Integer(i).toString());
                if (addr.isReachable(10)){
                    System.out.println("Available: " + addr.getHostAddress()+ ", Name: " + addr.getCanonicalHostName());
                    //Available_Devices.add(addr.getHostAddress());
                }
                else System.out.println("Not available: "+ addr.getHostAddress());

            }catch (IOException ioex){}
        }

        System.out.println("\nAll Connected devices(" + Available_Devices.size() +"):");
        for(int i=0;i<Available_Devices.size();++i) System.out.println(Available_Devices.get(i));
    }

}
