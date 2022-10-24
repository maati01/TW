package com.jetbrains;

public class WhileSemaphore implements Semaphore {
    private boolean state = true;
    private int wait = 0;

    @Override
    public synchronized void P() throws InterruptedException {
        wait++;
        while (!state) {
            wait();
        }
        wait--;
        state = false;
    }

    @Override
    public synchronized void V() {
        if (wait > 0) notify();
        state = true;
    }

    public String toString() {
        return "WhileSemaphore";
    }

}
