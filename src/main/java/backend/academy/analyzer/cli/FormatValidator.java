package backend.academy.analyzer.cli;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

// Класс для валидации параметра format
public class FormatValidator implements IParameterValidator {
    @Override
    public void validate(String name, String value) throws ParameterException {
        if (!value.equals("markdown") && !value.equals("adoc")) {
            throw new ParameterException("Parameter " + name + " must be either 'markdown' or 'adoc'");
        }
    }
}
