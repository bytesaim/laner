package io.github.thecarisma.laner;

import java.io.BufferedReader;
import java.io.Reader;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 */
public class LanerBufferedReader  extends BufferedReader {

    private Reader in;

    public LanerBufferedReader(Reader in) {
        super(in);
        this.in = in;
    }

    public Reader getReader() {
        return in;
    }

}
