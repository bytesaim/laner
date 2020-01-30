package io.github.thecarisma.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ServerRequest {

    public PrintWriter out;
    private BufferedReader in;
    public String endpoint = "";
    public String HTTPversion = "";
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> parameters = new HashMap<>();
    public RequestMethod method = RequestMethod.UNKNOWN;
    private StringBuilder body = new StringBuilder();
    private MultipartStream multipartStream;
    private boolean readBody = false;
    private boolean readMultipart = false;

    public ServerRequest(PrintWriter out, BufferedReader in) throws IOException {
        this.out = out;
        this.in = in;
        String inputLine;
        while ((inputLine = in.readLine()).length() != 0) {
            if (endpoint.equals("")) {
                endpoint = inputLine;
                String[] s1 = endpoint.split(" ");
                setMethod(s1[0].trim());
                String[] s2 = s1[1].split("\\?");
                endpoint = s2[0];
                HTTPversion = s1[2];
                String key = "", value = "";
                boolean parseKey = true;
                for (char c : s2[1].toCharArray()) {
                    if (c == '&') {
                        if (!key.isEmpty()) {
                            parameters.put(key, value);
                        }
                        key = "";
                        value = "";
                        parseKey = true;
                        continue;
                    }
                    if (c == '=') {
                        if (!parseKey) {
                            value += c;
                        }
                        parseKey = false;
                        continue;
                    }
                    if (parseKey) {
                        key += c;
                    } else {
                        value += c;
                    }
                }
                if (!key.isEmpty()) {
                    parameters.put(key, value);
                }
            } else {
                String[] s1 = inputLine.split(":");
                headers.put(s1[0].trim(), s1[1].trim());
            }

        }
        if (headers.get("Content-Type") == null) {
            readMultipart = true;
            multipartStream = new MultipartStream("", "");
        }
    }

    /**
     * Get the request body from the BufferedReader.
     */
    public String getRawBody() throws IOException {
        if (!readBody) {
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            for (int i = 0; i < contentLength; i++) {
                body.append((char) in.read());
            }
            readBody = true;
        }
        return body.toString();
    }

    /**
     * Get the request body from the BufferedReader.
     */
    public MultipartStream getBodyMultipartStream() throws IOException {
        if (!readMultipart) {
            if (!headers.get("Content-Type").contains("boundary")) {
                readMultipart = true;
                return multipartStream;
            }
            String boundary = headers.get("Content-Type").split(";")[1];
            if (readBody) {
                multipartStream = new MultipartStream(getRawBody(), boundary.trim());
            } else {
                multipartStream = new MultipartStream(in, boundary);
            }
            readMultipart = true;
        }
        return multipartStream;
    }

    private void setMethod(String methodString) {
        switch (methodString) {
            case "POST":
                method = RequestMethod.POST;
                break;
            case "PUT":
                method = RequestMethod.PUT;
                break;
            case "PATCH":
                method = RequestMethod.PATCH;
                break;
            case "DELETE":
                method = RequestMethod.DELETE;
                break;
            case "GET":
            default:
                method = RequestMethod.GET;
                break;
        }
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String removeHeader(String key, String value) {
        return headers.remove(key);
    }

    public void addParameter(String key, String value) {
        parameters.put(key, value);
    }

    public String removeParameter(String key, String value) {
        return parameters.remove(key);
    }

}
