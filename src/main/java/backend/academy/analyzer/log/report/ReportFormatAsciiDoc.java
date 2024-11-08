package backend.academy.analyzer.log.report;

public class ReportFormatAsciiDoc extends ReportGenerator {
    @Override
    protected String formatHeader(String title) {
        return "== " + title + "\n\n";
    }

    @Override
    protected String formatTableStart() {
        return "|===\n";
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
