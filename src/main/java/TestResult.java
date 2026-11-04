enum Status { PASSED, FAILED }

record TestResult(String name, Status status, long durationMs) { }

