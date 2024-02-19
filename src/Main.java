import Managers.Managers;
import TaskManager.TaskManager;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        Task task1 = new Task("1", "1", Status.NEW, 0);
        manager.createTask(task1);

        Task task2 = new Task("2", "2", Status.IN_PROGRESS, 0);
        manager.createTask(task2);

        Epic task3 = new Epic("epic3", "2", Status.NEW, 0);
        manager.createEpic(task3);

        Subtask task4 = new Subtask("sub4", "sub4", Status.NEW, 3, 0);
        manager.createSubTask(task4);

        Subtask task5 = new Subtask("sub5", "sub5", Status.IN_PROGRESS, 3, 0);
        manager.createSubTask(task5);

        manager.getTasksByIndex(1);

        task1 = new Task("1", "1", Status.IN_PROGRESS, 0);
        manager.updateTask(task1, 1);

        manager.getTasksByIndex(1);

        manager.getTasksByIndex(2);
        manager.getEpicsByIndex(3);
        manager.getSubTasksByIndex(4);
        manager.getSubTasksByIndex(5);





        printAllTasks(manager);
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
