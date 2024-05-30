package http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import taskmanager.TaskManager;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;

public class HistoryHttpHandler extends BaseHttpHandler implements HttpHandler {
    TaskManager taskManager;

    public HistoryHttpHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    TimeAdapter timeAdapter = new TimeAdapter();
    DurationAdapter durationAdapter = new DurationAdapter();

    Gson gsonTask = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, timeAdapter)
            .registerTypeAdapter(Duration.class, durationAdapter)
            .create();

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
