package app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AppTest {
    @Test
    public void defualtName() {
        assertEquals(App.greet("Гена"), "Привет, Гена!");
    }

    @Test
    public void emptyName() {
        assertEquals(App.greet(""), "Привет, НЕЗНАКОМЕЦ!");
    }

    @Test
    public void nullName() {
        assertEquals(App.greet(null), "У тебя нет имени?");
    }
}
