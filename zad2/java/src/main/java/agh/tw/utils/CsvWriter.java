package agh.tw.utils;


import agh.tw.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CsvWriter {
    Table table;
    private final String PATH_TO_RESULTS = "src/main/java/agh/tw/results/";

    public CsvWriter(Table table) {
        this.table = table;
    }

    public void writeToCsv(int sticksNumber, int iterations, boolean isWaiter) throws FileNotFoundException {
        List<String> dataLines = new ArrayList<>();

        table.getPhilosophers().forEach(philosopher -> dataLines.add(String.valueOf(philosopher.getTimer().getAverageWaitingTime())));

        String fileName = (isWaiter) ? "conductor" : "both_forks";
        File csvOutputFile = new File(PATH_TO_RESULTS + fileName + "_" + sticksNumber + "_" + iterations + ".txt");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.forEach(pw::println);
        }
    }
}
