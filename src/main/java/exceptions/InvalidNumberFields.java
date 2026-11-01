package exceptions;

public class InvalidNumberFields extends RuntimeException {
    public InvalidNumberFields(String testLine) {
        super(testLine + " — число полей должно быть равно трём");
    }
}
