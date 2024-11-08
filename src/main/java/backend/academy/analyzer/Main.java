package backend.academy.analyzer;

import backend.academy.analyzer.log.report.ReportFormatAsciiDoc;
import backend.academy.analyzer.log.report.LogReport;
import backend.academy.analyzer.log.report.ReportFormatMarkdown;
import com.beust.jcommander.JCommander;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {
    public static void main(String[] args) {
        CliParams params = new CliParams();
        JCommander.newBuilder()
            .addObject(params)
            .build()
            .parse(args);
        LogReader logReader = new LogReader();
        LogReport logReport = new LogReport(logReader.readFile(params.path()));
        ReportFormatMarkdown markdownLogReport = new ReportFormatMarkdown();
        System.out.println(markdownLogReport.generate(logReport));
        ReportFormatAsciiDoc asciiDocLogReport = new ReportFormatAsciiDoc();
        System.out.println(asciiDocLogReport.generate(logReport));
    }
}
