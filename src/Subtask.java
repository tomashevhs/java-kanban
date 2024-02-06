public class Subtask extends Task {
    public int getEpicId() {
        return epicId;
    }

    protected int epicId;
    public Subtask(String title, String description, Progress status, int epicId) {
        super(title, description, status);
        this.epicId = epicId;
    }
    @Override
    public String toString() {
        return "SubTask " + "{" + "Название:'" + title + '\'' +
                ", описание:'" + description + '\'' +
                ", id: '" + id + '\'' +
                ", статус: '" + status + "'" + "}";
    }
}
