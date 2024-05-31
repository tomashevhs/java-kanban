package http;

import com.sun.net.httpserver.HttpExchange;
import taskmanager.TaskManager;

import java.io.IOException;

public class PrioritizedHttpHandler extends BaseHttpHandler {
    public PrioritizedHttpHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if (method.equals("GET")) {
            if (!taskManager.getPrioritizedTasks().isEmpty()) {
                String json = gsonTask.toJson(taskManager.getPrioritizedTasks());
                sendText(exchange, json);
            } else {
                sendNotFound(exchange, "Истории просмотров нет.");
            }
        } else {
            sendNotFound(exchange, "Такого эндпоинта не существует");
        }
    }
}
