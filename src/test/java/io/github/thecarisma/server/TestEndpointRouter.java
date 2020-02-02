package io.github.thecarisma.server;

import java.io.IOException;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 */
public class TestEndpointRouter {

    public static void main(String[] args) {
        Server server = new Server("192.168.8.100",7510);
        EndpointRouter endpointRouter = new EndpointRouter(server);
        endpointRouter.post("/", new ServerListener() {
            @Override
            public void report(Request request, Response response) {
                try {
                    response.close("hello how are your".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        server.run();
    }

}
