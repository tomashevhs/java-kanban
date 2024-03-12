package historymanager;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    ArrayList<Task> viewedTasks = new ArrayList<>();
    static final int VIEWEDTASKS_CAPACITY = 10;

    @Override
    public void add(Task task) {
        if (viewedTasks.size() == VIEWEDTASKS_CAPACITY) {
            viewedTasks.remove(0);
        }
        viewedTasks.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return viewedTasks;
    }
}
