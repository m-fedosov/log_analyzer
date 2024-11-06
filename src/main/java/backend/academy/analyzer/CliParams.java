package backend.academy.analyzer;

import com.beust.jcommander.Parameter;
import lombok.Getter;

@Getter
public class CliParams {
    // TODO: добавить валидацию параметров
    @Parameter(names = { "-p", "--path" }, description = "Path to log files", required = true)
    private String path;

//    @Parameter(names = "--from", description = "Дата начала диапазона", converter = LocalDateConverter.class)
//    private LocalDate fromDate;

    @Parameter(names = { "-f", "--format" }, description = "Output format")
    private String format;
}