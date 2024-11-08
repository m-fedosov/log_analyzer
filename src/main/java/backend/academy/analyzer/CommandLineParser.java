package backend.academy.analyzer;

import backend.academy.analyzer.cli.CliParams;
import backend.academy.analyzer.cli.validator.DateValidator;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

public class CommandLineParser {
    public static CliParams parse(String[] args) {
        CliParams params = new CliParams();
        try {
            JCommander.newBuilder()
                .addObject(params)
                .build()
                .parse(args);
            new DateValidator().validate(params);
        } catch (ParameterException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return params;
    }
}
