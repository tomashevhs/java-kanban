import java.util.Objects;

public class Task {
    private String title;
    private String description;
    private Integer id;
    private Progress status;


    public Task(String title, String description, Progress status) {
        this.title = title;
        this.description = description;
        this.status = status;
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
    public boolean equals(Object o) { // метод equals переопределён
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override // не забываем об аннотации
    public int hashCode() {
        int hash = 17; // объявляем и инициализируем переменную hash
        if (id != null) { // проверяем значение первого поля
            hash = hash + id.hashCode();
        }
        hash = hash * 31;

        return hash; // возвращаем хеш
    }



    @Override
    public String toString() {
        return "Название:'" + title + '\'' +
                ", описание:'" + description + '\'' +
                ", id: '" + id + '\'' +
                ", статус: '" + status + "'";
    }
}
