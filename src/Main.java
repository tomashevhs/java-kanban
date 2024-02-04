

public class Main {

    public static void main(String[] args) {
        Epic epic = new Epic("Переезда", "Переезд на другую квартиру", Progress.NEW);
        TaskManager taskManager = new TaskManager();
        taskManager.createEpic(epic);

        Subtask subTask = new Subtask("Упаковать мебель", "Взять пленку и обернуть мебель", Progress.DONE);
        taskManager.createSubTask(subTask, 1);
        Subtask subTask2 = new Subtask("Упаковать вещи", "разложить все по сумкам и чемоданам", Progress.DONE);
        taskManager.createSubTask(subTask2, 1);


        taskManager.getEpicsByIndex(1);
    }
}
