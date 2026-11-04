import java.util.List;

record RunResult(List<TestResult> results, long actualTimeMs) {}
