package backend.academy.analyzer.cli;

import com.beust.jcommander.ParameterException;

public class DateValidator {
    public void validate(CliParams params) throws ParameterException {
        if (params.fromDate() != null
            && params.toDate() != null
            && params.fromDate().isAfter(params.toDate())) {
            throw new ParameterException(
                "FromDate " + params.fromDate() + " must be before or equal ToDate " + params.toDate()
            );
        }
    }
}
