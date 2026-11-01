package exceptions;

public class InvalidDurationFormatException extends RuntimeException {
    public InvalidDurationFormatException(String testLine) {
        super(testLine + " — значение длительности не является целым числом");
    }
}
