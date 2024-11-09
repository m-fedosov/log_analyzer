package backend.academy.analyzer;

import backend.academy.analyzer.cli.CliParams;
import backend.academy.analyzer.log.reader.LogReader;
import backend.academy.analyzer.log.report.LogReport;
import backend.academy.analyzer.log.report.ReportTextFormatAsciiDoc;
import backend.academy.analyzer.log.report.ReportTextFormatMarkdown;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReportGenerator {
    private CliParams params;

    public String generateReport() {
        String report = "";
        LogReader logReader = new LogReader(params);
        LogReport logReport = new LogReport(logReader.read());
        if (params.format().equals("markdown")) {
            ReportTextFormatMarkdown markdownLogReport = new ReportTextFormatMarkdown();
            report = markdownLogReport.generate(logReport);
        }
        if (params.format().equals("adoc")) {
            ReportTextFormatAsciiDoc asciiDocLogReport = new ReportTextFormatAsciiDoc();
            report = asciiDocLogReport.generate(logReport);
        }
        return report;
    }
}
