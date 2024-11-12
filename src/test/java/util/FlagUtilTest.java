package util;

import org.cli.util.FlagUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FlagUtilTest {

    @Test
    public void testParseDate_ValidDate() {
        String dateString = "2024-12-11";

        LocalDate result = FlagUtil.parseDate(dateString);

        assertEquals(LocalDate.of(2024, 11, 12), result);
    }

    @Test
    public void testParseDate_InvalidDateFormat() {
        assertThrows(DateTimeException.class, () -> {
            FlagUtil.parseDate("11,11,2023");
        });
    }

    @Test
    public void testReadCSVFile_ValidFile() throws IOException {
        Path tempFile = Files.createTempFile("test",".csv");
        Files.write(tempFile, "cookie,timestamp\ncookie1,2024-11-09T10:00:00\ncookie2,2024-11-10T10:00:00\ncookie3,2024-11-11T10:00:00\ncookie4,2024-11-11T11:00:00\n".getBytes());

        List<String[]> records = FlagUtil.readCSVFile(tempFile.toString());

        assertEquals(5, records.size());
        assertArrayEquals(new String[]{"cookie4", "2024-11-11T11:00:00"}, records.get(4));
    }

    @Test
    public void testReadCSVFile_FileNotFound() {
        String filePath = "non_existent_file.csv";

        assertThrows(RuntimeException.class, () -> {
            FlagUtil.readCSVFile(filePath);
        });
    }
}