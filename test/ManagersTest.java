import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ManagersTest {

    @Test
    void checkUtilityClass() {
        assertNotNull(Managers.getInMemoryTaskManager(), "Объект не возвращается.");
        assertNotNull(Managers.getDefaultHistory(), "Объект не возвращается.");
    }
}