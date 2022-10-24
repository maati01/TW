package com.jetbrains;

import java.util.ArrayList;
import java.util.List;


public class Race {
    private final Counter counter;
    private final Semaphore semaphore;

    public Race(Semaphore semaphore) {
        this.semaphore = semaphore;
        counter = new Counter(this.semaphore);
    }

    private List<Thread> createIncThreads(int threadsNumber, int iterations) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadsNumber; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < iterations; j++) {
                    try {
                        counter.increment();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }));
        }
        return threads;
    }

    private List<Thread> createDecThreads(int threadsNumber, int iterations) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadsNumber; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < iterations; j++) {
                    try {
                        counter.decrement();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }));
        }
        return threads;
    }

    public void run(int threadsNumber, int iterations) throws InterruptedException {
        List<Thread> incThreads = createIncThreads(threadsNumber, iterations);
        List<Thread> decThreads = createDecThreads(threadsNumber, iterations);

        incThreads.forEach(Thread::start);
        decThreads.forEach(Thread::start);

        for (int i = 0; i < threadsNumber; i++) {
            incThreads.get(i).join();
            decThreads.get(i).join();
        }

        printSomeInfo(threadsNumber, iterations);
    }

    private void printSomeInfo(int threadsNumber, int iterations) {
        System.out.println("Iterations: " + iterations + "\nThreads: " + threadsNumber + "\nResult for " + semaphore.toString() + ": " + counter.getCounter() + "\n\n");
    }
}
