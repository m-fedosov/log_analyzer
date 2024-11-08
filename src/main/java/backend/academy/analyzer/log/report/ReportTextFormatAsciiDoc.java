package backend.academy.analyzer.log.report;

public class ReportTextFormatAsciiDoc extends ReportTextGenerator {
    @Override
    protected String formatHeader(String title) {
        return "== " + title + "\n\n";
    }

    @Override
    protected String formatTableStart(String... columns) {
        return "|===\n" + "| " + String.join(" | ", columns) + "\n";
    }

    @Override
    protected String formatTableRow(String... columns) {
        return "| " + String.join(" | ", columns) + "\n";
    }

    @Override
    protected String formatTableEnd() {
        return "|===\n";
    }
}
