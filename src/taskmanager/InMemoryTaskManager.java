package taskmanager;

import historymanager.InMemoryHistoryManager;
import managers.Managers;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static tasks.Status.DONE;

public class InMemoryTaskManager implements TaskManager {

    protected int id = 0;
    protected final HashMap<Integer, Task> allTask = new HashMap<>();
    protected final HashMap<Integer, Subtask> allSubTask = new HashMap<>();
    protected final HashMap<Integer, Epic> allEpics = new HashMap<>();
    protected Set<Task> prioritizedTasksByTime = new TreeSet<>(Comparator.comparing(Task::getStartTime));
    InMemoryHistoryManager inMemoryHistoryManager = (InMemoryHistoryManager) Managers.getDefaultHistory();

    public ArrayList<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasksByTime);
    }

    private boolean intersectionSearch(Task task1, Task task2) {
        if (task1.getEndTime().isAfter(task2.getStartTime())) {
            return true;
        } else if (task1.getStartTime().isBefore(task2.getEndTime())) {
            return true;
        } else if (task1.getStartTime().isAfter(task2.getStartTime()) && task1.getEndTime().isBefore(task2.getEndTime())) {
            return true;
        } else if (task2.getStartTime().isAfter(task1.getStartTime()) && task2.getEndTime().isBefore(task1.getEndTime())) {
            return true;
        } else {
            return false;
        }
    }


    private void startTimeOfEpic(int epicId) {
        allEpics.get(epicId).setStartTime(getSubTasksIdByEpicId(epicId).stream()
                .map(Task::getStartTime)
                .min(LocalDateTime::compareTo)
                .orElse(LocalDateTime.MAX)
        );
    }

    private void endTimeOfEpic(int epicId) {
        allEpics.get(epicId).setEndTime(getSubTasksIdByEpicId(epicId).stream()
                .map(Task::getEndTime)
                .max(LocalDateTime::compareTo)
                .orElse(LocalDateTime.MAX)
        );
    }

    private void durationOfEpic(int epicId) {
        allEpics.get(epicId).setDuration(Duration.ofMinutes(getSubTasksIdByEpicId(epicId).stream()
                .mapToLong(subTask -> subTask.getDuration().toMinutes())
                .sum()));
    }

    @Override
    public int createTask(Task task) {
        id = getNextId();
        int taskId = id;
        task.setId(taskId);

        if (task.getStartTime() != null) {
            List<Task> list = getPrioritizedTasks().stream()
                    .filter(task1 -> intersectionSearch(task, task1))
                    .collect(Collectors.toList());
            if (list.isEmpty()) {
                allTask.put(taskId, task);
                prioritizedTasksByTime.add(task);
            }
        } else {
            task.setStartTime(LocalDateTime.MAX);
            allTask.put(taskId, task);
        }

        return taskId;
    }

    @Override
    public int createEpic(Epic epic) {
        id = getNextId();
        int epicId = id;
        epic.setId(epicId);
        allEpics.put(epicId, epic);
        startTimeOfEpic(epicId);
        durationOfEpic(epicId);
        endTimeOfEpic(epicId);
        return epicId;

    }

    @Override
    public int createSubTask(Subtask subTask) {
        if (allEpics.containsKey(subTask.getEpicId())) {
            int id = getNextId();
            subTask.setId(id);
            updateEpicStatus(allEpics.get(subTask.getEpicId()));
            if (subTask.getStartTime() != null) {
                List<Task> list = getPrioritizedTasks().stream()
                        .filter(task1 -> intersectionSearch(subTask, task1))
                        .collect(Collectors.toList());
                if (list.isEmpty()) {
                    allTask.put(id, subTask);
                    allEpics.get(subTask.getEpicId()).addSubTaskId(subTask.getId());
                    prioritizedTasksByTime.add(subTask);
                }
            } else {
                subTask.setStartTime(LocalDateTime.MAX);
            }
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
        allTask.keySet().forEach(id -> {
            if (allTask.get(id).getStartTime() != null) {
                inMemoryHistoryManager.remove(id);
                prioritizedTasksByTime.remove(allTask.get(id));
            }
        });
        allTask.clear();

    }

    @Override
    public void removeAllSubTasks() {
        allSubTask.keySet().forEach(id -> {
            if (allSubTask.get(id).getStartTime() != null) {
                inMemoryHistoryManager.remove(id);
                prioritizedTasksByTime.remove(allSubTask.get(id));
            }

        });
        allSubTask.values().forEach(task -> {
            Epic epic = allEpics.get(task.getEpicId());
            epic.removeAllSubTaskId();
            epic.setStatus(Status.NEW);
        });

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
        prioritizedTasksByTime.remove(allTask.get(taskId));
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
        prioritizedTasksByTime.remove(allSubTask.get(subTaskId));
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

        if (task.getStartTime() != null) {
            List<Task> list = getPrioritizedTasks().stream()
                    .filter(task1 -> intersectionSearch(task, task1))
                    .collect(Collectors.toList());
            if (list.isEmpty()) {
                allTask.put(taskId, task);
                prioritizedTasksByTime.add(task);
            }
        } else {
            task.setStartTime(LocalDateTime.MAX);
            allTask.put(taskId, task);
        }
    }

    @Override
    public void updateSubTask(Subtask subTask) {
        int subId = subTask.getId();
        subTask.setId(subId);
        int epicId = subTask.getEpicId();
        updateEpicStatus(allEpics.get(epicId));

        if (subTask.getStartTime() != null) {
            List<Task> list = getPrioritizedTasks().stream()
                    .filter(task1 -> intersectionSearch(subTask, task1))
                    .collect(Collectors.toList());
            if (list.isEmpty()) {
                allTask.put(id, subTask);
                allEpics.get(subTask.getEpicId()).addSubTaskId(subTask.getId());
                prioritizedTasksByTime.add(subTask);
                updateEpicStatus(allEpics.get(epicId));
                startTimeOfEpic(epicId);
                durationOfEpic(epicId);
                endTimeOfEpic(epicId);
            }
        } else {
            subTask.setStartTime(LocalDateTime.MAX);
            allTask.put(id, subTask);
        }
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
        startTimeOfEpic(epicId);
        durationOfEpic(epicId);
        endTimeOfEpic(epicId);
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


