package agh.tw;

import agh.tw.utils.CsvWriter;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Table table = new Table(5);
        table.getPhilosophers().forEach(Thread::start);
        table.getPhilosophers().forEach(philosopher -> {
            try {
                philosopher.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        table.getPhilosophers().forEach(s -> System.out.printf("Philosopher %d: %d     %s\n",
                s.getIndex(), s.getEatingCounter(), s.getTimer().getWaitArray()));


        CsvWriter csvWriter = new CsvWriter(table);
        csvWriter.writeToCsv();

    }
}
