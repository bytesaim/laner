package io.github.thecarisma.server;

import java.io.*;
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
    protected BufferedReader bin;
    private String HttpVersion = "";
    private Map<String, String> headers = new HashMap<>();
    private Method method = Method.UNKNOWN;
    private Map<String, String> parameters = new HashMap<>();
    private String endpoint = "/";
    private StringBuilder body = new StringBuilder();
    private MultipartStream multipartStream;
    private boolean readBody = false;
    private boolean readMultipart = false;

    public Request(InputStream in) throws IOException {
        this.in = in;
        this.bin = new BufferedReader(new InputStreamReader(in));
        boolean parsedHead = false;
        String inputLine;
        if (bin.ready()) {
            while ((inputLine = bin.readLine()).length() != 0) {
                if (!parsedHead) {
                    parsedHead = true;
                    endpoint = inputLine;
                    String[] s1 = endpoint.split(" ");
                    setMethod(s1[0].trim());
                    String[] s2 = s1[1].split("\\?");
                    endpoint = s2[0];
                    HttpVersion = s1[2];
                    String key = "", value = "";
                    boolean parseKey = true;
                    if (s2.length <= 1) {
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
                        parameters.put(key, value);
                    }
                } else {
                    String[] s1 = inputLine.split(":");
                    headers.put(s1[0].trim(), s1[1].trim());
                }

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
    public String getBody() throws IOException {
        if (!readBody) {
            if (headers.get("Content-Length") == null) {
                readBody = true;
                return body.toString();
            }
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            for (int i = 0; i < contentLength; i++) {
                body.append((char) bin.read());
            }
            readBody = true;
        }
        return body.toString();
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
                return multipartStream;
            }
            String boundary = headers.get("Content-Type").split(";")[1];
            if (readBody) {
                multipartStream = new MultipartStream(getBody(), boundary.trim());
            } else {
                multipartStream = new MultipartStream(bin, boundary);
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
