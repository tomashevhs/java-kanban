package managers;

import historyManager.InMemoryHistoryManager;
import taskManager.InMemoryTaskManager;
import historyManager.HistoryManager;
import taskManager.TaskManager;

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
