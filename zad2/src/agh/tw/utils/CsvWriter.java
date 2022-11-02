package agh.tw.utils;

import agh.tw.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CsvWriter {
    Table table;

    public CsvWriter(Table table){
        this.table = table;
    }

    public void writeToCsv() throws FileNotFoundException {
        List<String> dataLines = new ArrayList<>();

        table.getPhilosophers().forEach(philosopher -> dataLines.add(String.valueOf(philosopher.getTimer().getAverageWaitingTime())));

        File csvOutputFile = new File("src/agh/tw/results/result2.csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.forEach(pw::println);
        }
    }
}
