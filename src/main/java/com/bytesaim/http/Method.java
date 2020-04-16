package com.bytesaim.http;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 *
 * https://restfulapi.net/http-methods/
 * https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html
 */
public enum Method {
    /**
     * Invalid Request, never happening, except the request does not contain
     * the Status-Line
     */
    UNKNOWN,

    /**
     * CRUD OPERATION: Read.
     * This requests is used to retrieve resource representation/information only
     * and not to modify it in any way
     */
    GET,

    /**
     * CRUD OPERATION: Create.
     * This requests is used to create new subordinate resources.
     * Talking strictly in terms of REST, POST methods are used to create a
     * new resource into the collection of resources.
     */
    POST,

    /**
     * CRUD OPERATION: Delete.
     * This requests APIs are used to delete resources (identified by the Request-URI)
     */
    DELETE,

    /**
     * CRUD OPERATION: Partial Update.
     * HTTP PATCH requests are to make partial update on a resource. If you see PUT
     * requests also modify a resource entity so to make more clear.
     */
    PATCH,

    /**
     * CRUD OPERATION: Update.
     * This requests APIs primarily to update existing resource (if the resource
     * does not exist, then API may decide to create a new resource or not).
     */
    PUT,

    /**
     * CRUD OPERATION: .
     * The TRACE method is used to invoke a remote, application-layer loop- back
     * of the request message.
     */
    TRACE,

    /**
     * CRUD OPERATION: .
     * This specification reserves the method name CONNECT for use with a proxy
     * that can dynamically switch to being a tunnel.
     */
    CONNECT,

    /**
     * CRUD OPERATION: .
     * Same as GET, but it transfers the status line and the header section only.
     */
    HEAD,

    /**
     * CRUD OPERATION: .
     * Describe the communication options for the target resource.
     */
    OPTIONS
}
