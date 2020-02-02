package io.github.thecarisma.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 */
public class EndpointRouter {

    private Server mServer ;
    private Map<Method, Map<String, ServerListenerFactory>> mMethodRoutes = new HashMap<>();

    public EndpointRouter(Server server) {
        this.mServer = server;
        this.mServer.setRouter(this);
    }

    protected void treatRequest(Request request, Response response) throws IOException {
        if (mMethodRoutes.containsKey(request.getMethod())) {
            Map<String, ServerListenerFactory> mServerListenerFactories = mMethodRoutes.get(request.getMethod());
            for (String key : mServerListenerFactories.keySet()) {
                Pattern p = Pattern.compile(key);
                Matcher m = p.matcher(request.getEndpoint());
                if (m.find())  {
                    ServerListenerFactory serverListener = mServerListenerFactories.get(key);
                    if (serverListener instanceof ServerListener) {
                        ((ServerListener) serverListener).report(request, response);
                    }
                    if (serverListener instanceof ServerRawListener) {
                        ((ServerRawListener) serverListener).report(request.in, response.out);
                    }
                }
            }

        } else {
            response.close();
        }
    }

    public void get(String endpoint, ServerListenerFactory serverListenerFactory) {
        route(Method.GET, endpoint, serverListenerFactory);
    }

    public void post(String endpoint, ServerListenerFactory serverListenerFactory) {
        route(Method.POST, endpoint, serverListenerFactory);
    }

    public void delete(String endpoint, ServerListenerFactory serverListenerFactory) {
        route(Method.DELETE, endpoint, serverListenerFactory);
    }

    public void put(String endpoint, ServerListenerFactory serverListenerFactory) {
        route(Method.PUT, endpoint, serverListenerFactory);
    }

    public void route(Method method, String endpoint, ServerListenerFactory serverListenerFactory) {
        if (mMethodRoutes.containsKey(method)) {
            mMethodRoutes.get(method).put(endpoint, serverListenerFactory);
        } else {
            Map<String, ServerListenerFactory> mServerListenerFactories = new HashMap<>();
            mServerListenerFactories.put(endpoint, serverListenerFactory);
            mMethodRoutes.put(method, mServerListenerFactories);
        }

    }

}
