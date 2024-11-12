package org.cli;

import org.cli.parser.CommandParser;
import org.cli.service.CommandLineInterfaceService;
import org.cli.service.CookieService;

public class Main {
    public static void main(String[] args) {
        CookieService cookieService = new CookieService();
        CommandParser commandParser = new CommandParser(cookieService);
        CommandLineInterfaceService commandLineInterfaceService = new CommandLineInterfaceService(commandParser);
        commandLineInterfaceService.listenForCommand();
    }
}