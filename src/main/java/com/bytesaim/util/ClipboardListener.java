package com.bytesaim.util;

import com.bytesaim.lan.LanerListener;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClipboardListener extends Thread implements ClipboardOwner {

    Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
    private ArrayList<LanerListener> lanerListeners = new ArrayList<>();
    public boolean broadcastClipboardObject = true;

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
            if (broadcastClipboardObject) {
                broadcastToListeners(new ClipboardObject(contents, c));
            } else {
                broadcastClipboardObject = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(ClipboardListener.class.getName()).log(Level.SEVERE, null, ex);
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

    public static class ClipboardObject {
        public Transferable transferable;
        public Clipboard clipboard;

        public ClipboardObject(Transferable transferable, Clipboard clipboard) {
            this.transferable = transferable;
            this.clipboard = clipboard;
        }
    }

}
