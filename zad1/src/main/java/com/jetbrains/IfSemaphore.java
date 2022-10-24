package com.jetbrains;

public class IfSemaphore implements Semaphore {
    private boolean state = true;
    private int wait = 0;


    @Override
    public synchronized void P() throws InterruptedException {
        wait++;
        if (!state) wait();
        wait--;
        state = false;
    }

    @Override
    public synchronized void V() throws InterruptedException {
        if (wait > 0) notify();
        state = true;
    }

    public String toString(){
        return "IfSemaphore";
    }
}
