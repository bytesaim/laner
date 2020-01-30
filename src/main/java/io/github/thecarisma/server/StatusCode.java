package io.github.thecarisma.server;

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
    public static int OK          = 200;

    /**
     *
     */
    public static int CREATED        = 201;

    /**
     *
     */
    public static int ACCEPTED       = 202;

    /**
     *
     */
    public static int NO_CONTENT     = 204;

    /**
     *
     */
    public static int MOVED_PERMANENTLY        = 301;

    /**
     *
     */
    public static int FOUND        = 302;

    /**
     *
     */
    public static int SEE_OTHER        = 303;

    /**
     *
     */
    public static int NOT_MODIFIED        = 304;

    /**
     *
     */
    public static int TEMPORARY_REDIRECT        = 307;

    /**
     *
     */
    public static int BAD_REQUEST        = 400;

    /**
     *
     */
    public static int UNAUTHORISED        = 401;

    /**
     *
     */
    public static int FORBIDDEN        = 403;

    /**
     *
     */
    public static int NOT_FOUND        = 404;

    /**
     *
     */
    public static int METHOD_NOT_ALLOWED        = 405;

    /**
     *
     */
    public static int NOT_ACCEPTABLE        = 406;

    /**
     *
     */
    public static int PRECONDITION_FAILED        = 412;

    /**
     *
     */
    public static int UNSUPPORTED_MEDIA_TYPE        = 415;

    /**
     *
     */
    public static int INTERNAL_SERVER_ERROR        = 500;

    /**
     *
     */
    public static int NOT_IMPLEMENTED        = 501;
}
