package backend.academy.analyzer.log.record;

import java.nio.file.Path;
import java.time.LocalDateTime;

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
) {}
