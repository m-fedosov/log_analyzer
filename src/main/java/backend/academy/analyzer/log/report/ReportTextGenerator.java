package backend.academy.analyzer.log.report;

import java.time.format.DateTimeFormatter;
import java.util.Map;

@SuppressWarnings("MultipleStringLiterals")
public abstract class ReportTextGenerator {
    protected DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public String generate(LogReport logReport) {
        StringBuilder report = new StringBuilder();

        report.append(formatHeader("Общая информация"))
            .append(formatTableStart("Метрика", "Значение"))
            .append(formatTableRow("Файл(-ы)", String.join(", ", logReport.fileNames())))
            .append(formatTableRow(
                "Начальная дата",
                logReport.fromDate() == null ? "-" : logReport.fromDate().format(dateFormatter))
            )
            .append(formatTableRow(
                "Конечная дата",
                logReport.toDate() == null ? "-" : logReport.toDate().format(dateFormatter))
            )
            .append(formatTableRow(
                "Количество запросов", String.format("%,d", logReport.recordsCount()))
            )
            .append(formatTableRow(
                "Средний размер ответа", String.format("%,d", logReport.averageBodyBytesSent()))
            )
            .append(formatTableRow(
                "95p размера ответа", String.format("%,.2f", logReport.percentile95BodyBytesSent()))
            )
            .append(formatTableEnd())
            .append("\n");

        report.append(
            generateSortedTableSection(
                "Запрашиваемые ресурсы",
                "Ресурс",
                "Количество",
                logReport.resources())
        );

        report.append(generateStatusCodesSection(logReport));

        report.append(
            generateSortedTableSection(
                "HTTP methods",
                "Метод",
                "Количество",
                logReport.methods())
        );

        report.append(
            generateSortedTableSection(
                "User Agents",
                "httpUserAgent",
                "Количество",
                logReport.agents())
        );

        return report.toString();
    }

    @SuppressWarnings("LineLength")
    private String generateSortedTableSection(String header, String column1, String column2, Map<String, Integer> data) {
        // Generates a sorted (reverse) table section for any data
        StringBuilder section = new StringBuilder();
        section.append(formatHeader(header))
            .append(formatTableStart(column1, column2));

        data.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .forEach(entry -> section.append(formatTableRow(entry.getKey(), String.format("%,d", entry.getValue()))));

        section.append(formatTableEnd()).append("\n");
        return section.toString();
    }

    private String generateStatusCodesSection(LogReport logReport) {
        // Generates a sorted (reverse) table section for response codes
        StringBuilder section = new StringBuilder();
        section.append(formatHeader("Коды ответа"))
            .append(formatTableStart("Код", "Имя", "Количество"));

        logReport.statuses().entrySet().stream()
            .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
            .forEach(entry -> {
                String statusText
                    = org.apache.http.impl.EnglishReasonPhraseCatalog.INSTANCE.getReason(entry.getKey(), null);
                String statusName = statusText != null ? statusText : "Unknown";
                section.append(formatTableRow(
                    String.valueOf(entry.getKey()), statusName, String.format("%,d", entry.getValue()))
                );
            });

        section.append(formatTableEnd()).append("\n");
        return section.toString();
    }

    protected abstract String formatHeader(String title);

    protected abstract String formatTableStart(String... columns);

    protected abstract String formatTableRow(String... columns);

    protected abstract String formatTableEnd();
}
