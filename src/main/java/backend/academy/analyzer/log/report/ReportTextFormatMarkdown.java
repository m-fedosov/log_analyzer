package backend.academy.analyzer.log.report;

public class ReportTextFormatMarkdown extends ReportTextGenerator {
    @Override
    protected String formatHeader(String title) {
        return "#### " + title + "\n\n";
    }

    @Override
    protected String formatTableStart(String... columns) {
        StringBuilder stringBuilder = new StringBuilder("|");
        for (String column : columns) {
            stringBuilder.append(String.format( " %s |", column));
        }
        stringBuilder.append("\n");

        stringBuilder.append("|");
        stringBuilder.append(":----:|".repeat(columns.length));
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    @Override
    protected String formatTableRow(String... columns) {
        StringBuilder stringBuilder = new StringBuilder("|");
        for (String column : columns) {
            stringBuilder.append(String.format( " %s |", column));
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    @Override
    protected String formatTableEnd() {
        return "\n";
    }
}
