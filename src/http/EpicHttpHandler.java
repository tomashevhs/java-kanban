package http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import taskmanager.TaskManager;
import tasks.Epic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;

public class EpicHttpHandler extends BaseHttpHandler implements HttpHandler {
    TaskManager taskManager;

    public EpicHttpHandler(TaskManager taskManager) {
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
                Optional<Integer> taskIdOpt = getTaskId(exchange);

                if (taskIdOpt.isEmpty()) {
                    handleGetListOfTask(exchange);
                } else {
                    if (checkResponse(exchange)) {
                        handleGetSubTaskByEpicId(exchange, taskIdOpt.get());
                    } else {
                        handleGetTaskById(exchange, taskIdOpt.get());
                    }
                }
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

    private void handleGetListOfTask(HttpExchange exchange) throws IOException {
        if (!taskManager.getListOfEpics().isEmpty()) {
            String jsonTask = gsonTask.toJson(taskManager.getListOfEpics());
            sendText(exchange, jsonTask);
        } else {
            sendNotFound(exchange, "На текущий момент задач нет.");
        }
    }

    private void handleGetTaskById(HttpExchange exchange, Integer taskIdInt) throws IOException {
        if (taskManager.getEpicsByIndex(taskIdInt) != null) {
            String jsonTask = gsonTask.toJson(taskManager.getEpicsByIndex(taskIdInt));
            sendText(exchange, jsonTask);
        } else {
            sendNotFound(exchange, "Такой задачи не существует.");
        }
    }

    private void handleGetSubTaskByEpicId(HttpExchange exchange, Integer taskIdInt) throws IOException {
        if (!taskManager.getListOfEpics().isEmpty()) {
            String jsonTask = gsonTask.toJson(taskManager.getSubTasksIdByEpicId(taskIdInt));
            sendText(exchange, jsonTask);
        } else {
            sendNotFound(exchange, "На текущий момент задач нет.");
        }
    }

    private void handlePostTask(HttpExchange exchange) throws IOException {

        if (getTaskId(exchange).isPresent()) {
            if (taskManager.getEpicsByIndex(getTaskId(exchange).get()) != null) {
                Epic task = gsonTask.fromJson(new String(exchange.getRequestBody().readAllBytes(),
                        StandardCharsets.UTF_8), Epic.class);
                taskManager.updateEpic(task);
                sendText(exchange, "Задача успешно обновлена.");
                noSendText(exchange, "Задача успешно обновлена.");
            } else {
                sendNotFound(exchange, "Задача не найдена");
            }
        } else {
            Epic task = gsonTask.fromJson(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8),
                    Epic.class);
            String jsonTask = gsonTask.toJson(taskManager.createEpic(task));
            sendText(exchange, jsonTask);
        }


    }

    private void handleDeleteTask(HttpExchange exchange) throws IOException {
        Optional<Integer> taskIdOpt = getTaskId(exchange);

        if (taskIdOpt.isPresent()) {
            if (taskManager.getEpicsByIndex(taskIdOpt.get()) != null) {
                Epic task = gsonTask.fromJson(new String(exchange.getRequestBody().readAllBytes(),
                        StandardCharsets.UTF_8), Epic.class);
                taskManager.removeEpicsByIndex(task.getId());
            } else {
                sendNotFound(exchange, "Такой задачи не существует.");
            }
        } else {
            sendNotFound(exchange, "Такой задачи не существует.");
        }
    }

    private Optional<Integer> getTaskId(HttpExchange exchange) {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        try {
            return Optional.of(Integer.parseInt(pathParts[3]));
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    private boolean checkResponse(HttpExchange httpExchange) {
        String[] pathParts = httpExchange.getRequestURI().getPath().split("/");
        return pathParts[3].equals("subtasks");
    }
}

