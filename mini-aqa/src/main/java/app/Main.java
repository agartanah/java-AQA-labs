package app;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties properties = new Properties();

        try (InputStream input = App.class.getClassLoader()
                .getResourceAsStream("app.properties")) {

            if (input == null) {
                throw new IllegalStateException("Файл app.properties не найден");
            }

            properties.load(new InputStreamReader(input, StandardCharsets.UTF_8));

            String name = properties.getProperty("student.name");

            System.out.println(App.greet(name));

        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения app.properties", e);
        }
    }
}
