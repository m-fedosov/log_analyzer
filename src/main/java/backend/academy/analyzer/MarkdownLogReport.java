package backend.academy.analyzer;

import java.util.Map;

public class MarkdownLogReport {
    public String generate(LogReport logReport) {
        StringBuilder report = new StringBuilder();

        report.append("#### Общая информация\n\n")
            .append("|        Метрика        |     Значение |\n")
            .append("|:---------------------:|-------------:|\n")
            .append(String.format("|       Файл(-ы)        | `%s` |\n", String.join(", ", logReport.fileNames())))
            .append(String.format("|    Начальная дата     |   %s |\n", logReport.fromDate() == null ? "-" : logReport.fromDate()))
            .append(String.format("|     Конечная дата     |   %s |\n", logReport.toDate() == null ? "-" : logReport.toDate()))
            .append(String.format("|  Количество запросов  |       %,d |\n", logReport.recordsCount()))
            .append(String.format("| Средний размер ответа |       %,db |\n", logReport.averageBodyBytesSent()))
            .append(String.format("|   95p размера ответа  |       %,f |\n", logReport.percentile95BodyBytesSent()))
            .append("\n");

        report.append("#### Запрашиваемые ресурсы\n\n")
            .append("|     Ресурс      | Количество |\n")
            .append("|:---------------:|-----------:|\n");
        // Сортировка запрашиваемых ресурсов от самых частых к самым редким
        logReport.resources().entrySet()
            .stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .forEach(
                entry -> report.append(String.format("|  %-15s |      %,d |\n", entry.getKey(), entry.getValue()))
            );
        report.append("\n");

        report.append("#### Коды ответа\n\n")
            .append("| Код |          Имя          | Количество |\n")
            .append("|:---:|:---------------------:|-----------:|\n");
        // Сортировка кодов ответа от самых частых к самым редким
        logReport.statuses().entrySet()
            .stream()
            .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
            .forEach(
                entry -> {
                    String statusText = org.apache.http.impl.EnglishReasonPhraseCatalog.INSTANCE.getReason(entry.getKey(), null);
                    String statusName = statusText != null ? statusText : "Unknown";
                    report.append(String.format("| %-3d | %-20s |      %,d |\n", entry.getKey(), statusName, entry.getValue()));
                });

        return report.toString();
    }
}
