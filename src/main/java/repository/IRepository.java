package repository;

import java.util.List;
import java.util.Map;

/**
 * Created by steven on 26/08/16.
 */
public interface IRepository<T> {
    List<T> read(String query, Map<String, Object> params);
    T readSingleItem(String query, Map<String, Object> params);

    T write(T item);
}
