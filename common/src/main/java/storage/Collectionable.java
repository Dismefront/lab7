package storage;

import java.time.LocalDate;

public interface Collectionable extends Comparable<Collectionable>, Validateable {

    Long getId();

    void setId(long id);

    long getSalary();

    String getName();

    Coordinates getCoordinates();

    LocalDate getCreationDate();

    LocalDate getStartDate();

    Status getStatus();

    Person getPerson();

    Position getPosition();

    @Override
    int compareTo(Collectionable worker);

    @Override
    boolean isValid();

}
