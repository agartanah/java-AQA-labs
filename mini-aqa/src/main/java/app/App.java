package app;

public class App {
    public static String greet(String name) {
        if (name == null) {
            return "У тебя нет имени?";
        }

        if (name.isBlank()) {
            name = "НЕЗНАКОМЕЦ";
        }

        return "Привет, " + name + "!";
    }
}