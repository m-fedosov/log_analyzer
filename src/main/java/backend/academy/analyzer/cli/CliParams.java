package backend.academy.analyzer.cli;

import backend.academy.analyzer.cli.converter.DateConverter;
import backend.academy.analyzer.cli.validator.FormatValidator;
import backend.academy.analyzer.cli.validator.PathValidator;
import com.beust.jcommander.Parameter;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class CliParams {
    @Parameter(
        names = { "-p", "--path" },
        description = "Path to log files", required = true,
        validateWith = PathValidator.class
    )
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
    private String format = "markdown";  // Default value

    @Parameter(
        names = { "--filter-field" },
        description = "Field to filter (e.g., agent, method)"
    )
    private String field;

    @Parameter(
        names = { "--filter-value" },
        description = "Value to filter by (supports * as wildcard)"
    )
    private String value;
}
