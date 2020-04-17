package com.bytesaim.http;

import java.io.IOException;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 */
public class TestEndpointRouter {

    public static void main(String[] args) {
        Server server = new Server("127.0.0.1",7510);
        EndpointRouter endpointRouter = new EndpointRouter(server);
        endpointRouter.get("/*", new ServerListener() {
            @Override
            public void report(Request request, Response response) {
                try {
                    response.close("hello how are you".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        ServerListener serverListener = new ServerListener() {
            @Override
            public void report(Request request, Response response) {
                System.out.println("Handling request: " + request.getEndpoint());
            }
        };
        server.addServerListenerFactory(serverListener);
        System.out.println("Server Started: " + server.getHost());
        server.run();
    }

}
