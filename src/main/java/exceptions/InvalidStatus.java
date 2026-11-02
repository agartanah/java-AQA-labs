package exceptions;

public class InvalidStatus extends RuntimeException {
    public InvalidStatus(String testRun) {
        super(testRun + " — такого статуса не существует");
    }
}
