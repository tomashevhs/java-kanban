package managers;

import historymanager.InMemoryHistoryManager;
import taskmanager.InMemoryTaskManager;
import historymanager.HistoryManager;
import taskmanager.TaskManager;

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
