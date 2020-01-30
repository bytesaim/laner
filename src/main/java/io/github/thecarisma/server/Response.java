package io.github.thecarisma.server;

import io.github.thecarisma.exceptions.ResponseHeaderException;
import io.github.thecarisma.laner.LanerPrintWriter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 */
public class Response {

    private LanerPrintWriter out;
    private Map<String, String[]> headers = new HashMap<>();
    public boolean headersSent = false;

    public Response(LanerPrintWriter out) {
        this.out = out;
    }

    public void write(String data) {
        out.write(data);
    }

    public Map<String, String[]> getHeaders() {
        return headers;
    }

    public void appendHeader(String key, String... values) throws ResponseHeaderException {
        if (headersSent) {
            throw new ResponseHeaderException();
        }
        headers.put(key, values);
    }

    public String[] removeHeader(String key) {
        return headers.remove(key);
    }

    public String getHttpVersion() {
        return "HTTP/1.1";
    }

    private void writeHeaders() {
        //write the headers
    }

}
