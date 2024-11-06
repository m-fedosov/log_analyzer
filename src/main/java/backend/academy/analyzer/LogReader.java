package backend.academy.analyzer;

import lombok.AllArgsConstructor;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class LogReader {
    String path;

    private static final Pattern LOG_PATTERN = Pattern.compile(
        "^(?<remoteAddr>\\S+) - (?<remoteUser>\\S*) \\[(?<timeLocal>[^\\]]+)] " +
            "\"(?<request>[^\"]+)\" (?<status>\\d{3}) (?<bodyBytesSent>\\d+) " +
            "\"(?<httpReferer>[^\"]*)\" \"(?<httpUserAgent>[^\"]*)\""
    );

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z");

    private static LogRecord parse(String logLine) {
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
            remoteAddr, remoteUser, timeLocal, request, status, bodyBytesSent, httpReferer, httpUserAgent
        );
    }

    public List<LogRecord> readFile() {
        try(
            FileInputStream fileInputStream = new FileInputStream(this.path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream))
        ) {
            ArrayList<LogRecord> logRecords = new ArrayList<>();
            String str;
            while ((str = reader.readLine()) != null)   {
                logRecords.add(parse(str));
            }
            return logRecords;
        } catch (Exception e) {
            throw new RuntimeException("Failed to read logs file", e);
        }
    }
}