package repository.app;

import entities.UsersEntity;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import repository.HibernateRepository;
import repository.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by steven on 01/09/16.
 */
@Import({HibernateRepository.class})
@Component
public class UserRepository extends Repository<UsersEntity> implements IUserRepository {
    @Override
    public List<UsersEntity> users() {
        return null;
    }

    @Override
    public List<UsersEntity> usersByName(String name) {
        return null;
    }

    @Override
    public UsersEntity usersById(Integer id) {
        return null;
    }

    @Override
    public UsersEntity add(UsersEntity user) {
        return null;
    }

    @Override
    public UsersEntity authenticate(String username, String password) {
        Map<String,Object> params = new HashMap<>();
        params.put("userName", username);
        params.put("password", password);
        return readSingleItem("FROM UsersEntity WHERE userName = :userName AND password = :password", params);
    }
}
