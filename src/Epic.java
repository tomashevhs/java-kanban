import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subTasksId;


    public Epic(String title, String description, Progress status, int id) {
        super(title, description, status, id);
        subTasksId = new ArrayList<>();
    }

public void addSubTaskId (int sunTaskOd) {
    subTasksId.add(sunTaskOd);
}

public void removeAllSubTaskId () {
    subTasksId.clear();
}

    public ArrayList<Integer> getSubTasksId() {
        return subTasksId;
    }

    public void setSubTasksId(ArrayList<Integer> subTasksId) {
        this.subTasksId = subTasksId;
    }
    @Override
    public String toString() {
        return "Epic: " + "{" + "Название:'" + getTitle() + '\'' +
                ", описание:'" + getDescription() + '\'' +
                ", id: '" + getId() + '\'' +
                ", статус: '" + getStatus() + "'" + "}";
    }
}
