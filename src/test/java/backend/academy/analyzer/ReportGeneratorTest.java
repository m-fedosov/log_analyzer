package backend.academy.analyzer;

import backend.academy.analyzer.cli.CliParams;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorTest {

    @Test
    @DisplayName("Wrong log line format can't parse")
    void generateReportFailPath() {
        String[] args = {"-p", "logs/wrong_log_line_format.txt"};
        CommandLineParser commandLineParser = new CommandLineParser(args);
        CliParams params = commandLineParser.parse();
        ReportGenerator reportGenerator = new ReportGenerator(params);
        assertThrows(IllegalArgumentException.class, reportGenerator::generateReport);
    }

    @Test
    @DisplayName("Ok log line format parsed")
    void generateReportOkPath() {
        String[] args = {"-p", "logs/2024backend_logs_examples.txt"};
        CommandLineParser commandLineParser = new CommandLineParser(args);
        CliParams params = commandLineParser.parse();
        ReportGenerator reportGenerator = new ReportGenerator(params);
        assertDoesNotThrow(reportGenerator::generateReport);
    }
}
