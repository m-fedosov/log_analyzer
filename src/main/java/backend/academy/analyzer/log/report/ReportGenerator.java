package backend.academy.analyzer.log.report;

import java.time.format.DateTimeFormatter;
import java.util.Map;

public abstract class ReportGenerator {
    protected DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public String generate(LogReport logReport) {
        StringBuilder report = new StringBuilder();

        report.append(formatHeader("Общая информация"))
            .append(formatTableStart("Метрика", "Значение"))
            .append(formatTableRow("Файл(-ы)", String.join(", ", logReport.fileNames())))
            .append(formatTableRow("Начальная дата", logReport.fromDate() == null ? "-" : logReport.fromDate().format(dateFormatter)))
            .append(formatTableRow("Конечная дата", logReport.toDate() == null ? "-" : logReport.toDate().format(dateFormatter)))
            .append(formatTableRow("Количество запросов", String.format("%,d", logReport.recordsCount())))
            .append(formatTableRow("Средний размер ответа", String.format("%,d", logReport.averageBodyBytesSent())))
            .append(formatTableRow("95p размера ответа", String.format("%,.2f", logReport.percentile95BodyBytesSent())))
            .append(formatTableEnd())
            .append("\n");

        report.append(formatHeader("Запрашиваемые ресурсы"))
            .append(formatTableStart("Ресурс", "Количество"));
        // Сортировка запрашиваемых ресурсов от самых частых к самым редким
        logReport.resources().entrySet()
            .stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .forEach(entry -> report.append(formatTableRow(entry.getKey(), String.format("%,d", entry.getValue()))));
        report.append(formatTableEnd())
            .append("\n");

        report.append(formatHeader("Коды ответа"))
            .append(formatTableStart("Код", "Имя", "Количество"));
        // Сортировка кодов ответа от самых частых к самым редким
        logReport.statuses().entrySet()
            .stream()
            .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
            .forEach(entry -> {
                String statusText = org.apache.http.impl.EnglishReasonPhraseCatalog.INSTANCE.getReason(entry.getKey(), null);
                String statusName = statusText != null ? statusText : "Unknown";
                report.append(formatTableRow(String.valueOf(entry.getKey()), statusName, String.format("%,d", entry.getValue())));
            });
        report.append(formatTableEnd());

        return report.toString();
    }

    protected abstract String formatHeader(String title);
    protected abstract String formatTableStart(String... columns);
    protected abstract String formatTableRow(String... columns);
    protected abstract String formatTableEnd();
}
