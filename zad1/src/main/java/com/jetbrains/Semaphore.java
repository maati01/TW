package com.jetbrains;

public interface Semaphore {
    void P() throws InterruptedException;

    void V() throws InterruptedException;
}
