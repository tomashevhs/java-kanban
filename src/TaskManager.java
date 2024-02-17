import java.util.ArrayList;

public interface TaskManager {
    void createTask(Task task);

    void createEpic(Epic epic);

    void createSubTask(Subtask subTask);

    ArrayList<Task> getListOfTasks();

    ArrayList<Task> getListOfEpics();

    ArrayList<Task> getListOfSubTasks();

    void removeAllTasks();

    void removeAllSubTasks();

    void removeAllEpics();

    Task getTasksByIndex(int taskId);

    Subtask getSubTasksByIndex(int subTaskId);

    Epic getEpicsByIndex(int epicId);

    ArrayList<Subtask> getSubTasksIdByEpicId(int epicId);

    void removeTasksByIndex(int taskId);

    void removeSubTasksByIndex(Integer subTaskId);

    void removeEpicsByIndex(int epicId);

    void updateTask(Task task, int taskId);

    void updateSubTask(Subtask subTask);

    void updateEpic(Epic epic);

    void updateEpicStatus(Epic epic);

    Integer getNextId();
}
