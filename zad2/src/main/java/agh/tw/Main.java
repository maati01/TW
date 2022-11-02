package agh.tw;

import agh.tw.utils.CsvWriter;

import java.io.FileNotFoundException;

public class Main {
    private static boolean save = false;
    private static int sticksNumber = 10;
    private static int iterations = 100;

    public static void main(String[] args){
        parseArgs(args);

        Table table = new Table(sticksNumber, iterations);
        table.getPhilosophers().forEach(Thread::start);
        table.getPhilosophers().forEach(philosopher -> {
            try {
                philosopher.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        saveResults(table);

    }

    public static void parseArgs(String[] args){
        if(args.length == 3){
            sticksNumber = Integer.parseInt(args[0]);
            iterations = Integer.parseInt(args[1]);
            save = args[2].equals("true");
        }
    }

    public static void saveResults(Table table){
        if(save) {
            CsvWriter csvWriter = new CsvWriter(table);
            try {
                csvWriter.writeToCsv();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
