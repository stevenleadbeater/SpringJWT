package repository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class Repository<T> implements IRepository<T> {

    @Autowired
    private RepositoryImpl repositoryImpl;

    public List<T> read(String query, Map<String, Object> params) {
        return repositoryImpl.read(query, params);
    }

    public T readSingleItem(String query, Map<String, Object> params) {
        return repositoryImpl.readSingleItem(query, params);
    }

    public T write(T item) {
        return repositoryImpl.write(item);
    }
}

