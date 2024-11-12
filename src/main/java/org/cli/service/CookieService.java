package org.cli.service;

import org.cli.util.FlagUtil;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CookieService {
    public List<String> findMostRecentCookie(String date, String csvFilePath){
        try{
            LocalDate targetDate = FlagUtil.parseDate(date);
            List<String[]> csvRecords = FlagUtil.readCSVFile(csvFilePath);
            String[] headers = csvRecords.remove(0);
            return csvRecords.stream()
                    .filter(record -> LocalDate.parse(record[1].substring(0,10)).isEqual(targetDate))
                    .map(record -> record[0])
                    .collect(Collectors.toList());
        } catch (DateTimeException e){
            throw new RuntimeException("Date was entered in incorrect format");
        }
    }
}
