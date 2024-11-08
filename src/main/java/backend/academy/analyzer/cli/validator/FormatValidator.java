package backend.academy.analyzer.cli.validator;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class FormatValidator implements IParameterValidator {
    @Override
    public void validate(String name, String value) throws ParameterException {
        if (!value.equals("markdown") && !value.equals("adoc")) {
            throw new ParameterException("Parameter " + name + " must be either 'markdown' or 'adoc'");
        }
    }
}
