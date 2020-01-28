package io.github.thecarisma.laner;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Vector;

public class TestType {

    //@Test
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
                if (addr.isReachable(1000)){
                    System.out.println("Available: " + addr.getHostAddress()+ ", Name: " + addr.getCanonicalHostName());
                    //Available_Devices.add(addr.getHostAddress());
                }
                else System.out.println("Not available: "+ addr.getHostAddress());

            }catch (IOException ioex){}
        }

        System.out.println("\nAll Connected devices(" + Available_Devices.size() +"):");
        for(int i=0;i<Available_Devices.size();++i) System.out.println(Available_Devices.get(i));
    }

    public void Test1()  {
        int[] ports = { 22, 25, 80, 5555, 7680  };
        for (int port : ports) {
            if (LanerNetworkInterface.isReachable("192.168.8.1", port, 10))
                System.out.println("Port: " + port + " is open");
        }
    }

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(80, 200, InetAddress.getByName("192.168.8.102"));
            System.out.println(serverSocket.getInetAddress().getHostAddress());
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(1);
        }

        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;
            out.write("HTTP/1.0 200 OK\r\n");
            out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
            out.write("Server: Apache/0.8.4\r\n");
            out.write("Content-Type: text/html\r\n");
            out.write("Content-Length: 59\r\n");
            out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
            out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
            out.write("\r\n");
            out.write("<html><head><title>Exemple</title></head>");
            out.write("<body>Ceci est une page d'exemple.</body></html>");
            //out.write(outputLine);

            while ((inputLine = in.readLine()) != null) {
                //out.println(outputLine);
                //if (outputLine.equals("Bye."))
                //    break;
                System.out.println(inputLine);
                break;
            }
            out.close();
            in.close();
            clientSocket.close();
        }
        //serverSocket.close();
    }

}
