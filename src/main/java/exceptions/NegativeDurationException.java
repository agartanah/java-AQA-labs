package exceptions;

public class NegativeDurationException extends RuntimeException {
    public NegativeDurationException(String testLine) {
        super(testLine + " — значение длительности не должно быть отрицательным");
    }
}
