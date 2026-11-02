import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Path pathRun1 = Path.of("run1.log");
            Path pathRun2 = Path.of("run2.log");

            if (!Files.exists(pathRun1) || !Files.exists(pathRun2)) {
                System.out.println("Путь(-и) файла(-ов) не существует(-ют)!");
            }

            if (!Files.isReadable(pathRun1) || !Files.isReadable(pathRun2)) {
                System.out.println("Файл(-ы) нельзя прочитать!");
            }

            List<String> testRun1 = Files.readAllLines(pathRun1);
            List<String> testRun2 = Files.readAllLines(pathRun2);

            var flakyTests = FlakyDetector.flaky(testRun1, testRun2);
            var summaryRun1 = FlakyDetector.statusSummary(testRun1);
            var summaryRun2 = FlakyDetector.statusSummary(testRun2);

            System.out.println("Flaky-кандидаты: \n\t" + flakyTests);
            System.out.println("Прогон 1, сводка: \n\t" + summaryRun1);
            System.out.println("Прогон 2, сводка: \n\t" + summaryRun2);
        } catch (InvalidPathException e) {
            System.out.println("Неправильный путь!");
        } catch (IOException e) {
            System.out.println("Во время чтения файла произошла ошибка!" + "\n" + e.getMessage());
        }
    }
}
