package backend.academy.analyzer;

import backend.academy.analyzer.cli.CliParams;
import backend.academy.analyzer.log.reader.LogReader;
import backend.academy.analyzer.log.report.LogReport;
import backend.academy.analyzer.log.report.ReportTextFormatAsciiDoc;
import backend.academy.analyzer.log.report.ReportTextFormatMarkdown;

public class ReportGenerator {
    public static String generateReport(CliParams params) {
        String report = "";
        try {
            LogReader logReader = new LogReader(params.path(), params.fromDate(), params.toDate());
            LogReport logReport = new LogReport(logReader.read());
            if (params.format().equals("markdown")) {
                ReportTextFormatMarkdown markdownLogReport = new ReportTextFormatMarkdown();
                report = markdownLogReport.generate(logReport);
            }
            if (params.format().equals("adoc")) {
                ReportTextFormatAsciiDoc asciiDocLogReport = new ReportTextFormatAsciiDoc();
                report = asciiDocLogReport.generate(logReport);
            }
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return report;
    }
}
