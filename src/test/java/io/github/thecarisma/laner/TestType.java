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
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(1);
        }

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
        KnockKnockProtocol kkp = new KnockKnockProtocol();

        outputLine = kkp.processInput(null);
        out.println(outputLine);

        while ((inputLine = in.readLine()) != null) {
            outputLine = kkp.processInput(inputLine);
            out.println(outputLine);
            if (outputLine.equals("Bye."))
                break;
        }
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }

    static class KnockKnockProtocol {
        private static final int WAITING = 0;
        private static final int SENTKNOCKKNOCK = 1;
        private static final int SENTCLUE = 2;
        private static final int ANOTHER = 3;

        private static final int NUMJOKES = 5;

        private int state = WAITING;
        private int currentJoke = 0;

        private String[] clues = { "Turnip", "Little Old Lady", "Atch", "Who", "Who" };
        private String[] answers = { "Turnip the heat, it's cold in here!",
                "I didn't know you could yodel!",
                "Bless you!",
                "Is there an owl in here?",
                "Is there an echo in here?" };

        public String processInput(String theInput) {
            String theOutput = null;

            if (state == WAITING) {
                theOutput = "Knock! Knock!";
                state = SENTKNOCKKNOCK;
            } else if (state == SENTKNOCKKNOCK) {
                if (theInput.equalsIgnoreCase("Who's there?")) {
                    theOutput = clues[currentJoke];
                    state = SENTCLUE;
                } else {
                    theOutput = "You're supposed to say \"Who's there?\"! " +
                            "Try again. Knock! Knock!";
                }
            } else if (state == SENTCLUE) {
                if (theInput.equalsIgnoreCase(clues[currentJoke] + " who?")) {
                    theOutput = answers[currentJoke] + " Want another? (y/n)";
                    state = ANOTHER;
                } else {
                    theOutput = "You're supposed to say \"" +
                            clues[currentJoke] +
                            " who?\"" +
                            "! Try again. Knock! Knock!";
                    state = SENTKNOCKKNOCK;
                }
            } else if (state == ANOTHER) {
                if (theInput.equalsIgnoreCase("y")) {
                    theOutput = "Knock! Knock!";
                    if (currentJoke == (NUMJOKES - 1))
                        currentJoke = 0;
                    else
                        currentJoke++;
                    state = SENTKNOCKKNOCK;
                } else {
                    theOutput = "Bye.";
                    state = WAITING;
                }
            }
            return theOutput;
        }
    }

}
