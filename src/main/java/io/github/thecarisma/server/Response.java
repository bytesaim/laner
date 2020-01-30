package io.github.thecarisma.server;

import io.github.thecarisma.laner.LanerPrintWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 */
public class Response {

    private LanerPrintWriter out;
    private String HTTPversion = "";
    private Map<String, String> headers = new HashMap<>();
    private Method method = Method.UNKNOWN;

    public Response(LanerPrintWriter out) {

    }

    public void writeHeaders() {
        //write the headers
    }

    public void write(String data) {
        out.write(data);
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

}
