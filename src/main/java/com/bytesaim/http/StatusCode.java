package com.bytesaim.http;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 *
 * Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
 *
 * https://restfulapi.net/http-status-codes/
 * https://tools.ietf.org/html/rfc7231
 * https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
 */
public enum StatusCode {

    // 2XX
    OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    NO_CONTENT(204, "No Content"),
    PARTIAL_CONTENT(206, "Partial Content"),

    // 3XX
    MOVED_PERMANENTLY(301, "Moved Permanently"),
    FOUND(302, "Found"),
    SEE_OTHER(303, "See Other"),
    NOT_MODIFIED(304, "Not Modified"),
    TEMPORARY_REDIRECT(307, "Temporary Redirect"),

    // 4XX
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    PRECONDITION_FAILED(412, "Precondition Failed"),
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),

    // 5XX
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented"),

    ;

    /**
     *
     */
    private final int code;

    /**
     *
     */
    private final String value;

    /**
     *
     * @param code
     * @param value
     */
    StatusCode(int code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     *
     * @return
     */
    public int getCode() {
        return code;
    }

    /**
     *
     * @return
     */
    public String getValue() {
        return value;
    }

}
