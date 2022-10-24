package com.jetbrains;

public class Counter {
    private final Semaphore semaphore;
    private int counter = 0;

    Counter(Semaphore semaphore){
        this.semaphore = semaphore;
    }

    public int getCounter() {
        return counter;
    }

    void increment() throws InterruptedException {
        semaphore.P();
        counter++;
        semaphore.V();
    }

    void decrement() throws InterruptedException {
        semaphore.P();
        counter--;
        semaphore.V();
    }
}
