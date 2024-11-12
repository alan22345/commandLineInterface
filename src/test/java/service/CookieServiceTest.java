package service;

import org.cli.service.CookieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CookieServiceTest {

    private CookieService cookieService;
    private static Path tempFile;
    @BeforeEach
    public void setUp() {
        cookieService = new CookieService();
        try{
            tempFile = Files.createTempFile("test", ".csv");
            Files.write(tempFile,"cookie,timestamp\ncookie1,2024-11-09T10:00:00\ncookie2,2024-11-10T10:00:00\ncookie3,2024-11-11T10:00:00\ncookie4,2024-11-11T11:00:00\n".getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFindMostRecentCookie_ValidDateAndCSV() {

        String date = "2024-11-11";
        List<String> result = cookieService.findMostRecentCookie(date, tempFile.toString());

        assertEquals(2, result.size());
        assertTrue(result.contains("cookie4"));
        assertTrue(result.contains("cookie3"));
    }

    @Test
    public void testFindMostRecentCookie_NoMatchingDate() {

        String date = "2024-01-01";
        List<String> result = cookieService.findMostRecentCookie(date, tempFile.toString());

        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindMostRecentCookie_InvalidDateFormat() {
        String date = "invalid-date";
        String csvFilePath = "path/to/csvfile.csv";

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            cookieService.findMostRecentCookie(date, csvFilePath);
        });
        assertEquals("Date was entered in incorrect format", thrown.getMessage());
    }

    @Test
    public void testFindMostRecentCookie_EmptyCSV() {
        String date = "2024-11-12";
        try {
            Path tempEmptyFile = Files.createTempFile("empty",".csv");

            List<String> result = cookieService.findMostRecentCookie(date, tempEmptyFile.toString());

            assertTrue(result.isEmpty());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
