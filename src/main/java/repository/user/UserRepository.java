package repository.user;

import model.Role;
import model.User;
import model.validator.Notification;

import java.util.*;

public interface UserRepository {

    List<User> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password);

    boolean save(User user);

    void removeAll();

    boolean existsByUsername(String username);

    List<Role> findAllRoles();

    boolean updateDatabase(Long id, String username, String password) ;


    User findById(Long id);

    void remove(Long id);

    List<User> findAllEmployees();

}