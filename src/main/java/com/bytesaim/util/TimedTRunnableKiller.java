package com.bytesaim.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimedTRunnableKiller {

    public static void timeTRunnableDeath(final TRunnable tRunnable, final int timeInSeconds) {
        final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    if (tRunnable.isRunning()) {
                        tRunnable.stop();
                        executor.shutdown();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, timeInSeconds, TimeUnit.SECONDS);
    }

}
