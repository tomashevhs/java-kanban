package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private TasksType type;
    private ArrayList<Integer> subTasksId;
    private LocalDateTime endTime;

    public Epic(String title, String description, Status status, int id,
                Duration duration, LocalDateTime startTime, LocalDateTime endTime) {
        super(title, description, status, id, duration, startTime);
        subTasksId = new ArrayList<>();
        this.endTime = endTime;
        this.type = TasksType.EPIC;
    }

    public void addSubTaskId(int subTaskId) {
        int epicId = getId();
        if (subTaskId != epicId) {
            subTasksId.add(subTaskId);
        }
    }

    public void removeAllSubTaskId() {
        subTasksId.clear();
    }

    public ArrayList<Integer> getSubTasksId() {
        return subTasksId;
    }

    public void setSubTasksId(ArrayList<Integer> subTasksId) {
        this.subTasksId = subTasksId;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    @Override
    public void setType(TasksType type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return "Tasks.Epic: " + "{" + "Название:'" + getTitle() + '\'' +
                ", описание:'" + getDescription() + '\'' +
                ", id: '" + getId() + '\'' +
                ", статус: '" + getStatus() + '\'' +
                ", время начала: " + getStartTime() + '\'' +
                ", продолжительность: " + getDuration() + '\'' +
                ", время окончания: " + getEndTime().format(DATE_TIME_FORMATTER) + "'" + "}";
    }
}
