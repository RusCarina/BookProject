package controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Book;
import model.Role;
import model.User;
import model.validator.Notification;
import service.user.AuthenticationService;
import view.AdministratorView;
import view.EmployeeView;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdministratorController {
    private final AdministratorView administratorView;
    private final AuthenticationService authenticationService;
    private final List<String> reportUsers;
    List<Book> addedToCartBooks = new ArrayList<>();

    private final String pdfFilePath = "Raport_Admin_1" +  ".pdf";

    public AdministratorController(AdministratorView administratorView, AuthenticationService authenticationService, List<String> selectedUsers) {
        this.reportUsers = selectedUsers;
        this.administratorView = administratorView;
        this.authenticationService = authenticationService;

        this.administratorView.addCreateButtonListener(new AdministratorController.CreateButtonHandler());
        this.administratorView.addRetrieveButtonListener(new AdministratorController.RetrieveButtonHandler());
        this.administratorView.addUpdateButtonListener(new AdministratorController.UpdateButtonHandler());
        this.administratorView.addDeleteButtonListener(new AdministratorController.DeleteButtonHandler());
        this.administratorView.addShowAllListener(new AdministratorController.ShowAllHandler());
        this.administratorView.addGenerateReportListener(new AdministratorController.ReportHandler());

    }

    private class ReportHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.out.println(reportUsers);
            try {
                PdfWriter writer = new PdfWriter(pdfFilePath);
                PdfDocument pdfDocument = new PdfDocument(writer);
                Document document = new Document(pdfDocument);

                document.add(new Paragraph("Employee Report"));

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                for (String soldBook : reportUsers) {
                    document.add(new Paragraph(soldBook));

                    //document.add(new Paragraph("-------------"));
                }

                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

            reportUsers.add("New employee " + username + " added.\n");

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

                reportUsers.add("Employee " + username + " updated username/password.\n");

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

            reportUsers.add("Employee " + selectedUser.getUsername() + " deleted.\n");

            List<User> users = authenticationService.findAllEmployees();
            administratorView.setUsersData(users);

        }
    }

}
