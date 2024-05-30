import taskmanager.InMemoryTaskManager;
import tasks.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    InMemoryTaskManager taskManager;

    @BeforeEach
    void beforeEach() {
        taskManager = new InMemoryTaskManager();
    }

    @Test //Проверка задач с заданным id и задач с сгенерированным id
    void checkTaskWithGeneratedIdAndTaskWithGivenId(){
        Task task1 = new Task("1", "1", Status.NEW, 0, Duration.ofMinutes(10),
                LocalDateTime.of(2023, 3, 17, 17,15));
        taskManager.createTask(task1);

        Task task2 = new Task("1","2", Status.IN_PROGRESS, 1, Duration.ofMinutes(10),
                LocalDateTime.of(2023, 3, 17, 17,15));
        taskManager.createTask(task2);
        assertNotEquals(task1.getId(), task2.getId(), "Неверно задается id.");
    }

}