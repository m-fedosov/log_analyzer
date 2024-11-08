package backend.academy.analyzer;

import lombok.AllArgsConstructor;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@AllArgsConstructor
public class LogReader {
    private String path;
    private LocalDate fromDate;
    private LocalDate toDate;

    private static final Pattern LOG_PATTERN = Pattern.compile(
        "^(?<remoteAddr>\\S+) - (?<remoteUser>\\S*) \\[(?<timeLocal>[^\\]]+)] " +
            "\"(?<request>[^\"]+)\" (?<status>\\d{3}) (?<bodyBytesSent>\\d+) " +
            "\"(?<httpReferer>[^\"]*)\" \"(?<httpUserAgent>[^\"]*)\""
    );

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z");

    private static LogRecord parse(Path path, String logLine) {
        Matcher matcher = LOG_PATTERN.matcher(logLine);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Can't parse. Wrong log line format: " + logLine);
        }

        String remoteAddr       = matcher.group("remoteAddr");
        String remoteUser       = matcher.group("remoteUser");
        LocalDateTime timeLocal = LocalDateTime.parse(matcher.group("timeLocal"), DATE_FORMATTER);
        String request          = matcher.group("request");
        int status              = Integer.parseInt(matcher.group("status"));
        int bodyBytesSent       = Integer.parseInt(matcher.group("bodyBytesSent"));
        String httpReferer      = matcher.group("httpReferer");
        String httpUserAgent    = matcher.group("httpUserAgent");

        return new LogRecord(
            path, remoteAddr, remoteUser, timeLocal, request, status, bodyBytesSent, httpReferer, httpUserAgent
        );
    }

    public Stream<LogRecord> readFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            return reader.lines()
                         .map(logLine -> LogReader.parse(Path.of(path), logLine))
                         .filter(logRecord -> this.filterByDate(logRecord.timeLocal()));
        } catch (Exception e) {
            throw new RuntimeException("Failed to read logs file", e);
        }
    }

    private boolean filterByDate(LocalDateTime logRecordTime) {
        // Date is between [fromDateT00:00 and (toDate+1)T00:00]
        if (fromDate != null && logRecordTime.isBefore(fromDate.atStartOfDay())) {
            return false;
        }
        if (toDate != null && !logRecordTime.isBefore(toDate.plusDays(1).atStartOfDay())) {
            return false;
        }
        return true;
    }
}
