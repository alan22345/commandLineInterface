package parser;

import org.cli.parser.CommandParser;
import org.cli.service.CookieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommandParserTest {
    private CookieService cookieService;
    private CommandParser commandParser;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        cookieService = mock(CookieService.class);
        commandParser = new CommandParser(cookieService);
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testParseCommand_ValidCommand() {
        String command = "getMostRecentCookie -d 2024-11-12 -f mycookies.csv";
        List<String> mockCookies = Arrays.asList("cookie1", "cookie2");
        when(cookieService.findMostRecentCookie("2024-11-12", "mycookies.csv")).thenReturn(mockCookies);

        commandParser.parseCommand(command);
        String out = outputStreamCaptor.toString();
        out = out.replaceAll("\r","");
        String expected = "cookie1\ncookie2\n";
        assertEquals(out, expected);
        verify(cookieService).findMostRecentCookie("2024-11-12", "mycookies.csv");
    }

    @Test
    public void testParseCommand_UnknownCommand() {
        String command = "unknownCommand";

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            commandParser.parseCommand(command);
        });
        assertEquals("Unknown command used", thrown.getMessage());
    }

    @Test
    public void testParseFlags_MissingFlagValue() {
        String command = "getMostRecentCookie -d";

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            commandParser.parseCommand(command);
        });
        assertEquals("Expecting an input for every flag", thrown.getMessage());
    }

    @Test
    public void testHandleCookieOut_NoCookiesFound() {
        List<String> emptyList = new ArrayList<>();
        when(cookieService.findMostRecentCookie(anyString(), anyString())).thenReturn(emptyList);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            commandParser.parseCommand("getMostRecentCookie --date 2024-11-12 --csv mycookies.csv");
        });
        assertEquals("No cookies found", thrown.getMessage());
    }

    @Test
    public void testParseCommand_EmptyCommand() {
        String command = "";

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            commandParser.parseCommand(command);
        });
        assertEquals("Unknown command used", thrown.getMessage());
    }
}