package service.user;

import model.Role;
import model.User;
import model.validator.Notification;

import java.util.List;

public interface AuthenticationService {
    Notification<Boolean> registerCustomer(String username, String password);
    Notification<Boolean> registerEmployee(String username, String password);
    Notification<Boolean> registerAdministrator(String username, String password);

    Notification<User> login(String username, String password);

    boolean logout(User user);

    List<User> findAll();
    List<Role> findAllRoles();

    User findById(Long id);

    boolean updateEmployee(Long id, String username, String password);

    void remove(Long id);

    boolean updateDatabase(Long id, String username, String password);

    List<User> findAllEmployees();


}