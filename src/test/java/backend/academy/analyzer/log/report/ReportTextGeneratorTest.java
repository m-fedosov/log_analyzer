package backend.academy.analyzer.log.report;

import backend.academy.analyzer.CommandLineParser;
import backend.academy.analyzer.ReportGenerator;
import backend.academy.analyzer.cli.CliParams;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Log4j2
class ReportTextGeneratorTest {

    @Test
    void generateMarkdown() {
        String report = """
            #### Общая информация

            | Метрика | Значение |
            |:----:|:----:|
            | Файл(-ы) | 2024backend_logs_10_lines.txt |
            | Начальная дата | 2024-11-06 |
            | Конечная дата | 2024-11-06 |
            | Количество запросов | 10 |
            | Средний размер ответа | 1,166 |
            | 95p размера ответа | 2,487.05 |

            #### Запрашиваемые ресурсы

            | Ресурс | Количество |
            |:----:|:----:|
            | /protocol/uniform-intranet_Distributed.htm | 1 |
            | /dedicated-open%20architecture-complexity-Digitized.js | 1 |
            | /migration%20definition.css | 1 |
            | /Business-focused-didactic/conglomeration-fresh-thinking.svg | 1 |
            | /collaboration%20alliance-flexibility_motivating/capability.gif | 1 |
            | /standardization.htm | 1 |
            | /Multi-tiered%20Front-line.js | 1 |
            | /leverage/multimedia-non-volatile_actuating/executive.css | 1 |
            | /24%20hour/static.php | 1 |
            | /moderator/non-volatile/array/4th%20generation.svg | 1 |

            #### Коды ответа

            | Код | Имя | Количество |
            |:----:|:----:|:----:|
            | 200 | OK | 6 |
            | 404 | Not Found | 2 |
            | 400 | Bad Request | 1 |
            | 500 | Internal Server Error | 1 |

            #### HTTP methods

            | Метод | Количество |
            |:----:|:----:|
            | GET | 7 |
            | DELETE | 2 |
            | HEAD | 1 |

            #### User Agents

            | httpUserAgent | Количество |
            |:----:|:----:|
            | Mozilla | 9 |
            | Opera | 1 |

            """;
        String[] args = {"-p", "logs/2024backend_logs_10_lines.txt", "-f", "markdown"};
        CommandLineParser commandLineParser = new CommandLineParser(args);
        CliParams params = commandLineParser.parse();
        ReportGenerator reportGenerator = new ReportGenerator(params);
        assertEquals(report, reportGenerator.generateReport());
    }

    @Test
    void generateAsciiDoc() {
        String report = """
            == Общая информация

            |===
            | Метрика | Значение
            | Файл(-ы) | 2024backend_logs_10_lines.txt
            | Начальная дата | 2024-11-06
            | Конечная дата | 2024-11-06
            | Количество запросов | 10
            | Средний размер ответа | 1,166
            | 95p размера ответа | 2,487.05
            |===

            == Запрашиваемые ресурсы

            |===
            | Ресурс | Количество
            | /protocol/uniform-intranet_Distributed.htm | 1
            | /dedicated-open%20architecture-complexity-Digitized.js | 1
            | /migration%20definition.css | 1
            | /Business-focused-didactic/conglomeration-fresh-thinking.svg | 1
            | /collaboration%20alliance-flexibility_motivating/capability.gif | 1
            | /standardization.htm | 1
            | /Multi-tiered%20Front-line.js | 1
            | /leverage/multimedia-non-volatile_actuating/executive.css | 1
            | /24%20hour/static.php | 1
            | /moderator/non-volatile/array/4th%20generation.svg | 1
            |===

            == Коды ответа

            |===
            | Код | Имя | Количество
            | 200 | OK | 6
            | 404 | Not Found | 2
            | 400 | Bad Request | 1
            | 500 | Internal Server Error | 1
            |===

            == HTTP methods

            |===
            | Метод | Количество
            | GET | 7
            | DELETE | 2
            | HEAD | 1
            |===

            == User Agents

            |===
            | httpUserAgent | Количество
            | Mozilla | 9
            | Opera | 1
            |===

            """;
        String[] args = {"-p", "logs/2024backend_logs_10_lines.txt", "-f", "adoc"};
        CommandLineParser commandLineParser = new CommandLineParser(args);
        CliParams params = commandLineParser.parse();
        ReportGenerator reportGenerator = new ReportGenerator(params);
        assertEquals(report, reportGenerator.generateReport());
    }
}
