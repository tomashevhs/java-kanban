import taskmanager.FileBackedTaskManager;
import taskmanager.TaskManager;
import tasks.*;

import java.time.Duration;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        FileBackedTaskManager fileBackedTaskManager =  new FileBackedTaskManager();
        Task task1 = new Task(TasksType.TASK,"1", "1", Status.NEW, 0, Duration.ofMinutes(10),
                LocalDateTime.of(2023, 3, 17, 17,15));
        fileBackedTaskManager.createTask(task1);

        Task task2 = new Task(TasksType.TASK, "2", "2", Status.IN_PROGRESS, 0, Duration.ofMinutes(10),
                LocalDateTime.of(2023, 3, 17, 17,15));
        fileBackedTaskManager.createTask(task2);

        Epic task3 = new Epic(TasksType.EPIC,"epic3", "2", Status.NEW, 0, Duration.ofMinutes(10),
                LocalDateTime.of(2023, 3, 17, 17,15),
                LocalDateTime.of(2023, 3, 17, 17,15));
        fileBackedTaskManager.createEpic(task3);

        Subtask task4 = new Subtask(TasksType.SUBTASK, "sub4", "sub4", Status.NEW, 3, 0, Duration.ofMinutes(10),
                LocalDateTime.of(2023, 3, 17, 17,15));
        fileBackedTaskManager.createSubTask(task4);

        Subtask task5 = new Subtask(TasksType.SUBTASK, "sub5", "sub5", Status.IN_PROGRESS, 3, 0, Duration.ofMinutes(10),
                LocalDateTime.of(2023, 3, 17, 17,15));
        fileBackedTaskManager.createSubTask(task5);

        fileBackedTaskManager.getTasksByIndex(1);

        task1 = new Task(TasksType.TASK, "1", "1", Status.IN_PROGRESS, 0, Duration.ofMinutes(10),
                LocalDateTime.of(2023, 3, 17, 17,15));

        fileBackedTaskManager.updateTask(task1, 1);

        fileBackedTaskManager.getTasksByIndex(1);

        fileBackedTaskManager.getTasksByIndex(2);
        fileBackedTaskManager.getEpicsByIndex(3);
        fileBackedTaskManager.getSubTasksByIndex(4);
        fileBackedTaskManager.getSubTasksByIndex(5);






    }

    public static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getListOfTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.getListOfEpics()) {
            System.out.println(epic);

            for (Task task : manager.getSubTasksIdByEpicId(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getListOfSubTasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}
