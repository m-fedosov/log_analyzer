package backend.academy.analyzer.cli;

import com.beust.jcommander.Parameter;
import lombok.Getter;
import java.time.LocalDate;

@Getter
public class CliParams {
    // TODO: добавить валидацию параметров
    @Parameter(
        names = { "-p", "--path" },
        description = "Path to log files", required = true)
    private String path;

    @Parameter(
        names = { "--from" },
        description = "Start date in format yyyy-MM-dd (optional)",
        converter = DateConverter.class
    )
    private LocalDate fromDate;

    @Parameter(
        names = { "--to" },
        description = "End date in format yyyy-MM-dd (optional)",
        converter = DateConverter.class
    )
    private LocalDate toDate;

    @Parameter(
        names = { "-f", "--format" },
        description = "Output format (allowed values: markdown, adoc)",
        validateWith = FormatValidator.class
    )
    private String format = "markdown";  // Значение по умолчанию
}
