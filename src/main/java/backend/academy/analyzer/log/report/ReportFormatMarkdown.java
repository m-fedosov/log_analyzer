package backend.academy.analyzer.log.report;

public class ReportFormatMarkdown extends ReportGenerator {
    @Override
    protected String formatHeader(String title) {
        return "#### " + title + "\n\n";
    }

    @Override
    protected String formatTableStart() {
        return "|:---------------------:|-------------:|\n";
    }

    @Override
    protected String formatTableRow(String... columns) {
        return String.format("| %-20s | %s |\n", columns[0], columns[1]);
    }

    @Override
    protected String formatTableEnd() {
        return "\n";
    }
}
