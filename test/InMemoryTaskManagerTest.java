import taskmanager.InMemoryTaskManager;
import tasks.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    InMemoryTaskManager taskManager;

    @BeforeEach
    void beforeEach() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
        //Тест проверки создания задач и поиска их по id
    void addAllTasks() {

        Task task1 = new Task(TasksType.TASK,"1", "1", Status.NEW, 0);
        taskManager.createTask(task1);

        Task task2 = new Task(TasksType.TASK, "2", "2", Status.IN_PROGRESS, 0);
        taskManager.createTask(task2);

        Epic task3 = new Epic(TasksType.EPIC,"epic3", "2", Status.NEW, 0);
        taskManager.createEpic(task3);

        Subtask task4 = new Subtask(TasksType.SUBTASK, "sub4", "sub4", Status.NEW, 3, 0);
        taskManager.createSubTask(task4);

        Subtask task5 = new Subtask(TasksType.SUBTASK, "sub5", "sub5", Status.IN_PROGRESS, 3, 0);
        taskManager.createSubTask(task5);

        assertNotNull(taskManager.getTasksByIndex(task1.getId()), "Задача не возвращается.");
        assertNotNull(taskManager.getTasksByIndex(task2.getId()), "Задача не возвращается.");
        assertNotNull(taskManager.getEpicsByIndex(task3.getId()), "Задача не возвращается.");
        assertNotNull(taskManager.getSubTasksByIndex(task4.getId()), "Задача не возвращается.");
        assertNotNull(taskManager.getSubTasksByIndex(task5.getId()), "Задача не возвращается.");
    }

    @Test
        //Тест проверки равенства экземпляров Tasks.Task с одинаковым Id
    void addNewTask() {
        Task task = new Task(TasksType.TASK, "Test addNewTask", "Test addNewTask description", Status.NEW, 0);
        final int taskId = taskManager.createTask(task);

        final Task savedTask = taskManager.getTasksByIndex(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getListOfTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
        //Тест проверки равенства экземпляров Tasks.Epic с одинаковым Id
    void addNewEpic() {
        Epic task2 = new Epic(TasksType.EPIC,"Test addNewEpic", "Test addNewEpic description", Status.NEW, 0);
        final int taskId = taskManager.createEpic(task2);

        final Epic savedTask = taskManager.getEpicsByIndex(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task2, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getListOfEpics();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task2, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void addEpicInEpic() { //Тест проверки возможности положить эпик в эпик
        Epic task3 = new Epic(TasksType.EPIC, "epic3", "2", Status.NEW, 0);
        taskManager.createEpic(task3);

        Subtask task4 = new Subtask(TasksType.SUBTASK, "sub4", "sub4", Status.NEW, 1, 0);
        taskManager.createSubTask(task4);
        task3.addSubTaskId(1);

        System.out.println(task3.getSubTasksId());
        assertEquals(1, task3.getSubTasksId().size(), "Неверное количество задач.");
    }


    @Test //Проверка задач с заданным id и задач с сгенерированным id
    void checkTaskWithGeneratedIdAndTaskWithGivenId(){
        Task task1 = new Task(TasksType.TASK, "1", "1", Status.NEW, 0);
        taskManager.createTask(task1);

        Task task2 = new Task(TasksType.TASK,"2", "2", Status.IN_PROGRESS, 1);
        taskManager.createTask(task2);
        assertNotEquals(task1.getId(), task2.getId(), "Неверно задается id.");
    }

    @Test //Проверка сохранения истории просмотренных задач
    void checkHistory() {
        Task task1 = new Task(TasksType.TASK,"1", "1", Status.NEW, 0);
        taskManager.createTask(task1);
        taskManager.getTasksByIndex(1);

        task1 = new Task(TasksType.TASK,"12", "12", Status.IN_PROGRESS, 0);
        taskManager.updateTask(task1, 1);

        taskManager.getTasksByIndex(1);
        Main.printAllTasks(taskManager);

        assertEquals(2, taskManager.getHistory().size(), "История отображается не верно.");
    }
}