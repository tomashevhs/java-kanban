package taskmanager;

import historymanager.InMemoryHistoryManager;
import managers.Managers;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static tasks.Status.DONE;

public class InMemoryTaskManager implements TaskManager {

    protected int id = 0;
    protected final HashMap<Integer, Task> allTask = new HashMap<>();
    protected final HashMap<Integer, Subtask> allSubTask = new HashMap<>();
    protected final HashMap<Integer, Epic> allEpics = new HashMap<>();
    InMemoryHistoryManager inMemoryHistoryManager = (InMemoryHistoryManager) Managers.getDefaultHistory();


    @Override
    public int createTask(Task task) {
        id = getNextId();
        int taskId = id;
        task.setId(taskId);
        allTask.put(taskId, task);
        return taskId;
    }

    @Override
    public int createEpic(Epic epic) {
        id = getNextId();
        int epicId = id;
        epic.setId(epicId);
        allEpics.put(epicId, epic);
        return epicId;
    }

    @Override
    public int createSubTask(Subtask subTask) {
        if (allEpics.containsKey(subTask.getEpicId())) {
            int id = getNextId();
            subTask.setId(id);
            allSubTask.put(id, subTask);
            allEpics.get(subTask.getEpicId()).addSubTaskId(subTask.getId());
            updateEpicStatus(allEpics.get(subTask.getEpicId()));
            return id;
        }
        return -1;
    }


    @Override
    public ArrayList<Task> getListOfTasks() {
        return new ArrayList<>(allTask.values());
    }

    @Override
    public ArrayList<Task> getListOfEpics() {
        return new ArrayList<>(allEpics.values());
    }

    @Override
    public ArrayList<Task> getListOfSubTasks() {
        return new ArrayList<>(allSubTask.values());
    }

    @Override
    public void removeAllTasks() {
        allTask.clear();
    }

    @Override
    public void removeAllSubTasks() {
        for (Subtask subTask : allSubTask.values()) {
            Epic epic = allEpics.get(subTask.getEpicId());
            epic.removeAllSubTaskId();
            epic.setStatus(Status.NEW);
        }
        allSubTask.clear();

    }

    @Override
    public void removeAllEpics() {
        ArrayList<Integer> epicIdList = new ArrayList<>(allEpics.keySet());
        for (int epicId : epicIdList) {
            Epic epic = allEpics.get(epicId);
            if (epic.getSubTasksId().isEmpty()) {
                allEpics.remove(epicId);
            } else {
                for (int id : epic.getSubTasksId()) {
                    allSubTask.remove(id);
                }
                allEpics.remove(epicId);
            }
        }
        epicIdList.clear();
    }

    @Override
    public Task getTasksByIndex(int taskId) {
        Task task = allTask.get(taskId);
        inMemoryHistoryManager.add(task);
        return task;
    }

    @Override
    public Epic getEpicsByIndex(int epicId) {
        Epic epic = allEpics.get(epicId);
        inMemoryHistoryManager.add(epic);
        return epic;
    }

    @Override
    public Subtask getSubTasksByIndex(int subTaskId) {
        Subtask subTask = allSubTask.get(subTaskId);
        inMemoryHistoryManager.add(subTask);
        return subTask;
    }



    @Override
    public ArrayList<Subtask> getSubTasksIdByEpicId(int epicId) {
        Epic epic = allEpics.get(epicId);
        ArrayList<Subtask> subTasks = new ArrayList<>();
        for (int id : epic.getSubTasksId()) {
            subTasks.add(allSubTask.get(id));
        }
        return subTasks;
    }

    @Override
    public void removeTasksByIndex(int taskId) {
        allTask.remove(taskId);
        inMemoryHistoryManager.remove(taskId);
    }

    @Override
    public void removeSubTasksByIndex(Integer subTaskId) {
        Subtask subtask = allSubTask.get(subTaskId);
        int epicId = subtask.getEpicId();
        Epic epic = allEpics.get(epicId);
        ArrayList<Integer> subIds = epic.getSubTasksId();
        subIds.remove(subTaskId);
        epic.setSubTasksId(subIds);
        allSubTask.remove(subTaskId);
        updateEpicStatus(epic);
        inMemoryHistoryManager.remove(subTaskId);
    }

    @Override
    public void removeEpicsByIndex(int epicId) {
        Epic epic = allEpics.get(epicId);
        if (epic.getSubTasksId().isEmpty()) {
            allEpics.remove(epicId);
        } else {
            for (int id : epic.getSubTasksId()) {
                allSubTask.remove(id);
            }
            allEpics.remove(epicId);
        }
        inMemoryHistoryManager.remove(epicId);
    }

    @Override
    public void updateTask(Task task, int taskId) {
        task.setId(taskId);
        allTask.put(taskId, task);
    }

    @Override
    public void updateSubTask(Subtask subTask) {
        int subId = subTask.getId();
        subTask.setId(subId);
        allSubTask.put(subId, subTask);
        int epicId = subTask.getEpicId();
        updateEpicStatus(allEpics.get(epicId));
    }

    @Override
    public void updateEpic(Epic epic) {
        Epic epic2 = allEpics.get(epic.getId());
        ArrayList<Integer> subIds = epic2.getSubTasksId();
        int epicId = epic.getId();
        epic.setId(epicId);
        allEpics.put(epicId, epic);
        epic.setSubTasksId(subIds);
        updateEpicStatus(epic);
    }

    @Override
    public void updateEpicStatus(Epic epic) {
        boolean isAllNew = true;
        boolean isAllDone = true;

        for (int idSubTask : epic.getSubTasksId()) {
            Subtask subTask = allSubTask.get(idSubTask);
            if (subTask.getStatus() != Status.NEW) {
                isAllNew = false;
            }
            if (subTask.getStatus() != DONE) {
                isAllDone = false;
            }
        }

        if (isAllNew) {
            epic.setStatus(Status.NEW);
        } else if (!isAllDone && !isAllNew) {
            epic.setStatus(Status.IN_PROGRESS);
        } else {
            epic.setStatus(DONE);
        }
    }

    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    @Override
    public Integer getNextId() {
        id++;
        return id;
    }
}


