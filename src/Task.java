import java.util.Objects;

public class Task {
    private String title;
    private String description;
    private Integer id;
    private Progress status;


    public Task(String title, String description, Progress status, int id) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Progress getStatus() {
        return status;
    }

    public void setStatus(Progress status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(title, task.title) && Objects.equals(description, task.description)
                && Objects.equals(id, task.id) && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        if (title != null) {
            hash = hash + title.hashCode();
        }
        hash = hash * 31;
        if (description != null) {
            hash = hash + description.hashCode();
        }
        if (id != null) {
            hash = hash + id.hashCode();
        }
        if (status != null) {
            hash = hash + status.hashCode();
        }
        return hash;
    }

    @Override
    public String toString() {
        return "Task: " + "{" + "Название:'" + title + '\'' +
                ", описание:'" + description + '\'' +
                ", id: '" + id + '\'' +
                ", статус: '" + status + "'" + "}";
    }
}
