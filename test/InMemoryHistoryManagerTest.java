import historymanager.InMemoryHistoryManager;
import tasks.Status;
import tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.TasksType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    InMemoryHistoryManager historyManager;


    @Test
    void add() {
        Task task = new Task(TasksType.TASK, "Test addNewTask", "Test addNewTask description",
                Status.NEW, 0, Duration.ofMinutes(10),
                LocalDateTime.of(2023, 3, 17, 17, 15));
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");

    }


}