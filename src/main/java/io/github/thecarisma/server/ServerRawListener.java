package io.github.thecarisma.server;

import java.io.Reader;
import java.io.Writer;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 */
public interface ServerRawListener extends ServerListenerFactory {
    void report(Reader in, Writer out);
}
