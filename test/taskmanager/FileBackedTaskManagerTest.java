package taskmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {

    FileBackedTaskManager fileBackedTaskManager;

    @BeforeEach
    void beforeEach() {
        fileBackedTaskManager = new FileBackedTaskManager();
        try {
            File tempFile = File.createTempFile("sprint7.csv", null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}