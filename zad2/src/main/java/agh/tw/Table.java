package agh.tw;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private final List<Stick> sticks = new ArrayList<>();
    private final List<Philosopher> philosophers = new ArrayList<>();
    private final int sticksNumber;

    Table(int sticksNumber, int iterations){
        this.sticksNumber = sticksNumber;
        for(int i = 0; i < sticksNumber; i++){
            sticks.add(new Stick(1));
            philosophers.add(new Philosopher(i, this, iterations));
        }
    }

    public List<Stick> getSticks(){
        return sticks;
    }

    public List<Philosopher> getPhilosophers(){
        return philosophers;
    }

    public int getSize(){
        return sticksNumber;
    }
}
