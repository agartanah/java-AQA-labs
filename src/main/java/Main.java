import java.util.List;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) {
        List<TestResult> tests = List.of(
                new TestResult("LoginTest",        Status.PASSED, 320),
                new TestResult("RegisterTest",     Status.PASSED, 450),
                new TestResult("CreateTaskTest",   Status.FAILED, 380),
                new TestResult("UpdateTaskTest",   Status.FAILED, 410),
                new TestResult("DeleteTaskTest",   Status.PASSED, 300),
                new TestResult("CommentTest",      Status.PASSED, 490),
                new TestResult("SearchTest",       Status.FAILED, 360),
                new TestResult("LogoutTest",       Status.PASSED, 430)
        );

        try {
            RunResult result = ParallelRunner.runParallelTests(tests);
            ParallelRunner.printSummary(result);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Выполнение было прервано");
        } catch (ExecutionException e) {
            System.out.println("Ошибка при выполнении теста: " + e.getCause());
        }
    }
}
