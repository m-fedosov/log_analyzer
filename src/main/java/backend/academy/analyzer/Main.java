package backend.academy.analyzer;

import backend.academy.analyzer.cli.CliParams;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@UtilityClass
@Log4j2
public class Main {
    public static void main(String[] args) {
        CommandLineParser commandLineParser = new CommandLineParser(args);
        CliParams params = commandLineParser.parse();
        ReportGenerator reportGenerator = new ReportGenerator(params);
        log.info(reportGenerator.generateReport());
    }
}
