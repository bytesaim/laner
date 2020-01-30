package io.github.thecarisma.exceptions;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 */
public class ResponseHeaderException extends Exception {

    public ResponseHeaderException() {
        super("Cannot append to the response header, the headers has already been sent");
    }

}
