import HistoryManager.InMemoryHistoryManager;
import TaskManagers.InMemoryTaskManager;
import HistoryManager.HistoryManager;
import TaskManagers.TaskManager;

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
