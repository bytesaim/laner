package io.github.thecarisma.laner;

import java.io.OutputStream;
import java.io.PrintWriter;

public class LanerPrintWriter extends PrintWriter {

    public LanerPrintWriter(PrintWriter writer) {
        super(writer);
    }

    public LanerPrintWriter(OutputStream out) {
        super(out);
    }

    public LanerPrintWriter(PrintWriter writer, boolean autoFlush) {
        super(writer, autoFlush);
    }

    public LanerPrintWriter(OutputStream out, boolean autoFlush) {
        super(out, autoFlush);
    }

    public boolean isOpen() {
        return out != null;
    }

}
