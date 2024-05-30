package http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import taskmanager.TaskManager;
import tasks.Task;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;


public class TaskHttpHandler extends BaseHttpHandler implements HttpHandler {
    TaskManager taskManager;

    public TaskHttpHandler(TaskManager taskManager) {
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

        switch (method) {
            case "GET":
                handleGetTask(exchange);
                break;
            case "POST":
                handlePostTask(exchange);
                break;
            case "DELETE":
                handleDeleteTask(exchange);
                break;
            default:
                sendNotFound(exchange, "Такого эндпоинта не существует");
        }
    }

    private void handleGetTask(HttpExchange exchange) throws IOException {
        Optional<Integer> taskIdOpt = getTaskId(exchange);

        if (taskIdOpt.isEmpty()) {
            if (!taskManager.getListOfTasks().isEmpty()) {
                String jsonTask = gsonTask.toJson(taskManager.getListOfTasks());
                sendText(exchange, jsonTask);
            } else {
                sendNotFound(exchange, "На текущий момент задач нет.");
            }
        } else {
            if (taskManager.getTasksByIndex(taskIdOpt.get()) != null) {
                String jsonTask = gsonTask.toJson(taskManager.getTasksByIndex(taskIdOpt.get()));
                sendText(exchange, jsonTask);
            } else {
                sendNotFound(exchange, "Такого id не существует.");
            }
        }
    }

    private void handlePostTask(HttpExchange exchange) throws IOException {
        Optional<Integer> taskIdOpt = getTaskId(exchange);

        if (taskIdOpt.isPresent()) {
            if (taskManager.getTasksByIndex(taskIdOpt.get()) != null) {
                Task task = gsonTask.fromJson(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8), Task.class);
                if (!taskManager.taskComparisonByTimeDuringUpdate(task)) {
                    taskManager.updateTask(task, taskIdOpt.get());
                    String jsonTask = gsonTask.toJson(taskManager.getTasksByIndex(taskIdOpt.get()));
                    noSendText(exchange, "Задача успешно обновлена.");
                } else {
                    sendNotAcceptable(exchange, "Задачи пересекаются по времени.");
                }
            } else {
                sendNotFound(exchange, "Такого id не существует.");
            }
        } else {
            Task task = gsonTask.fromJson(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8), Task.class);
            if (!taskManager.taskComparisonByTimeDuringUpdate(task)) {
                taskManager.createTask(task);
                String jsonTask = gsonTask.toJson(taskManager.createTask(task));
                sendText(exchange, jsonTask);
            } else {
                sendNotAcceptable(exchange, "Задачи пересекаются по времени.");
            }
        }
    }

    private void handleDeleteTask(HttpExchange exchange) throws IOException {
        Optional<Integer> taskIdOpt = getTaskId(exchange);

        if (taskIdOpt.isPresent()) {
            if (taskManager.getTasksByIndex(taskIdOpt.get()) != null) {
                Task task = gsonTask.fromJson(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8), Task.class);
                taskManager.removeTasksByIndex(task.getId());
            } else {
                sendNotFound(exchange, "Такого id не существует.");
            }
        } else {
            sendNotFound(exchange, "Такого id не существует.");
        }
    }

    private Optional<Integer> getTaskId(HttpExchange exchange) {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        try {
            return Optional.of(Integer.parseInt(pathParts[2]));
        } catch (Exception exception) {
            return Optional.empty();
        }
    }
}
