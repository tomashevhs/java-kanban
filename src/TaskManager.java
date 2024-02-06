import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    Integer id = 0;
    HashMap<Integer, Task> allTask = new HashMap<>();
    HashMap<Integer, Subtask> allSubTask = new HashMap<>();
    HashMap<Integer, Epic> allEpics = new HashMap<>();


    public void createTask(Task task) {
        id = getNextId();
        int taskId = id;
        task.setId(taskId);
        allTask.put(taskId, task);
    }

    public void createEpic(Epic epic) {
        id = getNextId();
        int epicId = id;
        epic.setId(epicId);
        allEpics.put(epicId, epic);

    }

    public void createSubTask(Subtask subTask) {
        if (allEpics.containsKey(subTask.epicId)) {
            id = getNextId();
            int subId = id;
            subTask.setId(subId);
            allSubTask.put(subId, subTask);
        }
        Epic epic = allEpics.get(subTask.epicId);
        epic.addSubTaskId(id);
        updateEpicStatus(subTask.epicId);
    }

    public ArrayList<Task> getListOfTasks() {
        return new ArrayList<>(allTask.values());
    }

    public ArrayList<Task> getListOfEpics() {
        return new ArrayList<>(allEpics.values());
    }

    public ArrayList<Task> getListOfSubTasks() {
        return new ArrayList<>(allSubTask.values());
    }

    public void removeAllTasks() {
        allTask.clear();
        System.out.println("Все задачи удалены.");
    }

    public void removeAllSubTasks() {
        allSubTask.clear();
        System.out.println("Все задачи удалены.");
    }

    public void removeAllEpics() {
        allEpics.clear();
        System.out.println("Все задачи удалены.");
    }

    public Task getTasksByIndex(int taskId) {
       return allTask.get(taskId);
    }

    public Subtask getSubTasksByIndex(int subTaskId) {
        return allSubTask.get(subTaskId);
    }

    public Epic getEpicsByIndex(int epicId) {
      return allEpics.get(epicId);
    }

    public ArrayList<Subtask> getSubTasksIdByEpicId(int epicId) {
        Epic epic = allEpics.get(epicId);
        ArrayList<Subtask> subTasks = new ArrayList<>();
        for (int id : epic.subTasksId) {
            subTasks.add(allSubTask.get(id));
        }
        return subTasks;
    }

    public void removeTasksByIndex(int taskId) {
        allTask.remove(taskId);
    }

    public void removeSubTasksByIndex(int subTaskId, int epicId) {
        allSubTask.remove(subTaskId);
        Epic epic = allEpics.get(epicId);
        epic.subTasksId.remove(subTaskId);
    }

    public void removeEpicsByIndex(int epicId) {
        allEpics.remove(epicId);
    }

    public void updateTask(Task task, int taskId) {
        task.setId(taskId);
        allTask.put(taskId, task);
    }

    public void updateSubTask(Subtask subTask, int subTaskId) {
        subTask.setId(subTaskId);
        allSubTask.put(subTaskId, subTask);
        int epicId = subTask.getEpicId();
        updateEpicStatus(epicId);
    }

    public void updateEpicStatus(int epicId) {
        Epic epic = allEpics.get(epicId);
        for (int idSubTask : epic.subTasksId) {
            Subtask subTask = allSubTask.get(idSubTask);
            if (subTask.getStatus() == Progress.NEW) {
                epic.setStatus(Progress.NEW);
            } else if (subTask.getStatus() != Progress.NEW || subTask.getStatus() == Progress.DONE) {
                epic.setStatus(Progress.IN_PROGRESS);
            } else if (subTask.getStatus() != Progress.NEW && subTask.getStatus() != Progress.IN_PROGRESS) {
                epic.setStatus(Progress.DONE);
            }
        }
    }

    public Integer getNextId() {
        id++;
        return id;
    }
}



