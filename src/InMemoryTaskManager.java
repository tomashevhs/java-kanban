import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    private int id = 0;
    HashMap<Integer, Task> allTask = new HashMap<>();
    HashMap<Integer, Subtask> allSubTask = new HashMap<>();
    HashMap<Integer, Epic> allEpics = new HashMap<>();


    @Override
    public void createTask(Task task) {
        id = getNextId();
        int taskId = id;
        task.setId(taskId);
        allTask.put(taskId, task);

    }

    @Override
    public void createEpic(Epic epic) {
        id = getNextId();
        int epicId = id;
        epic.setId(epicId);
        allEpics.put(epicId, epic);
    }

    @Override
    public void createSubTask(Subtask subTask) {
        int epicId = subTask.getEpicId();
        if (allEpics.containsKey(epicId)) {
            id = getNextId();
            int subId = id;
            subTask.setId(subId);
            allSubTask.put(subId, subTask);
            Epic epic = allEpics.get(epicId);
            epic.addSubTaskId(subId);
            updateEpicStatus(epic);
        }
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
        return allTask.get(taskId);
    }

    @Override
    public Subtask getSubTasksByIndex(int subTaskId) {
        return allSubTask.get(subTaskId);
    }

    @Override
    public Epic getEpicsByIndex(int epicId) {
        return allEpics.get(epicId);
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
            if (subTask.getStatus() != Status.DONE) {
                isAllDone = false;
            }
        }

        if (isAllNew) {
            epic.setStatus(Status.NEW);
        } else if (!isAllDone && !isAllNew) {
            epic.setStatus(Status.IN_PROGRESS);
        } else {
            epic.setStatus(Status.DONE);
        }
    }

    @Override
    public Integer getNextId() {
        id++;
        return id;
    }
}




