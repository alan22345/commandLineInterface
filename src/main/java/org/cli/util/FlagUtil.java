package org.cli.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FlagUtil {
    public static LocalDate parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
        return LocalDate.parse(dateString, formatter);
    }

    public static List<String[]> readCSVFile(String filePath){
        StringBuilder stringBuilder = readLines(filePath);
        return getRecords(stringBuilder);
    }
    private static StringBuilder readLines(String filePath){
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null){
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e){
            throw new RuntimeException("Couldn't read the specified file");
        }
        return stringBuilder;
    }

    private static List<String[]> getRecords(StringBuilder stringBuilder){
        List<String[]> records = new ArrayList<>();
        String[] lines = stringBuilder.toString().split("\n");
        for (String line : lines){
            String[] fields = line.split(",");
            records.add(fields);
        }
        return records;
    }
}
