package agh.tw;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private final List<Stick> sticks = new ArrayList<>();
    private final List<PhilosopherBase> philosophers = new ArrayList<>();
    private final int sticksNumber;

    Table(int sticksNumber, int iterations, boolean isWaiter){
        this.sticksNumber = sticksNumber;

        if (isWaiter) {
            createPhilosophersWithWaiter(iterations);
        } else {
            createBasicPhilosophers(iterations);
        }
    }

    private void createBasicPhilosophers(int iterations){
        for(int i = 0; i < sticksNumber; i++){
            sticks.add(new Stick(1));
            philosophers.add(new Philosopher(i, this, iterations));
        }
    }

    private void createPhilosophersWithWaiter(int iterations){
        Waiter waiter = new Waiter(sticksNumber - 1);
        for(int i = 0; i < sticksNumber; i++){
            sticks.add(new Stick(1));
            philosophers.add(new PhilosopherWithWaiter(i, this, iterations, waiter));
        }
    }

    public List<Stick> getSticks(){
        return sticks;
    }

    public List<PhilosopherBase> getPhilosophers(){
        return philosophers;
    }

    public int getSize(){
        return sticksNumber;
    }
}
