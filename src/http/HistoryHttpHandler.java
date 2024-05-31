package http;

import com.sun.net.httpserver.HttpExchange;
import taskmanager.TaskManager;

import java.io.IOException;

public class HistoryHttpHandler extends BaseHttpHandler {

    public HistoryHttpHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if (method.equals("GET")) {
            if (!taskManager.getHistory().isEmpty()) {
                String json = gsonTask.toJson(taskManager.getHistory());
                sendText(exchange, json);
            } else {
                sendNotFound(exchange, "Истории просмотров нет.");
            }

        } else {
            sendNotFound(exchange, "Такого эндпоинта не существует");
        }
    }
}
