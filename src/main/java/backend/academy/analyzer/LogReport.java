package backend.academy.analyzer;

import lombok.Getter;
import java.util.stream.Stream;

@Getter
public class LogReport {
    private long recordsCount;

    LogReport(Stream<LogRecord> logRecordStream) {
        logRecordStream.forEach(_ -> accumulate());
        logRecordStream.close();
    }

    private void accumulate() {
        recordsCount++;
    }
}
