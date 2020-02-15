package com.bytesaim.server;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Adewale Azeez "azeezadewale98@gmail.com"
 *
 * Request format according to w3.org
 * @<code>
 *     Request-Line   = Method SP Request-URI SP HTTP-Version CRLF
 *     Headers = Accept
 *              | ...
 *              | ...
 * </code>
 *
 * Raw request format:
 * <code>
 *     -> A Request-line
 *     -> Zero or more header (General|Request|Entity) fields followed by CRLF
 *     -> An empty line (i.e., a line with nothing preceding the CRLF)
 *        indicating the end of the header fields
 *     -> Optionally a message-body
 * </code>
 *
 * https://www.w3.org/Protocols/rfc2616/rfc2616-sec5.html
 * https://www.tutorialspoint.com/http/http_requests.htm
 */
public class Request {

    protected InputStream in ;
    private String HttpVersion = "";
    private Map<String, String> headers = new HashMap<>();
    private Method method = Method.UNKNOWN;
    private Map<String, String> parameters = new HashMap<>();
    private String endpoint = "/";
    private byte[] body = new byte[]{};
    private MultipartStream multipartStream;
    private boolean readBody = false;
    private boolean readMultipart = false;

    public Request(InputStream in_) throws IOException {
        this.in = in_;
        ByteArrayOutputStream currentLineOut = new ByteArrayOutputStream();
        boolean parsedHead = false;
        int b;
        while ((b = in.read()) != -1) {
            currentLineOut.write(b);
            String currentLineOut_ = new String(currentLineOut.toByteArray());
            if (currentLineOut_.endsWith("\r\n")) {
                if (currentLineOut_.equals("\r\n")) {
                    break;
                }
                currentLineOut_ = currentLineOut_.replace("\r\n", "");
                if (!parsedHead) {
                    parsedHead = true;
                    endpoint = currentLineOut_;
                    String[] s1 = endpoint.split(" ");
                    setMethod(s1[0].trim());
                    String[] s2 = URLDecoder.decode(s1[1], "UTF-8").split("\\?");
                    endpoint = s2[0];
                    HttpVersion = s1[2];
                    String key = "", value = "";
                    boolean parseKey = true;
                    if (s2.length <= 1) {
                        currentLineOut = new ByteArrayOutputStream();
                        continue;
                    }
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
                        parameters.put(key, URLDecoder.decode(value, "UTF-8"));
                    }
                }
                String[] s1 = currentLineOut_.split(":");
                StringBuilder value = new StringBuilder();
                for (int i = 1; i < s1.length; i++) {
                    value.append(s1[i]);
                }
                headers.put(s1[0].trim(), value.toString().trim());
                currentLineOut = new ByteArrayOutputStream();
            }
        }
        if (headers.get("Content-Type") == null) {
            readMultipart = true;
            multipartStream = new MultipartStream("", "");

        } else if (headers.get("Content-Type").equals("application/x-www-form-urlencoded")) {
            String[] bodyParams = new String(getBody(), StandardCharsets.UTF_8).split("&");
            for (String bodyParam : bodyParams) {
                String[] b1 = bodyParam.split("=");
                parameters.put(b1[0], (b1.length > 1 ? URLDecoder.decode(b1[1], "UTF-8") : ""));
            }
        }
    }

    /**
     * Get the request body from the BufferedReader.
     */
    public byte[] getBody() throws IOException {
        if (!readBody) {
            if (headers.get("Content-Length") == null) {
                readBody = true;
                return body;
            }
            long contentLength = Integer.parseInt(headers.get("Content-Length"));
            long i = 0;
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            while (i < contentLength) {
                buffer.write(in.read());
                i++;
            }
            body = buffer.toByteArray();
            buffer.close();
            readBody = true;
        }
        return body;
    }

    protected void setMethod(String methodString) {
        switch (methodString) {
            case "POST":
                method = Method.POST;
                break;
            case "PUT":
                method = Method.PUT;
                break;
            case "PATCH":
                method = Method.PATCH;
                break;
            case "DELETE":
                method = Method.DELETE;
                break;
            case "TRACE":
                method = Method.TRACE;
                break;
            case "CONNECT":
                method = Method.CONNECT;
                break;
            case "HEAD":
                method = Method.HEAD;
                break;
            case "OPTIONS":
                method = Method.HEAD;
                break;
            case "GET":
            default:
                method = Method.GET;
                break;
        }
    }

    /**
     * Get the request body from the BufferedReader.
     */
    public MultipartStream getBodyMultipartStream() throws IOException {
        if (!readMultipart) {
            if (!headers.get("Content-Type").contains("boundary")) {
                readMultipart = true;
                multipartStream = new MultipartStream(in, "000000000");
                return multipartStream;
            }
            String boundary = headers.get("Content-Type").split(";")[1];
            if (readBody) {
                multipartStream = new MultipartStream(new String(getBody(), StandardCharsets.UTF_8), boundary.trim());
            } else {
                multipartStream = new MultipartStream(in, boundary);
            }
            readMultipart = true;
        }
        return multipartStream;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Method getMethod() {
        return method;
    }

    public String getHttpVersion() {
        return HttpVersion;
    }

}
