package backend.academy.analyzer;

import backend.academy.analyzer.cli.CliParams;

public class Main {
    public static void main(String[] args) {
        CliParams params = CommandLineParser.parse(args);
        String report = ReportGenerator.generateReport(params);
        System.out.println(report);
    }
}
