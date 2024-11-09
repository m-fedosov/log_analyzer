package backend.academy.analyzer;

import backend.academy.analyzer.cli.CliParams;
import backend.academy.analyzer.cli.validator.DateValidator;
import com.beust.jcommander.JCommander;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommandLineParser {
    private String[] args;

    public CliParams parse() {
        CliParams params = new CliParams();
        JCommander.newBuilder()
                .addObject(params)
                .build()
                .parse(args);
        new DateValidator().validate(params);
        return params;
    }
}
