package com.bytesaim.http;

import java.io.IOException;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 */
public class TestEndpointRouter {

    public static void main(String[] args) {
        Server server = new Server("172.16.40.27",7510);
        EndpointRouter endpointRouter = new EndpointRouter(server);
        endpointRouter.get("/greet", new ServerListener() {
            @Override
            public void report(Request request, Response response) {
                try {
                    response.close("hello how are your".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        /*endpointRouter.defaultRoute(new ServerListener() {
            @Override
            public void report(Request request, Response response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });*/
        server.run();
    }

}
