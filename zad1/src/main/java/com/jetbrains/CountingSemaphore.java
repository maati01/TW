package com.jetbrains;

public class CountingSemaphore implements Semaphore{
    private int resourceValue;
    private final WhileSemaphore gate;
    private final WhileSemaphore mutex;

    public CountingSemaphore(int value){
        this.resourceValue = value;
        this.gate = new WhileSemaphore();
        this.mutex = new WhileSemaphore();
    }

    @Override
    public void P() throws InterruptedException {
        gate.P();
        mutex.P();
        resourceValue --;
        if(resourceValue > 0){
            gate.V();
        }
        mutex.V();
    }

    @Override
    public void V() throws InterruptedException {
        mutex.P();
        resourceValue ++;
        if(resourceValue == 1){
            gate.V();
        }
        mutex.V();
    }

    @Override
    public String toString() {
        return "CountingSemaphore";
    }
}
