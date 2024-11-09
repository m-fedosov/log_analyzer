package backend.academy.analyzer.log.record;

import java.nio.file.Path;
import java.time.LocalDateTime;

@SuppressWarnings("RecordComponentNumber")
public record LogRecord(
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
    private static final String UNKNOWN = "Unknown";

    public String method() {
        return (request != null && !request.isEmpty())
            ? request.split(" ")[0]
            : UNKNOWN;
    }

    public String agent() {
        return (httpUserAgent != null && !httpUserAgent.isEmpty())
            ? httpUserAgent.split("[ /]", 2)[0]
            : UNKNOWN;
    }
}
