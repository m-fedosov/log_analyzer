package backend.academy.analyzer.log.reader;

import backend.academy.analyzer.cli.CliParams;
import backend.academy.analyzer.log.record.LogRecord;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.experimental.UtilityClass;

@UtilityClass
class Filter {
    static boolean checkRecord(LogRecord logRecord, CliParams params) {
        return filterByDate(logRecord, params) && filterByFieldValue(logRecord, params);
    }

    private static boolean filterByDate(LogRecord logRecord, CliParams params) {
        // Date is between [fromDateT00:00 and (toDate+1)T00:00]
        LocalDate fromDate = params.fromDate();
        LocalDate toDate = params.toDate();
        LocalDateTime logRecordTime = logRecord.timeLocal();

        if (fromDate != null && logRecordTime.isBefore(fromDate.atStartOfDay())) {
            return false;
        }
        if (toDate != null && !logRecordTime.isBefore(toDate.plusDays(1).atStartOfDay())) {
            return false;
        }
        return true;
    }

    private static boolean filterByFieldValue(LogRecord logRecord, CliParams params) {
        String field = params.field();
        String value = params.value();
        if (field == null || value == null) {
            return true;
        }

        value = value.replace("*", ".*"); // To RegEx
        if ("agent".equals(field)) {
            return logRecord.agent().matches(value);
        }
        if ("method".equals(field)) {
            return logRecord.method().matches(value);
        }

        return false;
    }
}
