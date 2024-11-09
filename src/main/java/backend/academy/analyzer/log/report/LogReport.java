package backend.academy.analyzer.log.report;

import backend.academy.analyzer.log.record.LogRecord;
import com.google.common.math.Quantiles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public class LogReport {
    private final HashSet<String> fileNames = new HashSet<>();
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private long recordsCount;
    private long totalBodyBytesSent;
    private final ArrayList<Integer> bodyBytesSent = new ArrayList<>();
    private final HashMap<String, Integer> resources = new HashMap<>();
    private final HashMap<Integer, Integer> statuses = new HashMap<>();
    private final HashMap<String, Integer> methods = new HashMap<>();
    private final HashMap<String, Integer> agents = new HashMap<>();

    public LogReport(Stream<LogRecord> logRecordStream) {
        logRecordStream.forEach(this::accumulate);
        logRecordStream.close();
    }

    private void accumulate(LogRecord logRecord) {
        fileNames.add(String.valueOf(logRecord.path().getFileName()));

        recordsCount++;

        totalBodyBytesSent += logRecord.bodyBytesSent();
        bodyBytesSent.add(logRecord.bodyBytesSent());

        if (fromDate == null || logRecord.timeLocal() != null && logRecord.timeLocal().isBefore(fromDate)) {
            fromDate = logRecord.timeLocal();
        }

        if (toDate == null || logRecord.timeLocal() != null && logRecord.timeLocal().isAfter(toDate)) {
            toDate = logRecord.timeLocal();
        }

        String resource = logRecord.request().split(" ")[1];
        resources.put(resource, resources.getOrDefault(resource, 0) + 1);

        statuses.put(logRecord.status(), statuses.getOrDefault(logRecord.status(), 0) + 1);

        methods.put(logRecord.method(), methods.getOrDefault(logRecord.method(), 0) + 1);

        agents.put(logRecord.agent(), agents.getOrDefault(logRecord.agent(), 0) + 1);
    }

    public int averageBodyBytesSent() {
        if (recordsCount == 0) {
            return 0;
        }
        return (int) (totalBodyBytesSent / recordsCount);
    }

    @SuppressWarnings("MagicNumber")
    public double percentile95BodyBytesSent() {
        if (bodyBytesSent.isEmpty()) {
            return 0d;
        }
        return Quantiles.percentiles().index(95).compute(bodyBytesSent);
    }
}
