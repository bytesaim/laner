package io.github.thecarisma.util;

import io.github.thecarisma.laner.LanerListener;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClipBoardListener implements ClipboardOwner, Runnable {

    Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
    private ArrayList<LanerListener> lanerListeners = new ArrayList<>();

    public ClipBoardListener(LanerListener lanerListener) {
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
    public void lostOwnership(Clipboard c, Transferable t) {

        //maybe find better alt to sleep to wait for large
        //file that was copied
        //check if content changes
        Transferable contents = null;
        try {
            Thread.sleep(1000);
            contents = sysClip.getContents(this);
            broadcastToListeners(new ClipBoardStatus(contents, c));
        } catch (Exception ex) {
            Logger.getLogger(ClipBoardListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        //give back overship, possibly
        TakeOwnership((contents != null) ? contents : t);
    }

    void TakeOwnership(Transferable t) {
        sysClip.setContents(t, this);
    }

    private void broadcastToListeners(Object o) {
        for (LanerListener lanerListener : lanerListeners) {
            lanerListener.report(o);
        }
    }

    public static class ClipBoardStatus {
        public Transferable transferable;
        public Clipboard clipboard;

        public ClipBoardStatus(Transferable transferable, Clipboard clipboard) {
            this.transferable = transferable;
            this.clipboard = clipboard;
        }
    }

}
