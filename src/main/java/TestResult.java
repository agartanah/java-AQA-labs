enum Status { PASSED, FAILED, SKIPPED }

record TestResult(String name, Status status, long durationMs) { }
