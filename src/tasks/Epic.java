package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subTasksId;
    private LocalDateTime endTime;

    public Epic(TasksType type, String title, String description, Status status, int id,
                Duration duration, LocalDateTime startTime, LocalDateTime endTime) {
        super(type, title, description, status, id, duration, startTime);
        subTasksId = new ArrayList<>();
        this.endTime = endTime;
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
