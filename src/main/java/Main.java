import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            Path path = Path.of("run.log");

            if (!Files.exists(path)) {
                System.out.println("Файла с таким путём не существует!");
            }

            if (!Files.isReadable(path)) {
                System.out.println("Файл нельзя прочитать!");
            }

            var testParser = new TestLogParser(path);

            System.out.println("Результаты:");
            for (var test : testParser.getTestResults()) {
                System.out.println("\t" + test);
            }

            System.out.println("Битые строки:");
            for (var error : testParser.getErrors()) {
                System.out.printf("\t" + error.getMessage() + "\n");
            }

            Optional<TestResult> firstFail = testParser.firstFailed();

            if (firstFail.isEmpty()) {
                System.out.println("Нет упавших тестов");
            } else {
                System.out.printf("Первый упавший тест: " + firstFail.get());
            }
        } catch (InvalidPathException e) {
            System.out.println("Неправильный путь!");
        } catch (IOException e) {
            System.out.println("Во время чтения файла произошла ошибка!" + "\n" + e.getMessage());
        }
    }
}
