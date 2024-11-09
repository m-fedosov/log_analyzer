package backend.academy.analyzer.log.report;

import backend.academy.analyzer.CommandLineParser;
import backend.academy.analyzer.cli.CliParams;
import backend.academy.analyzer.log.reader.LogReader;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

class LogReportTest {

    LogReport logReport = generateLogReport();

    static LogReport generateLogReport() {
        String[] args = {"-p", "logs/2024backend_logs_10_lines.txt"};
        CommandLineParser commandLineParser = new CommandLineParser(args);
        CliParams params = commandLineParser.parse();
        LogReader logReader = new LogReader(params);
        return new LogReport(logReader.read());
    }

    @Test
    void averageBodyBytesSent() {
        assertEquals(1166, logReport.averageBodyBytesSent());
    }

    @Test
    void percentile95BodyBytesSent() {
        assertEquals(2487.05 , logReport.percentile95BodyBytesSent());
    }

    @Test
    void fromDate() {
        LocalDateTime fromDate = LocalDateTime.parse("2024-11-06T14:36:28");
        assertEquals(fromDate, logReport.fromDate());
    }

    @Test
    void toDate() {
        LocalDateTime toDate = LocalDateTime.parse("2024-11-06T14:36:28");
        assertEquals(toDate, logReport.toDate());
    }

    @Test
    void recordsCount() {
        assertEquals(10, logReport.recordsCount());
    }

    @Test
    void resources() {
        HashMap<String, Integer> resources = new HashMap<>();
        resources.put("/protocol/uniform-intranet_Distributed.htm", 1);
        resources.put("/dedicated-open%20architecture-complexity-Digitized.js", 1);
        resources.put("/migration%20definition.css", 1);
        resources.put("/Business-focused-didactic/conglomeration-fresh-thinking.svg", 1);
        resources.put("/collaboration%20alliance-flexibility_motivating/capability.gif", 1);
        resources.put("/standardization.htm", 1);
        resources.put("/Multi-tiered%20Front-line.js", 1);
        resources.put("/leverage/multimedia-non-volatile_actuating/executive.css", 1);
        resources.put("/24%20hour/static.php", 1);
        resources.put("/moderator/non-volatile/array/4th%20generation.svg", 1);
        assertEquals(resources, logReport.resources());
    }

    @Test
    void statuses() {
        HashMap<Integer, Integer> statuses = new HashMap<>();
        statuses.put(400, 1);
        statuses.put(500, 1);
        statuses.put(404, 2);
        statuses.put(200, 6);
        assertEquals(statuses, logReport.statuses());
    }

    @Test
    void methods() {
        HashMap<String, Integer> methods = new HashMap<>();
        methods.put("HEAD", 1);
        methods.put("DELETE", 2);
        methods.put("GET", 7);
        assertEquals(methods, logReport.methods());
    }

    @Test
    void agents() {
        HashMap<String, Integer> agents = new HashMap<>();
        agents.put("Mozilla", 9);
        agents.put("Opera", 1);
        assertEquals(agents, logReport.agents());
    }
}
