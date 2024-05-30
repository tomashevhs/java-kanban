package tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    private TasksType type;
    private final int epicId;

    public Subtask(String title, String description, Status status, int epicId, int id,
                   Duration duration, LocalDateTime startTime) {

        super(title, description, status, id, duration, startTime);
        this.type = TasksType.SUBTASK;
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "SubTask: " + "{" + "Название:'" + getTitle() + '\'' +
                ", описание:'" + getDescription() + '\'' +
                ", id: '" + getId() + '\'' +
                ", статус: '" + getStatus() +
                ", время начала: " + getStartTime() + '\'' +
                ", продолжительность: " + getDuration() + '\'' +
                ", время окончания: " + getEndTime().format(DATE_TIME_FORMATTER) + "'" + "}";
    }

    @Override
    public void setType(TasksType type) {
        this.type = type;
    }
}
