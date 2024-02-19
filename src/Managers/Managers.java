package Managers;

import HistoryManager.InMemoryHistoryManager;
import TaskManager.InMemoryTaskManager;
import HistoryManager.HistoryManager;
import TaskManager.TaskManager;

public class Managers {

    private Managers() {
    }

    public static TaskManager getDefault() {
        return getInMemoryTaskManager();
    }

    public static TaskManager getInMemoryTaskManager() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
