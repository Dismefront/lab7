package storage;

import java.io.Serializable;
import java.time.LocalDate;

public class Worker implements Collectionable, Serializable {



    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long salary; //Значение поля должно быть больше 0
    private LocalDate startDate; //Поле не может быть null
    private Position position; //Поле может быть null
    private Status status; //Поле может быть null
    private Person person; //Поле не может быть null

    public Worker(String name, Coordinates coordinates,
                  Long salary, LocalDate startDate, Position position, Status status, Person person) {
        this.name = name;
        this.coordinates = coordinates;
        this.salary = salary;
        this.startDate = startDate;
        this.position = position;
        this.status = status;
        this.person = person;

        this.creationDate = LocalDate.now();
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        Worker w = (Worker) obj;
        return this.id == w.getId();
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getSalary() {
        return this.salary;
    }

    @Override
    public String getName() {
        if (this.name == null)
            return "";
        return this.name;
    }

    @Override
    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    @Override
    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    @Override
    public LocalDate getStartDate() {
        return this.startDate;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @Override
    public Person getPerson() {
        return this.person;
    }

    @Override
    public int compareTo(Collectionable worker) {
        return Long.compare(this.salary, worker.getSalary());
    }

    public boolean isBlank(String name) {
        return name == null || name.isEmpty();
    }

    @Override
    public boolean isValid() {
        return this.coordinates != null &&
                !isBlank(this.name) && this.coordinates.isValid() &&
                this.salary > 0 && this.id > 0 &&
                this.startDate != null && this.creationDate != null &&
                this.person != null;
    }

    @Override
    public String toString() {
        String pos = this.position == null ? "null" : this.position.name();
        String st = this.status == null ? "null" : this.status.name();
        return "(" + this.id + ", name:" + this.getName() +
                ", coords:" + this.coordinates + ", creDate:" + this.creationDate + ", salary:" +
                this.salary + ", startDate:" + this.startDate + ", Pos:" + pos + ", status:" +
                st + ", person" + this.person + ")";
    }

}
