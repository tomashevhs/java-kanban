package taskmanager;

import historymanager.HistoryManager;
import tasks.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    static String toString(Task task) {
        String str;
        if (task instanceof Subtask) {
            str = task.getId() + "," + task.getType() + "," + task.getTitle() +
                    "," + task.getStatus() + "," + task.getDescription() + "," + ((Subtask) task).getEpicId();
        } else {
            str = task.getId() + "," + task.getType() + "," + task.getTitle() + "," + task.getStatus() + "," +
                    task.getDescription() + ",";
        }
        return str;
    }

    static Task fromStringToTask(String value) {
        Task task = null;
        String[] split = value.split(",");
        for (int i = 0; i < split.length; i++) {
            switch (split[1]) {
                case "TASK":
                    task = new Task(TasksType.valueOf(split[1]), split[2], split[4], Status.valueOf(split[3]),
                            Integer.parseInt(split[0]));
                    break;
                case "EPIC":
                    task = new Epic(TasksType.valueOf(split[1]), split[2], split[4], Status.valueOf(split[3]),
                            Integer.parseInt(split[0]));
                    break;
                case "SUBTASK":
                    task = new Subtask(TasksType.valueOf(split[1]), split[2], split[4], Status.valueOf(split[3]),
                            Integer.parseInt(split[0]), Integer.parseInt(split[5]));
                    break;
            }
        }
        return task;
    }

    static String historyToString(HistoryManager manager) {
        StringBuilder builder = new StringBuilder();

        for (Task task : manager.getHistory()) {
            builder.append(task.getId());
        }
        return builder.toString();
    }


    static List<Integer> historyFromString(String value) {
        List<Integer> listOfId = new ArrayList<>();
        String[] split = value.split(",");
        for (String id : split) {
            int idStringToInteger = Integer.parseInt(id);
            listOfId.add(idStringToInteger);
        }
        return listOfId;
    }

    public void toRestoreHistory(List<Integer> list) {
        for (int i : list) {
            if (allTask.containsKey(i)) {
                inMemoryHistoryManager.add(allTask.get(i));
            } else if (allEpics.containsKey(i)) {
                inMemoryHistoryManager.add(allEpics.get(i));
            } else if (allSubTask.containsKey(i)) {
                inMemoryHistoryManager.add(allSubTask.get(i));
            }
        }
    }

    public void save() {
        try (Writer fileWriter = new FileWriter("sprint7.csv")) {

            fileWriter.write("id,type,name,status,description,epic\n");

            for (Task task : getListOfTasks()) {
                fileWriter.write(toString(task) + '\n');
            }

            for (Task epic : getListOfEpics()) {
                fileWriter.write(toString(epic) + '\n');
            }

            for (Task subTask : getListOfSubTasks()) {
                fileWriter.write(toString(subTask) + '\n');
            }

            fileWriter.write(" " + '\n');
            fileWriter.write(historyToString(inMemoryHistoryManager));

        } catch (IOException e) {
            throw new ManagerSaveException("Error");
        }
    }

    static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager();

        try (FileReader reader = new FileReader(file)) {
            BufferedReader br = new BufferedReader(reader);
            int maxId = 0;
            while (br.ready()) {

                String line = br.readLine();
                if (line.equals(" ")) {
                    line = br.readLine();
                    fileBackedTaskManager.toRestoreHistory(historyFromString(line));
                } else {
                    Task task = fromStringToTask(line);
                    if (task.getType().equals(TasksType.TASK)) {
                        fileBackedTaskManager.allTask.put(task.getId(), task);
                        if (task.getId() > maxId) {
                            maxId = task.getId();
                        }
                    } else if (task.getType().equals(TasksType.EPIC)) {
                        Epic epic = (Epic) task;
                        fileBackedTaskManager.allEpics.put(epic.getId(), epic);
                        if (epic.getId() > maxId) {
                            maxId = epic.getId();
                        }
                    } else if (task.getType().equals(TasksType.SUBTASK)) {
                        Subtask subTask = (Subtask) task;
                        fileBackedTaskManager.allSubTask.put(subTask.getId(), subTask);
                        for (int id : fileBackedTaskManager.allEpics.keySet()) {
                            if (subTask.getEpicId() == id) {
                                Epic epic = fileBackedTaskManager.allEpics.get(id);
                                epic.addSubTaskId(subTask.getId());
                            }
                            if (subTask.getId() > maxId) {
                                maxId = subTask.getId();
                            }
                        }
                    }
                }
                fileBackedTaskManager.id = maxId;
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Error");
        }
        return fileBackedTaskManager;
    }

    @Override
    public int createTask(Task task) {
        super.createTask(task);
        save();
        return task.getId();
    }

    @Override
    public int createEpic(Epic epic) {
        super.createTask(epic);
        save();
        return epic.getId();
    }

    @Override
    public int createSubTask(Subtask subtask) {
        super.createTask(subtask);
        save();
        return subtask.getId();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllSubTasks() {
        super.removeAllSubTasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public Task getTasksByIndex(int taskId) {
        Task task = super.getTasksByIndex(taskId);
        save();
        return task;
    }

    @Override
    public Subtask getSubTasksByIndex(int subTaskId) {
        Subtask subTask = super.getSubTasksByIndex(subTaskId);
        save();
        return subTask;
    }

    @Override
    public Epic getEpicsByIndex(int epicId) {
        Epic epic = super.getEpicsByIndex(epicId);
        save();
        return epic;
    }

    @Override
    public ArrayList<Subtask> getSubTasksIdByEpicId(int epicId) {
        ArrayList<Subtask> subTasks = super.getSubTasksIdByEpicId(epicId);
        save();
        return subTasks;
    }

    @Override
    public void removeSubTasksByIndex(Integer subTaskId) {
        super.removeSubTasksByIndex(subTaskId);
        save();
    }

    @Override
    public void removeEpicsByIndex(int epicId) {
        super.removeEpicsByIndex(epicId);
        save();
    }

    @Override
    public void updateTask(Task task, int taskId) {
        super.updateTask(task, taskId);
        save();
    }

    @Override
    public void updateSubTask(Subtask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateEpicStatus(Epic epic) {
        super.updateEpicStatus(epic);
        save();
    }

}