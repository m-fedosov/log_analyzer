package backend.academy.analyzer;

import backend.academy.analyzer.cli.CliParams;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {
    public static void main(String[] args) {
        CliParams params = CommandLineParser.parse(args);
        String report = ReportGenerator.generateReport(params);
        System.out.println(report);
    }
}
