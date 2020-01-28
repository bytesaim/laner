package io.github.thecarisma.util;

import io.github.thecarisma.laner.LanerListener;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClipboardListener implements ClipboardOwner, TRunnable {

    Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
    private ArrayList<LanerListener> lanerListeners = new ArrayList<>();
    private boolean stopListening = false;

    public ClipboardListener(LanerListener lanerListener) {
        this.lanerListeners.add(lanerListener);
    }

    public ArrayList<LanerListener> getLanerListeners() {
        return lanerListeners;
    }

    public void addLanerListener(LanerListener lanerListener) {
        this.lanerListeners.add(lanerListener);
    }

    public void removeLanerListener(LanerListener lanerListener) {
        this.lanerListeners.remove(lanerListener);
    }

    @Override
    public void run() {
        Transferable trans = sysClip.getContents(this);
        TakeOwnership(trans);

    }

    @Override
    public void lostOwnership(final Clipboard c, final Transferable t) {

        //maybe find better alt to sleep to wait for large
        //file that was copied
        //check if content changes
        Transferable contents = null;
        try {
            Thread.sleep(1000);
            contents = sysClip.getContents(this);
            broadcastToListeners(new ClipboardStatus(contents, c));
        } catch (Exception ex) {
            Logger.getLogger(ClipboardListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        //give back overship, possibly
        if (!stopListening) {
            TakeOwnership((contents != null) ? contents : t);
        }

    }

    void TakeOwnership(Transferable t) {
        sysClip.setContents(t, this);
    }

    private void broadcastToListeners(Object o) {
        for (LanerListener lanerListener : lanerListeners) {
            lanerListener.report(o);
        }
    }

    @Override
    public void stop() {
        stopListening = false;
    }

    public static class ClipboardStatus {
        public Transferable transferable;
        public Clipboard clipboard;

        public ClipboardStatus(Transferable transferable, Clipboard clipboard) {
            this.transferable = transferable;
            this.clipboard = clipboard;
        }
    }

}
