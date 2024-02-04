import java.util.ArrayList;
import java.util.HashMap;


public class TaskManager {

    static Integer id = 0;
    HashMap<Integer, Task> allTask = new HashMap<>();
    HashMap<Integer, Subtask> allSubTask = new HashMap<>();
    HashMap<Integer, Epic> allEpics = new HashMap<>();
    HashMap<Integer, ArrayList<Integer>> epicsIdWithSubTasksId = new HashMap<>();
    ArrayList<Integer> subTasksId = new ArrayList<>();


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

    public void createSubTask(Subtask subTask, int epicId) {
        id = getNextId();
        int subId = id;
        subTask.setId(subId);
        allSubTask.put(subId, subTask);
        subTasksId.add(id);
        epicsIdWithSubTasksId.put(epicId,subTasksId);
        updateEpicStatus(epicId);
    }

    public void getAllTasks() {
        System.out.println(allTask);
    }

    public void getAllSubTasks() {
        System.out.println(allSubTask);
    }

    public void getAllTEpics() {
        System.out.println(allEpics);
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

    public void getTasksByIndex(int taskId) {
        System.out.println(allTask.get(taskId));
    }

    public void getSubTasksByIndex(int subTaskId) {
        System.out.println(allSubTask.get(subTaskId));
    }

    public void getEpicsByIndex(int epicId) {
        System.out.println(allEpics.get(epicId));
    }

    public void getSubTasksIdByEpicId(int epicId) {
        if (epicsIdWithSubTasksId.containsKey(epicId)) {
            ArrayList<Integer> subsId = epicsIdWithSubTasksId.get(epicId);
            for (Integer id : subsId) {
                if (allSubTask.containsKey(id)) {
                    System.out.println(allSubTask.get(id));
                }
            }
        } else {
            System.out.println("Такого эпика не существует.");
        }
    }

    public void removeTasksByIndex(int taskId) {
        allTask.remove(taskId);
        System.out.println(allTask);
    }

    public void removeSubTasksByIndex(int subTaskId, int epicId) {
        if (allSubTask.containsKey(subTaskId)) {
            allSubTask.remove(subTaskId);
            System.out.println(allSubTask);
        }
        if (epicsIdWithSubTasksId.containsKey(epicId)) {
            ArrayList<Integer> subsId = epicsIdWithSubTasksId.get(epicId);
            if (subsId.contains(subTaskId)) {
                subsId.remove(subTaskId);
            }
        }
    }

    public void removeEpicsByIndex(int epicId) {
        allEpics.remove(epicId);
        System.out.println(allEpics);
        epicsIdWithSubTasksId.remove(epicId);
    }

    public void updateTask(Task task, int taskId) {
        task.setId(taskId);
        allTask.put(taskId, task);
    }

    public void updateSubTask(Subtask subTask, int subTaskId, int epicId) {
        subTask.setId(subTaskId);
        allSubTask.put(subTaskId, subTask);
        updateEpicStatus(epicId);

    }

    public void updateEpicStatus(int epicId) {
      ArrayList<Integer> subTaskId = epicsIdWithSubTasksId.get(epicId);
      for (int idSubTask : subTaskId) {
        Subtask subTask = allSubTask.get(idSubTask);
        if (subTask.getStatus() != Progress.NEW && subTask.getStatus() != Progress.IN_PROGRESS) {
            Epic epic = allEpics.get(epicId);
            epic.setStatus(Progress.DONE);
        } else if (subTask.getStatus() != Progress.NEW && subTask.getStatus() != Progress.DONE) {
            Epic epic = allEpics.get(epicId);
            epic.setStatus(Progress.IN_PROGRESS);
        } else {
            Epic epic = allEpics.get(epicId);
            epic.setStatus(Progress.NEW);
        }
      }
    }

    public Integer getNextId() {
        id++;
        return id;
    }
}



