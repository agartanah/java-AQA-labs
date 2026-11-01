package exceptions;

public class InvalidStatus extends RuntimeException {
    public InvalidStatus(String testLine) {
        super(testLine + " — некорректный статус");
    }
}
