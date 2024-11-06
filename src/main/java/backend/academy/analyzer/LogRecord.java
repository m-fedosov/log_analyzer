package backend.academy.analyzer;

import java.time.LocalDateTime;

public record LogRecord (
    String remoteAddr,
    String remoteUser,
    LocalDateTime timeLocal,
    String request,
    int status,
    int bodyBytesSent,
    String httpReferer,
    String httpUserAgent
) {}
