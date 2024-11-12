package org.cli.service;

import org.cli.parser.CommandParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandLineInterfaceService {
    private final CommandParser commandParser;

    public CommandLineInterfaceService(CommandParser commandParser) {
        this.commandParser = commandParser;
    }

    public void listenForCommand(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter command:");
        while(true){
            try{
                String input = reader.readLine();
                if (input == null || input.trim().isEmpty()){
                    continue;
                }
                commandParser.parseCommand(input);
            } catch (Exception e){
                System.err.println(e.getMessage());
            }
        }
    }
}
