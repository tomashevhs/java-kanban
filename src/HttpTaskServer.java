import com.sun.net.httpserver.HttpServer;
import http.*;
import managers.Managers;
import taskmanager.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8080;
    TaskManager taskManager;

    public HttpTaskServer(TaskManager manager) {
        this.taskManager = manager;
    }

    public static void main(String[] args) throws IOException {

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 10);


        httpServer.createContext("/task", new TaskHttpHandler(Managers.getDefault()));
        httpServer.createContext("/subtask", new SubtaskHttpHandler(Managers.getDefault()));
        httpServer.createContext("/epic", new EpicHttpHandler(Managers.getDefault()));
        httpServer.createContext("/history", new HistoryHttpHandler(Managers.getDefault()));
        httpServer.createContext("/prioritized", new PrioritizedHttpHandler(Managers.getDefault()));
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");

    }

}
