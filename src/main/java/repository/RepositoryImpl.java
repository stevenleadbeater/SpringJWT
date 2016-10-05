package repository;

import java.util.List;
import java.util.Map;

public abstract class RepositoryImpl {
    public abstract <T> List<T> read(String query, Map<String, Object> params);
    public abstract <T> T readSingleItem(String query, Map<String, Object> params);

    public abstract <T> T write(T item);
}
