package agh.tw;


import agh.tw.utils.Timer;
import jdk.jshell.spi.ExecutionControl;

public class PhilosopherBase extends Thread {
    protected final int index;
    protected final Table table;
    protected int eatingCounter = 0;
    protected final Timer timer = new Timer();
    protected final int iterations;
    protected final int leftIndex;
    protected final int rightIndex;
    private final int THINKING_TIME = 10;
    private final int EATING_TIME = 5;

    PhilosopherBase(int index, Table table, int iterations) {
        this.index = index;
        this.table = table;
        leftIndex = (index == 0) ? table.getSize() - 1 : index - 1;
        rightIndex = index;
        this.iterations = iterations;
    }

    @Override
    public void run() {
        try {
            throw new ExecutionControl.NotImplementedException("Method is not implemented");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
    }

    public void eat() {
        System.out.println("Philosopher " + index + " begins to eat.");
        try {
            sleep(EATING_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Philosopher " + index + " finished eating.");
        eatingCounter++;
    }

    public void think() {
        System.out.println("Philosopher " + index + " begins to think.");
        try {
            sleep(THINKING_TIME);
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
