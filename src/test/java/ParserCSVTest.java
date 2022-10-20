import com.dannyhromau.main.dao.parser.ParserCsv;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Testing of getting an index")
public class ParserCSVTest {
    @Test
    @DisplayName("parsing line from CSV-table")
    void getParsedLines() {
        String path = "src/main/resources/files/postings.csv";
        ParserCsv parserCsv = new ParserCsv();
        List<String> parsedLines = parserCsv.getParsedLines(path);
        int actual = parsedLines.size();
        int expected = 25;
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("parsing empty table")
    void getEmptyList() {
        String path = "src/main/resources/files/postings(for test).csv";
        ParserCsv parserCsv = new ParserCsv();
        List<String> parsedLines = parserCsv.getParsedLines(path);
        int actual = parsedLines.size();
        int expected = 0;
        assertEquals(expected, actual);
    }

}
