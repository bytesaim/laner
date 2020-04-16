package com.bytesaim.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 */
public interface ServerRawListener extends ServerListenerFactory {
    void report(InputStream in, OutputStream out) throws IOException;
}
