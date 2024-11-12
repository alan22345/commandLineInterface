package service;


import org.cli.parser.CommandParser;
import org.cli.service.CommandLineInterfaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommandLineInterfaceServiceTest {
    private CommandParser commandParser;
    private CommandLineInterfaceService cliService;
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errorStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        commandParser = mock(CommandParser.class);
        cliService = new CommandLineInterfaceService(commandParser);
        System.setOut(new PrintStream(outputStreamCaptor));
        System.setErr(new PrintStream(errorStreamCaptor));
    }

    @Test
    public void testListenForCommand_ValidInput() throws Exception {
        String simulatedInput = "getMostRecentCookie --date 2024-11-12 --csv mycookies.csv\nexit\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Thread thread = new Thread(() -> cliService.listenForCommand());
        thread.start();

        Thread.sleep(100);

        verify(commandParser).parseCommand("getMostRecentCookie --date 2024-11-12 --csv mycookies.csv");
        assertTrue(outputStreamCaptor.toString().contains("Enter command:"));

        thread.interrupt();
    }

    @Test
    public void testListenForCommand_EmptyInput() throws Exception {
        String simulatedInput = " ";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Thread thread = new Thread(() -> cliService.listenForCommand());
        thread.start();

        Thread.sleep(100);

        verify(commandParser, never()).parseCommand(anyString());
        assertTrue(outputStreamCaptor.toString().contains("Enter command:"));

        thread.interrupt();
    }

    @Test
    public void testListenForCommand_ExceptionHandling() throws Exception {
        String simulatedInput = "getMostRecentCookie --date 2024-11-12 --csv mycookies.csv\nexit\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        doThrow(new RuntimeException("Test exception")).when(commandParser).parseCommand(anyString());

        Thread thread = new Thread(() -> cliService.listenForCommand());
        thread.start();

        Thread.sleep(100);

        assertTrue(outputStreamCaptor.toString().contains("Enter command:"));
        assertTrue(errorStreamCaptor.toString().contains("Test exception"));

        thread.interrupt();
    }

}
