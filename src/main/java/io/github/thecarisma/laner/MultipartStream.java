package io.github.thecarisma.laner;

import java.util.HashMap;
import java.util.Map;

public class MultipartStream {

    public final String rawBody ;
    public final String boundary ;

    public MultipartStream(String rawBody) {
        this.rawBody = rawBody;
        this.boundary = ""; //read from content
    }

    public MultipartStream(String rawBody, String boundary) {
        this.rawBody = rawBody;
        this.boundary = boundary;
    }

    public boolean hasnext() {
        return false;
    }

    public MultipartData next() {
        return null;
    }

}
