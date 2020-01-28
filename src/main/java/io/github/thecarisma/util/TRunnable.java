package io.github.thecarisma.util;

import java.io.IOException;

public interface TRunnable extends Runnable {
    void stop() throws Exception;
}
