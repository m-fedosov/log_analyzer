package backend.academy.analyzer;

import backend.academy.analyzer.cli.CliParams;
import backend.academy.analyzer.cli.validator.DateValidator;
import com.beust.jcommander.JCommander;

public class CommandLineParser {
    public static CliParams parse(String[] args) {
        CliParams params = new CliParams();
        JCommander.newBuilder()
                .addObject(params)
                .build()
                .parse(args);
        new DateValidator().validate(params);
        return params;
    }
}
