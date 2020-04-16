package com.bytesaim.http;

import java.util.HashMap;
import java.util.Map;

public class MultipartData {

    private String name;
    private Map<String, String> headers = new HashMap<>();
    private byte[] body;

    public MultipartData() {
        this.name = "";
        this.body = new byte[]{};
    }

    public MultipartData(Map<String, String> headers, String body) {
        this.name = headers.get("name");
        this.headers = headers;
        this.body = body.getBytes();
    }

    public MultipartData(String name, Map<String, String> headers, String body) {
        this.name = name;
        this.headers = headers;
        this.body = body.getBytes();
    }

    public MultipartData(Map<String, String> headers, byte[] body) {
        this.name = headers.get("name");
        this.headers = headers;
        this.body = body;
    }

    public MultipartData(String name, Map<String, String> headers, byte[] body) {
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

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

}
