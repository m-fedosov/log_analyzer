package backend.academy.analyzer;

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
        log.info(logReport.fileNames());
        log.info(logReport.fromDate());
        log.info(logReport.toDate());
        log.info(logReport.recordsCount());
        log.info(logReport.averageBodyBytesSent());
        log.info(logReport.percentile95BodyBytesSent());
        log.info(logReport.resources());
        log.info(logReport.statuses());
    }
}
