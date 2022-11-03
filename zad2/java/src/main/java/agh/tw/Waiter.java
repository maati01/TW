package agh.tw;

import java.util.concurrent.Semaphore;

public class Waiter extends Semaphore {
    Waiter(int permits){
        super(permits);
    }
}
