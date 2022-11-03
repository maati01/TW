package agh.tw;

public class Philosopher extends PhilosopherBase{
    Philosopher(int index, Table table, int iterations) {
        super(index, table, iterations);
    }

    @Override
    public synchronized void run() {
        for (int i = 0; i < iterations; i++) {
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
}
