import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ManagersTest {

    @Test
    void checkUtilityClass() {
        assertNotNull(managers.getInMemoryTaskManager(), "Объект не возвращается.");
        assertNotNull(managers.getDefaultHistory(), "Объект не возвращается.");
    }
}