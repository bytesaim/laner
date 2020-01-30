package io.github.thecarisma.server;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 */
public interface ServerListener {
    void report(Request request, Response response);
    void report(BufferedReader in, PrintWriter out);
}
