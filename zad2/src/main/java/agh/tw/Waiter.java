package agh.tw;

import java.util.concurrent.Semaphore;

public class Waiter extends Semaphore {
    Waiter(int permits){
        super(permits);
    }

//    public boolean checkAvailability(int leftIndex, int rightIndex){
//        return table.getSticks()
//    }

}
