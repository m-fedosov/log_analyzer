package backend.academy.analyzer.log.record;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record LogRecord (
    Path path,
    String remoteAddr,
    String remoteUser,
    LocalDateTime timeLocal,
    String request,
    int status,
    int bodyBytesSent,
    String httpReferer,
    String httpUserAgent
) {
    public String getMethod() {
        return (request != null && !request.isEmpty())
            ? request.split(" ")[0]
            : "Unknown";
    }

    public String getAgent() {
        return (httpUserAgent != null && !httpUserAgent.isEmpty())
            ? httpUserAgent.split("[ /]", 2)[0]
            : "Unknown";
    }
}
