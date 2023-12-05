package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Book;
import model.Role;
import model.User;
import model.validator.Notification;
import service.user.AuthenticationService;
import view.AdministratorView;
import view.EmployeeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdministratorController {
    private final AdministratorView administratorView;
    private final AuthenticationService authenticationService;
    private final List<User> selectedUsers;
    private int finishCounter =0;
    List<Book> addedToCartBooks = new ArrayList<>();

    public AdministratorController(AdministratorView administratorView, AuthenticationService authenticationService, List<User> selectedUsers) {
        this.selectedUsers = selectedUsers;
        this.administratorView = administratorView;
        this.authenticationService = authenticationService;

        this.administratorView.addCreateButtonListener(new AdministratorController.CreateButtonHandler());
        this.administratorView.addRetrieveButtonListener(new AdministratorController.RetrieveButtonHandler());
        this.administratorView.addUpdateButtonListener(new AdministratorController.UpdateButtonHandler());
        this.administratorView.addDeleteButtonListener(new AdministratorController.DeleteButtonHandler());
        this.administratorView.addShowAllListener(new AdministratorController.ShowAllHandler());
    }

    private class ShowAllHandler implements EventHandler<ActionEvent> {

        private List<User> users;

        @Override
        public void handle(ActionEvent event) {
            users = authenticationService.findAllEmployees();
            //System.out.println(users);
            administratorView.setUsersData(users);
        }
    }

    public class CreateButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            List<User> users;
            users = authenticationService.findAllEmployees();
            int ok = 0;

            String username = administratorView.getUsernameCreate();
            String password = administratorView.getPasswordCreate();

            Notification<Boolean> registerNotification = authenticationService.registerEmployee(username, password);

            for (User u : users){
                if (u.getUsername().equals(username)) {
                    administratorView.setActionTarget1("Username taken!");
                    ok = 1;
                }
            }
            if (registerNotification.hasErrors()) {
                administratorView.setActionTarget1(registerNotification.getFormattedErrors());
            } else {
                if(ok == 0) {
                    administratorView.setActionTarget1("Register successful!");
                }
            }

            List<User> usersUpdatedList = authenticationService.findAllEmployees();
            administratorView.setUsersData(usersUpdatedList);
        }
    }

    private class RetrieveButtonHandler implements EventHandler<ActionEvent> {

        User user;
        String username;

        @Override
        public void handle(ActionEvent event) {

            if (Objects.equals(administratorView.getId(), "Id not found!")){
                administratorView.setRetriveTextArea("Id not found!");
            }
            else {
                Long id = Long.valueOf(administratorView.getId());
                user = authenticationService.findById(id);
                username = user.getUsername();
                administratorView.setRetriveTextArea(username);
            }
        }
    }

    public class UpdateButtonHandler implements EventHandler<ActionEvent> {

        User user;
        String username;
        String password;
        @Override
        public void handle(ActionEvent event) {

            //List<User> users;
            //users = authenticationService.findAll();

            if (Objects.equals(administratorView.getIdUpdate(), "Id not found!")) {

                administratorView.setIdUpdate("Id not found!");
            }
            else {
                Long id = Long.parseLong(administratorView.getIdUpdate());
                user = authenticationService.findById(id);
                username = administratorView.getUsernameUpdate();
                password = administratorView.getPasswordUpdate();

                boolean registerNotification = authenticationService.updateEmployee(id, username, password);

                if (registerNotification){
                    administratorView.setActionTarget2("Something went wrong!");
                }
                else {
                    administratorView.setActionTarget2("Password and Username updated!");
                }
                authenticationService.updateDatabase(user.getId(), username, password);
                List<User> users = authenticationService.findAllEmployees();
                administratorView.setUsersData(users);
            }

        }
    }

    public class DeleteButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

            User selectedUser = administratorView.getSelectedUser();

            authenticationService.remove(selectedUser.getId());

            List<User> users = authenticationService.findAllEmployees();
            administratorView.setUsersData(users);

        }
    }

}
