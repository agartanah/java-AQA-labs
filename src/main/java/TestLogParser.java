import exceptions.InvalidDurationFormatException;
import exceptions.InvalidNumberFields;
import exceptions.InvalidStatus;
import exceptions.NegativeDurationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class TestLogParser {
    private static final String SEPARATOR = "\\|";

    private List<TestResult> testResults = new ArrayList<>();
    private final List<Exception> errors = new ArrayList<>();

    public TestLogParser(Path path) throws IOException {
        testResults = parse(Files.readAllLines(path));
    }

    public List<TestResult> parse(List<String> testLines) {
        errors.clear();
        testResults.clear();

        Map<String, TestResult> testResultMap = new LinkedHashMap<>();

        for (String testLine : testLines) {
            String[] fields = testLine.split(SEPARATOR);

            if (fields.length != 3) {
                errors.add(new InvalidNumberFields(testLine));
                continue;
            }

            if (fields[0].isBlank() || fields[1].isBlank() || fields[2].isBlank()) {
                errors.add(new InvalidNumberFields(testLine));
                continue;
            }

            Status status;

            try {
                status = Status.valueOf(fields[1]);
            } catch (IllegalArgumentException e) {
                errors.add(new InvalidStatus(testLine));
                continue;
            }

            long duration;

            try {
                duration = Long.parseLong(fields[2]);
            } catch (NumberFormatException e) {
                errors.add(new InvalidDurationFormatException(testLine));
                continue;
            }

            if (duration < 0) {
                errors.add(new NegativeDurationException(testLine));
                continue;
            }

            TestResult testResult = new TestResult(
                    fields[0],
                    status,
                    duration
            );

            testResultMap.remove(testResult.name());
            testResultMap.put(testResult.name(), testResult);
        }

        testResults.addAll(testResultMap.values());

        return new ArrayList<>(testResults);
    }

    public Optional<TestResult> firstFailed(List<TestResult> results) {
        return results.stream()
                .filter(testResult -> testResult.status() == Status.FAILED)
                .findFirst();
    }

    public Optional<TestResult> firstFailed() {
        if (testResults.isEmpty()) {
            return Optional.empty();
        }

        return testResults.stream()
                .filter(testResult -> testResult.status() == Status.FAILED)
                .findFirst();
    }

    public List<TestResult> getTestResults() {
        return new ArrayList<>(testResults);
    }

    public List<Exception> getErrors() {
        return new ArrayList<>(errors);
    }
}
