package io.github.thecarisma.server;

import java.net.Socket;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 */
public class Router {

    private Server mServer ;

    public Router(Server server) {
        this.mServer = server;
    }

    protected void treatRequest(Socket socket) {

    }

    public void get(String endpoint, ServerListenerFactory serverListenerFactory) {

    }

}
