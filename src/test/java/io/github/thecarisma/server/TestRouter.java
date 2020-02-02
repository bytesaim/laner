package io.github.thecarisma.server;

import java.io.IOException;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 */
public class TestRouter {

    public static void main(String[] args) {
        Server server = new Server("192.168.8.100",7510);
        Router router = new Router(server);
        router.post("/greet_me", new ServerListener() {
            @Override
            public void report(Request request, Response response) {
                try {
                    response.write("hello how are your".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        server.run();
    }

}
