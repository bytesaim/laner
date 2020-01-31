package io.github.thecarisma.server;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 */
public interface ServerListener extends ServerListenerFactory {
    void report(Request request, Response response);
}
