

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task = new Task("Написать пост", "Описать новые кроссовки", Progress.NEW);
        taskManager.createTask(task);

        Task task2 = new Task("Почистить куртку", "Отнести куртку в химчистку", Progress.NEW);
        taskManager.createTask(task2);

        Epic epic = new Epic("Пстроить дом", "Построить дом из бруса", Progress.NEW);
        taskManager.createEpic(epic);

        Subtask subTask = new Subtask("Купить брус", "Найти самую маленькую цену и купить", Progress.NEW, 3);
        taskManager.createSubTask(subTask);

        Subtask subTask2 = new Subtask("Купить шпатлевку", "Найти самую маленькую цену и купить", Progress.NEW, 3);
        taskManager.createSubTask(subTask2);

        Epic epic2 = new Epic("Купить машину", "Купить БМВ", Progress.NEW);
        taskManager.createEpic(epic2);

        Subtask subTask3 = new Subtask("Приехать в автосалон", "Выбрать машину", Progress.NEW, 6);
        taskManager.createSubTask(subTask3);
        System.out.println(taskManager.getSubTasksIdByEpicId(3));
        System.out.println(taskManager.getEpicsByIndex(3));
        System.out.println();

        Subtask subtask4 = new Subtask("Купить плитку", "найти подешевле", Progress.NEW, 3);
        taskManager.updateSubTask(subtask4, 5);


        System.out.println(taskManager.getSubTasksIdByEpicId(3));
        System.out.println(taskManager.getEpicsByIndex(3));
    }
}
