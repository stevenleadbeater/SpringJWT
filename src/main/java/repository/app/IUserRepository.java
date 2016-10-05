package repository.app;

import entities.UsersEntity;

import java.util.List;

/**
 * Created by steven on 01/09/16.
 */
public interface IUserRepository {
    List<UsersEntity> users();
    List<UsersEntity> usersByName(String name);
    UsersEntity usersById(Integer id);
    UsersEntity add(UsersEntity user);
    UsersEntity authenticate(String username, String password);
}
