package io.github.thecarisma.laner;

import io.github.thecarisma.util.TimedTRunnableKiller;
import org.junit.Test;

public class TestEthernetStatus {

    @Test
    public void Test1() {
        EthernetStatus ethernetStatus = new EthernetStatus(new LanerListener() {
            @Override
            public void report(Object o) {
                if (o instanceof ConnectionStatus) {
                    System.out.println(o);
                }
            }
        });
        ethernetStatus.run();
        TimedTRunnableKiller.timeTRunnableDeath(ethernetStatus, 10);
    }

}
