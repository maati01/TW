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
            philosophers.add(new PhilosopherWithStarvation(i, this, iterations));
        }
        System.out.println("Created basic philosophers.");
    }

    private void createPhilosophersWithWaiter(int iterations){
        Waiter waiter = new Waiter(sticksNumber - 1);
        for(int i = 0; i < sticksNumber; i++){
            sticks.add(new Stick(1));
            philosophers.add(new PhilosopherWithWaiter(i, this, iterations, waiter));
        }
        System.out.println("Created a philosophers with a waiter.");
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
