package backend.academy.analyzer.cli.converter;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateConverter implements IStringConverter<LocalDate> {
    @Override
    public LocalDate convert(String date) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new ParameterException(
                "Date format for " + date + " is invalid, it must be \"yyyy-MM-dd\" for ISO-8601"
            );
        }
    }
}
