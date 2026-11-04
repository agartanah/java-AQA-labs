import java.util.List;
import java.util.concurrent.*;

public class ParallelRunner {
    static Callable<TestResult> fakeTest(String name, long durationMs, Status status) {
        return () -> {
            Thread.sleep(durationMs);
            return new TestResult(name, status, durationMs);
        };
    }

    public static RunResult runParallelTests(List<TestResult> tests)
            throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        try {
            List<Callable<TestResult>> tasks = tests.stream()
                    .map(
                            test -> fakeTest(
                                test.name(),
                                test.durationMs(),
                                test.status()
                            )
                    )
                    .toList();

            long startTime = System.nanoTime();

            var futuresTest = executorService.invokeAll(tasks);

            long endTime = System.nanoTime();

            long resultTimeMs = (endTime - startTime) / 1_000_000;

            List<TestResult> results = futuresTest.stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toList();

            return new RunResult(results, resultTimeMs);
        } finally {
            executorService.shutdown();
        }
    }

    public static void printSummary(RunResult runResult) {
        List<TestResult> results = runResult.results();

        long passed = results.stream()
                .filter(test -> test.status() == Status.PASSED)
                .count();

        long failed = results.stream()
                .filter(test -> test.status() == Status.FAILED)
                .count();

        long totalTime = results.stream()
                .mapToLong(TestResult::durationMs)
                .sum();

        long actualTime = runResult.actualTimeMs();

        System.out.println("Прошло: " + passed + ", упало: " + failed);
        System.out.println("Суммарное время тестов: " + totalTime + " ms");
        System.out.println("Реальное время прогона: " + actualTime + " ms");
        System.out.println("Параллельность сэкономила: " + (totalTime - actualTime) + " ms");
    }
}
