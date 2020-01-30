package io.github.thecarisma.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 */
public class Response {

    protected String HTTPversion = "";
    protected Map<String, String> headers = new HashMap<>();
    protected Method method = Method.UNKNOWN;

    public Response(PrintWriter out) {

    }

    public String write(Object o) {
        return null;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHTTPversion() {
        return HTTPversion;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String removeHeader(String key, String value) {
        return headers.remove(key);
    }

    public void setHTTPversion(String HTTPversion) {
        this.HTTPversion = HTTPversion;
    }
}
