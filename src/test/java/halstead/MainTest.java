package halstead;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MainTest {
    @Test
    void testMainMethodExists() {
        assertDoesNotThrow(() -> {
            Method mainMethod = Main.class.getMethod("main", String[].class);
            assertNotNull(mainMethod);
        });
    }

    @Test
    void testMainMethodRunsWithoutExceptions() {
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }
}


