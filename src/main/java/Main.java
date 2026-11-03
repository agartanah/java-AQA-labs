import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void printSummary(List<TestResult> run) {
        Map<Status, List<String>> grouped = run.stream()
                .collect(Collectors.groupingBy(
                        TestResult::status,
                        Collectors.mapping(TestResult::name, Collectors.toList())
                ));

        List<String> topThree = run.stream()
                .sorted(Comparator.comparingLong(TestResult::durationMs).reversed())
                .limit(3)
                .map((TestResult::name))
                .toList();

        double avgDuration = run.stream().filter(testResult -> testResult.status() == Status.PASSED).mapToLong(TestResult::durationMs).average().orElse(0);

        System.out.println("=== Сводка прогона ===");

        Arrays.stream(Status.values())
                .forEach(status -> {
                    List<String> names = grouped.getOrDefault(status, List.of());
                    System.out.printf("%s (%d): %s%n",
                            status, names.size(), String.join(", ", names));
                });

        System.out.println("Топ-3 долгих: " + String.join(", ", topThree));
        System.out.printf("Среднее время прошедших: %.1f ms%n", avgDuration);
    }

    public static void main(String[] args) {
        List<TestResult> run = List.of(
                new TestResult("LoginTest",        Status.PASSED, 120),
                new TestResult("CreateTaskTest",   Status.PASSED, 940),
                new TestResult("UpdateStatusTest", Status.FAILED, 2100),
                new TestResult("CommentTest",      Status.PASSED, 380),
                new TestResult("DeleteTaskTest",   Status.FAILED, 760),
                new TestResult("StatsTest",        Status.SKIPPED, 0),
                new TestResult("LogoutTest",       Status.PASSED, 90)
        );

        printSummary(run);
    }
}
