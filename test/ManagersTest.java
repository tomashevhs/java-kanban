import managers.Managers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ManagersTest {

    @Test
    void checkUtilityClass() {

        assertNotNull(Managers.getDefaultHistory(), "Объект не возвращается.");
    }
}