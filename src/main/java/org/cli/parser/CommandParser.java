package org.cli.parser;

import org.cli.service.CookieService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.cli.constant.CommandConstant.GET_MOST_RECENT_COOKIE_COMMAND;
import static org.cli.constant.FlagConstant.CSV_FILENAME_FLAG;
import static org.cli.constant.FlagConstant.DATE_FLAG;

public class CommandParser {
    private final CookieService cookieService;

    public CommandParser(CookieService cookieService) {
        this.cookieService = cookieService;
    }

    public void parseCommand(String command){
        String[] args = command.split(" ");
        Map<String,String> flagMap = parseFlags(args);
        if (GET_MOST_RECENT_COOKIE_COMMAND.equals(args[0])) {
            List<String> cookieList = cookieService.findMostRecentCookie(flagMap.get(DATE_FLAG), flagMap.get(CSV_FILENAME_FLAG));
            handleCookieOut(cookieList);
        } else {
            throw new RuntimeException("Unknown command used");
        }
    }

    private Map<String, String> parseFlags(String[] flags){
        HashMap<String,String> flagMap = new HashMap<>();
        try {
            for (int i = 1; i < flags.length; i+=2) {
                flagMap.put(flags[i],flags[i+1]);
            }
        } catch (IndexOutOfBoundsException e){
            throw new RuntimeException("Expecting an input for every flag");
        }
        return flagMap;
    }

    private void handleCookieOut(List<String> cookieList){
        if(cookieList.isEmpty()){
            throw new RuntimeException("No cookies found");
        } else {
            for(String cookie: cookieList){
                System.out.println(cookie);
            }
        }
    }
}
