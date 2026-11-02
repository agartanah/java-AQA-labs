package exceptions;

public class InvalidNumberFields extends RuntimeException {
    public InvalidNumberFields(String testRun) {
        super(testRun + " — количество полей должно быть равно двум");
    }
}
