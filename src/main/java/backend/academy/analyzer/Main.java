package backend.academy.analyzer;

import backend.academy.analyzer.cli.CliParams;
import backend.academy.analyzer.log.report.ReportFormatAsciiDoc;
import backend.academy.analyzer.log.report.LogReport;
import backend.academy.analyzer.log.report.ReportFormatMarkdown;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {
    public static void main(String[] args) {
        CliParams params = new CliParams();
        try {
            JCommander.newBuilder()
                .addObject(params)
                .build()
                .parse(args);
        } catch (ParameterException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        LogReader logReader = new LogReader();
        LogReport logReport = new LogReport(logReader.readFile(params.path()));
        if (params.format().equals("markdown")) {
            ReportFormatMarkdown markdownLogReport = new ReportFormatMarkdown();
            System.out.println(markdownLogReport.generate(logReport));
        }
        if (params.format().equals("adoc")) {
            ReportFormatAsciiDoc asciiDocLogReport = new ReportFormatAsciiDoc();
            System.out.println(asciiDocLogReport.generate(logReport));
        }
    }
}
