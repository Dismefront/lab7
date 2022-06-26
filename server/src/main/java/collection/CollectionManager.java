package collection;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface containing basic collection functions
 * @param <T> will be used Worker in further program
 */

public interface CollectionManager<T> {

    long generateId();

    boolean add(T element);

    String updateId(T element);

    String removeById(Long id, String username);

    void clear();

    void removeFirst();

    void sort();

    boolean isEmpty();

    List<T> getCollection();

}
