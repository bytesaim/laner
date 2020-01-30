package io.github.thecarisma.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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

    public PrintWriter out;
    private BufferedReader in;
    private String endpoint = "";
    private String HTTPversion = "";
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> parameters = new HashMap<>();
    private Method method = Method.UNKNOWN;
    private StringBuilder body = new StringBuilder();
    private MultipartStream multipartStream;
    private boolean readBody = false;
    private boolean readMultipart = false;

    public Request(PrintWriter out, BufferedReader in) throws IOException {
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
            case "GET":
            default:
                method = Method.GET;
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

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getHTTPversion() {
        return HTTPversion;
    }

    public void setHTTPversion(String HTTPversion) {
        this.HTTPversion = HTTPversion;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

}
