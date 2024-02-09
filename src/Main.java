

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task = new Task("Написать пост", "Описать новые кроссовки", Status.NEW, 0);
        taskManager.createTask(task);

        Task task2 = new Task("Почистить куртку", "Отнести куртку в химчистку", Status.NEW, 0);
        taskManager.createTask(task2);

        Epic epic = new Epic("Построить дом", "Построить дом из бруса", Status.NEW, 0);
        taskManager.createEpic(epic);

        Subtask subTask = new Subtask("Купить брус", "Найти самую маленькую цену и купить", Status.NEW, 3, 0);
        taskManager.createSubTask(subTask);

        Subtask subTask2 = new Subtask("Купить шпатлевку", "Найти самую маленькую цену и купить", Status.NEW, 3, 0);
        taskManager.createSubTask(subTask2);

        System.out.println(taskManager.getListOfEpics() + "\n");
        System.out.println(taskManager.getListOfSubTasks() + "\n");

        taskManager.removeSubTasksByIndex(4);

        System.out.println(taskManager.getListOfEpics() + "\n");
        System.out.println(taskManager.getListOfSubTasks() + "\n");


    }
}
