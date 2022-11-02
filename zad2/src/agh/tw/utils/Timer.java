package agh.tw.utils;

import java.util.ArrayList;

public class Timer {
    private boolean reset = true;
    private long startTime;
    private long endTime;
    private final ArrayList<Double> waitArray = new ArrayList<>();

    public void startTime(){
        if(reset){
            startTime = System.nanoTime();
            reset = false;
        }
    }

    public void stopTime(){
        endTime = System.nanoTime();
        reset = true;
        waitArray.add((endTime - startTime)/1e6);
    }

    public ArrayList<Double> getWaitArray() {
        return waitArray;
    }

    public double getAverageWaitingTime() throws IllegalArgumentException{
        int sum = 0;
        for (double value : waitArray) {
            sum += value;
        }

        return (double) sum/waitArray.size();
    }
}
