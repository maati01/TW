package agh.tw;

import agh.tw.utils.Timer;

public class Philosopher extends Thread {
    private final int index;
    private final Table table;
    private int eatingCounter = 0;
    private final Timer timer = new Timer();
    int leftIndex, rightIndex;


    Philosopher(int index, Table table) {
        this.index = index;
        this.table = table;
        leftIndex = (index == 0) ? table.getSize() - 1 : index - 1;
        rightIndex = index;
    }

    @Override
    public synchronized void run() {
        for (int i = 0; i < 10; i++) {
            timer.startTime();
            try {
                table.getSticks().get(leftIndex).acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (table.getSticks().get(rightIndex).tryAcquire()) {
                timer.stopTime();
                eat();
                table.getSticks().get(rightIndex).release();
            }
            table.getSticks().get(leftIndex).release();
            think();
        }

    }

    public void eat() {
        System.out.println("Philosopher " + index + " is starting to eat.");
        try {
            sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Philosopher " + index + " finished eating.");
        eatingCounter++;
    }

    public void think() {
        System.out.println("Philosopher " + index + " is starting to think.");
        try {
            sleep(75);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Philosopher " + index + " finished thinking.");
    }

    public int getEatingCounter() {
        return eatingCounter;
    }

    public int getIndex() {
        return index;
    }

    public Timer getTimer() {
        return timer;
    }
}
