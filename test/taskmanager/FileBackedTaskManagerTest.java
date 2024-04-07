package taskmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.*;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {

    FileBackedTaskManager fileBackedTaskManager;

    @BeforeEach
    void beforeEach() {
        fileBackedTaskManager = new FileBackedTaskManager();
        try {
            File tempFile = File.createTempFile("sprint7.csv", null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void save() {
        FileBackedTaskManager fileBackedTaskManager =  new FileBackedTaskManager();
        Task task1 = new Task(TasksType.TASK,"1", "1", Status.NEW, 0);
        fileBackedTaskManager.createTask(task1);

        Task task2 = new Task(TasksType.TASK, "2", "2", Status.IN_PROGRESS, 0);
        fileBackedTaskManager.createTask(task2);

        Epic task3 = new Epic(TasksType.EPIC,"epic3", "2", Status.NEW, 0);
        fileBackedTaskManager.createEpic(task3);

        Subtask task4 = new Subtask(TasksType.SUBTASK, "sub4", "sub4", Status.NEW, 3, 0);
        fileBackedTaskManager.createSubTask(task4);

        Subtask task5 = new Subtask(TasksType.SUBTASK, "sub5", "sub5", Status.IN_PROGRESS, 3, 0);
        fileBackedTaskManager.createSubTask(task5);

        fileBackedTaskManager.getTasksByIndex(1);

        task1 = new Task(TasksType.TASK, "1", "1", Status.IN_PROGRESS, 0);

        fileBackedTaskManager.updateTask(task1, 1);

        fileBackedTaskManager.getTasksByIndex(1);
    }
}