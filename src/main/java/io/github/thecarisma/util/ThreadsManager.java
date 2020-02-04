package io.github.thecarisma.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//TODO: move to a different project TODAY
public class ThreadsManager {

    private Map<String, ArrayList<TRunnable>> tRunnables = new HashMap<>();

    public void register(String owner, TRunnable tRunnable) {
        if (!tRunnables.containsKey(owner)) {
            ArrayList<TRunnable> tRunable = new ArrayList<>();
            tRunnables.put(owner, tRunable);
        }
        tRunnables.get(owner).add(tRunnable);
    }

    public ArrayList<TRunnable> getTRunnables(String owner) {
        return tRunnables.get(owner);
    }

    public boolean unRegister(String owner, TRunnable tRunnable) {
        if (tRunnables.containsKey(owner)) {
            return tRunnables.get(owner).remove(tRunnable);
        }
        return false;
    }

    public void startAll(String owner) {
        if (tRunnables.containsKey(owner)) {
            ArrayList<TRunnable> runnables = tRunnables.get(owner);
            for (int i = 0; i < runnables.size(); i++) {
                if (!runnables.get(i).isRunning()) {
                    new Thread(runnables.get(i)).start();
                }
            }
        }
    }

    public void startAll() {
        for (String owner : tRunnables.keySet()) {
            ArrayList<TRunnable> runnables = tRunnables.get(owner);
            for (int i = 0; i < runnables.size(); i++) {
                if (!runnables.get(i).isRunning()) {
                    new Thread(runnables.get(i)).start();
                }
            }
        }
    }

    public void kill(String owner, TRunnable tRunnable) throws Exception {
        if (tRunnables.containsKey(owner)) {
            for (int i = 0; i < tRunnables.get(owner).size(); i++) {
                if (tRunnables.get(owner).get(i).equals(tRunnable)) {
                    if (tRunnables.get(owner).get(i).isRunning()) {
                        tRunnables.get(owner).get(i).stop();
                        //unRegister(owner, tRunnables.get(owner).get(i));
                    }
                }
            }
        }
    }

    public void killAll(String owner) throws Exception {
        if (tRunnables.containsKey(owner)) {
            ArrayList<TRunnable> runnables = tRunnables.get(owner);
            for (int i = 0; i < runnables.size(); i++) {
                if (runnables.get(i).isRunning()) {
                    runnables.get(i).stop();
                }
            }
        }
    }

    public void killAll() throws Exception {
        for (String owner : tRunnables.keySet()) {
            ArrayList<TRunnable> runnables = tRunnables.get(owner);
            for (int i = 0; i < runnables.size(); i++) {
                if (runnables.get(i).isRunning()) {
                    runnables.get(i).stop();
                    //unRegister(owner, tRunnables.get(owner).get(i));
                }
            }
        }
    }

    public static Runnable getRunnable(Thread t) {
        Field f = null;
        try {
            f = Thread.class.getDeclaredField("target");
            f.setAccessible(true);
            return (Runnable) Objects.requireNonNull(f).get(t);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // might happen in a different version of Java (works in Java 7)
            e.printStackTrace();
        } // shouldn't happen, we already made the field accessible when we created it

        return null;
    }

}
