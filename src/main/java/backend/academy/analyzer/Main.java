package backend.academy.analyzer;

import com.beust.jcommander.JCommander;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {
    public static void main(String[] args) {
        CliParams params = new CliParams();
        JCommander.newBuilder()
            .addObject(params)
            .build()
            .parse(args);
        LogReader logReader = new LogReader(params.path());
        log.info(logReader.readFile());
    }
}
