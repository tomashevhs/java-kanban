package taskManager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    int createTask(Task task);

    int createEpic(Epic epic);

    int createSubTask(Subtask subTask);

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

    List<Task> getHistory();
}
