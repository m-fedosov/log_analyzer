package backend.academy.analyzer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class LogReader {
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

    public Stream<LogRecord> readFile(String path) {
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            return reader.lines()
                         .map(logLine -> LogReader.parse(Path.of(path), logLine));
        } catch (Exception e) {
            throw new RuntimeException("Failed to read logs file", e);
        }
    }
}
