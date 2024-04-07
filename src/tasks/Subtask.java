package tasks;

public class Subtask extends Task {

    private final int epicId;

    public Subtask(TasksType type, String title, String description, Status status, int epicId, int id) {
        super(type, title, description, status, id);
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
                ", статус: '" + getStatus() + "'" + "}";
    }
}
