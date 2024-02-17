

public class Managers {

    private Managers() {
    }

    public static TaskManager getDefault() {
        return getInMemoryTaskManager();
    }

    public static InMemoryTaskManager getInMemoryTaskManager() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        return inMemoryTaskManager;
    }

    public static HistoryManager getDefaultHistory() {
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        return inMemoryHistoryManager;
    }
}
