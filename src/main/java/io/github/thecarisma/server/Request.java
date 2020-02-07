package io.github.thecarisma.server;

import java.io.*;
import java.net.URLDecoder;
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
        while ((inputLine = bin.readLine()) != null && !inputLine.isEmpty()) {
            if (!parsedHead) {
                parsedHead = true;
                endpoint = inputLine;
                String[] s1 = endpoint.split(" ");
                setMethod(s1[0].trim());
                String[] s2 = s1[1].split("\\?");
                endpoint = URLDecoder.decode(s2[0], "UTF-8");
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
                    parameters.put(key, URLDecoder.decode(value, "UTF-8"));
                }
            } else {
                String[] s1 = inputLine.split(":");
                headers.put(s1[0].trim(), s1[1].trim());
            }

        }
        if (headers.get("Content-Type") == null) {
            readMultipart = true;
            multipartStream = new MultipartStream("", "");

        } else if (headers.get("Content-Type").equals("application/x-www-form-urlencoded")) {
            String[] bodyParams = getBody().split("&");
            for (String bodyParam : bodyParams) {
                String[] b1 = bodyParam.split("=");
                parameters.put(b1[0], (b1.length > 1 ? URLDecoder.decode(b1[1], "UTF-8") : ""));
            }
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
            int i = 0;
            while (bin.ready() && i < contentLength) {
                body.append((char) bin.read());
                i++;
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
                multipartStream = new MultipartStream(bin, "000000000");
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
