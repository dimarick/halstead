package halstead;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.out;

    private static final String TEST_CONFIG_FILENAME = "test-config.properties";

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void testMainMethodExists() {
        assertDoesNotThrow(() -> {
            Method mainMethod = Main.class.getMethod("main", String[].class);
            assertNotNull(mainMethod);
        });
    }

    @Test
    void testMainMethodRunsWithoutExceptions() {
        assertDoesNotThrow(() -> Main.main(new String[]{}),
            "Программа не должна падать, если аргументы не переданы."
        );
    }

    // Тест проверяет, что программа успешно запускается и отрабатывает без ошибок, когда ей передан валидный файл
    @Test
    void testMainMethodRunsWithoutExceptions_And_PrintExpectedOutput() {
        URL resource = Main.class.getClassLoader().getResource(TEST_CONFIG_FILENAME);
        if (resource == null) {
            throw new IllegalStateException("Не удалось найти тестовый файл конфигурации: " + TEST_CONFIG_FILENAME);
        }
        String configPath = new File(resource.getFile()).getAbsolutePath();

        assertDoesNotThrow(() -> Main.main(new String[]{configPath}));

        // Проверяем, что в поток ошибок ничего не попало
        assertTrue(errContent.toString().isEmpty());

        // Проверяем, что в стандартный вывод попал ожидаемый заголовок
        assertTrue(outContent.toString().contains("Демонстрация расчетов по метрикам Холстеда"));
    }

    // Тест что программа выводит ошибку пользователя - не переданы аргументы
    @Test
    void main_shouldPrintUsageError_whenNoArgumentsProvided() {
        Main.main(new String[]{});

        assertTrue(outContent.toString().contains("Ошибка: Укажите путь к файлу конфигурации"));
    }
}
