enum Status { PASSED, FAILED, SKIPPED }

public record TestResult(String name, Status status, long durationMs) {
    @Override
    public String toString() {
        return "%s — %s (%d ms)".formatted(name, status, durationMs);
    }
}
