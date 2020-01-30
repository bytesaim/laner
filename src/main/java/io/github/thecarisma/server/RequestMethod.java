package io.github.thecarisma.server;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 *
 * https://restfulapi.net/http-methods/
 * //todo: move to status code
 * Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
 */
public enum RequestMethod {
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
    PUT
}
