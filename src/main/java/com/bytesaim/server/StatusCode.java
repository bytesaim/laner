package com.bytesaim.server;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 *
 * Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
 *
 * https://restfulapi.net/http-status-codes/
 */
public class StatusCode {

    /**
     *
     */
    public final static int OK          = 200;

    /**
     *
     */
    public final static int CREATED        = 201;

    /**
     *
     */
    public final static int ACCEPTED       = 202;

    /**
     *
     */
    public final static int NO_CONTENT     = 204;

    /**
     *
     */
    public final static int PARTIAL_CONTENT     = 206;

    /**
     *
     */
    public final static int MOVED_PERMANENTLY        = 301;

    /**
     *
     */
    public final static int FOUND        = 302;

    /**
     *
     */
    public final static int SEE_OTHER        = 303;

    /**
     *
     */
    public final static int NOT_MODIFIED        = 304;

    /**
     *
     */
    public final static int TEMPORARY_REDIRECT        = 307;

    /**
     *
     */
    public final static int BAD_REQUEST        = 400;

    /**
     *
     */
    public final static int UNAUTHORISED        = 401;

    /**
     *
     */
    public final static int FORBIDDEN        = 403;

    /**
     *
     */
    public final static int NOT_FOUND        = 404;

    /**
     *
     */
    public final static int METHOD_NOT_ALLOWED        = 405;

    /**
     *
     */
    public final static int NOT_ACCEPTABLE        = 406;

    /**
     *
     */
    public final static int PRECONDITION_FAILED        = 412;

    /**
     *
     */
    public final static int UNSUPPORTED_MEDIA_TYPE        = 415;

    /**
     *
     */
    public final static int INTERNAL_SERVER_ERROR        = 500;

    /**
     *
     */
    public final static int NOT_IMPLEMENTED        = 501;

    /**
     *
     * @param statusCode
     * @return
     */
    public static String statusCodeValue(int statusCode) {
        switch (statusCode) {
            case OK:
                return "OK";
            case CREATED:
                return "Created";
            case ACCEPTED:
                return "Accepted";
            case NO_CONTENT:
                return "No Content";
            case PARTIAL_CONTENT:
                return "Partial Content";
            case MOVED_PERMANENTLY:
                return "Moved Permanently";
            case FOUND:
                return "FOUND";
            case SEE_OTHER:
                return "SEE_OTHER";
            case NOT_MODIFIED:
                return "NOT_MODIFIED";
            case TEMPORARY_REDIRECT:
                return "TEMPORARY_REDIRECT";
            case BAD_REQUEST:
                return "BAD_REQUEST";
            case UNAUTHORISED:
                return "UNAUTHORISED";
            case METHOD_NOT_ALLOWED:
                return "METHOD_NOT_ALLOWED";
            case FORBIDDEN:
                return "FORBIDDEN";
            case NOT_FOUND:
                return "NOT_FOUND";
            case NOT_ACCEPTABLE:
                return "NOT_ACCEPTABLE";
            case PRECONDITION_FAILED:
                return "PRECONDITION_FAILED";
            case UNSUPPORTED_MEDIA_TYPE:
                return "UNSUPPORTED_MEDIA_TYPE";
            case INTERNAL_SERVER_ERROR:
                return "INTERNAL_SERVER_ERROR";
            case NOT_IMPLEMENTED:
                return "NOT_IMPLEMENTED";
        }
        return "UNKNOWN";
    }
}
