package Managers;

import HistoryManager.InMemoryHistoryManager;
import TaskManager.InMemoryTaskManager;
import HistoryManager.HistoryManager;
import TaskManager.TaskManager;

public class Managers {

    private Managers() {
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
