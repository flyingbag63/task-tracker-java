public class TaskDTO {
    private Integer id;
    private String name;
    private String status;

    public TaskDTO() {

    }

    public TaskDTO(Integer id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Id: " + this.id +  ", Name: " + this.name + ", Status " + this.status;
    }
}
