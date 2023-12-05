package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.Book;
import model.User;
import model.validator.Notification;
import model.validator.UserValidator;
import service.book.BookService;
import service.user.AuthenticationService;
import view.AdministratorView;
import view.CustomerView;
import view.EmployeeView;
import view.LoginView;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final BookService bookService;


    public LoginController(LoginView loginView, AuthenticationService authenticationService, BookService bookService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.bookService = bookService;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());

    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

//            username = "admin@admin";
//            password = "adminadmin.1";

//            username = "empl1@empl";
//            password = "employee.1";

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasErrors()){
                loginView.setActionTargetText(loginNotification.getFormattedErrors());
            }else{

                loginView.setActionTargetText("LogIn Successfull!");

                if (loginNotification.getResult().getRoles().get(0).getRole().equals("customer")) {
                    loginView.closeLogIn();
                    openCustomerView();
                } else if (loginNotification.getResult().getRoles().get(0).getRole().equals("administrator"))  {
                    loginView.closeLogIn();
                    openAdministratorView();
                }
                else {
                    loginView.closeLogIn();
                    openEmployeeView();
                }
            }

        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.registerCustomer(username, password);

            if (registerNotification.hasErrors()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Register successful!");
            }
        }
    }

    private void openCustomerView(){

        CustomerView customerView = new CustomerView(new Stage());
        CustomerController customerController = new CustomerController(customerView, bookService);

    }

    private void openAdministratorView(){

        AdministratorView administratorView = new AdministratorView(new Stage());
        List<User> selected = new ArrayList<>();
        AdministratorController adminController = new AdministratorController(administratorView, authenticationService, selected);

    }

    private void openEmployeeView(){

        EmployeeView employeeView = new EmployeeView(new Stage());
        List<User> selected = new ArrayList<>();
        EmployeeController employeeController = new EmployeeController(employeeView, bookService, selected);

    }
}
