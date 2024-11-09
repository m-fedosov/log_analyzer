package backend.academy.analyzer;

import backend.academy.analyzer.cli.CliParams;
import com.beust.jcommander.JCommander;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorTest {

    @Test
    @DisplayName("Wrong log line format can't parse")
    void generateReportFailPath() {
        String[] args = {"-p", "logs/wrong_log_line_format.txt"};
        CliParams params = CommandLineParser.parse(args);
        assertThrows(IllegalArgumentException.class, () -> {
            ReportGenerator.generateReport(params);
        });
    }

    @Test
    @DisplayName("Ok log line format parsed")
    void generateReportOkPath() {
        String[] args = {"-p", "logs/2024backend_logs_examples.txt"};
        CliParams params = CommandLineParser.parse(args);
        assertDoesNotThrow(() -> {
            ReportGenerator.generateReport(params);
        });
    }
}
