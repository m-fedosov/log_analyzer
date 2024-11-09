package backend.academy.analyzer.cli;

import backend.academy.analyzer.CommandLineParser;
import com.beust.jcommander.ParameterException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CliParamsTest {

    @Test
    @DisplayName("path not valid exception")
    void parseFailPath() {
        String[] args = {"-p", "file_not_exist.txt"};
        assertThrows(ParameterException.class, () -> {
            CommandLineParser.parse(args);
        });
    }

    @Test
    @DisplayName("path is valid, no exception")
    void parseOkPath() {
        String[] args = {"-p", "logs/2024backend_logs_examples.txt"};
        assertDoesNotThrow(() -> {
            CommandLineParser.parse(args);
        });
    }

    @Test
    @DisplayName("URL not valid exception")
    void parseFailUrl() {
        String[] args = {"-p", "badUrl://random.com/some_file.txt"};
        assertThrows(ParameterException.class, () -> {
            CommandLineParser.parse(args);
        });
    }

    @Test
    @DisplayName("URL is valid, no exception")
    void parseOkUrl() {
        String[] args = {"-p", "https://good_url/nginx_logs"};
        assertDoesNotThrow(() -> {
            CommandLineParser.parse(args);
        });
    }

    @Test
    @DisplayName("fromDate is after toDate - wrong")
    void parseWrongDate() {
        String[] args = {"-p", "logs/2024backend_logs_examples.txt", "--from", "2024-08-31", "--to", "2024-07-31"};
        assertThrows(ParameterException.class, () -> {
            CommandLineParser.parse(args);
        });
    }

    @Test
    @DisplayName("fromDate is before toDate - ok")
    void parseOkDateFull() {
        String[] args = {"-p", "logs/2024backend_logs_examples.txt", "--from", "2024-07-31", "--to", "2024-08-31"};
        assertDoesNotThrow(() -> {
            CommandLineParser.parse(args);
        });
    }

    @Test
    @DisplayName("fromDate only - ok")
    void parseOkDateFrom() {
        String[] args = {"-p", "logs/2024backend_logs_examples.txt", "--from", "2024-07-31"};
        assertDoesNotThrow(() -> {
            CommandLineParser.parse(args);
        });
    }

    @Test
    @DisplayName("toDate only - ok")
    void parseOkDateTo() {
        String[] args = {"-p", "logs/2024backend_logs_examples.txt", "--to", "2024-08-31"};
        assertDoesNotThrow(() -> {
            CommandLineParser.parse(args);
        });
    }
}
