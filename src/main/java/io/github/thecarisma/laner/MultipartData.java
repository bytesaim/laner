package io.github.thecarisma.laner;

import java.util.HashMap;
import java.util.Map;

public class MultipartData {

    public String name;
    public Map<String, String> headers = new HashMap<>();
    public String body;

    public MultipartData(Map<String, String> headers, String body) {
        this.name = headers.get("name");
        this.headers = headers;
        this.body = body;
    }

    public MultipartData(String name, Map<String, String> headers, String body) {
        this.name = name;
        this.headers = headers;
        this.body = body;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
