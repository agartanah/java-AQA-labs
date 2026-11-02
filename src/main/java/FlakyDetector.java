import exceptions.InvalidNumberFields;
import exceptions.InvalidStatus;

import java.util.*;

enum Status { PASSED, FAILED }

public class FlakyDetector {
    private static final String SEPARATOR = "\\|";

    private String[] validateTestRun(String testRun) throws InvalidNumberFields, InvalidStatus {
        String[] testRunLineFields = testRun.split(SEPARATOR);

        if (testRunLineFields.length != 2) {
            throw new InvalidNumberFields(testRun);
        }

        try {
            Status.valueOf(testRunLineFields[1]);
        } catch (IllegalArgumentException e) {
            throw new InvalidStatus(testRun);
        }

        return testRunLineFields;
    }

    public Set<String> failed(List<String> run) throws InvalidNumberFields, InvalidStatus {
        Set<String> failedRuns = new HashSet<>();

        for (String testRunLine : run) {
            String[] testRunLineFields = validateTestRun(testRunLine);

            if (Status.valueOf(testRunLineFields[1]) != Status.FAILED) {
                continue;
            }

            failedRuns.add(testRunLineFields[0]);
        }

        return failedRuns;
    }

    public Set<String> passed(List<String> run) throws InvalidNumberFields, InvalidStatus {
        Set<String> passedRuns = new HashSet<>();

        for (String testRunLine : run) {
            String[] testRunLineFields = validateTestRun(testRunLine);

            if (Status.valueOf(testRunLineFields[1]) != Status.PASSED) {
                continue;
            }

            passedRuns.add(testRunLineFields[0]);
        }

        return passedRuns;
    }

    // в условии сказано, что результат этого метода — это тесты, которые упали
    // в первом прогоне и прошли во втором. Но я решил сразу учитывать и обратную ситуацию,
    // потому что, как я понял, если тест сначала прошёл, а потом упал — он тоже будет flaky.
    // Поэтому мне показалось удобнее сделать проверку сразу в обе стороны.
    public List<String> flaky(List<String> run1, List<String> run2) {
        Set<String> run1Failed = failed(run1);
        Set<String> run2Failed = failed(run2);

        Set<String> run1Passed = passed(run1);
        Set<String> run2Passed = passed(run2);

        run1Failed.retainAll(run2Passed);
        run1Passed.retainAll(run2Failed);
        run1Failed.addAll(run1Passed);

        return new ArrayList<>(run1Failed);
    }

    public Map<String, Long> statusSummary(List<String> run) {
        Map<String, Long> summary = new HashMap<>();

        for (String testRunLine : run) {
            String[] testRunFields = validateTestRun(testRunLine);

            summary.merge(
                    testRunFields[1],
                    1L,
                    Long::sum
            );
        }

        return summary;
    }
}
