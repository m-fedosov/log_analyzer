package backend.academy.analyzer.cli.validator;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PathValidator implements IParameterValidator {
    @Override
    public void validate(String name, String value) throws ParameterException {
        boolean isValidPath = false;
        boolean isValidURL = false;
        if (Files.exists(Path.of(value))) {
            isValidPath = true;
        }

        try {
            new URI(value).toURL();
            isValidURL = true;
        } catch (URISyntaxException | MalformedURLException | IllegalArgumentException _) {
        }

        if (!isValidPath && !isValidURL) {
            throw new ParameterException("Parameter " + name + " must be a valid URL or a valid path to file");
        }
    }
}
