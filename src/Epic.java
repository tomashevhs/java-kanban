import java.util.ArrayList;

public class Epic extends Task {
    protected ArrayList<Integer> subTasksId = new ArrayList<>();
    public void addSubTaskId (int sunTaskOd) {
        subTasksId.add(sunTaskOd);
    }

    public void removeAllSubTaskId () {
        subTasksId.clear();
    }
    public Epic(String title, String description, Progress status) {
        super(title, description, status);
    }
    @Override
    public String toString() {
        return "Epic " + "{" + "Название:'" + title + '\'' +
                ", описание:'" + description + '\'' +
                ", id: '" + id + '\'' +
                ", статус: '" + status + "'" + "}";
    }
}
